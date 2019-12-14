package com.e_gadget.MindFire;

import android.graphics.Rect;

public class CResultLayout 
{
	public static int m_nClinetX;  	//The client area width;
	public static int m_nClinetY;  	//The client area height;

	public static int m_NextWidth;	//The "Next" button width
	public static int m_NextHeight;	//The "Next" button height

	public static int m_NextLotWidth;	//The "Next" button area width: = m_NextWidth + m_HLWidth*2;     
	public static int m_NextLotHeight;	//The "Next" button area height: = m_NextHeight + m_HLWidth*2;     

	public static int m_NextStartX;		//The "Deal" button area left position 
	public static int m_NextStartY;		//The "Deal" button area top position
	
	public static int m_ResultWidth;	//The "Result" area width
	public static int m_ResultHeight;	//The "Result" area height

	public static int m_ResultStartX;		//The "Result" area left position 
	public static int m_ResultStartY;		//The "Result" area top position
	
	public static int m_HLWidth;
	
	public static int m_nTitleTop;
	
	public static void Initialize_HVGAP()
	{
		m_nClinetX = CDealLayout.m_nClinetX;
		m_nClinetY = CDealLayout.m_nClinetY;
		
		m_NextWidth = CDealLayout.m_DealWidth;
		m_NextHeight = CDealLayout.m_DealHeight;

		m_NextLotWidth = CDealLayout.m_DealLotWidth;;     
		m_NextLotHeight = CDealLayout.m_DealLotHeight;     

		m_NextStartX = CDealLayout.m_DealStartX; 
		m_NextStartY = CDealLayout.m_DealStartY;
		
		m_ResultWidth = m_nClinetX*4/5;
		m_ResultHeight = m_nClinetY*4/5;
		
		m_nTitleTop = m_nClinetY/15;

		m_ResultStartX = (m_nClinetX-m_ResultWidth)/2; 
		m_ResultStartY = m_nClinetY-m_NextLotHeight-m_ResultHeight;
		
		m_HLWidth = CDealLayout.m_HLWidth;
	}

	public static void Initialize_HVGAL()
	{
		m_nClinetX = CDealLayout.m_nClinetX;
		m_nClinetY = CDealLayout.m_nClinetY;
		
		m_NextWidth = CDealLayout.m_DealWidth;
		m_NextHeight = CDealLayout.m_DealHeight;

		m_NextLotWidth = CDealLayout.m_DealLotWidth;;     
		m_NextLotHeight = CDealLayout.m_DealLotHeight;     

		m_NextStartX = CDealLayout.m_DealStartX; 
		m_NextStartY = CDealLayout.m_DealStartY;
		
		m_ResultWidth = m_nClinetX*3/4;
		m_ResultHeight = m_nClinetY*3/4;

		m_nTitleTop = m_nClinetY/10;
		
		m_ResultStartX = (m_nClinetX-m_ResultWidth)/2; 
		m_ResultStartY = m_nClinetY-m_NextLotHeight-m_ResultHeight;
		m_HLWidth = CDealLayout.m_HLWidth;
	}

	public static void Initialize_QVGAP()
	{
		m_nClinetX = CDealLayout.m_nClinetX;
		m_nClinetY = CDealLayout.m_nClinetY;
		
		m_NextWidth = CDealLayout.m_DealWidth;
		m_NextHeight = CDealLayout.m_DealHeight;

		m_NextLotWidth = CDealLayout.m_DealLotWidth;;     
		m_NextLotHeight = CDealLayout.m_DealLotHeight;     

		m_NextStartX = CDealLayout.m_DealStartX; 
		m_NextStartY = CDealLayout.m_DealStartY;
		
		m_ResultWidth = m_nClinetX*2/3;
		m_ResultHeight = m_nClinetY*2/3;

		m_nTitleTop = m_nClinetY/6;
		
		m_ResultStartX = (m_nClinetX-m_ResultWidth)/2; 
		m_ResultStartY = m_nClinetY-m_NextLotHeight-m_ResultHeight;
		m_HLWidth = CDealLayout.m_HLWidth;
	}

	public static void Initialize_QVGAL()
	{
		m_nClinetX = CDealLayout.m_nClinetX;
		m_nClinetY = CDealLayout.m_nClinetY;
		
		m_NextWidth = CDealLayout.m_DealWidth;
		m_NextHeight = CDealLayout.m_DealHeight;

		m_NextLotWidth = CDealLayout.m_DealLotWidth;;     
		m_NextLotHeight = CDealLayout.m_DealLotHeight;     

		m_NextStartX = CDealLayout.m_DealStartX; 
		m_NextStartY = CDealLayout.m_DealStartY;
		
		m_ResultWidth = m_nClinetX*2/3;
		m_ResultHeight = m_nClinetY*2/3;

		m_nTitleTop = m_nClinetY/6;
		
		m_ResultStartX = (m_nClinetX-m_ResultWidth)/2; 
		m_ResultStartY = m_nClinetY-m_NextLotHeight-m_ResultHeight;
		m_HLWidth = CDealLayout.m_HLWidth;
	}
	
	public static Rect GetResultRect()
	{
		Rect rect = new Rect(m_ResultStartX, m_ResultStartY, m_ResultStartX+m_ResultWidth, m_ResultStartY+m_ResultHeight);
		return rect;
	}
	
	public static Rect GetNextRect()
	{
		Rect rect = new Rect(m_NextStartX+m_HLWidth, m_NextStartY+m_HLWidth, m_NextStartX+m_HLWidth+m_NextWidth, m_NextStartY+m_HLWidth+m_NextHeight);
		return rect;
	}
	
	public static Rect GetNextBoundary()
	{
		Rect rect = new Rect(m_NextStartX, m_NextStartY, m_NextStartX+m_NextLotWidth, m_NextStartY+m_NextLotHeight);
		return rect;
	}
	
	public static boolean IsHitNextButton(int x, int y)
	{
		boolean bRet = false;
		Rect rect = GetNextRect();
		bRet = rect.contains(x, y);	
		return bRet;	
	}
}
