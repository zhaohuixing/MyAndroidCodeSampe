package com.e_gadget.MindFire;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class CGameView extends View 
{
	/* The game thread object */
	private TextView 		m_StatusText;
	private CMindFire 		m_Application;
	private Handler 		m_MsgHandler;
	public CGame 			m_Game;
	boolean					m_bInitialized;
	private Handler 		m_TimerHandler;
	private Runnable 		m_UpdateTimeTask;

	
	private int				m_MsgShowTimer;
	
	private static final int  MSG_SHOW_TIME = 200;

	private static final int  TIMER_INTERVAL_MS = 50;
	
	public CGameView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		// register our interest in hearing about changes to our surface
		
		// create thread only; it's started in surfaceCreated()
		m_bInitialized = false;
		
		CGameHelper.SetGameContext(context);
		
		m_MsgHandler = new Handler() 
		{
			@Override
			public void handleMessage(Message m) 
			{
				m_StatusText.setVisibility(m.getData().getInt("viz"));
				m_StatusText.setText(m.getData().getString("text"));
			}
		};
		
		
		if(context != null)
		{
			String resTitle = context.getString(R.string.text_result_title);
			CGameHelper.SetResultTitle(resTitle);
		}
		setFocusable(true); // make sure we get key events
		m_Game = new CGame();
		m_Game.SetParent(this);
		m_TimerHandler = new Handler();
		m_UpdateTimeTask = null;
		StartTimer();
	}

	protected void  onLayout(boolean changed, int left, int top, int right, int bottom)
	{
		super.onLayout(changed, left, top, right, bottom);
		int width = right - left;
		int height = bottom - top;
		CDealLayout.Initialize(width, height);
		if(m_bInitialized == false)
		{
			CLayoutCursor cursor = new CLayoutCursor(); 
			CGameHighLight.Initialize(cursor);
		}	
		m_Game.ReloadResource();
		if(m_bInitialized == false)
		{
			DoStart();
			m_bInitialized = true;
		}
		else
		{
			invalidate();
		}
	}
	
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		CDealLayout.Initialize(w, h);
		if(m_bInitialized == false)
		{
			CLayoutCursor cursor = new CLayoutCursor(); 
			CGameHighLight.Initialize(cursor);
		}	
		m_Game.ReloadResource();
		if(m_bInitialized == false)
		{
			DoStart();
			m_bInitialized = true;
		}	
		else
		{
			invalidate();
		}
	}
	
	protected void onDraw(Canvas canvas)
	{
		if(m_Game != null)
		{
			m_Game.OnDraw(canvas);
		}
	}
	
	public void SetSavedInstanceState(Bundle savedState)
	{
		invalidate();
	}
	
	public void AttachApplication(CMindFire app)
	{
		m_Application = app;
	}
	
	/* Query game thread object. */

	/* Attach the status text view. */
	public void setStatusText(TextView sText) 
	{
		m_StatusText = sText;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		boolean bRet = false;
		
		if(CGameState.IsShowMessage())
		{
			HideTextMessage();
		}
		
		OnTouchLayerEvent(event);	
		if(CGameState.IsGameReady())
		{
			StartNewGame();
			CGameState.SetGameRunning();
			bRet = true;
		}
		else if(CGameState.IsGameRunning() || CGameState.IsGameResult())
		{
			bRet = onGameTouchEvent(event);	
		}
		invalidate(); 
		return bRet;
	}

	private boolean onGameTouchEvent(MotionEvent event)
	{
		boolean bRet = false;
	
		if(m_Game != null)
		{
			m_Game.onTouchEvent(event);
			bRet = true;
		}
		
		return bRet;
	}
	
	private void OnTouchLayerEvent(MotionEvent event)
	{
        int x = (int)event.getX();
        int y = (int)event.getY();
        
        switch (event.getAction()) 
        {
            case MotionEvent.ACTION_DOWN:
           		CGameTouchLayer.TouchStart(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
            	if(CGameState.IsTouchMode() == true)
            		CGameTouchLayer.TouchMove(x, y);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            	if(CGameState.IsTouchMode() == true)
            		CGameTouchLayer.TouchStop(x, y);
                break;
        }
	}
	
	/*
	 * Key-down events.
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent msg) 
	{
		boolean bRet = false;
		
		int nState = CGameState.GetGameState();
		if(CGameState.IsGameRunning())
		{
			bRet = handleGameKeyDown(keyCode, msg);	
		}
		else if(nState == CGameState.GAME_RESULT)
		{
		  	bRet = handleResultKeyDown(keyCode, msg);
		}
		invalidate(); 
		 
		return bRet;
	}

	/*
	 * Key-up event.
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent msg) 
	{
		boolean bRet = false;
		
		 bRet = (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || 
				 keyCode == KeyEvent.KEYCODE_DPAD_RIGHT || 
				 keyCode == KeyEvent.KEYCODE_DPAD_DOWN || 
				 keyCode == KeyEvent.KEYCODE_DPAD_LEFT || 
				 keyCode == KeyEvent.KEYCODE_DPAD_UP);
	 	if(CGameState.IsGameReady() && bRet)
	 	{
		  	StartNewGame();
		  	CGameState.SetGameRunning();
	 	}
	 	else if(CGameState.IsGameRunning())
	 	{
		  	bRet = handleGameKeyUp(keyCode, msg);	
	 	}
	 	else if(CGameState.IsGameResult())
	 	{
		  	bRet = handleResultKeyUp(keyCode, msg);
	 	}
		invalidate(); 
		return bRet;
	}

	private boolean handleGameKeyUp(int keyCode, KeyEvent msg)
	{
		boolean bRet = false;
		if(CGameState.IsGameRunning())// && !m_Game.IsGameEnd())
		{
			bRet = handleDealKeyUp(keyCode, msg);
		}
		
		return bRet;
	}
	
	private boolean handleGameKeyDown(int keyCode, KeyEvent msg)
	{
		boolean bRet = false;
		if(CGameState.IsGameRunning())// && !m_Game.IsGameEnd())
		{
			bRet = handleDealKeyDown(keyCode, msg);
		}
		else if(CGameState.IsGameResult())
		{	
			bRet = handleResultKeyDown(keyCode, msg);
		}
		
		return bRet;
	}
	
	private boolean handleDealKeyUp(int keyCode, KeyEvent msg)
	{
		boolean bRet = false;
		if(m_Game != null && m_Game.m_CurDeal != null)
		{
			m_Game.onKeyUpEvent(keyCode);
			bRet = true;
		}
		return bRet;
	}
	
	private boolean handleDealKeyDown(int keyCode, KeyEvent msg)
	{
		boolean bRet = false;
		if(m_Game != null && m_Game.m_CurDeal != null)
		{
			m_Game.onKeyDownEvent(keyCode);
		}
		return bRet;
	}
		
	private boolean handleResultKeyUp(int keyCode, KeyEvent msg)
	{
		boolean bRet = true;
		if(m_Game != null)
		{
			m_Game.onResultKeyUp();
		}
		return bRet;
	}
	
	private boolean handleResultKeyDown(int keyCode, KeyEvent msg)
	{
		boolean bRet = true;
		if(m_Game != null && m_Game.m_CurDeal != null)
		{
			m_Game.onResultKeyDown();
		}
		return bRet;
	}
	
	public void ShowWarnMessage()
	{
/*		if(m_GameThread != null)
		{
			Context context = this.getContext();
			if(context != null)
			{
				Resources res = context.getResources();
				CharSequence str = "";
	            str = res.getText(R.string.text_warn);
	            ShowTextMessage(str);
			}
		} */ 
	}

	public void ShowHelpText()
	{
		Context context = this.getContext();
		if(context != null)
		{
			Resources res = context.getResources();
			CharSequence str = "";
            str = res.getText(R.string.text_help);
            ShowTextMessage(str);
		}
	}
	
	public void ShowTextMessage(CharSequence text)
	{
		if(m_MsgHandler != null)
		{	
			Message msg = m_MsgHandler.obtainMessage();
			Bundle b = new Bundle();
			b.putString("text", text.toString());
			b.putInt("viz", View.VISIBLE);
			msg.setData(b);
			m_MsgHandler.sendMessage(msg);
			CGameState.ShowMessage();
			m_MsgShowTimer = 0;
		}
	}
	
	public void HideTextMessage()
	{
		if(m_MsgHandler != null)
		{	
			Message msg = m_MsgHandler.obtainMessage();
			Bundle b = new Bundle();
            b.putString("text", "");
            b.putInt("viz", View.INVISIBLE);
            msg.setData(b);
			m_MsgHandler.sendMessage(msg);
			CGameState.HideMessage();
			m_MsgShowTimer = 0;
		}
	}
	
	public void NewGame()
	{
		m_Game.GetNextNewGame();
	}
	
	public void SaveInstanceState(Bundle outState)
	{
	}
	
	public void DoStart()
	{
		CGameType.Initialize();
		CGameState.Initialize();
		m_Game.DoStart();
	}
	
	private void StartNewGame()
	{
		DoStart();
		m_Game.NewDeal();
	}

	private void StartTimer()
	{
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
		if(CGameState.IsShowMessage())
		{
			++m_MsgShowTimer;
			if(MSG_SHOW_TIME < m_MsgShowTimer)
			{
				HideTextMessage();
			}
		}
		m_Game.OnTimerEvent();
		invalidate(); 
	}
	
	public void SaveGameScore(int nScore)
	{
		if(m_Application != null)
		{
			m_Application.SaveGameScore(nScore);
		}
	}
}
