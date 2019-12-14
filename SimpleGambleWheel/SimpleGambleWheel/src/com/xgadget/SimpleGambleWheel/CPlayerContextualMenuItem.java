package com.xgadget.SimpleGambleWheel;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class CPlayerContextualMenuItem extends MyAbsoluteLayout 
{
	private CPlayerContextualMenuItemGlossyBackground	m_Background;
	private TextView									m_Label;
	
	private void Initialize()
	{
		int w = this.getWidth();
		int h = this.getHeight();
		if(w <= 0)
			w = GameLayoutConstant.GetCurrentAvatarSize();
		if(h <= 0)
			h = GameLayoutConstant.GetCurrentAvatarSize()/2;
			
		m_Background.setLayoutParams(new LayoutParams(w, h, 0, 0));
		this.addView(m_Background);
		int tw = w-h;
		int lt = h/2;
		m_Label.setLayoutParams(new LayoutParams(tw, h, lt, 0));
		m_Label.setGravity(Gravity.CENTER);
		this.addView(m_Label);
		m_Label.setText("");
		m_Label.setTextColor(Color.YELLOW);
		m_Label.setTextSize(12.0f);
		
		this.setClickable(true);
	}
	
	public void SetLabelText(String label)
	{
		m_Label.setText(label);
	}
	
	public CPlayerContextualMenuItem(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		m_Background = new CPlayerContextualMenuItemGlossyBackground(context);
		m_Label = new TextView(context);
		Initialize();
	}

	public CPlayerContextualMenuItem(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		m_Background = new CPlayerContextualMenuItemGlossyBackground(context, attrs);
		m_Label = new TextView(context, attrs);
		Initialize();
	}

	public CPlayerContextualMenuItem(Context context, AttributeSet attrs,
			int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		m_Background = new CPlayerContextualMenuItemGlossyBackground(context, attrs, defStyle);
		m_Label = new TextView(context, attrs, defStyle);
		Initialize();
	}

	public void PostOnLayoutHandle()
	{
		int w = this.getWidth();
		int h = this.getHeight();
    	this.updateViewLayout(m_Background, new LayoutParams(w, h, 0, 0));
    	m_Background.PostOnLayoutHandle();
		int tw = w-h/4;
		int lt = h/8;
		this.updateViewLayout(m_Label, new LayoutParams(tw, h, lt, 0));
		InvalidateSubItems();
	}
	
	public void InvalidateSubItems()
	{
		m_Background.invalidate();
		m_Label.invalidate();
	}
	
	public void Show()
	{
		this.m_Background.setVisibility(View.VISIBLE);
		this.m_Label.setVisibility(View.VISIBLE);
	}
}
