package com.nest5.androidClient;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.location.Location;


import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MyOverlay extends Overlay {
	Location location = null;
	GeoPoint geoPositon; 
	private final int mRadius = 5;
	private Bitmap pin;
	private Context mContext;
	
	MyOverlay(Context context)
	{
		this.mContext = context;
		Resources r = mContext.getResources();
		pin = BitmapFactory.decodeResource(r, R.drawable.nest5_pin);
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow)
	{
		Projection projection = mapView.getProjection();
		GeoPoint geoPoint;
		
		if(shadow == false)
		{
			
			if(location != null)
			{
				Double latitude = location.getLatitude();
				Double longitude = location.getLongitude();
				
				geoPoint = new GeoPoint(latitude.intValue(),longitude.intValue());
			}
			else
			{
				geoPoint = geoPositon;
			}
			
			
			Point point = new Point();
			projection.toPixels(geoPoint, point);
			/*RectF oval = new RectF(point.x - mRadius,point.y - mRadius, point.x + mRadius, point.y + mRadius);
			Paint paint = new Paint();
			paint.setARGB(250, 255, 255, 255);
			paint.setAntiAlias(true);
			paint.setFakeBoldText(true);
			
			Paint backPaint = new Paint();
			backPaint.setARGB(250, 255, 255, 255);
			backPaint.setAntiAlias(true);
			RectF backRect = new RectF(point.x + 2 - mRadius,point.y - 3*mRadius, point.x + 65, point.y + mRadius);
			
			canvas.drawOval(oval, paint);
			canvas.drawRoundRect(backRect,5,5, backPaint);*/
			canvas.drawBitmap(pin,point.x - pin.getWidth() / 2, point.y - pin.getHeight() / 2, null);
			
			
		}
		else
		{
			
		}
		super.draw(canvas, mapView, shadow);
	}
	
	@Override
	public boolean onTap (GeoPoint point, MapView mapView)
	{
		return false;
	}
	
	public Location getLocation()
	{
		return this.location;
	}
	
	public void setLocation(Location location)
	{
		this.location = location;
		
		
	}
	
	public void setLocation(GeoPoint geo)
	{
		this.geoPositon = geo;
		
		
	}

}
