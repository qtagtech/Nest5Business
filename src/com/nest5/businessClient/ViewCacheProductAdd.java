package com.nest5.businessClient;

import com.nest5.businessClient.R;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ViewCacheProductAdd {
	 
    private View baseView;
    //private TextView nameView;
    public String type = "Producto";
    
    private TextView titleView;
    private EditText qtyView;

   
 
    public ViewCacheProductAdd(View baseView) {
        this.baseView = baseView;
    }
 
    public TextView getTitleView() {
        if (titleView== null) {
            titleView = (TextView) baseView.findViewById(R.id.product_row_title);
        }
        return titleView;
    }
    
    
    
    
 
    public EditText getQtyView() {
        if (qtyView == null) {
            qtyView = (EditText) baseView.findViewById(R.id.product_qty_text);
        }
        return qtyView;
    }
}