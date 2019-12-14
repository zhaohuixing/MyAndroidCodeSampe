package com.xgadget.uimodule;

import com.xgadget.uimodule.ResourceHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

public class MainViewMenuItem extends BasicLayoutView 
{
	public static final int		DEFAULT_MENUITEM_SIZE = 60;
	public static int	m_nCurrentMenuItemSize = DEFAULT_MENUITEM_SIZE;
	
	protected	int	m_BitmapID;
	protected 	int m_CenterX;
	protected 	int m_CenterY;
	protected	RectF 		m_RepaintRect;
	protected 	Rect		m_SrcImageRect;	
	protected   int		m_nMenuItemIndex;
	
	public static int GetCurrentMenuItemSize()
	{
		return m_nCurrentMenuItemSize;
	}

	public static void SetCurrentMenuItemSize(int size)
	{
		m_nCurrentMenuItemSize = size;
	}
	
	protected void Initialize()
	{
		m_BitmapID = -1;
		m_CenterX = 0;
		m_CenterY = 0;
			
		int size = MainViewMenuItem.GetCurrentMenuItemSize();
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
		int mw = MainViewMenuItem.GetCurrentMenuItemSize();
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
	
	public MainViewMenuItem(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public MainViewMenuItem(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public MainViewMenuItem(Context context, AttributeSet attrs,
			int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public void PostOnLayoutHandle()
	{
		int mw = MainViewMenuItem.GetCurrentMenuItemSize();
		int size = MainViewMenu.GetCurrentMenuSize();
		int cx = MainViewMenu.GetMenuCenterX();
		int cy = MainViewMenu.GetMenuCenterY();
		double r= (double)((size - mw)/2);
        double angleStep = 360.0f/((double)MainViewMenu.m_nMenuCount);
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
