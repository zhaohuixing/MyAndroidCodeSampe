package com.xgadget.SimpleGambleWheel;

import android.content.Context;
//import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
//import android.view.View.OnLongClickListener;

public class GamePlayer extends GameBaseObject 
{
    public int                     m_nChoiceNumber;    //Luck number for current bet
    public int                     m_nBetMoney;        //The money/chip pledge for current bet
    
    //Wallet
    //Packet*                 m_Packet;
    public	int		               m_Chips;
    
    //Ids
    public String               	m_PlayerName;
    public String               	m_PlayerID;
    public int                     	m_nSeatID;
    
    //Current turn
    public boolean                    m_bMyturn2Play;
    public boolean                    m_bWinThisPlay;
    
    public boolean                    m_bEnablePlayCurrentGame;
    
    public boolean                    m_bAcitvated;
    public boolean                    m_bMyself;
    
    private int                     	m_nOnlinePlayingState;
    
    private int                     	m_nCachedTransferedChip;
    
    
    private boolean                    m_bMaster;
    
    private boolean                    m_bOnlinePlayer;

    private	GamePlayerLayout			m_GamePlayerGraphicLayout;
 
    //Contextual menu
    private CPlayerContextualMenu		m_GamePlayerPopupMenu;
    
    //Player text message displayers
    //private CPlayerSendMessageView		m_OnlineTextPost;
    //private CPlayerMessageDisplayView	m_OnLineTextBoard;
    
    
    //Player Avatar animation parameters
	public int			m_nAvatarAnimationFrame;
	public long			m_AvatarTimeCount; 

	//BetView animation parameters
	public long			m_BetViewTimeCount; 
	public boolean		m_bBetViewResultAnimation;
	public boolean		m_bBetViewDrawCashBackground;
	public int			m_nBetViewAnimationFrame;
	public boolean		m_bBetViewShowBetNumbers;
	
	
    //Packet relate functions
    public void PacketInitialize(int nTotal)
    {
        m_Chips = nTotal;
    }

    public void PacketEarn(int nChip)
    {
        m_Chips += nChip;
    }

    public boolean PacketCanPay(int nBill)
    {
        boolean bRet = true;
        if(m_Chips < nBill)
        {
            bRet = false;
        }
        return bRet;
    }

    public int PacketPay(int nBill)
    {
        int nPayment = nBill; 
        if(m_Chips < nBill)
        {
            nPayment = m_Chips;
            m_Chips = 0;
        }
        else
        {
            m_Chips -= nBill;
        }
        return nPayment;
    }

    public int PacketBalance()
    {
        return m_Chips;
    }
    
    //General functions
    public void SetupPacket(int nChips)
    {
        PacketInitialize(nChips);
    }
    
    public int GetPacketBalance()
    {
        return PacketBalance();
    }

    public int AddMoneyToPacket(int nChips)
    {
        PacketEarn(nChips);
        int nRet = PacketBalance();
        return nRet;
    }
    
    public void CancelPendingBet()
    {
        if(0 <= m_nBetMoney)
        {    
            PacketEarn(m_nBetMoney);
        }    
        ClearPlayBet();
    }

    
    private void Initialize()
    {
		m_GamePlayerGraphicLayout = null;
	    m_nChoiceNumber = -1;    //Luck number for current bet
	    m_nBetMoney = -1;        //The money/chip pledge for current bet
	    
	    //Wallet
	    //Packet*                 m_Packet;
	    m_Chips = 0;
	    
	    //Ids
	    m_PlayerName = new String();
	    m_PlayerID = new String();
	    m_nSeatID = 0;
	    
	    //Current turn
	    m_bMyturn2Play = false;
	    m_bWinThisPlay = false;
	    
	    m_bEnablePlayCurrentGame = true;
	    
	    m_bAcitvated = true;
	    m_bMyself = false;
	    
	    m_nOnlinePlayingState = GameConstants.GAME_PLAYER_STATE_NORMAL;
	    
	    m_nCachedTransferedChip = 0;
	    
	    
	    m_bMaster = false;
	    
	    m_bOnlinePlayer = false;
	    
		m_nAvatarAnimationFrame = 0;
		m_AvatarTimeCount = CApplicationController.GetSystemTimerClick();
	
		m_BetViewTimeCount = CApplicationController.GetSystemTimerClick();
		m_bBetViewResultAnimation = false;
		m_nBetViewAnimationFrame = 0;
		m_bBetViewDrawCashBackground = false;
		m_bBetViewShowBetNumbers = true;
		m_GamePlayerPopupMenu = null;
		
		//m_OnlineTextPost = null;
		//m_OnLineTextBoard = null;
    }
    
