package com.nest5.businessClient;

import java.util.List;

import com.nest5.businessClient.HomeObjectFragment.OnHomeObjectFragmentCreatedListener;
import com.nest5.businessClient.HomeObjectFragment.OnIngredientCategorySelectedListener;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;



//Instances of this class are fragments representing a single
//object in our collection.
public  class Nest5ReadObjectFragment extends Fragment {
public static final String ARG_OBJECT = "object";


public interface OnNest5ReadObjectFragmentCreatedListener {
    public void OnNest5ReadObjectFragmentCreated(View v);
    
}

private OnNest5ReadObjectFragmentCreatedListener onNest5ReadObjectFragmentCreatedListener;
private  View rootView;

@Override
public void onAttach(Activity activity){
	super.onAttach(activity);
	try
	{
		onNest5ReadObjectFragmentCreatedListener = (OnNest5ReadObjectFragmentCreatedListener) activity;
	}
	catch(ClassCastException e){
		throw new ClassCastException(activity.toString() + " must implement OnNest5ReadObjectFragmentCreatedListener");
	}
	
}

public Nest5ReadObjectFragment(){
	
}
@Override
public View onCreateView(LayoutInflater inflater,
     ViewGroup container, Bundle savedInstanceState) {
	rootView = inflater.inflate(
	         R.layout.nest5_read, container, false);
	 //mContext = rootView.getContext();
	 Bundle args = getArguments();
	 //((TextView) rootView.findViewById(android.R.id.text1)).setText(
	        // Integer.toString(args.getInt(ARG_OBJECT)));
	Drawable background = getResources().getDrawable(R.drawable.rosewood);
	 background.setAlpha(50);
	 LinearLayout backLayout = (LinearLayout) rootView.findViewById(R.id.nest5_read_layout);
	 backLayout.setBackgroundDrawable(background);
	 return rootView;
}



@Override public void onResume() {
	super.onResume();
	onNest5ReadObjectFragmentCreatedListener.OnNest5ReadObjectFragmentCreated(rootView);
}


}
