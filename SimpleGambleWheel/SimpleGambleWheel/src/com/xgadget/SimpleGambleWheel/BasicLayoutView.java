package com.xgadget.SimpleGambleWheel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.xgadget.SimpleGambleWheel.MyAbsoluteLayout;

public class BasicLayoutView extends View 
{
	protected MyAbsoluteLayout			m_LayoutContainer;
	
	public BasicLayoutView(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		this.m_LayoutContainer = null;
	}

	public BasicLayoutView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.m_LayoutContainer = null;
	}

	public BasicLayoutView(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.m_LayoutContainer = null;
	}

	public void PostOnLayoutHandle()
	{
		
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
}
