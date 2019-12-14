package com.e_gadget.MindFire;

import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class CSplashScreen 
{
	private static Drawable 			m_GameBeginAnimator = null;
	private static Drawable 			m_BkgndSnapshot = null;
	private static boolean				m_bIsRunning = false;
	private static int					m_nGameBeginClick = -1;
	private static final int			SPLASH_SINGLE_STEP = 30;
	private static CGame				m_Game = null;
	private static final int			BKGND_PAINT_RED = 255;
	private static final int			BKGND_PAINT_GREEN = 0;
	private static final int			BKGND_PAINT_BLUE = 255;
	private static final int			BKGND_PAINT_OPQUE = 164;
	
	
	//public static void Initialize(Vector<Drawable> 	splashimages, Drawable 	gameimg)
	public static void Initialize(Drawable 	gameimg)
	{
		//m_WelcomeAnimator = splashimages;
		m_GameBeginAnimator = gameimg;
		m_BkgndSnapshot = null;
	}

	public static void SetImage(Drawable gameimg)
	{
		m_GameBeginAnimator = gameimg;
	}

	public static void SetSnapshot(Drawable image)
	{
		m_BkgndSnapshot = image;
	}

	public static void AttachGame(CGame	game)
	{
		m_Game = game;	
	}
	
	public static void StartSplashScreen()
	{
		m_bIsRunning = true;
		m_nGameBeginClick = 0;
	}

	public static void Stop()
	{
		m_bIsRunning = false;
		m_nGameBeginClick = -1;
		m_BkgndSnapshot = null;
		m_GameBeginAnimator = null;
		CGameState.SetGameRunning();
	}

	public static void SwitchSplashScreenState()
	{
	}
	
	public static boolean IsRunning()
	{
		return m_bIsRunning;
	}
	
	public static void OnDraw(Canvas canvas)
	{
		if(m_bIsRunning == false || CDealLayout.IsInitialized() == false)
		{	
			return;
		}			
		
		if(0 <= m_nGameBeginClick)
			OnDrawGameBegin(canvas);
			
	}

	private static void OnDrawGameBegin(Canvas canvas)
	{
		if(0 <= m_nGameBeginClick && m_nGameBeginClick < SPLASH_SINGLE_STEP)
		{
			int nIndex = m_nGameBeginClick;
			OnDrawGameBeginRotation(canvas, nIndex);
		}
	}	

	private static void OnDrawGameBeginRotation(Canvas canvas, int nFrame)
	{
		int w = CDealLayout.WindowWidth();
		int h = CDealLayout.WindowHeight();
		Rect rect;
		if(m_BkgndSnapshot != null)
		{
			rect = new Rect(0, 0, w, h);
			m_BkgndSnapshot.setBounds(rect);
			m_BkgndSnapshot.draw(canvas);
		}

		canvas.save();
		int cx = CDealLayout.WindowCenterX();
		int cy = CDealLayout.WindowCenterY();


		float dX = ((float)nFrame)/((float)SPLASH_SINGLE_STEP);//SPLASH_HALF_STEP);

		float dY = ((float)(SPLASH_SINGLE_STEP-nFrame))/((float)SPLASH_SINGLE_STEP);//25.0f;
		float fAngle = dY*180.0f;
		canvas.rotate(fAngle, (float)cx, (float)cy);

		float fw = ((float)CDealLayout.WindowWidth())*dX;
		float fh = ((float)CDealLayout.WindowHeight())*(1.0f - dY);
		w = (int)fw;
		h = (int)fh;
		rect = new Rect(cx-w/2, cy-h/2, cx+w/2, cy+h/2);
		m_GameBeginAnimator.setBounds(rect);
		
		m_GameBeginAnimator.draw(canvas);

		canvas.restore();
	}
	
	public static void OnTimerEvent()
	{
		if(m_bIsRunning == false)
			return;
		if(0 <= m_nGameBeginClick)
		{
			++m_nGameBeginClick;
			if(SPLASH_SINGLE_STEP <= m_nGameBeginClick)
			{
				Stop();
			}
			return;
		}		
	}
}
