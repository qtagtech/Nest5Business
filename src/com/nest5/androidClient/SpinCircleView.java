package com.nest5.androidClient;



import com.google.android.maps.MyLocationOverlay;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Toast;


public class SpinCircleView extends View {
	
	private static String TAG = "CompassView";
	
	private float bearing;
	private Bitmap logo;
	private Bitmap deals;
	private Bitmap share;
	private Bitmap directory;
	private Bitmap promos;
	private Bitmap coupons;
	private Context mContext;
	private int px;
	private int py;
	float size_big;
	float size_small;
	float margin;
	private Resources r;
	private boolean touched;
	private float centers[];
	private float lado;
	private float distances[];
	private float startAngle;
	private float angle;
	public boolean animFinished = false;
	public RotateAnimation rotate;
	private Paint textPaint;
	private String closeString;
	private String directoryString;
	private String shareString;
	private String dealsString;
	private String couponsString;
	private int textHeight;
	Typeface BebasFont;
    Typeface VarelaFont;
    private float densityMultiplier;
	private float scaledPx;
	
	
	
	
	
	
	//required for whe in code creation
	public SpinCircleView(Context context)
	{
		super(context);
		mContext = context;
		initCompassView();
		
	}
	
	//requeired for whe inflating
	
	public SpinCircleView (Context context, AttributeSet attrs)
	{
		super(context,attrs);
		mContext = context;
		initCompassView();
		
	}
	
	public SpinCircleView (Context context, AttributeSet attrs,int defaultStyle)
	{
		super(context,attrs,defaultStyle);
		mContext = context;
		initCompassView();
		
	}
	
