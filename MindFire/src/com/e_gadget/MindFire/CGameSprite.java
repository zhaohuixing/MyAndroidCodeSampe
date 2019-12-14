package com.e_gadget.MindFire;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class CGameSprite 
{
	public static final int SPRITE_TYPE_DND = 0;
	public static final int SPRITE_TYPE_ANIMATION = 1;

	private int				m_Type;

	private int  			m_ClipWidth;
	private int  			m_ClipHeight;
	private Rect  			m_ClipRect;
	
	//Data member for Drag and Drop
	private Drawable  		m_Image1;
	private Drawable  		m_Image2;

	private CLayoutCursor	m_CursorStart;
	private CLayoutCursor	m_CursorEnd;
	private boolean 		m_bBasicCard;
	private int				m_nCardIndex;

	//Data member for animation
	private Vector<Drawable>			m_AnimatorList;
	private	int							m_nTimeSlice;
	
	private	boolean						m_bDualAnimation;
	
	public CGameSprite(int nType, CLayoutCursor start, CLayoutCursor end, Drawable  image1, Drawable  image2, boolean isBasicCard, int nIndex)
	{
		m_CursorStart = start;
		m_CursorEnd = end;
		m_Image1 = image1;
		m_Image2 = image2;
		m_bBasicCard = isBasicCard;
		m_nCardIndex = nIndex;
		m_Type = nType;
		m_AnimatorList = new Vector<Drawable>();
		InitializeData();
		m_bDualAnimation = false;
	}

	public CGameSprite(int nType)
	{
		m_Type = nType;
		m_CursorStart = null;
		m_CursorEnd = null;
		m_Image1 = null;
		m_Image2 = null;
		m_bBasicCard = false;
		m_nCardIndex = -1;
		m_AnimatorList = new Vector<Drawable>();
		m_nTimeSlice = 0;
		m_bDualAnimation = false;
	}

	public void SetDualAnimation()
	{
		m_bDualAnimation = true;
	}
	
	private void InitializeData()
	{
		m_ClipRect = CDealLayout.GetRect(m_CursorStart);
		m_ClipWidth = m_ClipRect.width();
		m_ClipHeight = m_ClipRect.height();
		m_nTimeSlice = 0;
	}

	public void SetClipRect(Rect rect)
	{
		m_ClipRect = rect;
		m_ClipWidth = m_ClipRect.width();
		m_ClipHeight = m_ClipRect.height();
	}
	
	public void AddAnimationImage(Drawable image)
	{
		if(m_Type == CGameSprite.SPRITE_TYPE_ANIMATION && m_AnimatorList != null)
		{
			m_AnimatorList.add(image);
		}
	}
	
	public boolean IsBasicCard()
	{
		return m_bBasicCard; 
	}
	
	public int GetCardIndex()
	{
		return m_nCardIndex; 
	}
	
	public CLayoutCursor GetStartCursor()
	{
		return m_CursorStart;
	}
	
	public CLayoutCursor GetEndCursor()
	{
		return m_CursorEnd;
	}

	public boolean IsStartCursor(CLayoutCursor cursor)
	{
		boolean bRet = false;
		
		if(m_CursorStart != null && cursor != null)
		{
			bRet = m_CursorStart.IsSame(cursor);
		}
		
		return bRet;
	}
	
	public boolean IsEndCursor(CLayoutCursor cursor)
	{
		boolean bRet = false;
		
		if(m_CursorEnd != null && cursor != null)
		{
			bRet = m_CursorEnd.IsSame(cursor);
		}
		
		return bRet;
	}

	public boolean IsDnDCursor(CLayoutCursor cursor)
	{
		boolean bRet = false;
		
		if(m_Type == CGameSprite.SPRITE_TYPE_DND)
		{
			bRet = IsStartCursor(cursor);
		}
		
		return bRet;
	}
	
	public void MoveTo(int x, int y)
	{
		if(m_Type == CGameSprite.SPRITE_TYPE_DND && m_CursorStart != null && m_CursorStart.CanDragAndDrop())
		{
			m_ClipRect.left = x - m_ClipWidth/2; 
			m_ClipRect.top = y - m_ClipHeight/2; 
			m_ClipRect.right = m_ClipRect.left + m_ClipWidth; 
			m_ClipRect.bottom = m_ClipRect.top + m_ClipHeight;
		}			
	}
	
	public void Draw(Canvas canvas)
	{
		if(m_Type == CGameSprite.SPRITE_TYPE_DND && m_CursorStart != null && m_CursorStart.CanDragAndDrop())
		{
			DnDDraw(canvas);
		}
		if(m_Type == CGameSprite.SPRITE_TYPE_ANIMATION)
		{
			AnimationDraw(canvas);
		}
	}
	
	public int PositionX()
	{
		return m_ClipRect.centerX();
	}

	public int PositionY()
	{
		return m_ClipRect.centerY();
	}
	
	private void DnDDraw(Canvas canvas)
	{
		if(m_Image1 != null)
		{
			m_Image1.setBounds(m_ClipRect);
			m_Image1.draw(canvas);
		}
	}
	
	public void OnTimerEvent()
	{
		if(m_Type == CGameSprite.SPRITE_TYPE_ANIMATION)
		{
			++m_nTimeSlice;
			if(m_nTimeSlice < 0)
				m_nTimeSlice = 0;
			
			if(m_AnimatorList != null && 0 < m_AnimatorList.size())
			{
				if(m_AnimatorList.size() <= m_nTimeSlice)
				{
					m_nTimeSlice = m_nTimeSlice%m_AnimatorList.size();
				}
			}
		}
	}
	
	public void AnimationDraw(Canvas canvas)
	{
		if(m_AnimatorList != null && 0 < m_AnimatorList.size() && m_ClipRect != null)
		{
			if(m_AnimatorList.size() <= m_nTimeSlice)
			{
				m_nTimeSlice = m_nTimeSlice%m_AnimatorList.size();
			}
			Drawable image = m_AnimatorList.elementAt(m_nTimeSlice);
			if(m_bDualAnimation == false)
			{	
				image.setBounds(m_ClipRect);
				image.draw(canvas);
			}
			else
			{
				int nw = m_ClipRect.width()/2;
				m_ClipRect.offset(nw, 0);
				image.setBounds(m_ClipRect);
				image.draw(canvas);
				m_ClipRect.offset(-2*nw, 0);
				image.setBounds(m_ClipRect);
				image.draw(canvas);
				m_ClipRect.offset(nw, 0);
			}
		}
	}
	
	public void ResetAnimationState()
	{
		if(m_Type == CGameSprite.SPRITE_TYPE_ANIMATION)
		{
			m_nTimeSlice = 0;
		}
	}
}
