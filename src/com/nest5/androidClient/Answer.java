package com.nest5.androidClient;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;


import com.google.gson.annotations.SerializedName;

public class Answer implements Parcelable {
	
	@SerializedName("company")
	public Company company;
	
	@SerializedName("promo")
	public Promo promo;
	
	@SerializedName("coupons")
	public ArrayList<Coupon> coupons;
	
	@SerializedName("stamps")
	public ArrayList<Stamp> stamps;
	
	
	
	
	
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
       
        int cant = (int)coupons.size();
        out.writeInt(cant);
        //Log.i("Escribiendo", String.valueOf((int)promos.size()));
                   for (Coupon it : coupons) {
                       out.writeParcelable(it, flags);
                   }
          int cantidad = (int)stamps.size();
          out.writeInt(cantidad);
          //Log.i("Escribiendo", String.valueOf((int)promos.size()));
          for (Stamp it : stamps) {
                  out.writeParcelable(it, flags);
            }       
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Answer> CREATOR = new Parcelable.Creator<Answer>() {
        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Answer(Parcel in) {
       // company =  Company.CREATOR.createFromParcel(in);
    	company = in.readParcelable(Company.class.getClassLoader());
        promo = in.readParcelable(Promo.class.getClassLoader());
        //Log.i("LEYENDO",company.name);
        
        
        int cant = (int)in.readInt();
        
        //Log.i("LEYENDO",String.valueOf(cant));
        
        coupons = new ArrayList<Coupon>();
        for (int i = 0; i < cant; i++) {
        	             Coupon coupon = in.readParcelable(Coupon.class.getClassLoader());
        	             coupons.add(coupon);
        	         }
        int cantidad = (int)in.readInt();
        
        //Log.i("LEYENDO",String.valueOf(cant));
        
        stamps = new ArrayList<Stamp>();
        for (int i = 0; i < cantidad; i++) {
        	             Stamp stamp = in.readParcelable(Stamp.class.getClassLoader());
        	             stamps.add(stamp);
        	         }
        
        
    }
	

}


