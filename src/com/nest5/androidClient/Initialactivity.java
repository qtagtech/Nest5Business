/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.nest5.androidClient;




import com.nest5.androidClient.MyHorizontalScrollView;

import com.nest5.androidClient.ViewUtils;



import com.nest5.androidClient.MyHorizontalScrollView.SizeCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.w3c.dom.Text;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.nest5.androidClient.MyLocation.LocationResult;


import android.R.bool;
import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main activity - requests "Hello, World" messages from the server and provides
 * a menu item to invoke the accounts activity.
 */
public class Initialactivity extends Activity {
    /**
     * Tag for logging.
     */
    private static final String TAG = "Initialactivity";

    /**
     * The current context.
     */
    private Context mContext = this;
    
    private int lay = R.layout.home;
    
    Typeface BebasFont;
    Typeface VarelaFont;
    RestService restService;
    
    EditText user;
    EditText pass;
    
    TextView userName;
    ImageView internetConnectionStatus;
    
    ProgressDialog dialogLogin;
    ProgressDialog dialogUpdateExtra;
    
    //JSONObject userInstance;
    User userInstance = null;
    
    
    //Respuesta cuando obtiene sellos al leer código QR
    Answer payload;
    
    //Saber si llama poner stampCard desde lista de promociones de usuario
    Boolean fromMyDeals = false;
    
    //Cuando da clic en promoción de usuario
    MyDeal currentDeal;
    
  //Cuando da clic en cupón de usuario
    AnswerCoupon currentCoupon;
    
    //Validar si botón redime cupón o sello
    
    Boolean redeemCoupon = false;
    
    Boolean redimiendo = false;
    
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    
    //private LocationManager locationManager;
	//private String provider;
    private LocationResult locationResult;
    private MyLocation myLocation;
	private double lat;
	private double lng;
	
	//private Location location;
	
	
	//varibales de menu
	MyHorizontalScrollView scrollView;
    View menu;
    View app;
    ImageView btnSlide;
    boolean menuOut = false;
    Handler handler = new Handler();
    int btnWidth;
    
    Bundle savedInstanceState;
	
	
	
	
	 

