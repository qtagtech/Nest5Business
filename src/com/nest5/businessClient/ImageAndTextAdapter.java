package com.nest5.businessClient;






import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;



public class ImageAndTextAdapter extends ArrayAdapter<String> {
	

    
    
    private Context mContext;
	private LayoutInflater inflater; 
    
    
    private String[] result;
 
    public ImageAndTextAdapter(Context context, String[] result, ListView _list,LayoutInflater inflater) {
    	
        super(context,0,result);
        this.result = result;
        this.mContext = context;
        this.inflater = inflater;
        
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Activity activity = (Activity) getContext();
 
        // Inflate the views from XML
        View rowView = convertView;
        ViewCache viewCache;
        if (rowView == null) {
            
            rowView = inflater.inflate(R.layout.item, null);
            viewCache = new ViewCache(rowView);
            rowView.setTag(viewCache);
        } else {
            viewCache = (ViewCache) rowView.getTag();
        }
        
        
        
     
        //String imageAndText = getItem(position);
        
        String currentItem = result[position];
		
		
		
		
		
		
	
       
        
        
        	viewCache.getSeparatorView().setVisibility(View.GONE);
        
    
	
     
        // Load the image and set it on the ImageView
       // String imageUrl = imageAndText.getImageUrl();
        ImageView imageView = viewCache.getImageView();
        imageView.setTag("Agregar"+position);
        
        
        
        imageView.setImageResource(R.drawable.add);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        
        //imageView.setLayoutParams(new Gallery.LayoutParams(150, 100));
        
       
 
        // Set the text on the TextView
        TextView titleView = viewCache.getTitleView();
        titleView.setText(currentItem);
        //Log.i("NOMBRE",name);
        //TextView descView = viewCache.getDescView();
        
        
        
        
        //descView.setText(description);
        
        
        return rowView;
    }
}