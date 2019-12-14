package com.xgadget.SimpleGambleWheel;

import android.content.Context;
import android.graphics.Color;
//import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
//import android.view.View.OnClickListener;
import android.widget.TextView;

public class GamePlayerLayout extends MyAbsoluteLayout 
{
	private GamePlayerAvatar			m_Avatar;
	private GamePlayerBetView			m_BetBulletin;
	public 	GamePlayer					m_Parent;
	private GameCashBalanceView			m_PlayerCashBalanceView;
	private View						m_TransparentClickButton;
	public TextView 					m_NameTag;
	protected MyAbsoluteLayout			m_LayoutContainer;
	public long 						m_CashViewShownTimerClick;
	public boolean 						m_bCashViewShown;
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
	
	private void Initialize()
	{
		m_Avatar = null;
		m_BetBulletin = null;
		m_Parent = null;
		m_LayoutContainer = null;
		m_PlayerCashBalanceView = null;
		m_CashViewShownTimerClick  = 0;
		m_bCashViewShown = false;
		m_NameTag.setBackgroundColor(Color.TRANSPARENT);
		m_NameTag.setTextSize(14.0f);
		m_NameTag.setGravity(Gravity.CENTER);
		this.addView(m_NameTag);
	}

	public GamePlayerLayout(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		m_NameTag = new TextView(context);
		Initialize();
	}

	public GamePlayerLayout(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		m_NameTag = new TextView(context);
		Initialize();
	}

	public GamePlayerLayout(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		m_NameTag = new TextView(context);
		Initialize();
	}

	public void AttachParent(GamePlayer parent)
	{
		m_Parent = parent;
		if(m_BetBulletin != null)
		{
			m_BetBulletin.AttachParent(m_Parent);
		}
		if(m_Avatar != null)
		{
			m_Avatar.AttachParent(m_Parent);
		}
		if(m_PlayerCashBalanceView != null)
		{
			m_PlayerCashBalanceView.AttachParent(m_Parent);
		}
		if(m_TransparentClickButton != null)
			this.bringChildToFront(m_TransparentClickButton);
		if(m_Parent != null)
		{
		    if(this.m_NameTag != null)
		    {
		    	this.m_NameTag.setText(m_Parent.GetPlayerName());
		    }
	    	if(m_Parent.IsMySelf() == true)
	    	{
	    		this.m_NameTag.setTextColor(Color.YELLOW);
	    	}
	    	else
	    	{
	    		this.m_NameTag.setTextColor(Color.GREEN);
	    	}
			
		}
	}
	
	public void AttachAvatar(GamePlayerAvatar avatar)
	{
		m_Avatar = avatar;
		m_Avatar.m_LayoutContainer = this;
		if(m_Parent != null)
		{
			m_Avatar.AttachParent(m_Parent);
		}
		if(m_TransparentClickButton != null)
			this.bringChildToFront(m_TransparentClickButton);
	}
	
	public void AttachBetBulletin(GamePlayerBetView	betView)
	{
		m_BetBulletin = betView;
		m_BetBulletin.m_LayoutContainer = this;
		if(m_Parent != null)
		{
			m_BetBulletin.AttachParent(m_Parent);
		}
		if(m_TransparentClickButton != null)
			this.bringChildToFront(m_TransparentClickButton);
	}
	
	public void Redraw()
	{
		if(m_Avatar != null)
			m_Avatar.invalidate();
		if(m_BetBulletin != null)
			m_BetBulletin.invalidate();
	}
	
	public boolean IsMyTurn()
	{
		boolean bRet = false;
		if(m_Parent != null)
			bRet = m_Parent.IsMyTurn();
		return bRet;
	}
	
	public boolean IsMySelf()
	{
		boolean bRet = false;
		if(m_Parent != null)
			bRet = m_Parent.IsMySelf();
		return bRet;
	}	
	
	public boolean WinThisTime()
	{
		boolean bRet = false;
		if(m_Parent != null)
			bRet = m_Parent.WinThisTime();
		return bRet;
	}
	

