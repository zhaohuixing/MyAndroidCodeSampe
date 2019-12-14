package com.xgadget.SimpleGambleWheel;

import android.view.MotionEvent;

public class GamePlayGroupBase 
{
	public CGameLobby		m_GameLobby;
	
	public GamePlayGroupBase() 
	{
		// TODO Auto-generated constructor stub
		m_GameLobby = null;
	}
	
	public int GetCurrentActivePlayingSeat()
	{
		int nRet = -1;
		if(this.m_GameLobby != null)
			nRet = this.m_GameLobby.GetCurrentActivePlayingSeat();
	    return nRet;
	}
	
	public boolean GetOnlineGroup()
	{
		return false;
	}
	
	public void StartGameSection()
	{
		
	}
	
	public void ShutdownSection()
	{
	    if(m_GameLobby == null)
	        return;
        
	    
	    if(m_GameLobby.m_Player0 != null)
	    {
	    	m_GameLobby.m_Player0.HideMenu();
	    	m_GameLobby.m_Player0.ClearPlayBet();
	    	m_GameLobby.m_Player0.Activate(false);
	    	m_GameLobby.m_Player0.ShowAndHidePlayer(true);
	    	m_GameLobby.m_Player0.RestoreCachedTransferChip();
	    }
	    if(m_GameLobby.m_Player1 != null)
	    {	
	    	m_GameLobby.m_Player1.HideMenu();
	    	m_GameLobby.m_Player1.ClearPlayBet();
	    	m_GameLobby.m_Player1.Activate(false);
	    	m_GameLobby.m_Player1.ShowAndHidePlayer(true);
	    	m_GameLobby.m_Player1.RestoreCachedTransferChip();
	    }
	    
	    if(m_GameLobby.m_Player2 != null)
	    {	
	    	m_GameLobby.m_Player2.HideMenu();
	    	m_GameLobby.m_Player2.ClearPlayBet();
	    	m_GameLobby.m_Player2.Activate(false);
	    	m_GameLobby.m_Player2.ShowAndHidePlayer(true);
	    	m_GameLobby.m_Player2.RestoreCachedTransferChip();
	    }

	    if(m_GameLobby.m_Player3 != null)
	    {	
	    	m_GameLobby.m_Player3.HideMenu();
	    	m_GameLobby.m_Player3.ClearPlayBet();
	    	m_GameLobby.m_Player3.Activate(false);
	    	m_GameLobby.m_Player3.ShowAndHidePlayer(true);
	    	m_GameLobby.m_Player3.RestoreCachedTransferChip();
	    }
	}
	
	public int GetActivatedPlayerCount()
	{
	    int nRet = 0;
	    if(m_GameLobby.m_Player0 != null && m_GameLobby.m_Player0.IsActivated() == true)
	    {	
	    	++nRet;
	    }
	    if(m_GameLobby.m_Player1 != null && m_GameLobby.m_Player1.IsActivated() == true)
	    {	
	    	++nRet;
	    }
	    
	    if(m_GameLobby.m_Player2 != null && m_GameLobby.m_Player2.IsActivated() == true)
	    {	
	    	++nRet;
	    }

	    if(m_GameLobby.m_Player3 != null && m_GameLobby.m_Player3.IsActivated() == true)
	    {	
	    	++nRet;
	    }
	    
	    return nRet;
	}
	
	public int GetMyCurrentMoney()
	{
		int nRet = -1;
	    return nRet;
	}

	public void AddMoneyToMyAccount(int nChips)
	{
	}

	public int GetPlayerCurrentMoney(int nSeat)
	{
		int nRet = -1;
	    return nRet;
	}

	public void AddMoneyToPlayerAccount(int nChips,  int nSeat)
	{
	}

	public void CancelPendPlayerBet()
	{
	}

	public int GetGameType()
	{
		int nRet = GameConstants.GAME_TYPE_8LUCK;
		if(m_GameLobby != null)
			nRet = m_GameLobby.GetGameType(); 
	    return nRet;
	}

    public void SetGameState(int nState)
    {
    	if(m_GameLobby != null)
    	{
    		m_GameLobby.SetGameState(nState);
    	}
    }

    public GamePlayer GetMyself()
    {
    	GamePlayer pMyself = null;
    	
    	if(this.m_GameLobby != null)
    		pMyself = this.m_GameLobby.GetMyself();
    	
    	return pMyself;
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
    	{
    		m_GameLobby.SetSystemOnHold(bOnHold);
    	}
	}

	public boolean IsSystemOnHold()
	{
		boolean bRet = false;
		
		if(m_GameLobby != null)
		{
			bRet = m_GameLobby.IsSystemOnHold();
		}
	    return bRet;
	}
	
	public void RestoreOffLineReadyState()
	{
	}

	public boolean CanPlayerPlayGame(int nType, int nSeatID)
	{
		return false;
	}
	
	public void ForceClosePlayerMenus()
	{
		
	}
	
    public void PauseGame()
    {
    }

    public void ResumeGame()
    {
    }

	public void OnTimerEvent()
	{
		
	}
	
	public boolean HandleTouchEvent(MotionEvent evnet)
	{
		return false;
	}
	
	public GamePlayer GetPlayer(int nSeatID)
	{
		GamePlayer player = null;
	
		if(m_GameLobby != null)
		{
			player = m_GameLobby.GetPlayer(nSeatID);
		}
		
		return player;
	}
	
	public String GetPlayerName(int nSeatID)
	{
		String strRet = null;
		if(m_GameLobby != null)
			strRet = m_GameLobby.GetPlayerName(nSeatID);
	    return strRet;
	}

	
    public void PlayerFinishPledge(int nSeat, int nLuckNumber, int nBetMoney)
    {
    }

    public void UpdateGameUI()
    {
		if(m_GameLobby != null)
		{
			m_GameLobby.UpdateGameUI();
		}
    }    
    
    public void UpdateForGameStateChange()
    {
    	
    }
    
    public void PlayerTranfereChipsFrom(int nSeat, int nReceiverID,  int nChips)
    {
    }

}
