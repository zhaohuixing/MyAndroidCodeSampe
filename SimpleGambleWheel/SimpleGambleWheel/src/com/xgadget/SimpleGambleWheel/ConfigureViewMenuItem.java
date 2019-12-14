package com.xgadget.SimpleGambleWheel;

import com.xgadget.uimodule.ResourceHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

public class ConfigureViewMenuItem extends BasicLayoutView 
{
	protected	int	m_BitmapID;
	protected 	int m_CenterX;
	protected 	int m_CenterY;
	protected	RectF 		m_RepaintRect;
	protected 	Rect		m_SrcImageRect;	
	protected   int		m_nMenuItemIndex;
	
	protected void Initialize()
	{
		m_BitmapID = -1;
		m_CenterX = 0;
		m_CenterY = 0;
			
		int size = GameLayoutConstant.GetCurrentMenuItemSize();
		m_SrcImageRect = new Rect(0, 0, size, size);
		m_RepaintRect = new RectF(0, 0, size, size);
		m_nMenuItemIndex = 0;
	}
	
	public void SetMenuIndex(int index)
	{
		m_nMenuItemIndex = index;
		PostOnLayoutHandle();
	}
	
	public void SetCenter(int cx, int cy)
	{
		m_CenterX = cx;
		m_CenterY = cy;
		int mw = GameLayoutConstant.GetCurrentMenuItemSize();
        int left = m_CenterX - mw/2;
        int top = m_CenterY - mw/2;
        int right = left + mw;
        int bottom = top + mw;
        layout(left, top, right, bottom);
	}

	public void SetBitmapID(int resID)
	{
		m_BitmapID = resID;
		PostOnLayoutHandle();
	}
	
	public ConfigureViewMenuItem(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public ConfigureViewMenuItem(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public ConfigureViewMenuItem(Context context, AttributeSet attrs,
			int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public void PostOnLayoutHandle()
	{
		int mw = GameLayoutConstant.GetCurrentMenuItemSize();
		int size = GameLayoutConstant.GetCurrentCompassWheelSize();
		//int cx = size/2;
		//int cy = cx;
		int screenWidth = GameLayoutConstant.GetCurrentScreenWidth(); //m_LayoutContainer.getWidth(); //MyAbsoluteLayout.GetCurrentScreenWidth();
		int screenHeight = GameLayoutConstant.GetCurrentContentViewHeight(); //m_LayoutContainer.getHeight();  //MyAbsoluteLayout.GetCurrentScreenHeight();
		int cx = screenWidth/2;
		int cy = screenHeight/2;
		double r= (double)((size - mw)/2);
        double angleStep = 360.0f/((double)ConfigureViewMenu.m_nMenuCount);
        double angle = angleStep*((double)m_nMenuItemIndex)*Math.PI/180.0f;
        m_CenterX = cx + (int)(r*Math.sin(angle));
        m_CenterY = cy - (int)(r*Math.cos(angle));

		if(m_LayoutContainer != null)
		{	
			m_LayoutContainer.SetChildNewDemension(this, mw, mw, m_CenterX-mw/2, m_CenterY-mw/2);		
		}	
		m_RepaintRect = new RectF(0, 0, mw, mw);
		
		invalidate();
	}

	protected void CalculateRenderRegion(Bitmap bitmap)
	{
		if(bitmap != null)
		{
			int w = bitmap.getWidth();
			int h = bitmap.getHeight();
			m_SrcImageRect = new Rect(0, 0, w, h);
		}
	}
	
	protected void onDraw(Canvas canvas)
	{
		if(0 <= m_BitmapID)
		{	
			Bitmap bitmap = ResourceHelper.GetBitmapByResourceID(m_BitmapID);
			if(bitmap != null)
			{	
				CalculateRenderRegion(bitmap);
				canvas.drawBitmap(bitmap, m_SrcImageRect, m_RepaintRect, null);
			}
		}	
 	}
	
}