	public boolean IsOnlinePlayer()
	{
		boolean bRet = false;
		if(m_Parent != null)
			bRet = m_Parent.IsOnlinePlayer();
		return bRet;
	}
	
	public int GetSeatID()
	{
		int nRet = 0;
		if(m_Parent != null)
			nRet = m_Parent.GetSeatID();
		return nRet;
	}

	public boolean GetEnable()
	{
		boolean bRet = false;
		if(m_Parent != null)
			bRet = m_Parent.IsEnabled();
		return bRet;
	}

	public boolean IsWinFlyerActive()
	{
		boolean bRet = false;
		
		if(m_BetBulletin != null)
			bRet = m_BetBulletin.IsInResultWinAnimation();
		return bRet;
	}
	
	public void StartWinFlyerAnimation()
	{
		if(m_BetBulletin != null)
			m_BetBulletin.StartResultWinAnimation();
	}
	
	public void StopWinFlyerAnimation()
	{
		if(m_BetBulletin != null)
			m_BetBulletin.StopResultWinAnimation();
	}
	
	public boolean IsActive()
	{
	    boolean bRet = (this.getVisibility() != View.GONE);
	    return bRet;
	}
	
	public boolean AlreadyMadePledge()
	{
		boolean bRet = false;
		
		if(m_Parent != null)
			bRet = m_Parent.AlreadyMadePledge();
		
		return bRet;
	}
	
	public int GetPlayBetLuckNumber()
	{
		int nRet = -1;
		if(m_Parent != null)
			nRet = m_Parent.GetPlayBetLuckNumber();
		
		return nRet;
	}

	public int GetPlayBet()
	{
		int nRet = -1;
		if(m_Parent != null)
			nRet = m_Parent.GetPlayBet();
		
		return nRet;
	}
	
	public void ShowPlayBet()
	{
		if(m_BetBulletin != null)
			m_BetBulletin.ShowPlayBet();
	}

	public void HidePlayBet()
	{
		if(m_BetBulletin != null)
			m_BetBulletin.HidePlayBet();
	}
	
	private void HandleProtraitLayoutChange()
	{
		int screenWidth = GameLayoutConstant.GetCurrentScreenWidth();
		int screenHeight = GameLayoutConstant.GetCurrentContentViewHeight();
		int xOffset = GameLayoutConstant.m_nAvatarDisplayProtraitHorizontalMarginMin;
		int yOffset = GameLayoutConstant.GetCurrentCashBalanceSignSize()+GameLayoutConstant.m_nAvatarDisplayTopOffsetToDecoration;
		int yBottomOffset = GameLayoutConstant.m_nAvatarDisplayTopOffsetToDecoration;
		int AvatarSize = GameLayoutConstant.GetCurrentAvatarSize();
		int PlayerHeight = (int)(((float)AvatarSize)*1.5f);
		int nSeatIndex = GetSeatID();
		int left = xOffset;   
		int top = yOffset;
		int tootalHeoght = AvatarSize*2;
		if(nSeatIndex == 1)
		{
			left = screenWidth - xOffset - AvatarSize;
			this.SetChildNewDemension(this.m_NameTag, AvatarSize, AvatarSize/2, 0, PlayerHeight);		
		}
		else if(nSeatIndex == 2)
		{
			left = screenWidth - xOffset - AvatarSize;
			top = screenHeight - yBottomOffset - tootalHeoght; 
			this.SetChildNewDemension(this.m_NameTag, AvatarSize, AvatarSize/2, 0, 0);		
		}
		else if(nSeatIndex == 3)
		{
			top = screenHeight - yBottomOffset - tootalHeoght; 
			this.SetChildNewDemension(this.m_NameTag, AvatarSize, AvatarSize/2, 0, 0);		
		}
		else
		{
			this.SetChildNewDemension(this.m_NameTag, AvatarSize, AvatarSize/2, 0, PlayerHeight);		
		}
		if(m_LayoutContainer != null)
			m_LayoutContainer.SetChildNewDemension(this, AvatarSize, tootalHeoght, left, top);		
    
		//?????????????????
		if(m_TransparentClickButton != null)
		{	
			LayoutParams lp3 = new LayoutParams(AvatarSize, tootalHeoght, 0, 0);
			m_TransparentClickButton.setLayoutParams(lp3);
	   
			this.updateViewLayout(m_TransparentClickButton, lp3);
			this.bringChildToFront(m_TransparentClickButton);
		}	
	}
	
