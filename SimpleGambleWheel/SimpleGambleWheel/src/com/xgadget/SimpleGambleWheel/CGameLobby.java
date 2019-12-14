package com.xgadget.SimpleGambleWheel;

//import android.view.MotionEvent;
//import android.os.SystemClock;

public class CGameLobby implements GameStateDelegate
{
    public	GamePlayer 					m_Player0;
    public	GamePlayer 					m_Player1;
    public	GamePlayer 					m_Player2;
    public	GamePlayer 					m_Player3;
	
    private GameCashBalanceView			m_CashMachine;
	private GambleWheelGadget			m_GambleWheel;
	private GamblePlaySession 			m_GameSession;
	private ActivePlayerAnimator		m_ActivePlayerIndicator; 
	
	private boolean						m_bOnHold;
	//????
    public float                       	m_ptTouchStartX;
    public float                       	m_ptTouchStartY;
    public float                       	m_ptTouchEndX;
    public float                       	m_ptTouchEndY;
    public long                       	m_timeTouchStart;

	//????
    public int 							m_nPlayerTurnIndex;
    public boolean                    	m_bInOffLineReadyPlay;
    public long          				m_OffLineReadyPlayTime;
    public long          				m_OffLineResetToReadyDelay;
	
    public void PauseGame()
    {
    	if(this.m_GambleWheel != null)
    	{
    		this.m_GambleWheel.Stop();
    	}
    }
    
    public void PlayCurrentGameStateSound()
    {
    	if(Configuration.canPlaySound() == true && this.m_GameSession != null)
    	{
    		this.m_GameSession.PlayCurrentGameStateSound();
    	}
    }
    
	public CGameLobby()
	{
		m_ActivePlayerIndicator = null;
		m_CashMachine = null;
		m_GambleWheel = new GambleWheelGadget(); 
		m_GambleWheel.RegisterDelegate(this);
		m_GameSession = new GamblePlaySession();
		
	    m_Player0 = new GamePlayer();
	    m_Player1 = new GamePlayer();
	    m_Player2 = new GamePlayer();
	    m_Player3 = new GamePlayer();

	    m_Player0.m_nSeatID = 0;
	    m_Player1.m_nSeatID = 1;
	    m_Player2.m_nSeatID = 2;
	    m_Player3.m_nSeatID = 3;

	    m_Player0.m_bMyself = true;
	    m_Player1.m_bMyself = false;
	    m_Player2.m_bMyself = false;
	    m_Player3.m_bMyself = false;

	    m_Player0.RegisterDelegate(this);
	    m_Player1.RegisterDelegate(this);
	    m_Player2.RegisterDelegate(this);
	    m_Player3.RegisterDelegate(this);
	    
	    m_bOnHold = false;
	
	    m_nPlayerTurnIndex = -1;
	    m_bInOffLineReadyPlay = false;
	    
	    //?????
		//?????
        m_OffLineResetToReadyDelay = GameConstants.OFFLINE_PLAYER_TURN_DELAY;
	
		//?????
		//?????
	}
	
	public void AttachCashMachine(GameCashBalanceView	cashView)
	{
		m_CashMachine = cashView;
		//m_CashMachine.AttachParent(this);
	}
	
	public void AttachWheel(CCompassRender	wheel)
	{
		if(m_GambleWheel != null)
		{	
			m_GambleWheel.AttachWheel(wheel);
			m_GambleWheel.SetGameType(m_GameSession.GetGameType());
		}	
	}
	
	public void AttachPointer(CPinRender	pointer)
	{
		if(m_GambleWheel != null)
		{	
			m_GambleWheel.AttachPointer(pointer);
		}	
	}

	public void AttachPlayer0Layout(GamePlayerLayout player0layout)
	{
		if(m_Player0 != null)
			m_Player0.AttachLayout(player0layout);
	}
	
	public void AttachPlayer1Layout(GamePlayerLayout player1layout)
	{
		if(m_Player1 != null)
			m_Player1.AttachLayout(player1layout);
	}

	public void AttachPlayer2Layout(GamePlayerLayout player2layout)
	{
		if(m_Player2 != null)
			m_Player2.AttachLayout(player2layout);
	}

