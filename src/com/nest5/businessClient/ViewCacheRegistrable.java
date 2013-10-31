package com.nest5.businessClient;

import com.nest5.businessClient.R;


import android.view.View;
//import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewCacheRegistrable {
	 
    private View baseView;
    //private TextView nameView;
    public String type = "Producto";
    
    //private Button buttonView;
    private ImageView imageView;
    private TextView priceView;
    private TextView nameView;
   
 
    public ViewCacheRegistrable(View baseView) {
        this.baseView = baseView;
    }
 
    /*public TextView getNameView() {
        if (nameView== null) {
            nameView = (TextView) baseView.findViewById(R.id.item_name);
        }
        return nameView;
    }*/
    
    public TextView getPriceView() {
        if (priceView== null) {
            priceView = (TextView) baseView.findViewById(R.id.item_price);
        }
        return priceView;
    }
    
    
 
    public ImageView getImageView() {
        if (imageView == null) {
            imageView = (ImageView) baseView.findViewById(R.id.item_image_test);
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