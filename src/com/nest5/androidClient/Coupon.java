package com.nest5.androidClient;





import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Coupon implements Parcelable {

	
	@SerializedName("id")
	public int id;
	
	@SerializedName("date")
	public String date;
	
	@SerializedName("redeemed")
	public int redeemed;
	
	@SerializedName("promo")
	public Promo promo;
	
	
	
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
        out.writeInt(redeemed);
        out.writeParcelable(promo,flags);
       
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Coupon> CREATOR = new Parcelable.Creator<Coupon>() {
        public Coupon createFromParcel(Parcel in) {
            return new Coupon(in);
        }

        public Coupon[] newArray(int size) {
            return new Coupon[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Coupon(Parcel in) {
        id = in.readInt();
        date = in.readString();
        redeemed = in.readInt();
        promo = in.readParcelable(Promo.class.getClassLoader());
        
    }
}
