package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.R.bool;
import android.content.Context;


public class Sale {
	//Para guardar una Sale, hace en el datasource createsale y guarda las propiedades basicas, luego al sale le hace saveItems en donde se coge cada registrable y se guarda
	//en la base de datos como un  SaleItem.
	
	//Al momento de traer un Sale, se hace en el conversor de cursor a Sale, q recorra cada objeto, si es tipo producto, trae el producto, y lo mete en un hashmap con la cantidad y hace setProducts
	//asi para los ingredientes y los combos.
	private long id;
	private long date;
	private String payment_method;
	//crear un nuevo campo que sea para tipo de pago cogiendo objetos de tipo de pago de base de datos
	private double value_received;
	private LinkedHashMap<Product,Double> products;
	private LinkedHashMap<Ingredient,Double> ingredients;
	private LinkedHashMap<Combo,Double> combos;
	//private LinkedHashMap<Registrable, Double> items;
	//private SalesMan salesMan;

	//si le da al boton guardar crea un objeto en memoria con los registrables y las cantidades de cada uno, en base de datos solo guarda cosas finalizadas y pagadas.

	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	  public long getDate() {
	    return date;
	  }

	  public void setDate(long date) 
	  {
	    this.date = date;
	  }
	  
	  public String getPaymentMethod()
	  {
		 return payment_method; 
	  }
	  
	  public void setValue(Double value)
	  {
		  this.value_received = value; 
	  }
	  
	  public Double getValue()
	  {
		  return this.value_received; 
	  }
	  
	  public void setPaymentMethod(String pay_meth) 
	  {
		    this.payment_method = pay_meth;
	  }
	  
	  public void saveItem(Context mContext,int type, long item_id, double qty) //se hace para cada registrable en initialactvity currentOrde. El type lo coge de Registrable.
	  {
		  //guarda cada registrable como un SaleItem
		  
		  SaleItemDataSource saleItemDataSource = new SaleItemDataSource(mContext);
		  saleItemDataSource.open();
		  saleItemDataSource.saveRelation(this.id,type,item_id,qty);
		  saleItemDataSource.close();
	  }
	  
	  
	  
	  public void setCombos(LinkedHashMap<Combo, Double> combos)
	  {
		  this.combos = combos;
	  }
	  
	  
	  public LinkedHashMap<Combo, Double> getCombos()
	  {
		 
		     return this.combos;
		  
	  }
	  
	  
	  public void setProducts(LinkedHashMap<Product, Double> products)
	  {
		  this.products = products;
	  }
	  
	  
	  public LinkedHashMap<Product, Double> getProducts()
	  {
		 
		     return this.products;
		  
	  }
	  
	  public void setIngredients(LinkedHashMap<Ingredient, Double> ingredients)
	  {
		  this.ingredients = ingredients;
	  }
	  
	  public LinkedHashMap<Ingredient, Double> getIngredients()
	  {
		 
		     return ingredients;
		     
		  
	  }
	  
	  /*private void setCombos()
	  {
	  combos = new LinkedHashMap<Combo, Double>();
		  Iterator<Entry<Registrable, Double>> it = this.items.entrySet().iterator();
		    
		    
		     while(it.hasNext())
		     {
		    	 Map.Entry<Registrable,Double> registrablePair = (Map.Entry<Registrable,Double>)it.next();
		    	 if(registrablePair.getKey().type == Registrable.TYPE_COMBO)
		    	 {
		    		 combos.put(registrablePair.getKey().combo, registrablePair.getValue());
		    	 }
		    	
		     }
		     
		     //guardar todas las relaciones en la base de datos
		  
	  }*/
	  
	  
	  
	 
	
		  
	

	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return String.valueOf(date);
	  }

}
