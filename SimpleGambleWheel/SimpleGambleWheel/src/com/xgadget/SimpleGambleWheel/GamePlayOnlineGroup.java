package com.xgadget.SimpleGambleWheel;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.SystemClock;
//import android.util.Log;
import android.view.MotionEvent;

import com.xgadget.XSGService.XSGAPIGameServiceManager;
//import com.xgadget.XSGService.XSGAPIMessage;
import com.xgadget.XSGService.XSGAPIReleaseConfigure;

public class GamePlayOnlineGroup extends GamePlayGroupBase 
{
    private boolean m_bOnlineGameMasterCheck;
    private boolean m_bOnlineGameMasterSettingReceived;
    private boolean m_bOnlineGamePeerBalanceReceived;
    private boolean m_bHost;
	private long	m_TimeStartOnlineMasterCheck;
	public GamePlayOnlineGroup() 
	{
		// TODO Auto-generated constructor stub
		super();
	    m_bOnlineGameMasterCheck = false;
	    m_bOnlineGameMasterSettingReceived = false;
	    m_bOnlineGamePeerBalanceReceived = false;
	    m_bHost = false;
	    m_TimeStartOnlineMasterCheck = 0;
	}

	public boolean GetOnlineGroup()
	{
		return true;
	}
	
	public void StartNewGame()
	{
		
	}
	
	public boolean IsSystemOnHold()
	{
	    return false;
	}

	public void SetSystemOnHold(boolean bOnHold)
	{
	}
	
	public void StartGameSection()
	{
	    super.StartGameSection();
	}
	
	public void ShutdownSection()
	{
	    Configuration.setOnline(false);
	    int nState = GetGameState();
	    if(nState == GameConstants.GAME_STATE_READY || nState == GameConstants.GAME_STATE_RUN)
	        CancelPendPlayerBet();
	    
	    GamePlayer mySelf = this.GetMyself();
	    if(mySelf != null)
	    {
	        int nBalance = mySelf.GetPacketBalance();
	        ScoreRecord.SetMyChipBalance(nBalance);
	        ScoreRecord.SaveScore();
	    }
	    
	    this.ResetXSGAPIService();
	    m_bHost = false;
	    m_bOnlineGameMasterCheck = false;
	    XSGAPIGameServiceManager.getXSGAPIGameManager().SetAutoRejectInvitation(false);
	    super.ShutdownSection();
	}
	
	public void AbsoluteShutdownOnlineGame()
	{
	    Configuration.setOnline(false);
	    int nState = GetGameState();
	    if(nState == GameConstants.GAME_STATE_READY || nState == GameConstants.GAME_STATE_RUN)
	        CancelPendPlayerBet();
	    
	    GamePlayer mySelf = this.GetMyself();
	    if(mySelf != null)
	    {
	        int nBalance = mySelf.GetPacketBalance();
	        ScoreRecord.SetMyChipBalance(nBalance);
	        ScoreRecord.SaveScore();
	    }
	    
	    this.AbsoultShutdownXSGAPIService();
	    m_bHost = false;
	    m_bOnlineGameMasterCheck = false;
	    super.ShutdownSection();
	}
	public void PauseGame()
	{
	    this.CancelPendPlayerBet();
	    SimpleGambleWheel.m_ApplicationController.SetGameState(GameConstants.GAME_STATE_RESET);
	    int nType = SimpleGambleWheel.m_ApplicationController.m_GameController.GetGameType();
	    ScoreRecord.SetGameType(nType);
	    ScoreRecord.SetThemeType(Configuration.getCurrentThemeType());
	    ScoreRecord.SetPlayTurnType(Configuration.getPlayTurnType());
	    
	    GamePlayer pMyself = this.GetMyself();
	    
	    int nChips ;
	    if(pMyself != null)
	    {
	        nChips = pMyself.GetPacketBalance();
	        ScoreRecord.SetMyChipBalance(nChips);
	    }
	    ScoreRecord.SaveScore();
	    SimpleGambleWheel.m_ApplicationController.m_GameController.GotoOfflineGame();
	}
	
