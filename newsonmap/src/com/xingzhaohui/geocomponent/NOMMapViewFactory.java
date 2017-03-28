package com.xingzhaohui.geocomponent;

import android.content.Context;

public class NOMMapViewFactory 
{
	protected static NOMGoogleMapView CreateGoogleMapView(Context context, INOMMapViewDelegate delegate)
	{
		NOMGoogleMapView mapView = new NOMGoogleMapView(context);
		mapView.RegisterDelegate(delegate);
		return mapView;
	}

	public static INOMMapViewInterface CreateDefaultMapView(Context context, INOMMapViewDelegate delegate)
	{
		INOMMapViewInterface pRet = null;
		pRet = NOMMapViewFactory.CreateGoogleMapView(context, delegate);
		return pRet;
	}
}
