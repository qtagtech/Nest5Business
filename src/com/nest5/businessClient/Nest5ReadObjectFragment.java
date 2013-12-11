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

import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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

    public void OnManualReadPressed(String email,Boolean redeem);
    
}

private OnNest5ReadObjectFragmentCreatedListener mCallback;
private  View rootView;
private Button readManualBtn;
private Button manualButton;
private EditText manualEmail;
private Button manualRedeem;


@Override
public void onAttach(Activity activity){
	super.onAttach(activity);
	try
	{

		mCallback = (OnNest5ReadObjectFragmentCreatedListener) activity;

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

	 readManualBtn = (Button) rootView.findViewById(R.id.read_manual_username);
	 readManualBtn.setOnClickListener(displayManualUsernameListener);
	 //Temporal, mientras la prioridad es el lector manual y no el banda magnética
	 manualEmail = (EditText) rootView.findViewById(R.id.manual_email);
	 if(manualEmail != null){
		 manualButton = (Button) rootView.findViewById(R.id.stamp_manual_user);
		 manualRedeem = (Button) rootView.findViewById(R.id.redeem_manual_user);
			manualButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					
					if (!manualEmail.getText().toString().trim().equals(""))
					{
						mCallback.OnManualReadPressed(manualEmail.getText().toString(),false);
					}
					
				}
			});
manualRedeem.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					
					if (!manualEmail.getText().toString().trim().equals(""))
					{
						mCallback.OnManualReadPressed(manualEmail.getText().toString(),true);
					}
					
				}
			}); 
	 }//acaba lo temporal
		

	 return rootView;
}



private OnClickListener displayManualUsernameListener = new OnClickListener() {

	@Override
	public void onClick(View v) {
		LinearLayout frame = (LinearLayout) rootView.findViewById(R.id.manual_user_layout);
		frame.setVisibility(View.VISIBLE);
		if(manualEmail == null){
			manualEmail = (EditText) rootView.findViewById(R.id.manual_email);
			manualButton = (Button) rootView.findViewById(R.id.stamp_manual_user);
			manualButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					
					if (!manualEmail.getText().toString().trim().equals(""))
					{
						mCallback.OnManualReadPressed(manualEmail.getText().toString(),false);
					}
					
				}
			});
		}
		
		

	}
};





@Override public void onResume() {
	super.onResume();
	mCallback.OnNest5ReadObjectFragmentCreated(rootView);

}


}
