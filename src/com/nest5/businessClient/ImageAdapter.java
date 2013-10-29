package com.nest5.businessClient;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View.OnClickListener;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<Registrable> items;
    private LayoutInflater inflater;
    
    private Typeface varela;
    
    private OnClickListener cListener;
    private ImageView imageView;
    private TextView priceView;
    private TextView nameView;
    
    
    
    
    
    

    public ImageAdapter(Context c,List<Registrable> _items,LayoutInflater _inflater,OnClickListener cListener) {
        mContext = c;
        items = _items;
        inflater = _inflater;
        this.cListener = cListener;
        varela = Typeface.createFromAsset(mContext.getAssets(), "fonts/Varela-Regular.otf");
    }

    public int getCount() {
        return items.size();
    }

    public Registrable getItem(int position) {
        return items.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        //ImageView imageView;
        Registrable item = getItem(position);
       
        
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
        nameView.setText(item.name);
        nameView.setTypeface(varela);
        imageView = viewCache.getImageView();
        imageView.setTag("registrar"+position);
        //imageView.setImageResource(mThumbIds[4]);
        imageView.setId(position);
        //imageView.setTypeface(varela);
        //buttonView.setOnClickListener(cListener);
        priceView = viewCache.getPriceView();
        priceView.setText(String.valueOf(Initialactivity.roundWhole(item.price * (1 + item.tax))));
        //priceView.setText(String.valueOf(item.price * (1 + item.tax)));
        priceView.setTypeface(varela);
        
        return rowView;
    }

    // references to our images
    /*private Integer[] mThumbIds = {
            R.drawable.app_icon, R.drawable.background,
            R.drawable.banner_menu, R.drawable.big_logo,
            R.drawable.bt_coupons_normal, R.drawable.bt_coupons_pressed,
            R.drawable.bt_deals_pressed, R.drawable.bt_directory_normal,
            R.drawable.cart, R.drawable.face_cheer,
            R.drawable.face_two, R.drawable.ipod,
            R.drawable.face_three, R.drawable.ic_mailboxes_accounts,
            R.drawable.app_icon, R.drawable.background,
            R.drawable.banner_menu, R.drawable.big_logo,
            R.drawable.bt_coupons_normal, R.drawable.bt_coupons_pressed,
            R.drawable.bt_deals_pressed, R.drawable.bt_directory_normal,
            R.drawable.cart, R.drawable.face_cheer,
            R.drawable.face_two, R.drawable.ipod,
            R.drawable.face_three, R.drawable.ic_mailboxes_accounts,
            R.drawable.app_icon, R.drawable.background,
            R.drawable.banner_menu, R.drawable.big_logo,
            R.drawable.bt_coupons_normal, R.drawable.bt_coupons_pressed,
            R.drawable.bt_deals_pressed, R.drawable.bt_directory_normal,
            R.drawable.cart, R.drawable.face_cheer,
            R.drawable.face_two, R.drawable.ipod,
            R.drawable.face_three, R.drawable.ic_mailboxes_accounts,
            R.drawable.app_icon, R.drawable.background,
            R.drawable.banner_menu, R.drawable.big_logo,
            R.drawable.bt_coupons_normal, R.drawable.bt_coupons_pressed,
            R.drawable.bt_deals_pressed, R.drawable.bt_directory_normal,
            R.drawable.cart, R.drawable.face_cheer,
            R.drawable.face_two, R.drawable.ipod,
            R.drawable.face_three, R.drawable.ic_mailboxes_accounts,
            R.drawable.app_icon, R.drawable.background,
            R.drawable.banner_menu, R.drawable.big_logo,
            R.drawable.bt_coupons_normal, R.drawable.bt_coupons_pressed,
            R.drawable.bt_deals_pressed, R.drawable.bt_directory_normal,
            R.drawable.cart, R.drawable.face_cheer,
            R.drawable.face_two, R.drawable.ipod,
            R.drawable.face_three, R.drawable.ic_mailboxes_accounts,
            R.drawable.app_icon, R.drawable.background,
            R.drawable.banner_menu, R.drawable.big_logo,
            R.drawable.bt_coupons_normal, R.drawable.bt_coupons_pressed,
            R.drawable.bt_deals_pressed, R.drawable.bt_directory_normal,
            R.drawable.cart, R.drawable.face_cheer,
            R.drawable.face_two, R.drawable.ipod,
            R.drawable.face_three, R.drawable.ic_mailboxes_accounts
    };*/
}
