package com.nest5.businessClient;


import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.renderscript.Type;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyHorizontalLayout extends LinearLayout {
	
	private Typeface varela;
 
 Context myContext;
 ArrayList<String> itemList = new ArrayList<String>();

 public MyHorizontalLayout(Context context) {
  super(context);
  myContext = context;
 }

 public MyHorizontalLayout(Context context, AttributeSet attrs) {
  super(context, attrs);
  myContext = context;
 }

 public MyHorizontalLayout(Context context, AttributeSet attrs,
   int defStyle) {
  super(context, attrs, defStyle);
  myContext = context;
 }
 
 void add(String name ){
  int newIdx = itemList.size();
  itemList.add(name);
  addView(getImageView(newIdx));
  setBackgroundDrawable(getResources().getDrawable(R.drawable.shelve));
 }
 
 //
 
 RelativeLayout getImageView(int i){
  Bitmap bm = null;
  if (i < itemList.size()){
   bm = decodeSampledBitmapFromUri(220, 220);
  }
  varela = Typeface.createFromAsset(myContext.getAssets(), "fonts/Varela-Regular.otf");
  RelativeLayout lay = new RelativeLayout(myContext);
  LinearLayout.LayoutParams layoutParams = new LayoutParams(0, 100,.25f);
  layoutParams.setMargins(10, 10, 10, 0);
  lay.setPadding(0, 0, 0, 18);
  lay.setLayoutParams(layoutParams);
  
  ImageView imageView = new ImageView(myContext);
  	RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
  	imageParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
  	//
     imageView.setLayoutParams(imageParams);
     imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
     imageView.setImageBitmap(bm);
     TextView txt= new TextView(myContext);
     RelativeLayout.LayoutParams txtParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, 50);
     txtParams.addRule(RelativeLayout.CENTER_IN_PARENT);
     txt.setLayoutParams(txtParams);
     txt.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
     txt.setText(itemList.get(i));
     txt.setTypeface(varela);
     txt.setTextColor(Color.parseColor("#ffffff"));
     txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
     
     lay.addView(imageView);
     lay.addView(txt);
     
     

  return lay;
 }
 
 public Bitmap decodeSampledBitmapFromUri(int reqWidth, int reqHeight) {
     Bitmap bm = null;
     
     // First decode with inJustDecodeBounds=true to check dimensions
     final BitmapFactory.Options options = new BitmapFactory.Options();
     options.inJustDecodeBounds = true;
     BitmapFactory.decodeResource(getResources(), R.drawable.olla, options);
     
     // Calculate inSampleSize
     options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
     
     // Decode bitmap with inSampleSize set
     options.inJustDecodeBounds = false;
     bm = BitmapFactory.decodeResource(getResources(),R.drawable.olla, options); 
     
     return bm;  
    }
    
    public int calculateInSampleSize(
      
     BitmapFactory.Options options, int reqWidth, int reqHeight) {
     // Raw height and width of image
     final int height = options.outHeight;
     final int width = options.outWidth;
     int inSampleSize = 1;
        
     if (height > reqHeight || width > reqWidth) {
      if (width > height) {
       inSampleSize = Math.round((float)height / (float)reqHeight);   
      } else {
       inSampleSize = Math.round((float)width / (float)reqWidth);   
      }   
     }
     
     return inSampleSize;   
    }
 
}