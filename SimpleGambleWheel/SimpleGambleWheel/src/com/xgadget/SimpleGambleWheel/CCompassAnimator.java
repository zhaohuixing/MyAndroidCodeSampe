package com.xgadget.SimpleGambleWheel;

import com.xgadget.uimodule.ResourceHelper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.graphics.Bitmap;
//import android.R.color;

public class CCompassAnimator extends BasicLayoutView 
{
	private int			m_GameType;
	private int			m_ThemeType;
	private boolean		m_bAnimation;
	private long		m_TimeCount; 
	private int         m_HighlightIndex = 0;
	private int 		m_nHighlightAnimationStep = 0;
	
	private void Initialize()
	{
		this.setBackgroundColor(17170445);
		m_GameType = GameConstants.GAME_TYPE_8LUCK;
		m_ThemeType = GameConstants.GAME_THEME_ANIMAL;
		m_TimeCount = CApplicationController.GetSystemTimerClick();
		m_bAnimation = true;
	    m_HighlightIndex = 0;
	    m_nHighlightAnimationStep = 0;
	}

	public void SetGameType(int nType)
	{
		m_GameType = nType;
		invalidate();
	}

	public void SetGameTheme(int nTheme)
	{
		m_ThemeType = nTheme;
		invalidate();
	}
	
	public int GetGameType()
	{
		return m_GameType;
	}
	
	public CCompassAnimator(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		this.Initialize();
	}

	public CCompassAnimator(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.Initialize();
	}

	public CCompassAnimator(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.Initialize();
	}

	public void PostOnLayoutHandle()
	{
		int screenWidth = GameLayoutConstant.GetCurrentScreenWidth(); //m_LayoutContainer.getWidth(); //MyAbsoluteLayout.GetCurrentScreenWidth();
		int screenHeight = GameLayoutConstant.GetCurrentContentViewHeight(); //m_LayoutContainer.getHeight();  //MyAbsoluteLayout.GetCurrentScreenHeight();
		int size = GameLayoutConstant.GetCurrentCompassWheelSize();
		int x, y;
		if(screenWidth < screenHeight)
		{
			x = (screenWidth - size)/2;
			y = (screenHeight - size)/2;
		}
		else
		{
			x = (screenWidth - size)/2;
			y = (screenHeight - size)/2;
		}
		if(m_LayoutContainer != null)
			m_LayoutContainer.SetChildNewDemension(this, size, size, x, y);		
		
		//m_RepaintRect = new RectF(0, 0, size, size);		
		
		invalidate();
	}	

	protected void onDraw(Canvas canvas)
	{
		//Bitmap bitmap = GetCurrentCompassImage();
		//if(bitmap != null)
		//	canvas.drawBitmap(bitmap, m_SrcImageRect, m_RepaintRect, null);
		if(m_bAnimation == true)
		{
			if(m_ThemeType == GameConstants.GAME_THEME_ANIMAL || m_ThemeType == GameConstants.GAME_THEME_FOOD || m_ThemeType == GameConstants.GAME_THEME_FLOWER)
				DrawThemeAnimation(canvas);
			else	
				DrawAnimation(canvas);
		}
 	}

	private void DrawThemeAnimation(Canvas canvas)
	{
		Bitmap bitmap = null;
		if(m_ThemeType == GameConstants.GAME_THEME_FOOD)
		{
			bitmap = ResourceHelper.GetFoodIconBitmap(m_HighlightIndex);
		}
		else if(m_ThemeType == GameConstants.GAME_THEME_FLOWER)
		{
			bitmap = ResourceHelper.GetFlowerIconBitmap(m_HighlightIndex);
		}
		else
		{
			bitmap = ResourceHelper.GetAnimalIconBitmap(m_HighlightIndex);
		}
		if(bitmap != null)
		{
			float fSize = getWidth();
			int imgw = bitmap.getWidth();
			int imgh = bitmap.getHeight();
			Rect srcRect = new Rect(0, 0, imgw, imgh);
		    float rBase = fSize/2.0f;

		    float rSign = rBase*0.65f;
		    float iconSize;
			int nGameState = SimpleGambleWheel.m_ApplicationController.GetGameState();
			if(nGameState == GameConstants.GAME_STATE_RESULT)
			{
				iconSize = rBase*(0.3f + ((float)m_nHighlightAnimationStep)*0.01f);
			}
			else
			{	
				if(m_nHighlightAnimationStep <= 50)
				{	
					iconSize = rBase*(0.3f + ((float)m_nHighlightAnimationStep)*0.0008f);
				}
				else
				{
					iconSize = rBase*(0.34f - ((float)(m_nHighlightAnimationStep-50))*0.0008f);
				}
			}	
		    float cx = rBase;
		    float cy = rBase-rSign; 
		    float sx = cx-iconSize*0.5f;
		    float sy = cy-iconSize*0.5f; 
		    
		    RectF rt = new RectF(sx, sy, sx+iconSize, sy+iconSize);

		    float nCount = 8.0f;
		    if(m_GameType == GameConstants.GAME_TYPE_6LUCK)
		        nCount = 6.0f;
		    else if(m_GameType == GameConstants.GAME_TYPE_4LUCK)
		        nCount = 4.0f;
		    else if(m_GameType == GameConstants.GAME_TYPE_2LUCK)
		        nCount = 2.0f;
		 
		    float fStartAngle = 180.0f/nCount;
		    float fAngle = 360.0f/nCount;
			canvas.save();
			canvas.rotate(fStartAngle+fAngle*((float)m_HighlightIndex), rBase, rBase);
			canvas.drawBitmap(bitmap, srcRect, rt, null);
			canvas.restore();
		}
		
	}
	
