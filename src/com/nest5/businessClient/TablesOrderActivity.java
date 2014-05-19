package com.nest5.businessClient;



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
import android.content.SharedPreferences;
import android.graphics.Typeface;

public class TablesOrderActivity extends Activity {
	private Context mContext;
	private Table[] tables;

	
	private Typeface VarelaFont;
	private Typeface BebasFont;
	private TextView tit;
	private LinearLayout mainScreen;
	

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tables_order_layout);
		mContext = this;
		BebasFont = Typeface
				.createFromAsset(getAssets(), "fonts/BebasNeue.otf");
		VarelaFont = Typeface.createFromAsset(getAssets(),
				"fonts/Varela-Regular.otf");
		SharedPreferences prefs  = Util.getSharedPreferences(mContext);
		String mesas = prefs.getString(Setup.SAVED_TABLES, "");
		try{
			GsonBuilder gsonBuilder = new GsonBuilder();
		    Gson gson = gsonBuilder.create();
		    tables = gson.fromJson(mesas,Table[].class);
		}catch(Exception e){
			tables = null;
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
		    		
					for(Table table : tables){
						LayoutInflater layoutInflater = (LayoutInflater)getBaseContext()
			    				.getSystemService(LAYOUT_INFLATER_SERVICE);
			    		View popupView = layoutInflater.inflate(R.layout.popuptable, null);
						final PopupWindow popupWindow = new PopupWindow(
								popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						Button btnOpenClose = (Button)popupView.findViewById(R.id.toggletable);
						TextView titulo = (TextView) popupView.findViewById(R.id.popuptitle);
						titulo.setTypeface(VarelaFont);
						titulo.setText(table.getName());
						Spinner clients = (Spinner) popupView.findViewById(R.id.popupspinner);
						Integer[] NumberOfClients = new Integer[table.getClients()];
						for (int i = 0; i < NumberOfClients.length; i++)
						    NumberOfClients[i] = i + 1;
						ArrayAdapter<Integer> adapter = 
								new ArrayAdapter<Integer>(TablesOrderActivity.this, 
										android.R.layout.simple_spinner_item, NumberOfClients);
						adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						clients.setAdapter(adapter);
						btnOpenClose.setOnClickListener(new Button.OnClickListener(){
							@Override
							public void onClick(View v) {
								
							}});
								
							  DisplayMetrics displayMetrics = new DisplayMetrics();
							  getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
							  int offsetX = displayMetrics.widthPixels - tit.getMeasuredWidth();
							  int offsetY = displayMetrics.heightPixels - tit.getMeasuredHeight();
						
						popupWindow.showAsDropDown(tit, table.getCoordinate_x() - offsetX, table.getCoordinate_y() - tit.getMeasuredHeight());
						//popupWindow.showAtLocation(tit, Gravity.NO_GRAVITY, );
						
						
					}
				}
		    	
		    	//popupWindow.showAsDropDown(tit, table.getCoordinate_x(), table.getCoordinate_y());
		    }

		}, 100L);
		
	}

}
