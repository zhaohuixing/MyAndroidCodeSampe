package com.e_gadget.MindFire;

public class COperator 
{
	public static final int NONE = 0;
	public static final int PLUS = 1;
	public static final int MINUS = 2;
	public static final int TIME = 3;
	public static final int DIVIDE = 4;

	private int		m_nOperator;
	
	private int 	m_nErrorCode; 	//1: Divider is zero
									//2: Can not be divided.
									//3: The result is negative
									//4: Invalid operator type
	
	
	public COperator()
	{
		m_nErrorCode = 0;
		m_nOperator = 0;
	}
	
	public COperator Clone()
	{
		COperator opRet = new COperator();
		opRet.SetOperation(this.m_nOperator);
		return opRet;
	}
	
	public void Reset()
	{
		m_nErrorCode = 0;
		m_nOperator = 0;
	}

	public boolean IsValid()
	{
		boolean bRet = (PLUS <= m_nOperator && m_nOperator <= DIVIDE);
		return bRet;
	}	
	
	public int GetError()
	{
		return m_nErrorCode;
	}
	
	public void ResetErrorCode()
	{
		m_nErrorCode = 0;
	}	

	public int GetOperation()
	{
		return m_nOperator;
	}
	
	public void SetOperation(int nOp)
	{
		m_nErrorCode = 0;
		m_nOperator = nOp;
	}
	
	public void Set2Plus()
	{
		m_nErrorCode = 0;
		m_nOperator = PLUS;
	}

	public void Set2Minus()
	{
		m_nErrorCode = 0;
		m_nOperator = MINUS;
	}

	public void Set2Time()
	{
		m_nErrorCode = 0;
		m_nOperator = TIME;
	}

	public void Set2Divide()
	{
		m_nErrorCode = 0;
		m_nOperator = DIVIDE;
	}

	public int Operation(int v1, int v2)
	{
		int nRet = -1;
		
		switch(m_nOperator)
		{
			case PLUS:
				nRet = v1 + v2;
				break;
			case MINUS:
				if(v1 < v2)
				{
					m_nErrorCode = 3;
				}
				else
				{	
					nRet = v1 - v2;
				}					
				break;
			case TIME:
				nRet = v1 * v2;
				break;
			case DIVIDE:
				if(v2 == 0)	
				{	
					m_nErrorCode = 1;
				}
				else
				{
					int nr = v1%v2;
					if(nr != 0)
					{
						m_nErrorCode = 2;
					}
					else
					{
						nRet = v1/v2;
					}	
				}	
				break;
			default:
				break;
		}
		
		return nRet;
	}

	public String toString()
	{
		String sRet = new String("?");
		
		switch(m_nOperator)
		{
			case PLUS:
				sRet = " + ";
				break;
			case MINUS:
				sRet = " - ";
				break;
			case TIME:
				sRet = " x ";
				break;
			case DIVIDE:
				sRet = " ÷ ";
				break;
		}
		
		return sRet;
	}
}
