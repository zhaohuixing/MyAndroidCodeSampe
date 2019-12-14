package com.xgadget.SimpleGambleWheel;

import android.content.Context;
import android.util.AttributeSet;

import com.xgadget.widget.*;
import com.xgadget.widget.adapters.*;

public class GameConfigureGameModePicker extends WheelView 
{
	private void Initialize(Context context)
	{
        setVisibleItems(2);
        String typeList[] = new String[] {StringFactory.GetString_Online(), StringFactory.GetString_Offline()};
        
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(context, typeList);
        adapter.setTextSize(18);
        setViewAdapter(adapter);
        if(Configuration.isOnline() == true)
        	setCurrentItem(0);
        else
        	setCurrentItem(1);
        	
        addScrollingListener(new OnWheelScrollListener() 
        {
            public void onScrollingStarted(WheelView wheel) 
            {
            }
            public void onScrollingFinished(WheelView wheel) 
            {
            	int nIndex = getCurrentItem();
            	if(nIndex == 0)
            		Configuration.setOnline(true);
            	else 
            		Configuration.setOnline(false);
            }
        });       
	}

	public GameConfigureGameModePicker(Context context, AttributeSet attrs,
			int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public GameConfigureGameModePicker(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public GameConfigureGameModePicker(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public void LoadCurrentConfiguration()
	{
        if(Configuration.isOnline() == true)
        	setCurrentItem(0);
        else
        	setCurrentItem(1);
	}
}
