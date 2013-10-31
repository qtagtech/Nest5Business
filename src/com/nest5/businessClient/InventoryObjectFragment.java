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
public  class InventoryObjectFragment extends Fragment {
public static final String ARG_OBJECT = "object";


public interface OnInventoryObjectFragmentCreatedListener {
    public void OnInventoryObjectFragmentCreated(View v);
    
}

private OnInventoryObjectFragmentCreatedListener onInventoryObjectFragmentCreatedListener;
private  View rootView;

@Override
public void onAttach(Activity activity){
	super.onAttach(activity);
	try
	{
		onInventoryObjectFragmentCreatedListener = (OnInventoryObjectFragmentCreatedListener) activity;
	}
	catch(ClassCastException e){
		throw new ClassCastException(activity.toString() + " must implement OnInventoryObjectFragmentCreatedListener");
	}
	
}

public InventoryObjectFragment(){
	
}
@Override
public View onCreateView(LayoutInflater inflater,
     ViewGroup container, Bundle savedInstanceState) {
	rootView = inflater.inflate(
	         R.layout.inventory, container, false);
	 //mContext = rootView.getContext();
	 Bundle args = getArguments();
	 //((TextView) rootView.findViewById(android.R.id.text1)).setText(
	        // Integer.toString(args.getInt(ARG_OBJECT)));
	Drawable background = getResources().getDrawable(R.drawable.rosewood);
	 background.setAlpha(50);
	 RelativeLayout backLayout = (RelativeLayout) rootView.findViewById(R.id.inventory_layout);
	 backLayout.setBackgroundDrawable(background);
	 return rootView;
}



@Override public void onResume() {
	super.onResume();
	onInventoryObjectFragmentCreatedListener.OnInventoryObjectFragmentCreated(rootView);
}


}
