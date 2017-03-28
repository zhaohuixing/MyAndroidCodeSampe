package com.xingzhaohui.guicomponent;

import com.xingzhaohui.newsonmap.CustomActionBarController;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;

public class CustomActionBar 
{
	public static final int ACTIONBAR_BTN_SEARCH = 0;
	public static final int ACTIONBAR_BTN_SETTING = 1;
	public static final int ACTIONBAR_BTN_POST = 2;
	
	
	private ICustomActionBarDelegate m_ActionBarController;
	
	private CustomBitmapButton					m_SearchButton;
	private CustomBitmapButton					m_SettingButton;
	private CustomBitmapButton					m_PostButton;

	public CustomActionBar(ICustomActionBarDelegate controller) 
	{
		// TODO Auto-generated constructor stub
		m_ActionBarController = controller;
		m_SearchButton = null;
		m_SettingButton = null;
		m_PostButton = null;
	}

	public void HandleSearchButtonClicked()
	{
		if(m_ActionBarController != null)
		{
			m_ActionBarController.HandleActionBarButtonClicked(ACTIONBAR_BTN_SEARCH);
		}
	}
	
	public void HandleSettingButtonClicked()
	{
		if(m_ActionBarController != null)
		{
			m_ActionBarController.HandleActionBarButtonClicked(ACTIONBAR_BTN_SETTING);
		}
	}
	
	public void HandlePostButtonClicked()
	{
		if(m_ActionBarController != null)
		{
			m_ActionBarController.HandleActionBarButtonClicked(ACTIONBAR_BTN_POST);
		}
	}
	
	public void CreateActionBarButtons(Bitmap find200, Bitmap set200, Bitmap post200)
	{
		if(m_ActionBarController == null)
			return;
		
		ICustomActionBarControllerDelegate parent = m_ActionBarController.GetParent();
		if(parent != null)
		{
			Activity activity = parent.GetApplicationActivity();
			if(activity != null)
			{
				m_SearchButton = new CustomBitmapButton(activity);
				m_SearchButton.SetBitmap(find200);
				parent.AddChild(m_SearchButton);
				m_SearchButton.setOnClickListener(new OnClickListener() 
				{
					@Override
					public void onClick(View arg0) 
					{
						HandleSearchButtonClicked();
					}
				});
				
				m_SettingButton = new CustomBitmapButton(activity);
				m_SettingButton.SetBitmap(set200);
				parent.AddChild(m_SettingButton);
				m_SettingButton.setOnClickListener(new OnClickListener() 
				{
					@Override
					public void onClick(View arg0) 
					{
						HandleSettingButtonClicked();
					}
				});

				m_PostButton = new CustomBitmapButton(activity);
				m_PostButton.SetBitmap(post200);
				parent.AddChild(m_PostButton);
				m_PostButton.setOnClickListener(new OnClickListener() 
				{
					@Override
					public void onClick(View arg0) 
					{
						HandlePostButtonClicked();
					}
				});
				
				this.ShowActionBar(false);
			}
		}
	}
	
	public void UpdateLayout(int left, int top, int width, int height)
	{
		int barw = (int)(((float)width)/3.0f);
		if(CustomActionBarController.PBUTTONMAX_WIDTH*3 < barw)
			barw = CustomActionBarController.PBUTTONMAX_WIDTH*3;
		int btnsize = barw/3;
		int sx = left + (width-barw)/2;
		int sy = top + (height - btnsize);
		int ex = sx + btnsize;
		int ey = sy + btnsize;
		if(m_SearchButton != null && m_SettingButton != null && m_PostButton != null)
		{	
			m_SearchButton.layout(sx, sy, ex, ey);
			sx = ex;
			ex = sx + btnsize;
			m_SettingButton.layout(sx, sy, ex, ey);
			sx = ex;
			ex = sx + btnsize;
			m_PostButton.layout(sx, sy, ex, ey);
		}	
	}
	
	public void ShowActionBar(boolean bShow)
	{
		if(bShow == true)
		{
			if(m_SearchButton != null && m_SettingButton != null && m_PostButton != null)
			{
				m_SearchButton.setVisibility(View.VISIBLE);
				m_SettingButton.setVisibility(View.VISIBLE);
				m_PostButton.setVisibility(View.VISIBLE);
			}
		}
		else 
		{
			if(m_SearchButton != null && m_SettingButton != null && m_PostButton != null)
			{
				m_SearchButton.setVisibility(View.GONE);
				m_SettingButton.setVisibility(View.GONE);
				m_PostButton.setVisibility(View.GONE);
			}
		}
	}
	
	public int GetActionBarButtonTop()
	{
		int nRet = 0;
		
		if(m_SearchButton != null)
		{
			nRet = m_SearchButton.getTop();
		}
		
		return nRet;
	}

	public int GetSearchButtonCenterX()
	{
		int nRet = 0;
		
		if(m_SearchButton != null)
		{
			nRet = m_SearchButton.getLeft() + m_SearchButton.getWidth()/2;
		}
		
		return nRet;
	}

	public int GetSettingButtonCenterX()
	{
		int nRet = 0;
		
		if(m_SettingButton != null)
		{
			nRet = m_SettingButton.getLeft() + m_SettingButton.getWidth()/2;
		}
		
		return nRet;
	}

	public int GetPostButtonCenterX()
	{
		int nRet = 0;
		
		if(m_PostButton != null)
		{
			nRet = m_PostButton.getLeft() + m_PostButton.getWidth()/2;
		}
		
		return nRet;
	}
	
}
