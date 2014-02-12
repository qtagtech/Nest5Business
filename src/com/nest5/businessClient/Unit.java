package com.nest5.businessClient;

import java.util.LinkedHashMap;

public class Unit {
	
	private long id;
	private String name;
	private String initials;
	private LinkedHashMap<String,Double> multipliers;
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

	  public void setInitials(String initials) {
	    this.initials = initials;
	  }
	  
	  public String getInitials() {
		    return initials;
		  }
	  
	  public void setMultipliers(LinkedHashMap<String, Double> multipliers)
	  {
		  this.multipliers = multipliers;
	  }
	  
	  public LinkedHashMap<String, Double> getMultipliers() {
		    return multipliers;
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