	public GamePlayer() 
	{
		// TODO Auto-generated constructor stub
		super();
		Initialize();
	}
	
	
	public void AttachLayout(GamePlayerLayout layout)
	{
		m_GamePlayerGraphicLayout = layout;
		//m_GamePlayerGraphicLayout.setClickable(true);
		m_GamePlayerGraphicLayout.AttachParent(this);
		/*m_GamePlayerGraphicLayout.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				ShowMenu();
			}
		});	*/
		/*m_GamePlayerGraphicLayout.setLongClickable(true);
		m_GamePlayerGraphicLayout.setOnLongClickListener(new OnLongClickListener() 
		{
			@Override
	        public boolean onLongClick(View v) 
			{
	            // TODO Auto-generated method stub
				ShowMenu();
				return true;
	        }		
		});*/
		m_GamePlayerGraphicLayout.AddPlayerChipBalanceView();
	}

	public void AttachAvatar(GamePlayerAvatar avatar)
	{
		if(m_GamePlayerGraphicLayout != null)
		{
			avatar.AttachParent(this);
			//avatar.setClickable(true);
			//avatar.setOnClickListener(new OnClickListener() 
			//{
			//	@Override
			//	public void onClick(View arg0) 
			//	{
			//		ShowMenu();
			//	}
			//});	
			m_GamePlayerGraphicLayout.AttachAvatar(avatar);
		}	
	}
	
	public void AttachBetBulletin(GamePlayerBetView	betView)
	{
		if(m_GamePlayerGraphicLayout != null)
		{
			betView.AttachParent(this);
			/*betView.setClickable(true);
			betView.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					ShowMenu();
				}
			});*/	
			m_GamePlayerGraphicLayout.AttachBetBulletin(betView);
		}	
	}
	
	public boolean IsMyTurn()
	{
		return m_bMyturn2Play;
	}

	public boolean IsMySelf()
	{
		return m_bMyself;
	}

	public boolean WinThisTime()
	{
		return m_bWinThisPlay;
	}
	
	public void SetPlayResult(boolean bWin)
	{
	    m_bWinThisPlay = bWin;
	    this.Invalidate();
	}

	
	public boolean IsOnlinePlayer()
	{
		return m_bOnlinePlayer;
	}

	public void SetOnlinePlayer(boolean bOnline)
	{
		m_bOnlinePlayer = bOnline;
		if(m_bOnlinePlayer == false)
		{
			m_bMaster = false;
		}
	}
	
	public int GetSeatID()
	{
		return m_nSeatID;
	}
	
    public void UpdateCurrentGamePlayablity()
    {
        if(m_Delegate != null)
        {
            int nType = m_Delegate.GetGameType();
            m_bEnablePlayCurrentGame = CanPlayGame(nType);
            ClearPlayBet();
            Invalidate();
        }
    }
	
	public void UpdateLayout()
	{
        Invalidate();
	}

