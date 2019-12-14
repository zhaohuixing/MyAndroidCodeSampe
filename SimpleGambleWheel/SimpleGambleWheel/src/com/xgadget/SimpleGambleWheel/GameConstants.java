package com.xgadget.SimpleGambleWheel;

public class GameConstants 
{
	//Player online playing state
	public static final int GAME_PLAYER_STATE_NORMAL = 0;
	public static final int GAME_PLAYER_STATE_SUSPEND = 1;

	public static final int GAME_ONLINE_STATE_OFFLINE = 0;
	public static final int GAME_ONLINE_STATE_ONLINE = 1;
	
	public static final int GAME_TYPE_8LUCK = 0;
	public static final int GAME_TYPE_6LUCK = 1;
	public static final int GAME_TYPE_4LUCK = 2;
	public static final int GAME_TYPE_2LUCK = 3;

	public static final int GAME_THEME_ANIMAL = 0;
	public static final int GAME_THEME_FOOD  = 1;
	public static final int GAME_THEME_FLOWER = 2;
	public static final int GAME_THEME_NUMBER = 3;
	public static final int GAME_THEMECOUNT = 4;
	
	public static final int GAME_ONLINE_PLAYER_STATE_UNKNOWN = -1;
	public static final int GAME_ONLINE_PLAYER_STATE_READY = 0;
	public static final int GAME_ONLINE_PLAYER_STATE_RUN = 1;
	public static final int GAME_ONLINE_PLAYER_STATE_RESULT = 2;
	public static final int GAME_ONLINE_PLAYER_STATE_RESET = 3;


	public static final int GAME_STATE_READY = 0;
	public static final int GAME_STATE_RUN = 1;
	public static final int GAME_STATE_RESULT = 2;
	public static final int GAME_STATE_RESET = 3;   //For in mult-player mode, all player reset state from result to ready

	public static final int GAME_POINTER_SPIN_FAST = 0;
	public static final int GAME_POINTER_SPIN_MEDIUM = 1;
	public static final int GAME_POINTER_SPIN_SLOW = 2;
	public static final int GAME_POINTER_SPIN_VIBRATION = 3;

	public static final int GAME_POINTER_RUN_ANGLE_STEP = 2;
	public static final int GAME_POINTER_VIBRATION_ANGLE_UNIT = 6;
	public static final int GAME_POINTER_MEDIUM_ANGLE_UNIT = 75;
	public static final int GAME_POINTER_FAST_ANGLE_UNIT = 90; //130.0f
	public static final int GAME_POINTER_FAST_ANGLE_MAXCOUNT = 329;
	
	public static final int GAME_BET_THRESHOLD_8LUCK = 1;
	public static final int GAME_BET_THRESHOLD_6LUCK = 10;
	public static final int GAME_BET_THRESHOLD_4LUCK = 20;
	public static final int GAME_BET_THRESHOLD_2LUCK = 40;

	public static final int GAME_PLAYTURN_TYPE_SEQUENCE = 0;
	public static final int GAME_PLAYTURN_TYPE_MAXBET = 1;
	
	public static final int PURCHASED_CHIPS_1 = 1000;
	public static final int GAME_DEFAULT_CHIPSINPACKET = PURCHASED_CHIPS_1; 

	//public static final int PRODUCT_ID_100CHIPS     @"com.xgadget.SimpleGambleWheel.1000Chips"

	//public static final int SANDBOX			NO

	//public static final int MAX_FALSHADDIS  PLAY       15   

	public static final int GREETING_SHOW_TIME = 15;

	public static final int OFFLINE_PLAYER_TURN_DELAY = 1800;

	public static final int ONLINE_GAME_NONGKMASTER_CHECK_TIMELINE = 10000;

	public static final int ONLINE_GAME_MESSAGE_DISPLAY_TIMELINE = 10;
	
	public static final long AVATAR_ANIMATION_TIMEINTERNVAL = 300;  //millisecond

	public static final int		m_GameTimerElapse = 10;		//millisecond

	public static final int		m_PlayerMenuDisplayTimerElapse = 10000;		//millisecond
}
