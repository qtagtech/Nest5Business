package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class IngredientDataSource {
	
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = {Setup.COLUMN_ID,Setup.COLUMN_NAME,Setup.COLUMN_INGREDIENT_CATEGORY_ID,Setup.COLUMN_INGREDIENT_TAX_ID,Setup.COLUMN_INGREDIENT_UNIT,Setup.COLUMN_INGREDIENT_COST_PER_UNIT,Setup.COLUMN_INGREDIENT_PRICE_PER_UNIT,Setup.COLUMN_INGREDIENT_PRICE_MEASURE,Setup.COLUMN_INGREDIENT_QTY,Setup.COLUMN_INGREDIENT_DATE,Setup.COLUMN_OWN_SYNC_ID};


	  public IngredientDataSource(MySQLiteHelper _dbHelper) {
	    dbHelper = _dbHelper;

	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }
	  
	  public void open(SQLiteDatabase _database)
	  {
		  database = _database;
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public Ingredient createIngredient(String name,IngredientCategory category,Tax tax,Unit unit,double costPerUnit, double pricePerUnit,double priceMeasure,double qty,long date,long syncId) {
	    ContentValues values = new ContentValues();
	    values.put(Setup.COLUMN_NAME, name.trim().toLowerCase());
	    values.put(Setup.COLUMN_INGREDIENT_CATEGORY_ID, category.getId());
	    values.put(Setup.COLUMN_INGREDIENT_TAX_ID, tax.getId());
	    values.put(Setup.COLUMN_INGREDIENT_UNIT, unit.getId());
	    values.put(Setup.COLUMN_INGREDIENT_COST_PER_UNIT,costPerUnit);
	    values.put(Setup.COLUMN_INGREDIENT_PRICE_PER_UNIT,pricePerUnit);
	    values.put(Setup.COLUMN_INGREDIENT_PRICE_MEASURE,priceMeasure);
	    values.put(Setup.COLUMN_INGREDIENT_QTY,qty);
	    values.put(Setup.COLUMN_INGREDIENT_DATE,date);
	    values.put(Setup.COLUMN_OWN_SYNC_ID, syncId);
	    
	    long insertId = database.insert(Setup.TABLE_INGREDIENTS, null,
	        values);
	    Cursor cursor = database.query(Setup.TABLE_INGREDIENTS,
	        allColumns, Setup.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Ingredient newIngredient = cursorToIngredient(cursor);
	    cursor.close();
	    return newIngredient;
	  }
	  
	  public Ingredient updateIngredient(long id,String name,IngredientCategory category,Tax tax,Unit unit,double costPerUnit, double pricePerUnit,double priceMeasure,double qty/*,long date*/)
	  {
		  IngredientDataSource ids = new IngredientDataSource(dbHelper);
		  Ingredient oldIng =  ids.getIngredient(id);
		  long syncId = oldIng.getSyncId();
		  ContentValues values = new ContentValues();
		    values.put(Setup.COLUMN_NAME, name);
		    values.put(Setup.COLUMN_INGREDIENT_CATEGORY_ID, category.getId());
		    values.put(Setup.COLUMN_INGREDIENT_TAX_ID, tax.getId());
		    values.put(Setup.COLUMN_INGREDIENT_UNIT, unit.getId());
		    values.put(Setup.COLUMN_INGREDIENT_COST_PER_UNIT,costPerUnit);
		    values.put(Setup.COLUMN_INGREDIENT_PRICE_PER_UNIT,pricePerUnit);
		    values.put(Setup.COLUMN_INGREDIENT_PRICE_MEASURE,priceMeasure);
		    values.put(Setup.COLUMN_INGREDIENT_QTY,qty);
		    values.put(Setup.COLUMN_OWN_SYNC_ID, syncId);
		    //values.put(Setup.COLUMN_INGREDIENT_DATE,date);
		    database.update(Setup.TABLE_INGREDIENTS, values, Setup.COLUMN_ID + " = " + id, null);
		    Cursor cursor = database.query(Setup.TABLE_INGREDIENTS,
			        allColumns, Setup.COLUMN_ID + " = " + id, null,
			        null, null, null);
			    cursor.moveToFirst();
			    Ingredient newIngredient = cursorToIngredient(cursor);
			    cursor.close();
			    return newIngredient;
	  }

	  public void deleteIngredient(Ingredient ingredient) {
	    long id = ingredient.getId();
	    System.out.println("Ingredient deleted with id: " + id);
	    database.delete(Setup.TABLE_INGREDIENTS, Setup.COLUMN_ID
	        + " = " + id, null);
	  }
	  
	  public Ingredient getIngredient(long id) {
		  Ingredient ingredient = null;
		  StringBuilder tables = new StringBuilder();
		  for(int i = 0; i < allColumns.length; i++){
			  if(i != 0)
				  tables.append(",");
			  tables.append(allColumns[i]);
			  
		  }
		   Cursor cursor = database.rawQuery("select "+tables+" from " + Setup.TABLE_INGREDIENTS + " where " + Setup.COLUMN_ID + "=" + id  , null);
	        if (cursor != null) 
	        	{
	        		cursor.moveToFirst();
	        		
	      		      ingredient = cursorToIngredient(cursor);
	      		      
	      		    // Make sure to close the cursor
	      		    cursor.close();
	        	}
	        return ingredient;
		  }

	  public List<Ingredient> getAllIngredient() {
	    List<Ingredient> ingredients = new ArrayList<Ingredient>();

	    Cursor cursor = database.query(Setup.TABLE_INGREDIENTS,
	        allColumns, null, null, null, null, Setup.COLUMN_NAME);

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
	  
	  public List<Ingredient> getAIngredientByName(String name) {
		  	name = name.toLowerCase().trim();
		    List<Ingredient> ingredients = new ArrayList<Ingredient>();
		    
		    Cursor cursor = database.query(Setup.TABLE_INGREDIENTS,
		        allColumns, Setup.COLUMN_NAME + " = ?", new String[] {name}, null, null, Setup.COLUMN_INGREDIENT_DATE);

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
	  
	  

	  private Ingredient cursorToIngredient(Cursor cursor) {
	    Ingredient ingredient = new Ingredient();
	    ingredient.setId(cursor.getLong(0));
	    ingredient.setName(cursor.getString(1));
	    //aqui en vez de poner en el objeto un id, poner un objeto de tipo categoryIngredient
	    //ingredient.setCategoryId(cursor.getLong(2));
	    IngredientCategoryDataSource ingredientCategoryDatasource = new IngredientCategoryDataSource(dbHelper);
    	ingredientCategoryDatasource.open();
	    IngredientCategory ingredientCategory = ingredientCategoryDatasource.getIngredientCategory(cursor.getLong(2));
	    ingredient.setCategory(ingredientCategory);
	    TaxDataSource taxDatasource = new TaxDataSource(dbHelper);
    	taxDatasource.open();
	    Tax tax = taxDatasource.getTax(cursor.getLong(3));
	    ingredient.setTax(tax);
	    UnitDataSource unitDatasource = new UnitDataSource(dbHelper);
    	unitDatasource.open();
	    Unit unit = unitDatasource.getUnit(cursor.getLong(4));
	    ingredient.setUnit(unit);
	    ingredient.setCostPerUnit(cursor.getDouble(5));
	    ingredient.setPricePerUnit(cursor.getDouble(6));
	    ingredient.setPriceMeasure(cursor.getDouble(7));
	    ingredient.setQty(cursor.getDouble(8));
	    ingredient.setDate(cursor.getLong(9));
	    ingredient.setSyncId(cursor.getLong(10));
	    return ingredient;
	    
	  }

}
