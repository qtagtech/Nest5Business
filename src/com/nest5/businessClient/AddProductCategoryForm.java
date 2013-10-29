package com.nest5.businessClient;



import android.app.Activity;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class AddProductCategoryForm extends DialogFragment {
	
	public interface OnAddProductCategoryListener {
        public void OnAddProductCategorySave(String name);
        
    }
	
private OnAddProductCategoryListener onAddProductCategoryListener;
private DialogFragment frag;

private TextView txtName;


@Override
public void onAttach(Activity activity){
	super.onAttach(activity);
	try
	{
		onAddProductCategoryListener = (OnAddProductCategoryListener) activity;
	}
	catch(ClassCastException e){
		throw new ClassCastException(activity.toString() + " must implement OnAddProducttCategoryListener");
	}
}


 public AddProductCategoryForm() {
     // Empty constructor required for DialogFragment
 }
 
 

 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
	 frag = this;
     View view = inflater.inflate(R.layout.add_product_category, container);
     
     Button saveBtn = (Button) view.findViewById(R.id.product_category_add_button);
     Button cancelBtn = (Button) view.findViewById(R.id.product_category_cancel_button);
     txtName = (TextView) view.findViewById(R.id.product_category_name_edit);
  // Create an ArrayAdapter using the string array and a default spinner layout
    /* ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
             R.array.planets_array, android.R.layout.simple_spinner_item);*/
     
     getDialog().setTitle("Agregar Nueva Categoría de Producto");
     
     saveBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String ingName = txtName.getText().toString();
			
			if(ingName.isEmpty())
			{
				txtName.findFocus();
				Toast.makeText(getActivity(), "Es necesario indicar un nombre de Categoría", Toast.LENGTH_SHORT).show();
			}
			else
			{
				onAddProductCategoryListener.OnAddProductCategorySave(ingName);
				frag.dismiss();
			}
			
			
		}
	});
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