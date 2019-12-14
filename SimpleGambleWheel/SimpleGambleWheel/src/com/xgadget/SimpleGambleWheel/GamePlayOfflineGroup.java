package com.xgadget.SimpleGambleWheel;

import android.os.SystemClock;
import android.view.MotionEvent;

public class GamePlayOfflineGroup extends GamePlayGroupBase 
{

	public GamePlayOfflineGroup() 
	{
		// TODO Auto-generated constructor stub
		super();
	}

	public boolean GetOnlineGroup()
	{
		return false;
	}
	
	public void ShutdownSection()
	{
	    this.PauseGame();
	    super.ShutdownSection();
	}

	
	public void StartGameSection()
	{
		super.StartGameSection();
		this.CancelPendPlayerBet();
		InitializeOfflineGameSetting();
	    InitializeDefaultPlayersConfiguration();    
	    //[m_PlayingSpinner StartAnimation];
	}
	
	public void InitializeOfflineGameSetting()
	{
		if(m_GameLobby != null)
			m_GameLobby.RestoreOffLineReadyState(); 
	}
	
	public void InitializeDefaultPlayersConfiguration()
	{
		if(m_GameLobby != null)
			m_GameLobby.InitializeDefaultOfflinePlayersConfiguration(); 
	}
	
	public int GetMyCurrentMoney()
	{
		int nRet = -1;
		if(m_GameLobby != null)
			nRet = m_GameLobby.GetMyCurrentMoney(); 
	    return nRet;
	}

	public void AddMoneyToMyAccount(int nChips)
	{
		if(m_GameLobby != null)
			 m_GameLobby.AddMoneyToMyAccount(nChips); 
	}

	public int GetPlayerCurrentMoney(int nSeat)
	{
		int nRet = -1;
		if(m_GameLobby != null)
			nRet = m_GameLobby.GetPlayerCurrentMoney(nSeat); 
	    return nRet;
	}

	public void AddMoneyToPlayerAccount(int nChips,  int nSeat)
	{
		if(m_GameLobby != null)
			m_GameLobby.OfflineAddMoneyToPlayerAccount(nChips, nSeat);
	}

	public void CancelPendPlayerBet()
	{
		if(m_GameLobby != null)
			m_GameLobby.OfflineCancelPendPlayerBet();
	}

	public int GetGameState()
	{
		int nRet = GameConstants.GAME_STATE_READY;
		
		if(m_GameLobby != null)
			nRet = m_GameLobby.GetGameState();
		
		return nRet;
	}
	
	public void SetSystemOnHold(boolean bOnHold)
	{
		if(m_GameLobby != null)
			m_GameLobby.SetSystemOnHold(bOnHold);
	}

	public boolean IsSystemOnHold()
	{
		boolean bRet = false;
		if(m_GameLobby != null)
			bRet = m_GameLobby.IsSystemOnHold();
		
		return bRet;
	}
	
	public void RestoreOffLineReadyState()
	{
		if(m_GameLobby != null)
			m_GameLobby.RestoreOffLineReadyState();
	}

	public boolean CanPlayerPlayGame(int nType, int nSeatID)
	{
		boolean bRet = false;
		if(m_GameLobby != null)
			bRet = m_GameLobby.CanPlayerPlayGame(nType, nSeatID);
		return bRet;
	}
	
	public void ForceClosePlayerMenus()
	{
		if(m_GameLobby != null)
			m_GameLobby.ForceClosePlayerMenus();
	}

    public void PauseGame()
    {
		if(m_GameLobby != null)
		{
			m_GameLobby.PauseGame();
		}
    }

    public void ResumeGame()
    {
/*    	
        if([Configuration canPlaySound])
        {
            [m_GameSession PlayCurrentGameStateSound];
        } 
*/           
        CancelPendPlayerBet();
        SimpleGambleWheel.m_ApplicationController.SetGameState(GameConstants.GAME_STATE_RESET);
        SavePlayersOfflineStateInformation();
    }
    
