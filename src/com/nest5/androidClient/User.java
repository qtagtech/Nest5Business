package com.nest5.androidClient;



import android.os.Parcel;
import android.os.Parcelable;


import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {
	
	@SerializedName("id")
	public int id;
	
	@SerializedName("date")
	public String date;
	
	@SerializedName("email")
	public String email;
	
	@SerializedName("name")
	public String name;
	
	@SerializedName("phone")
	public String phone;
	
	@SerializedName("username")
	public String username;
	
	
	
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
        out.writeString(email);
        out.writeString(name);
        out.writeString(phone);
        out.writeString(username);
        
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
        email = in.readString();
        name = in.readString();
        phone = in.readString();
        username = in.readString();
        
        
    }
	

}


