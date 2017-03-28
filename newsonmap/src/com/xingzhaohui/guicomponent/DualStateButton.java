package com.xingzhaohui.guicomponent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class DualStateButton extends Button 
{
	protected Bitmap							m_State1Image;
	protected Bitmap							m_State2Image;
    protected boolean							m_IsState2;
	protected IDualStateButtonDelegate			m_Delegate;
    
	protected void HandleButtonClicked()
	{
		if(m_Delegate != null)
		{
			if(m_IsState2 == false)
			{
				m_Delegate.OnStateOneClick();
			}
			else
			{
				m_Delegate.OnStateTwoClick();
			}
		}
	}
	
	
	protected void Initialize()
	{
		this.setBackgroundColor(Color.TRANSPARENT);
		m_State1Image = null;
		m_State2Image = null;
		m_IsState2 = false;
		m_Delegate = null;
		
		this.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				HandleButtonClicked();
			}
		});
	}
	
	public DualStateButton(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public DualStateButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public DualStateButton(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public void SetDelegate(IDualStateButtonDelegate	delegate)
	{
		m_Delegate = delegate;
	}
	
	public void SetBitmap(Bitmap bitmap1, Bitmap bitmap2)
	{
		m_State1Image = bitmap1;
		m_State2Image = bitmap2;
		this.invalidate();
	}
	
	public void SetStateOne()
	{
		m_IsState2 = false;
		this.invalidate();
	}
	
	public void SetStateTwo()
	{
		m_IsState2 = true;
		this.invalidate();
	}

	public boolean IsStateOne()
	{
		boolean bRet = (m_IsState2 == false);
		return bRet;
	}

	public boolean IsStateTwo()
	{
		boolean bRet = (m_IsState2 == true);
		return bRet;
	}

	protected void DrawStateOne(Canvas canvas)
	{
		if(m_State1Image != null)
		{
			int w = getWidth();
			int h = getHeight();
			RectF repaintRect = new RectF(0.0f, 0.0f, ((float)w), ((float)h));
			
			w = m_State1Image.getWidth();
			h = m_State1Image.getHeight();
			Rect imageRect = new Rect(0, 0, w, h);
			
			canvas.drawBitmap(m_State1Image, imageRect, repaintRect, null);
		}
	}
	
	protected void DrawStateTwo(Canvas canvas)
	{
		if(m_State2Image != null)
		{
			int w = getWidth();
			int h = getHeight();
			RectF repaintRect = new RectF(0.0f, 0.0f, ((float)w), ((float)h));
			
			w = m_State2Image.getWidth();
			h = m_State2Image.getHeight();
			Rect imageRect = new Rect(0, 0, w, h);
			
			canvas.drawBitmap(m_State2Image, imageRect, repaintRect, null);
		}
	}
	
	protected void onDraw(Canvas canvas)
	{
		if(m_IsState2 == false)
		{
			this.DrawStateOne(canvas);
		}
		else 
		{
			this.DrawStateTwo(canvas);
		}
 	}


	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) 
	{
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		this.invalidate();
	}
	
}
