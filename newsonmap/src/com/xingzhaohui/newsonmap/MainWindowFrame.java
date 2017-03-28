package com.xingzhaohui.newsonmap;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.xingzhaohui.awsservice.*;
import com.xingzhaohui.geocomponent.*;
import com.xingzhaohui.guicomponent.*;

public class MainWindowFrame extends RelativeLayout implements ICustomActionBarControllerDelegate, INOMMapViewContainerDelegate
{
	private NOMMapViewContainer				m_MapView;
	private CustomActionBarController		m_ActionBarController;
	private MainApplicationController		m_AppController;
	private MainMenuController				m_MainMenuController;
	
	
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
	
	
	private void Initialize(Context context)
	{
		m_MapView = new NOMMapViewContainer(context);
		m_MapView.AttachDelegate(this);
		this.addView(m_MapView);
		m_ActionBarController = new CustomActionBarController(this);
		m_MainMenuController = new  MainMenuController(context, this);
	}

	
	public MainWindowFrame(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public MainWindowFrame(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public MainWindowFrame(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}


	/* (non-Javadoc)
	 * @see android.widget.RelativeLayout#onLayout(boolean, int, int, int, int)
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		m_MapView.layout(0, 0, this.getWidth(), this.getHeight());
		
		if(m_ActionBarController != null)
		{
			m_ActionBarController.UpdateLayout();
		}
		if(m_MainMenuController != null)
		{
			m_MainMenuController.UpdateLayout();
		}
	}


	/* (non-Javadoc)
	 * @see android.view.View#onSizeChanged(int, int, int, int)
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) 
	{
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		m_MapView.layout(0, 0, this.getWidth(), this.getHeight());

		if(m_ActionBarController != null)
		{
			m_ActionBarController.UpdateLayout();
		}	
		if(m_MainMenuController != null)
		{
			m_MainMenuController.UpdateLayout();
		}
	}

	public void SetAppController(MainApplicationController appController)
	{
		m_AppController = appController;
	}
	
	public Activity GetApplicationActivity()
	{
		return m_AppController.GetApplicationActivity();
	}
	
	public void AddChild(View view)
	{
		this.addView(view);
	}
	
	public int GetContainerHeight()
	{
		return this.getHeight();
	}
	
	public int GetContainerWidth()
	{
		return this.getWidth();
	}
	
	public int GetContainerLeft()
	{
		return this.getLeft();
	}
	
	public int GetContainerTop()
	{
		return this.getTop();
	}
	
	public void LoadChildControls()
	{
		if(m_ActionBarController != null)
		{
			Bitmap btnBmp = ResourceHelper.GetPopButtonBitmap();
			m_ActionBarController.CreatePopupButton(btnBmp);

			Bitmap find200 = ResourceHelper.GetFind200Bitmap();
			Bitmap set200 = ResourceHelper.GetSetting200Bitmap();
			Bitmap post200 = ResourceHelper.GetPost200Bitmap();
			m_ActionBarController.CreateActionBarButtons(find200, set200, post200);
		}
		if(m_MainMenuController != null)
		{
			m_MainMenuController.LoadMenuItems();
			m_MainMenuController.CloseMenus();
		}
	}

	public void CloseMenuAndActionBar()
	{
		if(m_ActionBarController != null)
		{
			m_ActionBarController.ShowPopupButton(true);
			m_ActionBarController.ShowActionBar(false);
		}
		if(m_MainMenuController != null)
		{
			m_MainMenuController.CloseMenus();
		}
	}
	public void OnClickEvent(View v)
	{
		CloseMenuAndActionBar();
	}
	
	public void HandleMapViewTouchEvent(double latitude, double longitude)
	{
		CloseMenuAndActionBar();
		//????????????????
		//????????????????
		//????????????????
		//????????????????
		/*
		NOMNewsMetaDataRecord queryData = new NOMNewsMetaDataRecord();
		queryData.m_NewsID = NOMSystemHelper.GenerateGUID();
		queryData.m_NewsLatitude = latitude;
		queryData.m_NewsLongitude = longitude;
		queryData.m_NewsMainCategory = NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS;
		queryData.m_NewsSubCategory = NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_FOODANDDRINK;
		int nImageID =  ResourceHelper.GetMapMarkerImageID(NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS, NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_FOODANDDRINK, 0);
		if(m_MapView != null)
		{
			m_MapView.AddQueryMarker(queryData, nImageID);
		}
		*/
	}
	
	public void HandleSelectedNewsMetaData(NOMNewsMetaDataRecord data)
	{
		
	}
	
	public int GetActionBarButtonTop()
	{
		int nRet = 0;
		
		if(m_ActionBarController != null)
		{
			nRet = m_ActionBarController.GetActionBarButtonTop();
		}
		
		return nRet;
	}

	public int GetSearchButtonCenterX()
	{
		int nRet = 0;
		
		if(m_ActionBarController != null)
		{
			nRet = m_ActionBarController.GetSearchButtonCenterX();
		}
		
		return nRet;
	}

	public int GetSettingButtonCenterX()
	{
		int nRet = 0;
		
		if(m_ActionBarController != null)
		{
			nRet = m_ActionBarController.GetSettingButtonCenterX();
		}
		
		return nRet;
	}

	public int GetPostButtonCenterX()
	{
		int nRet = 0;
		
		if(m_ActionBarController != null)
		{
			nRet = m_ActionBarController.GetPostButtonCenterX();
		}
		
		return nRet;
	}
	
	public void HandleSearchButtonClicked()
	{
		if(m_MainMenuController != null)
		{
			m_MainMenuController.OpenSearchMenu();
		}
	}
	
	public void HandleSettingButtonClicked()
	{
		if(m_MainMenuController != null)
		{
			m_MainMenuController.OpenSettingMenu();
		}
	}
	
	public void HandlePostButtonClicked()
	{
		if(m_MainMenuController != null)
		{
			m_MainMenuController.OpenPostMenu();
		}
	}
	
	public void onTimerEvent()
	{
		if(m_MapView != null)
		{	
			m_MapView.onTimerEvent();
		}	
	}
	
	public void HandleMenuItemEvent(int menuItemID)
	{
		if(menuItemID == AppConstants.GUIID_SETTINGMENU_ITEM_MAPSTANDARD_ID)
		{
			if(m_MapView != null)
			{	
				m_MapView.SetStandardMapType();
			}
			return;
		}
		else if(menuItemID == AppConstants.GUIID_SETTINGMENU_ITEM_MAPSATELLITE_ID)
		{
			if(m_MapView != null)
			{	
				m_MapView.SetSatelliteMapType();
			}
			return;
		}
		else if(menuItemID == AppConstants.GUIID_SETTINGMENU_ITEM_MAPHYBRID_ID)
		{
			if(m_MapView != null)
			{	
				m_MapView.SetHybridMapType();
			}
			return;
		}
		else if(menuItemID == AppConstants.GUIID_SETTINGMENU_ITEM_CLEARMAP_ID)
		{
			if(m_MapView != null)
			{	
				m_MapView.ClearMap();
			}
			return;
		}
		else if(menuItemID == AppConstants.GUIID_SETTINGMENU_ITEM_SHOWMYLOCATION_ID)
		{
			if(m_MapView != null)
			{	
				m_MapView.GotoMyLocation();
			}
			return;
		}
		else 
		{
			NOMNewsMetaDataRecord queryData = new NOMNewsMetaDataRecord();
			queryData.m_NewsID = NOMSystemHelper.GenerateGUID();
			queryData.m_NewsLatitude = 51.117101;
			queryData.m_NewsLongitude = -114.118423;
			queryData.m_NewsMainCategory = NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS;
			queryData.m_NewsSubCategory = NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_FOODANDDRINK;
			int nImageID =  ResourceHelper.GetMapMarkerImageID(NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS, NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_FOODANDDRINK, 0);
			if(m_MapView != null)
			{
				m_MapView.AddQueryMarker(queryData, nImageID);
			}
			
		}
	}
}
