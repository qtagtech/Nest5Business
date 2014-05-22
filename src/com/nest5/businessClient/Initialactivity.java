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
 * Table designed by RaÃºl Inc from the Noun Project
 * */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
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
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
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
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.acs.acr31.ACR31Reader;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.nest5.businessClient.AddIngredientCategoryForm.OnAddIngredientCategoryListener;
import com.nest5.businessClient.AddIngredientForm.OnAddIngredientListener;
import com.nest5.businessClient.AddProductCategoryForm.OnAddProductCategoryListener;
import com.nest5.businessClient.AddProductForm.OnAddProductListener;
import com.nest5.businessClient.CloseTableForm.OnSelectTableActionListener;
import com.nest5.businessClient.CreateComboView.OnCreateComboListener;
import com.nest5.businessClient.CreateProductView.OnCreateProductListener;
import com.nest5.businessClient.DailyObjectFragment.OnDailyObjectFragmentCreatedListener;
import com.nest5.businessClient.HomeObjectFragment.OnHomeObjectFragmentCreatedListener;
import com.nest5.businessClient.HomeObjectFragment.OnIngredientCategorySelectedListener;
import com.nest5.businessClient.InventoryObjectFragment.OnInventoryObjectFragmentCreatedListener;
import com.nest5.businessClient.Nest5ReadObjectFragment.OnNest5ReadObjectFragmentCreatedListener;
import com.nest5.businessClient.OrderForm.OnOrderFomrFragmentCreatedListener;
import com.nest5.businessClient.OrderForm.OnOrderListener;
import com.nest5.businessClient.PaymentForm.OnPayListener;
import com.nest5.businessClient.PrintInvoiceForm.OnPrintSelectListener;
import com.nest5.businessClient.SalesObjectFragment.OnSalesObjectFragmentCreatedListener;
import com.nest5.businessClient.SelectAddItem.OnAddItemSelectedListener;
import com.nest5.businessClient.WifiDirectDialog.DeviceActionListener;


