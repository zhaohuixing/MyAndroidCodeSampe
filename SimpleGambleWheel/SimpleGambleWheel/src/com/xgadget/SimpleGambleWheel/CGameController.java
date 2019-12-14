package com.xgadget.SimpleGambleWheel;


import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.xgadget.XSGService.XSGAPIConstants;
import com.xgadget.XSGService.XSGAPIGameServiceManager;
import com.xgadget.XSGService.XSGAPIGameServiceManagerDelegate;
//import com.xgadget.XSGService.XSGAPIMessage;
import com.xgadget.XSGService.XSGAPINetworking;
import com.xgadget.XSGService.XSGAPIReleaseConfigure;
import com.xgadget.XSGService.XSGAPIScoreBoard;
import com.xgadget.XSGService.XSGAPIScoreBoardDelegate;
import com.xgadget.XSGService.XSGAPIScoreBoardIntScore;
import com.xgadget.XSGService.XSGAPIUser;
import com.xgadget.XSGService.XSGAPIUserDelegate;

import android.util.Log;
import android.view.MotionEvent;

public class CGameController implements XSGAPIGameServiceManagerDelegate, XSGAPIUserDelegate, XSGAPIScoreBoardDelegate
{
	private GameView					m_GameView;
    public CGameLobby					m_GameLobby;
	private GamePlayGroupBase           m_ActiveGameGroup;
	private GamePlayOnlineGroup         m_OnlineGameGroup;
	private GamePlayOfflineGroup        m_OfflineGameGroup;
	
	private XSGAPIUser					m_CachedInvitationSender;
    private XSGAPIScoreBoard 			m_GameScoreBoard;
	
	public CGameLobby GetGameLobby()
	{
		return m_GameLobby;
	}
	
	public int GetWinScopeIndex()
    {
    	return m_GameLobby.GetWinScopeIndex();
    }
	
	public void PauseGame()
	{
	    m_GameLobby.PauseGame();
	    if(m_ActiveGameGroup != null)
	        m_ActiveGameGroup.PauseGame();
	    if(Configuration.canPlaySound() == true)
	    {
	        SoundSource.StopAllPlayingSound();
	    }
	}

	public void ResumeGame()
	{
	    //?????m_GameLobby.ResumeGame();
	}
	
	private void SwitchToOfflineState()
	{
		m_OnlineGameGroup.m_GameLobby = null;
		m_OfflineGameGroup.m_GameLobby = m_GameLobby;
		m_ActiveGameGroup = m_OfflineGameGroup;
		m_CachedInvitationSender = null;
		m_GameLobby.ReloadPlayerMenus();
	}

	public void SwitchToOnlineState()
	{
		m_OfflineGameGroup.m_GameLobby = null;
		m_OnlineGameGroup.m_GameLobby = m_GameLobby;
		m_ActiveGameGroup = m_OnlineGameGroup;
		m_CachedInvitationSender = null;
	}
	
	public void StartGameSection()
	{
		if(m_ActiveGameGroup != null)
		{
			m_ActiveGameGroup.StartGameSection();
		}
		else
		{
			SwitchToOfflineState();
			m_ActiveGameGroup.StartGameSection();
		}
	}
	
	public CGameController() 
	{
		// TODO Auto-generated constructor stub
		m_GameView = null;
		m_GameLobby = new CGameLobby(); 
		m_OnlineGameGroup = new GamePlayOnlineGroup();
		m_OfflineGameGroup = new GamePlayOfflineGroup();
		m_ActiveGameGroup = null;
		m_CachedInvitationSender = null;
		XSGAPIGameServiceManager.RegisterServiceManagerDelegate(this);
		
		m_GameScoreBoard = new XSGAPIScoreBoard(XSGAPIScoreBoard.XSGAPI_SB_SCORE_SORT, this);
		m_GameScoreBoard.createHighScoresDomain();
		SwitchToOfflineState();
	}
	
	public void AttachCashMachine(GameCashBalanceView	cashView)
	{
		if(m_GameLobby != null)
			m_GameLobby.AttachCashMachine(cashView);
	}
	
	public void AttachGameView(GameView gameView)
	{
		m_GameView = gameView;
		m_GameView.invalidate();
	}

	public void AttachWheel(CCompassRender	wheel)
	{
		if(m_GameLobby != null)
			m_GameLobby.AttachWheel(wheel);
	}
	
	public void AttachPointer(CPinRender pointer)
	{
		if(m_GameLobby != null)
			m_GameLobby.AttachPointer(pointer);
	}
	
