package com.xingzhaohui.guicomponent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.view.View;

public class NOMMenuBackgroundView extends View 
{
	private Shader m_Gradient = null;
	private ShapeDrawable m_LayoutShape = null;
	
	private Bitmap			m_SignScrollUp;
	private Bitmap			m_SignScrollDown;
	private boolean			m_bShowScrollSign;
	
	private void Initialize(Context context)
	{
    	float r = NOMPopoverMenu.GetCornerSize();
    	float[] outerR = new float[]{r, r, r, r, r, r, r, r};
    	m_LayoutShape = new ShapeDrawable(new RoundRectShape(outerR, null, null));
    	m_Gradient = null;
    	m_SignScrollUp = null;
    	m_SignScrollDown = null;
    	m_bShowScrollSign = false;
	}
	
	public void SetShownScrollSign(boolean bShown)
	{
    	m_bShowScrollSign = bShown;
    	this.invalidate();
	}
	
	private void InitializeGradientObject()
	{
		float y2 = this.getHeight();
		float cx = this.getWidth()/2.0f;
		m_Gradient =  new LinearGradient(cx, 0, cx, y2, new int[] {0xA0000000, 0xFF000000}, null, Shader.TileMode.MIRROR);		
	}
	
	public void SetScrollSigns(Bitmap upSign, Bitmap downSign)
	{
    	m_SignScrollUp = upSign;
    	m_SignScrollDown = downSign;
	}
	
	public NOMMenuBackgroundView(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public NOMMenuBackgroundView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public NOMMenuBackgroundView(Context context, AttributeSet attrs,
			int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}
	
	/* (non-Javadoc)
	 * @see android.view.View#layout(int, int, int, int)
	 */
	@Override
	public void layout(int l, int t, int r, int b) 
	{
		// TODO Auto-generated method stub
		super.layout(l, t, r, b);
		InitializeGradientObject();
	}

	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) 
	{
		// TODO Auto-generated method stub
    	if(m_Gradient != null && m_LayoutShape != null)
    	{	
    		m_LayoutShape.getPaint().setShader(m_Gradient);
    		m_LayoutShape.setBounds(0, 0, this.getWidth(), this.getHeight());
    		m_LayoutShape.draw(canvas);
    	}
    	if(m_bShowScrollSign == true)
    	{
    		DrawSigns(canvas);
    	}
	}
	
	private void DrawSigns(Canvas canvas)
	{
    	if(m_SignScrollUp != null && m_SignScrollDown != null)
    	{
			int imgw = m_SignScrollUp.getWidth();
			int imgh = m_SignScrollUp.getHeight();
			float ratio = ((float)imgw)/((float)imgh);
			Rect imgRect = new Rect(0, 0, imgw, imgh);
        	float h = NOMPopoverMenu.GetCornerSize();
    		float w = ratio*h;
    		float wh = (float)this.getHeight();
    		float ww = (float)this.getWidth();
    		float sx= (ww - w)*0.5f;
    		float sy = 0;
    		RectF rt = new RectF(sx, sy, sx+w, sy+h);
			canvas.drawBitmap(m_SignScrollUp, imgRect, rt, null);
    		sy = wh - h;
    		rt = new RectF(sx, sy, sx+w, wh);
			canvas.drawBitmap(m_SignScrollDown, imgRect, rt, null);
    	}
	}

}
