package com.xingzhaohui.newsonmap;


import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.ads.*;
import com.xingzhaohui.adcomponent.*;
import com.xingzhaohui.guicomponent.CustomActionBar;
import com.xingzhaohui.guicomponent.CustomBitmapButton;
import com.xingzhaohui.guicomponent.ICustomActionBarControllerDelegate;
import com.xingzhaohui.guicomponent.ICustomActionBarDelegate;

public class CustomActionBarController implements ICustomActionBarDelegate
{
	private ICustomActionBarControllerDelegate  m_Parent;
	private CustomBitmapButton					m_PopupButton;
	private CustomActionBar						m_ActionBar;
	private AdView 								m_AdView;

	
	public static final int		PBUTTONMAX_WIDTH = 100;
	
	public CustomActionBarController(ICustomActionBarControllerDelegate delegate) 
	{
		// TODO Auto-generated constructor stub
		m_Parent = delegate;
		m_PopupButton = null;
		m_AdView = null;
		m_ActionBar = new CustomActionBar(this);
	}

	public ICustomActionBarControllerDelegate GetParent()
	{
		return m_Parent;
	}
	
	public void CreatePopupButton(Bitmap btnBmp)
	{
		if(m_Parent != null)
		{	
			Activity activity = m_Parent.GetApplicationActivity();
			if(activity != null)
			{
				m_AdView = new AdView(activity, AdSize.BANNER, NOMAdConfiguration.AD_UNIT_ID);
				m_Parent.AddChild(m_AdView);
				
				m_PopupButton = new CustomBitmapButton(activity);
				m_PopupButton.SetBitmap(btnBmp);
				m_Parent.AddChild(m_PopupButton);
				this.ArrangePopupButton();
				m_PopupButton.setOnClickListener(new OnClickListener() 
				{
					@Override
					public void onClick(View arg0) 
					{
						HandlePopupButtonClicked();
					}
				});
			}
		}
	}
	
	public void CreateActionBarButtons(Bitmap find200, Bitmap set200, Bitmap post200)
	{
		if(m_Parent != null && m_ActionBar != null)
		{
			m_ActionBar.CreateActionBarButtons(find200, set200, post200);
		}	
	}
	
	public void HandlePopupButtonClicked()
	{
		ShowPopupButton(false);
		if(m_ActionBar != null)
		{
			m_ActionBar.ShowActionBar(true);
		}
	}
	
	public void HandleSearchButtonClicked()
	{
		if(m_Parent != null)
		{
			m_Parent.HandleSearchButtonClicked();
		}
	}

	public void HandleSettingButtonClicked()
	{
		if(m_Parent != null)
		{
			m_Parent.HandleSettingButtonClicked();
		}
	}
	
	public void HandlePostButtonClicked()
	{
		if(m_Parent != null)
		{
			m_Parent.HandlePostButtonClicked();		
		}
	}
	
	public void HandleActionBarButtonClicked(int nID)
	{
		switch(nID)
		{
			case CustomActionBar.ACTIONBAR_BTN_SEARCH:
				HandleSearchButtonClicked();
				break;
			case CustomActionBar.ACTIONBAR_BTN_SETTING:
				HandleSettingButtonClicked();
				break;
			case CustomActionBar.ACTIONBAR_BTN_POST:
				HandlePostButtonClicked();
				break;
		}
	}

	public void ShowPopupButton(boolean bShow)
	{
		if(bShow == true)
		{
			if(m_PopupButton != null)
				m_PopupButton.setVisibility(View.VISIBLE);
		}
		else 
		{
			if(m_PopupButton != null)
				m_PopupButton.setVisibility(View.GONE);
		}
		ShowAdView(bShow);
	}
	
	public void ArrangePopupButton()
	{
		int w = m_Parent.GetContainerWidth();
		int h = m_Parent.GetContainerHeight();
		int btnw = (int)(((float)w)/5.0f);
		if(CustomActionBarController.PBUTTONMAX_WIDTH < btnw)
			btnw = CustomActionBarController.PBUTTONMAX_WIDTH;
		int btnh = btnw/2;
		
		int left = (w - btnw)/2;
		int top = h - btnh;
		int right = left + btnw;
		int bottom = top + btnh;
		m_PopupButton.layout(left, top, right, bottom);
		
		if(m_AdView != null)
		{
			int adwidth = m_AdView.getWidth();
			int adheight = m_AdView.getHeight();
			left = (w - adwidth)/2;
			top = h - adheight;
			right = left + adwidth;
			bottom = top + adheight;
			m_AdView.layout(left, top, right, bottom);
		}
	}
	
	public void UpdateLayout()
	{
		if(m_PopupButton != null)
			this.ArrangePopupButton();
		if(m_ActionBar != null)
		{
			int left = m_Parent.GetContainerLeft();
			int top = m_Parent.GetContainerTop();
			int width = m_Parent.GetContainerWidth();
			int height = m_Parent.GetContainerHeight();
			m_ActionBar.UpdateLayout(left, top, width, height);
		}
	}
	
	public void ShowActionBar(boolean bShow)
	{
		if(m_ActionBar != null)
		{
			m_ActionBar.ShowActionBar(bShow);
		}
	}

	public void ShowAdView(boolean bShow)
	{
		if(m_AdView != null)
		{
			if(bShow == true)
			{	
				m_AdView.setVisibility(View.VISIBLE);
				m_AdView.loadAd(new AdRequest());
			}
			else
			{
				m_AdView.setVisibility(View.GONE);
			}
		}
	}

	public int GetActionBarButtonTop()
	{
		int nRet = 0;
		
		if(m_ActionBar != null)
		{
			nRet = m_ActionBar.GetActionBarButtonTop();
		}
		
		return nRet;
	}

	public int GetSearchButtonCenterX()
	{
		int nRet = 0;
		
		if(m_ActionBar != null)
		{
			nRet = m_ActionBar.GetSearchButtonCenterX();
		}
		
		return nRet;
	}

	public int GetSettingButtonCenterX()
	{
		int nRet = 0;
		
		if(m_ActionBar != null)
		{
			nRet = m_ActionBar.GetSettingButtonCenterX();
		}
		
		return nRet;
	}

	public int GetPostButtonCenterX()
	{
		int nRet = 0;
		
		if(m_ActionBar != null)
		{
			nRet = m_ActionBar.GetPostButtonCenterX();
		}
		
		return nRet;
	}
	
}
