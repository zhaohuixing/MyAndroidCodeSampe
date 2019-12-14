package com.xgadget.uimodule;

import com.xgadget.uimodule.ResourceHelper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CDumyGreenRoundRectView extends View 
{
    public float m_Width;
    public float m_Height;
  
    
	private	RectF 		m_RepaintRect;
    RectF								m_EdgeRect;
    Paint								m_EdgePaint;
	
	private void Initialize()
	{
		this.setBackgroundColor(17170445);
		m_Width = 200.0f;
		m_Height = 100.0f;
		m_RepaintRect = new RectF(0, 0, m_Width, m_Height);

		m_EdgePaint = new Paint();
		m_EdgePaint.setColor(Color.RED);
		m_EdgePaint.setStrokeWidth(6);        
		m_EdgePaint.setStyle(Paint.Style.STROKE);  
		//m_EdgeRect = new RectF(4, 4, 196, 96);
		m_EdgeRect = new RectF(5, 5, 195, 95);
	}
    
	public CDumyGreenRoundRectView(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public CDumyGreenRoundRectView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public CDumyGreenRoundRectView(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	protected void drawDumyBackground(Canvas canvas)
	{
		m_Width = this.getWidth();
		m_Height = this.getHeight();
		m_RepaintRect = new RectF(0, 0, m_Width, m_Height);

		m_EdgeRect = new RectF(5, 5, m_Width-5, m_Height-5);
        canvas.drawRoundRect(m_RepaintRect, 10.0f, 10.0f, ResourceHelper.GetGreenTexturePaint());
        canvas.drawRoundRect(m_EdgeRect, 6.0f, 6.0f, m_EdgePaint);
	}
	
	protected void onDraw(Canvas canvas)
	{
		drawDumyBackground(canvas);
	}
	
	public int GetEdgeSize()
	{
		return 5;
	}
}
