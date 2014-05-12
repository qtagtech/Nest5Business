package com.nest5.businessClient;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
 
public class TCPPrint {
 
    private String serverMessage;
    public String SERVERIP; //your computer IP address
    public  int SERVERPORT;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;
    public static final int CONNECTING = 2;
    public static final int CONNECTED = 1;
    public static final int DISCONNECTED = 0;
    public static final int SUDDENLY_DISCONNECTED = -1;
    public static final String STATUS_CHANGE= "status_change";
    private int status = 0;
    private Context mContext;
    
 
    PrintWriter out;
    BufferedReader in;
 
    /**
     *  Constructor of the class. OnMessagedReceived listens for the messages received from server
     */
    public TCPPrint(OnMessageReceived listener, Context context,String server, int port) {
        mMessageListener = listener;
        mContext = context; 
        SERVERIP = server;
        SERVERPORT = port;
    }
 
    /**
     * Sends the message entered by client to the server
     * @param message text entered by client
     */
    public void sendMessage(String message){
        if (out != null && !out.checkError()) {
            out.println(message);
            out.flush();
        }
    }
 
    public void stopClient(){
        mRun = false;
    }
    public int getStatus(){
    	return this.status;
    }
    public void setStatus(int _status){
    	this.status = _status;
    	if (mMessageListener != null) {
            mMessageListener.messageReceived(STATUS_CHANGE);
        }
    }
 
    public void run() {
 
        mRun = true;
 
        try {
        	
        	SharedPreferences prefs = Util.getSharedPreferences(mContext);
        	Log.i("TCP Print",SERVERIP);
        	Log.i("TCP Print",String.valueOf(SERVERPORT));
            //here you must put your computer's IP address.
        	Log.i("TCP Print","Empezando printer client");
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
            setStatus(CONNECTING);
            //create a socket to make the connection with the server
            Socket socket = new Socket(serverAddr, SERVERPORT);
 
            try {
 
                //send the message to the server
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                setStatus(CONNECTED);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //in this while the client listens for the messages sent by the server
                while (mRun) {
                    serverMessage = in.readLine();
 
                    if (serverMessage != null && mMessageListener != null) {
                        //call the method messageReceived from MyActivity class
                        mMessageListener.messageReceived(serverMessage);
                    }
                    serverMessage = null;
 
                }
 
                Log.e("TCP Print", "S: Received Message: '" + serverMessage + "'");
 
            } catch (Exception e) {
            	e.printStackTrace();
                Log.e("TCP Print", "S: Error", e);
            } finally {
                //the socket must be closed. It is not possible to reconnect to this socket
                // after it is closed, which means a new socket instance has to be created.
                socket.close();
                setStatus(SUDDENLY_DISCONNECTED);
            }
 
        } catch (Exception e) {
        	e.printStackTrace();
            Log.e("TCP Print", "C: Error", e);
            setStatus(DISCONNECTED);
 
        }
 
    }
 
    //Declare the interface. The method messageReceived(String message) will must be implemented in the MyActivity
    //class at on asynckTask doInBackground
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}