	public void SetPlayerName(String szName)
	{
	    m_PlayerName = szName;
	    
	    if(this.m_GamePlayerGraphicLayout != null)
	    {
	    	this.m_GamePlayerGraphicLayout.m_NameTag.setText(szName);
	    	if(this.IsMySelf() == true)
	    	{
	    		this.m_GamePlayerGraphicLayout.m_NameTag.setTextColor(Color.YELLOW);
	    	}
	    	else
	    	{
	    		this.m_GamePlayerGraphicLayout.m_NameTag.setTextColor(Color.GREEN);
	    	}
	    }
	    
	   /* 
	    if(m_OnlinePlayerNameTag && m_bOnlinePlayer)
	    {
	        [m_OnlinePlayerNameTag setText:m_PlayerName];
	        if([self IsMyself])
	            [m_OnlinePlayerNameTag setText:[StringFactory GetString_OfflineMySelfID]];
	        
	        
	        if([self IsActivated])
	            m_OnlinePlayerNameTag.hidden = NO;
	        else
	            m_OnlinePlayerNameTag.hidden = YES;
	    }
		*/
	}
	
	public void SetPlayerID(String szPlayerID)
	{
	    m_PlayerID = szPlayerID;
	}

	public void SetMyTurnFlag(boolean bMyTurn)
	{
		m_bMyturn2Play = bMyTurn;
	}
	
	public void SetMySelfFlag(boolean bMyself)
	{
	    m_bMyself = bMyself;
	    /*if(m_OnlinePlayerNameTag && m_bOnlinePlayer)
	    {
	        if([self IsMyself])
	        {    
	            [m_OnlinePlayerNameTag setText:[StringFactory GetString_OfflineMySelfID]];
	            [m_OnlinePlayerNameTag setTextColor:[UIColor blueColor]];
	        }    
	        else
	        {
	            [m_OnlinePlayerNameTag setTextColor:[UIColor yellowColor]];
	            if(m_PlayerName)
	                [m_OnlinePlayerNameTag setText:m_PlayerName];
	            else
	                [m_OnlinePlayerNameTag setText:@""];
	        }
	    }*/
	}
	
	public void SetSeatID(int nSeatID)
	{
	    m_nSeatID = nSeatID;
	    UpdateLayout();
	}
	

	public String GetPlayerName()
	{
		return m_PlayerName;
	}
	
	public String GetPlayerID()
	{
		return m_PlayerID;
	}
	
	public void OnTimerEvent()
	{
		if(m_GamePlayerGraphicLayout != null)
			m_GamePlayerGraphicLayout.OnTimerEvent();
		if(this.m_GamePlayerPopupMenu != null)
			m_GamePlayerPopupMenu.OnTimerEvent();
	}
	
	public void Invalidate()
	{
		if(m_GamePlayerGraphicLayout != null)
			m_GamePlayerGraphicLayout.Invalidate();
	}

	public boolean IsEnabled()
	{
	    if(m_bAcitvated == false)
	        return false;
	    
	    return m_bEnablePlayCurrentGame;
	}
	
	private void ChangeWinFlyerState()
	{
		int nState = SimpleGambleWheel.m_ApplicationController.GetGameState();
		if(nState == GameConstants.GAME_STATE_RESULT && this.WinThisTime() == true)
		{
	        if(m_GamePlayerGraphicLayout != null)
	        	m_GamePlayerGraphicLayout.StartWinFlyerAnimation();
		}
		else
		{
	        if(m_GamePlayerGraphicLayout != null && m_GamePlayerGraphicLayout.IsWinFlyerActive() == true)
	        	m_GamePlayerGraphicLayout.StopWinFlyerAnimation();
		}
		//if(this.IsOnlinePlayer() == false)
		//{	
		//	if(nState == GameConstants.GAME_STATE_RESET)
		//	{
		//		ClearPlayBet();
		//	}
		//}	
	}
	
	public void MakePlayBet(int nLuckNumber, int nBetPledge)
	{
	    m_nChoiceNumber = nLuckNumber; 
	    m_nBetMoney = PacketPay(nBetPledge);
	    HidePlayBet();
	}
	
