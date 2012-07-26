package com.nest5.androidClient;





import com.nest5.androidClient.AsyncImageLoader.ImageCallback;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.CharArrayBuffer;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;



public class ImageAdapterMyDeal extends ArrayAdapter<MyDeal> {
	
	/**
     * State of ListView item that has never been determined.
     */
    private static final int STATE_UNKNOWN = 0;

    /**
     * State of a ListView item that is sectioned. A sectioned item must
     * display the separator.
     */
    private static final int STATE_SECTIONED_CELL = 1;

    /**
     * State of a ListView item that is not sectioned and therefore does not
     * display the separator.
     */
    private static final int STATE_REGULAR_CELL = 2;
    
    private int[] mCellStates;
   
	 
    private ListView list;
    private AsyncImageLoader asyncImageLoader;
    private MyDeal[] result;
    private Context mContext;
 
    public ImageAdapterMyDeal(Context context, MyDeal[] result, ListView _list) {
    	
        super(context,0,result);
        mContext = context;
        mCellStates = result == null ? null : new int[result.length];
        
        Log.i("Prueba",String.valueOf(mCellStates.length));
        
        this.list = _list;
        this.result = result;
        asyncImageLoader = new AsyncImageLoader();
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity) getContext();
 
        // Inflate the views from XML
        View rowView = convertView;
        ViewCache viewCache;
        if (rowView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(R.layout.item, null);
            viewCache = new ViewCache(rowView);
            rowView.setTag(viewCache);
        } else {
            viewCache = (ViewCache) rowView.getTag();
        }
        String imageUrl = "http://www.atlas-sicherheitsdienst.com/404.gif";
        
        
     
        //String imageAndText = getItem(position);
        
        MyDeal currentDeal = result[position];
        imageUrl = Setup.STAGE_URL+currentDeal.imagen;
        String companyName = currentDeal.company.name;
		String name = currentDeal.promo.action.equals("Compra") ? mContext.getString(R.string.buyPerk, currentDeal.promo.reqQTY,currentDeal.promo.requirement)+" en "+currentDeal.company.name : mContext.getString(R.string.visitPerk, currentDeal.promo.reqQTY,currentDeal.promo.requirement)+" en "+currentDeal.company.name;
		
		name += mContext.getString(R.string.withNest5)+ currentDeal.promo.perk;
		int cantStamps = currentDeal.stamps.size();
		
		
		/*
         * Separator
         */
        boolean needSeparator = false;

        
        

        switch (mCellStates[position]) {
            case STATE_SECTIONED_CELL:
                needSeparator = true;
                break;

            case STATE_REGULAR_CELL:
                needSeparator = false;
                break;

            case STATE_UNKNOWN:
            default:
                // A separator is needed if it's the first itemview of the
                // ListView or if the group of the current cell is different
                // from the previous itemview.
                if (position == 0) {
                    needSeparator = true;
                } else {
                    
                		String previous = result[position - 1].company.name;
                		//Log.i("TEST",previous);
                    if ((previous.length() > 0) && (companyName.length() > 0) && !((previous.substring(0, 1)).equalsIgnoreCase((companyName.substring(0, 1))))) {
                        needSeparator = true;
                        //Log.i("PRUEBA",previous.substring(0, 1)+" "+name.substring(0, 1));
                        
                    }
                    else
                    {
                    	needSeparator = false;
                    }

                    
                }

                // Cache the result
                mCellStates[position] = needSeparator ? STATE_SECTIONED_CELL : STATE_REGULAR_CELL;
                break;
        }
       
        //Log.i("SEPARATOR",String.valueOf(needSeparator));
        if (needSeparator) {
            viewCache.getSeparatorView().setText(companyName.substring(0, 1));
            viewCache.getSeparatorView().setVisibility(View.VISIBLE);
        } else {
        	viewCache.getSeparatorView().setVisibility(View.GONE);
        }
    
	
     
        // Load the image and set it on the ImageView
       // String imageUrl = imageAndText.getImageUrl();
        ImageView imageView = viewCache.getImageView();
        imageView.setTag(imageUrl);
        Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl, new ImageCallback() {
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                ImageView imageViewByTag = (ImageView) list.findViewWithTag(imageUrl);
                if (imageViewByTag != null) {
                    imageViewByTag.setImageDrawable(imageDrawable);
                }
            }
            
        });
        
        if(cachedImage != null)
        {
        	imageView.setImageDrawable(cachedImage);
        }
        else{
        	imageView.setImageResource(R.drawable.big_logo);
        }
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        
        //imageView.setLayoutParams(new Gallery.LayoutParams(150, 100));
        
       
 
        // Set the text on the TextView
        TextView titleView = viewCache.getTitleView();
        titleView.setText(name);
        titleView.setTextSize(11);
        //Log.i("NOMBRE",name);
        TextView descView = viewCache.getDescView();
        String description = "Tienes "+currentDeal.stamps.size()+" Sellos de "+currentDeal.promo.reqQTY;
        descView.setText(description);
        
        
        return rowView;
    }
}