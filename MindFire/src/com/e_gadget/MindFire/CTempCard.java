package com.e_gadget.MindFire;

public class CTempCard 
{
	private int 	m_nValue;  //The intermediate value from calculation
	private int		m_nIndex;  //The index of temporary card in intermediate card list
	
	public CTempCard(int nValue)
	{
		m_nValue = nValue;
		m_nIndex = -1;
	}

	public CTempCard(int nValue, int nIndex)
	{
		m_nValue = nValue;
		m_nIndex = nIndex;
	}

	public void SetIndex(int nIndex)
	{
		m_nIndex = nIndex;
	}

	public int GetIndex()
	{
		return m_nIndex;
	}

	public int GetValue()
	{
		return m_nValue;
	}
	
	public int GetType()
	{
		return -1;
	}

	public CTempCard Clone()
	{
		CTempCard card = new CTempCard(this.m_nValue, this.m_nIndex);
		return card;
	}

	public String toString()
	{
		String str = new String("");
		
		Integer Index = new Integer(GetIndex());
		Integer Value = new Integer(GetValue());
		
		str = "(-1)" + Value.toString() + "(" + Index.toString()+")"; 
		return str;
	}
	
	public String Value2String()
	{
		String str = new String("");
		
		Integer Value = new Integer(GetValue());
		
		str = Value.toString(); 
		return str;
	}
	
}
