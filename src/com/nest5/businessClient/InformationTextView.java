package com.nest5.businessClient;

import com.nest5.businessClient.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.TextView;

import android.widget.LinearLayout;


public class InformationTextView extends LinearLayout {

	ImageButton iconbtn;
	TextView message;
	
	private String messageText;
	private int icon;
	
	

	public InformationTextView(Context context) {
		super(context);
		//Inflate the view from the XML Layout
		initHeader();
		
		
		
		
	}
	
	public InformationTextView(Context context, AttributeSet attrs)
	{
		super(context,attrs);
		initHeader();
		
	}
	
	public InformationTextView(Context context, AttributeSet attrs, int _icon, String _message)
	{
		super(context,attrs);
		icon = _icon;
		messageText = _message;
		initHeader();
		
	}
	
	
	protected void initHeader(){
		String infSrvice = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(infSrvice);
		li.inflate(R.layout.info_text_view,this,true);
		int iconId;
		switch (icon)
		{
			case 0: iconId = R.drawable.face_winl;
				break;
			case 1: iconId = R.drawable.face_one;
			break;
			case 2: iconId = R.drawable.face_two;
			break;
			case 3: iconId = R.drawable.face_three;
			break;
			case 4: iconId = R.drawable.face_cheer;
			break;
			default: iconId = R.drawable.face_cheer;
				break; 
		}
		//Get References to child controls
		iconbtn = (ImageButton) findViewById(R.id.infotext_icon);
		message = (TextView) findViewById(R.id.infotext_message);
		
		message.setText(messageText);
		iconbtn.setImageResource(iconId);
	}

}