	public void AttachPlayer3Layout(GamePlayerLayout player3layout)
	{
		if(m_Player3 != null)
			m_Player3.AttachLayout(player3layout);
	}
	
	public void AttachActivePlayerIndicator(ActivePlayerAnimator indicator)
	{
		m_ActivePlayerIndicator = indicator;
		if(m_ActivePlayerIndicator != null)
			m_ActivePlayerIndicator.MoveToActivePlayerPosition();
	}
	
    public void SpinGambleWheel(CPinActionLevel action)
    {
		if(m_GambleWheel != null)
		{
			m_GambleWheel.StartSpin(action);
		}
    }	
	
    
    
	public void OnTimerEvent()
	{
		if(m_GambleWheel != null)
		{
			m_GambleWheel.OnTimerEvent();
		}
		if(m_Player0 != null)
			m_Player0.OnTimerEvent();
		if(m_Player1 != null)
			m_Player1.OnTimerEvent();
		if(m_Player2 != null)
			m_Player2.OnTimerEvent();
		if(m_Player3 != null)
			m_Player3.OnTimerEvent();
		if(m_ActivePlayerIndicator != null)
			m_ActivePlayerIndicator.OnTimerEvent();
	}
	
	public void UpdateGameLayout()
	{
	    /*for(int i = 0; i < 4; ++i)
	    {
	        if(m_Players[i])
	        {
	            [m_Players[i] UpdateLayout];
	        }
	    }*/
		
	    this.LocatePlayingSpinner();
	}
	
	public void LocatePlayingSpinner()
	{
		UpdateGameUI();
	}

	public void InitializeDefaultOfflinePlayersConfiguration()
	{

        String sText = StringFactory.GetString_OfflineMySelfID();
        m_Player0.m_bAcitvated = true;
        m_Player0.SetPlayerID(sText);
        m_Player0.SetPlayerName(sText);
        
        
        String sText1 = StringFactory.GetString_OfflinePlayer1ID();
        m_Player1.m_bAcitvated = true;
        m_Player1.SetPlayerID(sText1);
        m_Player1.SetPlayerName(sText1);
        
        String sText2 = StringFactory.GetString_OfflinePlayer2ID();
        m_Player2.m_bAcitvated = true;
        m_Player2.SetPlayerID(sText2);
        m_Player2.SetPlayerName(sText2);
        
        String sText3 = StringFactory.GetString_OfflinePlayer3ID();
        m_Player3.m_bAcitvated = true;
        m_Player3.SetPlayerID(sText3);
        m_Player3.SetPlayerName(sText3);
        
        OfflinePlayersLoadMoney();
        SimpleGambleWheel.m_ApplicationController.SetGameState(GameConstants.GAME_STATE_RESET);
        m_nPlayerTurnIndex = -1;
        LocatePlayingSpinner();
        
        //[m_PlayingSpinner StartAnimation];
		
	}
	
	public void OfflinePlayersLoadMoney()
	{
	     if(ScoreRecord.GetSavedRecord() == 0)
	     {
	    	 m_Player0.SetupPacket(GameConstants.GAME_DEFAULT_CHIPSINPACKET);
	    	 m_Player1.SetupPacket(GameConstants.GAME_DEFAULT_CHIPSINPACKET);
	    	 m_Player2.SetupPacket(GameConstants.GAME_DEFAULT_CHIPSINPACKET);
	    	 m_Player3.SetupPacket(GameConstants.GAME_DEFAULT_CHIPSINPACKET);
	             
	         ScoreRecord.SetMyChipBalance(GameConstants.GAME_DEFAULT_CHIPSINPACKET);
	         ScoreRecord.SetPlayer1ChipBalance(GameConstants.GAME_DEFAULT_CHIPSINPACKET);
	         ScoreRecord.SetPlayer2ChipBalance(GameConstants.GAME_DEFAULT_CHIPSINPACKET);
	         ScoreRecord.SetPlayer3ChipBalance(GameConstants.GAME_DEFAULT_CHIPSINPACKET);
	     }
	     else
	     {
	         int nChips = ScoreRecord.GetMyChipBalance();
	         m_Player0.SetupPacket(nChips);
	         nChips = ScoreRecord.GetPlayer1ChipBalance();
	         m_Player1.SetupPacket(nChips);
	         nChips = ScoreRecord.GetPlayer2ChipBalance();
	         m_Player2.SetupPacket(nChips);
	         nChips = ScoreRecord.GetPlayer3ChipBalance();
	         m_Player3.SetupPacket(nChips);
	     }
	     m_Player0.UpdateCurrentGamePlayablity();
	     m_Player1.UpdateCurrentGamePlayablity();
	     m_Player2.UpdateCurrentGamePlayablity();
	     m_Player3.UpdateCurrentGamePlayablity();
	}
	
