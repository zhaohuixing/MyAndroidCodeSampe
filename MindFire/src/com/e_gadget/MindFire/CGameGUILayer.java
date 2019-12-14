package com.e_gadget.MindFire;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class CGameGUILayer 
{
    //The operator button
    private CGameToggleButton		m_OperatorBtn;

    //The "=" button
    private CGameImageButton		m_AssignBtn;
    
    //The "+, -, *, /" button group
    private CGameSignBar			m_SignBtns;

    //The "Cancel" button
    private CGameImageButton		m_CancelBtn;

    //The "New Deal" button
    public CGameImageButton		m_DealBtn;

	private boolean					m_Initialized;
	
    /** Paint to draw the cursor highlight on screen. */
    private Paint 					m_CursorPaint;
	
    
	public CGameGUILayer()
	{
	    m_OperatorBtn = null;
	    m_AssignBtn = null;
	    m_SignBtns = null;
	    m_CancelBtn = null;
	    m_DealBtn = null;
		m_Initialized = false;
		m_CursorPaint = null;
		Initialize();
	}
	
	public void Initialize()
	{
		if(m_Initialized == true)
			return;
		
		if(CGameHelper.CanLoadResource() && CDealLayout.IsInitialized())
		{	
			Drawable image1 = CGameHelper.GetSignImage(0, false);
			Drawable image2 = CGameHelper.GetSignImage(0, true);
		    m_OperatorBtn = new CGameToggleButton(CDealLayout.GetOperatorRect(), false);
		    m_OperatorBtn.AddImages(image1, image2);
		    //The "+" button
			image1 = CGameHelper.GetSignImage(COperator.PLUS, false);
			image2 = CGameHelper.GetSignImage(COperator.PLUS, true);
		    m_OperatorBtn.AddImages(image1, image2);
		    //The "-" button
			image1 = CGameHelper.GetSignImage(COperator.MINUS, false);
			image2 = CGameHelper.GetSignImage(COperator.MINUS, true);
		    m_OperatorBtn.AddImages(image1, image2);
		    //The "*" button
			image1 = CGameHelper.GetSignImage(COperator.TIME, false);
			image2 = CGameHelper.GetSignImage(COperator.TIME, true);
		    m_OperatorBtn.AddImages(image1, image2);
		    //The "/" button
			image1 = CGameHelper.GetSignImage(COperator.DIVIDE, false);
			image2 = CGameHelper.GetSignImage(COperator.DIVIDE, true);
		    m_OperatorBtn.AddImages(image1, image2);
		    

		    //The "=" button
			image1 = CGameHelper.GetSignImage(5, false);
			image2 = CGameHelper.GetSignImage(5, true);
		    m_AssignBtn = new CGameImageButton(image1, image2, CDealLayout.GetCalculateRect());
		    
		    //The "+, -, *, /" button group
		    m_SignBtns = new CGameSignBar();
		    //The "+" button
			image1 = CGameHelper.GetSignImage(COperator.PLUS, false);
			image2 = CGameHelper.GetSignImage(COperator.PLUS, true);
		    CGameImageButton btn = new CGameImageButton(image1, image2, CDealLayout.GetSignsRect(0));
		    m_SignBtns.Add(btn);

		    //The "-" button
			image1 = CGameHelper.GetSignImage(COperator.MINUS, false);
			image2 = CGameHelper.GetSignImage(COperator.MINUS, true);
		    btn = new CGameImageButton(image1, image2, CDealLayout.GetSignsRect(1));
		    m_SignBtns.Add(btn);

		    //The "*" button
			image1 = CGameHelper.GetSignImage(COperator.TIME, false);
			image2 = CGameHelper.GetSignImage(COperator.TIME, true);
		    btn = new CGameImageButton(image1, image2, CDealLayout.GetSignsRect(2));
		    m_SignBtns.Add(btn);
		    
		    //The "/" button
			image1 = CGameHelper.GetSignImage(COperator.DIVIDE, false);
			image2 = CGameHelper.GetSignImage(COperator.DIVIDE, true);
		    btn = new CGameImageButton(image1, image2, CDealLayout.GetSignsRect(3));
		    m_SignBtns.Add(btn);

		    //The "Cancel" button
			image1 = CGameHelper.GetSignImage(6, false);
			image2 = CGameHelper.GetSignImage(6, true);
		    m_CancelBtn = new CGameImageButton(image1, image2, CDealLayout.GetCancelButtonRect());

		    //The "New Deal" button
			image1 = CGameHelper.GetSignImage(7, false);
			image2 = CGameHelper.GetSignImage(7, true);
			m_DealBtn = new CGameImageButton(image1, image2, CDealLayout.GetNewDealRect());
			
			m_Initialized = true;
		}			
	}
	
	public void SetCursorPaint(Paint paint)
	{
		m_CursorPaint = paint;
	}

	public void ReloadResource()
	{
		if(m_Initialized == false)
			Initialize();
		else
		{	
		    m_OperatorBtn.SetBoundary(CDealLayout.GetOperatorRect());
		    m_AssignBtn.SetBoundary(CDealLayout.GetCalculateRect());
			m_SignBtns.SetButtonBoundary(0, CDealLayout.GetSignsRect(0));
			m_SignBtns.SetButtonBoundary(1, CDealLayout.GetSignsRect(1));
			m_SignBtns.SetButtonBoundary(2, CDealLayout.GetSignsRect(2));
			m_SignBtns.SetButtonBoundary(3, CDealLayout.GetSignsRect(3));
			m_SignBtns.SetButtonBoundary(4, CDealLayout.GetCancelButtonRect());
			m_SignBtns.SetButtonBoundary(5, CDealLayout.GetNewDealRect());
		}			
	}
	
	public void OnDraw(Canvas canvas)
	{
		OnOperatorDraw(canvas);
		OnAssignDraw(canvas);
		OnSignsBarDraw(canvas);
		OnCancelDraw(canvas);
		OnNewDealDraw(canvas);
	}

	public void OnDrawInResult(Canvas canvas)
	{
		//OnNewDealDraw(canvas);
		if(m_DealBtn != null)
		{
			if(m_DealBtn.IsPressed())
			{
				CLayoutCursor cursor = CGameHighLight.GetCursor();
				cursor.Set(CLayoutCursor.TYPE_NEWDEAL_BUTTON, 0);
				CGameHelper.DrawHighligCursor(canvas, m_CursorPaint, cursor);
			}
			m_DealBtn.Draw(canvas);
		}			
	}
	
	private void OnOperatorDraw(Canvas canvas)
	{
		CLayoutCursor cursor = CGameHighLight.GetCursor();
		int nType = cursor.GetType();
		if(nType == CLayoutCursor.TYPE_OPERATOR_BUTTON)
		{
			CGameHelper.DrawHighligCursor(canvas, m_CursorPaint, cursor);
		}
		if(m_OperatorBtn != null)
			m_OperatorBtn.Draw(canvas);
	}
	
	private void OnAssignDraw(Canvas canvas)
	{
		CLayoutCursor cursor = CGameHighLight.GetCursor();
		int nType = cursor.GetType();
		if(nType == CLayoutCursor.TYPE_CALCULATE_BUTTON)
		{
			CGameHelper.DrawHighligCursor(canvas, m_CursorPaint, cursor);
		}
		if(m_AssignBtn != null)
			m_AssignBtn.Draw(canvas);
	}
	
	private void OnSignsBarDraw(Canvas canvas)
	{
		OnSignButtonDrawHighlight(canvas);
		if(m_SignBtns != null)
			m_SignBtns.Draw(canvas);
	}

	private void OnSignButtonDrawHighlight(Canvas canvas)
	{
		CLayoutCursor cursor = CGameHighLight.GetCursor();
		int nType = cursor.GetType();
		int nIndex = cursor.GetIndex();
		if(nType == CLayoutCursor.TYPE_SIGNSBAR_BUTTON && 0 <= nIndex && nIndex <= 3)
		{
			CGameHelper.DrawHighligCursor(canvas, m_CursorPaint, cursor);
		}
	}
	
	private void OnCancelDraw(Canvas canvas)
	{
		CLayoutCursor cursor = CGameHighLight.GetCursor();
		int nType = cursor.GetType();
		if(nType == CLayoutCursor.TYPE_CANCEL_BUTTON)
		{
			CGameHelper.DrawHighligCursor(canvas, m_CursorPaint, cursor);
		}
		if(m_CancelBtn != null)
			m_CancelBtn.Draw(canvas);
	}

	private void OnNewDealDraw(Canvas canvas)
	{
		CLayoutCursor cursor = CGameHighLight.GetCursor();
		int nType = cursor.GetType();
		if(nType == CLayoutCursor.TYPE_NEWDEAL_BUTTON)
		{
               CGameHelper.DrawHighligCursor(canvas, m_CursorPaint, cursor);
		}
		if(m_DealBtn != null)
			m_DealBtn.Draw(canvas);
	}
	
	public void DoKeyPress()
	{
		CLayoutCursor cursor = CGameHighLight.GetCursor();
		OnKeyDown(cursor);
	}

	public void OnKeyDown(CLayoutCursor cursor)
	{
		int nType = cursor.GetType();
		int nIndex = cursor.GetIndex();

		switch(nType)
		{
			case CLayoutCursor.TYPE_OPERATOR_BUTTON:
				if(m_OperatorBtn != null)
					m_OperatorBtn.onKeyDown();
				break;
			case CLayoutCursor.TYPE_SIGNSBAR_BUTTON:
				if(m_SignBtns != null)
				{	
					m_SignBtns.SetActiveButton(nIndex);				
					m_SignBtns.onKeyDown();
				}					
				break;
			case CLayoutCursor.TYPE_CALCULATE_BUTTON:
				if(m_AssignBtn != null)
					m_AssignBtn.onKeyDown();
				break;
			case CLayoutCursor.TYPE_CANCEL_BUTTON:
				if(m_CancelBtn != null)
					m_CancelBtn.onKeyDown();
				break;
			case CLayoutCursor.TYPE_NEWDEAL_BUTTON:
				if(m_DealBtn != null)
					m_DealBtn.onKeyDown();
				break;
		}
	}
	
	public void SetSignBarActiveButton(int index)
	{
		if(m_SignBtns != null)
			m_SignBtns.SetActiveButton(index);
	}

	public void ResetOperatorButton()
	{
		if(m_OperatorBtn != null)
		{	
			m_OperatorBtn.Reset();
		}			
	}
	
	public void OnOperatorButtonUp()
	{
		if(m_OperatorBtn != null)
		{	
			m_OperatorBtn.onKeyUp();
			m_OperatorBtn.Toggle();
		}			
	}

	public void OnSignBarUp(CLayoutCursor cursor)
	{
		if(m_SignBtns != null && m_OperatorBtn != null)
		{	
			m_SignBtns.onKeyUp();
			m_OperatorBtn.SetActive(m_SignBtns.GetActiveButton()+1);
			//Log.w(this.getClass().getName(), "OnSignBarUp Call SetActive");					
			
		}			
	}

	public void OnAssignButtonUp()
	{
		if(m_AssignBtn != null)
		{	
			m_AssignBtn.onKeyUp();
		}			
	}
	
	public int GetActiveOperation()
	{
		int nRet = -1;
		if(m_OperatorBtn != null)
		{	
			nRet = m_OperatorBtn.GetActive();
		}			
		
		return nRet;
	}	

	public void SetActiveOperation(int index)
	{
		if(m_OperatorBtn != null)
		{	
			m_OperatorBtn.SetActive(index);
			Log.w(this.getClass().getName(), "SetActiveOperation Call SetActive");
		}			
	}	
	
	public Drawable GetCursorImage(CLayoutCursor cursor)
	{
		Drawable image = null;
		int nType = cursor.GetType();
		int nIndex = cursor.GetIndex();

		switch(nType)
		{
			case CLayoutCursor.TYPE_OPERATOR_BUTTON:
				if(m_OperatorBtn != null)
					image = m_OperatorBtn.GetNormalImage(m_OperatorBtn.GetActive());
				break;
			case CLayoutCursor.TYPE_SIGNSBAR_BUTTON:
				if(m_SignBtns != null)
					image = m_SignBtns.GetNormalImage(nIndex);				
				break;
			case CLayoutCursor.TYPE_CALCULATE_BUTTON:
				if(m_AssignBtn != null)
					image = m_AssignBtn.GetNormalImage();
				break;
			case CLayoutCursor.TYPE_CANCEL_BUTTON:
				if(m_CancelBtn != null)
					image = m_CancelBtn.GetNormalImage();
				break;
			case CLayoutCursor.TYPE_NEWDEAL_BUTTON:
				if(m_DealBtn != null)
					image = m_DealBtn.GetNormalImage();
				break;
		}
		return image;
		
	}
	
	public void OnResultNextButtonDown()
	{
		if(m_DealBtn != null)
			m_DealBtn.onKeyDown();
	}

	public void OnResultNextButtonUp()
	{
		if(m_DealBtn != null)
			m_DealBtn.onKeyUp();
	}
}
