package com.xingzhaohui.newsonmap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MainBKView extends View 
{
	private void Initialize(Context context)
	{
		this.setClickable(true);
	}
	
	public MainBKView(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		this.Initialize(context);
	}

	public MainBKView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.Initialize(context);
	}

	public MainBKView(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.Initialize(context);
	}

	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) 
	{
		// TODO Auto-generated method stub
		Paint m_Paint = new Paint();
		m_Paint.setAntiAlias(true);
		m_Paint.setARGB(255, 255, 32, 32);
		m_Paint.setStrokeWidth(40);
		
		float w = this.getWidth();
		float h = this.getHeight();
		RectF rect = new RectF(0, 0, w, h);
		
		//canvas.drawRect(rect, m_Paint);
		canvas.drawOval(rect, m_Paint);
	}

	/* (non-Javadoc)
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		NOMMainActivity.m_ApplicationController.OnClickEvent(this);
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}

	//@Override
	//protected void onClick(View v) 
    //{
    //	NOMMainActivity.m_ApplicationController.OnClickEvent(v);
    //}
	
	
}
