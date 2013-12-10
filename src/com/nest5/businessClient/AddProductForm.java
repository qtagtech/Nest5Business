package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class AddProductForm extends DialogFragment {
	
	public interface OnAddProductListener {
        public void OnAddProductSave(String name, ProductCategory categoryId, double cost, double price, Tax tax);
        
    }
	
private OnAddProductListener onAddProductListener;
private DialogFragment frag;
private List<ProductCategory> categories;
private List<Tax> taxes;
private TextView txtName;
private Spinner spinner;
private Spinner taxSpinner;
private EditText txtCost;
private EditText txtPrice;
private Context mContext;
private CheckBox chkAutomaticCost;
private CheckBox chkTaxIncluded;

@Override
public void onAttach(Activity activity){
	super.onAttach(activity);
	try
	{
		onAddProductListener = (OnAddProductListener) activity;
	}
	catch(ClassCastException e){
		throw new ClassCastException(activity.toString() + " must implement OnAddProductListener");
	}
}


 public AddProductForm() {
     // Empty constructor required for DialogFragment
 }
 
 public AddProductForm(List<ProductCategory> productCategories,List<Tax> taxes)
 {
	
	 categories = productCategories;
	 this.taxes = taxes;
	 
 }


@Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
	 frag = this;
     View view = inflater.inflate(R.layout.add_product, container);
     spinner = (Spinner) view.findViewById(R.id.product_category_spinner);
     taxSpinner = (Spinner) view.findViewById(R.id.product_tax_spinner);
     txtCost = (EditText) view.findViewById(R.id.product_cost_edit);
     txtPrice = (EditText) view.findViewById(R.id.product_price_edit);
     chkAutomaticCost = (CheckBox) view.findViewById(R.id.product_automatic_cost);
     chkTaxIncluded = (CheckBox) view.findViewById(R.id.product_tax_included);
     TextView desc = (TextView) view.findViewById(R.id.product_automaticost_label);
     TextView taxDesc = (TextView) view.findViewById(R.id.product_tax_label);
     
     //si la lista de categorias esta vacia, limpar el layout y poner un botn de ir a crear o de cancelar
     
     
     Button saveBtn = (Button) view.findViewById(R.id.product_add_button);
     Button cancelBtn = (Button) view.findViewById(R.id.product_cancel_button);
     txtName = (TextView) view.findViewById(R.id.product_name_edit);
     if(categories.size() == 0)
     {
    	spinner.setVisibility(View.GONE);
    	taxSpinner.setVisibility(View.GONE);
    	chkTaxIncluded.setVisibility(View.GONE);
    	txtName.setVisibility(View.GONE);
    	txtPrice.setVisibility(View.GONE);
    	txtCost.setVisibility(View.GONE);
    	chkAutomaticCost.setVisibility(View.GONE);
    	taxDesc.setVisibility(View.GONE);
    	desc.setVisibility(View.GONE);
    	saveBtn.setText("Crear Nueva Categoría");
    	TextView title = (TextView) view.findViewById(R.id.product_category_label);
    	title.setText("Para crear un Producto, primero debes tener categorías configuradas.\nHaz clic en crear ahora.");
    	saveBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
					onAddProductListener.OnAddProductSave(null,null,0.0,0.0,null);
					frag.dismiss();
				
				
				
			}
		});
    	getDialog().setTitle("Agregar Nuevo Producto");
    	
     }
     else
     {
    	// Create an ArrayAdapter using the string array and a default spinner layout
    	    /* ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
    	             R.array.planets_array, android.R.layout.simple_spinner_item);*/
    	     
    	    	 String[] lista = new String[categories.size()];
    	         Iterator<ProductCategory> iterator = categories.iterator();
    	         int i = 0;
    	         while(iterator.hasNext())
    	         {
    	         	//Log.i("HOLAAA",iterator.next().getName());
    	         	lista[i] = iterator.next().getName();
    	         	i++;
    	         }
    	         ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_dropdown_item,lista);
    	         // Apply the adapter to the spinner
    	         spinner.setAdapter(adapter); 
    	         String[] lista2 = new String[taxes.size()];
    	         Iterator<Tax> iteratorTax = taxes.iterator();
    	         int j = 0;
    	         while(iteratorTax.hasNext())
    	         {
    	         	//Log.i("HOLAAA",iterator.next().getName());
    	         	lista2[j] = iteratorTax.next().getName();
    	         	j++;
    	         }
    	         ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_dropdown_item,lista2);
    	         // Apply the adapter to the spinner
    	         taxSpinner.setAdapter(adapter2); 
    	     
    	     try{
    	    	 getDialog().setTitle("Agregar Nuevo Producto"); 
    	     }
    	     catch(Exception ex){
    	    	 ex.printStackTrace();
    	     }
    	     
    	     
    	     saveBtn.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				String ingName = txtName.getText().toString();
    				int position = spinner.getSelectedItemPosition();
    				ProductCategory category = categories.get(position);
    				int position2 = taxSpinner.getSelectedItemPosition();
    				Tax tax = taxes.get(position2);
    				String cost = txtCost.getText().toString();
    				double costVal = 0.0;
    				if(cost != null && !cost.isEmpty()) {

    					costVal = Double.parseDouble(txtCost.getText().toString());

    				}
    				String price = txtPrice.getText().toString();
    				double priceVal = 0.0;
    				if(price != null && !price.isEmpty()) {

    					priceVal = Double.parseDouble(txtPrice.getText().toString());
    					if(chkTaxIncluded.isChecked())
    					{
    						priceVal = priceVal / (1 + tax.getPercentage());
    					}

    				}
    				if(ingName.isEmpty())
    				{
    					txtName.findFocus();
    					Toast.makeText(getActivity(), "Es necesario indicar un nombre de Producto", Toast.LENGTH_SHORT).show();
    				}
    				else if(costVal == 0.0)
    				{
    					txtCost.findFocus();
    					Toast.makeText(getActivity(), "Es necesario indicar un costo de Producto", Toast.LENGTH_SHORT).show();
    					
    				}
    				else if(priceVal == 0.0){
    					txtPrice.findFocus();
    					Toast.makeText(getActivity(), "Es necesario indicar un precio de Producto", Toast.LENGTH_SHORT).show();
    				}
    				else{
    					Log.i("ACAAAAAAAAA", priceVal+" "+costVal+" "+tax.getPercentage());
    					onAddProductListener.OnAddProductSave(ingName, category,costVal,priceVal,tax);
    					frag.dismiss();
    				}
    				
    				
    			}
    		});
    	      
     }
     
     cancelBtn.setOnClickListener(new OnClickListener() {
	 		
	 		@Override
	 		public void onClick(View v) {
	 			frag.dismiss();
	 			
	 		}
	 	});
     
     /*list.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			onAddItemSelectedListener.OnAddItemSelected(position);
			frag.dismiss();
			
		}
	});*/
     
    
     //se pone el listener de la lista aca y se pasa al parent activity 

     return view;
 }
}