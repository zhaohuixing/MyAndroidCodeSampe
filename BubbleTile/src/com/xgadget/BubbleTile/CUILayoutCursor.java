/**
 * 
 */
package com.xgadget.BubbleTile;

/**
 * @author zhaohuixing
 * This class is a UI cursor class to record active UI cursor position to support device keypad key event
 * iterating the UI controls in game window
 */
public class CUILayoutCursor 
{
	public static final int	HITTEST_NONE = -1;
	public static final int	HITTEST_SETUPBUTTON = 0;
	public static final int	HITTEST_UNDOALLBUTTON = 1;
	public static final int	HITTEST_UNDOBUTTON = 2;
	public static final int	HITTEST_NEWGAMEBUTTON = 3;
	public static final int	HITTEST_HELPTIPBUTTON = 4;
	public static final int	HITTEST_BUBBLE = 5;
	public static final int	HITTEST_FIRST = HITTEST_SETUPBUTTON;
	public static final int	HITTEST_LAST = HITTEST_BUBBLE;

	private int m_HT_ObjectType;
	private int m_HT_ObjectIndex;

	public CUILayoutCursor()
	{
		m_HT_ObjectType = CUILayoutCursor.HITTEST_NONE;
		m_HT_ObjectIndex = -1;
	}

	public CUILayoutCursor(int nType, int nIndex)
	{
		Set(nType, nIndex);
	}

	public void Reset()
	{
		m_HT_ObjectType = CUILayoutCursor.HITTEST_NONE;
		m_HT_ObjectIndex = -1;
	}

	public boolean IsValid()
	{
		boolean bRet = (m_HT_ObjectType != CUILayoutCursor.HITTEST_NONE && -1 < m_HT_ObjectIndex);
		return bRet;
	}

	public CUILayoutCursor Clone()
	{
		CUILayoutCursor ret = new CUILayoutCursor(m_HT_ObjectType, m_HT_ObjectIndex);
		return ret;
	}

	public void Set(int nType, int nIndex)
	{
		if(CUILayoutCursor.HITTEST_FIRST <= nType && nType <= CUILayoutCursor.HITTEST_LAST)
		{
			m_HT_ObjectType = nType;
			m_HT_ObjectIndex = nIndex;
			if(m_HT_ObjectIndex < 0)
				m_HT_ObjectIndex = 0;

			if(m_HT_ObjectType == CUILayoutCursor.HITTEST_SETUPBUTTON || 
				m_HT_ObjectType == CUILayoutCursor.HITTEST_UNDOALLBUTTON ||
				m_HT_ObjectType == CUILayoutCursor.HITTEST_UNDOBUTTON ||
				m_HT_ObjectType == CUILayoutCursor.HITTEST_NEWGAMEBUTTON ||
				m_HT_ObjectType == CUILayoutCursor.HITTEST_HELPTIPBUTTON)
			{
				m_HT_ObjectIndex = 0;
			}
		}
	}

	public void Set(CUILayoutCursor cursor)
	{
		Set(cursor.GetType(), cursor.GetIndex());
	}

	public int GetType()
	{
		return m_HT_ObjectType;
	}

	public int GetIndex()
	{
		return m_HT_ObjectIndex;
	}

	public boolean IsSame(CUILayoutCursor cursor)
	{
		boolean bRet = (cursor.GetType() == m_HT_ObjectType && cursor.GetIndex() == m_HT_ObjectIndex);
		return bRet;
	}

	public boolean IsSame(int nType, int nIndex)
	{
		boolean bRet = (nType == m_HT_ObjectType && nIndex == m_HT_ObjectIndex);
		return bRet;
	}
	
