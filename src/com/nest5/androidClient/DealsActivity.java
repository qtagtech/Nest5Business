package com.nest5.androidClient;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.WindowManager;


import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.*;


public class DealsActivity extends Activity {
	/**
     * Tag for logging.
     */
    private static final String TAG = "DealsActivity";

    /**
     * The current context.
     */
    private Context mContext = this;
    
    /**
     * Begins the activity.
     */
    
    Typeface BebasFont;
    Typeface VarelaFont;
    
    private Deal deal;
    
    TextView title;
    ScrollingTextView description;
    ScrollingTextView address;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
	    BebasFont= Typeface.createFromAsset(getAssets(), "fonts/BebasNeue.otf");
	    VarelaFont= Typeface.createFromAsset(getAssets(), "fonts/Varela-Regular.otf");
	    
          
        
        
        //Log.i(TAG,String.valueOf(deal.promos.size()));
        
        //Toast.makeText(mContext,"hola" , Toast.LENGTH_LONG).show();


        
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences prefs = Util.getSharedPreferences(mContext);
        String connectionStatus = prefs.getString(Util.CONNECTION_STATUS, Util.DISCONNECTED);
        if (Util.DISCONNECTED.equals(connectionStatus)) {
            startActivity(new Intent(this, AccountsActivity.class));
        }
        Bundle b = getIntent().getExtras();
        
        deal = b.getParcelable("com.nest5.androidclient.Deal");
        
        setContentView(R.layout.company_info);
        
        LinearLayout dealList = (LinearLayout) findViewById(R.id.company_info_deals);
        
        for (Promo it : deal.promos) {
            
        
        	PromoRow promoR = new PromoRow(mContext);
        	
        	String titleText = it.action.equals("buy") ? getString(R.string.buyPerk, it.reqQTY,it.requirement) : getString(R.string.visitPerk, it.reqQTY,it.requirement);
        	String perkText = String.valueOf(it.perkQTY) +" "+ it.perk;
        	promoR.title.setText(titleText);
        	promoR.perk.setText(perkText);
        	promoR.title.setTypeface(BebasFont);
        	promoR.perk.setTypeface(BebasFont);
        	promoR.nest5.setTypeface(VarelaFont);
            dealList.addView(promoR);
        }
        
        title = (TextView) findViewById(R.id.company_info_title);
        description = (ScrollingTextView) findViewById(R.id.company_info_description);
        address = (ScrollingTextView) findViewById(R.id.company_info_address);
        
        title.setText(deal.company.name);
        description.setText(deal.company.name);
        address.setText(deal.company.name + " Calle 7AA # 30 - 244, Ed. El Vergel Apto 2104");
        title.setTypeface(BebasFont);
        description.setTypeface(VarelaFont);
        address.setTypeface(VarelaFont);
        
        
 
    }

    /**
     * Shuts down the activity.
     */
    @Override
    public void onDestroy() {
        
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        // Invoke the Register activity
        menu.getItem(0).setIntent(new Intent(this, AccountsActivity.class));
        return true;
    }
    
    @Override
    public void onBackPressed() {
    	Intent inten = new Intent(mContext,Initialactivity.class);
	    	inten.putExtra("com.nest5.androidClient.layout", R.layout.deals);
	    	startActivity(inten);
	    	finish();
       
    }
	
	
	
    
    
    
}

class PromoRow extends LinearLayout {
	
	ImageView image;
	TextView title;
	TextView nest5;
	TextView perk;

	public PromoRow(Context context) {
		super(context);
		//Inflate the view from the XML Layout
		
		String infSrvice = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(infSrvice);
		li.inflate(R.layout.deal_row,this,true);
		
		//Get References to child controls
		//image = (ImageView) findViewById(R.id.deal_row_picture);
		title = (TextView) findViewById(R.id.deal_row_title);
		nest5 = (TextView) findViewById(R.id.deal_row_nest5);
		perk = (TextView) findViewById(R.id.deal_row_perk);
		
		
	}
	
}
