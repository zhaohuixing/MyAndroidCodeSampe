package com.xgadget.ChuiNiuLite;


//import java.util.List;

//import org.json.JSONException;
//import org.json.JSONObject;

//import com.xgadget.XSGService.XSGAPIConstants;
//import com.xgadget.XSGService.XSGAPIGameServiceManager;
//import com.xgadget.XSGService.XSGAPIGameServiceManagerDelegate;
//import com.xgadget.XSGService.XSGAPIMessage;
//import com.xgadget.XSGService.XSGAPINetworking;
//import com.xgadget.XSGService.XSGAPIReleaseConfigure;
//import com.xgadget.XSGService.XSGAPIScoreBoard;
//import com.xgadget.XSGService.XSGAPIScoreBoardDelegate;
//import com.xgadget.XSGService.XSGAPIScoreBoardIntScore;
//import com.xgadget.XSGService.XSGAPIUser;
//import com.xgadget.XSGService.XSGAPIUserDelegate;

//import android.util.Log;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
//import com.xgadget.uimodule.BasicLayoutView;
import com.xgadget.uimodule.CToolbarButton;
import com.xgadget.uimodule.CustomBitmapButton;
import com.xgadget.uimodule.GlossyBitmapButton;
import com.xgadget.uimodule.MyAbsoluteLayout;
import com.xgadget.uimodule.ResourceHelper;

public class CGameController //implements XSGAPIGameServiceManagerDelegate, XSGAPIUserDelegate, XSGAPIScoreBoardDelegate
{
	private CGameView					m_GameView;
	private CToolbarButton				m_SystemButton;
	private CToolbarButton				m_HelpButton;
	private CToolbarButton				m_PlayModeChangeButton;
	private GlossyBitmapButton			m_StartGameButton;
	
//	private XSGAPIUser					m_CachedInvitationSender;
//    private XSGAPIScoreBoard 			m_GameScoreBoard;
	
	public void PauseGame()
	{
/*	    m_GameLobby.PauseGame();
	    if(m_ActiveGameGroup != null)
	        m_ActiveGameGroup.PauseGame();
	    if(Configuration.canPlaySound() == true)
	    {
	        SoundSource.StopAllPlayingSound();
	    }*/
		ShowOrHideToolbarButtons(true);
	}

	public void ResumeGame()
	{
	    //?????m_GameLobby.ResumeGame();
		ShowOrHideToolbarButtons(false);
	}
	
	
	public CGameController() 
	{
		// TODO Auto-generated constructor stub
		m_GameView = null;
		m_SystemButton = null;
		m_HelpButton = null;
		m_PlayModeChangeButton = null;
/*		m_CachedInvitationSender = null;
		XSGAPIGameServiceManager.RegisterServiceManagerDelegate(this);
		
		m_GameScoreBoard = new XSGAPIScoreBoard(XSGAPIScoreBoard.XSGAPI_SB_SCORE_SORT, this);
		m_GameScoreBoard.createHighScoresDomain();*/
	}

	public void UpdateGameUI()
	{
		if(m_GameView != null)
			m_GameView.invalidate();
	}
	
