package com.e_gadget.MindFire;

public class CCardBase 
{
	public static final int SPADE = 0;
	public static final int DIAMOND = 1;
	public static final int CLUB = 2;
	public static final int HEART = 3;

	
	public int  m_nIndex;
	
	public CCardBase(int nIndex)
	{
		m_nIndex = nIndex;
	}
	
	public int GetIndex()
	{
		return m_nIndex;
	}

	public int GetType()
	{
		//return m_nIndex/13;
		return CCardBase.GetCardType(m_nIndex);
	}

	public int GetValue()
	{
		//return m_nIndex%13+1;
		return CCardBase.GetCardValue(m_nIndex);
	}

	public CCardBase Clone()
	{
		CCardBase card = new CCardBase(this.m_nIndex);
		return card;
	}
	
	public String toString()
	{
		String str = new String("");
		
		Integer Type = new Integer(GetType());
		Integer Index = new Integer(GetIndex());
		Integer Value = new Integer(GetValue());
		
		str = "(" + Type.toString() +")"+ Value.toString() + "(" + Index.toString()+")"; 
		return str;
	}

	public String Value2String()
	{
		String str = new String("");
		
		Integer Value = new Integer(GetValue());
		
		str = Value.toString(); 
		return str;
	}
	
	public static int GetCardValue(int nIndex)
	{
		if(nIndex < 0)
			nIndex *= -1;
		
		return nIndex%13+1;
	}

	public static int GetCardType(int nIndex)
	{
		if(nIndex < 0)
			nIndex *= -1;
		
		return nIndex/13;
	}
}
