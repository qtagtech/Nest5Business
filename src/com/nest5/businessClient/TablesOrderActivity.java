package com.nest5.businessClient;



import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.bugsense.trace.BugSenseHandler;
import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class TablesOrderActivity extends Activity {
	private Context mContext;
	private Table[] tables;

	
	private Typeface VarelaFont;
	private Typeface BebasFont;
	private TextView tit;
	private LinearLayout mainScreen;
	private LinkedList<CurrentTable<Table,Integer>> openTables;
	LinkedList<PopupWindow> todaslasmesas;
	

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        BugSenseHandler.initAndStartSession(TablesOrderActivity.this, "1a5a6af1");
		setContentView(R.layout.tables_order_layout);
		mContext = this;
		BebasFont = Typeface
				.createFromAsset(getAssets(), "fonts/BebasNeue.otf");
		VarelaFont = Typeface.createFromAsset(getAssets(),
				"fonts/Varela-Regular.otf");
		SharedPreferences prefs  = Util.getSharedPreferences(mContext);
		String mesas = prefs.getString(Setup.SAVED_TABLES, "");
		openTables = new LinkedList<CurrentTable<Table,Integer>>();
		todaslasmesas = new LinkedList<PopupWindow>();
		try{
			GsonBuilder gsonBuilder = new GsonBuilder();
		    Gson gson = gsonBuilder.create();
		    tables = gson.fromJson(mesas,Table[].class);
		}catch(Exception e){
			e.printStackTrace();
			tables = null;
		}
		try{
			GsonBuilder gsonBuilder = new GsonBuilder();
		    Gson gson = gsonBuilder.create();
		    String lista = getIntent().getStringExtra("mesasabiertas");
		    //Log.i("MISPRUEBAS","Lista : "+lista);
		    openTables = gson.fromJson(lista, new TypeToken<LinkedList<CurrentTable<Table,Integer>>>(){}.getType());
		}catch(Exception e){
			e.printStackTrace();
		}
				
		tit = (TextView) findViewById(R.id.tables_layout_title);
		mainScreen = (LinearLayout) findViewById(R.id.table_layout_home);
		
	}
	
	
	@Override
	protected void onResume(){
		super.onResume();
		new Handler().postDelayed(new Runnable(){

		    public void run() {
		    	if(tables != null){
		    		
					for(final Table table : tables){
						LayoutInflater layoutInflater = (LayoutInflater)getBaseContext()
			    				.getSystemService(LAYOUT_INFLATER_SERVICE);
			    		View popupView = layoutInflater.inflate(R.layout.popuptable, null);
						final PopupWindow popupWindow = new PopupWindow(
								popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						Button btnOpenClose = (Button)popupView.findViewById(R.id.toggletable);
						TextView titulo = (TextView) popupView.findViewById(R.id.popuptitle);
						titulo.setTypeface(VarelaFont);
						titulo.setText(table.getName());
						final Spinner clients = (Spinner) popupView.findViewById(R.id.popupspinner);
						Integer[] NumberOfClients = new Integer[table.getClients()];
						for (int i = 0; i < NumberOfClients.length; i++)
						    NumberOfClients[i] = i + 1;
						ArrayAdapter<Integer> adapter = 
								new ArrayAdapter<Integer>(TablesOrderActivity.this, 
										android.R.layout.simple_spinner_item, NumberOfClients);
						adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						clients.setAdapter(adapter);
						if(openTables != null){
							//Log.i("MISPRUEBAS","En el if != null");
							for(CurrentTable<Table,Integer> mesa : openTables){
								if(table.getName().equalsIgnoreCase(mesa.getTable().getName())){
									//poner boton cerrar mesa
									//Log.i("MISPRUEBAS","nombres iguakes");
									btnOpenClose.setText(getResources().getString(R.string.close));
								}
							}
						}
						
						DisplayMetrics displayMetrics = new DisplayMetrics();
						  getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
						  int offsetX = displayMetrics.widthPixels - tit.getMeasuredWidth();
						  int offsetY = displayMetrics.heightPixels;
					
					popupWindow.showAsDropDown(tit, table.getCoordinate_x() - offsetX, table.getCoordinate_y()  - offsetY/2 + 2*tit.getMeasuredHeight());
					todaslasmesas.push(popupWindow);
					//popupWindow.showAtLocation(tit, Gravity.NO_GRAVITY, );
						
						btnOpenClose.setOnClickListener(new Button.OnClickListener(){
							@Override
							public void onClick(View v) {
								if((((Button) v).getText().toString()).equalsIgnoreCase(getResources().getString(R.string.open))){
									int client = (Integer) clients.getSelectedItem();
									Intent returnIntent = new Intent();
									////Log.i("MISPRUEBAS",table.getName());
									returnIntent.putExtra("MIMESA",table);
									returnIntent.putExtra("MIMESACLIENTES",client);
									setResult(RESULT_OK,returnIntent);
									finish();
								}else{//closing table
									Intent returnIntent = new Intent();
									returnIntent.putExtra("MIMESA",table);
									setResult(Setup.CLOSE_TABLE,returnIntent);
									finish();
									
								}
								
							}});
					}
				}
		    	
		    	//popupWindow.showAsDropDown(tit, table.getCoordinate_x(), table.getCoordinate_y());
		    }

		}, 100L);
		
	}
	
	@Override
	public void onPause(){
		super.onPause();
		if(todaslasmesas.size() > 0){
			for(PopupWindow actual : todaslasmesas){
				actual.dismiss();
			}
		}
		
		
	}
    @Override
    protected void onStart()
    {
        super.onStart();
        FlurryAgent.onStartSession(this, "J63XVCZCXV4NN4P2SQZT");
        SharedPreferences prefs = Util.getSharedPreferences(mContext);
        String deviceId = prefs.getString(Setup.DEVICE_REGISTERED_ID, "null");
        String compid = prefs.getString(Setup.COMPANY_ID, "0");
        String jString = "{device_id:"+deviceId+",company:"+compid+"}";
        BugSenseHandler.setUserIdentifier(jString);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        BugSenseHandler.closeSession(TablesOrderActivity.this);
        FlurryAgent.onEndSession(this);
    }

}
