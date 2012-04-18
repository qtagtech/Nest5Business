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

import com.google.gson.Gson;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
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
    
    
    
	 

    /**
     * A {@link BroadcastReceiver} to receive the response from a register or
     * unregister request, and to update the UI.
     */
    private final BroadcastReceiver mUpdateUIReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String accountName = intent.getStringExtra(DeviceRegistrar.ACCOUNT_NAME_EXTRA);
            int status = intent.getIntExtra(DeviceRegistrar.STATUS_EXTRA,
                    DeviceRegistrar.ERROR_STATUS);
            String message = null;
            String connectionStatus = Util.DISCONNECTED;
            if (status == DeviceRegistrar.REGISTERED_STATUS) {
                message = getResources().getString(R.string.registration_succeeded);
                connectionStatus = Util.CONNECTED;
            } else if (status == DeviceRegistrar.UNREGISTERED_STATUS) {
                message = getResources().getString(R.string.unregistration_succeeded);
            } else {
                message = getResources().getString(R.string.registration_error);
            }

            // Set connection status
            SharedPreferences prefs = Util.getSharedPreferences(mContext);
            prefs.edit().putString(Util.CONNECTION_STATUS, connectionStatus).commit();

            // Display a notification
            Util.generateNotification(mContext, String.format(message, accountName));
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

        SharedPreferences prefs = Util.getSharedPreferences(mContext);
        String connectionStatus = prefs.getString(Util.CONNECTION_STATUS, Util.DISCONNECTED);
        if (Util.DISCONNECTED.equals(connectionStatus)) {
            startActivity(new Intent(this, AccountsActivity.class));
        }
        int  layoutValue = getIntent().getIntExtra("com.nest5.androidClient.layout",0);
        lay = layoutValue != 0 ? layoutValue : R.layout.home;
        
        setScreenContent();
 
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

    // Manage UI Screens

    
    private void setHomeScreenContent(){
    	setContentView(R.layout.home);
    	
    	final SpinCircleView circle = (SpinCircleView) findViewById(R.id.main_spin);
    	
    	
        
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

    /**
     * Sets the screen content based on the screen id.
     */
    private void setScreenContent() {
        
        switch (lay) {
            case R.layout.home:
                setHomeScreenContent();
                break;
            case R.layout.deals:
                setDealsScreenContent();
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
    

}
