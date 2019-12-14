package com.xgadget.ChuiNiuLite;

import android.graphics.Rect;
import android.view.View;
import android.view.View.OnClickListener;

import com.xgadget.uimodule.MainViewMenu;
import com.xgadget.uimodule.MyAbsoluteLayout;
import com.xgadget.uimodule.MainViewMenuItem;

public class CMainMenuController 
{
	private MainMenuView					m_BackgroundView;
	private MainViewMenuItem			m_SoundMenuItem;
	private MainViewMenuItem			m_BKMenuItem;
	
	
	public CMainMenuController() 
	{
		// TODO Auto-generated constructor stub
		m_BackgroundView = null;
		m_SoundMenuItem = null;
		m_BKMenuItem = null;
	}

	public void LoadMenuUI()
	{
		//MainViewMenu.SetMenuCount(5);
		MainViewMenu.SetMenuCount(4);

		Rect rect = CGameLayout.GetGameSceneDeviceRect();
		MainViewMenu.SetCenter(rect.width()/2, rect.height()/2);
		
		m_BackgroundView = (MainMenuView)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.MenuView);
		m_BackgroundView.m_LayoutContainer = (MyAbsoluteLayout)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.GameMenuLayout);

		m_SoundMenuItem = (MainViewMenuItem)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.MenuItemSoundSwitch);
		m_SoundMenuItem.m_LayoutContainer = (MyAbsoluteLayout)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.GameMenuLayout);
		m_SoundMenuItem.SetMenuIndex(2);
		//m_SoundMenuItem.SetBitmapID(R.drawable.musicicon);
		UpdateSoundMenuDisplay();
		m_SoundMenuItem.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				UpdateSoundConfigure();	
			}
		});
		
		
		m_BKMenuItem = (MainViewMenuItem)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.MenuItemBackgroundSetup);
		m_BKMenuItem.m_LayoutContainer = (MyAbsoluteLayout)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.GameMenuLayout);
		m_BKMenuItem.SetMenuIndex(1);
		//m_BKMenuItem.SetBitmapID(R.drawable.nighticon);
		UpdateBackgroundMenuDisplay();
		m_BKMenuItem.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				UpdateBackGroundType();	
			}
		});

		MainViewMenuItem ToolMenuItem = (MainViewMenuItem)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.MenuItemConfigure);
		ToolMenuItem.m_LayoutContainer = (MyAbsoluteLayout)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.GameMenuLayout);
		ToolMenuItem.SetMenuIndex(0);
		ToolMenuItem.SetBitmapID(R.drawable.toolicon);
		ToolMenuItem.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				OpenConfigureSubMenuView();	
			}
		});

		MainViewMenuItem ScoreMenuItem = (MainViewMenuItem)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.MenuItemScore);
		ScoreMenuItem.m_LayoutContainer = (MyAbsoluteLayout)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.GameMenuLayout);
		ScoreMenuItem.SetMenuIndex(3);
		ScoreMenuItem.SetBitmapID(R.drawable.scoreicon);
		ScoreMenuItem.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				OpenScoreViews();	
			}
		});

		//MainViewMenuItem PostMenuItem = (MainViewMenuItem)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.MenuItemPost);
		//PostMenuItem.m_LayoutContainer = (MyAbsoluteLayout)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.GameMenuLayout);
		//PostMenuItem.SetMenuIndex(4);
		//PostMenuItem.SetBitmapID(R.drawable.posticon);
		
	}
	
    private void UpdateSoundConfigure()
    {
		boolean bEnable = CConfiguration.IsSoundEnable();
		if(bEnable == true)
		{
			CConfiguration.SetSoundEnable(false);
			//Configuration.setPlaySoundEffect(false);
			//ScoreRecord.SetSoundEnable(false);
			//ScoreRecord.SaveScore();
			//SoundSource.StopAllPlayingSound();
		}
		else
		{
			CConfiguration.SetSoundEnable(true);
			CFCActivity.m_ApplicationController.m_GameController.PlayBackgroundSound();
			//Configuration.setPlaySoundEffect(true);
			//ScoreRecord.SetSoundEnable(true);
			//ScoreRecord.SaveScore();
			//this.m_GameController.PlayCurrentGameStateSound();
		}
		
    	UpdateSoundMenuDisplay();
    }
    
    private void UpdateSoundMenuDisplay()
    {
    	if(m_SoundMenuItem != null)
    	{	
    		boolean bEnable = CConfiguration.IsSoundEnable();;
    		if(bEnable == true)
    			m_SoundMenuItem.SetBitmapID(R.drawable.muteicon);
    		else
    			m_SoundMenuItem.SetBitmapID(R.drawable.musicicon);
    	}    			
    }
	
    private void UpdateBackGroundType()
    {
    	int nType = CConfiguration.GetGameBackground();
    	nType = (nType + 1)%3;
    	CConfiguration.SetGameBackground(nType);
    	if(m_BackgroundView != null)
    		m_BackgroundView.invalidate();

		CFCActivity.m_ApplicationController.m_GameController.UpdateGameUI();
    	
    	UpdateBackgroundMenuDisplay();
    }

    private void UpdateBackgroundMenuDisplay()
    {
    	if(m_BKMenuItem != null)
    	{	
    		int nType = CConfiguration.GetGameBackground();
    		if(nType == CConfiguration.GAME_BACKGROUND_NIGHTSKY)
    			m_BKMenuItem.SetBitmapID(R.drawable.nighticon);
    		else if(nType == CConfiguration.GAME_BACKGROUND_CHECKPATTERN)
    			m_BKMenuItem.SetBitmapID(R.drawable.checkicon);
    		else 
    			m_BKMenuItem.SetBitmapID(R.drawable.dayicon);
    	}
    }
    
    private void OpenConfigureSubMenuView()
    {
    	CFCActivity.m_ApplicationController.OpenConfigureSubMenuView();
    }
    
    private void OpenScoreViews()
    {
    	//????
    	CFCActivity.m_ApplicationController.OpenScoreView();
    }
}
