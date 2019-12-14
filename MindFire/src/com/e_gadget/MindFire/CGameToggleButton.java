package com.e_gadget.MindFire;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class CGameToggleButton 
{
	/* The regular button state images */
	private Vector<Drawable>	m_NormalImages;

	/* The button pressed state images */
	private Vector<Drawable>	m_PressedImages;

	/* The button position in the screen */
	private Rect		m_Boundary;

	/* The flag to identify the button state */
	private boolean		m_bPressed;

	/* The active index */
	private int		m_nActive;
	
	/*Flag for toggling to zero index*/
	private boolean	m_bToogle2Zero;
	
	public CGameToggleButton(Rect rect, boolean bZeroIndex)
	{
		m_NormalImages = new Vector<Drawable>();
		m_PressedImages = new Vector<Drawable>();
		m_Boundary = rect;
		m_bPressed =  false;
		m_nActive = 0;
		m_bToogle2Zero = bZeroIndex;
	}

	public CGameToggleButton(int left, int top, int right, int bottom, boolean bZeroIndex)
	{
		m_NormalImages = new Vector<Drawable>();
		m_PressedImages = new Vector<Drawable>();
		m_Boundary = new Rect(left, top, right, bottom);
		m_bPressed =  false;
		m_nActive = 0;
		m_bToogle2Zero = bZeroIndex;
	}
	
	public void AddImages(Drawable Image1, Drawable Image2)
	{
		m_NormalImages.add(Image1);
		m_PressedImages.add(Image2);
	}

	public boolean HitTest(int x, int y)
	{
		boolean bRet = m_Boundary.contains(x, y);
		return bRet;
	}

	public void SetState(boolean bPressed)
	{
		m_bPressed = bPressed;
	}

	public boolean IsPressed()
	{
		return m_bPressed;
	}

	public void onKeyDown()
	{
		SetState(true);
	}

	public void onKeyUp()
	{
		SetState(false);
	}

	public void onClickDown()
	{
		SetState(true);
	}

	public void onClickUp()
	{
		SetState(false);
	}

	public void ResetState()
	{
		SetState(false);
	}

	public void Reset()
	{
		SetState(false);
		m_nActive = 0;
	}
	
	public void Toggle()
	{
		++m_nActive;
		if(m_NormalImages.size() <= m_nActive || m_PressedImages.size() <= m_nActive )
		{
			if(m_bToogle2Zero)
				m_nActive = 0;
			else
			{	
				if(1 < m_NormalImages.size()|| 1 < m_PressedImages.size())
					m_nActive = 1;
				else
					m_nActive = 0;
			}				
		}				
	}

	public void SetActive(int index)
	{
		m_nActive = index;
		if(m_NormalImages.size() <= m_nActive || m_PressedImages.size() <= m_nActive || m_nActive < 0)
		{
			m_nActive = Math.min(m_NormalImages.size(), m_PressedImages.size());
		}		
		Log.w(this.getClass().getName(), "SetActive is called");					
	}
	
	public int GetActive()
	{
		return m_nActive;
	}
	
	public void Draw(Canvas canvas)
	{
		if(m_bPressed)
		{
			if(m_PressedImages != null && 0 < m_PressedImages.size() 
			&& 0 <= m_nActive && m_nActive < m_PressedImages.size())
			{
				m_PressedImages.elementAt(m_nActive).setBounds(m_Boundary);
				m_PressedImages.elementAt(m_nActive).draw(canvas);
				m_PressedImages.elementAt(m_nActive).invalidateSelf();
			}
		}
		else
		{
			if(m_NormalImages != null && 0 < m_NormalImages.size() 
			&& 0 <= m_nActive && m_nActive < m_NormalImages.size())
			{
				m_NormalImages.elementAt(m_nActive).setBounds(m_Boundary);
				m_NormalImages.elementAt(m_nActive).draw(canvas);
				m_NormalImages.elementAt(m_nActive).invalidateSelf();
			}
		}
	}
	
	public Drawable GetNormalImage(int index)
	{
		Drawable ret = null;

		if(0 <= index && index < m_NormalImages.size())
		{
			ret = m_NormalImages.elementAt(index);
		}
		
		return ret;
	}

	public void ReloadResource(Drawable Image1, Drawable Image2, int i)
	{
		if(0 <= i && i < m_NormalImages.size())
		{
			m_NormalImages.set(i, Image1);
			m_PressedImages.set(i, Image2);
		}
	}
	
	public void SetBoundary(Rect rect)
	{
		m_Boundary = rect;
	}
	
}
