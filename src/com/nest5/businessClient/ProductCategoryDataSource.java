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
	      Setup.COLUMN_NAME };

	  public ProductCategoryDataSource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
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

	  public ProductCategory createProductCategory(String name) {
	    ContentValues values = new ContentValues();
	    values.put(Setup.COLUMN_NAME, name);
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
		   Cursor cursor = database.rawQuery("select * from " + Setup.TABLE_CATEGORY_PRODUCTS + " where " + Setup.COLUMN_ID + "=" + id  , null);
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
	    return productCategory;
	  }

}