	public void LoadGameGraphicUI()
	{
		m_GameView = (CGameView)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.game);
		m_GameView.m_LayoutContainer = (MyAbsoluteLayout)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.GameEntireLayout);
		m_GameView.SetupFGPreviewObject();
		m_SystemButton = (CToolbarButton)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.SystemButton);
		m_SystemButton.m_LayoutContainer = (MyAbsoluteLayout)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.GameEntireLayout);
		m_SystemButton.SetToolbarIndex(0);
		m_SystemButton.SetBitmap(CImageLoader.GetSystemButtonBitmap());
		m_SystemButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				OnSystemButtonClick();
			}
		});

		m_HelpButton = (CToolbarButton)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.QuestionButton);
		m_HelpButton.m_LayoutContainer = (MyAbsoluteLayout)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.GameEntireLayout);
		m_HelpButton.SetToolbarIndex(1);
		m_HelpButton.SetBitmap(CImageLoader.GetScoreHelpBitmap());
		m_HelpButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				OnScoreHelpButtonClick();
			}
		});

		m_PlayModeChangeButton = (CToolbarButton)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.PlayModeChangeButton);
		m_PlayModeChangeButton.m_LayoutContainer = (MyAbsoluteLayout)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.GameEntireLayout);
		m_PlayModeChangeButton.SetToolbarIndex(2);
		m_PlayModeChangeButton.SetBitmap(CImageLoader.GetMouthButtonBitmap());
		m_PlayModeChangeButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				OnGamePlayModeChange();
			}
		});
		//??????????
		//m_RealTimeScoreButton.setVisibility(View.GONE);
		
		Rect screenrt = CGameLayout.GetGameSceneDeviceRect();
		int nSize = GlossyBitmapButton.m_nDefaultButtonSize;
		int nTop = screenrt.height() - 2*nSize;
		int nLeft = screenrt.width() - nSize;
		
		m_StartGameButton = (GlossyBitmapButton)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.StartGameButton);
		m_StartGameButton.m_LayoutContainer = (MyAbsoluteLayout)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.GameEntireLayout);
		m_StartGameButton.SetBitmap(CImageLoader.GetStartGameBitmap());
		m_StartGameButton.SetLeftTop(nLeft, nTop);
		m_StartGameButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				OnStartGameButtonClick();
			}
		});
		m_StartGameButton.PostOnLayoutHandle();
		//m_HelpButton = null;
		//m_RealTimeScoreButton = null;
		m_GameView.m_Game.SetController(this);
		HideFGPreviewWindow();
	}
	
	public void HideFGPreviewWindow()
	{
		m_GameView.m_LayoutContainer.bringChildToFront(m_GameView);
		m_GameView.m_LayoutContainer.bringChildToFront(m_SystemButton);
		m_GameView.m_LayoutContainer.bringChildToFront(m_HelpButton);
		m_GameView.m_LayoutContainer.bringChildToFront(m_PlayModeChangeButton);
		m_GameView.m_LayoutContainer.bringChildToFront(m_StartGameButton);
		
	}
	
	private void OnSystemButtonClick()
	{
		if(CFCActivity.m_ApplicationController != null)
		{
			CFCActivity.m_ApplicationController.OpenMainMenuView();
		}
	}
	
	private void OnScoreHelpButtonClick()
	{
		if(CFCActivity.m_ApplicationController != null)
		{
			CFCActivity.m_ApplicationController.OpenScoreHelpView();
		}
	}
	
	private void OnGamePlayModeChange()
	{
		if(CFCActivity.m_ApplicationController != null && this.m_GameView != null)
		{
			if(CConfiguration.IsMouthMode() == true)
			{
				m_PlayModeChangeButton.SetBitmap(CImageLoader.GetMouthButtonBitmap());
				this.m_GameView.StopFacialGestureProcess();
			}
			else
			{
				m_PlayModeChangeButton.SetBitmap(CImageLoader.GetFaceButtonBitmap());
				this.m_GameView.StartFacialGestureProcess();
			}
			m_PlayModeChangeButton.invalidate();
		}
	}
	
	private void OnStartGameButtonClick()
	{
		if(m_GameView != null && m_GameView.m_Game != null)
		{
			if(CGameScene.IsGameStateReady() == true)
			{
				m_GameView.GameStart();
			}
			else if(CGameScene.IsGameStateResult() == true || CGameScene.IsGameStatePause() == true)
			{
				m_GameView.ResetGame();
			}
			else
			{
				m_GameView.GamePause();
			}
			this.UpdateGameUI();
		}
	}
	
	public void ShowOrHideToolbarButtons(boolean bShow)
	{
		if(bShow == true)
		{
			if(m_SystemButton != null)
				m_SystemButton.setVisibility(View.VISIBLE);
			if(m_HelpButton != null)
				m_HelpButton.setVisibility(View.VISIBLE);
			if(m_PlayModeChangeButton != null)
			{
				m_PlayModeChangeButton.setVisibility(View.VISIBLE);
				if(CConfiguration.IsMouthMode() == true)
				{
					m_PlayModeChangeButton.SetBitmap(CImageLoader.GetFaceButtonBitmap());
				}
				else
				{
					m_PlayModeChangeButton.SetBitmap(CImageLoader.GetMouthButtonBitmap());
				}
			}	
		}
		else
		{
			if(m_SystemButton != null)
				m_SystemButton.setVisibility(View.GONE);
			if(m_HelpButton != null)
				m_HelpButton.setVisibility(View.GONE);
			if(m_PlayModeChangeButton != null)
				m_PlayModeChangeButton.setVisibility(View.GONE);
		}
	}
	
	public void HandleGameEndEvent()
	{
		ShowOrHideToolbarButtons(true);
	}
	
	public void HandleGameStartEvent()
	{
		ShowOrHideToolbarButtons(false);
	}
	
	public void OnTimerEvent()
	{
		if(m_GameView != null)
		{
			m_GameView.OnTimerEvent();
		}
	}
	
	public boolean HandleTouchEvent(MotionEvent evnet)
	{
		boolean bRet = true;
		
		return bRet;
	}
	
    public void PlayBackgroundSound()
    {
    	if(m_GameView != null)
    	{
    		m_GameView.m_Game.PlayBlockageSound();
    	}
    }

    public void HandleBoardcastWinningResult()
    {
    	
    }
    
    private void AskForNextLevelGame()
    {
		// custom dialog
    	CFCActivity.m_ApplicationController.ShowFullScreenCustomDialog();

		// set the custom dialog components - text, image and button
		TextView text = (TextView)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.fullscreendialogtext);
		
		Resources res = CFCActivity.m_ApplicationController.m_CurrentActivity.getResources();
		CharSequence str = "";
        str = res.getText(R.string.PlayNewLevelGame);
		String message = str.toString();
		text.setText(message);

		CustomBitmapButton closeButton = (CustomBitmapButton)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.fullscreendialogCloseButton);
		closeButton.SetBitmap(CImageLoader.GetRoundCloseBitmap());
		// if button is clicked, close the custom dialog
		closeButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				CFCActivity.m_ApplicationController.HideFullScreenCustomDialog();
				m_GameView.ForceToStartGame();
			}
		});

		CustomBitmapButton okButton = (CustomBitmapButton)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.fullscreendialogOKButton);
		okButton.SetBitmap(CImageLoader.GetRoundOKBitmap());
		// if button is clicked, close the custom dialog
		okButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				CFCActivity.m_ApplicationController.HideFullScreenCustomDialog();
				
		        int nTotalScore = ScoreRecord.getTotalWinScore();
		        int nScoreMaxSkill = CConfiguration.getCanGamePlaySkillAtScore(nTotalScore);
		        int nScoreMaxLevel = CConfiguration.getCanGamePlayLevelAtScore(nTotalScore);

                CConfiguration.setGameLevel(nScoreMaxLevel);
		        CConfiguration.setGameSkill(nScoreMaxSkill);
				
				m_GameView.ForceToStartGame();
			}
		});
    	
    }
    
    public void HandleNewGameLevelSelection()
    {
        int nTotalScore = ScoreRecord.getTotalWinScore();
        int nCurrentSkill = CConfiguration.getGameSkill();
        int nCurrentLevel = CConfiguration.getGameLevel();
        int nScoreMaxSkill = CConfiguration.getCanGamePlaySkillAtScore(nTotalScore);
        int nScoreMaxLevel = CConfiguration.getCanGamePlayLevelAtScore(nTotalScore);

        int nCurrent = nCurrentSkill + nCurrentLevel*3;
        int nThreshold = nScoreMaxSkill + nScoreMaxLevel*3;

        if(nCurrent < nThreshold)
        {
/*     
            if ([CustomModalAlertView Ask:nil withButton1:[StringFactory GetString_CurrentGame] withButton2:[StringFactory GetString_NextLevelGame]] == ALERT_OK)
            {
                [Configuration setGameLevel:nScoreMaxLevel];
                [Configuration setGameSkill:nScoreMaxSkill];
            }*/
        	AskForNextLevelGame();
        	return;
        }
        else
        {
        	if(m_GameView != null)
        	{
        		m_GameView.ForceToStartGame();
        	}
        }
    }
    
    public void HandlePostWinGameAction()
    {
        HandleBoardcastWinningResult();
        HandleNewGameLevelSelection();
    }	
    
    public void HandlePostLostGameAction()
    {
    }

}
