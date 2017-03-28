package com.xingzhaohui.guicomponent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NOMMenuItemContainerView extends RelativeLayout 
{
	private static final int NOMMENUITEM_UI_MARGIN = 2;
	
	private class NOMMenuItemBackgroundView extends View
	{
		private void Initialize()
		{
			this.setBackgroundColor(Color.WHITE);
		}
		
		public NOMMenuItemBackgroundView(Context context) 
		{
			super(context);
			// TODO Auto-generated constructor stub
			Initialize();
		}

		public NOMMenuItemBackgroundView(Context context, AttributeSet attrs) 
		{
			super(context, attrs);
			// TODO Auto-generated constructor stub
			Initialize();
		}

		public NOMMenuItemBackgroundView(Context context, AttributeSet attrs,
				int defStyle) 
		{
			super(context, attrs, defStyle);
			// TODO Auto-generated constructor stub
			Initialize();
		}
	}

	private NOMMenuItemBackgroundView      	m_Background;
	private ImageView						m_Icon;
	private TextView						m_Label;
	
	private void Initialize(Context context)
	{
		m_Background = new NOMMenuItemBackgroundView(context);
		this.addView(m_Background);
		this.setBackgroundColor(Color.TRANSPARENT);
		m_Icon = new ImageView(context);
		this.addView(m_Icon);
		m_Icon.setVisibility(View.GONE);
		m_Label = new TextView(context);
		this.addView(m_Label);
		//m_Label.setVisibility(View.GONE);
		m_Label.setVisibility(View.VISIBLE);
		m_Label.setTextSize(16);
		m_Label.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
		m_Label.setTextColor(Color.rgb(0,0,0));
		m_Label.setGravity(Gravity.CENTER);
		m_Label.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
	}
	
	public NOMMenuItemContainerView(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		this.Initialize(context);
	}

	public NOMMenuItemContainerView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.Initialize(context);
	}

	public NOMMenuItemContainerView(Context context, AttributeSet attrs,
			int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.Initialize(context);
	}

	public void SetIcon(Bitmap icon)
	{
		if(icon != null)
		{	
			m_Icon.setImageBitmap(icon);
			m_Icon.setVisibility(View.VISIBLE);
		}	
	}

	public void SetLabel(String label)
	{
		if(label != null && 0 < label.length())
		{	
			m_Label.setText(label);
			m_Label.setVisibility(View.VISIBLE);
			m_Label.setGravity(Gravity.CENTER);
			m_Label.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		}	
	}
	
	public void UpdateChildLayout()
	{
		int left = 0;//NOMMENUITEM_UI_MARGIN;
		int top = NOMMENUITEM_UI_MARGIN;
		int right = this.getWidth();// - NOMMENUITEM_UI_MARGIN;
		int bottom = ((int)NOMPopoverMenu.GetMenuItemHeight()) - NOMMENUITEM_UI_MARGIN;
		int size = ((int)NOMPopoverMenu.GetMenuItemHeight()) - 2*NOMMENUITEM_UI_MARGIN;
		m_Background.layout(left, top, right, bottom);
		boolean bIconShown = (m_Icon.getVisibility() == View.VISIBLE);
		boolean bLabelShown = (m_Label.getVisibility() == View.VISIBLE);
		if(bIconShown == true && bLabelShown == true)
		{
			right = left + size;
			m_Icon.layout(left, top, right, bottom);
			left = right;
			right = this.getWidth() - NOMMENUITEM_UI_MARGIN;
			m_Label.layout(left, top, right, bottom);
			m_Label.setGravity(Gravity.CENTER);
			m_Label.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		}
		else if(bIconShown == true && bLabelShown == false)
		{
			left = (this.getWidth() - size)/2;
			right = left + size;
			m_Icon.layout(left, top, right, bottom);
			m_Label.setVisibility(View.GONE);
		}
		else if(bIconShown == false && bLabelShown == true)
		{
			//RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			m_Label.layout(left, top, right, bottom);
			//m_Label.setLayoutParams(layoutparams);
			m_Icon.setVisibility(View.GONE);
			m_Label.setGravity(Gravity.CENTER);
			m_Label.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		}
		else
		{
			m_Icon.setVisibility(View.GONE);
			m_Label.setVisibility(View.GONE);
			m_Label.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) 
	{
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		UpdateChildLayout();
	}

	/* (non-Javadoc)
	 * @see android.view.View#onSizeChanged(int, int, int, int)
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) 
	{
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		UpdateChildLayout();
	}
}
