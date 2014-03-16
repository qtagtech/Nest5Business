package com.nest5.businessClient;

import java.io.File;
import java.util.List;
import java.util.prefs.Preferences;

import org.json.JSONObject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	





	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	private RestService restService;
	private Context mContext;
	private SharedPreferences prefs;
	private static String deviceID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		// Set up the login form.
		
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
		mContext = this;
		 prefs = Util.getSharedPreferences(mContext);
		 registerReceiver(onSyncDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		prefs = Util.getSharedPreferences(mContext);
		if(prefs.getBoolean(Setup.LOGGED_IN, false)){
			Intent inten = new Intent(mContext, Initialactivity.class);
			startActivity(inten);
		}
		
	}
	@Override
	protected void onPause(){
		super.onPause();
		try{
			unregisterReceiver(onSyncDownloadComplete);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			// Enviar email al servidor: params.email, params.android

			Log.i("MISPRUEBAS","EMPEZANDO REQUEST");
			 restService = new RestService(sendCredentialsHandler, mContext,
			 Setup.PROD_URL+"/company/checkLogin");
			 restService.addParam("email", mEmail);
			 restService.addParam("password", mPassword);		 
			 restService.setCredentials("apiadmin", Setup.apiKey);
			 try {
			 restService.execute(RestService.POST);} catch (Exception e) {
			 e.printStackTrace(); 
			 Log.i("MISPRUEBAS","Error empezando request");}
			 
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	
	
	private final Handler sendCredentialsHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			//showProgress(false); We don't remove the progress indicator since it is going to send DEvideid to the Bdata server
			
			//temporal abre actividad loggeado
		/*	prefs.edit().putBoolean(Setup.LOGGED_IN, true).putString(Setup.COMPANY_ID, "12212").putString(Setup.COMPANY_NAME, "Nest5 Test Company").commit();
			Intent intenter= new Intent(mContext, Initialactivity.class);
			startActivity(intenter);*/
			JSONObject respuesta = null;
				Log.i("MISPRUEBAS","LLEGUE");
			try {
				respuesta = new JSONObject((String) msg.obj);
			} catch (Exception e) {
				Log.i("MISPRUEBAS","ERROR JSON");
				e.printStackTrace();
				mEmailView.setError(getString(R.string.login_error));
			}

			if (respuesta != null) {
				Log.i("MISPRUEBAS","CON RESPUESTA");
				int status = 0;
				String compid = "";
				String compname = "";
				try {
					status = respuesta.getInt("status");
					compid = respuesta.getString("id");
					compname = respuesta.getString("name");

				} catch (Exception e) {
					Log.i("MISPRUEBAS","ERROR COGER DATOS");
					e.printStackTrace();
				}
				// quitar loading
				
				
				if (status == 1) {
					//Abrir Nueva Activity porque esta registrado ---- Enero de 2014, registrar dispositivo para la empresa actual en Nest5BigData Server
					deviceID = DeviceID.getDeviceId(mContext);
					//Log.i("AACCCAAAID", deviceID);
					
					//Intent inten = new Intent(mContext, Initialactivity.class);
					//startActivity(inten);
					Log.i("MISPRUEBAS","EMPEZANDO REQUEST PARA DEVICE ID");
					//http://localhost:8080/Nest5BusinessData/deviceOps/registerDevice?payload={%22device_id%22:%220A01B18B436A002555AF%22,%22company%22:1}
					 restService = new RestService(sendDeviceHandler, mContext,
					 Setup.PROD_BIGDATA_URL+"/deviceOps/registerDevice");
					 String jString = "{device_id:"+deviceID+",company:"+compid+"}";
					 prefs.edit().putBoolean(Setup.LOGGED_IN, true).putString(Setup.COMPANY_ID, compid).putString(Setup.COMPANY_NAME, compname).putString(Setup.DEVICE_REGISTERED_ID, deviceID).commit();
					 restService.addParam("payload", jString);		 
					 restService.setCredentials("apiadmin", Setup.apiKey);
					 try {
					 restService.execute(RestService.POST);} catch (Exception e) {
					 e.printStackTrace(); 
					 Log.i("MISPRUEBAS","Error empezando request de deviceid");}
				} else {
					mEmailView.setError(getString(R.string.login_error));
					showProgress(false); 
					//Abrir Nueva Activity porque esta registrado
					/*prefs.edit().putBoolean(Setup.LOGGED_IN, false).putString(Setup.COMPANY_ID, "0").putString(Setup.COMPANY_NAME, "N/A").commit();
					Intent inten = new Intent(mContext, Initialactivity.class);
					startActivity(inten);*/
				}

			}
			else{
				showProgress(false); 
				mEmailView.setError(getString(R.string.login_error));
			}

		}
	};
	private final Handler sendDeviceHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			showProgress(false); 
			prefs = Util.getSharedPreferences(mContext);
			JSONObject respuesta = null;
				Log.i("MISPRUEBAS","LLEGUE DE REGISTRAR DEVICEID");
			try {
				respuesta = new JSONObject((String) msg.obj);
			} catch (Exception e) {
				Log.i("MISPRUEBAS","ERROR JSON en device id");
				e.printStackTrace();
				mEmailView.setError(getString(R.string.device_error));
			}

			if (respuesta != null) {
				Log.i("MISPRUEBAS","CON RESPUESTA de device id");
				int status = 0;
				int responsecode = 0;
				String message = "";
				try {
					status = respuesta.getInt("status");
					responsecode = respuesta.getInt("code");
					message = respuesta.getString("message");

				} catch (Exception e) {
					Log.i("MISPRUEBAS","ERROR COGER DATOS en device id");
					e.printStackTrace();
				}
				// quitar loading
				
				
				if (status == 200) {
					//ok! status received, but still we have to check for code 555 that says everything done in Nest5 as expected.
					if(responsecode == 555){ //new register
				    	mLoginStatusMessageView.setText("Sincronizando Base de Datos");
						showProgress(true);
						prefs.edit().putBoolean(Setup.DEVICE_REGISTERED, true).commit();
						//before opening the main activity, sync the database
						String url = Setup.PROD_BIGDATA_URL+"/databaseOps/importDatabase?payload={\"company\":"+prefs.getString(Setup.COMPANY_ID, "0")+"}";
						DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
						
						// in order for this if to run, you must use the android 3.2 to compile your app
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
						    request.allowScanningByMediaScanner();
						    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
						}


 
						request.setDestinationInExternalFilesDir(mContext, Environment.getDataDirectory() + "/databases/", "nest5posinit.sql");
						request.setVisibleInDownloadsUi(false);
						// get download service and enqueue file
						DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
						manager.enqueue(request);
						//Intent inten = new Intent(mContext, Initialactivity.class);
						//startActivity(inten);
					}
					else{
						if(responsecode == 55511){ //registered already
					    	mLoginStatusMessageView.setText("Sincronizando Base de Datos");
							showProgress(true);
							Log.i("MISPRUEBAS",message);
							prefs.edit().putBoolean(Setup.DEVICE_REGISTERED, true).commit();
							//before opening the main activity, sync the database
							String url = Setup.PROD_BIGDATA_URL+"/databaseOps/importDatabase?payload={\"company\":"+prefs.getString(Setup.COMPANY_ID, "0")+"}";
							DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
							
							// in order for this if to run, you must use the android 3.2 to compile your app
							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
							    request.allowScanningByMediaScanner();
							    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
							}
							request.setDestinationInExternalFilesDir(mContext, Environment.getDataDirectory() + "/databases/", "nest5posinit.sql");
							request.setVisibleInDownloadsUi(false);
							// get download service and enqueue file
							DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
							manager.enqueue(request);
							//Intent inten = new Intent(mContext, Initialactivity.class);
							//startActivity(inten);
						}
						else{
							
							prefs.edit().putBoolean(Setup.DEVICE_REGISTERED, false).commit();
							prefs.edit().putString(Setup.DEVICE_REGISTERED_ID, "null").commit();
							mEmailView.setError(message);
						}
						
					}
					//if registered, it doesn't matter if new are re-register, take minsale, maxsale and current sale for keeping them in the database.
					/*
					 * public static final String COMPANY_NIT = "company_message";
    					public static final String COMPANY_TEL = "company_message";
    					public static final String COMPANY_ADDRESS = "company_message";
    					public static final String COMPANY_EMAIL = "company_message";
    					public static final String COMPANY_URL = "company_message";
    					public static final String COMPANY_MESSAGE = "company_message";
    					
    					*/

					int maxSale = 0;
					int currentSale = 0;
					String prefix = "";
					String nit = "";
					String tel = "";
					String address = "";
					String email = "";
					String url = "";
					String invoiceMessage = "";
					String tipMessage = "";
					try {
						maxSale = respuesta.getInt("maxSale");
						Log.i("DATOSINFO","maxSale: "+String.valueOf(maxSale));
						currentSale = respuesta.getInt("currentSale");
						Log.i("DATOSINFO","currentSale: "+String.valueOf(currentSale));
						prefix = respuesta.getString("prefix");
						Log.i("DATOSINFO","prefix: "+prefix);
						nit = respuesta.getString("nit");
						Log.i("DATOSINFO","nit: "+nit);
						tel = respuesta.getString("tel");
						Log.i("DATOSINFO","tel: "+tel);
						address = respuesta.getString("address");
						Log.i("DATOSINFO","address: "+address);
						email = respuesta.getString("email");
						Log.i("DATOSINFO","email: "+email);
						url = respuesta.getString("url");
						Log.i("DATOSINFO","url: "+url);
						invoiceMessage = respuesta.getString("invoiceMessage");
						Log.i("DATOSINFO","invoiceMessage: "+invoiceMessage);
						tipMessage = respuesta.getString("tipMessage");
						Log.i("DATOSINFO","tipMessage: "+tipMessage);
					} catch (Exception e) {
						Log.i("MISPRUEBAS","ERROR COGER DATOS de sales");
						e.printStackTrace();
					}
					
					prefs.edit()
					.putInt(Setup.MAX_SALE, maxSale)
					.putInt(Setup.CURRENT_SALE, currentSale)
					.putString(Setup.INVOICE_PREFIX, prefix)
					.putString(Setup.COMPANY_ADDRESS, address)
					.putString(Setup.COMPANY_EMAIL, email)
					.putString(Setup.COMPANY_MESSAGE, invoiceMessage)
					.putString(Setup.COMPANY_NIT, nit)
					.putString(Setup.COMPANY_TEL, tel)
					.putString(Setup.COMPANY_URL, url)
					.putString(Setup.TIP_MESSAGE, tipMessage)
					.commit();
				} else {
					
					prefs.edit().putBoolean(Setup.DEVICE_REGISTERED, false).commit();
					prefs.edit().putString(Setup.DEVICE_REGISTERED_ID, "null").commit();
					mEmailView.setError(getString(R.string.device_error));
					
					
				}

			}
			else{
				prefs.edit().putBoolean(Setup.DEVICE_REGISTERED, false).commit();
				mEmailView.setError(getString(R.string.device_error));
			}

		}
	};
	
	/**
	 * @param context used to check the device version and DownloadManager information
	 * @return true if the download manager is available
	 */
	public static boolean isDownloadManagerAvailable(Context context) {
	    try {
	        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
	            return false;
	        }
	        Intent intent = new Intent(Intent.ACTION_MAIN);
	        intent.addCategory(Intent.CATEGORY_LAUNCHER);
	        intent.setClassName("com.android.providers.downloads.ui", "com.android.providers.downloads.ui.DownloadList");
	        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent,
	                PackageManager.MATCH_DEFAULT_ONLY);
	        return list.size() > 0;
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	BroadcastReceiver onSyncDownloadComplete=new BroadcastReceiver() {
	    public void onReceive(Context ctxt, Intent intent) {
	        // Do Something
	    	showProgress(false);
	    	Intent inten = new Intent(mContext, Initialactivity.class);
			startActivity(inten);
	    }
	};
}
