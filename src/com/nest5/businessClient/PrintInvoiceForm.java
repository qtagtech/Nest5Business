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



public class PrintInvoiceForm extends DialogFragment {
	
	public interface OnPrintSelectListener {
        public void OnPrintSelect(int Type);
        
    }
	
private OnPrintSelectListener onPrintSelectListener;
private DialogFragment frag;
private Context mContext;
public int PRINT_ORDER = 1;
public int PRINT_INVOICE = 2;

@Override
public void onAttach(Activity activity){
	super.onAttach(activity);
	try
	{
		onPrintSelectListener = (OnPrintSelectListener) activity;
	}
	catch(ClassCastException e){
		throw new ClassCastException(activity.toString() + " must implement OnPrintSelectListener");
	}
}


 public PrintInvoiceForm() {
     // Empty constructor required for DialogFragment
 }
 


 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
	 frag = this;
     View view = inflater.inflate(R.layout.print_invoice, container);
     mContext = view.getContext();
     Button orderButton = (Button) view.findViewById(R.id.order_button);
     Button invoiceButton = (Button) view.findViewById(R.id.invoice_button);
     getDialog().setTitle("¿Qué deseas imprimir?");
     orderButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			onPrintSelectListener.OnPrintSelect(PRINT_ORDER);
			frag.dismiss();
		}
	});
     invoiceButton.setOnClickListener(new OnClickListener() {
 		
 		@Override
 		public void onClick(View v) {
 			onPrintSelectListener.OnPrintSelect(PRINT_INVOICE);
 			frag.dismiss();
 		}
 	});
     
     return view;
 }
}