	public GamePlayer GetPlayer(int nSeatID)
	{
		GamePlayer pPlayer = null;
		if(m_Player0 != null && m_Player0.m_nSeatID == nSeatID)
			pPlayer = m_Player0;
		else if(m_Player1 != null && m_Player1.m_nSeatID == nSeatID)
			pPlayer = m_Player1;
		else if(m_Player2 != null && m_Player2.m_nSeatID == nSeatID)
			pPlayer = m_Player2;
		else if(m_Player3 != null && m_Player3.m_nSeatID == nSeatID)
			pPlayer = m_Player3;
	    return pPlayer;
	}

	public GamePlayer GetPlayerAtSeat(int nSeatID)
	{
	    return this.GetPlayer(nSeatID);
	}
	
	public GamePlayer GetMyself()
	{
		GamePlayer myself = null;
		
		if(m_Player0 != null && m_Player0.m_bMyself == true)
			myself = m_Player0;
		else if(m_Player1 != null && m_Player1.m_bMyself == true)
			myself = m_Player1;
		else if(m_Player2 != null && m_Player2.m_bMyself == true)
			myself = m_Player2;
		else if(m_Player3 != null && m_Player3.m_bMyself == true)
			myself = m_Player3;
		
		return myself;
	}
	
    @Override
	public void SetGameType(int nType)
    {
        boolean bNeedUpdate = (m_GameSession.GetGameType() != nType);
        m_GameSession.SetGameType(nType);
        if(bNeedUpdate)
        {
            //[[CGameSectionManager GetGlobalGameUIDelegate] UpdateForGameTypeChange];
    		if(m_GambleWheel != null)
    		{	
    			m_GambleWheel.SetGameType(m_GameSession.GetGameType());
    		}	
        }
    }
    
    public void SetThemeType(int nTheme)
    {
		if(m_GambleWheel != null)
		{	
			m_GambleWheel.SetGameTheme(nTheme);
		}	
    }

    
    @Override
	public int GetGameType()
    {
        return m_GameSession.GetGameType();
    }
    
    @Override
	public void SetGameState(int nState)
    {
        boolean bNeedUpdate = (m_GameSession.GetGameState() != nState);
        if(bNeedUpdate)
        {
            m_GameSession.SetGameState(nState);
        	SimpleGambleWheel.m_ApplicationController.SetGameState(nState);
        } 
    }
    
