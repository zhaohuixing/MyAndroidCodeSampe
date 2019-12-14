package com.e_gadget.MindFire;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class CGameSignBar 
{
	private Vector<CGameImageButton>	m_Buttons;
	private int							m_ActiveButton;

	public CGameSignBar()
	{
		m_Buttons = new Vector<CGameImageButton>();
			m_Buttons.clear();
		m_ActiveButton = -1;
	}

	public void SetButtonBoundary(int index, Rect rect)
	{
		if(0 <= index && index < m_Buttons.size())
		{
			m_Buttons.elementAt(index).SetBoundary(rect);
		}
	}
	
	public void Add(CGameImageButton button)
	{
		m_Buttons.add(button);
	}
	
	public int GetActiveButton()
	{
		return m_ActiveButton;
	}

	public void SetActiveButton(int nActive)
	{
		int nCount = m_Buttons.size();
		if(0 <= nActive && nActive < nCount)
			m_ActiveButton = nActive;
	}

	public void ResetState()
	{
		int nCount = m_Buttons.size();
		for(int i = 0; i < nCount; ++i)
		{
			m_Buttons.elementAt(i).ResetState();
		}
		m_ActiveButton = -1;
	}

	public int HitTest(int x, int y)
	{
		int nRet = -1;
		int nCount = m_Buttons.size();
		for(int i = 0; i < nCount; ++i)
		{
			if(m_Buttons.elementAt(i).HitTest(x, y) == true)
			{
				nRet = i;
				break;
			}
		}
		return nRet;
	}

	public int onKeyDown()
	{
		int nRet = -1;

		int nCount = m_Buttons.size();
		if(0 <= m_ActiveButton && m_ActiveButton < nCount)
		{
			m_Buttons.elementAt(m_ActiveButton).onKeyDown();
			nRet = m_ActiveButton;
		}

		return nRet;
	}

	public int onKeyUp()
	{
		int nRet = -1;

		int nCount = m_Buttons.size();
		if(0 <= m_ActiveButton && m_ActiveButton < nCount)
		{
			m_Buttons.elementAt(m_ActiveButton).onKeyUp();
			nRet = m_ActiveButton;
		}

		return nRet;
	}

	public int onClickDown(int x, int y)
	{
		int nRet = HitTest(x, y);
		int nCount = m_Buttons.size();
		if(0 <= m_ActiveButton && m_ActiveButton < nCount)
		{
			ResetState();
		}
		if(0 <= nRet && nRet < nCount)
		{
			m_ActiveButton = nRet;
			m_Buttons.elementAt(m_ActiveButton).onClickDown();
		}

		return nRet;
	}

	public int onClickUp(int x, int y)
	{
		int nRet = HitTest(x, y);
		int nCount = m_Buttons.size();
		if(0 <= m_ActiveButton && m_ActiveButton < nCount)
		{
			ResetState();
		}
		if(0 <= nRet && nRet < nCount)
		{
			m_ActiveButton = nRet;
			m_Buttons.elementAt(m_ActiveButton).onClickUp();
		}

		return nRet;
	}

	public void Draw(Canvas canvas)
	{
		int nCount = m_Buttons.size();
		for(int i = 0; i < nCount; ++i)
		{
			m_Buttons.elementAt(i).Draw(canvas);
		}
	}
	
	public Drawable GetNormalImage(int index)
	{
		Drawable ret = null;

		if(0 <= index && index < m_Buttons.size())
		{
			ret = m_Buttons.elementAt(index).GetNormalImage();
		}
		
		return ret;
	}
	
	public void ReloadResource(int i, Drawable Image1, Drawable Image2, Rect rect)
	{
		if(0 <= i && i < m_Buttons.size())
		{
			m_Buttons.elementAt(i).ReloadResource(Image1, Image2, rect);
		}
	}
	
}
