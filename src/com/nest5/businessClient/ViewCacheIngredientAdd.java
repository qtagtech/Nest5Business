package com.nest5.businessClient;

import com.nest5.businessClient.R;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ViewCacheIngredientAdd {
	 
    private View baseView;
    //private TextView nameView;
    public String type = "Producto";
    
    private TextView titleView;
    private EditText qtyView;
    private Spinner unitView;
   
 
    public ViewCacheIngredientAdd(View baseView) {
        this.baseView = baseView;
    }
 
    /*public TextView getNameView() {
        if (nameView== null) {
            nameView = (TextView) baseView.findViewById(R.id.item_name);
        }
        return nameView;
    }*/
    
    public TextView getTitleView() {
        if (titleView== null) {
            titleView = (TextView) baseView.findViewById(R.id.ingredient_row_title);
        }
        return titleView;
    }
    
    public Spinner getUnitView() {
        if (unitView== null) {
            unitView = (Spinner) baseView.findViewById(R.id.ingredient_add_unit_spinner);
        }
        return unitView;
    }
    
    
 
    public EditText getQtyView() {
        if (qtyView == null) {
            qtyView = (EditText) baseView.findViewById(R.id.ingredient_qty_text);
        }
        return qtyView;
    }
}