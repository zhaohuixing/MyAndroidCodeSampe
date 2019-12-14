package com.xgadget.SimpleGambleWheel;

import java.util.EnumSet;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.webkit.WebView;
//import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
//import android.content.res.Configuration;





import com.xgadget.uimodule.*;
import com.xgadget.XSGService.XSGAPIConstants;
import com.xgadget.XSGService.XSGAPIGameServiceManager;
import com.xgadget.XSGService.XSGAPIReleaseConfigure;
//import com.xgadget.amazonbuy.CAmazonPurchaseObserver;
//import com.xgadget.amazonbuy.CAmazonPurchaseObserverDelegate;
import com.amazon.inapp.purchasing.PurchasingManager;
import com.xgadget.uimodule.CustomBitmapButton;
//import com.xgadget.util.IabHelper;
//import com.xgadget.util.IabResult;
//import com.xgadget.util.Inventory;
//import com.xgadget.util.Purchase;
import com.amazon.ags.api.AGResponseCallback;
import com.amazon.ags.api.AmazonGames;
import com.amazon.ags.api.AmazonGamesCallback;
import com.amazon.ags.api.AmazonGamesClient;
import com.amazon.ags.api.AmazonGamesFeature;
import com.amazon.ags.api.AmazonGamesStatus;
import com.amazon.ags.api.leaderboards.LeaderboardsClient;
import com.amazon.ags.api.leaderboards.SubmitScoreResponse;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


public class SimpleGambleWheel extends Activity implements OnClickListener//, CAmazonPurchaseObserverDelegate

{
	public static CApplicationController    m_ApplicationController; 
	
	
	public ViewFlipper 			m_ScreenController;
	
	public MyAbsoluteLayout 		m_GameLayoutContainer;
	private GameView				m_GameView;
	private CCompassRender			m_WheelGadget;
	private CCompassAnimator		m_WheelAnimator;
	private CPinRender              m_PointerGadget;
    
	public GameTBSystemButton		m_SystemButton;
    public GameTBOnlineCloseButton  m_OnlineCloseButton;
    public GameTBOnlineOpenButton  m_OnlineOpenButton;

    public GameCashBalanceView 	m_CashMachine; 
    
    
    public CMessageDisplayBoard		m_StatusBar;//???????
    public CPlayerMessageDisplayView		m_RecievedPlayerMessaeBoard;//???????
    public CPlayerSendMessageView 	m_SendOnlineTextMessagePanel;
    
    private GamePlayerLayout		m_PlayerLayout0;
    private GamePlayerAvatar		m_PlayerAvatar0;
    private GamePlayerBetView		m_PlayerBetView0;
    
    private GamePlayerLayout		m_PlayerLayout1;
    private GamePlayerAvatar		m_PlayerAvatar1;
    private GamePlayerBetView		m_PlayerBetView1;

    private GamePlayerLayout		m_PlayerLayout2;
    private GamePlayerAvatar		m_PlayerAvatar2;
    private GamePlayerBetView		m_PlayerBetView2;
    
    private GamePlayerLayout		m_PlayerLayout3;
    private GamePlayerAvatar		m_PlayerAvatar3;
    private GamePlayerBetView		m_PlayerBetView3;
    private ActivePlayerAnimator	m_ActivePlayerIndicator;
    
    //Menu view layout
	public MyAbsoluteLayout 		m_MenuLayoutContainer;
	private MainMenuView			m_MenuView;
	public GameTBSystemButton		m_MenuSystemButton;
//	private ConfigureViewMenu		m_MainMenu;

	public ConfigureViewMenuItem		m_MenuItemStartGame;
	public ConfigureViewMenuItem		m_MenuItemConfigure;
	public ConfigureViewMenuItem		m_MenuItemOnlineSetup;
	public ConfigureViewMenuItem		m_MenuItemSound;
	public ConfigureViewMenuItem		m_MenuItemScore;
	public ConfigureViewMenuItem		m_MenuItemPost;
	public ConfigureViewMenuItem		m_MenuItemPurchase;
  	
  	
	

	//Player bet view
//	private MyAbsoluteLayout 		m_PlayerPledgeLayoutContainer;
	private PlayerBetChipsPicker		m_PlayerPledgeChipPicker;
	private PlayerBetLuckyNumberPicker	m_PlayerPledgeNumberPicker;
	private PlayerPledgeHeadView		m_PlayerPledgeHeadView;
	private TextView					m_PlayerPledgeNameTag;
	private CustomBitmapButton			m_PlayerPledgeCloseButton;
	
	//GameMode configure view
	public CustomBitmapButton					m_GameModeConfigureViewNextButton;
	public GameConfigureGameModePicker		m_GameModePicker;
	public CustomBitmapButton				m_GameModeConfigureViewCloseButton;

	//Online Game play method configure view
	public CustomBitmapButton					m_OnlinePlayMethodConfigureViewNextButton;
	public CustomBitmapButton					m_OnlinePlayMethodConfigureViewPrevButton;
	public GameConfigureMethodPicker		m_OnlinePlayMethodPicker;
	public CustomBitmapButton				m_OnlinePlayMethodConfigureViewCloseButton;

	//Configure Game pledge number view
	public CustomBitmapButton					m_ConfigurePledgeTypeViewNextButton;
	public CustomBitmapButton					m_ConfigurePledgeTypeViewPrevButton;
	public CustomBitmapButton				m_ConfigurePledgeTypeViewCloseButton;
	public GameConfigureNumberPicker		m_ConfigurePledgeTypePicker;
	public GameConfigureHeadGadget			m_ConfigurePledgeHeadViewMe;
	public GameConfigureHeadGadget			m_ConfigurePledgeHeadViewRopa1;
	public GameConfigureHeadGadget			m_ConfigurePledgeHeadViewRopa2;
	public GameConfigureHeadGadget			m_ConfigurePledgeHeadViewRopa3;
	
	//Offline Game Ropa bet method configure view
	public CustomBitmapButton					m_OfflineBetMethodConfigureViewNextButton;
	public CustomBitmapButton					m_OfflineBetMethodConfigureViewPrevButton;
	public GameConfigureTypePicker			m_OfflineBetMethodPicker;
	public CustomBitmapButton				m_OfflineBetMethodConfigureViewCloseButton;

	//Game theme selection configure view
	public CustomBitmapButton					m_GameThemeConfigureViewNextButton;
	public CustomBitmapButton					m_GameThemeConfigureViewPrevButton;
	public GameThemeTypeConfigurationPicker		m_GameThemeTypePicker;
	public CustomBitmapButton					m_GameThemeConfigureViewCloseButton;
	
	//Score View 
	public CustomBitmapButton				m_ScoreViewCloseButton;

	//Online setup View 
	public CustomBitmapButton				m_OnlineSetupViewCloseButton;
	public CustomBitmapButton					m_OnlineSetupViewOKButton;
//	public CheckBox							m_OnlineOptionCheckBox;
	public CSwitchButton					m_btnOnlineSwitch;
	public ImageView						m_OnlineIcon;
	
	public EditText							m_OnlinePlayerNameEditBox;
	
	//Online player list View
	public CustomBitmapButton				m_OnlinePlayerListConnectButton;
	public CustomBitmapButton				m_OnlinePlayerListCloseButton;

	//ScoreBoard View
	public CustomBitmapButton				m_ScoreBoardCloseButton;
	
	//Googole Play in-App purchase
    // The helper object
//    private IabHelper mHelper;
    private boolean   m_GPBuyEnabled = true;
    private boolean m_bAdActivity = false; 
	
    
    //Amazon in-app purchase 
    private String m_CurrentUser;
    private boolean m_bCanPostToGameCircle;
    
    AmazonGames agsGameClient;
    
    
    private AdView m_AdView;
    
    
    // Make a list of the Amazon GameCircle features your game uses.
    // Only show the features you are using.
    EnumSet<AmazonGamesFeature> agsGameFeatures = EnumSet.of(AmazonGamesFeature.Leaderboards);
     