public class Initialactivity extends FragmentActivity implements
		OnAddItemSelectedListener, OnAddIngredientListener,
		OnIngredientCategorySelectedListener,
		OnHomeObjectFragmentCreatedListener, OnAddIngredientCategoryListener,
		OnAddProductCategoryListener, OnAddProductListener,
		OnCreateProductListener, OnPayListener, OnOrderFomrFragmentCreatedListener,OnOrderListener,OnCreateComboListener,
		OnSalesObjectFragmentCreatedListener, ChannelListener,
		DeviceActionListener, ConnectionInfoListener, PeerListListener,
		OnDailyObjectFragmentCreatedListener,
		OnInventoryObjectFragmentCreatedListener,
		OnNest5ReadObjectFragmentCreatedListener,
		ScanDevicesFragment.SelectDevice,
		OnPrintSelectListener,
		OnSelectTableActionListener
		{

	public static final String TAG = "Initialactivity";

	public static final int TOAST_COMMAND = 1;

	public static final int DELETE_ALL_COMMAND = 2;

	public static final int SEND_ALL_COMMAND = 3;

	public static final int TABLE_TYPE_TODAY = 0;

	public static final int TABLE_TYPE_ALL = 1;
	
	private static final int RETURN_FROM_DESIGN_TABLE = 5551;
	
	private static final int RETURN_FROM_OPENCLOSE_TABLE = 5552;
	
	private static final int OPEN_TABLE_ACTION = 55515;
	
	private boolean openOtherWindow = false;
	private int openOtherWindowAction = 0;
	
	volatile static boolean isPausing = false;
	
	private final BackUpOrdersHandler backUpOrdersHandler = new BackUpOrdersHandler(this);
	private final RecoverOrdersHandler recoverOrdersHandler = new RecoverOrdersHandler(this);
	private final MHandler mHandler = new MHandler(this);
	private final ContadorMinutos contador = new ContadorMinutos(this);
	private final BackUpThread constantBackUp = new BackUpThread(this);
	
	
	/***
	 * 
	 * 
	 * PRINTER OPTIONS
	 * 
	 * 
	 * ****/
	public static final char[] CLEAR_PRINTER = {(char)0x10};
	public static final char[] FULL_CUT = {(char)0x19};
	public static final char[] PARTIAL_CUT = {(char)0x1A};
	public static final char[] INITIALIZE_PRINTER = {(char)0x1B,(char)0x40};
	public static final char[] PRINT_FEED_ONE_LINE = {(char)0x0A};
	public static final char[] PRINT_CARRIAGE = {(char)0x0D};
	public static final char[] ADD_N_DOT_ROWS = {(char)0x16};
	public static final char[] PRINT_FEED_N_LINES = {(char)0x1B,(char)0x64};
	public static final char[] FINALIZE_TICKET = {(char)0x15,(char)0xFF};
	public static final char[] HORIZONTAL_TAB = {(char)0x09};
	public static final char[] JUSTIFICATION_CENTER = {(char)0x1B,(char)0x61,(char)0x31};
	public static final char[] JUSTIFICATION_LEFT = {(char)0x1B,(char)0x61,(char)0x30};
	public static final char[] JUSTIFICATION_RIGHT = {(char)0x1B,(char)0x61,(char)0x32};
	public static final char[] DOUBLE_WIDE_CHARACTERS = {(char)0x12};
	public static final char[] SINGLE_WIDE_CHARACTERS = {(char)0x13};
	public static final char[] UNDERLINE_CANCEL = {(char)0x1B,(char)0x2D,(char)0x30};
	public static final char[] UNDERLINE_SINGLE = {(char)0x1B,(char)0x2D,(char)0x31};
	public static final char[] UNDERLINE_DOUBLE = {(char)0x1B,(char)0x2D,(char)0x32};
	public static final char[] ITALIC_CANCEL = {(char)0x1B,(char)0x49,(char)0x00};
	public static final char[] ITALIC_STYLE = {(char)0x1B,(char)0x49,(char)0x01};
	
	
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
	private AutoCompleteTextView autoCompleteTextView;
	TableLayout table;
	TableLayout dailyTable;
	TableLayout inventoryTable;
	// Saber si llama poner stampCard desde lista de promociones de usuario
	Boolean fromMyDeals = false;
	// Validar si botón redime cupón o sello
	Boolean redeemCoupon = false;
	Boolean redimiendo = false;
	View app;
	ImageView btnSlide;
	boolean menuOut = false;
	Handler handler = new Handler();
	int btnWidth;
	static ListView ordersList;
	static TextView sale_name;
	static TextView sale_details;
	Bundle savedInstanceState;
	boolean validHome = true;
	private SQLiteDatabase db;
	private MySQLiteHelper dbHelper;
	private IngredientCategoryDataSource ingredientCategoryDatasource;
	private ProductCategoryDataSource productCategoryDatasource;
	private IngredientDataSource ingredientDatasource;
	private ProductDataSource productDatasource;
	private ComboDataSource comboDatasource;
	private TaxDataSource taxDataSource;
	private UnitDataSource unitDataSource;
	private SaleDataSource saleDataSource;
	private SyncRowDataSource syncRowDataSource;
	ImageAdapter gridAdapter;
	SaleAdapter cookingAdapter;
	static LayoutInflater inflater;
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
	List<Registrable> allRegistrables;
	List<Unit> units;
	List<Sale> saleList;
	List<Sale> salesFromToday;
	List<SyncRow> syncRows;
	private LinkedHashMap<Registrable, Integer> currentOrder;
	private CurrentTable<Table,Integer> currentTable;
	private List<Registrable> currentList;
	private List<Registrable> inTableRegistrable;
	private LinkedHashMap<String, LinkedHashMap<Registrable, Integer>> savedOrders;
	private static LinkedList<LinkedHashMap<Registrable, Integer>> cookingOrders;
	private static LinkedHashMap<LinkedHashMap<Registrable, Integer>, Integer> cookingOrdersDelivery;
	private static LinkedHashMap<LinkedHashMap<Registrable, Integer>, Integer> cookingOrdersTogo;
	private static LinkedHashMap<LinkedHashMap<Registrable, Integer>, Long> cookingOrdersTimes;
	private static LinkedHashMap<LinkedHashMap<Registrable, Integer>, CurrentTable<Table,Integer>> cookingOrdersTable;
	private static LinkedList<CurrentTable<Table,Integer>> openTables;
	private String[] frases;
	private static int currentSelectedPosition = -1;
	private static int currentSelectedAddTable = -1;
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
	// Propiedades de lector magnÃƒÂ©tico ACR31 de ACS Ltd.
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
	
	private int totalSync = 0; //variable para contar cuantas synRows se han guardado con éxito
	
	/*
	 * Sync Server - Big Data Server Variables
	 * 
	 * */
	
	private static String deviceID;
	
	
	/***
	 * 
	 * 
	 * CONEXIÃ“N BLUETOOTH IMPRESORAS
	 * 
	 * 
	 * */
	public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
  
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    
    
    // Array adapter for the conversation thread
    //private ArrayAdapter<String> mConversationArrayAdapter;
    // String buffer for outgoing messages
    private StringBuffer mOutStringBuffer;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    private BluetoothChatService mChatService = null;
    private BluetoothSerialService mSerialService = null;
    private String mConnectedDeviceName = null;
    private Menu mMenu;
    
    /****
     * 
     * 
     * IMPRESION POR TCP / IP
     * 
     * ****/
    
    private TCPPrint mTCPPrint;

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
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		dbHelper = new MySQLiteHelper(this);
		ingredientCategoryDatasource = new IngredientCategoryDataSource(dbHelper);
		db = ingredientCategoryDatasource.open();
		ingredientCategories = ingredientCategoryDatasource
				.getAllIngredientCategory();
		// ingredientCategoryDatasource.close();
		productCategoryDatasource = new ProductCategoryDataSource(dbHelper);
		productCategoryDatasource.open(db);
		productsCategories = productCategoryDatasource.getAllProductCategory();
		taxDataSource = new TaxDataSource(dbHelper);
		taxDataSource.open(db);
		taxes = taxDataSource.getAllTax();
		unitDataSource = new UnitDataSource(dbHelper);
		unitDataSource.open(db);
		units = unitDataSource.getAllUnits();
		ingredientDatasource = new IngredientDataSource(dbHelper);
		ingredientDatasource.open(db);
		ingredientes = ingredientDatasource.getAllIngredient();
		productDatasource = new ProductDataSource(dbHelper);
		productDatasource.open(db);
		productos = productDatasource.getAllProduct();
		comboDatasource = new ComboDataSource(dbHelper);
		comboDatasource.open(db);
		combos = comboDatasource.getAllCombos();
		saleDataSource = new SaleDataSource(dbHelper);
		saleDataSource.open(db);
		saleList = saleDataSource.getAllSales();
		syncRowDataSource = new SyncRowDataSource(dbHelper);
		syncRowDataSource.open(db);
		
		
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
		//Log.d(TAG, today.toString());
		//Log.d(TAG, tomorrow.toString());
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(System.currentTimeMillis());
		//Log.d(TAG, now.toString());

		//Log.d(TAG, "Diferencia entre tiempos: " + String.valueOf(end - init));
		salesFromToday = saleDataSource.getAllSalesWithin(init, end);
		updateRegistrables();
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
		Tab ordersTab = actionBar.newTab().setText("Órdenes")
				.setTabListener(tabListener);
		/*Tab dailyTab = actionBar.newTab().setText("Registros")
				.setTabListener(tabListener);
		Tab inventoryTab = actionBar.newTab().setText("Inventarios")
				.setTabListener(tabListener);*/
		Tab nest5ReadTab = actionBar.newTab().setText("Nest5")
				.setTabListener(tabListener);
		actionBar.addTab(homeTab, true);
		actionBar.addTab(ordersTab, false);
		//actionBar.addTab(dailyTab, false);
		//actionBar.addTab(inventoryTab, false);
		actionBar.addTab(nest5ReadTab, false);
		
		
		currentOrder = new LinkedHashMap<Registrable, Integer>();
		inTableRegistrable = new ArrayList<Registrable>();
		savedOrders = new LinkedHashMap<String, LinkedHashMap<Registrable, Integer>>();
		cookingOrders = new LinkedList<LinkedHashMap<Registrable, Integer>>();
		//cookingOrdersMethods = new LinkedHashMap<LinkedHashMap<Registrable, Integer>, String>();
		cookingOrdersDelivery = new LinkedHashMap<LinkedHashMap<Registrable, Integer>, Integer>();
		cookingOrdersTogo = new LinkedHashMap<LinkedHashMap<Registrable, Integer>, Integer>();
		//cookingOrdersTip = new LinkedHashMap<LinkedHashMap<Registrable, Integer>, Integer>();
		//cookingOrdersDiscount = new LinkedHashMap<LinkedHashMap<Registrable, Integer>, Double>();
		cookingOrdersTimes = new LinkedHashMap<LinkedHashMap<Registrable, Integer>, Long>();
		cookingOrdersTable = new LinkedHashMap<LinkedHashMap<Registrable, Integer>, CurrentTable<Table,Integer>>();
		openTables = new LinkedList<CurrentTable<Table,Integer>>();
		//cookingOrdersReceived = new LinkedHashMap<LinkedHashMap<Registrable, Integer>, Double>();
		frases = getResources().getStringArray(R.array.phrases);
		timer = new Timer();
		deviceID = DeviceID.getDeviceId(mContext);
		//////Log.i("AACCCAAAID",deviceID);
		BebasFont = Typeface
				.createFromAsset(getAssets(), "fonts/BebasNeue.otf");
		VarelaFont = Typeface.createFromAsset(getAssets(),
				"fonts/Varela-Regular.otf");
		// Lector de tarjetas magnéticas
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		//mReader = new ACR31Reader(mAudioManager);
		/* Initialize the reset progress dialog */
		
		
		
		mResetProgressDialog = new ProgressDialog(mContext);

		// ACR31 RESET CALLBACK
		/*mReader.setOnResetCompleteListener(new ACR31Reader.OnResetCompleteListener() {

			
			//hola como estas
			@Override
			public void onResetComplete(ACR31Reader reader) {

				if (mSettingSleepTimeout) {


					mGettingStatus = true;


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
		});*/
		/* Set the raw data callback. */
		/*mReader.setOnRawDataAvailableListener(new ACR31Reader.OnRawDataAvailableListener() {

			@Override
			public void onRawDataAvailable(ACR31Reader reader, byte[] rawData) {
				////Log.i("MISPRUEBAS", "setOnRawDataAvailableListener");

				final String hexString = toHexString(rawData)
						+ (reader.verifyData(rawData) ? " (Checksum OK)"
								: " (Checksum Error)");

				////Log.i("MISPRUEBAS", hexString);
				if (reader.verifyData(rawData)) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {

							mResetProgressDialog
									.setMessage("Solicitando Información al Servidor...");
							mResetProgressDialog.setCancelable(false);
							mResetProgressDialog.setIndeterminate(true);
							mResetProgressDialog.show();

						}
					});
					SharedPreferences prefs = Util
							.getSharedPreferences(mContext);

					restService = new RestService(recievePromoandUserHandler,
							mContext, Setup.PROD_URL
									+ "/company/initMagneticStamp");
					restService.addParam("company",
							prefs.getString(Setup.COMPANY_ID, "0"));
					restService.addParam("magnetic5", hexString);
					restService.setCredentials("apiadmin", Setup.apiKey);
					try {
						restService.execute(RestService.POST);
					} catch (Exception e) {
						e.printStackTrace();
						////Log.i("MISPRUEBAS", "Error empezando request");
					}
				}

			}
		});*/

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
		//mReader.start();
		// If BT is not on, request that it be enabled.óóóóó BTóóóóóóóóóóóó
        // setupChat() will then be called during onActivityRe//sultsetupChat() È»óó½«µóóóóÚ¼ó onActivityResult
		//recoverCookingOrders();//recover every single order that was on hold before the application was closed
		RecoverThread recoverBackUp = new RecoverThread(this);
		recoverBackUp.run();
		isPausing = false;
		mHandler.postDelayed(contador,60*1000);
		backUpOrdersHandler.postDelayed(constantBackUp, 1000 * 30);
		updateRegistrables(); //update all elements than can be sold (ingredients, products, combos)
		try{
			if (!mBluetoothAdapter.isEnabled()) {
	            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
	        // Otherwise, setup the serial session
	        } else {
	            if (mChatService == null) setupChat();
	        	//if (mSerialService == null) setupChat();
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
        
	}

	@Override
	public synchronized void onResume() {
		super.onResume();
		receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);
		registerReceiver(receiver, intentFilter);
		registerReceiver(onSyncDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
		SharedPreferences prefs = Util.getSharedPreferences(mContext);
		String connectionStatus = prefs.getString(Util.CONNECTION_STATUS,
				Util.DISCONNECTED);
		prefs.edit().putBoolean(Setup.IS_UPDATING, false);
		//mReader.start();
		// Performing this check in onResume() covers the case in which BT wasóó onResume() óóÖ´óÐ´Ë¼óóóóó BT óóóóóóóóó
        // not enabled during onStart(), so we were paused to enable it...óó onStart() óÚ¼óóÞ·óóóóóóóÇ±óóóÍ£óóóóóó...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.ACTION_REQUEST_ENABLE óî¶¯óóóóÊ±óóóóóóóó onResume()óó
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
        	
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
              // Start the Bluetooth chat services
              mChatService.start();
            }
        }
		lay = R.layout.home;
		setScreenContent();
		syncRows = syncRowDataSource.getAllSyncRows();
		//////Log.i("SYNC", "syncrows en db: "+String.valueOf(syncRows.size()));
		if(isConnectedToInternet())
			updateMaxSales();
		if(deviceText != null){
			/**
			 * Print over TCP / IP
			 * 
			 * ***/
			new connectTask().execute("");
		}
		

		//Toast.makeText(mContext, String.valueOf(productos.size()) ,Toast.LENGTH_LONG).show();
		
		
		if(openOtherWindow){
			openOtherWindow = false;
			switch (openOtherWindowAction) {
			case OPEN_TABLE_ACTION:
				showCloseTableDialog();
				break;

			default:
				break;
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
		unregisterReceiver(onSyncDownloadComplete);
		mResetProgressDialog.dismiss();
		if(mTCPPrint != null)
			mTCPPrint.stopClient();
		//keepCookingOrders(); //OJO ESTO ES LO QUE FUNCIONA ANTES DE USAR LOS THREADS
		isPausing = true;
		backUpOrdersHandler.removeCallbacks(constantBackUp);
		BackUpThread hacerBackUp = new BackUpThread(this);
		hacerBackUp.run();
		

	}

	/**
	 * Shuts down the activity.
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		// Stop the Bluetooth chat service
		try{
			if (mChatService != null) mChatService.stop();
	        //if (mSerialService != null) mSerialService.stop();
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			if (dbHelper != null) {
	            dbHelper.close();
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
        
        
	}

	@Override
	protected void onStop() {
		super.onStop();
		mResetProgressDialog.dismiss();
		//mReader.stop();
	}

	//

	// Manage UI Screens

	private void setSplashScreen() {
		setContentView(R.layout.splash);

	}
	private void setupChat() {
        //Log.d(TAG, "setupChat()");
        mChatService = new BluetoothChatService(this, mHandlerBlueTooth);   
    }

	private void setHomeScreenContent() {
		SharedPreferences prefs = Util.getSharedPreferences(mContext);
		if (prefs.getInt(Util.INTERNET_CONNECTION, 0) == 1) {

		} else {
			// Toast.makeText(mContext, "No tienes conexión a internet.",
			// Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		mMenu = menu;
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
			SharedPreferences defaultprefs = PreferenceManager.getDefaultSharedPreferences(mContext);
			boolean layouttables = defaultprefs.getBoolean("arrange_tables", false);
			if(layouttables){
				MenuItem tables = mMenu.findItem(R.id.layouttables);
				if(!tables.isVisible()){
					tables.setVisible(true);
					invalidateOptionsMenu();
				}
				MenuItem mesas = mMenu.findItem(R.id.menu_show_tables);
				if(!mesas.isVisible()){
					mesas.setVisible(true);
					invalidateOptionsMenu();
				}
				
				
			}	
		
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
			/*
			 * Toast.makeText(mContext, " " + salesFromToday.size(),
			 * Toast.LENGTH_LONG).show();
			 */
			backUpDb();
			return true;
		case R.id.print_zreport:
			if(isConnectedToInternet())
				{
				onZReportSelect();
				}
			else{
				return false;
				}
		/*case R.id.menu_load_register:

			return true;*/
		case R.id.menu_connect_printer:
			mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();   
	        if (mBluetoothAdapter == null) {
	            Toast.makeText(this, "Este dispositivo no tiene Bluetooth", Toast.LENGTH_LONG).show();
	            return false;
	        }
	        showScanDialog();
			return true;
		case R.id.appsettings:
			showSettings();
			return true;
		case R.id.layouttables:
			showTableLayout();
			return true;
		case R.id.menu_show_tables:
			showMesasLayout();
			return true;
			
		/*case R.id.menu_connect_device:
			if (!isWifiP2pEnabled) {
				Toast.makeText(Initialactivity.this,
						"Debes Activar WifiDirect", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
				return true;
			}
			statusText.setText("Buscando dispositivos...");
			manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
				@Override
				public void onSuccess() {
					Toast.makeText(Initialactivity.this, "BÃºsqueda Iniciada",
							Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onFailure(int reasonCode) {
					Toast.makeText(Initialactivity.this,
							"BÃºsqueda Fallida: " + reasonCode,
							Toast.LENGTH_SHORT).show();
				}
			});
			return true;*/

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void showScanDialog() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager(); 
        ScanDevicesFragment scanDialog = new ScanDevicesFragment();
        scanDialog.show(fm, "fragment_edit_name");
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

	private void showCreateProductFormDialog(String name, ProductCategory category, Double cost,  Double price,  Tax tax) {
		// guardar producto
					// Toast.makeText(mContext, category.getName(),
					// Toast.LENGTH_LONG).show();
					Product product = null;
					try {
						product = productDatasource.createProduct(name, category, 0,
								cost, price, tax,0);

					} catch (Exception e) {
						e.printStackTrace();
					}
					if (product != null) {

						//productList.add(new Registrable(product));
						/*Toast.makeText(mContext,
								product.getName() + " Creado Satisfactoriamente.",
								Toast.LENGTH_SHORT).show();*/
						android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
						
						CreateProductView createProductDialog = new CreateProductView(
								ingredientes, product);
						createProductDialog.show(fm, "fragment_edit_name");
						// informar de cambios a lista de ingredientes
					} else {
						Toast.makeText(mContext,
								"Hubo Errores Creando el Producto '" + name + "'.",
								Toast.LENGTH_SHORT).show();
						// informar de cambios a lista de ingredientes
					}
		
	}

	private void showAddComboFormDialog() {
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

		CreateComboView createComboDialog = new CreateComboView(ingredientes,
				productos, taxes);
		createComboDialog.show(fm, "fragment_edit_name");
	}

	private void showPaymentFormDialog() {
		//////Log.i("MISPRUEBAS","Seleccionada para pagar: "+cookingOrders.get(currentSelectedPosition).size());
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
		PaymentForm editNameDialog = new PaymentForm(currentOrder);
		editNameDialog.show(fm, "fragment_edit_name");
	}
	private void showOrderFormDialog() {
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
		OrderForm editNameDialog = new OrderForm(currentOrder);
		editNameDialog.show(fm, "fragment_edit_name");
	}
	
	private void showPrintInvoiceDialog(){
		if (currentSelectedPosition == -1)
			return;
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
		PrintInvoiceForm printOrderDialog = new PrintInvoiceForm();
		printOrderDialog.show(fm, "fragment_edit_name");
	}
	
	private void showSettings(){
		Intent intent = new Intent();
        intent.setClass(mContext, SetPreferenceActivity.class);
        startActivityForResult(intent, 0); 
	}
	
	private void showTableLayout() {
		Intent intent = new Intent();
        intent.setClass(mContext, TablesActivity.class);
        startActivityForResult(intent, RETURN_FROM_DESIGN_TABLE); 
	}
	private void showMesasLayout() {
		Intent intento = new Intent();
		if(openTables.size() > 0){
			Gson gson = new Gson();
		    String list = gson.toJson(openTables); 
		    intento.putExtra("mesasabiertas", list);
		}
		 
        intento.setClass(mContext, TablesOrderActivity.class);
        startActivityForResult(intento, RETURN_FROM_OPENCLOSE_TABLE); 
	}
	
	private void showCloseTableDialog(){ //no muestra porque no ha cargado la vista, poner una bandera que diga si viene de alla y tomarlo en onresume o algo asi 
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
		CloseTableForm closeTableDialog = new CloseTableForm();
		closeTableDialog.show(fm, "fragment_edit_name");
	}


	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		// ////Log.i(TAG, String.valueOf(redeemCoupon));
		if (scanResult != null) {

		}
		
		if (requestCode == RETURN_FROM_DESIGN_TABLE) {
			String result = null;
	        if(resultCode == RESULT_OK){
	            result = intent.getStringExtra(Setup.SAVED_TABLES);
	        }
	        if (resultCode == RESULT_CANCELED) {
	            //Write your code if there's no result
	        }
			if(result != null){
				//load tables view and allow to make order for it
				showMesasLayout();
			}
	    }
		
		if(requestCode == RETURN_FROM_OPENCLOSE_TABLE){
			Table actual = null;
			int clientes = 0;
	        if(resultCode == RESULT_OK){
	            actual = intent.getParcelableExtra("MIMESA");
	            clientes = intent.getIntExtra("MIMESACLIENTES", 0);
	            if(actual != null){
					//load tables view and allow to make order for it
					currentTable = new CurrentTable<Table, Integer>(actual, clientes); 
					statusText.setVisibility(View.VISIBLE);
					statusText.setText(actual.getName() + " con "+clientes+" Clientes.");
					//Log.i("MISPRUEBAS",currentTable.getTable().getName());
				}
	        }
	        if (resultCode == RESULT_CANCELED) {
	            //Write your code if there's no result
	        }
	        
	        if(resultCode == Setup.CLOSE_TABLE){
	        	//Log.i("MISPRUEBAS","volviendo de cerrar mesa");
	        	actual = intent.getParcelableExtra("MIMESA");
	        	//tomar mesa que se cierra, preguntar si es cancelar venta o pagar, si es cancelar borra de opentables, de orders etc y si es pagar, pone en currentsale y abre dialogo pagar
	        	if(actual != null){
	        		//Log.i("MISPRUEBAS","volviendo de cerrar mesa y no esta nulo");
					//load tables view and allow to make order for it
					currentTable = new CurrentTable<Table, Integer>(actual, 0); 
					//Log.i("MISPRUEBAS","regreasa de dar clic en cerrar mesa con: "+currentTable.getTable().getName());
					statusText.setVisibility(View.VISIBLE);
					statusText.setText("Cerrando: "+actual.getName());
					openOtherWindow = true;
					openOtherWindowAction = OPEN_TABLE_ACTION;
				}
	        }
			
			
		}
		
		
		// else continue with any other code you need in the method
		
			SharedPreferences defaultprefs = PreferenceManager.getDefaultSharedPreferences(mContext);
			boolean layouttables = defaultprefs.getBoolean("arrange_tables", false);
			MenuItem tablelayouts = mMenu.findItem(R.id.layouttables);
			MenuItem showtables = mMenu.findItem(R.id.menu_show_tables);
			boolean change = false;
			if(tablelayouts.isVisible() != layouttables)
				change = true;
			tablelayouts.setVisible(layouttables);
			showtables.setVisible(layouttables);
			if(change){
				invalidateOptionsMenu();
			}
			
			
		
	}

	/**
	 * Sets the screen content based on the screen id.
	 */
	private void setScreenContent() {

		switch (lay){
		case R.layout.splash:
			setSplashScreen();
			break;
		case R.layout.home:
			setHomeScreenContent();
			break;

		}
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
						priceMeasure, qty, date,0);

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
				createSyncRow("\""+Setup.TABLE_INGREDIENTS+"\"",ingredient.getId(), ingredient.getSyncId(), "{\"name\": \""+ingredient.getName()+"\",\"_id\":"+ingredient.getId()+",\""+Setup.COLUMN_INGREDIENT_CATEGORY_ID+"\": "+category.getSyncId()+",\""+Setup.COLUMN_INGREDIENT_TAX_ID+"\": "+tax.getSyncId()+",\""+Setup.COLUMN_INGREDIENT_UNIT+"\": "+unit.getSyncId()+",\""+Setup.COLUMN_INGREDIENT_COST_PER_UNIT+"\": "+costPerUnit+",\""+Setup.COLUMN_INGREDIENT_PRICE_PER_UNIT+"\": "+pricePerUnit+",\""+Setup.COLUMN_INGREDIENT_PRICE_MEASURE+"\":"+priceMeasure+" ,\""+Setup.COLUMN_INGREDIENT_QTY+"\": "+qty+",\""+Setup.COLUMN_INGREDIENT_DATE+"\":"+date+"}");
				
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
				createSyncRow(Setup.TABLE_INGREDIENTS,newIngredient.getId(), newIngredient.getSyncId(), "{name: \""+newIngredient.getName()+"\",_id:"+newIngredient.getId()+","+Setup.COLUMN_INGREDIENT_CATEGORY_ID+": "+category.getSyncId()+","+Setup.COLUMN_INGREDIENT_TAX_ID+": "+tax.getSyncId()+","+Setup.COLUMN_INGREDIENT_UNIT+": "+unit.getSyncId()+","+Setup.COLUMN_INGREDIENT_COST_PER_UNIT+": "+costPerUnit+","+Setup.COLUMN_INGREDIENT_PRICE_PER_UNIT+": "+pricePerUnit+","+Setup.COLUMN_INGREDIENT_PRICE_MEASURE+":"+priceMeasure+" ,"+Setup.COLUMN_INGREDIENT_QTY+": "+qty+"}");
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
					.createProductCategory(name,0);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (categoryProduct != null) {
			Toast.makeText(mContext,
					categoryProduct.getName() + " Creado Satisfactoriamente.",
					Toast.LENGTH_SHORT).show();
			// informar de cambios a lista de ingredientes
			productsCategories.add(categoryProduct);
			//create syncRow for syncronization with main server
			createSyncRow(Setup.TABLE_CATEGORY_PRODUCTS,categoryProduct.getId(), categoryProduct.getSyncId(), "{name: \""+categoryProduct.getName()+"\",_id:"+categoryProduct.getId()+"}");
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
					.createIngredientCategory(name,0);
			

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
			//create syncRow for syncronization with main server
			createSyncRow(Setup.TABLE_CATEGORY_INGREDIENTS,ingredientProduct.getId(), ingredientProduct.getSyncId(), "{name: \""+ingredientProduct.getName()+"\",_id:"+ingredientProduct.getId()+"}");

		} else {
			Toast.makeText(mContext,
					"Hubo Errores Creando el Ingrediente '" + name + "'.",
					Toast.LENGTH_SHORT).show();
			// informar de cambios a lista de ingredientes
		}
	}
	//creates a SyncRow and tries to sync, if successfull deletes it, if not leaves it there for later synchronization retryal
	private void createSyncRow(String table, long rowId,
			long syncId, String fields) {
			SyncRow syncRow = null;
			try {
				SharedPreferences prefs = Util.getSharedPreferences(mContext);
				String deviceId = prefs.getString(Setup.DEVICE_REGISTERED_ID, "null");
				if(deviceId.equalsIgnoreCase("null")){ //Device not properly registered in nest5 big data.
					return ;
				}
				syncRowDataSource.open();
				syncRow = syncRowDataSource.createSyncRow(deviceId, table, rowId, syncId, fields);
	
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (syncRow != null) {
				if(isConnectedToInternet())
					sendAllSyncRows(); //sync todas las que haya, si esta es la unica, total será igual a uno y solo subirá una, sino subirá todas las que haya y habra un receiver para cada una.
			} else {
				Toast.makeText(mContext,
						"Hubo Errores Creando el SyncRow '" + syncId + "'.",
						Toast.LENGTH_SHORT).show();
			}
		
		
	}
	
	//checks if there are old unsynced rows and tries to sync them
		private void sendAllSyncRows() {
				List<SyncRow> syncRows = null;
				try {
					SharedPreferences prefs = Util.getSharedPreferences(mContext);
					String deviceId = prefs.getString(Setup.DEVICE_REGISTERED_ID, "null");
					if(deviceId.equalsIgnoreCase("null")){ //Device not properly registered in nest5 big data.
						return ;
					}
					syncRowDataSource.open();
					syncRows = syncRowDataSource.getAllSyncRows();
		
				} catch (Exception e) {
					e.printStackTrace();
				}
				totalSync = syncRows.size();
				//////Log.i("SYNC", "syncrows en db: "+String.valueOf(syncRows.size()));
				for(int i = 0; i < syncRows.size(); i++){//guardo cada row y si llega con éxito resta uno
					SyncRow syncRow = syncRows.get(i);
					StringBuilder row = new StringBuilder();
					row.append("{\"fields\":");
					row.append(syncRow.getFields());
					row.append(",\"device_id\":");
					row.append(prefs.getString(Setup.DEVICE_REGISTERED_ID, "null"));
					row.append(",\"table\":");
					row.append(syncRow.getTable());
					row.append(",\"row_id\":");
					row.append(syncRow.getRowId());
					row.append(",\"time_created\":");
					row.append(syncRow.getTimeCreated());
					row.append(",\"sync_id\":");
					row.append(syncRow.getSyncId());
					row.append("}");
					restService = new RestService(dataRowSent, mContext,
							Setup.PROD_BIGDATA_URL + "/rowOps/rowReceived");
					restService.addParam("row", row.toString());
					restService.addParam("sync_row_id", String.valueOf(syncRow.getId()));//se envia para que el servidor lo regrese y se sepa que row se estaba subiendo
					restService.setCredentials("apiadmin", Setup.apiKey);
					try {
						//////Log.i("MISPRUEBAS", "empezando upload dataRow");
						restService.execute(RestService.POST);
					} catch (Exception e) {
						e.printStackTrace();
						
					}
				}
		}
		
		private void updateMaxSales() {
				SharedPreferences prefs = Util.getSharedPreferences(mContext);
				String deviceId = prefs.getString(Setup.DEVICE_REGISTERED_ID, "null");
				if(deviceId.equalsIgnoreCase("null")){ //Device not properly registered in nest5 big data.
					return ;
				}
				restService = new RestService(updateMaxHandler, mContext,
						 Setup.PROD_BIGDATA_URL+"/deviceOps/fetchMaxSale");
						 String jString = "{device_id:"+deviceID+"}";
						 restService.addParam("payload", jString);		 
						 restService.setCredentials("apiadmin", Setup.apiKey);
						 try {
						 restService.execute(RestService.POST);
						 } catch (Exception e) {
							 e.printStackTrace(); 
							 //////Log.i("MISPRUEBAS","Error empezando request de deviceid");
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
			btnTag.setText((values[i]));
			btnTag.setId(i);
			btnTag.setOnClickListener(typeButtonClickListener);
			ll.addView(btnTag);

		}
		SharedPreferences defaultprefs = PreferenceManager.getDefaultSharedPreferences(mContext);
		boolean layouttables = defaultprefs.getBoolean("arrange_tables", false);
		
		itemsView = (GridView) v.findViewById(R.id.gridview);
		statusText = (TextView) v.findViewById(R.id.group_owner);
		deviceText = (TextView) v.findViewById(R.id.device_info);
		saleValue = (TextView) v.findViewById(R.id.sale_info);
		autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autocomplete_registrable);
		if(layouttables){
			statusText.setVisibility(View.VISIBLE);
			//statusText.setText("No hay Mesa Seleccionada.");
		}
		else{
			statusText.setVisibility(View.INVISIBLE);
			statusText.setText("");
		}
		updateSaleValue();
		pagarButton = (Button) v.findViewById(R.id.pay_register);
		guardarButton = (Button) v.findViewById(R.id.save_register);
		pagarButton.setOnClickListener(payListener);
		guardarButton.setOnClickListener(saveListener);
		registerList = new ArrayList<Registrable>();
		gridAdapter = new ImageAdapter(mContext, registerList, inflater,
				gridButtonListener);
		setGridContent(gridAdapter, comboList);
		ArrayList<String> registrables = new ArrayList<String>();
		for(Registrable actual : allRegistrables){
			registrables.add(actual.name);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, registrables);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				String selection = (String) parent.getItemAtPosition(position);
		        int pos = -1;

		        for (int i = 0; i < allRegistrables.size(); i++) {
		            if (allRegistrables.get(i).name.equalsIgnoreCase(selection)) {
		                pos = i;
		                break;
		            }
		        }
		        if(pos > -1){
		        	if (currentOrder.containsKey(allRegistrables.get(pos))) {
						currentOrder.put(allRegistrables.get(pos),
								currentOrder.get(allRegistrables.get(pos)) + 1);

					} else {
						currentOrder.put(allRegistrables.get(pos), 1);
					}
					makeTable(allRegistrables.get(pos).name);	
		        }
		        else{
		        	Toast.makeText(mContext, "No Existe el Ítem", Toast.LENGTH_LONG).show();
		        }
		        autoCompleteTextView.setText("");
		        autoCompleteTextView.setHint("Buscar Ítems para Registrar");
				
			}
			
		});
        autoCompleteTextView.setText("");
        autoCompleteTextView.setHint("Buscar Ítems para Registrar");
		// Tomar la tabla de la izquierda del home view
		table = (TableLayout) v.findViewById(R.id.my_table);
		makeTable("NA");
		
		deviceText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mTCPPrint != null){
					mTCPPrint.stopClient();
				}
				new connectTask().execute("");
				
				
			}
		});
		

	}
	
	

	@Override
	public void OnSalesObjectFragmentCreated(View v) {
		Button closeBtn = (Button) v.findViewById(R.id.close_turn);
		Button editBtn = (Button) v.findViewById(R.id.edit_turn);
		Button deleteBtn = (Button) v.findViewById(R.id.delete_turn);
		Button printBtn = (Button) v.findViewById(R.id.print_order);
		ordersList = (ListView) v.findViewById(R.id.turn_list);
		sale_name = (TextView) v.findViewById(R.id.sale_name);
		sale_details = (TextView) v.findViewById(R.id.sale_details);
		
		closeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (currentSelectedPosition == -1)
					return;
				
				LinkedHashMap<Registrable, Integer> currentSale = cookingOrders
						.get(currentSelectedPosition);
				currentOrder = currentSale;
				showPaymentFormDialog();
			}
		});
		
		printBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showPrintInvoiceDialog();
			}
		});

		editBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (currentSelectedPosition == -1)
					return;
				currentOrder = cookingOrders.get(currentSelectedPosition);
				currentTable = cookingOrdersTable.get(currentOrder);
				statusText.setText("Editando pedido "+currentTable.getTable().getName());
				LinkedHashMap<Registrable, Integer> currentSale = cookingOrders
						.get(currentSelectedPosition);
				cookingOrders.remove(currentSelectedPosition);
				//cookingOrdersMethods.remove(currentSale);
				cookingOrdersDelivery.remove(currentSale);
				cookingOrdersTogo.remove(currentSale);
				//cookingOrdersTip.remove(currentSale);
				//cookingOrdersDiscount.remove(currentSale);
				//cookingOrdersReceived.remove(currentSale);
				cookingOrdersTimes.remove(currentSale);
				openTables.remove(cookingOrdersTable.get(currentSale));
				cookingOrdersTable.remove(currentSale);
				currentSelectedPosition = -1;
				//sendCommandMessage(DELETE_ALL_COMMAND);
				List<Long> items = new ArrayList<Long>();
				List<String> nameTables = new ArrayList<String>();
				for (LinkedHashMap<Registrable, Integer> current : cookingOrders) {
					items.add(cookingOrdersTimes.get(current));
					nameTables.add(cookingOrdersTable.get(current).getTable().getName());
				}
				cookingAdapter = new SaleAdapter(mContext, items, nameTables,inflater);
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
				//cookingOrdersMethods.remove(currentSale);
				cookingOrdersDelivery.remove(currentSale);
				cookingOrdersTogo.remove(currentSale);
				//cookingOrdersTip.remove(currentSale);
				//cookingOrdersDiscount.remove(currentSale);
				//cookingOrdersReceived.remove(currentSale);
				cookingOrdersTimes.remove(currentSale);
				openTables.remove(cookingOrdersTable.get(currentSale));
				cookingOrdersTable.remove(currentSale);
				currentSelectedPosition = -1;

				List<Long> items = new ArrayList<Long>();
				List<String> nameTables = new ArrayList<String>();
				for (LinkedHashMap<Registrable, Integer> current : cookingOrders) {
					items.add(cookingOrdersTimes.get(current));
					nameTables.add(cookingOrdersTable.get(current).getTable().getName());
				}
				cookingAdapter = new SaleAdapter(mContext, items, nameTables,inflater);
				ordersList.setAdapter(cookingAdapter);
				sale_name.setText("Pedido Eliminado");
				sale_details.setText("Selecciona otro para ver Detalles.");
			}
		});

		List<Long> items = new ArrayList<Long>();
		List<String> nameTables = new ArrayList<String>();
		for (LinkedHashMap<Registrable, Integer> current : cookingOrders) {
			items.add(cookingOrdersTimes.get(current));
			nameTables.add(cookingOrdersTable.get(current).getTable().getName());
		}
		cookingAdapter = new SaleAdapter(mContext, items, nameTables,inflater);
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
			String name = current.getName().trim().toLowerCase(Locale.getDefault());
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
	
	@Override
	public void OnOrderFomrFragmentCreatedListener(View v) {
		
		
		Spinner addToOpenTable = (Spinner) v.findViewById(R.id.open_tables_add_order);
		TextView title = (TextView) v.findViewById(R.id.add_toopentable_title);
		if(openTables.size() > 0){
			title.setVisibility(View.VISIBLE);
			addToOpenTable.setVisibility(View.VISIBLE);
			ArrayList<String> nameTables = new ArrayList<String>();
			nameTables.add("No, es una mesa nueva.");
			for (CurrentTable<Table, Integer> current : openTables) {
				nameTables.add(current.getTable().getName());
			}
			ArrayAdapter<String> adapter = 
					new ArrayAdapter<String>(Initialactivity.this, 
							android.R.layout.simple_spinner_item, nameTables);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			addToOpenTable.setAdapter(adapter);
			addToOpenTable.setOnItemSelectedListener(new OnItemSelectedListener() {
			    @Override
			    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			    	int pos = position - 1;
			        currentSelectedAddTable = pos;
			        if(pos > -1)
			        	currentTable = openTables.get(pos);
			    }

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					//currentSelectedAddTable = -1;
					
				}

			});
		}else{
			title.setVisibility(View.INVISIBLE);
			addToOpenTable.setVisibility(View.INVISIBLE);
		}
		
		
		
	}

	private OnClickListener readMagneticCardListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			/* Check the reset volume. */
			if (checkResetVolume()) {

				mResetProgressDialog
						.setMessage("Preparando Lector de Tarjetas...");
				mResetProgressDialog.setCancelable(false);
				mResetProgressDialog.setIndeterminate(true);

				mResetProgressDialog.show();
				//mReader.reset();
			}

		}
	};

	// lectira manual de usuario, es decir con correo electronico, esto es un
	// metodo que viene del fragment de nest5ead
	@Override
	public void OnManualReadPressed(String email, Boolean redeem) {
		if (!redeem) {
			mResetProgressDialog.setMessage("Sellando Tarjeta...");
			mResetProgressDialog.setCancelable(false);
			mResetProgressDialog.setIndeterminate(true);
			mResetProgressDialog.show();
			SharedPreferences prefs = Util.getSharedPreferences(mContext);

			restService = new RestService(recievePromoandUserHandler, mContext,
					Setup.PROD_URL + "/company/initManualStamp");
			restService.addParam("company",
					prefs.getString(Setup.COMPANY_ID, "0"));
			restService.addParam("email", email);
			restService.setCredentials("apiadmin", Setup.apiKey);
			try {
				restService.execute(RestService.POST);
			} catch (Exception e) {
				e.printStackTrace();
				////Log.i("MISPRUEBAS", "Error empezando request");
			}
		} else {
			mResetProgressDialog.setMessage("Redimiendo Beneficios...");
			mResetProgressDialog.setCancelable(false);
			mResetProgressDialog.setIndeterminate(true);
			mResetProgressDialog.show();
			SharedPreferences prefs = Util.getSharedPreferences(mContext);

			restService = new RestService(recieveRedeemConfirm, mContext,
					Setup.PROD_URL + "/company/initManualRedeem");
			restService.addParam("company",
					prefs.getString(Setup.COMPANY_ID, "0"));
			restService.addParam("email", email);
			restService.setCredentials("apiadmin", Setup.apiKey);
			try {
				restService.execute(RestService.POST);
			} catch (Exception e) {
				e.printStackTrace();
				////Log.i("MISPRUEBAS", "Error empezando request");
			}
		}

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
	//ACA VOY REVISANDO CODIGO!!!!

	OnClickListener payListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// Toast.makeText(mContext, "Hola", Toast.LENGTH_LONG).show();
			showOrderFormDialog();

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
		
		//actualizar sales de hoy
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
		Log.d("GUARDANDOVENTA", today.toString());
		Log.d("GUARDANDOVENTA", tomorrow.toString());
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(System.currentTimeMillis());
		Log.d(TAG, now.toString());

		Log.d(TAG, "Diferencia entre tiempos: " + String.valueOf(end - init));
		salesFromToday = saleDataSource.getAllSalesWithin(init, end);

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
			Log.w("DAYILETABLES"," "+combos.size()+" "+products.size()+" "+ingredients.size());
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
			showCreateProductFormDialog(name, category,cost,price,tax);
		}

	}

	@Override
	public void OnProductCreated(Boolean status,LinkedHashMap<Ingredient, Double> ingredents,
			Product product) {
		if(status){
			Iterator<Entry<Ingredient, Double>> it = ingredents.entrySet()
					.iterator();
			StringBuilder ingredientes = new StringBuilder();
			ingredientes.append("[");
			int i = 0;
			while (it.hasNext()) {
				Map.Entry<Ingredient, Double> ingredientPair = (Map.Entry<Ingredient, Double>) it
						.next();
				product.addIngredient(dbHelper, ingredientPair.getKey(),
						ingredientPair.getValue());
				Toast.makeText(mContext, "Ingrediente: "+ingredientPair.getKey()+" Cantidad: "+ingredientPair.getValue(), Toast.LENGTH_LONG).show();

				// Log.d("INGREDIENTES","INGREDIENTE: "+ingrediente.getKey().getName()+" "+ingrediente.getValue());
				if(i != 0){
					ingredientes.append(",");
				}
				ingredientes.append("{\"sync_id\": "+ingredientPair.getKey().getSyncId()+",\"qty\": "+ingredientPair.getValue()+"}");
				i++;
			}
			product.refreshIngredients(dbHelper);
			productos.add(product);
			//crear syncrow
			productList.add(new Registrable(product));
			
			
			ingredientes.append("]");
			createSyncRow("\""+Setup.TABLE_PRODUCTS+"\"",product.getId(),product.getSyncId(), "{\""+Setup.COLUMN_NAME+"\": \""+product.getName()+"\",\""+Setup.COLUMN_PRODUCT_AUTOMATIC+"\":"+product.isAutomaticCost()+",\"_id\":"+product.getId()+",\""+Setup.COLUMN_PRODUCT_CATEGORY_ID+"\": "+product.getCategory().getSyncId()+",\""+Setup.COLUMN_PRODUCT_TAX_ID+"\": "+product.getTax().getSyncId()+",\""+Setup.COLUMN_PRODUCT_COST+"\": "+product.getCost()+",\""+Setup.COLUMN_PRODUCT_PRICE+"\": "+product.getPrice()+",\"ingredients\": "+ingredientes.toString()+"}"); //hacer la forma que cuando se reciba esto, se creen los ingredientes (la base de datos debe exportar la forma)

		}else{
			productDatasource.deleteProduct(product);
			
		}
		
	}

	@Override
	public void OnComboCreated(Boolean status,LinkedHashMap<Ingredient, Double> ingredents,
			LinkedHashMap<Product, Double> products, String name, Double cost,
			Double price, Tax tax) {
		if(status){
			Combo combo = comboDatasource.createCombo(name, 0, cost, price, tax,0);
			Iterator<Entry<Ingredient, Double>> it = ingredents.entrySet()
					.iterator();
			StringBuilder ingredientes = new StringBuilder();
			ingredientes.append("[");
			StringBuilder productos = new StringBuilder();
			productos.append("[");
			int i = 0;
			while (it.hasNext()) {
				Map.Entry<Ingredient, Double> ingredientPair = (Map.Entry<Ingredient, Double>) it
						.next();
				combo.addIngredient(dbHelper, ingredientPair.getKey(),
						ingredientPair.getValue());
				
				if(i != 0){
					ingredientes.append(",");
				}
				ingredientes.append("{\"sync_id\": "+ingredientPair.getKey().getSyncId()+",\"qty\": "+ingredientPair.getValue()+"}");
				i++;

				// Log.d("INGREDIENTES","INGREDIENTE: "+ingrediente.getKey().getName()+" "+ingrediente.getValue());
			}
			ingredientes.append("]");
			Iterator<Entry<Product, Double>> pt = products.entrySet().iterator();
			int j = 0;
			while (pt.hasNext()) {
				Map.Entry<Product, Double> productPair = (Map.Entry<Product, Double>) pt
						.next();
				combo.addProduct(dbHelper, productPair.getKey(),
						productPair.getValue());
				
				
				if(j != 0){
					productos.append(",");
				}
				productos.append("{\"sync_id\": "+productPair.getKey().getSyncId()+",\"qty\": "+productPair.getValue()+"}");
				j++;

			}
			productos.append("]");
			createSyncRow("\""+Setup.TABLE_COMBOS+"\"",combo.getId(), combo.getSyncId(), "{\"name\": \""+combo.getName()+"\",\""+Setup.COLUMN_COMBO_AUTOMATIC+"\":"+combo.isAutomaticCost()+",\"_id\":"+combo.getId()+",\""+Setup.COLUMN_COMBO_TAX_ID+"\": "+combo.getTax().getSyncId()+",\""+Setup.COLUMN_COMBO_COST+"\": "+combo.getCost()+",\""+Setup.COLUMN_COMBO_PRICE+"\": "+combo.getPrice()+",\"ingredients\": "+ingredientes.toString()+",\"products\": "+productos.toString()+"}");

			combo.refreshIngredients(dbHelper);
			combo.refreshProducts(dbHelper);
			combos.add(combo);
			comboList.add(new Registrable(combo));
		}else{
			
		}
		

	}

	@Override
	public void OnPayClicked(String method, double value, double discount,int tipp) {
		currentOrder = cookingOrders.get(currentSelectedPosition);
		int togo = 0;
		int delivery = 0;
		try{
			 togo = cookingOrdersTogo.get(currentOrder) != null ? cookingOrdersTogo.get(currentOrder) : 0;
			 delivery = cookingOrdersDelivery.get(currentOrder) != null ? cookingOrdersDelivery.get(currentOrder) : 0;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		int number = checkSaleNumber(); //si falla se resta un numero de las ventas actuales mas adelante,.
		if(number > 0){
			saveSale(method,value,discount,delivery,togo,tipp);
			Date date = new Date();
			String fecha = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(date); 
			//String fecha = DateFormat.getDateFormat(Initialactivity.this).format(
				//	date);

			// imprimir, conectar por wifi y enviar el texto arregladito a la app de
			// puente
			String mesa = "DOMICILIO / PARA LLEVAR";
			if(currentTable != null){
				mesa = currentTable.getTable().getName().toUpperCase(Locale.getDefault());
			}
			int lines = 0;
			StringBuilder factura = new StringBuilder();
			//factura.append("MR. PASTOR COMIDA\r\nRaPIDA MEXICANA" + "\r\n");
			SharedPreferences prefs = Util.getSharedPreferences(mContext);
			String empresa  = prefs.getString(Setup.COMPANY_NAME, "Nombre de Empresa");
			String nit  = prefs.getString(Setup.COMPANY_NIT, "000000000-0");
			String email  = prefs.getString(Setup.COMPANY_EMAIL, "email@empresa.com");
			String pagina  = prefs.getString(Setup.COMPANY_URL, "http://www.empresa.com");
			String direccion  = prefs.getString(Setup.COMPANY_ADDRESS, "Dirección Física Empresa");
			String telefono  = prefs.getString(Setup.COMPANY_TEL, "555-55-55");
			String mensaje  = prefs.getString(Setup.COMPANY_MESSAGE, "No hay ningún mensaje configurado aún. En el mensaje es recomendable mencionar tus redes sociales, benficios y promociones que tengas, además de información de interés paratus clientes. ");
			String propina  = prefs.getString(Setup.TIP_MESSAGE, "No hay ningún mensaje de propina configurado aÃºn. ");
			String resolution  = prefs.getString(Setup.RESOLUTION_MESSAGE, "Resolución de facturación No. 00000-0000 de 1970 DIAN");
			int currentSale = prefs.getInt(Setup.CURRENT_SALE, 0);
			factura.append(empresa + "\r\n");
			factura.append(nit + "\r\n");
			factura.append(direccion + "\r\n");
			factura.append(telefono + "\r\n");
			factura.append(email + "\r\n");
			factura.append(pagina + "\r\n");
			factura.append(resolution + "\r\n");
			factura.append("Factura de Venta No. "+String.valueOf(currentSale)+"\r\n");
			lines++;
			factura.append("\r\n");
			factura.append(fecha);
			factura.append("\r\n");
			factura.append(mesa);
			lines++;
			lines++;
			lines++;
			factura.append("\r\n");
			factura.append("    Item       Cantidad   Precio\r\n");
			lines++;
			Iterator<Entry<Registrable, Integer>> it = currentOrder.entrySet()
					.iterator();
			//////Log.i("MISPRUEBAS","Valor de currentOrder"+String.valueOf(currentOrder.size()));
			// Log.d(TAG,String.valueOf(currentOrder.size()));
			LinkedHashMap<Registrable, Integer> currentObjects = new LinkedHashMap<Registrable, Integer>();
			float base = 0;
			float iva = 0;
			float total = 0;
			ArrayList<String> productos = new ArrayList<String>();
			ArrayList<String> quantities = new ArrayList<String>();
			ArrayList<String> precios = new ArrayList<String>();
			while (it.hasNext()) {

				LinkedHashMap.Entry<Registrable, Integer> pairs = (LinkedHashMap.Entry<Registrable, Integer>) it
						.next();

				currentObjects.put(pairs.getKey(), pairs.getValue());

				String name = pairs.getKey().name;

				int longName = name.length();
				int subLength = 14 - longName;
				if (subLength < 0)
					name = name.substring(0, 14);
				int espacios1 = 4;
				int espacios2 = 12;
				if (name.length() < 14)
				{
					espacios1 += 14 - name.length();
				}
				factura.append(name);
				productos.add(name);
				int qtyL = String.valueOf(pairs.getValue()).length();
				float precioiva = (float)Math.round(pairs.getKey().price + pairs.getKey().price
						* pairs.getKey().tax );
				 base += (float)Math.round(pairs.getKey().price * pairs.getValue());
				 iva += (float)Math.round((pairs.getKey().price * pairs.getKey().tax) * pairs.getValue());
				 total += precioiva * pairs.getValue();
				 
				int priceL = String.valueOf(precioiva).length();
				espacios1 = espacios1 - qtyL < 1 ? espacios1 = 1 : espacios1 - qtyL;
				espacios2 = espacios2 - priceL < 1 ? espacios2 = 1 : espacios2 - priceL;
				espacios2 = espacios2 - qtyL < 1 ? espacios2 = 1 : espacios2 - qtyL;
				for (int k = 0; k < espacios1; k++) {
					factura.append(" ");
				}
				factura.append(pairs.getValue());
				for (int k = 0; k < espacios2; k++) {
					factura.append(" ");
				}
				quantities.add(String.valueOf(pairs.getValue()));
				factura.append("$");
				factura.append(precioiva);
				factura.append("\r\n");
				precios.add("$"+precioiva);
				lines++;
			}
			float propvalue = 0; 
					if(tipp == 1)
						propvalue = (float)Math.round(total * 0.1);
					
			float descuento = 0;
			if(discount > 0){
				descuento = (float) Math.round( base - ( base * ( discount / 0 ) ) );
			}
			lines++;
			lines++;
			factura.append("\r\n");
			factura.append("<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>\r\n");
			factura.append("BASE:      $"+base+"\r\n");
			factura.append("Descuento ("+discount+"):      $"+descuento+"\r\n");
			factura.append("Imp.:      $"+iva+"\r\n");
			factura.append("SUBTOTAL:     $"+Math.round(total - descuento)+"\r\n");
			factura.append("PROPINA:     $"+propvalue+"\r\n");
			float precfinal = propvalue + total - descuento;
			factura.append("TOTAL:     $"+precfinal+"\r\n");
			factura.append("\r\n");
			factura.append("<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>\r\n");
			factura.append("\r\n");
			lines++;
			factura.append(propina + "\r\n");
			factura.append(mensaje);
			String send = factura.toString();
			//////Log.i("MISPRUEBAS",factura.toString());

			// Enviar un string diferente que lleva la orden actual.
		//	new WiFiSend().execute(comanda.toString());// enviar el mensaje de
														// verdad
			
					int[] arrayOfInt = new int[2];
					arrayOfInt[0] = 27;
					arrayOfInt[1] = 64;
					int[] array2 = new int[3];
					array2[0] = 27;
					array2[1] = 74;
					array2[2] = 2;
					StringBuilder builder1 = new StringBuilder();
					for(int h= 0; h<2; h++)
			        {
						builder1.append(Character.toChars(arrayOfInt[h]));
			        }
					StringBuilder builder2 = new StringBuilder();
					
					builder2.append(Character.toChars(10));
			        
			        
			            
			        StringBuilder complete = new StringBuilder(String.valueOf(builder1.toString())).append(String.valueOf(builder2.toString()));
			        String enviar = complete.toString(); 
					Boolean printed = true;
					        try{
					        	if(mChatService.getState() == mChatService.STATE_CONNECTED)
								{
									try {
										mChatService.write(factura.toString().getBytes("x-UnicodeBig"));
									} catch (UnsupportedEncodingException e) {
										e.printStackTrace();
									}
								}
								else
								{
									printed = false;
									Toast.makeText(mContext, "No hay impresora bluetooth conectada.", Toast.LENGTH_LONG).show();
								}
					        }catch(NullPointerException e){
					        	printed = false;
					        	e.printStackTrace();
					        }
					        if(!printed){//buscar impresora TCP/IP
					        	StringBuilder formateado = new StringBuilder();
								formateado.append(CLEAR_PRINTER);
								formateado.append(INITIALIZE_PRINTER);
								formateado.append(JUSTIFICATION_CENTER);
								formateado.append(DOUBLE_WIDE_CHARACTERS);
								formateado.append(empresa);
								formateado.append(SINGLE_WIDE_CHARACTERS);
								formateado.append(PRINT_FEED_ONE_LINE);
								formateado.append(nit);
								formateado.append(PRINT_FEED_ONE_LINE);
								formateado.append(direccion);
								formateado.append(PRINT_FEED_ONE_LINE);
								formateado.append(telefono);
								formateado.append(PRINT_FEED_ONE_LINE);
								formateado.append(email);
								formateado.append(PRINT_FEED_ONE_LINE);
								formateado.append(pagina);
								formateado.append(PRINT_FEED_ONE_LINE);
								formateado.append("Factura de Venta No."+String.valueOf(currentSale));
								formateado.append(PRINT_FEED_ONE_LINE);
								formateado.append(resolution);
								formateado.append(PRINT_FEED_ONE_LINE);
								formateado.append(fecha);
								formateado.append(PRINT_FEED_N_LINES);
								formateado.append((char) 0x02);
								formateado.append(mesa);
								formateado.append(PRINT_FEED_N_LINES);
								formateado.append((char) 0x02);
								formateado.append(DOUBLE_WIDE_CHARACTERS);
								formateado.append(JUSTIFICATION_LEFT);
								formateado.append("ITEM");
								formateado.append(HORIZONTAL_TAB);
								formateado.append("CANT.");
								formateado.append(HORIZONTAL_TAB);
								formateado.append("PRECIO");
								formateado.append(SINGLE_WIDE_CHARACTERS);
								formateado.append(PRINT_FEED_ONE_LINE);
								for(String actual : productos){
									int pos = productos.indexOf(actual);
									String cantidad = quantities.get(pos);
									String precio = precios.get(pos);
									formateado.append(actual);
									formateado.append(HORIZONTAL_TAB);
									formateado.append(HORIZONTAL_TAB);
									formateado.append("x"+cantidad);
									formateado.append(HORIZONTAL_TAB);
									formateado.append(HORIZONTAL_TAB);
									formateado.append(precio);
									formateado.append(PRINT_FEED_ONE_LINE);
								}
								formateado.append(DOUBLE_WIDE_CHARACTERS);
								formateado.append("______________________");
								formateado.append(SINGLE_WIDE_CHARACTERS);
								formateado.append(PRINT_FEED_N_LINES);
								formateado.append((char) 0x02);
								formateado.append("BASE:");
								formateado.append(HORIZONTAL_TAB);
								formateado.append(HORIZONTAL_TAB);
								formateado.append(HORIZONTAL_TAB);
								formateado.append(HORIZONTAL_TAB);
								formateado.append("$"+base);
								formateado.append(PRINT_FEED_ONE_LINE);
								formateado.append("DESCUENTO (:"+discount+"%)");
								formateado.append(HORIZONTAL_TAB);
								formateado.append(HORIZONTAL_TAB);
								formateado.append(HORIZONTAL_TAB);
								formateado.append("$"+descuento);
								formateado.append(PRINT_FEED_ONE_LINE);
								formateado.append("Impuesto:");
								formateado.append(HORIZONTAL_TAB);
								formateado.append(HORIZONTAL_TAB);
								formateado.append(HORIZONTAL_TAB);
								formateado.append("$"+iva);
								formateado.append(PRINT_FEED_ONE_LINE);
								formateado.append("SUBTOTAL:");
								formateado.append(HORIZONTAL_TAB);
								formateado.append(HORIZONTAL_TAB);
								formateado.append(HORIZONTAL_TAB);
								formateado.append("$"+Math.round(total - descuento));
								formateado.append(PRINT_FEED_ONE_LINE);
								formateado.append("PROPINA:");
								formateado.append(HORIZONTAL_TAB);
								formateado.append(HORIZONTAL_TAB);
								formateado.append(HORIZONTAL_TAB);
								formateado.append("$"+propvalue);
								formateado.append(PRINT_FEED_ONE_LINE);
								formateado.append("TOTAL:");
								formateado.append(HORIZONTAL_TAB);
								formateado.append(HORIZONTAL_TAB);
								formateado.append(HORIZONTAL_TAB);
								formateado.append(HORIZONTAL_TAB);
								formateado.append("$"+precfinal);
								formateado.append(PRINT_FEED_ONE_LINE);
								formateado.append(DOUBLE_WIDE_CHARACTERS);
								formateado.append("______________________");
								formateado.append(SINGLE_WIDE_CHARACTERS);
								formateado.append(PRINT_FEED_N_LINES);
								formateado.append((char) 0x02);
								formateado.append(ITALIC_STYLE);
								formateado.append(propina);
								formateado.append(PRINT_FEED_N_LINES);
								formateado.append((char) 0x02);
								formateado.append(mensaje);
								formateado.append(ITALIC_CANCEL);
								formateado.append(FINALIZE_TICKET);
								formateado.append(FULL_CUT);
					        	 if (mTCPPrint != null) {
					        		 if(mTCPPrint.getStatus() == TCPPrint.CONNECTED){
					        			 mTCPPrint.sendMessage(formateado.toString());
					        			 mTCPPrint.sendMessage(formateado.toString());
					        		 }
					        		 else{
					        			 mTCPPrint.stopClient();
					        			 new connectTask().execute(formateado.toString());
					        			 alertbox("¡Oops!", "Al Parecer no hay impresora disponible. Estamos tratando de reconectarnos e imprimir. Si no funciona, reinicia la Red o la impresora y ve a órdenes para imprimir el pedido.");
					        		 }   
					                }else{
					                	alertbox("¡Oops!", "Al Parecer no hay impresora disponible. Trataremos en este momento de nuevo de imprimir el pedido. Si no funciona, reinicia la red o la impreso y ve a órdenes para imprimir de nuevo la orden.");
					                	new connectTask().execute(formateado.toString());
					                }
					        }
			        currentOrder.clear(); //NUEVOO
			        
			        makeTable("NA");
			        
			
		}
		else{
			alertbox("!ATENCIÓN!", "Esta venta no se puede facturar. Este dispositivo no tiene más facturas autorizadas. Consulta el administrador, o si tu lo eres, ve a tu panel de control Nest5 y autoriza más facturas. Para más información: http://soporte.nest5.com");
		}
	}

	private static OnItemClickListener orderListListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int pos,
				long arg3) {
			currentSelectedPosition = pos;
			//Toast.makeText(mContext, String.valueOf(currentSelectedPosition), Toast.LENGTH_LONG).show();
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
	
	@Override
	public void OnPrintSelect(int Type) {
		LinkedHashMap<Registrable, Integer> imprimiendo = cookingOrders.get(currentSelectedPosition);
			Date date = new Date();
			String fecha = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(date); 
			String mesa = "DOMICILIO / PARA LLEVAR";
			CurrentTable<Table, Integer> mesaActual = cookingOrdersTable.get(imprimiendo);
			if(mesaActual != null){
				mesa = mesaActual.getTable().getName().toUpperCase(Locale.getDefault());
			}
			
			StringBuilder factura = new StringBuilder();
			SharedPreferences prefs = Util.getSharedPreferences(mContext);
			String empresa  = prefs.getString(Setup.COMPANY_NAME, "Nombre de Empresa");
			String nit  = prefs.getString(Setup.COMPANY_NIT, "000000000-0");
			String email  = prefs.getString(Setup.COMPANY_EMAIL, "email@empresa.com");
			String pagina  = prefs.getString(Setup.COMPANY_URL, "http://www.empresa.com");
			String direccion  = prefs.getString(Setup.COMPANY_ADDRESS, "Dirección Física Empresa");
			String telefono  = prefs.getString(Setup.COMPANY_TEL, "555-55-55");
			String mensaje  = prefs.getString(Setup.COMPANY_MESSAGE, "No hay ningún mensaje configurado aÃºn. En el mensaje es recomendable mencionar tus redes sociales, benficios y promociones que tengas, además de información de interés paratus clientes. ");
			String propina  = prefs.getString(Setup.TIP_MESSAGE, "No hay ningún mensaje de propina configurado aÃºn. ");
			String resolution  = prefs.getString(Setup.RESOLUTION_MESSAGE, "Resolución de facturación No. 00000-0000 de 1970 DIAN");
			//int currentSale = prefs.getInt(Setup.CURRENT_SALE, 0);
			factura.append("COPIA DE ORDEN\r\n");
			factura.append("NO VÁLIDO COMO FACTURA\r\n");
			factura.append("--------------------\r\n");
			factura.append(empresa + "\r\n");
			factura.append(empresa + "\r\n");
			factura.append(nit + "\r\n");
			factura.append(direccion + "\r\n");
			factura.append(telefono + "\r\n");
			factura.append(email + "\r\n");
			factura.append(pagina + "\r\n");
			factura.append(resolution + "\r\n");
			factura.append("\r\n");
			factura.append(fecha);
			factura.append("\r\n");
			factura.append(mesa);
			factura.append("\r\n");
			factura.append("    Item       Cantidad   Precio\r\n");
			Iterator<Entry<Registrable, Integer>> it = imprimiendo.entrySet()
					.iterator();
			//////Log.i("MISPRUEBAS","Valor de currentOrder"+String.valueOf(currentOrder.size()));
			// Log.d(TAG,String.valueOf(currentOrder.size()));
			LinkedHashMap<Registrable, Integer> currentObjects = new LinkedHashMap<Registrable, Integer>();
			float base = 0;
			float iva = 0;
			float total = 0;
			ArrayList<String> productos = new ArrayList<String>();
			ArrayList<String> quantities = new ArrayList<String>();
			ArrayList<String> precios = new ArrayList<String>();
			while (it.hasNext()) {

				LinkedHashMap.Entry<Registrable, Integer> pairs = (LinkedHashMap.Entry<Registrable, Integer>) it
						.next();

				currentObjects.put(pairs.getKey(), pairs.getValue());

				String name = pairs.getKey().name;

				int longName = name.length();
				int subLength = 14 - longName;
				if (subLength < 0)
					name = name.substring(0, 14);
				int espacios1 = 4;
				int espacios2 = 12;
				if (name.length() < 14)
				{
					espacios1 += 14 - name.length();
				}
				factura.append(name);
				productos.add(name);
				int qtyL = String.valueOf(pairs.getValue()).length();
				float precioiva = (float)Math.round(pairs.getKey().price + pairs.getKey().price
						* pairs.getKey().tax );
				 base += (float)Math.round(pairs.getKey().price * pairs.getValue());
				 iva += (float)Math.round((pairs.getKey().price * pairs.getKey().tax) * pairs.getValue());
				 total += precioiva * pairs.getValue();
				 
				int priceL = String.valueOf(precioiva).length();
				espacios1 = espacios1 - qtyL < 1 ? espacios1 = 1 : espacios1 - qtyL;
				espacios2 = espacios2 - priceL < 1 ? espacios2 = 1 : espacios2 - priceL;
				espacios2 = espacios2 - qtyL < 1 ? espacios2 = 1 : espacios2 - qtyL;
				for (int k = 0; k < espacios1; k++) {
					factura.append(" ");
				}
				factura.append(pairs.getValue());
				quantities.add(String.valueOf(pairs.getValue()));
				for (int k = 0; k < espacios2; k++) {
					factura.append(" ");
				}
				factura.append("$");
				factura.append(precioiva);
				factura.append("\r\n");
				precios.add("$"+precioiva);
			}
			factura.append("\r\n");
			factura.append("<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>\r\n");
			factura.append("BASE:      $"+base+"\r\n");
			factura.append("Imp.:      $"+iva+"\r\n");
			factura.append("SUBTOTAL:     $"+total+"\r\n");
			factura.append("\r\n");
			factura.append("<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>\r\n");
			factura.append("\r\n");
			factura.append(propina + "\r\n");
			factura.append(mensaje);

					Boolean printed = true;
					        try{
					        	if(mChatService.getState() == mChatService.STATE_CONNECTED)
								{
									try {
										mChatService.write(factura.toString().getBytes("x-UnicodeBig"));
									} catch (UnsupportedEncodingException e) {
										e.printStackTrace();
									}
								}
								else
								{
									printed = false;
									Toast.makeText(mContext, "No hay impresora bluetooth conectada.", Toast.LENGTH_LONG).show();
								}
					        }catch(NullPointerException e){
					        	printed = false;
					        	e.printStackTrace();
					        }
					        if(!printed){//buscar impresora TCP/IP
					        	StringBuilder formateado = new StringBuilder();
								formateado.append(CLEAR_PRINTER);
								formateado.append(INITIALIZE_PRINTER);
								formateado.append(JUSTIFICATION_CENTER);
								formateado.append(DOUBLE_WIDE_CHARACTERS);
								formateado.append("CUENTA PEDIDO No."+String.valueOf(currentSelectedPosition + 1));
								formateado.append(PRINT_FEED_ONE_LINE);
								formateado.append(fecha);
								formateado.append(PRINT_FEED_N_LINES);
								formateado.append((char) 0x02);
								formateado.append(mesa);
								formateado.append(PRINT_FEED_N_LINES);
								formateado.append((char) 0x02);
								formateado.append(SINGLE_WIDE_CHARACTERS);
								formateado.append("NO VÁLIDO COMO FACTURA DE VENTA");
								formateado.append(PRINT_FEED_ONE_LINE);
								formateado.append(PRINT_FEED_N_LINES);
								formateado.append((char) 0x03);
								formateado.append(DOUBLE_WIDE_CHARACTERS);
								formateado.append(JUSTIFICATION_LEFT);
								formateado.append("ITEM");
								formateado.append(HORIZONTAL_TAB);
								formateado.append("CANT.");
								formateado.append(HORIZONTAL_TAB);
								formateado.append("PRECIO");
								formateado.append(SINGLE_WIDE_CHARACTERS);
								formateado.append(PRINT_FEED_ONE_LINE);
								for(String actual : productos){
									int pos = productos.indexOf(actual);
									String cantidad = quantities.get(pos);
									String precio = precios.get(pos);
									formateado.append(actual);
									formateado.append(HORIZONTAL_TAB);
									formateado.append(HORIZONTAL_TAB);
									formateado.append("x"+cantidad);
									formateado.append(HORIZONTAL_TAB);
									formateado.append(HORIZONTAL_TAB);
									formateado.append(precio);
									formateado.append(PRINT_FEED_ONE_LINE);
								}
								formateado.append(DOUBLE_WIDE_CHARACTERS);
								formateado.append("______________________");
								formateado.append(SINGLE_WIDE_CHARACTERS);
								formateado.append(PRINT_FEED_N_LINES);
								formateado.append((char) 0x02);
								formateado.append("BASE:");
								formateado.append(HORIZONTAL_TAB);
								formateado.append(HORIZONTAL_TAB);
								formateado.append(HORIZONTAL_TAB);
								formateado.append(HORIZONTAL_TAB);
								formateado.append("$"+base);
								formateado.append(PRINT_FEED_ONE_LINE);
								formateado.append("Impuesto:");
								formateado.append(HORIZONTAL_TAB);
								formateado.append(HORIZONTAL_TAB);
								formateado.append(HORIZONTAL_TAB);
								formateado.append("$"+iva);
								formateado.append(PRINT_FEED_ONE_LINE);
								formateado.append("TOTAL:");
								formateado.append(HORIZONTAL_TAB);
								formateado.append(HORIZONTAL_TAB);
								formateado.append(HORIZONTAL_TAB);
								formateado.append("$"+total);
								formateado.append(PRINT_FEED_ONE_LINE);
								formateado.append(DOUBLE_WIDE_CHARACTERS);
								formateado.append("______________________");
								formateado.append(SINGLE_WIDE_CHARACTERS);
								formateado.append(PRINT_FEED_N_LINES);
								formateado.append((char) 0x02);
								formateado.append(ITALIC_STYLE);
								formateado.append(propina);
								formateado.append(PRINT_FEED_ONE_LINE);
								formateado.append("Â¿DESEA INCLUIRLA?");
								formateado.append(PRINT_FEED_ONE_LINE);
								formateado.append("SI: |____|");
								formateado.append(HORIZONTAL_TAB);
								formateado.append("NO:|____|");
								formateado.append(PRINT_FEED_N_LINES);
								formateado.append((char) 0x02);
								formateado.append(mensaje);
								formateado.append(ITALIC_CANCEL);
								formateado.append(FINALIZE_TICKET);
								formateado.append(FULL_CUT);
					        	 if (mTCPPrint != null) {
					        		 if(mTCPPrint.getStatus() == TCPPrint.CONNECTED){
					        			 mTCPPrint.sendMessage(formateado.toString());
					        			 mTCPPrint.sendMessage(formateado.toString());
					        		 }
					        		 else{
					        			 mTCPPrint.stopClient();
					        			 new connectTask().execute(formateado.toString());
					        			 alertbox("Â¡Oops!", "Al Parecer no hay impresora disponible. Estamos tratando de reconectarnos e imprimir. Si no funciona, reinicia la Red o la impresora y ve a órdenes para imprimir el pedido.");
					        		 }   
					                }else{
					                	alertbox("Â¡Oops!", "Al Parecer no hay impresora disponible. Trataremos en este momento de nuevo de imprimir el pedido. Si no funciona, reinicia la red o la impreso y ve a órdenes para imprimir de nuevo la orden.");
					                	new connectTask().execute(formateado.toString());
					                }
					        }
			        //currentOrder.clear(); //NUEVOO
			        //makeTable("NA");
							List<Long> items = new ArrayList<Long>();
							List<String> nameTables = new ArrayList<String>();
							for (LinkedHashMap<Registrable, Integer> current : cookingOrders) {
								items.add(cookingOrdersTimes.get(current));
								nameTables.add(cookingOrdersTable.get(current).getTable().getName());
							}
							cookingAdapter = new SaleAdapter(mContext, items, nameTables,inflater);
					ordersList.setAdapter(cookingAdapter);
					ordersList.setOnItemClickListener(orderListListener);
			        currentSelectedPosition = -1;
			        
			
		
		
	};
	
	@Override
	public void OnTableActionSelect(int Type) {
		if(Type == CloseTableForm.CANCEL_ORDER){
			if (currentTable == null)
				return;
			LinkedHashMap<Registrable,Integer> currentSale = null;
			for(Entry<LinkedHashMap<Registrable, Integer>, CurrentTable<Table, Integer>> mesa : cookingOrdersTable.entrySet()){
				if(mesa.getValue().getTable().getName().equalsIgnoreCase(currentTable.getTable().getName())){
					currentSale = mesa.getKey();
					break;
				}
			}
			if(currentSale != null){
				//currentTable = null;
				cookingOrders.remove(currentSale);
				cookingOrdersDelivery.remove(currentSale);
				cookingOrdersTimes.remove(currentSale);
				cookingOrdersTogo.remove(currentSale);
				cookingOrdersTable.remove(currentSale);
				for(CurrentTable<Table, Integer> actual : openTables){
					if(actual.getTable().getName().equalsIgnoreCase(currentTable.getTable().getName())){
						openTables.remove(actual);
						break;
					}
				}
				currentTable = null;
				statusText.setText("Orden de Mesa cancelada con éxito.");
			}
			
			List<Long> items = new ArrayList<Long>();
			List<String> nameTables = new ArrayList<String>();
			for (LinkedHashMap<Registrable, Integer> current : cookingOrders) {
				items.add(cookingOrdersTimes.get(current));
				nameTables.add(cookingOrdersTable.get(current).getTable().getName());
			}
			cookingAdapter = new SaleAdapter(mContext, items, nameTables,inflater);
			ordersList.setAdapter(cookingAdapter);
			ordersList.setOnItemClickListener(orderListListener);
			
		}
		if(Type == CloseTableForm.PRINT_INVOICE){
			if (currentTable == null)
				return;
			for(Entry<LinkedHashMap<Registrable, Integer>, CurrentTable<Table, Integer>> mesa : cookingOrdersTable.entrySet()){
				if(mesa.getValue().getTable().getName().equalsIgnoreCase(currentTable.getTable().getName())){
					currentOrder = mesa.getKey();
					currentSelectedPosition =  cookingOrders.indexOf(mesa.getKey());
					break;
				}
			}
			
			
			if(currentOrder == null)
				return;
			showPaymentFormDialog();
		}
	}
	
	private void onZReportSelect(){
		SharedPreferences prefs = Util.getSharedPreferences(mContext);
		mResetProgressDialog = new ProgressDialog(mContext);
		mResetProgressDialog
				.setMessage("Recibiendo Información Actualizada...");
		mResetProgressDialog.setCancelable(false);
		mResetProgressDialog.setIndeterminate(true);
		mResetProgressDialog.show();
		restService = new RestService(receivedZReport,
				mContext, Setup.PROD_BIGDATA_URL
						+ "/databaseOps/zReport");
		restService.addParam("company",
				prefs.getString(Setup.COMPANY_ID, "0"));
		restService.addParam("reportDate",
				"04/01/2014-04/30/2014");
		restService
				.setCredentials("apiadmin", Setup.apiKey);
		try {
			restService.execute(RestService.POST);
		} catch (Exception e) {
			e.printStackTrace();
			////Log.i("MISPRUEBAS", "Error empezando request");
		}
	}

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
						"La conexión falló. Reinténtalo de nuevo.",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void disconnect() {

		manager.removeGroup(channel, new ActionListener() {

			@Override
			public void onFailure(int reasonCode) {
				Log.d(TAG, "La desconexión falló, la razón es:" + reasonCode);

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
			Toast.makeText(
					this,
					"Se perdió la conexión con el canal, inténtalo de nuevo.",
					Toast.LENGTH_LONG).show();
			// resetData();
			retryChannel = true;
			manager.initialize(this, getMainLooper(), this);
		} else {
			Toast.makeText(
					this,
					"óError grave!. Se perdió por completo la conexión con dispositivos. Reintenta Desactivando/Activando WifiDirect otra vez.",
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

	protected static void startTimer() {
		isTimerRunning = true;
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				

			}
		}, 0, 60000);
	}
	
	private static class ContadorMinutos implements Runnable{
		  private WeakReference<Initialactivity> mActivity;
		  public ContadorMinutos(Initialactivity activity){
			  mActivity = new WeakReference<Initialactivity>(activity);
		  }
		public void run() {
    	  currentTime = System.currentTimeMillis(); // increase every sec
    	  threadMsg();
      }
      private void threadMsg() {
    	  	Initialactivity activity = mActivity.get();
    	  	activity.mHandler.postDelayed(activity.contador, 60 * 1000);  
    	  	activity.mHandler.sendEmptyMessage(0);
            
      }
  }
public static class MHandler extends Handler {
		private WeakReference<Initialactivity> mActivity;
		
		public MHandler(Initialactivity activity){
			mActivity = new WeakReference<Initialactivity>(activity);
		}

		public void handleMessage(Message msg) {
			Initialactivity activity = mActivity.get();
			List<Long> items = new ArrayList<Long>();
			List<String> nameTables = new ArrayList<String>();
			for (LinkedHashMap<Registrable, Integer> current : cookingOrders) {
				items.add(cookingOrdersTimes.get(current));
				nameTables.add(cookingOrdersTable.get(current).getTable().getName());
			}
			SaleAdapter cookingAdapter = new SaleAdapter(activity, items, nameTables,inflater);
			ordersList.setAdapter(cookingAdapter);
		}
	};
	
	/*BLUETOOTH*/
	@Override
	public void onDeviceSelected(String address) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "address--->"+address, Toast.LENGTH_SHORT).show();
        // Get the BLuetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        try {
			device.createRfcommSocketToServiceRecord(uuid);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        // Attempt to connect to the device
        mChatService.connect(device); 
        //mSerialService.connect(device);
	}
	
	

	

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
				if(pair.getKey() != null)
					total += (pair.getValue() * pair.getKey().getPrice());

				// Log.d("INGREDIENTES","INGREDIENTE: "+ingrediente.getKey().getName()+" "+ingrediente.getValue());
			}
			DecimalFormat dec = new DecimalFormat("$###,###,###");

			saleValue.setText("Ventas del DÃ­a: " + dec.format(total));

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
					"AtenciÃ³n",
					"Para leer una tarjeta Nest5 debes subir el volumen de tu dispositivo al máximo.");
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
		if (!prefs.getBoolean(Setup.DEVICE_REGISTERED, false)) { //forces logout so the user logs in again and the device gets registered
			prefs.edit().putBoolean(Setup.LOGGED_IN, false)
					.putString(Setup.COMPANY_ID, "0")
					.putString(Setup.COMPANY_NAME, "N/A")
					.putBoolean(Setup.DEVICE_REGISTERED, false).commit();
			Intent inten = new Intent(mContext, LoginActivity.class);
			startActivity(inten);
		}
	}

	private void backUpDb() {
		
		//si hay syncrows sin guardar no descarga nada sino que las sube y luego la descargarÂ¿, si no lo logra, no baja nada y notifica
		//por elmomento si hay filas, se trata de subir si hay conexion a internt y se dice q se vuelva a undri el boton
		
		List<SyncRow> syncRows = null;
		syncRowDataSource.open();
		syncRows = syncRowDataSource.getAllSyncRows();
		if(syncRows.size() > 0){//avisa que habia filas sin guardar en el servidor, revisa internet, si no hay avisa, si lo hay las sube y dice que vuelva a intentar
			if(isConnectedToInternet()){
				sendAllSyncRows();
				//alerta
				alertbox("¡Oops!", "Parece que hay registros de ventas o inventario sin guardar. Intentaremos sincronizar de nuevo y luego por favor presiona el botón otra vez.");
			}
			else{//avisa que no hay internet, si el problema persiste entrar a soporte.nest5.com
				//alerta
				alertbox("¡Oops!", "Para esta operación debes estar conectado a Internet. Conéctate y vuelve a presionar el botón.");
			}
		}
		else{
			if(isConnectedToInternet()){
				mResetProgressDialog = new ProgressDialog(mContext);
				mResetProgressDialog.setMessage("Sincronizando Base de Datos, esto puede tardar unos minutos...");
				mResetProgressDialog.setCancelable(false);
				mResetProgressDialog.setIndeterminate(true);
				mResetProgressDialog.show();

				saveFileRecord();
			}
			else{
				alertbox("¡Oops!", "Para esta operación debes estar conectado a Internet. Conéctate y vuelve a presionar el botón.");
			}
			
		}

		

	}
	


	// Amazon S3 Upload Images guarda el archivo en base de datos recibe nombre
	// a usar para el archivo y sube a s3

	private void saveFileRecord() {
		if(isConnectedToInternet()){
			mResetProgressDialog
			.setMessage("Actualizando informaciÃ³n. Esto puede tardar unos minutos.");
			mResetProgressDialog.setCancelable(false);
			mResetProgressDialog.setIndeterminate(true);
			mResetProgressDialog.show();
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			dbHelper.onUpgrade(db, 0, 1);
			Runnable runnable = new Runnable() {
		        public void run() {
		        	try {
		    			  downloadFile();
		    		  } catch (Exception e) {}	
		        	databasehandler.sendEmptyMessage(0);    
		    }
		  };
		  
		  Thread mythread = new Thread(runnable);
		  mythread.start();  
		}
		else{
			alertbox("Â¡Oops!", "Para esta operaciÃ³n debes estar conectado a Internet. Conéctate y vuelve a presionar el botÃ³n.");
		}
		 
		
		
	}

	/*private final Handler fileSavedHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// mResetProgressDialog.dismiss();

			// temporal abre actividad loggeado
			
			JSONObject respuesta = null;
			////Log.i("MISPRUEBAS", "LLEGUE");
			try {
				respuesta = new JSONObject((String) msg.obj);
			} catch (Exception e) {
				////Log.i("MISPRUEBAS", "ERROR JSON");
				e.printStackTrace();
				mResetProgressDialog.dismiss();
				Toast.makeText(mContext,
						"Error guardando registro de backup de base de datos",
						Toast.LENGTH_LONG).show();
			}

			if (respuesta != null) {
				////Log.i("MISPRUEBAS", "CON RESPUESTA");
				int status = 0;
				String name = "";

				try {
					status = respuesta.getInt("status");
					name = respuesta.getString("name");

				} catch (Exception e) {
					////Log.i("MISPRUEBAS", "ERROR COGER DATOS");
					e.printStackTrace();
					mResetProgressDialog.dismiss();
					Toast.makeText(
							mContext,
							"Error guardando registro de backup de base de datos",
							Toast.LENGTH_LONG).show();
				}
				// quitar loading

				if (status == 1) {
					////Log.i("MISPRUEBAS", "listo");
					// Abrir Nueva Activity porque esta registrado
					// Toast.makeText(mContext, "Datos guardados con ÃƒÂ©xito.",
					// Toast.LENGTH_LONG).show();
					File file = DbExportImport.exportDb(name);
					UploadFileToS3 uploadTask = new UploadFileToS3();
					uploadTask.execute(file);
				} else {
					////Log.i("MISPRUEBAS", "noooo");
					mResetProgressDialog.dismiss();
					Toast.makeText(
							mContext,
							"Error guardando registro de backup de base de datos",
							Toast.LENGTH_LONG).show();
				}

			} else {
				mResetProgressDialog.dismiss();
				Toast.makeText(mContext,
						"Error guardando registro de backup de base de datos",
						Toast.LENGTH_LONG).show();
			}

		}
	};*/

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
			AWSCredentials credentials = new BasicAWSCredentials(accessKey,
					secretKey);
			AmazonS3 conn = new AmazonS3Client(credentials);
			try {
				////Log.i("MISPRUEBAS", "subiendo archivo");
				conn.putObject("com.nest5.businessClient", params[0].getName(),
						params[0]);
			} catch (com.amazonaws.AmazonServiceException e) {
				////Log.i("MISPRUEBAS", "exeption 1");
				e.printStackTrace();

				return false;

			} catch (com.amazonaws.AmazonClientException e) {
				////Log.i("MISPRUEBAS", "exeption 2");
				e.printStackTrace();

				return false;

			}

			// TODO: register the new account here.

			return true;
		}

		@Override
		protected void onPostExecute(final Boolean status) {
			mResetProgressDialog.dismiss();
			if (status)
				Toast.makeText(mContext, "Datos guardados con éxito.",
						Toast.LENGTH_LONG).show();
			else
				Toast.makeText(
						mContext,
						"Error guardando el archivo en la nube, inéntalo de nuevo por favor..",
						Toast.LENGTH_LONG).show();
			////Log.i("MISPRUEBAS", "lelgo al final de la asynctask");
			// mResetProgressDialog.dismiss();
			// Guardar referencia a archivo y empresa en nest5.

		}

		@Override
		protected void onCancelled() {

		}

	}

	/*
	 * Recibe datos de promocion y usuario al leer tarjeta magnetica, o al
	 * enviar email de usuario a API
	 */
	private final Handler recievePromoandUserHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			mResetProgressDialog.dismiss();
			Promo[] promos = null;
			User user = null;
			JSONObject respuesta = null;
			String mensaje = "Error de Comunicación con Nest5, inténtalo de nuevo por favor.";
			int status = 0;
			try {
				respuesta = new JSONObject((String) msg.obj);
			} catch (Exception e) {
				showMessageDialog("ERROR", mensaje);
			}
			if (respuesta != null) {
				try {
					mensaje = respuesta.getString("message");
					status = respuesta.getInt("status");
				} catch (Exception e) {
					showMessageDialog("ERROR", mensaje);
				}
				if (status == 1) {
					try {
						Gson gson = new Gson();
						Gson gson2 = new Gson();
						promos = gson.fromJson(
								(String) respuesta.getString("promos"),
								Promo[].class);
						user = gson2.fromJson(
								(String) respuesta.getString("user"),
								User.class);
					} catch (Exception e) {
						showMessageDialog("ERROR", mensaje);
						e.printStackTrace();
					}
					// showMessageDialog("Listo", "Promo:" +
					// promos[0].name+" Usuario: "+user.name);

					// Llenar lista con adaptador de array
					companyPromos = Arrays.asList(promos);
					currentUser = user;
					ListView list = (ListView) findViewById(R.id.manual_variable_list);

					ArrayAdapter<Promo> promociones = new ArrayAdapter<Promo>(
							mContext, android.R.layout.simple_list_item_1,
							promos) {
						@Override
						public View getView(int position, View convertView,
								ViewGroup parent) {
							TextView textView = (TextView) super.getView(
									position, convertView, parent);
							Promo obj = getItem(position);
							String name = obj.name + " En: " + obj.store;
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
							// enviar empresa, usuario y promocion que sellara
							mResetProgressDialog = new ProgressDialog(mContext);
							mResetProgressDialog
									.setMessage("Sellando Tarjeta Nest5 de "
											+ currentUser.name + "...");
							mResetProgressDialog.setCancelable(false);
							mResetProgressDialog.setIndeterminate(true);
							mResetProgressDialog.show();
							int promoid = companyPromos.get(position).id;
							int userid = currentUser.id;
							SharedPreferences prefs = Util
									.getSharedPreferences(mContext);
							restService = new RestService(
									recieveStampsAndCouponsUser, mContext,
									Setup.PROD_URL + "/promo/beLucky");

							restService.addParam("code",
									Integer.toString(promoid));
							restService.addParam("id", Integer.toString(userid));
							restService.addParam("frombusiness", "claro");
							restService
									.setCredentials("apiadmin", Setup.apiKey);
							try {
								restService.execute(RestService.POST);
							} catch (Exception e) {
								e.printStackTrace();
								////Log.i("MISPRUEBAS", "Error empezando request");
							}

						}
					});

				} else {
					showMessageDialog("ERROR", mensaje);
				}

			} else {
				showMessageDialog("ERROR", mensaje);
			}

		}

	};

	/*
	 * recieveRedeemConfirm Redime beneficios del usuario, recibe las
	 * promociones y el nÃºmero de cupones del usuario en cada una
	 */
	private final Handler recieveRedeemConfirm = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			mResetProgressDialog.dismiss();
			Promo[] promos = null;
			User user = null;
			JSONObject respuesta = null;
			String mensaje = "Error de Comunicación con Nest5, inténtalo de nuevo por favor.";
			int status = 0;
			try {
				respuesta = new JSONObject((String) msg.obj);
			} catch (Exception e) {
				showMessageDialog("ERROR", mensaje);
			}
			if (respuesta != null) {
				try {
					mensaje = respuesta.getString("message");
					status = respuesta.getInt("status");
				} catch (Exception e) {
					showMessageDialog("ERROR", mensaje);
				}
				if (status == 1) {
					try {
						Gson gson = new Gson();
						Gson gson2 = new Gson();
						promos = gson.fromJson(
								(String) respuesta.getString("promos"),
								Promo[].class);
						user = gson2.fromJson(
								(String) respuesta.getString("user"),
								User.class);
					} catch (Exception e) {
						showMessageDialog("ERROR", mensaje);
						e.printStackTrace();
					}
					// showMessageDialog("Listo", "Promo:" +
					// promos[0].name+" Usuario: "+user.name);

					// Llenar lista con adaptador de array
					companyPromos = Arrays.asList(promos);
					currentUser = user;
					ListView list = (ListView) findViewById(R.id.manual_variable_list);

					ArrayAdapter<Promo> promociones = new ArrayAdapter<Promo>(
							mContext, android.R.layout.simple_list_item_1,
							promos) {
						@Override
						public View getView(int position, View convertView,
								ViewGroup parent) {
							TextView textView = (TextView) super.getView(
									position, convertView, parent);
							Promo obj = getItem(position);
							String name = obj.name + " En: " + obj.store;
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
							// enviar empresa, usuario y promocion que sellara
							mResetProgressDialog = new ProgressDialog(mContext);
							mResetProgressDialog
									.setMessage("Redimiendo Beneficio Nest5 de "
											+ currentUser.name + "...");
							mResetProgressDialog.setCancelable(false);
							mResetProgressDialog.setIndeterminate(true);
							mResetProgressDialog.show();
							int promoid = companyPromos.get(position).id;
							int userid = currentUser.id;
							SharedPreferences prefs = Util
									.getSharedPreferences(mContext);
							restService = new RestService(recieveRedeemedUser,
									mContext, Setup.PROD_URL
											+ "/promo/redeemCouponBusiness");

							restService.addParam("code",
									Integer.toString(promoid));
							restService.addParam("id", Integer.toString(userid));
							restService.addParam("frombusiness", "claro");
							restService
									.setCredentials("apiadmin", Setup.apiKey);
							try {
								restService.execute(RestService.POST);
							} catch (Exception e) {
								e.printStackTrace();
								////Log.i("MISPRUEBAS", "Error empezando request");
							}

						}
					});

				} else {
					showMessageDialog("ERROR", mensaje);
				}

			} else {
				showMessageDialog("ERROR", mensaje);
			}

		}

	};

	/*
	 * Recibe datos de promocion y usuario al leer tarjeta magnetica, o al
	 * enviar email de usuario a API
	 */
	private final Handler recieveStampsAndCouponsUser = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			mResetProgressDialog.dismiss();
			JSONObject respuesta = null;
			String mensaje = "Error de Comunicación con Nest5, inténtalo de nuevo por favor.";
			int status = 0;
			int sellos = 0;
			int coupones = 0;
			try {
				respuesta = new JSONObject((String) msg.obj);
			} catch (Exception e) {
				////Log.i("MISPRUEBAS", "ERROR 0");
				showMessageDialog("ERROR", mensaje);

			}
			if (respuesta != null) {
				try {

					status = respuesta.getInt("status");

				} catch (Exception e) {
					////Log.i("MISPRUEBAS", "ERROR 1");
					showMessageDialog("ERROR", mensaje);
				}
				if (status == 1) {
					try {
						sellos = respuesta.getInt("stamps");
						coupones = respuesta.getInt("coupons");
					} catch (Exception e) {
						showMessageDialog("ERROR", mensaje);
						////Log.i("MISPRUEBAS", "ERROR 2");
					}
					showMessageDialog("Tarjeta sellada con Éxito",
							currentUser.name + " con este ha acumulado "
									+ sellos + " sellos y " + coupones
									+ " cupones.");

				} else {
					////Log.i("MISPRUEBAS", "ERROR 3");
					showMessageDialog("ERROR", mensaje);
				}

			} else {
				////Log.i("MISPRUEBAS", "ERROR 4");
				showMessageDialog("ERROR", mensaje);
			}

		}

	};

	/*
	 * recieveRedeemedUser recive si redimio bien el beneficio
	 */
	private final Handler recieveRedeemedUser = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			mResetProgressDialog.dismiss();
			JSONObject respuesta = null;
			String mensaje = "Error de Comunicación con Nest5, inténtalo de nuevo por favor.";
			int status = 0;

			try {
				respuesta = new JSONObject((String) msg.obj);
			} catch (Exception e) {
				////Log.i("MISPRUEBAS", "ERROR 0");
				showMessageDialog("ERROR", mensaje);

			}
			if (respuesta != null) {
				try {

					status = respuesta.getInt("status");

				} catch (Exception e) {
					////Log.i("MISPRUEBAS", "ERROR 1");
					showMessageDialog("ERROR", mensaje);
				}
				if (status == 1) {

					showMessageDialog(
							"Beneficio redimido con Éxito",
							currentUser.name
									+ " ha redimido un beneficio y ahora enamóralo entregándoselo.");

				} else {
					////Log.i("MISPRUEBAS", "ERROR 3");
					showMessageDialog("ERROR", mensaje);
				}

			} else {
				////Log.i("MISPRUEBAS", "ERROR 4");
				showMessageDialog("ERROR", mensaje);
			}

		}

	};
	
	
	//handle upload row to big data server
	private final Handler dataRowSent = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			 
			//mResetProgressDialog.dismiss();	
			JSONObject respuesta = null;
				////Log.i("MISPRUEBAS","LLEGUE DE subir fila");
				totalSync--;//no importa lo que pase, cada que trata de subir una fila dice que lo hizo, solo borra la syncrow de la base de ddatos si se guarda nuevo documento o se actualiza, de resto queda ahi, para un próximo intento
			try {
				respuesta = new JSONObject((String) msg.obj);
			} catch (Exception e) {
				////Log.i("MISPRUEBAS","ERROR JSON en subir row");
				e.printStackTrace();
				//no se hace nada, la fila no se pudo subir y se debe subir de nuevo luego
			}

			if (respuesta != null) {
				//////Log.i("MISPRUEBAS","CON RESPUESTA de subir row");
				int status = 0;
				int responsecode = 0;
				String message = "";
				Long sync_id = 0L;
				Long sync_row = 0L;
				try {
					status = respuesta.getInt("status");
					responsecode = respuesta.getInt("code");
					message = respuesta.getString("message");
					//Log.w("MISPRUEBAS","Mesnaje del Servidor: "+message);
					//Log.w("MISPRUEBAS","Objeto respuesta: "+respuesta.toString());

				} catch (Exception e) {
					////Log.i("MISPRUEBAS","ERROR COGER DATOS al subir row");
					e.printStackTrace();
				}
				// quitar loading
				
				
				if (status == 201) { //se creo un objeto nuevo exitosamente, se debe actualizar el sync_id con el payload 
					//ok! status received, but still we have to check for code 555 that says everything done in Nest5 as expected.
					if(responsecode == 555 ){
						try {
							sync_id = respuesta.getLong("syncId");
							sync_row = respuesta.getLong("syncRow");
							//////Log.i("MISPRUEBAS","valores sync_id y sync_row: "+String.valueOf(sync_id)+" --- "+String.valueOf(sync_row));

						} catch (Exception e) {
							////Log.i("MISPRUEBAS","ERROR cogiendo el syncId o el syncRow enviado por el servidor");
							e.printStackTrace();
						}
						if((sync_id != 0L) && (sync_row != 0L)){//se debe actualizar el valor en el objeto local porque fue creado como nuevo con éxito en el servidor
							SyncRow sync = syncRowDataSource.getSyncRow(sync_row);
							String table = null;
							Long id = null;
							
							try{
								table = sync.getTable();
								id = sync.getRowId();
								//////Log.i("MISPRUEBAS","valores table y id: "+table+" --- "+String.valueOf(id));
								if(updateSyncIdInRow(table,id,Setup.COLUMN_SYNC_ID,sync_id) > 0)
									deleteSyncRow(sync_row);
							}
							catch(Exception e){
								Log.e("MISPRUEBAS",e.toString());
							}
						}
					}
					else{//se presentó un error desconocido
						Log.w("MISPRUEBAS","ERROR desconocido al subir syncRow");
					}
					
					
					
				} else {
					if(status == 200){ //se actualizó una fila, 
						//ok! status received, but still we have to check for code 555 that says everything done in Nest5 as expected.
						if(responsecode == 555 ){
							try {
								sync_row = respuesta.getLong("syncRow");

							} catch (Exception e) {
								////Log.i("MISPRUEBAS","ERROR cogiendo el syncId o el syncRow enviado por el servidor");
								e.printStackTrace();
							}
							if((sync_row != 0L)){//se debe actualizar el valor en el objeto local porque fue creado como nuevo con éxito en el servidor
								try{
									deleteSyncRow(sync_row);
										
								}
								catch(Exception e){
									Log.e("ERRORES",e.toString());
								}
							}
						}
						else{//error desconocido
							Log.w("MISPRUEBAS","ERROR desconocido al subir syncRow 2");
						}
					}
					else{
						if(status == 406){//se hizo overlap con otra fila enviada desde otro dispositivo
							if(responsecode == 55513){ //confirmar que el http 406 si haya sido enviado porque el error era de overlap, el código 13 dice que si
								//se debe pedir que manden una actualización de la fila, para tomar el valor que puso otro dispositivo que no permitió que este se guardara por obverlap
								try {
									sync_row = respuesta.getLong("syncRow");

								} catch (Exception e) {
									////Log.i("MISPRUEBAS","ERROR cogiendo el syncId o el syncRow enviado por el servidor");
									e.printStackTrace();
								}
								if((sync_row != 0L)){//se debe actualizar el valor en el objeto local porque fue creado como nuevo con éxito en el servidor
									try{
										deleteSyncRow(sync_row);

									}
									catch(Exception e){
										Log.e("ERRORES",e.toString());
									}
								}
							}
						}
					}
				}
			}
			else{
				//no hubo respuesta del servidor
			}
		//	if(totalSync == 0)
				//mResetProgressDialog.dismiss(); //ya no se muestra espera, solo se hace si hay internet, si hay errorres guarda row, si no los hay bien, si no hay internet guarda syncrow

		}

		
	};
	
	private final Handler updateMaxHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			prefs = Util.getSharedPreferences(mContext);
			JSONObject respuesta = null;
			try {
				respuesta = new JSONObject((String) msg.obj);
			} catch (Exception e) {
				////Log.i("MISPRUEBAS","ERROR JSON en updateMaxHandler");
				e.printStackTrace();
			}

			if (respuesta != null) {
				////Log.i("MISPRUEBAS","CON RESPUESTA de updateMaxHandler");
				int status = 0;
				int responsecode = 0;
				String message = "";
				try {
					status = respuesta.getInt("status");
					responsecode = respuesta.getInt("code");
					message = respuesta.getString("message");

				} catch (Exception e) {
					////Log.i("MISPRUEBAS","ERROR COGER DATOS updateMaxHandler");
					e.printStackTrace();
				}
				
				////Log.i("MISPRUEBAS","ojo: "+String.valueOf(status)+" "+message);

				if (status == 200) {
					
					if(responsecode == 555){ 
						//if registered, it doesn't matter if new are re-register, take minsale, maxsale and current sale for keeping them in the database.

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
						String resolution = "";
						try {
							maxSale = respuesta.getInt("maxSale");
							currentSale = respuesta.getInt("currentSale");
							prefix = respuesta.getString("prefix");
							nit = respuesta.getString("nit");
							tel = respuesta.getString("tel");
							address = respuesta.getString("address");
							email = respuesta.getString("email");
							url = respuesta.getString("url");
							invoiceMessage = respuesta.getString("invoiceMessage");
							tipMessage = respuesta.getString("tipMessage");
							resolution = respuesta.getString("resolution");

						} catch (Exception e) {
							////Log.i("MISPRUEBAS","ERROR COGER DATOS de sales");
							e.printStackTrace();
						}
						int actualsale = prefs.getInt(Setup.CURRENT_SALE, 0);
						if(actualsale == 0){
							prefs.edit().putInt(Setup.CURRENT_SALE, currentSale).commit();
						}
						
						prefs.edit()
						.putInt(Setup.MAX_SALE, maxSale)
						.putString(Setup.INVOICE_PREFIX, prefix)
						.putString(Setup.COMPANY_ADDRESS, address)
						.putString(Setup.COMPANY_EMAIL, email)
						.putString(Setup.COMPANY_MESSAGE, invoiceMessage)
						.putString(Setup.TIP_MESSAGE, tipMessage)
						.putString(Setup.COMPANY_NIT, nit)
						.putString(Setup.COMPANY_TEL, tel)
						.putString(Setup.COMPANY_URL, url)
						.putString(Setup.RESOLUTION_MESSAGE, resolution)
						.commit();
						////Log.i("UPDATESALE","CurrentSale sin modificar: "+String.valueOf(prefs.getInt(Setup.CURRENT_SALE, currentSale)));
			    		////Log.i("UPDATESALE","maxSale sin modificar: "+String.valueOf(prefs.getInt(Setup.MAX_SALE, currentSale)));
			    		////Log.i("DATOSINFO","maxSale: "+String.valueOf(maxSale));

						////Log.i("DATOSINFO","currentSale: "+String.valueOf(currentSale));

						////Log.i("DATOSINFO","prefix: "+prefix);

						////Log.i("DATOSINFO","nit: "+nit);

						////Log.i("DATOSINFO","tel: "+tel);

						////Log.i("DATOSINFO","address: "+address);

						////Log.i("DATOSINFO","email: "+email);
						
						////Log.i("DATOSINFO","url: "+url);
						
						////Log.i("DATOSINFO","invoiceMessage: "+invoiceMessage);
						
						////Log.i("DATOSINFO","tipMessage: "+tipMessage);
					}
					else{
						
						
					}
					
				} 

			}
			else{

			}

		}
	};
	
	
	private final Handler receivedZReport = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mResetProgressDialog.dismiss();
			prefs = Util.getSharedPreferences(mContext);
			JSONObject respuesta = null;
			try {
				respuesta = new JSONObject((String) msg.obj);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (respuesta != null) {
				int status = 0;
				int responsecode = 0;
				String message = "";
				JSONObject payload = null;
				try {
					status = respuesta.getInt("status");
					responsecode = respuesta.getInt("code");
					message = respuesta.getString("message");
					payload = respuesta.getJSONObject("pay");

				} catch (Exception e) {
					////Log.i("MISPRUEBAS","ERROR COGER DATOS updateMaxHandler");
					e.printStackTrace();
				}
				
				////Log.i("MISPRUEBAS","ojo: "+String.valueOf(status)+" "+message);

				if (status == 200) {
						double ventas = 0;
						double descuentos = 0;
						double impuestos = 0;
						double propinas = 0;
						double domicilios = 0;
						double llevar = 0;
						double tarjeta = 0;
						double efectivo = 0;
						int contEfectivo = 0;
						int contTarjeta = 0;
						int contDomicilio = 0;
						int contLlevar = 0;
						
						
						try {
							ventas = payload.getDouble("ventas");
							descuentos = payload.getDouble("descuentos");
							impuestos = payload.getDouble("impuestos");
							propinas = payload.getDouble("propinas");
							domicilios = payload.getDouble("domicilios");
							llevar = payload.getDouble("llevar");
							tarjeta = payload.getDouble("tarjeta");
							efectivo = payload.getDouble("efectivo");
							contEfectivo = payload.getInt("contEfectivo");
							contTarjeta = payload.getInt("contTarjeta");
							contDomicilio = payload.getInt("contDomicilio");
							contLlevar = payload.getInt("contLlevar");

						} catch (Exception e) {
							e.printStackTrace();
						}
						String fecha = DateFormat.getDateFormat(Initialactivity.this).format(
								new Date());
						StringBuilder factura = new StringBuilder();
						//factura.append("MR. PASTOR COMIDA\r\nRaPIDA MEXICANA" + "\r\n");
						SharedPreferences prefs = Util.getSharedPreferences(mContext);
						String empresa  = prefs.getString(Setup.COMPANY_NAME, "Nombre de Empresa");
						String nit  = prefs.getString(Setup.COMPANY_NIT, "000000000-0");
						String email  = prefs.getString(Setup.COMPANY_EMAIL, "email@empresa.com");
						String pagina  = prefs.getString(Setup.COMPANY_URL, "http://www.empresa.com");
						String direccion  = prefs.getString(Setup.COMPANY_ADDRESS, "Dirección Física Empresa");
						String telefono  = prefs.getString(Setup.COMPANY_TEL, "555-55-55");
						factura.append(empresa + "\r\n");
						factura.append(nit + "\r\n");
						factura.append(direccion + "\r\n");
						factura.append(telefono + "\r\n");
						factura.append(email + "\r\n");
						factura.append(pagina + "\r\n");
						factura.append("\r\n");
						factura.append("\r\n");
						String labelVentasBrutas = "Valor Ventas Brutas";
						String labelDescuentos = "Descuentos(-)";
						String labelImpuestos = "Impuesto de Ventas";
						String labelSubtotal = "Subtotal Ventas";
						String labelDevoluciones = "Devoluciones(-)";
						String labelImpDevoluciones = "Impuesto Venta Devoluciones(-)";
						String labelVentasNetas = "Ventas Netas";
						String labelPropinas = "Propinas";
						String labelIngresosCaja = "Ingresos a Caja";
						String labelDomicilio = "Domicilio";
						String labelLlevar = "Llevar";
						String labelEfectivo = "Efectivo";
						String labelTarjeta = "Tarjeta";
						String labelIngresoReal = "Ingreso Real";
						String labelContado = "Ventas de Contado";
						String labelTransacciones = "Total de Transacciones";
						int totalEspacios = 32;
						factura.append(padRight(labelVentasBrutas, (totalEspacios - labelVentasBrutas.length())));
						factura.append("\r\n");
						factura.append(padLeft("$"+String.valueOf(ventas), (totalEspacios - ("$"+String.valueOf(ventas)).length())));
						factura.append("\r\n");
						factura.append(padRight(labelDescuentos, (totalEspacios - labelDescuentos.length())));
						factura.append("\r\n");
						factura.append(padLeft("$"+String.valueOf(descuentos), (totalEspacios - ("$"+String.valueOf(descuentos)).length())));
						factura.append("\r\n");
						factura.append(padRight(labelImpuestos, (totalEspacios - labelImpuestos.length())));
						factura.append("\r\n");
						factura.append(padLeft("$"+String.valueOf(impuestos), (totalEspacios - ("$"+String.valueOf(impuestos)).length())));
						factura.append("\r\n");
						factura.append("________________________________\r\n");
						factura.append(padLeft(labelSubtotal+" $"+String.valueOf(ventas - descuentos + impuestos), (totalEspacios - (labelSubtotal+" $"+String.valueOf(ventas - descuentos + impuestos)).length())));
						factura.append("\r\n");
						factura.append(padRight(labelPropinas, (totalEspacios - labelPropinas.length())));
						factura.append("\r\n");
						factura.append(padLeft("$"+String.valueOf(propinas), (totalEspacios - ("$"+String.valueOf(propinas)).length())));
						factura.append("\r\n");
						factura.append("________________________________\r\n");
						factura.append(padLeft(labelIngresosCaja+" $"+String.valueOf(ventas - descuentos + impuestos + propinas), (totalEspacios - (labelIngresosCaja+" $"+String.valueOf(ventas - descuentos + impuestos + propinas)).length())));
						factura.append("\r\n");
						factura.append("\r\n");
						factura.append(padRight(labelDomicilio, (totalEspacios - labelDomicilio.length())));
						factura.append("\r\n");
						factura.append(padLeft("$"+String.valueOf(domicilios), (totalEspacios - ("$"+String.valueOf(domicilios)).length())));
						factura.append("\r\n");
						factura.append(padRight(labelLlevar, (totalEspacios - labelLlevar.length())));
						factura.append("\r\n");
						factura.append(padLeft("$"+String.valueOf(llevar), (totalEspacios - ("$"+String.valueOf(llevar)).length())));
						factura.append("\r\n");
						factura.append(padRight(labelEfectivo, (totalEspacios - labelEfectivo.length())));
						factura.append("\r\n");
						factura.append(padLeft("$"+String.valueOf(efectivo), (totalEspacios - ("$"+String.valueOf(efectivo)).length())));
						factura.append("\r\n");
						factura.append(padRight(labelTarjeta, (totalEspacios - labelTarjeta.length())));
						factura.append("\r\n");
						factura.append(padLeft("$"+String.valueOf(tarjeta), (totalEspacios - ("$"+String.valueOf(tarjeta)).length())));
						factura.append("\r\n");
						factura.append("\r\n");
						factura.append("________________________________\r\n");
						factura.append(padLeft(labelIngresoReal+" $"+String.valueOf(ventas - descuentos + impuestos + propinas), (totalEspacios - (labelIngresosCaja+" $"+String.valueOf(ventas - descuentos + impuestos + propinas)).length())));
						factura.append("\r\n");
						factura.append(padLeft(labelContado+" $"+String.valueOf(ventas - descuentos + impuestos), (totalEspacios - (labelContado+" $"+String.valueOf(ventas - descuentos + impuestos)).length())));
						factura.append("\r\n");
						factura.append(padRight(labelDomicilio, (totalEspacios - labelDomicilio.length())));
						factura.append("\r\n");
						factura.append(padLeft(String.valueOf(contDomicilio), (totalEspacios - (String.valueOf(contDomicilio)).length())));
						factura.append("\r\n");
						factura.append(padRight(labelLlevar, (totalEspacios - labelLlevar.length())));
						factura.append("\r\n");
						factura.append(padLeft(String.valueOf(contLlevar), (totalEspacios - (String.valueOf(contLlevar)).length())));
						factura.append("\r\n");
						factura.append(padRight(labelEfectivo, (totalEspacios - labelEfectivo.length())));
						factura.append("\r\n");
						factura.append(padLeft(String.valueOf(contEfectivo), (totalEspacios - (String.valueOf(contEfectivo)).length())));
						factura.append("\r\n");
						factura.append(padRight(labelTarjeta, (totalEspacios - labelTarjeta.length())));
						factura.append("\r\n");
						factura.append(padLeft(String.valueOf(contTarjeta), (totalEspacios - (String.valueOf(contTarjeta)).length())));
						factura.append("\r\n");
						factura.append("________________________________\r\n");
						factura.append(padLeft(labelTransacciones+" "+String.valueOf(contEfectivo + contTarjeta), (totalEspacios - (labelTransacciones+" "+String.valueOf(contEfectivo + contTarjeta)).length())));
						Boolean printed = true;
				        try{
				        	if(mChatService.getState() == BluetoothChatService.STATE_CONNECTED)
							{
								try {
									mChatService.write(factura.toString().getBytes("x-UnicodeBig"));
								} catch (UnsupportedEncodingException e) {
									e.printStackTrace();
								}
							}
							else
							{
								printed = false;
								Toast.makeText(mContext, "No hay impresora bluetooth conectada.", Toast.LENGTH_LONG).show();
							}
				        }catch(NullPointerException e){
				        	printed = false;
				        	e.printStackTrace();
				        }
				        if(!printed){//buscar impresora TCP/IP
				        	StringBuilder formateado = new StringBuilder();
				        	formateado.append(CLEAR_PRINTER);
							formateado.append(INITIALIZE_PRINTER);
							formateado.append(JUSTIFICATION_CENTER);
							formateado.append(DOUBLE_WIDE_CHARACTERS);
							formateado.append(empresa);
							formateado.append(SINGLE_WIDE_CHARACTERS);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(nit);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(direccion);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(telefono);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(email);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(pagina);
							formateado.append(PRINT_FEED_N_LINES);
							formateado.append((char) 0x03);
							formateado.append(DOUBLE_WIDE_CHARACTERS);
							formateado.append("CIERRE DE CAJA Z");
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(fecha);
							formateado.append(SINGLE_WIDE_CHARACTERS);
							formateado.append(PRINT_FEED_N_LINES);
							formateado.append((char) 0x03);
							formateado.append(SINGLE_WIDE_CHARACTERS);
							formateado.append(JUSTIFICATION_LEFT);
							formateado.append(labelVentasBrutas);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_RIGHT);
							formateado.append("$"+String.valueOf(ventas));
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_LEFT);
							formateado.append(labelDescuentos);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_RIGHT);
							formateado.append("$"+String.valueOf(descuentos));
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_LEFT);
							formateado.append(labelImpuestos);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_RIGHT);
							formateado.append("$"+String.valueOf(impuestos));
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_LEFT);
							formateado.append(DOUBLE_WIDE_CHARACTERS);
							formateado.append("______________________");
							formateado.append(SINGLE_WIDE_CHARACTERS);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_RIGHT);
							formateado.append(labelSubtotal+" $"+String.valueOf(ventas - descuentos + impuestos));
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_LEFT);
							formateado.append(labelPropinas);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_RIGHT);
							formateado.append("$"+String.valueOf(propinas));
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_LEFT);
							formateado.append(DOUBLE_WIDE_CHARACTERS);
							formateado.append("______________________");
							formateado.append(SINGLE_WIDE_CHARACTERS);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_RIGHT);
							formateado.append(labelIngresosCaja+" $"+String.valueOf(ventas - descuentos + impuestos + propinas));
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_LEFT);
							formateado.append(labelDomicilio);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_RIGHT);
							formateado.append("$"+String.valueOf(domicilios));
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_LEFT);
							formateado.append(labelLlevar);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_RIGHT);
							formateado.append("$"+String.valueOf(llevar));
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_LEFT);
							formateado.append(labelEfectivo);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_RIGHT);
							formateado.append("$"+String.valueOf(efectivo));
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_LEFT);
							formateado.append(labelTarjeta);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_RIGHT);
							formateado.append("$"+String.valueOf(tarjeta));
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_LEFT);
							formateado.append(DOUBLE_WIDE_CHARACTERS);
							formateado.append("______________________");
							formateado.append(SINGLE_WIDE_CHARACTERS);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_RIGHT);
							formateado.append(labelIngresoReal+" $"+String.valueOf(ventas - descuentos + impuestos + propinas));
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(labelContado+" $"+String.valueOf(ventas - descuentos + impuestos));
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_LEFT);
							formateado.append(labelDomicilio);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_RIGHT);
							formateado.append(String.valueOf(contDomicilio));
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_LEFT);
							formateado.append(labelLlevar);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_RIGHT);
							formateado.append(String.valueOf(contLlevar));
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_LEFT);
							formateado.append(labelEfectivo);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_RIGHT);
							formateado.append(String.valueOf(contEfectivo));
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_LEFT);
							formateado.append(labelTarjeta);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_RIGHT);
							formateado.append(String.valueOf(contTarjeta));
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_LEFT);
							formateado.append(DOUBLE_WIDE_CHARACTERS);
							formateado.append("______________________");
							formateado.append(SINGLE_WIDE_CHARACTERS);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(JUSTIFICATION_RIGHT);
							formateado.append(labelTransacciones+" "+String.valueOf(contEfectivo + contTarjeta));
							formateado.append(FINALIZE_TICKET);
							formateado.append(FULL_CUT);
				        	 if (mTCPPrint != null) {
				        		 if(mTCPPrint.getStatus() == TCPPrint.CONNECTED){
				        			 mTCPPrint.sendMessage(formateado.toString());
				        			 mTCPPrint.sendMessage(formateado.toString());
				        		 }
				        		 else{
				        			 mTCPPrint.stopClient();
				        			 new connectTask().execute(formateado.toString());
				        			 alertbox("¡Oops!", "Al Parecer no hay impresora disponible. Estamos tratando de reconectarnos e imprimir. Si no funciona, reinicia la Red o la impresora y ve a órdenes para imprimir el pedido.");
				        		 }   
				                }else{
				                	alertbox("¡Oops!", "Al Parecer no hay impresora disponible. Trataremos en este momento de nuevo de imprimir el pedido. Si no funciona, reinicia la red o la impreso y ve a órdenes para imprimir de nuevo la orden.");
				                	new connectTask().execute(formateado.toString());
				                }
				        }

						
						
						
					}
					else{
						//otro status diferente de 200
						//Log.i("MISPRUEBAS",String.valueOf(status)+": "+message);
						Toast.makeText(mContext, "No hay registros del día para imprimir reporte", Toast.LENGTH_LONG).show();
					}
					
				} 
				else{
					//respuesta = null
					//Log.i("MISPRUEBAS","Respuesta null de servidor");
				}

			}
			

		};

	public void downloadFile() {
		try {
	        //set the download URL, a url that points to a file on the internet
	        //this is the file to be downloaded
			prefs = Util.getSharedPreferences(mContext);
			URL url = new URL(Setup.PROD_BIGDATA_URL+"/databaseOps/importDatabase?payload={\"company\":"+prefs.getString(Setup.COMPANY_ID, "0")+"}"); 

	        //create the new connection
	        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

	        //set up some things on the connection
	        urlConnection.setRequestMethod("GET");
	        urlConnection.setDoOutput(true);

	        //and connect!
	        urlConnection.connect();

	        //set the path where we want to save the file
	        //in this case, going to save it on the root directory of the
	        //sd card.
	        File SDCardFolder = new File (Environment.getExternalStorageDirectory() + "/nest5_files");
	        if (!SDCardFolder.exists()) {
	        	SDCardFolder.mkdirs();
	        }
	        //create a new file, specifying the path, and the filename
	        //which we want to save the file as.

	        File file = new File(SDCardFolder,"initpos.ne5");

	        //this will be used to write the downloaded data into the file we created
	        FileOutputStream fileOutput = new FileOutputStream(file);

	        //this will be used in reading the data from the internet
	        InputStream inputStream = urlConnection.getInputStream();

	        //this is the total size of the file
	        int totalSize = urlConnection.getContentLength();
	        //variable to store total downloaded bytes
	        int downloadedSize = 0;

	        //create a buffer...
	        byte[] buffer = new byte[1024];
	        int bufferLength = 0; //used to store a temporary size of the buffer

	        //now, read through the input buffer and write the contents to the file
	        while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
	                //add the data in the buffer to the file in the file output stream (the file on the sd card
	                fileOutput.write(buffer, 0, bufferLength);
	                //add up the size so we know how much is downloaded
	                downloadedSize += bufferLength;
	                //this is where you would do something to report the prgress, like this maybe
	                //updateProgress(downloadedSize, totalSize);

	        }
	        //close the output stream when done
	        fileOutput.close();
	        ////Log.i("PRUEBAS","Acabo el archivo");

	//catch some possible errors...
	} catch (MalformedURLException e) {
	        e.printStackTrace();
	} catch (IOException e) {
	        e.printStackTrace();
	}
        
  }
	
	Handler databasehandler = new Handler() {
		  @Override
		  public void handleMessage(Message msg) {
			  ////Log.i("PRUEBAS", "LLego de bajar el archivo");
			  
			  //showProgress(false);
			  
		    	SQLiteDatabase db = dbHelper.getWritableDatabase();
		    	dbHelper.onCreate(db);
		    	updateRegistrables();
		    	mResetProgressDialog.dismiss();
		     }
		 };
	
	
	private int updateSyncIdInRow(String table, Long id,
			String field, Long value) {
		ContentValues values = new ContentValues();
		values.put(field, value);
		return db.update(table, values, Setup.COLUMN_ID + " = " + id, null);
		
		//actualizar las listas de acuerdo a las cosas que se hayan hecho
	}
	private int deleteSyncRow(Long id) {
		return db.delete(Setup.TABLE_SYNC_ROW, Setup.COLUMN_ID
		        + " = " + id, null);
		
		//actualizar las listas de acuerdo a las cosas que se hayan hecho
	}
	
	
	
	//handle downlaod database
	
	
	private BroadcastReceiver onSyncDownloadComplete = new BroadcastReceiver() {
	    public void onReceive(Context ctxt, Intent intent) {
	        // Do Something
	    	//showProgress(false);
	    	//update database (delete it?)
	    	//prefs.edit().putBoolean(Setup.IS_UPDATING, true).commit();
	    	Log.e("GUARDANDOVENTA","LLEGO AL RECEIVER!!!");
	    	SQLiteDatabase db = dbHelper.getWritableDatabase();
	    	dbHelper.onCreate(db);
	    	
	    	//mContext.deleteDatabase("nest5pos.db");
	    	
	    	//cargar en las listas todo otra vez
	    	
	    	//reload all info on app
	    	ingredientCategoryDatasource = new IngredientCategoryDataSource(dbHelper);
			ingredientCategories = ingredientCategoryDatasource
					.getAllIngredientCategory(db);
	    	productCategoryDatasource = new ProductCategoryDataSource(dbHelper); //here it should open the app and create the databse since it doesn't exist
			productCategoryDatasource.open(db);
			productsCategories = productCategoryDatasource.getAllProductCategory();
			ingredientDatasource = new IngredientDataSource(dbHelper);
			ingredientDatasource.open(db);
			ingredientes = ingredientDatasource.getAllIngredient();
			productDatasource = new ProductDataSource(dbHelper);
			productDatasource.open(db);
			taxDataSource = new TaxDataSource(dbHelper);
			taxDataSource.open(db);
			taxes = taxDataSource.getAllTax();
			unitDataSource = new UnitDataSource(dbHelper);
			unitDataSource.open(db);
			units = unitDataSource.getAllUnits();
			saleDataSource = new SaleDataSource(dbHelper);
			saleDataSource.open();
			saleList = saleDataSource.getAllSales();
			mResetProgressDialog.dismiss();
			
	    	
	    }
	    
	};
	
