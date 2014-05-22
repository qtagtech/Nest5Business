package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View.OnClickListener;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SaleAdapter extends ArrayAdapter<Long> {
    private Context mContext;
    private List<Long> items;
    private List<String> tables;
    private LayoutInflater inflater;
    
    private Typeface varela;
    
    //private OnClickListener cListener;
    private ImageView imageView;
    private TextView timeView;
    private TextView nameView;

    
    
    
    
    

    public SaleAdapter(Context c,List<Long> _items,List<String> _tables,LayoutInflater _inflater) {
        super(c,R.layout.sell_item,_items);
    	mContext = c;
        items = _items;
        inflater = _inflater;
        tables = _tables;
        	

        //this.cListener = cListener;
        varela = Typeface.createFromAsset(mContext.getAssets(), "fonts/Varela-Regular.otf");
    }

    public int getCount() {
        return items.size();
    }

    public Long getItem(int position) {
        return items.get(position);
    }
    public String getTable(int position){
    	return tables.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        //ImageView imageView;
        Long item = getItem(position) != null ? getItem(position) : 0;
       
        
     // Inflate the views from XML
        
        View rowView = convertView;
        ViewCacheSale viewCache;
        
        if (rowView == null) {
            
            rowView = inflater.inflate(R.layout.sell_item, null);
            viewCache = new ViewCacheSale(rowView);
            rowView.setTag(viewCache);
        } else {
            viewCache = (ViewCacheSale) rowView.getTag();
        }

        //imageView.setImageResource(mThumbIds[position]);
       // return imageView;
        nameView = viewCache.getNameView();
        
        nameView.setText(getTable(position)+System.getProperty("line.separator")+"Venta número: "+String.valueOf(position + 1));
        //nameView.setTypeface(varela);
        //imageView = viewCache.getImageView();
        //imageView.setTag("registrar"+position);
        //imageView.setImageResource(mThumbIds[4]);
        //imageView.setId(position);
        //imageView.setTypeface(varela);
        //buttonView.setOnClickListener(cListener);
        Double time =  Math.floor((System.currentTimeMillis() - item) / 1000 / 60);
        timeView = viewCache.getTimeView();
        
        timeView.setText(String.valueOf(time));
        //priceView.setText(String.valueOf(item.price * (1 + item.tax)));
        timeView.setTypeface(varela);
        
        return rowView;
    }
    
    
    

    
}
