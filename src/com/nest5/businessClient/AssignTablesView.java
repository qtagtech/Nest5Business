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


public class AssignTablesView extends DialogFragment {
	
	public interface OnCreateComboListener {
        //public void OnComboCreated(Boolean status,LinkedHashMap<Ingredient,Double> ingredents,LinkedHashMap<Product,Double> products,String name,Double cost,Double price,Tax tax); //manda datos de combo para crearlo
        
    }
	
private OnCreateComboListener onCreateComboListener;
private DialogFragment frag;
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
		//onCreateComboListener = (OnCreateComboListener) activity;
	}
	catch(ClassCastException e){
		//throw new ClassCastException(activity.toString() + " must implement OnCreateComboListener");
	}
}


 public AssignTablesView() {
     // Empty constructor required for DialogFragment
 }
 



@Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
	 frag = this;
	 this.inflater = inflater;
     View view = inflater.inflate(R.layout.create_combo, container);
 	getDialog().setTitle("Crear un Nuevo Combo");
 	mContext = view.getContext();
 	
 	
 	
 	
 	
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
			frag.dismiss();	
		}
	});

     return view;
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
//
	      if(curPosition % 4 == 0)
	      {
	    	  currentRow = new MyHorizontalLayout(mContext);
	    	  layout.addView(currentRow);
	    	  
	      }
	      if(curType == TYPE_INGREDIENT)
	      {

		      curPosition++;
		      listAdapterIngredients.notifyDataSetChanged(); 
	      }
	      else
	      {

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