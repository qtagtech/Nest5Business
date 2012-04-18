package com.nest5.androidClient;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;


import com.google.gson.annotations.SerializedName;

public class Company implements Parcelable {

	
	@SerializedName("id")
	public int id;
	
	@SerializedName("name")
	public String name;
	
	/* everything below here is for implementing Parcelable */

    // 99.9% of the time you can just ignore this
	@Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        
        
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Company> CREATOR = new Parcelable.Creator<Company>() {
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        public Company[] newArray(int size) {
            return new Company[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Company(Parcel in) {
        id = in.readInt();
        name = in.readString();
        
        
    }
    
}
