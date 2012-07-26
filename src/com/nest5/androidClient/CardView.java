package com.nest5.androidClient;




import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class CardView extends View {
	
	private static String TAG = "CompassView";
	
	
	private Bitmap goodStamp;
	private Bitmap badStamp;
	private Bitmap couponStamp;
	private Bitmap resizedGoodBitmap;
	private Bitmap resizedBadBitmap;
	private Bitmap resizedCouponBitmap;
	private Context mContext;
	private int px;
	private int py;
	double rad_size;
	double margin;
	private Resources r;

	
	private Paint textPaint;
	
	private int textHeight;
	Typeface BebasFont;
    Typeface VarelaFont;
    private double densityMultiplier;
	private float scaledPx;
	
	private int total = 2;
	private int good = 1;
	private boolean coupon = false;
	private double rows;
	private int rowDivider = 5;
	double screenWidth;
	double screenHeight;
	
	
	
	
	
	
	//required for whe in code creation
	public CardView(Context context)
	{
		super(context);
		mContext = context;
		initCompassView();
		
	}
	
	//requeired for whe inflating
	
	public CardView (Context context, AttributeSet attrs)
	{
		super(context,attrs);
		mContext = context;
		initCompassView();
		
	}
	
	public CardView (Context context, AttributeSet attrs, int _total, int _good,boolean _coupon)
	{
		super(context,attrs);
		r = this.getResources();
		densityMultiplier = r.getDisplayMetrics().density;
		Log.i("InitialActivity","Alto: "+String.valueOf((int) (densityMultiplier * 160)));
		this.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,(int) (densityMultiplier * 160 )));
		mContext = context;
		total = _total;
		good = _good;
		coupon = _coupon;
		initCompassView();
	}
	
	public CardView (Context context, AttributeSet attrs,int defaultStyle)
	{
		super(context,attrs,defaultStyle);
		mContext = context;
		initCompassView();
		
	}
	
	protected void initCompassView()
	{
		setFocusable(true);
		 
		
		goodStamp = BitmapFactory.decodeResource(r, R.drawable.big_logo);
		badStamp = BitmapFactory.decodeResource(r, R.drawable.big_logo_trans);
		couponStamp = BitmapFactory.decodeResource(r, R.drawable.estrella_cupon);
		//
		
		
		
		BebasFont= Typeface.createFromAsset(mContext.getAssets(), "fonts/BebasNeue.otf");
	    VarelaFont= Typeface.createFromAsset(mContext.getAssets(), "fonts/Varela-Regular.otf");
		
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setTypeface(BebasFont);
		textPaint.setColor(r.getColor(R.color.text_color));
		
	    //Log.i("InitialActivity",String.valueOf(screenWidth)+" "+String.valueOf(screenHeight));
		//scaledPx = 17 * densityMultiplier;
		textPaint.setTextSize(scaledPx);
		
		textHeight = (int) textPaint.measureText("yY");
		
		
		
		

		

		
		
		
		
		
		
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		
		/*int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
		   int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
		   Log.i("CAAAAA","Midiendo:"+ String.valueOf(parentHeight));
		   this.setMeasuredDimension(parentWidth, 900);
		   this.setLayoutParams(new LinearLayout.LayoutParams(parentWidth,900));
		   super.onMeasure(widthMeasureSpec, 200);*/
		 
		int measureWidth = measure(widthMeasureSpec);
		int measureHeight = measure(heightMeasureSpec);
		screenWidth = measureWidth;
	     screenHeight = measureHeight;
		
		
		setMeasuredDimension(measureWidth,measureHeight);
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		Log.i("InitialActivity",String.valueOf(screenWidth)+" "+String.valueOf(screenHeight));
		double width = goodStamp.getWidth(); //pixeles

		double height = goodStamp.getHeight(); //pixeles
		
		double width2 = couponStamp.getWidth();
		double height2 = couponStamp.getHeight();
		
		
		
		double minSpec = (screenWidth / densityMultiplier);  // dpi
		//Log.i("InitialActivity","minspec: "+String.valueOf(minSpec)+" screenWidth: "+String.valueOf(screenWidth)+" densityMultiplier"+String.valueOf(densityMultiplier));
		// create a matrix for the manipulation

				Matrix matrix = new Matrix();
				Matrix matrix2 = new Matrix();
				if(total >= 10)
				{
					rows =  Math.ceil(total / 5);
					
				}
				else
				{
					if(total <= 5)
					{
						rows = 1;
					}
					else // 6,7,8,9
					{
						rows = 2;
							
					}
				}
				//Log.i("InitialActivity","ROWS: "+String.valueOf(rows)+"TOTAL: "+String.valueOf(total));
				// resize the bit map
				
				double topRad = screenWidth/5;//pixeles (480px /5 círculos por fila)
				double maxRad = (160 * densityMultiplier + 0.5f)/rows; //(Altura de 160 dpi(pasado a pixeles) / número de filas que se necesiten)
				if(!coupon)
				{					 
					 rad_size = Math.min(maxRad,topRad); //Se usa el más pequeño de los dos diámetros
				}
				else
				{
					rad_size = 160;
				}
				
							
				
				double scaleWidth = (rad_size ) / width; // se saca factor de escala para imagen en pixeles
				double scaleHeight = ( rad_size)/ height; //pixeles
				double scaleWidth2 = (rad_size ) / width2; // se saca factor de escala para imagen en pixeles
				double scaleHeight2 = ( rad_size)/ height2; //pixeles
				
				
				matrix.postScale((float)scaleWidth, (float)scaleHeight); //matriz para escalar
				matrix2.postScale((float)scaleWidth2, (float)scaleHeight2); //matriz para escalar
				
				
				// recreate the new Bitmap
				
				
				 resizedGoodBitmap = Bitmap.createBitmap(goodStamp, 0, 0, (int)width, (int)height, matrix, false); //imágenes escaladas
				 resizedBadBitmap = Bitmap.createBitmap(badStamp, 0, 0, (int)width, (int)height, matrix, false);
				 resizedCouponBitmap = Bitmap.createBitmap(couponStamp, 0, 0, (int)width2, (int)height2, matrix2, false);
				 
				 
				 
				 //rowDivider = (int)(minSpec / rad_size) + 1;
				 rowDivider = 5; //siempre parte cada 5, lo que varía es el número de filas
		
		int i = 0;
		
		px = (int)((screenWidth/2) - (5*rad_size)/2); // inicia en el punto donde quita del total de ancho de la pantalla lo que se llevan 5 círculos con el radio escogido
		py = (int)(((160*densityMultiplier + 0.5f)/2) - (rows * rad_size)/2);
		if(total < 5)
		{
			px = (int)((screenWidth/2) - (total*rad_size)/2);
		}
		//margin = 0.0625 * (densityMultiplier * 160);
		//Log.i("WERWRWER",String.valueOf(rad_size)+" Margen: "+String.valueOf(margin));
		int j = 0;
		int h = 0;
		while((i <= rows) && (j < total))
		{
			
				if((j % rowDivider == 0) && (j != 0))
				{
					px = (int)((screenWidth/2) - (5*rad_size)/2);
					//py += rad_size + margin;
					py += rad_size;
					i++;
				}
				if(coupon)
				{
					canvas.drawBitmap(resizedCouponBitmap, px, py, null);
				}
				else
				{
					if(h < good)
			           {
			        	   canvas.drawBitmap(resizedGoodBitmap, px, py, null);
			        	   h++;
			           }
			           else
			           {
			        	   canvas.drawBitmap(resizedBadBitmap, px, py, null);  
			           }
				}
				
	           
				
				px += rad_size;//rad_size + margin;
				j++;
			
			
			
		}
		
		
		
		
		
		
	}
	
	/*@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		
		
		//return true;
		return super.onTouchEvent(event);
		
		
	}*/
	
	 
	
	private int measure(int measureSpec)
	{
		int result = 0;
		//decode teh measurements
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		if(specMode== MeasureSpec.UNSPECIFIED)
		{
			//Return a default size of 200 if no bounds are specified
			result = 320;
		}else{
			//As we want to fill the available space always return the full available bounds
			result = specSize;
		}
		return result;
	}
	
	
	
	
	
	
	
	
	

	

}
