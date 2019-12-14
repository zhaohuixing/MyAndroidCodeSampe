package com.xgadget.SimpleGambleWheel;

import com.xgadget.uimodule.ResourceHelper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.graphics.Bitmap;
//import android.R.color;

public class CCompassRender extends BasicLayoutView 
{
	private	RectF 		m_RepaintRect;
	private Rect		m_SrcImageRect;	
	private int			m_GameType;
	private int			m_ThemeType;
	
	private CCompassAnimator   m_AnimatorLayer;
	
	private Bitmap GetCurrentCompassImage()
	{
		if(m_GameType  == GameConstants.GAME_TYPE_2LUCK)
		{
			if(m_ThemeType == GameConstants.GAME_THEME_ANIMAL)
				return ResourceHelper.GetAnimalCompass2Bitmap();
			else if(m_ThemeType == GameConstants.GAME_THEME_FOOD)
				return ResourceHelper.GetFoodCompass2Bitmap();
			else if(m_ThemeType == GameConstants.GAME_THEME_FLOWER)
				return ResourceHelper.GetFlowerCompass2Bitmap();
			else
				return ResourceHelper.GetCompass2Bitmap();
		}	
		else if(m_GameType  == GameConstants.GAME_TYPE_4LUCK)
		{	
			if(m_ThemeType == GameConstants.GAME_THEME_ANIMAL)
				return ResourceHelper.GetAnimalCompass4Bitmap();
			else if(m_ThemeType == GameConstants.GAME_THEME_FOOD)
				return ResourceHelper.GetFoodCompass4Bitmap();
			else if(m_ThemeType == GameConstants.GAME_THEME_FLOWER)
				return ResourceHelper.GetFlowerCompass4Bitmap();
			else
				return ResourceHelper.GetCompass4Bitmap();
		}	
		else if(m_GameType  == GameConstants.GAME_TYPE_6LUCK)
		{	
			if(m_ThemeType == GameConstants.GAME_THEME_ANIMAL)
				return ResourceHelper.GetAnimalCompass6Bitmap();
			else if(m_ThemeType == GameConstants.GAME_THEME_FOOD)
				return ResourceHelper.GetFoodCompass6Bitmap();
			else if(m_ThemeType == GameConstants.GAME_THEME_FLOWER)
				return ResourceHelper.GetFlowerCompass6Bitmap();
			else
				return ResourceHelper.GetCompass6Bitmap();
		}	
		else
		{	
			if(m_ThemeType == GameConstants.GAME_THEME_ANIMAL)
				return ResourceHelper.GetAnimalCompass8Bitmap();
			else if(m_ThemeType == GameConstants.GAME_THEME_FOOD)
				return ResourceHelper.GetFoodCompass8Bitmap();
			else if(m_ThemeType == GameConstants.GAME_THEME_FLOWER)
				return ResourceHelper.GetFlowerCompass8Bitmap();
			else
				return ResourceHelper.GetCompass8Bitmap();
		}	
	}
	
	private void Initialize()
	{
		this.setBackgroundColor(17170445);
		int imagew = ResourceHelper.GetCompassBitmapSize();
		m_SrcImageRect = new Rect(0, 0, imagew, imagew);
		
		int size;
		size = GameLayoutConstant.GetCurrentCompassWheelSize();
		
		m_RepaintRect = new RectF(0, 0, size, size);

		m_GameType = GameConstants.GAME_TYPE_8LUCK;
		m_ThemeType = GameConstants.GAME_THEME_ANIMAL;
		m_AnimatorLayer = null;
	}
	
	public void SetAnimator(CCompassAnimator   animatorLayer)
	{
		m_AnimatorLayer = animatorLayer;
		if(m_AnimatorLayer != null)
		{
			m_AnimatorLayer.SetGameType(m_GameType);
		}
	}

	public void SetGameType(int nType)
	{
		m_GameType = nType;
		invalidate();
		if(m_AnimatorLayer != null)
		{
			m_AnimatorLayer.SetGameType(m_GameType);
		}
	}

	public void SetGameTheme(int nTheme)
	{
		m_ThemeType = nTheme;
		if(m_AnimatorLayer != null)
		{
			m_AnimatorLayer.SetGameTheme(m_ThemeType);
		}
		invalidate();
	}
	
	public int GetGameType()
	{
		return m_GameType;
	}
	
	public CCompassRender(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		this.Initialize();
	}

	public CCompassRender(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.Initialize();
	}

	public CCompassRender(Context context, AttributeSet attrs, int defStyle) 
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
		
		m_RepaintRect = new RectF(0, 0, size, size);		
		
		invalidate();
	}	

	protected void onDraw(Canvas canvas)
	{
		Bitmap bitmap = GetCurrentCompassImage();
		if(bitmap != null)
			canvas.drawBitmap(bitmap, m_SrcImageRect, m_RepaintRect, null);
 	}
	
	public void OnTimerEvent()
	{
		if(m_AnimatorLayer != null)
		{
			m_AnimatorLayer.OnTimerEvent();
		}
	}
}
