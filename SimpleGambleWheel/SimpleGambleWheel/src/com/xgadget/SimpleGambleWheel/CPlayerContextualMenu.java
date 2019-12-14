package com.xgadget.SimpleGambleWheel;

//import com.xgadget.SimpleGambleWheel.MyAbsoluteLayout.LayoutParams;

import android.content.Context;
//import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

public class CPlayerContextualMenu extends MyAbsoluteLayout 
{
	protected MyAbsoluteLayout			m_LayoutContainer;
	protected GamePlayer				m_Parent;

	public long 			m_MenuShownTimerClick;
	public boolean 			m_bMenuShown;
	
	
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
	
	/**
	 * @return the m_Parent
	 */
	public GamePlayer getM_Parent() 
	{
		return m_Parent;
	}

	/**
	 * @param m_Parent the m_Parent to set
	 */
	public void setM_Parent(GamePlayer m_Parent) 
	{
		this.m_Parent = m_Parent;
	}

	private void Initialize()
	{
		m_LayoutContainer = null;
		m_Parent = null;
		m_MenuShownTimerClick = 0;
		m_bMenuShown = false;
	}

	public CPlayerContextualMenu(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public CPlayerContextualMenu(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public CPlayerContextualMenu(Context context, AttributeSet attrs,
			int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize();
	}
	
	public void PostOnLayoutHandle()
	{
		int screenWidth = GameLayoutConstant.GetCurrentScreenWidth();
		int screenHeight = GameLayoutConstant.GetCurrentContentViewHeight();
		if(screenWidth < screenHeight)
		{
			HandleProtraitLayoutChange();
		}
		else
		{
			HandleLandscapeLayoutChange();
		}
		int width = GameLayoutConstant.GetCurrentAvatarSize();
		int height = width*2/5;
		
		int nCount = this.getChildCount();
        for (int i = 0; i < nCount; i++) 
        {
            View child = getChildAt(i);
            if(child instanceof CPlayerContextualMenuItem) 
            {
            	this.updateViewLayout(child, new LayoutParams(width, height, 0, height*i));
            	((CPlayerContextualMenuItem)child).PostOnLayoutHandle();
            	((CPlayerContextualMenuItem)child).layout(0, height*i, width, height*(i+1));
            }
        }		
	}

	private void HandleProtraitLayoutChange()
	{
		int nCount = this.getChildCount();
		int screenWidth = GameLayoutConstant.GetCurrentScreenWidth();
		int screenHeight = GameLayoutConstant.GetCurrentContentViewHeight();
		int xOffset = GameLayoutConstant.m_nAvatarDisplayProtraitHorizontalMarginMin;
		int yOffset = GameLayoutConstant.GetCurrentCashBalanceSignSize()+GameLayoutConstant.m_nAvatarDisplayTopOffsetToDecoration;
		int yBottomOffset = GameLayoutConstant.m_nAvatarDisplayTopOffsetToDecoration;
		int AvatarSize = GameLayoutConstant.GetCurrentAvatarSize();
		int PlayerHeight = (int)(((float)AvatarSize)*1.5f);
		int nSeatIndex = m_Parent.GetSeatID();
		int hHeight = nCount*AvatarSize/2;
		if(hHeight <= 0)
			hHeight = AvatarSize/2;
		int left = xOffset;   
		int top = yOffset;
		if(nSeatIndex == 1)
		{
			left = screenWidth - xOffset - AvatarSize;
		}
		else if(nSeatIndex == 2)
		{
			left = screenWidth - xOffset - AvatarSize;
			top = screenHeight - yBottomOffset - PlayerHeight; 
		}
		else if(nSeatIndex == 3)
		{
			top = screenHeight - yBottomOffset - PlayerHeight; 
		}
		if(m_LayoutContainer != null)
			m_LayoutContainer.SetChildNewDemension(this, AvatarSize, hHeight, left, top);		
    			
	}
	
	private void HandleLandscapeLayoutChange()
	{
		int nCount = this.getChildCount();
		int screenWidth = GameLayoutConstant.GetCurrentScreenWidth();
		int screenHeight = GameLayoutConstant.GetCurrentContentViewHeight();
		int compassSize = GameLayoutConstant.GetCurrentCompassWheelSize()/2;
		int AvatarSize = GameLayoutConstant.GetCurrentAvatarSize();
		int PlayerHeight = (int)(((float)AvatarSize)*1.5f);
		int hHeight = nCount*AvatarSize/2;
		if(hHeight <= 0)
			hHeight = AvatarSize/2;
		int cx = screenWidth/2;
		int yOffset = GameLayoutConstant.m_nAvatarDisplayLandscapeVerticalMarginMin;
		int yBottomOffset = yOffset;
		int nSeatIndex = m_Parent.GetSeatID();
		int left = cx-compassSize-AvatarSize;   
		int top = yOffset;
		if(nSeatIndex == 1)
		{
			left = cx+compassSize;
		}
		else if(nSeatIndex == 2)
		{
			left = cx+compassSize;
			top = screenHeight - yBottomOffset - PlayerHeight; 
		}
		else if(nSeatIndex == 3)
		{
			top = screenHeight - yBottomOffset - PlayerHeight; 
		}
		if(m_LayoutContainer != null)
			m_LayoutContainer.SetChildNewDemension(this, AvatarSize, hHeight, left, top);		
	}
	
	public void ShowSubMenuItems()
	{
		int nCount = this.getChildCount();
        for (int i = 0; i < nCount; i++) 
        {
            View child = getChildAt(i);
        	child.setVisibility(View.VISIBLE);
            if(child instanceof CPlayerContextualMenuItem) 
            {
            	((CPlayerContextualMenuItem)child).Show();
               	((CPlayerContextualMenuItem)child).InvalidateSubItems();
            }
        }		
	}
	
	public void OnTimerEvent()
	{
		if(m_bMenuShown == true)
		{
			long currentTime = CApplicationController.GetSystemTimerClick(); //SystemClock.uptimeMillis();
			if(GameConstants.m_PlayerMenuDisplayTimerElapse <= currentTime - m_MenuShownTimerClick)
			{
				HideMenu();
			}
		}
	}
	
	public void ShowMenu()
	{
		setVisibility(View.VISIBLE);
		PostOnLayoutHandle();
		ShowSubMenuItems();
		m_MenuShownTimerClick = CApplicationController.GetSystemTimerClick(); //SystemClock.uptimeMillis();
		m_bMenuShown = true;
	}
	
	public void HideMenu()
	{
		setVisibility(View.GONE);
		PostOnLayoutHandle();
		m_MenuShownTimerClick = 0;
		m_bMenuShown = false;
	}
}
