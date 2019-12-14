package com.xgadget.SimpleGambleWheel;

import android.content.Context;
import android.util.AttributeSet;
import com.xgadget.widget.*;
import com.xgadget.widget.adapters.*;


public class PlayerOfflineTransferChipsReceiverPicker extends WheelView 
{
	private int 	m_SenderOfflineSeatID;
	
	CPlayerOfflineTransferChipsController    m_Controller;
	
	public void AttachController(CPlayerOfflineTransferChipsController  Controller)
	{
		m_Controller = Controller;
	}
	
	private void Initialize(Context context)
	{
		m_Controller = null;
//		m_CurrentChipBalance = 1000;
		setViewAdapter(new NumericWheelAdapter(context, 1, 3));
		setVisibleItems(3);
		OnWheelChangedListener wheelListener = new OnWheelChangedListener() 
		{
			public void onChanged(WheelView wheel, int oldValue, int newValue) 
			{
				if(m_Controller != null)
				{
					//m_Controller.m_nSelectedChips = newValue +1;
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

	public PlayerOfflineTransferChipsReceiverPicker(Context context, AttributeSet attrs,
			int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public PlayerOfflineTransferChipsReceiverPicker(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public PlayerOfflineTransferChipsReceiverPicker(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	private String[] GetTransferReceiverList(int nSenderID)
	{
		String[] strRet = null;
		
		if(nSenderID == 1)
		{
			strRet = new String[]{StringFactory.GetString_Me(), StringFactory.GetString_OfflinePlayer2ID(), StringFactory.GetString_OfflinePlayer3ID()};
		}
		else if(nSenderID == 2)
		{
			strRet = new String[]{StringFactory.GetString_Me(), StringFactory.GetString_OfflinePlayer1ID(), StringFactory.GetString_OfflinePlayer3ID()};
		}
		else if(nSenderID == 3)
		{
			strRet = new String[]{StringFactory.GetString_Me(), StringFactory.GetString_OfflinePlayer1ID(), StringFactory.GetString_OfflinePlayer2ID()};
		}
		else
		{
			strRet = new String[]{StringFactory.GetString_OfflinePlayer1ID(), StringFactory.GetString_OfflinePlayer2ID(), StringFactory.GetString_OfflinePlayer3ID()};
		}
		
		return strRet;
	}
	
	
	public void ReloadPlayerTransferSetting(int nSenderID, int nReceiverIDIndex)
	{
		m_SenderOfflineSeatID = nSenderID;		
		int nCurrentIndex = nReceiverIDIndex;
        String typeList[] = GetTransferReceiverList(nSenderID);
        
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity, typeList);
        adapter.setTextSize(18);
        setViewAdapter(adapter);
       	setCurrentItem(nCurrentIndex);        
        	
		OnWheelChangedListener wheelListener = new OnWheelChangedListener() 
		{
			public void onChanged(WheelView wheel, int oldValue, int newValue) 
			{
				if(m_Controller != null)
				{
					m_Controller.m_nSelectedTransferReceiverIDIndex = newValue;
				}
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

	public void ReloadOnlinePlayerTransferSetting(String szReceiverName)
	{
		m_SenderOfflineSeatID = 0;		
		int nCurrentIndex = 0;
        String typeList[] = new String[]{szReceiverName};
        
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity, typeList);
        adapter.setTextSize(18);
        setViewAdapter(adapter);
       	setCurrentItem(nCurrentIndex);        
        	
		OnWheelChangedListener wheelListener = new OnWheelChangedListener() 
		{
			public void onChanged(WheelView wheel, int oldValue, int newValue) 
			{
				//if(m_Controller != null)
				//{
				//	m_Controller.m_nSelectedTransferReceiverIDIndex = newValue;
				//}
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
}
