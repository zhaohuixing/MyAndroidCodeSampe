package com.xingzhaohui.newsonmap;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.xingzhaohui.component.*;
import com.xingzhaohui.geocomponent.*;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class NOMMainActivity extends NOMGoogleServiceActivity 
{
	public static MainApplicationController    m_ApplicationController = null; 

	public static void InitializeApplicationController(NOMMainActivity activity)
	{
		if(m_ApplicationController == null)
		{
			m_ApplicationController = new MainApplicationController(activity);
		}
		else 
		{
			m_ApplicationController.SetApplicationActivity(activity);
		}
		m_ApplicationController.Initialize();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        ResourceHelper.SetResourceContext(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     
		setContentView(R.layout.nommain_layout);
		View mainView = getWindow().getDecorView();
		Rect dim = new Rect();
		mainView.getWindowVisibleDisplayFrame(dim);		
		int w = dim.width(); 
		int h = dim.height(); 
		GUILayout.SetScreenSize(w, h);
		NOMMainActivity.InitializeApplicationController(this);
		NOMMainActivity.m_ApplicationController.onCreate(savedInstanceState);
	}

	@Override
	public void onPostCreate(Bundle savedInstanceState) 
	{
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		//delayedHide(100);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onLowMemory()
	 */
	@Override
	public void onLowMemory() 
	{
		// TODO Auto-generated method stub
		NOMMainActivity.m_ApplicationController.onLowMemory();
		super.onLowMemory();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	public void onPause() 
	{
		// TODO Auto-generated method stub
		NOMMainActivity.m_ApplicationController.onPause();
		super.onPause();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	public void onResume() 
	{
		// TODO Auto-generated method stub
		NOMMainActivity.m_ApplicationController.onResume();
		super.onResume();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) 
	{
		// TODO Auto-generated method stub
		NOMMainActivity.m_ApplicationController.onSaveInstanceState(outState);
		super.onSaveInstanceState(outState);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	public void onDestroy() 
	{
		// TODO Auto-generated method stub
		NOMMainActivity.m_ApplicationController.onDestroy();
		super.onDestroy();
	}


	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
/*
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
		}
	};
*/	

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
/*	
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}
*/	
}
