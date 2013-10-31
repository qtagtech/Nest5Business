package com.nest5.businessClient;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Promo implements Parcelable {

	
	@SerializedName("id")
	public int id;
	
	@SerializedName("action")
	public String action;
	
	@SerializedName("reqQTY")
	public int reqQTY;
	
	@SerializedName("perkQTY")
	public int perkQTY;
	
	@SerializedName("requirement")
	public String requirement;
	
	@SerializedName("perk")
	public String perk;
	
	@SerializedName("name")
	public String name;
	
	@SerializedName("storename")
	public String store;
	
	@SerializedName("storeid")
	public int storeid;
	
	
	
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
        out.writeString(action);
        out.writeInt(reqQTY);
        out.writeInt(perkQTY);
        out.writeString(requirement);
        out.writeString(perk);
        out.writeString(name);
        out.writeString(store);
        out.writeInt(storeid);
        
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Promo> CREATOR = new Parcelable.Creator<Promo>() {
        public Promo createFromParcel(Parcel in) {
            return new Promo(in);
        }

        public Promo[] newArray(int size) {
            return new Promo[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Promo(Parcel in) {
        id = in.readInt();
        action = in.readString();
        reqQTY = in.readInt();
        perkQTY = in.readInt();
        requirement = in.readString();
        perk = in.readString();
        name = in.readString();
        store = in.readString();
        storeid = in.readInt();
    }
}
