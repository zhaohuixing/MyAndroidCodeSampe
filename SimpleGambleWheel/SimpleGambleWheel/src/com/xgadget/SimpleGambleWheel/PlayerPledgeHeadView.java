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

public class PlayerPledgeHeadView extends View 
{
	private RectF 			m_RepaintRect;
	private Rect 			m_SrcImageRect;
	private boolean			m_bMyself;
	private boolean			m_bEnable;
	
	
	private void Initialize()
	{
		Bitmap bitmap = ResourceHelper.GetMyBetEnableHeadBitmap();
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		m_SrcImageRect = new Rect(0, 0, w, h);
		this.setBackgroundColor(Color.TRANSPARENT);
		w = getWidth();
		h = getHeight();
		m_RepaintRect = new RectF(0, 0, w, h);
		m_bMyself = true;
		m_bEnable = true;
	}

	public void SetPlayerAsMySelf(boolean bMyself)
	{
		m_bMyself = bMyself;
		invalidate();
	}

	public void SetPlayerEnable(boolean bEnable)
	{
		m_bEnable = bEnable;
		invalidate();
	}
	
	public PlayerPledgeHeadView(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public PlayerPledgeHeadView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public PlayerPledgeHeadView(Context context, AttributeSet attrs,
			int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize();
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

	protected void DrawMyself(Canvas canvas)
	{
		Bitmap bitmap = null;
		if(m_bEnable == true)
			bitmap = ResourceHelper.GetMyBetEnableHeadBitmap();
		else
			bitmap = ResourceHelper.GetMyBetDisableHeadBitmap();
		
		if(bitmap != null)
		{
			int w = getWidth();
			int h = getHeight();
			m_RepaintRect = new RectF(0.0f, 0.0f, ((float)w), ((float)h));
			canvas.drawBitmap(bitmap, m_SrcImageRect, m_RepaintRect, null);
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
			int w = getWidth();
			int h = getHeight();
			m_RepaintRect = new RectF(0.0f, 0.0f, ((float)w), ((float)h));
			canvas.drawBitmap(bitmap, m_SrcImageRect, m_RepaintRect, null);
		}
	}
	
	protected void onDraw(Canvas canvas)
	{
		if(m_bMyself == true)
			DrawMyself(canvas);
		else
			DrawRoPa(canvas);
 	}
	

}