    @Override
	public int GetGameState()
	{
        return m_GameSession.GetGameState();
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
	        //[self PostMyOnlineGameBalance];
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

	public void OfflineAddMoneyToPlayerAccount(int nChips,  int nSeat)
	{
	    GamePlayer player = this.GetPlayer(nSeat);
	    if(player != null && player.IsActivated() == true)
	    {
	        player.AddMoneyToPacket(nChips);
	        int nMoney = player.GetPacketBalance();
	        if(nSeat == 1)
	            ScoreRecord.SetPlayer1ChipBalance(nMoney);
	        if(nSeat == 2)
	        	ScoreRecord.SetPlayer2ChipBalance(nMoney);
	        if(nSeat == 3)
	        	ScoreRecord.SetPlayer3ChipBalance(nMoney);
	        else 
	        	ScoreRecord.SetMyChipBalance(nMoney);
	        
	        if(player.IsEnabled() == false)
	            player.UpdateCurrentGamePlayablity();
	    }
	}

	public String GetPlayerName(int nSeat)
	{
		String strRet = "";
		
	    GamePlayer player = this.GetPlayer(nSeat);
	    if(player != null)
	    {
	    	strRet = player.GetPlayerName();
	    }
		
	    return strRet;
	}

	public void OfflineCancelPendPlayerBet()
	{
	    int nState = GetGameState();
	    
	    if(nState == GameConstants.GAME_STATE_RUN || nState == GameConstants.GAME_STATE_READY || nState == GameConstants.GAME_STATE_RESET)
	    {    
	        if(m_Player0 != null && m_Player0.IsEnabled())
	        {
	            m_Player0.CancelPendingBet();
	        }
	        if(m_Player1 != null && m_Player1.IsEnabled())
	        {
	            m_Player1.CancelPendingBet();
	        }
	        if(m_Player2 != null && m_Player2.IsEnabled())
	        {
	            m_Player2.CancelPendingBet();
	        }
	        if(m_Player3 != null && m_Player3.IsEnabled())
	        {
	            m_Player3.CancelPendingBet();
	        }
	    }
	    else if(nState == GameConstants.GAME_STATE_RESULT) 
	    {
	        if(m_Player0 != null && m_Player0.IsEnabled())
	        {
	        	m_Player0.ClearPlayBet();
	        }	
	        if(m_Player1 != null && m_Player1.IsEnabled())
	        {
	        	m_Player1.ClearPlayBet();
	        }	
	        if(m_Player2 != null && m_Player2.IsEnabled())
	        {
	        	m_Player2.ClearPlayBet();
	        }	
	        if(m_Player3 != null && m_Player3.IsEnabled())
	        {
	        	m_Player3.ClearPlayBet();
	        }	
	    }
	    SimpleGambleWheel.m_ApplicationController.SetGameState(GameConstants.GAME_STATE_RESET);
	}

	public void SetSystemOnHold(boolean bOnHold)
	{
		m_bOnHold = bOnHold;
	}

	public boolean IsSystemOnHold()
	{
	    return m_bOnHold;
	}
	
	public void RestoreOffLineReadyState()
	{
	    if(IsSystemOnHold() == true)
	    {
	        SetSystemOnHold(false);   
	    }
	    
	    if(Configuration.isDirty() == true)
	    {
	        int nType = Configuration.getCurrentGameType();
	        SetGameType(nType);
	        int nTheme = Configuration.getCurrentThemeType();
	        SetThemeType(nTheme);
	        
	        UpdatePlayersCurrentGamePlayablity();
	        Configuration.resetDirty();
	    }
	    SimpleGambleWheel.m_ApplicationController.SetGameState(GameConstants.GAME_STATE_RESET);
	}

	public void UpdatePlayersCurrentGamePlayablity()
	{
        if(m_Player0 != null && m_Player0.IsActivated())
        {
        	m_Player0.UpdateCurrentGamePlayablity();
        }	
        if(m_Player1 != null && m_Player1.IsActivated())
        {
        	m_Player1.UpdateCurrentGamePlayablity();
        }	
        if(m_Player2 != null && m_Player2.IsActivated())
        {
        	m_Player2.UpdateCurrentGamePlayablity();
        }	
        if(m_Player3 != null && m_Player3.IsActivated())
        {
        	m_Player3.UpdateCurrentGamePlayablity();
        }	
        SimpleGambleWheel.m_ApplicationController.UpdateGameUI();
	}
	
	public boolean CanPlayerPlayGame(int nType, int nSeatID)
	{
	    boolean bRet = false;
	    
	    GamePlayer player = this.GetPlayer(nSeatID);
	    
	    if(player != null && (player.CanPlayGame(nType) == true || player.IsEnabled() == true))
	    {
	    	bRet = true;
	    }
	    return bRet;	
	}
    
    @Override
	public void PointerStopAt(int nAngle)
    {
    	m_GameSession.PointerStopAt(nAngle);
    
    /*//Debugcode
    int nPosition = [self GetPointerPosition];
    int nWinNumber = [self GetWinScopeIndex]+1;
    NSLog(@"Pointer angle:%i, Wining #:%i", nPosition, nWinNumber);
  */
    }
    
    @Override
	public int GetPointerPosition()
    {
    	return m_GameSession.GetPointerPosition();
    }
    
    @Override
	public int GetWinScopeIndex()
    {
    	return m_GameSession.GetWinScopeIndex();
    }
	
    public void UpdateOfflineOtherPlayersAutoPledge(boolean bChangeState)
    {
        int nBetMoney;
        int nChoiceNumber;
        int nType = GetGameType();
        
        if(m_Player1 != null && m_Player1.IsActivated() == true && m_Player1.AlreadyMadePledge() == false)
        {
        	m_Player1.UpdateCurrentGamePlayablity();
            if(m_Player1.IsEnabled() == true)
            {    
            	nBetMoney = m_Player1.GenerateAutoOffLineBetPledge(nType);
                nChoiceNumber = m_Player1.GenerateAutoOffLineBetLuckNumber(nType);
                m_Player1.MakePlayBet(nChoiceNumber, nBetMoney);
            }
        }
        if(m_Player2 != null && m_Player2.IsActivated() == true && m_Player2.AlreadyMadePledge() == false)
        {
        	m_Player2.UpdateCurrentGamePlayablity();
            if(m_Player2.IsEnabled() == true)
            {    
            	nBetMoney = m_Player2.GenerateAutoOffLineBetPledge(nType);
                nChoiceNumber = m_Player2.GenerateAutoOffLineBetLuckNumber(nType);
                m_Player2.MakePlayBet(nChoiceNumber, nBetMoney);
            }
        }
        if(m_Player3 != null && m_Player3.IsActivated() == true && m_Player3.AlreadyMadePledge() == false)
        {
        	m_Player3.UpdateCurrentGamePlayablity();
            if(m_Player3.IsEnabled() == true)
            {    
            	nBetMoney = m_Player3.GenerateAutoOffLineBetPledge(nType);
                nChoiceNumber = m_Player3.GenerateAutoOffLineBetLuckNumber(nType);
                m_Player3.MakePlayBet(nChoiceNumber, nBetMoney);
            }
        }
        if(bChangeState == true)
        {    
            ShowPlayersPledgeInformation();
            SimpleGambleWheel.m_ApplicationController.SetGameState(GameConstants.GAME_STATE_READY);
            UpdateOffLineGamePlayTurn();
        }    
    }
    
    public void OfflinePlayersPledgeDone()
    {
    	ShowPlayersPledgeInformation();
        SimpleGambleWheel.m_ApplicationController.SetGameState(GameConstants.GAME_STATE_READY);
        UpdateOffLineGamePlayTurn();
    }
    
    public void ShowPlayersPledgeInformation()
    {
        if(m_Player0 != null && m_Player0.IsActivated() == true && m_Player0.IsEnabled() == true)
        {
            m_Player0.ShowPlayBet();
        }
        if(m_Player1 != null && m_Player1.IsActivated() == true && m_Player1.IsEnabled() == true)
        {
            m_Player1.ShowPlayBet();
        }
        if(m_Player2 != null && m_Player2.IsActivated() == true && m_Player2.IsEnabled() == true)
        {
            m_Player2.ShowPlayBet();
        }
        if(m_Player3 != null && m_Player3.IsActivated() == true && m_Player3.IsEnabled() == true)
        {
            m_Player3.ShowPlayBet();
        }
    }
    
    public void UpdateOffLineGamePlayTurn()
    {
        m_nPlayerTurnIndex = GetNextSequencePlayTurn(m_nPlayerTurnIndex);
        SetOffLinePlayTurn();
        LocatePlayingSpinner();
        if(Configuration.isRoPaAutoBet() == true)
        {    
            if(0 < m_nPlayerTurnIndex)
            {    
                m_OffLineReadyPlayTime = CApplicationController.GetSystemTimerClick();
                m_bInOffLineReadyPlay = true;
            }    
        }
    }

    public boolean PlayerCanPlay(int nIndex)
    {
        int nType = GetGameType();
        boolean bRet = CanPlayerPlayGame(nType, nIndex);
        return bRet;
    }
    
    public int GetNextSequencePlayTurn(int nCurrentTurn)
    {
        if(nCurrentTurn < 0)
            return 0;
        
        int nIndex = nCurrentTurn;
        for(int i = 0; i < 4; ++i)
        {
            nIndex = (nIndex+1)%4;
            if(PlayerCanPlay(nIndex) == true)
            {
                return nIndex;
            }
        }
        return nIndex;
    } 
    
    public boolean AllOfflinePlayersMakePledge()
    {
        boolean bRet = false;
        
        if(m_Player0 != null && m_Player0.IsEnabled() == true)
        {
            if(m_Player0.AlreadyMadePledge() == false)
            	return false;
            else 
            	bRet = true;
        }
        if(m_Player1 != null && m_Player1.IsEnabled() == true)
        {
            if(m_Player1.AlreadyMadePledge() == false)
            	return false;
            else 
            	bRet = true;
        }
        if(m_Player2 != null && m_Player2.IsEnabled() == true)
        {
            if(m_Player2.AlreadyMadePledge() == false)
            	return false;
            else 
            	bRet = true;
        }
        if(m_Player3 != null && m_Player3.IsEnabled() == true)
        {
            if(m_Player3.AlreadyMadePledge() == false)
            	return false;
            else 
            	bRet = true;
        }
        
        
        return bRet;
    }

    public void StartOffLinePlayerAction()
    {
        if(0 < m_nPlayerTurnIndex)
        {
            m_bInOffLineReadyPlay = false;
            CPinActionLevel action = new CPinActionLevel();
            action.CreateRandomLevel();
            SimpleGambleWheel.m_ApplicationController.SpinGambleWheel(action);
        }
    }


    public void SetOffLinePlayTurn()
    {
        if(m_Player0 != null && m_Player0.IsEnabled() == true)
        {
        	if(0 == m_nPlayerTurnIndex)
        		m_Player0.SetMyTurnFlag(true);
            else 
        		m_Player0.SetMyTurnFlag(false);
        }
        if(m_Player1 != null && m_Player1.IsEnabled() == true)
        {
        	if(1 == m_nPlayerTurnIndex)
        		m_Player1.SetMyTurnFlag(true);
            else 
        		m_Player1.SetMyTurnFlag(false);
        }
        if(m_Player2 != null && m_Player2.IsEnabled() == true)
        {
        	if(2 == m_nPlayerTurnIndex)
        		m_Player2.SetMyTurnFlag(true);
            else 
        		m_Player2.SetMyTurnFlag(false);
        }
        if(m_Player3 != null && m_Player3.IsEnabled() == true)
        {
        	if(3 == m_nPlayerTurnIndex)
        		m_Player3.SetMyTurnFlag(true);
            else 
        		m_Player3.SetMyTurnFlag(false);
        }
        
        m_OffLineResetToReadyDelay = GameConstants.OFFLINE_PLAYER_TURN_DELAY;
    }
    
    public boolean IsMyTurnOffline()
    {
        boolean bRet = (m_nPlayerTurnIndex == 0);
        return bRet;
    }
    
	public int GetCurrentActivePlayingSeat()
	{
	    return m_nPlayerTurnIndex;
	}
    
    public void ForceClosePlayerMenus()
    {
    	
    }
    
    public void SavePlayersOfflineStateInformation()
    {
        int nType = GetGameType();
        ScoreRecord.SetGameType(nType);
        ScoreRecord.SetThemeType(Configuration.getCurrentThemeType());
        
        ScoreRecord.SetSoundEnable(Configuration.canPlaySound());
        ScoreRecord.SetPlayTurnType(Configuration.getPlayTurnType());
        if(Configuration.isRoPaAutoBet() == true)
            ScoreRecord.SetOfflineBetMethod(0);
        else
            ScoreRecord.SetOfflineBetMethod(1);
        
        int nChips;
        
        if(m_Player0 != null)
        {
            nChips = m_Player0.GetPacketBalance();
            ScoreRecord.SetPlayerChipBalance(nChips,0);
        }
        if(m_Player1 != null)
        {
            nChips = m_Player1.GetPacketBalance();
            ScoreRecord.SetPlayerChipBalance(nChips,1);
        }
        if(m_Player2 != null)
        {
            nChips = m_Player2.GetPacketBalance();
            ScoreRecord.SetPlayerChipBalance(nChips,2);
        }
        if(m_Player3 != null)
        {
            nChips = m_Player3.GetPacketBalance();
            ScoreRecord.SetPlayerChipBalance(nChips,3);
        }
        
        ScoreRecord.SaveScore();
    }
    
    public void OfflinePlayerFinishPledge(int nSeat, int nLuckNumber, int nBetMoney)
    {
        int nCount = 4;
        if(nSeat == 0 && m_Player0 != null && m_Player0.IsActivated() == true)
        {
        	m_Player0.MakePlayBet(nLuckNumber, nBetMoney);
            //m_bMyselfMakeBet = NO;
            if(Configuration.isRoPaAutoBet() == true)
            {
                UpdateOfflineOtherPlayersAutoPledge(true);
                return;
            }
            else
            {
                CheckOfflineNextPlayerManualPledge((nSeat+1)%nCount);
                return;
            }
        }
        else if(nSeat == 1 && m_Player1 != null && m_Player1.IsActivated() == true)
        {
            if(Configuration.isRoPaAutoBet() == false)
            {
            	m_Player1.MakePlayBet(nLuckNumber, nBetMoney);
                CheckOfflineNextPlayerManualPledge((nSeat+1)%nCount);
                return;
            }
        }
        else if(nSeat == 2 && m_Player2 != null && m_Player2.IsActivated() == true)
        {
            if(Configuration.isRoPaAutoBet() == false)
            {
            	m_Player2.MakePlayBet(nLuckNumber, nBetMoney);
                CheckOfflineNextPlayerManualPledge((nSeat+1)%nCount);
                return;
            }
        }
        else if(nSeat == 3 && m_Player3 != null && m_Player3.IsActivated() == true)
        {
            if(Configuration.isRoPaAutoBet() == false)
            {
                m_Player3.MakePlayBet(nLuckNumber, nBetMoney);
                OfflinePlayersPledgeDone();
                return;
            }
        }
    }
    
    public void CheckOfflineNextPlayerManualPledge(int nSeat)
    {
        int nCount = 4;
        if(0 <= nSeat && nSeat < nCount-1)
        {
        	GamePlayer player = this.GetPlayer(nSeat);
            if(player != null && ((player.IsEnabled() == true && player.AlreadyMadePledge() == true) || (player.IsEnabled() == false && nSeat != 0)))
            {
                CheckOfflineNextPlayerManualPledge((nSeat+1)%nSeat);
                return;
            }
        }
        else if(nSeat == nCount-1)
        {
            if(m_Player3 != null && ((m_Player3.IsEnabled() == true && m_Player3.AlreadyMadePledge() == true) || (m_Player3 == null || m_Player3.IsEnabled() == false)))
            {
                OfflinePlayersPledgeDone();
                return;
            }
        }
    }
   
    public void UpdateGameUI()
    {
    	if(m_ActivePlayerIndicator != null)
    		m_ActivePlayerIndicator.MoveToActivePlayerPosition();
    }
    
    public void ReloadPlayerMenus()
    {
        if(m_Player0 != null)
        {
        	m_Player0.ReloadMenus();
        }
        if(m_Player1 != null)
        {
        	m_Player1.ReloadMenus();
        }
        if(m_Player2 != null)
        {
        	m_Player2.ReloadMenus();
        }
        if(m_Player3 != null)
        {
        	m_Player3.ReloadMenus();
        }
    }
    
    public void OfflinePlayerTranfereChipsFrom(int nSeat, int nReceiverID, int nChips)
    {
        if(this.IsSystemOnHold() == true)
        {
            SetSystemOnHold(false);   
        }
        if(nSeat < 0 || 3 < nSeat || nReceiverID < 0 || 3 < nReceiverID || nChips == 0)
            return;
        
        OfflineAddMoneyToPlayerAccount(-nChips, nSeat);
        OfflineAddMoneyToPlayerAccount(nChips, nReceiverID);
        UpdatePlayersCurrentGamePlayablity();
    }
    
    public void ForceUpdatePlayersUI()
    {
        if(m_Player0 != null)
        {
        	m_Player0.ForceUpdatePlayersUI();
        }
        if(m_Player1 != null)
        {
        	m_Player1.ForceUpdatePlayersUI();
        }
        if(m_Player2 != null)
        {
        	m_Player2.ForceUpdatePlayersUI();
        }
        if(m_Player3 != null)
        {
        	m_Player3.ForceUpdatePlayersUI();
        }
    }
}
