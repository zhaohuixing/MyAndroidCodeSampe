/**
 * 
 */
package com.xgadget.uimodule;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author zhaohuixing
 *
 */
public class CSwitchButton extends View 
{
	private Bitmap		m_BitmapOn;
	private Bitmap		m_BitmapOff;
	private boolean		m_bOnOffState;
	
	
	public void SetBitmaps(Bitmap bmpOn, Bitmap bmpOff)
	{
		m_BitmapOn = bmpOn;
		m_BitmapOff = bmpOff;
	}
	/**
	 * @return the m_bOnOffState
	 */
	public boolean isM_bOnOffState() 
	{
		return m_bOnOffState;
	}

	/**
	 * @param m_bOnOffState the m_bOnOffState to set
	 */
	public void setM_bOnOffState(boolean m_bOnOffState) 
	{
		this.m_bOnOffState = m_bOnOffState;
		invalidate();
	}

	public void SetOnOffState(boolean m_bOnOffState) 
	{
		this.m_bOnOffState = m_bOnOffState;
		invalidate();
	}

	public boolean GetOnOffState() 
	{
		return this.m_bOnOffState;
	}
	
	/**
	 * @param context
	 */
	public CSwitchButton(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		InitializeMembers();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public CSwitchButton(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		InitializeMembers();
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public CSwitchButton(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		InitializeMembers();
	}

	private void InitializeMembers()
	{
		m_BitmapOn = null;
		m_BitmapOff = null;
		m_bOnOffState = false;
	}
	
	@Override
	protected void onDraw(Canvas canvas) 
	{
		// TODO Auto-generated method stub
		if(m_bOnOffState == false)
			DrawOffState(canvas);
		else
			DrawOnState(canvas);
	}

	private void DrawOffState(Canvas canvas)
	{
		if(m_BitmapOff != null)
		{
			int nImgW = m_BitmapOff.getWidth();
			int nImgH = m_BitmapOff.getHeight();
			float fImgR = ((float)nImgW)/((float)nImgH);
			
			Rect imgRect = new Rect(0, 0, nImgW, nImgH);
			
			int nBtnW = this.getWidth();
			int nBtnH = this.getHeight();
			float fBtnR = ((float)nBtnW)/((float)nBtnH);
			int nDrawW = nBtnW;
			int nDrawH = nBtnH;
			
			if(fImgR <= fBtnR)
			{
				nDrawW = (int)(((float)nBtnH)*fImgR);
			}
			else
			{
				nDrawH = (int)(((float)nBtnW)/fImgR);
			}
			
			int left = (nBtnW - nDrawW)/2;
			int top = (nBtnH - nDrawH)/2;
			Rect drawRect = new Rect(left, top, left + nDrawW, top + nDrawH);
			canvas.drawBitmap(m_BitmapOff, imgRect, drawRect, null);
		}
	}
	
	private void DrawOnState(Canvas canvas)
	{
		if(m_BitmapOn != null)
		{
			int nImgW = m_BitmapOn.getWidth();
			int nImgH = m_BitmapOn.getHeight();
			float fImgR = ((float)nImgW)/((float)nImgH);
			
			Rect imgRect = new Rect(0, 0, nImgW, nImgH);
			
			int nBtnW = this.getWidth();
			int nBtnH = this.getHeight();
			float fBtnR = ((float)nBtnW)/((float)nBtnH);
			int nDrawW = nBtnW;
			int nDrawH = nBtnH;
			
			if(fImgR <= fBtnR)
			{
				nDrawW = (int)(((float)nBtnH)*fImgR);
			}
			else
			{
				nDrawH = (int)(((float)nBtnW)/fImgR);
			}
			
			int left = (nBtnW - nDrawW)/2;
			int top = (nBtnH - nDrawH)/2;
			Rect drawRect = new Rect(left, top, left + nDrawW, top + nDrawH);
			canvas.drawBitmap(m_BitmapOn, imgRect, drawRect, null);
		}
	}
	
}