	public void AttachPlayer0Layout(GamePlayerLayout player0layout)
	{
		if(m_GameLobby != null)
			m_GameLobby.AttachPlayer0Layout(player0layout);
	}
	
	public void AttachPlayer1Layout(GamePlayerLayout player1layout)
	{
		if(m_GameLobby != null)
			m_GameLobby.AttachPlayer1Layout(player1layout);
	}

	public void AttachPlayer2Layout(GamePlayerLayout player2layout)
	{
		if(m_GameLobby != null)
			m_GameLobby.AttachPlayer2Layout(player2layout);
	}

	public void AttachPlayer3Layout(GamePlayerLayout player3layout)
	{
		if(m_GameLobby != null)
			m_GameLobby.AttachPlayer3Layout(player3layout);
	}
	
	public void AttachActivePlayerIndicator(ActivePlayerAnimator indicator)
	{
		if(m_GameLobby != null)
			m_GameLobby.AttachActivePlayerIndicator(indicator);
	}

	public void OnTimerEvent()
	{
		if(m_GameLobby != null)
			m_GameLobby.OnTimerEvent();
		if(m_ActiveGameGroup != null)
			m_ActiveGameGroup.OnTimerEvent(); 
	}
	
	public boolean HandleTouchEvent(MotionEvent evnet)
	{
		boolean bRet = true;
		
		if(m_ActiveGameGroup != null)
		{
			m_ActiveGameGroup.HandleTouchEvent(evnet);
		}
		return bRet;
	}
	
	public int GetMyCurrentMoney()
	{
		int nRet = -1;
		if(m_ActiveGameGroup != null)
			nRet = m_ActiveGameGroup.GetMyCurrentMoney(); 
	    return nRet;
	}

	public GamePlayer GetMyself()
	{
		GamePlayer player = null;
		if(m_ActiveGameGroup != null)
			player = m_ActiveGameGroup.GetMyself(); 
	    return player;
	}
	
	public void AddMoneyToMyAccount(int nChips)
	{
		if(m_ActiveGameGroup != null)
			 m_ActiveGameGroup.AddMoneyToMyAccount(nChips); 
	}

	public int GetPlayerCurrentMoney(int nSeat)
	{
		int nRet = -1;
		if(m_ActiveGameGroup != null)
			nRet = m_ActiveGameGroup.GetPlayerCurrentMoney(nSeat); 
	    return nRet;
	}

	public void AddMoneyToPlayerAccount(int nChips,  int nSeat)
	{
		if(m_ActiveGameGroup != null)
			m_ActiveGameGroup.AddMoneyToPlayerAccount(nChips, nSeat);
	}

	public String GetPlayerName(int nSeatID)
	{
		String strRet = null;
		if(m_ActiveGameGroup != null)
			strRet = m_ActiveGameGroup.GetPlayerName(nSeatID);
	    return strRet;
	}

	public void CancelPendPlayerBet()
	{
		if(m_ActiveGameGroup != null)
			m_ActiveGameGroup.CancelPendPlayerBet();
	}

	public int GetGameType()
	{
		int nRet = GameConstants.GAME_TYPE_8LUCK;
		if(m_GameLobby != null)
			nRet = m_GameLobby.GetGameType(); 
	    return nRet;
	}

	public int GetGameState()
	{
		int nRet = GameConstants.GAME_STATE_READY;
		
		if(this.m_GameLobby != null)
			nRet = m_GameLobby.GetGameState();
		
		return nRet;
	}
	
    public void SetGameState(int nState)
    {
		if(m_GameLobby != null)
			m_GameLobby.SetGameState(nState);
    }
    
    public void SetGameType(int nType)
    {
		if(m_GameLobby != null)
			m_GameLobby.SetGameType(nType);
    }
    
    public void SetThemeType(int nType)
    {
		if(m_GameLobby != null)
			m_GameLobby.SetThemeType(nType);
    }
    
	public void SetSystemOnHold(boolean bOnHold)
	{
		if(m_ActiveGameGroup != null)
			m_ActiveGameGroup.SetSystemOnHold(bOnHold);
	}

	public boolean IsSystemOnHold()
	{
		boolean bRet = false;
		if(m_ActiveGameGroup != null)
			bRet = m_ActiveGameGroup.IsSystemOnHold();
		return bRet;
	}
	
	public boolean IsOnline()
	{
		boolean bRet = false;
		
		if(m_ActiveGameGroup != null)
			bRet = m_ActiveGameGroup.GetOnlineGroup();
		
		return bRet;
	}

