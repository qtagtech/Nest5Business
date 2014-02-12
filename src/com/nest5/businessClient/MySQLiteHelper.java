package com.nest5.businessClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;

import android.content.SharedPreferences;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

  public static final String TABLE_CATEGORY_INGEREDIENTS = "ingredient_category";


  
  private Context mContext;
  private static final String DATABASE_NAME = "nest5pos.db";
  private static final int DATABASE_VERSION = 1;
  
  private ArrayList<String> CREATE_SCRIPT;
  private ArrayList<String> DROP_SCRIPT;

  

  // Database creation sql statement
  /*private static final String DATABASE_CREATE = "create table "
      + TABLE_CATEGORY_INGEREDIENTS + "(" + Setup.COLUMN_ID
      + " integer primary key autoincrement, " + Setup.COLUMN_INGREDIENT_NAME
      + " text not null);";*/

  public MySQLiteHelper(Context context) {
	  
	  super(context, DATABASE_NAME, null, DATABASE_VERSION);
	 
	  Log.w(MySQLiteHelper.class.getName(),"Creando objeto MySQLHelper");
	
	  mContext = context;

  }

  @Override
  public void onCreate(SQLiteDatabase database) {
	  Log.w(MySQLiteHelper.class.getName(),"Creating database version " + DATABASE_VERSION);
	  try{
		  getLoadFile();
		  for(int j = 0; j < DROP_SCRIPT.size(); j++){
			  Log.w(MySQLiteHelper.class.getName(),"SQL: " + DROP_SCRIPT.get(j));
			  database.execSQL(DROP_SCRIPT.get(j));  
		  }
		  for(int i = 0; i < CREATE_SCRIPT.size() ; i++)
		  {
			  Log.w(MySQLiteHelper.class.getName(),"SQL: " + CREATE_SCRIPT.get(i));
			  database.execSQL(CREATE_SCRIPT.get(i)); 
		  }
	  }
	  catch(Exception e){
		  
	  }
	  
	  
    
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	  
    Log.w(MySQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion);
    try{
    	getLoadFile();
    	for(int i = 0; i < DROP_SCRIPT.size() ; i++)

  	  {
  		  db.execSQL(DROP_SCRIPT.get(i)); 
  	  }
    }
    catch(Exception e){
    	
    }
  
	  File base = null;
	  try{
		  base = new File(mContext.getExternalFilesDir(null) + Environment.getDataDirectory().getPath()+"/databases/","nest5posinit.sql");
		  base.delete();
	  }
	  catch (Exception e){}

  }
  
  private void getLoadFile() throws IOException {
	    String line;
	    StringBuilder text = new StringBuilder();
	    ArrayList<String> sqlStmts = new ArrayList<String>();
	    ArrayList<String> sqlDropStmts = new ArrayList<String>();
	    
	    
		   Log.d("ACAAAAAAAAA","en nueva base de datos");
		   Log.w(MySQLiteHelper.class.getName(),"En getLoadFile()");
		   File base = null;
		   BufferedReader buffreader = null;
		   InputStream inputStream = null;
		   
		   try {
			   
			    base = new File(mContext.getExternalFilesDir(null) + Environment.getDataDirectory().getPath()+"/databases/","nest5posinit.sql");
		         inputStream = new FileInputStream(base);//mContext.getResources().openRawResource(R.raw.mytables_dbnueva);
		        
		        InputStreamReader inputreader = new InputStreamReader(inputStream);
		         buffreader = new BufferedReader(inputreader);

		        while (( line = buffreader.readLine()) != null) {
		            // Build the Create statements
		            if (line.trim().length() > 1) {
		                if (line.trim().endsWith(";")) {
		                    text.append(" " + line.trim());
		                    sqlStmts.add(text.toString());
		                } else {
		                    text.append(" " + line.trim());
		                }
		            } else {
		                if (text.length() > 0) 
		                    text.delete(0, text.length());
		            }

		            // Build the Drop statements
		            if (line.trim().contains("CREATE TABLE")) {
		                // Capture the table
		                sqlDropStmts.add("DROP TABLE IF EXISTS " + text.substring(13, text.length() - 1).trim().toString());
		            }

		            if (line.trim().contains("CREATE INDEX")) {
		                // Capture the index
		                sqlDropStmts.add("DROP INDEX IF EXISTS " + text.substring(13, text.length()).trim().toString());
		            }

		            if (line.trim().contains("CREATE VIEW")) {
		                // Capture the view
		                sqlDropStmts.add("DROP VIEW IF EXISTS " + text.substring(13, text.length()).trim().toString());
		            }
		        }
		        
		    } catch (IOException ie) {
		        // We have an error to look up
		        Log.e("MySQLLiteHelper", "This Error Occured " + ie.toString());
		    } catch (Exception e) {
		        // We have an error to look up
		        Log.e("MySQLLiteHelper", "This Error Occured " + e.toString());
		    }
		   finally{
			   //if(base != null)
				  // base.delete();
			   if(inputStream != null){
				   inputStream.close();
				   buffreader.close();  
			   }
			   
		   }
	   
	    
	    
	    DROP_SCRIPT =  sqlDropStmts;
	    CREATE_SCRIPT = sqlStmts;
	    
	   
	 }

  


} 