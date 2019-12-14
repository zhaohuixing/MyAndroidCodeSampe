package com.e_gadget.MindFire;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class CDeal 
{
	public static final String KEY_DEAL = "Deal_Key";
	public static final String KEY_DEAL_CARD = "Deal_Card_Key";
	public static final String KEY_DEAL_TEMPCARD = "Deal_TempCard_Key";
	public static final String KEY_CARD_VALUE_PREFIX = "Deal_Card_Value_";
	public static final String KEY_CARD_SELECT_PREFIX = "Deal_Card_Select_";
	public static final String KEY_TEMPCARD_VALUE_PREFIX = "Deal_TempCard_Value_";
	public static final String KEY_TEMPCARD_SELECT_PREFIX = "Deal_TempCard_Select_";

	public static final String KEY_PCAL_FIRST_CARD_TYPE = "Pending_Card1_Type"; //0: basic, 1: temp, other: N/A 
	public static final String KEY_PCAL_FIRST_CARD_VALUE = "Pending_Card1_Value"; // 
	public static final String KEY_PCAL_FIRST_CARD_INDEX = "Pending_Card1_Index"; // 

	public static final String KEY_PCAL_SECOND_CARD_TYPE = "Pending_Card2_Type"; //0: basic, 1: temp, other: N/A 
	public static final String KEY_PCAL_SECOND_CARD_VALUE = "Pending_Card2_Value"; // 
	public static final String KEY_PCAL_SECOND_CARD_INDEX = "Pending_Card2_Index"; // 

	public static final String KEY_PCAL_RESULT_CARD_TYPE = "Pending_Card3_Type"; //0: basic, 1: temp, other: N/A 
	public static final String KEY_PCAL_RESULT_CARD_VALUE = "Pending_Card3_Value"; // 
	public static final String KEY_PCAL_RESULT_CARD_INDEX = "Pending_Card3_Index"; // 

	public static final String KEY_PCAL_OPERATOR_KEY = "Pending_Operator_Key"; //0: none, 1: plus..... 

	public static final String KEY_CALSTEP = "CalSteps_Key"; 
	
	public static final String KEY_CALSTEP_FIRST_CARD_TYPE = "Steps_Card1_Type_"; //0: basic, 1: temp, other: N/A 
	public static final String KEY_CALSTEP_FIRST_CARD_VALUE = "Steps_Card1_Value_"; // 
	public static final String KEY_CALSTEP_FIRST_CARD_INDEX = "Steps_Card1_Index_"; // 

	public static final String KEY_CALSTEP_SECOND_CARD_TYPE = "Steps_Card2_Type_"; //0: basic, 1: temp, other: N/A 
	public static final String KEY_CALSTEP_SECOND_CARD_VALUE = "Steps_Card2_Value_"; // 
	public static final String KEY_CALSTEP_SECOND_CARD_INDEX = "Steps_Card2_Index_"; // 

	public static final String KEY_CALSTEP_RESULT_CARD_TYPE = "Steps_Card3_Type_"; //0: basic, 1: temp, other: N/A 
	public static final String KEY_CALSTEP_RESULT_CARD_VALUE = "Steps_Card3_Value_"; // 
	public static final String KEY_CALSTEP_RESULT_CARD_INDEX = "Steps_Card3_Index_"; // 

	public static final String KEY_CALSTEP_OPERATOR_KEY = "Steps_Operator_Key_"; //0: none, 1: plus..... 

	public static final int CARD_NUMBER_DEAL = 13;
	
	public Vector<CCard>			m_DealCards;
	public Vector<CCard>			m_TempCards;
	public Vector<CCalculation>		m_CalculationSteps;
	
    public CCalculation				m_Calculation;	
    
    public boolean					m_bDirty;	

    /** Paint to draw the cursor highlight on screen. */
    private Paint m_CursorPaint;
	
	public CDeal()
	{
		m_DealCards = new Vector<CCard>();
		m_DealCards.clear();
		m_TempCards = new Vector<CCard>();
		m_TempCards.clear();
		m_Calculation = new CCalculation();
		m_CalculationSteps = new Vector<CCalculation>();
		m_CalculationSteps.clear();
		m_bDirty = false;

	    // Initialize paint for cursor highlight
		m_CursorPaint = new Paint();
		m_CursorPaint.setAntiAlias(true);
		m_CursorPaint.setARGB(255, 255, 32, 32);
		
	}
	
	public void Reset()
	{
		m_DealCards.clear();
		m_TempCards.clear();
		m_Calculation.Reset();
		m_CalculationSteps.clear();
		m_bDirty = false;
		
	}
	
	public boolean IsDirty()
	{
		return m_bDirty;
	}
	public boolean IsDealDone()
	{
		boolean bRet = false;
		
		if(GetLeftCardCount() == 0 && GetLeftTempCardCount() == 0)
		{
			if(!m_Calculation.IsValid())
			{
				bRet = true;
			}
			else
			{
				if(m_Calculation.IsDone())
					bRet = true;
			}
		}
		
		return bRet;
	}

	public boolean IsLastCalculation()
	{
		boolean bRet = (GetLeftCardCount() == 0 && GetLeftTempCardCount() == 0);
		
		return bRet;
	}
	
	public void ResetCalculator()
	{
		m_Calculation.Reset();
	}
	
	public int GetCardCount()
	{
		return (int)m_DealCards.size();
	}

	public int GetLeftCardCount()
	{
		int nCount = m_DealCards.size();
		int nRet = 0;

		for(int i = 0; i < nCount; ++i)
		{
			if(!m_DealCards.elementAt(i).IsSelected())
				++nRet;
		}
		return nRet;
	}

	public int GetTempCardCount()
	{
		return m_TempCards.size();
	}

	public int GetLeftTempCardCount()
	{
		int nCount = m_TempCards.size();
		int nRet = 0;

		for(int i = 0; i < nCount; ++i)
		{
			if(!m_TempCards.elementAt(i).IsSelected())
				++nRet;
		}
		return nRet;
	}
	
	public boolean AddCard(int nIndex)
	{
		if(4 <= m_DealCards.size())
			return false;
		
		CCard card = new CCard(nIndex);
		card.LoadImage();
		m_DealCards.add(card);

		m_bDirty = true;
		return true;
	}

	public boolean AddCard(CCardBase carddata)
	{
		if(4 <= (int)m_DealCards.size())
			return false;
		
		CCard card = new CCard(carddata);
		card.LoadImage();
		m_DealCards.add(card);
		
		m_bDirty = true;
		return true;
	}

	public int QueryCardByIndex(int index)
	{
		int nRet = -1;

		int nCount = m_DealCards.size();
		
		if(nCount <= 0)
			return nRet;

		for(int i = 0; i < nCount; ++i)
		{
			if(m_DealCards.elementAt(i).GetIndex() == index)
			{	
				nRet = i;
				return nRet;
			}	
		}
		
		return nRet;
	}
	
	public int QueryAvailableCardWithValue(int nValue)
	{
		int nPos = -1;

		int nCount = m_DealCards.size();
		
		if(nCount <= 0)
			return nPos;

		for(int i = 0; i < nCount; ++i)
		{
			if(!m_DealCards.elementAt(i).IsSelected() && m_DealCards.elementAt(i).GetValue() == nValue)
			{	
				nPos = i;
				return nPos;
			}	
		}
		
		return nPos;
	}

	public int QueryAvailableTempCardWithValue(int nValue)
	{
		int nPos = -1;

		int nCount = m_TempCards.size();
		
		if(nCount <= 0)
			return nPos;

		for(int i = 0; i < nCount; ++i)
		{
			if(!m_TempCards.elementAt(i).IsSelected() && m_TempCards.elementAt(i).GetValue() == nValue)
			{	
				nPos = i;
				return nPos;
			}	
		}
		
		return nPos;
	}
	
	public int AddTempCard(int nValue)
	{
		if(4 <= (int)m_TempCards.size())
			return -1;

		int nIndex = m_TempCards.size();
		CTempCard carddata = new CTempCard(nValue);
		carddata.SetIndex(nIndex);
		CCard card = new CCard(carddata);
		card.LoadImage();
		m_TempCards.add(card);
		
		return nIndex;
	}

	public int AddTempCard(CTempCard carddata)
	{
		if(4 <= (int)m_TempCards.size())
			return -1;

		int nIndex = m_TempCards.size();
		carddata.SetIndex(nIndex);
		CCard card = new CCard(carddata);
		card.LoadImage();
		m_TempCards.add(card);
		
		return nIndex;
	}

	public int AddTempCard(CCard card)
	{
		if(4 <= (int)m_TempCards.size())
			return -1;

		int nIndex = m_TempCards.size();
		card.SetTempCardIndex(nIndex);
		m_TempCards.add(card);
		
		return nIndex;
	}
	
	public CCard GetCard(int nPos)
	{
		CCard card = null;
		if(nPos < m_DealCards.size() && 0 <= nPos)
		{
			card = m_DealCards.get(nPos).Clone(); 
		}
		
		return card;
	}

	public CCard GetTempCard(int nPos)
	{
		CCard card = null;
		if(nPos < m_TempCards.size() && 0 <= nPos)
		{
			card = m_TempCards.get(nPos).Clone(); 
		}
		
		return card;
	}
	
	public boolean IsCardSelected(int nPos)
	{
		boolean bRet = false;
		
		if(nPos < m_DealCards.size() && 0 <= nPos)
		{
			bRet = m_DealCards.elementAt(nPos).IsSelected(); 
		}
		
		return bRet; 
	}

	public void SetCardSelected(int nPos, boolean bSelected)
	{
		if(nPos < m_DealCards.size() && 0 <= nPos)
		{
			m_DealCards.elementAt(nPos).SetSelected(bSelected); 
		}
	}

	public void RestoreCardStatus(int nPos)
	{
		SetCardSelected(nPos, false);
	}
	
	
	public boolean IsTempCardSelected(int nPos)
	{
		boolean bRet = false;
		
		if(nPos < m_TempCards.size() && 0 <= nPos)
		{
			bRet = m_TempCards.elementAt(nPos).IsSelected(); 
		}
		
		return bRet; 
	}

	public void SetTempCardSelected(int nPos, boolean bSelected)
	{
		if(nPos < m_TempCards.size() && 0 <= nPos)
		{
			m_TempCards.elementAt(nPos).SetSelected(bSelected); 
		}
	}

	public void RestoreTempCardStatus(int nPos)
	{
		SetTempCardSelected(nPos, false);
	}
	
	public void Restore()
	{
		int nCount = m_DealCards.size();
		
		for(int i = 0; i < nCount; ++i)
		{
			m_DealCards.elementAt(i).SetSelected(false);
		}
		m_TempCards.clear();
		m_Calculation.Reset();
		m_CalculationSteps.clear();
		CLayoutCursor cursor = CGameHighLight.GetCursor();
		cursor.Set(CLayoutCursor.TYPE_BASIC_CARD, 0);
		CGameHighLight.SetHighLight(cursor);
	}
	
	public CAnswerRecord CreateAnswerRecord()
	{
		if(GetLeftCardCount() == 0 && GetLeftTempCardCount() == 0 && m_Calculation.IsValid() && m_Calculation.IsDone())
		{
			CacheAndResetCalculation();
		}
		CAnswerRecord arObj = new CAnswerRecord();
		
		int nCount;// = this.m_DealCards.size();
		int i;
//		for(i = 0; i < nCount; ++i)
//			arObj.m_DealCards.add(this.m_DealCards.elementAt(i).Clone());

		nCount = this.m_CalculationSteps.size();
		for(i = 0; i < nCount; ++i)
			//arObj.m_CalculationSteps.add(this.m_CalculationSteps.elementAt(i).Clone());
			arObj.m_CalculationSteps.add(this.m_CalculationSteps.elementAt(i));
		
		return arObj;
	}
	
	public boolean SetCardAsOperand1(int nPos)
	{
		boolean bRet = false;
		
		if(IsCardSelected(nPos))
			return bRet;
		
		CCard card = GetCard(nPos);
		m_Calculation.AddFirstCard(card);
		SetCardSelected(nPos, true);
		bRet = true;
		
		CLayoutCursor cursor = CGameHighLight.GetCursor();
		cursor.Set(CLayoutCursor.TYPE_OPERAND_CARD, 0);
		CGameHighLight.SetHighLight(cursor);
		
		return bRet;
	}

	public boolean SetCardAsOperand2(int nPos)
	{
		boolean bRet = false;
		
		if(IsCardSelected(nPos))
			return bRet;
		
		CCard card = GetCard(nPos);
		m_Calculation.AddSecondCard(card);
		SetCardSelected(nPos, true);
		bRet = true;

		CLayoutCursor cursor = CGameHighLight.GetCursor();
		cursor.Set(CLayoutCursor.TYPE_OPERAND_CARD, 1);
		CGameHighLight.SetHighLight(cursor);
		
		return bRet;
	}

	public boolean SetTempCardAsOperand1(int nPos)
	{
		boolean bRet = false;
		
		if(IsTempCardSelected(nPos))
			return bRet;
		
		CCard card = GetTempCard(nPos);
		m_Calculation.AddFirstCard(card);
		SetTempCardSelected(nPos, true);
		bRet = true;

		CLayoutCursor cursor = CGameHighLight.GetCursor();
		cursor.Set(CLayoutCursor.TYPE_OPERAND_CARD, 0);
		CGameHighLight.SetHighLight(cursor);
		
		return bRet;
	}

	public boolean SetTempCardAsOperand2(int nPos)
	{
		boolean bRet = false;
		
		if(IsTempCardSelected(nPos))
			return bRet;
		
		CCard card = GetTempCard(nPos);
		m_Calculation.AddSecondCard(card);
		SetTempCardSelected(nPos, true);
		bRet = true;

		CLayoutCursor cursor = CGameHighLight.GetCursor();
		cursor.Set(CLayoutCursor.TYPE_OPERAND_CARD, 1);
		CGameHighLight.SetHighLight(cursor);
		
		return bRet;
	}
	
	public void SetOperator(int nOp)
	{
		m_Calculation.SetOperator(nOp);
	}
	
	public void SetOperandCard(int index, CCard card)
	{
		boolean bRet = m_Calculation.SetOperandCardAt(index, card);
		if(bRet)
		{
			CLayoutCursor cursor = CGameHighLight.GetCursor();
			cursor.Set(CLayoutCursor.TYPE_OPERAND_CARD, index);
			CGameHighLight.SetHighLight(cursor);
		}
	}
	
	public CCard GetOperandCard(int index)
	{
		CCard card = null;
		
		switch(index)
		{
			case 0:
				card = m_Calculation.GetFirstCard();
				break;
			case 1:
				card = m_Calculation.GetSecondCard();
				break;
		}
		
		return card;
	}

	public CCard GetResultCard()
	{
		CCard card = null;
		
		card = m_Calculation.GetResultCard();
		
		return card;
	}
	
	public boolean CanCalculate()
	{
		return m_Calculation.IsValid();
	}

	public boolean Calculate()
	{
		return m_Calculation.Calculate();
	}
	
	public int GetCalculationValue()
	{
		return m_Calculation.GetResultValue();
	}
	
	public void CacheAndResetCalculation()
	{
		m_CalculationSteps.add(m_Calculation);
		m_Calculation = new CCalculation();		
	}
	
	public String toString()
	{
		String sText = new String();

    	int nCount = m_DealCards.size();
    	if(0 < nCount)
    	{	
    		int i;
    		for(i = 0; i < nCount; ++i)
    		{
    			String sTemp;
    			CCard card = m_DealCards.get(i);
    			sTemp = card.toString();
    			sText += sTemp;  
    			sText += "__";  
    		}
    	}
    	return sText;
	}
	
	public String StringOfLeftCards()
	{
		String sText = new String();

    	int nCount = m_DealCards.size();
    	if(0 < nCount)
    	{	
    		int i;
    		for(i = 0; i < nCount; ++i)
    		{
    			if(!IsCardSelected(i))
    			{	
    				String sTemp;
    				CCard card = m_DealCards.get(i);
    				sTemp = card.toString();
    				sText += sTemp;  
    				sText += "__";
    			}    				
    		}
    	}
    	return sText;
	}

	public String StringOfLeftTempCards()
	{
		String sText = new String();

    	int nCount = m_TempCards.size();
    	if(0 < nCount)
    	{	
    		int i;
    		for(i = 0; i < nCount; ++i)
    		{
    			if(!IsTempCardSelected(i))
    			{	
    				String sTemp;
    				CCard card = m_TempCards.get(i);
    				sTemp = card.toString();
    				sText += sTemp;  
    				sText += "__";
    			}    				
    		}
    	}
    	return sText;
	}

	public boolean GetRightAnswer(int nAnswer)
	{
		boolean bRet = false;
		if(GetLeftCardCount() == 0 && GetLeftTempCardCount() == 1)
		{	
			int nRet = QueryAvailableTempCardWithValue(nAnswer);
			if(0 <= nRet)
				bRet = true;
		}
		else if(GetLeftCardCount() == 0 && GetLeftTempCardCount() == 0)
		{
			if(GetCalculationValue() == nAnswer)
				bRet = true;
		}
		
		return bRet;
	}
	

	public boolean CursorTest(CLayoutCursor cursor)
	{
		boolean bRet = false;
		int nType = cursor.GetType();
		int nIndex = cursor.GetIndex();

		switch(nType)
		{
		case CLayoutCursor.TYPE_NONE:
			bRet = false;
			break;
		case CLayoutCursor.TYPE_BASIC_CARD:
			if(0 <= nIndex && nIndex < GetCardCount())
			{	
				if(IsCardSelected(nIndex))
					bRet = false;
				else
					bRet = true;
			}					
			else
			{	
				bRet = false;
			}
			break;
		case CLayoutCursor.TYPE_TEMP_CARD:
			if(0 <= nIndex && nIndex < GetTempCardCount())
			{	
				if(IsTempCardSelected(nIndex))
					bRet = false;
				else
					bRet = true;
			}					
			else
			{	
				bRet = false;
			}
			break;
		case CLayoutCursor.TYPE_OPERAND_CARD:
			if(nIndex == 0)
			{	
				if(m_Calculation.HasFirstCard())
					bRet = true;
				else
					bRet = false;
			}					
			else
			{	
				if(m_Calculation.HasSecondCard())
					bRet = true;
				else
					bRet = false;
			}
			break;
		case CLayoutCursor.TYPE_OPERATOR_BUTTON:
			bRet = true;
			break;
		case CLayoutCursor.TYPE_CALCULATE_BUTTON:
			bRet = true;
			break;
		case CLayoutCursor.TYPE_RESULT_CARD:
			if(m_Calculation.HasResult())
			{
				bRet = true;
			}					
			else
			{	
				bRet = false;
			}
			break;
		case CLayoutCursor.TYPE_SIGNSBAR_BUTTON:
			if(0 <= nIndex && nIndex <= 3)
			{
				bRet = true;
			}					
			else
			{	
				bRet = false;
			}
			break;
		case CLayoutCursor.TYPE_CANCEL_BUTTON:
			bRet = true;
			break;
		case CLayoutCursor.TYPE_NEWDEAL_BUTTON:
			bRet = true;
			break;
		}
		
		return bRet;
	}

	public CLayoutCursor HitTest(int x, int y)
	{
		CLayoutCursor cursor = new CLayoutCursor();

		Rect rect;
		int i;
    	int nCount = m_DealCards.size();
    	if(0 < nCount)
    	{	
    		for(i = 0; i < nCount; ++i)
    		{
    			if(!IsCardSelected(i))
    			{
    				rect = CDealLayout.GetBasicCardRect(i);
    				if(rect.contains(x, y))
    				{
    					cursor.Set(CLayoutCursor.TYPE_BASIC_CARD, i);
    					return cursor; 
    				}
    			}    				
    		}
    	}
		
    	nCount = m_TempCards.size();
    	if(0 < nCount)
    	{
    		int i2 = 0;
    		for(i = 0; i < nCount; ++i)
    		{
    			if(!IsTempCardSelected(i))
    			{	
    				rect = CDealLayout.GetTempCardRect(i2);
    				if(rect.contains(x, y))
    				{
    					cursor.Set(CLayoutCursor.TYPE_TEMP_CARD, i2);
    					return cursor; 
    				}
    				++i2;
    			}    				
    		}
    	}

		if(m_Calculation.HasFirstCard())
		{	
			rect = CDealLayout.GetOperand1Rect();
			if(rect.contains(x, y))
			{
				cursor.Set(CLayoutCursor.TYPE_OPERAND_CARD, 0);
				return cursor; 
			}
		}

		if(m_Calculation.HasSecondCard())
		{	
			rect = CDealLayout.GetOperand2Rect();
			if(rect.contains(x, y))
			{
				cursor.Set(CLayoutCursor.TYPE_OPERAND_CARD, 1);
				return cursor; 
			}
		}

		rect = CDealLayout.GetOperatorRect();
		if(rect.contains(x, y))
		{
			cursor.Set(CLayoutCursor.TYPE_OPERATOR_BUTTON, 0);
			return cursor; 
		}

		rect = CDealLayout.GetCalculateRect();
		if(rect.contains(x, y))
		{
			cursor.Set(CLayoutCursor.TYPE_CALCULATE_BUTTON, 0);
			return cursor; 
		}

		rect = CDealLayout.GetResultCardRect();
		if(rect.contains(x, y))
		{
			cursor.Set(CLayoutCursor.TYPE_RESULT_CARD, 0);
			return cursor; 
		}

		rect = CDealLayout.GetCancelButtonRect();
		if(rect.contains(x, y))
		{
			cursor.Set(CLayoutCursor.TYPE_CANCEL_BUTTON, 0);
			return cursor; 
		}

		rect = CDealLayout.GetNewDealRect();
		if(rect.contains(x, y))
		{
			cursor.Set(CLayoutCursor.TYPE_NEWDEAL_BUTTON, 0);
			return cursor; 
		}
		
		for(i = 0; i < 4; ++i)
		{
			rect = CDealLayout.GetSignsRect(i);
			if(rect.contains(x, y))
			{
				cursor.Set(CLayoutCursor.TYPE_SIGNSBAR_BUTTON, i);
				return cursor; 
			}
		}
		
		return cursor;
	}
	
	public int TempCardDrawIndexFromArrayIndex(int nRealIndex)
	{
		int nRet = -1;

		int nCount = m_TempCards.size();
		int n = 0;

		for(int i = 0; i < nCount; ++i)
		{
			if(nRealIndex == i && !m_TempCards.elementAt(i).IsSelected())
			{
				nRet = n;
				return nRet;
			}
			if(!m_TempCards.elementAt(i).IsSelected())
				++n;
		}
		
		return nRet;
	}

	public int TempCardArrayIndexFromDrawIndex(int nDrawIndex)
	{
		int nRet = -1;

		int nCount = m_TempCards.size();
		int n = 0;

		for(int i = 0; i < nCount; ++i)
		{
			if(!m_TempCards.elementAt(i).IsSelected())
			{
				if(n == nDrawIndex)
				{
					nRet = i;
					return nRet;
				}
				else
				{
					++n;
				}
			}
		}
		
		return nRet;
	}

	
	public void RestoreOperandBackToOrginal(int nIndex)
	{
		CCard card = null;
		if(nIndex == 0 && m_Calculation.HasFirstCard())
			card = m_Calculation.GetFirstCard();
		else if(nIndex == 1 && m_Calculation.HasSecondCard())
			card = m_Calculation.GetSecondCard();
		
		if(card != null)
		{
			int nCardIndex = card.GetIndex();
			if(card.IsBasicCard())
			{
				int nPos = QueryCardByIndex(nCardIndex);
				if(0 <= nPos)
				{
					RestoreCardStatus(nPos);
					if(nIndex == 0)
						m_Calculation.RemoveFirstCard();
					else
						m_Calculation.RemoveSecondCard();

					CLayoutCursor cursor = CGameHighLight.GetCursor();
					cursor.Set(CLayoutCursor.TYPE_BASIC_CARD, nPos);
					CGameHighLight.SetHighLight(cursor);
					
				}
			}
			else
			{
				if(0 <= nCardIndex)
				{
					RestoreTempCardStatus(nCardIndex);	
					if(nIndex == 0)
						m_Calculation.RemoveFirstCard();
					else
						m_Calculation.RemoveSecondCard();

					CLayoutCursor cursor = CGameHighLight.GetCursor();
					cursor.Set(CLayoutCursor.TYPE_TEMP_CARD, TempCardDrawIndexFromArrayIndex(nCardIndex));
					CGameHighLight.SetHighLight(cursor);
				}
			}
		}
	}
	
	public void OnDraw(Canvas canvas)
	{
		OnBasicCardsDraw(canvas);
		OnTempCardsDraw(canvas);
	}
	
	private void OnBasicCardsDraw(Canvas canvas)
	{
		CLayoutCursor cursor = CGameHighLight.GetCursor();
		int nType = cursor.GetType();
		int nIndex = cursor.GetIndex();

		int i;
		Rect rect;
		for(i = 0; i < m_DealCards.size(); ++i)
		{
			if(nType == CLayoutCursor.TYPE_BASIC_CARD && nIndex == i && CGameState.InAnimation() == false)
			{
				rect = CDealLayout.GetBasicCardRect(i);	
                if(CGameState.GetSecondState() == CGameState.GAME_SUBSTATE_DND)
                {
                	CGameHelper.DrawPhantomCard(canvas, rect);
                }
                else
                {
                	CGameHelper.DrawHighligCursor(canvas, m_CursorPaint, cursor);
                	m_DealCards.elementAt(i).Draw(canvas, rect);
                }                	
                
			}
			else if(!IsCardSelected(i))
			{	
				rect = CDealLayout.GetBasicCardRect(i);	
				m_DealCards.elementAt(i).Draw(canvas, rect);
			}				
		}
	}
	
	private void OnTempCardsDraw(Canvas canvas)
	{
		CLayoutCursor cursor = CGameHighLight.GetCursor();
		int nType = cursor.GetType();
		int nIndex = cursor.GetIndex();

		int i, i2;
		Rect rect;
		for(i = 0; i < m_TempCards.size(); ++i)
		{
			i2 = TempCardDrawIndexFromArrayIndex(i);
			if(0 <= i2)
			{	
				rect = CDealLayout.GetTempCardRect(i2);
				if(nType == CLayoutCursor.TYPE_TEMP_CARD && nIndex == i2)
				{
					
	                if(CGameState.GetSecondState() == CGameState.GAME_SUBSTATE_DND)
	                {
	                	CGameHelper.DrawPhantomCard(canvas, rect);
	                }
					else
					{
		                CGameHelper.DrawHighligCursor(canvas, m_CursorPaint, cursor);
						m_TempCards.elementAt(i).Draw(canvas, rect); 
					}
				}
				else
				{	
					m_TempCards.elementAt(i).Draw(canvas, rect);
				}				
			}				
		}
	}
	
	public void ReloadResource()
	{
		int i;
		for(i = 0; i < m_DealCards.size(); ++i)
		{
			m_DealCards.elementAt(i).LoadImage();
        }                	

		for(i = 0; i < m_TempCards.size(); ++i)
		{
			m_TempCards.elementAt(i).LoadImage(); 
		}
		
		m_Calculation.ReloadResource();
		for(i = 0; i < m_CalculationSteps.size(); ++i)
		{
			m_CalculationSteps.elementAt(i).ReloadResource(); 
		}
	}
	
	public CDeal Clone()
	{
		CDeal deal = new CDeal();
		
		int i;
		
		for(i = 0; i < m_DealCards.size(); i++)
			deal.m_DealCards.add(m_DealCards.elementAt(i).Clone());
		
		for(i = 0; i < m_TempCards.size(); i++)
			deal.m_TempCards.add(m_TempCards.elementAt(i).Clone());
		
		for(i = 0; i < m_TempCards.size(); i++)
			deal.m_CalculationSteps.add(m_CalculationSteps.elementAt(i).Clone());
		
		deal.m_Calculation = m_Calculation.Clone();	
	    
	    deal.m_bDirty = m_bDirty;	

	    return deal;
	}

	public void CreateAnimationList(Vector<CCardAnimator> AnimatorList, boolean hasBack)
	{
		int i;
		
		Rect rect;
		Drawable image = null;
		int nValue;
		int nIndex;
		for(i = 0; i < m_DealCards.size(); i++)
		{	
			if(m_DealCards.elementAt(i).IsSelected() == true)
			{	
				if(hasBack == true)
				{	
					image = m_DealCards.elementAt(i).GetBackSideImage();
					nValue = m_DealCards.elementAt(i).GetIndex();
				}
				else
				{
					image = null;
					nValue = -1;
				}
			}				
			else
			{	
				image = m_DealCards.elementAt(i).GetCardImage();
				nValue = m_DealCards.elementAt(i).GetIndex();
			}				
			
			rect = CDealLayout.GetBasicCardRect(i);
			
			if(image != null)
			{	
				CCardAnimator animator = new CCardAnimator(image, rect, nValue, true, false, i);
				AnimatorList.add(animator);
			}				
		}			
		
		for(i = 0; i < m_TempCards.size(); i++)
		{	
			if(m_TempCards.elementAt(i).IsSelected() == false)
			{	
				image = m_TempCards.elementAt(i).GetCardImage();
				nIndex = TempCardDrawIndexFromArrayIndex(i);
				rect = CDealLayout.GetTempCardRect(nIndex);
				nValue = m_TempCards.elementAt(i).GetValue();
				CCardAnimator animator = new CCardAnimator(image, rect, nValue, false, false, nIndex);
				AnimatorList.add(animator);
			}				
		}
		
		boolean bBasic;
		CCard card = null;
		card = m_Calculation.GetFirstCard();
		if(card != null)
		{
			image = card.GetCardImage();
			rect = CDealLayout.GetOperand1Rect();
			bBasic = card.IsBasicCard();
			if(bBasic == true)
			{
				nValue = card.GetIndex();
			}
			else
			{
				nValue = card.GetValue();
			}
			CCardAnimator animator = new CCardAnimator(image, rect, nValue, bBasic, true, 0);
			AnimatorList.add(animator);
		}
		card = m_Calculation.GetSecondCard();
		if(card != null)
		{
			image = card.GetCardImage();
			rect = CDealLayout.GetOperand2Rect();
			bBasic = card.IsBasicCard();
			if(bBasic == true)
			{
				nValue = card.GetIndex();
			}
			else
			{
				nValue = card.GetValue();
			}
			CCardAnimator animator = new CCardAnimator(image, rect, nValue, bBasic, true, 1);
			AnimatorList.add(animator);
		}
		card = m_Calculation.GetResultCard();
		if(card != null)
		{
			image = card.GetCardImage();
			rect = CDealLayout.GetResultCardRect();
			nValue = card.GetValue();
			CCardAnimator animator = new CCardAnimator(image, rect, nValue, false, true, 2);
			AnimatorList.add(animator);
		}
	}
	
	public void SaveInstanceState(Bundle outState)
	{
		boolean bValid = (0 < m_TempCards.size() || 0 < m_DealCards.size());
		outState.putBoolean(KEY_DEAL, bValid);
		if(bValid == true)
		{
			String strKey = new String("");
			int nCount = m_DealCards.size();
			outState.putInt(KEY_DEAL_CARD, nCount);
			if(0 < nCount)
			{
				for(int i = 0; i < nCount; ++i)
				{
					Integer indexKey = new Integer(i);
					strKey = KEY_CARD_VALUE_PREFIX + indexKey.toString();
					outState.putInt(strKey, m_DealCards.elementAt(i).GetIndex());
					strKey = KEY_CARD_SELECT_PREFIX + indexKey.toString();
					outState.putBoolean(strKey, m_DealCards.elementAt(i).IsSelected());
				}
			}
			nCount = m_TempCards.size();
			outState.putInt(KEY_DEAL_TEMPCARD, nCount);
			if(0 < nCount)
			{
				for(int i = 0; i < nCount; ++i)
				{
					Integer indexKey = new Integer(i);
					strKey = KEY_TEMPCARD_VALUE_PREFIX + indexKey.toString();
					outState.putInt(strKey, m_TempCards.elementAt(i).GetValue());
					strKey = KEY_TEMPCARD_SELECT_PREFIX + indexKey.toString();
					outState.putBoolean(strKey, m_TempCards.elementAt(i).IsSelected());
				}
			}
			
			SavePendingCalculationState(outState);
			SaveCalculationStepsState(outState);
		}
	}
	
	public void SavePendingCalculationState(Bundle outState)
	{
		int nType = -1;
		int nValue;
		int nIndex;
		CCard card = null;
		if(m_Calculation.HasFirstCard())
		{
			card = m_Calculation.GetFirstCard();
			if(card != null)
			{
				if(card.IsBasicCard())
					nType = 0;
				else
					nType = 1;
			}
		}
		outState.putInt(KEY_PCAL_FIRST_CARD_TYPE, nType);
		if(nType == 1 || nType == 0)
		{
			if(card != null)
			{
				if(nType == 0) //Basic card, index is same as value for key storage
				{
					nValue = card.GetIndex();
					nIndex = card.GetIndex();
				}
				else //Temp card, value and index is different
				{
					nValue = card.GetValue();
					nIndex = card.GetIndex();
				}
				outState.putInt(KEY_PCAL_FIRST_CARD_VALUE, nValue);
				outState.putInt(KEY_PCAL_FIRST_CARD_INDEX, nIndex);
			}
		}
		
		nType = -1;
		card = null;
		if(m_Calculation.HasSecondCard())
		{
			card = m_Calculation.GetSecondCard();
			if(card != null)
			{
				if(card.IsBasicCard())
					nType = 0;
				else
					nType = 1;
			}
		}
		outState.putInt(KEY_PCAL_SECOND_CARD_TYPE, nType);
		if(nType == 1 || nType == 0)
		{
			if(card != null)
			{
				if(nType == 0) //Basic card, index is same as value for key storage
				{
					nValue = card.GetIndex();
					nIndex = card.GetIndex();
				}
				else //Temp card, value and index is different
				{
					nValue = card.GetValue();
					nIndex = card.GetIndex();
				}
				outState.putInt(KEY_PCAL_SECOND_CARD_VALUE, nValue);
				outState.putInt(KEY_PCAL_SECOND_CARD_INDEX, nIndex);
			}
		}
		
		nType = -1;
		card = null;
		if(m_Calculation.HasResult())
		{
			card = m_Calculation.GetResultCard();
			if(card != null)
			{
				nType = 1;
			}
		}
		outState.putInt(KEY_PCAL_RESULT_CARD_TYPE, nType);
		if(nType == 1)
		{
			if(card != null)
			{
				nValue = card.GetValue();
				nIndex = card.GetIndex();
				outState.putInt(KEY_PCAL_RESULT_CARD_VALUE, nValue);
				outState.putInt(KEY_PCAL_RESULT_CARD_INDEX, nIndex);
			}
		}

		nType = m_Calculation.GetOperation();
		outState.putInt(KEY_PCAL_OPERATOR_KEY, nType);
	}
	
	public void SaveCalculationStepsState(Bundle outState)
	{
		int nCount = m_CalculationSteps.size();
		outState.putInt(KEY_CALSTEP, nCount);
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CCalculation cal = m_CalculationSteps.elementAt(i);
				if(cal != null)
				{
					SaveCalStepState(outState, cal, i);
				}
			}
		}
	}
	
	public void SaveCalStepState(Bundle outState, CCalculation cal, int i)
	{
		Integer indexKey = new Integer(i);
		String strKey = new String("");
		
		int nType = -1;
		int nValue;
		int nIndex;
		CCard card = null;
		if(cal.HasFirstCard())
		{
			card = cal.GetFirstCard();
			if(card != null)
			{
				if(card.IsBasicCard())
					nType = 0;
				else
					nType = 1;
			}
		}
		strKey = KEY_CALSTEP_FIRST_CARD_TYPE + indexKey.toString();
		outState.putInt(strKey, nType);
		if(nType == 1 || nType == 0)
		{
			if(card != null)
			{
				if(nType == 0) //Basic card, index is same as value for key storage
				{
					nValue = card.GetIndex();
					nIndex = card.GetIndex();
				}
				else //Temp card, value and index is different
				{
					nValue = card.GetValue();
					nIndex = card.GetIndex();
				}
				strKey = KEY_CALSTEP_FIRST_CARD_VALUE + indexKey.toString();
				outState.putInt(strKey, nValue);
				strKey = KEY_CALSTEP_FIRST_CARD_INDEX + indexKey.toString();
				outState.putInt(strKey, nIndex);
			}
		}
		
		nType = -1;
		card = null;
		if(cal.HasSecondCard())
		{
			card = cal.GetSecondCard();
			if(card != null)
			{
				if(card.IsBasicCard())
					nType = 0;
				else
					nType = 1;
			}
		}
		strKey = KEY_CALSTEP_SECOND_CARD_TYPE + indexKey.toString();
		outState.putInt(strKey, nType);
		if(nType == 1 || nType == 0)
		{
			if(card != null)
			{
				if(nType == 0) //Basic card, index is same as value for key storage
				{
					nValue = card.GetIndex();
					nIndex = card.GetIndex();
				}
				else //Temp card, value and index is different
				{
					nValue = card.GetValue();
					nIndex = card.GetIndex();
				}
				strKey = KEY_CALSTEP_SECOND_CARD_VALUE + indexKey.toString();
				outState.putInt(strKey, nValue);
				strKey = KEY_CALSTEP_SECOND_CARD_INDEX + indexKey.toString();
				outState.putInt(strKey, nIndex);
			}
		}
		
		nType = -1;
		card = null;
		if(cal.HasResult())
		{
			card = cal.GetResultCard();
			if(card != null)
			{
				nType = 1;
			}
		}
		strKey = KEY_CALSTEP_RESULT_CARD_TYPE + indexKey.toString();
		outState.putInt(strKey, nType);
		if(nType == 1)
		{
			if(card != null)
			{
				nValue = card.GetValue();
				nIndex = card.GetIndex();
				strKey = KEY_CALSTEP_RESULT_CARD_VALUE + indexKey.toString();
				outState.putInt(strKey, nValue);
				strKey = KEY_CALSTEP_RESULT_CARD_INDEX + indexKey.toString();
				outState.putInt(strKey, nIndex);
			}
		}

		nType = cal.GetOperation();
		strKey = KEY_CALSTEP_OPERATOR_KEY + indexKey.toString();
		outState.putInt(strKey, nType);
	}
	
	public void LoadRestoreData(Bundle saveState)
	{
		boolean bValid = saveState.getBoolean(KEY_DEAL);
		if(bValid == true)
		{
			String strKey = new String("");
			int nCount = saveState.getInt(KEY_DEAL_CARD);
			int nValue;
			int nIndex;
			boolean bSelected;
			if(0 < nCount)
			{
				for(int i = 0; i < nCount; ++i)
				{
					Integer indexKey = new Integer(i);
					strKey = KEY_CARD_VALUE_PREFIX + indexKey.toString();
					nIndex = saveState.getInt(strKey);
					strKey = KEY_CARD_SELECT_PREFIX + indexKey.toString();
					bSelected = saveState.getBoolean(strKey);
					AddCard(nIndex);
					SetCardSelected(i, bSelected);
				}
			}
			nCount = saveState.getInt(KEY_DEAL_TEMPCARD);
			if(0 < nCount)
			{
				for(int i = 0; i < nCount; ++i)
				{
					Integer indexKey = new Integer(i);
					strKey = KEY_TEMPCARD_VALUE_PREFIX + indexKey.toString();
					nValue = saveState.getInt(strKey);
					strKey = KEY_TEMPCARD_SELECT_PREFIX + indexKey.toString();
					bSelected = saveState.getBoolean(strKey);
					AddTempCard(nValue);
					SetTempCardSelected(i, bSelected);
				}
			}
			
			LoadRestoreCalculation(saveState);
			LoadRestoreCalculationSteps(saveState);
		}
	}
	
	public void LoadRestoreCalculation(Bundle saveState)
	{
		int nType = -1;
		int nValue;
		int nIndex;
		m_Calculation.Reset();
		nType = saveState.getInt(KEY_PCAL_FIRST_CARD_TYPE);
		if(nType == 1 || nType == 0)
		{
			CCard card = null;
			nValue = saveState.getInt(KEY_PCAL_FIRST_CARD_VALUE);
			nIndex = saveState.getInt(KEY_PCAL_FIRST_CARD_INDEX);
			if(nType == 0) //Basic card, index is same as value for key storage
			{
				card = new CCard(nValue);
			}
			else //Temp card, value and index is different
			{
				card = new CCard(nValue, nIndex);
			}
			if(card != null)
			{
				m_Calculation.AddFirstCard(card);
			}
		}
		
		nType = -1;
		nType = saveState.getInt(KEY_PCAL_SECOND_CARD_TYPE);
		if(nType == 1 || nType == 0)
		{
			CCard card = null;
			nValue = saveState.getInt(KEY_PCAL_SECOND_CARD_VALUE);
			nIndex = saveState.getInt(KEY_PCAL_SECOND_CARD_INDEX);
			if(nType == 0) //Basic card, index is same as value for key storage
			{
				card = new CCard(nValue);
			}
			else //Temp card, value and index is different
			{
				card = new CCard(nValue, nIndex);
			}
			if(card != null)
			{
				m_Calculation.AddSecondCard(card);
			}
		}

		nType = saveState.getInt(KEY_PCAL_OPERATOR_KEY);
		if(0 < nType)
		{
			m_Calculation.SetOperator(nType);
		}
		
		nType = -1;
		nType = saveState.getInt(KEY_PCAL_RESULT_CARD_TYPE);
		if(nType == 1)
		{
			CCard card = null;
			nValue = saveState.getInt(KEY_PCAL_RESULT_CARD_VALUE);
			nIndex = saveState.getInt(KEY_PCAL_RESULT_CARD_INDEX);
			card = new CCard(nValue, nIndex);
			if(card != null)
			{
				m_Calculation.AddResultCard(card);
			}
		}
	}
	
	public void LoadRestoreCalculationSteps(Bundle saveState)
	{
		int nCount = saveState.getInt(KEY_CALSTEP);
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				LoadRestoreCalStepState(saveState, i);
			}
		}
	}
	
	public void LoadRestoreCalStepState(Bundle saveState, int i)
	{
		Integer indexKey = new Integer(i);
		String strKey = new String("");
		
		int nType = -1;
		int nValue;
		int nIndex;
		CCard card1 = null;
		CCard card2 = null;
		CCard card3 = null;
		int nOperator = 0;
		
		strKey = KEY_CALSTEP_FIRST_CARD_TYPE + indexKey.toString();
		nType = saveState.getInt(strKey);
		
		if(nType == 1 || nType == 0)
		{
			strKey = KEY_CALSTEP_FIRST_CARD_VALUE + indexKey.toString();
			nValue = saveState.getInt(strKey);
			strKey = KEY_CALSTEP_FIRST_CARD_INDEX + indexKey.toString();
			nIndex = saveState.getInt(strKey);
			if(nType == 0) //Basic card, index is same as value for key storage
			{
				card1 = new CCard(nValue);
			}
			else
			{
				card1 = new CCard(nValue, nIndex);
			}
		}
		
		nType = -1;
		strKey = KEY_CALSTEP_SECOND_CARD_TYPE + indexKey.toString();
		nType = saveState.getInt(strKey);
		if(nType == 1 || nType == 0)
		{
			strKey = KEY_CALSTEP_SECOND_CARD_VALUE + indexKey.toString();
			nValue = saveState.getInt(strKey);
			strKey = KEY_CALSTEP_SECOND_CARD_INDEX + indexKey.toString();
			nIndex = saveState.getInt(strKey);
			if(nType == 0) //Basic card, index is same as value for key storage
			{
				card2 = new CCard(nValue);
			}
			else
			{
				card2 = new CCard(nValue, nIndex);
			}
		}
		
		nType = -1;
		strKey = KEY_CALSTEP_RESULT_CARD_TYPE + indexKey.toString();
		nType = saveState.getInt(strKey);
		if(nType == 1)
		{
			strKey = KEY_CALSTEP_RESULT_CARD_VALUE + indexKey.toString();
			nValue = saveState.getInt(strKey);
			strKey = KEY_CALSTEP_RESULT_CARD_INDEX + indexKey.toString();
			nIndex = saveState.getInt(strKey);
			card3 = new CCard(nValue, nIndex);
		}

		strKey = KEY_CALSTEP_OPERATOR_KEY + indexKey.toString();
		nOperator = saveState.getInt(strKey);
		if(card1 != null && card2 != null && card3 != null && 0 < nOperator)
		{
			CCalculation cal = new CCalculation();
			cal.AddFirstCard(card1);
			cal.AddSecondCard(card2);
			cal.SetOperator(nOperator);
			cal.AddResultCard(card3);
			m_CalculationSteps.add(cal);
		}
	}	
}