	public void CancelPendPlayerBet()
	{
	    if(m_GameLobby == null)
	        return;
        
	    if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsEnabled() == true)
	    {	
	    	m_GameLobby.m_Player0.CancelPendingBet();
	    	m_GameLobby.m_Player0.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RESET);
	    }
	    if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsEnabled() == true)
	    {	
	    	m_GameLobby.m_Player1.CancelPendingBet();
	    	m_GameLobby.m_Player1.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RESET);
	    }
	    
	    if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsEnabled() == true)
	    {	
	    	m_GameLobby.m_Player2.CancelPendingBet();
	    	m_GameLobby.m_Player2.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RESET);
	    }

	    if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsEnabled() == true)
	    {	
	    	m_GameLobby.m_Player3.CancelPendingBet();
	    	m_GameLobby.m_Player3.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RESET);
	    }

	    SimpleGambleWheel.m_ApplicationController.SetGameState(GameConstants.GAME_STATE_RESET);
	    this.PostMyOnlineGameBalance();
	    this.PostMyOnlineGameState();
	}

	public int GetMyCurrentMoney()
	{
	    int nRet = -1;
	    GamePlayer myself = this.GetMyself();
	    if(myself != null && myself.IsActivated() == true)
	        nRet = myself.GetPacketBalance();
	    return nRet;
	}

	public void AddMoneyToMyAccount(int nChips)
	{
	    GamePlayer myself = this.GetMyself();
	    if(myself != null && myself.IsActivated() == true)
	    {    
	        myself.AddMoneyToPacket(nChips);
	        int nMoney = myself.GetPacketBalance();
	        ScoreRecord.SetMyChipBalance(nMoney);
	        ScoreRecord.SaveScore();
	        if(myself.IsEnabled() == false)
	            myself.UpdateCurrentGamePlayablity();
	        this.PostMyOnlineGameBalance();
	    }    
	}

	public int GetPlayerCurrentMoney(int nSeat)
	{
	    int nRet = -1;

	    GamePlayer player = this.GetPlayer(nSeat);
	    if(player != null && player.IsActivated() == true)
	        nRet = player.GetPacketBalance();
	    
	    return nRet;
	}

	public void AddMoneyToPlayerAccount(int nChips, int nSeat)
	{
	    GamePlayer player = this.GetPlayer(nSeat);
	    if(player != null && player.IsActivated() == true)
	    {
	        player.AddMoneyToPacket(nChips);
	        if(player.IsEnabled() == false)
	            player.UpdateCurrentGamePlayablity();
	    }
	}

	public void OnTimerEvent()
	{
		//if(m_GameLobby != null)
		//{	
		//	m_GameLobby.OnTimerEvent();
		//}		
	    if(m_bOnlineGameMasterCheck == true)
	    {
	        long currentTime = CApplicationController.GetSystemTimerClick();
	        if(GameConstants.ONLINE_GAME_NONGKMASTER_CHECK_TIMELINE <= (currentTime - m_TimeStartOnlineMasterCheck))
	        {
	        	m_TimeStartOnlineMasterCheck = currentTime;
	            if( XSGAPIGameServiceManager.getXSGAPIGameManager().IsGameHost() == false && m_bOnlineGameMasterSettingReceived == false)
	            {
	                this.XSGAPINonMasterPlayerSendMasterSettingRequest();
	            }
	        }
	    }
	}
	
	public void UpdateForGameStateChange()
	{
	    int nGameState =  SimpleGambleWheel.m_ApplicationController.m_GameController.GetGameState();
	    int nMySeatID = this.GetMySeatID();
	    if(nGameState == GameConstants.GAME_STATE_RUN)
	    {
	    	if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsEnabled() == true)
	    	{
	    		m_GameLobby.m_Player0.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RUN);
                if(m_GameLobby.m_Player0.GetSeatID() == nMySeatID)
                {
                    this.PostMyOnlineGameState();
                }
	    	}
	    	if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsEnabled() == true)
	    	{
	    		m_GameLobby.m_Player1.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RUN);
                if(m_GameLobby.m_Player1.GetSeatID() == nMySeatID)
                {
                    this.PostMyOnlineGameState();
                }
	    	}
	    	if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsEnabled() == true)
	    	{
	    		m_GameLobby.m_Player2.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RUN);
                if(m_GameLobby.m_Player2.GetSeatID() == nMySeatID)
                {
                    this.PostMyOnlineGameState();
                }
	    	}
	    	if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsEnabled() == true)
	    	{
	    		m_GameLobby.m_Player3.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RUN);
                if(m_GameLobby.m_Player3.GetSeatID() == nMySeatID)
                {
                    this.PostMyOnlineGameState();
                }
	    	}
	    }
	    else if(nGameState == GameConstants.GAME_STATE_RESULT)
	    {
	        int nWinNumber = m_GameLobby.GetWinScopeIndex()+1;
	        int nTotalPledge = 0;
	        int nWinPlayers = 0;
	        int nPledge = 0;
            if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsEnabled() == true)
            {
                nPledge = m_GameLobby.m_Player0.GetPlayBet();
                if(0 < nPledge)
                {    
                    nTotalPledge += m_GameLobby.m_Player0.GetPlayBet();
                    if(m_GameLobby.m_Player0.GetPlayBetLuckNumber() == nWinNumber)
                    {    
                        ++nWinPlayers; 
                    }
                }    
            }        	
            if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsEnabled() == true)
            {
                nPledge = m_GameLobby.m_Player1.GetPlayBet();
                if(0 < nPledge)
                {    
                    nTotalPledge += m_GameLobby.m_Player1.GetPlayBet();
                    if(m_GameLobby.m_Player1.GetPlayBetLuckNumber() == nWinNumber)
                    {    
                        ++nWinPlayers; 
                    }
                }    
            }        	
            if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsEnabled() == true)
            {
                nPledge = m_GameLobby.m_Player2.GetPlayBet();
                if(0 < nPledge)
                {    
                    nTotalPledge += m_GameLobby.m_Player2.GetPlayBet();
                    if(m_GameLobby.m_Player2.GetPlayBetLuckNumber() == nWinNumber)
                    {    
                        ++nWinPlayers; 
                    }
                }    
            }        	
            if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsEnabled() == true)
            {
                nPledge = m_GameLobby.m_Player3.GetPlayBet();
                if(0 < nPledge)
                {    
                    nTotalPledge += m_GameLobby.m_Player3.GetPlayBet();
                    if(m_GameLobby.m_Player3.GetPlayBetLuckNumber() == nWinNumber)
                    {    
                        ++nWinPlayers; 
                    }
                }    
            } 
            
            //Restore winners' pledge
            if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsEnabled() == true)
            {
            	if(0 <= nTotalPledge && m_GameLobby.m_Player0.GetPlayBetLuckNumber() == nWinNumber)
                {    
            		nPledge = m_GameLobby.m_Player0.GetPlayBet();
                    m_GameLobby.m_Player0.AddMoneyToPacket(nPledge);
                    nTotalPledge = nTotalPledge - nPledge;
                }    
            }    
            if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsEnabled() == true)
            {
            	if(0 <= nTotalPledge && m_GameLobby.m_Player1.GetPlayBetLuckNumber() == nWinNumber)
                {    
                    nPledge = m_GameLobby.m_Player1.GetPlayBet();
                    m_GameLobby.m_Player1.AddMoneyToPacket(nPledge);
                    nTotalPledge = nTotalPledge - nPledge;
                }    
            }    
            if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsEnabled() == true)
            {
            	if(0 <= nTotalPledge && m_GameLobby.m_Player2.GetPlayBetLuckNumber() == nWinNumber)
                {    
                    nPledge = m_GameLobby.m_Player2.GetPlayBet();
                    m_GameLobby.m_Player2.AddMoneyToPacket(nPledge);
                    nTotalPledge = nTotalPledge - nPledge;
                }    
            }    
            if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsEnabled() == true)
            {
            	if(0 <= nTotalPledge && m_GameLobby.m_Player3.GetPlayBetLuckNumber() == nWinNumber)
                {    
                    nPledge = m_GameLobby.m_Player3.GetPlayBet();
                    m_GameLobby.m_Player3.AddMoneyToPacket(nPledge);
                    nTotalPledge = nTotalPledge - nPledge;
                }    
            }    
	        
	        //Set winners' earning chips
	        if(nTotalPledge < 0)
	            nTotalPledge = 0; 
	        
	        int nWinChips = -1;
	        if(0 < nWinPlayers)
	        {
	            nWinChips = (int)(((float)nTotalPledge)/((float)nWinPlayers));
	        }
	        
	        String myStatus = StringFactory.GetString_PlayingDone();
	        String otherStatus = "";
	         
            if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsEnabled() == true)
            {
            	if(0 <= nWinChips && m_GameLobby.m_Player0.GetPlayBetLuckNumber() == nWinNumber)
                {    
            		m_GameLobby.m_Player0.AddMoneyToPacket(nWinChips);
            		m_GameLobby.m_Player0.SetPlayResult(true); 
                    if(nMySeatID == m_GameLobby.m_Player0.GetSeatID())
                    {    
                        ScoreRecord.SetMyLastPlayResult(nWinChips);
                        myStatus = StringFactory.GetString_YouWin();
                    }    
                }    
                else
                {
                	 m_GameLobby.m_Player0.SetPlayResult(false);
                     int nBet = m_GameLobby.m_Player0.GetPlayBet()*(-1);
                     if(nMySeatID == m_GameLobby.m_Player0.GetSeatID())
	                 {    
	                     ScoreRecord.SetMyLastPlayResult(nBet);
	                     myStatus = StringFactory.GetString_YouLose();
	                 }    
                }
                if(nMySeatID == m_GameLobby.m_Player0.GetSeatID())
                {    
                    int nChip = m_GameLobby.m_Player0.GetPacketBalance();
                    ScoreRecord.SetPlayerChipBalance(nChip, nMySeatID);
                    this.PostMyOnlineGameBalance();
                    m_GameLobby.m_Player0.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RESULT);
                    this.PostMyOnlineGameState();
                }
                else 
                {
                    if( m_GameLobby.m_Player0.GetOnlinePlayingState() == GameConstants.GAME_ONLINE_PLAYER_STATE_RUN)
                        otherStatus = StringFactory.GetString_OtherStillPlaying();
                }
                m_GameLobby.m_Player0.GameStateChange();
            }
            
            if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsEnabled() == true)
            {
            	if(0 <= nWinChips && m_GameLobby.m_Player1.GetPlayBetLuckNumber() == nWinNumber)
                {    
            		m_GameLobby.m_Player1.AddMoneyToPacket(nWinChips);
            		m_GameLobby.m_Player1.SetPlayResult(true); 
                    if(nMySeatID == m_GameLobby.m_Player1.GetSeatID())
                    {    
                        ScoreRecord.SetMyLastPlayResult(nWinChips);
                        myStatus = StringFactory.GetString_YouWin();
                    }    
                }    
                else
                {
                	m_GameLobby.m_Player1.SetPlayResult(false);
                     int nBet = m_GameLobby.m_Player1.GetPlayBet()*(-1);
                     if(nMySeatID == m_GameLobby.m_Player1.GetSeatID())
	                 {    
	                     ScoreRecord.SetMyLastPlayResult(nBet);
	                     myStatus = StringFactory.GetString_YouLose();
	                 }    
                }
                if(nMySeatID == m_GameLobby.m_Player1.GetSeatID())
                {    
                    int nChip = m_GameLobby.m_Player1.GetPacketBalance();
                    ScoreRecord.SetPlayerChipBalance(nChip, nMySeatID);
                    this.PostMyOnlineGameBalance();
                    m_GameLobby.m_Player1.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RESULT);
                    this.PostMyOnlineGameState();
                }
                else 
                {
                    if( m_GameLobby.m_Player1.GetOnlinePlayingState() == GameConstants.GAME_ONLINE_PLAYER_STATE_RUN)
                        otherStatus = StringFactory.GetString_OtherStillPlaying();
                }
                m_GameLobby.m_Player1.GameStateChange();
            }
           
            if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsEnabled() == true)
            {
            	if(0 <= nWinChips && m_GameLobby.m_Player2.GetPlayBetLuckNumber() == nWinNumber)
                {    
            		m_GameLobby.m_Player2.AddMoneyToPacket(nWinChips);
            		m_GameLobby.m_Player2.SetPlayResult(true); 
                    if(nMySeatID == m_GameLobby.m_Player2.GetSeatID())
                    {    
                        ScoreRecord.SetMyLastPlayResult(nWinChips);
                        myStatus = StringFactory.GetString_YouWin();
                    }    
                }    
                else
                {
                	m_GameLobby.m_Player2.SetPlayResult(false);
                     int nBet = m_GameLobby.m_Player2.GetPlayBet()*(-1);
                     if(nMySeatID == m_GameLobby.m_Player2.GetSeatID())
	                 {    
	                     ScoreRecord.SetMyLastPlayResult(nBet);
	                     myStatus = StringFactory.GetString_YouLose();
	                 }    
                }
                if(nMySeatID == m_GameLobby.m_Player2.GetSeatID())
                {    
                    int nChip = m_GameLobby.m_Player2.GetPacketBalance();
                    ScoreRecord.SetPlayerChipBalance(nChip, nMySeatID);
                    this.PostMyOnlineGameBalance();
                    m_GameLobby.m_Player2.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RESULT);
                    this.PostMyOnlineGameState();
                }
                else 
                {
                    if( m_GameLobby.m_Player2.GetOnlinePlayingState() == GameConstants.GAME_ONLINE_PLAYER_STATE_RUN)
                        otherStatus = StringFactory.GetString_OtherStillPlaying();
                }
                m_GameLobby.m_Player2.GameStateChange();
            }

            if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsEnabled() == true)
            {
            	if(0 <= nWinChips && m_GameLobby.m_Player3.GetPlayBetLuckNumber() == nWinNumber)
                {    
            		m_GameLobby.m_Player3.AddMoneyToPacket(nWinChips);
            		m_GameLobby.m_Player3.SetPlayResult(true);
                    if(nMySeatID == m_GameLobby.m_Player3.GetSeatID())
                    {    
                        ScoreRecord.SetMyLastPlayResult(nWinChips);
                        myStatus = StringFactory.GetString_YouWin();
                    }    
                }    
                else
                {
                	m_GameLobby.m_Player3.SetPlayResult(false); 
                     int nBet = m_GameLobby.m_Player3.GetPlayBet()*(-1);
                     if(nMySeatID == m_GameLobby.m_Player3.GetSeatID())
	                 {    
	                     ScoreRecord.SetMyLastPlayResult(nBet);
	                     myStatus = StringFactory.GetString_YouLose();
	                 }    
                }
                if(nMySeatID == m_GameLobby.m_Player3.GetSeatID())
                {    
                    int nChip = m_GameLobby.m_Player3.GetPacketBalance();
                    ScoreRecord.SetPlayerChipBalance(nChip, nMySeatID);
                    this.PostMyOnlineGameBalance();
                    m_GameLobby.m_Player3.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RESULT);
                    this.PostMyOnlineGameState();
                }
                else 
                {
                    if( m_GameLobby.m_Player3.GetOnlinePlayingState() == GameConstants.GAME_ONLINE_PLAYER_STATE_RUN)
                        otherStatus = StringFactory.GetString_OtherStillPlaying();
                }
                m_GameLobby.m_Player3.GameStateChange();
            }
	        
	        ScoreRecord.SaveScore();
	        String szText = myStatus + " " + otherStatus;
	        SimpleGambleWheel.m_ApplicationController.ShowStatusBar(szText);
	        return;
	    }
	    else if(nGameState == GameConstants.GAME_STATE_RESET)
	    {
            if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsEnabled() == true)
            {
                if(m_GameLobby.m_Player0.GetOnlinePlayingState() == GameConstants.GAME_ONLINE_PLAYER_STATE_RESET)
                {
                	m_GameLobby.m_Player0.GameStateChange();
                	m_GameLobby.m_Player0.UpdateOnlinePlayingStateByMoneyBalance();
                }
            }	    	
            if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsEnabled() == true)
            {
                if(m_GameLobby.m_Player1.GetOnlinePlayingState() == GameConstants.GAME_ONLINE_PLAYER_STATE_RESET)
                {
                	m_GameLobby.m_Player1.GameStateChange();
                	m_GameLobby.m_Player1.UpdateOnlinePlayingStateByMoneyBalance();
                }
            }	    	
            if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsEnabled() == true)
            {
                if(m_GameLobby.m_Player2.GetOnlinePlayingState() == GameConstants.GAME_ONLINE_PLAYER_STATE_RESET)
                {
                	m_GameLobby.m_Player2.GameStateChange();
                	m_GameLobby.m_Player2.UpdateOnlinePlayingStateByMoneyBalance();
                }
            }	    	
            if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsEnabled() == true)
            {
                if(m_GameLobby.m_Player3.GetOnlinePlayingState() == GameConstants.GAME_ONLINE_PLAYER_STATE_RESET)
                {
                	m_GameLobby.m_Player3.GameStateChange();
                	m_GameLobby.m_Player3.UpdateOnlinePlayingStateByMoneyBalance();
                }
            }	    	
	    	
	        if(Configuration.isPlayTurnBySequence() == true)
	        {
	            SimpleGambleWheel.m_ApplicationController.ShowStatusBar(StringFactory.GetString_PlayTurnIsSequence());
	        }
	        else 
	        {
	        	SimpleGambleWheel.m_ApplicationController.ShowStatusBar(StringFactory.GetString_PlayTurnIsMaxBet());
	        }
	    }    
	}

	private boolean IsOnlineGameStateReadyToPlay()
	{
		if(this.m_GameLobby == null)
			return false;
	    
	    if( this.m_GameLobby.GetGameState() != GameConstants.GAME_STATE_READY)
	        return false;

	    boolean bHaveActiveOne = false;

	    if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsEnabled() == true)
	    {	
            bHaveActiveOne = true;
            if(m_GameLobby.m_Player0.GetOnlinePlayingState() != GameConstants.GAME_ONLINE_PLAYER_STATE_READY)
                return false;
	    }
	    if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsEnabled() == true)
	    {	
            bHaveActiveOne = true;
            if(m_GameLobby.m_Player1.GetOnlinePlayingState() != GameConstants.GAME_ONLINE_PLAYER_STATE_READY)
                return false;
	    }
	    
	    if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsEnabled() == true)
	    {	
            bHaveActiveOne = true;
            if(m_GameLobby.m_Player2.GetOnlinePlayingState() != GameConstants.GAME_ONLINE_PLAYER_STATE_READY)
                return false;
	    }

	    if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsEnabled() == true)
	    {	
            bHaveActiveOne = true;
            if(m_GameLobby.m_Player3.GetOnlinePlayingState() != GameConstants.GAME_ONLINE_PLAYER_STATE_READY)
                return false;
	    }
	    
	    if(bHaveActiveOne == false)
	        return  false;
	    
	    return true;
	}
	
	private boolean IsMyTurn()
	{
		if(this.m_GameLobby == null)
			return false;
	
	    int nMySeatID = this.GetMySeatID();
	    if(nMySeatID == this.m_GameLobby.GetCurrentActivePlayingSeat())
	        return true; 
		
		return false;
	}
	
	
	private void OnTouchBegin(float x, float y)
	{
	    if(IsSystemOnHold() == true || m_bOnlineGameMasterCheck == true)
	        return;
	    
	    if(this.IsOnlineGameStateReadyToPlay() == false)
	        return;
	    
	    if(this.IsMyTurn())
	    {
	    	m_GameLobby.m_ptTouchStartX = x;
	    	m_GameLobby.m_ptTouchStartY = y;
	    	m_GameLobby.m_ptTouchEndX = x;
	    	m_GameLobby.m_ptTouchEndY = y;
	    	m_GameLobby.m_timeTouchStart = SystemClock.currentThreadTimeMillis();
	        return;
	    }
	}
	
	private void OnTouchMove(float x, float y)
	{
	    if(IsSystemOnHold() == true || m_bOnlineGameMasterCheck == true)
	        return;
	    
	    if(this.IsOnlineGameStateReadyToPlay() == false)
	        return;
		
	}
	
	private void OnTouchEnd(float x, float y)
	{
	    if(IsSystemOnHold() == true || m_bOnlineGameMasterCheck == true)
	        return;
	    
	    if(this.IsOnlineGameStateReadyToPlay() == false)
	    {	
	    	HandleNonSpinTouchEvent();
	        return;
	    }
	   
	    if(this.IsMyTurn())
	    {
	    	m_GameLobby.m_ptTouchEndX = x;
	    	m_GameLobby.m_ptTouchEndY = y;
		    long nEndTime = SystemClock.currentThreadTimeMillis();
	        float time = ((float)(nEndTime - m_GameLobby.m_timeTouchStart))/1000.0f;
	        CPinActionLevel action = new CPinActionLevel();
	        action.CreateLevel(m_GameLobby.m_ptTouchStartX, m_GameLobby.m_ptTouchStartY, m_GameLobby.m_ptTouchEndX, m_GameLobby.m_ptTouchEndY, time);
	        this.PostSpinGambleWheelMessage(action);
	        
	        SimpleGambleWheel.m_ApplicationController.SpinGambleWheel(action);
	        
	        
		    if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsEnabled() == true)
		    {	
	            m_GameLobby.m_Player0.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RUN);
                if(m_GameLobby.m_Player0.IsMySelf() == true)
                    this.PostMyOnlineGameState();

		    }
		    
		    if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsEnabled() == true)
		    {	
	            m_GameLobby.m_Player1.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RUN);
                if(m_GameLobby.m_Player1.IsMySelf() == true)
                    this.PostMyOnlineGameState();
		    }
		    
		    if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsEnabled() == true)
		    {	
	            m_GameLobby.m_Player2.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RUN);
                if(m_GameLobby.m_Player2.IsMySelf() == true)
                    this.PostMyOnlineGameState();
		    }

		    if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsEnabled() == true)
		    {	
	            m_GameLobby.m_Player3.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RUN);
                if(m_GameLobby.m_Player3.IsMySelf() == true)
                    this.PostMyOnlineGameState();
		    }
	    }    
	    
	}
	
	private void HandleSpinPointerEvent(MotionEvent event)
	{
		float x = event.getX();
		float y = event.getY();
    
		switch (event.getAction()) 
		{
        	case MotionEvent.ACTION_DOWN:
        		OnTouchBegin(x, y);
        		break;
        	case MotionEvent.ACTION_MOVE:
        		OnTouchMove(x, y);
        		break;
        	case MotionEvent.ACTION_UP:
        	case MotionEvent.ACTION_CANCEL:
        		OnTouchEnd(x, y);
        		break;
		}	
	}
	
	public boolean HandleTouchEvent(MotionEvent event)
	{
		boolean bRet = true;
		
		HandleSpinPointerEvent(event);
	
		return bRet;
	}	
	
	public boolean CanOnlinePlayerPledgeInResultRun()
	{
        if(m_GameLobby.m_Player0 != null && 
        m_GameLobby.m_Player0.IsActivated() == true && 
        m_GameLobby.m_Player0.IsMySelf() == false && 
        (m_GameLobby.m_Player0.GetOnlinePlayingState() == GameConstants.GAME_ONLINE_PLAYER_STATE_RESULT || 
        m_GameLobby.m_Player0.GetOnlinePlayingState() == GameConstants.GAME_ONLINE_PLAYER_STATE_RUN))
        {
        	return false;
        }

        if(m_GameLobby.m_Player1 != null && 
        m_GameLobby.m_Player1.IsActivated() == true && 
        m_GameLobby.m_Player1.IsMySelf() == false && 
        (m_GameLobby.m_Player1.GetOnlinePlayingState() == GameConstants.GAME_ONLINE_PLAYER_STATE_RESULT || 
        m_GameLobby.m_Player1.GetOnlinePlayingState() == GameConstants.GAME_ONLINE_PLAYER_STATE_RUN))
        {
        	return false;
        }
        
        if(m_GameLobby.m_Player2 != null && 
        m_GameLobby.m_Player2.IsActivated() == true && 
        m_GameLobby.m_Player2.IsMySelf() == false && 
        (m_GameLobby.m_Player2.GetOnlinePlayingState() == GameConstants.GAME_ONLINE_PLAYER_STATE_RESULT || 
        m_GameLobby.m_Player2.GetOnlinePlayingState() == GameConstants.GAME_ONLINE_PLAYER_STATE_RUN))
        {
        	return false;
        }
        
        if(m_GameLobby.m_Player3 != null && 
        m_GameLobby.m_Player3.IsActivated() == true && 
        m_GameLobby.m_Player3.IsMySelf() == false && 
        (m_GameLobby.m_Player3.GetOnlinePlayingState() == GameConstants.GAME_ONLINE_PLAYER_STATE_RESULT || 
        m_GameLobby.m_Player3.GetOnlinePlayingState() == GameConstants.GAME_ONLINE_PLAYER_STATE_RUN))
        {
        	return false;
        }
        
		return true;
	}
	
	public boolean CanOnlinePlayerPledgeInRun()
	{
        if(m_GameLobby.m_Player0 != null && 
        m_GameLobby.m_Player0.IsEnabled() == true &&		
        m_GameLobby.m_Player0.IsMySelf() == false && 
        m_GameLobby.m_Player0.GetOnlinePlayingState() == GameConstants.GAME_ONLINE_PLAYER_STATE_RUN)
        {
        	return false;
        }
        
        if(m_GameLobby.m_Player1 != null && 
        m_GameLobby.m_Player1.IsEnabled() == true &&		
        m_GameLobby.m_Player1.IsMySelf() == false && 
        m_GameLobby.m_Player1.GetOnlinePlayingState() == GameConstants.GAME_ONLINE_PLAYER_STATE_RUN)
        {
        	return false;
        }
     
        if(m_GameLobby.m_Player2 != null && 
        m_GameLobby.m_Player2.IsEnabled() == true &&		
        m_GameLobby.m_Player2.IsMySelf() == false && 
        m_GameLobby.m_Player2.GetOnlinePlayingState() == GameConstants.GAME_ONLINE_PLAYER_STATE_RUN)
        {
        	return false;
        }
        
        if(m_GameLobby.m_Player3 != null && 
        m_GameLobby.m_Player3.IsEnabled() == true &&		
        m_GameLobby.m_Player3.IsMySelf() == false && 
        m_GameLobby.m_Player3.GetOnlinePlayingState() == GameConstants.GAME_ONLINE_PLAYER_STATE_RUN)
        {
        	return false;
        }
        
		return true;
	}	
	public void HandleNonSpinTouchEvent()
	{
		if(this.m_GameLobby == null)
			return;
	    
        GamePlayer pMyself = this.GetMyself();
		if(this.m_GameLobby.GetGameState() == GameConstants.GAME_STATE_RESET)
	    {
	        if(pMyself != null && pMyself.IsEnabled() == true)
	        {
	            boolean bCanPledge = this.CanOnlinePlayerPledgeInResultRun();
	            
	            if(pMyself.AlreadyMadePledge() == false && bCanPledge == true)
	            {
	            	SimpleGambleWheel.m_ApplicationController.MakePlayerManualPledge(pMyself.GetSeatID());
	                return;
	            }
	        }    
	    }
	    else if(this.m_GameLobby.GetGameState() == GameConstants.GAME_STATE_RESULT)
	    {
	        boolean bCanClick = this.CanOnlinePlayerPledgeInRun();
	        
	        if(pMyself != null && pMyself.IsEnabled() == true && bCanClick == true)
	        {
	        	pMyself.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RESET);
	        	pMyself.UpdateOnlinePlayingStateByMoneyBalance();
	            this.PostMyOnlineGameState();
	            SimpleGambleWheel.m_ApplicationController.SetGameState(GameConstants.GAME_STATE_RESET);
	            return;         
	        }
	    }

	    if(pMyself != null && pMyself.IsActivated() == true && pMyself.IsEnabled() == false)
	    {
	        SetSystemOnHold(true);
	        //[ApplicationConfigure SetRedeemPlayerSeat:[pMyself GetSeatID]];
	        //[GUIEventLoop SendEvent:GUIID_EVENT_PURCHASECHIPS eventSender:self];
	        //???????????????????
	    	//Purchase
	    	//??????????????????
	    	return;
	    }    
		
	}
	
	//???? HandleAWSPeerConnectedPing
	public void HandleXSGAPIPeerConnectedPing()
	{
	    if(m_bOnlineGameMasterCheck == true)
	    {
	        if(XSGAPIGameServiceManager.getXSGAPIGameManager().IsGameHost() == false && m_bOnlineGameMasterSettingReceived == false)
	        {
	            this.XSGAPINonMasterPlayerSendMasterSettingRequest();
	        }
	        if(m_bOnlineGamePeerBalanceReceived == false)
	        {
	        	this.XSGAPIPlayerSendPeerBalanceRequest();
	        }
	    }
	    else
	    {
	        if(m_bOnlineGamePeerBalanceReceived == false)
	        {
	        	this.XSGAPIPlayerSendPeerBalanceRequest();
	        }
	    }
	}
	
	private void handleTextMessage(String text, String playerID)
	{
    	XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handleTextMessage", text);
	    
	    GamePlayer pPLayer = this.GetPlayerByPlayerID(playerID);
	    if(pPLayer != null && pPLayer.IsActivated() == true)
	    {
	        pPLayer.ShowOnlineMessage(text);
	    }
	}
	
	private GamePlayer GetActivatedPLayerByPlayerID(String msgMasterID)
	{
		GamePlayer player = null;
		if(msgMasterID == null || msgMasterID.length() <= 0)
			return player;

        if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsActivated() == true && msgMasterID.equals(m_GameLobby.m_Player0.GetPlayerID()) == true)
        	return m_GameLobby.m_Player0;

        if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsActivated() == true && msgMasterID.equals(m_GameLobby.m_Player1.GetPlayerID()) == true)
        	return m_GameLobby.m_Player1;

        if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsActivated() == true && msgMasterID.equals(m_GameLobby.m_Player2.GetPlayerID()) == true)
        	return m_GameLobby.m_Player2;
        
        if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsActivated() == true && msgMasterID.equals(m_GameLobby.m_Player3.GetPlayerID()) == true)
        	return m_GameLobby.m_Player3;
        
		return player;
	}
	
	private void handlePlayersOrderMessage(JSONObject msgData)
	{
		if(msgData == null)
		{	
			XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handlePlayersOrderMessage is called.", "empty json object");
			return;
		}
		XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handlePlayersOrderMessage is called.", "handlePlayersOrderMessage");
	
		try
	    {
			
	        boolean bNeedPostMyInfo = false;
	        if(m_bOnlineGameMasterCheck == true)
	            bNeedPostMyInfo = true;
	            
	        this.StopOnlineGameMasterCheck();
	        int nLastSeatID = 0;
	        String msgMasterID = msgData.getString(GameMsgConstant.GAME_MSG_KEY_MASTER_ID);
	        if(msgMasterID != null && 0 < msgMasterID.length())
	        {
	        	GamePlayer player = GetActivatedPLayerByPlayerID(msgMasterID);
	        	if(player != null)
	        	{
	        		player.SetSeatID(0);
	        		player.SetOnlineGameMaster(true);
	        	}
	        }
	        else
	        {
	            this.DisableNonAcitvatedSeat(nLastSeatID);
	            if(bNeedPostMyInfo == true)
	                this.DelayPostMyInfo();
	            return;
	        }
	        nLastSeatID = 1;
	        String msgPlayer1ID = msgData.getString(GameMsgConstant.GAME_MSG_KEY_PLAYERONE_ID);
	        if(msgPlayer1ID != null && 0 < msgPlayer1ID.length())
	        {
	        	GamePlayer player = GetActivatedPLayerByPlayerID(msgPlayer1ID);
	        	if(player != null)
	        	{
	        		player.SetSeatID(1);
	        		player.SetOnlineGameMaster(false);
	        	}
	        }
	        else
	        {
	            this.DisableNonAcitvatedSeat(nLastSeatID);
	            if(bNeedPostMyInfo == true)
	                this.DelayPostMyInfo();
	            return;
	        }
	        
	        nLastSeatID = 2;
	        String msgPlayer2ID = msgData.getString(GameMsgConstant.GAME_MSG_KEY_PLAYERTWO_ID);
	        if(msgPlayer2ID != null && 0 < msgPlayer2ID.length())
	        {
	        	GamePlayer player = GetActivatedPLayerByPlayerID(msgPlayer2ID);
	        	if(player != null)
	        	{
	        		player.SetSeatID(2);
	        		player.SetOnlineGameMaster(false);
	        	}
	        }
	        else
	        {
	        	this.DisableNonAcitvatedSeat(nLastSeatID);
	            if(bNeedPostMyInfo == true)
	                this.DelayPostMyInfo();
	            return;
	        }
	        nLastSeatID = 3;
	        String msgPlayer3ID = msgData.getString(GameMsgConstant.GAME_MSG_KEY_PLAYERTHREE_ID);
	        if(msgPlayer3ID != null && 0 < msgPlayer3ID.length())
	        {
	        	GamePlayer player = GetActivatedPLayerByPlayerID(msgPlayer3ID);
	        	if(player != null)
	        	{
	        		player.SetSeatID(3);
	        		player.SetOnlineGameMaster(false);
	        	}
	        }
	        else
	        {
	        	this.DisableNonAcitvatedSeat(nLastSeatID);
	            if(bNeedPostMyInfo == true)
	                this.DelayPostMyInfo();
	            return;
	        }
	        GamePlayer pPlayer = this.GetMyself();
	        if(pPlayer != null && pPlayer.IsEnabled() == true)
	        {
	            pPlayer.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RESET);
	            this.PostMyOnlineGameState();
	        }
	        if(bNeedPostMyInfo == true)
                this.DelayPostMyInfo();
	    }
        catch (JSONException e) 
        {
        	XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handlePlayersOrderMessage faile by:", e.getMessage());
        }
		
		//this.DelayPostMyInfo();
		
	}

	private void handleGameSetttingMessage(JSONObject msgData)
	{
		if(msgData == null)
		{	
			XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handleGameSetttingMessage is called.", "empty json object");
			return;
		}
		XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handleGameSetttingMessage is called.", "handleGameSetttingMessage");
	
		try
		{
		    int nGameType = msgData.getInt(GameMsgConstant.GAME_MSG_KEY_GAMETYPEMSG);
		    if(0 <= nGameType)
		    {
		        if(XSGAPIGameServiceManager.getXSGAPIGameManager().IsGameHost() == false && m_bOnlineGameMasterCheck == true)
		        {
		            m_bOnlineGameMasterSettingReceived = true;
	                this.StopOnlineGameMasterCheck();
		            
	                if(m_bOnlineGamePeerBalanceReceived == false)
		            {
		               this.XSGAPIPlayerSendPeerBalanceRequest();
		            }
		     
		        }
		        
		        Configuration.setCurrentGameType(nGameType);
		        SimpleGambleWheel.m_ApplicationController.SetGameType(nGameType);
		    } 
		     
		    int nThemeType = msgData.getInt(GameMsgConstant.GAME_MSG_KEY_GAMETHEMEMSG);
	        if(GameConstants.GAME_THEME_ANIMAL <= nThemeType && nThemeType <= GameConstants.GAME_THEME_NUMBER)
	        {	
	        	Configuration.setCurrentThemeType(nThemeType);
	        	SimpleGambleWheel.m_ApplicationController.SetThemeType(nThemeType);
	        }
	    	    
		    int nPlayTurnType = msgData.getInt(GameMsgConstant.GAME_MSG_KEY_ONLINEPLAYSEQUENCE);
		    if(0 <= nPlayTurnType)
		    {    
		        Configuration.setPlayTurn(nPlayTurnType);
		    }
		    this.UpdateOnlineGamePlayerPlayabilty();
	    }
        catch (JSONException e) 
        {
        	XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handleGameSetttingMessage faile by:", e.getMessage());
        }
	}

	private void handlePlayerBalanceMessage(JSONObject msgData)
	{
		if(msgData == null)
		{	
			XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handlePlayerBalanceMessage is called.", "empty json object");
			return;
		}
		XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handlePlayerBalanceMessage is called.", "handlePlayerBalanceMessage");
	    
		try
		{
			String msgPlayerID = msgData.getString(GameMsgConstant.GAME_MSG_KEY_PLAIN_PLAYER_ID);
			int nChip = msgData.getInt(GameMsgConstant.GAME_MSG_KEY_PLAYER_CHIPS_BALANCE);
			if(nChip < 0 && msgPlayerID != null && 0 < msgPlayerID.length())
			{
				if(m_bOnlineGamePeerBalanceReceived == false)
				{
					m_bOnlineGamePeerBalanceReceived = true;
					if(m_bOnlineGameMasterCheck == true)
					{
						if(XSGAPIGameServiceManager.getXSGAPIGameManager().IsGameHost() == false)
						{
							if(m_bOnlineGameMasterSettingReceived == true)
								this.StopOnlineGameMasterCheck();
						}
						else
						{
							this.StopOnlineGameMasterCheck();
						}
					}
				}	
	            if(nChip <= 0)
	                 m_bOnlineGamePeerBalanceReceived = false;
	        }
		   	if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsActivated() == true && msgPlayerID.equals(m_GameLobby.m_Player0.GetPlayerID()) == true)
		    {
		   		m_GameLobby.m_Player0.SetupPacket(nChip);
		   		int nState = SimpleGambleWheel.m_ApplicationController.GetGameState();
                if(nState != GameConstants.GAME_STATE_RESULT && nState != GameConstants.GAME_STATE_RUN)
                {
                	m_GameLobby.m_Player0.UpdateCurrentGamePlayablity();
                }
                return;
		    }
		   	if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsActivated() == true && msgPlayerID.equals(m_GameLobby.m_Player1.GetPlayerID()) == true)
		    {
		   		m_GameLobby.m_Player1.SetupPacket(nChip);
		   		int nState = SimpleGambleWheel.m_ApplicationController.GetGameState();
                if(nState != GameConstants.GAME_STATE_RESULT && nState != GameConstants.GAME_STATE_RUN)
                {
                	m_GameLobby.m_Player1.UpdateCurrentGamePlayablity();
                }
                return;
		    }
		   	if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsActivated() == true && msgPlayerID.equals(m_GameLobby.m_Player2.GetPlayerID()) == true)
		    {
		   		m_GameLobby.m_Player2.SetupPacket(nChip);
		   		int nState = SimpleGambleWheel.m_ApplicationController.GetGameState();
                if(nState != GameConstants.GAME_STATE_RESULT && nState != GameConstants.GAME_STATE_RUN)
                {
                	m_GameLobby.m_Player2.UpdateCurrentGamePlayablity();
                }
                return;
		    }
		   	if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsActivated() == true && msgPlayerID.equals(m_GameLobby.m_Player3.GetPlayerID()) == true)
		    {
		   		m_GameLobby.m_Player3.SetupPacket(nChip);
		   		int nState = SimpleGambleWheel.m_ApplicationController.GetGameState();
                if(nState != GameConstants.GAME_STATE_RESULT && nState != GameConstants.GAME_STATE_RUN)
                {
                	m_GameLobby.m_Player3.UpdateCurrentGamePlayablity();
                }
                return;
		    }
			
		}	
		catch (JSONException e) 
	    {
	      	XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handlePlayerBalanceMessage faile by:", e.getMessage());
	    }
		
	}
	
	private void handlePlayerPledgeBetMessage(JSONObject msgData)
	{
		if(msgData == null)
		{	
			XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handlePlayerPledgeBetMessage is called.", "empty json object");
			return;
		}
		XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handlePlayerPledgeBetMessage is called.", "handlePlayerPledgeBetMessage");
	    
		try
		{
			String msgPlayerID = msgData.getString(GameMsgConstant.GAME_MSG_KEY_PLAIN_PLAYER_ID);
			int nChip = msgData.getInt(GameMsgConstant.GAME_MSG_KEY_PLAYER_CHIPS_BALANCE);
			int nLuckyNumber = msgData.getInt(GameMsgConstant.GAME_MSG_KEY_PLEDGET_LUCKYNUMBER);
			int nBet = msgData.getInt(GameMsgConstant.GAME_MSG_KEY_PLEDGET_BET);
			if(msgPlayerID != null && 0 < msgPlayerID.length() && 0 <= nChip && 0 <= nLuckyNumber && 0 <= nBet)
			{
				GamePlayer player = GetActivatedPLayerByPlayerID(msgPlayerID);
				if(player != null)
				{
	                int nSeat = player.GetSeatID();
	                this.PlayerFinishPledge(nSeat, nLuckyNumber, nBet);
	                player.SetupPacket(nChip);
				}
			}
		}	
		catch (JSONException e) 
	    {
	      	XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handlePlayerPledgeBetMessage faile by:", e.getMessage());
	    }

	}
	
	private void handleNextPlayTurnMessage(JSONObject msgData)
	{
		if(msgData == null)
		{	
			XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handleNextPlayTurnMessage is called.", "empty json object");
			return;
		}
		XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handleNextPlayTurnMessage is called.", "handleNextPlayTurnMessage");
	    
		try
		{
		    String msgPlayerID = msgData.getString(GameMsgConstant.GAME_MSG_KEY_GAMENEXTTURN_ID);
		    if(msgPlayerID == null)
		    	return;
		    this.m_GameLobby.m_nPlayerTurnIndex = -1;
		    if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsEnabled() == true && msgPlayerID.equals(m_GameLobby.m_Player0.GetPlayerID()) == true)
		    {	
		    	this.m_GameLobby.m_nPlayerTurnIndex = m_GameLobby.m_Player0.GetSeatID();
                this.SetOnLinePlayTurn();
                this.m_GameLobby.LocatePlayingSpinner();
                SimpleGambleWheel.m_ApplicationController.SetGameState(GameConstants.GAME_STATE_READY);
                return;
		    }
		    if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsEnabled() == true && msgPlayerID.equals(m_GameLobby.m_Player1.GetPlayerID()) == true)
		    {	
		    	this.m_GameLobby.m_nPlayerTurnIndex = m_GameLobby.m_Player1.GetSeatID();
                this.SetOnLinePlayTurn();
                this.m_GameLobby.LocatePlayingSpinner();
                SimpleGambleWheel.m_ApplicationController.SetGameState(GameConstants.GAME_STATE_READY);
                return;
		    }
		    if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsEnabled() == true && msgPlayerID.equals(m_GameLobby.m_Player2.GetPlayerID()) == true)
		    {	
		    	this.m_GameLobby.m_nPlayerTurnIndex = m_GameLobby.m_Player2.GetSeatID();
                this.SetOnLinePlayTurn();
                this.m_GameLobby.LocatePlayingSpinner();
                SimpleGambleWheel.m_ApplicationController.SetGameState(GameConstants.GAME_STATE_READY);
                return;
		    }
		    if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsEnabled() == true && msgPlayerID.equals(m_GameLobby.m_Player3.GetPlayerID()) == true)
		    {	
		    	this.m_GameLobby.m_nPlayerTurnIndex = m_GameLobby.m_Player3.GetSeatID();
                this.SetOnLinePlayTurn();
                this.m_GameLobby.LocatePlayingSpinner();
                SimpleGambleWheel.m_ApplicationController.SetGameState(GameConstants.GAME_STATE_READY);
                return;
		    }
		}	
		catch (JSONException e) 
	    {
	      	XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handleNextPlayTurnMessage faile by:", e.getMessage());
	    }
	}
	
	private void handlePlayerStateChangeMessage(JSONObject msgData)
	{
		if(msgData == null)
		{	
			XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handlePlayerStateChangeMessage is called.", "empty json object");
			return;
		}
		XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handlePlayerStateChangeMessage is called.", "handlePlayerStateChangeMessage");
	    
		try
		{
		    String msgPlayerID = msgData.getString(GameMsgConstant.GAME_MSG_KEY_PLAIN_PLAYER_ID);
		    int nState = msgData.getInt(GameMsgConstant.GAME_MSG_KEY_PLAYERSTATE);
		    if(nState < 0 || msgPlayerID == null || msgPlayerID.length() <= 0)
		        return;
		    
		    if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsEnabled() == true && msgPlayerID.equals(m_GameLobby.m_Player0.GetPlayerID()) == true)
		    {	
		    	m_GameLobby.m_Player0.SetOnlinePlayingState(nState);
	            return;
		    }
		    if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsEnabled() == true && msgPlayerID.equals(m_GameLobby.m_Player1.GetPlayerID()) == true)
		    {	
		    	m_GameLobby.m_Player1.SetOnlinePlayingState(nState);
	            return;
		    }
		    if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsEnabled() == true && msgPlayerID.equals(m_GameLobby.m_Player2.GetPlayerID()) == true)
		    {	
		    	m_GameLobby.m_Player2.SetOnlinePlayingState(nState);
	            return;
		    }
		    if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsEnabled() == true && msgPlayerID.equals(m_GameLobby.m_Player3.GetPlayerID()) == true)
		    {	
		    	m_GameLobby.m_Player3.SetOnlinePlayingState(nState);
	            return;
		    }
		    
		    if(SimpleGambleWheel.m_ApplicationController.GetGameState() == GameConstants.GAME_STATE_RESULT)
		    {
		    	if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsEnabled() == true && 
		    			m_GameLobby.m_Player0.GetOnlinePlayingState() == GameConstants.GAME_ONLINE_PLAYER_STATE_RUN)
		    	{
		    		return;
		    	}
		        SimpleGambleWheel.m_ApplicationController.ShowStatusBar(StringFactory.GetString_PlayingDone());
		    }
		}	
		catch (JSONException e) 
	    {
	      	XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handlePlayerStateChangeMessage faile by:", e.getMessage());
	    }
	}
	
	private void handlePlayerPlayablityChangeMessage(JSONObject msgData)
	{
		return; //No need to do anything
		/*
	    NSString* msgPlayerID = [msgData valueForKey:GAME_MSG_KEY_PLAIN_PLAYER_ID];
	    NSNumber* msgEnable = [msgData valueForKey:GAME_MSG_KEY_PLAYERPLAYABLITY];
	    if(msgEnable == nil || msgPlayerID == nil)
	        return;
	    
	    int nValue = [msgEnable intValue];
	    BOOL bEnable = YES;
	    if(nValue == 0)
	        bEnable = NO;
	    
	    for(int i = 0; i < 4; ++i)
	    {
	        if(m_Players[i] && [m_Players[i] IsEnabled] && [msgPlayerID isEqualToString:[m_Players[i] GetPlayerID]])
	        {
	            return;
	        }    
	    }*/
		
		/*if(msgData == null)
		{	
			XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handlePlayerPlayablityChangeMessage is called.", "empty json object");
			return;
		}
		XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handlePlayerPlayablityChangeMessage is called.", "handlePlayerPlayablityChangeMessage");
	    
		try
		{
		
		}	
		catch (JSONException e) 
	    {
	      	XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handlePlayerPlayablityChangeMessage faile by:", e.getMessage());
	    }*/
	}
	
	public void handleChipTransferMessage(JSONObject msgData, String senderID)
	{
		if(msgData == null)
		{	
			XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handleChipTransferMessage is called.", "empty json object");
			return;
		}
		if(senderID == null)
		{
			XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handleChipTransferMessage is called.", "empty playerID");
			return;
		}
		XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handleChipTransferMessage is called.", "handleChipTransferMessage");		
		
		try
		{
		    String msgRecieverID = msgData.getString(GameMsgConstant.GAME_MSG_KEY_MONEYRECIEVER);
		    int nChips = msgData.getInt(GameMsgConstant.GAME_MSG_KEY_TRANSMONEYMOUNT);
		    if(msgRecieverID == null || msgRecieverID.length() <= 0 || nChips <= 0)
		        return;
		    
		    int nSenderIndex = -1;
		    String senderName = "";
		    boolean bNeedKeepCheck = true;
		
		    if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsActivated() == true && senderID.equals(m_GameLobby.m_Player0.GetPlayerID()) == true)
		    {	
		    	m_GameLobby.m_Player0.AddMoneyToPacket(-nChips);
		    	m_GameLobby.m_Player0.UpdateCurrentGamePlayablity();
	            nSenderIndex = 0;
	            senderName = m_GameLobby.m_Player0.GetPlayerName();
	            bNeedKeepCheck = false;
		    }
		    
		    if(bNeedKeepCheck == true && m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsActivated() == true && senderID.equals(m_GameLobby.m_Player1.GetPlayerID()) == true)
		    {	
		    	m_GameLobby.m_Player1.AddMoneyToPacket(-nChips);
		    	m_GameLobby.m_Player1.UpdateCurrentGamePlayablity();
	            nSenderIndex = 1;
	            senderName = m_GameLobby.m_Player1.GetPlayerName();
	            bNeedKeepCheck = false;
		    }
		    
		    if(bNeedKeepCheck == true && m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsActivated() == true && senderID.equals(m_GameLobby.m_Player2.GetPlayerID()) == true)
		    {	
		    	m_GameLobby.m_Player2.AddMoneyToPacket(-nChips);
		    	m_GameLobby.m_Player2.UpdateCurrentGamePlayablity();
	            nSenderIndex = 2;
	            senderName = m_GameLobby.m_Player2.GetPlayerName();
	            bNeedKeepCheck = false;
		    }

		    if(bNeedKeepCheck == true && m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsActivated() == true && senderID.equals(m_GameLobby.m_Player3.GetPlayerID()) == true)
		    {	
		    	m_GameLobby.m_Player3.AddMoneyToPacket(-nChips);
		    	m_GameLobby.m_Player3.UpdateCurrentGamePlayablity();
	            nSenderIndex = 3;
	            senderName = m_GameLobby.m_Player3.GetPlayerName();
	            bNeedKeepCheck = false;
		    }
	
		    if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsActivated() == true && msgRecieverID.equals(m_GameLobby.m_Player0.GetPlayerID()) == true)
		    {	
		    	m_GameLobby.m_Player0.AddMoneyToPacket(nChips);
		    	m_GameLobby.m_Player0.UpdateCurrentGamePlayablity();
		    	m_GameLobby.m_Player0.ShowBalance();
	            if(0 <= nSenderIndex && nSenderIndex <= 3)
	            {    
	            	GamePlayer pPlayer = this.GetPlayer(nSenderIndex);
	            	if(pPlayer != null)
	            	{
	            		String szNumber = String.valueOf(nChips);
		                String szTextMsg =senderName + " " + StringFactory.GetString_PlayerSendMoneyToOtherFmt() + " " + m_GameLobby.m_Player0.GetPlayerName() + ": " + szNumber;
		                pPlayer.ShowOnlineMessage(szTextMsg);
	            	}
	            }  
	            if(m_GameLobby.m_Player0.IsMySelf() == true)
	            {    
	                this.PostChipTransferReceiptMessage(senderID);
	            }    
	            return;
		    }

		    if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsActivated() == true && msgRecieverID.equals(m_GameLobby.m_Player1.GetPlayerID()) == true)
		    {	
		    	m_GameLobby.m_Player1.AddMoneyToPacket(nChips);
		    	m_GameLobby.m_Player1.UpdateCurrentGamePlayablity();
		    	m_GameLobby.m_Player1.ShowBalance();
	            if(0 <= nSenderIndex && nSenderIndex <= 3)
	            {    
	            	GamePlayer pPlayer = this.GetPlayer(nSenderIndex);
	            	if(pPlayer != null)
	            	{
	            		String szNumber = String.valueOf(nChips);
		                String szTextMsg =senderName + " " + StringFactory.GetString_PlayerSendMoneyToOtherFmt() + " " + m_GameLobby.m_Player1.GetPlayerName() + ": " + szNumber;
		                pPlayer.ShowOnlineMessage(szTextMsg);
	            	}
	            }  
	            if(m_GameLobby.m_Player1.IsMySelf() == true)
	            {    
	                this.PostChipTransferReceiptMessage(senderID);
	            }    
	            return;
		    }

		    if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsActivated() == true && msgRecieverID.equals(m_GameLobby.m_Player2.GetPlayerID()) == true)
		    {	
		    	m_GameLobby.m_Player2.AddMoneyToPacket(nChips);
		    	m_GameLobby.m_Player2.UpdateCurrentGamePlayablity();
		    	m_GameLobby.m_Player2.ShowBalance();
	            if(0 <= nSenderIndex && nSenderIndex <= 3)
	            {    
	            	GamePlayer pPlayer = this.GetPlayer(nSenderIndex);
	            	if(pPlayer != null)
	            	{
	            		String szNumber = String.valueOf(nChips);
		                String szTextMsg =senderName + " " + StringFactory.GetString_PlayerSendMoneyToOtherFmt() + " " + m_GameLobby.m_Player2.GetPlayerName() + ": " + szNumber;
		                pPlayer.ShowOnlineMessage(szTextMsg);
	            	}
	            }  
	            if(m_GameLobby.m_Player2.IsMySelf() == true)
	            {    
	                this.PostChipTransferReceiptMessage(senderID);
	            }    
	            return;
		    }

		    if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsActivated() == true && msgRecieverID.equals(m_GameLobby.m_Player3.GetPlayerID()) == true)
		    {	
		    	m_GameLobby.m_Player3.AddMoneyToPacket(nChips);
		    	m_GameLobby.m_Player3.UpdateCurrentGamePlayablity();
		    	m_GameLobby.m_Player3.ShowBalance();
	            if(0 <= nSenderIndex && nSenderIndex <= 3)
	            {    
	            	GamePlayer pPlayer = this.GetPlayer(nSenderIndex);
	            	if(pPlayer != null)
	            	{
	            		String szNumber = String.valueOf(nChips);
		                String szTextMsg =senderName + " " + StringFactory.GetString_PlayerSendMoneyToOtherFmt() + " " + m_GameLobby.m_Player3.GetPlayerName() + ": " + szNumber;
		                pPlayer.ShowOnlineMessage(szTextMsg);
	            	}
	            }  
	            if(m_GameLobby.m_Player3.IsMySelf() == true)
	            {    
	                this.PostChipTransferReceiptMessage(senderID);
	            }    
	            return;
		    }
		}	
		catch (JSONException e) 
	    {
	      	XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handleChipTransferMessage faile by:", e.getMessage());
	    }
	}
	
	public void handleChipTransferReceiptMessage()
	{
		XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handleChipTransferReceiptMessage()", "handleChipTransferReceiptMessage");
	    GamePlayer myself = this.GetMyself();
	    if(myself != null)
	    {
	    	GamePlayer peer = this.OnlinePeerPlayer();
	        if(peer != null)
	        {
	            int nChips = myself.GetCachedTransferedChipNumber();
	            peer.AddMoneyToPacket(nChips);
	            peer.UpdateCurrentGamePlayablity();
	            peer.ShowBalance();		        
	        }

	        myself.SetCachedTransferedChipNumber(0);
	    }
	}
	
	public void handleSpinGambleWheelMessage(JSONObject msgData)
	{
		if(msgData == null)
		{	
			XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handleSpinGambleWheelMessage is called.", "empty json object");
			return;
		}
		XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handleSpinGambleWheelMessage is called.", "handleSpinGambleWheelMessage");		
		
		try
		{
		    CPinActionLevel action = new CPinActionLevel();
		    action.m_nFastCycle = 0;
		    int msgFast = msgData.getInt(GameMsgConstant.GAME_MSG_KEY_ACTION_FASTCYCLE); 
		    if(0 <= msgFast)
		        action.m_nFastCycle = msgFast;
		    
		    int msgMedium = msgData.getInt(GameMsgConstant.GAME_MSG_KEY_ACTION_MEDIUMCYCLE); 
		    action.m_nMediumCycle = 0;
		    if(0 <= msgMedium)
		        action.m_nMediumCycle = msgMedium;
		    
		    int msgSlow = msgData.getInt(GameMsgConstant.GAME_MSG_KEY_ACTION_SLOWCYCLE); 
		    action.m_nSlowCycle = 0;
		    if(0 <= msgSlow)
		        action.m_nSlowCycle = msgSlow;
		   
		    int msgAngle = msgData.getInt(GameMsgConstant.GAME_MSG_KEY_ACTION_SLOWANGLE);
		    action.m_nSlowAngle = 0;
		    if(0 <= msgAngle)
		        action.m_nSlowAngle = msgAngle;
		    
		    int msgVib = msgData.getInt(GameMsgConstant.GAME_MSG_KEY_ACTION_VIBCYCLE);
		    action.m_nVibCycle = 0;
		    if(0 <= msgVib)
		        action.m_nVibCycle = msgVib;
		    
		    int nClockwise = msgData.getInt(GameMsgConstant.GAME_MSG_KEY_ACTION_CLOCKWISE);
	        if(nClockwise <= 0)
	            action.m_bClockwise = false;
	        else 
	            action.m_bClockwise = true;
		    
	        //?????????????????????????????????
		    SimpleGambleWheel.m_ApplicationController.SetGameState(GameConstants.GAME_STATE_RUN);
		    SimpleGambleWheel.m_ApplicationController.SpinGambleWheel(action);
		    if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsEnabled() == true)
		    {	
		    	m_GameLobby.m_Player0.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RUN);
	            if(m_GameLobby.m_Player0.IsMySelf() == true)
	                this.PostMyOnlineGameState();
		    }
		    if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsEnabled() == true)
		    {	
		    	m_GameLobby.m_Player1.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RUN);
	            if(m_GameLobby.m_Player1.IsMySelf() == true)
	                this.PostMyOnlineGameState();
		    }
		    if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsEnabled() == true)
		    {	
		    	m_GameLobby.m_Player2.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RUN);
	            if(m_GameLobby.m_Player2.IsMySelf() == true)
	                this.PostMyOnlineGameState();
		    }
		    if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsEnabled() == true)
		    {	
		    	m_GameLobby.m_Player3.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RUN);
	            if(m_GameLobby.m_Player3.IsMySelf() == true)
	                this.PostMyOnlineGameState();
		    }
		}	
		catch (JSONException e) 
	    {
	      	XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.handleChipTransferMessage faile by:", e.getMessage());
	    }
	}
	
	public void parserMessageInformation(String msg, String playerID)
	{
		if(msg == null || msg.length() <= 0)
			return;

    	XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.parserMessageInformation message", msg);
    	XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.parserMessageInformation sender ID", playerID);
		
    	try
    	{
    		JSONObject msgData  = new JSONObject(msg);
    	    int nTypeID = msgData.getInt(GameMsgConstant.GAME_MSG_KEY_TYPE);
    		switch(nTypeID)
    		{
            	case GameMsgConstant.GAME_MSG_TYPE_TEXT:
            	{
            		String msgText = msgData.getString(GameMsgConstant.GAME_MSG_KEY_TEXTMSG);
            	   	XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.parserMessageInformation message type is text message", msgText);
            	   	if(msgText != null && 0 < msgText.length())
            	   	{
            	   		this.handleTextMessage(msgText, playerID);
            	   	}
            		break;
            	}
            	case GameMsgConstant.GAME_MSG_TYPE_MASTERCANDIATES:
            	{
            		this.handlePlayersOrderMessage(msgData);
            		break;
            	}
            	case GameMsgConstant.GAME_MSG_TYPE_GAMESETTINGSYNC:
            	{
            		this.handleGameSetttingMessage(msgData);
            		break;
            	}
            	case GameMsgConstant.GAME_MSG_TYPE_PLAYERBALANCE:
            	{
            		this.handlePlayerBalanceMessage(msgData);
            		break;
            	}
            	case GameMsgConstant.GAME_MSG_TYPE_PLAYERBET:
            	{
            		this.handlePlayerPledgeBetMessage(msgData);
            		break;
            	}
            	case GameMsgConstant.GAME_MSG_TYPE_GAMEPLAYNEXTTURN:
            	{
            		this.handleNextPlayTurnMessage(msgData);
            		break;
            	}
            	case GameMsgConstant.GAME_MSG_TYPE_PLAYERSTATE:
            	{
            		this.handlePlayerStateChangeMessage(msgData);
            		break;
            	}
            	case GameMsgConstant.GAME_MSG_TYPE_PLAYERPLAYABLITY:
            	{
            		//No need to do anything
            		//this.handlePlayerPlayablityChangeMessage(msgData);
            		break;
            	}
            	case GameMsgConstant.GAME_MSG_TYPE_CANCELPENDINGBET:
            	{
            		this.CancelPendPlayerBet();
            		break;
            	}
            	case GameMsgConstant.GAME_MSG_TYPE_MONEYTRANSFER:
            	{
            		this.handleChipTransferMessage(msgData, playerID);
            		break;
            	}
            	case GameMsgConstant.GAME_MSG_TYPE_MONEYTRANSFERRECEIPT:
            	{
            		this.handleChipTransferReceiptMessage();
            		break;
            	}
            	case GameMsgConstant.GAME_MSG_TYPE_ACTIONLEVEL:
            	{
            		this.handleSpinGambleWheelMessage(msgData);
            		break;
            	}
            	case GameMsgConstant.GAME_MSG_TYPE_AWSGAMEMASTERSETTINGNOTRECEIVED:
            	{
            		if(XSGAPIGameServiceManager.getXSGAPIGameManager().IsGameHost() == true)
            		{
            			this.PostOnlineGameSettting();
            		}
            		break;
            	}
            	case GameMsgConstant.GAME_MSG_TYPE_AWSGAMEPEERBALANCENOTRECEIVED:
            	{
            		DelayPostMyInfo();
            		break;
            	}
    		}
    	}
        catch (JSONException e) 
        {
        	XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.parserMessageInformation error:", e.getMessage());
        }
		SimpleGambleWheel.m_ApplicationController.UpdateGameUI();
	}
	
	private GamePlayer GetPlayerByPlayerID(String szPlayerID)
	{
	    GamePlayer pPlayer = null;
	    if(szPlayerID == null || szPlayerID.length() <= 0 || m_GameLobby == null)
	        return pPlayer;
        
	    
	    if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.GetPlayerID() != null && szPlayerID.equals(m_GameLobby.m_Player0.GetPlayerID()) == true)
	    	return m_GameLobby.m_Player0; 

	    if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.GetPlayerID() != null && szPlayerID.equals(m_GameLobby.m_Player1.GetPlayerID()) == true)
	    	return m_GameLobby.m_Player1; 
	    
	    if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.GetPlayerID() != null && szPlayerID.equals(m_GameLobby.m_Player2.GetPlayerID()) == true)
	    	return m_GameLobby.m_Player2; 

	    if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.GetPlayerID() != null && szPlayerID.equals(m_GameLobby.m_Player3.GetPlayerID()) == true)
	    	return m_GameLobby.m_Player3; 
	    
	    return pPlayer;
	}
	
	//StopGKCenterMasterCheck()
	private void StopOnlineGameMasterCheck()
	{
	    m_bOnlineGameMasterCheck = false;
	}

	private void ClearOnlineInitializeFlags()
	{
		m_bOnlineGameMasterCheck = true;
	    m_bOnlineGamePeerBalanceReceived = false;
	    m_bOnlineGameMasterSettingReceived = false;
	}
	
	//AWS related functions
	//-(void)StartAWSOnlineGamePreset
	public void StartXSGAPIOnlineGamePreset()
	{
	    this.ClearOnlineInitializeFlags();
	    this.SetOnlineGameHost(XSGAPIGameServiceManager.getXSGAPIGameManager().IsGameHost()); 

	    String peerID = XSGAPIGameServiceManager.getXSGAPIGameManager().GetConnectedPeer().GetUserID();
	    String peerName =  XSGAPIGameServiceManager.getXSGAPIGameManager().GetConnectedPeer().GetUserName();
	    String myID = XSGAPIGameServiceManager.getXSGAPIGameManager().GetLocalUser().GetUserID();
	    String myName = XSGAPIGameServiceManager.getXSGAPIGameManager().GetLocalUser().GetUserName();
	    
	    if(m_bHost)
	    {
	    	m_GameLobby.m_Player0.IntializeOnlinePlayerInfo(myName, myID, 0, true);
	    	m_GameLobby.m_Player0.Activate(true);
	        int nChips = ScoreRecord.GetMyChipBalance();
	        m_GameLobby.m_Player0.SetupPacket(nChips);
	        m_GameLobby.m_Player0.SetOnlineGameMaster(true);
	        
	        //?????????????2012-12-22
	        m_GameLobby.m_Player0.UpdateCurrentGamePlayablity();
	        
	    
	        m_GameLobby.m_Player1.IntializeOnlinePlayerInfo(peerName, peerID, 1, false);
	    	m_GameLobby.m_Player1.Activate(true);
	    	m_GameLobby.m_Player1.SetupPacket(GameConstants.GAME_DEFAULT_CHIPSINPACKET);//???????????????????
	    	m_GameLobby.m_Player1.SetOnlineGameMaster(false);
	        //?????????????2012-12-22
	    	m_GameLobby.m_Player1.UpdateCurrentGamePlayablity();

	        //       [self PostOnlineGamePlayersOrder];
	        String szData = XSGAPIGameServiceManager.getXSGAPIGameManager().GetPeerInitialData();
	        this.HandleXSGAPINonMasterPeerInitialData(szData);
	    }
	    else
	    {
	    	m_GameLobby.m_Player0.IntializeOnlinePlayerInfo(myName, myID, 1, true);
	    	m_GameLobby.m_Player0.Activate(true);
	        int nChips = ScoreRecord.GetMyChipBalance();
	        m_GameLobby.m_Player0.SetupPacket(nChips);
	        m_GameLobby.m_Player0.SetOnlineGameMaster(false);
	        
	        //?????????????2012-12-22
	        m_GameLobby.m_Player0.UpdateCurrentGamePlayablity();
	        
	        m_GameLobby.m_Player1.IntializeOnlinePlayerInfo(peerName, peerID, 0, false);
	    	m_GameLobby.m_Player1.Activate(true);
	    	m_GameLobby.m_Player1.SetupPacket(GameConstants.GAME_DEFAULT_CHIPSINPACKET);//???????????????????
	    	m_GameLobby.m_Player1.SetOnlineGameMaster(true);
	        //?????????????2012-12-22
	    	m_GameLobby.m_Player1.UpdateCurrentGamePlayablity();
	        
	        String szData = XSGAPIGameServiceManager.getXSGAPIGameManager().GetPeerInitialData();
	        this.HandleXSGAPIMasterPeerInitialData(szData);
	    }
    	m_GameLobby.m_Player2.ShowAndHidePlayer(false);
    	m_GameLobby.m_Player3.ShowAndHidePlayer(false);
	    //[m_PlayingSpinner StartAnimation];
	}
	
	//private void HandleAWSNonMasterPeerInitialData(String szData)
	private void HandleXSGAPINonMasterPeerInitialData(String szData)
	{
	    boolean bGetBalance = false;
	    if(szData != null && 0 < szData.length())
	    {
	    	try
	    	{
		    	JSONObject msgData  = new JSONObject(szData);
	    		if(msgData != null)
	    		{
	    			int nChip = msgData.getInt(GameMsgConstant.GAME_MSG_KEY_PLAYER_CHIPS_BALANCE);
	    			if(0 <= nChip)
	    			{
	    	            m_bOnlineGamePeerBalanceReceived = true;
	    	            this.StopOnlineGameMasterCheck();
	    	            m_GameLobby.m_Player1.SetupPacket(nChip); ///////////
	    	            m_GameLobby.m_Player1.UpdateCurrentGamePlayablity();
	    	            bGetBalance = true;
	    			}
	    		}
	    	}
	        catch (JSONException e) 
	        {
	        	XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.HandleXSGAPINonMasterPeerInitialData failed by:", e.getMessage());
	        }
	    }
	    
        if(bGetBalance == true)
        {
            m_bOnlineGameMasterSettingReceived = true;
            this.StopOnlineGameMasterCheck();
            return;
        }
	    
	    this.PostOnlineGameSettting();
	    this.PostMyOnlineGameBalance();
	    this.StopOnlineGameMasterCheck();//2012-12-22
	}

	//private void HandleAWSMasterPeerInitialData(String szData)
	private void HandleXSGAPIMasterPeerInitialData(String szData)
	{
	    //?????????????2012-12-22
	    boolean bGetType = false;
	    boolean bGetTurn = false;
	    boolean bGetBalance = false;
	    
	    if(szData != null && 0 < szData.length())
	    {
	    	try
	    	{
		    	JSONObject msgData  = new JSONObject(szData);
	    		if(msgData != null)
	    		{
	    			int nGameType = msgData.getInt(GameMsgConstant.GAME_MSG_KEY_GAMETYPEMSG);
	    	        if(0 <= nGameType)
	    	        {
	    	            Configuration.setCurrentGameType(nGameType);
	    	            SimpleGambleWheel.m_ApplicationController.SetGameType(nGameType);
	    	            bGetType = true;
	    	        }
	    		    int nThemeType = msgData.getInt(GameMsgConstant.GAME_MSG_KEY_GAMETHEMEMSG);
	    	        if(GameConstants.GAME_THEME_ANIMAL <= nThemeType && nThemeType <= GameConstants.GAME_THEME_NUMBER)
	    	        {	
	    	        	Configuration.setCurrentThemeType(nThemeType);
	    	        	SimpleGambleWheel.m_ApplicationController.SetThemeType(nThemeType);
	    	        }
	    	        
	    	        int nPlayTurnType = msgData.getInt(GameMsgConstant.GAME_MSG_KEY_ONLINEPLAYSEQUENCE);
	    	        if(0 <= nPlayTurnType)
	    	        {
	    	            Configuration.setPlayTurn(nPlayTurnType);
	    	            bGetTurn = true;
	    	        }
	    	        
	    	        int nChip = msgData.getInt(GameMsgConstant.GAME_MSG_KEY_PLAYER_CHIPS_BALANCE);
	    	        if(0 <= nChip)
	    	        {
	    	            m_bOnlineGamePeerBalanceReceived = true;
	    	            this.StopOnlineGameMasterCheck();
	    	            m_GameLobby.m_Player1.SetupPacket(nChip); ///////////
	    	            m_GameLobby.m_Player1.UpdateCurrentGamePlayablity();
	    	            bGetBalance = true;
	    	        }

	    		}		    	
	    	}
	        catch (JSONException e) 
	        {
	        	XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.HandleXSGAPIMasterPeerInitialData failed by:", e.getMessage());
	        }
	    }
        if(bGetBalance == true && bGetTurn == true && bGetType == true)
        {
            m_bOnlineGameMasterSettingReceived = true;
            this.StopOnlineGameMasterCheck();
            return;
        }
	    
	    this.PostMyOnlineGameBalance();
	}
	
	//??????????????????
	//AWSNonMasterPlayerSendMasterSettingRequest
	private void XSGAPINonMasterPlayerSendMasterSettingRequest()
	{
	    GameMessage msg = new GameMessage();
	    GameMsgFormatter.BeginFormatMsg(msg, GameMsgConstant.GAME_MSG_TYPE_AWSGAMEMASTERSETTINGNOTRECEIVED);
	    GameMsgFormatter.EndFormatMsg(msg);
	    this.SendMessageToAllPlayers(msg);
	}
	
	//??????????????????
	//AWSPlayerSendPeerBalanceRequest
	private void XSGAPIPlayerSendPeerBalanceRequest()	
	{
	    GameMessage msg = new GameMessage();
	    GameMsgFormatter.BeginFormatMsg(msg, GameMsgConstant.GAME_MSG_TYPE_AWSGAMEPEERBALANCENOTRECEIVED);
	    GameMsgFormatter.EndFormatMsg(msg);
	    this.SendMessageToAllPlayers(msg);
	}
	
	//-(void)ResetAWSService
	private void ResetXSGAPIService()
	{
        if(XSGAPIGameServiceManager.getXSGAPIGameManager().InOnlineGamePlay() == true)
        {
        	XSGAPIGameServiceManager.getXSGAPIGameManager().SendGamePlayDisconnectedMessageToPeer();
            XSGAPIGameServiceManager.getXSGAPIGameManager().EndGamePlaying();
            this.m_GameLobby.m_Player0.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RESET);
            this.m_GameLobby.m_Player1.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RESET);
            SimpleGambleWheel.m_ApplicationController.SetGameState(GameConstants.GAME_STATE_RESET);
	    }
	}
	
	private void AbsoultShutdownXSGAPIService()
	{
        if(XSGAPIGameServiceManager.getXSGAPIGameManager().InOnlineGamePlay() == true)
        {
        	XSGAPIGameServiceManager.getXSGAPIGameManager().SendGamePlayDisconnectedMessageToPeer();
            XSGAPIGameServiceManager.getXSGAPIGameManager().AbsoultExitGameService();
            this.m_GameLobby.m_Player0.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RESET);
            this.m_GameLobby.m_Player1.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RESET);
            SimpleGambleWheel.m_ApplicationController.SetGameState(GameConstants.GAME_STATE_RESET);
	    }
	}
	
    public void PostMyOnlineGameBalance()
	{
    	XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.PostMyOnlineGameBalance is called", "PostMyOnlineGameBalance");
        int nBalance = 0;

	    if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsActivated() == true && m_GameLobby.m_Player0.IsMySelf() == true)
	    {
            nBalance = m_GameLobby.m_Player0.GetPacketBalance();
            String szPlayerID = m_GameLobby.m_Player0.GetPlayerID();
            GameMessage msg = new GameMessage();
            GameMsgFormatter.FormatPlayerBalanceMsg(msg, szPlayerID, nBalance);
            this.SendMessageToAllPlayers(msg);
	    	return; 
	    }
        
	    if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsActivated() == true && m_GameLobby.m_Player1.IsMySelf() == true)
	    {
            nBalance = m_GameLobby.m_Player1.GetPacketBalance();
            String szPlayerID = m_GameLobby.m_Player1.GetPlayerID();
            GameMessage msg = new GameMessage();
            GameMsgFormatter.FormatPlayerBalanceMsg(msg, szPlayerID, nBalance);
            this.SendMessageToAllPlayers(msg);
	    	return; 
	    }

	    if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsActivated() == true && m_GameLobby.m_Player2.IsMySelf() == true)
	    {
            nBalance = m_GameLobby.m_Player2.GetPacketBalance();
            String szPlayerID = m_GameLobby.m_Player2.GetPlayerID();
            GameMessage msg = new GameMessage();
            GameMsgFormatter.FormatPlayerBalanceMsg(msg, szPlayerID, nBalance);
            this.SendMessageToAllPlayers(msg);
	    	return; 
	    }

	    if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsActivated() == true && m_GameLobby.m_Player3.IsMySelf() == true)
	    {
            nBalance = m_GameLobby.m_Player3.GetPacketBalance();
            String szPlayerID = m_GameLobby.m_Player3.GetPlayerID();
            GameMessage msg = new GameMessage();
            GameMsgFormatter.FormatPlayerBalanceMsg(msg, szPlayerID, nBalance);
            this.SendMessageToAllPlayers(msg);
	    	return; 
	    }
	}
    
    public void PostMyOnlineGameState()
	{
    	XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.PostMyOnlineGameState is called", "PostMyOnlineGameState");
	    
    	if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsActivated() == true && m_GameLobby.m_Player0.IsMySelf() == true)
	    {
            int nState = m_GameLobby.m_Player0.GetOnlinePlayingState();
            String szPlayerID = m_GameLobby.m_Player0.GetPlayerID();
            GameMessage msg = new GameMessage();
            GameMsgFormatter.FormatPlayerStateMsg(msg, szPlayerID, nState);
            this.SendMessageToAllPlayers(msg);
	    	return; 
	    }

	    if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsActivated() == true && m_GameLobby.m_Player1.IsMySelf() == true)
	    {
            int nState = m_GameLobby.m_Player1.GetOnlinePlayingState();
            String szPlayerID = m_GameLobby.m_Player1.GetPlayerID();
            GameMessage msg = new GameMessage();
            GameMsgFormatter.FormatPlayerStateMsg(msg, szPlayerID, nState);
            this.SendMessageToAllPlayers(msg);
	    	return; 
	    }

	    if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsActivated() == true && m_GameLobby.m_Player2.IsMySelf() == true)
	    {
            int nState = m_GameLobby.m_Player2.GetOnlinePlayingState();
            String szPlayerID = m_GameLobby.m_Player2.GetPlayerID();
            GameMessage msg = new GameMessage();
            GameMsgFormatter.FormatPlayerStateMsg(msg, szPlayerID, nState);
            this.SendMessageToAllPlayers(msg);
	    	return; 
	    }

	    if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsActivated() == true && m_GameLobby.m_Player3.IsMySelf() == true)
	    {
            int nState = m_GameLobby.m_Player3.GetOnlinePlayingState();
            String szPlayerID = m_GameLobby.m_Player3.GetPlayerID();
            GameMessage msg = new GameMessage();
            GameMsgFormatter.FormatPlayerStateMsg(msg, szPlayerID, nState);
            this.SendMessageToAllPlayers(msg);
	    	return; 
	    }
	    
	}

    public void PostOnlineGameSettting()
	{
    	XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.PostOnlineGameSettting is called", "PostOnlineGameSettting");
        GameMessage msg = new GameMessage();
        int nGameType = SimpleGambleWheel.m_ApplicationController.m_GameController.GetGameType();
        int nPlayTurnType = Configuration.getPlayTurnType();
        GameMsgFormatter.FormatGameSettingMsg(msg, nGameType, nPlayTurnType);
        this.SendMessageToAllPlayers(msg);
	}
    
    public void PostSpinGambleWheelMessage(CPinActionLevel action)
	{
    	XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.PostSpinGambleWheelMessage is called", "PostSpinGambleWheelMessage");
        GameMessage msg = new GameMessage();
        GameMsgFormatter.FormatActionMsg(msg, action);
        this.SendMessageToAllPlayers(msg);
	}
    
    public void PostMyOnlineGameBet()
    {
    	XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.PostMyOnlineGameBet is called", "PostMyOnlineGameBet");
    	if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsActivated() == true && m_GameLobby.m_Player0.IsMySelf() == true && m_GameLobby.m_Player0.AlreadyMadePledge() == true)
	    {
            int chipBalace = m_GameLobby.m_Player0.GetPacketBalance();
            int nBet = m_GameLobby.m_Player0.GetPlayBet();
            int nLuckNumber = m_GameLobby.m_Player0.GetPlayBetLuckNumber();
            String szPlayerID = m_GameLobby.m_Player0.GetPlayerID();
            GameMessage msg = new GameMessage();
            GameMsgFormatter.FormatPlayerBetMsg(msg, szPlayerID, nLuckNumber, nBet, chipBalace);
            this.SendMessageToAllPlayers(msg);
	    	return; 
	    }

    	if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsActivated() == true && m_GameLobby.m_Player1.IsMySelf() == true && m_GameLobby.m_Player1.AlreadyMadePledge() == true)
	    {
            int chipBalace = m_GameLobby.m_Player1.GetPacketBalance();
            int nBet = m_GameLobby.m_Player1.GetPlayBet();
            int nLuckNumber = m_GameLobby.m_Player1.GetPlayBetLuckNumber();
            String szPlayerID = m_GameLobby.m_Player1.GetPlayerID();
            GameMessage msg = new GameMessage();
            GameMsgFormatter.FormatPlayerBetMsg(msg, szPlayerID, nLuckNumber, nBet, chipBalace);
            this.SendMessageToAllPlayers(msg);
	    	return; 
	    }

    	if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsActivated() == true && m_GameLobby.m_Player2.IsMySelf() == true && m_GameLobby.m_Player2.AlreadyMadePledge() == true)
	    {
            int chipBalace = m_GameLobby.m_Player2.GetPacketBalance();
            int nBet = m_GameLobby.m_Player2.GetPlayBet();
            int nLuckNumber = m_GameLobby.m_Player2.GetPlayBetLuckNumber();
            String szPlayerID = m_GameLobby.m_Player2.GetPlayerID();
            GameMessage msg = new GameMessage();
            GameMsgFormatter.FormatPlayerBetMsg(msg, szPlayerID, nLuckNumber, nBet, chipBalace);
            this.SendMessageToAllPlayers(msg);
	    	return; 
	    }

    	if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsActivated() == true && m_GameLobby.m_Player3.IsMySelf() == true && m_GameLobby.m_Player3.AlreadyMadePledge() == true)
	    {
            int chipBalace = m_GameLobby.m_Player3.GetPacketBalance();
            int nBet = m_GameLobby.m_Player3.GetPlayBet();
            int nLuckNumber = m_GameLobby.m_Player3.GetPlayBetLuckNumber();
            String szPlayerID = m_GameLobby.m_Player3.GetPlayerID();
            GameMessage msg = new GameMessage();
            GameMsgFormatter.FormatPlayerBetMsg(msg, szPlayerID, nLuckNumber, nBet, chipBalace);
            this.SendMessageToAllPlayers(msg);
	    	return; 
	    }
    	
    }
    
    public void PostChipTransferReceiptMessage(String szMoneySenderID)
    {
        GameMessage msg = new GameMessage();
        GameMsgFormatter.FormatChipTransferReceiptMsg(msg, szMoneySenderID);
        this.SendMessage(msg, szMoneySenderID);
    }

    public void PostMyTextMessage(String text)
    {
        GameMessage msg = new GameMessage();
        GameMsgFormatter.FormatTextMsg(msg, text);
        this.SendMessageToAllPlayers(msg);
    	
    }
    
    private void DelayPostMyInfo()
    {
        GamePlayer pPlayer = this.GetMyself();
        if(pPlayer != null)
            pPlayer.UpdateCurrentGamePlayablity();
        this.PostMyOnlineGameBalance();
    }
    
    private void SendNextPlayTurnMessage()
    {
		XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.SendNextPlayTurnMessage", "SendNextPlayTurnMessage");
    
        if(0 <= this.m_GameLobby.m_nPlayerTurnIndex)
        {
            GamePlayer pPlayer = this.GetPlayer(this.m_GameLobby.m_nPlayerTurnIndex);
            if(pPlayer != null && pPlayer.IsEnabled() == true)
            {
                String szPlayerID = pPlayer.GetPlayerID();
                GameMessage msg = new GameMessage();
                GameMsgFormatter.FormatNextTurnMsg(msg, szPlayerID);
                this.SendMessageToAllPlayers(msg);
                return;
            }
        }
    }    
    
    
    
    private int GetMySeatID()
    {
        int nRet = -1;
	    if(m_GameLobby == null)
	        return nRet;
        
	    
	    if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.GetPlayerID() != null && m_GameLobby.m_Player0.IsMySelf() == true)
	    {
            nRet = m_GameLobby.m_Player0.GetSeatID();
	    	return nRet; 
	    }

	    if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.GetPlayerID() != null && m_GameLobby.m_Player1.IsMySelf() == true)
	    {
            nRet = m_GameLobby.m_Player1.GetSeatID();
	    	return nRet; 
	    }

	    if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.GetPlayerID() != null && m_GameLobby.m_Player2.IsMySelf() == true)
	    {
            nRet = m_GameLobby.m_Player2.GetSeatID();
	    	return nRet; 
	    }
	    
	    if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.GetPlayerID() != null && m_GameLobby.m_Player3.IsMySelf() == true)
	    {
            nRet = m_GameLobby.m_Player3.GetSeatID();
	    	return nRet; 
	    }
        return nRet;
    }
    
    private void UpdateOnlineGamePlayerPlayabilty()
    {
    	if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsActivated() == true)
	    {
    		m_GameLobby.m_Player0.ClearPlayBet();
    		m_GameLobby.m_Player0.UpdateCurrentGamePlayablity();
	    }
    	if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsActivated() == true)
	    {
    		m_GameLobby.m_Player1.ClearPlayBet();
    		m_GameLobby.m_Player1.UpdateCurrentGamePlayablity();
	    }
    	if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsActivated() == true)
	    {
    		m_GameLobby.m_Player2.ClearPlayBet();
    		m_GameLobby.m_Player2.UpdateCurrentGamePlayablity();
	    }
    	if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsActivated() == true)
	    {
    		m_GameLobby.m_Player3.ClearPlayBet();
    		m_GameLobby.m_Player3.UpdateCurrentGamePlayablity();
	    }
        
    }    
    
    private void DisableNonAcitvatedSeat(int nLastSeatID)
    {
    	XSGAPIReleaseConfigure.LogDebugInfo("GamePlayOnlineGroup.DisableNonAcitvatedSeat is called.", "DisableNonAcitvatedSeat");
	    
    	if(m_GameLobby.m_Player0 != null)
	    {
	    	int nSeatID = m_GameLobby.m_Player0.GetSeatID();
            if(nSeatID < 0 || nLastSeatID <= nSeatID)
            {
            	m_GameLobby.m_Player0.Activate(false);
            }
	    }
	    if(m_GameLobby.m_Player1 != null)
	    {
	    	int nSeatID = m_GameLobby.m_Player1.GetSeatID();
            if(nSeatID < 0 || nLastSeatID <= nSeatID)
            {
            	m_GameLobby.m_Player1.Activate(false);
            }
	    }
	    if(m_GameLobby.m_Player2 != null)
	    {
	    	int nSeatID = m_GameLobby.m_Player2.GetSeatID();
            if(nSeatID < 0 || nLastSeatID <= nSeatID)
            {
            	m_GameLobby.m_Player2.Activate(false);
            }
	    }
	    if(m_GameLobby.m_Player3 != null)
	    {
	    	int nSeatID = m_GameLobby.m_Player3.GetSeatID();
            if(nSeatID < 0 || nLastSeatID <= nSeatID)
            {
            	m_GameLobby.m_Player3.Activate(false);
            }
	    }

        GamePlayer pPlayer = this.GetMyself();
        if(pPlayer != null && pPlayer.IsEnabled() == true)
        {
            pPlayer.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_RESET);
            this.PostMyOnlineGameState();
        }
    }

    public void PlayerFinishPledge(int nSeat, int nLuckNumber, int nBetMoney)
    {
        GamePlayer pPlayer = this.GetPlayer(nSeat);
        if(pPlayer != null && pPlayer.IsActivated() == true)
        {
        	pPlayer.MakePlayBet(nLuckNumber, nBetMoney);
        	pPlayer.SetOnlinePlayingState(GameConstants.GAME_ONLINE_PLAYER_STATE_READY);
            int nMyseatiD = this.GetMySeatID();
            if(nMyseatiD == nSeat)
            {
                this.PostMyOnlineGameBet();
            }
            
            this.UpdateOnlinePlayersStateToReady();
        }
    }
    
    private void UpdateOnlinePlayersStateToReady()
    {
        boolean bShowBet = true;
	   	if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsEnabled() == true)
	    {
            if (m_GameLobby.m_Player0.AlreadyMadePledge() == false || m_GameLobby.m_Player0.GetOnlinePlayingState() != GameConstants.GAME_ONLINE_PLAYER_STATE_READY) 
            {
                return;
            }
	    }
	   	if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsEnabled() == true)
	    {
            if (m_GameLobby.m_Player1.AlreadyMadePledge() == false || m_GameLobby.m_Player1.GetOnlinePlayingState() != GameConstants.GAME_ONLINE_PLAYER_STATE_READY) 
            {
                return;
            }
	    }
	   	if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsEnabled() == true)
	    {
            if (m_GameLobby.m_Player2.AlreadyMadePledge() == false || m_GameLobby.m_Player2.GetOnlinePlayingState() != GameConstants.GAME_ONLINE_PLAYER_STATE_READY) 
            {
                return;
            }
	    }
	   	if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsEnabled() == true)
	    {
            if (m_GameLobby.m_Player3.AlreadyMadePledge() == false || m_GameLobby.m_Player3.GetOnlinePlayingState() != GameConstants.GAME_ONLINE_PLAYER_STATE_READY) 
            {
                return;
            }
	    }
        
        if(bShowBet == true)
        {
            this.ShowPlayersPledgeInformation();
            this.MakeOnlinePlayTurn();
            SimpleGambleWheel.m_ApplicationController.SetGameState(GameConstants.GAME_STATE_READY);
        }
    }

    private void ShowPlayersPledgeInformation()
    {
    	if(m_GameLobby != null)
    		m_GameLobby.ShowPlayersPledgeInformation();
    }
 
    private void MakeOnlinePlayTurn()
    {
        if(Configuration.getPlayTurnType() == GameConstants.GAME_PLAYTURN_TYPE_SEQUENCE)
        {
            this.MakeNextOnlinePlayTurnBySequence();
        }
        else 
        {
        	this.MakeNextOnlinePlayTurnByMaxBet();
        }
    }

    private void MakeNextOnlinePlayTurnBySequence()
    {
        int nCount = this.GetEnabledOnlinePlayesCount();
        if(nCount <= 0 || this.m_GameLobby == null)
            return;
        
        if(this.m_GameLobby.m_nPlayerTurnIndex < 0)
        {
        	this.m_GameLobby.m_nPlayerTurnIndex = 0;
        }
        else 
        {
        	this.m_GameLobby.m_nPlayerTurnIndex = (this.m_GameLobby.m_nPlayerTurnIndex+1)%nCount;
        }
        GamePlayer pPlayer = this.GetPlayer(this.m_GameLobby.m_nPlayerTurnIndex);
        if(pPlayer != null && pPlayer.IsEnabled() == true)
        {    
            this.SetOnLinePlayTurn();
            this.m_GameLobby.LocatePlayingSpinner();
            if(m_bHost == true)
                this.SendNextPlayTurnMessage();
        }
        else
        {
            this.MakeNextOnlinePlayTurnBySequence();
        }
        String playerName = this.GetPlayerName(this.m_GameLobby.m_nPlayerTurnIndex);
        String szText = playerName + StringFactory.GetString_ItIsPlayTurnFmt();
        SimpleGambleWheel.m_ApplicationController.ShowStatusBar(szText);
    }
    
    private void MakeNextOnlinePlayTurnByMaxBet()
    {
        int nBetMax = 0;
        this.m_GameLobby.m_nPlayerTurnIndex = 0;
	    if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsEnabled() == true && m_GameLobby.m_Player0.AlreadyMadePledge() == true)
	    {	
            int nBet = m_GameLobby.m_Player0.GetPlayBet();
            if(nBetMax < nBet)
            {
            	 this.m_GameLobby.m_nPlayerTurnIndex = m_GameLobby.m_Player0.GetSeatID();
                nBetMax = nBet;
            }
	    }
	    if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsEnabled() == true && m_GameLobby.m_Player1.AlreadyMadePledge() == true)
	    {	
            int nBet = m_GameLobby.m_Player1.GetPlayBet();
            if(nBetMax < nBet)
            {
            	 this.m_GameLobby.m_nPlayerTurnIndex = m_GameLobby.m_Player1.GetSeatID();
                nBetMax = nBet;
            }
	    }
	    if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsEnabled() == true && m_GameLobby.m_Player2.AlreadyMadePledge() == true)
	    {	
            int nBet = m_GameLobby.m_Player2.GetPlayBet();
            if(nBetMax < nBet)
            {
            	 this.m_GameLobby.m_nPlayerTurnIndex = m_GameLobby.m_Player2.GetSeatID();
                nBetMax = nBet;
            }
	    }
	    if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsEnabled() == true && m_GameLobby.m_Player3.AlreadyMadePledge() == true)
	    {	
            int nBet = m_GameLobby.m_Player3.GetPlayBet();
            if(nBetMax < nBet)
            {
            	 this.m_GameLobby.m_nPlayerTurnIndex = m_GameLobby.m_Player3.GetSeatID();
                nBetMax = nBet;
            }
	    }
        
        GamePlayer pPlayer = this.GetPlayer(this.m_GameLobby.m_nPlayerTurnIndex);
        if(pPlayer != null && pPlayer.IsEnabled() == true)
        {    
            this.SetOnLinePlayTurn();
            this.m_GameLobby.LocatePlayingSpinner();
            if(m_bHost)
                this.SendNextPlayTurnMessage();
        }
        String playerName = this.GetPlayerName(this.m_GameLobby.m_nPlayerTurnIndex);
        String szText = playerName + StringFactory.GetString_ItIsPlayTurnFmt();
        SimpleGambleWheel.m_ApplicationController.ShowStatusBar(szText);
    }
    
    
    private void SetOnlineGameHost(boolean bHost)
    {
        m_bHost = bHost;
    }

    private boolean IsOnlineGameHost()
    {
        return m_bHost;
    }

    private void SetOnLinePlayTurn()
    {
	    if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsEnabled() == true)
	    {	
            if(m_GameLobby.m_Player0.GetSeatID() == this.m_GameLobby.m_nPlayerTurnIndex)
            {
            	m_GameLobby.m_Player0.SetMyTurnFlag(true);
            }
            else
            {
            	m_GameLobby.m_Player0.SetMyTurnFlag(false);
            }
	    }
	    if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsEnabled() == true)
	    {	
            if(m_GameLobby.m_Player1.GetSeatID() == this.m_GameLobby.m_nPlayerTurnIndex)
            {
            	m_GameLobby.m_Player1.SetMyTurnFlag(true);
            }
            else
            {
            	m_GameLobby.m_Player1.SetMyTurnFlag(false);
            }
	    }
	    if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsEnabled() == true)
	    {	
            if(m_GameLobby.m_Player2.GetSeatID() == this.m_GameLobby.m_nPlayerTurnIndex)
            {
            	m_GameLobby.m_Player2.SetMyTurnFlag(true);
            }
            else
            {
            	m_GameLobby.m_Player2.SetMyTurnFlag(false);
            }
	    }
	    if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsEnabled() == true)
	    {	
            if(m_GameLobby.m_Player3.GetSeatID() == this.m_GameLobby.m_nPlayerTurnIndex)
            {
            	m_GameLobby.m_Player3.SetMyTurnFlag(true);
            }
            else
            {
            	m_GameLobby.m_Player3.SetMyTurnFlag(false);
            }
	    }
    }
   
    private int GetEnabledOnlinePlayesCount()
    {
	    int nRet = 0;
	    if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsEnabled() == true)
	    {	
	    	++nRet;
	    }
	    if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsEnabled() == true)
	    {	
	    	++nRet;
	    }
	    
	    if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsEnabled() == true)
	    {	
	    	++nRet;
	    }

	    if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsEnabled() == true)
	    {	
	    	++nRet;
	    }
	    
	    return nRet;
    }
    
    public void SendMessageToAllPlayers(GameMessage msg)
    {
        XSGAPIGameServiceManager.getXSGAPIGameManager().SendGamePlayingMessage(msg._m_GameMessage);
    }

    public void SendMessage(GameMessage msg, String playerID)
    {
    	SendMessageToAllPlayers(msg);
    }
    
    private GamePlayer OnlinePeerPlayer()
    {
    	GamePlayer player = null;
    	
	    if(m_GameLobby != null && m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsOnlinePlayer() == true && m_GameLobby.m_Player0.IsMySelf() == false)
	    {	
	    	return m_GameLobby.m_Player0;
	    }
	    if(m_GameLobby != null && m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsOnlinePlayer() == true && m_GameLobby.m_Player1.IsMySelf() == false)
	    {	
	    	return m_GameLobby.m_Player1;
	    }
	    if(m_GameLobby != null && m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsOnlinePlayer() == true && m_GameLobby.m_Player2.IsMySelf() == false)
	    {	
	    	return m_GameLobby.m_Player2;
	    }
	    if(m_GameLobby != null && m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsOnlinePlayer() == true && m_GameLobby.m_Player3.IsMySelf() == false)
	    {	
	    	return m_GameLobby.m_Player3;
	    }
    	
    	return player;
    }
    
    public void OnlinePlayerTranfereChipsFrom(int nChips)
    {
    	GamePlayer myself = this.GetMyself();
    	GamePlayer peer = OnlinePeerPlayer();
        if(myself != null && peer != null)
        {
            myself.SetCachedTransferedChipNumber(nChips);
            myself.AddMoneyToPacket(-nChips);
	        if(myself.IsEnabled() == false)
	        	myself.UpdateCurrentGamePlayablity();
	        this.PostChipTransferMessage(nChips, peer.GetPlayerID());
	        this.PostMyOnlineGameBalance();
        }
    }
    
    public void PostChipTransferMessage(int nTransferChips, String szPlayerID)
    {
        GameMessage msg = new GameMessage();
        GameMsgFormatter.FormatChipTransferMsg(msg, szPlayerID, nTransferChips);
        SendMessageToAllPlayers(msg);
    }
    
    public String GetOnlinePeerName()
    {
    	String string = new String("Online Peer");
    	
    	GamePlayer peer = this.OnlinePeerPlayer();
    	if(peer != null)
    		string = peer.GetPlayerName();
    	
    	return string;
    }
}
