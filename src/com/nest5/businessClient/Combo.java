package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;


public class Combo {
	
	private long id;
	private String name;
	//private ProductCategory category;
	private int automaticCost;
	//private PriceStrategy strategy;
	private double cost;
	private double price;
	private Tax tax;
	private List<Product> products;
	private List<Ingredient> ingredients;

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
	  public void refreshIngredients(Context mContext)
	  {
		  ComboIngredientDataSource comboIngredientDatasource = new ComboIngredientDataSource(mContext);
		  comboIngredientDatasource.open();
		    List<Ingredient> allIngredients = comboIngredientDatasource.getAllIngredients(this.id);
		    comboIngredientDatasource.close();
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
	  
	  public void addIngredient(Context mContext,Ingredient ingredient,double qty) //esta cantidad siempre se normaliza a la unidad de medida estandar, por ejemplo gr o ml
	  {
		  ComboIngredientDataSource comboIngredientDataSource = new ComboIngredientDataSource(mContext);
		  comboIngredientDataSource.open();
		  comboIngredientDataSource.saveRelation(this.id,ingredient.getId(),qty);
		  comboIngredientDataSource.close(); 
	  }
	  
	  public void removeIngredient(Context mContext,Ingredient ingredient)
	  {
		  ComboIngredientDataSource comboIngredientDataSource = new ComboIngredientDataSource(mContext);
		  comboIngredientDataSource.open();
		  comboIngredientDataSource.deleteRelation(this.id,ingredient.getId());
		  comboIngredientDataSource.close(); 
	  }
	  
	  public void refreshProducts(Context mContext)
	  {
		  ComboProductDataSource comboProductDataSource = new ComboProductDataSource(mContext);
		  comboProductDataSource.open();
		    List<Product> allProducts = comboProductDataSource.getAllProducts(this.id);
		    comboProductDataSource.close();
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
	  
	  public void addProduct(Context mContext,Product product,double qty) //esta cantidad siempre se normaliza a la unidad de medida estandar, por ejemplo gr o ml
	  {
		  ComboProductDataSource comboProductDataSource = new ComboProductDataSource(mContext);
		  comboProductDataSource.open();
		  comboProductDataSource.saveRelation(this.id,product.getId(),qty);
		  comboProductDataSource.close(); 
	  }
	  
	  public void removeProduct(Context mContext,Product product)
	  {
		  ComboProductDataSource comboProductDataSource = new ComboProductDataSource(mContext);
		  comboProductDataSource.open();
		  comboProductDataSource.deleteRelation(this.id,product.getId());
		  comboProductDataSource.close(); 
	  }
	
		  
	

	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return name;
	  }

}
