package com.xgadget.uimodule;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.view.View;

public class CGreenGlossyBackground extends View 
{
	private	RectF 								m_RepaintRect;
	private Shader m_GreenGradient = null;
	private ShapeDrawable m_OutterRoundShape = null;
	private Shader m_HighlightGradient = null;
	private ShapeDrawable m_HighlightRoundShape = null;

	private void CreateGreenGradientShader()
	{
		float y2 = m_RepaintRect.height();
		float cx = m_RepaintRect.width()/2.0f;
		m_GreenGradient =  new LinearGradient(cx, 0, cx, y2, new int[] {0xFF1A661A, 0xFFC0FFC0, 0xFF1A661A}, null, Shader.TileMode.MIRROR);		        

		m_HighlightGradient =  new LinearGradient(cx, 0, cx, y2/2.0f, new int[] {0xFFCCFFCC, 0x6699E599}, null, Shader.TileMode.MIRROR);		        
		
	}

	private void CreateRoundShapeDrawables()
	{
    	float h = m_RepaintRect.height();
    	float rr = h/2.0f;
    	float[] outerR = new float[]{rr, rr, rr, rr, rr, rr, rr, rr};
    	m_OutterRoundShape = new ShapeDrawable(new RoundRectShape(outerR, null, null));
    	float rr1 = rr/2.0f;
    	float[] outerR1 = new float[]{rr1, rr1, rr1, rr1, rr1, rr1, rr1, rr1};
    	m_HighlightRoundShape = new ShapeDrawable(new RoundRectShape(outerR1, null, null));
	}
	
	public void Initialize()
	{
		this.setBackgroundColor(Color.TRANSPARENT);
		//this
		int w = this.getWidth();
		int h = this.getHeight();
		m_RepaintRect = new RectF(0, 0, w, h);
		CreateGreenGradientShader();
		CreateRoundShapeDrawables();
	}
	
	public void PostOnLayoutHandle()
	{
		Initialize();
		this.invalidate();
	}
	public CGreenGlossyBackground(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public CGreenGlossyBackground(Context context,
			AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public CGreenGlossyBackground(Context context,
			AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) 
	{
		// TODO Auto-generated method stub
	    DrawBasicBackground(canvas);
	    DrawHighLightBackground(canvas);
	}

    private void DrawBasicBackground(Canvas canvas)
    {
    	if(m_OutterRoundShape != null && m_GreenGradient != null)
    	{	
    		m_OutterRoundShape.getPaint().setShader(m_GreenGradient);
    		m_OutterRoundShape.setBounds(0, 0, (int)m_RepaintRect.width(), (int)m_RepaintRect.height());
    		m_OutterRoundShape.draw(canvas);
    	}	
    }
    
    private void DrawHighLightBackground(Canvas canvas)
    {
    	if(m_HighlightRoundShape != null && m_HighlightGradient != null)
    	{	
    		float w = m_RepaintRect.width();
    		float h = m_RepaintRect.height();
    		float w1 = w - h/2.0f;
    		float h2 = h/2.0f;
    		int left = (int)((w-w1)*0.5f);
    		int right = left +(int)w1;
    		int top = 0;
    		int bottom = (int)h2;
    		
    		m_HighlightRoundShape.getPaint().setShader(m_HighlightGradient);
    		m_HighlightRoundShape.setBounds(left, top, right, bottom);
    		m_HighlightRoundShape.draw(canvas);
    	}	
    }
}
