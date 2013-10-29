package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SaleItemDataSource {
	
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = {Setup.COLUMN_ID,Setup.COLUMN_SALE_ID,Setup.COLUMN_SALE_ITEM_TYPE,Setup.COLUMN_SALE_ITEM_ID,Setup.COLUMN_SALE_ITEM_QTY};
	  private Context mContext;

	  public SaleItemDataSource(Context context) {
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

	  
	  
	  public void saveRelation(long sale_id, int item_type,long item_id,double item_qty)
	  {
		  ContentValues values = new ContentValues();
		    values.put(Setup.COLUMN_SALE_ID, sale_id);
		    values.put(Setup.COLUMN_SALE_ITEM_TYPE, item_type);
		    values.put(Setup.COLUMN_SALE_ITEM_ID, item_id);
		    values.put(Setup.COLUMN_SALE_ITEM_QTY, item_qty);
		    long insertId = database.insert(Setup.TABLE_SALE_ITEM, null,
			        values);
		    
	  }
	  
	  public void deleteRelation(long sale_id,String item_type, long item_id)
	  {
		  //System.out.println("Product deleted with id: " + id);
		    //database.delete(Setup.TABLE_PRODUCTINGREDIENT, Setup.COLUMN_ID
		       // + " = " + id, null);  
		  
		  //borrar de la tabal donde producto e ingreddiente sean los que llegan
	  }
	  
	  public LinkedHashMap<Registrable,Double> getAllItems(long sale)
	  {
		
		  LinkedHashMap<Registrable,Double> items = new LinkedHashMap<Registrable,Double>();

		    Cursor cursor = database.query(Setup.TABLE_SALE_ITEM,
		        allColumns, Setup.COLUMN_SALE_ID+" = "+String.valueOf(sale), null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      Registrable item = cursorToItem(cursor);
		      Double qty = cursor.getDouble(4);
		      items.put(item,qty);
		      cursor.moveToNext();
		    }
		    // Make sure to close the cursor
		    cursor.close();
		    return items; 
	  }
	  
	 /* public List<Product> getAllProducts(long ingredient)
	  {
		
		  List<Product> products = new ArrayList<Product>();

		    Cursor cursor = database.query(Setup.TABLE_PRODUCTINGREDIENT,
		        allColumns, Setup.COLUMN_PRODUCTINGREDIENT_INGREDIENT_ID+" = "+String.valueOf(ingredient), null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      Product product = cursorToProduct(cursor);
		      products.add(product);
		      cursor.moveToNext();
		    }
		    // Make sure to close the cursor
		    cursor.close();
		    return products; 
	  }*/
	  
	  

	/*  public void deleteProduct(Product product) {
	    long id = product.getId();
	    System.out.println("Product deleted with id: " + id);
	    database.delete(Setup.TABLE_PRODUCTS, Setup.COLUMN_ID
	        + " = " + id, null);
	  }*/

	  
	  
	  

	  private Registrable cursorToItem(Cursor cursor) {
		    
		  Registrable item = new Registrable(cursor.getInt(2),cursor.getLong(3));
		  return item;
		    
		    
		  }
	  
	  

}
