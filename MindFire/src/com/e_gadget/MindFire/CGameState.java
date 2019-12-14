package com.e_gadget.MindFire;

/*
 * 1st level state:
 * 		0 (GAME_READY): the entry state of a game 
 * 		1 (GAME_RUNNING): the state of a game in running 
 * 		2 (GAME_PAUSE): the state of a game in pause 
 * 		3 (GAME_RESULT): the state of in the end of a game and reporting the result list
 * 
 * 2nd level state:
 * 		0: (GAME_SUBSTATE_NONE): 			none
 * 		1: (GAME_SUBSTATE_DNDREADY):		beginning of the drag and drop  
 * 		2: (GAME_SUBSTATE_DND):			in the drag and drop  
 * 		3: (GAME_SUBSTATE_ANIMATION):		in the animation  
 *   
 */

public class CGameState 
{
	public static final int ACTIVITY_GAME = 0;
	public static final int ACTIVITY_INFO = 1;
	public static int ACTIVITY_STATE = ACTIVITY_GAME;

	public static final int GAME_SPLASHSCREEN = 0;
	public static final int GAME_READY = 1;
	public static final int GAME_RUNNING = 2;
	public static final int GAME_RESULT = 3;
	public static final int GAME_STATE_FIRST = GAME_SPLASHSCREEN;
	public static final int GAME_STATE_LAST = GAME_RESULT;

	public static int GAME_STATE = GAME_SPLASHSCREEN;

	//The second level state: animation in running state or result state
	public static final int GAME_SUBSTATE_NONE = 0;
	public static final int GAME_SUBSTATE_DNDREADY = 1;  
	public static final int GAME_SUBSTATE_DND = 2;  
	public static final int GAME_SUBSTATE_ANIMATION = 3;  
	
	public static int GAME_SECOND_STATE = GAME_SUBSTATE_NONE;
	
	public static boolean GAME_ANIMATABLE = true; 

	private static int GAME_ANIMATION_SEMAPHORE = 0;
	public static boolean m_bShowMsg = false;
	
	public static final int TOUCH_MODE_NONE = 0;
	public static final int TOUCH_MODE_ACTION = 1;
	public static int TOUCH_MODE = TOUCH_MODE_NONE;

	public static void Initialize()
	{
		if(CGameType.IsSplashScreenEnable() == true)
			GAME_STATE = GAME_SPLASHSCREEN;
		else
			GAME_STATE = GAME_READY;
		
		TOUCH_MODE = TOUCH_MODE_NONE;
		ACTIVITY_STATE = ACTIVITY_GAME;
		m_bShowMsg = false;
	}
	
	public static void EnterTouchMode()
	{
		TOUCH_MODE = TOUCH_MODE_ACTION;
	}

	public static void LeaveTouchMode()
	{
		TOUCH_MODE = TOUCH_MODE_NONE;
	}
	
	public static void SetGameState(int nState) 
	{
		if (GAME_STATE_FIRST <= nState && nState <= GAME_STATE_LAST) 
		{
			GAME_STATE = nState;
		}
	}

	public static int GetGameState() 
	{
		return GAME_STATE;
	}

	public static void SetGameRunning() 
	{
		GAME_STATE = GAME_RUNNING;
	}

	public static void SetGameSplashScreen() 
	{
		GAME_STATE = GAME_SPLASHSCREEN;
	}

	public static boolean IsGameSplashScreen() 
	{
		boolean bRet = (GAME_STATE == GAME_SPLASHSCREEN);
		return bRet;
	}

	public static void SetGameResult() 
	{
		GAME_STATE = GAME_RESULT;
	}

	public static void Reset() 
	{
		GAME_STATE = GAME_READY;
		TOUCH_MODE = TOUCH_MODE_NONE;
		m_bShowMsg = false;
	}

	public static boolean IsGameRunning() 
	{
		boolean bRet = (GAME_STATE == GAME_RUNNING);
		return bRet;
	}

//	public static boolean IsGamePause() 
//	{
//		boolean bRet = (GAME_STATE == GAME_PAUSE);
//		return bRet;
//	}

	public static boolean IsGameResult() 
	{
		boolean bRet = (GAME_STATE == GAME_RESULT);
		return bRet;
	}

	public static boolean IsGameReady() 
	{
		boolean bRet = (GAME_STATE == GAME_READY);
		return bRet;
	}