    public void SavePlayersOfflineStateInformation()
    {
		if(m_GameLobby != null)
			m_GameLobby.SavePlayersOfflineStateInformation();
    }

	public void OnTimerEvent()
	{
		if(m_GameLobby != null)
		{	
			//m_GameLobby.OnTimerEvent();
			if(m_GameLobby.m_bInOffLineReadyPlay == true && IsSystemOnHold() == false && Configuration.isRoPaAutoBet() == true)
			{
				long curTime = CApplicationController.GetSystemTimerClick();
				if(m_GameLobby.m_OffLineResetToReadyDelay < (curTime - m_GameLobby.m_OffLineReadyPlayTime))
				{
					if(m_GameLobby.AllOfflinePlayersMakePledge() == true)
					{    
						m_GameLobby.StartOffLinePlayerAction();
					}    
					else
					{
						m_GameLobby.m_nPlayerTurnIndex = m_GameLobby.m_nPlayerTurnIndex-1;
						if(m_GameLobby.m_nPlayerTurnIndex < 0)
							m_GameLobby.m_nPlayerTurnIndex = 0;
						m_GameLobby.m_bInOffLineReadyPlay = false;
						SimpleGambleWheel.m_ApplicationController.MakePlayerManualPledge(0);
					}
				}	
	        }
	    }
	}

	public boolean HandleTouchEvent(MotionEvent event)
	{
		boolean bRet = true;
		
		HandleSpinPointerEvent(event);
	
		return bRet;
	}

	private boolean CanSetToOnHold()
	{
	    boolean bRet = false;
	    
	    if((GetGameState() == GameConstants.GAME_STATE_READY || GetGameState() == GameConstants.GAME_STATE_RESET))
	    {
	        bRet = true;
	    }
	    
	    return bRet;
	}
	
	private void HandleNonSpinTouchEvent()
	{
		if(m_GameLobby == null)
			return;
		
	    if(GetGameState() == GameConstants.GAME_STATE_RESET)
	    {
	        if(Configuration.isRoPaAutoBet() == true)
	        {
	            GamePlayer pMyself = m_GameLobby.GetMyself();
	            if(pMyself != null && pMyself.IsActivated() == true)
	            {
	                pMyself.UpdateCurrentGamePlayablity();
	                if(pMyself.IsEnabled() == false)
	                {
	                    if(CanSetToOnHold() == true)
	                    {
	                        SetSystemOnHold(true);
	                        //[ApplicationConfigure SetRedeemPlayerSeat:0];
	                        //[GUIEventLoop SendEvent:GUIID_EVENT_PURCHASECHIPS eventSender:self];
	                        SimpleGambleWheel.m_ApplicationController.PurchaseChips();
	                    }
	                    return;
	                }
	                if(pMyself.AlreadyMadePledge() == false)
	                {
	                	SimpleGambleWheel.m_ApplicationController.MakePlayerManualPledge(0);
	                    return;
	                }
	            }
	            m_GameLobby.UpdateOfflineOtherPlayersAutoPledge(true);
	        }
	        else 
	        {
	            for(int i = 0; i < 4; ++i)
	            {
	            	GamePlayer player = m_GameLobby.GetPlayer(i);
	                if(player != null && player.IsEnabled() == true && player.AlreadyMadePledge() == false)
	                {
	                	SimpleGambleWheel.m_ApplicationController.MakePlayerManualPledge(i);
	                    return;
	                }
	            }
	        	
	        }
	    }
	    else if(GetGameState() == GameConstants.GAME_STATE_RESULT)
	    {
	        SimpleGambleWheel.m_ApplicationController.SetGameState(GameConstants.GAME_STATE_RESET);//[m_GameController SetGameState:GAME_STATE_RESET];
	        return;         
	    }
	}
	
