package com.e_gadget.MindFire;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class CCardAnimator 
{
	//Previous card and the backside images
	private Drawable  		m_Card;
	private Rect			m_Boundary;
	private int 			m_nAnimationStep;
	private int				m_nCounter;
	private boolean			m_bBasicCard;
	private	boolean 		m_bCalculatiorCard;
	private int				m_nCardValue; 		//Basic Card: index.
												//Temp Card: value.
	private int				m_nPositionIndex; 	//Basic Card: index.
	
	public CCardAnimator()
	{
		m_Card = null;
		m_nAnimationStep = 10;
		m_nCounter = 0;
		m_Boundary = new Rect(0, 0, 71, 90);
		
		m_bCalculatiorCard = false;
		m_bBasicCard = true;
		m_nCardValue = -1;
								
		m_nPositionIndex = -1;
		
	}

	public CCardAnimator(Drawable pCard, Rect rect, int nValue, boolean bBasic, boolean bCalc, int nIndex)
	{
		m_Card = pCard;
		m_nAnimationStep = 15;
		m_nCounter = 0;
		m_Boundary = rect;
		
		m_bCalculatiorCard = bCalc;
		m_bBasicCard = bBasic;
		m_nCardValue = nValue;
								
		m_nPositionIndex = nIndex;
	}

	public void ResetAnimationState()
	{
		m_nCounter = 0;
	}
	
	public void SetAnimationStep(int n)
	{
		m_nAnimationStep = n;
	}
	
	public void SetBoundary(Rect rect)
	{
		m_Boundary = rect;
	}

	public void SetCardValue(int value)
	{
		m_nCardValue = value;
	}
	
	public void SetCardPosition(int index)
	{
		m_nPositionIndex = index;
	}
	
	public Drawable GetCard()
	{
		return m_Card;
	}

	public void OnTimerEvent()
	{
		++m_nCounter;
	}
	
	public void OnDraw(Canvas canvas)
	{
		if(0 <= m_nCounter && m_nCounter < m_nAnimationStep)
			OnDrawCardFlyAway(canvas, m_nCounter);
	}
	
	public void OnDrawCardFlyAway(Canvas canvas, int nStep)
	{
		if(m_Card != null)
		{	
			m_Card.setBounds(m_Boundary);
			canvas.save();
			int x = m_Boundary.centerX();
			int y = m_Boundary.centerX();
			//canvas.rotate(30.0f*((float)nStep)/((float)m_nAnimationStep), (float)x, (float)y);
			float fscale = ((float)(m_nAnimationStep-nStep))/((float)(m_nAnimationStep));
			canvas.scale(fscale*fscale*fscale, 1.0f);
			//canvas.rotate(6, (float)x, (float)y);
			canvas.rotate(10.0f*((float)nStep)/((float)m_nAnimationStep), (float)x, (float)y);
			m_Card.draw(canvas);
			canvas.restore();
		}
	}
	
	public void ReloadResource()
	{
		m_Card = null;
		
		m_Boundary = new Rect(0, 0, 1, 1);

		if(m_bBasicCard == true && -1 < m_nCardValue)
		{
			m_Card = CGameHelper.GetBasicCardImage(m_nCardValue);
			if(m_bCalculatiorCard == false)
			{	
				m_Boundary = CDealLayout.GetBasicCardRect(m_nPositionIndex);
			}
			else
			{
				if(m_nPositionIndex == 0)
					m_Boundary = CDealLayout.GetOperand1Rect();
				else if(m_nPositionIndex == 1)
					m_Boundary = CDealLayout.GetOperand2Rect();
				else
					m_Boundary = CDealLayout.GetResultCardRect();
			}
		}
		else if(m_bBasicCard == false && -1 < m_nCardValue && m_bCalculatiorCard == false)
		{
			m_Card = CGameHelper.GetTempCardImage(m_nCardValue);
			if(m_bCalculatiorCard == false)
			{	
				m_Boundary = CDealLayout.GetTempCardRect(m_nPositionIndex);
			}				
			else
			{
				if(m_nPositionIndex == 0)
					m_Boundary = CDealLayout.GetOperand1Rect();
				else if(m_nPositionIndex == 1)
					m_Boundary = CDealLayout.GetOperand2Rect();
				else
					m_Boundary = CDealLayout.GetResultCardRect();
			}
		}
	}
}
