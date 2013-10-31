package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.R.bool;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SaleDataSource {
	
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = {Setup.COLUMN_ID,Setup.COLUMN_SALE_DATE,Setup.COLUMN_SALE_METHOD,Setup.COLUMN_SALE_RECEIVED};
	  private Context mContext;

	  public SaleDataSource(Context context) {
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

	  public Sale createSale(long date,String method,double received) { //se llama desde initialActivity a guardar
	    ContentValues values = new ContentValues();
	    values.put(Setup.COLUMN_SALE_DATE, date);
	    values.put(Setup.COLUMN_SALE_METHOD, method);
	    values.put(Setup.COLUMN_SALE_RECEIVED, received);
	    
	    
	    long insertId = database.insert(Setup.TABLE_SALE, null,
	        values);
	    Cursor cursor = database.query(Setup.TABLE_SALE,
	        allColumns, Setup.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Sale newSale = cursorToSale(cursor);
	    cursor.close();
	    return newSale;
	  }

	  public void deleteSale(Sale sale) {
	    long id = sale.getId();
	    System.out.println("Sale deleted with id: " + id);
	    database.delete(Setup.TABLE_SALE, Setup.COLUMN_ID
	        + " = " + id, null);
	  }

	  public List<Sale> getAllSales() {
	    List<Sale> sales = new ArrayList<Sale>();

	    Cursor cursor = database.query(Setup.TABLE_SALE,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Sale sale = cursorToSale(cursor);
	      sales.add(sale);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return sales;
	  }
	  
	  
	  public List<Sale> getAllSalesWithin(Long init, Long end) {
		    List<Sale> sales = new ArrayList<Sale>();
		    
		    
		    Cursor cursor = database.query(Setup.TABLE_SALE,allColumns, Setup.COLUMN_SALE_DATE + " BETWEEN ? AND ?", new String[] {
	                String.valueOf(init), String.valueOf(end) }, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      Sale sale = cursorToSale(cursor);
		      sales.add(sale);
		      cursor.moveToNext();
		    }
		    // Make sure to close the cursor
		    cursor.close();
		    return sales;
		  }
	  
	  public Sale getSale(long id) {
		  Sale sale = null;
		   Cursor cursor = database.rawQuery("select * from " + Setup.TABLE_SALE + " where " + Setup.COLUMN_ID + "=" + id  , null);
	        if (cursor != null) 
	        	{
	        		cursor.moveToFirst();
	        		
	      		      sale = cursorToSale(cursor);
	      		      
	      		    // Make sure to close the cursor
	      		    cursor.close();
	        	}
	        return sale;
		  }
	  
	  

	  private Sale cursorToSale(Cursor cursor) {
	    Sale sale = new Sale();
	    sale.setId(cursor.getLong(0));
	    sale.setDate(cursor.getLong(1));
	    sale.setPaymentMethod(cursor.getString(2));
	    sale.setValue(cursor.getDouble(3));
	    
	    //toma el data source de SaleItem y trae todos los que haya con sale_id de este
	    //crea 3 hashmap, para ingredientes, productos y combos.
	    //recorre los salItem, toma el type, y los compara con Registrable.TYPE_...
	    //verifica si es ingrediente entonces busca en base de datos el ingrediente con ese id
	    //lo agrega al hashmap creado con la cantidad actual y al final hace sale.setIngredients(HashMap)
	    //lo mismo para products y combos.
	    LinkedHashMap<Ingredient, Double> ingredients = new LinkedHashMap<Ingredient, Double>();
	    LinkedHashMap<Product, Double> products = new LinkedHashMap<Product, Double>();
	    LinkedHashMap<Combo, Double> combos = new LinkedHashMap<Combo, Double>();
	    LinkedHashMap<Registrable, Double> items;
	    //LinkedHashMap<Combo, Double> cobos;
	    SaleItemDataSource saleItemDataSource = new SaleItemDataSource(mContext);
	    SQLiteDatabase db = saleItemDataSource.open();
	    items = saleItemDataSource.getAllItems(cursor.getLong(0));
	    
	    Iterator<Entry<Registrable, Double>> it = items.entrySet().iterator();
	    IngredientDataSource ingredientDataSource = new IngredientDataSource(mContext);
	    ProductDataSource productDataSource = new ProductDataSource(mContext);
	    ComboDataSource comboDataSource = new ComboDataSource(mContext);
	    
	    ingredientDataSource.open(db);
	    productDataSource.open(db);
	    comboDataSource.open(db);
	    
	     while(it.hasNext())
	     {
	    	 Map.Entry<Registrable,Double> registrablePair = (Map.Entry<Registrable,Double>)it.next();
	    	 if(registrablePair.getKey().type == Registrable.TYPE_INGREDIENT)
	    	 {
	    		 Ingredient ing = ingredientDataSource.getIngredient(registrablePair.getKey().id);
	    		 ingredients.put(ing, registrablePair.getValue());
	    	 }
	    	 else if(registrablePair.getKey().type == Registrable.TYPE_PRODUCT)
	    	 {
	    		 Product prod = productDataSource.getProduct(registrablePair.getKey().id);
	    		 products.put(prod, registrablePair.getValue()); 
	    	 }
	    	 else // es un combo
	    	 {
	    		 Combo combo = comboDataSource.getCombo(registrablePair.getKey().id);
	    		 combos.put(combo, registrablePair.getValue());
	    		//se agrega el combo 
	    	 }
	    	 //product.addIngredient(mContext,ingredientPair.getKey(),ingredientPair.getValue());
	    	 
	    	 //Log.d("INGREDIENTES","INGREDIENTE: "+ingrediente.getKey().getName()+" "+ingrediente.getValue());	
	     }
	     saleItemDataSource.close();
	     ingredientDataSource.close();
	     productDataSource.close();
	     sale.setIngredients(ingredients);
	     sale.setProducts(products);
	     sale.setCombos(combos);
	    		
	    return sale;
	  }

}