	public boolean AlreadyMadePledge()
	{
		boolean bRet = false;
		
		if(0 < m_nChoiceNumber && 0 < m_nBetMoney)
			bRet = true;
		
		return bRet;
	}
	
	public void ShowPlayBet()
	{
        if(m_GamePlayerGraphicLayout != null)
        	m_GamePlayerGraphicLayout.ShowPlayBet();
		this.Invalidate();
	}

	public void HidePlayBet()
	{
        if(m_GamePlayerGraphicLayout != null)
        	m_GamePlayerGraphicLayout.HidePlayBet();
		this.Invalidate();
	}
	
	public void ClearPlayBet()
	{
	    m_nChoiceNumber = -1; 
	    m_nBetMoney = -1;
		this.Invalidate();
	}

	public int GetPlayBetLuckNumber()
	{
	    return m_nChoiceNumber;
	}

	public int GetPlayBet()
	{
	    return m_nBetMoney;
	}
	
	public void GameStateChange()
	{
	    if(IsEnabled() == true)
	    {   
	       ChangeWinFlyerState();
	    }
	    else
	    {
	        if(m_GamePlayerGraphicLayout != null && m_GamePlayerGraphicLayout.IsWinFlyerActive() == true)
	        	m_GamePlayerGraphicLayout.StopWinFlyerAnimation();
	    }	    
	    this.Invalidate();
	}
	
	public boolean CanPlayGame(int nType)
	{
		boolean bRet = false;
        int nChips = PacketBalance();
        switch (nType) 
        {
            case GameConstants.GAME_TYPE_8LUCK:
                if(GameConstants.GAME_BET_THRESHOLD_8LUCK <= nChips)
                    bRet = true;
                break;
            case GameConstants.GAME_TYPE_6LUCK:
                if(GameConstants.GAME_BET_THRESHOLD_6LUCK <= nChips)
                    bRet = true;
                break;
            case GameConstants.GAME_TYPE_4LUCK:
                if(GameConstants.GAME_BET_THRESHOLD_4LUCK <= nChips)
                    bRet = true;
                break;
            case GameConstants.GAME_TYPE_2LUCK:
                if(GameConstants.GAME_BET_THRESHOLD_2LUCK <= nChips)
                    bRet = true;
                break;
        }
		
		return bRet;
	}
	
	public void Activate(boolean bActivated)
	{
		HideMenu();
	    m_bAcitvated = bActivated;
	    if(m_bAcitvated == true)
	    {
/*	        m_MySeat.hidden = NO;
	        if(m_bOnlinePlayer)
	        {
	            m_OnlinePlayerNameTag.hidden = NO;
	            if([self IsMyself])
	            {    
	                [m_OnlinePlayerNameTag setText:[StringFactory GetString_OfflineMySelfID]];
	                [m_OnlinePlayerNameTag setTextColor:[UIColor blueColor]];
	            }
	            else 
	            {
	                [m_OnlinePlayerNameTag setTextColor:[UIColor yellowColor]];
	                if(m_PlayerName)
	                {    
	                    [m_OnlinePlayerNameTag setText:m_PlayerName];
	                }    
	                else
	                {    
	                    [m_OnlinePlayerNameTag setText:@""];
	                }
	            }     
	        }
	        else 
	        {
	            m_OnlinePlayerNameTag.hidden = YES;
	        }*/
	    }
	    else
	    {
/*	        [m_PopupMenu CloseMenu];
	        [m_OnlinePlayerNameTag setText:@""];
	        m_OnlinePlayerNameTag.hidden = YES;
	        m_MySeat.hidden = YES;
	        [m_MySeat ReleaseOnlineAvatarImage];
	        if(m_CachedGKMatchPlayer != nil)
	        {
	            [m_CachedGKMatchPlayer release];
	            m_CachedGKMatchPlayer = nil;
	        }
	        if(m_PlayerName)
	        {    
	            [m_PlayerName release];
	            m_PlayerName = nil;
	        }    
	        if(m_PlayerID)
	        {    
	            [m_PlayerID release];
	            m_PlayerID = nil;
	        }    
	        m_nSeatID = -1;
	        m_bMyself = NO;
	        m_PlayerOnlineState = GAME_ONLINE_PLAYER_STATE_UNKNOWN;
	        m_nChoiceNumber = -1;    //Luck number for current bet
	        m_nBetMoney = -1;        //The money/chip pledge for current bet
	        [m_Packet Initialize:0];
	        if(m_OnLineTextBoard != nil)
	        {    
	            [m_OnLineTextBoard CleanTextMessage];
	            if([m_OnLineTextBoard IsOpened])
	                [m_OnLineTextBoard CloseView:NO];
	        }   
	        if(m_OnlineTextPost != nil)
	        {
	            [m_OnlineTextPost CleanTextMessage];
	            if([m_OnlineTextPost IsOpened])
	                [m_OnlineTextPost CloseView:NO];
	        }*/
	    }
	}

