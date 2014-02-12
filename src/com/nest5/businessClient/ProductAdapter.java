package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
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
    private TextView titleView;
    private EditText qtyView;
    private HashMap<String, Product> productos;
    private HashMap<String, String> productRecord;


    
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
        productos = new HashMap<String, Product>();
        productRecord = new HashMap<String, String>();
        


        
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
        productos.put(item.getName(), item);
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
        qtyView.setText(productRecord.get(item.getName()));
        qtyView.setId(position);
        qtyView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    final int position = view.getId();
                    final EditText edit = (EditText) view;
                    if (edit.getText() != null && !edit.getText().toString().isEmpty())
                        if (position < getCount())
                            productRecord.put(getItem(position).getName(), edit.getText().toString());
                }
            }
        });
        
        return rowView;
    }

    

	
	public HashMap<String, Product> getProducts(){
		return productos;
	}
}
