package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;

public class TablesActivity extends Activity {
	private Context mContext;
	int numTables;
	int maxClients;
	Integer[] NumberOfClients;
	Integer[] tables;
	LinkedList<PopupWindow> mesas;
	LinkedHashMap<PopupWindow,Integer> ClientesxMesa;
	LinkedHashMap<PopupWindow,String> NombresxMesa;
	LinkedHashMap<PopupWindow,int[]> CoordenadasxMesa;

	
	private Typeface VarelaFont;
	private Typeface BebasFont;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tables_layout);
		mContext = this;
		BebasFont = Typeface
				.createFromAsset(getAssets(), "fonts/BebasNeue.otf");
		VarelaFont = Typeface.createFromAsset(getAssets(),
				"fonts/Varela-Regular.otf");
		SharedPreferences defaultprefs = PreferenceManager.getDefaultSharedPreferences(mContext);
		numTables = Integer.parseInt(defaultprefs.getString("quant_tables", "30"));
		maxClients = Integer.parseInt(defaultprefs.getString("max_clients_table", "10"));
		NumberOfClients = new Integer[maxClients];
		for (int i = 0; i < NumberOfClients.length; i++)
		    NumberOfClients[i] = i + 1;
		tables = new Integer[numTables];
		for (int i = 0; i < tables.length; i++)
		    tables[i] = i + 1;
		mesas = new LinkedList<PopupWindow>();
		ClientesxMesa = new LinkedHashMap<PopupWindow, Integer>();
		NombresxMesa = new LinkedHashMap<PopupWindow, String>();
		CoordenadasxMesa = new LinkedHashMap<PopupWindow, int[]>();
		final Button btnOpenPopup = (Button) findViewById(R.id.openpopup);
		final Button btnSaveTables = (Button) findViewById(R.id.save_tables);
		btnOpenPopup.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				LayoutInflater layoutInflater = 
						(LayoutInflater)getBaseContext()
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				View popupView = layoutInflater.inflate(R.layout.popup, null);
				final PopupWindow popupWindow = new PopupWindow(
						popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				
				Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
				mesas.push(popupWindow);
				int actual = mesas.size();
				Spinner popupSpinner = (Spinner)popupView.findViewById(R.id.popupspinner);
				TextView titulo = (TextView) popupView.findViewById(R.id.popuptitle);
				titulo.setTypeface(VarelaFont);
				Spinner number = (Spinner) popupView.findViewById(R.id.popupnumber);
				ArrayAdapter<Integer> adapter2 = 
						new ArrayAdapter<Integer>(TablesActivity.this, 
								android.R.layout.simple_spinner_item, tables);
				adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				number.setAdapter(adapter2);
				try{
					number.setSelection(actual - 1);
				}catch(Exception e){
					e.printStackTrace();
				}
				ArrayAdapter<Integer> adapter = 
						new ArrayAdapter<Integer>(TablesActivity.this, 
								android.R.layout.simple_spinner_item, NumberOfClients);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				popupSpinner.setAdapter(adapter);
				
				btnDismiss.setOnClickListener(new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						//Toast.makeText(mContext, (String)((TextView)popupWindow.getContentView().findViewById(R.id.popuptitle)).getText(),Toast.LENGTH_LONG).show();
						mesas.remove(popupWindow);
						CoordenadasxMesa.remove(popupWindow);
						popupWindow.dismiss();
					}});
				popupWindow.showAsDropDown(btnOpenPopup, 50, -30);
				
				popupView.setOnTouchListener(new OnTouchListener() {
					int orgX, orgY;
					int offsetX, offsetY;
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							orgX = (int) event.getX();
							orgY = (int) event.getY();
							break;
						case MotionEvent.ACTION_MOVE:
							offsetX = (int)event.getRawX() - orgX;
							offsetY = (int)event.getRawY() - orgY;
							popupWindow.update(offsetX, offsetY, -1, -1, true);
							break;
						}
						int[] coors = {offsetX,offsetY};
						CoordenadasxMesa.remove(popupWindow);
						CoordenadasxMesa.put(popupWindow, coors);
						return true;
						
					}});
			}

		});
		
		btnSaveTables.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String list = "";
				LinkedList<Table> createdTables = new LinkedList<Table>(); ;
				if(mesas.size() > 0 ){ 
					for(PopupWindow mesa : mesas){
						String name = (String)((TextView)mesa.getContentView().findViewById(R.id.popuptitle)).getText();
						int number = (Integer)((Spinner)mesa.getContentView().findViewById(R.id.popupnumber)).getSelectedItem();
						String complete = name+String.valueOf(number);
						int clients = (Integer)((Spinner)mesa.getContentView().findViewById(R.id.popupspinner)).getSelectedItem();
						int[] coordinates = new int[2];
						LinearLayout mainl = (LinearLayout) mesa.getContentView().findViewById(R.id.main_table_layout);
						mainl.getLocationOnScreen(coordinates);
						Table actual = new Table(complete,clients,coordinates);
						createdTables.push(actual);
					}
					GsonBuilder gb = new GsonBuilder();
					Gson gson = gb.create();
					list = gson.toJson(createdTables);
					SharedPreferences prefs = Util.getSharedPreferences(mContext);
					prefs.edit().putBoolean(Setup.HAS_CONFIGURED_TABLES, true)
					.putString(Setup.SAVED_TABLES, list).commit();
				}
				Intent returnIntent = new Intent();
				returnIntent.putExtra(Setup.SAVED_TABLES,list);
				setResult(RESULT_OK,returnIntent);
				finish();
				
				
			}
		});
	}

}
