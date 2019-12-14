package com.xgadget.SimpleGambleWheel;

import com.xgadget.uimodule.ResourceHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.LinearLayout;
//import android.widget.TextView;

public class GameConfigureHeadGadget extends View 
{
	private RectF 			m_HeadRepaintRect;
	private Rect 			m_HeadSrcImageRect;
	private RectF 			m_SignRepaintRect;
	private Rect 			m_SignSrcImageRect;
	private boolean			m_bMyself;
	private boolean			m_bEnable;
	private int				m_CurrentBalance = 1000;
	
	private void Initialize(Context context)
	{
		this.setBackgroundColor(Color.TRANSPARENT);
		Bitmap bitmap = ResourceHelper.GetMyBetEnableHeadBitmap();
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		m_HeadSrcImageRect = new Rect(0, 0, w, h);
		w = getWidth();
		h = getHeight();
		m_HeadRepaintRect = new RectF(0, 0, w, h-w);

		bitmap = ResourceHelper.GetBalanceBitmap();
		w = bitmap.getWidth();
		h = bitmap.getHeight();
		m_SignSrcImageRect = new Rect(0, 0, w, h);
		w = getWidth();
		h = getHeight();
		m_SignRepaintRect = new RectF(0, h-w, w, h);
		
		m_bMyself = true;
		m_bEnable = true;
	}
	
    protected void onLayout(boolean changed, int l, int t, int r, int b) 
    {
    	//??????
    	super.onLayout(changed, l, t, r, b);
		float w = (float)getWidth();
		float h = (float)getHeight();
		m_HeadRepaintRect = new RectF(0.0f, 0.0f, w, h-w);
		m_SignRepaintRect = new RectF(0.0f, h-w, w, h);
		invalidate();
    }

	public GameConfigureHeadGadget(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public GameConfigureHeadGadget(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public GameConfigureHeadGadget(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public void SetAsMyself(boolean bMyself)
	{
		m_bMyself = bMyself;
		invalidate();
	}

	public void SetEnable(boolean bEnable)
	{
		m_bEnable = bEnable;
		invalidate();
	}

	public void SetBalance(int nChips)
	{
		m_CurrentBalance = nChips;
		invalidate();
	}
	protected void DrawMyself(Canvas canvas)
	{
		Bitmap bitmap = null;
		if(m_bEnable == true)
			bitmap = ResourceHelper.GetMyBetEnableHeadBitmap();
		else
			bitmap = ResourceHelper.GetMyBetDisableHeadBitmap();
		
		if(bitmap != null)
		{
			canvas.drawBitmap(bitmap, m_HeadSrcImageRect, m_HeadRepaintRect, null);
		}
	}
	
	protected void DrawRoPa(Canvas canvas)
	{
		Bitmap bitmap = null;
		if(m_bEnable == true)
			bitmap = ResourceHelper.GetRoPaBetEnableHeadBitmap();
		else
			bitmap = ResourceHelper.GetRoPaBetDisableHeadBitmap();
		
		if(bitmap != null)
		{
			canvas.drawBitmap(bitmap, m_HeadSrcImageRect, m_HeadRepaintRect, null);
		}
	}
	
	protected void DrawCashBalance(Canvas canvas)
	{
		Bitmap bitmap = ResourceHelper.GetBalanceBitmap();
		if(bitmap != null)
			canvas.drawBitmap(bitmap, m_SignSrcImageRect, m_SignRepaintRect, null);
	}
	
	private void DrawCashNumber(Canvas canvas)
	{
		Bitmap bitmap = ResourceHelper.GetYellowNumberBitmap(m_CurrentBalance);
		if(bitmap != null)
		{	
			RectF drawRect;
			float hh = m_HeadRepaintRect.height();
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
				float top = c - dh*1.1f/2.0f + hh;
				float right = left + dw;
				float bottom = top + dh;
				drawRect = new RectF(left, top, right, bottom); 
			}
			else
			{
				float dh = c*0.8f;
				dw = dh*r;
				float left = c-dw*1.05f/2.0f;
				float top = c - dh*1.1f/2.0f + hh;
				float right = left + dw;
				float bottom = top + dh;
				drawRect = new RectF(left, top, right, bottom); 
			}
			canvas.drawBitmap(bitmap, srcRect, drawRect, null);
		}	
	}
	
	
	protected void onDraw(Canvas canvas)
	{
		if(m_bMyself == true)
			DrawMyself(canvas);
		else
			DrawRoPa(canvas);
		
		DrawCashBalance(canvas);
		DrawCashNumber(canvas);
 	}
	
}
