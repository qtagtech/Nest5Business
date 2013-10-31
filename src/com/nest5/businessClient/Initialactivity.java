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
package com.nest5.businessClient;

/*
 * 
 * <a href="http://thenounproject.com/noun/piggy-bank/#icon-No2155" target="_blank">Piggy Bank</a> designed by <a href="http://thenounproject.com/jezmael" target="_blank">Jezmael Basilio</a> from The Noun Project
 * 
 * */


import com.nest5.businessClient.User;

import com.nest5.businessClient.AddIngredientCategoryForm.OnAddIngredientCategoryListener;
import com.nest5.businessClient.AddProductCategoryForm.OnAddProductCategoryListener;
import com.nest5.businessClient.AddProductForm.OnAddProductListener;
import com.nest5.businessClient.CreateComboView.OnCreateComboListener;
import com.nest5.businessClient.CreateProductView.OnCreateProductListener;
import com.nest5.businessClient.DailyObjectFragment.OnDailyObjectFragmentCreatedListener;
import com.nest5.businessClient.HomeObjectFragment.OnHomeObjectFragmentCreatedListener;
import com.nest5.businessClient.InventoryObjectFragment.OnInventoryObjectFragmentCreatedListener;
import com.nest5.businessClient.Nest5ReadObjectFragment.OnNest5ReadObjectFragmentCreatedListener;
import com.nest5.businessClient.R;
import com.nest5.businessClient.PaymentForm.OnPayListener;
import com.nest5.businessClient.SalesObjectFragment.OnSalesObjectFragmentCreatedListener;
import com.nest5.businessClient.SelectAddItem.OnAddItemSelectedListener;
import com.nest5.businessClient.AddIngredientForm.OnAddIngredientListener;
import com.nest5.businessClient.HomeObjectFragment.OnIngredientCategorySelectedListener;
import com.nest5.businessClient.Registrable;




import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import java.security.GeneralSecurityException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;
import java.util.Random;

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

//import com.example.android.BluetoothChat.BluetoothChat;

import com.nest5.businessClient.WifiDirectDialog.DeviceActionListener;

import com.acs.acr31.ACR31Reader;

import com.acs.acr31.Track1Data;
import com.acs.acr31.Track2Data;
import com.acs.acr31.TrackData;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.nest5.businessClient.ViewUtils;

import android.R.color;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ChannelListener;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main activity - requests "Hello, World" messages from the server and provides
 * a menu item to invoke the accounts activity.
 */
