package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UnitDataSource {
	
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private Context mContext;
	  private String[] allColumns = { Setup.COLUMN_ID,Setup.COLUMN_NAME,Setup.COLUMN_UNIT_INITIALS,Setup.COLUMN_UNIT_MULTIPLIERS };

	  public UnitDataSource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	    mContext = context;
	  }

	  public SQLiteDatabase open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	    return database;
	  }
	  
	  public void open(SQLiteDatabase _database)
	  {
		  database = _database;
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public Unit createUnit(String name,String initials,String multipliers) {
	    ContentValues values = new ContentValues();
	    values.put(Setup.COLUMN_NAME, name);
	    values.put(Setup.COLUMN_UNIT_INITIALS, initials);
	    values.put(Setup.COLUMN_UNIT_MULTIPLIERS, multipliers);
	    long insertId = database.insert(Setup.TABLE_UNIT, null,
	        values);
	    Cursor cursor = database.query(Setup.TABLE_UNIT,
	        allColumns, Setup.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Unit newUnit = cursorToUnit(cursor);
	    cursor.close();
	    return newUnit;
	  }

	  public void deleteUnit(Unit unit) {
	    long id = unit.getId();
	    System.out.println("Unit deleted with id: " + id);
	    database.delete(Setup.TABLE_UNIT, Setup.COLUMN_ID
	        + " = " + id, null);
	  }

	  public List<Unit> getAllUnits() {
	    List<Unit> units = new ArrayList<Unit>();

	    Cursor cursor = database.query(Setup.TABLE_UNIT,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Unit unit = cursorToUnit(cursor);
	      units.add(unit);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return units;
	  }
	  
	  public Unit getUnit(long id) {
		   Unit unit = null;
		   Cursor cursor = database.rawQuery("select * from " + Setup.TABLE_UNIT + " where " + Setup.COLUMN_ID + "=" + id  , null);
	        if (cursor != null) 
	        	{
	        		cursor.moveToFirst();
	        		
	      		      unit = cursorToUnit(cursor);
	      		      
	      		    // Make sure to close the cursor
	      		    cursor.close();
	        	}
	        return unit;
		  }

	  private Unit cursorToUnit(Cursor cursor) {
	    Unit unit = new Unit();
	    unit.setId(cursor.getLong(0));
	    unit.setName(cursor.getString(1));
	    unit.setInitials(cursor.getString(2));
	    LinkedHashMap<String, Double> multipliers = convertToHashMap(cursor.getString(3));
	    unit.setMultipliers(multipliers);
	    return unit;
	  }
	  
	  public static LinkedHashMap<String,Double> convertToHashMap(String savedString)
	  {
		  savedString = savedString.replace("{", " ");
		  savedString = savedString.replace("}", " ");
		  String clean = savedString.trim();
		  String pairs[] = clean.split(",");
		  LinkedHashMap<String, Double> resultSet = new LinkedHashMap<String, Double>();
		  for(int i = 0; i < pairs.length; i++){
			  String cleanPair = pairs[i].trim();
			  String keyValuPair[] = pairs[i].split(":");
			  if(keyValuPair.length != 2)
			  {
				  Log.e("CONVERTING_MULTIPLIERS","Error El formato de: "+cleanPair+" No es de un Multiplicador Correcto");
			  }
			  else
			  {
				  resultSet.put(keyValuPair[0].trim(), Double.valueOf(keyValuPair[1].trim()));
			  }
				  
		  }
		  return resultSet;
		  
	  }

}
