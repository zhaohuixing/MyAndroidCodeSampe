package com.xgadget.SimpleGambleWheel;


import com.xgadget.uimodule.CustomBitmapButton;
import com.xgadget.uimodule.*;

import android.content.Context;
import android.graphics.Color;
//import android.graphics.Color;
//import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class CPlayerMessageDisplayView extends MyAbsoluteLayout 
{
	protected MyAbsoluteLayout							m_LayoutContainer;
	private CDumyBlueRoundRectView 					m_DumyBackground;
	private CustomBitmapButton 							m_CloseButton;
	private TextView									m_Label;
	
	private boolean									m_bShown = false;	
	
	OnClickListener closeButtonHandler = new OnClickListener() 
	{
		public void onClick(View v) 
		{
			// Perform action on clicks
			onClose();
		}
	};
	
	public void onClose()
	{
		//m_bShown = false;
		//this.setVisibility(View.GONE);
		this.CloseMe();
	}
	
	public boolean IsShown()
	{
		return m_bShown;
	}

	public void SetShown(boolean bShown)
	{
		m_bShown = bShown;
	}
	
	public void ShowMe()
	{
		m_bShown = true;
		this.setVisibility(View.VISIBLE);
	}
	
	public void CloseMe()
	{
		m_bShown = false;
		this.setVisibility(View.GONE);
	}
	
	public void Initialize()
	{
		m_LayoutContainer = null;
		m_bShown = false;
	}
	
	public CPlayerMessageDisplayView(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		this.Initialize();
		m_DumyBackground = new CDumyBlueRoundRectView(context);
		this.addView(m_DumyBackground);
		
		m_Label = new TextView(context);
		m_Label.setLayoutParams(new LayoutParams(320, 50, 0, 0));
		m_Label.setGravity(Gravity.CENTER);
		this.addView(m_Label);
		m_Label.setText("");
		m_Label.setTextColor(Color.YELLOW);
		m_Label.setTextSize(18.0f);
		
		m_CloseButton = new CustomBitmapButton(context);
		m_CloseButton.SetBitmap(ResourceHelper.GetCloseButtonBitmap());
		m_CloseButton.setOnClickListener(closeButtonHandler);
		this.addView(m_CloseButton);
		this.SetChildNewDemension(m_CloseButton, 20, 20, 180, 0);
	}

	public CPlayerMessageDisplayView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.Initialize();
		m_DumyBackground = new CDumyBlueRoundRectView(context, attrs);
		this.addView(m_DumyBackground);
		
		m_Label = new TextView(context, attrs);
		m_Label.setLayoutParams(new LayoutParams(320, 50, 0, 0));
		m_Label.setGravity(Gravity.CENTER);
		this.addView(m_Label);
		m_Label.setText("");
		m_Label.setTextColor(Color.YELLOW);
		m_Label.setTextSize(18.0f);
		
		m_CloseButton = new CustomBitmapButton(context, attrs);
		m_CloseButton.SetBitmap(ResourceHelper.GetCloseButtonBitmap());
		m_CloseButton.setOnClickListener(closeButtonHandler);
		this.addView(m_CloseButton);
		this.SetChildNewDemension(m_CloseButton, 20, 20, 180, 0);
	}

	public CPlayerMessageDisplayView(Context context, AttributeSet attrs,
			int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.Initialize();
		m_DumyBackground = new CDumyBlueRoundRectView(context, attrs, defStyle);
		this.addView(m_DumyBackground);
		
		m_Label = new TextView(context, attrs, defStyle);
		m_Label.setLayoutParams(new LayoutParams(320, 50, 0, 0));
		m_Label.setGravity(Gravity.CENTER);
		this.addView(m_Label);
		m_Label.setText("");
		m_Label.setTextColor(Color.YELLOW);
		m_Label.setTextSize(18.0f);
		
		m_CloseButton = new CustomBitmapButton(context, attrs, defStyle);
		m_CloseButton.SetBitmap(ResourceHelper.GetCloseButtonBitmap());
		m_CloseButton.setOnClickListener(closeButtonHandler);
		this.addView(m_CloseButton);
		this.SetChildNewDemension(m_CloseButton, 20, 20, 180, 0);
	}
	
	public void PostOnLayoutHandle()
	{
		int w = GameLayoutConstant.GetCurrentScreenWidth();

		int bw = w - 2*GameLayoutConstant.GetCurrentBalanceSignSize(); //GameLayoutConstant.GetCurrentCompassWheelSize();
		int bh = GameLayoutConstant.GetAdBannerHeight();
		if(GameLayoutConstant.IsScreenProtraitMode() == true)
		{
			bh = bh + bh/2;
		}
		
		int nX = (w-bw)/2;
		
		if(m_LayoutContainer != null)
			m_LayoutContainer.SetChildNewDemension(this, bw, bh, nX, 0);		
	
		this.SetChildNewDemension(m_DumyBackground, bw, bh, 0, 0);
		this.SetChildNewDemension(m_Label, bw-10, bh-10, 5, 5);
		int nSize = bh*2/5;
		this.SetChildNewDemension(m_CloseButton, nSize, nSize, bw-nSize, 0);
		
		
//		TextView v = new TextView(getContext());
//		v.setText("Test View");
//		this.addView(v);
		
		m_DumyBackground.invalidate();
		m_CloseButton.invalidate();
	}
	
	public void SetTextMessage(String text)
	{
		if(m_Label != null)
		{
			m_Label.setText(text);
		}
	}
	
	public String GetTextMessage()
	{
		String msg = null;
		if(m_Label != null)
		{
			CharSequence str = m_Label.getText();
			msg = str.toString();
		}
		
		return msg;
	}
	
	public boolean HasTextMessage()
	{
		String msg = null;
		if(m_Label != null)
		{
			CharSequence str = m_Label.getText();
			msg = str.toString();
		}
		
		if(msg == null || msg.length() <= 0)
			return false;
		else
			return true;
	}
	
	public void CleanTextMessage()
	{
		if(m_Label != null)
		{
			m_Label.setText("");
		}
	}
	
	public boolean IsOpened()
	{
		boolean bRet = false;
		
		if(this.getVisibility() == View.VISIBLE)
			bRet = true;
		
		return bRet;
	}
    
	public void CloseView(boolean bAnimation)
	{
		//this.setVisibility(View.GONE);
		this.CloseMe();
	}

	public void OpenView(boolean bAnimation)
	{
		//this.setVisibility(View.VISIBLE);
		this.ShowMe();
	}
	
	/**
	 * @return the m_LayoutContainer
	 */
	public MyAbsoluteLayout getM_LayoutContainer() 
	{
		return m_LayoutContainer;
	}

	/**
	 * @param m_LayoutContainer the m_LayoutContainer to set
	 */
	public void setM_LayoutContainer(MyAbsoluteLayout m_LayoutContainer) 
	{
		this.m_LayoutContainer = m_LayoutContainer;
	}
	
}
