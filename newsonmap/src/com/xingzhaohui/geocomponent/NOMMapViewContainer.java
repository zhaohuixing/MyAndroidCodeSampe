package com.xingzhaohui.geocomponent;

import java.util.HashMap;

import com.xingzhaohui.awsservice.*;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class NOMMapViewContainer extends RelativeLayout implements INOMMapViewDelegate
{
	private INOMMapViewInterface				m_MapView;
	private INOMMapViewContainerDelegate		m_Parent;
	
	private HashMap<String, NOMNewsMetaDataRecord> 	m_DataLut;
	
	private void Initialize(Context context)
	{
		m_MapView = NOMMapViewFactory.CreateDefaultMapView(context, this); 
		m_Parent = null;
		m_DataLut = new HashMap<String, NOMNewsMetaDataRecord>();
	}
	
	public NOMMapViewContainer(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public NOMMapViewContainer(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public NOMMapViewContainer(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		m_MapView = NOMMapViewFactory.CreateDefaultMapView(context, this); 
		m_Parent = null;
	}

	public void AttachDelegate(INOMMapViewContainerDelegate delegate)
	{
		m_Parent = delegate;
	}
	
	public double GetMyLocationLatitude()
	{
		return m_MapView.GetMyLocationLatitude();
	}
	
	public double GetMyLocationLongitude()
	{
		return m_MapView.GetMyLocationLongitude();
	}
	
	
	/* (non-Javadoc)
	 * @see android.widget.RelativeLayout#onLayout(boolean, int, int, int, int)
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) 
	{
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
	
		m_MapView.SetLayout(0, 0, this.getWidth(), this.getHeight());
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

	public void HandleMapViewTouchEvent(double latitude, double longitude)
	{
		if(m_Parent != null)
			m_Parent.HandleMapViewTouchEvent(latitude, longitude);
	}
	
	public void HandleMapMarkerClick(String markerID)
	{
		if(markerID != null && 0 < markerID.length())
		{
			NOMNewsMetaDataRecord data = m_DataLut.get(markerID);
			if(data != null)
			{
				if(m_Parent != null)
					m_Parent.HandleSelectedNewsMetaData(data);
			}
		}
	}
	
	public void AddMapView(View mapview)
	{
		this.addView(mapview);
	}
	
	public void onTimerEvent()
	{
		if(m_MapView != null)
		{	
			m_MapView.onTimerEvent();
		}	
	}

	public void ClearMap()
	{
		if(m_MapView != null)
		{	
			m_MapView.ClearMap();
		}	
		m_DataLut.clear();
	}
	
	public void SetStandardMapType()
	{
		if(m_MapView != null)
		{	
			m_MapView.SetStandardMapType();
		}	
	}
	
	public void SetSatelliteMapType()
	{
		if(m_MapView != null)
		{	
			m_MapView.SetSatelliteMapType();
		}	
	}
	
	public void SetHybridMapType()
	{
		if(m_MapView != null)
		{	
			m_MapView.SetHybridMapType();
		}	
	}

	public void AddQueryMarker(NOMNewsMetaDataRecord queryData, int nImageID)
	{
		if(queryData != null && m_MapView != null)
		{
			//????????????????????????
			//????????????????????????
			String szID = m_MapView.AddMapMarker(queryData.m_NewsLatitude, queryData.m_NewsLongitude, nImageID);
			m_DataLut.put(szID, queryData);
		}
	}
	
	public void GotoMyLocation()
	{
		if(m_MapView != null)
		{	
			m_MapView.GotoMyLocation();
		}	
	}
}

