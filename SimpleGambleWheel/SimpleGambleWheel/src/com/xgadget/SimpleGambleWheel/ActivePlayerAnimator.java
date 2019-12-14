package com.xgadget.SimpleGambleWheel;

import com.xgadget.uimodule.ResourceHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class ActivePlayerAnimator extends BasicLayoutView 
{
	static private long		m_TimeCount; 
    static private int		m_nStepAngle;
    static private boolean  m_bInitialized = false;
    
	private	RectF 		m_RepaintRect;
	private Rect		m_SrcImageRect;	
    
	private void Initialize()
	{
		this.setBackgroundColor(17170445);
		if(ActivePlayerAnimator.m_bInitialized == false)
		{	
			ActivePlayerAnimator.m_TimeCount = CApplicationController.GetSystemTimerClick();
			ActivePlayerAnimator.m_nStepAngle = 0;
			ActivePlayerAnimator.m_bInitialized = true;
		}	
		Bitmap bitmap = ResourceHelper.GetRedStarBitmap();
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		m_SrcImageRect = new Rect(0, 0, w, h);
		m_RepaintRect = new RectF(0, 0, w, h);
	}
	
	public ActivePlayerAnimator(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public ActivePlayerAnimator(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public ActivePlayerAnimator(Context context, AttributeSet attrs,
			int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public void PostOnLayoutHandle()
	{
		MoveToActivePlayerPosition();
	}
	
	private void HandleProtraitLayoutChange()
	{
		int nActiveSeat = SimpleGambleWheel.m_ApplicationController.m_GameController.GetCurrentActivePlayingSeat();
		if(nActiveSeat < 0)
		{
			this.setVisibility(View.GONE);
			return;
		}
		else
		{
			this.setVisibility(View.VISIBLE);
		}
		//?????
		int nSize = GameLayoutConstant.GetActivePlayerIndicatorSize();
		int screenWidth = GameLayoutConstant.GetCurrentScreenWidth();
		int screenHeight = GameLayoutConstant.GetCurrentContentViewHeight();
		int screenCX = screenWidth/2;
		int compassRadium = GameLayoutConstant.GetCurrentCompassWheelSize()/2;
		int xOffset = GameLayoutConstant.m_nAvatarDisplayProtraitHorizontalMarginMin;
		int yOffset = GameLayoutConstant.GetCurrentCashBalanceSignSize()+GameLayoutConstant.m_nAvatarDisplayTopOffsetToDecoration;
		int yBottomOffset = GameLayoutConstant.m_nAvatarDisplayTopOffsetToDecoration;
		int AvatarSize = GameLayoutConstant.GetCurrentAvatarSize();
		int PlayerHeight = (int)(((float)AvatarSize)*1.5f);
		int left = xOffset + (AvatarSize-nSize)/2;   
		int top = yOffset+PlayerHeight;
		if(nActiveSeat == 1)
		{
			left = screenWidth - xOffset - AvatarSize + (AvatarSize-nSize)/2;
		}
		else if(nActiveSeat == 2)
		{
			left = screenWidth - xOffset - AvatarSize + (AvatarSize-nSize)/2;
			top = screenHeight - yBottomOffset - PlayerHeight-nSize; 
		}
		else if(nActiveSeat == 3)
		{
			top = screenHeight - yBottomOffset - PlayerHeight-nSize; 
		}
		
		if(nActiveSeat == 0 || nActiveSeat == 3)
		{
			if((screenCX - compassRadium) < (left + nSize))
			{
				left = xOffset;
			}
		}
		else
		{
			if(left < (screenCX + compassRadium))
			{
				left = screenWidth - xOffset - nSize;
			}
		}
		if(m_LayoutContainer != null)
			m_LayoutContainer.SetChildNewDemension(this, nSize, nSize, left, top);		
		
		m_RepaintRect = new RectF(0, 0, nSize, nSize); 
	}
	
	private void HandleLandscapeLayoutChange()
	{
		int nActiveSeat = SimpleGambleWheel.m_ApplicationController.m_GameController.GetCurrentActivePlayingSeat();
		if(nActiveSeat < 0)
		{
			this.setVisibility(View.GONE);
			return;
		}
		else
		{
			this.setVisibility(View.VISIBLE);
		}
		//?????
		int nSize = GameLayoutConstant.GetActivePlayerIndicatorSize();
		int screenWidth = GameLayoutConstant.GetCurrentScreenWidth();
		int screenHeight = GameLayoutConstant.GetCurrentContentViewHeight();
		int compassSize = GameLayoutConstant.GetCurrentCompassWheelSize()/2;
		int AvatarSize = GameLayoutConstant.GetCurrentAvatarSize();
		int PlayerHeight = (int)(((float)AvatarSize)*1.5f);
		int cx = screenWidth/2;
		int yOffset = GameLayoutConstant.m_nAvatarDisplayLandscapeVerticalMarginMin;
		int yBottomOffset = yOffset;
		int left = cx-compassSize-AvatarSize + (AvatarSize-nSize)/2;   
		int top = yOffset + PlayerHeight;
		if(nActiveSeat == 1)
		{
			left = cx+compassSize + (AvatarSize-nSize)/2; 
		}
		else if(nActiveSeat == 2)
		{
			left = cx+compassSize + (AvatarSize-nSize)/2;
			top = screenHeight - yBottomOffset - PlayerHeight - nSize; 
		}
		else if(nActiveSeat == 3)
		{
			top = screenHeight - yBottomOffset - PlayerHeight - nSize; 
		}
		if(m_LayoutContainer != null)
			m_LayoutContainer.SetChildNewDemension(this, nSize, nSize, left, top);		
		
		m_RepaintRect = new RectF(0, 0, nSize, nSize); 
	}
	
	public void MoveToActivePlayerPosition()
	{
		int screenWidth = GameLayoutConstant.GetCurrentScreenWidth();
		int screenHeight = GameLayoutConstant.GetCurrentContentViewHeight();
		if(screenWidth < screenHeight)
		{
			HandleProtraitLayoutChange();
		}
		else
		{
			HandleLandscapeLayoutChange();
		}
		invalidate();
		
	}
	public void OnTimerEvent()
	{
		if(this.getVisibility() == View.GONE)
			return;
		
		long currentTime = CApplicationController.GetSystemTimerClick();
		long nStep = GameConstants.AVATAR_ANIMATION_TIMEINTERNVAL/10;
		
		if(nStep <= (currentTime - ActivePlayerAnimator.m_TimeCount))
		{   
			ActivePlayerAnimator.m_TimeCount = currentTime;
			ActivePlayerAnimator.m_nStepAngle = (ActivePlayerAnimator.m_nStepAngle + 10)%360;
			invalidate();
		}
	}

	protected void onDraw(Canvas canvas)
	{
		if(this.getVisibility() == View.GONE)
			return;
		
		DrawAnimation(canvas);
 	}	
	
	private void DrawAnimation(Canvas canvas)
	{
		Bitmap bitmap =  ResourceHelper.GetRedStarBitmap();
		if(bitmap != null)
		{
			int cx = getWidth()/2;
			int cy = getHeight()/2;
			canvas.save();
			canvas.rotate(ActivePlayerAnimator.m_nStepAngle, cx, cy);
			canvas.drawBitmap(bitmap, m_SrcImageRect, m_RepaintRect, null);
			canvas.restore();
		}
		
	}
}
