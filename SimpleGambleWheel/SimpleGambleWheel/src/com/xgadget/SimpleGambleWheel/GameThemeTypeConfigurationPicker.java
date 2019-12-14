package com.xgadget.SimpleGambleWheel;

import android.content.Context;
import android.util.AttributeSet;

import com.xgadget.widget.*;
import com.xgadget.widget.adapters.*;


public class GameThemeTypeConfigurationPicker extends WheelView 
{

	private void Initialize(Context context)
	{
        setVisibleItems(GameConstants.GAME_THEMECOUNT);
        setViewAdapter(new GraphicThemeSelectionPickerAdapter());
        int nTheme = Configuration.getCurrentThemeType();
       	setCurrentItem(nTheme);     

        addScrollingListener(new OnWheelScrollListener() 
        {
            public void onScrollingStarted(WheelView wheel) 
            {
            }
            public void onScrollingFinished(WheelView wheel) 
            {
            	int nTheme = getCurrentItem();
                Configuration.setCurrentThemeType(nTheme);
                ScoreRecord.SetThemeType(nTheme);
            }
        });       
	}

	
	public GameThemeTypeConfigurationPicker(Context context, AttributeSet attrs,
			int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public GameThemeTypeConfigurationPicker(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public GameThemeTypeConfigurationPicker(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public void LoadCurrentConfiguration() 
	{
		// TODO Auto-generated method stub
        int nTheme = Configuration.getCurrentThemeType();
       	setCurrentItem(nTheme);     
	}
	
}
