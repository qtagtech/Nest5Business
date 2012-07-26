package com.nest5.androidClient;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;


import com.google.gson.annotations.SerializedName;

public class AnswerCoupon implements Parcelable {
	
	@SerializedName("company")
	public Company company;
	
	@SerializedName("promocion")
	public Promo promo;
	
	@SerializedName("coupon")
	public Coupon coupon;
	
	
	@SerializedName("imagen")
	public String imagen;
	
	
	
	
	
	
	
	
	/* everything below here is for implementing Parcelable */

    // 99.9% of the time you can just ignore this
	@Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
    	out.writeParcelable(company,flags);
    	
    	out.writeParcelable(promo,flags);
    	out.writeParcelable(coupon,flags);
    	out.writeString(imagen);
        
        
                
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<AnswerCoupon> CREATOR = new Parcelable.Creator<AnswerCoupon>() {
        public AnswerCoupon createFromParcel(Parcel in) {
            return new AnswerCoupon(in);
        }

        public AnswerCoupon[] newArray(int size) {
            return new AnswerCoupon[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private AnswerCoupon(Parcel in) {
       // company =  Company.CREATOR.createFromParcel(in);
    	company = in.readParcelable(Company.class.getClassLoader());
        promo = in.readParcelable(Promo.class.getClassLoader());
        coupon = in.readParcelable(Coupon.class.getClassLoader());
        imagen = in.readString();
        //Log.i("LEYENDO",company.name);
        
        
        
        
    }
	

}


