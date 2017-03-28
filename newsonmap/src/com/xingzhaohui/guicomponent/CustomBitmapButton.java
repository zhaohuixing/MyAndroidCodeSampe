package com.xingzhaohui.guicomponent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.Button;

public class CustomBitmapButton extends Button 
{
	protected RectF 			m_RepaintRect;
	protected Rect 				m_SrcImageRect;
	protected Bitmap			m_SrcImage;
	
	public void SetBitmap(Bitmap bitmap)
	{
		if(bitmap != null)
		{	
			m_SrcImage = bitmap; //ResourceHelper.GetPrevButtonBitmap()
			int w = bitmap.getWidth();
			int h = bitmap.getHeight();
			m_SrcImageRect = new Rect(0, 0, w, h);
		}	
	}
	
	protected void Initialize()
	{
		m_SrcImage = null;
		
		this.setBackgroundColor(Color.TRANSPARENT);
	}

	
	public CustomBitmapButton(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public CustomBitmapButton(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public CustomBitmapButton(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	protected void onDraw(Canvas canvas)
	{
		if(m_SrcImage != null)
		{
			int w = getWidth();
			int h = getHeight();
			m_RepaintRect = new RectF(0.0f, 0.0f, ((float)w), ((float)h));
			canvas.drawBitmap(m_SrcImage, m_SrcImageRect, m_RepaintRect, null);
		}
 	}
	
}