	public void GotoOnLineGame()
	{
	    if(m_ActiveGameGroup != null && m_ActiveGameGroup.GetOnlineGroup() == false)
	    {
	    	m_ActiveGameGroup.RestoreOffLineReadyState();
	    }
	    SimpleGambleWheel.m_ApplicationController.HandleMainOnlineGameButtonClick();
	}
	
	public void ResetGameStateAndType()
	{
		//if(m_ActiveGameGroup != null)
		//	m_ActiveGameGroup.ResetGameStateAndType();
	    if(Configuration.isDirty())
	    {
	        if(Configuration.canPlaySound() == false)
	        {
	           SoundSource.StopAllPlayingSound();
	        }
	        else
	        {
	            SoundSource.PlayWheelStaticSound();
	        }
	        
	        if(Configuration.isOnline())
	        {
	            this.GotoOnLineGame();
	        }
	        else
	        {    
	            RestoreOffLineReadyState();
	        }    
	        Configuration.resetDirty();
	    }    
		
	}
	
    public void PlayCurrentGameStateSound()
    {
    	if(this.m_GameLobby != null)
    	{
    		this.m_GameLobby.PlayCurrentGameStateSound();
    	}
    }

	
	public void RestoreOffLineReadyState()
	{
		if(m_ActiveGameGroup != null)
			m_ActiveGameGroup.RestoreOffLineReadyState();
	}
	
	public boolean CanPlayerPlayGame(int nType, int nSeatID)
	{
		boolean bRet = false;
		if(m_ActiveGameGroup != null)
			bRet = m_ActiveGameGroup.CanPlayerPlayGame(nType, nSeatID);
		return bRet;
	}
	
    public void SpinGambleWheel(CPinActionLevel action)
    {
        m_GameLobby.SpinGambleWheel(action);
        if(m_ActiveGameGroup != null)
        	m_ActiveGameGroup.ForceClosePlayerMenus();
        if(m_ActiveGameGroup.GetOnlineGroup() == true)
        {
            SimpleGambleWheel.m_ApplicationController.ShowStatusBar(StringFactory.GetString_Spinning());
        }
    }

    public GamePlayer GetPlayer(int nSeatID)
    {
    	GamePlayer player = null;
        if(m_ActiveGameGroup != null)
        	player = m_ActiveGameGroup.GetPlayer(nSeatID);
    	
    	return player;
    }
 
    public void PlayerFinishPledge(int nSeat, int nLuckNumber, int nBetMoney)
    {
        if(m_ActiveGameGroup != null)
        	m_ActiveGameGroup.PlayerFinishPledge(nSeat, nLuckNumber, nBetMoney);
    }
    
    public void PlayerTranfereChipsFrom(int nSeat, int nReceiverID,  int nChips)
    {
        if(m_ActiveGameGroup != null)
        	m_ActiveGameGroup.PlayerTranfereChipsFrom(nSeat, nReceiverID,  nChips);
    }
    
    public int GetCurrentActivePlayingSeat()
    {
    	int nRet = -1;
        if(m_ActiveGameGroup != null)
        	nRet = m_ActiveGameGroup.GetCurrentActivePlayingSeat();
    	
        return nRet;
    }
    
    public void UpdateGameUI()
    {
        if(m_ActiveGameGroup != null)
        	m_ActiveGameGroup.UpdateGameUI();
    }
    
    public void UpdateForGameStateChange()
    {
        if(m_ActiveGameGroup != null)
        	m_ActiveGameGroup.UpdateForGameStateChange();
    }
    
    public void ReloadPlayerMenus()
    {
    	if(m_GameLobby != null)
    	{
    		m_GameLobby.ReloadPlayerMenus();
    	}
    }
    
    public void shutdownCurrentGame()
    {
        if(this.IsOnline() == true)
        	this.GotoOfflineGame();
    }

    public void GotoOfflineGame()
    {
        if(m_ActiveGameGroup != null && m_ActiveGameGroup.GetOnlineGroup() == true)
        {
            m_ActiveGameGroup.ShutdownSection();
            this.SwitchToOfflineState();
            m_ActiveGameGroup.StartGameSection();
            SimpleGambleWheel.m_ApplicationController.OnlineRequestCancelled();
        }    
    }
   