	private void DrawAnimation(Canvas canvas)
	{
		int nGameState = SimpleGambleWheel.m_ApplicationController.GetGameState();
		if(nGameState == GameConstants.GAME_STATE_RESULT)
		{
			if(m_nHighlightAnimationStep%3 == 0)
				return;
		}
		
		Bitmap bitmap = ResourceHelper.GetHighligherBitmap();
		if(bitmap != null)
		{
			float fSize = getWidth();
			int imgw = bitmap.getWidth();
			int imgh = bitmap.getHeight();
			Rect srcRect = new Rect(0, 0, imgw, imgh);
		    float rBase = fSize/2.0f;
		    float rSign = rBase*0.65f;
		    float iconSize = rBase*0.3f;
		    float cx = rBase;
		    float cy = rBase-rSign; 
		    float sx = cx-iconSize*0.5f;
		    float sy = cy-iconSize*0.5f; 
		    
		    RectF rt = new RectF(sx, sy, sx+iconSize, sy+iconSize);

		    float nCount = 8.0f;
		    if(m_GameType == GameConstants.GAME_TYPE_6LUCK)
		        nCount = 6.0f;
		    else if(m_GameType == GameConstants.GAME_TYPE_4LUCK)
		        nCount = 4.0f;
		    else if(m_GameType == GameConstants.GAME_TYPE_2LUCK)
		        nCount = 2.0f;
		 
		    
		    float fStartAngle = 180.0f/nCount;
		    float fAngle = 360.0f/nCount;
			canvas.save();
			Paint paint = ResourceHelper.GetDefaultTextImageRenderPaint();
			paint.setColor(1222508509);
			canvas.rotate(fStartAngle+fAngle*((float)m_HighlightIndex), rBase, rBase);
			canvas.drawBitmap(bitmap, srcRect, rt, paint);
			canvas.restore();
		}
	}
	
	private void StartAnimation()
	{
		m_bAnimation = true;
		m_TimeCount = CApplicationController.GetSystemTimerClick();
	    m_HighlightIndex = 0;
	    m_nHighlightAnimationStep = 0;
		invalidate();
	}
	
	public void OnTimerEvent()
	{
		int nGameState = SimpleGambleWheel.m_ApplicationController.GetGameState();
		if(nGameState != GameConstants.GAME_STATE_RUN /*&& WinThisTime() == true */)
		{
			if(nGameState == GameConstants.GAME_STATE_RESULT)
			{
			    m_HighlightIndex = SimpleGambleWheel.m_ApplicationController.GetWinScopeIndex();
				if(m_bAnimation == false)
				{
					StartAnimation();
				}
				else
				{
					long currentTime = CApplicationController.GetSystemTimerClick();
					long nStep = GameConstants.AVATAR_ANIMATION_TIMEINTERNVAL/5;
				    if(m_GameType == GameConstants.GAME_TYPE_2LUCK)
				        nStep = GameConstants.AVATAR_ANIMATION_TIMEINTERNVAL/3;
					
					if(nStep <= (currentTime - m_TimeCount))
					{   
					    m_nHighlightAnimationStep = (m_nHighlightAnimationStep+1)%10;
						m_TimeCount = currentTime;
						invalidate();
					}
				}
			}
			else
			{	
				if(m_bAnimation == false)
				{
					StartAnimation();
				}
				else
				{
					long currentTime = CApplicationController.GetSystemTimerClick();
					long nStep = GameConstants.AVATAR_ANIMATION_TIMEINTERNVAL/5;
					if(m_GameType == GameConstants.GAME_TYPE_2LUCK)
						nStep = GameConstants.AVATAR_ANIMATION_TIMEINTERNVAL/3;
				
					if(nStep <= (currentTime - m_TimeCount))
					{   
						m_nHighlightAnimationStep = (m_nHighlightAnimationStep+1)%100;
						m_TimeCount = currentTime;
						if(m_GameType == GameConstants.GAME_TYPE_8LUCK)
							m_HighlightIndex = (m_HighlightIndex+1)%8;
						else if(m_GameType == GameConstants.GAME_TYPE_6LUCK)
							m_HighlightIndex = (m_HighlightIndex+1)%6;
						else if(m_GameType == GameConstants.GAME_TYPE_4LUCK)
							m_HighlightIndex = (m_HighlightIndex+1)%4;
						else if(m_GameType == GameConstants.GAME_TYPE_2LUCK)
							m_HighlightIndex = (m_HighlightIndex+1)%2;
						invalidate();
					}
				}
			}
		}
		else
		{
			if(m_bAnimation == true)
			{	
				m_bAnimation = false;
				invalidate();
			}	
		}
		
	}
}
