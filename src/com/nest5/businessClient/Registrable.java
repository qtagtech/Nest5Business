package com.nest5.businessClient;

public class Registrable {
	
	public String name;
	public double price;
	public int type;
	public double tax;
	public long id;
	public Product product;
	public Ingredient ingredient;
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

}
