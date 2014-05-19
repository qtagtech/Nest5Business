package com.nest5.businessClient;



import android.os.Parcel;
import android.os.Parcelable;


import android.util.Pair;

import com.google.gson.annotations.SerializedName;

public class Table implements Parcelable {
	
	@SerializedName("name")
	private String name;
	
	@SerializedName("coordinate_x")
	private int coordinate_x;
	
	@SerializedName("coordinate_y")
	private int coordinate_y;
	
	@SerializedName("clients")
	private int clients;
	
	public String getName(){
		return this.name;
	}
	public int getClients(){
		return this.clients;
	}
	public int getCoordinate_x(){
		return this.coordinate_x;
	}
	public int getCoordinate_y(){
		return this.coordinate_y;
	}
	public void setName(String _name){
		this.name = _name;
	}
	public void setName(int _clients){
		this.clients = _clients;
	}
	public void setCoordinates_x(int _coordinates_x){
		this.coordinate_x = _coordinates_x;
	}
	public void setCoordinates_y(int _coordinates_y){
		this.coordinate_y = _coordinates_y;
	}
	
	public Table(String name, int clients, int[] coordinates){
		this.name = name;
		this.clients = clients;
		this.coordinate_x = coordinates[0];
		this.coordinate_y = coordinates[1];
	}
	
	/* everything below here is for implementing Parcelable */

    // 99.9% of the time you can just ignore this
	@Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeInt(coordinate_x);
        out.writeInt(coordinate_y);
        out.writeInt(clients);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Table> CREATOR = new Parcelable.Creator<Table>() {
        public Table createFromParcel(Parcel in) {
            return new Table(in);
        }

        public Table[] newArray(int size) {
            return new Table[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Table(Parcel in) {
       
        name = in.readString();
        coordinate_x = in.readInt();
        coordinate_y = in.readInt();
        clients = in.readInt(); 
    }
	

}


