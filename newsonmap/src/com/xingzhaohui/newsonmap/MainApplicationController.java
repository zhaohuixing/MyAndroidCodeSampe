package com.xingzhaohui.newsonmap;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class MainApplicationController 
{
	private MainWindowFrame		m_MainAppView;
	private NOMMainActivity		m_ApplicationActivity;
	
	private Handler 				m_TimerHandler;
	private Runnable 				m_UpdateTimeTask;

	private static final int  		TIMER_INTERVAL_MS = 10000;
    private long					m_TimingCount;	
	
	
	public void onCreate(Bundle bundle)
	{
		if(m_MainAppView != null)
			m_MainAppView.onCreate(bundle);
	}
	
	public void onDestroy()
	{
		if(m_MainAppView != null)
			m_MainAppView.onDestroy();
	}
	
	public void onLowMemory()
	{
		if(m_MainAppView != null)
			m_MainAppView.onLowMemory();
	}
	
	public void onPause()
	{
		if(m_MainAppView != null)
			m_MainAppView.onPause();
	}
	
	public void onResume()
	{
		if(m_MainAppView != null)
			m_MainAppView.onResume();
	}
	
	public void onSaveInstanceState(Bundle outState)
	{
		if(m_MainAppView != null)
			m_MainAppView.onSaveInstanceState(outState);
	}
	
	public MainApplicationController(NOMMainActivity activity)
	{
		m_MainAppView = null;
		m_ApplicationActivity = activity;
		m_TimerHandler = new Handler();
		m_UpdateTimeTask = null;
	}
	
	public void SetApplicationActivity(NOMMainActivity activity)
	{
		m_ApplicationActivity = activity;
	}
	
	public NOMMainActivity GetApplicationActivity()
	{
		return m_ApplicationActivity;
	}
	
	public void SetMainAppView(MainWindowFrame mainView)
	{
		m_MainAppView = mainView;
		m_MainAppView.SetAppController(this);
	}
	
	public void Initialize()
	{
		if(m_ApplicationActivity != null)
		{
			m_MainAppView = (MainWindowFrame)m_ApplicationActivity.findViewById(R.id.MainWindowLayout);
			m_MainAppView.SetAppController(this);
			m_MainAppView.LoadChildControls();
		}
		StartTimer();
	}
	
	public void StartTimer()
	{
		m_UpdateTimeTask = new Runnable() 
		{
			public void run() 
			{
				if(m_TimerHandler != null)
				{	
					m_TimerHandler.postDelayed(m_UpdateTimeTask, TIMER_INTERVAL_MS);
					timerUpdate();
				}					
			}
		};	
		
		m_TimerHandler.removeCallbacks(m_UpdateTimeTask);
		m_TimerHandler.postDelayed(m_UpdateTimeTask, TIMER_INTERVAL_MS);
	}
	
	private void timerUpdate() 
	{
		if(m_MainAppView != null)
		{
			m_MainAppView.onTimerEvent();
		}
	}
	
	public void OnClickEvent(View v)
	{
		if(m_MainAppView != null)
		{
			m_MainAppView.OnClickEvent(v);
		}
	}
}
