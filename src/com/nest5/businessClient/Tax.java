package com.nest5.businessClient;

public class Tax {
	
	private long id;
	private String name;
	private double percentage;
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
	  
	  public double getPercentage() {
		    return percentage;
		  }

		  public void setPercentage(double percentage) {
		    this.percentage = percentage;
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
