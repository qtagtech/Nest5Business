package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class PaymentForm extends DialogFragment {
	
	
	public interface OnPayListener {
        public void OnPayClicked(String method,int isDelivery, int isTogo, double value,int tip,double discount);
        
    }
	
private Context mContext;
private LinkedHashMap<Registrable, Integer> items;
private Button cashBtn;
private Button cardBtn;
private Button deliveryBtn;
private Button togoBtn;
private Button tipBtn;
private EditText discountTxt;
private Button payBtn;
private Button cancelBtn;
private EditText valueTxt;
private Double price;
private TextView changeTxt;
private String method = "cash";
private OnPayListener onPayListener;
private PaymentForm frag;
private Double priceVal;
private Integer isDelivery = 0;
private Integer isToGo = 0;
private Integer tip = 0;





@Override
public void onAttach(Activity activity){
	super.onAttach(activity);
	try
	{
		onPayListener = (OnPayListener) activity;
	}
	catch(ClassCastException e){
		throw new ClassCastException(activity.toString() + " must implement OnPayListener");
	}
}


 public PaymentForm() {
     // Empty constructor required for DialogFragment
 }
 
 public PaymentForm(LinkedHashMap<Registrable, Integer> items) {
     this.items = items;
 }
 
 @Override
 public void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
 }
 


 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
	 //frag = this;
     View view = inflater.inflate(R.layout.payment_form, container);
    mContext = view.getContext();
    cashBtn = (Button) view.findViewById(R.id.payment_option_cash);
    payBtn = (Button) view.findViewById(R.id.payment_form_pay_button);
    cancelBtn = (Button) view.findViewById(R.id.payment_form_cancel_button);
    cardBtn = (Button) view.findViewById(R.id.payment_option_card);
    deliveryBtn = (Button) view.findViewById(R.id.is_delivery);
    togoBtn = (Button) view.findViewById(R.id.is_togo);
    tipBtn = (Button) view.findViewById(R.id.tip_btn);
    valueTxt = (EditText) view.findViewById(R.id.payment_form_text);
    changeTxt = (TextView) view.findViewById(R.id.payment_form_change);
    discountTxt = (EditText) view.findViewById(R.id.payment_form_discount);
    cashBtn.setBackgroundColor(Color.GRAY);
    cashBtn.setEnabled(false);
    frag = this;
    
    /*valueTxt.setOnFocusChangeListener(new OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if(!hasFocus)
			{
				String qty = valueTxt.getText().toString();
				double val = 0.0;
				if(!qty.isEmpty() && qty != null)
				{
					val = Double.valueOf(changeTxt.getText().toString());
					
				}
				else
				{
					changeTxt.setText("0");
				}
				if(val < price)
				
				{
					payBtn.setEnabled(false);
				}
				else
				{
					payBtn.setEnabled(true);
					double change = val - price;
					changeTxt.setText(String.valueOf(change));
				}
				
			}
			
		}
	});*/
    
    cashBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
				v.setBackgroundColor(Color.GRAY);
				valueTxt.setEnabled(true);
				valueTxt.setText(String.valueOf(price));
				method = "cash";
				v.setEnabled(false);
				cardBtn.setEnabled(true);
				cardBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_button));
			
		}
	});
    cardBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			
				v.setBackgroundColor(Color.GRAY);
				valueTxt.setEnabled(false);
				valueTxt.setText(String.valueOf(price));
				method = "card";
				v.setEnabled(false);
				cashBtn.setEnabled(true);
				cashBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_button));
			
			
		}
		
		
	});
deliveryBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(isDelivery == 1){
				v.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_button));
				isDelivery = 0;
			}
			else{
				v.setBackgroundColor(Color.GRAY);
				isDelivery = 1;
			}
		}
	});
togoBtn.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		if(isToGo == 1){
			v.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_button));
			isToGo = 0;
		}
		else{
			v.setBackgroundColor(Color.GRAY);
			isToGo = 1;
		}
	}
});
tipBtn.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		if(tip == 1){
			v.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_button));
			tip = 0;
		}
		else{
			v.setBackgroundColor(Color.GRAY);
			tip = 1;
		}
	}
});
    
    payBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			String qty = valueTxt.getText().toString();
			double val = 0.0;
			if(!qty.isEmpty() && qty != null)
			{
				val = Double.valueOf(valueTxt.getText().toString());
				
			}
			String disc = discountTxt.getText().toString();
			double discount = 0.0;
			if(!disc.isEmpty() && disc != null)
			{
				val = Double.valueOf(discountTxt.getText().toString());
				
			}
			
			onPayListener.OnPayClicked(method,isDelivery,isToGo, val,tip,discount);
			Toast.makeText(mContext, "Cambio: "+String.valueOf(val - price), Toast.LENGTH_LONG).show();
			frag.dismiss();
			
		}
		
	});
    
    cancelBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			frag.dismiss();
		}
	});
    //Toast.makeText(mContext, "Cantidad de Articulos a vender: "+items.size(), Toast.LENGTH_LONG).show(); 
    Iterator<Entry<Registrable, Integer>> it = items.entrySet().iterator();
    price = 0.0;
     while(it.hasNext())
     {
    	 Map.Entry<Registrable,Integer> registrablePair = (Map.Entry<Registrable,Integer>)it.next();
    	 price += (registrablePair.getKey().price * (1 + registrablePair.getKey().tax)) * registrablePair.getValue();
    	
     }
     valueTxt.setText(String.valueOf(price));
     getDialog().setTitle("Precio de Pedido: "+price);
     priceVal = price;
     
    

     return view;
 }
}