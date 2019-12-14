package com.xgadget.uimodule;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

public class GlossyBitmapButton extends BasicLayoutView 
{
	protected RectF 			m_RepaintRect;
	protected Rect 				m_SrcImageRect;
	protected Bitmap			m_SrcImage;
	private int					m_Size;
	
	public final static int			m_nDefaultButtonSize = 80;
	
	private  int				m_Left;
	private  int				m_Top;
	
	
	public void SetBitmap(Bitmap bitmap)
	{
		m_SrcImage = bitmap;
		if(m_SrcImage != null)
		{	
			int w = m_SrcImage.getWidth();
			int h = m_SrcImage.getHeight();
			m_SrcImageRect = new Rect(0, 0, w, h);
		}
	}
	
	public void SetLeftTop(int nLeft, int nTop)
	{
		m_Left = nLeft;
		m_Top = nTop;
	}
	
	protected void Initialize()
	{
		m_SrcImage = null;
		m_Size = m_nDefaultButtonSize;
		m_Left = 0;
		m_Top = 0;

		this.setBackgroundColor(Color.TRANSPARENT);
	}

	
	public GlossyBitmapButton(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public GlossyBitmapButton(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public GlossyBitmapButton(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public void PostOnLayoutHandle()
	{
		if(m_LayoutContainer != null)
			m_LayoutContainer.SetChildNewDemension(this, m_Size, m_Size, m_Left, m_Top);		

		m_RepaintRect = new RectF(0, 0, m_Size, m_Size);		
		
		invalidate();
	}	
	
	protected void onDraw(Canvas canvas)
	{
		if(m_SrcImage != null)
		{
			//int w = getWidth();
			//int h = getHeight();
			//m_RepaintRect = new RectF(0.0f, 0.0f, ((float)w), ((float)h));
			canvas.drawBitmap(m_SrcImage, m_SrcImageRect, m_RepaintRect, null);
		}
 	}
	
}
