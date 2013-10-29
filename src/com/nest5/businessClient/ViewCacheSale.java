package com.nest5.businessClient;

import com.nest5.businessClient.R;


import android.view.View;
//import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewCacheSale {
	 
    private View baseView;
    //private TextView nameView;
    public String type = "Sale";
    
    //private Button buttonView;
    private ImageView imageView;
    private TextView timeView;
    private TextView nameView;
   
 
    public ViewCacheSale(View baseView) {
        this.baseView = baseView;
    }
 
    /*public TextView getNameView() {
        if (nameView== null) {
            nameView = (TextView) baseView.findViewById(R.id.item_name);
        }
        return nameView;
    }*/
    
    public TextView getTimeView() {
        if (timeView== null) {
            timeView = (TextView) baseView.findViewById(R.id.item_timer);
        }
        return timeView;
    }
    
    
 
    public ImageView getImageView() {
        if (imageView == null) {
            imageView = (ImageView) baseView.findViewById(R.id.clock_image);
        }
        return imageView;
    }
    
    public TextView getNameView() {
        if (nameView == null) {
            nameView = (TextView) baseView.findViewById(R.id.item_name);
        }
        return nameView;
    }
}