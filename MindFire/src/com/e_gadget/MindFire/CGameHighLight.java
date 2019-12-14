package com.e_gadget.MindFire;


public class CGameHighLight 
{
	private static CLayoutCursor m_Cursor;
	
	public static void Initialize(CLayoutCursor cursor)
	{
		m_Cursor = cursor;
	}

	public static void Reset()
	{
		m_Cursor.Reset();
	}

	public static boolean IsHighLight()
	{
		boolean bRet = m_Cursor.IsValid();
		return bRet;
	}

	public static void SetHighLight(int nType, int nIndex)
	{
		m_Cursor.Set(nType, nIndex);
	}

	public static void SetHighLight(CLayoutCursor cursor)
	{
		m_Cursor.Set(cursor);
	}

	public static int GetHighLightObjectType()
	{
		return m_Cursor.GetType();
	}

	public static int GetHighLightObjectIndex()
	{
		return m_Cursor.GetIndex();
	}

	public static CLayoutCursor GetCursor()
	{
		return m_Cursor.Clone();
	}
}