	private void HandleLandscapeLayoutChange()
	{
		int screenWidth = GameLayoutConstant.GetCurrentScreenWidth();
		int screenHeight = GameLayoutConstant.GetCurrentContentViewHeight();
		int compassSize = GameLayoutConstant.GetCurrentCompassWheelSize()/2;
		int AvatarSize = GameLayoutConstant.GetCurrentAvatarSize();
		int PlayerHeight = (int)(((float)AvatarSize)*1.5f);
		int cx = screenWidth/2;
		int yOffset = GameLayoutConstant.m_nAvatarDisplayLandscapeVerticalMarginMin;
		int yBottomOffset = yOffset;
		int nSeatIndex = GetSeatID();
		int left = cx-compassSize-AvatarSize;   
		int top = yOffset;
		int tootalHeoght = AvatarSize*2;
		if(nSeatIndex == 1)
		{
			left = cx+compassSize;
			this.SetChildNewDemension(this.m_NameTag, AvatarSize, AvatarSize/2, 0, PlayerHeight);		
		}
		else if(nSeatIndex == 2)
		{
			left = cx+compassSize;
			top = screenHeight - yBottomOffset -tootalHeoght; 
			this.SetChildNewDemension(this.m_NameTag, AvatarSize, AvatarSize/2, 0, 0);		
		}
		else if(nSeatIndex == 3)
		{
			top = screenHeight - yBottomOffset - tootalHeoght; 
			this.SetChildNewDemension(this.m_NameTag, AvatarSize, AvatarSize/2, 0, 0);		
		}
		else
		{
			this.SetChildNewDemension(this.m_NameTag, AvatarSize, AvatarSize/2, 0, PlayerHeight);		
		}
		if(m_LayoutContainer != null)
			m_LayoutContainer.SetChildNewDemension(this, AvatarSize, tootalHeoght, left, top);		

		if(m_TransparentClickButton != null)
		{	
			LayoutParams lp3 = new LayoutParams(AvatarSize, tootalHeoght, 0, 0);
			m_TransparentClickButton.setLayoutParams(lp3);
	   
			this.updateViewLayout(m_TransparentClickButton, lp3);
			this.bringChildToFront(m_TransparentClickButton);
		}	
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
		if(m_Parent != null)
		{
			m_Parent.UpdateInvisiableMenuLayout();
		}
		if(SimpleGambleWheel.m_ApplicationController.m_GameController.IsOnline() == true && m_Parent.IsOnlinePlayer() == false && m_NameTag.getVisibility() != View.GONE)
			m_NameTag.setVisibility(View.GONE);
		else if(SimpleGambleWheel.m_ApplicationController.m_GameController.IsOnline() == true && m_Parent.IsOnlinePlayer() == true && m_NameTag.getVisibility() != View.VISIBLE)
			m_NameTag.setVisibility(View.VISIBLE);
		else if(SimpleGambleWheel.m_ApplicationController.m_GameController.IsOnline() == false && m_NameTag.getVisibility() != View.VISIBLE)
			m_NameTag.setVisibility(View.VISIBLE);
		
		invalidate();
	}
	
	public void OnTimerEvent()
	{
		if(m_Avatar != null)
			m_Avatar.OnTimerEvent();
		if(m_BetBulletin != null)
			m_BetBulletin.OnTimerEvent();
		if(m_bCashViewShown == true)
		{
			long currentTime = CApplicationController.GetSystemTimerClick();//SystemClock.uptimeMillis();
			if(GameConstants.m_PlayerMenuDisplayTimerElapse <= currentTime - m_CashViewShownTimerClick)
			{
				ClosePlayerChipBalanceView();
			}
		}
	}
	
