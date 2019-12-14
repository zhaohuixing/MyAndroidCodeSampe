package com.xgadget.SimpleGambleWheel;

import com.xgadget.uimodule.ResourceHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
//import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

public class GamePlayerBetView extends BasicLayoutView 
{
	private	RectF 		m_RepaintRect1;
	private Rect		m_SrcImageRect1;	
	private	RectF 		m_RepaintRect2;
	private Rect		m_SrcImageRect2;	
	private	RectF 		m_RepaintDisableRect;
	private Rect		m_SrcDisableImageRect;	

	private GamePlayer		m_Parent;
    
	public void ShowPlayBet()
	{
		m_Parent.m_bBetViewShowBetNumbers = true;
		this.invalidate();
	}

	public void HidePlayBet()
	{
		m_Parent.m_bBetViewShowBetNumbers = false;
		this.invalidate();
	}
    
	private void Initialize()
	{
		Bitmap bitmap = ResourceHelper.GetPledgeSignBitmap();
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		m_SrcImageRect1 = new Rect(0, 0, w, h);

		bitmap = ResourceHelper.GetLuckyNumberSignBitmap();
		w = bitmap.getWidth();
		h = bitmap.getHeight();
		m_SrcImageRect2 = new Rect(0, 0, w, h);

		bitmap = ResourceHelper.GetCrossBitmap();
		w = bitmap.getWidth();
		h = bitmap.getHeight();
		m_SrcDisableImageRect = new Rect(0, 0, w, h);
		
		CalculateRepaintRegions();
	
		m_Parent = null;
	}

