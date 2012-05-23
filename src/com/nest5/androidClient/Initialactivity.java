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




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
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
    
    ProgressDialog dialogLogin;
    ProgressDialog dialogUpdateExtra;
    
    
    
	 

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
                
                
                restService = new RestService(sendEmailHandler, mContext, "http://nest5stage.herokuapp.com/api/user/newAndroidUser");
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
        getWindow().setFormat(PixelFormat.RGBA_8888);
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);

        // Register a receiver to provide register/unregister notifications
        registerReceiver(mUpdateUIReceiver, new IntentFilter(Util.UPDATE_UI_INTENT));
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

    /**
     * Shuts down the activity.
     */
    @Override
    public void onDestroy() {
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
            
            case R.id.menu_logout:
            	//Toast.makeText(mContext, "HOLA", Toast.LENGTH_SHORT).show();
                Util.userLogout(mContext);
                
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    // Manage UI Screens

    
    private void setExtraDataScreenContent(){
    	
    	setContentView(R.layout.extra_data);
    	 user = (EditText) findViewById(R.id.fullname);
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
    	
    	final SpinCircleView circle = (SpinCircleView) findViewById(R.id.main_spin);
    	
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
				case 1: Toast.makeText(mContext, "0", Toast.LENGTH_SHORT).show();
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
					lay = R.layout.deals;
					circle.rotate.setAnimationListener(animReady);
					break;
				case 5:
					Toast.makeText(mContext, "4", Toast.LENGTH_SHORT).show();
					break;
				case 6:
					Toast.makeText(mContext, "5", Toast.LENGTH_SHORT).show();
					break;
				default: 
				break;
				}
				return false;
			}
		});
    	
    }
    
    private void setDealsScreenContent(){
    	
    	//setContentView(R.layout.deals);
    	//Intent inte = new Intent(mContext, DealsActivity.class);
    	//startActivity(inte);
    	setContentView(R.layout.deals);
    	final ListView dealsList =  (ListView) findViewById(R.id.close_deals);
    	final ProgressDialog dialog;
    	
    	
	    dialog = new ProgressDialog(mContext);
	    new  AsyncTask<String, Void, String>(){
	    	boolean conError = false;
	    @Override
    	protected void onPreExecute(){
    		dialog.setMessage("Estamos buscando las mejores ofertas...");
    		dialog.show();
    	}
        @Override
    	protected String doInBackground(String... addrs) {
        	
        	StringBuilder builder = null;
        	for (String addr : addrs) {
        		builder = new StringBuilder();
    		HttpClient client = new DefaultHttpClient();
    		HttpGet httpGet = new HttpGet(addr);
    		try {
    			HttpResponse response = client.execute(httpGet);
    			StatusLine statusLine = response.getStatusLine();
    			int statusCode = statusLine.getStatusCode();
    			if (statusCode == 200) {
    				HttpEntity entity = response.getEntity();
    				InputStream content = entity.getContent();
    				BufferedReader reader = new BufferedReader(
    						new InputStreamReader(content));
    				String line;
    				while ((line = reader.readLine()) != null) {
    					builder.append(line);
    				}
    			} else {
    				Log.e(Initialactivity.class.toString(), "Failed to download file");
    				conError = true;
    			}
    		} catch (ClientProtocolException e) {
    			e.printStackTrace();
    			conError = true;
    			
    		} catch (IOException e) {
    			e.printStackTrace();
    			conError = true;
    			
    		}
    		
        }
        	return builder.toString();
        }
        
        
        @Override
        protected void onPostExecute(String result) {
        	
        	
        	//List<ImageAndText> imageList = createList(result);
    		
    	    
    	   
    	   
    	   
    	   if(conError){
    		   dialog.hide();
    		   lay = R.layout.home;
    		   setScreenContent();
    		   Toast.makeText(mContext, "Lo Sentimos.\nHubo errores al contactar el servidor.", Toast.LENGTH_SHORT).show();
    	   }
    	   else{
    		   try{
        		   Gson gson = new Gson();
       				final Deal lista[] = gson.fromJson(result, Deal[].class);
       				ImageAdapter adapter1 = new ImageAdapter(mContext, lista,dealsList);
    				dealsList.setAdapter(adapter1);
       				dialog.hide();
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
        		   e.printStackTrace();
        		   dialog.hide();
        		   lay = R.layout.home;
        		   setScreenContent();
        		   Toast.makeText(mContext, "La información recibida parece no ser válida", Toast.LENGTH_SHORT).show();
        		   
        	   }
    		   
    		  
    		   
    	   }
    	   
    	   
    	    
        }  		
			
				
    	    
        }.execute(Setup.DEV_URL+"/usuario/jsonTest");
	    
	    
	    
    }
    
    private void setLoggingScreenContent()
    {
    	setContentView(R.layout.deals);
    	dialogLogin = new ProgressDialog(mContext);
	    dialogLogin.setMessage("Inciando Sesión");
	    dialogLogin.show();
    	
    }
   /* private void setHelloWorldScreenContent() {
        setContentView(R.layout.hello_world);

        final TextView helloWorld = (TextView) findViewById(R.id.hello_world);
        final Button sayHelloButton = (Button) findViewById(R.id.say_hello);
        sayHelloButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sayHelloButton.setEnabled(false);
                helloWorld.setText(R.string.contacting_server);

                // Use an AsyncTask to avoid blocking the UI thread
                new AsyncTask<Void, Void, String>() {
                    private String message;

                    @Override
                    protected String doInBackground(Void... arg0) {
                        MyRequestFactory requestFactory = Util.getRequestFactory(mContext,
                                MyRequestFactory.class);
                        final HelloWorldRequest request = requestFactory.helloWorldRequest();
                        Log.i(TAG, "Sending request to server");
                        request.getMessage().fire(new Receiver<String>() {
                            @Override
                            public void onFailure(ServerFailure error) {
                                message = "Failure: " + error.getMessage();
                            }

                            @Override
                            public void onSuccess(String result) {
                                message = result;
                            }
                        });
                        return message;
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        helloWorld.setText(result);
                        sayHelloButton.setEnabled(true);
                    }
                }.execute();
            }
        });
    }*/

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	  IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
    	  if (scanResult != null) {
    	    // handle scan result
    		  Toast.makeText(mContext, scanResult.getContents(), Toast.LENGTH_LONG).show();
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
        }
    }
    
    @Override
    public void onBackPressed() {
    	Log.i(TAG, String.valueOf(R.layout.deals)+"   "+String.valueOf(lay));
      if(lay == R.layout.deals){
    	  lay = R.layout.home;
    	  setScreenContent();
      }
      else{
    	  finish();
      }
       
    }
    
    public void fbLogin(View v){
    	Intent inten = new Intent(mContext,FacebookActivity.class);
    	//inten.putExtra("com.nest5.androidClient.layout", R.layout.deals);
    	startActivity(inten);
    	
    }
    public void updateExtra(View v){
    	
    	String u = user.getText().toString();
    	//String p = pass.getText().toString();
    	restService = new RestService(mHandlerGet, this, "http://nest5stage.herokuapp.com/api/user/updateAndroidUser"); //Create new rest service for get
    	
    	restService.setCredentials("apiadmin", Setup.apiKey);
    	//enviar id de usuario para actualizar datos de nombre
    	SharedPreferences prefs = Util.getSharedPreferences(mContext);
        int uid = prefs.getInt(Util.USER_REGISTRATION_ID, 0);
        restService.addParam("name", u);
        restService.addParam("userid", String.valueOf(uid));
        dialogUpdateExtra = new ProgressDialog(mContext);
	    dialogUpdateExtra.setMessage("Actualizando Información");
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
    
   /* private final Handler mHandlerGet = new Handler(){
    	@Override
    	public void handleMessage(Message msg){
    		User user = null; 
    		try{
    				Gson gson = new Gson();
    				user = gson.fromJson((String)msg.obj, User.class);
    		}
    		catch(Exception e){
    			e.printStackTrace();
    		}
    		
    				String status = "unathorized";
    				if(user != null){
    					//Toast.makeText(mContext,user.username , Toast.LENGTH_LONG).show();
    					status = "authorized";
    				}
    				else{
    					//Toast.makeText(mContext,"Login Incorrecto" , Toast.LENGTH_LONG).show();
    				}
    				
    				
    	    		String message = null;
    	            String loggedStatus = Util.LOGGEDOUT;
    	            if (status == "authorized") {
    	                message = getResources().getString(R.string.registration_succeeded);
    	                loggedStatus = Util.LOGGEDIN;
    	                Log.i(TAG, "Registrado");
    	                
    	            } else if (status == "unuthorizedok") {
    	                message = getResources().getString(R.string.unregistration_succeeded);
    	                Log.i(TAG, "Salido");
    	            } else {
    	                message = getResources().getString(R.string.registration_error);
    	                Log.i(TAG, "Error");
    	                String usuario = user != null ? user.username: "usuario";
        	            Toast.makeText(mContext,String.format(message, usuario), Toast.LENGTH_LONG).show();
    	            }
    	           

    	            // Set connection status
    	            SharedPreferences prefs = Util.getSharedPreferences(mContext);
    	            prefs.edit().putString(Util.LOGGED_STATUS, loggedStatus).commit();
    	            
    	            if(status == "authorized"){
    	            	lay = R.layout.home;
    	    	    	setScreenContent();
    	            }
    	            
    	            // Display a notification
    	           
    		
    		
    			
    			
    		}		
    };*/
    
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
    
    public void scanCode(View v){
    	IntentIntegrator integrator = new IntentIntegrator(this);
    	integrator.initiateScan();
    }
    

}
