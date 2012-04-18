package com.nest5.androidClient;

import android.database.CharArrayBuffer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewCache {
	 
    private View baseView;
    private TextView titleView;
    private TextView descView;
    private ImageView imageView;
   private TextView separator;
 
    public ViewCache(View baseView) {
        this.baseView = baseView;
    }
 
    public TextView getSeparatorView() {
        if (separator== null) {
            separator = (TextView) baseView.findViewById(R.id.separator);
        }
        return separator;
    }
    public TextView getTitleView() {
        if (titleView == null) {
            titleView = (TextView) baseView.findViewById(R.id.title);
        }
        return titleView;
    }
    public TextView getDescView() {
        if (descView == null) {
            descView = (TextView) baseView.findViewById(R.id.description);
        }
        return descView;
    }
 
    public ImageView getImageView() {
        if (imageView == null) {
            imageView = (ImageView) baseView.findViewById(R.id.image);
        }
        return imageView;
    }
}