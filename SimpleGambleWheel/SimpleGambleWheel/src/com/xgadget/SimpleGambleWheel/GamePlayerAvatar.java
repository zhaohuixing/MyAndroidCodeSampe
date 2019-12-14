package com.xgadget.SimpleGambleWheel;

import com.xgadget.uimodule.ResourceHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

public class GamePlayerAvatar extends BasicLayoutView 
{
	private	RectF 		m_RepaintRect;
	private Rect		m_SrcImageRect;	
	
	private GamePlayer		m_Parent;
	
	private void Initialize()
	{
		m_Parent = null;
	}
	
	public GamePlayerAvatar(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public GamePlayerAvatar(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public GamePlayerAvatar(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize();
	}
	
	public void AttachParent(GamePlayer parent)
	{
		m_Parent = parent;
	}

	public void PostOnLayoutHandle()
	{
		int size = GameLayoutConstant.GetCurrentAvatarSize();
		int nSeatIndex = GetSeatID();
		int top = 0;
		if(nSeatIndex == 0)
		{
			top = 0;
		}
		else if(nSeatIndex == 1)
		{
			top = 0;
		}
		else if(nSeatIndex == 2)
		{
			top = size/2;
		}
		else if(nSeatIndex == 3)
		{
			top = size/2;
		}

		if(m_LayoutContainer != null)
			m_LayoutContainer.SetChildNewDemension(this, size, size, 0, top);		
		
		invalidate();
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
	
	public boolean IsEnable()
	{
		boolean bRet = false;
		if(m_Parent != null)
		{	
			bRet = m_Parent.IsEnabled();
		}	
		return bRet;
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
	
	private void CalculateRenderParameters(Bitmap bitmap)
	{
		if(bitmap == null)
		{
			return;
		}
		int size = GameLayoutConstant.GetCurrentAvatarSize();
		int imgwidth = bitmap.getWidth();
		int imgheight = bitmap.getHeight();
		if(imgheight == 0 || imgwidth == 0)
		{
			m_RepaintRect = new RectF(0, 0, size, size);
			m_SrcImageRect = new Rect(0, 0, size, size);
			return;
		}
		float ratio = ((float)(imgwidth))/((float)(imgheight));
		m_SrcImageRect = new Rect(0, 0, imgwidth, imgheight);
		if(1.0f <= ratio)
		{
			float w = (float)size;
			float h = ((float)size)/ratio;
			float y = (size - h)/2.0f;
			m_RepaintRect = new RectF(0, y, w, y+h);
		}
		else
		{
			float h = (float)size;
			float w = ((float)size)*ratio;
			float x = (size - w)/2.0f;
			m_RepaintRect = new RectF(x, 0, w+x, h);
		}
	}
	
	private Bitmap GetCurremtAvatarRunBitmap()
	{
		Bitmap bitmap = null;
		
		int nIndex = m_Parent.m_nAvatarAnimationFrame;
		if(3 <= nIndex)
			nIndex = 1;
		
		if(IsMySelf() == true)
		{
			if(this.IsMyTurn() == true)
				bitmap = ResourceHelper.GetMyPlayBitmap(nIndex);
			else 
				bitmap = ResourceHelper.GetMyIdleBitmap(nIndex);
		}
		else
		{
			if(this.IsMyTurn() == true)
				bitmap = ResourceHelper.GetRoPaPlayBitmap(nIndex);
			else
				bitmap = ResourceHelper.GetRoPaIdleBitmap(nIndex);
		}
		
		CalculateRenderParameters(bitmap);
		
		return bitmap;
	}
	
	private Bitmap GetCurremtAvatarResultBitmap()
	{
		Bitmap bitmap = null;

		int nIndex = m_Parent.m_nAvatarAnimationFrame;
		if(3 <= nIndex)
			nIndex = 1;
		
		if(IsMySelf() == true)
		{
			if(WinThisTime() == true)
				bitmap = ResourceHelper.GetMyWinBitmap(nIndex);
			else
				bitmap = ResourceHelper.GetMyCryBitmap(nIndex);
		}
		else
		{
			if(WinThisTime() == true)
				bitmap = ResourceHelper.GetRoPaWinBitmap(nIndex);
			else
				bitmap = ResourceHelper.GetRoPaCryBitmap(nIndex);
		}

		CalculateRenderParameters(bitmap);
		
		return bitmap;
	}
	
	private Bitmap GetCurremtAvatarIdleBitmap()
	{
		Bitmap bitmap = null;
		
		int nIndex = m_Parent.m_nAvatarAnimationFrame;
		if(3 <= nIndex)
			nIndex = 1;
		
		if(IsMySelf() == true)
		{
			bitmap = ResourceHelper.GetMyIdleBitmap(nIndex);
		}
		else
		{
			bitmap = ResourceHelper.GetRoPaIdleBitmap(nIndex);
		}

		CalculateRenderParameters(bitmap);
		
		return bitmap;
	}
	
	private Bitmap GetCurrentAvatarImage()
	{
		Bitmap bitmap = null;

		int nGameState = SimpleGambleWheel.m_ApplicationController.GetGameState();
		if(nGameState == GameConstants.GAME_STATE_RUN)
		{
			bitmap = GetCurremtAvatarRunBitmap();
		}
		else if(nGameState == GameConstants.GAME_STATE_RESULT)
		{
			bitmap = GetCurremtAvatarResultBitmap();
		}
		else
		{
			bitmap = GetCurremtAvatarIdleBitmap();
		}
		
		return bitmap;
	}

	private void DrawEnableAnimation(Canvas canvas)
	{
		Bitmap bitmap = GetCurrentAvatarImage();
		if(bitmap != null)
			canvas.drawBitmap(bitmap, m_SrcImageRect, m_RepaintRect, null);
	}
	
	private void DrawDisableAnimation(Canvas canvas)
	{
		Bitmap bitmap = null;

		int nIndex = m_Parent.m_nAvatarAnimationFrame;
		if(3 <= nIndex)
			nIndex = 1;
		
		if(IsMySelf() == true)
		{
			bitmap = ResourceHelper.GetMyCryBitmap(nIndex);
		}
		else
		{
			bitmap = ResourceHelper.GetRoPaCryBitmap(nIndex);
		}

		CalculateRenderParameters(bitmap);

		Paint paint = ResourceHelper.GetSemiTransparentPaint();
		if(bitmap != null)
			canvas.drawBitmap(bitmap, m_SrcImageRect, m_RepaintRect, paint);
	}
	
	protected void onDraw(Canvas canvas)
	{
		if(m_Parent == null)
			return;
		
		if(SimpleGambleWheel.m_ApplicationController.m_GameController.IsOnline() == true && m_Parent.IsOnlinePlayer() == false)
			return;
		
		if(IsEnable() == true)
		{
			DrawEnableAnimation(canvas);
		}
		else
		{
			DrawDisableAnimation(canvas);
		}
 	}
	
	public void OnTimerEvent()
	{
		if(m_Parent == null)
			return;
		
		long currentTime = CApplicationController.GetSystemTimerClick();
		if(GameConstants.AVATAR_ANIMATION_TIMEINTERNVAL <= (currentTime - m_Parent.m_AvatarTimeCount))
		{   
			m_Parent.m_AvatarTimeCount = currentTime;
			m_Parent.m_nAvatarAnimationFrame = (1+m_Parent.m_nAvatarAnimationFrame)%4;
			invalidate();
		}    
	}
}
