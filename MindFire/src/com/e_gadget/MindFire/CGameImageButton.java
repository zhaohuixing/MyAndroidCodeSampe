package com.e_gadget.MindFire;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class CGameImageButton 
{
	/* The regular button state image */
	private Drawable	m_NormalImage;

	/* The button pressed state image */
	private Drawable	m_PressedImage;

	/* The button position in the screen */
	private Rect		m_Boundary;

	/* The flag to identify the button state */
	private boolean		m_bPressed;

	public CGameImageButton(Drawable Image1, Drawable Image2, Rect rect)
	{
		m_NormalImage = Image1;
		m_PressedImage = Image2;
		m_Boundary = rect;
		m_bPressed =  false;
	}

	public CGameImageButton(Drawable Image1, Drawable Image2, int left, int top, int right, int bottom)
	{
		m_NormalImage = Image1;
		m_PressedImage = Image2;
		m_Boundary = new Rect(left, top, right, bottom);
		m_bPressed =  false;
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

	public void Draw(Canvas canvas)
	{
		if(m_bPressed)
		{
			if(m_PressedImage != null)
			{
				m_PressedImage.setBounds(m_Boundary);
				m_PressedImage.draw(canvas);
				m_PressedImage.invalidateSelf();
			}
		}
		else
		{
			if(m_NormalImage != null)
			{
				m_NormalImage.setBounds(m_Boundary);
				m_NormalImage.draw(canvas);
				m_NormalImage.invalidateSelf();
			}
		}
	}
	
	public Drawable GetNormalImage()
	{
		return m_NormalImage;
	}
	
	public void ReloadResource(Drawable Image1, Drawable Image2, Rect rect)
	{
		m_NormalImage = Image1;
		m_PressedImage = Image2;
		m_Boundary = rect;
	}

	public void SetBoundary(Rect rect)
	{
		m_Boundary = rect;
	}
}
