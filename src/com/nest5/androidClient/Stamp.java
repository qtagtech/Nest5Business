package com.nest5.androidClient;





import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Stamp implements Parcelable {

	
	@SerializedName("id")
	public int id;
	
	@SerializedName("date")
	public String date;
	
	@SerializedName("redeemed")
	public Boolean redeemed;
	
	
	
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
        out.writeString(date);
        out.writeByte((byte) (redeemed ? 1 : 0)); 
       
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Stamp> CREATOR = new Parcelable.Creator<Stamp>() {
        public Stamp createFromParcel(Parcel in) {
            return new Stamp(in);
        }

        public Stamp[] newArray(int size) {
            return new Stamp[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Stamp(Parcel in) {
        id = in.readInt();
        date = in.readString();
        redeemed = in.readByte() == 1; 
        
    }
}
