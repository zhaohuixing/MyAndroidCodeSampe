package com.e_gadget.MindFire;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class CStarAnimator 
{
	private Vector<Drawable> 	m_Stars;
	private Rect 				m_Boundary;
	private int					m_nStarStep;

	public CStarAnimator(Vector<Drawable> stars)
	{
		m_Stars = stars;
		m_Boundary = null;
		m_nStarStep = 0;
	}
	
	public void SetBoundary(Rect rect)
	{
		m_Boundary = rect;
	}
	
	public void OnTimerEvent()
	{
		++m_nStarStep;
		if(0 < m_Stars.size())
		{	
			if(m_Stars.size()+1 <= m_nStarStep)
				m_nStarStep = 0;
		}			
	}

	public void OnDraw(Canvas canvas)
	{
		if(m_Boundary != null && 0 < m_Stars.size())
		{
			if(m_nStarStep < m_Stars.size())
			{
				m_Stars.elementAt(m_nStarStep).setBounds(m_Boundary);
				m_Stars.elementAt(m_nStarStep).draw(canvas);
			}			
		}
	}
}
