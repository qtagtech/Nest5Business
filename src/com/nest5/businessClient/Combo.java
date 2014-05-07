package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import android.content.Context;


public class Combo {
	@SerializedName("id")
	private long id;
	@SerializedName("name")
	private String name;
	//private ProductCategory category;
	@SerializedName("automaticCost")
	private int automaticCost;
	//private PriceStrategy strategy;
	@SerializedName("cost")
	private double cost;
	@SerializedName("price")
	private double price;
	@SerializedName("tax")
	private Tax tax;
	@SerializedName("products")
	private List<Product> products;
	@SerializedName("ingredients")
	private List<Ingredient> ingredients;
	@SerializedName("syncId")
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
	  
	  /*public ProductCategory getCategory()
	  {
		 return category; 
	  }
	  
	  public void setCategory(ProductCategory category) {
		    this.category = category;
		  }*/
	  
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
	  
	  //Cambiar todo esto por combo_ingredient ademas de poner combo_product
	  public void refreshIngredients(MySQLiteHelper dbHelper)
	  {
		  ComboIngredientDataSource comboIngredientDatasource = new ComboIngredientDataSource(dbHelper);
		  comboIngredientDatasource.open();
		    List<Ingredient> allIngredients = comboIngredientDatasource.getAllIngredients(this.id);
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
		  ComboIngredientDataSource comboIngredientDataSource = new ComboIngredientDataSource(dbHelper);
		  comboIngredientDataSource.open();
		  comboIngredientDataSource.saveRelation(this.id,ingredient.getId(),qty);
	  }
	  
	  public void removeIngredient(MySQLiteHelper dbHelper,Ingredient ingredient)
	  {
		  ComboIngredientDataSource comboIngredientDataSource = new ComboIngredientDataSource(dbHelper);
		  comboIngredientDataSource.open();
		  comboIngredientDataSource.deleteRelation(this.id,ingredient.getId());

	  }
	  
	  public void refreshProducts(MySQLiteHelper dbHelper)
	  {
		  ComboProductDataSource comboProductDataSource = new ComboProductDataSource(dbHelper);
		  comboProductDataSource.open();
		    List<Product> allProducts = comboProductDataSource.getAllProducts(this.id);
		    this.products = allProducts;
	  }
	  
	  public void setProducts(List<Product> products)
	  {
		  this.products = products;
	  }
	  
	  public List<Product> getProducts()
	  {
		  if(this.products != null)
		    return this.products;
		  else
			  return new ArrayList<Product>();
	  }
	  
	  public void addProduct(MySQLiteHelper dbHelper,Product product,double qty) //esta cantidad siempre se normaliza a la unidad de medida estandar, por ejemplo gr o ml
	  {
		  ComboProductDataSource comboProductDataSource = new ComboProductDataSource(dbHelper);
		  comboProductDataSource.open();
		  comboProductDataSource.saveRelation(this.id,product.getId(),qty);
	  }
	  
	  public void removeProduct(MySQLiteHelper dbHelper,Product product)
	  {
		  ComboProductDataSource comboProductDataSource = new ComboProductDataSource(dbHelper);
		  comboProductDataSource.open();
		  comboProductDataSource.deleteRelation(this.id,product.getId());
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
