package com.e_gadget.MindFire;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.content.res.Resources;

public class CBackgroundLayer 
{
	/** The drawable to use as the background of the animation canvas */
	
	private boolean		m_Initialized;

	private int			m_nRStarNumber;
	private int			m_nBStarNumber;
	
	private CScoreAnimator	m_ScoreAnimator;
	
	public CBackgroundLayer()
	{
		// Load background desktop image from resource
		m_Initialized = false;
		m_nRStarNumber = 0;
		m_nBStarNumber = 0;
		m_ScoreAnimator = new CScoreAnimator(); 
		Initialize();
	}
	
	public void Initialize()
	{
		if(m_Initialized == true)
			return;
		
		if(CGameHelper.CanLoadResource() && CDealLayout.IsInitialized())
		{	
			SetupStars();
			m_Initialized = true;
		}			
	}
	
	public void ResetStars()
	{
		m_nRStarNumber = 0;
		m_nBStarNumber = 0;
	}
	
	public void SetRunningStars(int nBlue, int nRed)
	{
		m_nRStarNumber = nRed;
		m_nBStarNumber = nBlue;
	}

	public void SetResultStars(int nRed)
	{
		m_nRStarNumber = nRed;
	}

	public void OnTimerEvent()
	{
        if(m_ScoreAnimator != null)
        {
        	m_ScoreAnimator.OnTimerEvent();
        }	
	}
	
	public void OnDraw(Canvas canvas)
	{
		int nState = CGameState.GetGameState();
        
        if(nState == CGameState.GAME_RUNNING)
        	DrawRunningStateStars(canvas);
	}

	public void ReloadResource()
	{
		if(m_Initialized == false)
		{
			Initialize();
		}
	}
	
	private void SetupStars()
	{
		Bitmap StarBitmap = CGameHelper.GetScoreSourceBitmap();
		if(StarBitmap != null)
		{
			
			int width = CGameHelper.GetScoreImageWidth();
			int height = CGameHelper.GetScoreImageHeight();
			int count = CGameHelper.GetScoreAnimatorFrames();
			for(int i = 0;  i <= count; ++i)
			{
				int j = i;
				if(j == count)
					j = 1;

				Resources res = CGameHelper.GetSystemResource();
				
		        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
				Canvas canvas = new Canvas(bitmap);
		        Rect src = new Rect(0, 0, 0, 0);
		        Rect dst = new Rect(0, 0, width, height);
		        src.left = j*width; 
		        src.right = src.left + width; 
		        src.bottom = src.top + height;
				canvas.drawBitmap(StarBitmap, src, dst, null);
				Drawable bimage = (Drawable)(new BitmapDrawable(res, bitmap));

				bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
				canvas = new Canvas(bitmap);
		        src.left = j*width; 
		        src.right = src.left + width; 
		        src.top = height;
		        src.bottom = src.top + height;
				canvas.drawBitmap(StarBitmap, src, dst, null);
				Drawable rimage = (Drawable)(new BitmapDrawable(res, bitmap));
				m_ScoreAnimator.AddStars(rimage, bimage); 
			}
		}
	}
	
	private void DrawRunningStateStars(Canvas canvas)
	{
		if(m_ScoreAnimator == null)
			return;
		
		int nCount = m_nRStarNumber + m_nBStarNumber;
		Rect rect;
		for(int i = 0; i < nCount; ++i)
		{
			rect = CDealLayout.GetScoreRect(i);
			if(i < m_nBStarNumber)
			{
				m_ScoreAnimator.DrawBlueStar(canvas, rect);	
			}
			else
			{
				m_ScoreAnimator.DrawRedStar(canvas, rect);
			}
		}
 
	}
}
