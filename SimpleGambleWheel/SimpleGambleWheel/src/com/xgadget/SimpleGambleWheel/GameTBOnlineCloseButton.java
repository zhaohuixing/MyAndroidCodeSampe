package com.xgadget.SimpleGambleWheel;

import com.xgadget.uimodule.ResourceHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class GameTBOnlineCloseButton extends BasicLayoutView 
{
	private RectF 			m_RepaintRect;
	private int				m_Left;
	private int				m_Top;
	private Rect 			m_SrcImageRect;
	private int				m_nToolbarIndex;
	
	private void Initialize()
	{
		m_nToolbarIndex = 0;
		m_Left = 0;
		m_Top = 0;
		
		m_RepaintRect = new RectF(0, 0, GameLayoutConstant.m_nToolbarButtonMinSize, GameLayoutConstant.m_nToolbarButtonMinSize);
		m_SrcImageRect = new Rect(0, 0, ResourceHelper.GetToolbarButtonImageWidth(), ResourceHelper.GetToolbarButtonImageHeight());
	}
	
	public void SetToolbarIndex(int nIndex)
	{
		m_nToolbarIndex = nIndex;
		if(m_nToolbarIndex < 0)
			m_nToolbarIndex = 0;
		
		int size = GameLayoutConstant.GetCurrentToolbarHeight();
		m_Left = size*m_nToolbarIndex;
		
		if(m_LayoutContainer != null)
			m_LayoutContainer.SetChildNewDemension(this, size, size, m_Left, m_Top);		
		
		//int w = this.getWidth();
		//int h = this.getHeight();
		m_RepaintRect = new RectF(0, 0, size, size);	
	}
	
	public GameTBOnlineCloseButton(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public GameTBOnlineCloseButton(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public GameTBOnlineCloseButton(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public void PostOnLayoutHandle()
	{
		int size = GameLayoutConstant.GetCurrentToolbarHeight();

		m_Left = size*m_nToolbarIndex;
		
		int w = this.getWidth();
		int h = this.getHeight();
		m_RepaintRect = new RectF(0, 0, w, h);		
		
		if(m_LayoutContainer != null)
			m_LayoutContainer.SetChildNewDemension(this, size, size, m_Left, m_Top);		
		if(this.getVisibility() == View.VISIBLE)
			invalidate();
	}	

	protected void onDraw(Canvas canvas)
	{
		if(this.getVisibility() != View.VISIBLE)
			return;
		
		Bitmap bitmap = ResourceHelper.GetOnlineOffButtonBitmap();
		if(bitmap != null)
			canvas.drawBitmap(bitmap, m_SrcImageRect, m_RepaintRect, null);
 	}
}
