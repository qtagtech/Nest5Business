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
  private ArrayList<String> CREATE_TEMP_SCRIPT;
  private ArrayList<String> DROP_TEMP_SCRIPT;
  

  // Database creation sql statement
  /*private static final String DATABASE_CREATE = "create table "
      + TABLE_CATEGORY_INGEREDIENTS + "(" + Setup.COLUMN_ID
      + " integer primary key autoincrement, " + Setup.COLUMN_INGREDIENT_NAME
      + " text not null);";*/

  public MySQLiteHelper(Context context) {
	  
	  super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  mContext = context;
	  SharedPreferences prefs = Util.getSharedPreferences(mContext);
	  if(prefs.getBoolean(Setup.NEW_INSTALL, true)){
		  Log.i("MISPRUEBAS", "NUEVO INSTALL");
		  try{
			  getLoadFile(true);
		  }
		  catch(Exception e){
			  
		  } 
		  prefs.edit().putBoolean(Setup.NEW_INSTALL, false).commit();
	  }
	  else{
		  Log.i("MISPRUEBAS", " NO ESNUEVO INSTALL");
		  try{
			  getLoadFile(false);
		  }
		  catch(Exception e){
			  
		  }
		  
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
            + newVersion + ", saving all data in temp tables and restoring to new tables");
    
    /*
     * 
     * ACA EMPIEZA SECCION PARA NUEVA BASE DE DATOS
     * 
     * 
     * para hacer el cambio se copia todo lo que hay en mytables y se pega reemplazado en mytables_backup, alla se pone la terminacion _bu en todas las tablas y en todos los 
     * statements de insert se pone _bu al final de insert into table_bu y se quita de donde se selecciona al final. luego se vuelve a mytables y se agrega el campo o la tabla nueva
     * que se quiera poner y e los statements de insert al final se pone en la tabla que se hicieron cambios el valor por defecto que se debe poner
     * */
    
    /*
     * Cuando se quiera limpiar la base de datos se usan los siguientes dos loops y al final se va a onCreate, primero se toma todo de los archivos para borrar temps y originiales
     * porque manda el parametro nuevaDB en false, en el construcor de esta clase, de resto se usa lo que hay despues de la explicacion larga que es para actualizar db, tomando
     * siempre  todo con nuevaDB = false
     * al final luego de borrar todo lo que hubiera, temps y originales, toma con nueva db true para solo usar el archivo q es para nueva db y poner lo valores iniciales
     * predeterminados
     * 
     * */
    /*for(int i = 0; i < DROP_SCRIPT.size() ; i++)
	  {
		  db.execSQL(DROP_SCRIPT.get(i)); 
	  }
    for(int i = 0; i < DROP_TEMP_SCRIPT.size() ; i++)
	  {
		  db.execSQL(DROP_TEMP_SCRIPT.get(i)); 
	  }
  //carga archivos de sql raw
	  try{
		  getLoadFile(true);
	  }
	  catch(Exception e){
		  
	  }
	  onCreate(db);*/
	  
    /*
     * 
     * ACA TERMINA LA PARTE PARA NUEVA BASE DE DATOS VACIA
     * 
     * */
    
    /*
     * 
     * ACA EMPIEZA SECCION PARA ACTUALIZAR BASE DE DATOS
     * 
     * */
    //toma el script de update que tiene la version vieja del archvio raw mytables
    //luego crea tabals temporales con ese archivo, 
    //depsues inserta todo lo de las tablas actuales en las temporales
    //borra las viejas y quedan las temporales
    //crea las nuevas
    //inserta los datos de las temporales en las nuevas
    //borra las temporales
    
    /*
     * 
     * para hacer el cambio se copia todo lo que hay en mytables y se pega reemplazado en mytables_backup, alla se pone la terminacion _bu en todas las tablas y en todos los 
     * statements de insert se pone _bu al final de insert into table_bu y se quita de donde se selecciona al final. luego se vuelve a mytables y se agrega el campo o la tabla nueva
     * que se quiera poner y e los statements de insert al final se pone en la tabla que se hicieron cambios el valor por defecto que se debe poner
     * 
     * TRATAR SIEMPRE DE LUEGO DE REALIZAR UPDATE REFLEJAS LOS CAMBIOS EN MYTABLES_BACKUP, PARA ASI NO TENER QUE REVISAR TODO Y VOLVERLO A HACER LA PROXIMA VEZ
     * 
     * 
     * */
    
    //Toma todo de mytables_backup, crea tabals temporales e inserta todo lo de las tablas reales en las temporales, con los comnados que hay en el archivo
    Log.w(MySQLiteHelper.class.getName(),"+ELIMINANDO TABLAS TEMPORALES POR SI EXISTIAN+");
    for(int i = 0; i < DROP_TEMP_SCRIPT.size() ; i++)
	  {
		  db.execSQL(DROP_TEMP_SCRIPT.get(i)); 
	  }
    Log.w(MySQLiteHelper.class.getName(),"+TABLAS TEMPORALES ELIMINADAS+");
    
    Log.w(MySQLiteHelper.class.getName(),"+CREANDO TABLAS TEMPORALES+");
    for(int i = 0; i < CREATE_TEMP_SCRIPT.size() ; i++)
	  {
		  db.execSQL(CREATE_TEMP_SCRIPT.get(i)); 
	  }
    Log.w(MySQLiteHelper.class.getName(),"+TABLAS TEMPORALES CREADAS+");
    
    //Borra tablas originales porque todo ya esta guardado en las temporales
    Log.w(MySQLiteHelper.class.getName(),"+BORRANDO TABLAS ORIGINALES+");
    for(int i = 0; i < DROP_SCRIPT.size() ; i++)
	  {
		  db.execSQL(DROP_SCRIPT.get(i)); 
	  }
    Log.w(MySQLiteHelper.class.getName(),"+TABLAS ORIGINALES BORRADAS+");
    //Crea tablas nuevas originales y pasa toda la info de las temporales a las nuevas originales con los comandos que hay alla
    //aca se debe poner en mytables, en la parte de los insert al final, el valor que tendran las filas que ya estaban y se restauraran de las temporales, en la nueva
    //columna que se agregue
    Log.w(MySQLiteHelper.class.getName(),"+CREANDO NUEVAS TABLAS ORIGINALES+");
    for(int i = 0; i < CREATE_SCRIPT.size() ; i++)
	  {
		  db.execSQL(CREATE_SCRIPT.get(i)); 
	  }
    Log.w(MySQLiteHelper.class.getName(),"+NUEVAS TABLAS ORIGINALES CREADAS+");
    //Borra las temporales porque ya estan creadas las nuevas originales y restaurada la informacion con los campos nuevos en los valores que se hayan puesto en mytables raw
    Log.w(MySQLiteHelper.class.getName(),"+ELIMINANDO TABLAS TEMPORALES+");
    for(int i = 0; i < DROP_TEMP_SCRIPT.size() ; i++)
	  {
		  db.execSQL(DROP_TEMP_SCRIPT.get(i)); 
	  }
    Log.w(MySQLiteHelper.class.getName(),"+TABLAS TEMPORALES ELIMINADAS+");
    /*
     * 
     * ACA TERMINA LA PARTE PARA ACTUALIZAR BASE DE DATOS
     * 
     * */
    
  }
  
  private void getLoadFile(Boolean nuevaDB) throws IOException {
	    String line;
	    StringBuilder text = new StringBuilder();
	    ArrayList<String> sqlStmts = new ArrayList<String>();
	    ArrayList<String> sqlDropStmts = new ArrayList<String>();
	    StringBuilder text2 = new StringBuilder();
	    ArrayList<String> sqlTempStmts = new ArrayList<String>();
	    ArrayList<String> dropTempStmts = new ArrayList<String>();
	    
	   if(nuevaDB)
	   {
		   Log.d("ACAAAAAAAAA","en nueva base de datos");
		   try {
		        InputStream inputStream = mContext.getResources().openRawResource(R.raw.mytables_dbnueva);
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
	   }
	   else
	   {
		   Log.d("ACAAAAAAAAA","en actualizar base de datos");
		   try {
		        InputStream inputStream = mContext.getResources().openRawResource(R.raw.mytables);
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
		    
		    try {
		        InputStream inputStreamTemp = mContext.getResources().openRawResource(R.raw.mytables_backup);
		        InputStreamReader inputreaderTemp = new InputStreamReader(inputStreamTemp);
		        BufferedReader buffreader2 = new BufferedReader(inputreaderTemp);

		        while (( line = buffreader2.readLine()) != null) {
		            // Build the Create statements
		            if (line.trim().length() > 1) {
		                if (line.trim().endsWith(";")) {
		                    text2.append(" " + line.trim());
		                    sqlTempStmts.add(text2.toString());
		                } else {
		                    text2.append(" " + line.trim());
		                }
		            } else {
		                if (text2.length() > 0) 
		                    text2.delete(0, text2.length());
		            }

		            // Build the Drop statements
		            if (line.trim().contains("CREATE TEMPORARY TABLE")) {
		                // Capture the table
		            	dropTempStmts.add("DROP TABLE IF EXISTS " + text2.substring(23, text2.length() - 1).trim().toString());
		            }

		            if (line.trim().contains("CREATE INDEX")) {
		                // Capture the index
		            	dropTempStmts.add("DROP INDEX IF EXISTS " + text2.substring(23, text2.length()).trim().toString());
		            }

		            if (line.trim().contains("CREATE VIEW")) {
		                // Capture the view
		            	dropTempStmts.add("DROP VIEW IF EXISTS " + text2.substring(23, text2.length()).trim().toString());
		            }
		        }
		    } catch (IOException ie) {
		        // We have an error to look up
		        Log.e("MySQLLiteHelper", "This Error Occured " + ie.toString());
		    } catch (Exception e) {
		        // We have an error to look up
		        Log.e("MySQLLiteHelper", "This Error Occured " + e.toString());
		    }  
	   }
	    
	    
	    DROP_SCRIPT =  sqlDropStmts;
	    CREATE_SCRIPT = sqlStmts;
	    DROP_TEMP_SCRIPT = dropTempStmts;
	    CREATE_TEMP_SCRIPT = sqlTempStmts;
	   
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
	    for(int i = 0; i < DROP_TEMP_SCRIPT.size() ; i++)
		  {
			  db.execSQL(DROP_TEMP_SCRIPT.get(i)); 
		  }
	  //carga archivos de sql raw
		  try{
			  getLoadFile(true);
		  }
		  catch(Exception e){
			  
		  }
		  onCreate(db);
  }

} 