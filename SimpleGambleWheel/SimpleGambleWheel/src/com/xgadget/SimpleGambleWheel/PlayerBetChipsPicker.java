package com.xgadget.SimpleGambleWheel;

import android.content.Context;
import android.util.AttributeSet;
import com.xgadget.widget.*;
import com.xgadget.widget.adapters.*;


public class PlayerBetChipsPicker extends WheelView 
{
	private int 	m_CurrentChipBalance;
	protected MyAbsoluteLayout			m_LayoutContainer;
	
	CPlayerMakeBetController    m_Controller;
	
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
	
	public void AttachController(CPlayerMakeBetController  Controller)
	{
		m_Controller = Controller;
	}
	
	public void SetCurrentPickerState(int nMaxValue, int nCurrentSelectValue)
	{
		m_CurrentChipBalance = nMaxValue;
		setViewAdapter(new NumericWheelAdapter(this.getContext(), 1, m_CurrentChipBalance));
		this.setCurrentItem(nCurrentSelectValue-1);
	}
	
	private void Initialize(Context context)
	{
		m_Controller = null;
		m_LayoutContainer = null;
		m_CurrentChipBalance = 1000;
		setViewAdapter(new NumericWheelAdapter(context, 1, m_CurrentChipBalance));
		setVisibleItems(8);
		OnWheelChangedListener wheelListener = new OnWheelChangedListener() 
		{
			public void onChanged(WheelView wheel, int oldValue, int newValue) 
			{
				if(m_Controller != null)
				{
					m_Controller.m_nSelectedChips = newValue +1;
				}
				/*if (!timeScrolled) 
				{
					timeChanged = true;
					picker.setCurrentHour(hours.getCurrentItem());
					picker.setCurrentMinute(mins.getCurrentItem());
					timeChanged = false;
				}*/
			}
		};
		addChangingListener(wheelListener);
		
		OnWheelClickedListener click = new OnWheelClickedListener() 
		{
            public void onItemClicked(WheelView wheel, int itemIndex) 
            {
                //wheel.setCurrentItem(itemIndex, true);
            }
        };
        addClickingListener(click);
	
		OnWheelScrollListener scrollListener = new OnWheelScrollListener() 
		{
			public void onScrollingStarted(WheelView wheel) 
			{
				//timeScrolled = true;
			}
			public void onScrollingFinished(WheelView wheel) 
			{
				//timeScrolled = false;
				//timeChanged = true;
				//picker.setCurrentHour(hours.getCurrentItem());
				//picker.setCurrentMinute(mins.getCurrentItem());
				//timeChanged = false;
			}
		};
		addScrollingListener(scrollListener);
        
	}

	public PlayerBetChipsPicker(Context context, AttributeSet attrs,
			int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public PlayerBetChipsPicker(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public PlayerBetChipsPicker(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public void PostOnLayoutHandle()
	{
		int width = GameLayoutConstant.GetCurrentPlayerBetPickerWidth();
		int height = GameLayoutConstant.GetCurrentPlayerBetPickerHeight();
		//int nScreentWidth = GameLayoutConstant.GetCurrentScreenWidth();
		//int nScreentHeight = GameLayoutConstant.GetCurrentContentViewHeight();
		if(m_LayoutContainer != null)
			m_LayoutContainer.SetChildNewDemension(this, width/2, height, width/2, 0);		
	}	
	
}
