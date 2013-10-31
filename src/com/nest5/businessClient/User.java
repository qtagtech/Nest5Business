package com.nest5.businessClient;



import android.os.Parcel;
import android.os.Parcelable;


import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {
	
	@SerializedName("id")
	public int id;
	
	@SerializedName("birthday")
	public String date;
	
	@SerializedName("name")
	public String name;
	
	@SerializedName("picture")
	public String picture;
	
	@SerializedName("originalCity")
	public String city;
	
	@SerializedName("gender")
	public String gender;
	
	
	

	
	
	
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
        out.writeString(name);
        out.writeString(picture);
        out.writeString(city);
        out.writeString(gender);

        
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private User(Parcel in) {
       
        id = in.readInt();
        date = in.readString();
        name = in.readString();
        picture = in.readString();
        city = in.readString();
        gender = in.readString();

        
        
    }
	

}


