package com.nest5.businessClient;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class CreateComboView extends DialogFragment {
	
	public interface OnCreateComboListener {
        public void OnComboCreated(Boolean status,LinkedHashMap<Ingredient,Double> ingredents,LinkedHashMap<Product,Double> products,String name,Double cost,Double price,Tax tax); //manda datos de combo para crearlo
        
    }
	
private OnCreateComboListener onCreateComboListener;
private DialogFragment frag;
private List<Ingredient> ingredients;
private List<Product> products;
private List<Tax> taxes;
private Combo combo;
private List<Ingredient> selectedIngredients;
private List<Product> selectedProducts;

private LinkedHashMap<Ingredient,Double> ingredient_qties;
private LinkedHashMap<Product,Double> product_qties;
private Context mContext;

private Button saveBtn;
private Button cancelBtn;
private GridView itemsView;
private ScrollView shelves;
private MyHorizontalLayout currentRow;
private ListView ingredientsList;
private ListView productsList;
private LinearLayout ll;
private EditText comboPrice;
private EditText comboCost;
private EditText comboName;
private Spinner taxSpinner;
private CheckBox taxChk;

private IngredientAdapter listAdapterIngredients;
private ProductAdapter listAdapterProducts;

private int curPosition;
private static int TYPE_PRODUCT = 1;
private static int TYPE_INGREDIENT = 2;
private int curType;

private LayoutInflater inflater;




@Override
public void onAttach(Activity activity){
	super.onAttach(activity);
	try
	{
		onCreateComboListener = (OnCreateComboListener) activity;
	}
	catch(ClassCastException e){
		throw new ClassCastException(activity.toString() + " must implement OnCreateComboListener");
	}
}


 public CreateComboView() {
     // Empty constructor required for DialogFragment
 }
 
 public CreateComboView(List<Ingredient> ingredients,List<Product> products,List<Tax> taxes)
 {
	
	 this.ingredients = ingredients;
	 this.products = products;
	 this.taxes = taxes;
	 //this.combo = 
	 
 }


@Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
	 frag = this;
	 this.inflater = inflater;
     View view = inflater.inflate(R.layout.create_combo, container);
 	getDialog().setTitle("Crear un Nuevo Combo");
 	mContext = view.getContext();
 	ll =(LinearLayout) view.findViewById(R.id.ingredient_categories_buttons);
 	saveBtn = (Button) view.findViewById(R.id.save_recepie);
 	cancelBtn = (Button) view.findViewById(R.id.cancel_recepie);
 	itemsView = (GridView) view.findViewById(R.id.gridview_ingredients);
 	shelves = (ScrollView) view.findViewById(R.id.shelves);
 	ingredientsList = (ListView) view.findViewById(R.id.ingredient_add_list);
 	productsList = (ListView) view.findViewById(R.id.product_add_list);		
 	comboCost = (EditText) view.findViewById(R.id.combo_cost_edit);
 	comboPrice = (EditText) view.findViewById(R.id.combo_price_edit);
 	comboName = (EditText) view.findViewById(R.id.combo_name_edit);
 	taxChk = (CheckBox) view.findViewById(R.id.combo_tax_included);
 	taxSpinner = (Spinner) view.findViewById(R.id.combo_tax_spinner);
 	//ImageAdapterCreateProduct gridAdapter = new ImageAdapterCreateProduct(mContext, ingredients, inflater,touchListener);
 	ImageAdapterCreateCombo gridAdapter = new ImageAdapterCreateCombo(mContext, products, inflater,touchListener);
 	itemsView.setAdapter(gridAdapter);
	selectedIngredients = new ArrayList<Ingredient>();
	selectedProducts = new ArrayList<Product>();
	curPosition = 0;
	curType = TYPE_PRODUCT;
	shelves.setOnDragListener(dragListener);
	String[] lista2 = new String[taxes.size()];
    Iterator<Tax> iteratorTax = taxes.iterator();
    int j = 0;
    while(iteratorTax.hasNext())
    {
    	
    	lista2[j] = iteratorTax.next().getName();
    	j++;
    }
    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_dropdown_item,lista2);
    // Apply the adapter to the spinner
    taxSpinner.setAdapter(adapter2); 
	
	
	listAdapterProducts = new ProductAdapter(mContext, selectedProducts, inflater);
	productsList.setAdapter(listAdapterProducts);
	product_qties = new LinkedHashMap<Product,Double>();
	Activity activity = getActivity();
	listAdapterIngredients = new IngredientAdapter(mContext, selectedIngredients, inflater,activity);
	ingredientsList.setAdapter(listAdapterIngredients);
	ingredient_qties = new LinkedHashMap<Ingredient,Double>();
	//Log.d("ACAAAAAAA"," "+ingredients.size());
	
	
	//Poner botones de ingredientes o Productos
	
	String values[] = {"Productos","Ingredientes"};
	 
	 
	 for(int i = 0; i < values.length; i++)
	 {
		 
		 	Button btnTag = (Button) inflater.inflate(R.layout.template_button, null);
	        //btnTag.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		 	//btnTag.setBackgroundColor(R.drawable.blue_button);
	        btnTag.setText((values[i]));
	        btnTag.setId(i);
	        btnTag.setOnClickListener(typeButtonClickListener);
	        ll.addView(btnTag); 
	        
	 }
 	
 	
 	
 	
 	cancelBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			frag.dismiss();
			
		}
	});
 	
saveBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			String price = comboPrice.getText().toString();
			Double priceVal = 0.0;
			if(price != null && !price.isEmpty())
			{
				priceVal = Double.valueOf(price);
			}
			String cost = comboCost.getText().toString();
			Double costVal = 0.0;
			if(cost != null && !cost.isEmpty())
			{
				costVal = Double.valueOf(cost);
			}
			
			Tax tax = taxes.get(taxSpinner.getSelectedItemPosition());
			
			String name = comboName.getText().toString();
			if(name == null || name.isEmpty())
			{
				Toast.makeText(mContext, "Debes Poner un Nombre al Combo", Toast.LENGTH_SHORT).show();
				comboName.findFocus();
				return;
			}
			else if(priceVal == 0)
			{
				Toast.makeText(mContext, "Debes Poner un Precio al Combo", Toast.LENGTH_SHORT).show();
				comboPrice.findFocus();
				return;
			}
			else if(costVal == 0)
			{
				Toast.makeText(mContext, "Debes Poner un Costo al Combo", Toast.LENGTH_SHORT).show();
				comboCost.findFocus();
				return;
			}
			HashMap<String, Double> multipliers = listAdapterIngredients.getMultipliers();
			HashMap<String, Ingredient> ingredients = listAdapterIngredients.getIngredients();
			LinkedHashMap<Ingredient,Double> values = new LinkedHashMap<Ingredient, Double>();
			for(int i = 0; i < ingredientsList.getChildCount(); i++){
				ViewGroup view = (ViewGroup) ingredientsList.getChildAt(i);
				TextView title = null;
		    	EditText quan = null;
		    	Spinner units = null;
		        try{
		        	int count =  view.getChildCount();
		        	for (int j = 0; j < count; j++) {
		        	    View vi = view.getChildAt(j);
		        	    
		        	    if(vi instanceof Spinner){
		        	    	units = (Spinner) vi;
		        	    	
		        	    }else{
		        	    	if(vi instanceof EditText)
		        	    		{
		        	    			quan = (EditText) vi;
		        	    		}else{
		        	    			title = (TextView) vi;
		        	    		}
		        	    	}
		        	    }
		        	
		        	Double unidad = multipliers.get(units.getSelectedItem().toString());
		        	Double total = Double.parseDouble(quan.getText().toString()) * unidad;
		        	if(total != 0){
		        		values.put(ingredients.get(title.getText().toString()),total);
		        	}
		        	//Log.d("UNIDADES","Se pusieron "+total+" de "+title.getText().toString());

		        }catch(Exception e){
		        	e.printStackTrace();
		        }

				
			}
			Boolean success = true;
			HashMap<String, Product> productos = listAdapterProducts.getProducts();
			LinkedHashMap<Product,Double> values_product = new LinkedHashMap<Product, Double>();
			for(int i = 0; i < productsList.getChildCount(); i++){
				ViewGroup view = (ViewGroup) productsList.getChildAt(i);
				//Log.d("UNIDADES","productos; "+view.getClass().toString());
				TextView title = null;
		    	EditText quan = null;
		    	
		        try{
		        	int count =  view.getChildCount();
		        	for (int j = 0; j < count; j++) {
		        	    View vi = view.getChildAt(j);
		        	    
		        	    if(vi instanceof EditText){
		        	    	quan = (EditText) vi;
		        	    	
		        	    }else{
		        	    	title = (TextView) vi;
		        	    }
		        	}
		        	//title =  (TextView) view.findViewById(R.id.product_row_title);
		        	//quan = (EditText) view.findViewById(R.id.product_qty_text);
		        	Double total = Double.parseDouble(quan.getText().toString());
		        	
		        	if(total != 0){
		        		values_product.put(productos.get(title.getText().toString()),total);
		        	}
		        	Log.d("UNIDADES","Se pusieron "+total+" de "+title.getText().toString());

		        }catch(Exception e){
		        	success = false;
		        	e.printStackTrace();
		        }

				
			}
			
			onCreateComboListener.OnComboCreated(success,values,values_product,name,costVal,priceVal,tax);
			
			/*Iterator<Entry<Ingredient, Double>> it = qties.entrySet().iterator();
		    
		    
		     while(it.hasNext())
		     {
		    	 Map.Entry<Ingredient,Double> ingrediente = (Map.Entry<Ingredient,Double>)it.next();
		    	 Log.d("INGREDIENTES","INGREDIENTE: "+ingrediente.getKey().getName()+" "+ingrediente.getValue());	
		     }*/
			
			frag.dismiss();
			
		}
	});

     return view;
 }