	protected void initCompassView()
	{
		setFocusable(true);
		 r = this.getResources();
		
		logo = BitmapFactory.decodeResource(r, R.drawable.big_logo);
		deals = BitmapFactory.decodeResource(r, R.drawable.ipod);
		share = BitmapFactory.decodeResource(r, R.drawable.handshake);
		directory = BitmapFactory.decodeResource(r, R.drawable.search);
		promos = BitmapFactory.decodeResource(r, R.drawable.cart);
		coupons = BitmapFactory.decodeResource(r, R.drawable.star);
		
		closeString = r.getString(R.string.close_deals);
		directoryString = r.getString(R.string.directory);
		shareString = r.getString(R.string.share);
		couponsString = r.getString(R.string.my_coupons);
		dealsString = r.getString(R.string.my_promos);
		
		BebasFont= Typeface.createFromAsset(mContext.getAssets(), "fonts/BebasNeue.otf");
	    VarelaFont= Typeface.createFromAsset(mContext.getAssets(), "fonts/Varela-Regular.otf");
		
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setTypeface(BebasFont);
		textPaint.setColor(r.getColor(R.color.text_color));
		densityMultiplier = r.getDisplayMetrics().density;
		scaledPx = 17 * densityMultiplier;
		textPaint.setTextSize(scaledPx);
		
		textHeight = (int) textPaint.measureText("yY");
		
		
		
		
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int measureWidth = measure(widthMeasureSpec);
		int measureHeight = measure(heightMeasureSpec);
		int d = Math.min(measureWidth, measureHeight);
		setMeasuredDimension(d,d);
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		
		px = getMeasuredWidth() / 2;
		py = getMeasuredHeight() / 2;
		
		
		 size_big = logo.getWidth();
		 size_small = deals.getWidth();
		margin = Math.min(Math.min(px, py) - (size_big / 2 + size_small),30);
		lado = size_big/2 + size_small/2 + margin;
		//Log.i(TAG,"grande: "+radius_big+", pequeno: "+radius_small+", margen: "+margin);
		
		
		
		//Draw the background
		
		//canvas.drawCircle(px, py,radius_big, circlePaint);
		
		canvas.drawBitmap(logo, px - size_big/2, py - size_big / 2, null);
		//canvas.drawBitmap(deals,px - size_small/2,py - size_big/2 - margin - size_small,null );
		
		 angle = 360 / 5;
		 startAngle = 90;
		
		if(distances == null)
		{
			centers = new float[12];
			distances = new float[12];
			int j = 2;
			int m = 0;
			distances[0] = 0;
			distances[1] = 0;
			while(m < 5)
			{
				distances[j] = (float) (lado * Math.cos((startAngle + m * angle) * Math.PI/180)); 
				distances[j+1] = (float)(lado * Math.sin((startAngle + m * angle) * Math.PI/180));
				m++;
				j += 2;
			}
			
			/*
			distances[2] = (float)(lado * Math.cos(startAngle*Math.PI/180));
			distances[3] = (float)(lado * Math.sin(startAngle*Math.PI/180));
			distances[4] = (float)(lado * Math.cos(2 * Math.PI/4));
			distances[5] = (float)(lado * Math.sin(2 * Math.PI/4));
			distances[6] = (float)(lado * Math.cos(3 * Math.PI/4));
			distances[7] = (float)(lado * Math.sin(3 * Math.PI/4));
			distances[8] = (float)(lado * Math.cos(5 * Math.PI/4));
			distances[9] = (float)(lado * Math.sin(5 * Math.PI/4));
			distances[10] = (float)(lado * Math.cos(7 * Math.PI/4));
			distances[11] = (float)(lado * Math.sin(7 * Math.PI/4));
			*/
			//save centers
			int i =0;
			
			while(i < distances.length)
			{
				centers[i] = px + distances [i];
				centers[i + 1] = py - distances[i+1];
				i += 2;
			}
			
			
			
			
			
			
			
		}
		//Deals
		canvas.drawBitmap(deals,centers[2] - size_small / 2,centers[3] - size_small / 2 ,null );
		float size = textPaint.measureText(closeString);
		float cardinalX = centers[2] - size/2;
		float cardinalY = centers[3] + size_small / 2;
		canvas.drawText(closeString, cardinalX,cardinalY,textPaint);
		//Directory
		canvas.drawBitmap(directory,centers[4] - size_small / 2,centers[5] - size_small / 2 ,null );
		size = textPaint.measureText(directoryString);
		cardinalX = centers[4] - size/2;
		cardinalY = centers[5] + size_small / 2;
		canvas.drawText(directoryString, cardinalX,cardinalY,textPaint);
		//Promos
		canvas.drawBitmap(promos,centers[6] - size_small / 2,centers[7] - size_small / 2 ,null );
		size = textPaint.measureText(dealsString);
		cardinalX = centers[6] - size/2;
		cardinalY = centers[7] + size_small / 2;
		canvas.drawText(dealsString, cardinalX,cardinalY,textPaint);
		//Coupons
		canvas.drawBitmap(coupons,centers[8] - size_small / 2,centers[9] - size_small / 2 ,null );
		size = textPaint.measureText(couponsString);
		cardinalX = centers[8] - size/2;
		cardinalY = centers[9] + size_small / 2;
		canvas.drawText(couponsString, cardinalX,cardinalY,textPaint);
		//Share
		canvas.drawBitmap(share,centers[10] - size_small / 2,centers[11] - size_small / 2 ,null );
		size = textPaint.measureText(shareString);
		cardinalX = centers[10] - size/2;
		cardinalY = centers[11] + size_small / 2;
		canvas.drawText(shareString, cardinalX,cardinalY,textPaint);
		
		
		
		
		
		//rotate our perspective so that the top is faceing the current bearing
		
		
		
		/*
		int textWidth = (int)textPaint.measureText("W");
		int cardinalX = px - textWidth/2;
		int cardinalY = py - (int)radius_big + textHeight;
		
		*/
		//Draw the marker every 15 degrees and text every 45
		/*
		for (int i = 0 ; i < 24; i++)
		{
			//Draw a marker
			canvas.drawLine(px, py - radius, px, py - radius + 10, markerPaint);
			canvas.save();
			//canvas.translate(0, textHeight);
			canvas.rotate(15, px, py);
			
			
		}
		*/
		
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		
		
		//return true;
		return super.onTouchEvent(event);
		
		
	}
	
	 
	