    // Create a callback to handle initialization result codes.
    AmazonGamesCallback agsGameCallback = new AmazonGamesCallback() 
    {
        @Override
        public void onServiceNotReady(AmazonGamesStatus reason) 
        {
        	m_bCanPostToGameCircle = false;
        	switch (reason) 
            {
            case CANNOT_AUTHORIZE:
                /**
                 * The service could not authorize the client. This should only
                 * occur if the network is not available the first time the game
                 * attempts to connect.
                 */
            case CANNOT_BIND:
                /**
                * The service could not authorize the client. This
                * should only occur if the network is not available the
                * first time the game attempts to connect.
                */
            case NOT_AUTHENTICATED:
                /**
                 * The device is not registered with an account.
                 */
            case NOT_AUTHORIZED:
                /**
                 * The game in not authorized to use the service. Check your
                 * package name and signature registered in the Developer's
                 * Portal.
                 */
            case SERVICE_NOT_OPTED_IN:
                /**
                 * The device is not opted-in to use the service.
                 */
                break;
            case SERVICE_CONNECTED:
            	//m_bCanPostToGameCircle = true;
            	break;
            default:
                break;
            }
        }

		@Override
		public void onServiceReady(AmazonGamesClient arg0) 
		{
			// TODO Auto-generated method stub
        	m_bCanPostToGameCircle = true;
		}
    };    
    
	public static void InitializeGlobalApplicationController(SimpleGambleWheel activity)
	{
		if(m_ApplicationController == null)
		{
			m_ApplicationController = new CApplicationController();
		}
		m_ApplicationController.SetCurrentActivity(activity);
	}
    
