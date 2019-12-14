package com.e_gadget.MindFire;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class CDnDAnimationLayer 
{
	private CGameSprite 	m_DNDSprite;

    private Paint 			m_CursorPaint;
	
	public CDnDAnimationLayer()
	{
		m_DNDSprite = null;
	    
		// Initialize paint for cursor highlight
		m_CursorPaint = new Paint();
		m_CursorPaint.setAntiAlias(true);
		m_CursorPaint.setARGB(128, 255, 250, 32);
	}
	
	public void StartDragAndDrop(CGameSprite sprite)
	{
		if(CGameState.IsGameRunning())
		{	
			m_DNDSprite = sprite;
			CGameState.StartDragAndDrop();
		}			
	}

	public void DragAndDropOver(int x, int y)
	{
		if(CGameState.IsGameRunning() && m_DNDSprite != null)
		{	
			if(CGameState.GetSecondState() == CGameState.GAME_SUBSTATE_DNDREADY)
			{
				CGameState.DragAndDropMoveOver();
				m_DNDSprite.MoveTo(x, y);
			}
			else if(CGameState.GetSecondState() == CGameState.GAME_SUBSTATE_DND)
			{
				m_DNDSprite.MoveTo(x, y);
			}
		}			
	}
	
	public void StopDragAndDrop()
	{
		if(CGameState.IsGameRunning())
		{	
			m_DNDSprite = null;
			CGameState.StopDragAndDrop();
		}			
	}
	
	public void Reset()
	{
		m_DNDSprite = null;
	}
	
	public boolean IsDnDCursor(CLayoutCursor cursor)
	{
		boolean bRet = false;
		if(CGameState.InDragAndDrop() && m_DNDSprite != null)
		{	
			bRet = m_DNDSprite.IsDnDCursor(cursor);
		}			
		
		return bRet;
	}
	
	public void OnDraw(Canvas canvas)
	{
		CLayoutCursor cursor = m_DNDSprite.GetStartCursor();
		
		if(cursor.CanDragAndDrop())
		{
			int x = m_DNDSprite.PositionX();
			int y = m_DNDSprite.PositionY();
			boolean bDraw = false;
			Rect rect = new Rect(0, 0, 0, 0);
			int index;
			int nIndex = cursor.GetIndex(); 
			
			switch(cursor.GetType())
			{
				case CLayoutCursor.TYPE_BASIC_CARD:
					index = CDealLayout.HitOperand(x, y);
					if(index == 0)
					{
						rect = CDealLayout.GetOperand1Boundary();
						bDraw = true;
					}
					else if(index == 1)
					{
						rect = CDealLayout.GetOperand2Boundary();
						bDraw = true;
					}
					else
					{
						index = CDealLayout.HitBasicCard(x, y);
						if(-1 < index && index == nIndex)
						{
							rect = CDealLayout.GetBasicCardRect(index);
							bDraw = true;
						}
					}
					break;
				case CLayoutCursor.TYPE_TEMP_CARD:
					index = CDealLayout.HitOperand(x, y);
					if(index == 0)
					{
						rect = CDealLayout.GetOperand1Boundary();
						bDraw = true;
					}
					else if(index == 1)
					{
						rect = CDealLayout.GetOperand2Boundary();
						bDraw = true;
					}
					else
					{
						index = CDealLayout.HitTempCard(x, y, 4);
						if(-1 < index && index == nIndex)
						{
							rect = CDealLayout.GetTempCardRect(index);
							bDraw = true;
						}
					}
					break;
				case CLayoutCursor.TYPE_OPERAND_CARD:
					int n = m_DNDSprite.GetCardIndex();
					if(m_DNDSprite.IsBasicCard())
					{
						index = CDealLayout.HitBasicCard(x, y);
						if(-1 < index && n == index)
						{
							rect = CDealLayout.GetBasicCardBoundary(index);
							bDraw = true;
						}
					}
					else
					{
						index = CDealLayout.HitTempCard(x, y, 4);
						if(-1 < index && n == index)
						{
							rect = CDealLayout.GetTempCardBoundary(index);
							bDraw = true;
						}
					}
					if(bDraw == false)
					{
						index = CDealLayout.HitOperand(x, y);
						if(-1 < index)
						{
							if(index == 0)
								rect = CDealLayout.GetOperand1Rect();
							else
								rect = CDealLayout.GetOperand2Rect();
								
							bDraw = true;
						}
					}
					break;
				case CLayoutCursor.TYPE_RESULT_CARD:
					index = CDealLayout.HitTempCard(x, y, 4);
					if(-1 < index)
					{
						rect = CDealLayout.GetTempCardBoundary(index);
						bDraw = true;
					}
					else
					{
						index = CDealLayout.HitOperand(x, y);
						if(index == 0)
						{
							rect = CDealLayout.GetOperand1Boundary();
							bDraw = true;
						}
						else if(index == 1)
						{
							rect = CDealLayout.GetOperand2Boundary();
							bDraw = true;
						}
						
					}
					break;
				case CLayoutCursor.TYPE_SIGNSBAR_BUTTON:
					if(CDealLayout.HitOperator(x, y))
					{
						rect = CDealLayout.GetOperatorBoundary();
						bDraw = true;
					}
					else
					{
						index = CDealLayout.HitSignsBar(x, y);
						if(nIndex == index)
						{
							rect = CDealLayout.GetSignsRect(index);
							bDraw = true;
						}
					}
					break;
			}
			if(bDraw)
			{
				CGameHelper.DrawHighligCursor(canvas, m_CursorPaint, rect);
			}
		}
		
		m_DNDSprite.Draw(canvas);
	}		
}
