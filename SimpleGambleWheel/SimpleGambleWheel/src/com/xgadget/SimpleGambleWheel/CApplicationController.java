package com.xgadget.SimpleGambleWheel;

import java.util.List;

import com.xgadget.XSGService.XSGAPIGameServiceManager;
import com.xgadget.XSGService.XSGAPINetworking;
import com.xgadget.XSGService.XSGAPIReleaseConfigure;
import com.xgadget.XSGService.XSGAPIScoreBoardIntScore;
import com.xgadget.XSGService.XSGAPIUser;
import com.xgadget.uimodule.CustomBitmapButton;
import com.xgadget.uimodule.ResourceHelper;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
//import android.widget.ViewFlipper;
import android.os.Handler;
import android.os.SystemClock; 

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class CApplicationController 
{
	public static long 			g_SystemTimerClick = 0;
	
	public SimpleGambleWheel 				m_CurrentActivity;
	public CGameController 					m_GameController;
	public CPlayerMakeBetController			m_PlayerBetViewController;
	public CScoreViewController				m_ScoreViewController;
	public CPlayerOfflineTransferChipsController		m_PlayerOfflineTransferController;
	public COnlineSetupController			m_OnlineSetupController;
	public COnlinePlayerListController			m_OnlinePlayerListController;
	//public CInAppPurchaseItemListController		m_InAppPruchaseItemListController;
	public CXSGAPIScoreboardController			m_XSGAPIScoreboardController;
	
	
	
	private Handler 				m_TimerHandler;
	private Runnable 				m_UpdateTimeTask;

	private static final int  		TIMER_INTERVAL_MS = 10;
    private long					m_TimingCount;	
    
    private int						m_ActiveViewIndex; //The index of current display view in ViewFlipper
    private boolean 				m_bNeedStartNewGame;
	
    private boolean					m_bInActivityConfigureChange;
    
    //private boolean					m_bReceivedMessageBoardOpenned;
    private long					m_nReceivedMessageBoardStart;
    private long					m_nStatusBarStart;
    
    
    public static long GetSystemTimerClick()
    {
    	return g_SystemTimerClick;
    }
    
    public void SetCurrentActivity(SimpleGambleWheel 	currentActivity)
    {
    	m_CurrentActivity = currentActivity;
    	UpdateGameUI();
    }
    
    public void SetAcitivityConfigureChangeFlag()
    {
    	m_bInActivityConfigureChange = true;
    	XSGAPIReleaseConfigure.LogDebugInfo("CApplicationController.SetAcitivityConfigureChangeFlag()", "true");
    }

    public void ClearAcitivityConfigureChangeFlag()
    {
    	m_bInActivityConfigureChange = false;
    	XSGAPIReleaseConfigure.LogDebugInfo("CApplicationController.ClearAcitivityConfigureChangeFlag()", "false");
    }

    public boolean IsActivityConfigureChange()
    {
    	return m_bInActivityConfigureChange;
    }
    
    public void HandleOnlineGameProcessByActivityConfigureChangeFlag()
    {
    	XSGAPIReleaseConfigure.LogDebugInfo("CApplicationController.HandleOnlineGameProcessByActivityConfigureChangeFlag()", "Handle online game by flag.....");
    }
    
    public void InitializeGameConfiguration()
    {
        ScoreRecord.IntiScore();
        int nType = ScoreRecord.GetGameType();
        int nTheme = ScoreRecord.GetThemeType();
        boolean bAuto = (ScoreRecord.GetOfflineBetMethod() == 0);
        boolean bSound = ScoreRecord.GetSoundEnable();
        Configuration.setCurrentGameType(nType);
        Configuration.setCurrentThemeType(nTheme);
        Configuration.setRoPaAutoBet(bAuto);
        Configuration.setPlaySoundEffect(bSound);
        Configuration.setPlayTurn(ScoreRecord.GetPlayTurnType());
    }
    
	public CApplicationController() 
	{
		// TODO Auto-generated constructor stub
		CApplicationController.g_SystemTimerClick = SystemClock.uptimeMillis();
		m_GameController = new CGameController(); 
		m_PlayerBetViewController = new CPlayerMakeBetController();
		m_ScoreViewController = new CScoreViewController();
		m_PlayerOfflineTransferController = new CPlayerOfflineTransferChipsController();
		m_OnlineSetupController = new COnlineSetupController();
		m_OnlinePlayerListController = new COnlinePlayerListController();
//		m_InAppPruchaseItemListController = new CInAppPurchaseItemListController();
		m_XSGAPIScoreboardController = new CXSGAPIScoreboardController();			
		
		m_CurrentActivity = null;
		m_TimerHandler = new Handler();
		m_UpdateTimeTask = null;
		//m_Timer = new GameThreadTimer(this);
		m_TimingCount = SystemClock.uptimeMillis();
		InitializeGameConfiguration();
		StartTimer();
		m_ActiveViewIndex = 0;
		m_bNeedStartNewGame = true;
		m_bInActivityConfigureChange = false;
	    m_nReceivedMessageBoardStart = 0;
	    m_nStatusBarStart = 0;
	}
	
	public int GetWinScopeIndex()
    {
    	return m_GameController.GetWinScopeIndex();
    }
	
	private void InitializeOnlineConfiguration()
	{
		if(m_GameController != null && m_CurrentActivity != null /*&& XSGAPINetworking.isNetworkAvailable(m_CurrentActivity) == true*/)
		{
			//this.m_GameController.CheckAndUpdateOnlineSetting();
			this.m_GameController.CheckAndUpdateOnlineSettingWithoutAlert();
		}
	}
	
	private void ReconfigureOnlineConfigure()
	{
		if(m_GameController != null && m_CurrentActivity != null/* && XSGAPINetworking.isNetworkAvailable(m_CurrentActivity) == true*/)
		{
			this.m_GameController.ReconfigureOnlineConfigure();
		}
	}
	
	private void ForceUpdatePlayersUI()
	{
		if(m_GameController != null)
		{
			this.m_GameController.ForceUpdatePlayersUI();
		}
	}
	
	public void StartApplication()
	{
		ForceUpdatePlayersUI();
		if(m_bNeedStartNewGame == false)
		{	
			ReconfigureOnlineConfigure();
			this.UpdateGameUI();
			return;
		}	
		else
		{
			InitializeOnlineConfiguration();
		}
		this.UpdateGameUI();
		ForceStartNewGame();
		
	}
	
	public void ForceStartNewGame()
	{
		if(m_GameController != null)
		{
			m_GameController.StartGameSection();
			m_bNeedStartNewGame = false;
			Configuration.resetDirty();
		}
	}
	
	private boolean IsInGamePlayMode()
	{
		//??????
		//??????
		return true;
	}
	
	public boolean HandleTouchEvent(MotionEvent event)
	{
		boolean bRet = true;
		
		if(IsInGamePlayMode() == true && m_GameController != null)
		{
			bRet = m_GameController.HandleTouchEvent(event);
		}
		
		return bRet;
	}
	
	
	public void StartTimer()
	{
		//m_Timer.StartTimer();
		m_UpdateTimeTask = new Runnable() 
		{
			public void run() 
			{
				if(m_TimerHandler != null)
				{	
					m_TimerHandler.postDelayed(m_UpdateTimeTask, TIMER_INTERVAL_MS);
					timerUpdate();
				}					
			}
		};	
		
		m_TimerHandler.removeCallbacks(m_UpdateTimeTask);
		m_TimerHandler.postDelayed(m_UpdateTimeTask, TIMER_INTERVAL_MS);
	}
	
	private void timerUpdate() 
	{
		CApplicationController.g_SystemTimerClick = SystemClock.uptimeMillis();
		if(GameConstants.m_GameTimerElapse <= CApplicationController.g_SystemTimerClick - m_TimingCount)
		{
			m_TimingCount = CApplicationController.g_SystemTimerClick;
			OnTimerEvent();
		}
	}
	
	public void StopTimer()
	{
		//m_Timer.StopTimer();
		
	}

	public void AttachCashMachine(GameCashBalanceView	cashView)
	{
		m_GameController.AttachCashMachine(cashView);
	}
	
	public void AttachGameView(GameView gameView)
	{
		m_GameController.AttachGameView(gameView);
	}
	
	public void AttachWheel(CCompassRender	wheel)
	{
		if(m_GameController != null)
			m_GameController.AttachWheel(wheel);
	}
	
	public void AttachPointer(CPinRender pointer)
	{
		if(m_GameController != null)
			m_GameController.AttachPointer(pointer);
	}
	
	public void AttachPlayer0Layout(GamePlayerLayout player0layout)
	{
		if(m_GameController != null)
			m_GameController.AttachPlayer0Layout(player0layout);
	}
	
	public void AttachPlayer1Layout(GamePlayerLayout player1layout)
	{
		if(m_GameController != null)
			m_GameController.AttachPlayer1Layout(player1layout);
	}

	public void AttachPlayer2Layout(GamePlayerLayout player2layout)
	{
		if(m_GameController != null)
			m_GameController.AttachPlayer2Layout(player2layout);
	}

	public void AttachPlayer3Layout(GamePlayerLayout player3layout)
	{
		if(m_GameController != null)
			m_GameController.AttachPlayer3Layout(player3layout);
	}
	
	public void AttachActivePlayerIndicator(ActivePlayerAnimator indicator)
	{
		if(m_GameController != null)
			m_GameController.AttachActivePlayerIndicator(indicator);
	}
	
	public void AttachNumberPicker(PlayerBetLuckyNumberPicker	picker)
	{
		if(m_PlayerBetViewController != null)
			m_PlayerBetViewController.AttachNumberPicker(picker);
	}

	public void AttachChipsPicker(PlayerBetChipsPicker	picker)
	{
		if(m_PlayerBetViewController != null)
			m_PlayerBetViewController.AttachChipsPicker(picker);
	}
	
	public void AttachPlayerHeadImage(PlayerPledgeHeadView headImage)
	{
		if(m_PlayerBetViewController != null)
			m_PlayerBetViewController.AttachPlayerHeadImage(headImage);
	}

	public void AttachPlayerNameTag(TextView nameTag)
	{
		if(m_PlayerBetViewController != null)
			m_PlayerBetViewController.AttachPlayerNameTag(nameTag);
	}
	
	public void AttachPlayerBetViewCloseButton(CustomBitmapButton closeBtn)
	{
		if(m_PlayerBetViewController != null)
		{
			closeBtn.SetBitmap(ResourceHelper.GetCloseButtonBitmap());
			m_PlayerBetViewController.AttachCloseButton(closeBtn);
		}	
	}
	
	public void OnTimerEvent()
	{
		if(m_GameController != null)
		{
			m_GameController.OnTimerEvent();
		}
		if(this.m_OnlinePlayerListController != null)
		{
			this.m_OnlinePlayerListController.OnTimerEvent();
		}
		//if(this.m_XSGAPIScoreboardController != null)
		//{
		//	this.m_XSGAPIScoreboardController.OnTimerEvent();
		//}
		
		long currentTime = CApplicationController.GetSystemTimerClick();
		if(this.m_CurrentActivity.m_RecievedPlayerMessaeBoard != null && this.m_CurrentActivity.m_RecievedPlayerMessaeBoard.IsShown() == true)
		{
			 long nTimeStamps = currentTime - this.m_nReceivedMessageBoardStart;
			 XSGAPIReleaseConfigure.LogDebugInfo("CApplicationController.OnTimerEvent MessageBoard dispaly", String.valueOf(nTimeStamps));
			 if(GameConstants.m_PlayerMenuDisplayTimerElapse <= nTimeStamps)
			 {
				 this.CloseRecievedOnlinePlayerMessageBoard();
			 }
		}
		if(this.m_CurrentActivity.m_StatusBar != null && this.m_CurrentActivity.m_StatusBar.IsShown() == true)
		{
			 long nTimeStamps = currentTime - this.m_nStatusBarStart;
			 XSGAPIReleaseConfigure.LogDebugInfo("CApplicationController.OnTimerEvent Status bar dispaly", String.valueOf(nTimeStamps));
			 if(GameConstants.m_PlayerMenuDisplayTimerElapse <= nTimeStamps)
			 {
				 this.CloseStatusBar();
			 }
		}
		
	}
	
	public int GetGameState()
	{
		int nRet = GameConstants.GAME_STATE_READY;
		
		if(m_GameController != null)
		{
			nRet = m_GameController.GetGameState();
		}
		return nRet;
	}
	
    public void SetGameState(int nState)
    {
    	if(m_GameController != null)
    	{
    		m_GameController.SetGameState(nState);
    	}
    	//UpdateGameUI();
    	UpdateForGameStateChange();
    }
    
    public void SetGameType(int nType)
    {
    	if(m_GameController != null)
    	{
    		m_GameController.SetGameType(nType);
    	}
    	UpdateForGameStateChange();
    }

    public void SetThemeType(int nType)
    {
    	if(m_GameController != null)
    	{
    		m_GameController.SetThemeType(nType);
    	}
    	UpdateForGameStateChange();
    }
    
	public void UpdateForGameStateChange()
	{
    	if(m_GameController != null)
    	{
    		m_GameController.UpdateForGameStateChange();
    	}
    	UpdateGameUI();
	}
	
	
    public void UpdateGameUI()
    {
    	if(this.m_CurrentActivity != null && this.m_GameController != null && this.m_CurrentActivity.m_CashMachine != null)
    	{
    		int number = this.m_GameController.GetMyCurrentMoney();
    		this.m_CurrentActivity.m_CashMachine.SetCashBalance(number);
    	}
    	if(this.m_GameController != null)
    	{
    		this.m_GameController.UpdateGameUI();
    	}
    	boolean bShow = (this.GetGameState() == GameConstants.GAME_STATE_READY || this.GetGameState() == GameConstants.GAME_STATE_RESET);
    	this.ShowAndHideGameViewSystemButton(bShow);
    }

    public void UpdateMenuViewState()
    {
    	UpdateSoundButtonDisplay();
/*    	AdView banner2 = (AdView)this.m_CurrentActivity.findViewById(R.id.banner_adview1);
    	if(banner2 != null)
    	{
    	    AdRequest adRequest = new AdRequest.Builder().build();
    	    banner2.loadAd(adRequest);
    	}
*/    	
    	//	banner2.loadAds();
 /*   	MobclixMMABannerXLAdView banner2 = (MobclixMMABannerXLAdView)this.m_CurrentActivity.findViewById(R.id.banner_adview1);
    	if(banner2 != null)
    	{	
    		banner2.getAd();
    	}	
*/
    }
    
    public void InvalidateMenuView()
    {
    	int nIndex = GetMenuViewIndexInViewGroup();
        if(nIndex == m_ActiveViewIndex)
        {
    		View menuView = (View)this.m_CurrentActivity.findViewById(R.id.MainMenuViewGroup);
    		this.m_CurrentActivity.m_ScreenController.bringChildToFront(menuView);
    		menuView.setVisibility(View.VISIBLE);
        	this.m_CurrentActivity.m_MenuLayoutContainer.invalidate();
        }
    }
    
    public void OpenMenuView()
    {
    	int nIndex = GetMenuViewIndexInViewGroup();
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null && 0 <= nIndex)
    	{
    		m_ActiveViewIndex = nIndex;
        	m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_lefttoright);
        	m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_lefttoright);
        	m_CurrentActivity.m_ScreenController.setDisplayedChild(nIndex);
        	UpdateMenuViewState();
			//SoundSource.StopAllPlayingSound();

        	//????????
        	//SetSystemOnHold(true);
    	}
    }
    
    public void CloseMenuViewAndStartNewGame()
    {
    	int nIndex = GetGameMainPlayViewIndexInViewGroup();
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null && 0 <= nIndex)
    	{
    		m_ActiveViewIndex = nIndex;
        	m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_righttoleft);
        	m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_righttoleft);
        	m_CurrentActivity.m_ScreenController.setDisplayedChild(nIndex);
        	m_CurrentActivity.m_GameLayoutContainer.SetLayout(0, 0, GameLayoutConstant.GetCurrentScreenWidth(), GameLayoutConstant.GetCurrentScreenHeight());
        	this.m_GameController.ResetGameStateAndType();
        	ForceStartNewGame();
        	//????????
        	//SetSystemOnHold(false);
    	}	
    }

    public void ShiftToMainView()
    {
    	int nIndex = GetGameMainPlayViewIndexInViewGroup();
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null && 0 <= nIndex)
    	{
    		if(nIndex != m_ActiveViewIndex)
    		{	
    			m_ActiveViewIndex = nIndex;
    			m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_righttoleft);
    			m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_righttoleft);
    			m_CurrentActivity.m_ScreenController.setDisplayedChild(nIndex);
    		}	
    		m_CurrentActivity.m_GameLayoutContainer.SetLayout(0, 0, GameLayoutConstant.GetCurrentScreenWidth(), GameLayoutConstant.GetCurrentScreenHeight());
        	this.m_GameController.ResetGameStateAndType();
    	}
    }
   
    public void GotoGameMainView()
    {
    	int nIndex = GetGameMainPlayViewIndexInViewGroup();
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null && 0 <= nIndex)
    	{
    		m_ActiveViewIndex = nIndex;
        	m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_righttoleft);
        	m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_righttoleft);
        	m_CurrentActivity.m_ScreenController.setDisplayedChild(nIndex);
        	m_CurrentActivity.m_GameLayoutContainer.SetLayout(0, 0, GameLayoutConstant.GetCurrentScreenWidth(), GameLayoutConstant.GetCurrentScreenHeight());
        	this.m_GameController.ResetGameStateAndType();
        	if(Configuration.isDirty() == true)
        	{	
        		ForceStartNewGame();        	
        	}	
        	//????????
        	//SetSystemOnHold(false);
    	}
    }
    
    public void OpenConfigureGameModeSubView(boolean bLeftRoRight)
    {
    	int nIndex = GetConfigureGameModeSubViewIndexInViewGroup();
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null && 0 <= nIndex)
    	{
    		m_ActiveViewIndex = nIndex;
    		if(bLeftRoRight == true)
    		{	
    			m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_lefttoright);
    			m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_lefttoright);
    		}
    		else
    		{
    			m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_righttoleft);
    			m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_righttoleft);
    		}
        	m_CurrentActivity.m_ScreenController.setDisplayedChild(nIndex);
        	m_CurrentActivity.m_GameModePicker.LoadCurrentConfiguration();
        	//????????
        	//SetSystemOnHold(false);
    	}
    }
    
    public void OpenConfigureOnlinePlayMethodSubView(boolean bLeftRoRight)
    {
    	int nIndex = GetConfigureOnlinePlayMethodSubViewIndexInViewGroup();
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null && 0 <= nIndex)
    	{
    		m_ActiveViewIndex = nIndex;
    		if(bLeftRoRight == true)
    		{	
    			m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_lefttoright);
    			m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_lefttoright);
    		}
    		else
    		{
    			m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_righttoleft);
    			m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_righttoleft);
    		}
        	m_CurrentActivity.m_ScreenController.setDisplayedChild(nIndex);
        	m_CurrentActivity.m_OnlinePlayMethodPicker.LoadCurrentConfiguration();
        	//????????
        	//SetSystemOnHold(false);
    	}
    }
    
    public void OpenConfigurePledgeTypeSubView(boolean bLeftRoRight)
    {
    	int nIndex = GetConfigurePledgeTypeSubViewIndexInViewGroup();
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null && 0 <= nIndex)
    	{
    		m_ActiveViewIndex = nIndex;
    		if(bLeftRoRight == true)
    		{	
    			m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_lefttoright);
    			m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_lefttoright);
    		}
    		else
    		{
    			m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_righttoleft);
    			m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_righttoleft);
    		}
        	m_CurrentActivity.m_ScreenController.setDisplayedChild(nIndex);
        	m_CurrentActivity.m_ConfigurePledgeTypePicker.LoadCurrentConfiguration();
    	}
    }
    
    public void OpenGameThemeConfigureSubView(boolean bLeftRoRight)
    {
    	int nIndex = GetGameThemeConfigureSubViewIndexInViewGroup();
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null && 0 <= nIndex)
    	{
    		m_ActiveViewIndex = nIndex;
    		if(bLeftRoRight == true)
    		{	
    			m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_lefttoright);
    			m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_lefttoright);
    		}
    		else
    		{
    			m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_righttoleft);
    			m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_righttoleft);
    		}
        	m_CurrentActivity.m_ScreenController.setDisplayedChild(nIndex);
        	m_CurrentActivity.m_GameThemeTypePicker.LoadCurrentConfiguration();
    	}
    }
    
    public void OpenConfigureOfflineBetMethodSubView()
    {
    	int nIndex = GetConfigureOfflineBetMethodSubViewIndexInViewGroup();
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null && 0 <= nIndex)
    	{
    		m_ActiveViewIndex = nIndex;
    		m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_lefttoright);
    		m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_lefttoright);
        	m_CurrentActivity.m_ScreenController.setDisplayedChild(nIndex);
        	m_CurrentActivity.m_OfflineBetMethodPicker.LoadCurrentConfiguration();
        	//????????
        	//SetSystemOnHold(false);
    	}
    }
    
    public void OpenScoreSubView()
    {
    	int nIndex = GetScoreSubViewIndexInViewGroup();
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null && 0 <= nIndex)
    	{
    		m_ActiveViewIndex = nIndex;
    		m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_lefttoright);
    		m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_lefttoright);
        	m_CurrentActivity.m_ScreenController.setDisplayedChild(nIndex);
        	m_ScoreViewController.LoadPlayersScore();
        	//m_CurrentActivity.m_OfflineBetMethodPicker.LoadCurrentConfiguration();
        	//????????
        	//SetSystemOnHold(false);
    	}
    }

    public void OpenPlayerOfflineTransferChipsSubView(int nSenderPlayerID)
    {
    	if(this.m_GameController != null)
    		this.m_GameController.CancelPendPlayerBet();
    	int nIndex = GetPlayerOfflineTransferChipsIndexInViewGroup();
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null && 0 <= nIndex)
    	{
    		m_ActiveViewIndex = nIndex;
    		m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_lefttoright);
    		m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_lefttoright);
        	m_CurrentActivity.m_ScreenController.setDisplayedChild(nIndex);
        	m_PlayerOfflineTransferController.Open(nSenderPlayerID);
    	}
    }

    public void OpenPlayerOnlineTransferChipsSubView()
    {
    	if(this.m_GameController != null)
    		this.m_GameController.CancelPendPlayerBet();
    	int nIndex = GetPlayerOfflineTransferChipsIndexInViewGroup();
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null && 0 <= nIndex)
    	{
    		m_ActiveViewIndex = nIndex;
    		m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_lefttoright);
    		m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_lefttoright);
        	m_CurrentActivity.m_ScreenController.setDisplayedChild(nIndex);
        	m_PlayerOfflineTransferController.OpenOnlineTransferView();
    	}
    }
   
    public void OpenOnlineSetupSubView(boolean bLeftRoRight)
    {
    	int nIndex = GetOnlineSetupSubViewIndexInViewGroup();
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null && 0 <= nIndex)
    	{
    		m_ActiveViewIndex = nIndex;
    		if(bLeftRoRight == true)
    		{	
    			m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_lefttoright);
    			m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_lefttoright);
    		}
    		else
    		{
    			m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_righttoleft);
    			m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_righttoleft);
    		}
        	m_CurrentActivity.m_ScreenController.setDisplayedChild(nIndex);
    	}
    }
    
    public void HandleOpenlineSetupSubView(boolean fromMenuView)
    {
    	if(this.m_OnlineSetupController != null)
    	{
    		this.m_OnlineSetupController.OpenOnlineSetupView(fromMenuView);
    	}
    }
    
    public void OpenOnlinePlayerListView()
    {
    	int nIndex = GetOnlinePlayerListViewGroup();
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null && 0 <= nIndex)
    	{
    		m_ActiveViewIndex = nIndex;
   			m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_lefttoright);
   			m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_lefttoright);
        	m_CurrentActivity.m_ScreenController.setDisplayedChild(nIndex);
        	m_OnlinePlayerListController.LoadOnlinePlayerList();
        	//m_CurrentActivity.m_OnlinePlayMethodPicker.LoadCurrentConfiguration();
        	//????????
        	//SetSystemOnHold(false);
    	}
    }

    public void OpenInAppPruchaseListView(int nStoreID)
    {
/*    	int nIndex = GetInAppPruchaseListViewGroup();
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null && 0 <= nIndex)
    	{
    		m_ActiveViewIndex = nIndex;
   			m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_lefttoright);
   			m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_lefttoright);
        	m_CurrentActivity.m_ScreenController.setDisplayedChild(nIndex);
        	this.m_InAppPruchaseItemListController.LoadInAppPurchaseItemList(nStoreID);
    	}*/
    }
    
	public void OnClickEvent(View v)
	{
		if(m_CurrentActivity == null)
			return;
		
        if (v == m_CurrentActivity.m_SystemButton) 
        {
        	OpenMenuView();
        } 
        else if(v == m_CurrentActivity.m_MenuSystemButton)
        {
        	GotoGameMainView();
        }
        else if (v == m_CurrentActivity.m_OnlineCloseButton) 
        {
        	this.m_GameController.ShutdownGameSection();
        	m_CurrentActivity.m_OnlineCloseButton.setVisibility(View.GONE);
        	m_CurrentActivity.m_OnlineOpenButton.setVisibility(View.VISIBLE);
        }
        else if (v == m_CurrentActivity.m_OnlineOpenButton)
        {
        	this.HandleMainOnlineGameButtonClick();
        }
	}
	
    public void SpinGambleWheel(CPinActionLevel action)
    {
    	if(m_GameController != null)
    	{
    		m_GameController.SpinGambleWheel(action);
    	}
        //[pController ShowStatusBar:[StringFactory GetString_Spinning]];
    	
/*        [m_GameLobby SpinGambleWheel:action];
        if(m_ActiveGameSection)
            [m_ActiveGameSection ForceClosePlayerMenus];
        if([m_ActiveGameSection GetGameOnlineState] == GAME_ONLINE_STATE_ONLINE)
        {
            ApplicationController* pController = (ApplicationController*)[GUILayout GetMainViewController];
            [pController ShowStatusBar:[StringFactory GetString_Spinning]];
        }*/
    }
    
    private int GetPlayerPledgeViewIndexInViewGroup()
    {
    	int nRet = -1;
    	
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null)
    	{
    		View pledgeView = (View)this.m_CurrentActivity.findViewById(R.id.PlayerPledgeViewGroup);
    		int nCount =  this.m_CurrentActivity.m_ScreenController.getChildCount();
    		for(int i = 0; i < nCount; ++i)
    		{
    			View pChild = this.m_CurrentActivity.m_ScreenController.getChildAt(i);
    			if(pChild == pledgeView)
    				return i;
    		}
    	}
    	
    	return nRet;
    }
    
    public void MakePlayerManualPledge(int nPlayerSeat)
    {
    	int nIndex = GetPlayerPledgeViewIndexInViewGroup();
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null && 0 <= nIndex && m_PlayerBetViewController != null)
    	{
    		m_ActiveViewIndex = nIndex;
        	m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_lefttoright);
        	m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_lefttoright);
        	m_CurrentActivity.m_ScreenController.setDisplayedChild(nIndex);
        	m_PlayerBetViewController.Open(nPlayerSeat);
        	
    	}
    }
    
    public int GetMenuViewIndexInViewGroup()
    {
    	int nRet = -1;
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null)
    	{
    		View playView = (View)this.m_CurrentActivity.findViewById(R.id.MainMenuViewGroup);
    		int nCount =  this.m_CurrentActivity.m_ScreenController.getChildCount();
    		for(int i = 0; i < nCount; ++i)
    		{
    			View pChild = this.m_CurrentActivity.m_ScreenController.getChildAt(i);
    			if(pChild == playView)
    				return i;
    		}
    	}

    	return nRet;
    }
    
    private int GetGameMainPlayViewIndexInViewGroup()
    {
    	int nRet = -1;
    	
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null)
    	{
    		View playView = (View)this.m_CurrentActivity.findViewById(R.id.GameMainPlayingViewGroup);
    		int nCount =  this.m_CurrentActivity.m_ScreenController.getChildCount();
    		for(int i = 0; i < nCount; ++i)
    		{
    			View pChild = this.m_CurrentActivity.m_ScreenController.getChildAt(i);
    			if(pChild == playView)
    				return i;
    		}
    	}
    	
    	return nRet;
    }
    
    private int GetConfigureGameModeSubViewIndexInViewGroup()
    {
    	int nRet = -1;
    	
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null)
    	{
    		View playView = (View)this.m_CurrentActivity.findViewById(R.id.ConfigureGameModeViewGroup);
    		int nCount =  this.m_CurrentActivity.m_ScreenController.getChildCount();
    		for(int i = 0; i < nCount; ++i)
    		{
    			View pChild = this.m_CurrentActivity.m_ScreenController.getChildAt(i);
    			if(pChild == playView)
    				return i;
    		}
    	}
    	
    	return nRet;
    }
    
    private int GetConfigureOnlinePlayMethodSubViewIndexInViewGroup()
    {
    	int nRet = -1;
    	
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null)
    	{
    		View playView = (View)this.m_CurrentActivity.findViewById(R.id.OnlineConfigurePlayMethodViewGroup);
    		int nCount =  this.m_CurrentActivity.m_ScreenController.getChildCount();
    		for(int i = 0; i < nCount; ++i)
    		{
    			View pChild = this.m_CurrentActivity.m_ScreenController.getChildAt(i);
    			if(pChild == playView)
    				return i;
    		}
    	}
    	
    	return nRet;
    }
    
    private int GetConfigurePledgeTypeSubViewIndexInViewGroup()
    {
    	int nRet = -1;
    	
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null)
    	{
    		View playView = (View)this.m_CurrentActivity.findViewById(R.id.ConfigureGameTypeViewGroup);
    		int nCount =  this.m_CurrentActivity.m_ScreenController.getChildCount();
    		for(int i = 0; i < nCount; ++i)
    		{
    			View pChild = this.m_CurrentActivity.m_ScreenController.getChildAt(i);
    			if(pChild == playView)
    				return i;
    		}
    	}
    	
    	return nRet;
    }
    
    private int GetGameThemeConfigureSubViewIndexInViewGroup()
    {
    	int nRet = -1;
    	
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null)
    	{
    		View playView = (View)this.m_CurrentActivity.findViewById(R.id.GameThemeTypeSelectViewGroup);
    		int nCount =  this.m_CurrentActivity.m_ScreenController.getChildCount();
    		for(int i = 0; i < nCount; ++i)
    		{
    			View pChild = this.m_CurrentActivity.m_ScreenController.getChildAt(i);
    			if(pChild == playView)
    				return i;
    		}
    	}
    	
    	return nRet;
    }
    
    private int GetConfigureOfflineBetMethodSubViewIndexInViewGroup()
    {
    	int nRet = -1;
    	
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null)
    	{
    		View playView = (View)this.m_CurrentActivity.findViewById(R.id.OfflinePledgeTypeViewGroup);
    		int nCount =  this.m_CurrentActivity.m_ScreenController.getChildCount();
    		for(int i = 0; i < nCount; ++i)
    		{
    			View pChild = this.m_CurrentActivity.m_ScreenController.getChildAt(i);
    			if(pChild == playView)
    				return i;
    		}
    	}
    	
    	return nRet;
    }
    
    private int GetScoreSubViewIndexInViewGroup()
    {
    	int nRet = -1;
    	
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null)
    	{
    		View playView = (View)this.m_CurrentActivity.findViewById(R.id.ScoreViewGroup);
    		int nCount =  this.m_CurrentActivity.m_ScreenController.getChildCount();
    		for(int i = 0; i < nCount; ++i)
    		{
    			View pChild = this.m_CurrentActivity.m_ScreenController.getChildAt(i);
    			if(pChild == playView)
    				return i;
    		}
    	}
    	
    	return nRet;
    }	
    
    private int GetPlayerOfflineTransferChipsIndexInViewGroup()
    {
    	int nRet = -1;
    	
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null)
    	{
    		View playView = (View)this.m_CurrentActivity.findViewById(R.id.PlayerOfflineTransferChipViewGroup);
    		int nCount =  this.m_CurrentActivity.m_ScreenController.getChildCount();
    		for(int i = 0; i < nCount; ++i)
    		{
    			View pChild = this.m_CurrentActivity.m_ScreenController.getChildAt(i);
    			if(pChild == playView)
    				return i;
    		}
    	}
    	
    	return nRet;
    }
    
    private int GetOnlineSetupSubViewIndexInViewGroup()
    {
    	int nRet = -1;
    	
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null)
    	{
    		View playView = (View)this.m_CurrentActivity.findViewById(R.id.OnlineSetupViewGroup);
    		int nCount =  this.m_CurrentActivity.m_ScreenController.getChildCount();
    		for(int i = 0; i < nCount; ++i)
    		{
    			View pChild = this.m_CurrentActivity.m_ScreenController.getChildAt(i);
    			if(pChild == playView)
    				return i;
    		}
    	}
    	
    	return nRet;
    }
    
    private int GetOnlinePlayerListViewGroup()
    {
    	int nRet = -1;
    	
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null)
    	{
    		View playView = (View)this.m_CurrentActivity.findViewById(R.id.OnlinePlayersViewGroup);
    		int nCount =  this.m_CurrentActivity.m_ScreenController.getChildCount();
    		for(int i = 0; i < nCount; ++i)
    		{
    			View pChild = this.m_CurrentActivity.m_ScreenController.getChildAt(i);
    			if(pChild == playView)
    				return i;
    		}
    	}
    	
    	return nRet;
    }
    
    private int GetInAppPruchaseListViewGroup()
    {
    	int nRet = -1;
    	
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null)
    	{
    		View playView = (View)this.m_CurrentActivity.findViewById(R.id.InAppPurchaseViewGroup);
    		int nCount =  this.m_CurrentActivity.m_ScreenController.getChildCount();
    		for(int i = 0; i < nCount; ++i)
    		{
    			View pChild = this.m_CurrentActivity.m_ScreenController.getChildAt(i);
    			if(pChild == playView)
    				return i;
    		}
    	}
    	
    	return nRet;
    }
    
    private int GetXSGAPIScoreBoardViewIndexInViewGroup()
    {
    	int nRet = -1;
    	
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null)
    	{
    		View playView = (View)this.m_CurrentActivity.findViewById(R.id.XSGAPIScoreBoardViewGroup);
    		int nCount =  this.m_CurrentActivity.m_ScreenController.getChildCount();
    		for(int i = 0; i < nCount; ++i)
    		{
    			View pChild = this.m_CurrentActivity.m_ScreenController.getChildAt(i);
    			if(pChild == playView)
    				return i;
    		}
    	}
    	
    	return nRet;
    }
    
    public void HandlePlayerBetViewClose()
    {
    	int nIndex = GetGameMainPlayViewIndexInViewGroup();
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null && 0 <= nIndex && m_PlayerBetViewController != null)
    	{
    		m_ActiveViewIndex = nIndex;
        	m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_righttoleft);
        	m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_righttoleft);
        	m_CurrentActivity.m_ScreenController.setDisplayedChild(nIndex);
        	m_CurrentActivity.m_GameLayoutContainer.SetLayout(0, 0, GameLayoutConstant.GetCurrentScreenWidth(), GameLayoutConstant.GetCurrentScreenHeight());
        	if(this.m_GameController != null)
        	{	
        		m_GameController.PlayerFinishPledge(m_PlayerBetViewController.m_nWorkingPlayerSeatID, 
        											m_PlayerBetViewController.m_nSelectedNumber, 
        											m_PlayerBetViewController.m_nSelectedChips);
        	}
        
        	this.UpdateGameUI();
    	}
    }
    
    public void ShowAndHideGameViewSystemButton(boolean bShow)
    {
    	if(bShow == true)
    	{
    		if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_SystemButton != null)
    		{
    			this.m_CurrentActivity.m_SystemButton.setVisibility(View.VISIBLE);
    		}
    	}
    	else
    	{
    		if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_SystemButton != null)
    		{
    			this.m_CurrentActivity.m_SystemButton.setVisibility(View.GONE);
    		}
    	}
    }
    
    public void RestoreDisplayView()
    {
    	this.ClearAcitivityConfigureChangeFlag();

    	int nIndex = GetGameMainPlayViewIndexInViewGroup();
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null && 0 <= m_ActiveViewIndex)
    	{
        	m_CurrentActivity.m_ScreenController.setDisplayedChild(m_ActiveViewIndex);
    	}
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_GameLayoutContainer != null && this.m_GameController != null)
    	{
    		this.m_GameController.ReloadPlayerMenus();
    	}
    	
    	
    	if(nIndex == m_ActiveViewIndex || m_ActiveViewIndex < 0)
    	{	
        	m_CurrentActivity.m_GameLayoutContainer.SetLayout(0, 0, GameLayoutConstant.GetCurrentScreenWidth(), GameLayoutConstant.GetCurrentScreenHeight());
        	//?????????m_CurrentActivity.m_GameLayoutContainer.SetLayout(0, 0, GameLayoutConstant.GetCurrentScreenWidth(), GameLayoutConstant.GetCurrentScreenHeight());
    	}
    	nIndex = this.GetOnlinePlayerListViewGroup();
    	if(nIndex == m_ActiveViewIndex)
    	{
    		this.m_OnlinePlayerListController.ReloadCachedPlayerList();
    	}

    	nIndex = GetXSGAPIScoreBoardViewIndexInViewGroup();
    	if(nIndex == m_ActiveViewIndex)
    	{
    		this.m_XSGAPIScoreboardController.ReloadCachedScoreList();
    	}
    	
    	if(XSGAPIGameServiceManager.getXSGAPIGameManager().InOnlineGamePlay() == true)
    	{
    		this.OnlineRequestDone();
    	}
    	else
    	{
    		this.OnlineRequestCancelled();
    	}
    	this.SetThemeType(Configuration.getCurrentThemeType());
    	this.m_GameController.PlayCurrentGameStateSound();

    	this.UpdateGameUI();
    }
    
    public void PurchaseChips()
    {
    	//??????????????????
    	//??????????????????
    	//??????????????????
    	//??????????????????
    	//??????????????????
    }
    
    public void UpdateSoundConfigure()
    {
		boolean bEnable = Configuration.canPlaySound();
		if(bEnable == true)
		{
			Configuration.setPlaySoundEffect(false);
			ScoreRecord.SetSoundEnable(false);
			ScoreRecord.SaveScore();
			SoundSource.StopAllPlayingSound();
		}
		else
		{
			Configuration.setPlaySoundEffect(true);
			ScoreRecord.SetSoundEnable(true);
			ScoreRecord.SaveScore();
			this.m_GameController.PlayCurrentGameStateSound();
		}
		
    	UpdateSoundButtonDisplay();
    }
    
    public void UpdateSoundButtonDisplay()
    {
    	if(this.m_CurrentActivity.m_MenuItemSound != null)
    	{	
    		boolean bEnable = Configuration.canPlaySound();
    		if(bEnable == true)
    			this.m_CurrentActivity.m_MenuItemSound.SetBitmapID(R.drawable.muteicon);
    		else
    			this.m_CurrentActivity.m_MenuItemSound.SetBitmapID(R.drawable.musicicon);
    	}    			
    }
    public void RegisterConfigurationMenuHandler()
    {
    	if(this.m_CurrentActivity == null)
    		return;
    	if(this.m_CurrentActivity.m_MenuItemStartGame != null)
    	{
    		this.m_CurrentActivity.m_MenuItemStartGame.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					CloseMenuViewAndStartNewGame();
				}
			});
    	}
    	if(this.m_CurrentActivity.m_MenuItemConfigure != null)
    	{
       		this.m_CurrentActivity.m_MenuItemConfigure.setOnClickListener(new OnClickListener() 
    			{
    				@Override
    				public void onClick(View arg0) 
    				{
    					OpenConfigureGameModeSubView(true);
    				}
    			});
    	}
    	if(this.m_CurrentActivity.m_MenuItemOnlineSetup != null)
    	{
       		this.m_CurrentActivity.m_MenuItemOnlineSetup.setOnClickListener(new OnClickListener() 
    			{
    				@Override
    				public void onClick(View arg0) 
    				{
    					HandleOpenlineSetupSubView(true);	
    				}
    			});
    	}
    	if(this.m_CurrentActivity.m_MenuItemSound != null)
    	{
       		this.m_CurrentActivity.m_MenuItemSound.setOnClickListener(new OnClickListener() 
    			{
    				@Override
    				public void onClick(View arg0) 
    				{
    					UpdateSoundConfigure();	
    				}
    			});
    		
    	}
    	if(this.m_CurrentActivity.m_MenuItemScore != null)
    	{
    		this.m_CurrentActivity.m_MenuItemScore.setOnClickListener(new OnClickListener() 
    			{
    				@Override
    				public void onClick(View arg0) 
    				{
    					//OpenScoreSubView(); //?????
    					m_CurrentActivity.AskForOpenScoreViewOption();
    				}
    			});
    	}
    	if(this.m_CurrentActivity.m_MenuItemPost != null)
    	{
    		this.m_CurrentActivity.m_MenuItemPost.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
			    	if(XSGAPINetworking.isNetworkAvailable(m_CurrentActivity) == false)
			    	{
			    		m_CurrentActivity.ShowDefaultAlert(StringFactory.GetString_AskOnlineConnection());
			    		return;
			    	}
		    		//m_CurrentActivity.AskForGamePostOption();
		    		m_CurrentActivity.AskForGameLeaderBoardPostOption();
				}
			});
    	}
    	if(this.m_CurrentActivity.m_MenuItemPurchase != null)
    	{
    		this.m_CurrentActivity.m_MenuItemPurchase.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
		    		//m_CurrentActivity.ShowDefaultAlert(StringFactory.GetString_OnlinePurchaseReminder());
					m_CurrentActivity.AskInAppPurchaseOption();
					//OpenInAppPruchaseListView(InAppPurchaseConstants.GOOGLEPLAYID);
				}
			});
    	}
    }

    public void HandleGameModeViewNextButtonClicked()
    {
    	if(Configuration.isOnline() == true)
    	{
    		OpenConfigureOnlinePlayMethodSubView(true);
    	}
    	else
    	{
    		OpenConfigurePledgeTypeSubView(true);
    	}
    }
    
    public void HandleOGPMViewNextButtonClicked()
    {
		OpenConfigurePledgeTypeSubView(true);
    }

    public void HandleOGPMViewPrevButtonClicked()
    {
    	OpenConfigureGameModeSubView(false);
    }
    
    public void HandlePledgeTypeViewNextButtonClicked()
    {
    	/*if(Configuration.isOnline() == true)
    	{
    		ConfigureSubViewCloseButtonClicked();
    	}
    	else
    	{
    		OpenConfigureOfflineBetMethodSubView();
    	}*/
		OpenGameThemeConfigureSubView(true);
   	
    }
    
    public void HandlePledgeTypeViewPrevButtonClicked()
    {
    	if(Configuration.isOnline() == true)
    	{
    		OpenConfigureOnlinePlayMethodSubView(false);
    	}
    	else
    	{
    		OpenConfigureGameModeSubView(false);
    	}
    }
    
    public void HandleGameThemeNextButtonClicked()
    {
    	if(Configuration.isOnline() == true)
    	{
    		ConfigureSubViewCloseButtonClicked();
    	}
    	else
    	{
    		OpenConfigureOfflineBetMethodSubView();
    	}
    }
    
    public void ConfigureSubViewCloseButtonClicked()
    {
        int nIndex = GetMenuViewIndexInViewGroup();
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null && 0 <= nIndex)
    	{
    		m_ActiveViewIndex = nIndex;
        	m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_righttoleft);
        	m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_righttoleft);
        	m_CurrentActivity.m_ScreenController.setDisplayedChild(nIndex);
        	UpdateMenuViewState();
        	//????????
        	//SetSystemOnHold(true);
    	}
    	
    }
    
    public void RegisterConfigurationGameModeViewItemHandler()   
    {
    	if(this.m_CurrentActivity == null)
    		return;
    	if(this.m_CurrentActivity.m_GameModeConfigureViewNextButton != null)
    	{
    		this.m_CurrentActivity.m_GameModeConfigureViewNextButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					HandleGameModeViewNextButtonClicked();
				}
			});
    	}
    	if(this.m_CurrentActivity.m_GameModeConfigureViewCloseButton != null)
    	{
    		this.m_CurrentActivity.m_GameModeConfigureViewCloseButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					ConfigureSubViewCloseButtonClicked();
				}
			});
    	}
    }
    
    public void RegisterConfigurationOnlineGamePlayMethodViewItemHandler()
    {
    	if(this.m_CurrentActivity == null)
    		return;
    	
    	if(this.m_CurrentActivity.m_OnlinePlayMethodConfigureViewNextButton != null)
    	{
    		this.m_CurrentActivity.m_OnlinePlayMethodConfigureViewNextButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					HandleOGPMViewNextButtonClicked();
				}
			});
    	}
    	if(this.m_CurrentActivity.m_OnlinePlayMethodConfigureViewPrevButton != null)
    	{
    		this.m_CurrentActivity.m_OnlinePlayMethodConfigureViewPrevButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					HandleOGPMViewPrevButtonClicked();
				}
			});
    	}
    	if(this.m_CurrentActivity.m_OnlinePlayMethodConfigureViewCloseButton != null)
    	{
    		this.m_CurrentActivity.m_OnlinePlayMethodConfigureViewCloseButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					ConfigureSubViewCloseButtonClicked();
				}
			});
    	}
    }
    
    public void RegisterConfigurationPledgeTypeViewItemHandler()
    {
    	if(this.m_CurrentActivity == null)
    		return;
    	
    	if(this.m_CurrentActivity.m_ConfigurePledgeTypeViewNextButton != null)
    	{
    		this.m_CurrentActivity.m_ConfigurePledgeTypeViewNextButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					HandlePledgeTypeViewNextButtonClicked();
				}
			});
    	}
    	if(this.m_CurrentActivity.m_ConfigurePledgeTypeViewPrevButton != null)
    	{
    		this.m_CurrentActivity.m_ConfigurePledgeTypeViewPrevButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					HandlePledgeTypeViewPrevButtonClicked();
				}
			});
    	}
    	if(this.m_CurrentActivity.m_ConfigurePledgeTypeViewCloseButton != null)
    	{
    		this.m_CurrentActivity.m_ConfigurePledgeTypeViewCloseButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					ConfigureSubViewCloseButtonClicked();
				}
			});
    	}
    	this.m_CurrentActivity.m_ConfigurePledgeHeadViewMe.SetAsMyself(true);
    	this.m_CurrentActivity.m_ConfigurePledgeHeadViewRopa1.SetAsMyself(false);
    	this.m_CurrentActivity.m_ConfigurePledgeHeadViewRopa2.SetAsMyself(false);
    	this.m_CurrentActivity.m_ConfigurePledgeHeadViewRopa3.SetAsMyself(false);
    	
    }
    
    public void RegisterConfigurationOfflineBetMethodViewItemHandler()    
    {
    	if(this.m_CurrentActivity == null)
    		return;
    	
    	if(this.m_CurrentActivity.m_OfflineBetMethodConfigureViewNextButton != null)
    	{
    		this.m_CurrentActivity.m_OfflineBetMethodConfigureViewNextButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					ConfigureSubViewCloseButtonClicked();
				}
			});
    	}
    	if(this.m_CurrentActivity.m_OfflineBetMethodConfigureViewCloseButton != null)
    	{
    		this.m_CurrentActivity.m_OfflineBetMethodConfigureViewCloseButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					ConfigureSubViewCloseButtonClicked();
				}
			});
    	}
    	if(this.m_CurrentActivity.m_OfflineBetMethodConfigureViewPrevButton != null)
    	{
    		this.m_CurrentActivity.m_OfflineBetMethodConfigureViewPrevButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					//OpenConfigurePledgeTypeSubView(false);
					OpenGameThemeConfigureSubView(false);
				}
			});
    	}
    }

    public void RegisterGameThemeConfigureViewItemHandler()    
    {
    	if(this.m_CurrentActivity == null)
    		return;
    	
    	if(this.m_CurrentActivity.m_GameThemeConfigureViewNextButton != null)
    	{
    		this.m_CurrentActivity.m_GameThemeConfigureViewNextButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					HandleGameThemeNextButtonClicked();
				}
			});
    	}
    	if(this.m_CurrentActivity.m_GameThemeConfigureViewCloseButton != null)
    	{
    		this.m_CurrentActivity.m_GameThemeConfigureViewCloseButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					ConfigureSubViewCloseButtonClicked();
				}
			});
    	}
    	if(this.m_CurrentActivity.m_GameThemeConfigureViewPrevButton != null)
    	{
    		this.m_CurrentActivity.m_GameThemeConfigureViewPrevButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					OpenConfigurePledgeTypeSubView(false);
				}
			});
    	}
    }
    
    public void RegisterScoreViewItemHandler()
    {
    	if(this.m_CurrentActivity.m_ScoreViewCloseButton != null)
    	{
    		this.m_CurrentActivity.m_ScoreViewCloseButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					ConfigureSubViewCloseButtonClicked();
				}
			});
    	}
    }
    
    //For the closebutton on online setup view
    public void RegisterOnlineSetupViewItemHandler()
    {
    	if(this.m_OnlineSetupController != null)
    	{
    		this.m_OnlineSetupController.RegisterLayoutControls();
    	}
    }

    public void RegisterOnlinePlayerListViewItemHandler()
    {
    	if(this.m_OnlinePlayerListController != null)
    	{
    		this.m_OnlinePlayerListController.RegisterLayoutControls();
    	}
    }
    
    public void RegisterXSGAPIScoreboardViewItemHandler()
    {
    	if(this.m_CurrentActivity.m_ScoreBoardCloseButton != null)
    	{
    		this.m_CurrentActivity.m_ScoreBoardCloseButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					ConfigureSubViewCloseButtonClicked();
				}
			});
    	}
    }
    
    public void RegisterInAppPurchaseItemListItemHandler()
    {
/*    	if(this.m_OnlinePlayerListController != null)
    	{
    		this.m_InAppPruchaseItemListController.RegisterLayoutControls();
    	} */
    }
    
    public void UpdateConfigurePledgeHeadViewsState()
    {
    	if(this.m_CurrentActivity == null || this.m_GameController == null)
    		return;
    	
    	int nType = Configuration.getCurrentGameType();
    	if(Configuration.isOnline() == true)
    	{
    		if(this.m_CurrentActivity.m_ConfigurePledgeHeadViewMe != null)
    		{	
    			this.m_CurrentActivity.m_ConfigurePledgeHeadViewMe.setVisibility(View.VISIBLE);
            	//???????
            	int nChip = this.m_GameController.GetMyCurrentMoney();
            	this.m_CurrentActivity.m_ConfigurePledgeHeadViewMe.SetBalance(nChip);
            	boolean bEnable = this.m_GameController.CanPlayerPlayGame(nType, 0);
            	this.m_CurrentActivity.m_ConfigurePledgeHeadViewMe.SetEnable(bEnable);
    		}	
        	if(this.m_CurrentActivity.m_ConfigurePledgeHeadViewRopa1 != null)
        	{	
        		this.m_CurrentActivity.m_ConfigurePledgeHeadViewRopa1.setVisibility(View.GONE);
        	}	
        	if(this.m_CurrentActivity.m_ConfigurePledgeHeadViewRopa2 != null)
        	{	
        		this.m_CurrentActivity.m_ConfigurePledgeHeadViewRopa2.setVisibility(View.GONE);
        	}	
        	if(this.m_CurrentActivity.m_ConfigurePledgeHeadViewRopa3 != null)
        	{	
        		this.m_CurrentActivity.m_ConfigurePledgeHeadViewRopa3.setVisibility(View.GONE);
        	}	
    	}
    	else
    	{
    		int nChip;
    		boolean bEnable;
    		if(this.m_CurrentActivity.m_ConfigurePledgeHeadViewMe != null)
    		{	
    			this.m_CurrentActivity.m_ConfigurePledgeHeadViewMe.setVisibility(View.VISIBLE);
            	nChip = this.m_GameController.GetMyCurrentMoney();
            	this.m_CurrentActivity.m_ConfigurePledgeHeadViewMe.SetBalance(nChip);
            	bEnable = this.m_GameController.CanPlayerPlayGame(nType, 0);
            	this.m_CurrentActivity.m_ConfigurePledgeHeadViewMe.SetEnable(bEnable);
    		}	
        	if(this.m_CurrentActivity.m_ConfigurePledgeHeadViewRopa1 != null)
        	{	
        		this.m_CurrentActivity.m_ConfigurePledgeHeadViewRopa1.setVisibility(View.VISIBLE);
            	nChip = this.m_GameController.GetPlayerCurrentMoney(1);
            	this.m_CurrentActivity.m_ConfigurePledgeHeadViewRopa1.SetBalance(nChip);
            	bEnable = this.m_GameController.CanPlayerPlayGame(nType, 1);
            	this.m_CurrentActivity.m_ConfigurePledgeHeadViewRopa1.SetEnable(bEnable);
        	}
        	if(this.m_CurrentActivity.m_ConfigurePledgeHeadViewRopa1 != null)
        	{	
        		this.m_CurrentActivity.m_ConfigurePledgeHeadViewRopa2.setVisibility(View.VISIBLE);
            	nChip = this.m_GameController.GetPlayerCurrentMoney(2);
            	this.m_CurrentActivity.m_ConfigurePledgeHeadViewRopa2.SetBalance(nChip);
            	bEnable = this.m_GameController.CanPlayerPlayGame(nType, 2);
            	this.m_CurrentActivity.m_ConfigurePledgeHeadViewRopa2.SetEnable(bEnable);
        	}	
        	if(this.m_CurrentActivity.m_ConfigurePledgeHeadViewRopa1 != null)
        	{	
        		this.m_CurrentActivity.m_ConfigurePledgeHeadViewRopa3.setVisibility(View.VISIBLE);
        		nChip = this.m_GameController.GetPlayerCurrentMoney(3);
        		this.m_CurrentActivity.m_ConfigurePledgeHeadViewRopa3.SetBalance(nChip);
        		bEnable = this.m_GameController.CanPlayerPlayGame(nType, 3);
        		this.m_CurrentActivity.m_ConfigurePledgeHeadViewRopa3.SetEnable(bEnable);
        	}	
    	}
    }
    
    public void RegisterPlayerOfflineTransferChipsViewItems()
    {
    	if(m_PlayerOfflineTransferController != null)
    		m_PlayerOfflineTransferController.RegisterPlayerOfflineTransferChipsViewItems();	
    }
    
    public void ClosePlayerOfflineTransferViewByCancel()  
    {
    	int nIndex = GetGameMainPlayViewIndexInViewGroup();
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null && 0 <= nIndex)
    	{
    		m_ActiveViewIndex = nIndex;
        	m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_righttoleft);
        	m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_righttoleft);
        	m_CurrentActivity.m_ScreenController.setDisplayedChild(nIndex);
        	m_CurrentActivity.m_GameLayoutContainer.SetLayout(0, 0, GameLayoutConstant.GetCurrentScreenWidth(), GameLayoutConstant.GetCurrentScreenHeight());
        	this.m_PlayerOfflineTransferController.Close();
    	}
    }

    public void ClosePlayerOfflineTransferViewByOK()  
    {
    	int nIndex = GetGameMainPlayViewIndexInViewGroup();
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null && 0 <= nIndex)
    	{
    		m_ActiveViewIndex = nIndex;
        	m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_righttoleft);
        	m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_righttoleft);
        	m_CurrentActivity.m_ScreenController.setDisplayedChild(nIndex);
        	m_CurrentActivity.m_GameLayoutContainer.SetLayout(0, 0, GameLayoutConstant.GetCurrentScreenWidth(), GameLayoutConstant.GetCurrentScreenHeight());
        	if(this.m_GameController != null)
        	{
        		this.m_GameController.PlayerTranfereChipsFrom(this.m_PlayerOfflineTransferController.m_nWorkingPlayerSeatID, this.m_PlayerOfflineTransferController.GetCurrentReceiverIDFromList(), this.m_PlayerOfflineTransferController.m_nSelectedTransferAmount);
        	}
        	this.m_PlayerOfflineTransferController.Close();
    	}
    }
    
    public void ClosePlayerOnlineTransferViewByOK()
    {
    	int nIndex = GetGameMainPlayViewIndexInViewGroup();
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null && 0 <= nIndex)
    	{
    		m_ActiveViewIndex = nIndex;
        	m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_righttoleft);
        	m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_righttoleft);
        	m_CurrentActivity.m_ScreenController.setDisplayedChild(nIndex);
        	m_CurrentActivity.m_GameLayoutContainer.SetLayout(0, 0, GameLayoutConstant.GetCurrentScreenWidth(), GameLayoutConstant.GetCurrentScreenHeight());
        	if(this.m_GameController != null)
        	{
        		this.m_GameController.OnlinePlayerTranfereChipsFrom(this.m_PlayerOfflineTransferController.m_nSelectedTransferAmount);
        	}
        	this.m_PlayerOfflineTransferController.Close();
    	}
    }

    public void CloseRecievedOnlinePlayerMessageBoard()
    {
		if(this.m_CurrentActivity.m_RecievedPlayerMessaeBoard != null && this.m_CurrentActivity.m_RecievedPlayerMessaeBoard.getVisibility() == View.VISIBLE)
		{
			this.m_CurrentActivity.m_RecievedPlayerMessaeBoard.CloseView(true);
		}
    }
    
    public void ShowRecievedOnlinePlayerMessageBoard()
    {
		if(this.m_CurrentActivity.m_RecievedPlayerMessaeBoard != null)
		{
			this.m_CurrentActivity.m_RecievedPlayerMessaeBoard.PostOnLayoutHandle();
    		this.m_CurrentActivity.m_GameLayoutContainer.bringChildToFront(this.m_CurrentActivity.m_RecievedPlayerMessaeBoard);
    		this.m_CurrentActivity.m_RecievedPlayerMessaeBoard.OpenView(true);
    	    m_nReceivedMessageBoardStart = CApplicationController.GetSystemTimerClick();
		}
    }

    public void ShowOnlinePlayerMessage(String text)
    {
		if(this.m_CurrentActivity.m_RecievedPlayerMessaeBoard != null)
		{
			this.m_CurrentActivity.m_RecievedPlayerMessaeBoard.PostOnLayoutHandle();
    		this.m_CurrentActivity.m_GameLayoutContainer.bringChildToFront(this.m_CurrentActivity.m_RecievedPlayerMessaeBoard);
    		this.m_CurrentActivity.m_RecievedPlayerMessaeBoard.SetTextMessage(text);
    		this.m_CurrentActivity.m_RecievedPlayerMessaeBoard.OpenView(true);
    	    m_nReceivedMessageBoardStart = CApplicationController.GetSystemTimerClick();
		}
    }

    public void CloseSendOnlineTextMessagePanel()
    {
		if(this.m_CurrentActivity.m_SendOnlineTextMessagePanel != null && this.m_CurrentActivity.m_SendOnlineTextMessagePanel.getVisibility() == View.VISIBLE)
		{
			this.m_CurrentActivity.m_SendOnlineTextMessagePanel.CloseView(true);
		}
    }

    public void SendOnlineTextMessageToPeer(String message)
    {
		if(this.m_CurrentActivity.m_SendOnlineTextMessagePanel != null && this.m_CurrentActivity.m_SendOnlineTextMessagePanel.getVisibility() == View.VISIBLE)
		{
			this.m_CurrentActivity.m_SendOnlineTextMessagePanel.CloseView(true);
			if(message != null && 0 < message.length())
			{
				this.m_GameController.SenderOnlinePlayerTextMessage(message);
			}
		}
    }
    
    public void OpenSendOnlineTextMessagePanel()
    {
		if(this.m_CurrentActivity.m_SendOnlineTextMessagePanel != null)
		{
			this.m_CurrentActivity.m_SendOnlineTextMessagePanel.PostOnLayoutHandle();
    		this.m_CurrentActivity.m_GameLayoutContainer.bringChildToFront(this.m_CurrentActivity.m_SendOnlineTextMessagePanel);
    		this.m_CurrentActivity.m_SendOnlineTextMessagePanel.CleanTextMessage();
    		this.m_CurrentActivity.m_SendOnlineTextMessagePanel.OpenView(true);
		}
    }
    
    public void ShowStatusBar(String text)
    {
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_GameLayoutContainer != null && this.m_CurrentActivity.m_StatusBar != null)
    	{
    		CloseRecievedOnlinePlayerMessageBoard();  		this.m_CurrentActivity.m_GameLayoutContainer.bringChildToFront(this.m_CurrentActivity.m_StatusBar);
    		this.m_CurrentActivity.m_StatusBar.ShowMe();//setVisibility(View.VISIBLE);
    		this.m_CurrentActivity.m_StatusBar.SetText(text);
    		this.m_nStatusBarStart = CApplicationController.GetSystemTimerClick();
    	}
    }

    public void CloseStatusBar()
    {
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_StatusBar != null)
    	{
    		this.m_CurrentActivity.m_StatusBar.CloseMe();//setVisibility(View.GONE);
    	}
    }
    
    public void HandleMainOnlineGameButtonClick()
    {
    	if(this.m_CurrentActivity == null)
    		return;
    	
    	if(XSGAPINetworking.isNetworkAvailable(m_CurrentActivity) == false)
    	{
    		m_CurrentActivity.ShowDefaultAlert(StringFactory.GetString_AskOnlineConnection());
    		return;
    	}
    	if(GameScore.IsOnlineServiceEnable() == false || GameScore.HasOnlineNickName() == false)
    	{
    		//???????????????????????
    		//HandleOpenlineSetupSubView(false);
        	m_CurrentActivity.AskForOnlineGameOption();
    		return;
    	}
    	if(GameScore.HasOnlinePlayerID() == false)
    	{
        	String szID = XSGAPIGameServiceManager.GenerateGUID();
        	GameScore.SetOnlinePlayerID(szID);
        	XSGAPIUser.registerLocalUserDelegate(m_GameController);
        	XSGAPIUser.localUser().LoadCurrentUserInformation();
    	}
    	
    	
    	this.OpenOnlinePlayerListView();
    }
    
    public void DumpOnlineUserList()
    {
    	int nIndex = this.GetOnlinePlayerListViewGroup();
    	if(nIndex == m_ActiveViewIndex)
    	{
    		this.m_OnlinePlayerListController.DumpOnlineUserList();
    	}
    }
    
    public void ShowFullScreenAlertDialog()
    {
		View playView = (View)this.m_CurrentActivity.findViewById(R.id.FullscreenAlertDialogLayout);
		this.m_CurrentActivity.m_ScreenController.bringChildToFront(playView);
		playView.setVisibility(View.VISIBLE);
    }

    public void HideFullScreenAlertDialog()
    {
		View playView = (View)this.m_CurrentActivity.findViewById(R.id.FullscreenAlertDialogLayout);
		playView.setVisibility(View.GONE);
    }

    public void ShowFullScreenCustomDialog()
    {
		View playView = (View)this.m_CurrentActivity.findViewById(R.id.FullscreenCustomDialogLayout);
		this.m_CurrentActivity.m_ScreenController.bringChildToFront(playView);
		playView.setVisibility(View.VISIBLE);
    }

    public void HideFullScreenCustomDialog()
    {
		View playView = (View)this.m_CurrentActivity.findViewById(R.id.FullscreenCustomDialogLayout);
		playView.setVisibility(View.GONE);
    }

    public void ShowFullScreenTwoSelectionDialog()
    {
		View playView = (View)this.m_CurrentActivity.findViewById(R.id.FullscreenTwoSelectionsDialogLayout);
		this.m_CurrentActivity.m_ScreenController.bringChildToFront(playView);
		playView.setVisibility(View.VISIBLE);
    }

    public void HideFullScreenTwoSelectionDialog()
    {
		View playView = (View)this.m_CurrentActivity.findViewById(R.id.FullscreenTwoSelectionsDialogLayout);
		playView.setVisibility(View.GONE);
    }

    public void ShowFullScreenThreeSelectionDialog()
    {
		View playView = (View)this.m_CurrentActivity.findViewById(R.id.FullscreenThreeSelectionsDialogLayout);
		this.m_CurrentActivity.m_ScreenController.bringChildToFront(playView);
		playView.setVisibility(View.VISIBLE);
    }

    public void HideFullScreenThreeSelectionDialog()
    {
		View playView = (View)this.m_CurrentActivity.findViewById(R.id.FullscreenThreeSelectionsDialogLayout);
		playView.setVisibility(View.GONE);
    }
    
    public void AddMoneyToMyAccount(int nChips)
	{
		if(this.m_GameController != null)
			this.m_GameController.AddMoneyToMyAccount(nChips); 
		
		this.UpdateGameUI();
	}
    
    //////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Online game related functions
    //
    //////////////////////////////////////////////////////////////////////////////////////////////
    public void SentInvitationRejected()
    {
        if(this.m_OnlinePlayerListController != null)
        {
        	this.m_OnlinePlayerListController.SentInvitationRejected();
        }
    }
    
    public void CloseOnlinePlayerListView()
    {
    	this.m_OnlinePlayerListController.CloseOnlinePlayerListView();
    }

    public void CloseInAppPruchaseItemListView()
    {
//    	this.m_InAppPruchaseItemListController.CloseInAppPruchaseListView();
    }
    
    public void OnlineRequestDone()
    {
    	m_CurrentActivity.m_OnlineCloseButton.setVisibility(View.VISIBLE);
    	m_CurrentActivity.m_OnlineOpenButton.setVisibility(View.GONE);
    	m_CurrentActivity.m_SystemButton.setVisibility(View.GONE);
    }
    
    public void OnlineRequestCancelled()
    {
    	m_CurrentActivity.m_OnlineOpenButton.setVisibility(View.VISIBLE);
    	m_CurrentActivity.m_OnlineCloseButton.setVisibility(View.GONE);
    	m_CurrentActivity.m_SystemButton.setVisibility(View.VISIBLE);
        XSGAPIGameServiceManager.getXSGAPIGameManager().SetAutoRejectInvitation(false);
    }
    
    public void AcceptCurrentInvitation()
    {
    	this.m_GameController.AcceptCurrentInvitation();
    	this.ShowStatusBar(StringFactory.GetString_OnlineStarting());
    }
    
    public void AbsoluteShutdownOnlineGame()
    {
    	this.m_GameController.AbsoluteShutdownOnlineGame();
    }
    
    public void OpenXSGAPIScoreBoardView()
    {
    	int nIndex = GetXSGAPIScoreBoardViewIndexInViewGroup();
    	if(this.m_CurrentActivity != null && this.m_CurrentActivity.m_ScreenController != null && 0 <= nIndex)
    	{
    		m_ActiveViewIndex = nIndex;
    		m_CurrentActivity.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_lefttoright);
    		m_CurrentActivity.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_lefttoright);
        	m_CurrentActivity.m_ScreenController.setDisplayedChild(nIndex);
        	m_XSGAPIScoreboardController.LoadOnlinePlayerList();
    	}
    }
    
    public void PostScoreToScoreBoard()
    {
    	//int nHighScore = ScoreRecord.GetMyMostWinChips();
    	//String myName = XSGAPIGameServiceManager.getXSGAPIGameManager().GetLocalUser().GetUserName();
    	//String myID = XSGAPIGameServiceManager.getXSGAPIGameManager().GetLocalUser().GetUserID();
    	if(this.m_GameController != null)
    		this.m_GameController.PostHighScoreToScoreBoard();
    }
    
    public void ShowHighScoreSubmitted()
    {
    	ShowStatusBar(StringFactory.GetString_HighScoreSubmitted());    	
    }
    
    public void ProcessXSGAPIScoreList(List<XSGAPIScoreBoardIntScore> scorelist)
    {
    	int nIndex = GetXSGAPIScoreBoardViewIndexInViewGroup();
    	if(m_ActiveViewIndex == nIndex)
        	m_XSGAPIScoreboardController.ProcessXSGAPIScoreList(scorelist);
    }
    
    public void ProcessXSGAPIScoreList2(List<XSGAPIScoreBoardIntScore> scorelist, boolean bQueryNext)
    {
    	int nIndex = GetXSGAPIScoreBoardViewIndexInViewGroup();
    	if(m_ActiveViewIndex == nIndex)
        	m_XSGAPIScoreboardController.ProcessXSGAPIScoreList2(scorelist, bQueryNext);
    }
    
    public void PostScoreToGameCircleLeaderBoard()
    {
/*    	LeaderboardsClient lbClient = agsClient.getLeaderboardsClient();
    	lbClient.submitScore(YOUR_LEADERBOARD_ID, longScoreValue, "user data").setCallback(new AGResponseCallback<submitscoreresponse>() 
    	{
    	 
    	    @Override
    	    public void onComplete(SubmitScoreResponse result) {
    	        if (result.isError()) {
    	            // Add optional error handling here.  Not required since re-tries and on-device request caching are automatic
    	     
    	        } else {
    	            // Continue game flow
    	        }
    	    }
    	});    
    	*/
    }
}
