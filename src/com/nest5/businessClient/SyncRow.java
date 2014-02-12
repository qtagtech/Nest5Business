package com.nest5.businessClient;

import java.security.Timestamp;

public class SyncRow {
	/*
	 *  
    public static final String COLUMN_DEVICE_ID = "device_id";
    public static final String COLUMN_TABLE_NAME = "reference";
    public static final String COLUMN_ROW_ID = "row_id";
    public static final String COLUMN_TIME_CREATED = "time_created";
    public static final String COLUMN_SYNC_ID = "sync_id";
    public static final String COLUMN_FIELDS = "fields";
	 * */
	
	private long id;
	private String deviceId;
	private String table;
	private long rowId;
	private Long timeCreated;
	private long syncId;
	private String fields;

	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	  public String getDeviceId() {
	    return deviceId;
	  }

	  public void setDeviceId(String deviceId) {
	    this.deviceId = deviceId;
	  }
	  public String getTable() {
		    return table;
		  }

	  public void setTable(String table) {
		    this.table = table;
		  }
	  public Long getRowId() {
		    return rowId;
		  }

	  public void setRowId(Long rowId) {
		    this.rowId = rowId;
		  }
	  public Long getTimeCreated() {
		    return timeCreated;
		  }

	  public void setTimeCreated(Long timeCreated) {
		    this.timeCreated = timeCreated;
		  }
	  public long getSyncId() {
		    return syncId;
		  }

	  public void setSyncId(long syncId) {
		    this.syncId = syncId;
		  }
	  public String getFields() {
		    return fields;
		  }

		  public void setFields(String fields) {
		    this.fields = fields;
		  }
		  
	
		  

	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return String.valueOf(syncId);
	  }

}
