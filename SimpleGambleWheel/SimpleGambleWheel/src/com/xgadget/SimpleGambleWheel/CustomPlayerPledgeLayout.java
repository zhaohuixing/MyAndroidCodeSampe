package com.xgadget.SimpleGambleWheel;

import com.xgadget.uimodule.ResourceHelper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class CustomPlayerPledgeLayout extends RelativeLayout 
{
    Rect									m_RepaintRect;
    
    
	private void Initialize()
	{
		int w = this.getWidth();
		int h = this.getHeight();
		m_RepaintRect = new Rect(0, 0, w, h);
	}

	public CustomPlayerPledgeLayout(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public CustomPlayerPledgeLayout(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public CustomPlayerPledgeLayout(Context context, AttributeSet attrs,
			int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize();
	}
	protected void onDraw(Canvas canvas)
	{
		int w = this.getWidth();
		int h = this.getHeight();
		m_RepaintRect = new Rect(0, 0, w, h);
        canvas.drawRect(m_RepaintRect, ResourceHelper.GetRedTexturePaint());
	}

}