    public void ShutdownGameSection()
    {
    	//Online game section, go to offline
        if(m_ActiveGameGroup != null && m_ActiveGameGroup.GetOnlineGroup() == true)
        {
        	m_ActiveGameGroup.ShutdownSection();
        	this.SwitchToOfflineState();
        	m_ActiveGameGroup.StartGameSection();
            SimpleGambleWheel.m_ApplicationController.OnlineRequestCancelled();
        }
        else if(m_ActiveGameGroup != null && m_ActiveGameGroup.GetOnlineGroup() == false) //Offline game section. go to online
        {
            PauseGame();
        	m_ActiveGameGroup.ShutdownSection();
        	this.SwitchToOnlineState();
        	m_ActiveGameGroup.StartGameSection();
            SimpleGambleWheel.m_ApplicationController.OnlineRequestDone();
        }
    	//???????
    }
    
    public void AbsoluteShutdownOnlineGame()
    {
    	//Online game section, go to offline
        if(m_ActiveGameGroup != null && m_ActiveGameGroup.GetOnlineGroup() == true)
        {
        	((GamePlayOnlineGroup)m_ActiveGameGroup).AbsoluteShutdownOnlineGame();
        	this.SwitchToOfflineState();
        }
    }
////////////////////////////////////////////////////////////////////////////
//
//Online related methods
//
/////////////////////////////////////////////////////////////////////////// 
    
    public void AskWantToXSGAPIOnlineService()
    {
    	//?????
    	//SimpleGambleWheel.m_ApplicationController.HandleOpenlineSetupSubView(false);
    	SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.AskForOnlineGameOption();
    }

    public void CheckAndUpdateOnlineSetting()
    {
        if(XSGAPINetworking.isNetworkAvailable(XSGAPIConstants.GetContext()) == true)
        {
            XSGAPIGameServiceManager.RegisterServiceManagerDelegate(this);
            String szNickName = GameScore.GetOnlineNickName();
            if(GameScore.IsOnlineServiceEnable() == false || szNickName == null || szNickName.length() <= 0 || GameScore.HasOnlinePlayerID() == false)
            {
            	this.AskWantToXSGAPIOnlineService();
            }
            else
            {
                if(XSGAPIGameServiceManager.getXSGAPIGameManager().IsConnectToAppUserDBDomain() == false)
                {
                	XSGAPIGameServiceManager.getXSGAPIGameManager().ConnectToAppUserDBDomain();
                    return;
                }
                XSGAPIUser.registerLocalUserDelegate(this);
                XSGAPIUser.localUser().LoadCurrentUserInformation();
                XSGAPIGameServiceManager.getXSGAPIGameManager().IntializeLocalPlayerSetting(XSGAPIUser.localUser());
                return;
            }
        }
    }
    
    public void CheckAndUpdateOnlineSettingWithoutAlert()    
    {
        if(XSGAPINetworking.isNetworkAvailable(XSGAPIConstants.GetContext()) == true)
        {
            XSGAPIGameServiceManager.RegisterServiceManagerDelegate(this);
            String szNickName = GameScore.GetOnlineNickName();
            if(GameScore.IsOnlineServiceEnable() == false || szNickName == null || szNickName.length() <= 0 || GameScore.HasOnlinePlayerID() == false)
            {
            	return;
            }
            else
            {
                if(XSGAPIGameServiceManager.getXSGAPIGameManager().IsConnectToAppUserDBDomain() == false)
                {
                	XSGAPIGameServiceManager.getXSGAPIGameManager().ConnectToAppUserDBDomain();
                    return;
                }
                XSGAPIUser.registerLocalUserDelegate(this);
                XSGAPIUser.localUser().LoadCurrentUserInformation();
                XSGAPIGameServiceManager.getXSGAPIGameManager().IntializeLocalPlayerSetting(XSGAPIUser.localUser());
                return;
            }
        }
    }
    public void ReconfigureOnlineConfigure()
    {
        if(XSGAPINetworking.isNetworkAvailable(XSGAPIConstants.GetContext()) == true && GameScore.IsOnlineServiceEnable() == true)
        {
            if(XSGAPIGameServiceManager.getXSGAPIGameManager().InOnlineGamePlay() == false)
            {
                XSGAPIGameServiceManager.RegisterServiceManagerDelegate(this);
                if(XSGAPIGameServiceManager.getXSGAPIGameManager().IsConnectToAppUserDBDomain() == false)
                {
                	XSGAPIGameServiceManager.getXSGAPIGameManager().ConnectToAppUserDBDomain();
                    return;
                }
                else
                {
                    XSGAPIUser.registerLocalUserDelegate(this);
                    XSGAPIUser.localUser().LoadCurrentUserInformation();
                    XSGAPIGameServiceManager.getXSGAPIGameManager().IntializeLocalPlayerSetting(XSGAPIUser.localUser());
                }
            	
            }
        }   
    }
    