	private void InitializeGUILayout()
	{
		m_ScreenController = (ViewFlipper)findViewById(R.id.ScreenController);
		
		
		m_GameLayoutContainer = (MyAbsoluteLayout)findViewById(R.id.GameEntireLayout);
        
        m_GameView = (GameView)findViewById(R.id.GameView);
        m_GameView.m_LayoutContainer = m_GameLayoutContainer;

        m_WheelGadget = (CCompassRender)findViewById(R.id.WheelView); 
        m_WheelGadget.m_LayoutContainer = m_GameLayoutContainer;
        
        m_WheelAnimator = (CCompassAnimator)findViewById(R.id.WheelAnimator);
        m_WheelAnimator.m_LayoutContainer = m_GameLayoutContainer;
        m_WheelGadget.SetAnimator(m_WheelAnimator);
        
        m_PointerGadget = (CPinRender)findViewById(R.id.PointerView);
        m_PointerGadget.m_LayoutContainer = m_GameLayoutContainer;
        
    	m_SystemButton = (GameTBSystemButton)findViewById(R.id.SystemButton);
    	m_SystemButton.m_LayoutContainer = m_GameLayoutContainer;
    	m_SystemButton.setClickable(true);
    	m_SystemButton.setOnClickListener(this);
    	m_SystemButton.SetToolbarIndex(1);
    	
    	m_OnlineCloseButton = (GameTBOnlineCloseButton)findViewById(R.id.OnlineCloseButton);
    	m_OnlineCloseButton.m_LayoutContainer = m_GameLayoutContainer;
    	m_OnlineCloseButton.SetToolbarIndex(0);
    	m_OnlineCloseButton.setVisibility(View.INVISIBLE);
    	m_OnlineCloseButton.setOnClickListener(this);
    	
    	m_OnlineCloseButton.setClickable(true);
    	
    	m_OnlineOpenButton = (GameTBOnlineOpenButton)findViewById(R.id.OnlineOpenButton);
    	m_OnlineOpenButton.m_LayoutContainer = m_GameLayoutContainer;
    	m_OnlineOpenButton.SetToolbarIndex(0);
    	m_OnlineOpenButton.setClickable(true);
    	m_OnlineOpenButton.setOnClickListener(this);
    
        m_CashMachine = (GameCashBalanceView)findViewById(R.id.CashMachine); 
        m_CashMachine.m_LayoutContainer = m_GameLayoutContainer;
    	m_CashMachine.setClickable(true);
   	
        m_PlayerLayout0 = (GamePlayerLayout)findViewById(R.id.GamePlayer0);
        m_PlayerLayout0.m_LayoutContainer = m_GameLayoutContainer;
        m_PlayerAvatar0 = (GamePlayerAvatar)findViewById(R.id.GamePlayerAvatar0);
        m_PlayerAvatar0.setClickable(true);
        m_PlayerBetView0 = (GamePlayerBetView)findViewById(R.id.GamePlayerBetView0);
        m_PlayerBetView0.setClickable(false);
        
        
        m_PlayerLayout1 = (GamePlayerLayout)findViewById(R.id.GamePlayer1);
        m_PlayerLayout1.m_LayoutContainer = m_GameLayoutContainer;
        m_PlayerAvatar1 = (GamePlayerAvatar)findViewById(R.id.GamePlayerAvatar1);
        m_PlayerAvatar1.setClickable(true);
        m_PlayerBetView1 = (GamePlayerBetView)findViewById(R.id.GamePlayerBetView1);
        m_PlayerBetView1.setClickable(false);
        
        m_PlayerLayout2 = (GamePlayerLayout)findViewById(R.id.GamePlayer2);
        m_PlayerLayout2.m_LayoutContainer = m_GameLayoutContainer;
        m_PlayerAvatar2 = (GamePlayerAvatar)findViewById(R.id.GamePlayerAvatar2);
        m_PlayerAvatar2.setClickable(true);
        m_PlayerBetView2 = (GamePlayerBetView)findViewById(R.id.GamePlayerBetView2);
        m_PlayerBetView2.setClickable(false);
	
        m_PlayerLayout3 = (GamePlayerLayout)findViewById(R.id.GamePlayer3);
        m_PlayerLayout3.m_LayoutContainer = m_GameLayoutContainer;
        m_PlayerAvatar3 = (GamePlayerAvatar)findViewById(R.id.GamePlayerAvatar3);
        m_PlayerAvatar3.setClickable(true);
        m_PlayerBetView3 = (GamePlayerBetView)findViewById(R.id.GamePlayerBetView3);
        m_PlayerBetView3.setClickable(false);
   	
        m_ActivePlayerIndicator = (ActivePlayerAnimator)findViewById(R.id.ActivePlayerIndicator);
        m_ActivePlayerIndicator.m_LayoutContainer = m_GameLayoutContainer;
        
      //???????
    	m_StatusBar = (CMessageDisplayBoard)findViewById(R.id.StatusBar);
    	m_StatusBar.m_LayoutContainer = m_GameLayoutContainer;
    	
        m_RecievedPlayerMessaeBoard = (CPlayerMessageDisplayView)findViewById(R.id.OnlinePlayerMsgBoard);//???????
        m_RecievedPlayerMessaeBoard.m_LayoutContainer = m_GameLayoutContainer;
        
        m_SendOnlineTextMessagePanel = (CPlayerSendMessageView)findViewById(R.id.OnlineSendMsgBoard);
        m_SendOnlineTextMessagePanel.m_LayoutContainer = m_GameLayoutContainer;
        m_SendOnlineTextMessagePanel.setVisibility(View.GONE);
        
    	//Menu View
    	ConfigureViewMenu.SetMenuCount(7);
    	
    	m_MenuLayoutContainer = (MyAbsoluteLayout)findViewById(R.id.GameMenuLayout);
    	m_MenuView = (MainMenuView)findViewById(R.id.MenuView);
    	m_MenuView.m_LayoutContainer = m_MenuLayoutContainer;
    	m_MenuSystemButton = (GameTBSystemButton)findViewById(R.id.MenuSystemButton);
    	m_MenuSystemButton.m_LayoutContainer = m_MenuLayoutContainer;
    	m_MenuSystemButton.SetToolbarIndex(0);
    	m_MenuSystemButton.setClickable(true);
    	m_MenuSystemButton.setOnClickListener(this);
    	
    	m_MenuItemStartGame = (ConfigureViewMenuItem)findViewById(R.id.MenuItemStartGame);
    	m_MenuItemStartGame.m_LayoutContainer = m_MenuLayoutContainer;
    	m_MenuItemStartGame.SetMenuIndex(0);
    	m_MenuItemStartGame.SetBitmapID(R.drawable.playback);
    	
    	m_MenuItemConfigure = (ConfigureViewMenuItem)findViewById(R.id.MenuItemConfigure);
    	m_MenuItemConfigure.m_LayoutContainer = m_MenuLayoutContainer;
    	m_MenuItemConfigure.SetMenuIndex(1);
    	m_MenuItemConfigure.SetBitmapID(R.drawable.toolicon);
    	
    	m_MenuItemOnlineSetup = (ConfigureViewMenuItem)findViewById(R.id.MenuItemOnlineSetup);
    	m_MenuItemOnlineSetup.m_LayoutContainer = m_MenuLayoutContainer;
    	m_MenuItemOnlineSetup.SetMenuIndex(2);
    	m_MenuItemOnlineSetup.SetBitmapID(R.drawable.onlinestate);
    	
    	m_MenuItemSound = (ConfigureViewMenuItem)findViewById(R.id.MenuItemSoundSwitch);
    	m_MenuItemSound.m_LayoutContainer = m_MenuLayoutContainer;
    	m_MenuItemSound.SetMenuIndex(3);
    	m_MenuItemSound.SetBitmapID(R.drawable.musicicon);
    	
    	m_MenuItemScore = (ConfigureViewMenuItem)findViewById(R.id.MenuItemScore);
    	m_MenuItemScore.m_LayoutContainer = m_MenuLayoutContainer;
    	m_MenuItemScore.SetMenuIndex(4);
    	m_MenuItemScore.SetBitmapID(R.drawable.scoreicon);
   	
    	m_MenuItemPost = (ConfigureViewMenuItem)findViewById(R.id.MenuItemPost);
    	m_MenuItemPost.m_LayoutContainer = m_MenuLayoutContainer;
    	m_MenuItemPost.SetMenuIndex(5);
    	m_MenuItemPost.SetBitmapID(R.drawable.posticon);
    	
    	m_MenuItemPurchase = (ConfigureViewMenuItem)findViewById(R.id.MenuItemPurchase);
    	m_MenuItemPurchase.m_LayoutContainer = m_MenuLayoutContainer;
    	m_MenuItemPurchase.SetMenuIndex(6);
    	m_MenuItemPurchase.SetBitmapID(R.drawable.payicon);
    
    	
    	//Player bet picker view
    	m_PlayerPledgeChipPicker = (PlayerBetChipsPicker)findViewById(R.id.PlayerPledgeChipsPicker);
    	m_PlayerPledgeNumberPicker= (PlayerBetLuckyNumberPicker)findViewById(R.id.PlayerPledgeNumberPicker);
    	m_PlayerPledgeHeadView = (PlayerPledgeHeadView)findViewById(R.id.PlayerPledgeHeadView);
    	m_PlayerPledgeNameTag = (TextView)findViewById(R.id.PlayerPledgeNameTag);
    	m_PlayerPledgeCloseButton = (CustomBitmapButton)findViewById(R.id.PlayerPledgeViewCloseButton);
    	m_PlayerPledgeCloseButton.SetBitmap(ResourceHelper.GetCloseButtonBitmap());

    	
    	//GameMode configure view
    	m_GameModeConfigureViewNextButton = (CustomBitmapButton)findViewById(R.id.GameModeViewNextButton);
    	m_GameModeConfigureViewNextButton.SetBitmap(ResourceHelper.GetNextButtonBitmap());
    	m_GameModePicker = (GameConfigureGameModePicker)findViewById(R.id.GameModePicker);
    	m_GameModeConfigureViewCloseButton = (CustomBitmapButton)findViewById(R.id.GameModeViewCloseButton);
    	m_GameModeConfigureViewCloseButton.SetBitmap(ResourceHelper.GetCloseButtonBitmap());
    
    	//Online Game play method configure view
    	m_OnlinePlayMethodConfigureViewNextButton = (CustomBitmapButton)findViewById(R.id.OnlinGamePlayerMethodViewNextButton);
    	m_OnlinePlayMethodConfigureViewNextButton.SetBitmap(ResourceHelper.GetNextButtonBitmap());
    	m_OnlinePlayMethodConfigureViewPrevButton = (CustomBitmapButton)findViewById(R.id.OnlinGamePlayerMethodViewPrevButton);
    	m_OnlinePlayMethodConfigureViewPrevButton.SetBitmap(ResourceHelper.GetPrevButtonBitmap());
    	m_OnlinePlayMethodPicker = (GameConfigureMethodPicker)findViewById(R.id.OnlineGamePlayMothodPicker);
    	m_OnlinePlayMethodConfigureViewCloseButton = (CustomBitmapButton)findViewById(R.id.OnlinePlayMethodCloseButton);
    	m_OnlinePlayMethodConfigureViewCloseButton.SetBitmap(ResourceHelper.GetCloseButtonBitmap());
    	
    	//Configure Game pledge number view
    	m_ConfigurePledgeTypeViewNextButton = (CustomBitmapButton)findViewById(R.id.ConfigureGameTypeViewNextButton);
    	m_ConfigurePledgeTypeViewNextButton.SetBitmap(ResourceHelper.GetNextButtonBitmap());
    	m_ConfigurePledgeTypeViewPrevButton = (CustomBitmapButton)findViewById(R.id.ConfigureGameTypeViewPrevButton);
    	m_ConfigurePledgeTypeViewPrevButton.SetBitmap(ResourceHelper.GetPrevButtonBitmap());
    	m_ConfigurePledgeTypeViewCloseButton = (CustomBitmapButton)findViewById(R.id.ConfigureGameTypeViewCloseButton);
    	m_ConfigurePledgeTypeViewCloseButton.SetBitmap(ResourceHelper.GetCloseButtonBitmap());;
    	m_ConfigurePledgeTypePicker = (GameConfigureNumberPicker)findViewById(R.id.GameConfigureNumberPicker);
    	m_ConfigurePledgeHeadViewMe = (GameConfigureHeadGadget)findViewById(R.id.GameConfigureHeadView1);
    	m_ConfigurePledgeHeadViewRopa1 = (GameConfigureHeadGadget)findViewById(R.id.GameConfigureHeadView2);
    	m_ConfigurePledgeHeadViewRopa2 = (GameConfigureHeadGadget)findViewById(R.id.GameConfigureHeadView3);
    	m_ConfigurePledgeHeadViewRopa3 = (GameConfigureHeadGadget)findViewById(R.id.GameConfigureHeadView4);
    
    	//Offline Game Ropa bet method configure view
    	m_OfflineBetMethodConfigureViewNextButton = (CustomBitmapButton)findViewById(R.id.OfflinePledgeTypeViewNextButton);
    	m_OfflineBetMethodConfigureViewNextButton.SetBitmap(ResourceHelper.GetNextButtonBitmap());
    	m_OfflineBetMethodConfigureViewPrevButton = (CustomBitmapButton)findViewById(R.id.OfflinePledgeTypeViewPrevButton);
    	m_OfflineBetMethodConfigureViewPrevButton.SetBitmap(ResourceHelper.GetPrevButtonBitmap());
    	m_OfflineBetMethodPicker = (GameConfigureTypePicker)findViewById(R.id.OfflinePledgeConfigureTypePicker);
    	m_OfflineBetMethodConfigureViewCloseButton = (CustomBitmapButton)findViewById(R.id.OfflinePledgeTypeCloseButton);
    	m_OfflineBetMethodConfigureViewCloseButton.SetBitmap(ResourceHelper.GetCloseButtonBitmap());
    	
    	//Game theme selection configure view
    	m_GameThemeConfigureViewNextButton = (CustomBitmapButton)findViewById(R.id.GameThemeTypeSelectViewNextButton);
    	m_GameThemeConfigureViewNextButton.SetBitmap(ResourceHelper.GetNextButtonBitmap());
    	m_GameThemeConfigureViewPrevButton = (CustomBitmapButton)findViewById(R.id.GameThemeTypeSelectViewPrevButton);
    	m_GameThemeConfigureViewPrevButton.SetBitmap(ResourceHelper.GetPrevButtonBitmap());
    	m_GameThemeTypePicker = (GameThemeTypeConfigurationPicker)findViewById(R.id.GameThemeTypeSelectionPicker);
    	m_GameThemeConfigureViewCloseButton = (CustomBitmapButton)findViewById(R.id.GameThemeTypeSelectCloseButton);
    	m_GameThemeConfigureViewCloseButton.SetBitmap(ResourceHelper.GetCloseButtonBitmap());
    	
    	//Score View 
    	m_ScoreViewCloseButton = (CustomBitmapButton)findViewById(R.id.ScoreViewCloseButton);
    	m_ScoreViewCloseButton.SetBitmap(ResourceHelper.GetCloseButtonBitmap());;
    
    	//Online setup View 
    	m_OnlineSetupViewCloseButton = (CustomBitmapButton)findViewById(R.id.OnlineSetupCloseButton);
    	m_OnlineSetupViewCloseButton.SetBitmap(ResourceHelper.GetCloseButtonBitmap());;
    	m_OnlineSetupViewOKButton = (CustomBitmapButton)findViewById(R.id.OnlineSetupOKButton);
    	m_OnlineSetupViewOKButton.SetBitmap(ResourceHelper.GetOKButtonBitmap());
		
    	m_btnOnlineSwitch = (CSwitchButton)findViewById(R.id.OnlineSwitchButton);
		m_btnOnlineSwitch.SetBitmaps(ResourceHelper.GetSwitchOnBitmap(), ResourceHelper.GetSwitchOffBitmap());
		m_btnOnlineSwitch.SetOnOffState(false);
		m_OnlineIcon = (ImageView)findViewById(R.id.OnlineIcon);
		m_OnlineIcon.setBackgroundResource(R.drawable.onlinedisablestate);
    	
    	m_OnlinePlayerNameEditBox = (EditText)findViewById(R.id.OnlineNameEdit);
    	
    	//Online player list View
    	m_OnlinePlayerListConnectButton = (CustomBitmapButton)findViewById(R.id.OnlinePlayersViewConnectButton);
    	m_OnlinePlayerListConnectButton.SetBitmap(ResourceHelper.GetConnectButtonBitmap());
    	m_OnlinePlayerListCloseButton = (CustomBitmapButton)findViewById(R.id.OnlinePlayersViewCancelButton);
    	m_OnlinePlayerListCloseButton.SetBitmap(ResourceHelper.GetCloseButtonBitmap());
    	
    	//ScoreBoard View
    	m_ScoreBoardCloseButton = (CustomBitmapButton)findViewById(R.id.XSGAPIScoreBoardViewCloseButton);
    	m_ScoreBoardCloseButton.SetBitmap(ResourceHelper.GetCloseButtonBitmap());
    	
    	
        //SimpleGambleWheel.m_ApplicationController.AttachViewsController(m_ScreenController);
        SimpleGambleWheel.m_ApplicationController.AttachGameView(m_GameView);
        SimpleGambleWheel.m_ApplicationController.AttachWheel(m_WheelGadget);
        SimpleGambleWheel.m_ApplicationController.AttachPointer(m_PointerGadget);
        SimpleGambleWheel.m_ApplicationController.AttachCashMachine(m_CashMachine);
        
        m_PlayerLayout0.AttachAvatar(m_PlayerAvatar0);
        m_PlayerLayout0.AttachBetBulletin(m_PlayerBetView0);
        SimpleGambleWheel.m_ApplicationController.AttachPlayer0Layout(m_PlayerLayout0);
        
        m_PlayerLayout1.AttachAvatar(m_PlayerAvatar1);
        m_PlayerLayout1.AttachBetBulletin(m_PlayerBetView1);
        SimpleGambleWheel.m_ApplicationController.AttachPlayer1Layout(m_PlayerLayout1);
        
        m_PlayerLayout2.AttachAvatar(m_PlayerAvatar2);
        m_PlayerLayout2.AttachBetBulletin(m_PlayerBetView2);
        SimpleGambleWheel.m_ApplicationController.AttachPlayer2Layout(m_PlayerLayout2);
        
        m_PlayerLayout3.AttachAvatar(m_PlayerAvatar3);
        m_PlayerLayout3.AttachBetBulletin(m_PlayerBetView3);
        SimpleGambleWheel.m_ApplicationController.AttachPlayer3Layout(m_PlayerLayout3);
        SimpleGambleWheel.m_ApplicationController.AttachActivePlayerIndicator(m_ActivePlayerIndicator);
	
        
        SimpleGambleWheel.m_ApplicationController.AttachNumberPicker(m_PlayerPledgeNumberPicker);
    	SimpleGambleWheel.m_ApplicationController.AttachChipsPicker(m_PlayerPledgeChipPicker);
    	SimpleGambleWheel.m_ApplicationController.AttachPlayerHeadImage(m_PlayerPledgeHeadView);
    	SimpleGambleWheel.m_ApplicationController.AttachPlayerNameTag(m_PlayerPledgeNameTag);
    	SimpleGambleWheel.m_ApplicationController.AttachPlayerBetViewCloseButton(m_PlayerPledgeCloseButton);
    	
    	SimpleGambleWheel.m_ApplicationController.RegisterConfigurationMenuHandler();
    	SimpleGambleWheel.m_ApplicationController.RegisterConfigurationGameModeViewItemHandler();
    	SimpleGambleWheel.m_ApplicationController.RegisterConfigurationOnlineGamePlayMethodViewItemHandler();
    	SimpleGambleWheel.m_ApplicationController.RegisterConfigurationPledgeTypeViewItemHandler();
    	SimpleGambleWheel.m_ApplicationController.RegisterConfigurationOfflineBetMethodViewItemHandler();
    	SimpleGambleWheel.m_ApplicationController.RegisterGameThemeConfigureViewItemHandler();
    	SimpleGambleWheel.m_ApplicationController.RegisterScoreViewItemHandler();
    	SimpleGambleWheel.m_ApplicationController.RegisterOnlineSetupViewItemHandler();
    	SimpleGambleWheel.m_ApplicationController.RegisterOnlinePlayerListViewItemHandler();
    	SimpleGambleWheel.m_ApplicationController.RegisterInAppPurchaseItemListItemHandler();
    	SimpleGambleWheel.m_ApplicationController.RegisterXSGAPIScoreboardViewItemHandler();

    	SimpleGambleWheel.m_ApplicationController.RegisterPlayerOfflineTransferChipsViewItems();
    	SimpleGambleWheel.m_ApplicationController.RestoreDisplayView();
        SimpleGambleWheel.m_ApplicationController.CloseStatusBar();
        SimpleGambleWheel.m_ApplicationController.CloseRecievedOnlinePlayerMessageBoard();
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
    	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGambleWheel.onCreate ()", "onActivityResult(" + requestCode + "," + resultCode + "," + data);

        // Pass on the activity result to the helper for handling
/*        if (mHelper != null && !mHelper.handleActivityResult(requestCode, resultCode, data))
        {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
            SimpleGambleWheel.m_ApplicationController.RestoreDisplayView();
        }
        else 
        {
        	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGambleWheel.onCreate ()", "onActivityResult handled by IABUtil.");
        	SimpleGambleWheel.m_ApplicationController.InvalidateMenuView();
        	SimpleGambleWheel.m_ApplicationController.UpdateGameUI();
            //if(mHelper != null)
            //	mHelper.queryInventoryAsync(mGotInventoryListener);
        }*/
    }
	
	private boolean 	m_bUserRegistered = false;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        m_bAdActivity = false; 
     
    	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGambleWheel.onCreate ()", "onCreate ()");
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);     
        ResourceHelper.SetResourceContext(SimpleGambleWheel.this);
        XSGAPIConstants.RegisterContext(SimpleGambleWheel.this);
        setContentView(R.layout.activity_simple_gamble_wheel);
        SimpleGambleWheel.InitializeGlobalApplicationController(this);   

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        GameLayoutConstant.SetCurrentScreenDensity(dm.xdpi, dm.ydpi, dm.density);
        
