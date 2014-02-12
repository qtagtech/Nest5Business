package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.nfc.Tag;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View.OnClickListener;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class IngredientAdapter extends BaseAdapter {
    private Context mContext;
    private List<Ingredient> items;
    private LayoutInflater inflater;
    private TextView titleView;
    private EditText qtyView;
    private Spinner unitView;
    private HashMap<String,Double> multipliers;
    private HashMap<String,Ingredient>ingredientes;
    private HashMap<String,String> ingredientRecord;
    private HashMap<String,Integer> unitRecord;
    private Typeface varela;
    
    //private OnClickListener cListener;
    //private OnTouchListener tListener;
    
    
    
    
    
    

    public IngredientAdapter(Context c,List<Ingredient> _items,LayoutInflater _inflater,Activity _activity/*,OnClickListener cListener*/) {
        mContext = c;
        items = _items;
        inflater = _inflater;
        //this.cListener = cListener;
        //this.tListener = touchListener;
        varela = Typeface.createFromAsset(mContext.getAssets(), "fonts/Varela-Regular.otf");
        ingredientRecord = new HashMap<String, String>();
        unitRecord = new HashMap<String, Integer>(); 
        multipliers = new HashMap<String, Double>();
        ingredientes = new HashMap<String, Ingredient>();

        
    }

    public int getCount() {
        return items.size();
    }

    public Ingredient getItem(int position) {
        return items.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        //ImageView imageView;
       
        Ingredient item = getItem(position);
        ingredientes.put(item.getName(), item);
     // Inflate the views from XML
        View rowView = convertView;
        ViewCacheIngredientAdd viewCache;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.ingredient_add_row, null);
            viewCache = new ViewCacheIngredientAdd(rowView);
            rowView.setTag(viewCache);
        } else {
            viewCache = (ViewCacheIngredientAdd) rowView.getTag();
        }
        titleView = viewCache.getTitleView();
        titleView.setText(item.getName());
        titleView.setTypeface(varela);
        qtyView = viewCache.getQtyView();
        qtyView.setTypeface(varela);
        qtyView.setText(ingredientRecord.get(item.getName()));
        qtyView.setId(position);
        qtyView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    final int position = view.getId();
                    final EditText edit = (EditText) view;
                    if (edit.getText() != null && !edit.getText().toString().isEmpty())
                        if (position < getCount())
                            ingredientRecord.put(getItem(position).getName(), edit.getText().toString());
                }
            }
        });
        unitView = viewCache.getUnitView();
		LinkedHashMap<String, Double> currentMultipliers = item.getUnit().getMultipliers();
		String[] list = new String[currentMultipliers.size()];
	    Iterator<Entry<String, Double>> it = currentMultipliers.entrySet().iterator();
	    int i = 0;
	     while(it.hasNext())
	     {
	    	 Map.Entry<String,Double> pairs = (Map.Entry<String,Double>)it.next();
	     	//Log.i("HOLAAA",iterator.next().getName());
	     	list[i] = pairs.getKey();
	     	multipliers.put(pairs.getKey(), pairs.getValue());
	     	
	     	i++;
	     }
	     ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_dropdown_item,list);
	     // Apply the adapter to the spinner
	     unitView.setAdapter(adapter);
	     try{
	    	 unitView.setSelection(unitRecord.get(item.getName())); 
	     }catch(Exception e){
	    	 
	     }
	     
	     unitView.setId(position);
	     //Log.w("CAMBIANDOTEXTO",String.valueOf(position));
	     unitView.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int pos, long arg3) {
				View v = (View) view.getParent();
				final int position = v.getId();
				Log.w("CAMBIANDOTEXTO",String.valueOf(position));
                unitRecord.put(getItem(position).getName(), pos);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        return rowView;
        
    }

    
    
    
    
	
	public HashMap<String, Double> getMultipliers(){
		return multipliers;
	}
	
	public HashMap<String, Ingredient> getIngredients(){
		return ingredientes;
	}
}