public class Initialactivity extends FragmentActivity implements
		OnAddItemSelectedListener, OnAddIngredientListener,
		OnIngredientCategorySelectedListener,
		OnHomeObjectFragmentCreatedListener, OnAddIngredientCategoryListener,
		OnAddProductCategoryListener, OnAddProductListener,
		OnCreateProductListener, OnPayListener, OnCreateComboListener,
		OnSalesObjectFragmentCreatedListener, ChannelListener,
		DeviceActionListener, ConnectionInfoListener, PeerListListener,
		OnDailyObjectFragmentCreatedListener,
		OnInventoryObjectFragmentCreatedListener,
		OnNest5ReadObjectFragmentCreatedListener {
	/**
	 * Tag for logging.
	 */
	public static final String TAG = "Initialactivity";

	public static final int TOAST_COMMAND = 1;

	public static final int DELETE_ALL_COMMAND = 2;

	public static final int SEND_ALL_COMMAND = 3;

	public static final int TABLE_TYPE_TODAY = 0;

	public static final int TABLE_TYPE_ALL = 1;

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
	TextView statusText;
	TextView deviceText = null;
	TextView saleValue;

	ProgressDialog dialogLogin;
	ProgressDialog dialogUpdateExtra;

	private Button pagarButton;

	private Button guardarButton;

	private Button todayTableBtn;
	private Button allTableBtn;

	// When requested, this adapter returns a DemoObjectFragment,
	// representing an object in the collection.
	DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
	ViewPager mViewPager;
	GridView itemsView;
	TableLayout table;
	TableLayout dailyTable;
	TableLayout inventoryTable;

	// Saber si llama poner stampCard desde lista de promociones de usuario
	Boolean fromMyDeals = false;

	// Validar si bot�n redime cup�n o sello

	Boolean redeemCoupon = false;

	Boolean redimiendo = false;

	private static final int TWO_MINUTES = 1000 * 60 * 2;

	// private LocationManager locationManager;
	// private String provider;

	// private Location location;

	// varibales de menu

	View app;
	ImageView btnSlide;
	boolean menuOut = false;
	Handler handler = new Handler();
	int btnWidth;
	ListView ordersList;
	TextView sale_name;
	TextView sale_details;

	Bundle savedInstanceState;

	boolean validHome = true;

	private SQLiteDatabase db;
	private IngredientCategoryDataSource ingredientCategoryDatasource;
	private ProductCategoryDataSource productCategoryDatasource;
	private IngredientDataSource ingredientDatasource;
	private ProductDataSource productDatasource;
	private ComboDataSource comboDatasource;
	private TaxDataSource taxDataSource;
	private UnitDataSource unitDataSource;
	private SaleDataSource saleDataSource;
	ImageAdapter gridAdapter;
	SaleAdapter cookingAdapter;
	LayoutInflater inflater;

	AlertDialog.Builder builder;
	AlertDialog alertDialog;

	List<IngredientCategory> ingredientCategories;
	List<ProductCategory> productsCategories;
	List<Tax> taxes;
	List<Ingredient> ingredientes;
	List<Product> productos;
	List<Combo> combos;
	List<Registrable> productList;
	List<Registrable> ingredientList;
	List<Registrable> comboList;
	List<Registrable> registerList;
	List<Unit> units;
	List<Sale> saleList;
	List<Sale> salesFromToday;

	private LinkedHashMap<Registrable, Integer> currentOrder;
	private List<Registrable> currentList;
	private List<Registrable> inTableRegistrable;
	private LinkedHashMap<String, LinkedHashMap<Registrable, Integer>> savedOrders;
	private LinkedList<LinkedHashMap<Registrable, Integer>> cookingOrders;
	private LinkedHashMap<LinkedHashMap<Registrable, Integer>, String> cookingOrdersMethods;
	private LinkedHashMap<LinkedHashMap<Registrable, Integer>, Long> cookingOrdersTimes;
	private LinkedHashMap<LinkedHashMap<Registrable, Integer>, Double> cookingOrdersReceived;
	private String[] frases;
	private int currentSelectedPosition = -1;
	private long editItemId = -1;
	private int editingItemPositionInRegistrable = -1;

	private Socket socket = null;
	private DataOutputStream wifiOutputStream = null;
	private DataInputStream wifiInputStream = null;

	private WifiP2pManager manager;
	private boolean isWifiP2pEnabled = false;
	private boolean retryChannel = false;

	private final IntentFilter intentFilter = new IntentFilter();
	private Channel channel;
	private BroadcastReceiver receiver = null;
	public Boolean isConnecting = false;
	private String connectedIp = "";
	public Boolean isConnected = false;

	// Todo lo del timer para actualizar label en la lista
	private static Boolean isTimerRunning = false;
	private static Timer timer;
	public static long currentTime;

	private long init;
	private long end;

	// Propiedades de lector magn�tico ACR31 de ACS Ltd.
	private AudioManager mAudioManager;
	private ACR31Reader mReader;
	private ProgressDialog mResetProgressDialog;
	private boolean mSettingSleepTimeout;
	private boolean mGettingStatus;
	private int mSleepTimeout = 255;

	private Button readCardBtn;

	

	private SharedPreferences prefs;
	
	private List<Promo> companyPromos;
	private User currentUser;
	


	/**
	 * @param isWifiP2pEnabled
	 *            the isWifiP2pEnabled to set
	 */
	public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled) {
		this.isWifiP2pEnabled = isWifiP2pEnabled;
	}

	/**
	 * A {@link BroadcastReceiver} to receive the response from a register or
	 * unregister request, and to update the UI.
	 */
	private final BroadcastReceiver mUpdateUIReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String accountId = intent.getStringExtra("USER_ID");
			String accountName = intent
					.getStringExtra(DeviceRegistrar.ACCOUNT_NAME_EXTRA);
			int status = intent.getIntExtra(DeviceRegistrar.STATUS_EXTRA,
					DeviceRegistrar.ERROR_STATUS);
			String message = null;
			String connectionStatus = Util.DISCONNECTED;
			SharedPreferences prefs = Util.getSharedPreferences(mContext);
			if (status == DeviceRegistrar.REGISTERED_STATUS) {
				message = getResources().getString(
						R.string.registration_succeeded);
				connectionStatus = Util.CONNECTED;
				prefs.edit()
						.putString(Util.CONNECTION_STATUS, connectionStatus)
						.commit();
				// Enviar email al servidor: params.email, params.android

				/*
				 * restService = new RestService(sendEmailHandler, mContext,
				 * "http://www.nest5.com/api/user/newAndroidUser");
				 * restService.addParam("email", accountName);
				 * restService.addParam("android", accountId);
				 * //Toast.makeText(mContext, accountId,
				 * Toast.LENGTH_LONG).show();
				 * restService.setCredentials("apiadmin", Setup.apiKey);
				 * prefs.edit().putString(Util.LOGGED_STATUS, Util.LOGGINGIN);
				 * try {
				 * 
				 * restService.execute(RestService.POST); //Executes the request
				 * with the HTTP POST verb } catch (Exception e) {
				 * e.printStackTrace(); }
				 */
			} else if (status == DeviceRegistrar.UNREGISTERED_STATUS) {
				prefs.edit()
						.putString(Util.CONNECTION_STATUS, connectionStatus)
						.commit();
				message = getResources().getString(
						R.string.unregistration_succeeded);
				// Iniciar actividad de Account
				startActivity(new Intent(mContext, AccountsActivity.class));
			} else {

				prefs.edit()
						.putString(Util.CONNECTION_STATUS, connectionStatus)
						.commit();
				message = getResources().getString(R.string.registration_error);
				startActivity(new Intent(mContext, AccountsActivity.class));
			}

			// Display a notification
			// Util.generateNotification(mContext, String.format(message,
			// accountName));
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
		setContentView(R.layout.swipe_view);
		checkLogin();
		// add necessary intent values to be matched.

		intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
		intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
		intentFilter
				.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
		intentFilter
				.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

		manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
		channel = manager.initialize(this, getMainLooper(), null);

		ingredientCategoryDatasource = new IngredientCategoryDataSource(this);
		db = ingredientCategoryDatasource.open();
		ingredientCategories = ingredientCategoryDatasource
				.getAllIngredientCategory();
		// ingredientCategoryDatasource.close();
		productCategoryDatasource = new ProductCategoryDataSource(this);
		productCategoryDatasource.open(db);
		productsCategories = productCategoryDatasource.getAllProductCategory();
		ingredientDatasource = new IngredientDataSource(this);
		ingredientDatasource.open(db);
		ingredientes = ingredientDatasource.getAllIngredient();
		productDatasource = new ProductDataSource(this);
		productDatasource.open(db);
		taxDataSource = new TaxDataSource(mContext);
		taxDataSource.open(db);
		taxes = taxDataSource.getAllTax();
		unitDataSource = new UnitDataSource(mContext);
		unitDataSource.open(db);
		units = unitDataSource.getAllUnits();
		saleDataSource = new SaleDataSource(mContext);
		saleDataSource.open(db);
		saleList = saleDataSource.getAllSales();
		Calendar today = Calendar.getInstance();
		Calendar tomorrow = Calendar.getInstance();
		today.set(Calendar.HOUR, 0);
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		tomorrow.roll(Calendar.DATE, 1);
		tomorrow.set(Calendar.HOUR, 0);
		tomorrow.set(Calendar.HOUR_OF_DAY, 0);
		tomorrow.set(Calendar.MINUTE, 0);
		tomorrow.set(Calendar.SECOND, 0);
		tomorrow.set(Calendar.MILLISECOND, 0);

		init = today.getTimeInMillis();
		end = tomorrow.getTimeInMillis();
		Log.d(TAG, today.toString());
		Log.d(TAG, tomorrow.toString());
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(System.currentTimeMillis());
		Log.d(TAG, now.toString());

		Log.d(TAG, "Diferencia entre tiempos: " + String.valueOf(end - init));
		salesFromToday = saleDataSource.getAllSalesWithin(init, end);
		productos = productDatasource.getAllProduct();
		comboDatasource = new ComboDataSource(mContext);
		comboDatasource.open(db);
		combos = comboDatasource.getAllCombos();
		productList = new ArrayList<Registrable>();
		inflater = Initialactivity.this.getLayoutInflater();
		Iterator<Product> iterator = productos.iterator();

		while (iterator.hasNext()) {
			// Log.i("HOLAAA",iterator.next().getName());
			productList.add(new Registrable(iterator.next()));

		}
		ingredientList = new ArrayList<Registrable>();
		Iterator<Ingredient> iterator2 = ingredientes.iterator();

		while (iterator2.hasNext()) {
			// Log.i("HOLAAA",iterator.next().getName());
			ingredientList.add(new Registrable(iterator2.next()));

		}

		comboList = new ArrayList<Registrable>();
		inflater = Initialactivity.this.getLayoutInflater();
		Iterator<Combo> iterator3 = combos.iterator();

		while (iterator3.hasNext()) {
			// Log.i("HOLAAA",iterator.next().getName());
			comboList.add(new Registrable(iterator3.next()));

		}
		// ingredientDatasource.close();
		mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(
				getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mDemoCollectionPagerAdapter);

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// When swiping between pages, select the
						// corresponding tab.
						getActionBar().setSelectedNavigationItem(position);

					}
				});

		final ActionBar actionBar = getActionBar();
		// Specify that tabs should be displayed in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create a tab listener that is called when the user changes tabs.
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				mViewPager.setCurrentItem(tab.getPosition());

			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

		};

		Tab homeTab = actionBar.newTab().setText("Inicio")
				.setTabListener(tabListener);
		Tab ordersTab = actionBar.newTab().setText("�rdenes")
				.setTabListener(tabListener);
		Tab dailyTab = actionBar.newTab().setText("Registros")
				.setTabListener(tabListener);
		Tab inventoryTab = actionBar.newTab().setText("Inventarios")
				.setTabListener(tabListener);
		Tab nest5ReadTab = actionBar.newTab().setText("Nest5")
				.setTabListener(tabListener);
		actionBar.addTab(homeTab, true);
		actionBar.addTab(ordersTab, false);
		actionBar.addTab(dailyTab, false);
		actionBar.addTab(inventoryTab, false);
		actionBar.addTab(nest5ReadTab, false);

		// Register a receiver to provide register/unregister notifications
		// registerReceiver(mUpdateUIReceiver, new
		// IntentFilter(Util.UPDATE_UI_INTENT));

		currentOrder = new LinkedHashMap<Registrable, Integer>();
		inTableRegistrable = new ArrayList<Registrable>();
		savedOrders = new LinkedHashMap<String, LinkedHashMap<Registrable, Integer>>();
		cookingOrders = new LinkedList<LinkedHashMap<Registrable, Integer>>();
		cookingOrdersMethods = new LinkedHashMap<LinkedHashMap<Registrable, Integer>, String>();
		cookingOrdersTimes = new LinkedHashMap<LinkedHashMap<Registrable, Integer>, Long>();
		cookingOrdersReceived = new LinkedHashMap<LinkedHashMap<Registrable, Integer>, Double>();
		frases = getResources().getStringArray(R.array.phrases);
		timer = new Timer();

		BebasFont = Typeface
				.createFromAsset(getAssets(), "fonts/BebasNeue.otf");
		VarelaFont = Typeface.createFromAsset(getAssets(),
				"fonts/Varela-Regular.otf");

		// Lector de tarjetas magn�ticas
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mReader = new ACR31Reader(mAudioManager);
		/* Initialize the reset progress dialog */
		mResetProgressDialog = new ProgressDialog(mContext);

		


		// ACR31 RESET CALLBACK
		mReader.setOnResetCompleteListener(new ACR31Reader.OnResetCompleteListener() {

			@Override
			public void onResetComplete(ACR31Reader reader) {

				if (mSettingSleepTimeout) {

					/* Get the status after modifying the sleep timeout. */
					mGettingStatus = true;

					/* Set the sleep timeout. */
					mReader.setSleepTimeout(mSleepTimeout);
					mSettingSleepTimeout = false;
				}

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						mResetProgressDialog.dismiss();
					};
				});
			}
		});
		/* Set the raw data callback. */
		mReader.setOnRawDataAvailableListener(new ACR31Reader.OnRawDataAvailableListener() {

			@Override
			public void onRawDataAvailable(ACR31Reader reader, byte[] rawData) {
				Log.i("MISPRUEBAS", "setOnRawDataAvailableListener");

				final String hexString = toHexString(rawData)
						+ (reader.verifyData(rawData) ? " (Checksum OK)"
								: " (Checksum Error)");

				Log.i("MISPRUEBAS", hexString);
				if(reader.verifyData(rawData)){
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							
							mResetProgressDialog.setMessage("Solicitando Informaci�n al Servidor...");
							mResetProgressDialog.setCancelable(false);
							mResetProgressDialog.setIndeterminate(true);
							mResetProgressDialog.show();
							
						}
					});
					SharedPreferences prefs = Util.getSharedPreferences(mContext);
					
					restService = new RestService(recievePromoandUserHandler, mContext,
							 Setup.PROD_URL+"/company/initMagneticStamp");
							 restService.addParam("company", prefs.getString(Setup.COMPANY_ID, "0"));
							 restService.addParam("magnetic5", hexString);
							 restService.setCredentials("apiadmin", Setup.apiKey);
							 try {
							 restService.execute(RestService.POST);} catch (Exception e) {
							 e.printStackTrace(); 
							 Log.i("MISPRUEBAS","Error empezando request");}
				}
				
			}
		});
		
		

	}

	@Override
	public void onSaveInstanceState(Bundle savedInstance) {
		super.onSaveInstanceState(savedInstance);

	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstance) {
		this.savedInstanceState = savedInstance;
	}

	@Override
	protected void onStart() {
		super.onStart();
		mReader.start();
	}

	@Override
	public void onResume() {
		super.onResume();

		receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);
		registerReceiver(receiver, intentFilter);

		SharedPreferences prefs = Util.getSharedPreferences(mContext);
		String connectionStatus = prefs.getString(Util.CONNECTION_STATUS,
				Util.DISCONNECTED);
		mReader.start();
		/*
		 * if (Util.DISCONNECTED.equals(connectionStatus)) {
		 * Log.i(TAG,"Desconectado"); startActivity(new Intent(this,
		 * AccountsActivity.class)); } else {
		 * if(Util.CONNECTING.equals(connectionStatus)){
		 * Log.i(TAG,"Conectando ando"); //Toast.makeText(mContext, "Loggeando",
		 * Toast.LENGTH_LONG).show(); //poner logging in lay = R.layout.logging;
		 * setScreenContent(); } else { Log.i(TAG,"Conectado"); int layoutValue
		 * = getIntent().getIntExtra("com.nest5.androidClient.layout",0);
		 * 
		 * String loggedStatus = prefs.getString(Util.LOGGED_STATUS,
		 * Util.LOGGEDOUT); //Toast.makeText(mContext, loggedStatus,
		 * Toast.LENGTH_LONG).show(); if(Util.LOGGEDIN.equals(loggedStatus)) {
		 * lay = layoutValue != 0 ? layoutValue : R.layout.home;
		 * 
		 * } else{ //Hacer Login forzado porque esta conectado con google pero
		 * por alguna razon no esta loggeado en nest5, //mientras tanto uuestra
		 * la pantalla de actividad para desconectar hy volver a conectar
		 * startActivity(new Intent(this, AccountsActivity.class));
		 * 
		 * 
		 * }
		 * 
		 * 
		 * setScreenContent(); } }
		 */

		lay = R.layout.home;

		setScreenContent();

	}

	@Override
	protected void onPause() {
		unregisterReceiver(receiver);
		super.onPause();
		mResetProgressDialog.dismiss();
		mReader.stop();

	}

	/**
	 * Shuts down the activity.
	 */
	@Override
	public void onDestroy() {

		super.onDestroy();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mResetProgressDialog.dismiss();
		mReader.stop();
	}

	//

	// Manage UI Screens

	private void setSplashScreen() {
		setContentView(R.layout.splash);

	}

	private void setHomeScreenContent() {

		// Toast.makeText(mContext, ingredientes.get(0).toString() +
		// " De Categoria: "+ingredientes.get(0).getCategory().toString(),
		// Toast.LENGTH_LONG).show();

		// String[] comments = new String[] { "Vegetales", "Carnes", "Legumbres"
		// };
		// int nextInt = new Random().nextInt(3);
		// Save the new comment to the database
		// IngredientCategory ingredientCategory =
		// ingredientCategoryDatasource.createIngredientCategory(comments[nextInt]);

		// ListView lista = (ListView)
		// findViewById(R.id.ingredient_category_list);

		// Use the SimpleCursorAdapter to show the
		// elements in a ListView
		// ArrayAdapter<IngredientCategory> adapter = new
		// ArrayAdapter<IngredientCategory>(this,
		// android.R.layout.simple_list_item_1, values);
		// lista.setAdapter(adapter);

		//

		SharedPreferences prefs = Util.getSharedPreferences(mContext);

		// traer el objeto del usuario porque cuando ya esta loggeado no esta el
		// objeto usuario aca

		if (prefs.getInt(Util.INTERNET_CONNECTION, 0) == 1) {

		} else {
			// Toast.makeText(mContext, "No tienes conexi�n a internet.",
			// Toast.LENGTH_LONG).show();
			/*
			 * AlertDialog.Builder builder = new AlertDialog.Builder(this);
			 * builder.setMessage(
			 * "No tienes una conexi�n a internet activa. Habil�tala haciendo click en aceptar y seleccionando luego una red."
			 * ) .setCancelable(false) .setPositiveButton("Aceptar", new
			 * DialogInterface.OnClickListener() { public void
			 * onClick(DialogInterface dialog, int id) { Intent intent = new
			 * Intent(Settings.ACTION_WIFI_SETTINGS);
			 * startActivityForResult(intent, 1); } })
			 * .setNegativeButton("Cancelar", new
			 * DialogInterface.OnClickListener() { public void
			 * onClick(DialogInterface dialog, int id) { finish(); } }).show();
			 */
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_add:
			showAddItemDialog();
			return true;
		case R.id.menu_sync:
			/*Toast.makeText(mContext, " " + salesFromToday.size(),
					Toast.LENGTH_LONG).show();*/
			backUpDb();
			return true;
		case R.id.menu_load_register:

			return true;
		case R.id.menu_connect_device:
			if (!isWifiP2pEnabled) {
				Toast.makeText(Initialactivity.this,
						"Debes Activar WifiDirect", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
				return true;
			}
			// statusText = (TextView) findViewById(R.id.group_owner);
			statusText.setText("Buscando dispositivos... ");

			manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {

				@Override
				public void onSuccess() {
					Toast.makeText(Initialactivity.this, "B�squeda Iniciada",
							Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onFailure(int reasonCode) {
					Toast.makeText(Initialactivity.this,
							"B�squeda Fallida: " + reasonCode,
							Toast.LENGTH_SHORT).show();
				}
			});
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void showAddItemDialog() {
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
		SelectAddItem editNameDialog = new SelectAddItem();
		editNameDialog.show(fm, "fragment_edit_name");
	}

	private void showAddIngredientFormDialog() {
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

		AddIngredientForm addingredientDialog = new AddIngredientForm(
				ingredientCategories, taxes, units);
		addingredientDialog.show(fm, "fragment_edit_name");
	}

	private void showAddProductFormDialog() {
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

		AddProductForm addProductDialog = new AddProductForm(
				productsCategories, taxes);
		addProductDialog.show(fm, "fragment_edit_name");
	}

	private void showAddProductCategoryFormDialog() {
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

		AddProductCategoryForm addProductCategoryDialog = new AddProductCategoryForm();
		addProductCategoryDialog.show(fm, "fragment_edit_name");
	}

	private void showAddIngredientCategoryFormDialog() {
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

		AddIngredientCategoryForm addingredientCategoryDialog = new AddIngredientCategoryForm();
		addingredientCategoryDialog.show(fm, "fragment_edit_name");
	}

	private void showCreateProductFormDialog(Product product) {
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

		CreateProductView createProductDialog = new CreateProductView(
				ingredientes, product);
		createProductDialog.show(fm, "fragment_edit_name");
	}

	private void showAddComboFormDialog() {
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

		CreateComboView createComboDialog = new CreateComboView(ingredientes,
				productos, taxes);
		createComboDialog.show(fm, "fragment_edit_name");
	}

	private void showPaymentFormDialog() {
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
		PaymentForm editNameDialog = new PaymentForm(currentOrder);
		editNameDialog.show(fm, "fragment_edit_name");
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		// Log.i(TAG, String.valueOf(redeemCoupon));
		if (scanResult != null) {

		}
		// else continue with any other code you need in the method

	}

	/**
	 * Sets the screen content based on the screen id.
	 */
	private void setScreenContent() {

		switch (lay) {
		case R.layout.splash:
			setSplashScreen();
			break;

		case R.layout.home:
			setHomeScreenContent();
			break;

		}
	}

	public void fbLogin(View v) {
		Intent inten = new Intent(mContext, FacebookActivity.class);
		// inten.putExtra("com.nest5.androidClient.layout", R.layout.deals);
		startActivity(inten);

	}

	private final Handler mHandlerGet = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			JSONObject respuesta = null;

			try {
				respuesta = new JSONObject((String) msg.obj);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (respuesta != null) {
				int status = 0;
				try {
					status = respuesta.getInt("status");

				} catch (Exception e) {
					e.printStackTrace();
				}
				// quitar loading
				dialogUpdateExtra.hide();
				// dialogLogin.hide();
				// Toast.makeText(mContext, String.valueOf(status),
				// Toast.LENGTH_LONG).show();
				if (status == 1) {
					lay = R.layout.home;
					setScreenContent();
				} else {
					Toast.makeText(
							mContext,
							"Lo Sentimos, hay errores con el servidor pero en breve lo arreglaremos.",
							Toast.LENGTH_LONG).show();
				}

			}

		}
	};

	public void scanCode(View v) {

		redeemCoupon = false;
		IntentIntegrator integrator = new IntentIntegrator(this);
		integrator.initiateScan();

	}

	@Override
	public void OnAddItemSelected(int item) {
		// Toast.makeText(mContext, String.valueOf(item),
		// Toast.LENGTH_LONG).show();
		// dependiendo de la posicion inicia la actividad necesaria para agregar
		// un ingrediente o una categoria o asi, y lo hace como un
		// dialogfragment con un formulario adentro. :)
		switch (item) {
		case 0:
			showAddIngredientFormDialog();
			break;
		case 1:
			showAddProductFormDialog();
			break;
		case 2:
			showAddComboFormDialog();
			break;
		case 3:
			showAddIngredientCategoryFormDialog();
			break;
		case 4:
			showAddProductCategoryFormDialog();
			break;
		}
	}

	@Override
	public void OnAddIngredientSave(String name, IngredientCategory category,
			Tax tax, Unit unit, double costPerUnit, double pricePerUnit,
			double priceMeasure, Boolean editing, double qty) {
		// TODO Auto-generated method stub
		// Toast.makeText(mContext, name+" "+categoryId.getName(),
		// Toast.LENGTH_LONG).show();
		if (!editing) {
			long date = System.currentTimeMillis();
			Ingredient ingredient = null;
			try {
				ingredient = ingredientDatasource.createIngredient(name,
						category, tax, unit, costPerUnit, pricePerUnit,
						priceMeasure, qty, date);

			} catch (Exception e) {
				e.printStackTrace();
			}
			if (ingredient != null) {
				ingredientes.add(ingredient);
				ingredientList.add(new Registrable(ingredient));
				Toast.makeText(mContext,
						ingredient.getName() + " Creado Satisfactoriamente.",
						Toast.LENGTH_SHORT).show();
				// informar de cambios a lista de ingredientes
			} else {
				Toast.makeText(mContext,
						"Hubo Errores Creando el Ingrediente '" + name + "'.",
						Toast.LENGTH_SHORT).show();
				// informar de cambios a lista de ingredientes
			}
		} else // se edito un ingrediente
		{
			Ingredient newIngredient = null;

			try {
				newIngredient = ingredientDatasource.updateIngredient(
						editItemId, name, category, tax, unit, costPerUnit,
						pricePerUnit, priceMeasure, qty);

			} catch (Exception e) {
				e.printStackTrace();
			}
			if (newIngredient != null) {

				ingredientes = ingredientDatasource.getAllIngredient();
				ingredientList.remove(editingItemPositionInRegistrable);
				ingredientList.add(new Registrable(newIngredient));
				editingItemPositionInRegistrable = -1;
				editItemId = -1;
				Toast.makeText(
						mContext,
						newIngredient.getName()
								+ " Actualizado Satisfactoriamente.",
						Toast.LENGTH_SHORT).show();
				// informar de cambios a lista de ingredientes
			} else {
				Toast.makeText(
						mContext,
						"Hubo Errores Actualizando el Ingrediente '" + name
								+ "'.", Toast.LENGTH_SHORT).show();
				// informar de cambios a lista de ingredientes
			}
		}

	}

	@Override
	public void OnAddProductCategorySave(String name) {
		// TODO Auto-generated method stub
		ProductCategory categoryProduct = null;
		try {
			categoryProduct = productCategoryDatasource
					.createProductCategory(name);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (categoryProduct != null) {
			Toast.makeText(mContext,
					categoryProduct.getName() + " Creado Satisfactoriamente.",
					Toast.LENGTH_SHORT).show();
			// informar de cambios a lista de ingredientes
			productsCategories.add(categoryProduct);
		} else {
			Toast.makeText(mContext,
					"Hubo Errores Creando el Ingrediente '" + name + "'.",
					Toast.LENGTH_SHORT).show();
			// informar de cambios a lista de ingredientes
		}

	}

	@Override
	public void OnAddIngredientCategorySave(String name) {
		// TODO Auto-generated method stub
		IngredientCategory ingredientProduct = null;
		try {
			ingredientProduct = ingredientCategoryDatasource
					.createIngredientCategory(name);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ingredientProduct != null) {
			Toast.makeText(
					mContext,
					ingredientProduct.getName() + " Creado Satisfactoriamente.",
					Toast.LENGTH_SHORT).show();
			// informar de cambios a lista de ingredientes
			ingredientCategories.add(ingredientProduct);

		} else {
			Toast.makeText(mContext,
					"Hubo Errores Creando el Ingrediente '" + name + "'.",
					Toast.LENGTH_SHORT).show();
			// informar de cambios a lista de ingredientes
		}
	}

	@Override
	public void OnIngredientCategorySelected(long id) {
		// TODO Auto-generated method stub

		// tomar id de la categoria, buscar todos los ingredentes y populate la
		// gridview

	}

	@Override
	public void OnHomeObjectFragmentCreated(View v) {
		// TODO Auto-generated method stub
		LinearLayout ll = (LinearLayout) v
				.findViewById(R.id.ingredient_categories_buttons);
		ll.removeAllViews();
		String values[] = { "Combos", "Productos", "Ingredientes", "+Vendido",
				"Nuevo" };

		for (int i = 0; i < values.length; i++) {

			Button btnTag = (Button) getLayoutInflater().inflate(
					R.layout.template_button, null);
			// btnTag.setLayoutParams(new
			// LayoutParams(LayoutParams.WRAP_CONTENT,
			// LayoutParams.WRAP_CONTENT));
			// btnTag.setBackgroundColor(R.drawable.blue_button);
			btnTag.setText((values[i]));
			btnTag.setId(i);
			btnTag.setOnClickListener(typeButtonClickListener);
			ll.addView(btnTag);

		}
		itemsView = (GridView) v.findViewById(R.id.gridview);
		statusText = (TextView) v.findViewById(R.id.group_owner);
		deviceText = (TextView) v.findViewById(R.id.device_info);
		saleValue = (TextView) v.findViewById(R.id.sale_info);
		updateSaleValue();
		pagarButton = (Button) v.findViewById(R.id.pay_register);
		guardarButton = (Button) v.findViewById(R.id.save_register);
		pagarButton.setOnClickListener(payListener);
		guardarButton.setOnClickListener(saveListener);
		registerList = new ArrayList<Registrable>();
		gridAdapter = new ImageAdapter(mContext, registerList, inflater,
				gridButtonListener);
		setGridContent(gridAdapter, comboList);

		// Tomar la tabla de la izquierda del home view
		table = (TableLayout) v.findViewById(R.id.my_table);
		makeTable("NA");

	}

	@Override
	public void OnSalesObjectFragmentCreated(View v) {

		Button closeBtn = (Button) v.findViewById(R.id.close_turn);
		Button editBtn = (Button) v.findViewById(R.id.edit_turn);
		Button deleteBtn = (Button) v.findViewById(R.id.delete_turn);
		ordersList = (ListView) v.findViewById(R.id.turn_list);
		sale_name = (TextView) v.findViewById(R.id.sale_name);
		sale_details = (TextView) v.findViewById(R.id.sale_details);

		closeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (currentSelectedPosition == -1)
					return;
				long saveDate = System.currentTimeMillis();
				LinkedHashMap<Registrable, Integer> currentSale = cookingOrders
						.get(currentSelectedPosition);

				Sale createdSale = saleDataSource.createSale(saveDate,
						cookingOrdersMethods.get(currentSale),
						cookingOrdersReceived.get(currentSale));
				if (createdSale != null) {
					Iterator<Entry<Registrable, Integer>> it = currentSale
							.entrySet().iterator();

					while (it.hasNext()) {
						Map.Entry<Registrable, Integer> pair = (Map.Entry<Registrable, Integer>) it
								.next();
						createdSale.saveItem(mContext, pair.getKey().type,
								pair.getKey().id, pair.getValue());

						// Log.d("INGREDIENTES","INGREDIENTE: "+ingrediente.getKey().getName()+" "+ingrediente.getValue());
					}
					cookingOrders.remove(currentSelectedPosition);
					cookingOrdersMethods.remove(currentSale);
					cookingOrdersReceived.remove(currentSale);
					cookingOrdersTimes.remove(currentSale);
					currentSelectedPosition = -1;
					sendCommandMessage(DELETE_ALL_COMMAND);
					List<Long> items = new ArrayList<Long>();
					for (LinkedHashMap<Registrable, Integer> current : cookingOrders) {
						items.add(cookingOrdersTimes.get(current));
					}

					cookingAdapter = new SaleAdapter(mContext, items, inflater);
					ordersList.setAdapter(cookingAdapter);
					salesFromToday = saleDataSource
							.getAllSalesWithin(init, end);

					// ordersList.setOnItemClickListener(orderListListener);
					// makeTable("NA");
					sale_name.setText("Venta Guardada con �xito");
					sale_details
							.setText("Selecciona otro elemento para ver detalles.");
					updateSaleValue();

				} else {
					Toast.makeText(mContext, "Error al Guardar la venta",
							Toast.LENGTH_LONG).show();
				}

			}
		});

		editBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (currentSelectedPosition == -1)
					return;
				currentOrder = cookingOrders.get(currentSelectedPosition);
				LinkedHashMap<Registrable, Integer> currentSale = cookingOrders
						.get(currentSelectedPosition);
				cookingOrders.remove(currentSelectedPosition);
				cookingOrdersMethods.remove(currentSale);
				cookingOrdersReceived.remove(currentSale);
				cookingOrdersTimes.remove(currentSale);
				currentSelectedPosition = -1;
				sendCommandMessage(DELETE_ALL_COMMAND);
				List<Long> items = new ArrayList<Long>();
				for (LinkedHashMap<Registrable, Integer> current : cookingOrders) {
					items.add(cookingOrdersTimes.get(current));
				}

				cookingAdapter = new SaleAdapter(mContext, items, inflater);
				ordersList.setAdapter(cookingAdapter);
				// ordersList.setOnItemClickListener(orderListListener);
				makeTable("NA");
				sale_name.setText("Nada Seleccionado");
				sale_details.setText("No hay Detalles.");
				mViewPager.setCurrentItem(0);

			}
		});

		deleteBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (currentSelectedPosition == -1)
					return;

				LinkedHashMap<Registrable, Integer> currentSale = cookingOrders
						.get(currentSelectedPosition);
				cookingOrders.remove(currentSelectedPosition);
				cookingOrdersMethods.remove(currentSale);
				cookingOrdersReceived.remove(currentSale);
				cookingOrdersTimes.remove(currentSale);
				currentSelectedPosition = -1;

				List<Long> items = new ArrayList<Long>();
				for (LinkedHashMap<Registrable, Integer> current : cookingOrders) {
					items.add(cookingOrdersTimes.get(current));
				}

				cookingAdapter = new SaleAdapter(mContext, items, inflater);
				ordersList.setAdapter(cookingAdapter);
				sale_name.setText("Pedido Eliminado");
				sale_details.setText("Selecciona otro para ver Detalles.");

				sendCommandMessage(DELETE_ALL_COMMAND);
				// sendCommandMessage(SEND_ALL_COMMAND);

			}
		});

		List<Long> items = new ArrayList<Long>();
		for (LinkedHashMap<Registrable, Integer> current : cookingOrders) {
			items.add(cookingOrdersTimes.get(current));
		}

		cookingAdapter = new SaleAdapter(mContext, items, inflater);
		ordersList.setAdapter(cookingAdapter);
		ordersList.setOnItemClickListener(orderListListener);

	}

	@Override
	public void OnDailyObjectFragmentCreated(View v) {
		// Tomar la tabla de la izquierda del daily view
		dailyTable = (TableLayout) v.findViewById(R.id.daily_my_table);
		todayTableBtn = (Button) v.findViewById(R.id.daily_today);
		allTableBtn = (Button) v.findViewById(R.id.daily_all);
		todayTableBtn.setOnClickListener(tableTypeListener);
		allTableBtn.setOnClickListener(tableTypeListener);
		// makeDailyTable(TABLE_TYPE_TODAY);

	}

	@Override
	public void OnInventoryObjectFragmentCreated(View v) {
		// llenar el spinner con los ingredientes disponibles
		inventoryTable = (TableLayout) v.findViewById(R.id.inventory_my_table);
		List<Ingredient> ingres = ingredientDatasource.getAllIngredient();
		Spinner ingSpin = (Spinner) v
				.findViewById(R.id.inventory_ingredient_spinner);
		List<String> nombres = new ArrayList<String>();
		String nombreActual = "";
		for (Ingredient current : ingres) {
			String name = current.getName().trim().toLowerCase();
			if (!nombreActual.equals(name)) {
				nombreActual = name;
				nombres.add(nombreActual);

			}
		}
		ArrayAdapter<String> adapt = new ArrayAdapter<String>(mContext,
				android.R.layout.simple_list_item_single_choice, nombres);
		ingSpin.setAdapter(adapt);
		ingSpin.setOnItemSelectedListener(ingSpinListener);

	}

	@Override
	public void OnNest5ReadObjectFragmentCreated(View v) {
		// TODO Auto-generated method stub
		readCardBtn = (Button) v.findViewById(R.id.read_magnetic_card);
		readCardBtn.setOnClickListener(readMagneticCardListener);

		

	}

	private OnClickListener readMagneticCardListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			/* Check the reset volume. */
			if (checkResetVolume()) {

				mResetProgressDialog.setMessage("Preparando Lector de Tarjetas...");
				mResetProgressDialog.setCancelable(false);
				mResetProgressDialog.setIndeterminate(true);

				mResetProgressDialog.show();
				mReader.reset();
			}

		}
	};

	
	//lectira manual de usuario, es decir con correo electronico, esto es un metodo que viene del fragment de nest5ead
	@Override
	public void OnManualReadPressed(String email) {
		mResetProgressDialog.setMessage("Sellando Tarjeta...");
		mResetProgressDialog.setCancelable(false);
		mResetProgressDialog.setIndeterminate(true);
		mResetProgressDialog.show();
		SharedPreferences prefs = Util.getSharedPreferences(mContext);
		
		restService = new RestService(recievePromoandUserHandler, mContext,
				 Setup.PROD_URL+"/company/initManualStamp");
				 restService.addParam("company", prefs.getString(Setup.COMPANY_ID, "0"));
				 restService.addParam("email", email);
				 restService.setCredentials("apiadmin", Setup.apiKey);
				 try {
				 restService.execute(RestService.POST);} catch (Exception e) {
				 e.printStackTrace(); 
				 Log.i("MISPRUEBAS","Error empezando request");}
		
	}


	private OnItemSelectedListener ingSpinListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> adapter, View arg1,
				int position, long arg3) {

			if (adapter.getSelectedItem().toString() != null
					&& !adapter.getSelectedItem().toString().isEmpty()) {
				List<Ingredient> ingredientes = ingredientDatasource
						.getAIngredientByName(adapter.getSelectedItem()
								.toString());
				makeInventoryTable(ingredientes);
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}
	};

	private OnClickListener tableTypeListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.daily_all:
				saleList = saleDataSource.getAllSales();
				makeDailyTable(TABLE_TYPE_ALL);
				break;
			case R.id.daily_today:
				makeDailyTable(TABLE_TYPE_TODAY);
				break;
			}

		}
	};

	private OnClickListener typeButtonClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// Toast.makeText(mContext, String.valueOf(v.getId()),
			// Toast.LENGTH_LONG).show();
			switch (v.getId()) {
			case 0:

				// traer todos los combos y ponerlos en el grid
				gridAdapter = new ImageAdapter(mContext, comboList, inflater,
						gridButtonListener);
				setGridContent(gridAdapter, comboList);
				break;
			case 1:
				// traer todos los productos y ponerlos en el grid
				// List<Product> products = productDatasource.getAllProduct();
				// I need to arrange something like this: an object where type,
				// name, price

				gridAdapter = new ImageAdapter(mContext, productList, inflater,
						gridButtonListener);
				setGridContent(gridAdapter, productList);

				// Toast.makeText(mContext,
				// "Total de productos: "+String.valueOf(products.size()),
				// Toast.LENGTH_LONG).show();
				break;
			case 2:
				gridAdapter = new ImageAdapter(mContext, ingredientList,
						inflater, gridButtonListener);
				;
				setGridContent(gridAdapter, ingredientList);
				break;
			case 3:
				break;
			}

		}
	};

	OnClickListener payListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// Toast.makeText(mContext, "Hola", Toast.LENGTH_LONG).show();
			showPaymentFormDialog();

		}
	};

	OnClickListener saveListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// Toast.makeText(mContext, "Hola", Toast.LENGTH_LONG).show();
			// showPaymentFormDialog();
			if (currentList.size() == 0)
				return;
			String name = "Sin Nombre";

			for (int i = frases.length; i > 0; i--) {
				shufflePhrases();
				name = frases[0];
				if (!savedOrders.containsKey(name)) {
					break;

				}

			}

			savedOrders.put(name, currentOrder);
			currentOrder.clear();
			makeTable("NA");
			Toast.makeText(mContext, "Venta Guardada con nombre: " + name,
					Toast.LENGTH_SHORT).show();

		}
	};

	private void setGridContent(ImageAdapter adapter,
			final List<Registrable> list) {
		currentList = list;
		itemsView.setAdapter(adapter);

		itemsView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// Toast.makeText(mContext, "" +list.get(position).price ,
				// Toast.LENGTH_SHORT).show();
				if (currentOrder.containsKey(list.get(position))) {
					currentOrder.put(list.get(position),
							currentOrder.get(list.get(position)) + 1);

				} else {
					currentOrder.put(list.get(position), 1);
				}

				// Integer a = currentOrder.get(list.get(position));
				// Toast.makeText(mContext, " "+a, Toast.LENGTH_LONG).show();
				makeTable(list.get(position).name);

			}
		});
		itemsView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int position, long id) {
				Registrable element = list.get(position);
				switch (element.type) {
				case Registrable.TYPE_INGREDIENT:
					Ingredient ingrediente = ingredientDatasource
							.getIngredient(element.id);
					editItemId = element.id;
					editingItemPositionInRegistrable = position;
					android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
					AddIngredientForm addingredientDialog = new AddIngredientForm(
							ingredientCategories, taxes, units, ingrediente);
					addingredientDialog.show(fm, "fragment_edit_name");
					break;
				case Registrable.TYPE_PRODUCT:
					Product producto = productDatasource.getProduct(element.id);
					break;
				case Registrable.TYPE_COMBO:
					Combo combo = comboDatasource.getCombo(element.id);
					break;

				}
				return true;
			}
		});

	}

	OnClickListener gridButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int position = v.getId();
			// Toast.makeText(mContext, String.valueOf(v.getId()),
			// Toast.LENGTH_LONG).show();

			if (currentOrder.containsKey(currentList.get(position))) {
				currentOrder.put(currentList.get(position),
						currentOrder.get(currentList.get(position)) + 1);

			} else {
				currentOrder.put(currentList.get(position), 1);
			}

			// Integer a = currentOrder.get(list.get(position));
			// Toast.makeText(mContext, " "+a, Toast.LENGTH_LONG).show();
			makeTable(currentList.get(position).name);

		}
	};

	OnClickListener removeClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int position = v.getId();

			Registrable selected = inTableRegistrable.get(position);
			// Toast.makeText(mContext, selected.name,
			// Toast.LENGTH_LONG).show();
			int qty = currentOrder.get(selected);
			if (qty - 1 == 0) {
				currentOrder.remove(selected);
				inTableRegistrable.remove(position);
			} else {
				currentOrder.put(selected, currentOrder.get(selected) - 1);
			}

			makeTable(selected.name);

		}
	};

	private void makeTable(String name) {
		table.removeAllViews();
		Iterator<Entry<Registrable, Integer>> it = currentOrder.entrySet()
				.iterator();
		int i = 0;
		while (it.hasNext()) {
			LinkedHashMap.Entry<Registrable, Integer> pairs = (LinkedHashMap.Entry<Registrable, Integer>) it
					.next();
			// System.out.println(pairs.getKey() + " = " + pairs.getValue());
			// it.remove(); // avoids a ConcurrentModificationException
			TableRow tr = (TableRow) getLayoutInflater().inflate(
					R.layout.table_row, null);

			TextView tItem = (TextView) tr.findViewById(R.id.cell_item);
			TextView tQty = (TextView) tr.findViewById(R.id.cell_qty);
			TextView tPrice = (TextView) tr.findViewById(R.id.cell_price);
			ImageButton rmvBtn = (ImageButton) tr
					.findViewById(R.id.cell_remove_button);

			tItem.setText(pairs.getKey().name);
			tQty.setText(String.valueOf(pairs.getValue()));
			tPrice.setText(String.valueOf(pairs.getKey().price));
			if (name == pairs.getKey().name) {
				tr.setBackgroundColor(Color.parseColor("#55222222"));
			}
			rmvBtn.setOnClickListener(removeClickListener);
			rmvBtn.setId(i);
			table.addView(tr);

			// Draw separator
			TextView tv = new TextView(mContext);
			tv.setBackgroundColor(Color.parseColor("#80808080"));
			tv.setHeight(2);
			table.addView(tv);
			inTableRegistrable.add(i, pairs.getKey());
			i++;

		}

	}

	private void makeDailyTable(int TABLE_TYPE) {
		dailyTable.removeAllViews();
		Double total = 0.0;
		DecimalFormat dec = new DecimalFormat("$###,###,###");
		TextView tv = new TextView(mContext);
		tv.setBackgroundColor(Color.parseColor("#80808080"));
		tv.setHeight(2);
		TableRow tr1 = (TableRow) getLayoutInflater().inflate(
				R.layout.daily_table_row, null);
		TextView tDate1 = (TextView) tr1.findViewById(R.id.cell_date);
		TextView tItem1 = (TextView) tr1.findViewById(R.id.cell_item);
		TextView tAccount1 = (TextView) tr1.findViewById(R.id.cell_account);
		TextView tVal1 = (TextView) tr1.findViewById(R.id.cell_value);
		TextView tTot1 = (TextView) tr1.findViewById(R.id.cell_total);
		tDate1.setText("FECHA");
		tAccount1.setText("CUENTA");
		tItem1.setText("ITEM");
		tVal1.setText("VALOR");
		tTot1.setText("TOTAL");
		tr1.setBackgroundColor(Color.CYAN);
		dailyTable.addView(tr1);
		dailyTable.addView(tv);

		List<Sale> usingSales = salesFromToday;

		switch (TABLE_TYPE) {
		case TABLE_TYPE_TODAY:
			usingSales = salesFromToday;
			break;
		case TABLE_TYPE_ALL:
			usingSales = saleList;
			break;
		}

		for (Sale currentSale : usingSales) {

			double totalLocal = 0.0;
			LinkedHashMap<Combo, Double> combos = currentSale.getCombos();
			LinkedHashMap<Product, Double> products = currentSale.getProducts();
			LinkedHashMap<Ingredient, Double> ingredients = currentSale
					.getIngredients();
			Iterator<Entry<Combo, Double>> it = combos.entrySet().iterator();
			Calendar date = Calendar.getInstance();
			date.setTimeInMillis(currentSale.getDate());
			String fecha = date.get(Calendar.MONTH) + "/"
					+ date.get(Calendar.DAY_OF_MONTH) + "/"
					+ date.get(Calendar.YEAR) + "\n"
					+ date.get(Calendar.HOUR_OF_DAY) + ":"
					+ date.get(Calendar.MINUTE) + ":"
					+ date.get(Calendar.SECOND);
			String account = currentSale.getPaymentMethod();
			while (it.hasNext()) {
				TableRow tr = (TableRow) getLayoutInflater().inflate(
						R.layout.daily_table_row, null);
				TextView tDate = (TextView) tr.findViewById(R.id.cell_date);
				TextView tItem = (TextView) tr.findViewById(R.id.cell_item);
				TextView tAccount = (TextView) tr
						.findViewById(R.id.cell_account);
				TextView tVal = (TextView) tr.findViewById(R.id.cell_value);
				TextView tTot = (TextView) tr.findViewById(R.id.cell_total);
				Map.Entry<Combo, Double> pair = (Map.Entry<Combo, Double>) it
						.next();
				Double value = pair.getValue() * pair.getKey().getPrice();

				tDate.setText(fecha);
				tAccount.setText(account);
				tItem.setText(pair.getKey().getName() + " en Combo");
				tVal.setText(dec.format(value));
				tTot.setText("----");
				total += value;
				totalLocal += value;
				dailyTable.addView(tr);

				// Log.d("INGREDIENTES","INGREDIENTE: "+ingrediente.getKey().getName()+" "+ingrediente.getValue());
			}

			Iterator<Entry<Product, Double>> it2 = products.entrySet()
					.iterator();

			while (it2.hasNext()) {
				Map.Entry<Product, Double> pair = (Map.Entry<Product, Double>) it2
						.next();
				TableRow tr = (TableRow) getLayoutInflater().inflate(
						R.layout.daily_table_row, null);
				TextView tDate = (TextView) tr.findViewById(R.id.cell_date);
				TextView tItem = (TextView) tr.findViewById(R.id.cell_item);
				TextView tAccount = (TextView) tr
						.findViewById(R.id.cell_account);
				TextView tVal = (TextView) tr.findViewById(R.id.cell_value);
				TextView tTot = (TextView) tr.findViewById(R.id.cell_total);

				Double value = pair.getValue() * pair.getKey().getPrice();

				tDate.setText(fecha);
				tAccount.setText(account);
				tItem.setText(pair.getKey().getName());
				tVal.setText(dec.format(value));
				tTot.setText("----");
				total += value;
				totalLocal += value;
				dailyTable.addView(tr);

				// Log.d("INGREDIENTES","INGREDIENTE: "+ingrediente.getKey().getName()+" "+ingrediente.getValue());
			}

			Iterator<Entry<Ingredient, Double>> it3 = ingredients.entrySet()
					.iterator();

			while (it3.hasNext()) {
				Map.Entry<Ingredient, Double> pair = (Map.Entry<Ingredient, Double>) it3
						.next();
				TableRow tr = (TableRow) getLayoutInflater().inflate(
						R.layout.daily_table_row, null);
				TextView tDate = (TextView) tr.findViewById(R.id.cell_date);
				TextView tItem = (TextView) tr.findViewById(R.id.cell_item);
				TextView tAccount = (TextView) tr
						.findViewById(R.id.cell_account);
				TextView tVal = (TextView) tr.findViewById(R.id.cell_value);
				TextView tTot = (TextView) tr.findViewById(R.id.cell_total);

				Double value = pair.getValue() * pair.getKey().getPrice();

				tDate.setText(fecha);
				tAccount.setText(account);
				tItem.setText(pair.getKey().getName());
				tVal.setText(dec.format(value));
				tTot.setText("----");
				total += value;
				totalLocal += value;
				dailyTable.addView(tr);

				// Log.d("INGREDIENTES","INGREDIENTE: "+ingrediente.getKey().getName()+" "+ingrediente.getValue());
			}

			TableRow tr = (TableRow) getLayoutInflater().inflate(
					R.layout.daily_table_row, null);
			TextView tDate = (TextView) tr.findViewById(R.id.cell_date);
			TextView tItem = (TextView) tr.findViewById(R.id.cell_item);
			TextView tAccount = (TextView) tr.findViewById(R.id.cell_account);
			TextView tVal = (TextView) tr.findViewById(R.id.cell_value);
			TextView tTot = (TextView) tr.findViewById(R.id.cell_total);

			tDate.setText(fecha);
			tAccount.setText("-------");
			tItem.setText("Ingreso por Ventas");
			tVal.setText("----");
			tTot.setText(dec.format(totalLocal));
			tr.setBackgroundColor(Color.LTGRAY);
			dailyTable.addView(tr);
			TableRow tr2 = (TableRow) getLayoutInflater().inflate(
					R.layout.daily_table_row, null);
			TextView tDate2 = (TextView) tr2.findViewById(R.id.cell_date);
			TextView tItem2 = (TextView) tr2.findViewById(R.id.cell_item);
			TextView tAccount2 = (TextView) tr2.findViewById(R.id.cell_account);
			TextView tVal2 = (TextView) tr2.findViewById(R.id.cell_value);
			TextView tTot2 = (TextView) tr2.findViewById(R.id.cell_total);

			tDate2.setText(fecha);
			tAccount2.setText("-------");
			tItem2.setText("Acumulado por Ventas");
			tVal2.setText("----");
			tTot2.setText(dec.format(total));
			tr2.setBackgroundColor(Color.GRAY);
			dailyTable.addView(tr2);

		}

	}

	private void makeInventoryTable(List<Ingredient> ingredients) {
		inventoryTable.removeAllViews();
		Double total = 0.0;
		DecimalFormat dec = new DecimalFormat("$###,###,###");
		TextView tv = new TextView(mContext);
		tv.setBackgroundColor(Color.parseColor("#80808080"));
		tv.setHeight(2);
		TableRow tr1 = (TableRow) getLayoutInflater().inflate(
				R.layout.inventory_table_row, null);
		TextView tDate1 = (TextView) tr1.findViewById(R.id.cell_date);
		TextView tItem1 = (TextView) tr1.findViewById(R.id.cell_type);
		TextView tAccount1 = (TextView) tr1.findViewById(R.id.cell_qty);
		TextView tVal1 = (TextView) tr1.findViewById(R.id.cell_unit_value);
		TextView tTot1 = (TextView) tr1.findViewById(R.id.cell_total_value);
		TextView tTotTot1 = (TextView) tr1.findViewById(R.id.cell_total_total);
		tDate1.setText("FECHA");
		tAccount1.setText("CANTIDAD");
		tItem1.setText("TIPO");
		tVal1.setText("VALOR\nUNIDAD");
		tTot1.setText("TOTAL\nITEM");
		tTotTot1.setText("TOTAL");
		tr1.setBackgroundColor(Color.CYAN);
		inventoryTable.addView(tr1);
		inventoryTable.addView(tv);

		for (Ingredient current : ingredients) {

			Calendar date = Calendar.getInstance();
			date.setTimeInMillis(current.getDate());
			String fecha = date.get(Calendar.MONTH) + "/"
					+ date.get(Calendar.DAY_OF_MONTH) + "/"
					+ date.get(Calendar.YEAR) + "\n"
					+ date.get(Calendar.HOUR_OF_DAY) + ":"
					+ date.get(Calendar.MINUTE) + ":"
					+ date.get(Calendar.SECOND);

			Double localVal = current.getQty() * current.getCostPerUnit();
			total += localVal;

			TableRow tr = (TableRow) getLayoutInflater().inflate(
					R.layout.inventory_table_row, null);
			TextView tDate = (TextView) tr.findViewById(R.id.cell_date);
			TextView tType = (TextView) tr.findViewById(R.id.cell_type);
			TextView tQty = (TextView) tr.findViewById(R.id.cell_qty);
			TextView tVal = (TextView) tr.findViewById(R.id.cell_unit_value);
			TextView tTot = (TextView) tr.findViewById(R.id.cell_total_value);
			TextView tTotTot = (TextView) tr
					.findViewById(R.id.cell_total_total);

			tDate.setText(fecha);
			tType.setText("E");
			tQty.setText(String.valueOf(current.getQty()));
			tVal.setText(dec.format(current.getCostPerUnit()));
			tTot.setText(dec.format(localVal));
			tTotTot.setText(" ----- ");

			inventoryTable.addView(tr);

		}

		TableRow tr2 = (TableRow) getLayoutInflater().inflate(
				R.layout.inventory_table_row, null);
		TextView tDate2 = (TextView) tr2.findViewById(R.id.cell_date);
		TextView tType2 = (TextView) tr2.findViewById(R.id.cell_type);
		TextView tQty2 = (TextView) tr2.findViewById(R.id.cell_qty);
		TextView tVal2 = (TextView) tr2.findViewById(R.id.cell_unit_value);
		TextView tTot2 = (TextView) tr2.findViewById(R.id.cell_total_value);
		TextView tTotTot2 = (TextView) tr2.findViewById(R.id.cell_total_total);

		tDate2.setText("------");
		tType2.setText("-----");
		tQty2.setText("-----");
		tVal2.setText("-----");
		tTot2.setText("TOTAL GENERAL");
		tTotTot2.setText(dec.format(total));
		tr2.setBackgroundColor(Color.LTGRAY);
		inventoryTable.addView(tr2);

	}

	public static double roundWhole(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#");

		return Double.valueOf(twoDForm.format(d));
	}

	@Override
	public void OnAddProductSave(String name, ProductCategory category,
			double cost, double price, Tax tax) {
		// TODO Auto-generated method stub
		if ((name == null) || (category == null)) {
			showAddProductCategoryFormDialog();
		} else {
			// guardar producto
			// Toast.makeText(mContext, category.getName(),
			// Toast.LENGTH_LONG).show();
			Product product = null;
			try {
				product = productDatasource.createProduct(name, category, 0,
						cost, price, tax);

			} catch (Exception e) {
				e.printStackTrace();
			}
			if (product != null) {

				productList.add(new Registrable(product));
				Toast.makeText(mContext,
						product.getName() + " Creado Satisfactoriamente.",
						Toast.LENGTH_SHORT).show();
				showCreateProductFormDialog(product);
				// informar de cambios a lista de ingredientes
			} else {
				Toast.makeText(mContext,
						"Hubo Errores Creando el Producto '" + name + "'.",
						Toast.LENGTH_SHORT).show();
				// informar de cambios a lista de ingredientes
			}
		}

	}

	@Override
	public void OnProductCreated(LinkedHashMap<Ingredient, Double> ingredents,
			Product product) {
		// TODO Auto-generated method stub

		/*
		 * for(Ingredient ingrediente : ingredents) {
		 * product.addIngredient(mContext, ingrediente, 100.0);
		 * product.refreshIngredients(mContext); productos.add(product); }
		 */
		Iterator<Entry<Ingredient, Double>> it = ingredents.entrySet()
				.iterator();

		while (it.hasNext()) {
			Map.Entry<Ingredient, Double> ingredientPair = (Map.Entry<Ingredient, Double>) it
					.next();
			product.addIngredient(mContext, ingredientPair.getKey(),
					ingredientPair.getValue());

			// Log.d("INGREDIENTES","INGREDIENTE: "+ingrediente.getKey().getName()+" "+ingrediente.getValue());
		}
		product.refreshIngredients(mContext);
		productos.add(product);

	}

	@Override
	public void OnComboCreated(LinkedHashMap<Ingredient, Double> ingredents,
			LinkedHashMap<Product, Double> products, String name, Double cost,
			Double price, Tax tax) {
		Combo combo = comboDatasource.createCombo(name, 0, cost, price, tax);
		Iterator<Entry<Ingredient, Double>> it = ingredents.entrySet()
				.iterator();

		while (it.hasNext()) {
			Map.Entry<Ingredient, Double> ingredientPair = (Map.Entry<Ingredient, Double>) it
					.next();
			combo.addIngredient(mContext, ingredientPair.getKey(),
					ingredientPair.getValue());

			// Log.d("INGREDIENTES","INGREDIENTE: "+ingrediente.getKey().getName()+" "+ingrediente.getValue());
		}
		Iterator<Entry<Product, Double>> pt = products.entrySet().iterator();

		while (pt.hasNext()) {
			Map.Entry<Product, Double> productPair = (Map.Entry<Product, Double>) pt
					.next();
			combo.addProduct(mContext, productPair.getKey(),
					productPair.getValue());

		}
		combo.refreshIngredients(mContext);
		combo.refreshProducts(mContext);
		combos.add(combo);
		comboList.add(new Registrable(combo));

	}

	@Override
	public void OnPayClicked(String method, double value) {

		// al guardar lo que hace es que guarda un objeto Sale con fecha, metodo
		// de pago y valor recibido.
		// despues toma currentOrder y dic saveItem(Context mContext,int type,
		// long item_id, double qty) para cada uno

		// al recuperar un sale se hace

		// price = 0.0;
		Date date = new Date();
		String fecha = DateFormat.getDateFormat(Initialactivity.this).format(
				date);

		// imprimir, conectar por wifi y enviar el texto arregladito a la app de
		// puente

		int lines = 0;
		StringBuilder factura = new StringBuilder();
		factura.append("MR. PASTOR COMIDA\r\nR�PIDA MEXICANA" + "\r\n");
		lines++;
		lines++;
		lines++;
		factura.append(fecha);
		lines++;
		lines++;
		lines++;
		factura.append("\r\n");
		factura.append("    Item       Cantidad   Precio\r\n");
		lines++;
		String names[] = { "Taco Al Pastor Con Cebolla", "Chimchanga",
				"Combo 1 con Chimmichanga" };
		String qties[] = { "1", "5", "15" };
		String prices[] = { "13900", "600", "4500" };
		int j = 0;
		Iterator<Entry<Registrable, Integer>> it = currentOrder.entrySet()
				.iterator();
		int i = 0;
		StringBuilder comanda = new StringBuilder();
		// Log.d(TAG,String.valueOf(currentOrder.size()));
		LinkedHashMap<Registrable, Integer> currentObjects = new LinkedHashMap<Registrable, Integer>();

		while (it.hasNext()) {

			LinkedHashMap.Entry<Registrable, Integer> pairs = (LinkedHashMap.Entry<Registrable, Integer>) it
					.next();

			currentObjects.put(pairs.getKey(), pairs.getValue());

			String name = pairs.getKey().name;

			int longName = name.length();
			// Log.d("NUMEROS",String.valueOf(longName));
			int subLength = 14 - longName;
			// Log.d("NUMEROS",String.valueOf(subLength));

			if (subLength < 0)
				name = name.substring(0, 14);
			int espacios1 = 4;
			int espacios2 = 12;
			if (name.length() < 14)
				;
			{
				espacios1 += 14 - name.length();
			}

			factura.append(name);

			int qtyL = String.valueOf(pairs.getValue()).length();
			int priceL = String.valueOf(
					pairs.getKey().price + pairs.getKey().price
							* pairs.getKey().tax).length();
			espacios1 = espacios1 - qtyL < 1 ? espacios1 = 1 : espacios1 - qtyL;
			espacios2 = espacios2 - priceL < 1 ? espacios2 = 1 : espacios2
					- priceL;

			for (int k = 0; k < espacios1; k++) {
				factura.append(" ");
			}
			factura.append(pairs.getValue());

			for (int k = 0; k < espacios2; k++) {
				factura.append(" ");
				//
			}

			factura.append("$");
			factura.append(pairs.getKey().price + pairs.getKey().price
					* pairs.getKey().tax);
			factura.append("\r\n");

			// solo en pruebas
			j++;
			if (j == 3)
				j = 0;
			// factura.append("Taco al pastor   3      $112600\r\n");
			lines++;

			// Crear comanda para sistema de comandas
			// comanda.append("N5AT-1::Hola como estas\n"); //prueba de Comando
			// apra sacar un toast con lo que diga el comando
			comanda.append(pairs.getValue());
			comanda.append(" -----> ");
			comanda.append(name);
			comanda.append("\n");

		}
		long startTime = System.currentTimeMillis();

		cookingOrders.add(currentObjects);
		Log.d(TAG, currentOrder.toString());

		// cookingOrders.add(currentOrder);
		cookingOrdersMethods.put(currentObjects, method);
		cookingOrdersTimes.put(currentObjects, startTime);
		cookingOrdersReceived.put(currentObjects, value);

		List<Long> items = new ArrayList<Long>();
		for (LinkedHashMap<Registrable, Integer> current : cookingOrders) {
			items.add(cookingOrdersTimes.get(current));
		}

		cookingAdapter = new SaleAdapter(mContext, items, inflater);
		ordersList.setAdapter(cookingAdapter);
		ordersList.setOnItemClickListener(orderListListener);
		if (!isTimerRunning) {
			startTimer();
		}
		currentOrder.clear();
		makeTable("NA");

		lines++;
		lines++;

		factura.append("Gracias Por Comprar en\r\nMr. Pastor.\r\n");
		lines++;
		factura.append("Ingresa a www.mrpastor.com\r\n");
		lines++;
		factura.append("S�guenos en\r\n");
		lines++;
		factura.append("facebook/misterpastor\r\n");
		lines++;
		factura.append("twitter.com/comidasmrpastor\r\n");
		lines++;
		factura.append("Danos tus sugerencias y\r\n");
		lines++;
		factura.append("opiniones y recibe beneficios.\r\n");
		lines++;
		String send = factura.toString();
		String finalString = String.valueOf(lines) + "\r\n" + send;
		Log.d(TAG, finalString);

		// Enviar un string diferente que lleva la orden actual.
		new WiFiSend().execute(comanda.toString());// enviar el mensaje de
													// verdad
		// new WiFiSend().execute(factura.toString());
		/*
		 * StringBuilder factura = new StringBuilder();
		 * factura.append("MR. PASTOR COMIDA R�PIDA MEXICANA"+"\r\n\r\n\r\n");
		 * Date date = new Date(); String fecha =
		 * DateFormat.getDateFormat(mContext).format(date);
		 * 
		 * factura.append(fecha); factura.append("\r\n");
		 * factura.append("    Item       Cantidad      Precio\r\n");
		 * factura.append("Taco al pastor    3            $12.600\r\n");
		 * factura.append("Taco al pastor    3            $12.600\r\n");
		 * factura.append("Taco al pastor    3            $12.600\r\n");
		 * factura.append("Taco al pastor    3            $12.600\r\n");
		 * factura.append("Taco al pastor    3            $12.600\r\n");
		 * factura.append("Taco al pastor    3            $12.600\r\n");
		 * factura.append("Taco al pastor    3            $12.600\r\n");
		 * factura.append("Taco al pastor    3            $12.600\r\n");
		 * factura.append("Taco al pastor    3            $12.600\r\n");
		 * factura.append("Taco al pastor    3            $12.600\r\n");
		 * factura.append("Taco al pastor    3            $12.600\r\n");
		 * factura.append("\r\n\r\n Gracias Por Comprar en Mr. Pastor.\r\n");
		 * factura.append("Ingresa http://www.mrpastor.com\r\n");
		 * factura.append(
		 * "O S�guenos en facebook/misterpastor - Twiiter.com/comidasmrpastor");
		 */

	}

	private OnItemClickListener orderListListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int pos,
				long arg3) {
			currentSelectedPosition = pos;
			sale_name.setText("Venta #" + pos + " en Cola.");
			StringBuilder sb = new StringBuilder();
			LinkedHashMap<Registrable, Integer> order = cookingOrders.get(pos);

			// Log.d(TAG,cookingOrders.toString());

			Iterator<Entry<Registrable, Integer>> it = (cookingOrders.get(pos))
					.entrySet().iterator();
			// Log.d(TAG,String.valueOf(cookingOrders.size()));

			while (it.hasNext()) {

				LinkedHashMap.Entry<Registrable, Integer> pairs = (LinkedHashMap.Entry<Registrable, Integer>) it
						.next();
				sb.append(pairs.getValue());
				sb.append("\t");
				sb.append(pairs.getKey().name);
				sb.append("\n");

			}

			// Log.d(TAG,sb.toString());

			// sale_details.setMaxLines(cookingOrders.size());
			sale_details.setText(sb.toString());

		}
	};

	private void shufflePhrases() {
		Random rnd = new Random();
		for (int i = frases.length - 1; i >= 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			String a = frases[index];
			frases[index] = frases[i];
			frases[i] = a;
		}
	}

	private void sendCommandMessage(int msgCode) {

		switch (msgCode) {
		case DELETE_ALL_COMMAND:
			StringBuilder str = new StringBuilder();
			str.append("N5AT-2\n");
			new WiFiSend().execute(str.toString());

			// sigue a SEND_ALL para agregar los nuevos compras
		case SEND_ALL_COMMAND:
			for (LinkedHashMap<Registrable, Integer> currentSale : cookingOrders) {
				str = new StringBuilder();
				Iterator<Entry<Registrable, Integer>> it = currentSale
						.entrySet().iterator();

				while (it.hasNext()) {
					Map.Entry<Registrable, Integer> pair = (Map.Entry<Registrable, Integer>) it
							.next();
					str.append(pair.getValue());
					str.append(" -----> ");
					str.append(pair.getKey().name);
					str.append("\n");
				}
				new WiFiSend().execute(str.toString());
			}
			break;
		}

	}

	private class WiFiSend extends AsyncTask<String, Void, Void> {

		protected Void doInBackground(String... msg) {
			// TODO Auto-generated method stub
			try {
				socket = new Socket(connectedIp, 8100);
				wifiOutputStream = new DataOutputStream(
						socket.getOutputStream());
				wifiInputStream = new DataInputStream(socket.getInputStream());
				Log.d(TAG, msg[0]);
				wifiOutputStream.writeUTF(msg[0]);
				// textIn.setText(dataInputStream.readUTF());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (wifiOutputStream != null) {
					try {
						wifiOutputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (wifiInputStream != null) {
					try {
						wifiInputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			return null;
		}
	}

	@Override
	public void showDetails(WifiP2pDevice device) {
		Toast.makeText(mContext, "Conectando con: " + device.deviceName,
				Toast.LENGTH_SHORT).show();
		isConnecting = true;
		WifiP2pConfig config = new WifiP2pConfig();
		config.deviceAddress = device.deviceAddress;
		config.wps.setup = WpsInfo.PBC;
		statusText.setText("Conectando con: " + device.deviceName);

		connect(config);

	}

	@Override
	public void connect(WifiP2pConfig config) {
		manager.connect(channel, config, new ActionListener() {

			@Override
			public void onSuccess() {
				// WiFiDirectBroadcastReceiver will notify us. Ignore for now.
			}

			@Override
			public void onFailure(int reason) {
				Toast.makeText(Initialactivity.this,
						"La conexi�n fall�. Reint�ntalo de nuevo.",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void disconnect() {

		manager.removeGroup(channel, new ActionListener() {

			@Override
			public void onFailure(int reasonCode) {
				Log.d(TAG, "La desconexi�n fall�, la raz�n es:" + reasonCode);

			}

			@Override
			public void onSuccess() {
				// fragment.getView().setVisibility(View.GONE);
			}

		});
	}

	@Override
	public void cancelDisconnect() {

		/*
		 * A cancel abort request by user. Disconnect i.e. removeGroup if
		 * already connected. Else, request WifiP2pManager to abort the ongoing
		 * request
		 */

	}

	@Override
	public void onPeersAvailable(WifiP2pDeviceList peerList) {
		if (!isConnecting) {
			List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
			peers.clear();
			peers.addAll(peerList.getDeviceList());
			// ((WiFiPeerListAdapter) deviceAdapter).notifyDataSetChanged();
			if (peers.size() == 0) {
				Log.d(Initialactivity.TAG, "No devices found");
				return;
			} else {
				android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
				WifiDirectDialog wifiDirectdialog = new WifiDirectDialog(peers);
				wifiDirectdialog.show(fm, "fragment_edit_name");
			}
		}

	}

	@Override
	public void onChannelDisconnected() {
		// we will try once more
		if (manager != null && !retryChannel) {
			Toast.makeText(this,
					"Se perdi� la conexi�n con el canal, int�ntalo de nuevo.",
					Toast.LENGTH_LONG).show();
			// resetData();
			retryChannel = true;
			manager.initialize(this, getMainLooper(), this);
		} else {
			Toast.makeText(
					this,
					"�Error grave!. Se perdi� por completo la conexi�n con dispositivos. Reintenta Desactivando/Activando WifiDirect otra vez.",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onConnectionInfoAvailable(WifiP2pInfo info) {
		// The owner IP is now known.
		// TextView view = (TextView) findViewById(R.id.group_owner);
		Toast.makeText(mContext, "Conectado a un dispositivo",
				Toast.LENGTH_SHORT).show();
		/*
		 * statusText.setText("Group Owner: " + ((info.isGroupOwner == true) ?
		 * "Si" : "No"));
		 */
		deviceText.setText("Comandas conectadas.");
		isConnected = true;

		// InetAddress from WifiP2pInfo struct.
		// view = (TextView) findViewById(R.id.device_info);
		statusText.setText("IP: - " + info.groupOwnerAddress.getHostAddress());
		connectedIp = info.groupOwnerAddress.getHostAddress();

	}

	public void resetData() {
		if (deviceText != null) {
			isConnecting = false;
			isConnected = false;
			Toast.makeText(mContext, "Comandas Desconectadas",
					Toast.LENGTH_LONG).show();

			deviceText.setText("Desconectado.");
			statusText.setText("Desconectado.");
			connectedIp = null;
			try {
				playSound(mContext);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void playSound(Context context) throws IllegalArgumentException,
			SecurityException, IllegalStateException, IOException {
		Uri soundUri = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		MediaPlayer mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setDataSource(context, soundUri);
		final AudioManager audioManager = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
			mMediaPlayer.setLooping(false);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
		}
	}

	protected void startTimer() {
		isTimerRunning = true;
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				currentTime = System.currentTimeMillis(); // increase every sec
				mHandler.obtainMessage(1).sendToTarget();

			}
		}, 0, 60000);
	}

	public Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			// StopWatch.time.setText(formatIntoHHMMSS(elapsedTime)); //this is
			// the textview
			// Toast.makeText(mContext, " "+currentTime,
			// Toast.LENGTH_LONG).show();
			List<Long> items = new ArrayList<Long>();
			for (LinkedHashMap<Registrable, Integer> current : cookingOrders) {
				items.add(cookingOrdersTimes.get(current));
			}

			cookingAdapter = new SaleAdapter(mContext, items, inflater);
			ordersList.setAdapter(cookingAdapter);
		}
	};

	private void updateSaleValue() {
		Double total = 0.0;
		for (Sale currentSale : salesFromToday) {
			LinkedHashMap<Combo, Double> combos = currentSale.getCombos();
			LinkedHashMap<Product, Double> products = currentSale.getProducts();
			LinkedHashMap<Ingredient, Double> ingredients = currentSale
					.getIngredients();
			Iterator<Entry<Combo, Double>> it = combos.entrySet().iterator();

			while (it.hasNext()) {
				Map.Entry<Combo, Double> pair = (Map.Entry<Combo, Double>) it
						.next();
				total += (pair.getValue() * pair.getKey().getPrice());

				// Log.d("INGREDIENTES","INGREDIENTE: "+ingrediente.getKey().getName()+" "+ingrediente.getValue());
			}

			Iterator<Entry<Product, Double>> it2 = products.entrySet()
					.iterator();

			while (it2.hasNext()) {
				Map.Entry<Product, Double> pair = (Map.Entry<Product, Double>) it2
						.next();
				total += (pair.getValue() * pair.getKey().getPrice());

				// Log.d("INGREDIENTES","INGREDIENTE: "+ingrediente.getKey().getName()+" "+ingrediente.getValue());
			}

			Iterator<Entry<Ingredient, Double>> it3 = ingredients.entrySet()
					.iterator();

			while (it3.hasNext()) {
				Map.Entry<Ingredient, Double> pair = (Map.Entry<Ingredient, Double>) it3
						.next();
				total += (pair.getValue() * pair.getKey().getPrice());

				// Log.d("INGREDIENTES","INGREDIENTE: "+ingrediente.getKey().getName()+" "+ingrediente.getValue());
			}
			DecimalFormat dec = new DecimalFormat("$###,###,###");

			saleValue.setText("Ventas del D�a: " + dec.format(total));

		}
	}

	/**
	 * 
	 * FUNCIONES DE LECTOR DE TARJETAS MAGNETICAS ACR31 DE ADVANCE CARD SYSTEMS
	 * LTD ACS LTD
	 * 
	 * 
	 * 
	 * 
	 * Checks the reset volume.
	 * 
	 * @return true if current volume is equal to maximum volume.
	 */

	/* Set the reset complete callback. */

	private void showMessageDialog(String titleId, String messageId) {

		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

		builder.setMessage(messageId)
				.setTitle(titleId)
				.setPositiveButton("Aceptar",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});

		builder.show();
	}

	private boolean checkResetVolume() {

		boolean ret = true;

		int currentVolume = mAudioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);

		int maxVolume = mAudioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

		if (currentVolume < maxVolume) {

			showMessageDialog(
					"Atenci�n",
					"Para leer una tarjeta Nest5 debes subir el volumen de tu dispositivo al m�ximo.");
			ret = false;
		}

		return ret;
	}

	/**
	 * Converts the byte array to HEX string.
	 * 
	 * @param buffer
	 *            the buffer.
	 * @return the HEX string.
	 */
	private String toHexString(byte[] buffer) {

		String bufferString = "";

		for (int i = 0; i < buffer.length; i++) {

			String hexChar = Integer.toHexString(buffer[i] & 0xFF);
			if (hexChar.length() == 1) {
				hexChar = "0" + hexChar;
			}

			bufferString += hexChar.toUpperCase(Locale.US) + " ";
		}

		return bufferString;
	}

	private void checkLogin() {
		prefs = Util.getSharedPreferences(mContext);
		if (!prefs.getBoolean(Setup.LOGGED_IN, false)) {
			prefs.edit().putBoolean(Setup.LOGGED_IN, false)
					.putString(Setup.COMPANY_ID, "0")
					.putString(Setup.COMPANY_NAME, "N/A").commit();
			Intent inten = new Intent(mContext, LoginActivity.class);
			startActivity(inten);
		}
	}
	
	private void backUpDb(){

		

		mResetProgressDialog = new ProgressDialog(mContext);
		mResetProgressDialog.setMessage("Protegiendo Base de Datos");
		mResetProgressDialog.setCancelable(false);
		mResetProgressDialog.setIndeterminate(true);
		mResetProgressDialog.show();

		saveFileRecord();
		
		
	}
	
	//Amazon S3 Upload Images guarda el archivo en base de datos recibe nombre a usar para el archivo y sube a s3
	
	private void saveFileRecord(){
		SharedPreferences prefs = Util.getSharedPreferences(mContext);
		
		restService = new RestService(fileSavedHandler, mContext,
				 Setup.PROD_URL+"/company/saveDB");
				 restService.addParam("company", prefs.getString(Setup.COMPANY_ID, "0"));	 
				 restService.setCredentials("apiadmin", Setup.apiKey);
				 try {
				 restService.execute(RestService.POST);} catch (Exception e) {
				 e.printStackTrace(); 
				 Log.i("MISPRUEBAS","Error empezando request");}
	}
	private final Handler fileSavedHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			//mResetProgressDialog.dismiss();
			
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
				mResetProgressDialog.dismiss();
				Toast.makeText(mContext, "Error guardando registro de backup de base de datos", Toast.LENGTH_LONG).show();
			}

			if (respuesta != null) {
				Log.i("MISPRUEBAS","CON RESPUESTA");
				int status = 0;
				String name = "";
				
				try {
					status = respuesta.getInt("status");
					name = respuesta.getString("name");
					

				} catch (Exception e) {
					Log.i("MISPRUEBAS","ERROR COGER DATOS");
					e.printStackTrace();
					mResetProgressDialog.dismiss();
					Toast.makeText(mContext, "Error guardando registro de backup de base de datos", Toast.LENGTH_LONG).show();
				}
				// quitar loading
				
				
				if (status == 1) {
					Log.i("MISPRUEBAS","listo");
					//Abrir Nueva Activity porque esta registrado
					//Toast.makeText(mContext, "Datos guardados con �xito.", Toast.LENGTH_LONG).show();
					File file = DbExportImport.exportDb(name);
					UploadFileToS3 uploadTask = new UploadFileToS3();
					uploadTask.execute(file);
				} else {
					Log.i("MISPRUEBAS","noooo");
					mResetProgressDialog.dismiss();
					Toast.makeText(mContext, "Error guardando registro de backup de base de datos", Toast.LENGTH_LONG).show();
				}

			}
			else{
				mResetProgressDialog.dismiss();
				Toast.makeText(mContext, "Error guardando registro de backup de base de datos", Toast.LENGTH_LONG).show();
			}

		}
	};
	

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */

	
	public class UploadFileToS3 extends AsyncTask<File, Void, Boolean> {
		@Override
		protected Boolean doInBackground(File... params) {

			// TODO: attempt upload
	        String accessKey = "AKIAIIQ5AOSHXVIRUSBA";
	        String secretKey = "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";
	        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
	        AmazonS3 conn = new AmazonS3Client(credentials);
	        try{
	        	Log.i("MISPRUEBAS","subiendo archivo");
	        	conn.putObject("com.nest5.businessClient", params[0].getName(), params[0]);
	        }
	        catch(com.amazonaws.AmazonServiceException e){
	        	Log.i("MISPRUEBAS","exeption 1");
	        	e.printStackTrace();

	        	return false;

	        	
	        }
	        catch(com.amazonaws.AmazonClientException e){
	        	Log.i("MISPRUEBAS","exeption 2");
	        	e.printStackTrace();

	        	return false;

	        }
	       
	        
			

		

			// TODO: register the new account here.

			return true;
		}

		@Override
		protected void onPostExecute(final Boolean status) {
			mResetProgressDialog.dismiss();	
			if(status)
				Toast.makeText(mContext, "Datos guardados con �xito.", Toast.LENGTH_LONG).show();
			else
				Toast.makeText(mContext, "Error guardando el archivo en la nube, int�ntalo de nuevo por favor0..", Toast.LENGTH_LONG).show();
			Log.i("MISPRUEBAS","lelgo al final de la asynctask");
        	//mResetProgressDialog.dismiss();
        	//Guardar referencia a archivo y empresa en nest5.
        	

			
		}
		@Override
		protected void onCancelled() {
			
		}

		

		
		

		
	}
	

	/*Recibe datos de promocion y usuario al leer tarjeta magnetica, o al enviar email de usuario a API */
	private final Handler recievePromoandUserHandler = new Handler()
    {
    	
    	@Override
    	public void handleMessage(Message msg){
    		mResetProgressDialog.dismiss();
    		Promo[] promos = null;
    		User user= null;
    		JSONObject respuesta = null;
    		String mensaje = "Error de Comunicaci�n con Nest5, int�ntalo de nuevo por favor.";
    		int status = 0;
	    	try{
	    		respuesta = new JSONObject((String) msg.obj);
	    	}
	    	catch(Exception e){
	    		showMessageDialog("ERROR", mensaje);
	    	}
	    	if(respuesta != null){
	    		try{
	    			mensaje = respuesta.getString("message");
	    			status = respuesta.getInt("status");
	    		}
	    		catch(Exception e){
	    			showMessageDialog("ERROR", mensaje);
	    		}
	    		if(status == 1){
	    			try{
	    				Gson gson = new Gson();
	    				Gson gson2 = new Gson();
			    		promos = gson.fromJson((String) respuesta.getString("promos"), Promo[].class);		    		
			    		user = gson2.fromJson((String) respuesta.getString("user"), User.class);
	    			}catch(Exception e){
	    				showMessageDialog("ERROR", mensaje);
	    				e.printStackTrace();
	    			}
	    			//showMessageDialog("Listo", "Promo:" + promos[0].name+" Usuario: "+user.name);
	    			
	    			//Llenar lista con adaptador de array
	    			companyPromos = Arrays.asList(promos);
	    			currentUser = user;
	    			ListView list = (ListView) findViewById(R.id.manual_variable_list);
	    			
	    			ArrayAdapter<Promo> promociones = new ArrayAdapter<Promo>(mContext, android.R.layout.simple_list_item_1,promos){
	    				@Override
	    			    public View getView(int position, View convertView, ViewGroup parent) {
	    			        TextView textView = (TextView) super.getView(position, convertView, parent);
	    			        Promo obj = getItem(position);
	    			        String name = obj.name +" En: "+obj.store;
	    			        textView.setText(name);
	    			        return textView;
	    			    }
	    			};
	    			list.setAdapter(promociones);
	    			list.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int position, long arg3) {
							// TODO Auto-generated method stub
							//enviar empresa, usuario y promocion que sellara
							mResetProgressDialog = new ProgressDialog(mContext);
							mResetProgressDialog.setMessage("Sellando Tarjeta Nest5 de "+currentUser.name+"...");
							mResetProgressDialog.setCancelable(false);
							mResetProgressDialog.setIndeterminate(true);
							mResetProgressDialog.show();
							int promoid = companyPromos.get(position).id;
							int userid = currentUser.id;
							SharedPreferences prefs = Util.getSharedPreferences(mContext);
							restService = new RestService(recieveStampsAndCouponsUser, mContext,
									 Setup.PROD_URL+"/promo/beLucky");
									 
									 restService.addParam("code",Integer.toString(promoid));
									 restService.addParam("id", Integer.toString(userid));
									 restService.addParam("frombusiness", "claro");
									 restService.setCredentials("apiadmin", Setup.apiKey);
									 try {
									 restService.execute(RestService.POST);} catch (Exception e) {
									 e.printStackTrace(); 
									 Log.i("MISPRUEBAS","Error empezando request");}
							
						}
					});
	    			
	    			
	    		}
	    		else{
	    			showMessageDialog("ERROR", mensaje);
	    		}
	    		
	    		
	    	}
	    	else{
	    		showMessageDialog("ERROR", mensaje);
	    	}
	    	
	    	
	    	
	    	
    	}
    	
    };
    
    /*Recibe datos de promocion y usuario al leer tarjeta magnetica, o al enviar email de usuario a API */
	private final Handler recieveStampsAndCouponsUser = new Handler()
    {
    	
    	@Override
    	public void handleMessage(Message msg){
    		mResetProgressDialog.dismiss();
    		JSONObject respuesta = null;
    		String mensaje = "Error de Comunicaci�n con Nest5, int�ntalo de nuevo por favor.";
    		int status = 0;
    		int sellos = 0;
    		int coupones = 0;
	    	try{
	    		respuesta = new JSONObject((String) msg.obj);
	    	}
	    	catch(Exception e){
	    		Log.i("MISPRUEBAS","ERROR 0");
	    		showMessageDialog("ERROR", mensaje);
	    		
	    	}
	    	if(respuesta != null){
	    		try{
	    			
	    			status = respuesta.getInt("status");
	    			
	    			
	    		}
	    		catch(Exception e){
	    			Log.i("MISPRUEBAS","ERROR 1");
	    			showMessageDialog("ERROR", mensaje);
	    		}
	    		if(status == 1){
	    			try{
	    				sellos = respuesta.getInt("stamps");
	    				coupones = respuesta.getInt("coupons");
	    			}catch(Exception e){
	    				showMessageDialog("ERROR", mensaje);
	    				Log.i("MISPRUEBAS","ERROR 2");
	    			}
	    			showMessageDialog("Tarjeta sellada con �xito", currentUser.name+" con este ha acumulado "+sellos+" sellos y "+coupones+" cupones.");
	    			
	    			
	    		}
	    		else{
	    			Log.i("MISPRUEBAS","ERROR 3");
	    			showMessageDialog("ERROR", mensaje);
	    		}
	    		
	    		
	    	}
	    	else{
	    		Log.i("MISPRUEBAS","ERROR 4");
	    		showMessageDialog("ERROR", mensaje);
	    	}

    	}
    	
    };
    
    
    
    //Load image from url
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    	  ImageView bmImage;

    	  public DownloadImageTask(ImageView bmImage) {
    	      this.bmImage = bmImage;
    	  }

    	  protected Bitmap doInBackground(String... urls) {
    	      String urldisplay = urls[0];
    	      Bitmap mIcon11 = null;
    	      try {
    	        InputStream in = new java.net.URL(urldisplay).openStream();
    	        mIcon11 = BitmapFactory.decodeStream(in);
    	      } catch (Exception e) {
    	          Log.e("Error", e.getMessage());
    	          e.printStackTrace();
    	      }
    	      return mIcon11;
    	  }

    	  protected void onPostExecute(Bitmap result) {
    	      bmImage.setImageBitmap(result);
    	  }
    	}
    



	
	

}
