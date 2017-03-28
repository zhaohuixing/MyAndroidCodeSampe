package com.xingzhaohui.geocomponent;

import android.view.View;

public interface INOMMapViewDelegate 
{
	public void HandleMapViewTouchEvent(double latitude, double longitude);
	public void HandleMapMarkerClick(String markerID);
	public void AddMapView(View mapview);
	public double GetMyLocationLatitude();
	public double GetMyLocationLongitude();
}