// The Handler that gets information back from the BluetoothChatService
    
    private final Handler mHandlerBlueTooth = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_STATE_CHANGE:
                                switch (msg.arg1) {
                case BluetoothChatService.STATE_CONNECTED:
                    //mTitle.setText(R.string.title_connected_to);
                    //mTitle.append(mConnectedDeviceName);
                    //mConversationArrayAdapter.clear();
                    
                    /*int lv = lv7.length();
                    String []s=new String[lv];
            		setPrinter(4,0);//óÖ·óóó×ª	
            		setPrinter(10, 1);
                    setPrinter(4);
            		for(int i=d;i<lv;i++){
            			s[i]=lv7.substring(i-1,i);			
            			if(s[i].equals("n")){
            				setPrinter(3);
            			
            			}
            			s[i] = RepString(s[i], "n", "");
            			printContent(s[i]);		
            			}*/
            		
                    break;
                case BluetoothChatService.STATE_CONNECTING:
                    //mTitle.setText(R.string.title_connecting);
                    break;
                case BluetoothChatService.STATE_LISTEN:
                case BluetoothChatService.STATE_NONE:
                    //mTitle.setText(R.string.title_not_connected);
                    BluetoothAdapter cwjBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    
                    if(cwjBluetoothAdapter == null){
                    	Toast.makeText(
        		    			Initialactivity.this,"No hay adaptador disponible",
        		    			Toast.LENGTH_SHORT)
        		    			.show();
                    }
                    if (!cwjBluetoothAdapter.isEnabled()) {

                    	Intent TurnOnBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

                    	startActivityForResult(TurnOnBtIntent, REQUEST_ENABLE_BT);

                    	}


                    break;
                }
                break;
            case MESSAGE_WRITE:
                byte[] writeBuf = (byte[]) msg.obj;
                // construct a string from the bufferóóóóÒ»óóóóóóóóóÐµóóÖ·ó
                String writeMessage = new String(writeBuf);
                Log.d("Escribio al socekt: ",writeMessage);
                //mConversationArrayAdapter.add("Me:  " + writeMessage);
                break;
            case MESSAGE_READ:
                byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the bufferóóóóÒ»óóóóóóÐ§óÖ½Ú»óóóóóóÐµóóÖ·ó
                String readMessage = new String(readBuf, 0, msg.arg1);
                Log.d(TAG,"Leido: "+ readMessage);
                //mConversationArrayAdapter.add(mConnectedDeviceName+":  " + readMessage);
                break;
            case MESSAGE_DEVICE_NAME:
                // save the connected device's name óóóóóóóóóè±¸óóó
                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                Toast.makeText(getApplicationContext(), "Connected to "
                               + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_TOAST:
                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                               Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };
    
   
    
    private  boolean isConnectedToInternet() {
    	ConnectivityManager conMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    	if (conMgr.getActiveNetworkInfo() != null &&  conMgr.getActiveNetworkInfo().isAvailable()
    	    && conMgr.getActiveNetworkInfo().isConnected()) 
    	return true;
    	else
    	return false;
    	}
    
    protected void alertbox(String title, String mymessage)
    {
    new AlertDialog.Builder(this)
       .setMessage(mymessage)
       .setTitle(title)
       .setCancelable(true)
       .setNeutralButton(android.R.string.ok,
          new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton){}
          })
       .show();
    }
    
    protected int checkSaleNumber(){
    	SharedPreferences prefs = Util.getSharedPreferences(mContext);
    	int maxSales = prefs.getInt(Setup.MAX_SALE, 0);
    	int currentSale = prefs.getInt(Setup.CURRENT_SALE, 0);
    	int disponibles = maxSales - currentSale;
    	return disponibles;
    	
    }
    protected int addSale(){ //se llama cuando se guarda una venta, solo se guarda si se puede facturar. el valor q esto devuelve se manda en fields y se agrega a la base de datos
    	SharedPreferences prefs = Util.getSharedPreferences(mContext);
    	int maxSales = prefs.getInt(Setup.MAX_SALE, 0);
    	int currentSale = prefs.getInt(Setup.CURRENT_SALE, 0);
    	int disponibles = maxSales - currentSale;
    	if(disponibles > 0){
    		////Log.i("UPDATESALE","CurrentSale: "+String.valueOf(prefs.getInt(Setup.CURRENT_SALE, currentSale + 1)));
    		////Log.i("UPDATESALE","maxSale: "+String.valueOf(prefs.getInt(Setup.MAX_SALE, currentSale + 1)));
    		prefs.edit().putInt(Setup.CURRENT_SALE, currentSale + 1).commit();
    	}
    	else{
    		return -1; //no se puede crear venta, no hay nÃºmeros de facturación disponibles. 
    	}
    	
    	return currentSale + 1;
    }
    protected int subSale(){ //se llama cuando se guarda una venta mal y se reestablece ek valor anterior
    	SharedPreferences prefs = Util.getSharedPreferences(mContext);
    	int currentSale = prefs.getInt(Setup.CURRENT_SALE, 0);
    	if(currentSale - 1 >= 0)
    		prefs.edit().putInt(Setup.CURRENT_SALE, currentSale - 1).commit();
    	return currentSale - 1 >= 0 ? currentSale - 1 : 0;
    }
	
	

	// Load image from url
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

	@Override
	public void OnOrderClicked(int isDelivery, int isTogo,String note) {
		// al guardar lo que hace es que guarda un objeto Sale con fecha, metodo
				// de pago y valor recibido.
				// despues toma currentOrder y dic saveItem(Context mContext,int type,
				// long item_id, double qty) para cada uno

				// al recuperar un sale se hace

				// price = 0.0;
				Date date = new Date();
				String fecha = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(date); 
				//String fecha = DateFormat.getDateFormat(Initialactivity.this).format(
						//date);

				// imprimir, conectar por wifi y enviar el texto arregladito a la app de
				// puente
				String mesa = "-DOMICILIO / PARA LLEVAR- O -MESA NO REGISTRADA-";
				if(currentTable != null){
					mesa = currentTable.getTable().getName().toUpperCase(Locale.getDefault());
				}
				int lines = 0;
				StringBuilder factura = new StringBuilder();
				String newline ="\r\n";
				String title =  "-----COMANDA----COMANDA-----" + "\r\n";
				String tableheader = "    Item              Cantidad\r\n";
				String notas = "NOTAS\r\n";
				factura.append(title);
				factura.append(mesa+"\r\n");
				lines++;
				factura.append(fecha);
				lines++;
				lines++;
				lines++;
				factura.append(newline);
				factura.append(tableheader);
				
				lines++;
				int j = 0;
				LinkedList<String> productos = new LinkedList<String>();
				LinkedList<Integer> quantities = new LinkedList<Integer>();
				LinkedList<Double> precios = new LinkedList<Double>();
				
				Iterator<Entry<Registrable, Integer>> it = currentOrder.entrySet()
						.iterator();
				// Log.d(TAG,String.valueOf(currentOrder.size()));
				LinkedHashMap<Registrable, Integer> currentObjects = new LinkedHashMap<Registrable, Integer>();

				while (it.hasNext()) {

					LinkedHashMap.Entry<Registrable, Integer> pairs = (LinkedHashMap.Entry<Registrable, Integer>) it
							.next();

					currentObjects.put(pairs.getKey(), pairs.getValue());
					String name = pairs.getKey().name;
					productos.add(name);
					int longName = name.length();
					int subLength = 14 - longName;
					if (subLength < 0)
						name = name.substring(0, 14);
					int espacios1 = 4;
					int espacios2 = 12;
					if (name.length() < 14)
					{
						espacios1 += 14 - name.length();
					}
					factura.append(name);
					
					int qtyL = String.valueOf(pairs.getValue()).length();
					espacios1 = espacios1 - qtyL < 1 ? espacios1 = 1 : espacios1 - qtyL;
					//espacios2 = espacios2 - priceL < 1 ? espacios2 = 1 : espacios2 - priceL;
					espacios2 = espacios2 - qtyL < 1 ? espacios2 = 1 : espacios2 - qtyL;
					for (int k = 0; k < espacios1; k++) {
						factura.append(" ");
					}
					factura.append(pairs.getValue());
					quantities.add(pairs.getValue());
					for (int k = 0; k < espacios2; k++) {
						factura.append(" ");
					}
					factura.append(newline);
					lines++;
				}
				factura.append(notas);
				factura.append(note);
				long startTime = System.currentTimeMillis();
				if(currentSelectedAddTable > -1){//esto significa que esta agregando la orden actual a otra existente, para la mesa que este seleccionada
					LinkedHashMap<Registrable, Integer> existingOrder = null;
					for(Map.Entry<LinkedHashMap<Registrable,Integer>,CurrentTable<Table,Integer>> tab : cookingOrdersTable.entrySet()){
						if(tab.getValue().getTable().getName().equalsIgnoreCase(currentTable.getTable().getName())){
							existingOrder = tab.getKey();
							break;
						}
					}
					if(existingOrder != null){
						int prevDelivery = cookingOrdersDelivery.get(existingOrder);
						int prevTogo =  cookingOrdersTogo.get(existingOrder);
						Long prevTime = cookingOrdersTimes.get(existingOrder);
						cookingOrders.remove(existingOrder);
						cookingOrdersDelivery.remove(existingOrder);
						cookingOrdersTable.remove(existingOrder);
						cookingOrdersTimes.remove(existingOrder);
						cookingOrdersTogo.remove(existingOrder);
						Iterator<Entry<Registrable, Integer>> itnuevo = existingOrder.entrySet()
								.iterator();

						while (itnuevo.hasNext()) {
							LinkedHashMap.Entry<Registrable, Integer> pairs = (LinkedHashMap.Entry<Registrable, Integer>) itnuevo
									.next();
							currentObjects.put(pairs.getKey(), pairs.getValue());
						}
						cookingOrders.add(currentObjects);
						cookingOrdersDelivery.put(currentObjects, prevDelivery);
						cookingOrdersTogo.put(currentObjects, prevTogo);
						cookingOrdersTimes.put(currentObjects, prevTime);
						cookingOrdersTable.put(currentObjects, currentTable);
					}
				} else{
					cookingOrders.add(currentObjects);
					cookingOrdersDelivery.put(currentObjects, isDelivery);
					cookingOrdersTogo.put(currentObjects, isTogo);
					cookingOrdersTimes.put(currentObjects, startTime);
					if(currentTable != null){
						cookingOrdersTable.put(currentObjects, currentTable);
						openTables.push(currentTable);
					}
					else{
						int[] coordinates = new int[2];
			        	coordinates[0] = 0;
			        	coordinates[1] = 0;
			        	CurrentTable<Table,Integer> tabletemp = new CurrentTable<Table, Integer>(new Table("Domicilio / Para Llevar", 1, coordinates), 1);
			        	cookingOrdersTable.put(currentObjects, tabletemp);
			        	openTables.push(tabletemp);
					}
				}
				
					
				List<Long> items = new ArrayList<Long>();
				List<String> nameTables = new ArrayList<String>();
				for (LinkedHashMap<Registrable, Integer> current : cookingOrders) {
					items.add(cookingOrdersTimes.get(current));
					nameTables.add(cookingOrdersTable.get(current).getTable().getName());
				}
				cookingAdapter = new SaleAdapter(mContext, items, nameTables,inflater);
				ordersList.setAdapter(cookingAdapter);
				ordersList.setOnItemClickListener(orderListListener);
				/*if (!isTimerRunning) {
					startTimer();
				}*/
				currentOrder.clear();
				currentTable  = null;
				statusText.setText("En Espera de Abrir mesa.");
				makeTable("NA");
				lines++;
				lines++;
				Boolean printed = true;
				        try{
				        	if(mChatService.getState() == BluetoothChatService.STATE_CONNECTED)
							{
								try {
									mChatService.write(factura.toString().getBytes("x-UnicodeBig"));
								} catch (UnsupportedEncodingException e) {
									e.printStackTrace();
								}
							}
							else
							{
								printed = false;
								Toast.makeText(mContext, "No hay impresora conectada.", Toast.LENGTH_LONG).show();
							}
				        }catch(NullPointerException e){
				        	printed = false;
				        	e.printStackTrace();
				        }
				        if(!printed){//buscar impresora TCP/IP
				        	StringBuilder formateado = new StringBuilder();
							formateado.append(CLEAR_PRINTER);
							formateado.append(INITIALIZE_PRINTER);
							formateado.append(JUSTIFICATION_CENTER);
							formateado.append(DOUBLE_WIDE_CHARACTERS);
							formateado.append("----COMANDA----");
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(fecha);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(mesa);
							formateado.append(PRINT_FEED_N_LINES);
							formateado.append((char) 0x03);
							formateado.append(JUSTIFICATION_LEFT);
							formateado.append("ITEM");
							formateado.append(HORIZONTAL_TAB);
							formateado.append(HORIZONTAL_TAB);
							formateado.append(HORIZONTAL_TAB);
							formateado.append("CANTIDAD");
							formateado.append(HORIZONTAL_TAB);
							formateado.append(SINGLE_WIDE_CHARACTERS);
							formateado.append(PRINT_FEED_ONE_LINE);
							for(String actual : productos){
								int pos = productos.indexOf(actual);
								int cantidad = quantities.get(pos);
								formateado.append(actual);
								formateado.append(HORIZONTAL_TAB);
								formateado.append(HORIZONTAL_TAB);
								formateado.append(HORIZONTAL_TAB);
								formateado.append(cantidad);
								formateado.append(PRINT_FEED_ONE_LINE);
							}
							formateado.append(PRINT_FEED_N_LINES);
							formateado.append((char) 0x02);
							formateado.append(ITALIC_STYLE);
							formateado.append(notas);
							formateado.append(PRINT_FEED_ONE_LINE);
							formateado.append(note);
							formateado.append(ITALIC_CANCEL);
							formateado.append(FINALIZE_TICKET);
							formateado.append(PARTIAL_CUT);
				        	 if (mTCPPrint != null) {
				        		 if(mTCPPrint.getStatus() == TCPPrint.CONNECTED){
				        			 mTCPPrint.sendMessage(formateado.toString());
				        			 mTCPPrint.sendMessage(formateado.toString());
				        		 }
				        		 else{
				        			 mTCPPrint.stopClient();
				        			 new connectTask().execute(formateado.toString());
				        			 alertbox("¡Oops!", "Al Parecer no hay impresora disponible. Estamos tratando de reconectarnos e imprimir. Si no funciona, reinicia la Red o la impresora y ve a órdenes para imprimir el pedido.");
				        		 }   
				                }else{
				                	alertbox("¡Oops!", "Al Parecer no hay impresora disponible. Trataremos en este momento de nuevo de imprimir el pedido. Si no funciona, reinicia la red o la impreso y ve a órdenes para imprimir de nuevo la orden.");
				                	new connectTask().execute(formateado.toString());
				                }
				        }
				        
				        
				        
		
	}
	
	private void saveSale(String method,Double value,Double discount,int delivery,int togo, int tip){
		int number = checkSaleNumber(); 
		int nextsale = addSale(); //se aumenta el valor de facturación, //si falla se resta un numero de las ventas actuales mas adelante,.
		Sale createdSale = null;
		long saveDate = System.currentTimeMillis();
		LinkedHashMap<Registrable,Integer> currentSale = currentOrder;
		if(number > 0){
			createdSale = saleDataSource.createSale(saveDate,
					method,
					value,
					0,
					delivery,
					togo,
					tip,
					discount,
					nextsale);
			
		}
		else{
			alertbox("!ATENCIÓN!", "Esta venta no se puede facturar. Este dispositivo no tiene más facturas autorizadas. Consulta el administrador, o si tu lo eres, ve a tu panel de control Nest5 y autoriza más facturas. Para más información: http://soporte.nest5.com");
		}
		if (createdSale != null) {
			Iterator<Entry<Registrable, Integer>> it = currentSale
					.entrySet().iterator();

			while (it.hasNext()) {
				Map.Entry<Registrable, Integer> pair = (Map.Entry<Registrable, Integer>) it
						.next();
				createdSale.saveItem(dbHelper, pair.getKey().type,
						pair.getKey().id, pair.getValue());//será que acá guarda el id local y sincroiniza asi por eso llega luego tod en ceros al volver a sincronizar?
				
				// Log.d("INGREDIENTES","INGREDIENTE: "+ingrediente.getKey().getName()+" "+ingrediente.getValue());
			}
			
			//cookingOrders.remove(currentSelectedPosition);
			try{
				cookingOrders.remove(currentSale);
				cookingOrdersDelivery.remove(currentSale);
				cookingOrdersTogo.remove(currentSale);
				cookingOrdersTimes.remove(currentSale);
				openTables.remove(cookingOrdersTable.get(currentSale));
				cookingOrdersTable.remove(currentSale);
		        //quitar mesas de las abiertas y quitar 
			}catch(Exception e){
				////Log.i("ERRORES_REMOVE","HAY UN ERROR AL REMOVER CURRENTSALE DE COOKINGORDERS");
				e.printStackTrace();
			}
			currentTable = null;
			statusText.setText("Cuenta Cerrada Exitosamente.");
			currentSelectedPosition = -1;
			//sendCommandMessage(DELETE_ALL_COMMAND);
			List<Long> items = new ArrayList<Long>();
			List<String> nameTables = new ArrayList<String>();
			for (LinkedHashMap<Registrable, Integer> current : cookingOrders) {
				items.add(cookingOrdersTimes.get(current));
				nameTables.add(cookingOrdersTable.get(current).getTable().getName());
			}
			cookingAdapter = new SaleAdapter(mContext, items, nameTables,inflater);
			ordersList.setAdapter(cookingAdapter);
			salesFromToday = saleDataSource
					.getAllSalesWithin(init, end);

			ordersList.setOnItemClickListener(orderListListener);
			//currentOrder.clear(); //NUEVO
			//makeTable("NA");
			sale_name.setText("Venta Guardada con Éxito");
			sale_details
					.setText("Selecciona otro elemento para ver detalles.");
			updateSaleValue();
			
			//pdate sale object to get saved items, since the object doesn't have them
			createdSale = saleDataSource.getSale(createdSale.getId());
			//Log.w("GUARDANDOVENTA","Cantidad de productos: "+String.valueOf(createdSale.getProducts().size()));
			createSyncRow("\""+Setup.TABLE_SALE+"\"",createdSale.getId(), createdSale.getSyncId(), createdSale.serializedFields());

		} else {
			subSale();//falló uardando venta por lo tanto resetea el valor de facturación actual al anterior.
			Toast.makeText(mContext, "Error al Guardar la venta",
					Toast.LENGTH_LONG).show();
			
		}
	}
	
	public static String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);  
	}

	public static String padLeft(String s, int n) {
	    return String.format("%1$" + n + "s", s);  
	}
	private void updateRegistrables(){
		try{
			productList = new ArrayList<Registrable>();
			allRegistrables = new ArrayList<Registrable>();
			inflater = Initialactivity.this.getLayoutInflater();
			Iterator<Product> iterator = productos.iterator();

			while (iterator.hasNext()) {
				// ////Log.i("HOLAAA",iterator.next().getName());
				Registrable current = new Registrable(iterator.next());
				productList.add(current);
				allRegistrables.add(current);

			}
			ingredientList = new ArrayList<Registrable>();
			Iterator<Ingredient> iterator2 = ingredientes.iterator();

			while (iterator2.hasNext()) {
				// //////Log.i("HOLAAA",iterator.next().getName());
				Registrable current = new Registrable(iterator2.next());
				ingredientList.add(current);
				allRegistrables.add(current);

			}

			comboList = new ArrayList<Registrable>();
			inflater = Initialactivity.this.getLayoutInflater();
			Iterator<Combo> iterator3 = combos.iterator();

			while (iterator3.hasNext()) {
				// ////Log.i("HOLAAA",iterator.next().getName());
				Registrable current = new Registrable(iterator3.next());
				comboList.add(current);
				allRegistrables.add(current);

			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/*****
	 * 
	 * 
	 * Print over tcp / ip
	 * 
	 * *****/
	
	
	public class connectTask extends AsyncTask<String,String,TCPPrint> {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
		String server = prefs.getString("pref_printerip", "0.0.0.0");
		int port = Integer.parseInt(prefs.getString("pref_printerport", "4098"));
        @Override
        protected TCPPrint doInBackground(String... message) {
            mTCPPrint = new TCPPrint(new TCPPrint.OnMessageReceived() {
                @Override
                public void messageReceived(String message) {
                    publishProgress(message);
                }
            }, mContext,server,port);
            mTCPPrint.run();
            return null;
        }
 
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            for(String actual : values){
            	if(actual.equals(TCPPrint.STATUS_CHANGE)){
                	//hay un cambio de estado en la conexiÃ²n con la impresora
                	switch(mTCPPrint.getStatus()){
                	case TCPPrint.CONNECTED:
                		deviceText.setText("CONECTADO A IMPRESORA");
                		break;
                	case TCPPrint.DISCONNECTED:
                		deviceText.setText("¡ATENCIÓN! NO HAY IMPRESORA");
                		break;
                	case TCPPrint.CONNECTING:
                		deviceText.setText("CONECTANDO...");
                		break;
                	case TCPPrint.SUDDENLY_DISCONNECTED:
                		deviceText.setText("¡ATENCIÓN! NO HAY IMPRESORA!");
                		alertbox("¡Oops!", "Por alguna razón se ha perdido la conexión con la impresora. Intenta presionando en el texto de conexión y si no funciona reinicia tu router o adaptador.");
                		if(mTCPPrint != null){
        					mTCPPrint.stopClient();
        				}
        				new connectTask().execute("");
                		break;
                	}
                }
            }
        }
    }
	private static final Runnable sRunnable = new Runnable() {
		
	      @Override
	      public void run() { 
	    	  
	    	  LinkedList<LinkedHashMap<Registrable,Integer>> cookingOrders = Initialactivity.getCookingOrders(); 
	    	  LinkedHashMap<LinkedHashMap<Registrable, Integer>, Long> cookingOrdersTimes = Initialactivity.getCookingOrdersTimes();
	    	  LinkedHashMap<LinkedHashMap<Registrable, Integer>, CurrentTable<Table, Integer>> cookingOrdersTable = Initialactivity.getCookingOrdersTables();
	    	  LinkedHashMap<LinkedHashMap<Registrable, Integer>, Integer> cookingOrdersTogo = Initialactivity.getCookingOrdersTogo();
	    	  LinkedHashMap<LinkedHashMap<Registrable, Integer>, Integer> cookingOrdersDelivery = Initialactivity.getCookingOrdersDelivery();
	    	String list = "";
	  		String deliveries = "";
	  		String togos = "";
	  		String times = "";
	  		String tables = "";
	  		String opentables = "";
	  		
	  		try {
	  			GsonBuilder gb = new GsonBuilder();
	  			gb.registerTypeAdapter(LinkedList.class, new SerialiserLinkedList());
	  			Gson gson = gb.create();
	  			////Log.i("MISPRUEBAS","antes de la lista sigue el linkedhashmap");
	  			list = gson.toJson(cookingOrders);
	  			gb.registerTypeAdapter(new LinkedHashMap<LinkedHashMap<Registrable, Integer>, Integer>().getClass(), new SerialiserLinkedHashMap());
	  			gson = gb.create();
	  			for(Map.Entry<LinkedHashMap<Registrable,Integer>, Integer> orden : cookingOrdersTogo.entrySet()){
	  				//Log.i("MISPRUEBAS",orden.toString());
	  			}
	  			
	  	        
	  	        ////Log.i("MISPRUEBAS","ya paso la lista sigue el linkedhashmap");
	  	        togos = gson.toJson(cookingOrdersTogo);
	  	        ////Log.i("MISPRUEBAS","acabo el primer linkedhashmap");
	  	        deliveries = gson.toJson(cookingOrdersDelivery);
	  	        ////Log.i("MISPRUEBAS","TAMAÃ‘P DE COOKINGORDERSTABE : "+cookingOrdersTable.size());
	  	        gb.registerTypeAdapter(new LinkedHashMap<LinkedHashMap<Registrable, Integer>, Long>().getClass(), new SerialiserLinkedHashMapLong());
	  	        gson = gb.create();
	  	        times = gson.toJson(cookingOrdersTimes);
	  	        if(cookingOrdersTable.size() > 0){ //it can be 0 when there are orders but not assigned to a table
	  	        	gb.registerTypeAdapter(new LinkedHashMap<LinkedHashMap<Registrable, Integer>, CurrentTable<Table,Integer>>().getClass(), new SerialiserLinkedHashMapCurrentTable());
	  		        gson = gb.create();
	  		        tables = gson.toJson(cookingOrdersTable);
	  		        ////Log.i("MISPRUEBAS","acabo el segundo linkedhashmap: "+tables);	
	  	        }
	  	        
	  	       
	  	        ////Log.i("MISPRUEBAS","Lista: "+list);
	  	        
	  		}catch(Exception e){
	  			e.printStackTrace();
	  		}

	  		
	  		
	  		
	    	  
	      }
	      
	  };
	  
	  private static class BackUpThread implements Runnable{
		  private WeakReference<Initialactivity> mActivity;
		  
		  public BackUpThread(Initialactivity activity){
			  mActivity = new WeakReference<Initialactivity>(activity);
		  }
          // After call for background.start this run method call
          public void run() {
        	  LinkedList<LinkedHashMap<Registrable,Integer>> cookingOrders = Initialactivity.getCookingOrders(); 
	    	  LinkedHashMap<LinkedHashMap<Registrable, Integer>, Long> cookingOrdersTimes = Initialactivity.getCookingOrdersTimes();
	    	  LinkedHashMap<LinkedHashMap<Registrable, Integer>, CurrentTable<Table, Integer>> cookingOrdersTable = Initialactivity.getCookingOrdersTables();
	    	  LinkedHashMap<LinkedHashMap<Registrable, Integer>, Integer> cookingOrdersTogo = Initialactivity.getCookingOrdersTogo();
	    	  LinkedHashMap<LinkedHashMap<Registrable, Integer>, Integer> cookingOrdersDelivery = Initialactivity.getCookingOrdersDelivery();
	    	  	String list = "";
	  			String deliveries = "";
	  			String togos = "";
	  			String times = "";
	  			String tables = "";
	  		try {
	  			GsonBuilder gb = new GsonBuilder();
	  			gb.registerTypeAdapter(LinkedList.class, new SerialiserLinkedList());
	  			Gson gson = gb.create();
	  			////Log.i("MISPRUEBAS","antes de la lista sigue el linkedhashmap");
	  			list = gson.toJson(cookingOrders);
	  			gb.registerTypeAdapter(new LinkedHashMap<LinkedHashMap<Registrable, Integer>, Integer>().getClass(), new SerialiserLinkedHashMap());
	  			gson = gb.create();
	  	        ////Log.i("MISPRUEBAS","ya paso la lista sigue el linkedhashmap");
	  	        togos = gson.toJson(cookingOrdersTogo);
	  	        ////Log.i("MISPRUEBAS","acabo el primer linkedhashmap");
	  	        deliveries = gson.toJson(cookingOrdersDelivery);
	  	        ////Log.i("MISPRUEBAS","TAMAÃ‘P DE COOKINGORDERSTABE : "+cookingOrdersTable.size());
	  	        gb.registerTypeAdapter(new LinkedHashMap<LinkedHashMap<Registrable, Integer>, Long>().getClass(), new SerialiserLinkedHashMapLong());
	  	        gson = gb.create();
	  	        times = gson.toJson(cookingOrdersTimes);
	  	        if(cookingOrdersTable.size() > 0){ //it can be 0 when there are orders but not assigned to a table
	  	        	gb.registerTypeAdapter(new LinkedHashMap<LinkedHashMap<Registrable, Integer>, CurrentTable<Table,Integer>>().getClass(), new SerialiserLinkedHashMapCurrentTable());
	  		        gson = gb.create();
	  		        tables = gson.toJson(cookingOrdersTable);
	  		        ////Log.i("MISPRUEBAS","acabo el segundo linkedhashmap: "+tables);	
	  	        }        
	  		}catch(Exception e){
	  			e.printStackTrace();
	  		}
	  		threadMsg(list, togos, times, tables, deliveries);
          }

          private void threadMsg(String list,String togos,String times,String tables, String deliveries) {
        	  	Initialactivity activity = mActivity.get();
        	  	if(!isPausing)
        	  		activity.backUpOrdersHandler.postDelayed(activity.constantBackUp, 60 * 1000); 
                  Message msgObj = activity.backUpOrdersHandler.obtainMessage();
                  Bundle b = new Bundle();
                  b.putString("list", list);
                  b.putString("togos", togos);
                  b.putString("times", times);
                  b.putString("tables", tables);
                  b.putString("deliveries", deliveries);
                  msgObj.setData(b);
                  activity.backUpOrdersHandler.sendMessage(msgObj);
              
          }

        

      }
	
	private static class BackUpOrdersHandler extends Handler {
	    private final WeakReference<Initialactivity> mActivity;

	    public BackUpOrdersHandler(Initialactivity activity) {
	      mActivity = new WeakReference<Initialactivity>(activity);
	    }

	    @Override
	    public void handleMessage(Message msg) {
	      Initialactivity activity = mActivity.get();
	      if (activity != null) {
	    	  String list = msg.getData().getString("list");
	    	  String deliveries = msg.getData().getString("deliveries");
	    	  String togos = msg.getData().getString("togos");
	    	  String tables = msg.getData().getString("tables");
	    	  String times = msg.getData().getString("times");
	    	  Log.i("MISPRUEBAS","lista: "+tables);
	        // ...actualizo listas si son 
	    	  if(!list.equalsIgnoreCase("")){
	  			SharedPreferences prefs = Util.getSharedPreferences(activity);
	  			prefs.edit().putString(Setup.COOKING_ORDERS, list).putString(Setup.COOKING_ORDERS_DELIVERIES, deliveries).putString(Setup.COOKING_ORDERS_TOGOS, togos).putString(Setup.COOKING_ORDERS_TIMES, times).putString(Setup.COOKING_ORDERS_TABLES, tables).commit();
	  			if(isPausing){
	  				Initialactivity.cookingOrders.clear();
		  			Initialactivity.cookingOrdersDelivery.clear();
		  			Initialactivity.cookingOrdersTimes.clear();
		  			Initialactivity.cookingOrdersTogo.clear();
		  			Initialactivity.cookingOrdersTable.clear();
	  			}
	  			
	  		}
	      }
	    }
	  }
	
	private static class RecoverThread implements Runnable{
		  private WeakReference<Initialactivity> mActivity;
		  
		  public RecoverThread(Initialactivity activity){
			  mActivity = new WeakReference<Initialactivity>(activity);
		  }
        // After call for background.start this run method call
        @SuppressWarnings("unchecked")
		public void run() {
        	Initialactivity.cookingOrders.clear();
        	Initialactivity.cookingOrdersDelivery.clear();
        	Initialactivity.cookingOrdersTimes.clear();
        	Initialactivity.cookingOrdersTable.clear();
        	Initialactivity.cookingOrdersTogo.clear();
    		SharedPreferences prefs = Util.getSharedPreferences(mActivity.get());
    		String list = prefs.getString(Setup.COOKING_ORDERS, "[]");
    		String deliveries = prefs.getString(Setup.COOKING_ORDERS_DELIVERIES, "[]");
    		String togos = prefs.getString(Setup.COOKING_ORDERS_TOGOS, "[]");
    		String times = prefs.getString(Setup.COOKING_ORDERS_TIMES, "[]");
    		String tables = prefs.getString(Setup.COOKING_ORDERS_TABLES, "[]");
    		prefs.edit().putString(Setup.COOKING_ORDERS, null).putString(Setup.COOKING_ORDERS_DELIVERIES, null).putString(Setup.COOKING_ORDERS_TOGOS, null).putString(Setup.COOKING_ORDERS_TIMES, null).putString(Setup.COOKING_ORDERS_TABLES, null).commit();
    		try{
    			GsonBuilder gsonBuilder = new GsonBuilder();
    		    gsonBuilder.registerTypeAdapter(LinkedList.class, new SerialiserLinkedList());
    		    gsonBuilder.registerTypeAdapter(new LinkedHashMap<LinkedHashMap<Registrable, Integer>, Integer>().getClass(), new SerialiserLinkedHashMap());
    		    Gson gson = gsonBuilder.create();
    		    Initialactivity.cookingOrders = gson.fromJson(list, LinkedList.class);//usar solo estos objetos para llenar los otros
    		    LinkedHashMap<LinkedHashMap<Registrable,Integer>, Integer> cookingOrdersDelivery_temp = gson.fromJson(deliveries, new LinkedHashMap<LinkedHashMap<Registrable, Integer>, Integer>().getClass());
    		    LinkedHashMap<LinkedHashMap<Registrable,Integer>, Integer> cookingOrdersTogo_temp = gson.fromJson(togos, new LinkedHashMap<LinkedHashMap<Registrable, Integer>, Integer>().getClass());
    		    gsonBuilder.registerTypeAdapter(new LinkedHashMap<LinkedHashMap<Registrable, Integer>, Long>().getClass(), new SerialiserLinkedHashMapLong());
    		    gson = gsonBuilder.create();
    		    LinkedHashMap<LinkedHashMap<Registrable,Integer>,Long> cookingOrdersTimes_temp = gson.fromJson(times, new LinkedHashMap<LinkedHashMap<Registrable, Integer>, Long>().getClass());
    		    
    		    Set<Map.Entry<LinkedHashMap<Registrable,Integer>,Integer>> valoresTogo = cookingOrdersTogo_temp.entrySet();
    		    Set<Map.Entry<LinkedHashMap<Registrable,Integer>,Integer>> valoresDelivery = cookingOrdersDelivery_temp.entrySet();
    		    Set<Map.Entry<LinkedHashMap<Registrable,Integer>,Long>> valoresTime = cookingOrdersTimes_temp.entrySet();
    		    int i = 0;
    		    for(Entry<LinkedHashMap<Registrable, Integer>, Integer> objeto : valoresTogo){
    		    	LinkedHashMap<Registrable, Integer> actual = cookingOrders.get(i);
    		    	Initialactivity.cookingOrdersTogo.put(actual,objeto.getValue());
    		    	i++;
    		    }
    		    i = 0;
    		    for(Entry<LinkedHashMap<Registrable, Integer>, Integer> objeto : valoresDelivery){
    		    	LinkedHashMap<Registrable, Integer> actual = cookingOrders.get(i);
    		    	Initialactivity.cookingOrdersDelivery.put(actual,objeto.getValue());
    		    	i++;
    		    }
    		    i = 0;
    		    for(Entry<LinkedHashMap<Registrable, Integer>, Long> objeto : valoresTime){
    		    	LinkedHashMap<Registrable, Integer> actual = cookingOrders.get(i);
    		    	Initialactivity.cookingOrdersTimes.put(actual,objeto.getValue());
    		    	i++;
    		    }
    		   
    		    Log.i("MISPRUEBAS","Tables string: "+tables);
    			if(tables != "[]"){ //if any order is assigned to a table
    			    gsonBuilder.registerTypeAdapter(new LinkedHashMap<LinkedHashMap<Registrable, Integer>, CurrentTable<Table,Integer>>().getClass(), new SerialiserLinkedHashMapCurrentTable());
    			    gson = gsonBuilder.create();
    			    LinkedHashMap<LinkedHashMap<Registrable,Integer>,CurrentTable<Table,Integer>> cookingOrdersTable_temp = gson.fromJson(tables, new LinkedHashMap<LinkedHashMap<Registrable, Integer>, CurrentTable<Table,Integer>>().getClass());
    			    //imprimo cookingOrders
    			    int j = 0;
    			    for(LinkedHashMap<Registrable, Integer> order : cookingOrders){
    			    	
    			    	Log.i("MISPRUEBAS2","-Orden No. "+j);
    			    	for(Map.Entry<Registrable, Integer> entrada : order.entrySet()){
    			    		Log.i("MISPRUEBAS2","--"+entrada.getKey().name+" : "+entrada.getValue());
    			    	}
    			    	j++;
    			    }
    			    //imprimocookingOrdersTable_temp
    			    j = 0;
    			    for(Entry<LinkedHashMap<Registrable, Integer>, CurrentTable<Table, Integer>> ordertable : cookingOrdersTable_temp.entrySet()){
    			    	
    			    	Log.i("MISPRUEBAS2","-OrdertableTemp No. "+j);
    			    	Log.i("MISPRUEBAS2","-Mesa temp: "+ordertable.getValue().getTable().getName());
    			    	for(Entry<Registrable,Integer> orden : ordertable.getKey().entrySet()){
    			    		Log.i("MISPRUEBAS2","--"+orden.getKey().name+" : "+orden.getValue());
    			    	}
    			    	
    			    	j++;
    			    }
    			    
    			    
    			    Set<Entry<LinkedHashMap<Registrable, Integer>, CurrentTable<Table, Integer>>> valoresTable = cookingOrdersTable_temp.entrySet();
    			    i = 0;
    			    for(Entry<LinkedHashMap<Registrable, Integer>, CurrentTable<Table, Integer>> objeto : valoresTable){
    			    	//Log.i("MISPRUEBAS","Mesa: "+cookingOrders.get(i)+" - "+objeto.getValue().getTable().getName());
    			    	LinkedHashMap<Registrable, Integer> actual = cookingOrders.get(i);
    			    	Initialactivity.cookingOrdersTable.put(actual,objeto.getValue());
    			    	openTables.push(objeto.getValue());
    			    	i++;
    			    }
    			    
    			  //imprimocookingOrdersTable
    			    j = 0;
    			    for(Entry<LinkedHashMap<Registrable, Integer>, CurrentTable<Table, Integer>> ordertable : cookingOrdersTable.entrySet()){
    			    	
    			    	Log.i("MISPRUEBAS2","-Ordertable No. "+j);
    			    	Log.i("MISPRUEBAS2","-En Mesa: "+ordertable.getValue().getTable().getName());
    			    	for(Entry<Registrable,Integer> orden : ordertable.getKey().entrySet()){
    			    		Log.i("MISPRUEBAS2","--"+orden.getKey().name+" : "+orden.getValue());
    			    	} 			    	
    			    	j++;
    			    }
    			    
    			}
    			
    		}catch(Exception e){
    			e.printStackTrace();
    		}
	  		threadMsg(1);
        }

        private void threadMsg(int ready) {
      	  	Initialactivity activity = mActivity.get();
            
                Message msgObj = activity.recoverOrdersHandler.obtainMessage();
                Bundle b = new Bundle();
                msgObj.setData(b);
                activity.recoverOrdersHandler.sendMessage(msgObj);
            
        }
    }
	
	private static class RecoverOrdersHandler extends Handler {
	    private final WeakReference<Initialactivity> mActivity;

	    public RecoverOrdersHandler(Initialactivity activity) {
	      mActivity = new WeakReference<Initialactivity>(activity);
	    }

	    @Override
	    public void handleMessage(Message msg) {
	      Initialactivity activity = mActivity.get();
	      if (activity != null) {
	    	  List<Long> items = new ArrayList<Long>();
  			List<String> nameTables = new ArrayList<String>();
  			for (LinkedHashMap<Registrable, Integer> current : Initialactivity.cookingOrders) {
  				items.add(Initialactivity.cookingOrdersTimes.get(current));
  				nameTables.add(Initialactivity.cookingOrdersTable.get(current).getTable().getName());
  			}
	    	  SaleAdapter cookingAdapter = new SaleAdapter(activity, items, nameTables,Initialactivity.inflater);
  			Initialactivity.ordersList.setAdapter(cookingAdapter);
  			Initialactivity.ordersList.setOnItemClickListener(Initialactivity.orderListListener);
	      }
	    }
	  }

	
	protected static LinkedList<LinkedHashMap<Registrable,Integer>> getCookingOrders(){
		return cookingOrders;
	}
	protected static LinkedHashMap<LinkedHashMap<Registrable, Integer>, Long> getCookingOrdersTimes(){
		return cookingOrdersTimes;
	}
	protected static LinkedHashMap<LinkedHashMap<Registrable, Integer>, CurrentTable<Table, Integer>> getCookingOrdersTables(){
		return cookingOrdersTable;
	}
	protected static LinkedHashMap<LinkedHashMap<Registrable, Integer>, Integer> getCookingOrdersTogo(){
		return cookingOrdersTogo;
	}
	protected static LinkedHashMap<LinkedHashMap<Registrable, Integer>, Integer> getCookingOrdersDelivery(){
		return cookingOrdersDelivery;
	}

	
	
	
	
	

}
