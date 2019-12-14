package com.e_gadget.MindFire;

import java.util.Vector;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class CGame 
{
	public static final String KEY_RIGHTANSWERS = "RightAnswers_Key";
	public CDeck							m_CurDeck;
	public CDeal    						m_CurDeal;
	public Vector<CAnswerRecord>			m_RightAnswers;
	private int 							m_AnswerListIterator;

    /** Paint to draw the cursor highlight on screen. */
    private Paint m_CursorPaint;

    /** Paint to draw the "e-gadget" on screen. */
    private Paint m_LogoPaint;
    
    private Paint m_PointPaint1;
    private Paint m_PointPaint2;
    private Paint m_PointPaint3;

    /** Paint to draw the result on screen. */
    private Paint m_ResultPaint;
    
    /* The object render card back side image */
    private Drawable 						m_CardBackSideImage;
    
    boolean									m_bInitialized;
    
    private CAnimationLayer 				m_AnimationLayer;
    
    private CBackgroundLayer				m_BkngLayer;
    
    private CGameGUILayer					m_GUILayer;					

    private Paint 							m_BkGndPaint;

    private boolean 						m_bCanResult;
    
    private Bundle 							m_RestoreStorage;
    private boolean							m_bRestoreStart;
    
    private CGameView						m_Parent;
	
    public CGame()
	{
		m_CurDeck = new CDeck();
		m_CurDeal = new CDeal();
		m_RightAnswers = new Vector<CAnswerRecord>();
		m_RightAnswers.clear();
		m_Parent = null;

	    // Initialize paint for cursor highlight
		m_CursorPaint = new Paint();
		m_CursorPaint.setAntiAlias(true);
		m_CursorPaint.setARGB(255, 255, 32, 32);
		
		m_BkGndPaint = new Paint();
		m_BkGndPaint.setAntiAlias(true);
		m_BkGndPaint.setARGB(255, 0, 128, 0);

	    m_LogoPaint = new Paint();
	    m_LogoPaint.setAntiAlias(true);
	    m_LogoPaint.setTextSize(32.0f);
	    m_LogoPaint.setARGB(127, 254, 127, 127);
	    
	    m_PointPaint1 = new Paint();
	    m_PointPaint1.setAntiAlias(true);
	    m_PointPaint1.setTextSize(36.0f);
	    m_PointPaint1.setARGB(255, 255, 255, 0);

	    m_PointPaint2 = new Paint();
	    m_PointPaint2.setAntiAlias(true);
	    m_PointPaint2.setTextSize(36.0f);
	    m_PointPaint2.setARGB(255, 255, 128, 64);

	    m_PointPaint3 = new Paint();
	    m_PointPaint3.setAntiAlias(true);
	    m_PointPaint3.setTextSize(36.0f);
	    m_PointPaint3.setARGB(255, 255, 0, 0);
	    
	    /** Paint to draw the result on screen. */
	    m_ResultPaint = new Paint();
	    m_ResultPaint.setAntiAlias(true);
	    m_ResultPaint.setTextSize(32.0f);
	    m_ResultPaint.setARGB(255, 254, 254, 254);
		
		m_bInitialized = false;
		
		m_AnimationLayer = new CAnimationLayer(); 
		m_AnswerListIterator = 0;

	    m_BkngLayer = new CBackgroundLayer();
	    m_GUILayer = new CGameGUILayer(); 
	    m_GUILayer.SetCursorPaint(m_CursorPaint);
	    
	    m_bCanResult = false;
	    m_RestoreStorage = null;
	    m_bRestoreStart = false;
	}
	
	public void SetSavedInstanceState(Bundle savedState)
	{
		m_RestoreStorage = savedState;
	}

	
	public void SetParent(CGameView	view)
	{
		m_Parent = view;
	}
	
	public void DoStart() 
	{
		if(m_bInitialized == false)
			InitalizeLayers();
		
		if(m_RestoreStorage == null)
		{
			DoStartWithoutRestore();
		}
		else
		{
			DoRestoreStart();
		}
	}
	
	private void DoStartWithoutRestore()
	{
		NewGame();
		CreateNewDealData();
		if(m_BkngLayer != null)
			m_BkngLayer.SetRunningStars(m_CurDeck.DealCount(), m_RightAnswers.size());
	    if(CGameType.IsSplashScreenEnable() == true)
	    {	
	    	InitializeSplashScreenLayer();
			CSplashScreen.StartSplashScreen();
	    }	    	
	}
	
	public void NewGame()
	{
		//m_nGameAnswer = CGameType.GetPoints();
		m_CurDeck.Shuffle();
		m_CurDeal.Reset();
		m_RightAnswers.clear();

		if(m_bInitialized == false)
			InitalizeLayers();
		
		if(m_AnimationLayer != null)
			m_AnimationLayer.Reset();
	    
		m_bCanResult = false;
	}

	public boolean StartDealResultAnimation()
	{
		boolean bRet = false;
		boolean hasBack = false;
		if(0 < m_CurDeck.Size())
			hasBack = true;
		m_AnimationLayer.Reset();
		
		m_CurDeal.CreateAnimationList(m_AnimationLayer.m_DealAnimation.m_AnimatorList, hasBack);
		if(m_CurDeal.GetRightAnswer(CGameType.GetPoints()) == true)
		{
			CLayoutCursor cursor = CGameHighLight.GetCursor();
			cursor.Set(CLayoutCursor.TYPE_BASIC_CARD, 0);
			CGameHighLight.SetHighLight(cursor);
			CAnswerRecord ans = m_CurDeal.CreateAnswerRecord();
			m_RightAnswers.add(ans);
			m_AnimationLayer.m_DealAnimation.SetSpriteState(true);
			bRet = true;
		}
		
		return bRet;
	}

	public void CreateNewDealData()
	{
		m_CurDeal.Reset();
		int i;
		int nIndex;
		for(i = 0; i < 4; ++i)
		{
			if(0 < m_CurDeck.Size())
			{	
				nIndex = m_CurDeck.PopupCard();
				if(-1 < nIndex)
					m_CurDeal.AddCard(nIndex);
			}					
		}
	}

	public void ResetNewDealLayer()
	{
		m_BkngLayer.SetRunningStars(m_CurDeck.DealCount(), m_RightAnswers.size());
		m_GUILayer.ResetOperatorButton();
		CLayoutCursor cursor = CGameHighLight.GetCursor();
		cursor.Set(CLayoutCursor.TYPE_BASIC_CARD, 0);
		CGameHighLight.SetHighLight(cursor);
	}
	
	
	public void NewDeal()
	{
		if(m_bInitialized == false)
			InitalizeLayers();

		boolean bRet = StartDealResultAnimation();
		CreateNewDealData();
		if(bRet == false)
		{
			ResetNewDealLayer();
		}
		else
		{
			m_BkngLayer.SetRunningStars(m_CurDeck.DealCount(), m_RightAnswers.size());
			m_GUILayer.ResetOperatorButton();
		}
		m_AnimationLayer.m_DealAnimation.SetController(this);
		m_AnimationLayer.StartDealAnimation();
	}

	public boolean IsGameEnd()
	{
		boolean bRet = false;
		bRet = (m_CurDeck.Size() == 0 && (m_CurDeal.IsDealDone() || !m_CurDeal.IsDirty()));
		return bRet;
	}
	
	public boolean IsDealGetRightAnswer()
	{
		boolean bRet = false;
		
		bRet = m_CurDeal.GetRightAnswer(CGameType.GetPoints()); 
		
		return bRet;
		
	}
	
	public void AddDealAnswerToList()
	{
		if(IsDealGetRightAnswer())
		{
			m_RightAnswers.add(m_CurDeal.CreateAnswerRecord());
		}
	}
	
	public void CopyCorrectAnswers(Vector<CAnswerRecord> AnswerList)
	{
		int nCount = m_RightAnswers.size();
		int i;
		for(i = 0; i < nCount; ++i)
		{
			AnswerList.add(m_RightAnswers.elementAt(i).Clone());
		}
	}
	
	public void InitalizeLayers()
	{
	    //The operator button
		//Card backside image
		m_CardBackSideImage = CGameHelper.GetCardBackSideImage();

		if(CGameHelper.CanLoadResource() && CDealLayout.IsInitialized())
		{
		    if(CGameType.IsSplashScreenEnable() == true)
		    {	
		    	CSplashScreen.AttachGame(this);
		    }	    	
			
			m_BkngLayer.Initialize();
			m_GUILayer.Initialize(); 
			m_AnimationLayer.Initialize();	
			m_AnimationLayer.SetController(this);
			m_bInitialized = true;
		}			
		
	}
	
	private void DrawCardBackSide(Canvas canvas)
	{
		if(0 < m_CurDeck.Size() && m_CardBackSideImage != null && IsGameEnd() == false)
		{
			int i;
			Rect rect;
			for(i = 0; i < 4; ++i)
			{
				rect = CDealLayout.GetBasicCardRect(i);
				m_CardBackSideImage.setBounds(rect);
				m_CardBackSideImage.draw(canvas);
			}
		}
	}
	
	private void OnNonAnimationDraw(Canvas canvas)
	{
		OnCalculationDraw(canvas, false);
	}
		
	private void OnCalculationDraw(Canvas canvas, boolean bAnimation)
	{
		OnOperand1Draw(canvas, bAnimation);
		OnOperand2Draw(canvas, bAnimation);
		OnResultCardDraw(canvas, bAnimation);
	}

	private void OnOperand1Draw(Canvas canvas, boolean bAnimation)
	{
			CCard card = m_CurDeal.m_Calculation.GetFirstCard();
			Rect rect = CDealLayout.GetOperand1Rect();
			if(card == null)
			{
				rect.offset(-1, 0);
				CGameHelper.DrawPhantomCard(canvas, rect);
			}
			else
			{
				CLayoutCursor cursor = CGameHighLight.GetCursor();
				int nType = cursor.GetType();
				int nIndex = cursor.GetIndex();
				rect = CDealLayout.GetOperand1Rect();
				if(nType == CLayoutCursor.TYPE_OPERAND_CARD && nIndex == 0 && bAnimation == false)
				{
	                if(CGameState.GetSecondState() == CGameState.GAME_SUBSTATE_DND)
	                {
	                	CGameHelper.DrawPhantomCard(canvas, rect);
	                }
	                else
	                {	
	                	CGameHelper.DrawHighligCursor(canvas, m_CursorPaint, cursor);
	                	card.Draw(canvas, rect);
	                }	                	
				}
				else
				{	
					card.Draw(canvas, rect);
				}					
			}
		}
		
	private void OnOperand2Draw(Canvas canvas, boolean bAnimation)
	{
		
			CCard card = m_CurDeal.m_Calculation.GetSecondCard();
			Rect rect = CDealLayout.GetOperand2Rect();
			if(card == null)
			{
				CGameHelper.DrawPhantomCard(canvas, rect);
			}
			else
			{
				CLayoutCursor cursor = CGameHighLight.GetCursor();
				int nType = cursor.GetType();
				int nIndex = cursor.GetIndex();
				rect = CDealLayout.GetOperand2Rect();
				if(nType == CLayoutCursor.TYPE_OPERAND_CARD && nIndex == 1 && bAnimation == false)
				{
	                if(CGameState.GetSecondState() == CGameState.GAME_SUBSTATE_DND)
	                {
	                	CGameHelper.DrawPhantomCard(canvas, rect);
	                }
	                else
	                {	
	                	CGameHelper.DrawHighligCursor(canvas, m_CursorPaint, cursor);
	                	card.Draw(canvas, rect);
	                }	                	
				}
				else
				{	
					card.Draw(canvas, rect);
				}					
			}
		}
		
	
	private void OnResultCardDraw(Canvas canvas, boolean bAnimation)
	{
		CCard card = m_CurDeal.m_Calculation.GetResultCard();
		Rect rect = CDealLayout.GetResultCardRect();
		if(card == null)
		{
			rect.offset(-1, 0);
			CGameHelper.DrawPhantomCard(canvas, rect);
		}
		else
		{
			CLayoutCursor cursor = CGameHighLight.GetCursor();
			int nType = cursor.GetType();
			rect = CDealLayout.GetResultCardRect();
			if(nType == CLayoutCursor.TYPE_RESULT_CARD && bAnimation == false)
			{
                if(CGameState.GetSecondState() == CGameState.GAME_SUBSTATE_DND)
                {
                	CGameHelper.DrawPhantomCard(canvas, rect);
                }
                else
                {	
                	CGameHelper.DrawHighligCursor(canvas, m_CursorPaint, cursor);
                	card.Draw(canvas, rect);
                }	                	
			}
			else
			{	
				card.Draw(canvas, rect);
			}					
		}
	}
		
	
	public void MoveCursorNext()
	{
		CLayoutCursor cursor = CGameHighLight.GetCursor();
		if(cursor.GetType() == CLayoutCursor.TYPE_TEMP_CARD)
		{
			cursor.Set(cursor.GetType(), m_CurDeal.TempCardArrayIndexFromDrawIndex(cursor.GetIndex()));
		}
		CLayoutCursor newcur = CDealLayout.NextCursor(cursor);
		boolean bValid = m_CurDeal.CursorTest(newcur);
		
		while(bValid == false)
		{
			newcur = CDealLayout.NextCursor(newcur);
			bValid = m_CurDeal.CursorTest(newcur);
		}

		if(newcur.GetType() == CLayoutCursor.TYPE_TEMP_CARD)
		{
			newcur.Set(newcur.GetType(), m_CurDeal.TempCardDrawIndexFromArrayIndex(newcur.GetIndex()));
		}
		
		CGameHighLight.SetHighLight(newcur);
	}

	public void MoveCursorPrev()
	{
		CLayoutCursor cursor = CGameHighLight.GetCursor();
		if(cursor.GetType() == CLayoutCursor.TYPE_TEMP_CARD)
		{
			cursor.Set(cursor.GetType(), m_CurDeal.TempCardArrayIndexFromDrawIndex(cursor.GetIndex()));
		}
		CLayoutCursor newcur = CDealLayout.PrevCursor(cursor);
		boolean bValid = m_CurDeal.CursorTest(newcur);
		
		while(bValid == false)
		{
			newcur = CDealLayout.PrevCursor(newcur);
			bValid = m_CurDeal.CursorTest(newcur);
		}

		if(newcur.GetType() == CLayoutCursor.TYPE_TEMP_CARD)
		{
			newcur.Set(newcur.GetType(), m_CurDeal.TempCardDrawIndexFromArrayIndex(newcur.GetIndex()));
		}
		
		CGameHighLight.SetHighLight(newcur);
		if(newcur.GetType() == CLayoutCursor.TYPE_SIGNSBAR_BUTTON)
		{
			m_GUILayer.SetSignBarActiveButton(newcur.GetIndex());
		}
	}
	
	public boolean onKeyUpEvent(int keyCode)
	{
		boolean bRet = false;
		if(CGameState.InMovement())
			return true;

		if(IsGameEnd() == true && CGameState.IsGameRunning())
		{
			if(m_bCanResult == false)
			{	
				NewDeal();
				m_bCanResult = true;
			}	
			else
			{	
				GoToResultState();
			}	
			return true;
		}
		
		switch(keyCode)
		{
			case KeyEvent.KEYCODE_DPAD_CENTER:
				DoAction();
				bRet = true;
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				MoveCursorNext();
				bRet = true;
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				MoveCursorNext();
				bRet = true;
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				MoveCursorPrev();
				bRet = true;
				break;
			case KeyEvent.KEYCODE_DPAD_UP:
				MoveCursorPrev();
				bRet = true;
				break;
		}
		
		return bRet;
	}
	
	public boolean onKeyDownEvent(int keyCode)
	{
		boolean bRet = true;
		if(CGameState.InMovement())
			return bRet;
		
		switch(keyCode)
		{
			case KeyEvent.KEYCODE_DPAD_CENTER:
				DoKeyPress();
				bRet = true;
				break;
		}
		
		return bRet;
	}
	
	public void DoAction()
	{
		if(CGameState.InMovement())
		{	
			return;
		}			
		else
		{
			DoKeyRelease();
		}			
	}

	private void DoKeyPress()
	{
		m_GUILayer.DoKeyPress();
	}		

	private void DoKeyRelease()
	{
		CLayoutCursor cursor = CGameHighLight.GetCursor();
		int nType = cursor.GetType();

		switch(nType)
		{
			case CLayoutCursor.TYPE_BASIC_CARD:
				HandleBasicCardEvent(cursor);
				break;
			case CLayoutCursor.TYPE_TEMP_CARD:
				HandleTempCardEvent(cursor);
				break;
			case CLayoutCursor.TYPE_OPERAND_CARD:
				HandleOperandCardEvent(cursor);
				break;
			case CLayoutCursor.TYPE_OPERATOR_BUTTON:
				HandleOperatorButtonUpEvent(cursor);
				break;
			case CLayoutCursor.TYPE_SIGNSBAR_BUTTON:
				HandleSignBarUpEvent(cursor);
				break;
			case CLayoutCursor.TYPE_CALCULATE_BUTTON:
				HandleAssignButtonUpEvent(cursor);
				break;
			case CLayoutCursor.TYPE_RESULT_CARD:
				HandleResultCardEvent(cursor);
				break;
			case CLayoutCursor.TYPE_CANCEL_BUTTON:
				HandleCancelButtonUpEvent(cursor);
				break;
			case CLayoutCursor.TYPE_NEWDEAL_BUTTON:
				HandleDealButtonUpEvent(cursor);
				break;
		}
	}

	private void HandleBasicCardEvent(CLayoutCursor cursor)
	{
		int nIndex = cursor.GetIndex();
		if(0 <= nIndex && nIndex < m_CurDeal.GetCardCount())
		{
			if(m_CurDeal.m_Calculation.HasFirstCard() == false)
			{
				m_CurDeal.SetCardAsOperand1(nIndex);
			}
			else if(m_CurDeal.m_Calculation.HasSecondCard() == false)
			{
				m_CurDeal.SetCardAsOperand2(nIndex);
			}
		}					
	}
	
	private void HandleTempCardEvent(CLayoutCursor cursor)
	{
		int nIndex = cursor.GetIndex();
		if(0 <= nIndex)
		{
			int n = m_CurDeal.TempCardArrayIndexFromDrawIndex(nIndex);
			
			if(0 <=  n && n < m_CurDeal.GetTempCardCount())
			if(m_CurDeal.m_Calculation.HasFirstCard() == false)
			{
				m_CurDeal.SetTempCardAsOperand1(n);
			}
			else if(m_CurDeal.m_Calculation.HasSecondCard() == false)
			{
				m_CurDeal.SetTempCardAsOperand2(n);
			}
		}					
	}
	
	private void HandleOperandCardEvent(CLayoutCursor cursor)
	{
		int nIndex = cursor.GetIndex();
		m_CurDeal.RestoreOperandBackToOrginal(nIndex);
	}
	
	private void HandleOperatorButtonUpEvent(CLayoutCursor cursor)
	{
		m_GUILayer.OnOperatorButtonUp();
		m_CurDeal.m_Calculation.SetOperator(m_GUILayer.GetActiveOperation());
	}

	private void HandleSignBarUpEvent(CLayoutCursor cursor)
	{
		m_GUILayer.OnSignBarUp(cursor);
		m_CurDeal.m_Calculation.SetOperator(m_GUILayer.GetActiveOperation());
	}
	
	private void HandleAssignButtonUpEvent(CLayoutCursor cursor)
	{
		m_GUILayer.OnAssignButtonUp();
		if(m_CurDeal.CanCalculate())
		{
			if(m_CurDeal.Calculate())
			{	
				cursor.Set(CLayoutCursor.TYPE_RESULT_CARD, 0);
				CGameHighLight.SetHighLight(cursor);
			}	
		}
	}
	
	private void HandleResultCardEvent(CLayoutCursor cursor)
	{
		CCard card = m_CurDeal.m_Calculation.GetResultCard();
		if(card != null)
		{	
			int n = m_CurDeal.AddTempCard(card);
			if(-1 < n)
			{	
				m_CurDeal.CacheAndResetCalculation();
				m_GUILayer.ResetOperatorButton();
				cursor.Set(CLayoutCursor.TYPE_TEMP_CARD,m_CurDeal.TempCardDrawIndexFromArrayIndex(n));
				CGameHighLight.SetHighLight(cursor);
				if(IsDealGetRightAnswer())
				{
					NewDeal();
					if(IsGameEnd() == true)
					{
						if(m_bCanResult == false)
							m_bCanResult = true;
					}
				}
			}						
		}
	}

	private void HandleCalculationResult()
	{
		CLayoutCursor cursor = CGameHighLight.GetCursor();
		CCard card = m_CurDeal.m_Calculation.GetResultCard();
		if(card != null)
		{	
			int n = m_CurDeal.AddTempCard(card);
			if(-1 < n)
			{	
				m_CurDeal.CacheAndResetCalculation();
				m_GUILayer.ResetOperatorButton();
				cursor.Set(CLayoutCursor.TYPE_TEMP_CARD,m_CurDeal.TempCardDrawIndexFromArrayIndex(n));
				CGameHighLight.SetHighLight(cursor);
				if(IsDealGetRightAnswer())
				{
					NewDeal();
					if(IsGameEnd() == true)
					{
						if(m_bCanResult == false)
							m_bCanResult = true;
					}
				}
			}						
		}
	}

	private void CancelDealOperation()
	{
		m_CurDeal.Restore();
		m_GUILayer.ResetOperatorButton();
	}
	
	private void HandleCancelButtonUpEvent(CLayoutCursor cursor)
	{
		CancelDealOperation();
	}
	
	private void HandleDealButtonUpEvent(CLayoutCursor cursor)
	{
		if(IsGameEnd() == false)
		{
			NewDeal();
			if(IsGameEnd() == true)
			{
				if(m_bCanResult == false)
				{	
					m_bCanResult = true;
				}	
			}
		}
		else
		{
			if(m_bCanResult == false)
			{	
				NewDeal();
				m_bCanResult = true;
			}	
			else
			{	
				GoToResultState();
			}	
		}
	}
	
	public void onTouchEvent(MotionEvent event)
	{
		if(CGameState.InAnimation())
			return;
		else
		{
			if(CGameState.IsGameRunning())
				onDragAndDrop(event);
			else if(CGameState.IsGameResult())
				onResultStateClick(event);
		}			
	}
	
	public boolean IsBasicCardCursor(CLayoutCursor cursor)
	{
		boolean bRet = false;
		
		int nType = cursor.GetType();
		int nIndex = cursor.GetIndex();
		CCard card = null;

		switch(nType)
		{
			case CLayoutCursor.TYPE_BASIC_CARD:
				bRet = true;
				break;
			case CLayoutCursor.TYPE_OPERAND_CARD:
				card = m_CurDeal.GetOperandCard(nIndex);
				if(card != null)
				{
					bRet = card.IsBasicCard();
				}
				break;
		}
		
		return bRet;
	}

	public int GetBasicCardCursorIndex(CLayoutCursor cursor)
	{
		int nRet = -1;
		
		int nType = cursor.GetType();
		int nIndex = cursor.GetIndex();
		CCard card = null;

		switch(nType)
		{
			case CLayoutCursor.TYPE_BASIC_CARD:
				nRet = nIndex;
				break;
			case CLayoutCursor.TYPE_OPERAND_CARD:
				card = m_CurDeal.GetOperandCard(nIndex);
				if(card != null)
				{
					if(card.IsBasicCard())
					{
						nRet = m_CurDeal.QueryCardByIndex(card.GetIndex()); 	
					}
				}
				break;
		}
		
		return nRet;
	}
	
	public boolean IsTempCardCursor(CLayoutCursor cursor)
	{
		boolean bRet = false;
		
		int nType = cursor.GetType();
		int nIndex = cursor.GetIndex();
		CCard card = null;

		switch(nType)
		{
			case CLayoutCursor.TYPE_TEMP_CARD:
				bRet = true;
				break;
			case CLayoutCursor.TYPE_OPERAND_CARD:
				card = m_CurDeal.GetOperandCard(nIndex);
				if(card != null)
				{
					bRet = card.IsTemperaryCard();
				}
				break;
		}
		
		return bRet;
	}

	public int GetTempCardCursorIndex(CLayoutCursor cursor)
	{
		int nRet = -1;
		
		int nType = cursor.GetType();
		int nIndex = cursor.GetIndex();
		CCard card = null;

		switch(nType)
		{
			case CLayoutCursor.TYPE_TEMP_CARD:
				nRet = nIndex;
				break;
			case CLayoutCursor.TYPE_OPERAND_CARD:
				card = m_CurDeal.GetOperandCard(nIndex);
				if(card != null)
				{
					if(card.IsTemperaryCard())
					{
						nRet = m_CurDeal.TempCardDrawIndexFromArrayIndex(card.GetIndex()); 	
					}
				}
				break;
		}
		
		return nRet;
	}
	
	
	public Drawable GetCursorImage(CLayoutCursor cursor)
	{
		Drawable image = null;
		int nType = cursor.GetType();
		int nIndex = cursor.GetIndex();
		int nPos;
		CCard card = null;

		switch(nType)
		{
			case CLayoutCursor.TYPE_BASIC_CARD:
				card = m_CurDeal.GetCard(nIndex);
				if(card != null)
				{
					image = card.GetCardImage();
				}
				break;
			case CLayoutCursor.TYPE_TEMP_CARD:
				nPos = m_CurDeal.TempCardArrayIndexFromDrawIndex(nIndex);
				card = m_CurDeal.GetTempCard(nPos);
				if(card != null)
				{
					image = card.GetCardImage();
				}
				break;
			case CLayoutCursor.TYPE_OPERAND_CARD:
				card = m_CurDeal.GetOperandCard(nIndex);
				if(card != null)
				{
					image = card.GetCardImage();
				}
				break;
			case CLayoutCursor.TYPE_RESULT_CARD:
				card = m_CurDeal.GetResultCard();
				if(card != null)
				{
					image = card.GetCardImage();
				}
				break;
			case CLayoutCursor.TYPE_OPERATOR_BUTTON:
			case CLayoutCursor.TYPE_SIGNSBAR_BUTTON:
			case CLayoutCursor.TYPE_CALCULATE_BUTTON:
			case CLayoutCursor.TYPE_CANCEL_BUTTON:
			case CLayoutCursor.TYPE_NEWDEAL_BUTTON:
				image = m_GUILayer.GetCursorImage(cursor);
				break;
		}
		return image;
	}
	
	public void onDragAndDrop(MotionEvent event)
	{
        float x = event.getX();
        float y = event.getY();
        
        switch (event.getAction()) 
        {
            case MotionEvent.ACTION_DOWN:
                DnDStart(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
            	if(event.getEdgeFlags() == 0)
            	{	
            		DnDMove(x, y);
            	}
            	else
            	{	 
            		DnDStop(x, y);
            	}            		
                break;
            case MotionEvent.ACTION_UP:
        		DnDStop(x, y);
                break;
            case MotionEvent.ACTION_CANCEL:
                DnDCancel();
                break;
        }
	}
	
	public void DnDStart(float x, float y)
	{
		CLayoutCursor cursor = m_CurDeal.HitTest((int)x, (int)y);
		if(cursor.IsValid()/* && cursor.CanDragAndDrop()*/)
		{
			CGameHighLight.SetHighLight(cursor);
			CLayoutCursor cur2 = null;
			Drawable  image1 = GetCursorImage(cursor);
			Drawable  image2 = null;
			boolean bBasic = IsBasicCardCursor(cursor);
			int index = -1;
			if(bBasic)
				index = GetBasicCardCursorIndex(cursor);
			else
				index = GetTempCardCursorIndex(cursor);
			
			CGameSprite sprite = new CGameSprite(CGameSprite.SPRITE_TYPE_DND, cursor, cur2, image1, image2, bBasic, index);
			m_AnimationLayer.StartDragAndDrop(sprite);
			m_GUILayer.OnKeyDown(cursor);
		}
	}
	public void DnDMove(float x, float y)
	{
		if(CGameState.InDragAndDrop())
		{
			m_AnimationLayer.DragAndDropOver((int)x, (int)y);
		}
	}
	
	public void DnDStop(float x, float y)
	{
		if(CGameState.InDragAndDrop())
		{
			CLayoutCursor cursor = CGameHighLight.GetCursor();
			if(CGameState.GetSecondState() == CGameState.GAME_SUBSTATE_DNDREADY)
			{
				CGameState.StopDragAndDrop();
				m_AnimationLayer.Reset();
				CLayoutCursor hitcur = m_CurDeal.HitTest((int)x, (int)y);
				if(cursor.IsSame(hitcur))
				{	
					DoKeyRelease();
				}
				else
				{
					if(hitcur.IsValid())
						CGameHighLight.SetHighLight(hitcur);
				}
			}
			else
			{	
				CGameState.StopDragAndDrop();
				m_AnimationLayer.Reset();
				int nType = cursor.GetType();
				switch(nType)
				{
					case CLayoutCursor.TYPE_BASIC_CARD:
						HandleBasicCardDnD(cursor, (int)x, (int)y);
						break;
					case CLayoutCursor.TYPE_TEMP_CARD:
						HandleTempCardDnD(cursor, (int)x, (int)y);
						break;
					case CLayoutCursor.TYPE_OPERAND_CARD:
						HandleOperandCardDnD(cursor, (int)x, (int)y);
						break;
					case CLayoutCursor.TYPE_RESULT_CARD:
						HandleResultCardDnD(cursor, (int)x, (int)y);
						break;
					case CLayoutCursor.TYPE_SIGNSBAR_BUTTON:
						HandleSignsBarDnD(cursor, (int)x, (int)y);
						break;
					case CLayoutCursor.TYPE_OPERATOR_BUTTON:
						HandleOperatorButtonDnD(cursor, (int)x, (int)y);
						break;
					case CLayoutCursor.TYPE_CALCULATE_BUTTON:
						HandleAssignButtonDnD(cursor, (int)x, (int)y);
						break;
					case CLayoutCursor.TYPE_CANCEL_BUTTON:
						HandleCancelButtonDnD(cursor, (int)x, (int)y);
						break;
					case CLayoutCursor.TYPE_NEWDEAL_BUTTON:
						HandleDealButtonDnD(cursor, (int)x, (int)y);
						break;
				}
			}				
		}
	}
	public void DnDCancel()
	{
		if(CGameState.InDragAndDrop())
		{
			CGameState.StopDragAndDrop();
			m_AnimationLayer.Reset();
		}
	}

	private void HandleBasicCardDnD(CLayoutCursor cursor, int x, int y)
	{
		int nIndex = cursor.GetIndex();
		
		int nTemp = CDealLayout.HitBasicCard(x, y);
		if(nTemp == nIndex)
		{
			HandleBasicCardEvent(cursor);
		}
		else
		{
			nTemp = CDealLayout.HitOperand(x, y);
			if(nTemp == 0)
			{
				if(m_CurDeal.m_Calculation.HasFirstCard())
					m_CurDeal.RestoreOperandBackToOrginal(0);
				
				m_CurDeal.SetCardAsOperand1(nIndex);
			}
			else if(nTemp == 1)
			{
				if(m_CurDeal.m_Calculation.HasSecondCard())
					m_CurDeal.RestoreOperandBackToOrginal(1);
				
				m_CurDeal.SetCardAsOperand2(nIndex);
			}
		}
	}
	
	private void HandleTempCardDnD(CLayoutCursor cursor, int x, int y)
	{
		int nIndex = cursor.GetIndex();
		int nTemp = CDealLayout.HitTempCard(x, y, m_CurDeal.GetTempCardCount());
		if(nTemp == nIndex)
		{
			HandleTempCardEvent(cursor);
		}
		else
		{
			nTemp = CDealLayout.HitOperand(x, y);
			int index = m_CurDeal.TempCardArrayIndexFromDrawIndex(nIndex);
			if(nTemp == 0)
			{
				if(m_CurDeal.m_Calculation.HasFirstCard())
					m_CurDeal.RestoreOperandBackToOrginal(0);
				
				m_CurDeal.SetTempCardAsOperand1(index);
			}
			else if(nTemp == 1)
			{
				if(m_CurDeal.m_Calculation.HasSecondCard())
					m_CurDeal.RestoreOperandBackToOrginal(1);
				
				m_CurDeal.SetTempCardAsOperand2(index);
			}
		}
	}
	
	private void HandleOperandCardDnD(CLayoutCursor cursor, int x, int y)
	{
		int nIndex = cursor.GetIndex();
		CCard card = m_CurDeal.GetOperandCard(nIndex);
		if(card != null)
		{
			boolean bHit = false;
			if(card.IsBasicCard())
			{
				bHit = CDealLayout.InBasicCardRow(x, y);
			}
			else
			{
				bHit = CDealLayout.InTempCardRow(x, y);
			}
			
			if(bHit == true)
			{
				m_CurDeal.RestoreOperandBackToOrginal(nIndex);
			}
			else 
			{
				int hittest = CDealLayout.HitOperand(x, y);
				if(-1 < hittest && hittest != nIndex)
				{
					CCard card2 = m_CurDeal.GetOperandCard(hittest);
					m_CurDeal.SetOperandCard(nIndex, card2);
					m_CurDeal.SetOperandCard(hittest, card);
				}
			}
		}
	}

	private void HandleSignsBarDnD(CLayoutCursor cursor, int x, int y)
	{
		int nIndex = cursor.GetIndex();
		m_GUILayer.OnSignBarUp(cursor);
		Log.w(this.getClass().getName(), "HandleSignsBarDnD Call m_GUILayer.OnSignBarUp");
		if(0 <= nIndex && nIndex < 4)
		{
			//HandleOperatorButtonUpEvent(cursor);
			cursor.Set(CLayoutCursor.TYPE_OPERATOR_BUTTON, 0);
			CGameHighLight.SetHighLight(cursor);
			m_GUILayer.SetActiveOperation(nIndex+1);
			Log.w(this.getClass().getName(), "HandleSignsBarDnD Call SetActiveOperation");
			m_CurDeal.m_Calculation.SetOperator(m_GUILayer.GetActiveOperation());
			Log.w(this.getClass().getName(), "HandleSignsBarDnD Call m_CurDeal.m_Calculation.SetOperator");
		}
	}
	
	private void HandleResultCardDnD(CLayoutCursor cursor, int x, int y)
	{
		if(CDealLayout.InTempCardRow(x, y))
		{
			HandleResultCardEvent(cursor);
		}
		else
		{
			int index = CDealLayout.HitOperand(x, y);
			if(index == 0 || index == 1)
			{
				if(m_CurDeal.IsLastCalculation())
				{
					HandleResultCardEvent(cursor);
				}
				else
				{
					CCard card = m_CurDeal.m_Calculation.GetResultCard();
					if(card != null)
					{	
						int n = m_CurDeal.AddTempCard(card);
						if(-1 < n)
						{	
							m_CurDeal.CacheAndResetCalculation();
							m_GUILayer.ResetOperatorButton();
							if(index == 0)
								m_CurDeal.SetTempCardAsOperand1(n);
							else
								m_CurDeal.SetTempCardAsOperand2(n);
						}						
					}
				}
			}
		}
	}
	
	private void HandleOperatorButtonDnD(CLayoutCursor cursor, int x, int y)
	{
		if(CDealLayout.HitOperator(x, y))
		{
			HandleOperatorButtonUpEvent(cursor);
		}
	}
	

	private void HandleAssignButtonDnD(CLayoutCursor cursor, int x, int y)
	{
		if(CDealLayout.HitAsignButton(x, y))
		{
			HandleAssignButtonUpEvent(cursor);
		}
	}
	

	private void HandleCancelButtonDnD(CLayoutCursor cursor, int x, int y)
	{
		if(CDealLayout.HitCancelButton(x, y))
		{
			HandleCancelButtonUpEvent(cursor);
		}
	}
	

	private void HandleDealButtonDnD(CLayoutCursor cursor, int x, int y)
	{
		if(CDealLayout.HitDealButton(x, y))
		{
			HandleDealButtonUpEvent(cursor);
		}
	}
	
	public void ReloadResource()
	{
		m_CurDeal.ReloadResource();
		
		for(int i = 0; i < m_RightAnswers.size(); ++i)
		{
			m_RightAnswers.elementAt(i).ReloadResource();
		}

	    m_CardBackSideImage = CGameHelper.GetCardBackSideImage();
	    
	    m_BkngLayer.ReloadResource();
	    m_GUILayer.ReloadResource();
	}

	public void GoToResultState()
	{
		if(HaveRightAnswers() == true)
		{	
			CGameState.SetGameResult();
			m_AnswerListIterator = 0;
			Rect rect = CResultLayout.GetNextRect();
			m_GUILayer.m_DealBtn.SetBoundary(rect);
		}
		else
		{
			GetNextNewGame();
		}
	}
	
	public void GoToIdle()
	{
		
	}
	
	public void GetNextNewGame()
	{
		Drawable snapshot = GetDesktopSnapshoot();
		CSplashScreen.SetSnapshot(snapshot);

		NewGame();
		CreateNewDealData();
		if(m_BkngLayer != null)
			m_BkngLayer.SetRunningStars(m_CurDeck.DealCount(), m_RightAnswers.size());
		CSplashScreen.StartSplashScreen();

		Drawable gameimage = CreateStaticGameImage();
		CSplashScreen.SetImage(gameimage);
		CSplashScreen.AttachGame(this);
		CGameTouchLayer.Initialize();
		CGameState.SetGameSplashScreen(); 
		CSplashScreen.StartSplashScreen();
	}

	public boolean HaveRightAnswers()
	{
		boolean bRet = false;
		if(0 < m_RightAnswers.size())
			bRet = true;
		
		return bRet;
	}
	
	public void GoToNextAnswerResult()
	{
		m_AnswerListIterator++;
		if(m_RightAnswers.size() <= m_AnswerListIterator)
		{
			HandleScoreSaving();
			GetNextNewGame();
		}
	}

	private void onResultStateClick(MotionEvent event)
	{
        float x = event.getX();
        float y = event.getY();
        
        switch (event.getAction()) 
        {
            case MotionEvent.ACTION_UP:
            	if(CResultLayout.IsHitNextButton((int)x, (int)y))
            	{
            		m_GUILayer.OnResultNextButtonUp();
            		GoToNextAnswerResult();
            	}
            case MotionEvent.ACTION_DOWN:
            	if(CResultLayout.IsHitNextButton((int)x, (int)y))
            	{
            	    m_GUILayer.OnResultNextButtonDown();
            	}
                break;
        }
	}
	
	public void onResultKeyDown()
	{
	    m_GUILayer.OnResultNextButtonDown();
	}
	
	public void onResultKeyUp()
	{
		m_GUILayer.OnResultNextButtonUp();
		GoToNextAnswerResult();
	}

	public void OnTimerEvent()
	{
		if(CGameState.IsTouchMode() == true)
			CGameTouchLayer.OnTimerEvent();
		if(CGameState.IsGameSplashScreen())
		{
			if(CSplashScreen.IsRunning()== true)
			{
				CSplashScreen.OnTimerEvent();
				return;
			}
		}

		if(m_BkngLayer != null)
			m_BkngLayer.OnTimerEvent();
		//m_GUILayer.OnTimerEvent();
		if(CGameState.InAnimation() || CGameState.IsGameResult())
		{
			m_AnimationLayer.OnTimerEvent();
		}			
	}
	
	public void OnDraw(Canvas canvas)
	{
		DrawDesktop(canvas);
		int nState = CGameState.GetGameState();
		switch(nState)
		{
			case CGameState.GAME_SPLASHSCREEN:
				OnDrawSplashScreenState(canvas);
				break;
			case CGameState.GAME_READY:
				OnDrawStaticGameLayout(canvas, true);
				break;
			case CGameState.GAME_RUNNING:
				OnDrawRunningState(canvas);
				break;
			case CGameState.GAME_RESULT:
				OnDrawResultState(canvas);
				break;
		}
		if(CGameState.IsTouchMode() == true)
			CGameTouchLayer.OnDraw(canvas);
	}		

	public void OnDrawRunningState(Canvas canvas)
	{
		m_BkngLayer.OnDraw(canvas);
		m_GUILayer.OnDraw(canvas);
		if(IsGameEnd() == false)
			DrawCardBackSide(canvas);
		m_CurDeal.OnDraw(canvas);
		OnNonAnimationDraw(canvas);
		if(CGameState.InMovement())
		{	
			m_AnimationLayer.OnDraw(canvas);
		}
	}		
	
	public void OnDrawResultState(Canvas canvas)
	{
		DrawResultTextTitle(canvas);
		m_BkngLayer.OnDraw(canvas);
		m_GUILayer.OnDrawInResult(canvas);
		if(0 < m_RightAnswers.size() && m_AnswerListIterator < m_RightAnswers.size())
		{
			m_RightAnswers.elementAt(m_AnswerListIterator).DrawResult(canvas);
		}
		m_AnimationLayer.OnDraw(canvas);
	}

	public void OnDrawSplashScreenState(Canvas canvas)
	{
		if(CSplashScreen.IsRunning())
		{
			CSplashScreen.OnDraw(canvas);
		}
	}
	
	public void OnDrawStaticGameLayout(Canvas canvas, boolean bAnimation)
	{
		//m_BkngLayer.OnDrawStatic(canvas);
		DrawDesktop(canvas);
		DrawCardBackSide(canvas);
		m_CurDeal.OnDraw(canvas);
		Rect rect = CDealLayout.GetOperand1Rect();
		if(rect != null)
		{
			CGameHelper.DrawPhantomCard(canvas, rect);
		}
		
		rect = CDealLayout.GetOperand2Rect();
		if(rect != null)
		{
			CGameHelper.DrawPhantomCard(canvas, rect);
		}
		
		rect = CDealLayout.GetResultCardRect();
		if(rect != null)
		{
			CGameHelper.DrawPhantomCard(canvas, rect);
		}
		m_GUILayer.OnDraw(canvas);
		
	}
	
	private Drawable CreateStaticGameImage()
	{
		Drawable image = null;
		
	    int width = CDealLayout.WindowWidth();
	    int height = CDealLayout.WindowHeight();
	    Bitmap destBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(destBmp);
		OnDrawStaticGameLayout(canvas, false);
		Resources res = CGameHelper.GetSystemResource();
		BitmapDrawable bmpimage = new BitmapDrawable(res, destBmp);
		image = (Drawable)bmpimage;	
		
		return image;
	}

	private Drawable GetDesktopSnapshoot()
	{
		Drawable image = null;
		
		int width = CDealLayout.WindowWidth();
		int height = CDealLayout.WindowHeight();
		Bitmap destBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(destBmp);
		OnDraw(canvas);
		Resources res = CGameHelper.GetSystemResource();
		BitmapDrawable bmpimage = new BitmapDrawable(res, destBmp);
		image = (Drawable)bmpimage;	
		
		return image;
	}
	
	private void InitializeSplashScreenLayer()
	{
		Drawable gameimage = CreateStaticGameImage();
		CSplashScreen.Initialize(gameimage);
		CSplashScreen.AttachGame(this);
		CGameTouchLayer.Initialize();
	}
	
	private void DrawDesktop(Canvas canvas)
	{
	    int width = CDealLayout.WindowWidth();
	    int height = CDealLayout.WindowHeight();
	    Rect rect = new Rect(0, 0, width, height);
        canvas.drawRect(rect, m_BkGndPaint);

        Paint PaintLevel = m_PointPaint3;
        if(CGameType.IsLevelEasy() == true)
        {
        	PaintLevel = m_PointPaint1;
        }
        else if(CGameType.IsLevelMedium() == true)
        {
        	PaintLevel = m_PointPaint2;
        }
        
        Integer nLevel = CGameType.GetPoints();
        String szLevel = nLevel.toString();
		int nCount = szLevel.length();
		PaintLevel.getTextBounds(szLevel, 0, nCount, rect);
	    width = rect.width();
	    height = rect.height();
		int cx = CDealLayout.WindowCenterX();
		int cy = CDealLayout.WindowCenterY();
		float x = (float)(cx-width/2); 
		float y = (float)(cy-height); 
		canvas.drawText(szLevel, x, y, PaintLevel);
        
        
		String logo = "e-gadget";
		nCount = logo.length();
		m_LogoPaint.getTextBounds(logo, 0, nCount, rect);
	    width = rect.width();
	    height = rect.height();
		x = (float)(cx-width/2); 
		y = (float)(cy+height/2); 
		canvas.drawText(logo, x, y, m_LogoPaint);
	}
	
	private void DrawResultTextTitle(Canvas canvas)
	{
		String title = CGameHelper.m_strResultTitle;
		if(title != null)
		{
		    int width = CDealLayout.WindowWidth()-4;
		    int height = CDealLayout.WindowHeight()-4;
		    Rect rect = new Rect(0, 0, width, height);
			int nCount = title.length();
			if(CDealLayout.IsLargeScreen() == false)
			    m_ResultPaint.setTextSize(16.0f);

			m_ResultPaint.getTextBounds(title, 0, nCount, rect);
		    width = rect.width();
			int cx = CDealLayout.WindowCenterX();
			float x = (float)(cx-width/2);
			float y = (float)(CResultLayout.m_nTitleTop); 
			canvas.drawText(title, x, y, m_ResultPaint);
		}
	}
	
	public void SaveInstanceState(Bundle outState)
	{
		if(CGameState.IsGameResult() == true && m_RightAnswers.size() <= m_AnswerListIterator)
			return;
			
		m_CurDeck.SaveInstanceState(outState);
		m_CurDeal.SaveInstanceState(outState);;
		SaveRightAnswersToBundle(outState);
	}
	
	public void SaveRightAnswersToBundle(Bundle outState)
	{
		int nCount = m_RightAnswers.size();
		outState.putInt(KEY_RIGHTANSWERS, nCount);
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CAnswerRecord answer = m_RightAnswers.elementAt(i);
				if(answer != null)
				{
					answer.SaveAnswerState(outState, i);
				}
			}
		}
	}
	
	private void DoRestoreStart()
	{
		if(m_RestoreStorage != null)
		{
			m_CurDeck.LoadRestoreData(m_RestoreStorage);
			m_CurDeal.LoadRestoreData(m_RestoreStorage);
			LoadRestoreRightAnswers(m_RestoreStorage);
			int nDeck = m_CurDeck.GetCardCount();
			int nDeal = m_CurDeal.GetCardCount();
			int nAnswer = m_RightAnswers.size();
			if(nDeck == 0 && nDeal == 0 && nAnswer == 0)
			{
				DoStartWithoutRestore();
				return;
			}
			else if(nDeck == 0 && nDeal == 0 && nAnswer != 0)
			{
				GoToResultState();
				return;
			}
			if(m_BkngLayer != null)
				m_BkngLayer.SetRunningStars(m_CurDeck.DealCount(), m_RightAnswers.size());
		    if(CGameType.IsSplashScreenEnable() == true)
		    {	
		    	InitializeSplashScreenLayer();
				CSplashScreen.StartSplashScreen();
		    }	    	
		}
	}
	
	private void LoadRestoreRightAnswers(Bundle saveState)
	{
		m_RightAnswers.clear();
		int nCount = saveState.getInt(KEY_RIGHTANSWERS);
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CAnswerRecord answer = new CAnswerRecord();
				if(answer != null)
				{
					answer.LoadRetoreAnswer(saveState, i);
				}
				if(0 < answer.m_CalculationSteps.size())
				{
					m_RightAnswers.add(answer);	
				}
			}
		}
	}
	
	private void HandleScoreSaving()
	{
		if(m_Parent != null)
		{
			int nScore = m_RightAnswers.size();
			m_Parent.SaveGameScore(nScore);
		}
	}
}	
