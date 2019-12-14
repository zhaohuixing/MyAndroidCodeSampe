package com.e_gadget.MindFire;

import java.util.Random;
import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class CGameTouchLayer 
{
	private static final int TOUCH_SPEED_FAST_THRESHOLD = 2;
	private static final int TOUCH_SPEED_MEDIUM_THRESHOLD = 6;
	private static final int TOUCH_SPEED_SLOW_THRESHOLD = 16;
	
	private	static int 		m_TStartX = 0;
	private	static int 		m_TStartY = 0;
	private	static int 		m_TEndX = 0;
	private	static int 		m_TEndY = 0;
	private	static int 		m_TCursorX = 0;
	private	static int 		m_TCursorY = 0;
	private	static int 		m_TTimer = 0;

	private static Vector<CStarAnimator> m_Stars = null;
	
	public static void Initialize()
	{
		m_Stars = new Vector<CStarAnimator>();
		Vector<Drawable> stars1 = new Vector<Drawable>();
		CGameHelper.LoadStarsImage(stars1);
		CStarAnimator star1 = new CStarAnimator(stars1);
		m_Stars.add(star1);
		
		Vector<Drawable> stars2 = new Vector<Drawable>();
		CGameHelper.LoadStarsImage(stars2);
		CStarAnimator star2 = new CStarAnimator(stars2);
		m_Stars.add(star2);
		
		Vector<Drawable> stars3 = new Vector<Drawable>();
		CGameHelper.LoadStarsImage(stars3);
		CStarAnimator star3 = new CStarAnimator(stars3);
		m_Stars.add(star3);

		Vector<Drawable> stars4 = new Vector<Drawable>();
		CGameHelper.LoadStarsImage(stars4);
		CStarAnimator star4 = new CStarAnimator(stars4);
		m_Stars.add(star4);

		Vector<Drawable> stars5 = new Vector<Drawable>();
		CGameHelper.LoadStarsImage(stars5);
		CStarAnimator star5 = new CStarAnimator(stars5);
		m_Stars.add(star5);
		
	}
	

	public static void Reset()
	{
		m_TStartX = 0;
		m_TStartY = 0;
		m_TEndX = 0;
		m_TEndY = 0;
		m_TCursorX = 0;
		m_TCursorY = 0;
		m_TTimer = 0;
	}

	public static void TouchStart(int x, int y)
	{
		m_TStartX = x;
		m_TStartY = y;
		m_TCursorX = m_TStartX;
		m_TCursorY = m_TStartY;
		m_TEndX = m_TStartX;
		m_TEndY = m_TStartY;
		m_TTimer = 1;
		CGameState.EnterTouchMode();
		
		ResetStarsPosition();
		
	}

	public static void TouchMove(int x, int y)
	{
		m_TCursorX = x;
		m_TCursorY = y;
		m_TEndX = m_TCursorX;
		m_TEndY = m_TCursorY;
		ResetStarsPosition();
	}

	public static void TouchStop(int x, int y)
	{
		m_TCursorX = x;
		m_TCursorY = y;
		m_TEndX = m_TCursorX;
		m_TEndY = m_TCursorY;
		CGameState.LeaveTouchMode();
	}
	
	public static void OnTimerEvent()
	{
		++m_TTimer;
		if(m_Stars != null)
		{
			if(1 < m_Stars.size())
			{	
				m_Stars.elementAt(m_TTimer%m_Stars.size()).OnTimerEvent();
			}
			else if(1 == m_Stars.size())
			{
				m_Stars.elementAt(0).OnTimerEvent();
			}
		}
	}

	public static void OnDraw(Canvas canvas)
	{
		if(1 < m_Stars.size())
		{	
			for(int i = 0; i < m_Stars.size(); ++i)
			{
				m_Stars.elementAt(i).OnDraw(canvas);
			}
		}
		else if(1 == m_Stars.size())
		{
			m_Stars.elementAt(0).OnDraw(canvas);
		}
	}
	
	private static int GetDistanceFactor()
	{
		int dx = m_TEndX - m_TStartX;
		int dy = m_TEndY - m_TStartY;
		
		int dt = dx*dx + dy*dy;
		if(dt == 0)
			return -1;
		
		int ds = CDealLayout.WindowWidth()*CDealLayout.WindowWidth() + CDealLayout.WindowHeight()*CDealLayout.WindowHeight();
		
		int dRet = ds/dt;
		
		return dRet;
		
	}
	
	private static void ResetStarsPosition()
	{
		if(m_Stars != null)
		{	
			for(int i = 0; i < m_Stars.size(); ++i)
			{
				ResetStarPosition(i);
			}
		}			
	}
	
	private static void ResetStarPosition(int i)
	{
		if(0 <= i && i < m_Stars.size())
		{
			int nseed = m_TCursorX*m_TCursorY*i*26;
			Random rand = new Random(nseed);
			int nRand = rand.nextInt();
			int sX = nRand%(CDealLayout.GetStarWidth()*5/3);
			if(nRand%2 == 1)
				sX *= -1;
			
			nseed = m_TCursorX*m_TCursorY*(m_Stars.size()-i)*91;
			rand = new Random(nseed);
			nRand = rand.nextInt();
			int sY = nRand%(CDealLayout.GetStarHeight()*5/3); 
			if(nRand%17 == 7)
				sX *= -1;
 
			int cx = m_TCursorX + sX;
			int cy = m_TCursorY + sY;
			
			int w = CDealLayout.GetStarWidth()/2;
			int h = CDealLayout.GetStarHeight()/2;
			
			Rect rect = new Rect(cx-w, cy-h, cx+w, cy +h);
			m_Stars.elementAt(i).SetBoundary(rect);
		}
	}
}
