package com.e_gadget.MindFire;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class CCard 
{
	/*The object hold regular card information*/
	private CCardBase	m_BasicCard;

	/*The object hold regular temporary card information*/
	private CTempCard	m_TempCard;

	/*The flag identify card type*/
	private boolean		m_bTemprary;

	/*The flag identify card status*/
	private boolean		m_bSelected;
	
    /* The object render card back side image */
    private Drawable m_BackSideImage;

    /* The object regular card image */
    private Drawable m_BasicCardImage;

    /* The object temporary card image */
    private Drawable m_TempCardImage;
    
	public CCard(int nIndex)
	{
		m_BasicCard = new CCardBase(nIndex);
		m_TempCard = null;
		m_bTemprary = false;
		m_bSelected = false;
	}

	public CCard(int n, boolean bTemp)
	{
		m_bTemprary = bTemp;
		if(!m_bTemprary)
		{
			m_BasicCard = new CCardBase(n);
			m_TempCard = null;
		}
		else
		{
			m_BasicCard = null;
			m_TempCard = new CTempCard(n);
		}
		m_bSelected = false;
	}

	public CCard(int nValue, int nIndex)
	{
		m_bTemprary = true;
		m_BasicCard = null;
		m_TempCard = new CTempCard(nValue, nIndex);
		m_bSelected = false;
	}

	public CCard(CCardBase card)
	{
		m_BasicCard = card;
		m_TempCard = null;
		m_bTemprary = false;
		m_bSelected = false;
	}

	public CCard(CTempCard card)
	{
		m_BasicCard = null;
		m_TempCard = card;
		m_bTemprary = true;
		m_bSelected = false;
	}

	public CCardBase GetCard()
	{
		return m_BasicCard; 
	}

	public CTempCard GetTempCard()
	{
		return m_TempCard; 
	}

	public int GetIndex()
	{
		if(!m_bTemprary && m_BasicCard != null)
			return m_BasicCard.GetIndex();
		else if(m_bTemprary && m_TempCard != null)
			return m_TempCard.GetIndex();
		else
			return -1;
	}

	public int GetType()
	{
		if(!m_bTemprary && m_BasicCard != null)
			return m_BasicCard.GetType();
		else
			return -1;
	}

	public int GetValue()
	{
		if(!m_bTemprary && m_BasicCard != null)
			return m_BasicCard.GetValue();
		else if(m_bTemprary && m_TempCard != null)
			return m_TempCard.GetValue();
		else
			return -1;
	}

	public boolean IsBasicCard()
	{
		return (!m_bTemprary);
	}

	public boolean IsTemperaryCard()
	{
		return m_bTemprary;
	}

	public boolean IsValidCard()
	{
		return (m_BasicCard != null || m_TempCard != null);
	}

	public boolean IsSelected()
	{
		return m_bSelected; 
	}
	
	public void SetSelected(boolean bSelected)
	{
		m_bSelected = bSelected;
	}
	
	public boolean SetTempCardIndex(int nIndex)
	{
		if(m_bTemprary && m_TempCard != null)
		{
			m_TempCard.SetIndex(nIndex);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public CCard Clone()
	{
		CCard card = null;
		
		if(!m_bTemprary && m_BasicCard != null)
		{
			card = new CCard(m_BasicCard);
		}
		else if(m_bTemprary && m_TempCard != null)
		{
			card = new CCard(m_TempCard);
		}
		
		if(card != null)
		{	
			card.SetSelected(this.m_bSelected);
		}
		if(card != null)
			card.LoadImage();
		
		return card;
	}


	public String toString()
	{
		String szText = new String("?");
		if(!m_bTemprary && m_BasicCard != null)
			szText = m_BasicCard.toString();
		else if(m_bTemprary && m_TempCard != null)
			szText = m_TempCard.toString();

		return szText;
	}

	public String Value2String()
	{
		String szText = new String("?");
		
		if(!m_bTemprary && m_BasicCard != null)
			szText = m_BasicCard.Value2String();
		else if(m_bTemprary && m_TempCard != null)
			szText = m_TempCard.Value2String();

		return szText;
	}
	
	public boolean LoadImage()
	{
		boolean bRet = false;
		
	    m_BackSideImage = CGameHelper.GetCardBackSideImage();
	    
	    if(m_bTemprary)
	    {	
	    	int nValue = GetValue(); 
	    	m_TempCardImage = CGameHelper.GetTempCardImage(nValue);
	    	if(m_TempCardImage != null)
	    		bRet = true;
	    }
	    else
	    {
	    	int nIndex = GetIndex();
	    	m_BasicCardImage = CGameHelper.GetBasicCardImage(nIndex);	    	
	    	if(m_BasicCardImage != null)
	    		bRet = true;
	    } 
	    return bRet;
	}
	
	public void Draw(Canvas canvas, int left, int top, int right, int bottom)
	{
		if(m_bSelected)
		{
			if(m_BackSideImage != null)
			{	
				m_BackSideImage.setBounds(left, top, right, bottom);
				m_BackSideImage.draw(canvas);
				m_BackSideImage.invalidateSelf();
			}	
		}
		else
		{
			if(m_bTemprary)
			{
				if(m_TempCardImage != null)
				{
					m_TempCardImage.setBounds(left, top, right, bottom);
					m_TempCardImage.draw(canvas);
					m_TempCardImage.invalidateSelf();
				}
			}
			else
			{
				if(m_BasicCardImage != null)
				{
					m_BasicCardImage.setBounds(left, top, right, bottom);
					m_BasicCardImage.draw(canvas);
					m_BasicCardImage.invalidateSelf();
					
				}
			}
		}
	}

	public void Draw(Canvas canvas, Rect bounds)
	{
		if(m_bSelected)
		{
			if(m_BackSideImage != null)
			{	
				m_BackSideImage.setBounds(bounds);
				m_BackSideImage.draw(canvas);
				m_BackSideImage.invalidateSelf();
			}	
		}
		else
		{
			if(m_bTemprary)
			{
				if(m_TempCardImage != null)
				{
					m_TempCardImage.setBounds(bounds);
					m_TempCardImage.draw(canvas);
					m_TempCardImage.invalidateSelf();
				}
			}
			else
			{
				if(m_BasicCardImage != null)
				{
					m_BasicCardImage.setBounds(bounds);
					m_BasicCardImage.draw(canvas);
					m_BasicCardImage.invalidateSelf();
				}
			}
		}
	}

	public void DrawBackSide(Canvas canvas, Rect bounds)
	{
		if(m_BackSideImage != null)
		{	
			m_BackSideImage.setBounds(bounds);
			m_BackSideImage.draw(canvas);
			m_BackSideImage.invalidateSelf();
		}	
	}
	
	public Drawable GetCardImage()
	{
		if(m_bTemprary)
			return m_TempCardImage;
		else
			return m_BasicCardImage;
	}

	public Drawable GetBackSideImage()
	{
		return m_BackSideImage;
	}
}