	public boolean IsActivated()
	{
	    return m_bAcitvated;
	}
	

	public int GenerateAutoOffLineBetLuckNumber(int nType)
	{
		int nRet = -1;
		if(m_bAcitvated == false)
			return nRet;
    
		int nRand = GameUitltyHelper.CreateRandomNumberWithSeed(m_nSeatID+1);
		if(nRand < 0)
			nRand *= -1;
		int nThreshold = GameUitltyHelper.GetGameLuckNumberThreshold(nType);
		nRet = nRand%nThreshold + 1;
		return nRet;
	}

	public int GenerateAutoOffLineBetPledge(int nType)
	{
		int nRet = -1;
		if(m_bAcitvated == false)
			return nRet;
        
        int nRand = GameUitltyHelper.CreateRandomNumberWithSeed(m_nSeatID+1);
		if(nRand < 0)
			nRand *= -1;
        int nThreshold = GameUitltyHelper.GetGameBetPledgeThreshold(nType);
        int nBalance = PacketBalance();
        int nDiff = nBalance - nThreshold;
        if(nDiff < 0)
            return nBalance;
        
        nRet = nThreshold + nRand%nThreshold;
        return nRet;
	}

	public CPinActionLevel GenerateAutoOffLineAction()
	{
		CPinActionLevel action = new CPinActionLevel();
		action.CreateRandomLevel();
		return action;
	}
	
	public void ReloadMenus()
	{
		if(SimpleGambleWheel.m_ApplicationController == null || 
		SimpleGambleWheel.m_ApplicationController.m_CurrentActivity == null ||
		SimpleGambleWheel.m_ApplicationController.m_GameController == null ||
		SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_GameLayoutContainer == null)
			return;
		
		Context context = SimpleGambleWheel.m_ApplicationController.m_CurrentActivity;
		if(m_GamePlayerPopupMenu != null)
		{
			int nCount = m_GamePlayerPopupMenu.getChildCount();
			if(0 < nCount)
			{
		        for (int i = 0; i < nCount; i++) 
		        {
		            m_GamePlayerPopupMenu.getChildAt(i).setVisibility(View.GONE);
		        }			
				m_GamePlayerPopupMenu.removeAllViews();
			}
			SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_GameLayoutContainer.removeView(m_GamePlayerPopupMenu);
			m_GamePlayerPopupMenu.setVisibility(View.GONE);
			m_GamePlayerPopupMenu = null;
		}
		m_GamePlayerPopupMenu = new CPlayerContextualMenu(context);
		m_GamePlayerPopupMenu.m_Parent = this;
		m_GamePlayerPopupMenu.m_LayoutContainer = SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_GameLayoutContainer;
		int left = 0;
		int top = 0;
		int width = GameLayoutConstant.GetCurrentAvatarSize();
		int height = width/2;
		if(m_GamePlayerGraphicLayout != null)
		{
			left = m_GamePlayerGraphicLayout.getLeft();
			top = m_GamePlayerGraphicLayout.getTop();
			width = m_GamePlayerGraphicLayout.getWidth();
			height = m_GamePlayerGraphicLayout.getHeight();
		}
		MyAbsoluteLayout.LayoutParams lp = new MyAbsoluteLayout.LayoutParams(width, height, left, top);
		m_GamePlayerPopupMenu.setLayoutParams(lp);
		SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_GameLayoutContainer.addView(m_GamePlayerPopupMenu);
		SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_GameLayoutContainer.bringChildToFront(m_GamePlayerPopupMenu);
		
		if(SimpleGambleWheel.m_ApplicationController.m_GameController.IsOnline() == true)
		{
			LoadOnlinePlayerMenuItems();
		}
		else
		{
			LoadOfflinePlayerMenuItems();
		}
	}
	
