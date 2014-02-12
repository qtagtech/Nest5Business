package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.Fragment;
import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class ScanDevicesFragment extends DialogFragment {
	
	public interface SelectDevice {
        public void onDeviceSelected(String address);
        
    }
	
	
	
private Context mContext;

private View view;

private DialogFragment frag;
	
private SelectDevice selectDevice;


private static final String TAG = "DeviceListActivity";
private static final boolean D = true;

// Return Intent extra
public static String EXTRA_DEVICE_ADDRESS = "device_address";

// Member fields
private BluetoothAdapter mBtAdapter;
private ArrayAdapter<String> mPairedDevicesArrayAdapter;
private ArrayAdapter<String> mNewDevicesArrayAdapter;

@Override
public void onAttach(Activity activity){
	super.onAttach(activity);
	try
	{
		selectDevice = (SelectDevice) activity;
	}
	catch(ClassCastException e){
		throw new ClassCastException(activity.toString() + " must implement SelectDevice");
	}
}


 public ScanDevicesFragment() {
     // Empty constructor required for DialogFragment
 }
 



@Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
	 
     view = inflater.inflate(R.layout.device_list, container);
     frag = this;
     mContext = view.getContext();
     Button scanButton = (Button) view.findViewById(R.id.button_scan);
     scanButton.setOnClickListener(new OnClickListener() {
         public void onClick(View v) {
             doDiscovery();
             v.setVisibility(View.GONE);
         }
     });
     
  // Initialize array adapters. One for already paired devices and
     // one for newly discovered devices
     mPairedDevicesArrayAdapter = new ArrayAdapter<String>(mContext, R.layout.device_name);
     mNewDevicesArrayAdapter = new ArrayAdapter<String>(mContext, R.layout.device_name);

     // Find and set up the ListView for paired devices
     ListView pairedListView = (ListView) view.findViewById(R.id.paired_devices);
     pairedListView.setAdapter(mPairedDevicesArrayAdapter);
     pairedListView.setOnItemClickListener(mDeviceClickListener);

     // Find and set up the ListView for newly discovered devices
     ListView newDevicesListView = (ListView) view.findViewById(R.id.new_devices);
     newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
     newDevicesListView.setOnItemClickListener(mDeviceClickListener);
     
  // Register for broadcasts when a device is discovered
     IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
     mContext.registerReceiver(mReceiver, filter);

     // Register for broadcasts when discovery has finished
     filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
     mContext.registerReceiver(mReceiver, filter);

     // Get the local Bluetooth adapter
     mBtAdapter = BluetoothAdapter.getDefaultAdapter();
     
  // Get a set of currently paired devices
     Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

     // If there are paired devices, add each one to the ArrayAdapter
     if (pairedDevices.size() > 0) {
         view.findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
         for (BluetoothDevice device : pairedDevices) {
             mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
         }
     } else {
         String noDevices = "No hay Dispositivos";
         mPairedDevicesArrayAdapter.add(noDevices);
     }
     
   
     
     
     

     return view;
 }

@Override
public void onDestroy() {
    super.onDestroy();

    // Make sure we're not doing discovery anymore
    if (mBtAdapter != null) {
        mBtAdapter.cancelDiscovery();
    }

    // Unregister broadcast listeners
    mContext.unregisterReceiver(mReceiver);
}

/**
 * Start device discover with the BluetoothAdapter
 */
private void doDiscovery() {
    //if (D) Log.d(TAG, "doDiscovery()");

    // Indicate scanning in the title
    //setProgressBarIndeterminateVisibility(true);
    //setTitle("Buscando Dispositivos");
	getDialog().setTitle("Buscando Impresoras");
    // Turn on sub-title for new devices
    view.findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

    // If we're already discovering, stop it
    if (mBtAdapter.isDiscovering()) {
        mBtAdapter.cancelDiscovery();
    }

    // Request discover from BluetoothAdapter
    mBtAdapter.startDiscovery();
}

//The on-click listener for all devices in the ListViews
private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
    public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
        // Cancel discovery because it's costly and we're about to connect
        mBtAdapter.cancelDiscovery();

        // Get the device MAC address, which is the last 17 chars in the View
        String info = ((TextView) v).getText().toString();
        String address = info.substring(info.length() - 17);

        // Create the result Intent and include the MAC address
        //Intent intent = new Intent();
        //intent.putExtra(EXTRA_DEVICE_ADDRESS, address);

        // Set result and finish this Activity
        //setResult(Activity.RESULT_OK, intent);
        //poner en el interface el valor del seleccionado
        selectDevice.onDeviceSelected(address);
        frag.dismiss();
    }
  
};

//The BroadcastReceiver that listens for discovered devices and
// changes the title when discovery is finished
private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        // When discovery finds a device
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            // Get the BluetoothDevice object from the Intent
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            // If it's already paired, skip it, because it's been listed already
            if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        // When discovery is finished, change the Activity title
        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
           
        	//getDialog().setTitle("Selecciona una Impresora");
            if (mNewDevicesArrayAdapter.getCount() == 0) {
                String noDevices = "Ninguno Encontrado";
                mNewDevicesArrayAdapter.add(noDevices);
            }
        }
    }
};


}