	public static CUILayoutCursor NextCursor(CUILayoutCursor cursor)
	{
		CUILayoutCursor newcur = new CUILayoutCursor();
		
		int OldType = cursor.GetType();
		int OldIndex = cursor.GetIndex();
		int NewType = cursor.GetType();
		int NewIndex = cursor.GetIndex();
		
		switch(OldType)
		{
			case CUILayoutCursor.HITTEST_NONE:
				NewType = CUILayoutCursor.HITTEST_SETUPBUTTON;
				NewIndex = 0;
				break;
			case CUILayoutCursor.HITTEST_SETUPBUTTON:
				NewType = CUILayoutCursor.HITTEST_UNDOALLBUTTON;
				NewIndex = 0;
				break;
			case CUILayoutCursor.HITTEST_UNDOALLBUTTON:
				NewType = CUILayoutCursor.HITTEST_UNDOBUTTON;
				NewIndex = 0;
				break;
			case CUILayoutCursor.HITTEST_UNDOBUTTON:
				NewType = CUILayoutCursor.HITTEST_NEWGAMEBUTTON;
				NewIndex = 0;
				break;
			case CUILayoutCursor.HITTEST_NEWGAMEBUTTON:
				NewType = CUILayoutCursor.HITTEST_HELPTIPBUTTON;
				NewIndex = 0;
				break;
			case CUILayoutCursor.HITTEST_HELPTIPBUTTON:
				NewType = CUILayoutCursor.HITTEST_BUBBLE;
				NewIndex = 0;
				break;
			case CUILayoutCursor.HITTEST_BUBBLE:
				if(OldIndex < 0)
				{	
					NewIndex = 0;
					NewType = OldType;
				}					
				else
				{	
					if(OldIndex < GameConfiguration.GetCurrentBubbleCount())
					{	
						NewIndex = OldIndex+1;
						NewType = OldType;
					}
					else
					{
						NewIndex = 0;
						NewType = CUILayoutCursor.HITTEST_SETUPBUTTON;
					}
				}
				break;
		}
		
		newcur.Set(NewType, NewIndex);
		
		return newcur;
	}

	public static CUILayoutCursor PrevCursor(CUILayoutCursor cursor)
	{
		CUILayoutCursor newcur = new CUILayoutCursor();
		
		int OldType = cursor.GetType();
		int OldIndex = cursor.GetIndex();
		int NewType = cursor.GetType();
		int NewIndex = cursor.GetIndex();
		
		switch(OldType)
		{
			case CUILayoutCursor.HITTEST_NONE:
				NewType = CUILayoutCursor.HITTEST_SETUPBUTTON;
				NewIndex = 0;
				break;
			case CUILayoutCursor.HITTEST_SETUPBUTTON:
				NewType = CUILayoutCursor.HITTEST_BUBBLE;
				NewIndex = GameConfiguration.GetCurrentBubbleCount()-1;
				break;
			case CUILayoutCursor.HITTEST_UNDOALLBUTTON:
				NewType = CUILayoutCursor.HITTEST_SETUPBUTTON;
				NewIndex = 0;
				break;
			case CUILayoutCursor.HITTEST_UNDOBUTTON:
				NewType = CUILayoutCursor.HITTEST_UNDOALLBUTTON;
				NewIndex = 0;
				break;
			case CUILayoutCursor.HITTEST_NEWGAMEBUTTON:
				NewType = CUILayoutCursor.HITTEST_UNDOBUTTON;
				NewIndex = 0;
				break;
			case CUILayoutCursor.HITTEST_HELPTIPBUTTON:
				NewType = CUILayoutCursor.HITTEST_NEWGAMEBUTTON;
				NewIndex = 0;
				break;
			case CUILayoutCursor.HITTEST_BUBBLE:
				if(0 < OldIndex)
				{	
					NewIndex = OldIndex-1;
					NewType = OldType;
				}					
				else
				{	
					NewIndex = 0;
					NewType = CUILayoutCursor.HITTEST_HELPTIPBUTTON;
				}
				break;
		}
	
		newcur.Set(NewType, NewIndex);
	
		return newcur;
	}
	
}
