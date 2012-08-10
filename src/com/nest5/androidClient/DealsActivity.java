package com.nest5.androidClient;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;



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
import android.location.Location;
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

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.gson.*;


public class DealsActivity extends MapActivity {
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
    
    MyLocationOverlay myLocationOverlay;
    
    MyOverlay positionOverlay;
    
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    
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
        //setContentView(R.layout.map);
        
        LinearLayout dealList = (LinearLayout) findViewById(R.id.company_info_deals);
        TextView text = (TextView) findViewById(R.id.header_username);
        text.setTypeface(BebasFont);
        text.setText("Promociones Disponibles");
        
        for (Promo it : deal.promos) {
            
        
        	PromoRow promoR = new PromoRow(mContext);
        	
        	String titleText = it.action.equals("Compra") ? getString(R.string.buyPerk, it.reqQTY,it.requirement) : getString(R.string.visitPerk, it.reqQTY,it.requirement);
        	String perkText = it.perk;
        	promoR.title.setText(titleText);
        	promoR.perk.setText(perkText);
        	promoR.title.setTypeface(BebasFont);
        	promoR.perk.setTypeface(BebasFont);
        	promoR.nest5.setTypeface(VarelaFont);
            dealList.addView(promoR,0);
        }
        
        title = (TextView) findViewById(R.id.company_info_title);
        description = (ScrollingTextView) findViewById(R.id.company_info_description);
        address = (ScrollingTextView) findViewById(R.id.company_info_address);
        
        title.setText(deal.company.name);
        description.setText(deal.company.name);
        address.setText(deal.company.name +" - "+deal.company.address);
        title.setTypeface(BebasFont);
        description.setTypeface(VarelaFont);
        address.setTypeface(VarelaFont);
        final MapView mapView = (MapView) findViewById(R.id.mapView);
        //mapView.setBuiltInZoomControls(true);
        //myLocationOverlay = new MyLocationOverlay(this, mapView);
        //mapView.getOverlays().add(myLocationOverlay);
        final MapController mapController = mapView.getController();
        float latitude = deal.latitude;
        float longitude = deal.longitude;
        GeoPoint position = new GeoPoint((int)(latitude*1E6), (int)(longitude*1E6));
        /*myLocationOverlay.runOnFirstFix(new Runnable() {
          public void run() {
        	 //mapView.getController().animateTo(position);
            mapController.animateTo(myLocationOverlay.getMyLocation());
            mapController.setCenter(myLocationOverlay.getMyLocation());
           
            
             }
        });
        
        myLocationOverlay.enableCompass(); 
        myLocationOverlay.enableMyLocation();*/
        
        
        positionOverlay = new MyOverlay(DealsActivity.this);
        List<Overlay> overlays = mapView.getOverlays();
        overlays.add(positionOverlay);
        
        positionOverlay.setLocation(position);
        mapController.setCenter(position);
        mapController.setZoom(18);
        
        /*
        float latitude = deal.latitude;
        float longitude = deal.longitude;
        GeoPoint position = new GeoPoint((int)latitude, (int)longitude);
        MapController mapController = mapView.getController();
  	    mapController.animateTo(position);
        mapController.setZoom(18);
        mapController.setCenter(position);
        positionOverlay = new MyOverlay();
        List<Overlay> overlays = mapView.getOverlays();
        overlays.add(positionOverlay);
        positionOverlay.setLocation(DealsActivity.this,position);*/
        
        
        
        
        
        
        
 
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
    public void onPause()
    {
    	//myLocationOverlay.disableCompass(); 
       // myLocationOverlay.disableMyLocation();
    	super.onPause();
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
