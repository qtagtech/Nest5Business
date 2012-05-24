package com.nest5.androidClient;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.TextView;

import android.widget.LinearLayout;


public class HeaderBar extends LinearLayout {

	ImageButton imagebtn;
	TextView usernameText;
	

	public HeaderBar(Context context) {
		super(context);
		//Inflate the view from the XML Layout
		initHeader();
		
		
		
		
	}
	
	public HeaderBar(Context context, AttributeSet attrs)
	{
		super(context,attrs);
		initHeader();
		
	}
	
	
	protected void initHeader(){
		String infSrvice = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(infSrvice);
		li.inflate(R.layout.header,this,true);
		
		//Get References to child controls
		imagebtn = (ImageButton) findViewById(R.id.header_button);
		usernameText = (TextView) findViewById(R.id.header_username);
	}

}
