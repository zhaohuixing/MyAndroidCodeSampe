package com.xgadget.SimpleGambleWheel;

import com.xgadget.uimodule.ResourceHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

public class GameTBSystemButton extends BasicLayoutView 
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
		
		int size = GameLayoutConstant.GetCurrentToolbarHeight();
		
		m_RepaintRect = new RectF(0, 0, size, size);
		m_SrcImageRect = new Rect(0, 0, ResourceHelper.GetToolbarButtonImageWidth(), ResourceHelper.GetToolbarButtonImageHeight());
		m_Left = GameLayoutConstant.m_nToolbarButtonMinSize;
		m_Top = 0;
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
		int w = this.getWidth();
		int h = this.getHeight();
		m_RepaintRect = new RectF(0, 0, w, h);	
	}
	
	public GameTBSystemButton(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public GameTBSystemButton(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public GameTBSystemButton(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public void PostOnLayoutHandle()
	{
		int size = GameLayoutConstant.GetCurrentToolbarHeight();
		
		m_Left = size*m_nToolbarIndex;
		
		if(m_LayoutContainer != null)
			m_LayoutContainer.SetChildNewDemension(this, size, size, m_Left, m_Top);		

		m_RepaintRect = new RectF(0, 0, size, size);		
		
		invalidate();
	}	

	protected void onDraw(Canvas canvas)
	{
		Bitmap bitmap = ResourceHelper.GetSystemButtonBitmap();
		if(bitmap != null)
			canvas.drawBitmap(bitmap, m_SrcImageRect, m_RepaintRect, null);
 	}

}
