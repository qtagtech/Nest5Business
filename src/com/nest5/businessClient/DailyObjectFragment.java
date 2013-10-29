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
public  class DailyObjectFragment extends Fragment {
public static final String ARG_OBJECT = "object";


public interface OnDailyObjectFragmentCreatedListener {
    public void OnDailyObjectFragmentCreated(View v);
    
}

private OnDailyObjectFragmentCreatedListener onDailyObjectFragmentCreatedListener;
private  View rootView;

@Override
public void onAttach(Activity activity){
	super.onAttach(activity);
	try
	{
		onDailyObjectFragmentCreatedListener = (OnDailyObjectFragmentCreatedListener) activity;
	}
	catch(ClassCastException e){
		throw new ClassCastException(activity.toString() + " must implement OnDailyObjectFragmentCreatedListener");
	}
	
}

public DailyObjectFragment(){
	
}
@Override
public View onCreateView(LayoutInflater inflater,
     ViewGroup container, Bundle savedInstanceState) {
	rootView = inflater.inflate(
	         R.layout.daily, container, false);
	 //mContext = rootView.getContext();
	 Bundle args = getArguments();
	 //((TextView) rootView.findViewById(android.R.id.text1)).setText(
	        // Integer.toString(args.getInt(ARG_OBJECT)));
	Drawable background = getResources().getDrawable(R.drawable.rosewood);
	 background.setAlpha(50);
	 RelativeLayout backLayout = (RelativeLayout) rootView.findViewById(R.id.daily_layout);
	 backLayout.setBackgroundDrawable(background);
	 return rootView;
}



@Override public void onResume() {
	super.onResume();
	onDailyObjectFragmentCreatedListener.OnDailyObjectFragmentCreated(rootView);
}


}
