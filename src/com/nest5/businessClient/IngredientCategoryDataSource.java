package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.List;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class IngredientCategoryDataSource {
	
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { Setup.COLUMN_ID,
	      Setup.COLUMN_NAME, Setup.COLUMN_OWN_SYNC_ID };

	  public IngredientCategoryDataSource(MySQLiteHelper _dbHelper) {
	    dbHelper = _dbHelper;
	  }

	  public SQLiteDatabase open() throws SQLException {
		
	    database = dbHelper.getWritableDatabase();
	    return database;
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public IngredientCategory createIngredientCategory(String name, long syncId) {
	    ContentValues values = new ContentValues();
	    values.put(Setup.COLUMN_NAME, name);
	    values.put(Setup.COLUMN_OWN_SYNC_ID, syncId);
	    long insertId = database.insert(Setup.TABLE_CATEGORY_INGREDIENTS, null,
	        values);
	    Cursor cursor = database.query(Setup.TABLE_CATEGORY_INGREDIENTS,
	        allColumns, Setup.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    IngredientCategory newIngredientCategory = cursorToIngredientCategory(cursor);
	    cursor.close();
	    return newIngredientCategory;
	  }
	  

	  public void deleteIngredientCategory(IngredientCategory ingredientCategory) {
	    long id = ingredientCategory.getId();
	    System.out.println("IngredientCategory deleted with id: " + id);
	    database.delete(Setup.TABLE_CATEGORY_INGREDIENTS, Setup.COLUMN_ID
	        + " = " + id, null);
	  }

	  public List<IngredientCategory> getAllIngredientCategory() {
		    List<IngredientCategory> ingredientCategories = new ArrayList<IngredientCategory>();
		    Cursor cursor = database.query(Setup.TABLE_CATEGORY_INGREDIENTS,
		        allColumns, null, null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      IngredientCategory ingredientCategory = cursorToIngredientCategory(cursor);
		      ingredientCategories.add(ingredientCategory);
		      cursor.moveToNext();
		    }
		    // Make sure to close the cursor
		    cursor.close();
		    return ingredientCategories;
		  }
	  public List<IngredientCategory> getAllIngredientCategory(SQLiteDatabase database) {
	    List<IngredientCategory> ingredientCategories = new ArrayList<IngredientCategory>();
	    this.database = database;
	    Cursor cursor = database.query(Setup.TABLE_CATEGORY_INGREDIENTS,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      IngredientCategory ingredientCategory = cursorToIngredientCategory(cursor);
	      ingredientCategories.add(ingredientCategory);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return ingredientCategories;
	  }
	  
	  public IngredientCategory getIngredientCategory(long id) {
		   IngredientCategory ingredientCategory = null;
		   StringBuilder tables = new StringBuilder();
			  for(int i = 0; i < allColumns.length; i++){
				  if(i != 0)
					  tables.append(",");
				  tables.append(allColumns[i]);
				  
			  }
		   Cursor cursor = database.rawQuery("select "+tables+" from " + Setup.TABLE_CATEGORY_INGREDIENTS + " where " + Setup.COLUMN_ID + "=" + id  , null);
	        if (cursor != null) 
	        	{
	        		Log.e("ELERROR",String.valueOf(cursor.getColumnCount()));
	        		cursor.moveToFirst();
	        		
	      		      ingredientCategory = cursorToIngredientCategory(cursor);
	      		      
	      		    // Make sure to close the cursor
	      		    cursor.close();
	        	}
	        return ingredientCategory;
		  }

	  private IngredientCategory cursorToIngredientCategory(Cursor cursor) {
		  IngredientCategory ingredientCategory = new IngredientCategory();
		  try{
			  
			    ingredientCategory.setId(cursor.getInt(0));
			    ingredientCategory.setName(cursor.getString(1));
			    ingredientCategory.setSyncId(cursor.getLong(2));
			     
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  return ingredientCategory;
	  }

}
