package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.util.Log;
import android.view.View.OnClickListener;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class IngredientAdapter extends BaseAdapter {
    private Context mContext;
    private List<Ingredient> items;
    private LayoutInflater inflater;
    private ImageView imageView;
    private TextView titleView;
    private EditText qtyView;
    private Spinner unitView;
    private HashMap<Ingredient,EditText> valuesTxt;
    private HashMap<Ingredient,Spinner> unitSpinners;
    private HashMap<String,Double> multipliers;
    
    private Typeface varela;
    
    //private OnClickListener cListener;
    //private OnTouchListener tListener;
    
    
    
    
    
    

    public IngredientAdapter(Context c,List<Ingredient> _items,LayoutInflater _inflater/*,OnClickListener cListener*/) {
        mContext = c;
        items = _items;
        inflater = _inflater;
        //this.cListener = cListener;
        //this.tListener = touchListener;
        varela = Typeface.createFromAsset(mContext.getAssets(), "fonts/Varela-Regular.otf");
        
        
        
        valuesTxt = new HashMap<Ingredient, EditText>();
        unitSpinners = new HashMap<Ingredient, Spinner>();
        multipliers = new HashMap<String, Double>();
        
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

        //imageView.setImageResource(mThumbIds[position]);
       // return imageView;
        titleView = viewCache.getTitleView();
        titleView.setText(item.getName());
        titleView.setTypeface(varela);
        qtyView = viewCache.getQtyView();
        qtyView.setTypeface(varela);
        qtyView.setId(position);
        //qtyView.setOnFocusChangeListener(onFocusChangeLlistener);
        unitView = viewCache.getUnitView();
        unitSpinners.put(item, unitView);
        valuesTxt.put(item, qtyView);
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
	     //createValue();
        return rowView;
        
    }

    
    
    
    
    /*private  OnFocusChangeListener onFocusChangeLlistener = new OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if(hasFocus)
			{
				//quantities.add(v.getId(), 0.0);
			}
			else
			{
				
				EditText txt = (EditText) v;
				String value = txt.getText().toString();
				double val = 0.0;
				if(value != null && !value.isEmpty())
				{
					val = Double.valueOf(value);
				}
				
				quantities.add(v.getId(), val);
			}	
		}
	};*/
	
	public LinkedHashMap<Ingredient,Double> createValue()
	{
		//coger cada uno de los que se haya metido y cada una de las cantidades, luego pasar por cada uno de los spinner y ver el multiplicador, hacer la conversion y guardar ing,cant
		
		LinkedHashMap<Ingredient,Double> receta = new LinkedHashMap<Ingredient,Double>();
		Iterator<Entry<Ingredient, EditText>> it = valuesTxt.entrySet().iterator();
	    
	    
	     while(it.hasNext())
	     {
	    	 Map.Entry<Ingredient,EditText> ingredientQtyPair = (Map.Entry<Ingredient,EditText>)it.next();
	    	 Spinner ingredientUnit = unitSpinners.get(ingredientQtyPair.getKey());
	    	 //Log.d("UNIDADES", (String) ingredientUnit.getAdapter().getItem(ingredientUnit.getSelectedItemPosition())); //debo poner la posicion
	    	 double multiplier = multipliers.get((String) ingredientUnit.getAdapter().getItem(ingredientUnit.getSelectedItemPosition()));
	    	 String qtyValue = ingredientQtyPair.getValue().getText().toString();
	    	 double qtyVal = 0.0;
	    	 if(!qtyValue.isEmpty() && qtyValue != null)
	    	 {
	    		 qtyVal = Double.valueOf(qtyValue);
	    	 }
	    	 
	    	 double quantity = qtyVal * multiplier;
	    	 
	    	 receta.put(ingredientQtyPair.getKey(), quantity);

	     	
	     }
		return receta;
	}
}