	private void HandleSpinPointerEvent(MotionEvent event)
	{
		float x = event.getX();
		float y = event.getY();
    
		switch (event.getAction()) 
		{
        	case MotionEvent.ACTION_DOWN:
        		TouchStart(x, y);
        		break;
        	case MotionEvent.ACTION_MOVE:
        		TouchMove(x, y);
        		break;
        	case MotionEvent.ACTION_UP:
        	case MotionEvent.ACTION_CANCEL:
        		TouchStop(x, y);
        		break;
		}	
	}

	private void TouchStart(float x, float y)
	{
		if(m_GameLobby == null)
			return;
		
	    if(IsSystemOnHold() == true)
	        return;
	    
	    if(GetGameState() != GameConstants.GAME_STATE_READY)
	        return;
	     
	    if(m_GameLobby.m_nPlayerTurnIndex == -1)
	    {
	    	m_GameLobby.UpdateOffLineGamePlayTurn();
	    }
	    
	    if(m_GameLobby.IsMyTurnOffline() == true || Configuration.isRoPaAutoBet() == false)
	    {
	    	m_GameLobby.m_ptTouchStartX = x;
	    	m_GameLobby.m_ptTouchStartY = y;
	    	m_GameLobby.m_ptTouchEndX = x;
	    	m_GameLobby.m_ptTouchEndY = y;
	    	m_GameLobby.m_timeTouchStart = SystemClock.currentThreadTimeMillis();
	        return;
	    }
	
	}

	private void TouchMove(float x, float y)
	{
		if(m_GameLobby == null)
			return;

	    if(IsSystemOnHold() == true)
	        return;
	    
	    if(GetGameState() != GameConstants.GAME_STATE_READY)
	        return;
		
	    if(m_GameLobby.IsMyTurnOffline() == true || Configuration.isRoPaAutoBet() == false)
	    {
	    	m_GameLobby.m_ptTouchEndX = x;
	    	m_GameLobby.m_ptTouchEndY = y;
	        return;
	    }
	}

	private void TouchStop(float x, float y)
	{
		if(m_GameLobby == null)
			return;

		
	    if(IsSystemOnHold() == true)
	        return;
	    
	    if(GetGameState() != GameConstants.GAME_STATE_READY)
	    {	
	    	HandleNonSpinTouchEvent();
	        return;
	    }
	    
	    if(m_GameLobby.IsMyTurnOffline() == true || Configuration.isRoPaAutoBet() == false)
	    {
	    	m_GameLobby.m_ptTouchEndX = x;
	    	m_GameLobby.m_ptTouchEndY = y;
		    long nEndTime = SystemClock.currentThreadTimeMillis();
	        float time = ((float)(nEndTime - m_GameLobby.m_timeTouchStart))/1000.0f;
	        CPinActionLevel action = new CPinActionLevel();
	        action.CreateLevel(m_GameLobby.m_ptTouchStartX, m_GameLobby.m_ptTouchStartY, m_GameLobby.m_ptTouchEndX, m_GameLobby.m_ptTouchEndY, time);
	        
	        SimpleGambleWheel.m_ApplicationController.SpinGambleWheel(action);
	    }    
	}

    public void PlayerFinishPledge(int nSeat, int nLuckNumber, int nBetMoney)
    {
        if(m_GameLobby != null)
        	m_GameLobby.OfflinePlayerFinishPledge(nSeat, nLuckNumber, nBetMoney);
    }
	
