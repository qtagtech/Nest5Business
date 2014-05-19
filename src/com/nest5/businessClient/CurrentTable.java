package com.nest5.businessClient;

import android.os.Parcel;
import android.os.Parcelable;
import com.nest5.businessClient.Table;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("hiding")
public class CurrentTable<Table, Integer> {
	@SerializedName("table")
    private Table table;
	@SerializedName("clients")
    private java.lang.Integer clients;

    public CurrentTable(Table first, java.lang.Integer second) {
    	super();
    	this.table = first;
    	this.clients = second;
    }

    public int hashCode() {
    	int hashFirst = table != null ? table.hashCode() : 0;
    	int hashSecond = clients != null ? clients.hashCode() : 0;

    	return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    public boolean equals(Object other) {
    	if (other instanceof CurrentTable) {
    		CurrentTable<?, ?> otherPair = (CurrentTable<?, ?>) other;
    		return 
    		((  this.table == otherPair.table ||
    			( this.table != null && otherPair.table != null &&
    			  this.table.equals(otherPair.table))) &&
    		 (	this.clients == otherPair.clients ||
    			( this.clients != null && otherPair.clients != null &&
    			  this.clients.equals(otherPair.clients))) );
    	}

    	return false;
    }

    public String toString()
    { 
           return "(" + table + ", " + clients + ")"; 
    }

    public Table getTable() {
    	return table;
    }

    public void setTable(Table first) {
    	this.table = first;
    }

    public java.lang.Integer getClients() {
    	return clients;
    }

    public void setClients(java.lang.Integer second) {
    	this.clients = second;
    }
	
	/* everything below here is for implementing Parcelable */

    // 99.9% of the time you can just ignore this
	public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable((Parcelable) table,flags);
        out.writeInt((java.lang.Integer) clients);

    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<CurrentTable<com.nest5.businessClient.Table,java.lang.Integer>> CREATOR = new Parcelable.Creator<CurrentTable<com.nest5.businessClient.Table,java.lang.Integer>>() {
        public CurrentTable<com.nest5.businessClient.Table,java.lang.Integer> createFromParcel(Parcel in) {
            return new CurrentTable<com.nest5.businessClient.Table,java.lang.Integer>(in);
        }

        @SuppressWarnings("unchecked")
		public CurrentTable<com.nest5.businessClient.Table,java.lang.Integer>[] newArray(int size) {
            return (CurrentTable<com.nest5.businessClient.Table, java.lang.Integer>[]) new CurrentTable<?,?>[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private CurrentTable(Parcel in) {
       
        table = in.readParcelable(com.nest5.businessClient.Table.class.getClassLoader());
        clients = (java.lang.Integer) in.readInt(); 
    }
	

}


