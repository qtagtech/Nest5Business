package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TaxDataSource {
	
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { Setup.COLUMN_ID,
	      Setup.COLUMN_NAME,Setup.COLUMN_TAX_PERCENTAGE };

	  public TaxDataSource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
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

	  public Tax createTax(String name, double percentage) {
	    ContentValues values = new ContentValues();
	    values.put(Setup.COLUMN_NAME, name);
	    values.put(Setup.COLUMN_TAX_PERCENTAGE, percentage);
	    long insertId = database.insert(Setup.TABLE_TAX, null,
	        values);
	    Cursor cursor = database.query(Setup.TABLE_TAX,
	        allColumns, Setup.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Tax newTax = cursorToTax(cursor);
	    cursor.close();
	    return newTax;
	  }

	  public void deleteTax(IngredientCategory ingredientCategory) {
	    long id = ingredientCategory.getId();
	    System.out.println("Tax deleted with id: " + id);
	    database.delete(Setup.TABLE_TAX, Setup.COLUMN_ID
	        + " = " + id, null);
	  }

	  public List<Tax> getAllTax() {
	    List<Tax> taxes = new ArrayList<Tax>();

	    Cursor cursor = database.query(Setup.TABLE_TAX,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Tax tax = cursorToTax(cursor);
	      taxes.add(tax);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return taxes;
	  }
	  
	  public Tax getTax(long id) {
		   Tax tax = null;
		   Cursor cursor = database.rawQuery("select * from " + Setup.TABLE_TAX + " where " + Setup.COLUMN_ID + "=" + id  , null);
	        if (cursor != null) 
	        	{
	        		cursor.moveToFirst();
	        		
	      		      tax = cursorToTax(cursor);
	      		      
	      		    // Make sure to close the cursor
	      		    cursor.close();
	        	}
	        return tax;
		  }

	  private Tax cursorToTax(Cursor cursor) {
	    Tax tax = new Tax();
	    tax.setId(cursor.getLong(0));
	    tax.setName(cursor.getString(1));
	    tax.setPercentage(cursor.getDouble(2));
	    return tax;
	  }

}
