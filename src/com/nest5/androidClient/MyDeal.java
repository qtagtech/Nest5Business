package com.nest5.androidClient;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

public class MyDeal implements Parcelable {
	
	@SerializedName("company")
	public Company company;
	
	@SerializedName("imagen")
	public String imagen;
	
	@SerializedName("stamps")
	public ArrayList<Stamp> stamps;
	
	@SerializedName("promocion")
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
        out.writeParcelable(company,flags);
        out.writeString(imagen);
        int cant = (int)stamps.size();
        out.writeInt(cant);
        //Log.i("Escribiendo", String.valueOf((int)promos.size()));
                   for (Stamp it : stamps) {
                       out.writeParcelable(it, flags);
                   }
        out.writeParcelable(promo,flags);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<MyDeal> CREATOR = new Parcelable.Creator<MyDeal>() {
        public MyDeal createFromParcel(Parcel in) {
            return new MyDeal(in);
        }

        public MyDeal[] newArray(int size) {
            return new MyDeal[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private MyDeal(Parcel in) {
       // company =  Company.CREATOR.createFromParcel(in);
        company = in.readParcelable(Company.class.getClassLoader());
        //Log.i("LEYENDO",company.name);
        imagen = in.readString();
        
        int cant = (int)in.readInt();
        //Log.i("LEYENDO",String.valueOf(cant));
        
        stamps = new ArrayList<Stamp>();
        for (int i = 0; i < cant; i++) {
        	             Stamp stamp = in.readParcelable(Stamp.class.getClassLoader());
        	             stamps.add(stamp);
        	         }
     // promo =  Company.CREATOR.createFromParcel(in);
        promo = in.readParcelable(Promo.class.getClassLoader());
        
        
    }
	

}


