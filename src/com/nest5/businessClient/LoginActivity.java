package com.nest5.businessClient;

import java.util.prefs.Preferences;

import org.json.JSONObject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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
			showProgress(false);
			
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
					//Abrir Nueva Activity porque esta registrado
					prefs.edit().putBoolean(Setup.LOGGED_IN, true).putString(Setup.COMPANY_ID, compid).putString(Setup.COMPANY_NAME, compname).commit();
					Intent inten = new Intent(mContext, Initialactivity.class);
					startActivity(inten);
				} else {
					mEmailView.setError(getString(R.string.login_error));
					
					//Abrir Nueva Activity porque esta registrado
					/*prefs.edit().putBoolean(Setup.LOGGED_IN, false).putString(Setup.COMPANY_ID, "0").putString(Setup.COMPANY_NAME, "N/A").commit();
					Intent inten = new Intent(mContext, Initialactivity.class);
					startActivity(inten);*/
				}

			}
			else{
				mEmailView.setError(getString(R.string.login_error));
			}

		}
	};
}