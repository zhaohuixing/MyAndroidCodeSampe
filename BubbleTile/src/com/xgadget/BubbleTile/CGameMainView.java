package com.xgadget.BubbleTile;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PointF;
import android.graphics.Rect;

import android.graphics.RectF;
import android.graphics.Shader;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Bitmap;
import android.view.InputEvent;
import android.view.MotionEvent;

public class CGameMainView extends View 
{
	//GameView m_gameView;
    private final Shader 		m_WoodTexture;
    private final Paint 		m_BkPaint;
    private final DrawFilter 	m_DrawFilter;
	
	PlayBoard playboard;
	public CGameMainView(Context context) 
	{
		super(context);
		this.Initialze();
		m_WoodTexture = new BitmapShader(CResourceHelper.GetWoodTextureBitmap(), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
		m_BkPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
		m_DrawFilter = new PaintFlagsDrawFilter(Paint.FILTER_BITMAP_FLAG | Paint.DITHER_FLAG, 0);
	}

	public CGameMainView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.Initialze();
		m_WoodTexture = new BitmapShader(CResourceHelper.GetWoodTextureBitmap(), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
		m_BkPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
		m_DrawFilter = new PaintFlagsDrawFilter(Paint.FILTER_BITMAP_FLAG | Paint.DITHER_FLAG, 0);
	}
	
	
	private void Initialze()
	{
		playboard = new PlayBoard(this);
		
	}
	
	public boolean onTouchEvent(MotionEvent event)
	{
		ArrayList<PointF> points = new ArrayList<PointF>();
		for (int i = 0; i <event.getPointerCount(); i++)
		{
			PointF pt = new PointF(event.getX(i), event.getY(i));
			points.add(pt);
		}
		
		if (event.getActionMasked() == MotionEvent.ACTION_DOWN)
		{
			playboard.touchesBegan(points);
		}
		else if (event.getActionMasked() == MotionEvent.ACTION_MOVE)
		{
			playboard.touchesMoved(points);
		}
		else if (event.getActionMasked() == MotionEvent.ACTION_UP)
		{
			playboard.touchesEnded(points);
		}
		else if (event.getActionMasked() == MotionEvent.ACTION_CANCEL)
		{
			playboard.touchesCancelled(points);
		}
		return false;
	}
	
	protected void  onLayout(boolean changed, int left, int top, int right, int bottom)
	{
		super.onLayout(changed, left, top, right, bottom);

		int w = right - left;
		int h = bottom - top;
		CGameLayout.SetScreenSize(w, h); 
		invalidate();
	}
	
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		CGameLayout.SetScreenSize(w, h); 
		invalidate();
	}
	
	protected void onDraw(Canvas canvas)
	{
/*		CBubblePainter bubblePaint = new CBubblePainter(); 
		bubblePaint.SetBubbleType(CBubblePainter.BUBBLE_TYPE_COLOR);
		bubblePaint.SetNumber(20);
		bubblePaint.DrawBubble(canvas, new Rect(210, 10, 290, 90));

		bubblePaint.SetBubbleType(CBubblePainter.BUBBLE_TYPE_STAR);
		bubblePaint.SetNumber(18);
		bubblePaint.DrawBubble(canvas, new Rect(210, 100, 290, 180));

		bubblePaint.SetBubbleType(CBubblePainter.BUBBLE_TYPE_FROG);
		bubblePaint.SetNumber(3);
		bubblePaint.DrawBubble(canvas, new Rect(210, 190, 290, 270));*/

        canvas.setDrawFilter(m_DrawFilter);
        m_BkPaint.setShader(m_WoodTexture);
        canvas.drawPaint(m_BkPaint);
        
		RectF rc = new RectF(10,10,300,300);
		playboard.drawRect(canvas, rc);

	}
	

}
