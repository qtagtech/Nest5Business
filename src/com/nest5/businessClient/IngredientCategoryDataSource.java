package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class IngredientCategoryDataSource {
	
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { Setup.COLUMN_ID,
	      Setup.COLUMN_NAME };

	  public IngredientCategoryDataSource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	  }

	  public SQLiteDatabase open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	    return database;
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public IngredientCategory createIngredientCategory(String name) {
	    ContentValues values = new ContentValues();
	    values.put(Setup.COLUMN_NAME, name);
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
	  
	  public IngredientCategory getIngredientCategory(long id) {
		   IngredientCategory ingredientCategory = null;
		   Cursor cursor = database.rawQuery("select * from " + Setup.TABLE_CATEGORY_INGREDIENTS + " where " + Setup.COLUMN_ID + "=" + id  , null);
	        if (cursor != null) 
	        	{
	        		cursor.moveToFirst();
	        		
	      		      ingredientCategory = cursorToIngredientCategory(cursor);
	      		      
	      		    // Make sure to close the cursor
	      		    cursor.close();
	        	}
	        return ingredientCategory;
		  }

	  private IngredientCategory cursorToIngredientCategory(Cursor cursor) {
	    IngredientCategory ingredientCategory = new IngredientCategory();
	    ingredientCategory.setId(cursor.getLong(0));
	    ingredientCategory.setName(cursor.getString(1));
	    return ingredientCategory;
	  }

}
