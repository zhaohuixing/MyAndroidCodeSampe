package com.e_gadget.MindFire;

import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class CAnswerRecord 
{
	public static final String KEY_ARSTEP = "ARSteps_Key_"; 
	
	public static final String KEY_ARSTEP_FIRST_CARD_TYPE = "ARSteps_Card1_Type_"; //0: basic, 1: temp, other: N/A 
	public static final String KEY_ARSTEP_FIRST_CARD_VALUE = "ARSteps_Card1_Value_"; // 
	public static final String KEY_ARSTEP_FIRST_CARD_INDEX = "ARSteps_Card1_Index_"; // 

	public static final String KEY_ARSTEP_SECOND_CARD_TYPE = "ARSteps_Card2_Type_"; //0: basic, 1: temp, other: N/A 
	public static final String KEY_ARSTEP_SECOND_CARD_VALUE = "ARSteps_Card2_Value_"; // 
	public static final String KEY_ARSTEP_SECOND_CARD_INDEX = "ARSteps_Card2_Index_"; // 

	public static final String KEY_ARSTEP_RESULT_CARD_TYPE = "ARSteps_Card3_Type_"; //0: basic, 1: temp, other: N/A 
	public static final String KEY_ARSTEP_RESULT_CARD_VALUE = "ARSteps_Card3_Value_"; // 
	public static final String KEY_ARSTEP_RESULT_CARD_INDEX = "ARSteps_Card3_Index_"; // 

	public static final String KEY_ARSTEP_OPERATOR_KEY = "ARSteps_Operator_Key_"; //0: none, 1: plus.....
	
	public Vector<CCalculation>		m_CalculationSteps;

	public CAnswerRecord()
	{
		m_CalculationSteps = new Vector<CCalculation>();
	}

	public void Reset()
	{
		m_CalculationSteps.clear();
	}

	public CAnswerRecord Clone()
	{
		CAnswerRecord newObj = new CAnswerRecord();
		int nCount;
		int i;

		nCount = this.m_CalculationSteps.size();
		for(i = 0; i < nCount; ++i)
			newObj.m_CalculationSteps.add(this.m_CalculationSteps.elementAt(i).Clone());
		
		return newObj;
	}
	
	public String toString()
	{
		String sRet = new String();

		int nCount;
		int i;
		
		nCount = m_CalculationSteps.size();
		Integer n = nCount;
		sRet += n.toString();
		sRet += " calculation steps\n";
		for(i = 0; i < nCount; ++i)
		{	
			sRet += m_CalculationSteps.elementAt(i).toString();
			sRet += "\n";
		}		
		
		return sRet;
	}
	
	public void ReloadResource()
	{
		int nCount;
		int i;

		nCount = m_CalculationSteps.size();
		for(i = 0; i < nCount; ++i)
			m_CalculationSteps.elementAt(i).ReloadResource();
	}
	
	
	public void ReloadImage()
	{
		int nCount = m_CalculationSteps.size();
		for(int i = 0; i < nCount; ++i)
			m_CalculationSteps.elementAt(i).ReloadResource();
	} 
	
	public void SaveAnswerState(Bundle outState, int i)
	{
		Integer indexKey = new Integer(i);
		String strKey = new String("");
		strKey = KEY_ARSTEP + indexKey.toString();
		int nCount = m_CalculationSteps.size();
		outState.putInt(strKey, nCount);
		if(0 < nCount)
		{
			for(int j = 0; j < nCount; ++j)
			{
				CCalculation cal = m_CalculationSteps.elementAt(j);
				if(cal != null)
				{
					SaveCalStepState(outState, cal, i, j);
				}
			}
		}
	}

	public void SaveCalStepState(Bundle outState, CCalculation cal, int rootindex, int subindex)
	{
		Integer rootindexKey = new Integer(rootindex);
		Integer subindexKey = new Integer(subindex);
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
		strKey = KEY_ARSTEP_FIRST_CARD_TYPE + rootindexKey.toString() + "_" + subindexKey.toString();
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
				strKey = KEY_ARSTEP_FIRST_CARD_VALUE + rootindexKey.toString() + "_" + subindexKey.toString();
				outState.putInt(strKey, nValue);
				strKey = KEY_ARSTEP_FIRST_CARD_INDEX + rootindexKey.toString() + "_" + subindexKey.toString();
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
		strKey = KEY_ARSTEP_SECOND_CARD_TYPE + rootindexKey.toString() + "_" + subindexKey.toString();
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
				strKey = KEY_ARSTEP_SECOND_CARD_VALUE + rootindexKey.toString() + "_" + subindexKey.toString();
				outState.putInt(strKey, nValue);
				strKey = KEY_ARSTEP_SECOND_CARD_INDEX + rootindexKey.toString() + "_" + subindexKey.toString();
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
		strKey = KEY_ARSTEP_RESULT_CARD_TYPE + rootindexKey.toString() + "_" + subindexKey.toString();
		outState.putInt(strKey, nType);
		if(nType == 1)
		{
			if(card != null)
			{
				nValue = card.GetValue();
				nIndex = card.GetIndex();
				strKey = KEY_ARSTEP_RESULT_CARD_VALUE + rootindexKey.toString() + "_" + subindexKey.toString();
				outState.putInt(strKey, nValue);
				strKey = KEY_ARSTEP_RESULT_CARD_INDEX + rootindexKey.toString() + "_" + subindexKey.toString();
				outState.putInt(strKey, nIndex);
			}
		}

		nType = cal.GetOperation();
		strKey = KEY_ARSTEP_OPERATOR_KEY + rootindexKey.toString() + "_" + subindexKey.toString();
		outState.putInt(strKey, nType);
	}
	
	public void LoadRetoreAnswer(Bundle saveState, int i)
	{
		Integer indexKey = new Integer(i);
		String strKey = new String("");
		strKey = KEY_ARSTEP + indexKey.toString();
		int nCount = saveState.getInt(strKey);
		if(0 < nCount)
		{
			for(int j = 0; j < nCount; ++j)
			{
				LoadRestoreCalStepState(saveState, i, j);
			}
		}
	}
	
	private void LoadRestoreCalStepState(Bundle saveState, int rootindex, int subindex)
	{
		Integer rootindexKey = new Integer(rootindex);
		Integer subindexKey = new Integer(subindex);
		String strKey = new String("");
		
		int nType = -1;
		int nValue;
		int nIndex;
		CCard card1 = null;
		CCard card2 = null;
		CCard card3 = null;
		int nOp = 0;
		
		strKey = KEY_ARSTEP_FIRST_CARD_TYPE + rootindexKey.toString() + "_" + subindexKey.toString();
		nType = saveState.getInt(strKey);
		if(nType == 1 || nType == 0)
		{
			strKey = KEY_ARSTEP_FIRST_CARD_VALUE + rootindexKey.toString() + "_" + subindexKey.toString();
			nValue = saveState.getInt(strKey);
			strKey = KEY_ARSTEP_FIRST_CARD_INDEX + rootindexKey.toString() + "_" + subindexKey.toString();
			nIndex = saveState.getInt(strKey);
			if(nType == 0) //Basic card, index is same as value for key storage
			{
				card1 = new CCard(nValue);
			}
			else //Temp card, value and index is different
			{
				card1 = new CCard(nValue, nIndex);
			}
		}
		
		nType = -1;
		strKey = KEY_ARSTEP_SECOND_CARD_TYPE + rootindexKey.toString() + "_" + subindexKey.toString();
		nType = saveState.getInt(strKey);
		if(nType == 1 || nType == 0)
		{
			strKey = KEY_ARSTEP_SECOND_CARD_VALUE + rootindexKey.toString() + "_" + subindexKey.toString();
			nValue = saveState.getInt(strKey);
			strKey = KEY_ARSTEP_SECOND_CARD_INDEX + rootindexKey.toString() + "_" + subindexKey.toString();
			nIndex = saveState.getInt(strKey);
			if(nType == 0) //Basic card, index is same as value for key storage
			{
				card2 = new CCard(nValue);
			}
			else //Temp card, value and index is different
			{
				card2 = new CCard(nValue, nIndex);
			}
		}
		
		nType = -1;
		strKey = KEY_ARSTEP_RESULT_CARD_TYPE + rootindexKey.toString() + "_" + subindexKey.toString();
		nType = saveState.getInt(strKey);
		if(nType == 1)
		{
			strKey = KEY_ARSTEP_RESULT_CARD_VALUE + rootindexKey.toString() + "_" + subindexKey.toString();
			nValue = saveState.getInt(strKey);
			strKey = KEY_ARSTEP_RESULT_CARD_INDEX + rootindexKey.toString() + "_" + subindexKey.toString();
			nIndex = saveState.getInt(strKey);
			card3 = new CCard(nValue, nIndex);
		}

		strKey = KEY_ARSTEP_OPERATOR_KEY + rootindexKey.toString() + "_" + subindexKey.toString();
		nOp = saveState.getInt(strKey);
		if(card1 != null && card2 != null && card3 != null && 0 < nOp)
		{
			CCalculation cal = new CCalculation();
			cal.AddFirstCard(card1);
			cal.AddSecondCard(card2);
			cal.SetOperator(nOp);
			cal.AddResultCard(card3);
			m_CalculationSteps.add(cal);
		}
	}
	
	public void DrawResult(Canvas canvas)
	{
		int nCount = m_CalculationSteps.size();
		
		int height = CDealLayout.m_nClinetY;

		int imgwidth = CDealLayout.GetCalculationWidth(); 
		int imgheight = CDealLayout.GetCalculationHeight();
	
		int startX = CResultLayout.m_ResultStartX; //(width-imgwidth)/2;
		int nstep = Math.abs((height/nCount-imgheight)/2); 
	
		int top;
		int bottom;
		int right = startX + imgwidth;
	
		for(int i = 0; i < nCount; ++i)
		{	
			top = CResultLayout.m_ResultStartY + (imgheight*4/5+nstep)*i;
			bottom = top + imgheight*4/5;
		
			Rect rect = new Rect(startX, top, right, bottom);
			m_CalculationSteps.elementAt(i).DrawResultCalculation(canvas, rect);
		}			
	}
}
