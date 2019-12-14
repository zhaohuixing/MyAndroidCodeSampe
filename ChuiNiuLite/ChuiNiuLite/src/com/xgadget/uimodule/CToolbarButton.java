package com.xgadget.uimodule;

//import com.xgadget.uimodule.ResourceHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

public class CToolbarButton extends BasicLayoutView 
{
	private final int			m_nDefaultTBButtonSize = 60;
	
	private RectF 			m_RepaintRect;
	private int				m_Left;
	private int				m_Top;
	private int				m_Size;
	private Rect 			m_SrcImageRect;
	private int				m_nToolbarIndex;
	protected Bitmap		m_SrcImage;
	
	private void Initialize()
	{
		m_nToolbarIndex = 0;
		m_Left = 0;
		m_Top = 0;
		
		m_Size = m_nDefaultTBButtonSize;
		
		m_RepaintRect = new RectF(0, 0, m_Size, m_Size);
		m_SrcImageRect = new Rect(0, 0, m_Size, m_Size);
		m_Left = m_nDefaultTBButtonSize;
		m_Top = 0;
		m_SrcImage = null;
	}
	
	public void SetBitmap(Bitmap bitmap)
	{
		m_SrcImage = bitmap;
		if(m_SrcImage != null)
		{	
			int w = m_SrcImage.getWidth();
			int h = m_SrcImage.getHeight();
			m_SrcImageRect = new Rect(0, 0, w, h);
		}
	}
	
	public void SetToolbarIndex(int nIndex)
	{
		m_nToolbarIndex = nIndex;
		if(m_nToolbarIndex < 0)
			m_nToolbarIndex = 0;
		
		int size = m_nDefaultTBButtonSize;
	
		m_Left = size*m_nToolbarIndex;
		
		if(m_LayoutContainer != null)
			m_LayoutContainer.SetChildNewDemension(this, size, size, m_Left, m_Top);		
		int w = this.getWidth();
		int h = this.getHeight();
		m_RepaintRect = new RectF(0, 0, w, h);	
	}
	
	public CToolbarButton(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public CToolbarButton(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public CToolbarButton(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public void PostOnLayoutHandle()
	{
		int size = m_nDefaultTBButtonSize;
		
		m_Left = size*m_nToolbarIndex;
		
		if(m_LayoutContainer != null)
			m_LayoutContainer.SetChildNewDemension(this, size, size, m_Left, m_Top);		

		m_RepaintRect = new RectF(0, 0, size, size);		
		
		invalidate();
	}	

	protected void onDraw(Canvas canvas)
	{
		if(m_SrcImage != null)
		{
			canvas.drawBitmap(m_SrcImage, m_SrcImageRect, m_RepaintRect, null);
		}
 	}

}
