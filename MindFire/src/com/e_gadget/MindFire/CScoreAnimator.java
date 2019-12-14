package com.e_gadget.MindFire;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class CScoreAnimator 
{
	private Vector<Drawable>			m_RedStars;
	private Vector<Drawable>			m_BlueStars;
	//private Vector<Drawable>			m_PinkStars;
	private	int							m_nBlueTimeSlice;
	private	int							m_nRedTimeSlice;
	private int							m_MaxTimeBlueClick;
	private int							m_nBlueClick;
	private int							m_MaxTimeRedClick;
	private int							m_nRedClick;

	public CScoreAnimator()
	{
		m_RedStars = new Vector<Drawable>();
		m_BlueStars = new Vector<Drawable>();
		//m_PinkStars = new Vector<Drawable>();
		m_nBlueTimeSlice = 0;
		m_nRedTimeSlice = 0;
		m_MaxTimeBlueClick = 4;
		m_nBlueClick = 0;
		m_MaxTimeRedClick = 2;
		m_nRedClick = 0;
	}
	
	public void OnTimerEvent()
	{
		m_nBlueClick++;
		if(m_MaxTimeBlueClick <= m_nBlueClick)
		{	
			m_nBlueTimeSlice++;
			if(m_BlueStars.size() <= m_nBlueTimeSlice)
			{
				m_nBlueTimeSlice = 0;
			}
			m_nBlueClick = 0;
		}			
		m_nRedClick++;
		if(m_MaxTimeRedClick <= m_nRedClick)
		{	
			m_nRedTimeSlice++;
			if(m_RedStars.size() <= m_nRedTimeSlice)
			{
				m_nRedTimeSlice = 0;
			}
			m_nRedClick = 0;
		}			
	}
	
	public void ResetState()
	{
		m_nBlueTimeSlice = 0;
		m_nRedTimeSlice = 0;
		m_nBlueClick = 0;
		m_nRedClick = 0;
	}
	
	//public void AddStars(Drawable redStar, Drawable blueStar, Drawable pinkStar)
	public void AddStars(Drawable redStar, Drawable blueStar)
	{
		m_RedStars.add(redStar);
		m_BlueStars.add(blueStar);
		//m_PinkStars.add(pinkStar);
	}
	
	public void DrawRedStar(Canvas canvas, Rect rect)
	{
		if(0 <= m_nRedTimeSlice && m_nRedTimeSlice < m_RedStars.size())
		{
			m_RedStars.elementAt(m_nRedTimeSlice).setBounds(rect);
			m_RedStars.elementAt(m_nRedTimeSlice).draw(canvas);
		}
	}

	public void DrawBlueStar(Canvas canvas, Rect rect)
	{
		if(0 <= m_nBlueTimeSlice && m_nBlueTimeSlice < m_BlueStars.size())
		{
			m_BlueStars.elementAt(m_nBlueTimeSlice).setBounds(rect);
			m_BlueStars.elementAt(m_nBlueTimeSlice).draw(canvas);
		}
	}

/*	public void DrawPinkStar(Canvas canvas, Rect rect)
	{
		if(0 <= m_nTimeSlice && m_nTimeSlice < m_PinkStars.size())
		{
			m_PinkStars.elementAt(m_nTimeSlice).setBounds(rect);
			m_PinkStars.elementAt(m_nTimeSlice).draw(canvas);
		}
	}*/
}
