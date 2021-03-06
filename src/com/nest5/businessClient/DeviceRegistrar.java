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



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;
import android.util.Log;


public class DeviceRegistrar {
    public static final String ACCOUNT_NAME_EXTRA = "AccountName";

    public static final String STATUS_EXTRA = "Status";

    public static final int REGISTERED_STATUS = 1;

    public static final int UNREGISTERED_STATUS = 2;

    public static final int ERROR_STATUS = 3;

    private static final String TAG = "DeviceRegistrar";
    private static  SharedPreferences settings;
    private  static  String accountName;
    private  static Intent updateUIIntent;
    private static Context contextApp;
    private static Boolean isRegister;
    private static String deviceId;
    private static String deviceRegId;
    public static void registerOrUnregister(final Context context,final String deviceRegistrationId, final boolean register) {
    	contextApp = context;
    	isRegister = register;
    	deviceRegId = deviceRegistrationId;
        settings = Util.getSharedPreferences(context);
        accountName = settings.getString(Util.ACCOUNT_NAME, "Unknown");
       updateUIIntent = new Intent(Util.UPDATE_UI_INTENT);
       
        
        
        

        deviceId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        

        
        if (register) {
            //req = request.register().using(proxy);
        	onSuccess("Conectado");
        } else {
        	onSuccess("Desconectado");
            //req = request.unregister().using(proxy);
        }
        
       
    }
    private static void clearPreferences(SharedPreferences.Editor editor) {
        editor.remove(Util.ACCOUNT_NAME);
        editor.remove(Util.AUTH_COOKIE);
        editor.remove(Util.DEVICE_REGISTRATION_ID);
    }
    /*req.fire(new Receiver<Void>() {*/
        

        
        public static void onFailure(String failure) {
            Log.w(TAG, "Error, se obtuvo :" + failure);
            // Clean up application state
            SharedPreferences.Editor editor = settings.edit();
            clearPreferences(editor);
            editor.commit();

            updateUIIntent.putExtra(ACCOUNT_NAME_EXTRA, accountName);
            updateUIIntent.putExtra(STATUS_EXTRA, ERROR_STATUS);
            contextApp.sendBroadcast(updateUIIntent);
        }

        
        public static void onSuccess(String response) {
            SharedPreferences settings = Util.getSharedPreferences(contextApp);
            SharedPreferences.Editor editor = settings.edit();
            if (isRegister) {
                editor.putString(Util.DEVICE_REGISTRATION_ID, deviceRegId);
            } else {
                clearPreferences(editor);
            }
            editor.commit();
            updateUIIntent.putExtra(ACCOUNT_NAME_EXTRA, accountName);
            updateUIIntent.putExtra("USER_ID", deviceRegId);
            updateUIIntent.putExtra(STATUS_EXTRA, isRegister ? REGISTERED_STATUS
                    : UNREGISTERED_STATUS);
            contextApp.sendBroadcast(updateUIIntent);
        }
    
}
