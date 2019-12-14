package com.xgadget.uimodule;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

public class CGreenGlossyButton extends ViewGroup 
{
	private CGreenGlossyBackground	m_Background;
	private TextView									m_Label;
	public static final float g_FontSize = 24.0f;
	
	private void Initialize()
	{
		int w = this.getWidth();
		int h = this.getHeight();
		this.addView(m_Background);
        this.updateViewLayout(m_Background, new ViewGroup.LayoutParams(w, h));		
		
		this.addView(m_Label);
		m_Label.setText("");
		m_Label.setTextColor(Color.argb(144, 96, 96, 96));
		//m_Label.setTextColor(Color.GRAY);
		m_Label.setTextSize(g_FontSize);
		m_Label.setGravity(Gravity.CENTER);
        this.updateViewLayout(m_Label, new ViewGroup.LayoutParams(w, h));		
		
		
	}
	
	public void SetLabelText(String label)
	{
		m_Label.setText(label);
	}
	
	public CGreenGlossyButton(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		m_Background = new CGreenGlossyBackground(context);
		m_Label = new TextView(context);
		Initialize();
	}

	public CGreenGlossyButton(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		m_Background = new CGreenGlossyBackground(context, attrs);
		m_Label = new TextView(context, attrs);
		Initialize();
	}

	public CGreenGlossyButton(Context context, AttributeSet attrs,
			int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		m_Background = new CGreenGlossyBackground(context, attrs, defStyle);
		m_Label = new TextView(context, attrs, defStyle);
		Initialize();
	}

	public void PostOnLayoutHandle()
	{
		int w = this.getWidth();
		int h = this.getHeight();
        //this.updateViewLayout(m_Background, new ViewGroup.LayoutParams(w, h));		
        //this.updateViewLayout(m_Label, new ViewGroup.LayoutParams(w, h));		
		m_Background.layout(0, 0, w, h);
        int nTop = h/6;//(h - (int)g_FontSize)/2;
		m_Label.layout(0, nTop, w, h);		
		m_Background.PostOnLayoutHandle();
	}

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) 
	{
		// TODO Auto-generated method stub
		int w = this.getWidth();
		int h = this.getHeight();
        //this.updateViewLayout(m_Background, new ViewGroup.LayoutParams(w, h));		
        //this.updateViewLayout(m_Label, new ViewGroup.LayoutParams(w, h));		
		m_Background.layout(0, 0, w, h);
        int nTop = h/6;
		m_Label.layout(0, nTop, w, h);		
        m_Background.PostOnLayoutHandle();
	}
	
}
