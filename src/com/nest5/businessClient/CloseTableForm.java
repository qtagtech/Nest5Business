package com.nest5.businessClient;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;



public class CloseTableForm extends DialogFragment {
	
	public interface OnSelectTableActionListener {
        public void OnTableActionSelect(int Type);
        
    }
	
private OnSelectTableActionListener onSelectTableActionListener;
private DialogFragment frag;
private Context mContext;
public static final int CANCEL_ORDER = 1;
public static final int PRINT_INVOICE = 2;

@Override
public void onAttach(Activity activity){
	super.onAttach(activity);
	try
	{
		onSelectTableActionListener = (OnSelectTableActionListener) activity;
	}
	catch(ClassCastException e){
		throw new ClassCastException(activity.toString() + " must implement OnSelectTableActionListener");
	}
}


 public CloseTableForm() {
     // Empty constructor required for DialogFragment
 }
 


 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
	 frag = this;
     View view = inflater.inflate(R.layout.print_invoice, container);
     mContext = view.getContext();
     Button orderButton = (Button) view.findViewById(R.id.order_button);
     orderButton.setText(mContext.getResources().getString(R.string.table_close));
     Button invoiceButton = (Button) view.findViewById(R.id.invoice_button);
     invoiceButton.setText(mContext.getResources().getString(R.string.table_cancel));
     getDialog().setTitle("¿Qué deseas hacer?");
     orderButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			onSelectTableActionListener.OnTableActionSelect(PRINT_INVOICE);
			frag.dismiss();
		}
	});
     invoiceButton.setOnClickListener(new OnClickListener() {
 		
 		@Override
 		public void onClick(View v) {
 			onSelectTableActionListener.OnTableActionSelect(CANCEL_ORDER);
 			frag.dismiss();
 		}
 	});
     
     return view;
 }
}