	public void ForceUpdatePlayersUI()
	{
		if(this.m_GameLobby != null)
		{
			this.m_GameLobby.ForceUpdatePlayersUI();
		}
	}

    
    public void DumpOnlineUserList()
    {
    	SimpleGambleWheel.m_ApplicationController.DumpOnlineUserList();
    }

    public void RejectCurrentInvitation()
    {
    	if(m_CachedInvitationSender != null)
    	{	
    		XSGAPIGameServiceManager.getXSGAPIGameManager().SendRejectInvitationTo(m_CachedInvitationSender.GetUserID());
    		m_CachedInvitationSender = null;
    	}	
    }
    
    public void AcceptCurrentInvitation()
    {
    	if(m_CachedInvitationSender != null)
    	{	
    		String szData = this.GetInvitationReceiverInitialData();
    		XSGAPIGameServiceManager.getXSGAPIGameManager().SendAcceptInvitationToPeer2(m_CachedInvitationSender, szData);
    		m_CachedInvitationSender = null;
    	}
    }
    
    public void SentInvitationAccepted()
    {
    	SimpleGambleWheel.m_ApplicationController.CloseOnlinePlayerListView();
    	SimpleGambleWheel.m_ApplicationController.ShowStatusBar(StringFactory.GetString_OnlineStarting());
    }
    
    public void HandleOnlinePeerConnectedPing()
    {
    	//???????
        if(m_ActiveGameGroup.GetOnlineGroup() == false)
        {
            //[self StartAWSOnlineGamePreset];
        	this.StartXSGAPIOnlineGamePreset();
        }
        //else
        if(m_ActiveGameGroup.GetOnlineGroup() == true)
        {
           ((GamePlayOnlineGroup)m_ActiveGameGroup).HandleXSGAPIPeerConnectedPing();
        }
    }

    //[self StartAWSOnlineGamePreset];
    public void StartXSGAPIOnlineGamePreset()
    {
		SimpleGambleWheel.m_ApplicationController.OnlineRequestDone();
        if(m_ActiveGameGroup.GetOnlineGroup() == false)
            this.ShutdownGameSection();
        m_ActiveGameGroup = m_OnlineGameGroup;
        ((GamePlayOnlineGroup)m_ActiveGameGroup).StartXSGAPIOnlineGamePreset();
    }
    
    public void ReceivedOnlineGamePlayMessage(String msg, String playerID)
    {
        if(m_ActiveGameGroup.GetOnlineGroup() == true)
        {
           ((GamePlayOnlineGroup)m_ActiveGameGroup).parserMessageInformation(msg, playerID);
        }
    }    
    
    public void SenderOnlinePlayerTextMessage(String msg)
    {
        if(m_ActiveGameGroup.GetOnlineGroup() == true)
        {
           ((GamePlayOnlineGroup)m_ActiveGameGroup).PostMyTextMessage(msg);
        }
    }
    
    public String GetOnlinePeerName()
    {
    	String string = new String("Online Peer");
    	
        if(m_ActiveGameGroup.GetOnlineGroup() == true)
        {
        	string = ((GamePlayOnlineGroup)m_ActiveGameGroup).GetOnlinePeerName();
        }
    	
    	return string;
    }

    public void OnlinePlayerTranfereChipsFrom(int nChips)
    {
        if(m_ActiveGameGroup.GetOnlineGroup() == true)
        {
        	((GamePlayOnlineGroup)m_ActiveGameGroup).OnlinePlayerTranfereChipsFrom(nChips);
        }
    }
////////////////////////////////////////////////////////////////////////////
//
//XSGAPIGameServiceManagerDelegate methods
//
/////////////////////////////////////////////////////////////////////////// 
    @Override
	public void XSGAPIGamePlayPeerConnected()
    {
		XSGAPIReleaseConfigure.LogDebugInfo("GameController.XSGAPIGamePlayPeerConnected()", "XSGAPIGamePlayPeerConnected");
		this.SentInvitationAccepted();
		SimpleGambleWheel.m_ApplicationController.OnlineRequestDone();
		this.HandleOnlinePeerConnectedPing();
    }
    
