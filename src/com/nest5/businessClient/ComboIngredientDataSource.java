package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ComboIngredientDataSource {
	
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = {Setup.COLUMN_ID,Setup.COLUMN_COMBOINGREDIENT_COMBO_ID,Setup.COLUMN_COMBOINGREDIENT_INGREDIENT_ID,Setup.COLUMN_COMBOINGREDIENT_QUANTITY};


	  public ComboIngredientDataSource(MySQLiteHelper _dbHelper) {
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

	  
	  
	  public void saveRelation(long combo, long ingredient,double qty)
	  {
		  ContentValues values = new ContentValues();
		    values.put(Setup.COLUMN_COMBOINGREDIENT_COMBO_ID, combo);
		    values.put(Setup.COLUMN_COMBOINGREDIENT_INGREDIENT_ID, ingredient);
		    values.put(Setup.COLUMN_COMBOINGREDIENT_QUANTITY, qty);
		    long insertId = database.insert(Setup.TABLE_COMBOINGREDIENT, null,
			        values);
		    
	  }
	  
	  public void deleteRelation(long product, long ingredient)
	  {
		  //System.out.println("Product deleted with id: " + id);
		    //database.delete(Setup.TABLE_PRODUCTINGREDIENT, Setup.COLUMN_ID
		       // + " = " + id, null);  
		  
		  //borrar de la tabal donde producto e ingreddiente sean los que llegan
	  }
	  
	  public List<Ingredient> getAllIngredients(long combo)
	  {
		
		  List<Ingredient> ingredients = new ArrayList<Ingredient>();

		    Cursor cursor = database.query(Setup.TABLE_COMBOINGREDIENT,
		        allColumns, Setup.COLUMN_COMBOINGREDIENT_COMBO_ID+" = "+String.valueOf(combo), null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      Ingredient ingredient = cursorToIngredient(cursor);
		      ingredients.add(ingredient);
		      cursor.moveToNext();
		    }
		    // Make sure to close the cursor
		    cursor.close();
		    return ingredients; 
	  }
	  
	  public List<Combo> getAllCombos(long ingredient)
	  {
		
		  List<Combo> combos = new ArrayList<Combo>();

		    Cursor cursor = database.query(Setup.TABLE_COMBOINGREDIENT,
		        allColumns, Setup.COLUMN_COMBOINGREDIENT_INGREDIENT_ID+" = "+String.valueOf(ingredient), null, null, null, null);

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

	  
	  
	  

	  private Ingredient cursorToIngredient(Cursor cursor) {
		    
		    
		    IngredientDataSource ingredientDatasource = new IngredientDataSource(dbHelper);
	    	ingredientDatasource.open();
		    Ingredient ingredient = ingredientDatasource.getIngredient(cursor.getLong(2));

		    	
		    return ingredient;
		  }
	  
	  private Combo cursorToCombo(Cursor cursor) {
		    
		    
		    ComboDataSource comboDatasource = new ComboDataSource(dbHelper);
	    	comboDatasource.open();
		    Combo combo = comboDatasource.getCombo(cursor.getLong(1));

		    	
		    return combo;
		  }

}
