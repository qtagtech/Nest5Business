package com.nest5.businessClient;

import java.util.List;

import com.nest5.businessClient.AddIngredientForm.OnAddIngredientListener;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;



//Instances of this class are fragments representing a single
//object in our collection.
public  class HomeObjectFragment extends Fragment /*implements View.OnClickListener*/ {
	
public static final String ARG_OBJECT = "object";

//private List<IngredientCategory> ingredients;
//private Context mContext;
private OnIngredientCategorySelectedListener onIngredientCategorySelectedListener;
private OnHomeObjectFragmentCreatedListener onHomeObjectFragmentCreatedListener;
//private long[] values;
private  View rootView;

public interface OnIngredientCategorySelectedListener {
    public void OnIngredientCategorySelected(long id);
    
}

public interface OnHomeObjectFragmentCreatedListener {
    public void OnHomeObjectFragmentCreated(View v);
    
}

@Override
public void onAttach(Activity activity){
	super.onAttach(activity);
	try
	{
		onIngredientCategorySelectedListener = (OnIngredientCategorySelectedListener) activity;
	}
	catch(ClassCastException e){
		throw new ClassCastException(activity.toString() + " must implement OnIngredientCategorySelectedListener");
	}
	try
	{
		onHomeObjectFragmentCreatedListener = (OnHomeObjectFragmentCreatedListener) activity;
	}
	catch(ClassCastException e){
		throw new ClassCastException(activity.toString() + " must implement OnHomeObjectFragmentCreatedListener");
	}
}

/*public HomeObjectFragment(List<IngredientCategory> ingres){
	
	ingredients = ingres;
}*/
public HomeObjectFragment(){
	
	
}

@Override
public View onCreateView(LayoutInflater inflater,
     ViewGroup container, Bundle savedInstanceState) {
 // The last two arguments ensure LayoutParams are inflated
 // properly.
 rootView = inflater.inflate(
         R.layout.home, container, false);
 //mContext = rootView.getContext();
 Bundle args = getArguments();
 //((TextView) rootView.findViewById(android.R.id.text1)).setText(
        // Integer.toString(args.getInt(ARG_OBJECT)));
Drawable background = getResources().getDrawable(R.drawable.rosewood);
 background.setAlpha(50);
 LinearLayout backLayout = (LinearLayout) rootView.findViewById(R.id.home_layout);
 backLayout.setBackgroundDrawable(background);
 //LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.ingredient_categories_buttons);
		 //values = new long[ingredients.size()];
		 
	 //tabHost.clearAllTabs();
	 /*for(int i = 0; i < ingredients.size(); i++)
	 {
		 
		 	Button btnTag = new Button(rootView.getContext());
	        btnTag.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	        btnTag.setText(ingredients.get(i).getName());
	        btnTag.setId((int)ingredients.get(i).getId());
	        btnTag.setOnClickListener(this);
	        ll.addView(btnTag); 
	        values[i] = ingredients.get(i).getId();
	 }*/
	 
	 /*GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter(rootView.getContext()));

	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            Toast.makeText(rootView.getContext(), "" + position, Toast.LENGTH_SHORT).show();
	        }
	    });*/
	 

 return rootView;
}

@Override public void onResume() {
	super.onResume();
	onHomeObjectFragmentCreatedListener.OnHomeObjectFragmentCreated(rootView);
}

	//@Override
	//public void onClick(View v) {
		//onIngredientCategorySelectedListener.OnIngredientCategorySelected(values[v.getId() - 1]);
	    /*switch(v.getId()) {
	        case 0:
	        Toast.makeText(mContext, "HOLA",Toast.LENGTH_LONG).show();
	        break;
	        case 1:
	        // do stuff;
	        break;
    
    	}*/
	//}
}