    @Override
	public void XSGAPIGamePlayPeerdisconnected()
    {
    	XSGAPIReleaseConfigure.LogDebugInfo("GameController.XSGAPIGamePlayPeerdisconnected()", "XSGAPIGamePlayPeerdisconnected");
        shutdownCurrentGame();
        SimpleGambleWheel.m_ApplicationController.OnlineRequestCancelled();
    }
    
    @Override
	public void XSGAPIHandleGamePlayPeerMessage(String pMsg, XSGAPIUser pUser)
	{
    	XSGAPIReleaseConfigure.LogDebugInfo("GameController.XSGAPIHandleGamePlayPeerMessage()", "XSGAPIHandleGamePlayPeerMessage");
        ReceivedOnlineGamePlayMessage(pMsg, pUser.GetUserID());
	}
	
	@Override
	public void XSGAPISendPlayMessageQueueConnected()
	{
    	XSGAPIReleaseConfigure.LogDebugInfo("GameController.XSGAPISendPlayMessageQueueConnected()", "XSGAPISendPlayMessageQueueConnected");
        //[pGameController StartAWSOnlineGamePreset];
    	this.StartXSGAPIOnlineGamePreset();
	}
	
	@Override
	public void XSGAPIRecievedNewInvitationFrom(XSGAPIUser sender)
	{
		if(sender != null)
		{
			m_CachedInvitationSender = sender;
			SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.AskForInviationFrom(sender.GetUserName());
		}
	}
	
	@Override
	public void XSGAPIInvitationCancelledBy(XSGAPIUser sender)
	{
		if(sender == null)
			return;
		XSGAPIReleaseConfigure.LogDebugInfo("GameController.XSGAPIInvitationCancelledBy()", sender.GetUserID());
		if(m_CachedInvitationSender != null && sender != null)
		{
			if(m_CachedInvitationSender.GetUserID().equals(sender.GetUserID()) == true)
			{
				m_CachedInvitationSender = null;
			}	
		}
		SimpleGambleWheel.m_ApplicationController.HideFullScreenCustomDialog();
    
		String szText = sender.GetUserName() + "" + StringFactory.GetString_InvitationCancelledStringFMT();
		SimpleGambleWheel.m_ApplicationController.ShowStatusBar(szText);
    
		this.shutdownCurrentGame();
		SimpleGambleWheel.m_ApplicationController.OnlineRequestCancelled();
		SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.ShowDefaultAlert(szText); 
	}
	
	@Override
	public void XSGAPISentInvitationAcceptedBy(XSGAPIUser sender)
	{
		XSGAPIReleaseConfigure.LogDebugInfo("GameController.XSGAPISentInvitationAcceptedBy()", "XSGAPISentInvitationAcceptedBy");
		this.SentInvitationAccepted();
		SimpleGambleWheel.m_ApplicationController.OnlineRequestDone();
	}
	
	@Override
	public void XSGAPISentInvitationRejectedBy(XSGAPIUser sender)
	{
		XSGAPIReleaseConfigure.LogDebugInfo("GameController.XSGAPISentInvitationRejectedBy()", "XSGAPISentInvitationRejectedBy");
		SimpleGambleWheel.m_ApplicationController.SentInvitationRejected();
	    this.shutdownCurrentGame();
	}
	
	@Override
	public void XSGAPISendingAcceptInvitionMessageDone()
	{
		XSGAPIReleaseConfigure.LogDebugInfo("GameController.XSGAPISendingAcceptInvitionMessageDone()", "XSGAPISendingAcceptInvitionMessageDone");
	}
	
	@Override
	public void XSGAPISendingSendInvitionMessageDone()
	{
		XSGAPIReleaseConfigure.LogDebugInfo("GameController.XSGAPISendingSendInvitionMessageDone()", "XSGAPISendingSendInvitionMessageDone");
	}
	
	@Override
	public void XSGAPISendingCancelInvitionMessageDone()
	{
		XSGAPIReleaseConfigure.LogDebugInfo("GameController.XSGAPISendingCancelInvitionMessageDone()", "XSGAPISendingCancelInvitionMessageDone");
	}
	
	@Override
	public void XSGAPISendingRejectInvitionMessageDone()
	{
		XSGAPIReleaseConfigure.LogDebugInfo("GameController.XSGAPISendingRejectInvitionMessageDone()", "XSGAPISendingRejectInvitionMessageDone");
        this.shutdownCurrentGame();
	}
	
