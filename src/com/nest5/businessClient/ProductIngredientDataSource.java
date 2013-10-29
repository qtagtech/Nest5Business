package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProductIngredientDataSource {
	
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = {Setup.COLUMN_ID,Setup.COLUMN_PRODUCTINGREDIENT_PRODUCT_ID,Setup.COLUMN_PRODUCTINGREDIENT_INGREDIENT_ID,Setup.COLUMN_PRODUCTINGREDIENT_QUANTITY};
	  private Context mContext;

	  public ProductIngredientDataSource(Context context) {
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

	  
	  
	  public void saveRelation(long product, long ingredient,double qty)
	  {
		  ContentValues values = new ContentValues();
		    values.put(Setup.COLUMN_PRODUCTINGREDIENT_PRODUCT_ID, product);
		    values.put(Setup.COLUMN_PRODUCTINGREDIENT_INGREDIENT_ID, ingredient);
		    values.put(Setup.COLUMN_PRODUCTINGREDIENT_QUANTITY, qty);
		    long insertId = database.insert(Setup.TABLE_PRODUCTINGREDIENT, null,
			        values);
		    
	  }
	  
	  public void deleteRelation(long product, long ingredient)
	  {
		  //System.out.println("Product deleted with id: " + id);
		    //database.delete(Setup.TABLE_PRODUCTINGREDIENT, Setup.COLUMN_ID
		       // + " = " + id, null);  
		  
		  //borrar de la tabal donde producto e ingreddiente sean los que llegan
	  }
	  
	  public List<Ingredient> getAllIngredients(long product)
	  {
		
		  List<Ingredient> ingredients = new ArrayList<Ingredient>();

		    Cursor cursor = database.query(Setup.TABLE_PRODUCTINGREDIENT,
		        allColumns, Setup.COLUMN_PRODUCTINGREDIENT_PRODUCT_ID+" = "+String.valueOf(product), null, null, null, null);

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
	  
	  public List<Product> getAllProducts(long ingredient)
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
	  }
	  
	  

	  public void deleteProduct(Product product) {
	    long id = product.getId();
	    System.out.println("Product deleted with id: " + id);
	    database.delete(Setup.TABLE_PRODUCTS, Setup.COLUMN_ID
	        + " = " + id, null);
	  }

	  
	  
	  

	  private Ingredient cursorToIngredient(Cursor cursor) {
		    
		    
		    IngredientDataSource ingredientDatasource = new IngredientDataSource(mContext);
	    	ingredientDatasource.open();
		    Ingredient ingredient = ingredientDatasource.getIngredient(cursor.getLong(2));
		    ingredientDatasource.close();
		    	
		    return ingredient;
		  }
	  
	  private Product cursorToProduct(Cursor cursor) {
		    
		    
		    ProductDataSource productDatasource = new ProductDataSource(mContext);
	    	productDatasource.open();
		    Product product = productDatasource.getProduct(cursor.getLong(1));
		    productDatasource.close();
		    	
		    return product;
		  }

}
