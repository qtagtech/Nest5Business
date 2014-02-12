package com.nest5.businessClient;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class BluetoothSerialService
{
  private static final boolean D = false;
  public static final int STATE_CONNECTED = 3;
  public static final int STATE_CONNECTING = 2;
  public static final int STATE_LISTEN = 1;
  public static final int STATE_NONE = 0;
  private static final UUID SerialPortServiceClass_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
  private static final String TAG = "BluetoothSerialService";
  private final BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
  private ConnectThread mConnectThread;
  private ConnectedThread mConnectedThread;
  private final Handler mHandler;
  
  private int mState = 0;

  public BluetoothSerialService(Context paramContext, Handler paramHandler)
  {
    this.mHandler = paramHandler;
    
  }

  private void connectionFailed()
  {
    setState(0);
    Message localMessage = this.mHandler.obtainMessage(5);
    Bundle localBundle = new Bundle();
    localBundle.putString("toast", "Unable to connect device");
    localMessage.setData(localBundle);
    this.mHandler.sendMessage(localMessage);
  }

  private void connectionLost()
  {
    setState(0);
    Message localMessage = this.mHandler.obtainMessage(5);
    Bundle localBundle = new Bundle();
    localBundle.putString("toast", "Device connection was lost");
    localMessage.setData(localBundle);
    this.mHandler.sendMessage(localMessage);
  }

  private void setState(int paramInt)
  {
    
    try
    {
      this.mState = paramInt;
      this.mHandler.obtainMessage(1, paramInt, -1).sendToTarget();
      return;
    }
    finally
    {
     // localObject = finally;
    }
    //throw localObject;
  }

  public void connect(BluetoothDevice paramBluetoothDevice)
  {
    
    try
    {
      if ((this.mState == 2) && (this.mConnectThread != null))
      {
        this.mConnectThread.cancel();
        this.mConnectThread = null;
      }
      if (this.mConnectedThread != null)
      {
        this.mConnectedThread.cancel();
        this.mConnectedThread = null;
      }
      this.mConnectThread = new ConnectThread(paramBluetoothDevice);
      this.mConnectThread.start();
      setState(2);
      
      return;
    }
    finally
    {
      //localObject = finally;
      
    }
    //throw localObject;
  }

  public void connected(BluetoothSocket paramBluetoothSocket, BluetoothDevice paramBluetoothDevice)
  {
    
    try
    {
      if (this.mConnectThread != null)
      {
        this.mConnectThread.cancel();
        this.mConnectThread = null;
      }
      if (this.mConnectedThread != null)
      {
        this.mConnectedThread.cancel();
        this.mConnectedThread = null;
      }
      this.mConnectedThread = new ConnectedThread(paramBluetoothSocket);
      this.mConnectedThread.start();
      Message localMessage = this.mHandler.obtainMessage(4);
      Bundle localBundle = new Bundle();
      localBundle.putString("device_name", paramBluetoothDevice.getName());
      localMessage.setData(localBundle);
      this.mHandler.sendMessage(localMessage);
      setState(3);
      return;
    }
    finally
    {
      //localObject = finally;
      
    }
    //throw localObject;
  }

  public int getState()
  {
    
    try
    {
      int i = this.mState;
      
      return i;
    }
    finally
    {
      //localObject = finally;
      
    }
    //throw localObject;
  }

  public void start()
  {
    
    try
    {
      if (this.mConnectThread != null)
      {
        this.mConnectThread.cancel();
        this.mConnectThread = null;
      }
      if (this.mConnectedThread != null)
      {
        this.mConnectedThread.cancel();
        this.mConnectedThread = null;
      }
      setState(0);
      
      return;
    }
    catch (Exception e) {
		// TODO: handle exception
	}
    
   
  }

  public void stop()
  {
    
    try
    {
      if (this.mConnectThread != null)
      {
        this.mConnectThread.cancel();
        this.mConnectThread = null;
      }
      if (this.mConnectedThread != null)
      {
        this.mConnectedThread.cancel();
        this.mConnectedThread = null;
      }
      setState(0);
      
      return;
    }
    finally
    {
      
    }
    
  }

  public void write(byte[] paramArrayOfByte)
  {
    
    try
    {
      if (this.mState != 3)
      {
        
      }
      else
      {
        ConnectedThread localConnectedThread = this.mConnectedThread;
        
        localConnectedThread.write(paramArrayOfByte);
      }
    }
    finally
    {
      
    }
  }

  private class ConnectThread extends Thread
  {
    private final BluetoothDevice mmDevice;
    private final BluetoothSocket mmSocket;

    public ConnectThread(BluetoothDevice arg2)
    {
      
      this.mmDevice = arg2;
      BluetoothSocket localBluetoothSocket = null;
      Method tmpM = null;
      try {
		tmpM = mmDevice.getClass().getMethod("createInsecureRfcommSocket", new Class[] { int.class });
		
		try {
			localBluetoothSocket = (BluetoothSocket)tmpM.invoke(mmDevice, Integer.valueOf(1));
		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			
			e.printStackTrace();
		}
         //localBluetoothSocket = arg2.createRfcommSocketToServiceRecord(BluetoothSerialService.SerialPortServiceClass_UUID);
		
		
	} catch (NoSuchMethodException e) {
		
		e.printStackTrace();
	}
      
    
      this.mmSocket = localBluetoothSocket;
      return;
    }

    public void cancel()
    {
      try
      {
        this.mmSocket.close();
        return;
      }
      catch (IOException localIOException)
      {
        while (true)
          Log.e("BluetoothSerialService", "close() of connect socket failed", localIOException);
      }
    }

    public void run()
    {
      Log.i("BluetoothSerialService", "BEGIN mConnectThread");
      setName("ConnectThread");
      BluetoothSocket localSocket = null;
      BluetoothSerialService.this.mAdapter.cancelDiscovery();
      try
      {
        this.mmSocket.connect();
      }
      catch (IOException localIOException1)
      {
    	  Log.i(TAG,"Fallo la primera Conexion");
    	  try {
			localSocket = mmDevice.createRfcommSocketToServiceRecord(BluetoothSerialService.SerialPortServiceClass_UUID);
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG,"Fallo la creacion de la segunda Conexion");
				
		}
    	  
              
          	
              try {
            	Log.d(TAG,"Tratando localSocket.connect");
				localSocket.connect();
			} catch (IOException e) {
				e.printStackTrace();
				Log.d(TAG,"Fallo la segunda conexion");
				BluetoothSerialService.this.connectionFailed();
		          try
		          {
		            this.mmSocket.close();
		            localSocket.close();
		           
		          }
		          catch (IOException localIOException2)
		          {
		            Log.e("BluetoothSerialService", "unable to close() socket during connection failure", localIOException2);
		          }
			}
          
        
      }
      
      synchronized (BluetoothSerialService.this)
      {
    	  BluetoothSerialService.this.mConnectThread = null;
    	  if(mmSocket.isConnected()) //para dispositivos que dejen conectar con conexion insegura de una.
    	  {
    	      BluetoothSerialService.this.connected(mmSocket, this.mmDevice); 
    	      return;
    	  }
          BluetoothSerialService.this.connected(localSocket, this.mmDevice);
      }
    }
  }

  private class ConnectedThread extends Thread
  {
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private final BluetoothSocket mmSocket;

    public ConnectedThread(BluetoothSocket arg2)
    {
      Log.d("BluetoothSerialService", "create ConnectedThread");
      
      this.mmSocket = arg2;
      InputStream localInputStream = null;
      OutputStream localOutputStream = null;
      try
      {
        localInputStream = mmSocket.getInputStream();
        localOutputStream = mmSocket.getOutputStream();
        
        
      }
      catch (IOException localIOException)
      {
       
          Log.e("BluetoothSerialService", "temp sockets not created", localIOException);
      }
      this.mmInStream = localInputStream;
      this.mmOutStream = localOutputStream;
      Log.i(TAG,"Streams de entrada y salida creados");
      return;
    }

    public void cancel()
    {
      try
      {
        this.mmSocket.close();
        return;
      }
      catch (IOException localIOException)
      {
        
          Log.e("BluetoothSerialService", "close() of connect socket failed", localIOException);
      }
    }

    public void run()
    {
      Log.i("BluetoothSerialService", "BEGIN mConnectedThread");
      byte[] arrayOfByte = new byte[1024];
      try
      {
        while (true)
        {
          int i = this.mmInStream.read(arrayOfByte);
          //BluetoothSerialService.this.mSettingsView.write(arrayOfByte, i);
        }
      }
      catch (IOException localIOException)
      {
        Log.e("BluetoothSerialService", "disconnected", localIOException);
        BluetoothSerialService.this.connectionLost();
      }
    }

    public void write(byte[] paramArrayOfByte)
    {
      try
      {
        this.mmOutStream.write(paramArrayOfByte);
        BluetoothSerialService.this.mHandler.obtainMessage(3, paramArrayOfByte.length, -1, paramArrayOfByte).sendToTarget();
        return;
      }
      catch (IOException localIOException)
      {
        
          Log.e("BluetoothSerialService", "Exception during write", localIOException);
      }
    }
  }
}