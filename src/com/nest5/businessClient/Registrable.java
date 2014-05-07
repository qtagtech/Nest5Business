package com.nest5.businessClient;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.SerializedName;

public class Registrable {
	
	@SerializedName("name")
	public String name;
	@SerializedName("price")
	public double price;
	@SerializedName("type")
	public int type;
	@SerializedName("tax")
	public double tax;
	@SerializedName("id")
	public long id;
	@SerializedName("product")
	public Product product;
	@SerializedName("ingredient")
	public Ingredient ingredient;
	@SerializedName("combo")
	public Combo combo;
	
	public final static int TYPE_INGREDIENT = 0;
	public final static int TYPE_PRODUCT = 1;
	public final static int TYPE_COMBO = 3;
	public final static int TYPE_CUSTOM = 2;
	
	public Registrable(String _name,double _price,double _tax){
		this.name = _name;
		this.price = _price;
		this.type = TYPE_CUSTOM;
		this.tax = _tax;
		this.id = 0;
	}
	
	public Registrable(int type, long id) //se usa cuando desde sale se traen los saleItems y se crea este objeto para hallar el producto, ingrediente o combo correspondiente
	{
		this.id = id;
		this.type = type;
	}
	
	public Registrable(Combo combo){
		this.name = combo.getName();
		this.price = combo.getPrice();
		this.type = TYPE_COMBO;
		this.tax = combo.getTax().getPercentage();
		this.id = combo.getId();
		this.combo = combo;
	}
	
	public Registrable(Product product){
		this.name = product.getName();
		this.price = product.getPrice();
		this.type = TYPE_PRODUCT;
		this.tax = product.getTax().getPercentage();
		this.id = product.getId();
		this.product = product;
	}
	
	public Registrable(Ingredient ingredient){
		this.name = ingredient.getName();
		this.price = ingredient.getPrice();
		this.type = TYPE_INGREDIENT;
		this.tax = ingredient.getTax().getPercentage();
		this.id = ingredient.getId();
		this.ingredient = ingredient;
	}
	
	//id, name, tax, product, ingredient, combo, price, type
	
	public void setId(long _id){
		this.id = _id;
	}
	public long getId(){
		return this.id;
	}
	public void setName(String _name){
		this.name = _name;
	}
	public String getName(){
		return this.name;
	}
	public void setPrice(double _price){
		this.price = _price;
	}
	public double getPrice(){
		return this.price;
	}
	public void setTax(double _tax){
		this.tax = _tax;
	}
	public double getTax(){
		return this.tax;
	}
	public void setProduct(Product _product){
		this.product = _product;
	}
	public Product getProduct(){
		return this.product;
	}
	public void setIngredient(Ingredient _ingredient){
		this.ingredient = _ingredient;
	}
	public Ingredient getIngredient(){
		return this.ingredient;
	}
	public void setCombo(Combo _combo){
		this.combo = _combo;
	}
	public Combo getCombo(){
		return this.combo;
	}
	public void setType(int _type){
		this.type = _type;
	}
	public int getType(){
		return this.type;
	}

	
}

