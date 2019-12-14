package com.xgadget.uimodule;

import com.xgadget.uimodule.ResourceHelper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CustomDialogBackground extends View 
{
	private	RectF 						m_RepaintRect;
    RectF								m_EdgeRect;
    Paint								m_EdgePaint;

	private void Initialize()
	{
		this.setBackgroundColor(17170445);
		m_RepaintRect = new RectF(0, 0, 300.0f, 250);

		m_EdgePaint = new Paint();
		m_EdgePaint.setColor(Color.GREEN);
		m_EdgePaint.setStrokeWidth(8);        
		m_EdgePaint.setStyle(Paint.Style.STROKE);  
		//m_EdgeRect = new RectF(4, 4, 196, 96);
		m_EdgeRect = new RectF(7, 7, 293, 243);
	}
    
	public CustomDialogBackground(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public CustomDialogBackground(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public CustomDialogBackground(Context context, AttributeSet attrs,
			int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) 
	{
		// TODO Auto-generated method stub
		this.drawCutsomizedBackground(canvas);
	}

	private void drawCutsomizedBackground(Canvas canvas)
	{
		int width = this.getWidth();
		int height = this.getHeight();
		m_RepaintRect = new RectF(0, 0, width, height);

		m_EdgeRect = new RectF(7, 7, width-7, height-7);
        canvas.drawRoundRect(m_RepaintRect, 14.0f, 14.0f, ResourceHelper.GetBrownTexturePaint());
        canvas.drawRoundRect(m_EdgeRect, 7.0f, 7.0f, m_EdgePaint);

	}
}
