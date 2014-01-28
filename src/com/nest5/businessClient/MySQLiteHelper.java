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
	  mContext = context;

		  
		  try{
			  getLoadFile();
		  }
		  catch(Exception e){
			  e.printStackTrace();
		  } 

	  
	  
	  

  }

  @Override
  public void onCreate(SQLiteDatabase database) {
	  Log.w(MySQLiteHelper.class.getName(),"Creating database version " + DATABASE_VERSION);
	  for(int i = 0; i < CREATE_SCRIPT.size() ; i++)
	  {
		  Log.w(MySQLiteHelper.class.getName(),"SQL: " + CREATE_SCRIPT.get(i));
		  database.execSQL(CREATE_SCRIPT.get(i)); 
	  }
    
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	  
    Log.w(MySQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion);
    
    

    for(int i = 0; i < DROP_SCRIPT.size() ; i++)

	  {
		  db.execSQL(DROP_SCRIPT.get(i)); 
	  }
    
  //carga archivos de sql raw
	  try{
		  getLoadFile();
	  }
	  catch(Exception e){
		  
	  }

	  onCreate(db);

	  
    
    
  }
  
  private void getLoadFile() throws IOException {
	    String line;
	    StringBuilder text = new StringBuilder();
	    ArrayList<String> sqlStmts = new ArrayList<String>();
	    ArrayList<String> sqlDropStmts = new ArrayList<String>();
	    StringBuilder text2 = new StringBuilder();
	    ArrayList<String> sqlTempStmts = new ArrayList<String>();
	    ArrayList<String> dropTempStmts = new ArrayList<String>();
	    
		   Log.d("ACAAAAAAAAA","en nueva base de datos");
		   try {
		        InputStream inputStream = new FileInputStream(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/dbestructurenest5.sql"));//mContext.getResources().openRawResource(R.raw.mytables_dbnueva);
		        
		        InputStreamReader inputreader = new InputStreamReader(inputStream);
		        BufferedReader buffreader = new BufferedReader(inputreader);

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
	   
	    
	    
	    DROP_SCRIPT =  sqlDropStmts;
	    CREATE_SCRIPT = sqlStmts;
	    
	   
	 }
  public static String DB_FILEPATH = Environment.getDataDirectory() +"/data/com.nest5.businessClient/databases/nest5pos.db";

  /**
   * Copies the database file at the specified location over the current
   * internal application database.
   * */
  public boolean importDatabase(String dbPath) throws IOException {

      // Close the SQLiteOpenHelper so it will commit the created empty
      // database to internal storage.
      close();
      File newDb = new File(dbPath);
      File oldDb = new File(DB_FILEPATH);
      if (newDb.exists()) {
          FileUtils.copyFile(new FileInputStream(newDb), new FileOutputStream(oldDb));
          // Access the copied database so SQLiteHelper will cache it and mark
          // it as created.
          getWritableDatabase().close();
          return true;
      }
      return false;
  }

  
  public void cleanDatabase(SQLiteDatabase db){
	  /*
	     * Cuando se quiera limpiar la base de datos se usan los siguientes dos loops y al final se va a onCreate, primero se toma todo de los archivos para borrar temps y originiales
	     * porque manda el parametro nuevaDB en false, en el construcor de esta clase, de resto se usa lo que hay despues de la explicacion larga que es para actualizar db, tomando
	     * siempre  todo con nuevaDB = false
	     * al final luego de borrar todo lo que hubiera, temps y originales, toma con nueva db true para solo usar el archivo q es para nueva db y poner lo valores iniciales
	     * predeterminados
	     * 
	     * */
	    for(int i = 0; i < DROP_SCRIPT.size() ; i++)
		  {
			  db.execSQL(DROP_SCRIPT.get(i)); 
		  }
	    
	  //carga archivos de sql raw
		  try{
			  getLoadFile();
		  }
		  catch(Exception e){
			  
		  }
		  onCreate(db);
  }


} 