	public void Invalidate()
	{
		if(m_Avatar != null)
			m_Avatar.invalidate();
		if(m_BetBulletin != null)
			m_BetBulletin.invalidate();
		if(m_PlayerCashBalanceView != null && m_PlayerCashBalanceView.getVisibility() != View.GONE)
			m_PlayerCashBalanceView.invalidate();
	}
	
	public void AddPlayerChipBalanceView()
	{
		m_PlayerCashBalanceView = new GameCashBalanceView(this.getContext());
		int nSize = GameLayoutConstant.GetCurrentAvatarSize();
		LayoutParams lp2 = new LayoutParams(nSize, nSize, 0, 0);
		m_PlayerCashBalanceView.setLayoutParams(lp2);
		m_PlayerCashBalanceView.SetForPlayerAvatar();
		m_PlayerCashBalanceView.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				if(m_Parent != null)
					m_Parent.ClosePlayerChipsBalanceView();
				else
					ClosePlayerChipBalanceView();
			}
		});		
		this.addView(m_PlayerCashBalanceView);
		if(this.m_Parent != null)
		{
			m_PlayerCashBalanceView.AttachParent(this.m_Parent);
		}
		m_PlayerCashBalanceView.PostOnLayoutHandle();
		m_PlayerCashBalanceView.setVisibility(View.GONE);
		m_TransparentClickButton = new View(this.getContext());
		m_TransparentClickButton.setBackgroundColor(Color.TRANSPARENT);
		LayoutParams lp3 = new LayoutParams(nSize, nSize*2, 0, 0);
		m_TransparentClickButton.setLayoutParams(lp3);
		this.addView(m_TransparentClickButton);
		this.bringChildToFront(m_TransparentClickButton);
		m_TransparentClickButton.setClickable(true); //??????????????????????
		m_TransparentClickButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				OnButtonClick();
			}
		});	
		
	}

	public void OnButtonClick()
	{
		if(m_Parent != null)
			m_Parent.ShowMenu();
	}
	
	public void OpenPlayerChipBalanceView()
	{
		if(m_PlayerCashBalanceView != null && m_Parent != null)
		{
			m_TransparentClickButton.setClickable(false);
			m_PlayerCashBalanceView.setClickable(true);
			int nCount = m_Parent.GetPacketBalance();
			m_PlayerCashBalanceView.PostOnLayoutHandle();
			m_PlayerCashBalanceView.SetCashBalance(nCount);
			m_PlayerCashBalanceView.setVisibility(View.VISIBLE);
			m_PlayerCashBalanceView.setClickable(true);
			this.bringChildToFront(m_PlayerCashBalanceView);
			//????????
			//????????
			m_CashViewShownTimerClick  = CApplicationController.GetSystemTimerClick();//SystemClock.uptimeMillis();
			m_bCashViewShown = true;
		}
	}

	public void ClosePlayerChipBalanceView()
	{
		if(m_PlayerCashBalanceView != null)
		{
			m_PlayerCashBalanceView.setVisibility(View.GONE);
			//if(m_Avatar != null)
			//	this.bringChildToFront(m_Avatar);
			//if(m_BetBulletin != null)
			//	this.bringChildToFront(m_BetBulletin);
			if(m_TransparentClickButton != null)
			{	
				this.bringChildToFront(m_TransparentClickButton);
				m_TransparentClickButton.setClickable(true);
			}	
			m_CashViewShownTimerClick  = 0;
			m_bCashViewShown = false;
		}
	}

	public boolean IsPlayerChipBalanceViewShown()
	{
		boolean bRet = false;
		if(m_PlayerCashBalanceView != null)
		{
			if(m_PlayerCashBalanceView.getVisibility() != View.GONE)
				bRet = true;
		}
		return bRet;
	}
}