	public GamePlayerBetView(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public GamePlayerBetView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public GamePlayerBetView(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize();
	}
	
	public void AttachParent(GamePlayer parent)
	{
		m_Parent = parent;
	}

	
	public int GetPlayBetLuckNumber()
	{
		int nRet = -1;
		if(m_Parent != null)
		{	
			nRet = m_Parent.GetPlayBetLuckNumber();
		}	
		
		return nRet;
	}

	public int GetPlayBet()
	{
		int nRet = -1;
		if(m_Parent != null)
		{	
			nRet = m_Parent.GetPlayBet();
		}	
		
		return nRet;
	}
	
	public boolean AlreadyMadePledge()
	{
		boolean bRet = false;
		
		if(m_Parent != null)
		{	
			bRet = m_Parent.AlreadyMadePledge();
		}	
		
		return bRet;
	}
	
	
	public GamePlayer GetParent()
	{
		return m_Parent;
	}

	public boolean IsMyTurn()
	{
		boolean bRet = false;
		if(m_Parent != null)
		{	
			bRet = m_Parent.IsMyTurn();
		}	
		return bRet;
	}

	public boolean IsMySelf()
	{
		boolean bRet = false;
		if(m_Parent != null)
		{	
			bRet = m_Parent.IsMySelf();
		}	
		return bRet;
	}	
	
	public boolean WinThisTime()
	{
		boolean bRet = false;
		if(m_Parent != null)
		{	
			bRet = m_Parent.WinThisTime();
		}	
		return bRet;
	}
	
	public int GetSeatID()
	{
		int nRet = 0;
		if(m_Parent != null)
		{	
			nRet = m_Parent.GetSeatID();
		}	
		return nRet;
	}	
	
	
	public boolean IsOnlinePlayer()
	{
		boolean bRet = false;
		if(m_Parent != null)
		{	
			bRet = m_Parent.IsOnlinePlayer();
		}	
		return bRet;
	}
	
	public boolean IsEnable()
	{
		boolean bRet = false;
		if(m_Parent != null)
		{	
			bRet = m_Parent.IsEnabled();
		}	
		return bRet;
	}
	
	
	public void PostOnLayoutHandle()
	{
		int size = GameLayoutConstant.GetCurrentAvatarSize();
		int nHeight = size/2;
		int nSeatIndex = GetSeatID();
		int top = 0;
		if(nSeatIndex == 0)
		{
			top = size;
		}
		else if(nSeatIndex == 1)
		{
			top = size;
		}
		else if(nSeatIndex == 2)
		{
			top = size+size/2;
		}
		else if(nSeatIndex == 3)
		{
			top = size+size/2;
		}
		
		if(m_LayoutContainer != null)
			m_LayoutContainer.SetChildNewDemension(this, size, nHeight, 0, top);		
		
		CalculateRepaintRegions();
		
		invalidate();
	}
	
	private void CalculateRepaintRegions()
	{
		float size = (float)GameLayoutConstant.GetCurrentAvatarSize();
		float nHeight = size/2.0f;
		m_RepaintRect1 = new RectF(0, 0, nHeight, nHeight);
		m_RepaintRect2 = new RectF(nHeight, 0, size, nHeight);
		float size2 = (float)m_SrcDisableImageRect.width();
		
		if(nHeight < size2)
			size2 = nHeight; 
		float x = (size - size2)/2.0f;
		float y = (nHeight - size2)/2.0f;
		
		m_RepaintDisableRect = new RectF(x, y, x+size2, y+size2);
	}
	
	protected void onDraw(Canvas canvas)
	{
		if(m_Parent == null)
			return;
		
		if(SimpleGambleWheel.m_ApplicationController.m_GameController.IsOnline() == true && m_Parent.IsOnlinePlayer() == false)
			return;
		
		if(IsEnable() == true)
		{
			DrawEnableState(canvas);
		}
		else
		{
			DrawDsiableState(canvas);
		}
 	}

	private void DrawEnableState(Canvas canvas)
	{
		int nGameState = SimpleGambleWheel.m_ApplicationController.GetGameState();
		if(nGameState == GameConstants.GAME_STATE_RESULT)
		{
			DrawResultAnimation(canvas);
		}
		else
		{	
			DrawEnableBackground(canvas);
		}	
	}
	
	private void DrawResultAnimation(Canvas canvas)
	{
		if(WinThisTime() == true)
		{
			DrawCashFlyingAnimation(canvas);
		}
		else
		{
			DrawEnableBackground(canvas);
		}
	}
	
	private void DrawCashFlyingAnimation(Canvas canvas)
	{
		Bitmap bitmap = ResourceHelper.GetCashBitmap();
		if(bitmap == null)
			return;
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Rect srcRect = new Rect(0, 0, w, h);
		float fw = (float)GameLayoutConstant.GetCurrentAvatarSize();
		float fh = fw/2.0f;
		RectF drawRect = new RectF(0, 0, fw, fh);
		if(m_Parent.m_bBetViewDrawCashBackground == true)
		{
			canvas.drawBitmap(bitmap, srcRect, drawRect, null);
		}
		float r = 0.1f + 0.1f*((float)m_Parent.m_nBetViewAnimationFrame);
		float rw = 0.9f*fw;
		float tw = 0.1f*fw;
		fw = rw + tw*r;
		fh = fh*r;
		drawRect = new RectF(0, 0, fw, fh);
		canvas.drawBitmap(bitmap, srcRect, drawRect, null);
	}
	
	private void DrawEnableBackground(Canvas canvas)
	{
		Bitmap bitmap = ResourceHelper.GetPledgeSignBitmap();
		if(bitmap != null)
			canvas.drawBitmap(bitmap, m_SrcImageRect1, m_RepaintRect1, null);
		if(Configuration.getCurrentThemeType() == GameConstants.GAME_THEME_NUMBER)
			bitmap = ResourceHelper.GetLuckyNumberSignBitmap();
		else
			bitmap = ResourceHelper.GetLuckyIcon2Bitmap();
			
		if(bitmap != null)
			canvas.drawBitmap(bitmap, m_SrcImageRect2, m_RepaintRect2, null);
		if(AlreadyMadePledge() == true)
			DrawEnableNumberSigns(canvas);
	}

	private void DrawEnableNumberSigns(Canvas canvas)
	{
		DrawPledgeChipsSign(canvas);
		if(Configuration.getCurrentThemeType() == GameConstants.GAME_THEME_NUMBER)
			DrawLuckyNumberSign(canvas);
		else
			DrawLuckyThemeSign(canvas);
	}
	
	
	private void DrawPledgeChipsSign(Canvas canvas)
	{
		Bitmap bitmap = null;
		if(m_Parent.m_bBetViewShowBetNumbers == false)
		{
			bitmap = ResourceHelper.GetQMarkYellowBitmap();
		}
		else
		{
			int nChips = this.GetPlayBet();
			if(0 <= nChips)
			{	
				bitmap = ResourceHelper.GetYellowNumberBitmap(nChips);
			}
		}	
		if(bitmap != null)
		{	
			RectF drawRect;
			
			int w = bitmap.getWidth();
			int h = bitmap.getHeight();
			Rect srcRect = new Rect(0, 0, w, h);
			float r = ((float)w)/((float)h);
			float size = m_RepaintRect1.width()*0.4f;
            float cx = m_RepaintRect1.centerX();
            float cy = m_RepaintRect1.centerY();
			if(1.0f < r)
			{
				float dw = size;
				float dh = dw/r;
				float left = cx-dw*1.1f/2.0f;
				float top = cy - dh*1.1f/2.0f;
				float right = left + dw;
				float bottom = top + dh;
				drawRect = new RectF(left, top, right, bottom); 
			}
			else
			{
				float dh = size;
				float dw = dh*r;
				float left = cx-dw*1.1f/2.0f;
				float top = cy - dh*1.1f/2.0f;
				float right = left + dw;
				float bottom = top + dh;
				drawRect = new RectF(left, top, right, bottom); 
			}
			canvas.drawBitmap(bitmap, srcRect, drawRect, null);
		}	
	}
	
	private void DrawLuckyNumberSign(Canvas canvas)
	{
		Bitmap bitmap = null;
		if(m_Parent.m_bBetViewShowBetNumbers == false)
		{
			bitmap = ResourceHelper.GetQMarkGreenBitmap();
		}
		else
		{
			int nNumber = this.GetPlayBetLuckNumber();
			if(0 <= nNumber)
			{	
				bitmap = ResourceHelper.GetGreenNumberBitmap(nNumber);
			}
		}	
		if(bitmap != null)
		{	
			RectF drawRect;
			
			int w = bitmap.getWidth();
			int h = bitmap.getHeight();
			Rect srcRect = new Rect(0, 0, w, h);
			float r = ((float)w)/((float)h);
			float size = m_RepaintRect2.width()*0.25f;
            float cx = m_RepaintRect2.centerX();
            float cy = m_RepaintRect2.centerY();
			if(1.0f < r)
			{
				float dw = size;
				float dh = dw/r;
				float left = cx-dw*1.06f/2.0f;
				float top = cy - dh*1.1f/2.0f;
				float right = left + dw;
				float bottom = top + dh;
				drawRect = new RectF(left, top, right, bottom); 
			}
			else
			{
				float dh = size;
				float dw = dh*r;
				float left = cx-dw*1.06f/2.0f;
				float top = cy - dh*1.1f/2.0f;
				float right = left + dw;
				float bottom = top + dh;
				drawRect = new RectF(left, top, right, bottom); 
			}
			canvas.drawBitmap(bitmap, srcRect, drawRect, null);
		}	
	}
	
	private void DrawLuckyThemeSign(Canvas canvas)
	{
		Bitmap bitmap = null;
		if(m_Parent.m_bBetViewShowBetNumbers == false)
		{
			bitmap = ResourceHelper.GetQMarkGreenBitmap();
			if(bitmap != null)
			{	
				RectF drawRect;
				
				int w = bitmap.getWidth();
				int h = bitmap.getHeight();
				Rect srcRect = new Rect(0, 0, w, h);
				float r = ((float)w)/((float)h);
				float size = m_RepaintRect2.width()*0.25f;
	            float cx = m_RepaintRect2.centerX();
	            float cy = m_RepaintRect2.centerY();
				if(1.0f < r)
				{
					float dw = size;
					float dh = dw/r;
					float left = cx-dw*1.06f/2.0f;
					float top = cy - dh*1.1f/2.0f;
					float right = left + dw;
					float bottom = top + dh;
					drawRect = new RectF(left, top, right, bottom); 
				}
				else
				{
					float dh = size;
					float dw = dh*r;
					float left = cx-dw*1.06f/2.0f;
					float top = cy - dh*1.1f/2.0f;
					float right = left + dw;
					float bottom = top + dh;
					drawRect = new RectF(left, top, right, bottom); 
				}
				canvas.drawBitmap(bitmap, srcRect, drawRect, null);
			}	
		}
		else
		{
			int nNumber = this.GetPlayBetLuckNumber();
			if(0 < nNumber)
			{	
				int nTheme = Configuration.getCurrentThemeType();
				if(nTheme == GameConstants.GAME_THEME_FLOWER)
					bitmap = ResourceHelper.GetFlowerIconBitmap(nNumber-1);
				else if(nTheme == GameConstants.GAME_THEME_FOOD)
	    			bitmap = ResourceHelper.GetFoodIconBitmap(nNumber-1);
				else
	    			bitmap = ResourceHelper.GetAnimalIconBitmap(nNumber-1);
					
					
				if(bitmap != null)
				{	
					RectF drawRect;
					
					int w = bitmap.getWidth();
					int h = bitmap.getHeight();
					Rect srcRect = new Rect(0, 0, w, h);
					float size = m_RepaintRect2.width()*0.60f;
		            float cx = m_RepaintRect2.centerX();
		            float cy = m_RepaintRect2.centerY();
					float left = cx-size/2.0f;
					float top = cy -size/2.0f;
					float right = left + size;
					float bottom = top + size;
					drawRect = new RectF(left, top, right, bottom); 
					canvas.drawBitmap(bitmap, srcRect, drawRect, null);
				}	
			
			}
		}	
	}

	
	private void DrawDsiableState(Canvas canvas)
	{
		Bitmap bitmap = ResourceHelper.GetCrossBitmap();
		if(bitmap != null)
		{	
			Paint paint = ResourceHelper.GetSemiTransparentPaint();
			canvas.drawBitmap(bitmap, m_SrcDisableImageRect, m_RepaintDisableRect, paint);
		}	
	}
	
	public void StartResultWinAnimation()
	{
		m_Parent.m_bBetViewResultAnimation = true;
		m_Parent.m_BetViewTimeCount = CApplicationController.GetSystemTimerClick();
		m_Parent.m_nBetViewAnimationFrame = 0;
		m_Parent.m_bBetViewDrawCashBackground = false;
		invalidate();
	}

	public void StopResultWinAnimation()
	{
		m_Parent.m_bBetViewResultAnimation = false;
		m_Parent.m_bBetViewDrawCashBackground = false;
		invalidate();
	}

	public boolean IsInResultWinAnimation()
	{
		return m_Parent.m_bBetViewResultAnimation;
	}
	
	public void OnTimerEvent()
	{
		if(m_Parent == null)
			return;
		
		if(m_Parent.m_bBetViewResultAnimation == true)
		{
			long currentTime = CApplicationController.GetSystemTimerClick();
			if(GameConstants.AVATAR_ANIMATION_TIMEINTERNVAL/6 <= (currentTime - m_Parent.m_BetViewTimeCount))
			{   
				m_Parent.m_BetViewTimeCount = currentTime;
				if(m_Parent.m_nBetViewAnimationFrame == 9 && m_Parent.m_bBetViewDrawCashBackground == false)
					m_Parent.m_bBetViewDrawCashBackground = true;
				m_Parent.m_nBetViewAnimationFrame = (1+m_Parent.m_nBetViewAnimationFrame)%10;
				invalidate();
			}
		}
	}
	
}