	@Override
	public void XSGAPIConnectDBDomainDone() //For nofitfication of Connecting DB domain
	{
		XSGAPIReleaseConfigure.LogDebugInfo("GameController.XSGAPIConnectDBDomainDone()", "XSGAPIConnectDBDomainDone");
	    this.CheckAndUpdateOnlineSetting();
	}
	
	@Override
	public void XSGAPIConnectDBDomainFailed() //For nofitfication of Connecting DB domain
	{
		XSGAPIReleaseConfigure.LogDebugInfo("GameController.XSGAPIConnectDBDomainFailed()", "XSGAPIConnectDBDomainFailed");
		GameScore.SetOnlineServiceEnable(false);
	}

	@Override
	public void XSGAPIRegisterUserDone()
	{
		XSGAPIReleaseConfigure.LogDebugInfo("GameController.XSGAPIRegisterUserDone()", "XSGAPIRegisterUserDone");
	    XSGAPIGameServiceManager.getXSGAPIGameManager().InitializeGamePlayService();
	}
	
	@Override
	public void XSGAPIRegisterUserFailed()
	{
		XSGAPIReleaseConfigure.LogDebugInfo("GameController.XSGAPIRegisterUserFailed()", "XSGAPIRegisterUserFailed");
		GameScore.SetOnlineServiceEnable(false);
	}

	@Override
	public void XSGAPIUnRegisterUserDone()
	{
		XSGAPIReleaseConfigure.LogDebugInfo("GameController.XSGAPIUnRegisterUserDone()", "XSGAPIUnRegisterUserDone");
	}
	
	@Override
	public void XSGAPIUnRegisterUserFailed()
	{
		XSGAPIReleaseConfigure.LogDebugInfo("GameController.XSGAPIUnRegisterUserFailed()", "XSGAPIUnRegisterUserFailed");
	}
	
	@Override
	public void XSGAPIDisconnectDBDomainDone()
	{
		XSGAPIReleaseConfigure.LogDebugInfo("GameController.XSGAPIDisconnectDBDomainDone()", "XSGAPIDisconnectDBDomainDone");
	}
	
	@Override
	public void XSGAPIQueryUserCountOperationDone()
	{
		XSGAPIReleaseConfigure.LogDebugInfo("GameController.XSGAPIQueryUserCountOperationDone()", "XSGAPIQueryUserCountOperationDone");
	}
	
	@Override
	public void XSGAPIQueryBeginUserListOperationDone()
	{
		XSGAPIReleaseConfigure.LogDebugInfo("GameController.XSGAPIQueryBeginUserListOperationDone()", "XSGAPIQueryBeginUserListOperationDone");
	    if(XSGAPIGameServiceManager.getXSGAPIGameManager().StillHaveUsers() == true)
	    {
	    	XSGAPIGameServiceManager.getXSGAPIGameManager().ContiunePollOnlineUserList();
	    }
	    else
	    {
	        this.DumpOnlineUserList();
	    }
	}
	
	@Override
	public void XSGAPIQueryNextUserListOperationDone()
	{
		XSGAPIReleaseConfigure.LogDebugInfo("GameController.XSGAPIQueryNextUserListOperationDone()", "XSGAPIQueryNextUserListOperationDone()");
	    if(XSGAPIGameServiceManager.getXSGAPIGameManager().StillHaveUsers() == true)
	    {
	    	XSGAPIGameServiceManager.getXSGAPIGameManager().ContiunePollOnlineUserList();
	    }
	    else
	    {
	        this.DumpOnlineUserList();
	    }
	}
    
	@Override
	public String GetInvitationSenderInitialData()
	{
		String szData = null;
	    int nGameType = GetGameType();
	    int nPlayTurnType = Configuration.getPlayTurnType();
	    int nBalance = GetMyCurrentMoney();
	  
		JSONObject jObject = new JSONObject();
		
		try
		{
			jObject.put(GameMsgConstant.GAME_MSG_KEY_GAMETYPEMSG, nGameType);
			jObject.put(GameMsgConstant.GAME_MSG_KEY_GAMETHEMEMSG, Configuration.getCurrentThemeType());
			jObject.put(GameMsgConstant.GAME_MSG_KEY_ONLINEPLAYSEQUENCE, nPlayTurnType);
			jObject.put(GameMsgConstant.GAME_MSG_KEY_PLAYER_CHIPS_BALANCE, nBalance);
			szData = jObject.toString();
		}
        catch (JSONException e) 
        {
            Log.w("CGameController.GetInvitationSenderInitialData:", e.getMessage());
            szData = null;
        }
		
		return szData;
	}