    public void UpdateForGameStateChange()
    {
        if(m_GameLobby != null && m_GameLobby.GetGameState() == GameConstants.GAME_STATE_RESULT)
        {
        	//Calculate total pledge and winners' number
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

            if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsEnabled() == true)
            {
            	if(0 <= nWinChips && m_GameLobby.m_Player0.GetPlayBetLuckNumber() == nWinNumber)
                {    
            		m_GameLobby.m_Player0.AddMoneyToPacket(nWinChips);
            		m_GameLobby.m_Player0.SetPlayResult(true); 
                    ScoreRecord.SetPlayerLastPlayResult(nWinChips, 0);
                }    
                else
                {
                	m_GameLobby.m_Player0.SetPlayResult(false);
                     int nBet = m_GameLobby.m_Player0.GetPlayBet()*(-1);
                     ScoreRecord.SetPlayerLastPlayResult(nBet, 0);
                }
                int nChip = m_GameLobby.m_Player0.GetPacketBalance();
                ScoreRecord.SetPlayerChipBalance(nChip, 0);
                m_GameLobby.m_Player0.GameStateChange();
            }
            
            if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsEnabled() == true)
            {
            	if(0 <= nWinChips && m_GameLobby.m_Player1.GetPlayBetLuckNumber() == nWinNumber)
                {    
            		m_GameLobby.m_Player1.AddMoneyToPacket(nWinChips);
            		m_GameLobby.m_Player1.SetPlayResult(true); 
                    ScoreRecord.SetPlayerLastPlayResult(nWinChips, 1);
                }    
                else
                {
                	m_GameLobby.m_Player1.SetPlayResult(false);
                     int nBet = m_GameLobby.m_Player1.GetPlayBet()*(-1);
                     ScoreRecord.SetPlayerLastPlayResult(nBet, 1);
                }
                int nChip = m_GameLobby.m_Player1.GetPacketBalance();
                ScoreRecord.SetPlayerChipBalance(nChip, 1);
                m_GameLobby.m_Player1.GameStateChange();
            }
           
            if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsEnabled() == true)
            {
            	if(0 <= nWinChips && m_GameLobby.m_Player2.GetPlayBetLuckNumber() == nWinNumber)
                {    
            		m_GameLobby.m_Player2.AddMoneyToPacket(nWinChips);
            		m_GameLobby.m_Player2.SetPlayResult(true); 
                    ScoreRecord.SetPlayerLastPlayResult(nWinChips, 2);
                }    
                else
                {
                	m_GameLobby.m_Player2.SetPlayResult(false);
                     int nBet = m_GameLobby.m_Player2.GetPlayBet()*(-1);
                     ScoreRecord.SetPlayerLastPlayResult(nBet, 2);
                }
                int nChip = m_GameLobby.m_Player2.GetPacketBalance();
                ScoreRecord.SetPlayerChipBalance(nChip, 2);
                m_GameLobby.m_Player2.GameStateChange();
            }

            if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsEnabled() == true)
            {
            	if(0 <= nWinChips && m_GameLobby.m_Player3.GetPlayBetLuckNumber() == nWinNumber)
                {    
            		m_GameLobby.m_Player3.AddMoneyToPacket(nWinChips);
            		m_GameLobby.m_Player3.SetPlayResult(true);
                    ScoreRecord.SetPlayerLastPlayResult(nWinChips, 3);
                }    
                else
                {
                	m_GameLobby.m_Player3.SetPlayResult(false); 
                     int nBet = m_GameLobby.m_Player3.GetPlayBet()*(-1);
                     ScoreRecord.SetPlayerLastPlayResult(nBet, 3);
                }
                int nChip = m_GameLobby.m_Player3.GetPacketBalance();
                ScoreRecord.SetPlayerChipBalance(nChip, 3);
                m_GameLobby.m_Player3.GameStateChange();
            }
            
            ScoreRecord.SaveScore();
            return;
          
            
        }
        else if(m_GameLobby != null && m_GameLobby.GetGameState() == GameConstants.GAME_STATE_RESET)
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
        
    	if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsActivated() == true)
    		m_GameLobby.m_Player0.GameStateChange();
    	if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsActivated() == true)
    		m_GameLobby.m_Player1.GameStateChange();
    	if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsActivated() == true)
    		m_GameLobby.m_Player2.GameStateChange();
    	if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsActivated() == true)
    		m_GameLobby.m_Player3.GameStateChange();
    }

    public void PlayerTranfereChipsFrom(int nSeat, int nReceiverID,  int nChips)
    {
        if(m_GameLobby != null)
       	m_GameLobby.OfflinePlayerTranfereChipsFrom(nSeat, nReceiverID,  nChips);
    }

}
