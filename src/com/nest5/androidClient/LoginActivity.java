//package com.nest5.androidClient;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.cookie.Cookie;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//
//import com.google.gson.Gson;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.PixelFormat;
//import android.graphics.Typeface;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.EditText;
//import android.widget.Toast;
//
//
//public class LoginActivity extends Activity {
//	/**
//     * Tag for logging.
//     */
//    private static final String TAG = "LoginActivity";
//
//    /**
//     * The current context.
//     */
//    private Context mContext = this;
//    
//    /**
//     * Begins the activity.
//     */
//    
//    Typeface BebasFont;
//    Typeface VarelaFont;
//    RestService restService;
//    
//    
//    private EditText user;
//    private EditText pass;
//    
//    
//    
//    
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        
//        super.onCreate(savedInstanceState);
//        getWindow().setFormat(PixelFormat.RGBA_8888);
//	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
//	    BebasFont= Typeface.createFromAsset(getAssets(), "fonts/BebasNeue.otf");
//	    VarelaFont= Typeface.createFromAsset(getAssets(), "fonts/Varela-Regular.otf");
//	    
//      
//        
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        SharedPreferences prefs = Util.getSharedPreferences(mContext);
//        String connectionStatus = prefs.getString(Util.CONNECTION_STATUS, Util.DISCONNECTED);
//        if (Util.DISCONNECTED.equals(connectionStatus)) {
//            startActivity(new Intent(this, AccountsActivity.class));
//        }
//        String loggedStatus = prefs.getString(Util.LOGGED_STATUS, Util.LOGGEDOUT);
//        if(Util.LOGGEDIN.equals(loggedStatus))
//        {
//        	Intent inten = new Intent(mContext,Initialactivity.class);
//	    	inten.putExtra("com.nest5.androidClient.layout", R.layout.home);
//	    	startActivity(inten);
//	    	finish();
//        }
//        
//        
//        setContentView(R.layout.login);
//        user = (EditText) findViewById(R.id.username);
//	    pass = (EditText) findViewById(R.id.password);
//	    
//	    user.setTypeface(BebasFont);
//	    pass.setTypeface(BebasFont);
//        
//        
//        
//        
// 
//    }
//
//    /**
//     * Shuts down the activity.
//     */
//    @Override
//    public void onDestroy() {
//        
//        super.onDestroy();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_menu, menu);
//        // Invoke the Register activity
//        menu.getItem(0).setIntent(new Intent(this, AccountsActivity.class));
//        return true;
//    }
//    
//    @Override
//    public void onBackPressed() {
//    	
//	    	finish();
//       
//    }
//    
//    public void fbLogin(View v){
//    	Intent inten = new Intent(mContext,FacebookActivity.class);
//    	//inten.putExtra("com.nest5.androidClient.layout", R.layout.deals);
//    	startActivity(inten);
//    	
//    }
//    public void doLogin(View v){
//    	
//    	String u = user.getText().toString();
//    	String p = pass.getText().toString();
//    	restService = new RestService(mHandlerGet, this, "http://nest5stage.herokuapp.com/user/api/restCall"); //Create new rest service for get
//    	
//        restService.setCredentials(u, p);
//        
//        try {
//        	
//			restService.execute(RestService.GET); //Executes the request with the HTTP GET verb
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    	
//    }
//    
//    private final Handler mHandlerGet = new Handler(){
//    	@Override
//    	public void handleMessage(Message msg){
//    		User user = null; 
//    		try{
//    				Gson gson = new Gson();
//    				user = gson.fromJson((String)msg.obj, User.class);
//    		}
//    		catch(Exception e){
//    			e.printStackTrace();
//    		}
//    		
//    				String status = "unathorized";
//    				if(user != null){
//    					//Toast.makeText(mContext,user.username , Toast.LENGTH_LONG).show();
//    					status = "authorized";
//    				}
//    				else{
//    					//Toast.makeText(mContext,"Login Incorrecto" , Toast.LENGTH_LONG).show();
//    				}
//    				
//    				
//    	    		String message = null;
//    	            String loggedStatus = Util.LOGGEDOUT;
//    	            if (status == "authorized") {
//    	                message = getResources().getString(R.string.registration_succeeded);
//    	                loggedStatus = Util.LOGGEDIN;
//    	                Log.i(TAG, "Registrado");
//    	                
//    	            } else if (status == "unuthorizedok") {
//    	                message = getResources().getString(R.string.unregistration_succeeded);
//    	                Log.i(TAG, "Salido");
//    	            } else {
//    	                message = getResources().getString(R.string.registration_error);
//    	                Log.i(TAG, "Error");
//    	                String usuario = user != null ? user.username: "usuario";
//        	            Toast.makeText(mContext,String.format(message, usuario), Toast.LENGTH_LONG).show();
//    	            }
//    	           
//
//    	            // Set connection status
//    	            SharedPreferences prefs = Util.getSharedPreferences(mContext);
//    	            prefs.edit().putString(Util.LOGGED_STATUS, loggedStatus).commit();
//    	            
//    	            if(status == "authorized"){
//    	            	Intent inten = new Intent(mContext,Initialactivity.class);
//    	    	    	inten.putExtra("com.nest5.androidClient.layout", R.layout.home);
//    	    	    	startActivity(inten);
//    	    	    	finish();
//    	            }
//    	            
//    	            // Display a notification
//    	           
//    		
//    		
//    			
//    			
//    		}		
//    };
//	
//	
//	
//    
//    
//    
//}