        View mainView = getWindow().getDecorView();
		Rect dim = new Rect();
		mainView.getWindowVisibleDisplayFrame(dim);		
		int w = dim.width(); //m_GameView.getWidth();
		int h = dim.height(); //m_GameView.getHeight();
		GameLayoutConstant.SetCurrentScreenDemension(w, h);
        InitializeGUILayout();
        SimpleGambleWheel.m_ApplicationController.StartApplication();
        //RefreshAdViews();
        SoundSource.InitializeSoundSource();
        //TapForTap.initialize(this, "F1DFCA1D562CCE574CAD8D2986B3EDAF");
        

        m_bUserRegistered = true;
       
        if(XSGAPIReleaseConfigure.GetCurrentDeviceType() != XSGAPIConstants.XSGAPIUSER_DEVICETYPE_ANDROID)
        {
        	m_GPBuyEnabled = false;
        	return;
        }
        m_GPBuyEnabled = true;
        //Google play in-app pruchase
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiLnb8m39cJ4tDR4SUGlHrZnhtoGcT86Sjgwz4biM89T57D9fzBSsQk3BHrjhrAhMJSeojYJDZhgL73sK+adtUkZ+Pu6CdcGPYfbp9OC0oAva0RWPgUiViX1SxTOfUsMVHpj63NfI+8ELAflfMy/Q9NVxMai8JUIKHeQ2hb3fqHr286cdXFAxchC4H2TjXPDiCMhgZ3PQWNSWPwwFgHA3JeA4MCAkPFgEWQiFUA+4yq3wFDTTXh5iwtI3T1Er02ATfF4gRMLKt83V4n96sK+02xcVU5clUNArFgMetmMIvN8dud+EWGudNdevpWavTPO0xFhjuGMxdoQLq8BLM5UxawIDAQAB";
        // Create the helper, passing it our context and the public key to verify signatures with
        XSGAPIReleaseConfigure.LogDebugInfo("SimpleGameWheel GP In-App pruchase", "Creating IAB helper.");
 /*
        try
        {
        	mHelper = new IabHelper(this, base64EncodedPublicKey);
        
        	// enable debug logging (for a production application, you should set this to false).
        	mHelper.enableDebugLogging(XSGAPIReleaseConfigure.IsDebugMode());
        
        	// Start setup. This is asynchronous and the specified listener
        	// will be called once setup completes.
        	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGameWheel GP In-App pruchase", "Starting setup.");
        	mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() 
        	{
        		public void onIabSetupFinished(IabResult result) 
        		{
        			XSGAPIReleaseConfigure.LogDebugInfo("SimpleGameWheel GP In-App pruchase", "Setup finished.");

        			if (!result.isSuccess()) 
        			{
        				// Oh noes, there was a problem.
        				XSGAPIReleaseConfigure.LogDebugInfo("SimpleGameWheel GP In-App pruchase", "Problem setting up in-app billing: " + result);
        				m_GPBuyEnabled = false;
        				return;
        			}

        			// Hooray, IAB is fully set up. Now, let's get an inventory of stuff we own.
        			XSGAPIReleaseConfigure.LogDebugInfo("SimpleGameWheel GP In-App pruchase", "Setup successful. Querying inventory.");
        	        try
        	        {
        	        	mHelper.queryInventoryAsync(mGotInventoryListener);
        	        }
        	        catch (Exception exception) 
        	        {
        	        }
        		}
        	});
        }
        catch (Exception exception) 
        {
        }
 */       
        m_AdView = new AdView(this);
        m_AdView.setAdSize(AdSize.BANNER);
        m_AdView.setAdUnitId("ca-app-pub-3124334635306550/6091623626");