	private int measure(int measureSpec)
	{
		int result = 0;
		//decode teh measurements
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		if(specMode== MeasureSpec.UNSPECIFIED)
		{
			//Return a default size of 200 if no bounds are specified
			result = 200;
		}else{
			//As we want to fill the available space always return the full available bounds
			result = specSize;
		}
		return result;
	}
	
	/*
	 * public void setBearing(float _bearing)
	{
		bearing = _bearing;
	}
	
	public float getBearing()
	{
		return bearing;
	}
	*/
	public int getClickId(float posX, float posY)
	{ 
		int id = -1;
		
			
		
			if((posX > centers[0] - size_big/2) && (posX < centers[0] + size_big/2) && (posY > centers[1]-size_big/2) && (posY < centers[1]+size_big/2))
			{
				//setPressed(1);
				animRotate(1);
				 id = 1;
			}
			
			
				if((posX > centers[2] - size_small/2) && (posX < centers[2] + size_small/2) && (posY > centers[3] - size_small/2) && (posY < centers[3] + size_small/2))
				{
					animRotate(2);
					id = 2;
					//setPressed(2);
				}
				if((posX > centers[4] - size_small/2) && (posX < centers[4] + size_small/2) && (posY > centers[5] - size_small/2) && (posY < centers[5] + size_small/2))
				{
					animRotate(3);
					id = 3;
					//setPressed(2);
				}
				if((posX > centers[6] - size_small/2) && (posX < centers[6] + size_small/2) && (posY > centers[7] - size_small/2) && (posY < centers[7] + size_small/2))
				{
					animRotate(4);
					id = 4;
					//setPressed(2);
				}
				if((posX > centers[8] - size_small/2) && (posX < centers[8] + size_small/2) && (posY > centers[9] - size_small/2) && (posY < centers[9] + size_small/2))
				{
					animRotate(5);
					id = 5;
					//setPressed(2);
				}
				if((posX > centers[10] - size_small/2) && (posX < centers[10] + size_small/2) && (posY > centers[11] - size_small/2) && (posY < centers[11] + size_small/2))
				{
					animRotate(6);
					id = 6;
					//setPressed(2);
				}
		
		
		
		//setPressed(id);
		return id;
		
	}
	
	private void setPressed(int id)
	{
		switch(id)
		{
		case 1: logo = BitmapFactory.decodeResource(r, R.drawable.big_logo);
		break;
		case 2: deals = BitmapFactory.decodeResource(r, R.drawable.bt_deals_pressed);
		}
		invalidate();
	}
	
	private void restoreAll()
	{
		logo = BitmapFactory.decodeResource(r, R.drawable.big_logo);
		deals = BitmapFactory.decodeResource(r, R.drawable.bt_deals_normal);
		share = BitmapFactory.decodeResource(r, R.drawable.bt_share_normal);
		directory = BitmapFactory.decodeResource(r, R.drawable.bt_directory_normal);
		promos = BitmapFactory.decodeResource(r, R.drawable.bt_promos_normal);
		coupons = BitmapFactory.decodeResource(r, R.drawable.bt_coupons_normal);
		invalidate();
	}
	
	private void animRotate(int id)
	{
		
		float rot = 0;
		switch(id)
		{
		case 1: rot = 0;
		break;
		case 2: rot = 0;
		break;
		case 3: rot = angle;
		break;
		case 4: rot = 2 * angle;
		break;
		case 5: rot = 3 * angle;
		break;
		case 6: rot = 4 * angle;
		}
		rotate = new RotateAnimation(0, (rot), px, py);
		rotate.setFillAfter(true);
		rotate.setDuration(1500);
		this.startAnimation(rotate);
		
		
		
	}

	

}
