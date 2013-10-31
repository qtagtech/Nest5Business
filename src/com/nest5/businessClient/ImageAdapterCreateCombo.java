package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View.OnClickListener;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapterCreateCombo extends BaseAdapter {
    private Context mContext;
    private List<Product> items;
    private LayoutInflater inflater;
    
    private ImageView imageView;
    private TextView nameView;
    private TextView priceView;
    
    private Typeface varela;
    
    //private OnClickListener cListener;
    private OnTouchListener tListener;
    
    
    
    
    
    

    public ImageAdapterCreateCombo(Context c,List<Product> _items,LayoutInflater _inflater,OnTouchListener touchListener/*,OnClickListener cListener*/) {
        mContext = c;
        items = _items;
        inflater = _inflater;
        //this.cListener = cListener;
        this.tListener = touchListener;
        varela = Typeface.createFromAsset(mContext.getAssets(), "fonts/Varela-Regular.otf");
        
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
        ViewCacheRegistrable viewCache;
        
        if (rowView == null) {
            
            rowView = inflater.inflate(R.layout.item_registrable, null);
            viewCache = new ViewCacheRegistrable(rowView);
            rowView.setTag(viewCache);
        } else {
            viewCache = (ViewCacheRegistrable) rowView.getTag();
        }

        //imageView.setImageResource(mThumbIds[position]);
       // return imageView;
        nameView = viewCache.getNameView();
        nameView.setText(item.getName());
        nameView.setTypeface(varela);
        imageView = viewCache.getImageView();
        imageView.setTag("registrar"+position);
        imageView.setId(position);
       // buttonView.setOnClickListener(cListener);
        priceView = viewCache.getPriceView();
        priceView.setText(String.valueOf(Initialactivity.roundWhole(item.getPrice() * (1 + item.getTax().getPercentage()))));
        //priceView.setText(String.valueOf(item.price * (1 + item.tax)));
        priceView.setTypeface(varela);
        imageView.setOnTouchListener(tListener);
        return rowView;
    }

    
}
