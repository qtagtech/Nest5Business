package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.nest5.businessClient.HomeObjectFragment.OnHomeObjectFragmentCreatedListener;

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


public class OrderForm extends DialogFragment {
	
	
	public interface OnOrderListener {
        public void OnOrderClicked(int isDelivery, int isTogo,String note);
        
    }
	public interface OnOrderFomrFragmentCreatedListener {
	    public void OnOrderFomrFragmentCreatedListener(View v);
	    
	}

private OnOrderFomrFragmentCreatedListener onOrderFomrFragmentCreatedListener;	
private Context mContext;
private LinkedHashMap<Registrable, Integer> items;
private Button deliveryBtn;
private Button togoBtn;
private Button payBtn;
private Button cancelBtn;
private EditText valueTxt;
private Double price;
private OnOrderListener onOrderListener;
private OrderForm frag;
private Integer isDelivery = 0;
private Integer isToGo = 0;
private static View rootView;





@Override
public void onAttach(Activity activity){
	super.onAttach(activity);
	try
	{
		onOrderListener = (OnOrderListener) activity;
	}
	catch(ClassCastException e){
		throw new ClassCastException(activity.toString() + " must implement OnPayListener");
	}
	try
	{
		onOrderFomrFragmentCreatedListener = (OnOrderFomrFragmentCreatedListener) activity;
	}
	catch(ClassCastException e){
		throw new ClassCastException(activity.toString() + " must implement OnOrderFomrFragmentCreatedListener");
	}
}


 public OrderForm() {
     // Empty constructor required for DialogFragment
 }
 
 public OrderForm(LinkedHashMap<Registrable, Integer> items) {
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
     View view = inflater.inflate(R.layout.order_form, container);  //poner spinner
     rootView = view;
    mContext = view.getContext();
    payBtn = (Button) view.findViewById(R.id.payment_form_pay_button);
    cancelBtn = (Button) view.findViewById(R.id.payment_form_cancel_button);
    deliveryBtn = (Button) view.findViewById(R.id.is_delivery);
    togoBtn = (Button) view.findViewById(R.id.is_togo);
    valueTxt = (EditText) view.findViewById(R.id.order_form_text);
    frag = this;
    
    

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

    
    payBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			String note = valueTxt.getText().toString();
			onOrderListener.OnOrderClicked(isDelivery,isToGo,note);
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
     getDialog().setTitle("Orden de Pedido - Comanda");
     
    

     return view;
 }
 
 @Override 
 public void onResume() {
		super.onResume();
		onOrderFomrFragmentCreatedListener.OnOrderFomrFragmentCreatedListener(rootView);
	}
}