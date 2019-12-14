package com.xgadget.SimpleGambleWheel;

public class CLuckScope 
{
	private int m_nStartAngle;
	private int m_nEndAngle;
	
	public CLuckScope() 
	{
		// TODO Auto-generated constructor stub
		m_nStartAngle = 0;
		m_nEndAngle = 0;
	}

	public void CreateScope(int startAngle, int endAngle)
	{
        m_nStartAngle = startAngle;
        m_nEndAngle = endAngle;
	}

	public boolean InScope(int angle)
	{
		boolean bRet = false;
		
		if(m_nEndAngle < m_nStartAngle) //Scope cross 360/0 degree limit
		{
			if((m_nStartAngle <= angle && angle <= 360) ||(0 <= angle && angle < m_nEndAngle))
				bRet = true;
		}
		else
		{
			if(m_nStartAngle <= angle && angle < m_nEndAngle)
				bRet = true;
		}
		
		return bRet;
	    
	}

	public int GetSweepAngle()
	{
		int nRet = 0;
	    if(m_nEndAngle < m_nStartAngle) //Scope cross 360/0 degree limit
	    {
	        nRet = (360-m_nStartAngle) + m_nEndAngle;
	    }
	    else
	    {
	        nRet = m_nEndAngle - m_nStartAngle;
	    }
		
		return nRet;
	}
	
}
