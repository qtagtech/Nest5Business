package com.nest5.businessClient;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;


public class SelectAddItem extends DialogFragment {
	
	public interface OnAddItemSelectedListener {
        public void OnAddItemSelected(int item);
        
    }
	
private OnAddItemSelectedListener onAddItemSelectedListener;
private DialogFragment frag;

@Override
public void onAttach(Activity activity){
	super.onAttach(activity);
	try
	{
		onAddItemSelectedListener = (OnAddItemSelectedListener) activity;
	}
	catch(ClassCastException e){
		throw new ClassCastException(activity.toString() + " must implement OnAddItemSelectedItem");
	}
}


 public SelectAddItem() {
     // Empty constructor required for DialogFragment
 }

 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
	 frag = this;
     View view = inflater.inflate(R.layout.add_menu, container);
     ListView list = (ListView) view.findViewById(R.id.add_item_list);
     String[] options = {"Agregar Ingrediente","Agregar Producto","Agregar Combo","Agregar Categoría de Ingrediente","Agregar Categoría de Producto"};
     ImageAndTextAdapter adapter = new ImageAndTextAdapter(view.getContext(),options, list, inflater);
     list.setAdapter(adapter);
     getDialog().setTitle("Agregar Nuevo Item");
     
     list.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			onAddItemSelectedListener.OnAddItemSelected(position);
			frag.dismiss();
			
		}
	});
     
    
     //se pone el listener de la lista aca y se pasa al parent activity 

     return view;
 }
}