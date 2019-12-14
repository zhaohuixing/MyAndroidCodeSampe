package com.xgadget.SimpleGambleWheel;

import android.content.Context;
import android.util.AttributeSet;
import com.xgadget.widget.*;
import com.xgadget.widget.adapters.*;


public class PlayerOfflineTransferChipsAmountPicker extends WheelView 
{
	private int 	m_CurrentPlayerBalance;
	CPlayerOfflineTransferChipsController    m_Controller;
	
	
	
	public void AttachController(CPlayerOfflineTransferChipsController  Controller)
	{
		m_Controller = Controller;
	}
	
	public void SetCurrentPickerState(int nNumber, int nSelectedNumber)
	{
		m_CurrentPlayerBalance = nNumber;
		setViewAdapter(new NumericWheelAdapter(this.getContext(), 1, m_CurrentPlayerBalance));
		setVisibleItems(m_CurrentPlayerBalance);
		this.setCurrentItem(nSelectedNumber-1);
	}
	
	private void Initialize(Context context)
	{
		m_Controller = null;
		m_CurrentPlayerBalance = 1000;
		setViewAdapter(new NumericWheelAdapter(context, 1, m_CurrentPlayerBalance));
		setVisibleItems(0);
		OnWheelChangedListener wheelListener = new OnWheelChangedListener() 
		{
			public void onChanged(WheelView wheel, int oldValue, int newValue) 
			{
				if(m_Controller != null)
				{
					m_Controller.m_nSelectedTransferAmount = newValue+1;
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

	public PlayerOfflineTransferChipsAmountPicker(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public PlayerOfflineTransferChipsAmountPicker(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public PlayerOfflineTransferChipsAmountPicker(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}
	
	public void ReloadPlayerTransferAmount(int nChipBalance, int nSelectTransferAmount)
	{
		m_CurrentPlayerBalance = nChipBalance;
		setViewAdapter(new NumericWheelAdapter(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity, 1, m_CurrentPlayerBalance));
		setVisibleItems(nSelectTransferAmount);
		OnWheelChangedListener wheelListener = new OnWheelChangedListener() 
		{
			public void onChanged(WheelView wheel, int oldValue, int newValue) 
			{
				if(m_Controller != null)
				{
					m_Controller.m_nSelectedTransferAmount = newValue+1;
				}
			}
		};
		addChangingListener(wheelListener);
		
		OnWheelClickedListener click = new OnWheelClickedListener() 
		{
            public void onItemClicked(WheelView wheel, int itemIndex) 
            {
            }
        };
        addClickingListener(click);
	
		OnWheelScrollListener scrollListener = new OnWheelScrollListener() 
		{
			public void onScrollingStarted(WheelView wheel) 
			{
			}
			public void onScrollingFinished(WheelView wheel) 
			{
			}
		};
		addScrollingListener(scrollListener);
		this.setCurrentItem(nSelectTransferAmount, true);
		//this.scroll(itemsToScroll, time)
	}
}