        // Add the AdView to the view hierarchy. The view will have no size
        // until the ad is loaded.
        android.widget.RelativeLayout layout = (android.widget.RelativeLayout) findViewById(R.id.GameMainPlayingViewGroup);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT); // or wrap_content
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        
        layout.addView(m_AdView, layoutParams);

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device.
        AdRequest adRequest = new AdRequest.Builder().build();

        // Start loading the ad in the background.
        m_AdView.loadAd(adRequest);
        
    }
/*
    // Listener that's called when we finish querying the items we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() 
    {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory)
        {
        	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGameWheel GP In-App pruchase", "Query inventory finished.");
            if (result.isFailure()) 
            {
            	m_GPBuyEnabled = false;
                return;
            }

            XSGAPIReleaseConfigure.LogDebugInfo("SimpleGameWheel GP In-App pruchase", "Query inventory was successful.");
            m_GPBuyEnabled = true;
             for(int i = 0; i < InAppPurchaseConstants.BUYITEMCOUNT; ++i)
            {
            	String szID = InAppPurchaseConstants.GetID(i);
            	if(szID != null)
            	{
                    if (inventory.hasPurchase(szID)) 
                    {
                    	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGameWheel GP In-App pruchase", "We have buy chips. Consuming it."+szID);
                        mHelper.consumeAsync(inventory.getPurchase(szID), mConsumeFinishedListener);
                    }
            	}
            }
            
            XSGAPIReleaseConfigure.LogDebugInfo("SimpleGameWheel GP In-App pruchase", "Initial inventory query finished.");
        }
    };
    
    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() 
    {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) 
        {
        	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGameWheel GP In-App pruchase", "Purchase finished: " + result + ", purchase: " + purchase);
            if (result.isFailure()) 
            {
                // Oh noes!
            	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGameWheel GP In-App pruchase","Error purchasing: " + result);
                return;
            }

            XSGAPIReleaseConfigure.LogDebugInfo("SimpleGameWheel GP In-App pruchase", "Purchase successful.");

            XSGAPIReleaseConfigure.LogDebugInfo("SimpleGameWheel GP In-App pruchase", "Purchase is done. Starting purchasing consumption.");
            mHelper.consumeAsync(purchase, mConsumeFinishedListener);
        }
    };
    
    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() 
    {
        public void onConsumeFinished(Purchase purchase, IabResult result) 
        {
        	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGameWheel GP In-App pruchase", "Consumption finished. Purchase: " + purchase + ", result: " + result);

            // If you have more than one sku, you probably should check...
            if (result.isSuccess()) 
            {
                // successfully consumed
            	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGameWheel GP In-App pruchase", "Consumption successful. Provisioning.");
            	String szID = purchase.getSku();
            	if(szID != null)
            	{
            		int nChips = InAppPurchaseConstants.GetBuyChipsByID(szID);
            		if(0 < nChips)
            		{
            	    	SimpleGambleWheel.m_ApplicationController.AddMoneyToMyAccount(nChips);
            	        if(Configuration.canPlaySound())
            	        {
            	            SoundSource.PlayDropCoinSound();
            	        }
            	        String szText = StringFactory.GetString_AcknowledgePruchase() +" " + String.valueOf(InAppPurchaseConstants.GetBuyChipsByID(szID)) + StringFactory.GetString_Chips() + "!";
            	        ShowDefaultAlert(szText);
            		}
            	}
            }
            else 
            {
            	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGameWheel GP In-App pruchase", "Error while consuming: " + result);
            }
            //updateUi();
            //setWaitScreen(false);
            XSGAPIReleaseConfigure.LogDebugInfo("SimpleGameWheel GP In-App pruchase", "End consumption flow.");
        }
    };
*/   
    // (arbitrary) request code for the purchase flow
    static final int RC_REQUEST = 10001;
    
    // Send GP pruchase request
    public void GooglePlayPurchase(String szSkuID) 
    {
    	
    	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGameWheel GP In-App pruchase", "Send GP purchase request for " + szSkuID);
 //       mHelper.launchPurchaseFlow(this, szSkuID, RC_REQUEST, mPurchaseFinishedListener);
    }
    
    // User select the "Google Play" 
    public void GooglePlayInAppPurchaseForChips(int nItemIndex)
    {
    	String szSkuID = InAppPurchaseConstants.GetID(nItemIndex);
    	if(szSkuID != null && 0 < szSkuID.length())
    	{
    		GooglePlayPurchase(szSkuID);
    	}
    }

    public void AmazonPurchase(String szSkuID)
    {
    	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGameWheel Amazon In-App pruchase", "Send Amazon purchase request for " + szSkuID);
//    	PurchasingManager.initiatePurchaseRequest(szSkuID);
    }
    
    public void AmazonInAppPurchaseForChips(int nItemIndex)
    {
    	String szSkuID = InAppPurchaseConstants.GetID(nItemIndex);
    	if(szSkuID != null && 0 < szSkuID.length())
    	{
    		AmazonPurchase(szSkuID);
    	}
    }
    
    public void onClick(View v) 
    {
    	SimpleGambleWheel.m_ApplicationController.OnClickEvent(v);
    }
    
    public void ShowDefaultAlert(String message)
    {
		// custom dialog
        SimpleGambleWheel.m_ApplicationController.ShowFullScreenAlertDialog();

		// set the custom dialog components - text, image and button
		TextView text = (TextView)findViewById(R.id.fullscreenalerttext);
		text.setText(message);

		CustomBitmapButton dialogButton = (CustomBitmapButton)findViewById(R.id.fullscreenalertCloseButton);
		dialogButton.SetBitmap(ResourceHelper.GetBlueCloseButtonBitmap());
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				SimpleGambleWheel.m_ApplicationController.HideFullScreenAlertDialog();
			}
		});
    }

    public void AskForGamePostOption()
    {
		// custom dialog
        SimpleGambleWheel.m_ApplicationController.ShowFullScreenCustomDialog();

		// set the custom dialog components - text, image and button
		TextView text = (TextView)this.findViewById(R.id.fullscreendialogtext);
		
		String message = StringFactory.GetString_ScoreBoardPostWarning();
		text.setText(message);

		CustomBitmapButton closeButton = (CustomBitmapButton)this.findViewById(R.id.fullscreendialogCloseButton);
		closeButton.SetBitmap(ResourceHelper.GetCloseButtonBitmap());;
		// if button is clicked, close the custom dialog
		closeButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
		        SimpleGambleWheel.m_ApplicationController.HideFullScreenCustomDialog();
			}
		});

		CustomBitmapButton okButton = (CustomBitmapButton) this.findViewById(R.id.fullscreendialogOKButton);
		okButton.SetBitmap(ResourceHelper.GetOKButtonBitmap());
		// if button is clicked, close the custom dialog
		okButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
		        SimpleGambleWheel.m_ApplicationController.HideFullScreenCustomDialog();
				SimpleGambleWheel.m_ApplicationController.PostScoreToScoreBoard();
			}
		});
    }
    
    public void AskForInviationFrom(String playerName)
    {
		// custom dialog
        SimpleGambleWheel.m_ApplicationController.ShowFullScreenCustomDialog();

		// set the custom dialog components - text, image and button
		TextView text = (TextView)this.findViewById(R.id.fullscreendialogtext);
		
		Resources res = this.getResources();
		CharSequence str = "";
        str = res.getText(R.string.OnlineGameInvitationAskFormt);
		String message = playerName + " " + str.toString();
		text.setText(message);

		CustomBitmapButton closeButton = (CustomBitmapButton)this.findViewById(R.id.fullscreendialogCloseButton);
		closeButton.SetBitmap(ResourceHelper.GetCloseButtonBitmap());;
		// if button is clicked, close the custom dialog
		closeButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				SimpleGambleWheel.m_ApplicationController.m_GameController.RejectCurrentInvitation();
		        SimpleGambleWheel.m_ApplicationController.HideFullScreenCustomDialog();
			}
		});

		CustomBitmapButton okButton = (CustomBitmapButton) this.findViewById(R.id.fullscreendialogOKButton);
		okButton.SetBitmap(ResourceHelper.GetOKButtonBitmap());
		// if button is clicked, close the custom dialog
		okButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				SimpleGambleWheel.m_ApplicationController.AcceptCurrentInvitation();
		        SimpleGambleWheel.m_ApplicationController.HideFullScreenCustomDialog();
			}
		});
    }
    
    public void AskForOnlineGameOption()
    {
		// custom dialog
        SimpleGambleWheel.m_ApplicationController.ShowFullScreenCustomDialog();

		// set the custom dialog components - text, image and button
		TextView text = (TextView)this.findViewById(R.id.fullscreendialogtext);
		
		String message = StringFactory.GetString_AskEnableFreeOnlineGameOption();
		text.setText(message);

		CustomBitmapButton closeButton = (CustomBitmapButton)this.findViewById(R.id.fullscreendialogCloseButton);
		closeButton.SetBitmap(ResourceHelper.GetCloseButtonBitmap());;
		// if button is clicked, close the custom dialog
		closeButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
		        SimpleGambleWheel.m_ApplicationController.HideFullScreenCustomDialog();
			}
		});

		CustomBitmapButton okButton = (CustomBitmapButton) this.findViewById(R.id.fullscreendialogOKButton);
		okButton.SetBitmap(ResourceHelper.GetOKButtonBitmap());
		// if button is clicked, close the custom dialog
		okButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
		        SimpleGambleWheel.m_ApplicationController.HideFullScreenCustomDialog();
				SimpleGambleWheel.m_ApplicationController.HandleOpenlineSetupSubView(false);
			}
		});
    }

    public void AskForOpenScoreViewOptionWithGameCircle()
    {
		// custom dialog
        SimpleGambleWheel.m_ApplicationController.ShowFullScreenThreeSelectionDialog();

        CGreenGlossyButton myScoreButton = (CGreenGlossyButton)this.findViewById(R.id.fs3sdlgone);
        myScoreButton.setClickable(true);
        myScoreButton.PostOnLayoutHandle();
        myScoreButton.SetLabelText(StringFactory.GetString_Me());
        myScoreButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
		        SimpleGambleWheel.m_ApplicationController.HideFullScreenThreeSelectionDialog();
		        SimpleGambleWheel.m_ApplicationController.OpenScoreSubView();
			}
		});
	
        CGreenGlossyButton xsgapiScoreBoardButton = (CGreenGlossyButton)this.findViewById(R.id.fs3sdlgtwo);
        xsgapiScoreBoardButton.setClickable(true);
        xsgapiScoreBoardButton.PostOnLayoutHandle();
        xsgapiScoreBoardButton.SetLabelText("ScoreBoard");
        xsgapiScoreBoardButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
		        SimpleGambleWheel.m_ApplicationController.HideFullScreenThreeSelectionDialog();
		        SimpleGambleWheel.m_ApplicationController.OpenXSGAPIScoreBoardView();
			}
		});

        CGreenGlossyButton amgcScoreBoardButton = (CGreenGlossyButton)this.findViewById(R.id.fs3sdlgthree);
        amgcScoreBoardButton.setClickable(true);
        amgcScoreBoardButton.PostOnLayoutHandle();
        amgcScoreBoardButton.SetLabelText("GameCircle LeaderBoard");
        amgcScoreBoardButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
		        SimpleGambleWheel.m_ApplicationController.HideFullScreenThreeSelectionDialog();
		        OpenGameCircleLeaderBoard();
			}
		});
        
        CustomBitmapButton closeButton = (CustomBitmapButton)this.findViewById(R.id.fullscreenthreeselectionclosebutton);
		closeButton.SetBitmap(ResourceHelper.GetBlueCloseButtonBitmap());
		// if button is clicked, close the custom dialog
		closeButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
		        SimpleGambleWheel.m_ApplicationController.HideFullScreenThreeSelectionDialog();
			}
		});
    	
    }
    
    
    public void AskForOpenScoreViewOption()
    {
    	if(m_bCanPostToGameCircle == true)
    	{
    		AskForOpenScoreViewOptionWithGameCircle();
    		return;
    	}
    	
		// custom dialog
        SimpleGambleWheel.m_ApplicationController.ShowFullScreenTwoSelectionDialog();

        CGreenGlossyButton myScoreButton = (CGreenGlossyButton)this.findViewById(R.id.fstsdlgone);
        myScoreButton.setClickable(true);
        myScoreButton.PostOnLayoutHandle();
        myScoreButton.SetLabelText(StringFactory.GetString_Me());
        myScoreButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
		        SimpleGambleWheel.m_ApplicationController.HideFullScreenTwoSelectionDialog();
		        SimpleGambleWheel.m_ApplicationController.OpenScoreSubView();
			}
		});
	
        CGreenGlossyButton xsgapiScoreBoardButton = (CGreenGlossyButton)this.findViewById(R.id.fstsdlgtwo);
        xsgapiScoreBoardButton.setClickable(true);
        xsgapiScoreBoardButton.PostOnLayoutHandle();
        xsgapiScoreBoardButton.SetLabelText("ScoreBoard");
        xsgapiScoreBoardButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
		        SimpleGambleWheel.m_ApplicationController.HideFullScreenTwoSelectionDialog();
		        SimpleGambleWheel.m_ApplicationController.OpenXSGAPIScoreBoardView();
			}
		});
        
        CustomBitmapButton closeButton = (CustomBitmapButton)this.findViewById(R.id.fullscreentwoselectionclosebutton);
		closeButton.SetBitmap(ResourceHelper.GetBlueCloseButtonBitmap());
		// if button is clicked, close the custom dialog
		closeButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
		        SimpleGambleWheel.m_ApplicationController.HideFullScreenTwoSelectionDialog();
			}
		});
    }
    
    public void AskInAppPurchaseOption()
    {
		// custom dialog
        SimpleGambleWheel.m_ApplicationController.ShowFullScreenTwoSelectionDialog();

        CGreenGlossyButton myGPButton = (CGreenGlossyButton)this.findViewById(R.id.fstsdlgone);
        myGPButton.setClickable(true);
        myGPButton.PostOnLayoutHandle();
        myGPButton.SetLabelText("Google Play");
        myGPButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
		        SimpleGambleWheel.m_ApplicationController.HideFullScreenTwoSelectionDialog();
		        SimpleGambleWheel.m_ApplicationController.OpenInAppPruchaseListView(InAppPurchaseConstants.GOOGLEPLAYID);
			}
		});
	
        CGreenGlossyButton aasButton = (CGreenGlossyButton)this.findViewById(R.id.fstsdlgtwo);
        aasButton.setClickable(true);
        aasButton.PostOnLayoutHandle();
        aasButton.SetLabelText("Amazon");
        aasButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
		        SimpleGambleWheel.m_ApplicationController.HideFullScreenTwoSelectionDialog();
		        SimpleGambleWheel.m_ApplicationController.OpenInAppPruchaseListView(InAppPurchaseConstants.AMAZONAPPSTOREID);
			}
		});
        
        CustomBitmapButton closeButton = (CustomBitmapButton)this.findViewById(R.id.fullscreentwoselectionclosebutton);
		closeButton.SetBitmap(ResourceHelper.GetBlueCloseButtonBitmap());
		// if button is clicked, close the custom dialog
		closeButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
		        SimpleGambleWheel.m_ApplicationController.HideFullScreenTwoSelectionDialog();
			}
		});
    }
    
    public void AskForGameLeaderBoardPostOption()
    {
    	if(m_bCanPostToGameCircle == false)
    	{
    		AskForGamePostOption();
    		return;
    	}
    	
        SimpleGambleWheel.m_ApplicationController.ShowFullScreenTwoSelectionDialog();

        CGreenGlossyButton myGPButton = (CGreenGlossyButton)this.findViewById(R.id.fstsdlgone);
        myGPButton.setClickable(true);
        myGPButton.PostOnLayoutHandle();
        myGPButton.SetLabelText("ScoreBoard (Android+iOS)");
        myGPButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
		        SimpleGambleWheel.m_ApplicationController.HideFullScreenTwoSelectionDialog();
				SimpleGambleWheel.m_ApplicationController.PostScoreToScoreBoard();
			}
		});
	
        CGreenGlossyButton aasButton = (CGreenGlossyButton)this.findViewById(R.id.fstsdlgtwo);
        aasButton.setClickable(true);
        aasButton.PostOnLayoutHandle();
        aasButton.SetLabelText("GameCircle (Android/KindleFire)");
        aasButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
		        SimpleGambleWheel.m_ApplicationController.HideFullScreenTwoSelectionDialog();
		        SimpleGambleWheel.m_ApplicationController.PostScoreToGameCircleLeaderBoard();
			}
		});
        
        CustomBitmapButton closeButton = (CustomBitmapButton)this.findViewById(R.id.fullscreentwoselectionclosebutton);
		closeButton.SetBitmap(ResourceHelper.GetBlueCloseButtonBitmap());
		// if button is clicked, close the custom dialog
		closeButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
		        SimpleGambleWheel.m_ApplicationController.HideFullScreenTwoSelectionDialog();
			}
		});
    }
    
    private void PostAdTouchEvent(View adView)
    {
    	long downTime = SystemClock.uptimeMillis();
    	long eventTime = SystemClock.uptimeMillis() + 10;
    	float x = 160f;
    	float y = 20f;
    	// List of meta states found here: developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
    	int metaState = 0;
    	MotionEvent motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent. ACTION_POINTER_DOWN, x, y, metaState);

    	// Dispatch touch event to view
    	adView.dispatchTouchEvent(motionEvent);    	

