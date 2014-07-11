package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SyncRowDataSource {
	/*
	 *  
    public static final String COLUMN_DEVICE_ID = "device_id";
    public static final String COLUMN_TABLE_NAME = "table";
    public static final String COLUMN_ROW_ID = "row_id";
    public static final String COLUMN_TIME_CREATED = "time_created";
    public static final String COLUMN_SYNC_ID = "sync_id";
    public static final String COLUMN_FIELDS = "fields";
	 * */
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { Setup.COLUMN_ID,Setup.COLUMN_DEVICE_ID, Setup.COLUMN_TABLE_NAME, Setup.COLUMN_ROW_ID, Setup.COLUMN_TIME_CREATED, Setup.COLUMN_SYNC_ID, Setup.COLUMN_FIELDS };

	  public SyncRowDataSource(MySQLiteHelper _dbHelper) {
	    dbHelper = _dbHelper;
	  }

	  public SQLiteDatabase open() throws SQLException {
		
	    database = dbHelper.getWritableDatabase();
	    return database;
	  }
	  public void open(SQLiteDatabase _database)
	  {
		  database = _database;
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public SyncRow createSyncRow(String deviceId, String table, Long rowId, long syncId, String fields) {
	    ContentValues values = new ContentValues();
	    values.put(Setup.COLUMN_DEVICE_ID, deviceId.trim().toLowerCase());
	    values.put(Setup.COLUMN_TABLE_NAME, table.trim().toLowerCase());
	    values.put(Setup.COLUMN_ROW_ID, rowId);
	    values.put(Setup.COLUMN_TIME_CREATED, new Date().getTime());
	    values.put(Setup.COLUMN_SYNC_ID,syncId);
	    values.put(Setup.COLUMN_FIELDS,fields);
	    long insertId = database.insert(Setup.TABLE_SYNC_ROW, null,
	        values);
	    Cursor cursor = database.query(Setup.TABLE_SYNC_ROW,
	        allColumns, Setup.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    SyncRow newSyncRow = cursorToSyncRow(cursor);
	    cursor.close();
	    return newSyncRow;
	  }

	  public void deleteSyncRow(SyncRow syncRow) {
	    long id = syncRow.getId();
	   // System.out.println("SyncRow deleted with id: " + id);
	    database.delete(Setup.TABLE_SYNC_ROW, Setup.COLUMN_ID
	        + " = " + id, null);
	  }

	  public List<SyncRow> getAllSyncRows() {
		    List<SyncRow> syncRows = new ArrayList<SyncRow>();
		    Cursor cursor = database.query(Setup.TABLE_SYNC_ROW,
		        allColumns, null, null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      SyncRow syncRow = cursorToSyncRow(cursor);
		      syncRows.add(syncRow);
		      cursor.moveToNext();
		    }
		    // Make sure to close the cursor
		    cursor.close();
		    return syncRows;
		  }
	  public List<SyncRow> getAllSyncRows(SQLiteDatabase database) {
	    List<SyncRow> syncRows = new ArrayList<SyncRow>();
	    this.database = database;
	    Cursor cursor = database.query(Setup.TABLE_SYNC_ROW,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      SyncRow syncRow = cursorToSyncRow(cursor);
	      syncRows.add(syncRow);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return syncRows;
	  }
	  
	  public SyncRow getSyncRow(long id) {
		   SyncRow syncRow = null;
		   Cursor cursor = database.rawQuery("select * from " + Setup.TABLE_SYNC_ROW + " where " + Setup.COLUMN_ID + "=" + id  , null);
	        if (cursor != null) 
	        	{
	        		cursor.moveToFirst();
	        		
	      		      syncRow = cursorToSyncRow(cursor);
	      		      
	      		    // Make sure to close the cursor
	      		    cursor.close();
	        	}
	        return syncRow;
		  }

	  private SyncRow cursorToSyncRow(Cursor cursor) {
	    SyncRow syncRow = new SyncRow();
	    syncRow.setId(cursor.getLong(0));
	    syncRow.setDeviceId(cursor.getString(1));
	    syncRow.setTable(cursor.getString(2));
	    syncRow.setRowId(cursor.getLong(3));
	    syncRow.setTimeCreated(cursor.getLong(4));
	    syncRow.setSyncId(cursor.getLong(5));
	    syncRow.setFields(cursor.getString(6));
	    return syncRow;
	  }

}
