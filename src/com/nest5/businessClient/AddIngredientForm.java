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
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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


public class AddIngredientForm extends DialogFragment {
	
	public interface OnAddIngredientListener {
        public void OnAddIngredientSave(String name, IngredientCategory categoryId,Tax tax,Unit unit,double costPerUnit, double pricePerUnit, double priceMeasure,Boolean editinng,double qty);
        
    }
	
private OnAddIngredientListener onAddIngredientListener;
private DialogFragment frag;
private List<IngredientCategory> categories;
private List<Tax> taxes;
private List<Unit> units;
private Ingredient ingredient = null;
private TextView txtName;
private Spinner spinner;
private Spinner taxSpinner;
private Spinner unitSpinner;
private Spinner priceUnitSpinner;
private Spinner costUnitSpinner;
private EditText txtCost;
private EditText txtPrice;
private EditText txtCostQty;
private EditText txtPriceQty;
private LinkedHashMap<String, Double> currentMultipliers;
private Context mContext;
private CheckBox chkTaxIncluded;
private Boolean editing = false;






@Override
public void onAttach(Activity activity){
	super.onAttach(activity);
	try
	{
		onAddIngredientListener = (OnAddIngredientListener) activity;
	}
	catch(ClassCastException e){
		throw new ClassCastException(activity.toString() + " must implement OnAddIngredientListener");
	}
}


 public AddIngredientForm() {
     // Empty constructor required for DialogFragment
 }
 
 public AddIngredientForm(List<IngredientCategory> ingredientCategories,List<Tax> taxes,List<Unit> units)
 {
	
	 categories = ingredientCategories;
	 this.taxes = taxes;
	 this.units = units;
	 
 }
 
 public AddIngredientForm(List<IngredientCategory> ingredientCategories,List<Tax> taxes,List<Unit> units,Ingredient ingredient)
 {
	
	 categories = ingredientCategories;
	 this.taxes = taxes;
	 this.units = units;
	 this.ingredient = ingredient;
	 this.editing = true;
	 
 }

 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
	 frag = this;
     View view = inflater.inflate(R.layout.add_ingredient, container);
     mContext = view.getContext();
     spinner = (Spinner) view.findViewById(R.id.ingredient_category_spinner);
     Button saveBtn = (Button) view.findViewById(R.id.ingredient_add_button);
     Button cancelBtn = (Button) view.findViewById(R.id.ingredient_cancel_button);
     txtName = (TextView) view.findViewById(R.id.ingredient_name_edit);
     taxSpinner = (Spinner) view.findViewById(R.id.ingredient_tax_spinner);
     unitSpinner = (Spinner) view.findViewById(R.id.ingredient_unit_spinner);
     costUnitSpinner = (Spinner) view.findViewById(R.id.ingredient_cost_unit_spinner);
     priceUnitSpinner = (Spinner) view.findViewById(R.id.ingredient_price_unit_spinner);
     
     txtCost = (EditText) view.findViewById(R.id.ingredient_cost_edit);
     txtPrice = (EditText) view.findViewById(R.id.ingredient_price_edit);
     txtCostQty = (EditText) view.findViewById(R.id.ingredient_cost_qty);
     txtPriceQty = (EditText) view.findViewById(R.id.ingredient_price_qty);
     
     chkTaxIncluded = (CheckBox) view.findViewById(R.id.ingredient_tax_included);
     
     TextView taxDesc = (TextView) view.findViewById(R.id.ingredient_tax_label);
     if(ingredient != null)
     {
    	 txtName.setText(ingredient.getName());
    	 txtCost.setText(String.valueOf(ingredient.getCostPerUnit()));
    	 txtPrice.setText(String.valueOf(ingredient.getPrice()));
    	 txtCostQty.setText("1");
    	 txtPriceQty.setText("1");
     }
     currentMultipliers = new LinkedHashMap<String, Double>();
  // Create an ArrayAdapter using the string array and a default spinner layout
    /* ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
             R.array.planets_array, android.R.layout.simple_spinner_item);*/
     String[] lista = new String[categories.size()];
     Iterator<IngredientCategory> iterator = categories.iterator();
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
     
