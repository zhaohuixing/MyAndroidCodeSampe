package com.xgadget.SimpleGambleWheel;

import com.xgadget.uimodule.ResourceHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
//import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

public class GameCashBalanceView extends BasicLayoutView 
{
	private	RectF 		m_RepaintRect;
	private Rect		m_SrcImageRect;	

	private GamePlayer  m_Parent;
	private int			m_CurrentBalance = 1000;
	
	private boolean 	m_bPlayerAvatarView;
	private void Initialize()
	{
//		m_Parent = null;
		Bitmap bitmap = ResourceHelper.GetBalanceBitmap();
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		m_SrcImageRect = new Rect(0, 0, w, h);
		
		float size = (float)GameLayoutConstant.GetCurrentBalanceSignSize();
		m_RepaintRect = new RectF(0, 0, size, size);
		m_bPlayerAvatarView = false;
		m_Parent = null;
	}

	public void AttachParent(GamePlayer parent)
	{
		m_Parent = parent;
		m_bPlayerAvatarView = true;
		PostOnLayoutHandle();
	}
	
	public GameCashBalanceView(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public GameCashBalanceView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public GameCashBalanceView(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public void PostOnLayoutHandle()
	{
		if(m_bPlayerAvatarView == false)
		{	
			int size = GameLayoutConstant.GetCurrentBalanceSignSize();
			int nScreentWidth = GameLayoutConstant.GetCurrentScreenWidth();
			int x = nScreentWidth - size;
			if(m_LayoutContainer != null)
				m_LayoutContainer.SetChildNewDemension(this, size, size, x, 0);		
		
			m_RepaintRect = new RectF(0, 0, size, size);
		}
		else
		{
			int size = GameLayoutConstant.GetCurrentAvatarSize();
			int top = size;
			if(m_Parent != null)
			{
				int nSeatIndex = m_Parent.GetSeatID();
				if(nSeatIndex == 2 || nSeatIndex == 3)
				{
					top = 0;
				}
			}
			if(m_LayoutContainer != null)
				m_LayoutContainer.SetChildNewDemension(this, size, size, 0, top);		
		
			m_RepaintRect = new RectF(0, 0, size, size);
		}
		invalidate();
	}
	
	protected void onDraw(Canvas canvas)
	{
		DrawCashSignBackground(canvas);
		DrawCashNumber(canvas);
 	}
	
	private void DrawCashSignBackground(Canvas canvas)
	{
		Bitmap bitmap = ResourceHelper.GetBalanceBitmap();
		if(bitmap != null)
			canvas.drawBitmap(bitmap, m_SrcImageRect, m_RepaintRect, null);
	}

	private void DrawCashNumber(Canvas canvas)
	{
		Bitmap bitmap = ResourceHelper.GetYellowNumberBitmap(m_CurrentBalance);
		if(bitmap != null)
		{	
			RectF drawRect;
			
			int w = bitmap.getWidth();
			int h = bitmap.getHeight();
			Rect srcRect = new Rect(0, 0, w, h);
			float r = ((float)w)/((float)h);
			float c = getWidth()/2.0f;
			float dw = c*0.8f;
			if(1.0f < r)
			{
				float dh = dw/r;
				float left = c-dw*1.05f/2.0f;
				float top = c - dh*1.1f/2.0f;
				float right = left + dw;
				float bottom = top + dh;
				drawRect = new RectF(left, top, right, bottom); 
			}
			else
			{
				float dh = c*0.8f;
				dw = dh*r;
				float left = c-dw*1.05f/2.0f;
				float top = c - dh*1.1f/2.0f;
				float right = left + dw;
				float bottom = top + dh;
				drawRect = new RectF(left, top, right, bottom); 
			}
			canvas.drawBitmap(bitmap, srcRect, drawRect, null);
		}	
	}

	public void SetCashBalance(int number)
	{
		this.m_CurrentBalance = number;
		this.invalidate();
	}
	
	public void SetForPlayerAvatar()
	{
		m_bPlayerAvatarView = true;
	}
}
