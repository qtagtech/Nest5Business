package com.nest5.businessClient;

import com.google.gson.annotations.SerializedName;

public class ProductCategory {
	
	@SerializedName("id")
	private long id;
	@SerializedName("name")
	private String name;
	@SerializedName("syncId")
	private Long syncId;

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
