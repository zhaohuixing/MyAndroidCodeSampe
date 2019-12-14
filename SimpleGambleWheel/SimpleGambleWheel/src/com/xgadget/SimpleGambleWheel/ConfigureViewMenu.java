package com.xgadget.SimpleGambleWheel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class ConfigureViewMenu extends MyAbsoluteLayout 
{
	public static int		m_nMenuCount = 7;
	
	protected MyAbsoluteLayout			m_LayoutContainer;
	
	public static void SetMenuCount(int nCount)
	{
		m_nMenuCount = nCount;
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
	
	public ConfigureViewMenu(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public ConfigureViewMenu(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public ConfigureViewMenu(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) 
	{
		int size = GameLayoutConstant.GetCurrentCompassWheelSize();
		int cx = size/2;
		int cy = cx;
		int mw = GameLayoutConstant.GetCurrentMenuItemSize();
		double r= (double)((size - mw)/2);
		int mcx, mcy;
        int count = getChildCount();
        int index = 0;
        double angleStep = 360.0f/((double)count);
        for (int i = 0; i < count; i++) 
        {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE && child instanceof ConfigureViewMenuItem) 
            {
            	double angle = angleStep*((double)index)*Math.PI/180.0f;
            	mcx = cx + (int)(r*Math.sin(angle));
            	mcy = cy - (int)(r*Math.cos(angle));
            	//mcx = mw/2 + i*mw;
            	//mcy = mw/2;
            	((ConfigureViewMenuItem)child).SetCenter(mcx, mcy);
            	((ConfigureViewMenuItem)child).PostOnLayoutHandle();
            	++index;
            }
        }
        super.onLayout(changed, left, top, right, bottom);
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
		{	
			m_LayoutContainer.SetChildNewDemension(this, size, size, x, y);		
		}
        int count = getChildCount();
        for (int i = 0; i < count; i++) 
        {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE && child instanceof ConfigureViewMenuItem) 
            {
            	((ConfigureViewMenuItem)child).PostOnLayoutHandle();
            }
        }
		
	}
		
}
