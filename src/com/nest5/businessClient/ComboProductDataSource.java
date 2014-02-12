package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ComboProductDataSource {
	
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = {Setup.COLUMN_ID,Setup.COLUMN_COMBOPRODUCT_COMBO_ID,Setup.COLUMN_COMBOPRODUCT_PRODUCT_ID,Setup.COLUMN_COMBOPRODUCT_QUANTITY};


	  public ComboProductDataSource(MySQLiteHelper _dbHelper) {
	    dbHelper = _dbHelper;

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

	  
	  
	  public void saveRelation(long combo, long product,double qty)
	  {
		  ContentValues values = new ContentValues();
		    values.put(Setup.COLUMN_COMBOPRODUCT_COMBO_ID, combo);
		    values.put(Setup.COLUMN_COMBOPRODUCT_PRODUCT_ID, product);
		    values.put(Setup.COLUMN_COMBOPRODUCT_QUANTITY, qty);
		    long insertId = database.insert(Setup.TABLE_COMBOPRODUCT, null,
			        values);
		    
	  }
	  
	  public void deleteRelation(long product, long ingredient)
	  {
		  //System.out.println("Product deleted with id: " + id);
		    //database.delete(Setup.TABLE_PRODUCTINGREDIENT, Setup.COLUMN_ID
		       // + " = " + id, null);  
		  
		  //borrar de la tabal donde producto e ingreddiente sean los que llegan
	  }
	  
	  public List<Product> getAllProducts(long combo)
	  {
		
		  List<Product> products = new ArrayList<Product>();

		    Cursor cursor = database.query(Setup.TABLE_COMBOPRODUCT,
		        allColumns, Setup.COLUMN_COMBOPRODUCT_COMBO_ID+" = "+String.valueOf(combo), null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      Product product = cursorToProduct(cursor);
		      products.add(product);
		      cursor.moveToNext();
		    }
		    // Make sure to close the cursor
		    cursor.close();
		    return products; 
	  }
	  
	  public List<Combo> getAllCombos(long product)
	  {
		
		  List<Combo> combos = new ArrayList<Combo>();

		    Cursor cursor = database.query(Setup.TABLE_COMBOPRODUCT,
		        allColumns, Setup.COLUMN_COMBOPRODUCT_PRODUCT_ID+" = "+String.valueOf(product), null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      Combo combo = cursorToCombo(cursor);
		      combos.add(combo);
		      cursor.moveToNext();
		    }
		    // Make sure to close the cursor
		    cursor.close();
		    return combos; 
	  }
	  
	  

	  /*public void deleteCombo(Combo combo) {
	    long id = combo.getId();
	    System.out.println("Combo deleted with id: " + id);
	    database.delete(Setup.TABLE_PRODUCTS, Setup.COLUMN_ID
	        + " = " + id, null);
	  }*/

	  
	  
	  

	  private Product cursorToProduct(Cursor cursor) {
		    
		    
		    ProductDataSource productDatasource = new ProductDataSource(dbHelper);
		    productDatasource.open();
		    Product product = productDatasource.getProduct(cursor.getLong(2));

		    	
		    return product;
		  }
	  
	  private Combo cursorToCombo(Cursor cursor) {
		    
		    
		    ComboDataSource comboDatasource = new ComboDataSource(dbHelper);
	    	comboDatasource.open();
		    Combo combo = comboDatasource.getCombo(cursor.getLong(1));

		    	
		    return combo;
		  }

}