	@Override
	public String GetInvitationReceiverInitialData()
	{
		String szData = null;
	    int nBalance = GetMyCurrentMoney();
		JSONObject jObject = new JSONObject();
		
		try
		{
			jObject.put(GameMsgConstant.GAME_MSG_KEY_PLAYER_CHIPS_BALANCE, nBalance);
			szData = jObject.toString();
		}
        catch (JSONException e) 
        {
            Log.w("CGameController.GetInvitationReceiverInitialData:", e.getMessage());
            szData = null;
        }
		
		return szData;
	}
	
////////////////////////////////////////////////////////////////////////////
//
//XSGAPIUserDelegate methods
//
/////////////////////////////////////////////////////////////////////////// 
	@Override
	public String GetCurrentUserName()
	{
		String strName = GameScore.GetOnlineNickName();
		
		return strName;
	}
	
	@Override
	public String GetCurrentUserID()
	{
		String strID = GameScore.GetOnlinePlayerID();
		
		return strID;
	}
	
	@Override
	public int GetCurrentUserType()
	{
		int nDeviceType = XSGAPIReleaseConfigure.GetCurrentDeviceType();
		
		return nDeviceType;
	}
	
	/////////////////////////////////////////////////////
	//
	//
	///XSGAPIScoreBoardDelegate methods
	//
	//
	/////////////////////////////////////////////////////
	public void ProcessHighScoreCount()
	{
		
	}
	
	public void ProcessPlayerScore(XSGAPIScoreBoardIntScore scoreRecord)
	{
		
	}
	
	public void ProcessPlayerList(List<XSGAPIScoreBoardIntScore> scoreList)
	{
		SimpleGambleWheel.m_ApplicationController.ProcessXSGAPIScoreList(scoreList);
	}
	
	public void ProcessPlayerList2(List<XSGAPIScoreBoardIntScore> scoreList, boolean bQueryNext)
	{
		SimpleGambleWheel.m_ApplicationController.ProcessXSGAPIScoreList2(scoreList, bQueryNext);
	}
	
	public void AddHighScoreDone()
	{
		SimpleGambleWheel.m_ApplicationController.ShowHighScoreSubmitted();
	}
	
	public void CreateDomainDone()
	{
		
	}
	
	public void DeleteHighScoreDone()
	{
		
	}
	
	//
    public void PostHighScoreToScoreBoard()
    {
    	if(GameScore.IsOnlineServiceEnable() == false || GameScore.HasOnlineNickName() == false)
    	{
        	SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.AskForOnlineGameOption();
    		return;
    	}
    	
    	if(this.m_GameScoreBoard != null)
    	{	
    		int nHighScore = ScoreRecord.GetMyMostWinChips();
			String myName = XSGAPIGameServiceManager.getXSGAPIGameManager().GetLocalUser().GetUserName();
			String myID = XSGAPIGameServiceManager.getXSGAPIGameManager().GetLocalUser().GetUserID();
    		if(0 <= nHighScore && myID != null && 0 < myID.length())
    		{	
    			XSGAPIScoreBoardIntScore score = new XSGAPIScoreBoardIntScore(myName, myID, nHighScore);
    			this.m_GameScoreBoard.addHighScore(score);
    		}
    	}
    }

    public void PostHighScoreToGameCircleLeaderBoard()
    {
    	if(GameScore.IsOnlineServiceEnable() == false || GameScore.HasOnlineNickName() == false)
    	{
        	SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.AskForOnlineGameOption();
    		return;
    	}
    	
    	if(this.m_GameScoreBoard != null)
    	{	
    		int nHighScore = ScoreRecord.GetMyMostWinChips();
    		if(0 <= nHighScore)
    		{	
    			SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.PostHighScoreToGameCircleLeaderBoard(nHighScore);
    		}
    	}
    }
    
    public void LoadXSGScoreBoard()
    {
    	if(this.m_GameScoreBoard != null)
    	{
    		this.m_GameScoreBoard.ClearCache();
    		this.m_GameScoreBoard.SetSortMethod(XSGAPIScoreBoard.XSGAPI_SB_SCORE_SORT);
    		this.m_GameScoreBoard.getHighScoresAsync();
    	}
    }

    public boolean LoadRemainScoreList()
    {
    	boolean bRet = false;
    	if(this.m_GameScoreBoard != null)
    	{
    		bRet = m_GameScoreBoard.getNextPageOfScores();
    	}
    	
    	return bRet;
    }
    
}
