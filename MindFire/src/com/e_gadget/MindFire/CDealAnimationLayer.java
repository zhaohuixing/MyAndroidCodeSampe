package com.e_gadget.MindFire;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class CDealAnimationLayer 
{
	public Vector<CCardAnimator>			m_AnimatorList;
	private CGameSprite 					m_DealWinSprite;
	private CGameSprite 					m_DealLoseSprite;
    public CGame 							m_Controller;
	
	private boolean							m_bWinner;
	private int								m_MaxTimeClick;
	private int								m_nClick;
	private	int								m_MaxCount;
	private	int								m_nCount;

	private static final int				MAX_CLICK = 3;
	
	public CDealAnimationLayer()
	{
		m_AnimatorList = new Vector<CCardAnimator>();
		m_bWinner = false;
		m_DealWinSprite = null;
		m_DealLoseSprite = null;
		m_MaxTimeClick = MAX_CLICK;
		m_nClick = 0;
		m_MaxCount = 15;
		m_nCount = 0;
		m_Controller = null;
	}
	
	public CDealAnimationLayer(CGameSprite winner, CGameSprite loser)
	{
		m_AnimatorList = new Vector<CCardAnimator>();
		m_bWinner = false;
		m_DealWinSprite = winner;
		m_DealLoseSprite = loser;
		m_MaxTimeClick = MAX_CLICK;
		m_nClick = 0;
		m_MaxCount = 15;
		m_nCount = 0;
		m_Controller = null;
		m_DealWinSprite.SetDualAnimation();
		m_DealLoseSprite.SetDualAnimation();
	}
	
	public void SetController(CGame controller)
	{
		m_Controller = controller;
	}
	
	public void SetSpriteState(boolean b)
	{
		m_bWinner = b;
	}
	public void Initialize()
	{
		m_DealWinSprite = new CGameSprite(CGameSprite.SPRITE_TYPE_ANIMATION);
		m_DealLoseSprite = new CGameSprite(CGameSprite.SPRITE_TYPE_ANIMATION);
		
		int i;
		int nFrames = CGameHelper.GetDealSpriteFrames();
		
		for(i = 0; i < nFrames; ++i)
		{	
			m_DealWinSprite.AddAnimationImage(CGameHelper.GetDealSpriteAnimationImage(0, i));
			m_DealLoseSprite.AddAnimationImage(CGameHelper.GetDealSpriteAnimationImage(1, i));
		}	
		m_DealWinSprite.SetClipRect(CDealLayout.GetDealSpriteRect());
		m_DealLoseSprite.SetClipRect(CDealLayout.GetDealSpriteRect());
		m_DealWinSprite.SetDualAnimation();
		m_DealLoseSprite.SetDualAnimation();
	}
	
	public void Reset()
	{
		if(m_DealWinSprite != null)
			m_DealWinSprite.ResetAnimationState();
		
		if(m_DealLoseSprite != null)
			m_DealLoseSprite.ResetAnimationState();
		
		m_AnimatorList.clear();
		m_nClick = 0;
		m_nCount = 0;
		m_bWinner = false;
	}

	public void ResetAnimationState()
	{
		if(m_DealWinSprite != null)
			m_DealWinSprite.ResetAnimationState();
		
		if(m_DealLoseSprite != null)
			m_DealLoseSprite.ResetAnimationState();
		
		int nCount = m_AnimatorList.size(); 
		for(int i = 0; i < nCount; ++i)
		{
			m_AnimatorList.elementAt(i).ResetAnimationState();
		}
		
		m_nClick = 0;
		m_nCount = 0;
	}

	public void StartAnimation()
	{
		ResetAnimationState();
		CGameState.EnterAnimation();
	}
	
	public void SetMaxTimeClick(int n)
	{
		m_MaxTimeClick = n;
	}
	
	public void SetMaxCount(int n)
	{
		m_MaxCount = n;
	}
	
	public void SetDesktopSpriteBoundary(Rect rect)
	{
		if(m_DealWinSprite != null)
			m_DealWinSprite.SetClipRect(rect);
		
		if(m_DealLoseSprite != null)
			m_DealLoseSprite.SetClipRect(rect);
	}
	
	public void OnTimerEvent()
	{
		m_nClick++;
		if(m_MaxTimeClick <= m_nClick)
		{	
			if(m_bWinner)
			{
				if(m_DealWinSprite != null)
				{
					m_DealWinSprite.OnTimerEvent();
				}					
			}
			else
			{
				if(m_DealLoseSprite != null)
				{	
					m_DealLoseSprite.OnTimerEvent();
				}					
			}
			int nCount = m_AnimatorList.size(); 
			for(int i = 0; i < nCount; ++i)
			{
				m_AnimatorList.elementAt(i).OnTimerEvent();
			}
			m_nClick = 0;
			m_nCount++;
			if(m_MaxCount <= m_nCount)
			{
				ExitAnimation();
			}
		}			
	}
	
	public void OnDraw(Canvas canvas)
	{
		int nCount = m_AnimatorList.size(); 
		for(int i = 0; i < nCount; ++i)
		{
			m_AnimatorList.elementAt(i).OnDraw(canvas);
		}
		if(m_bWinner)
		{
			if(m_DealWinSprite != null)
				m_DealWinSprite.Draw(canvas);
		}
		else
		{
			if(m_DealLoseSprite != null)
				m_DealLoseSprite.Draw(canvas);
		}
	}
	
	public void ReloadResource()
	{
		if(m_DealWinSprite != null)
		{	
			m_DealWinSprite.SetClipRect(CDealLayout.GetDealSpriteRect());
		}
		if(m_DealLoseSprite != null)
		{	
			m_DealLoseSprite.SetClipRect(CDealLayout.GetDealSpriteRect());
		}				
		int nCount = m_AnimatorList.size(); 
		for(int i = 0; i < nCount; ++i)
		{
			m_AnimatorList.elementAt(i).ReloadResource();
		}
	}
	
	private void ExitAnimation()
	{
		Reset();
		CGameState.LeaveAnimation();
	}
}