     String[] lista3 = new String[units.size()];
     Iterator<Unit> iteratorUnit = units.iterator();
     int k = 0;
     while(iteratorUnit.hasNext())
     {
     	//Log.i("HOLAAA",iterator.next().getName());
     	lista3[k] = iteratorUnit.next().getInitials();
     	k++;
     }
     ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_dropdown_item,lista3);
     // Apply the adapter to the spinner
     unitSpinner.setAdapter(adapter3); 
     
     
     
     unitSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			
			Unit curUnit = units.get(position);
			currentMultipliers = curUnit.getMultipliers();
			String[] list = new String[curUnit.getMultipliers().size()];
		    Iterator<Entry<String, Double>> it = curUnit.getMultipliers().entrySet().iterator();
		    int i = 0;
		     while(it.hasNext())
		     {
		    	 Map.Entry<String,Double> pairs = (Map.Entry<String,Double>)it.next();
		     	//Log.i("HOLAAA",iterator.next().getName());
		     	list[i] = pairs.getKey();
		     	
		     	i++;
		     }
		     ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_dropdown_item,list);
		     // Apply the adapter to the spinner
		     costUnitSpinner.setAdapter(adapter); 
		     priceUnitSpinner.setAdapter(adapter); 
			
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			
			
		}
	});
     
     getDialog().setTitle("Agregar Nuevo ingrediente");
     
     saveBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String ingName = txtName.getText().toString();
			int position = spinner.getSelectedItemPosition();
			IngredientCategory category = categories.get(position);
			int positionUnit = unitSpinner.getSelectedItemPosition();
			Unit unit = units.get(positionUnit);
			Double costMultiplier = currentMultipliers.get(costUnitSpinner.getSelectedItem());
			double priceMultiplier = currentMultipliers.get(priceUnitSpinner.getSelectedItem());
			int position2 = taxSpinner.getSelectedItemPosition();
			Tax tax = taxes.get(position2);
			String cost = txtCost.getText().toString();
			String costQty = txtCostQty.getText().toString();
			double costVal = 0.0;
			double costQtyVal = 0.0;
			double costPerUnit = 0.0;
			Log.d("GUARDANDO_INGEDIENTE","cost: "+cost+" costQty: "+costQty);
			if(cost != null && !cost.isEmpty() && costQty != null && !costQty.isEmpty()) {
				Log.d("GUARDANDO_INGEDIENTE","Entra a calcular costo");
				costVal = Double.valueOf(cost);
				costQtyVal = Double.valueOf(costQty);
				costPerUnit = costVal  / ( costQtyVal * costMultiplier );
				

			}
			Log.d("GUARDANDO_INGEDIENTE","costVal: "+String.valueOf(costVal)+" costQtyVal: "+String.valueOf(costQtyVal));
			Log.d("GUARDANDO_INGEDIENTE","costo por unidad: "+costPerUnit);
			String price = txtPrice.getText().toString();
			String priceQty = txtPriceQty.getText().toString();
			double priceVal = 0.0;
			double priceQtyVal = 0.0;
			double pricePerUnit = 0.0;
			Log.d("GUARDANDO_INGEDIENTE","price: "+price+" priceQty: "+priceQty);
			if(price != null && !price.isEmpty() && priceQty != null && !priceQty.isEmpty()) {
				Log.d("GUARDANDO_INGEDIENTE","Entra a calcular precio");
				priceVal = Double.valueOf(price);
				if(chkTaxIncluded.isChecked())
				{
					priceVal = priceVal / (1 + tax.getPercentage());
				}
				priceQtyVal = Double.valueOf(priceQty);
				if(priceQtyVal == 0){
					pricePerUnit = 0;
				}else{
					pricePerUnit = priceVal  / ( priceQtyVal * priceMultiplier );
				}
				
				
				

			}
			
			Log.d("GUARDANDO_INGEDIENTE","priceVal: "+String.valueOf(priceVal)+" priceQtyVal: "+String.valueOf(priceQtyVal));
			Log.d("GUARDANDO_INGEDIENTE","Precio por Unidad: "+pricePerUnit);
			if(ingName.isEmpty())
			{
				txtName.findFocus();
				Toast.makeText(getActivity(), "Es necesario indicar un nombre de Ingrediente", Toast.LENGTH_SHORT).show();
			}
			else if(costVal == 0.0)
			{
				txtCost.findFocus();
				Toast.makeText(getActivity(), "Es necesario indicar un Costo de Ingrediente", Toast.LENGTH_SHORT).show();
				
			}
			else if((price == null || price.isEmpty()) && (priceQty != null || !priceQty.isEmpty())){
				
				txtPrice.findFocus();
				Toast.makeText(getActivity(), "Es necesario indicar un precio de Ingrediente si se indica un valor de porción", Toast.LENGTH_SHORT).show();
			}//Un ingrediente si perimite tener precio en 0.0, siempre y cuando la cantidad de venta este en cero tambien, eso siginifica que no se vende por separado.
			else
			{
				onAddIngredientListener.OnAddIngredientSave(ingName,category,tax,unit,costPerUnit,pricePerUnit,priceQtyVal,editing,costQtyVal * costMultiplier);
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