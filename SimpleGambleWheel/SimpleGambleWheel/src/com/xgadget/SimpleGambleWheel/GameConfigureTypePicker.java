package com.xgadget.SimpleGambleWheel;

import android.content.Context;
import android.util.AttributeSet;

import com.xgadget.widget.*;
import com.xgadget.widget.adapters.*;


public class GameConfigureTypePicker extends WheelView 
{

	private void Initialize(Context context)
	{
        setVisibleItems(2);
        String typeList[] = new String[] {StringFactory.GetString_Automatically(), StringFactory.GetString_Manually()};
        
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(context, typeList);
        adapter.setTextSize(18);
        setViewAdapter(adapter);
        if(Configuration.isRoPaAutoBet() == true)
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
            	if(getCurrentItem() == 0)
            		Configuration.setRoPaAutoBet(true);
            	else
            		Configuration.setRoPaAutoBet(false);
            		
            }
        });       
	}

	
	public GameConfigureTypePicker(Context context, AttributeSet attrs,
			int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public GameConfigureTypePicker(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public GameConfigureTypePicker(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public void LoadCurrentConfiguration() 
	{
		// TODO Auto-generated method stub
        if(Configuration.isRoPaAutoBet() == true)
        	setCurrentItem(0);     
        else 
        	setCurrentItem(1);     
	}
	
}
