package com.xgadget.SimpleGambleWheel;


import com.xgadget.uimodule.*;

import android.content.Context;
import android.graphics.Color;
import android.text.InputFilter;
//import android.graphics.Color;
//import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
//import android.widget.EditText;

public class CPlayerSendMessageView extends MyAbsoluteLayout 
{
	protected MyAbsoluteLayout							m_LayoutContainer;
	//private CDumyBlueRoundRectView 						m_DumyBackground;
	private CDumyGreenRoundRectView						m_DumyBackground;
	private CustomBitmapButton 							m_CloseButton;
	private CustomBitmapButton 							m_SendButton;
	private AutoCompleteTextView									m_Label;
	//private EditText									m_Label;
	
	
	OnClickListener closeButtonHandler = new OnClickListener() 
	{
		public void onClick(View v) 
		{
			// Perform action on clicks
			onClose();
		}
	};

	OnClickListener sendButtonHandler = new OnClickListener() 
	{
		public void onClick(View v) 
		{
			// Perform action on clicks
			onSend();
		}
	};
	
	public void onSend()
	{
		String msg = GetTextMessage();
		SimpleGambleWheel.m_ApplicationController.SendOnlineTextMessageToPeer(msg);
	}
	
	public void onClose()
	{
		CloseView(true);
	}
	
	public void Initialize()
	{
		m_LayoutContainer = null;

		this.addView(m_DumyBackground);
		
		m_Label.setLayoutParams(new LayoutParams(320, 50, 0, 0));
		m_Label.setGravity(Gravity.CENTER);
		m_Label.setText("");
		m_Label.setTextColor(Color.YELLOW);
		m_Label.setTextSize(18.0f);
		InputFilter maxLengthFilter = new InputFilter.LengthFilter(200);	
		m_Label.setFilters(new InputFilter[]{ maxLengthFilter });
		//m_Label.setBackgroundColor(0x08000000);
		this.addView(m_Label);
		
		m_CloseButton.SetBitmap(ResourceHelper.GetCloseButtonBitmap());
		m_CloseButton.setOnClickListener(closeButtonHandler);
		this.addView(m_CloseButton);
		this.SetChildNewDemension(m_CloseButton, 0, 0, 180, 0);

		m_SendButton.SetBitmap(ResourceHelper.GetSendButtonBitmap());
		m_SendButton.setOnClickListener(sendButtonHandler);
		this.addView(m_SendButton);
		this.SetChildNewDemension(m_SendButton, 0, 0, 180, 0); 
		
	}
	
	public CPlayerSendMessageView(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		m_DumyBackground = new CDumyGreenRoundRectView(context);
		m_Label = new AutoCompleteTextView(context);
		m_CloseButton = new CustomBitmapButton(context);
		m_SendButton = new CustomBitmapButton(context);
		this.Initialize();
	}

	public CPlayerSendMessageView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		m_DumyBackground = new CDumyGreenRoundRectView(context);
		m_Label = new AutoCompleteTextView(context);
		m_CloseButton = new CustomBitmapButton(context);
		m_SendButton = new CustomBitmapButton(context);
		this.Initialize();
	}

	public CPlayerSendMessageView(Context context, AttributeSet attrs,
			int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		m_Label = new AutoCompleteTextView(context);
		m_DumyBackground = new CDumyGreenRoundRectView(context);
		m_CloseButton = new CustomBitmapButton(context);
		m_SendButton = new CustomBitmapButton(context);

		this.Initialize();
//		m_DumyBackground = new CDumyBlueRoundRectView(context, attrs, defStyle);
		
	}
	
	public void PostOnLayoutHandle()
	{
		int w = GameLayoutConstant.GetCurrentScreenWidth();

		int bw = GameLayoutConstant.GetCurrentCompassWheelSize();
		int bh = GameLayoutConstant.GetCurrentAvatarSize()+GameLayoutConstant.GetActivePlayerIndicatorSize();
		int yOffset = GameLayoutConstant.m_nAvatarDisplayLandscapeVerticalMarginMin;
		int nSize = bh*1/3;
		int nSendBtnSize = bh*2/5;
		if(GameLayoutConstant.IsScreenProtraitMode() == false)
		{
			bh = bh + bh/2;
		}
		else
		{
			yOffset = GameLayoutConstant.GetCurrentCashBalanceSignSize()+GameLayoutConstant.m_nAvatarDisplayTopOffsetToDecoration;
		}
		
		int nX = (w-bw)/2;
		
		if(m_LayoutContainer != null)
			m_LayoutContainer.SetChildNewDemension(this, bw, bh, nX, yOffset);		
	
		this.SetChildNewDemension(m_DumyBackground, bw, bh, 0, 0);
		this.SetChildNewDemension(m_Label, bw-10, bh-10, 5, 5);
		this.SetChildNewDemension(m_CloseButton, nSize, nSize, 0, bh-nSize);
		this.SetChildNewDemension(m_CloseButton, nSize, nSize, 0, bh-nSize);
		
		this.SetChildNewDemension(m_SendButton, nSendBtnSize, nSendBtnSize, bw-nSendBtnSize, bh-nSendBtnSize); 
		
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
		if(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity != null)
		{	
			InputMethodManager imm = (InputMethodManager)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(m_Label.getWindowToken(), 0);
		}	
		this.setVisibility(View.GONE);
	}

	public void OpenView(boolean bAnimation)
	{
		this.setVisibility(View.VISIBLE);
		if(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity != null)
		{	
			InputMethodManager imm = (InputMethodManager)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInputFromInputMethod(m_Label.getWindowToken(), 0);
		}	
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