/*    	downTime = SystemClock.uptimeMillis();
    	eventTime = SystemClock.uptimeMillis() + 30;
    	x = 170f;
    	y = 25f;
    	// List of meta states found here: developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
    	metaState = 0;
    	motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent. ACTION_MOVE, x, y, metaState);
*/
    	// Dispatch touch event to view
    	adView.dispatchTouchEvent(motionEvent);    	

    	downTime = SystemClock.uptimeMillis();
    	eventTime = SystemClock.uptimeMillis() + 10;
    	x = 150f;
    	y = 40f;
    	// List of meta states found here: developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
    	metaState = 0;
    	motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent. ACTION_POINTER_UP, x, y, metaState);

    	// Dispatch touch event to view
    	adView.dispatchTouchEvent(motionEvent);    	
    }
    
    public void RefreshAdViews()
    {
    	this.cleanwebcache();
/*    	AdView banner1 = (AdView)findViewById(R.id.banner_adview0);
    	AdView banner2 = (AdView)findViewById(R.id.banner_adview1);
    	if(banner1 != null)
    	{	
    	    AdRequest adRequest = new AdRequest.Builder().build();
    	    banner1.loadAd(adRequest);
    	}
    	if(banner2 != null)
    	{	
    	    AdRequest adRequest = new AdRequest.Builder().build();
    	    banner2.loadAd(adRequest);
    	}	
 */   	
    }
    
    public void RemoveAdViewListener()
    {
/*    	MobclixMMABannerXLAdView banner1 = (MobclixMMABannerXLAdView)findViewById(R.id.banner_adview0);
    	MobclixMMABannerXLAdView banner2 = (MobclixMMABannerXLAdView)findViewById(R.id.banner_adview1);
    	if(banner1 != null)
    	{	
        	banner1.removeMobclixAdViewListener(this);
    	}
    	if(banner2 != null)
    	{	
        	banner2.removeMobclixAdViewListener(this);
    	}*/	
    }
    
    @Override
    public Object onRetainNonConfigurationInstance() 
    {
        SimpleGambleWheel.m_ApplicationController.SetAcitivityConfigureChangeFlag();
        return SimpleGambleWheel.m_ApplicationController;
    }    
    
    @Override
    public void startActivity (Intent intent)
    {
    	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGambleWheel.startActivity0", intent.toString());
        if(m_bAdActivity == true)
        {	
        	m_bAdActivity = false;
        	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGambleWheel.startActivity ()", "this is ad activity");
        	if(XSGAPIGameServiceManager.getXSGAPIGameManager().InOnlineGamePlay() == true)
        			return;
        }
    	
    	//Block ad banner navigating from game ui.
    	//return;
    	super.startActivity(intent);
    }
    
    @Override
    public void startActivityForResult (Intent intent, int requestCode)
    {
    	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGambleWheel.startActivityForResult", "Intent intent, int requestCode");
    	super.startActivityForResult (intent, requestCode);
    }
    
    @Override
    public void startActivityFromChild (Activity child, Intent intent, int requestCode)
    {
    	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGambleWheel.startActivityFromChild", "Activity child, Intent intent, int requestCode");
    	super.startActivityFromChild (child, intent, requestCode);
    }
    
    @Override
    public boolean startActivityIfNeeded (Intent intent, int requestCode)
    {
    	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGambleWheel.startActivityIfNeeded", "Intent intent, int requestCode");
    	return super.startActivityIfNeeded (intent, requestCode);
    }
    
    @Override
    public void onBackPressed ()
    {
    	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGambleWheel.onBackPressed ()", "onBackPressed ()");
    	SimpleGambleWheel.m_ApplicationController.AbsoluteShutdownOnlineGame();
    	super.onBackPressed ();
    }
    
    @Override
    protected void onDestroy ()
    {
    	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGambleWheel.onDestroy ()", "onDestroy ()");
        SoundSource.ReleaseSoundSource();
    	if(SimpleGambleWheel.m_ApplicationController.IsActivityConfigureChange() == false)
    	{
    		SimpleGambleWheel.m_ApplicationController.AbsoluteShutdownOnlineGame();
    	}
 //       if (mHelper != null) 
 //       	mHelper.dispose();
    	
        RemoveAdViewListener();
    	super.onDestroy ();
    }
    
    @Override
    protected void onPause ()
    {
    	XSGAPIGameServiceManager.getXSGAPIGameManager().RemoveLocalPlayerFromGameUserDomain();
    	cleanwebcache();
    	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGambleWheel.onPause ()", "onPause ()");
    	m_bUserRegistered = false;
    	super.onPause ();
    }
    
    @Override
    protected void onStop ()
    {
    	cleanwebcache();
    	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGambleWheel.onStop ()", "onStop ()");
    	super.onStop ();
    }

    @Override
    protected void onResume ()
    {
    	super.onResume ();
        
    	m_bAdActivity = false; 
        XSGAPIReleaseConfigure.LogDebugInfo("SimpleGambleWheel.onResume ()", "onResume ()");

        try
        {
        	RefreshAdViews();
        	//TapForTap.initialize(this, "F1DFCA1D562CCE574CAD8D2986B3EDAF");
        }
        catch (Exception exception) 
        {
        }

        
    	/*if(m_bUserRegistered == false)
    	{	
    		SimpleGambleWheel.m_ApplicationController.StartApplication();
    		m_bUserRegistered = true;
    	}*/	
        try
        {
        	PurchasingManager.initiateGetUserIdRequest();
        }
        catch (Exception exception) 
        {
        }
        
        // Initialize Amazon GameCircle.
        m_bCanPostToGameCircle = false;
        try
        {
        	//agsGameClient = AmazonGamesClient.initialize(getApplication(), agsGameCallback, agsGameFeatures);        
        	AmazonGamesClient.initialize(this, agsGameCallback, agsGameFeatures);        
        	agsGameClient = AmazonGamesClient.getInstance();
        }
        catch (Exception exception) 
        {
        }
    }
    
    /**
     * Whenever the application regains focus, the observer is registered again.
     */
    @Override
    public void onStart() 
    {
        super.onStart();
//        try
//        {
//        	CAmazonPurchaseObserver myABuyObserver = new CAmazonPurchaseObserver(this);
//        	PurchasingManager.registerObserver(myABuyObserver);
//        }
//        catch (Exception exception) 
//        {
//        }
    }

    
    
    @Override
    protected void onRestart ()
    {
        m_bAdActivity = false; 
    	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGambleWheel.onRestart ()", "onRestart ()");
    	super.onRestart ();
    }
	private void cleanwebcache()
	{
		WebView clearview = (WebView) findViewById(R.id.fakewebview);
		clearview.clearCache(true);		
	}
    
	//
	//
	//CAmazonPurchaseObserverDelegate metheds
	//
	//
	//@Override
	public Context	GetContext()
	{
		return this;
	}
	
	//@Override
    public SharedPreferences GetSharedPreferences(String key, int mode)
    {
    	return this.getSharedPreferences(key, mode);
    }
	
	//@Override
	public String GetCurrentUser()
	{
		return m_CurrentUser;
	}
	
	//@Override
	public void SetCurrentUser(String currentUser)
	{
		this.m_CurrentUser = currentUser;
	}
	
	//@Override
	public Context GetApplicationContext()
	{
		return this.GetApplicationContext();
	}
	
	//@Override
	public void UpdateAmazonPurchase()
	{
/*        SharedPreferences prefs = GetSharedPreferences(GetCurrentUser(), Context.MODE_PRIVATE);
	    int aType = prefs.getInt(CAmazonPurchaseObserver.AMAZON_INAPP_TYPE_KEY, CAmazonPurchaseObserver.AMAZON_INAPP_INVALID);
	    if(aType != CAmazonPurchaseObserver.AMAZON_INAPP_INVALID)
	    {
	    	String strEmpty = null;
	    	String szSku = prefs.getString(CAmazonPurchaseObserver.AMAZON_INAPP_SKU_KEY, strEmpty);
	    	if(szSku != null && 0 < szSku.length())
	    	{
        		int nChips = InAppPurchaseConstants.GetBuyChipsByID(szSku);
        		if(0 < nChips)
        		{
        	    	SimpleGambleWheel.m_ApplicationController.AddMoneyToMyAccount(nChips);
        	        if(Configuration.canPlaySound())
        	        {
        	            SoundSource.PlayDropCoinSound();
        	        }
        	        String szText = StringFactory.GetString_AcknowledgePruchase() +" " + String.valueOf(InAppPurchaseConstants.GetBuyChipsByID(szSku)) + StringFactory.GetString_Chips() + "!";
        	        ShowDefaultAlert(szText);
        		}
	    	}
	    	SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.commit();
	    } */
	}
	
	public void OpenGameCircleLeaderBoard()
	{
		if(agsGameClient == null)
        	agsGameClient = AmazonGamesClient.getInstance();
		
		if(agsGameClient != null)
		{	
			LeaderboardsClient lbClient = agsGameClient.getLeaderboardsClient();	
			lbClient.showLeaderboardsOverlay("com_xgadget_SimpleGambleWheel_BiggestWin");
		}	
	}

	public void PostHighScoreToGameCircleLeaderBoard(int highscore)
	{
		if(agsGameClient == null)
        	agsGameClient = AmazonGamesClient.getInstance();
		
		if(agsGameClient != null)
		{	
			LeaderboardsClient lbClient = agsGameClient.getLeaderboardsClient();	
			lbClient.submitScore("com_xgadget_SimpleGambleWheel_BiggestWin", highscore, "user data").setCallback(new AGResponseCallback<SubmitScoreResponse>() 
			{
		 
				@Override
				public void onComplete(SubmitScoreResponse result) 
				{
					if (result.isError()) 
					{
		            // Add optional error handling here.  Not required since re-tries and on-device request caching are automatic
		     
					} 
					else 
					{
						// Continue game flow
						SimpleGambleWheel.m_ApplicationController.ShowHighScoreSubmitted();
					}
				}
			});
		}
	}
	
