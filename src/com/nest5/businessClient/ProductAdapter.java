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

public class ProductAdapter extends BaseAdapter {
    private Context mContext;
    private List<Product> items;
    private LayoutInflater inflater;
    private ImageView imageView;
    private TextView titleView;
    private EditText qtyView;
    //private Spinner unitView;
    private HashMap<Product,EditText> valuesTxt;
    private HashMap<Product,Spinner> unitSpinners;
    private HashMap<String,Double> multipliers;
    
    private Typeface varela;
    
    //private OnClickListener cListener;
    //private OnTouchListener tListener;
    
    
    
    
    
    

    public ProductAdapter(Context c,List<Product> _items,LayoutInflater _inflater/*,OnClickListener cListener*/) {
        mContext = c;
        items = _items;
        inflater = _inflater;
        //this.cListener = cListener;
        //this.tListener = touchListener;
        varela = Typeface.createFromAsset(mContext.getAssets(), "fonts/Varela-Regular.otf");
        
        
        
        valuesTxt = new HashMap<Product, EditText>();
        unitSpinners = new HashMap<Product, Spinner>();
        multipliers = new HashMap<String, Double>();
        
    }

    public int getCount() {
        return items.size();
    }

    public Product getItem(int position) {
        return items.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        //ImageView imageView;
       
        Product item = getItem(position);
        
     // Inflate the views from XML
        
        View rowView = convertView;
        ViewCacheProductAdd viewCache;
        
        if (rowView == null) {
            
            rowView = inflater.inflate(R.layout.product_add_row, null);
            viewCache = new ViewCacheProductAdd(rowView);
            rowView.setTag(viewCache);
        } else {
            viewCache = (ViewCacheProductAdd) rowView.getTag();
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
       
     
        valuesTxt.put(item, qtyView);
	
		
	     
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
	
	public LinkedHashMap<Product,Double> createValue()
	{
		//coger cada uno de los que se haya metido y cada una de las cantidades, luego pasar por cada uno de los spinner y ver el multiplicador, hacer la conversion y guardar ing,cant
		
		LinkedHashMap<Product,Double> receta = new LinkedHashMap<Product,Double>();
		Iterator<Entry<Product, EditText>> it = valuesTxt.entrySet().iterator();
	    
	    
	     while(it.hasNext())
	     {
	    	 Map.Entry<Product,EditText> ingredientQtyPair = (Map.Entry<Product,EditText>)it.next();
	    	 //Spinner ingredientUnit = unitSpinners.get(ingredientQtyPair.getKey());
	    	 //Log.d("UNIDADES", (String) ingredientUnit.getAdapter().getItem(ingredientUnit.getSelectedItemPosition())); //debo poner la posicion
	    	 //double multiplier = multipliers.get((String) ingredientUnit.getAdapter().getItem(ingredientUnit.getSelectedItemPosition()));
	    	 String qtyValue = ingredientQtyPair.getValue().getText().toString();
	    	 double qtyVal = 0.0;
	    	 if(!qtyValue.isEmpty() && qtyValue != null)
	    	 {
	    		 qtyVal = Double.valueOf(qtyValue);
	    	 }
	    	 
	    	 double quantity = qtyVal;
	    	 
	    	 receta.put(ingredientQtyPair.getKey(), quantity);

	     	
	     }
		return receta;
	}
}
