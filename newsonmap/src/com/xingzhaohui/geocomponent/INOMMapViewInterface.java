package com.xingzhaohui.geocomponent;

import android.os.Bundle;

public interface INOMMapViewInterface 
{
	public void onCreate(Bundle bundle);
	public void onDestroy();
	public void onLowMemory();
	public void onPause();
	public void onResume();
	public void onSaveInstanceState(Bundle outState);
	public void SetLayout(int l, int t, int r, int b); 
	public void RegisterDelegate(INOMMapViewDelegate delegate);
	public void onTimerEvent();
	
	public void ClearMap();
	public void SetStandardMapType();
	public void SetSatelliteMapType();
	public void SetHybridMapType();
	public void GotoMyLocation();
	
	public String AddMapMarker(double lat, double lon, String title, String description, String author, int nResID);
	public String AddMapMarker(double lat, double lon, int nResID);

	public double GetMyLocationLatitude();
	public double GetMyLocationLongitude();
	
}
