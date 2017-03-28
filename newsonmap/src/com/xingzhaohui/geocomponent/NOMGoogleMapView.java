package com.xingzhaohui.geocomponent;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class NOMGoogleMapView extends RelativeLayout implements INOMMapViewInterface, OnMapClickListener, OnMarkerClickListener, OnMyLocationChangeListener, OnCameraChangeListener
{
	private MapView 					m_MapView;
	private GoogleMap 					m_Map;
	private INOMMapViewDelegate			m_Parent;
	private double 						m_MyLocationLatitude;
	private double 						m_MyLocationLongitude;
	private boolean						m_bNeedZoomMap;
	
	private static NOMGoogleServiceActivity		g_LocationService = null;
	
	public static void SetLocationService(NOMGoogleServiceActivity service)
	{
		g_LocationService = service;
	}
	
	private void Initialize(Context context)
	{
		GoogleMapOptions options = new GoogleMapOptions();
		options.camera(new CameraPosition(new LatLng(0, 0), 1, 0, 0));          
		//m_Map = new MapView(context, options);
		m_MapView = new MapView(context);
		this.addView(m_MapView);
		m_Parent = null;
		m_Map = null;
		m_MyLocationLatitude = 0.0;
		m_MyLocationLongitude = 0.0;
		NOMGoogleServiceActivity.SetGoogleMapView(this);
		m_bNeedZoomMap = false;
		try
		{
			MapsInitializer.initialize(context);
		}
		catch(GooglePlayServicesNotAvailableException e)
		{
			
		}
		SetupMapIfNeeded();
	}
	
	protected void SetupMapIfNeeded()
	{
		if(m_Map == null && m_MapView != null)
		{
			m_Map = m_MapView.getMap();
		}
		if(m_Map != null)
		{
			m_Map.setOnMapClickListener(this);
			m_Map.setOnMyLocationChangeListener(this);
			m_Map.setOnMarkerClickListener(this);
			m_Map.setOnCameraChangeListener(this);
			m_Map.setMyLocationEnabled(true);
			this.GotoMyLocation();
		}
	}
	
	public double GetMyLocationLatitude()
	{
		return m_MyLocationLatitude;
	}
	
	public double GetMyLocationLongitude()
	{
		return m_MyLocationLongitude;
	}
	
	public void RegisterDelegate(INOMMapViewDelegate delegate)
	{
		m_Parent = delegate;
		m_Parent.AddMapView(this);
	}
	
	public NOMGoogleMapView(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public NOMGoogleMapView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public NOMGoogleMapView(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public void onCreate(Bundle bundle)
	{
		m_MapView.onCreate(bundle);
	}
	
	public void onDestroy()
	{
		m_MapView.onDestroy();
	}
	
	public void onLowMemory()
	{
		m_MapView.onLowMemory();
	}
	
	public void onPause()
	{
		m_MapView.onPause();
	}
	
	public void onResume()
	{
		m_MapView.onResume();
	}
	
	public void onSaveInstanceState(Bundle outState)
	{
		m_MapView.onSaveInstanceState(outState);
	}

	public void onTimerEvent()
	{
		if(m_Map == null && m_MapView != null)
		{
			m_Map = m_MapView.getMap();
			if(m_Map != null)
			{
				m_Map.setOnMapClickListener(this);
				m_Map.setOnMyLocationChangeListener(this);
				m_Map.setOnMarkerClickListener(this);
				m_Map.setOnCameraChangeListener(this);
				m_Map.setMyLocationEnabled(true);
				this.GotoMyLocation();
			}
		}	
	}
	
	public void SetLayout(int l, int t, int r, int b)
	{
		this.layout(l, t, r, b);
	}
	
	public void ClearMap()
	{
		if(m_Map != null)
		{
			m_Map.clear();
		}
	}
	
	public void SetStandardMapType()
	{
		if(m_Map != null)
		{
			m_Map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		}
	}
	
	public void SetSatelliteMapType()
	{
		if(m_Map != null)
		{
			m_Map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		}
	}
	
	public void SetHybridMapType()
	{
		if(m_Map != null)
		{
			m_Map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		}
	}
	
	public void GotoMyLocation()
	{
		if(NOMGoogleMapView.g_LocationService != null)
		{
			NOMGoogleMapView.g_LocationService.getLocation();
		}
		if(m_Map != null)
		{
			m_Map.moveCamera (CameraUpdateFactory.zoomTo(NOMGEOConstants.DEFAULT_GOOGLEMAP_ZOOM));
			m_Map.animateCamera (CameraUpdateFactory.newLatLng(new LatLng(m_MyLocationLatitude, m_MyLocationLongitude)));
		}
	}
	
	/* (non-Javadoc)
	 * @see android.widget.RelativeLayout#onLayout(boolean, int, int, int, int)
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) 
	{
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		m_MapView.layout(0, 0, this.getWidth(), this.getHeight());
	}
	
	@Override
	public void onMapClick(LatLng point) 
	{
		if(m_Parent != null && point != null)
		{
			double lat = point.latitude;
			double lng = point.longitude;
			m_Parent.HandleMapViewTouchEvent(lat, lng);
		}	
	}
	
	@Override
	public  boolean onMarkerClick(Marker marker)	
	{
		if(marker != null)
		{
			String szID = marker.getId();
			if(m_Parent != null)
				m_Parent.HandleMapMarkerClick(szID);
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	@Override
	public void onMyLocationChange(Location mylocation) 
	{
		// TODO Auto-generated method stub
		m_MyLocationLatitude = mylocation.getLatitude();
		m_MyLocationLongitude = mylocation.getLongitude();
	}
	
	@Override
	public void onCameraChange(CameraPosition arg0) 
	{
		// TODO Auto-generated method stub
		//if(m_bNeedZoomMap == true)
		//{
		//	m_bNeedZoomMap = false;
		//	m_Map.moveCamera (CameraUpdateFactory.zoomTo(11.0f));
		//}
	}
	
	public String AddMapMarker(double lat, double lon, String title, String description, String author, int nResID)
	{
		String szID = null;
		if(m_Map != null)
		{
			Marker marker = m_Map.addMarker(new MarkerOptions()
            .position(new LatLng(lat, lon))
            .title(title)
            .snippet(description)
            .icon(BitmapDescriptorFactory.fromResource(nResID)));
			szID = marker.getId();
		}
		return szID;
	}
	
	public String AddMapMarker(double lat, double lon, int nResID)
	{
		String szID = null;
		if(m_Map != null)
		{
			Marker marker = m_Map.addMarker(new MarkerOptions()
            .position(new LatLng(lat, lon))
            //.title("Test")
            //.snippet("Test info")
            .icon(BitmapDescriptorFactory.fromResource(nResID)));
			szID = marker.getId();
		}
		return szID;
	}

	public void SetMyLocation(double lat, double lon)
	{
		m_MyLocationLatitude = lat;
		m_MyLocationLongitude = lon;
	}

}