	public void LoadOnlinePlayerMenuItems()
	{
		if(SimpleGambleWheel.m_ApplicationController == null || 
		SimpleGambleWheel.m_ApplicationController.m_CurrentActivity == null ||
		SimpleGambleWheel.m_ApplicationController.m_GameController == null)
			return;
		
		if(m_GamePlayerPopupMenu == null)
			return;
		int nCount = m_GamePlayerPopupMenu.getChildCount();
		if(0 < nCount)
		{
	        for (int i = 0; i < nCount; i++) 
	        {
	            m_GamePlayerPopupMenu.getChildAt(i).setVisibility(View.GONE);
	        }			
			m_GamePlayerPopupMenu.removeAllViews();
		}

		int width = GameLayoutConstant.GetCurrentAvatarSize();
		int height = width/2;
		Context context = SimpleGambleWheel.m_ApplicationController.m_CurrentActivity;
		
		CPlayerContextualMenuItem pMenuItemChips = new CPlayerContextualMenuItem(context);
		MyAbsoluteLayout.LayoutParams lp1 = new MyAbsoluteLayout.LayoutParams(width, height, 0, 0);
		pMenuItemChips.setLayoutParams(lp1);
		pMenuItemChips.SetLabelText(StringFactory.GetString_Chips());
		pMenuItemChips.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				ShowPlayerChipsBalanceView();
			}
		});		
		
		m_GamePlayerPopupMenu.addView(pMenuItemChips);

		CPlayerContextualMenuItem pMenuItemMessage = new CPlayerContextualMenuItem(context);
		MyAbsoluteLayout.LayoutParams lp2 = new MyAbsoluteLayout.LayoutParams(width, height, 0, height);
		pMenuItemMessage.setLayoutParams(lp2);
		pMenuItemMessage.SetLabelText(StringFactory.GetString_Message());
		pMenuItemMessage.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				HandleOnlinePlayerMessageEvent();
			}
		});		
		m_GamePlayerPopupMenu.addView(pMenuItemMessage);
		
		if(this.IsMySelf() == true)
		{
			CPlayerContextualMenuItem pMenuItemTransfer = new CPlayerContextualMenuItem(context);
			MyAbsoluteLayout.LayoutParams lp3 = new MyAbsoluteLayout.LayoutParams(width, height, 0, 2*height);
			pMenuItemTransfer.setLayoutParams(lp3);
			pMenuItemTransfer.SetLabelText(StringFactory.GetString_SendMoney());
			pMenuItemTransfer.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					HandleOnlineChipTransferEvent();
				}
			});		
			m_GamePlayerPopupMenu.addView(pMenuItemTransfer);
		}
	
		m_GamePlayerPopupMenu.PostOnLayoutHandle();
		ShowMenu();
		HideMenu();
	}
	
	public void LoadOfflinePlayerMenuItems()
	{
		if(SimpleGambleWheel.m_ApplicationController == null || 
		SimpleGambleWheel.m_ApplicationController.m_CurrentActivity == null ||
		SimpleGambleWheel.m_ApplicationController.m_GameController == null)
			return;
		
		if(m_GamePlayerPopupMenu == null)
			return;
		int nCount = m_GamePlayerPopupMenu.getChildCount();
		if(0 < nCount)
		{
	        for (int i = 0; i < nCount; i++) 
	        {
	            m_GamePlayerPopupMenu.getChildAt(i).setVisibility(View.GONE);
	        }			
			m_GamePlayerPopupMenu.removeAllViews();
		}		
		int width = GameLayoutConstant.GetCurrentAvatarSize();
		int height = width/2;
		Context context = SimpleGambleWheel.m_ApplicationController.m_CurrentActivity;
		
		CPlayerContextualMenuItem pMenuItemChips = new CPlayerContextualMenuItem(context);
		MyAbsoluteLayout.LayoutParams lp1 = new MyAbsoluteLayout.LayoutParams(width, height, 0, 0);
		pMenuItemChips.setLayoutParams(lp1);
		pMenuItemChips.SetLabelText(StringFactory.GetString_Chips());
		pMenuItemChips.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				ShowPlayerChipsBalanceView();
			}
		});		
		m_GamePlayerPopupMenu.addView(pMenuItemChips);

		CPlayerContextualMenuItem pMenuItemTransfer = new CPlayerContextualMenuItem(context);
		MyAbsoluteLayout.LayoutParams lp2 = new MyAbsoluteLayout.LayoutParams(width, height, 0, height);
		pMenuItemTransfer.setLayoutParams(lp2);
		pMenuItemTransfer.SetLabelText(StringFactory.GetString_SendMoney());
		pMenuItemTransfer.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				HandleOfflinePlayerChipTransferEvent();
			}
		});		
		m_GamePlayerPopupMenu.addView(pMenuItemTransfer);
		
		m_GamePlayerPopupMenu.PostOnLayoutHandle();
		
		ShowMenu();
		HideMenu();
		
	}
	
	public void UpdateInvisiableMenuLayout()
	{
		if(m_GamePlayerPopupMenu != null && m_GamePlayerPopupMenu.getVisibility() == View.GONE)
		{
			m_GamePlayerPopupMenu.PostOnLayoutHandle();
		}
	}
	
	public void ShowMenu()
	{
		if(m_GamePlayerPopupMenu == null || m_GamePlayerPopupMenu.getVisibility() != View.GONE)
			return;
		
		SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_GameLayoutContainer.bringChildToFront(m_GamePlayerPopupMenu);
		m_GamePlayerPopupMenu.ShowMenu();
		SimpleGambleWheel.m_ApplicationController.UpdateGameUI();
	}
	
	public void HideMenu()
	{
		if(m_GamePlayerPopupMenu == null)
			return;
		
		m_GamePlayerPopupMenu.HideMenu();
	}
	
	private void HandleOfflinePlayerChipTransferEvent()
	{
		HideMenu();
		if(SimpleGambleWheel.m_ApplicationController != null)
			SimpleGambleWheel.m_ApplicationController.OpenPlayerOfflineTransferChipsSubView(this.GetSeatID());
	}
	
	private void ShowPlayerChipsBalanceView()
	{
		HideMenu();
		if(m_GamePlayerGraphicLayout != null)
		{
			m_GamePlayerGraphicLayout.OpenPlayerChipBalanceView();
		}	
	}

	public void ClosePlayerChipsBalanceView()
	{
		HideMenu();
		if(m_GamePlayerGraphicLayout != null)
		{
			m_GamePlayerGraphicLayout.ClosePlayerChipBalanceView();
		}	
	}
	
	private void HandleOnlinePlayerMessageEvent()
	{
		HideMenu();
		if(this.IsMySelf() == true)
		{
			SimpleGambleWheel.m_ApplicationController.OpenSendOnlineTextMessagePanel();
		}
		else
		{
			SimpleGambleWheel.m_ApplicationController.ShowRecievedOnlinePlayerMessageBoard();
		}
	}
	
	private void HandleOnlineChipTransferEvent()
	{
		HideMenu();
		if(this.IsMySelf() == true)
			SimpleGambleWheel.m_ApplicationController.OpenPlayerOnlineTransferChipsSubView();
	}
	
	//??????????????????????
    public void ShowOnlineMessage(String text)
    {
    	String szFullMessage = this.m_PlayerName + ":" + text;
    	SimpleGambleWheel.m_ApplicationController.ShowOnlinePlayerMessage(szFullMessage);
    }

    public void SetOnlinePlayingState(int nState)
    {
        m_nOnlinePlayingState = nState;
        if(m_nOnlinePlayingState == GameConstants.GAME_ONLINE_PLAYER_STATE_RESET && SimpleGambleWheel.m_ApplicationController.m_GameController.GetGameState() == GameConstants.GAME_STATE_RESET)
        {    
            this.UpdateOnlinePlayingStateByMoneyBalance();
        }    
        this.GameStateChange();
    }

    public int GetOnlinePlayingState()
    {
        return m_nOnlinePlayingState;
    }

    public void UpdateOnlinePlayingStateByMoneyBalance()
    {
        this.UpdateCurrentGamePlayablity();
    }

    public void SetOnlineGameMaster(boolean bMaster)
    {
    	this.m_bMaster = bMaster;
    	this.m_bOnlinePlayer = true;
    }
    
    public boolean IsOnlineGameMaster()
    {
        return this.m_bMaster;
    }

    public void ShowBalance()
    {
    	this.ShowPlayerChipsBalanceView();
    }

    public void SetCachedTransferedChipNumber(int nCachedSentChips)
    {
        m_nCachedTransferedChip = nCachedSentChips;
    }
    
    public int GetCachedTransferedChipNumber()
    {
        return m_nCachedTransferedChip;
    }
    
    public void RestoreCachedTransferChip()
    {
    	if(0 < m_nCachedTransferedChip)
    	{
    		this.AddMoneyToPacket(m_nCachedTransferedChip);
    		m_nCachedTransferedChip = 0;
    	}
    }

    
    public void IntializeOnlinePlayerInfo(String szName, String szID, int nSeatID, boolean bMyself)
    {
        m_PlayerName = szName;
        m_PlayerID = szID;
        m_bMyself = bMyself;
        m_nSeatID = nSeatID;
        this.m_bOnlinePlayer = true;
    	this.ReloadMenus();
    	if(m_bMyself == true)
    		m_PlayerName = StringFactory.GetString_Me();
	    if(this.m_GamePlayerGraphicLayout != null)
	    {
	    	this.m_GamePlayerGraphicLayout.m_NameTag.setText(m_PlayerName);
	    }
    	if(this.IsMySelf() == true)
    	{
    		this.m_GamePlayerGraphicLayout.m_NameTag.setTextColor(Color.YELLOW);
    	}
    	else
    	{
    		this.m_GamePlayerGraphicLayout.m_NameTag.setTextColor(Color.GREEN);
    	}
    	
    }
    
    public void ShowAndHidePlayer(boolean bShown)
    {
    	if(bShown == true)
    	{	
    		if(m_GamePlayerGraphicLayout != null)
    			m_GamePlayerGraphicLayout.setVisibility(View.VISIBLE);
        
    		if(m_GamePlayerPopupMenu != null)
    			m_GamePlayerPopupMenu.setVisibility(View.VISIBLE);
    	}
    	else
    	{
    		if(m_GamePlayerGraphicLayout != null)
    			m_GamePlayerGraphicLayout.setVisibility(View.GONE);
        
    		if(m_GamePlayerPopupMenu != null)
    			m_GamePlayerPopupMenu.setVisibility(View.GONE);
    	}
    }
    
    public void ForceUpdatePlayersUI()
    {
    	if(this.m_GamePlayerGraphicLayout != null)
    	{
    		this.m_GamePlayerGraphicLayout.PostOnLayoutHandle();
    	}
    }
}