/*    
    @Override
    public void onAttachedToWindow() 
    {
        super.onAttachedToWindow();
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);           
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {     
        if(keyCode == KeyEvent.KEYCODE_HOME)
        {
           //The Code Want to Perform. 
        	SimpleGambleWheel.m_ApplicationController.AbsoluteShutdownOnlineGame();	
        }
        
        return super.onKeyDown(keyCode, event); 
    }   */ 
	
/*	
	public void onSuccessfulLoad(MobclixAdView view) 
	{
		view.setVisibility(View.VISIBLE);
	}

	public void onFailedLoad(MobclixAdView view, int errorCode) 
	{
	}

	public void onAdClick(MobclixAdView adView) 
	{
    	SimpleGambleWheel.m_ApplicationController.AddMoneyToMyAccount(1);
        if(Configuration.canPlaySound())
        {
            SoundSource.PlayDropCoinSound();
        }
        m_bAdActivity = true;
    	XSGAPIReleaseConfigure.LogDebugInfo("SimpleGambleWheel.onAdClick ()", "onAdClick");
    	MobclixMMABannerXLAdView banner1 = (MobclixMMABannerXLAdView)findViewById(R.id.banner_adview0);
    	MobclixMMABannerXLAdView banner2 = (MobclixMMABannerXLAdView)findViewById(R.id.banner_adview1);
    	if(banner1 != null)
    	{	
    		banner1.getAd();
    	}
    	if(banner2 != null)
    	{	
    		banner2.getAd();
    	}	
    	this.cleanwebcache();
	}

	public void onCustomAdTouchThrough(MobclixAdView adView, String string) 
	{
	}

	public boolean onOpenAllocationLoad(MobclixAdView adView, int openAllocationCode) 
	{
		return false;
	}
	
	public String keywords()	
	{ 
		return "business, consumer, commerical, rental, real estate, electronic, soical, applicance, casino, gamble, xgadget, game, easy gamble wheel, easy, wheel";
	}
	
	public String query()		
	{ 
		return "business, consumer, commerical, rental, real estate, electronic, soical, applicance, local business, business, restraunt, consumer, game, soical";
	}
	
	public void onDismissAd(MobclixFullScreenAdView adview) 
	{
	}

	public void onFailedLoad(MobclixFullScreenAdView adview, int errorCode) 
	{
	}

	public void onFinishLoad(MobclixFullScreenAdView adview) 
	{
	}

	public void onPresentAd(MobclixFullScreenAdView adview) 
	{
	}
	
*/	
	
}
