package com.xgadget.ChuiNiuLite;

import java.util.List;

//import com.tapfortap.AdView;
//import com.xgadget.XSGService.XSGAPIGameServiceManager;
//import com.xgadget.XSGService.XSGAPINetworking;
import com.xgadget.XSGService.XSGAPIReleaseConfigure;
import com.xgadget.facegesture.CXFGCameraHelper;
import com.xgadget.facegesture.CXFGFaceView;
import com.xgadget.facegesture.CXFGFakePreview;
//import com.xgadget.XSGService.XSGAPIScoreBoardIntScore;
//import com.xgadget.XSGService.XSGAPIUser;
import com.xgadget.uimodule.CToolbarButton;
import com.xgadget.uimodule.CustomBitmapButton;
import com.xgadget.uimodule.MyAbsoluteLayout;
import com.xgadget.uimodule.ResourceHelper;

import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.hardware.Camera;
//import android.widget.ViewFlipper;
import android.os.Handler;
import android.os.SystemClock; 

public class CApplicationController 
{
	public ViewFlipper 			m_ScreenController;
	public static long 			g_SystemTimerClick = 0;
	
	public CFCActivity 			m_CurrentActivity;
	public CGameController 		m_GameController;
	public CMainMenuController	m_MenuController;
	public CConfigureViewController		m_ConfigureController;
	public CScoreViewController	m_ScoreViewController;
	
	public Camera               m_FrontCamera;
    private CXFGFaceView 		m_FGFaceView;
    private CXFGFakePreview 	m_FGPreview;
	
	//public CPlayerMakeBetController			m_PlayerBetViewController;
	//public CScoreViewController				m_ScoreViewController;
	//public CPlayerOfflineTransferChipsController		m_PlayerOfflineTransferController;
	//public COnlineSetupController			m_OnlineSetupController;
	//public COnlinePlayerListController			m_OnlinePlayerListController;
	//public CInAppPurchaseItemListController		m_InAppPruchaseItemListController;
	//public CXSGAPIScoreboardController			m_XSGAPIScoreboardController;
	
	
	
	private Handler 				m_TimerHandler;
	private Runnable 				m_UpdateTimeTask;

	private static final int  		TIMER_INTERVAL_MS = 10;
    private long					m_TimingCount;	
    
    private int						m_ActiveViewIndex; //The index of current display view in ViewFlipper
    private int						m_GameViewIndex; //The index of current display view in ViewFlipper
    private boolean 				m_bNeedStartNewGame;
	
    private boolean					m_bInActivityConfigureChange;
    
    //private boolean					m_bReceivedMessageBoardOpenned;
    private long					m_nReceivedMessageBoardStart;
    private long					m_nStatusBarStart;
    
    
    public static long GetSystemTimerClick()
    {
    	return g_SystemTimerClick;
    }
    
    public void SetCurrentActivity(CFCActivity 	currentActivity)
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

	public void UpdateGameUI()
	{
		
	}

	public void InitalizeGUIComponents()
	{
		LoadGameGraphicUI();
		LoadMainMenuViewControls();
		LoadScoreHelpViewControls();
		LoadConfigureViewControls();
		LoadScoreViewControls();
		LoadFacticalGestureParameters();
	}
	
	public void LoadFacticalGestureParameters()
	{
		//m_FrontCamera = CXFGCameraHelper.GetFrontCamera();
		
	}
	
	public boolean IsSupportFacticalGesture()
	{
		boolean bRet = false;
		
		if(m_FrontCamera != null)
		{
			bRet = true;
		}
		
		return bRet;
	}
	
	
	public void LoadGameGraphicUI()
	{
		m_ScreenController = (ViewFlipper)this.m_CurrentActivity.findViewById(R.id.ScreenController);
		if(m_GameController != null)
		{
			m_GameController.LoadGameGraphicUI();
		}
	}
	