private OnClickListener typeButtonClickListener = new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case 0: 
			ImageAdapterCreateCombo gridAdapter = new ImageAdapterCreateCombo(mContext, products, inflater,touchListener);
		 	itemsView.setAdapter(gridAdapter);
		 	curType = TYPE_PRODUCT;
			break;
		case 1:
			ImageAdapterCreateProduct gridAdapter2 = new ImageAdapterCreateProduct(mContext, ingredients, inflater,touchListener);
		 	itemsView.setAdapter(gridAdapter2);
		 	curType = TYPE_INGREDIENT;
			break;
		}
		
	}
};


private OnTouchListener touchListener = new OnTouchListener() {
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
		      ClipData data = ClipData.newPlainText("", "");
		      DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
		      v.startDrag(data, shadowBuilder, v, 0);
		      v.setVisibility(View.INVISIBLE);
		      //Toast.makeText(mContext, String.valueOf(v.getId()), Toast.LENGTH_LONG).show();
		      return true;
		    } else {
		    return false;
		    }
	}
}; 

private OnDragListener dragListener = new OnDragListener() {
	
	@Override
	public boolean onDrag(View v, DragEvent event) {
		Log.d("ACA ACA",String.valueOf(curType));
		int action = event.getAction();
	    switch (event.getAction()) {
	    case DragEvent.ACTION_DRAG_STARTED:
	    // Do nothing
	      break;
	    case DragEvent.ACTION_DRAG_ENTERED:
	      v.setAlpha((float) 0.5);
	      break;
	    case DragEvent.ACTION_DRAG_EXITED:        
	    	v.setAlpha((float) 1);
	      break;
	    case DragEvent.ACTION_DROP:
	      // Dropped, reassign View to ViewGroup
	      View view = (View) event.getLocalState();
	      ViewGroup owner = (ViewGroup) view.getParent();
	      owner.removeView(view);
	      ScrollView container = (ScrollView) v;
	      LinearLayout layout = (LinearLayout) v.findViewById(R.id.ingredient_shelve);
	      if(curType == TYPE_INGREDIENT)
	    	  selectedIngredients.add(ingredients.get(view.getId()));
	      else
	    	  selectedProducts.add(products.get(view.getId()));
	      
	      if(curPosition % 4 == 0)
	      {
	    	  currentRow = new MyHorizontalLayout(mContext);
	    	  layout.addView(currentRow);
	    	  
	      }
	      if(curType == TYPE_INGREDIENT)
	      {
	    	  currentRow.add(ingredients.get(view.getId()).getName());
		      curPosition++;
		      listAdapterIngredients.notifyDataSetChanged(); 
	      }
	      else
	      {
	    	  currentRow.add(products.get(view.getId()).getName());
		      curPosition++;
		      listAdapterProducts.notifyDataSetChanged(); 
	      }
	     
	      
	      view.setVisibility(View.VISIBLE);
	      break;
	    case DragEvent.ACTION_DRAG_ENDED:
	    	v.setAlpha((float) 1);
	      default:
	      break;
	    }
	    return true;
	}
};
}