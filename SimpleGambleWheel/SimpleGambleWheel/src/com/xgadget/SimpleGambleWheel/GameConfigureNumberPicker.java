package com.xgadget.SimpleGambleWheel;

import android.content.Context;
import android.util.AttributeSet;

import com.xgadget.widget.*;
import com.xgadget.widget.adapters.*;

public class GameConfigureNumberPicker extends WheelView 
{
	private void Initialize(Context context)
	{
		//setViewAdapter(new NumericWheelAdapter(context, 1, m_CurrentLuckNumber));
        String typeList[] = new String[] {"8", "6", "4", "2"};
        
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(context, typeList);
        adapter.setTextSize(18);
        setViewAdapter(adapter);
		setVisibleItems(4);
		LoadCurrentConfiguration();
		OnWheelChangedListener wheelListener = new OnWheelChangedListener() 
		{
			public void onChanged(WheelView wheel, int oldValue, int newValue) 
			{
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
				int nType = getCurrentItem();		
				Configuration.setCurrentGameType(nType);
				UpdatePledgeHeadViewsState();
			}
		};
		addScrollingListener(scrollListener);
        
	}

	public GameConfigureNumberPicker(Context context, AttributeSet attrs,
			int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public GameConfigureNumberPicker(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public GameConfigureNumberPicker(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public void LoadCurrentConfiguration()
	{
		int nType = Configuration.getCurrentGameType();		
		setCurrentItem(nType);
		UpdatePledgeHeadViewsState();
	}

	public void UpdatePledgeHeadViewsState()
	{
		if(SimpleGambleWheel.m_ApplicationController != null)
			SimpleGambleWheel.m_ApplicationController.UpdateConfigurePledgeHeadViewsState();
	}
	
}
