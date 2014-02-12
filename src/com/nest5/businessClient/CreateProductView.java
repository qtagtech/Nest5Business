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
import android.view.Window;
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


public class CreateProductView extends DialogFragment {
	
	public interface OnCreateProductListener {
        public void OnProductCreated(Boolean status,LinkedHashMap<Ingredient,Double> ingredents,Product product);
        
    }
	
private OnCreateProductListener onCreateProductListener;
private DialogFragment frag;
private List<Ingredient> ingredients;
private Product product;
private List<Ingredient> selectedIngredients;
private LinkedHashMap<Ingredient,Double> qties;
private Context mContext;

private Button saveBtn;
private Button cancelBtn;
private GridView itemsView;
private ScrollView shelves;
private MyHorizontalLayout currentRow;
private ListView ingredientsList;

private IngredientAdapter listAdapter;

private int curPosition;




@Override
public void onAttach(Activity activity){
	super.onAttach(activity);
	try
	{
		onCreateProductListener = (OnCreateProductListener) activity;
	}
	catch(ClassCastException e){
		throw new ClassCastException(activity.toString() + " must implement OnCreateProductListener");
	}
}


 public CreateProductView() {
     // Empty constructor required for DialogFragment
 }
 
 public CreateProductView(List<Ingredient> ingredients,Product product)
 {
	
	 this.ingredients = ingredients;
	 this.product = product;
	 
 }


@Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
	 frag = this;
     View view = inflater.inflate(R.layout.create_product, container);
 	getDialog().setTitle("Crear Receta para "+product.getName());
 	mContext = view.getContext();
 	saveBtn = (Button) view.findViewById(R.id.save_recepie);
 	cancelBtn = (Button) view.findViewById(R.id.cancel_recepie);
 	itemsView = (GridView) view.findViewById(R.id.gridview_ingredients);
 	shelves = (ScrollView) view.findViewById(R.id.shelves);
 	ingredientsList = (ListView) view.findViewById(R.id.ingredient_add_list);
 			
 	ImageAdapterCreateProduct gridAdapter = new ImageAdapterCreateProduct(mContext, ingredients, inflater,touchListener);
	setGridContent(gridAdapter,ingredients);
	selectedIngredients = new ArrayList<Ingredient>();
	curPosition = 0;
	shelves.setOnDragListener(dragListener);
	Activity activity = getActivity();
	Log.d("UNIDADES",activity.getClass().toString());
	listAdapter = new IngredientAdapter(mContext, selectedIngredients, inflater,activity);
	ingredientsList.setAdapter(listAdapter);
	qties = new LinkedHashMap<Ingredient,Double>();
	//Log.d("ACAAAAAAA"," "+ingredients.size());
 	
 	
 	
 	
 	cancelBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			onCreateProductListener.OnProductCreated(false, null, product);
			frag.dismiss();
			
		}
	});
 	
saveBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			HashMap<String, Double> multipliers = listAdapter.getMultipliers();
			HashMap<String, Ingredient> ingredients = listAdapter.getIngredients();
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
			onCreateProductListener.OnProductCreated(true,values,product);
			
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

private void setGridContent(ImageAdapterCreateProduct adapter,final List<Ingredient> list){
	
	itemsView.setAdapter(adapter);
	
}

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
	      selectedIngredients.add(ingredients.get(view.getId()));
	      
	      if(curPosition % 4 == 0)
	      {
	    	  currentRow = new MyHorizontalLayout(mContext);
	    	  layout.addView(currentRow);
	    	  
	      }
	     
	      currentRow.add(ingredients.get(view.getId()).getName());
	      curPosition++;
	      listAdapter.notifyDataSetChanged();
	      view.setVisibility(View.VISIBLE);
	      break;
	    case DragEvent.ACTION_DRAG_ENDED:
	    	v.setAlpha((float) 1);
	    	if (dropEventNotHandled(event)) {
	    		View view2 = (View) event.getLocalState();
                view2.setVisibility(View.VISIBLE);
            }
	    	break;
	      default:
	      break;
	    }
	    return true;
	}
	private boolean dropEventNotHandled(DragEvent dragEvent) {
        return !dragEvent.getResult();
    }
};
}