package com.xgadget.uimodule;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class MainViewMenu extends MyAbsoluteLayout 
{
	public static final int DEFAULT_MENU_SIZE = 300;
	
	public static int m_nMenuSize = DEFAULT_MENU_SIZE;
	
	public static int		m_nMenuCount = 7;
	
	
	private static int m_CenterX = 0;
	private static int m_CenterY = 0;
	
	
	protected MyAbsoluteLayout			m_LayoutContainer;
	
	
	public static int GetCurrentMenuSize()
	{
		return m_nMenuSize;
	}
	
	public static void SetCurrentMenuSize(int size)
	{
		m_nMenuSize = size;
	}
	
	public static void SetMenuCount(int nCount)
	{
		m_nMenuCount = nCount;
	}
	
	public static void SetCenter(int x, int y)
	{
		m_CenterX = x;
		m_CenterY = y;
	}
	
	public static int GetMenuCenterX()
	{
		return m_CenterX; 
	}

	public static int GetMenuCenterY()
	{
		return m_CenterY; 
	}
	
	/**
	 * @return the m_LayoutContainer
	 */
	public MyAbsoluteLayout getM_LayoutContainer() 
	{
		return m_LayoutContainer;
	}

	/**
	 * @param m_LayoutContainer the m_LayoutContainer to set
	 */
	public void setM_LayoutContainer(MyAbsoluteLayout m_LayoutContainer) 
	{
		this.m_LayoutContainer = m_LayoutContainer;
	}
	
	protected void Initialize(Context context)
	{
		this.m_LayoutContainer = null;
	}
	
	public MainViewMenu(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public MainViewMenu(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public MainViewMenu(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) 
	{
		int size = MainViewMenu.GetCurrentMenuSize();
		int cx = size/2;
		int cy = cx;
		int mw = MainViewMenuItem.GetCurrentMenuItemSize();
		double r= (double)((size - mw)/2);
		int mcx, mcy;
        int count = getChildCount();
        int index = 0;
        double angleStep = 360.0f/((double)count);
        for (int i = 0; i < count; i++) 
        {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE && child instanceof MainViewMenuItem) 
            {
            	double angle = angleStep*((double)index)*Math.PI/180.0f;
            	mcx = cx + (int)(r*Math.sin(angle));
            	mcy = cy - (int)(r*Math.cos(angle));
            	//mcx = mw/2 + i*mw;
            	//mcy = mw/2;
            	((MainViewMenuItem)child).SetCenter(mcx, mcy);
            	((MainViewMenuItem)child).PostOnLayoutHandle();
            	++index;
            }
        }
        super.onLayout(changed, left, top, right, bottom);
	}
	
	public void PostOnLayoutHandle()
	{
		int size = MainViewMenu.GetCurrentMenuSize();
		int x, y;
		x = m_CenterX - size/2;
		y = m_CenterY - size/2;
		if(m_LayoutContainer != null)
		{	
			m_LayoutContainer.SetChildNewDemension(this, size, size, x, y);		
		}
        int count = getChildCount();
        for (int i = 0; i < count; i++) 
        {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE && child instanceof MainViewMenuItem) 
            {
            	((MainViewMenuItem)child).PostOnLayoutHandle();
            }
        }
		
	}
		
}