    /**
     * A {@link BroadcastReceiver} to receive the response from a register or
     * unregister request, and to update the UI.
     */
    private final BroadcastReceiver mUpdateUIReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	String accountId = intent.getStringExtra("USER_ID");
            String accountName = intent.getStringExtra(DeviceRegistrar.ACCOUNT_NAME_EXTRA);
            int status = intent.getIntExtra(DeviceRegistrar.STATUS_EXTRA,
                    DeviceRegistrar.ERROR_STATUS);
            String message = null;
            String connectionStatus = Util.DISCONNECTED;
            SharedPreferences prefs = Util.getSharedPreferences(mContext);
            if (status == DeviceRegistrar.REGISTERED_STATUS) {
                message = getResources().getString(R.string.registration_succeeded);
                connectionStatus = Util.CONNECTED;
                prefs.edit().putString(Util.CONNECTION_STATUS, connectionStatus).commit();
                //Enviar email al servidor: params.email, params.android
                
                
                restService = new RestService(sendEmailHandler, mContext, "http://www.nest5.com/api/user/newAndroidUser");
                restService.addParam("email", accountName);
                restService.addParam("android", accountId);
                //Toast.makeText(mContext, accountId, Toast.LENGTH_LONG).show();
                restService.setCredentials("apiadmin", Setup.apiKey);
                prefs.edit().putString(Util.LOGGED_STATUS, Util.LOGGINGIN);
                try {
                	
        			restService.execute(RestService.POST); //Executes the request with the HTTP POST verb
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
            } else if (status == DeviceRegistrar.UNREGISTERED_STATUS) {
            	prefs.edit().putString(Util.CONNECTION_STATUS, connectionStatus).commit();
                message = getResources().getString(R.string.unregistration_succeeded);
                //Iniciar actividad de Account
                startActivity(new Intent(mContext, AccountsActivity.class));
            } else {
            	
            	prefs.edit().putString(Util.CONNECTION_STATUS, connectionStatus).commit();
                message = getResources().getString(R.string.registration_error);
                startActivity(new Intent(mContext, AccountsActivity.class));
            }

                    

            // Display a notification
//            Util.generateNotification(mContext, String.format(message, accountName));
        }
    };

    /**
     * Begins the activity.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        getWindow().setFormat(PixelFormat.RGBA_8888);
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);

        // Register a receiver to provide register/unregister notifications
        registerReceiver(mUpdateUIReceiver, new IntentFilter(Util.UPDATE_UI_INTENT));
        
     // Get the location manager
     		/*locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
     		// Define the criteria how to select the locatioin provider -> use
     		// default
     		Criteria criteria = new Criteria();
     		provider = locationManager.getBestProvider(criteria, false);
     		Location _location = locationManager.getLastKnownLocation(provider);
     		
     		//Log.i("InitialActivity","aca1");
     		// Initialize the location fields
     		if (_location != null) {
     			//Log.i("InitialActivity","aca2");
     			location = _location;
     			//Toast.makeText(mContext, String.valueOf(location.getAccuracy()), Toast.LENGTH_LONG).show();
     			System.out.println("Provider " + provider + " has been selected.");
     			lat =  (location.getLatitude());
     			lng =  (location.getLongitude());
     			
     		}*/ 
        
         locationResult = new LocationResult(){
            @Override
            public void gotLocation(Location location){
                //Got the location!
            	//Toast.makeText(mContext, String.valueOf(location.getLatitude()), Toast.LENGTH_LONG).show();
            	lat = location.getLatitude();
            	lng = location.getLongitude();
            }
        };
        myLocation = new MyLocation();
        myLocation.getLocation(this, locationResult);
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstance)
    {
    	super.onSaveInstanceState(savedInstance);
    	
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstance)
    {
    	this.savedInstanceState = savedInstance;
    }

    @Override
    public void onResume() {
        super.onResume();
        BebasFont= Typeface.createFromAsset(getAssets(), "fonts/BebasNeue.otf");
	    VarelaFont= Typeface.createFromAsset(getAssets(), "fonts/Varela-Regular.otf");
        SharedPreferences prefs = Util.getSharedPreferences(mContext);
        String connectionStatus = prefs.getString(Util.CONNECTION_STATUS, Util.DISCONNECTED);
        if (Util.DISCONNECTED.equals(connectionStatus)) {
        	Log.i(TAG,"Desconectado");
            startActivity(new Intent(this, AccountsActivity.class));
        }
        else
        {
        	if(Util.CONNECTING.equals(connectionStatus)){
            	Log.i(TAG,"Conectando ando");
            	//Toast.makeText(mContext, "Loggeando", Toast.LENGTH_LONG).show();
            	//poner logging in
            	lay = R.layout.logging;
            	setScreenContent();
            }
            else
            {
            	Log.i(TAG,"Conectado");
            	int  layoutValue = getIntent().getIntExtra("com.nest5.androidClient.layout",0);
                
                String loggedStatus = prefs.getString(Util.LOGGED_STATUS, Util.LOGGEDOUT);
                //Toast.makeText(mContext, loggedStatus, Toast.LENGTH_LONG).show();
                if(Util.LOGGEDIN.equals(loggedStatus))
                {
                	lay = layoutValue != 0 ? layoutValue : R.layout.home;
                	
                }
                else{
                	//Hacer Login forzado porque esta conectado con google pero por alguna razon no esta loggeado en nest5, 
                	//mientras tanto uuestra la pantalla de actividad para desconectar hy volver a conectar
                	startActivity(new Intent(this, AccountsActivity.class));
                    
                	
                }
                
                setScreenContent();
            }
        }
        
        
	    
 
    }
    
    @Override
	protected void onPause() {
    	myLocation.cancelTimer();
		super.onPause();
		//locationManager.removeUpdates(locationListener);
	}

    /**
     * Shuts down the activity.
     */
    @Override
    public void onDestroy() {
    	myLocation.cancelTimer();
        unregisterReceiver(mUpdateUIReceiver);
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
    public boolean onOptionsItemSelected(MenuItem item) {
       // AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	//Toast.makeText(mContext, String.valueOf(item.getItemId()), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            
            
            default:
                return super.onContextItemSelected(item);
        }
    }

    // Manage UI Screens

    
    private void setExtraDataScreenContent(){
    	
    	setContentView(R.layout.extra_data);
    	dialogLogin.hide();
    	 user = (EditText) findViewById(R.id.fullname);
    	 TextView title = (TextView) findViewById(R.id.extra_info);
    	 title.setTypeface(BebasFont);
	     //pass = (EditText) findViewById(R.id.password);
	     LinearLayout form = (LinearLayout) findViewById(R.id.textfield_layout);
	    ImageView loading = (ImageView) findViewById(R.id.loading_image);
	    user.setTypeface(BebasFont);
	    //pass.setTypeface(BebasFont);
	    /*SharedPreferences prefs = Util.getSharedPreferences(mContext);
        String connectionstatus = prefs.getString(Util.CONNECTION_STATUS, "Unknown");
        String loginStatus = prefs.getString(Util.LOGGED_STATUS, "unknown");
        if(Util.LOGGINGIN.equals(loginStatus) || Util.CONNECTING.equals(connectionstatus)){
        	//poner spinner
        	form.setVisibility(View.INVISIBLE);
        	loading.setVisibility(View.VISIBLE);
        }*/
	    
    }
    
    private void setHomeScreenContent(){
    	setContentView(R.layout.home);
    	
    	/*LayoutInflater inflater = LayoutInflater.from(this);
        scrollView = (MyHorizontalScrollView) inflater.inflate(R.layout.horz_scroll_with_list_menu, null);
        setContentView(scrollView);
        menu = inflater.inflate(R.layout.horz_scroll_menu, null);
        app = inflater.inflate(R.layout.home, null);
        ViewGroup tabBar = (ViewGroup) app.findViewById(R.id.tabBar);
        ListView listView;
        listView = (ListView) menu.findViewById(R.id.list);
        ViewUtils.initListView(this, listView, "Menu ", 30, android.R.layout.simple_list_item_1);
        btnSlide = (ImageView) tabBar.findViewById(R.id.BtnSlide);
        btnSlide.setOnClickListener(new ClickListenerForScrolling(scrollView, menu));
        final View[] children = new View[] { menu, app };
        // Scroll to app (view[1]) when layout finished.
        int scrollToViewIdx = 1;
        scrollView.initViews(children, scrollToViewIdx, new SizeCallbackForMenu(btnSlide));*/
        
        final SpinCircleView circle = (SpinCircleView) findViewById(R.id.main_spin);
    	
    	//userName = (TextView) tabBar.findViewById(R.id.header_username);
    	//internetConnectionStatus = (ImageView) tabBar.findViewById(R.id.header_connection_status);
        userName = (TextView) findViewById(R.id.header_username);
        internetConnectionStatus = (ImageView) findViewById(R.id.header_connection_status);
    	userName.setTypeface(BebasFont);
    	SharedPreferences prefs = Util.getSharedPreferences(mContext);
    	if(!isNetworkAvailable())
    	{
    		internetConnectionStatus.setImageResource(R.drawable.error);
    		
    		prefs.edit().putInt(Util.INTERNET_CONNECTION, Util.INTERNET_DISCONNECTED).commit();
    		
    	}
    	else
    	{
    		prefs.edit().putInt(Util.INTERNET_CONNECTION, Util.INTERNET_CONNECTED).commit();
    	}
    	
    	//traer el objeto del usuario porque cuando ya esta loggeado no esta el objeto usuario aca
    	if(userInstance == null)
    	{
    		restService = new RestService(userObjectHandlerGet, this, "http://www.nest5.com/api/user/requestAndroidUser"); //Create new rest service for get
        	
        	restService.setCredentials("apiadmin", Setup.apiKey);
        	//enviar id de usuario para pedir objeto
        	
            int uid = prefs.getInt(Util.USER_REGISTRATION_ID, 0);
            restService.addParam("userid", String.valueOf(uid));
            
            try {
            	
    			restService.execute(RestService.POST); //Executes the request with the HTTP POST verb
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	else
    	{
    		userName.setText(userInstance.name);
    	}
    	if(prefs.getInt(Util.INTERNET_CONNECTION, 0) == 1){
    	
    	//userName.setText(userInstance.name);
    	
    	
    	Button scan_btn = (Button) findViewById(R.id.scanButton);
    	
    	scan_btn.setTypeface(BebasFont);
    	
        
        circle.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				float posX = -1;
				float posY = -1;
				boolean touched = false;
				//circle.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.rotate_indefinitely) );
				// TODO Auto-generated method stub
				int action = event.getAction();
				if (event.getPointerCount() > 1)
				{
					int actionPointerId = action & MotionEvent.ACTION_POINTER_ID_MASK;
					int actionEvent = action & MotionEvent.ACTION_MASK;
					
					//int pointerIndex = findPointerIndex(actionPointerId);
					//posX = (int) event.getX(pointerIndex);
					//posY = (int) event.getY(pointerIndex);
				}else{
					//this.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.rotate_indefinitely) );
					posX = event.getX();
					posY = event.getY();
					
					
					switch (action) {
			        case MotionEvent.ACTION_DOWN:
			            touched = true;
			            break;
			        case MotionEvent.ACTION_MOVE:
			            touched = true;
			            break;
			        case MotionEvent.ACTION_UP:
			            touched = false;
			            break;
			        case MotionEvent.ACTION_CANCEL:
			            touched = false;
			            break;
			        case MotionEvent.ACTION_OUTSIDE:
			            touched = false;
			            break;
			        default:
			        }
					
					if(!touched)
					{
						//restoreAll();
					}
					
				}
				
				int pressed = circle.getClickId(posX,posY);
				
				AnimationListener animReady = new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						setScreenContent();
						// TODO Auto-generated method stub
						
					}
				};
				
				switch(pressed)
				{
				case 1: //Toast.makeText(mContext, "0", Toast.LENGTH_SHORT).show();
				break;
				case 2: 
					lay = R.layout.deals;
					setScreenContent();
					break;
				case 3:
					lay = R.layout.deals;
					circle.rotate.setAnimationListener(animReady);
					break;
				case 4:
					lay = R.layout.my_deals;
					circle.rotate.setAnimationListener(animReady);
					break;
				case 5:
					lay = R.layout.my_coupons;
					circle.rotate.setAnimationListener(animReady);
					break;
					
				case 6:
					//Toast.makeText(mContext, "Muy Pronto", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(Intent.ACTION_SEND);
					intent.setType("text/plain");
					intent.putExtra(Intent.EXTRA_TEXT, "Nest, Aplicación para cupones de descuentos en Las Mejores Marcas y Tiendas en mi teléfono móvil. http://www.nest5.com");
					startActivity(Intent.createChooser(intent, "Compartir Usando: "));
					break;
				default: 
				break;
				}
				return false;
			}
		});
    }
    	else
    	{
    		//Toast.makeText(mContext, "No tienes conexión a internet.", Toast.LENGTH_LONG).show();
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);  
            builder.setMessage("No tienes una conexión a internet activa. Habilítala haciendo click en aceptar y seleccionando luego una red.")  
                   .setCancelable(false)  
                   .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {  
                       public void onClick(DialogInterface dialog, int id) {  
                           Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);  
                       startActivityForResult(intent, 1);  
                       }  
                   })  
                   .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {  
                       public void onClick(DialogInterface dialog, int id) {  
                           finish();  
                       }  
                   }).show();
    	}
    	
    }
    
    private void setDealsScreenContent(){
    	
    	//setContentView(R.layout.deals);
    	//Intent inte = new Intent(mContext, DealsActivity.class);
    	//startActivity(inte);
    	setContentView(R.layout.deals);
    	
    	
    	userName = (TextView) findViewById(R.id.header_username);
    	userName.setTypeface(BebasFont);
    	userName.setText("Listado de Empresas");
    	userName.setTextSize(16);
    	
    	dialogUpdateExtra = new ProgressDialog(mContext);
	    dialogUpdateExtra.setMessage("Estamos buscando las mejores ofertas cercanas...");
	    dialogUpdateExtra.show();
	    LocationResult locationResult2 = new LocationResult(){
            @Override
            public void gotLocation(Location location){
                //Got the location!
            	//Toast.makeText(mContext, String.valueOf(location.getLatitude()), Toast.LENGTH_LONG).show();
            	lat = location.getLatitude();
            	lng = location.getLongitude();
            	String address= "No se ha podido hallar tu dirección actual.";
            	Geocoder gc = new Geocoder(mContext,Locale.getDefault());
            	List<Address> addresses = null;
            	try{
            		Log.i(TAG,"Entra try");
            		addresses = gc.getFromLocation(lat, lng, 1);
            		StringBuilder sb = new StringBuilder();
            		Log.i(TAG,"Antes de if");
            		if(addresses.size() > 0)
            		{
            			Log.i(TAG,"Mas de 0 direcciones");
            			Address actual = addresses.get(0);
            			for(int i = 0; i < actual.getMaxAddressLineIndex(); i++)
            			{
            				sb.append(actual.getAddressLine(i)).append("\n");
            				
            			}
            			
            			
            			
            		}
            		address = sb.toString();
            	}catch(IOException e){}
            	userName.setText(address);
            	restService = new RestService(getDealslHandler, mContext, Setup.PROD_URL+"/promo/showDeals");
                //restService.addParam("email", accountName);
                restService.addParam("latitude", String.valueOf(lat));
                restService.addParam("longitude",String.valueOf(lng));
                //Toast.makeText(mContext, accountId, Toast.LENGTH_LONG).show();
                restService.setCredentials("apiadmin", Setup.apiKey);
                //Log.i(TAG,"Aca1");
                try {
                	//Log.i(TAG,"Aca2");
        			restService.execute(RestService.GET); //Executes the request with the HTTP POST verb
        		} catch (Exception e) {
        			//Log.i(TAG,"Aca3");
        			e.printStackTrace();
        		}
            	
            }
        };
	   boolean enabled = myLocation.getLocation(mContext, locationResult2);
	   if(!enabled)
	   {
		   Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
       		startActivity(intent);
	   }
	    
	    
    }
    
 private void setMyDealsScreenContent(){
    	
    	//setContentView(R.layout.deals);
    	//Intent inte = new Intent(mContext, DealsActivity.class);
    	//startActivity(inte);
    	setContentView(R.layout.my_deals);
    	userName = (TextView) findViewById(R.id.header_username);
    	userName.setTypeface(BebasFont);
    	//traer el objeto del usuario porque cuando ya esta loggeado no esta el objeto usuario aca
    	if(userInstance == null)
    	{
    		restService = new RestService(userObjectHandlerGet, this, "http://www.nest5.com/api/user/requestAndroidUser"); //Create new rest service for get
        	
        	restService.setCredentials("apiadmin", Setup.apiKey);
        	//enviar id de usuario para pedir objeto
        	SharedPreferences prefs = Util.getSharedPreferences(mContext);
            int uid = prefs.getInt(Util.USER_REGISTRATION_ID, 0);
            restService.addParam("userid", String.valueOf(uid));
            
            try {
            	
    			restService.execute(RestService.POST); //Executes the request with the HTTP POST verb
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	else
    	{
    		userName.setText(userInstance.name);
    	}
    	
    	
        
    	
    	
	    dialogUpdateExtra = new ProgressDialog(mContext);
	    restService = new RestService(myDealsHandlerGet, this, "http://www.nest5.com/api/promo/showMyDeals"); //Create new rest service for get
    	
    	restService.setCredentials("apiadmin", Setup.apiKey);
    	//enviar id de usuario para pedir objeto
    	SharedPreferences prefs = Util.getSharedPreferences(mContext);
        int uid = prefs.getInt(Util.USER_REGISTRATION_ID, 0);
        restService.addParam("id", String.valueOf(uid));
        dialogUpdateExtra.setMessage("Estamos cargando tus tarjetas de sellos...");
        dialogUpdateExtra.show();
        try {
        	
			restService.execute(RestService.POST); //Executes the request with the HTTP POST verb
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    
	    
	    
    }
    
    private void setLoggingScreenContent()
    {
    	setContentView(R.layout.deals);
    	dialogLogin = new ProgressDialog(mContext);
	    dialogLogin.setMessage("Inciando Sesión");
	    dialogLogin.show();
    	
    }
    
    private void setStampScreenContent()
    {
    	setContentView(R.layout.stamp_card);
    	userName = (TextView) findViewById(R.id.header_username);
    	userName.setTypeface(BebasFont);
    	//traer el objeto del usuario porque cuando ya esta loggeado no esta el objeto usuario aca
    	if(userInstance == null)
    	{
    		restService = new RestService(userObjectHandlerGet, this, "http://www.nest5.com/api/user/requestAndroidUser"); //Create new rest service for get
        	
        	restService.setCredentials("apiadmin", Setup.apiKey);
        	//enviar id de usuario para pedir objeto
        	SharedPreferences prefs = Util.getSharedPreferences(mContext);
            int uid = prefs.getInt(Util.USER_REGISTRATION_ID, 0);
            restService.addParam("userid", String.valueOf(uid));
            
            try {
            	
    			restService.execute(RestService.POST); //Executes the request with the HTTP POST verb
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	else
    	{
    		userName.setText(userInstance.name);
    	}
    	CardView card;
    	
    	LinearLayout layout = (LinearLayout) findViewById(R.id.card_holder);
    	TextView title = (TextView) findViewById(R.id.card_info_title);
    	TextView description_one = (TextView) findViewById(R.id.card_info_requirement);
    	TextView description_two = (TextView) findViewById(R.id.card_info_perk);
    	TextView withNest5 = (TextView) findViewById(R.id.card_row_nest5);
    	InformationTextView ach1;
    	title.setTypeface(BebasFont);
    	description_one.setTypeface(VarelaFont);
    	description_two.setTypeface(VarelaFont);
    	withNest5.setTypeface(VarelaFont);
    	title.setText(payload.company.name);
    	String titleText = payload.promo.action.equals("Compra") ? getString(R.string.buyPerk, payload.promo.reqQTY,payload.promo.requirement) : getString(R.string.visitPerk, payload.promo.reqQTY,payload.promo.requirement);
    	String perkText = String.valueOf(payload.promo.perkQTY) +" "+ payload.promo.perk;
    	description_one.setText(titleText);
    	description_two.setText(perkText);
    	
    	
    	//ScrollingTextView ach1 = new ScrollingTextView(this,null);
    	//ach1.setTextAppearance(mContext, R.style.company_promos);
    	
    	
    	String couponsText;
    	if(payload.coupons.size() == 0)
    	{
    		
    		//card = new CardView(this,null,10,payload.stamps.size(),false); // No es cupón sino tarjeta de sellos
    		card = new CardView(this,null,payload.promo.reqQTY,payload.stamps.size(),false);
    		int restantes = payload.promo.reqQTY - payload.stamps.size();
    		
    		if(restantes == 1)
    		{
    			
    			couponsText = "¡Wow, 1 sello más y tendrás tu preciado cupón!";
    			ach1 = new InformationTextView(mContext, null, Setup.ICON_FACE_ONE, couponsText);
    		}
    		else
    		{
    			if(restantes == 2)
        		{
        			couponsText = "¡Casi Lo Logras! dos sellos más y tendrás tu cupón.";
        			ach1 = new InformationTextView(mContext, null, Setup.ICON_FACE_TWO, couponsText);
        		}
    			else
    			{
    				if(restantes == 3)
            		{
            			couponsText = "¡Vamos!, ese cupón solo está a 3 sellos de distancia.";
            			ach1 = new InformationTextView(mContext, null, Setup.ICON_FACE_THREE, couponsText);
            		}
    				else
    				{
    					couponsText = "¡Ánimo! Estás a "+restantes+" sellos de conseguir el cupón.";
    					ach1 = new InformationTextView(mContext, null, Setup.ICON_FACE_CHEER, couponsText);
    				}
    			}
    			
    			
    		}
    		    		
    	}
    	else
    	{    			
    		//card = new CardView(this,null,1,1,true);
    		card = new CardView(this,null,1,1,true);
    		couponsText = "¡Has Ganado un Cupón! Este sello te ha completado los "+payload.promo.reqQTY+" que eran necesarios para obtenerlo. \n¡Ve atus cupones, y redímelo!";
    		ach1 = new InformationTextView(mContext, null, Setup.ICON_FACE_WIN, couponsText);
    		Button goToCoupons = (Button) findViewById(R.id.couponsButton);
    		goToCoupons.setVisibility(View.VISIBLE);
    		goToCoupons.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					lay = R.layout.my_coupons;
					setScreenContent();
					
				}
			}); 
    	}
    	layout.addView(card);
    	
    	ach1.message.setTypeface(VarelaFont);
    	//ach1.setTextColor(Color.parseColor("#FFFFFF"));
    	LinearLayout achLayout = (LinearLayout) findViewById(R.id.achievements_container);
    	achLayout.addView(ach1);
    	
    	
    }
    
    private void setCouponScreenContent()
    {
    	setContentView(R.layout.my_coupons);
    	
    	userName = (TextView) findViewById(R.id.header_username);
    	userName.setTypeface(BebasFont);
    	//traer el objeto del usuario porque cuando ya esta loggeado no esta el objeto usuario aca
    	if(userInstance == null)
    	{
    		restService = new RestService(userObjectHandlerGet, this, "http://www.nest5.com/api/user/requestAndroidUser"); //Create new rest service for get
        	
        	restService.setCredentials("apiadmin", Setup.apiKey);
        	//enviar id de usuario para pedir objeto
        	SharedPreferences prefs = Util.getSharedPreferences(mContext);
            int uid = prefs.getInt(Util.USER_REGISTRATION_ID, 0);
            restService.addParam("userid", String.valueOf(uid));
            
            try {
            	
    			restService.execute(RestService.POST); //Executes the request with the HTTP POST verb
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	else
    	{
    		userName.setText(userInstance.name);
    	}
    	//setContentView(R.layout.deals);
    	//Intent inte = new Intent(mContext, DealsActivity.class);
    	//startActivity(inte);
    	
    	
    	
    	
        
    	
    	
	    dialogUpdateExtra = new ProgressDialog(mContext);
	    restService = new RestService(myCouponsHandlerGet, this, "http://www.nest5.com/api/user/requestUserCoupons"); //Create new rest service for get
    	
    	restService.setCredentials("apiadmin", Setup.apiKey);
    	//enviar id de usuario para pedir objeto
    	SharedPreferences prefs = Util.getSharedPreferences(mContext);
        int uid = prefs.getInt(Util.USER_REGISTRATION_ID, 0);
        restService.addParam("id", String.valueOf(uid));
        dialogUpdateExtra.setMessage("Estamos cargando tus Cupones...");
        dialogUpdateExtra.show();
        try {
        	
			restService.execute(RestService.POST); //Executes the request with the HTTP POST verb
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private void setMyDealScreenContent()
    {
    	
    	setContentView(R.layout.stamp_card);
    	userName = (TextView) findViewById(R.id.header_username);
    	userName.setTypeface(BebasFont);
    	//traer el objeto del usuario porque cuando ya esta loggeado no esta el objeto usuario aca
    	if(userInstance == null)
    	{
    		restService = new RestService(userObjectHandlerGet, this, "http://www.nest5.com/api/user/requestAndroidUser"); //Create new rest service for get
        	
        	restService.setCredentials("apiadmin", Setup.apiKey);
        	//enviar id de usuario para pedir objeto
        	SharedPreferences prefs = Util.getSharedPreferences(mContext);
            int uid = prefs.getInt(Util.USER_REGISTRATION_ID, 0);
            restService.addParam("userid", String.valueOf(uid));
            
            try {
            	
    			restService.execute(RestService.POST); //Executes the request with the HTTP POST verb
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	else
    	{
    		userName.setText(userInstance.name);
    	}
    	
    	CardView card;
    	
    	LinearLayout layout = (LinearLayout) findViewById(R.id.card_holder);
    	TextView title = (TextView) findViewById(R.id.card_info_title);
    	TextView description_one = (TextView) findViewById(R.id.card_info_requirement);
    	TextView description_two = (TextView) findViewById(R.id.card_info_perk);
    	TextView withNest5 = (TextView) findViewById(R.id.card_row_nest5);
    	InformationTextView ach1;
    	title.setTypeface(BebasFont);
    	description_one.setTypeface(VarelaFont);
    	description_two.setTypeface(VarelaFont);
    	withNest5.setTypeface(VarelaFont);
    	title.setText(currentDeal.company.name);
    	String titleText = currentDeal.promo.action.equals("Compra") ? getString(R.string.buyPerk, currentDeal.promo.reqQTY,currentDeal.promo.requirement) : getString(R.string.visitPerk, currentDeal.promo.reqQTY,currentDeal.promo.requirement);
    	String perkText = String.valueOf(currentDeal.promo.perkQTY) +" "+ currentDeal.promo.perk;
    	description_one.setText(titleText);
    	description_two.setText(perkText);
    	
    	
    	//ScrollingTextView ach1 = new ScrollingTextView(this,null);
    	//ach1.setTextAppearance(mContext, R.style.company_promos);
    	
    	
    	String couponsText;
    	
    		
    		//card = new CardView(this,null,10,payload.stamps.size(),false); // No es cupón sino tarjeta de sellos
    		card = new CardView(this,null,currentDeal.promo.reqQTY,currentDeal.stamps.size(),false);
    		int restantes = currentDeal.promo.reqQTY - currentDeal.stamps.size();
    		
    		if(restantes == 1)
    		{
    			
    			couponsText = "¡Wow, 1 sello más y tendrás tu preciado cupón!";
    			ach1 = new InformationTextView(mContext, null, Setup.ICON_FACE_ONE, couponsText);
    		}
    		else
    		{
    			if(restantes == 2)
        		{
        			couponsText = "¡Casi Lo Logras! dos sellos más y tendrás tu cupón.";
        			ach1 = new InformationTextView(mContext, null, Setup.ICON_FACE_TWO, couponsText);
        		}
    			else
    			{
    				if(restantes == 3)
            		{
            			couponsText = "¡Vamos!, ese cupón solo está a 3 sellos de distancia.";
            			ach1 = new InformationTextView(mContext, null, Setup.ICON_FACE_THREE, couponsText);
            		}
    				else
    				{
    					couponsText = "¡Ánimo! Estás a "+restantes+" sellos de conseguir el cupón.";
    					ach1 = new InformationTextView(mContext, null, Setup.ICON_FACE_CHEER, couponsText);
    				}
    			}
    			
    			
    		}
    		    		
    	
    	
    
    	layout.addView(card);
    	
    	ach1.message.setTypeface(VarelaFont);
    	//ach1.setTextColor(Color.parseColor("#FFFFFF"));
    	LinearLayout achLayout = (LinearLayout) findViewById(R.id.achievements_container);
    	achLayout.addView(ach1);
    	
    	
    }
    
    public void setCouponInfoScreenContent()
    {
    	setContentView(R.layout.coupon);
    	userName = (TextView) findViewById(R.id.header_username);
    	userName.setTypeface(BebasFont);
    	//traer el objeto del usuario porque cuando ya esta loggeado no esta el objeto usuario aca
    	if(userInstance == null)
    	{
    		restService = new RestService(userObjectHandlerGet, this, "http://www.nest5.com/api/user/requestAndroidUser"); //Create new rest service for get
        	
        	restService.setCredentials("apiadmin", Setup.apiKey);
        	//enviar id de usuario para pedir objeto
        	SharedPreferences prefs = Util.getSharedPreferences(mContext);
            int uid = prefs.getInt(Util.USER_REGISTRATION_ID, 0);
            restService.addParam("userid", String.valueOf(uid));
            
            try {
            	
    			restService.execute(RestService.POST); //Executes the request with the HTTP POST verb
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	else
    	{
    		userName.setText(userInstance.name);
    	}
    	TextView companyT = (TextView) findViewById(R.id.card_info_title);
    	ScrollingTextView requirement = (ScrollingTextView) findViewById(R.id.card_info_requirement);
    	ScrollingTextView perk = (ScrollingTextView) findViewById(R.id.card_info_perk);
    	TextView withNest5 = (TextView) findViewById(R.id.card_row_nest5);
    	Button btn = (Button) findViewById(R.id.redeemButton);
    	ImageView img = (ImageView)findViewById(R.id.couponStar);
    	companyT.setTypeface(BebasFont);
    	requirement.setTypeface(VarelaFont);
    	perk.setTypeface(VarelaFont);
    	withNest5.setTypeface(VarelaFont);
    	String titleText = currentCoupon.promo.action.equals("Compra") ? getString(R.string.buyPerk, currentCoupon.promo.reqQTY,currentCoupon.promo.requirement) : getString(R.string.visitPerk, currentCoupon.promo.reqQTY,currentCoupon.promo.requirement);
    	companyT.setText(currentCoupon.company.name);
    	requirement.setText(titleText);
    	perk.setText(currentCoupon.promo.perk);
    	if(redimiendo)
    	{
    		btn.setVisibility(View.INVISIBLE);
    		img.setImageResource(R.drawable.sello_estrella);
    		
    		redimiendo = false;
    	}
    	
    }
  

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	  IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
    	  Log.i(TAG, String.valueOf(redeemCoupon));
    	  if (scanResult != null) {
    		  //Toast.makeText(mContext, String.valueOf(location.getAccuracy()), Toast.LENGTH_LONG).show();
    		  	SharedPreferences prefs = Util.getSharedPreferences(mContext);
  	        	int uid = prefs.getInt(Util.USER_REGISTRATION_ID, 0);
    		  if(!redeemCoupon)
    		  {
    			// handle scan result
        		  //enviar id de usuario, código escaneado, clave de api y usuario de api a http://nest5stage.herokuapp.com/api/promo/beLucky?userId=XX&code=XXXX
        		  restService = new RestService(mHandlerLucky, this, "http://www.nest5.com/api/promo/beLucky"); //Create new rest service for get
        	    	
        	    	restService.setCredentials("apiadmin", Setup.apiKey);
        	    	//enviar id de usuario para actualizar datos de nombre
        	    	
        	        restService.addParam("id", String.valueOf(uid));
        	        restService.addParam("code", scanResult.getContents());
        	        restService.addParam("latitude", String.valueOf(lat));
        	        restService.addParam("longitude", String.valueOf(lng));
        	        dialogUpdateExtra = new ProgressDialog(mContext);
        		    dialogUpdateExtra.setMessage("Guardando...");
        		    dialogUpdateExtra.show();
        	        try {
        	        	
        				restService.execute(RestService.POST); //Executes the request with the HTTP POST verb
        			} catch (Exception e) {
        				e.printStackTrace();
        			}
        		  //Toast.makeText(mContext, scanResult.getContents(), Toast.LENGTH_LONG).show();  
    		  }
    		  else
    		  {
    			  //Redimir Cupón
    			  restService = new RestService(mHandlerRedeem, this, "http://www.nest5.com/api/promo/redeemCoupon"); //Create new rest service for get
      	    	
      	    	restService.setCredentials("apiadmin", Setup.apiKey);
      	    	//enviar id de usuario para actualizar datos de nombre
      	    	
      	        restService.addParam("id", String.valueOf(uid));
      	        restService.addParam("code", scanResult.getContents());
      	        dialogUpdateExtra = new ProgressDialog(mContext);
      		    dialogUpdateExtra.setMessage("Un momento, estamos solicitando la autorización...");
      		    dialogUpdateExtra.show();
      	        try {
      	        	
      				restService.execute(RestService.POST); //Executes the request with the HTTP POST verb
      			} catch (Exception e) {
      				e.printStackTrace();
      			}
    		  }
    	    
    	  }
    	  // else continue with any other code you need in the method
    	  
    	}
    
    
    
    /**
     * Sets the screen content based on the screen id.
     */
    private void setScreenContent() {
        
        switch (lay) {
	        case R.layout.extra_data:
	            setExtraDataScreenContent();
	            break;
            case R.layout.home:
                setHomeScreenContent();
                break;
            case R.layout.deals:
                setDealsScreenContent();
                break;
            case R.layout.logging:
                setLoggingScreenContent();
                break;
            case R.layout.stamp_card:
            	if(!fromMyDeals)
            	{
            		setStampScreenContent();
            	}
            	else
            	{
            		setMyDealScreenContent();
            	}
            	
                break;
            case R.layout.my_deals:
                setMyDealsScreenContent();
                break;
            case R.layout.coupon:
                setCouponInfoScreenContent();
                break;
            case R.layout.my_coupons:
                setCouponScreenContent();
                break;
        }
    }
    
    @Override
    public void onBackPressed() {
    	//Log.i(TAG, String.valueOf(R.layout.deals)+"   "+String.valueOf(lay));
      if(lay == R.layout.deals ){
    	  lay = R.layout.home;
    	  
      }
      else{
    	  if(lay== R.layout.my_deals)
    	  {
    		  lay = R.layout.home;
    		  
    	  }
    	  else
    	  {
    		  if(lay == R.layout.stamp_card)
        	  {
        		  if(!fromMyDeals)
        		  {
        			  lay = R.layout.home;
        		  }
        		  else
        		  {
        			  lay = R.layout.my_deals;
        			  fromMyDeals = false;
        		  }
        		  
        		  
        	  }
        	  else
        	  {
        		  if(lay == R.layout.my_coupons)
        		  {
        			  lay = R.layout.home;
        		  }
        		  else{
        			  if(lay == R.layout.coupon)
        			  {
        				lay = R.layout.my_coupons;  
        			  }
        			  else
        			  {
        				  finish();
        			  }
        			  
        		  }
        		  
        		  
        			  
        		  
        		  
        	  } 
    	  }
    	  
    	  
      }
      setScreenContent();
       
    }
    
    public void fbLogin(View v){
    	Intent inten = new Intent(mContext,FacebookActivity.class);
    	//inten.putExtra("com.nest5.androidClient.layout", R.layout.deals);
    	startActivity(inten);
    	
    }
    public void updateExtra(View v){
    	
    	String u = user.getText().toString();
    	//String p = pass.getText().toString();
    	restService = new RestService(mHandlerGet, this, "http://www.nest5.com/api/user/updateAndroidUser"); //Create new rest service for get
    	
    	restService.setCredentials("apiadmin", Setup.apiKey);
    	//enviar id de usuario para actualizar datos de nombre
    	SharedPreferences prefs = Util.getSharedPreferences(mContext);
        int uid = prefs.getInt(Util.USER_REGISTRATION_ID, 0);
        restService.addParam("name", u);
        restService.addParam("userid", String.valueOf(uid));
        dialogUpdateExtra = new ProgressDialog(mContext);
	    dialogUpdateExtra.setMessage("Actualizando tu Información");
	    dialogUpdateExtra.show();
        try {
        	
			restService.execute(RestService.POST); //Executes the request with the HTTP POST verb
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    private final Handler sendEmailHandler = new Handler(){
    	@Override
    	public void handleMessage(Message msg){
    		JSONObject respuesta = null;
    		JSONObject usuario = null;
    		String loggedStatus = Util.LOGGEDOUT;
    		String connectionStatus = Util.DISCONNECTED;
    		Log.i(TAG, (String) msg.obj);
    		try{
    			respuesta = new JSONObject((String) msg.obj);
    		}
    		catch(Exception e){
    			e.printStackTrace();
    		}
    		
    		if (respuesta != null){
    			int status = 0;
    			int uid = 0;
    			try{
    				status = respuesta.getInt("status");
    				uid = respuesta.getInt("uid");
    					
    				
    			}
    			catch (Exception e) {
					e.printStackTrace();
				}
    			
    			
    			dialogLogin.hide();
    			//Toast.makeText(mContext, String.valueOf(status), Toast.LENGTH_LONG).show();
    			if(status == 1 || status == 2)
    			{
    				Log.i(TAG,String.valueOf(uid));
    				loggedStatus = Util.LOGGEDIN;
    				SharedPreferences prefs = Util.getSharedPreferences(mContext);
    	            prefs.edit().putString(Util.LOGGED_STATUS, loggedStatus).commit();
    	            prefs.edit().putInt(Util.USER_REGISTRATION_ID, uid).commit();
    	            if(status == 1)
    	            {
    	            	try{
    	            		usuario = respuesta.getJSONObject("userInstance");
    	            		Gson gson = new Gson();
    	            		userInstance = gson.fromJson(usuario.toString(), User.class);
    	            		if(userInstance != null)
    	        	    	{
    	        	    		userName.setText(userInstance.name);
    	        	    	}
    						//userInstance = respuesta.getJSONObject("userInstance");
    						//Log.i(TAG,userInstance.toString());
    	            		Log.i(TAG,userInstance.username);
    	            	}
    	            	catch(Exception e){
    					
    	            	}
    	            	//coger con una clase propia todo el objeto del usuario que se traiga de la base de datos, completico completico
    	            	lay = R.layout.home;
        				setScreenContent();
        				
        				
    	            }
    	            else {
    	            	//poner para pedir nombre, telefono y otros datos
    	            	
    	            	lay = R.layout.extra_data;
        				setScreenContent();
    	            }
    				
    				
    			}
    			else
    			{
    				
    				loggedStatus = Util.LOGGEDOUT;
    				connectionStatus = Util.DISCONNECTED;
    				SharedPreferences prefs = Util.getSharedPreferences(mContext);
    	            prefs.edit().putString(Util.LOGGED_STATUS, loggedStatus).commit();
    	            //quitar la conexion
    	            prefs.edit().putString(Util.CONNECTION_STATUS, connectionStatus);
    	            startActivity(new Intent(mContext, AccountsActivity.class));
    			}
    			
    		}
    		
    		
    		
    		
    	}
    };
    
   
    
    private final Handler mHandlerGet = new Handler(){
    	@Override
    	public void handleMessage(Message msg){
    		JSONObject respuesta = null;
    		
    		try{
    			respuesta = new JSONObject((String) msg.obj);
    		}
    		catch(Exception e){
    			e.printStackTrace();
    		}
    		
    		if (respuesta != null){
    			int status = 0;
    			try{
    				status = respuesta.getInt("status");
    			
    			}
    			catch (Exception e) {
					e.printStackTrace();
				}
    			//quitar loading
    			dialogUpdateExtra.hide();
    			//dialogLogin.hide();
    			//Toast.makeText(mContext, String.valueOf(status), Toast.LENGTH_LONG).show();
    			if(status == 1)
    			{
    				  	lay = R.layout.home;
        				setScreenContent();
    	        }
    			else
    			{
    				Toast.makeText(mContext, "Lo Sentimos, hay errores con el servidor pero en breve lo arreglaremos.", Toast.LENGTH_LONG).show();
    			}
    			
    		}
    		
    		
    		
    		
    	}
    };
    
    private final Handler userObjectHandlerGet = new Handler()
    {
    	
    	@Override
    	public void handleMessage(Message msg){
    	
	    	try{
	    		//usuario = respuesta.getJSONObject("userInstance");
	    		Gson gson = new Gson();
	    		userInstance = gson.fromJson((String) msg.obj, User.class);
				//userInstance = respuesta.getJSONObject("userInstance");
				//Log.i(TAG,userInstance.toString());
	    		//Log.i(TAG,userInstance.username);
	    	}
	    	catch(Exception e){
			
	    	}
	    	
	    	if(userInstance != null)
	    	{
	    		userName.setText(userInstance.name);
	    	}
    	}
    	
    };
    
    private final Handler mHandlerLucky = new Handler()
    {
    	
    	@Override
    	public void handleMessage(Message msg){
    		JSONObject respuesta = null;
    		Log.i(TAG,"Pasando por acá");
    		Log.i(TAG,(String) msg.obj);
	    	try{
	    		//usuario = respuesta.getJSONObject("userInstance");
	    		//Gson gson = new Gson();
	    		//userInstance = gson.fromJson((String) msg.obj, User.class);
				//userInstance = respuesta.getJSONObject("userInstance");
				//Log.i(TAG,userInstance.toString());
	    		//Log.i(TAG,userInstance.username);
	    		respuesta = new JSONObject((String) msg.obj);
	    		Log.i(TAG,"Pasando por acá");
	    		Log.i(TAG,(String) msg.obj);
	    	}
	    	catch(Exception e){
			
	    	}
	    	Log.i(TAG,"Aca");
	    	if (respuesta != null){
	    		//Log.i(TAG,"Aca2");
    			int status = 0;
    			//int coupons = 0;
    			String payloadObject;
    			
    			try{
    				status = respuesta.getInt("status");
    				//coupons = respuesta.getInt("coupons");
    				
    				
    			
    			}
    			catch (Exception e) {
					e.printStackTrace();
				}
    			//quitar loading
    			dialogUpdateExtra.hide();
    			//dialogLogin.hide();
    			//Toast.makeText(mContext, String.valueOf(status), Toast.LENGTH_LONG).show();
    			if(status == 1)
    			{
    				//Toast.makeText(mContext,"¡Excelente! Has Acumulado otro sello con esta empresa",Toast.LENGTH_LONG).show();
    				//Tomar cupones y Sellos para mostrar pantalla de logro.
    				
    				
    				//crear un view CardView con el constructor que recibe sellos totales y sellos buenos (redimidos)
    				
    				try
    				{
    					payloadObject = respuesta.getString("payload");
    					
    					
    					Gson gson = new Gson();
    		    		payload = gson.fromJson(payloadObject, Answer.class);
    		    		
    		    		
    		    		//Log.i(TAG,"cupones: "+String.valueOf(payload.coupons)+" Sellos:"+String.valueOf(payload.stamps));
    		    		lay = R.layout.stamp_card;
    		    		fromMyDeals = false;
    		    		setScreenContent();
    		    		
    				}
    				catch(Exception e)
    				{
    					e.printStackTrace();
    					
    				}
    				
    			}
    			else
    			{
    				Toast.makeText(mContext, "Lo Sentimos, hay errores con el servidor pero en breve lo arreglaremos.", Toast.LENGTH_LONG).show();
    			}
    			
    		}
	    	else
	    	{
	    		dialogUpdateExtra.hide();
	    		Toast.makeText(mContext, "Sin Respuesta.", Toast.LENGTH_LONG).show();
	    	}
    	}
    	
    };
    
    private final Handler mHandlerRedeem = new Handler()
    {
    	
    	@Override
    	public void handleMessage(Message msg){
    		JSONObject respuesta = null;
    	Log.i("InitialActivity",(String) msg.obj);
	    	try{
	    		//usuario = respuesta.getJSONObject("userInstance");
	    		//Gson gson = new Gson();
	    		//userInstance = gson.fromJson((String) msg.obj, User.class);
				//userInstance = respuesta.getJSONObject("userInstance");
				//Log.i(TAG,userInstance.toString());
	    		//Log.i(TAG,userInstance.username);
	    		respuesta = new JSONObject((String) msg.obj);
	    		//Log.i(TAG,(String) msg.obj);
	    	}
	    	catch(Exception e){
			
	    	}
	    	//Log.i(TAG,"Aca");
	    	if (respuesta != null){
	    		//Log.i(TAG,"Aca2");
    			int status = 0;
    			//int coupons = 0;
    			String payloadObject;
    			
    			try{
    				status = respuesta.getInt("status");
    				//coupons = respuesta.getInt("coupons");
    				
    				
    			
    			}
    			catch (Exception e) {
					e.printStackTrace();
				}
    			//quitar loading
    			dialogUpdateExtra.hide();
    			//dialogLogin.hide();
    			//Toast.makeText(mContext, String.valueOf(status), Toast.LENGTH_LONG).show();
    			if(status == 1)
    			{
    				
    				
    				try
    				{
    					//Poner pantalla de cupón redimido
    		    		//lay = R.layout.home;
    					redimiendo = true;
    					lay = R.layout.coupon;
    		    		setScreenContent();
    		    		
    					
    					
    		    		
    				}
    				catch(Exception e)
    				{
    					e.printStackTrace();
    					
    				}
    				
    			}
    			else
    			{
    				Toast.makeText(mContext, "Lo Sentimos, hay errores con el servidor pero en breve lo arreglaremos.", Toast.LENGTH_LONG).show();
    			}
    			
    		}
	    	else
	    	{
	    		dialogUpdateExtra.hide();
	    		Toast.makeText(mContext, "Sin Respuesta.", Toast.LENGTH_LONG).show();
	    	}
    	}
    	
    };
    
    private final Handler myDealsHandlerGet = new Handler()
    {
    	
    	@Override
    	public void handleMessage(Message msg){
    		//Log.i(TAG,(String)msg.obj);
    		final ListView dealsList =  (ListView) findViewById(R.id.close_deals);
    		
    		
	    	try{
	    		Gson gson = new Gson();
   				final MyDeal lista[] = gson.fromJson((String)msg.obj, MyDeal[].class);
   				if(lista.length == 0)
   				{
   					dialogUpdateExtra.hide();
   					lay = R.layout.home;
   					Toast.makeText(mContext, "No tienes tarjetas de sellos activas", Toast.LENGTH_LONG).show();
   					setScreenContent();
   				}
   				else
   				{
   					ImageAdapterMyDeal adapter1 = new ImageAdapterMyDeal(mContext, lista,dealsList);
   					dealsList.setAdapter(adapter1);
   	   				dialogUpdateExtra.hide();
   	   				dealsList.setOnItemClickListener(new OnItemClickListener() {
   	     			    public void onItemClick(AdapterView<?> parent, View view,
   	     			            int position, long id) {
   	     			           // Toast.makeText(getApplicationContext(), lista[position].company.name,Toast.LENGTH_SHORT).show();
   	     			    	currentDeal = lista[position];
   	     			    	lay = R.layout.stamp_card;
   	     			    	fromMyDeals = true;
   	     			    	setScreenContent();
   	     			        }
   	     			      });
   				}
   				
   				
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
     		   dialogUpdateExtra.hide();
     		   lay = R.layout.home;
     		   setScreenContent();
     		   Toast.makeText(mContext, "La información recibida parece no ser válida", Toast.LENGTH_SHORT).show();
     		   
	    		
	    	}
	    	
	    	
	    	
    	}
    	
    };
    
    private final Handler getDealslHandler = new Handler()
    {
    	@Override
    	public void handleMessage(Message msg){
    		Log.i(TAG,"Aca4");
    		Log.i(TAG,(String)msg.obj);
    		final ListView dealsList =  (ListView) findViewById(R.id.close_deals);
    		try{
    			Log.i(TAG,"Aca5");
     		   Gson gson = new Gson();
    				final Deal lista[] = gson.fromJson((String) msg.obj, Deal[].class);
    				ImageAdapterDistance adapter1 = new ImageAdapterDistance(mContext, lista,dealsList);
 				dealsList.setAdapter(adapter1);
 				dialogUpdateExtra.hide();
    			 dealsList.setOnItemClickListener(new OnItemClickListener() {
  			    public void onItemClick(AdapterView<?> parent, View view,
  			            int position, long id) {
  			           // Toast.makeText(getApplicationContext(), lista[position].company.name,Toast.LENGTH_SHORT).show();
  			    	Intent inten = new Intent(mContext,DealsActivity.class);
  			    	Bundle b = new Bundle();
  			        b.putParcelable("com.nest5.androidclient.Deal", lista[position]);
  			        
  			       
  			    	inten.putExtras(b);
  			    	
  			    	
  			    	startActivity(inten);
  			        }
  			      });
     	   }catch(Exception e){
     		  Log.i(TAG,"Aca6");
     		   e.printStackTrace();
     		  dialogUpdateExtra.hide();
     		   lay = R.layout.home;
     		   setScreenContent();
     		   Toast.makeText(mContext, "La información recibida parece no ser válida", Toast.LENGTH_SHORT).show();
     		   
     	   }
    		
    	}
    };
    
    private final Handler myCouponsHandlerGet = new Handler()
    {
    	
    	@Override
    	public void handleMessage(Message msg){
    		//Log.i(TAG,(String)msg.obj);
    		final ListView dealsList =  (ListView) findViewById(R.id.close_deals);
    		
    		
	    	try{
	    		Gson gson = new Gson();
   				final AnswerCoupon lista[] = gson.fromJson((String)msg.obj, AnswerCoupon[].class);
   				if(lista.length == 0)
   				{
   					dialogUpdateExtra.hide();
   					lay = R.layout.home;
   					Toast.makeText(mContext, "No tienes cupones activos", Toast.LENGTH_LONG).show();
   					setScreenContent();
   				}
   				else
   				{
   					ImageAdapterMyCoupon adapter1 = new ImageAdapterMyCoupon(mContext, lista,dealsList);
   					dealsList.setAdapter(adapter1);
   	   				dialogUpdateExtra.hide();
   	   				dealsList.setOnItemClickListener(new OnItemClickListener() {
   	     			    public void onItemClick(AdapterView<?> parent, View view,
   	     			            int position, long id) {
   	     			           // Toast.makeText(getApplicationContext(), lista[position].company.name,Toast.LENGTH_SHORT).show();
   	     			    	currentCoupon = lista[position];
   	     			    	lay = R.layout.coupon;
   	     			    	//fromMyDeals = true;
   	     			    	setScreenContent();
   	     			        }
   	     			      });
   				}
   				
   				
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
     		   dialogUpdateExtra.hide();
     		   lay = R.layout.home;
     		   setScreenContent();
     		   Toast.makeText(mContext, "La información recibida parece no ser válida", Toast.LENGTH_SHORT).show();
     		   
	    		
	    	}
	    	
	    	
	    	
    	}
    	
    };
    
    public void scanCode(View v){
    	//locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
    	myLocation.getLocation(this, locationResult);
    	redeemCoupon = false;
    	IntentIntegrator integrator = new IntentIntegrator(this);
    	integrator.initiateScan();
    	
    }
    
    public void redeemCoupon(View v){
    	redeemCoupon = true;
    	IntentIntegrator integrator = new IntentIntegrator(this);
    	integrator.initiateScan();
    	
    }
    
    

    /** Determines whether one Location reading is better than the current Location fix
      * @param location  The new Location that you want to evaluate
      * @param currentBestLocation  The current Location fix, to which you want to compare the new one
      */
    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
        // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
          return provider2 == null;
        }
        return provider1.equals(provider2);
    }
    
    /*LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location _location) {
          // Called when a new location is found by the network location provider.
        	 location = _location;
        	 lat =  (location.getLatitude());
    		 lng =  (location.getLongitude());
    		 
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {}

        public void onProviderEnabled(String provider) {}

        public void onProviderDisabled(String provider) {}
      };*/
      
      private boolean isNetworkAvailable() {
    	    ConnectivityManager connectivityManager 
    	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    	    return activeNetworkInfo != null;
    	}
      
      /*
       *Fucniones del menu scrollhorizontal
       *
       * */
      
      static class ClickListenerForScrolling implements OnClickListener {
          HorizontalScrollView scrollView;
          View menu;
          /**
           * Menu must NOT be out/shown to start with.
           */
          boolean menuOut = false;

          public ClickListenerForScrolling(HorizontalScrollView scrollView, View menu) {
              super();
              this.scrollView = scrollView;
              this.menu = menu;
          }

          @Override
          public void onClick(View v) {
              Context context = menu.getContext();
              String msg = "Slide " + new Date();
              Toast.makeText(context, msg, 1000).show();
              System.out.println(msg);

              int menuWidth = menu.getMeasuredWidth();

              // Ensure menu is visible
              menu.setVisibility(View.VISIBLE);

              if (!menuOut) {
                  // Scroll to 0 to reveal menu
                  int left = 0;
                  scrollView.smoothScrollTo(left, 0);
              } else {
                  // Scroll to menuWidth so menu isn't on screen.
                  int left = menuWidth;
                  scrollView.smoothScrollTo(left, 0);
              }
              menuOut = !menuOut;
          }
      }
      
      /**
       * Helper that remembers the width of the 'slide' button, so that the 'slide' button remains in view, even when the menu is
       * showing.
       */
      static class SizeCallbackForMenu implements SizeCallback {
          int btnWidth;
          View btnSlide;

          public SizeCallbackForMenu(View btnSlide) {
              super();
              this.btnSlide = btnSlide;
          }

          @Override
          public void onGlobalLayout() {
              btnWidth = btnSlide.getMeasuredWidth();
              System.out.println("btnWidth=" + btnWidth);
          }

          @Override
          public void getViewSize(int idx, int w, int h, int[] dims) {
              dims[0] = w;
              dims[1] = h;
              final int menuIdx = 0;
              if (idx == menuIdx) {
                  dims[0] = w - btnWidth;
              }
          }
      }
       
    

}
