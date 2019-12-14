package com.e_gadget.MindFire;

public class CLayoutCursor 
{
	public static final int	TYPE_NONE = -1;
	public static final int	TYPE_BASIC_CARD = 0;
	public static final int	TYPE_TEMP_CARD = 1;
	public static final int	TYPE_OPERAND_CARD = 2;
	public static final int	TYPE_OPERATOR_BUTTON = 3;
	public static final int	TYPE_CALCULATE_BUTTON = 4;
	public static final int	TYPE_RESULT_CARD = 5;
	public static final int	TYPE_SIGNSBAR_BUTTON = 6;
	public static final int	TYPE_CANCEL_BUTTON = 7;
	public static final int	TYPE_NEWDEAL_BUTTON = 8;
	public static final int	TYPE_FIRST = TYPE_BASIC_CARD;
	public static final int	TYPE_LAST = TYPE_NEWDEAL_BUTTON;

	private int m_ObjectType;
	private int m_ObjectIndex;

	public CLayoutCursor()
	{
		m_ObjectType = CLayoutCursor.TYPE_NONE;
		m_ObjectIndex = -1;
	}

	public CLayoutCursor(int nType, int nIndex)
	{
		Set(nType, nIndex);
	}

	public void Reset()
	{
		m_ObjectType = CLayoutCursor.TYPE_NONE;
		m_ObjectIndex = -1;
	}

	public boolean IsValid()
	{
		boolean bRet = (m_ObjectType != CLayoutCursor.TYPE_NONE && -1 < m_ObjectIndex);
		return bRet;
	}

	public CLayoutCursor Clone()
	{
		CLayoutCursor ret = new CLayoutCursor(m_ObjectType, m_ObjectIndex);
		return ret;
	}

	public void Set(int nType, int nIndex)
	{
		if(CLayoutCursor.TYPE_FIRST <= nType && nType <= CLayoutCursor.TYPE_LAST)
		{
			m_ObjectType = nType;
			m_ObjectIndex = nIndex;
			if(m_ObjectIndex < 0)
				m_ObjectIndex = 0;

			if(m_ObjectType == CLayoutCursor.TYPE_OPERATOR_BUTTON || 
				m_ObjectType == CLayoutCursor.TYPE_CALCULATE_BUTTON ||
				m_ObjectType == CLayoutCursor.TYPE_RESULT_CARD ||
				m_ObjectType == CLayoutCursor.TYPE_CANCEL_BUTTON ||
				m_ObjectType == CLayoutCursor.TYPE_NEWDEAL_BUTTON)
			{
				m_ObjectIndex = 0;
			}
		}
	}

	public void Set(CLayoutCursor cursor)
	{
		Set(cursor.GetType(), cursor.GetIndex());
	}

	public int GetType()
	{
		return m_ObjectType;
	}

	public int GetIndex()
	{
		return m_ObjectIndex;
	}

	public boolean IsSame(CLayoutCursor cursor)
	{
		boolean bRet = (cursor.GetType() == m_ObjectType && cursor.GetIndex() == m_ObjectIndex);
		return bRet;
	}

	public boolean IsSame(int nType, int nIndex)
	{
		boolean bRet = (nType == m_ObjectType && nIndex == m_ObjectIndex);
		return bRet;
	}
	
	public boolean CanDragAndDrop()
	{
		boolean bRet = false;

		bRet = (m_ObjectType == CLayoutCursor.TYPE_BASIC_CARD || 
		m_ObjectType == CLayoutCursor.TYPE_TEMP_CARD ||
		m_ObjectType == CLayoutCursor.TYPE_OPERAND_CARD ||
		m_ObjectType == CLayoutCursor.TYPE_RESULT_CARD ||
		m_ObjectType == CLayoutCursor.TYPE_SIGNSBAR_BUTTON);
		
		return bRet;
	}

	public boolean CanAnimation()
	{
		boolean bRet = false;

		bRet = (m_ObjectType != CLayoutCursor.TYPE_CALCULATE_BUTTON &&  
				m_ObjectType != CLayoutCursor.TYPE_CANCEL_BUTTON);
		
		return bRet;
	}
	
}