	private void LoadScoreHelpViewControls()
	{
		CustomBitmapButton btnScoreHelpClose = (CustomBitmapButton)m_CurrentActivity.findViewById(R.id.ScoreHelpCloseButton);
		btnScoreHelpClose.SetBitmap(CImageLoader.GetRoundCloseBitmap());
		btnScoreHelpClose.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				ExitToMainGameView();
			}
		});
	}
	
	private void LoadMainMenuViewControls()
	{
		CToolbarButton btnClose = (CToolbarButton)m_CurrentActivity.findViewById(R.id.MenuSystemButton);
		btnClose.m_LayoutContainer = (MyAbsoluteLayout)m_CurrentActivity.findViewById(R.id.GameMenuLayout);
		btnClose.SetBitmap(CImageLoader.GetSystemButtonBitmap());
		btnClose.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				ExitToMainGameView();
			}
		});
		btnClose.PostOnLayoutHandle();
		if(m_MenuController != null)
		{
			m_MenuController.LoadMenuUI();
		}
	}
	
	private void LoadConfigureViewControls()
	{
		if(m_ConfigureController != null)
		{
			m_ConfigureController.LoadControls();
		}
	}
	
	private void LoadScoreViewControls()
	{
		CustomBitmapButton btnScoreClose = (CustomBitmapButton)m_CurrentActivity.findViewById(R.id.ScoreViewCloseButton);
		btnScoreClose.SetBitmap(CImageLoader.GetRoundCloseBitmap());
		btnScoreClose.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				ExitToMainGameView();
			}
		});
		
	}
	
	private void ExitToMainGameView()
	{
		int nIndex = GetGameMainPlayViewIndexInViewGroup();
		if(0 <= nIndex && nIndex != m_ActiveViewIndex)
		{
			m_ActiveViewIndex = nIndex;
			this.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_righttoleft);
			this.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_righttoleft);
			this.m_ScreenController.setDisplayedChild(nIndex);
		}
	}
	
	public void OpenScoreHelpView()
	{
		int nIndex = GetScoreHelpViewIndexInViewGroup();
		if(0 <= nIndex && nIndex != m_ActiveViewIndex)
		{
			m_ActiveViewIndex = nIndex;
			this.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_lefttoright);
			this.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_lefttoright);
			this.m_ScreenController.setDisplayedChild(nIndex);
		}
	}

	public void OpenMainMenuView()
	{
		int nIndex = GetMenuViewIndexInViewGroup();
		if(0 <= nIndex && nIndex != m_ActiveViewIndex)
		{
			m_ActiveViewIndex = nIndex;
			this.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_lefttoright);
			this.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_lefttoright);
			this.m_ScreenController.setDisplayedChild(nIndex);
		}
	}

	public void GotoMainMenuViewFromSubMenuView()
	{
		int nIndex = GetMenuViewIndexInViewGroup();
		if(0 <= nIndex && nIndex != m_ActiveViewIndex)
		{
			m_ActiveViewIndex = nIndex;
			this.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_righttoleft);
			this.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_righttoleft);
			this.m_ScreenController.setDisplayedChild(nIndex);
		}
	}

	public void OpenConfigureSubMenuView()
	{
		int nIndex = GetConfigureSubMenuViewIndexInViewGroup();
		if(0 <= nIndex && nIndex != m_ActiveViewIndex)
		{
			m_ActiveViewIndex = nIndex;
			this.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_lefttoright);
			this.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_lefttoright);
			this.m_ScreenController.setDisplayedChild(nIndex);
			if(m_ConfigureController != null)
			{
				m_ConfigureController.UpdateConfigurationViewInformationForOpenning();
			}
		}
	}
	
	public void OpenScoreView()
	{
		int nIndex = GetScoreViewIndexInViewGroup();
		if(0 <= nIndex && nIndex != m_ActiveViewIndex)
		{
			m_ActiveViewIndex = nIndex;
			this.m_ScreenController.setInAnimation(m_CurrentActivity, R.anim.in_lefttoright);
			this.m_ScreenController.setOutAnimation(m_CurrentActivity, R.anim.out_lefttoright);
			this.m_ScreenController.setDisplayedChild(nIndex);
			if(m_ScoreViewController != null)
			{
				m_ScoreViewController.LoadCurrentScore();
			}
		}
	}
	
	private int GetScoreViewIndexInViewGroup()
	{
    	int nRet = -1;
    	
    	if(this.m_CurrentActivity != null && this.m_ScreenController != null)
    	{
    		View playView = (View)this.m_CurrentActivity.findViewById(R.id.ScoreViewGroup);
    		int nCount =  this.m_ScreenController.getChildCount();
    		for(int i = 0; i < nCount; ++i)
    		{
    			View pChild = this.m_ScreenController.getChildAt(i);
    			if(pChild == playView)
    				return i;
    		}
    	}
    	
    	return nRet;
	}
	
	private int GetConfigureSubMenuViewIndexInViewGroup()
	{
    	int nRet = -1;
    	
    	if(this.m_CurrentActivity != null && this.m_ScreenController != null)
    	{
    		View playView = (View)this.m_CurrentActivity.findViewById(R.id.ConfigureViewGroup);
    		int nCount =  this.m_ScreenController.getChildCount();
    		for(int i = 0; i < nCount; ++i)
    		{
    			View pChild = this.m_ScreenController.getChildAt(i);
    			if(pChild == playView)
    				return i;
    		}
    	}
    	
    	return nRet;
	}
	
    private int GetGameMainPlayViewIndexInViewGroup()
    {
    	int nRet = -1;
    	
    	if(this.m_CurrentActivity != null && this.m_ScreenController != null)
    	{
    		View playView = (View)this.m_CurrentActivity.findViewById(R.id.GameMainPlayingViewGroup);
    		int nCount =  this.m_ScreenController.getChildCount();
    		for(int i = 0; i < nCount; ++i)
    		{
    			View pChild = this.m_ScreenController.getChildAt(i);
    			if(pChild == playView)
    				return i;
    		}
    	}
    	
    	return nRet;
    }

    private int GetScoreHelpViewIndexInViewGroup()
    {
    	int nRet = -1;
    	
    	if(this.m_CurrentActivity != null && this.m_ScreenController != null)
    	{
    		View playView = (View)this.m_CurrentActivity.findViewById(R.id.ScoreHelpGroup);
    		int nCount =  this.m_ScreenController.getChildCount();
    		for(int i = 0; i < nCount; ++i)
    		{
    			View pChild = this.m_ScreenController.getChildAt(i);
    			if(pChild == playView)
    				return i;
    		}
    	}
    	
    	return nRet;
    }

    private int GetMenuViewIndexInViewGroup()
    {
    	int nRet = -1;
    	
    	if(this.m_CurrentActivity != null && this.m_ScreenController != null)
    	{
    		View playView = (View)this.m_CurrentActivity.findViewById(R.id.MainMenuViewGroup);
    		int nCount =  this.m_ScreenController.getChildCount();
    		for(int i = 0; i < nCount; ++i)
    		{
    			View pChild = this.m_ScreenController.getChildAt(i);
    			if(pChild == playView)
    				return i;
    		}
    	}
    	
    	return nRet;
    }
    
	public void RestoreDisplayView()
	{
    	this.ClearAcitivityConfigureChangeFlag();

    	m_GameViewIndex = GetGameMainPlayViewIndexInViewGroup();
    	if(this.m_CurrentActivity != null && this.m_ScreenController != null && 0 <= m_ActiveViewIndex)
    	{
        	this.m_ScreenController.setDisplayedChild(m_ActiveViewIndex);
    	}
	}
	
    public void InitializeGameConfiguration()
    {
    	m_ActiveViewIndex = 0;
    	m_GameViewIndex = 0;
 /*       ScoreRecord.IntiScore();
        int nType = ScoreRecord.GetGameType();
        int nTheme = ScoreRecord.GetThemeType();
        boolean bAuto = (ScoreRecord.GetOfflineBetMethod() == 0);
        boolean bSound = ScoreRecord.GetSoundEnable();
        Configuration.setCurrentGameType(nType);
        Configuration.setCurrentThemeType(nTheme);
        Configuration.setRoPaAutoBet(bAuto);
        Configuration.setPlaySoundEffect(bSound);
        Configuration.setPlayTurn(ScoreRecord.GetPlayTurnType());*/
    }
    
	public CApplicationController() 
	{
		// TODO Auto-generated constructor stub
		CApplicationController.g_SystemTimerClick = SystemClock.uptimeMillis();
		m_GameController = new CGameController(); 
		m_MenuController = new CMainMenuController();
		m_ConfigureController = new CConfigureViewController();		
		m_ScoreViewController = new CScoreViewController();	
		
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
	    
	    m_ScreenController = null;
	    this.m_FrontCamera = null;
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

	public void OnTimerEvent()
	{
		if(m_GameController != null && m_GameViewIndex == m_ActiveViewIndex)
		{
			m_GameController.OnTimerEvent();
		}
/*		
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
*/		
	}
	
    public void ShowFullScreenAlertDialog()
    {
		View playView = (View)this.m_CurrentActivity.findViewById(R.id.FullscreenAlertDialogLayout);
		this.m_ScreenController.bringChildToFront(playView);
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
		this.m_ScreenController.bringChildToFront(playView);
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
		this.m_ScreenController.bringChildToFront(playView);
		playView.setVisibility(View.VISIBLE);
    }

    public void HideFullScreenTwoSelectionDialog()
    {
		View playView = (View)this.m_CurrentActivity.findViewById(R.id.FullscreenTwoSelectionsDialogLayout);
		playView.setVisibility(View.GONE);
    }
    
    public void StartFacialGestureDetection()
    {
		m_FrontCamera = CXFGCameraHelper.GetFrontCamera();
    	if(m_FrontCamera != null)
    	{	
        //private CXFGFaceView 		m_FGFaceView;
        //private CXFGFakePreview 	m_FGPreview;
    	//	m_FGFaceView = new CXFGFaceView(this.m_CurrentActivity);
    	//	m_FGPreview = new CXFGFakePreview(this.m_CurrentActivity, m_FGFaceView);
    	//	m_FGPreview.RegisterCamera(m_FrontCamera);
    	//	this.m_ScreenController.addView(m_FGPreview);
    	//	this.m_ScreenController.addView(m_FGFaceView);
    		RelativeLayout playView = (RelativeLayout)this.m_CurrentActivity.findViewById(R.id.GameMainPlayingViewGroup);
        	m_FGFaceView = new CXFGFaceView(this.m_CurrentActivity);
        	m_FGPreview = new CXFGFakePreview(this.m_CurrentActivity, m_FGFaceView);
        	m_FGPreview.RegisterCamera(m_FrontCamera);
        	m_FGPreview.layout(100, 100, 300, 250);
        	m_FGFaceView.layout(100, 100, 300, 250);
        	//m_FGPreview.setGravity(Gravity.CENTER);        	
        	//m_FGPreview.setVisibility(View.GONE);
        	playView.addView(m_FGPreview);//, 0);
        	playView.addView(m_FGFaceView);//, 1);
        	RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(200, 150);
        	playView.updateViewLayout(m_FGPreview, param);
        	//m_FGPreview.layout(400, 300, 400 + m_FGPreview.getMeasuredWidth(), 300 + m_FGPreview.getMeasuredHeight());
        	//m_FGPreview.requestLayout();
        	//playView.
    	
    	}
    }

    public void StopFacialGestureDetection()
    {
    	if(m_FrontCamera != null)
    	{	
/*	    	m_FaceView = new CXFGFaceView(this);
	    	m_Preview = new CXFGFakePreview(this, m_FaceView);
	    	m_Preview.RegisterCamera(frontCamera);
	    	m_Layout.addView(m_Preview);
	    	m_Layout.addView(m_FaceView);*/
    		RelativeLayout playView = (RelativeLayout)this.m_CurrentActivity.findViewById(R.id.GameMainPlayingViewGroup);
         	
        	playView.removeView(m_FGPreview);
        	playView.removeView(m_FGFaceView);
        	m_FrontCamera.release();
        	m_FrontCamera = null;
    	}
    }
    
}
