package com.e_gadget.MindFire;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class CAnimationLayer 
{
    private CDnDAnimationLayer		m_DnDAnimation;

    public	CDealAnimationLayer		m_DealAnimation;
    
    public CGame 					m_Controller;
    
	public CAnimationLayer()
	{
		m_DnDAnimation = new CDnDAnimationLayer();
		m_DealAnimation = new CDealAnimationLayer();
		m_Controller = null;
		//if(CDealLayout.IsLargeScreen())
		//{
		//	m_ResultAnimation.SetNoAnimation();
		//}
	}

	public CAnimationLayer(CGame controller)
	{
		m_DnDAnimation = new CDnDAnimationLayer();
//		m_ResultAnimation = new CResultAnimationLayer();
		m_DealAnimation = new CDealAnimationLayer();
		m_Controller = controller;
		m_DealAnimation.SetController(m_Controller);
		//if(CDealLayout.IsLargeScreen())
		//{
		//	m_ResultAnimation.SetNoAnimation();
		//}
	}
	
	public void SetController(CGame controller)
	{
		m_Controller = controller;
		if(m_DealAnimation != null)
		{
			m_DealAnimation.SetController(m_Controller);
		}
	}
	
	public void Initialize()
	{
		if(m_DealAnimation != null)
		{
			m_DealAnimation.Initialize();
		}
	}
	
	public void StartDragAndDrop(CGameSprite sprite)
	{
		m_DnDAnimation.StartDragAndDrop(sprite);
	}

	public void DragAndDropOver(int x, int y)
	{
		m_DnDAnimation.DragAndDropOver(x, y);
	}
	
	public void StopDragAndDrop()
	{
		m_DnDAnimation.StopDragAndDrop();
	}
	
	public void Reset()
	{
		m_DnDAnimation.Reset();
		m_DealAnimation.Reset();
	}
	
	public boolean IsDnDCursor(CLayoutCursor cursor)
	{
		return m_DnDAnimation.IsDnDCursor(cursor);
	}
	
	public void OnDraw(Canvas canvas)
	{
		int nState = CGameState.GetGameState();
		if(CGameState.InDragAndDrop())
		{	
			m_DnDAnimation.OnDraw(canvas);
		}
		else if(CGameState.InAnimation())
		{
			m_DealAnimation.OnDraw(canvas);
		}
	}		
	
	
	public void ReloadResource()
	{
		m_DealAnimation.ReloadResource();
	}
	
	public void StartDealAnimation()
	{
		m_DealAnimation.StartAnimation();
	}
	
	public void OnTimerEvent()
	{
		int nState = CGameState.GetGameState();
		if(CGameState.InAnimation())
		{
			m_DealAnimation.OnTimerEvent();
		}
	}
}
