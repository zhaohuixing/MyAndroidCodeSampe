package com.e_gadget.MindFire;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class CCalculation 
{

	private CCard 		m_FirstCard;
	private CCard 		m_SecondCard;
	private CCard 		m_ResultCard;
	private COperator  	m_Operator;
	
	private int 		m_nErrorCode; 	//1: Divider is zero
										//2: Can not be divided.
										//3: The result is negative
										//4: Invalid operator type
	
	private boolean		m_bDone; 
	
	public CCalculation()
	{
		m_FirstCard = null;
		m_SecondCard = null;
		m_ResultCard = null;
		m_Operator = new COperator();
		m_nErrorCode = 0;
		m_bDone = false;
	}
	
	public CCalculation Clone()
	{
		CCalculation opRet = new CCalculation();

		if(m_FirstCard != null)
			opRet.m_FirstCard = m_FirstCard.Clone();
		
		if(m_SecondCard != null)
			opRet.m_SecondCard = m_SecondCard.Clone();
		
		if(m_ResultCard != null)
			opRet.m_ResultCard = m_ResultCard.Clone();
		
		opRet.m_Operator.SetOperation(this.m_Operator.GetOperation());
		
		opRet.m_nErrorCode = m_nErrorCode;

		opRet.m_bDone = m_bDone;

		return opRet;
	}
	
	public int GetError()
	{
		return m_nErrorCode;
	}
	
	public void ResetErrorCode()
	{
		m_nErrorCode = 0;
	}	

	public void Reset()
	{
		m_FirstCard = null;
		m_SecondCard = null;
		m_ResultCard = null;
		m_Operator.Reset();
		m_nErrorCode = 0;
		m_bDone = false;
	}
	
	public boolean HasFirstCard()
	{
		boolean bRet = (m_FirstCard != null);
		return bRet;
	}

	public CCard GetFirstCard()
	{
		return m_FirstCard;
	}
	
	public boolean HasSecondCard()
	{
		boolean bRet = (m_SecondCard != null);
		return bRet;
	}

	public CCard GetSecondCard()
	{
		return m_SecondCard;
	}
	
	public boolean HasOperator()
	{
		boolean bRet = (m_Operator != null && m_Operator.IsValid());
		return bRet;
	}

	public boolean HasResult()
	{
		boolean bRet = (m_ResultCard != null);
		return bRet;
	}
	
	public void AddFirstCard(CCard card)
	{
		if(m_FirstCard != null && card != null && m_FirstCard.GetValue() != card.GetValue() && m_ResultCard != null)
		{
			m_ResultCard = null;
		}

		
		m_FirstCard = card;
		m_bDone = false;
		
		ResetErrorCode();
		Calculate();
	}

	public void AddSecondCard(CCard card)
	{
		if(m_SecondCard != null && card != null && m_SecondCard.GetValue() != card.GetValue() && m_ResultCard != null)
		{	
			m_ResultCard = null;
		}			
		
		m_SecondCard = card;
		m_bDone = false;
		ResetErrorCode();
		Calculate();
	}

	public void AddResultCard(CCard card) //For restore data
	{
		m_ResultCard = card;
		m_bDone = true;
		ResetErrorCode();
	}
	
	public boolean SetOperandCardAt(int index, CCard card)
	{
		boolean bRet = false;
		if(index == 0)
		{	
			AddFirstCard(card);
			bRet = true;
		}	
		else if(index == 1)
		{	
			AddSecondCard(card);
			bRet = true;
		}
		
		return bRet;
	}
	
	public void RemoveFirstCard()
	{
		m_FirstCard = null;
		m_ResultCard = null;
		m_bDone = false;
		ResetErrorCode();
	}

	public void RemoveSecondCard()
	{
		m_SecondCard = null;
		m_ResultCard = null;
		m_bDone = false;
		ResetErrorCode();
	}
	
	public boolean SetOperator(int nOp)
	{
		ResetErrorCode();

		if(m_Operator.GetOperation() != nOp)
		{	
			m_ResultCard = null;
			m_Operator.SetOperation(nOp);
			m_bDone = false;
		}
		Calculate();
		return true;
	}	

	public void ResetOperator()
	{
		m_ResultCard = null;
		m_bDone = false;
		m_Operator.Reset();
	}	
	
	public boolean IsValid()
	{
		boolean bRet = false;
		bRet = (m_FirstCard != null && m_SecondCard != null && m_Operator != null && m_Operator.IsValid());
		return bRet;
	}
	
	public boolean Calculate()
	{
		m_bDone = true;
		if(m_Operator != null && m_Operator.IsValid() && 
				m_FirstCard != null && m_SecondCard != null)
		{
			int n1 = m_FirstCard.GetValue();
			int n2 = m_SecondCard.GetValue();
			int nRet = m_Operator.Operation(n1, n2);
			if(nRet < 0)
			{
				m_ResultCard = null;
				m_bDone = false;
				return false;	
			}
			else
			{
				CTempCard tcard = new CTempCard(nRet);
				m_ResultCard = new CCard(tcard);
				m_ResultCard.LoadImage();
				return true;
			}				
		}
		else
		{
			m_ResultCard = null;
			m_bDone = false;
			return false;
		}
	}
	
	public CCard GetResultCard()
	{
		return m_ResultCard;
	}
	
	public int GetResultValue()
	{
		int nRet = -1;
		if(m_ResultCard != null)
		{
			nRet = m_ResultCard.GetValue();
		}	
		
		return nRet;
	}	

	public String toString()
	{
		String sRet = new String("?");
		String sFirstN = new String("?");
		String sOperator = new String("?");
		String sSecondN = new String("?");
		String sAssign = new String(" = ");
		String sResult = new String("?");
		
		if(m_FirstCard != null)
		{	
			sFirstN = m_FirstCard.toString();
		}	
		
		if(m_SecondCard != null)
		{	
			sSecondN = m_SecondCard.toString();
		}	
		
		if(m_Operator != null)
		{	
			sOperator = m_Operator.toString();
		}	
		
		if(m_ResultCard != null)
			sResult = m_ResultCard.toString();
		
		sRet = sFirstN + sOperator + sSecondN + sAssign + sResult;
		
		return sRet;
	}

	public String Express2String()
	{
		String sRet = new String("?");
		String sFirstN = new String("?");
		String sOperator = new String("?");
		String sSecondN = new String("?");
		String sAssign = new String(" = ");
		String sResult = new String("?");
		String sSpace = new String(" ");
		
		if(m_FirstCard != null)
		{	
			sFirstN = m_FirstCard.Value2String();
			if(sFirstN.length() == 1)
			{
				sFirstN += sSpace;
				sFirstN += sSpace;
			}
			else if(sFirstN.length() == 2)
			{
				sFirstN += sSpace;
			}
		}	
		
		if(m_SecondCard != null)
		{	
			sSecondN = m_SecondCard.Value2String();
			if(sSecondN.length() == 1)
			{
				sSecondN += sSpace;
				sSecondN += sSpace;
			}
			else if(sSecondN.length() == 2)
			{
				sSecondN += sSpace;
			}
		}
		
		if(m_Operator != null)
			sOperator = m_Operator.toString();
		
		if(m_ResultCard != null)
		{	
			sResult = m_ResultCard.Value2String();
		}
		
		sRet = sFirstN + sOperator + sSecondN + sAssign + sResult;
		
		return sRet;
	}
	
	public boolean IsDone()
	{
		return m_bDone;
	}
	
	public void ReloadResource()
	{
		if(m_FirstCard != null)
			m_FirstCard.LoadImage();
		
		if(m_SecondCard != null)
			m_SecondCard.LoadImage();
		
		if(m_ResultCard != null)
			m_ResultCard.LoadImage();
	}
	
	public int GetOperation()
	{
		int nRet = 0;
		
		if(m_Operator != null)
			nRet = m_Operator.GetOperation();
		
		return nRet;
	}
	
	//Draw result 
	public void DrawResultCalculation(Canvas canvas, Rect bounds)
	{
		int nCardWidth; 
		int nCardHeight;
		
		Drawable c1image = m_FirstCard.GetCardImage(); 
		Drawable c2image = m_SecondCard.GetCardImage(); 
		Drawable rimage = m_ResultCard.GetCardImage(); 
		Drawable opimage = CGameHelper.GetSignImage(m_Operator.GetOperation(), false);
		Drawable assimage = CGameHelper.GetSignImage(5, false);

		Rect rect = CDealLayout.GetOperand1Rect();
		rect.offset(-CDealLayout.m_OperandX1, -CDealLayout.m_OperandY1);
		nCardWidth = rect.width()*4/5; 
		nCardHeight = rect.height()*4/5;
		rect.left = rect.left*4/5 + bounds.left;
		rect.top = rect.top*4/5 + bounds.top;
		rect.right = rect.left + nCardWidth;
		rect.bottom = rect.top + nCardHeight;
		c1image.setBounds(rect);
		c1image.draw(canvas);

		rect = CDealLayout.GetOperand2Rect();
		rect.offset(-CDealLayout.m_OperandX1, -CDealLayout.m_OperandY1);
		nCardWidth = rect.width()*4/5; 
		nCardHeight = rect.height()*4/5;
		rect.left = rect.left*4/5 + bounds.left;
		rect.top = rect.top*4/5 + bounds.top;
		rect.right = rect.left + nCardWidth;
		rect.bottom = rect.top + nCardHeight;
		c2image.setBounds(rect);
		c2image.draw(canvas);
		
		rect = CDealLayout.GetResultCardRect();
		rect.offset(-CDealLayout.m_OperandX1, -CDealLayout.m_OperandY1);
		nCardWidth = rect.width()*4/5; 
		nCardHeight = rect.height()*4/5;
		rect.left = rect.left*4/5 + bounds.left;
		rect.top = rect.top*4/5 + bounds.top;
		rect.right = rect.left + nCardWidth;
		rect.bottom = rect.top + nCardHeight;
		rimage.setBounds(rect);
		rimage.draw(canvas);
		
		rect = CDealLayout.GetOperatorRect();
		rect.offset(-CDealLayout.m_OperandX1, -CDealLayout.m_OperandY1);
		nCardWidth = rect.width()*4/5; 
		nCardHeight = rect.height()*4/5;
		rect.left = rect.left*4/5 + bounds.left;
		rect.top = rect.top*4/5 + bounds.top;
		rect.right = rect.left + nCardWidth;
		rect.bottom = rect.top + nCardHeight;
		opimage.setBounds(rect);
		opimage.draw(canvas);
		
		rect = CDealLayout.GetCalculateRect();
		rect.offset(-CDealLayout.m_OperandX1, -CDealLayout.m_OperandY1);
		nCardWidth = rect.width()*4/5; 
		nCardHeight = rect.height()*4/5;
		rect.left = rect.left*4/5 + bounds.left;
		rect.top = rect.top*4/5 + bounds.top;
		rect.right = rect.left + nCardWidth;
		rect.bottom = rect.top + nCardHeight;
		assimage.setBounds(rect);
		assimage.draw(canvas);
	}
}
