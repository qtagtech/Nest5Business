package com.nest5.businessClient;



import java.util.ArrayList;
import java.util.List;







import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;



public class WifiDirectDialog extends DialogFragment {
	
	public interface DeviceActionListener {
		void showDetails(WifiP2pDevice device);

        void cancelDisconnect();

        void connect(WifiP2pConfig config);

        void disconnect();
        
    }
	
private DeviceActionListener deviceActionListener;
private DialogFragment frag;
private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
ProgressDialog progressDialog = null;
View mContentView = null;
private WifiP2pDevice device;
private ListView deviceList;
private ListAdapter deviceAdapter;


@Override
public void onAttach(Activity activity){
	super.onAttach(activity);
	try
	{
		deviceActionListener = (DeviceActionListener) activity;
	}
	catch(ClassCastException e){
		throw new ClassCastException(activity.toString() + " must implement DeviceActionListener");
	}
}


 public WifiDirectDialog() {
     // Empty constructor required for DialogFragment
 }
 public WifiDirectDialog(List<WifiP2pDevice> _peers) {
     this.peers = _peers;
 }
 
 

 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
	 frag = this;
     View view = inflater.inflate(R.layout.devices, container);
     deviceList = (ListView) view.findViewById(R.id.device_list);
     deviceAdapter = new WiFiPeerListAdapter(this.getActivity(), R.layout.row_devices, peers);
     deviceList.setAdapter(deviceAdapter);
     deviceList.setOnItemClickListener(new OnItemClickListener() {

    	 
    	 
    	 @Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
    		 WifiP2pDevice device = (WifiP2pDevice) deviceAdapter.getItem(position);
 	        ((DeviceActionListener) frag.getActivity()).showDetails(device);
 	        frag.dismiss();
			
		}
    	 
	});
     //progressDialog = new ProgressDialog(view.getContext());
     //this.onInitiateDiscovery();
     
     

     return view;
 }
 
 public WifiP2pDevice getDevice() {
     return device;
 }
 
 private static String getDeviceStatus(int deviceStatus) {
     Log.d(Initialactivity.TAG, "Peer status :" + deviceStatus);
     switch (deviceStatus) {
         case WifiP2pDevice.AVAILABLE:
             return "Disponible";
         case WifiP2pDevice.INVITED:
             return "Invitado";
         case WifiP2pDevice.CONNECTED:
             return "Conectado";
         case WifiP2pDevice.FAILED:
             return "Falla";
         case WifiP2pDevice.UNAVAILABLE:
             return "No Disponible";
         default:
             return "Desconocido";

     }
 }
 
 
 
 public void clearPeers() {
     peers.clear();
     ((WiFiPeerListAdapter) deviceAdapter).notifyDataSetChanged();
 }
 
 public void onInitiateDiscovery() {
     /*if (progressDialog != null && progressDialog.isShowing()) {
         progressDialog.dismiss();
     }
     progressDialog = ProgressDialog.show(getActivity(), "Atrás para Cancelar", "Buscando Dispositivos", true,
             true, new DialogInterface.OnCancelListener() {

                 @Override
                 public void onCancel(DialogInterface dialog) {
                     frag.dismiss();
                 }
             });*/
 }
 
 
 
 
 /**
  * Array adapter for ListFragment that maintains WifiP2pDevice list.
  */
 private class WiFiPeerListAdapter extends ArrayAdapter<WifiP2pDevice> {

     private List<WifiP2pDevice> items;

     /**
      * @param context
      * @param textViewResourceId
      * @param objects
      */
     public WiFiPeerListAdapter(Context context, int textViewResourceId,
             List<WifiP2pDevice> objects) {
         super(context, textViewResourceId, objects);
         items = objects;

     }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
         View v = convertView;
         if (v == null) {
             LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(
                     Context.LAYOUT_INFLATER_SERVICE);
             v = vi.inflate(R.layout.row_devices, null);
         }
         WifiP2pDevice device = items.get(position);
         if (device != null) {
             TextView top = (TextView) v.findViewById(R.id.device_name);
             TextView bottom = (TextView) v.findViewById(R.id.device_details);
             if (top != null) {
                 top.setText(device.deviceName);
             }
             if (bottom != null) {
                 //bottom.setText(device.status);
             }
         }

         return v;

     }
 }
}