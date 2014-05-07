package com.nest5.businessClient;

import com.google.gson.annotations.SerializedName;

public class Ingredient {
	
	@SerializedName("id")
	private long id;
	@SerializedName("name")
	private String name;
	@SerializedName("category")
	private IngredientCategory category;
	@SerializedName("tax")
	private Tax tax;
	@SerializedName("unit")
	private Unit unit;
	@SerializedName("qty")
	private double qty;
	@SerializedName("costPerUnit")
	private double costPerUnit;
	@SerializedName("pricePerUnit")
	private double pricePerUnit;
	@SerializedName("priceMeasure")
	private double priceMeasure;
	@SerializedName("date")
	private long date;
	@SerializedName("syncId")
	private long syncId;

	  public long getId() {
	    return id;
	  }
	  public long getDate() {
		    return date;
		  }
	  public void setDate(long date) {
		    this.date = date;
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
	  
	  public IngredientCategory getCategory()
	  {
		 return category; 
	  }
	  
	  public void setCategory(IngredientCategory category) {
		    this.category = category;
		  }
	  
	  public Tax getTax()
	  {
		 return tax; 
	  }
	  
	  public void setTax(Tax tax) {
		    this.tax = tax;
		  }
	  
		  
	  public Double getCost(double unitQty) {
		    return costPerUnit * unitQty;
		  }

	
		  
	  public Double getPrice() {
			    return pricePerUnit * priceMeasure;
			  }
	  
	  public Double getPrice(double unitQty)
	  {
		  return pricePerUnit * unitQty;
	  }


	  
	  public Double getCostPerUnit() {
		    return costPerUnit;
		  }

	  public void setCostPerUnit(Double costPerUnit) {
		    this.costPerUnit = costPerUnit;
		  }
	  
	  public Double getPricePerUnit() {
		    return pricePerUnit;
		  }

	  public void setPricePerUnit(Double pricePerUnit) {
		    this.pricePerUnit = pricePerUnit;
		  }
	  
	  public Double getPriceMeasure() {
		    return priceMeasure;
		  }

	  public void setPriceMeasure(Double priceMeasure) {
		    this.priceMeasure = priceMeasure;
		  }
	  
	  public Unit getUnit() {
		    return unit;
		  }

	  public void setUnit(Unit unit) {
		    this.unit = unit;
		  }
	  public Double getQty() {
		    return qty;
		  }
	  
	  public void setQty(double _qty) {
		    qty = _qty;
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
