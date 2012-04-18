package com.nest5.androidClient;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

public class Deal implements Parcelable {
	
	@SerializedName("company")
	public Company company;
	
	@SerializedName("imagen")
	public String imagen;
	
	@SerializedName("promos")
	public ArrayList<Promo> promos;
	
	
	
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
        int cant = (int)promos.size();
        out.writeInt(cant);
        //Log.i("Escribiendo", String.valueOf((int)promos.size()));
                   for (Promo it : promos) {
                       out.writeParcelable(it, flags);
                   }
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Deal> CREATOR = new Parcelable.Creator<Deal>() {
        public Deal createFromParcel(Parcel in) {
            return new Deal(in);
        }

        public Deal[] newArray(int size) {
            return new Deal[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Deal(Parcel in) {
       // company =  Company.CREATOR.createFromParcel(in);
        company = in.readParcelable(Company.class.getClassLoader());
        //Log.i("LEYENDO",company.name);
        imagen = in.readString();
        
        int cant = (int)in.readInt();
        //Log.i("LEYENDO",String.valueOf(cant));
        
        promos = new ArrayList<Promo>();
        for (int i = 0; i < cant; i++) {
        	             Promo promo = in.readParcelable(Promo.class.getClassLoader());
        	             promos.add(promo);
        	         }
        
        
    }
	

}