	public static void SetGameReady()
	{
		GAME_STATE = GAME_READY;
	}
	
//	public static void SetAnimation(boolean bAnimatable)
//	{
//		GAME_ANIMATABLE = bAnimatable;
		//if(GAME_ANIMATABLE == false)
		//	GAME_SECOND_STATE = GAME_SUBSTATE_NONE;
//	}
	
	public static void EnterAnimation()
	{
		//if(GAME_SECOND_STATE == GAME_SUBSTATE_NONE)
		{
//			GAME_ANIMATION_SEMAPHORE = 1;
			GAME_SECOND_STATE = GAME_SUBSTATE_ANIMATION;
		}
//		else if(GAME_SECOND_STATE == GAME_SUBSTATE_ANIMATION)
//		{
//			GAME_ANIMATION_SEMAPHORE++;
//		}
	}
	
	public static void LeaveAnimation()
	{
		if(GAME_SECOND_STATE == GAME_SUBSTATE_ANIMATION)
		{
//			GAME_ANIMATION_SEMAPHORE--;
//			if(GAME_ANIMATION_SEMAPHORE <= 0)
//			{
				GAME_SECOND_STATE = GAME_SUBSTATE_NONE;
//				GAME_ANIMATION_SEMAPHORE = 0;
//			}
		}
	}
	

	public static boolean IsAnimatable()
	{
		return GAME_ANIMATABLE;
	}

	public static boolean InAnimation()
	{
		boolean bRet = false; 
		if(GAME_ANIMATABLE == false)
			return bRet;
		bRet = (GAME_SECOND_STATE == GAME_SUBSTATE_ANIMATION);
		return bRet;
	}
	
	public static boolean InDragAndDrop()
	{
		boolean bRet = false; 
		bRet = (GAME_SECOND_STATE == GAME_SUBSTATE_DNDREADY ||
				GAME_SECOND_STATE == GAME_SUBSTATE_DND);
		
		return bRet;
	}

	public static boolean InMovement()
	{
		boolean bRet = false; 
		bRet = (GAME_SECOND_STATE != GAME_SUBSTATE_NONE);
		return bRet;
	}
	
//	public static void StartAnimation()
//	{
//		if(GAME_ANIMATABLE == false)
//			return;
		
//		GAME_SECOND_STATE = GAME_SUBSTATE_ANIMATION;
//	}
	
//	public static void StopAnimation()
//	{
//		ResetSecondState();
//	}

	public static void StartDragAndDrop()
	{
		if(GAME_SECOND_STATE == GAME_SUBSTATE_ANIMATION)
			return;
		
		GAME_SECOND_STATE = GAME_SUBSTATE_DNDREADY;
	}

	public static void DragAndDropMoveOver()
	{
		if(GAME_SECOND_STATE == GAME_SUBSTATE_ANIMATION)
			return;
		GAME_SECOND_STATE = GAME_SUBSTATE_DND;
	}
	
	public static void StopDragAndDrop()
	{
		if(GAME_SECOND_STATE == GAME_SUBSTATE_ANIMATION)
			return;
		ResetSecondState();
	}

	private static void ResetSecondState()
	{
		GAME_SECOND_STATE = GAME_SUBSTATE_NONE;
	}
	
	public static int GetSecondState()
	{
		return GAME_SECOND_STATE;
	}
	
	public static boolean IsTouchMode() 
	{
		boolean bRet = (TOUCH_MODE == TOUCH_MODE_ACTION);
		return bRet;
	}

	public static boolean IsShowMessage()
	{
		return m_bShowMsg;
	}

	public static void ShowMessage()
	{
		m_bShowMsg = true;
	}

	public static void HideMessage()
	{
		m_bShowMsg = false;
	}

	public static int GetActivityState()
	{
		return ACTIVITY_STATE;
	}
	
	public static void SetGameActivityState()
	{
		ACTIVITY_STATE = ACTIVITY_GAME;
	}
	
	public static void SetInfoActivityState()
	{
		ACTIVITY_STATE = ACTIVITY_INFO;
	}

	public static boolean IsGameActivityState()
	{
		boolean bRet = (ACTIVITY_STATE == ACTIVITY_GAME);
		return bRet;
	}
	
	public static boolean IsInfoActivityState()
	{
		boolean bRet = (ACTIVITY_STATE == ACTIVITY_INFO);
		return bRet;
	}
	
}
