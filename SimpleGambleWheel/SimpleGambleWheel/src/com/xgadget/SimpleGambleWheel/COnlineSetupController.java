package com.xgadget.SimpleGambleWheel;

import com.xgadget.XSGService.XSGAPIGameServiceManager;

import android.view.View;
import android.view.View.OnClickListener;

public class COnlineSetupController 
{
	private boolean		m_bOpenedByMenuView;
	
	public COnlineSetupController() 
	{
		// TODO Auto-generated constructor stub
		m_bOpenedByMenuView = true;
	}

	public void RegisterLayoutControls()
	{
    	if(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_OnlineSetupViewCloseButton != null)
    	{
    		SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_OnlineSetupViewCloseButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					HandleCloseButtonClicked();
				}
			});
    	}

    	if(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_OnlineSetupViewOKButton != null)
    	{
    		SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_OnlineSetupViewOKButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					HandleOKButtonClicked();
				}
			});
    	}
    	
		if(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_btnOnlineSwitch != null)
		{	
			SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_btnOnlineSwitch.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					UpdateOnlineSetting();	
				}
			});
		}
    	
    	InitializeConfigurationState();
	}
	
	public void InitializeConfigurationState()
	{
/*		if(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_OnlineOptionCheckBox != null)
		{
			boolean bEnable = GameScore.IsOnlineServiceEnable();
			SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_OnlineOptionCheckBox.setChecked(bEnable);
		}
*/
		
		if(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_btnOnlineSwitch != null)
		{	
			boolean bEnable = GameScore.IsOnlineServiceEnable();
			SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_btnOnlineSwitch.SetOnOffState(bEnable);
			if(bEnable == true)
			{
				SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_OnlineIcon.setBackgroundResource(R.drawable.onlinestate);
			}
			else
			{
				SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_OnlineIcon.setBackgroundResource(R.drawable.onlinedisablestate);
			}
			SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_btnOnlineSwitch.SetOnOffState(bEnable);
		}
		
		if(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_OnlinePlayerNameEditBox != null)
		{
			String szName = GameScore.GetOnlineNickName();
			if(szName != null && 0 < szName.length())
			{
				SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_OnlinePlayerNameEditBox.setText(szName);
			}
			else
			{
				SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_OnlinePlayerNameEditBox.setText("");
			}
		}
	}
	
	public void OpenOnlineSetupView(boolean fromMenuView)
	{
		this.m_bOpenedByMenuView = fromMenuView;
		InitializeConfigurationState();
		SimpleGambleWheel.m_ApplicationController.OpenOnlineSetupSubView(true);
	}
	
	public void HandleCloseButtonClicked()
	{
		CloseMe();
	}
	
	public void UpdateOnlineSetting()
	{
		boolean bState = SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_btnOnlineSwitch.GetOnOffState();
		bState = !bState;
		SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_btnOnlineSwitch.SetOnOffState(bState);  //??????????
		if(bState == true)
		{
			SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_OnlineIcon.setBackgroundResource(R.drawable.onlinestate);
		}
		else
		{
			SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_OnlineIcon.setBackgroundResource(R.drawable.onlinedisablestate);
		}
	}
	
	public void HandleOKButtonClicked()
	{
		boolean bPrevOnlineSetting = GameScore.IsOnlineServiceEnable();
		String szName = SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_OnlinePlayerNameEditBox.getText().toString();
        //??
		boolean bEnable = SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_btnOnlineSwitch.GetOnOffState();// = SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_OnlineOptionCheckBox.isChecked();
        //??
        
        
        if((szName == null || szName.length() <= 0) && bEnable == true)
        {
        	SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.ShowDefaultAlert(StringFactory.GetString_AskOnlineNickName());
        	return;
        }
        GameScore.SetOnlineServiceEnable(bEnable);
        GameScore.SetOnlineNickName(szName);
        if(GameScore.HasOnlinePlayerID() == false)
        {
        	String szID = XSGAPIGameServiceManager.GenerateGUID();
        	GameScore.SetOnlinePlayerID(szID);
        }
		
		CloseMe();
		
		if(bPrevOnlineSetting == false && bEnable == true)
		{
			SimpleGambleWheel.m_ApplicationController.m_GameController.CheckAndUpdateOnlineSetting();
		}
	}

	private void CloseMe()
	{
		if(this.m_bOpenedByMenuView == true)
		{
			SimpleGambleWheel.m_ApplicationController.ConfigureSubViewCloseButtonClicked();
		}
		else
		{
			SimpleGambleWheel.m_ApplicationController.GotoGameMainView();
		}
	}
	
}
