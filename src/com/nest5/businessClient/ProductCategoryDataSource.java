package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProductCategoryDataSource {
	
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { Setup.COLUMN_ID,
	      Setup.COLUMN_NAME, Setup.COLUMN_OWN_SYNC_ID };

	  public ProductCategoryDataSource(MySQLiteHelper _dbHelper) {
	    dbHelper = _dbHelper;
	  }

	  public SQLiteDatabase open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	    return database;
	  }
	  public void open(SQLiteDatabase db) throws SQLException {
		    database = db;
		    
		  }

	  public void close() {
	    dbHelper.close();
	  }

	  public ProductCategory createProductCategory(String name, long syncId) {
	    ContentValues values = new ContentValues();
	    values.put(Setup.COLUMN_NAME, name);
	    values.put(Setup.COLUMN_OWN_SYNC_ID, syncId);
	    long insertId = database.insert(Setup.TABLE_CATEGORY_PRODUCTS, null,
	        values);
	    Cursor cursor = database.query(Setup.TABLE_CATEGORY_PRODUCTS,
	        allColumns, Setup.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    ProductCategory newProductCategory = cursorToProductCategory(cursor);
	    cursor.close();
	    return newProductCategory;
	  }
	  
	  

	  public void deleteProductCategory(ProductCategory productCategory) {
	    long id = productCategory.getId();
	    System.out.println("ProductCategory deleted with id: " + id);
	    database.delete(Setup.TABLE_CATEGORY_PRODUCTS, Setup.COLUMN_ID
	        + " = " + id, null);
	  }

	  public List<ProductCategory> getAllProductCategory() {
	    List<ProductCategory> productCategories = new ArrayList<ProductCategory>();

	    Cursor cursor = database.query(Setup.TABLE_CATEGORY_PRODUCTS,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      ProductCategory productCategory = cursorToProductCategory(cursor);
	      productCategories.add(productCategory);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return productCategories;
	  }
	  
	  public ProductCategory getProductCategory(long id) {
		   ProductCategory productCategory = null;
		   StringBuilder tables = new StringBuilder();
			  for(int i = 0; i < allColumns.length; i++){
				  if(i != 0)
					  tables.append(",");
				  tables.append(allColumns[i]);
				  
			  }
		   Cursor cursor = database.rawQuery("select "+tables+" from " + Setup.TABLE_CATEGORY_PRODUCTS + " where " + Setup.COLUMN_ID + "=" + id  , null);
	        if (cursor != null) 
	        	{
	        		cursor.moveToFirst();
	        		
	      		      productCategory = cursorToProductCategory(cursor);
	      		      
	      		    // Make sure to close the cursor
	      		    cursor.close();
	        	}
	        return productCategory;
		  }

	  private ProductCategory cursorToProductCategory(Cursor cursor) {
	    ProductCategory productCategory = new ProductCategory();
	    productCategory.setId(cursor.getLong(0));
	    productCategory.setName(cursor.getString(1));
	    productCategory.setSyncId(cursor.getLong(2));
	    return productCategory;
	  }

}
