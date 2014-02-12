package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;


public class Product {
	
	private long id;
	private String name;
	private ProductCategory category;
	private int automaticCost;
	private double cost;
	private double price;
	private Tax tax;
	private List<Ingredient> ingredients;
	private long syncId;

	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	  public String getName() {
	    return name;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }
	  
	  public ProductCategory getCategory()
	  {
		 return category; 
	  }
	  
	  public void setCategory(ProductCategory category) {
		    this.category = category;
		  }
	  
	  public Tax getTax()
	  {
		 return tax; 
	  }
	  
	  public void setTax(Tax tax) {
		    this.tax = tax;
		  }
	  
	  public int isAutomaticCost() {
		    return automaticCost;
		  }

	  public void setAutomaticCost(int value) {
		    this.automaticCost = value;
		  }
		  
	  public Double getCost() {
		    return cost;
		  }

	  public void setCost(Double cost) {
		    this.cost = cost;
		  }
		  
	  public Double getPrice() {
			    return price;
			  }

	  public void setPrice(Double price) {
			    this.price = price;
			  }
	  
	  
	  public void refreshIngredients(MySQLiteHelper dbHelper)
	  {
		  ProductIngredientDataSource productIngredientDatasource = new ProductIngredientDataSource(dbHelper);
		  productIngredientDatasource.open();
		    List<Ingredient> allIngredients = productIngredientDatasource.getAllIngredients(this.id);
		    this.ingredients = allIngredients;
	  }
	  
	  public void setIngredients(List<Ingredient> ingredients)
	  {
		  this.ingredients = ingredients;
	  }
	  
	  public List<Ingredient> getIngredients()
	  {
		  if(this.ingredients != null)
		    return this.ingredients;
		  else
			  return new ArrayList<Ingredient>();
	  }
	  
	  public void addIngredient(MySQLiteHelper dbHelper,Ingredient ingredient,double qty) //esta cantidad siempre se normaliza a la unidad de medida estandar, por ejemplo gr o ml
	  {
		  ProductIngredientDataSource productIngredientDatasource = new ProductIngredientDataSource(dbHelper);
		  productIngredientDatasource.open();
		  productIngredientDatasource.saveRelation(this.id,ingredient.getId(),qty);
	  }
	  
	  public void removeIngredient(MySQLiteHelper dbHelper,Ingredient ingredient)
	  {
		  ProductIngredientDataSource productIngredientDatasource = new ProductIngredientDataSource(dbHelper);
		  productIngredientDatasource.open();
		  productIngredientDatasource.deleteRelation(this.id,ingredient.getId());

	  }
	  
	  public Long getSyncId(){
		  return this.syncId;
	  }
	  public void setSyncId(Long syncId){
		  this.syncId = syncId;
	  }
	
		  
	

	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return name;
	  }

}
