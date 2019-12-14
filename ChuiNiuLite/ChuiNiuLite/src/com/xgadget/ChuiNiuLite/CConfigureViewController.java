package com.xgadget.ChuiNiuLite;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xgadget.uimodule.CSwitchButton;
import com.xgadget.uimodule.CustomBitmapButton;
import com.xgadget.uimodule.ResourceHelper;

public class CConfigureViewController 
{
//	private CSwitchButton		m_btnOnlineSwitch;
	private CSwitchButton		m_btnThunderSwitch;
//	private EditText			m_OnlineNickNameEdit;
//	private ImageView			m_OnlineIcon;
	private ImageView			m_ThunderIcon;
	
	private ListView 			m_LevelSkillListView;
	private int					m_nCurrentSelectedGame;
	
	public CConfigureViewController() 
	{
		// TODO Auto-generated constructor stub
	}

	public void LoadControls()
	{
/*		m_btnOnlineSwitch = (CSwitchButton)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.OnlineSwitchButton);
		m_btnOnlineSwitch.SetBitmaps(CImageLoader.GetSwitchOnBitmap(), CImageLoader.GetSwitchOffBitmap());
		m_btnOnlineSwitch.SetOnOffState(false);
		m_btnOnlineSwitch.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				UpdateOnlineSetting();	
			}
		});
*/		
		
		m_btnThunderSwitch = (CSwitchButton)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.ThunderThemeButton);
		m_btnThunderSwitch.SetBitmaps(CImageLoader.GetSwitchOnBitmap(), CImageLoader.GetSwitchOffBitmap());
		m_btnThunderSwitch.SetOnOffState(CConfiguration.getThunderTheme());  //??????????
		m_btnThunderSwitch.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				UpdateThunderTheme();	
			}
		});
		
//		m_OnlineNickNameEdit = (EditText)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.OnlineNameEdit);
	
		CustomBitmapButton btnCancel = (CustomBitmapButton)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.ConfigureCloseButton);
		btnCancel.SetBitmap(CImageLoader.GetRoundCloseBitmap());
		btnCancel.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				OnCancelConfiguration();
			}
		});
		
		CustomBitmapButton btnOK = (CustomBitmapButton)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.ConfigureOKButton);
		btnOK.SetBitmap(CImageLoader.GetRoundOKBitmap());
		btnOK.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				OnOKConfiguration();
			}
		});		
		
//		m_OnlineIcon = (ImageView)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.OnlineIcon);
//		m_OnlineIcon.setBackgroundResource(R.drawable.onlinedisablestate);
		m_ThunderIcon = (ImageView)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.ThunderIcon);
		if(CConfiguration.getThunderTheme() == true)
		{
			m_ThunderIcon.setBackgroundResource(R.drawable.flashicon);
		}
		else
		{
			m_ThunderIcon.setBackgroundResource(R.drawable.flashdisableicon);
		}
		
		LoadSkillLevelControls();
	}
	
	private void LoadSkillLevelControls()
	{
		m_LevelSkillListView = (ListView)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.skillselectionlistview);
		m_LevelSkillListView.setAdapter(new CSkillSelectionListAdapter(CFCActivity.m_ApplicationController.m_CurrentActivity));
		m_LevelSkillListView.setOnItemClickListener(new OnItemClickListener() 
		{
        	@Override
        	public void onItemClick(AdapterView<?> a, View v, int position, long id) 
        	{
        		HandleLevelSkillSelection(position);
        	}  
        });
		int nSkill = CConfiguration.getGameSkill();
		int nLevel = CConfiguration.getGameLevel();
		int nSelection = nSkill + nLevel*3;  
		m_LevelSkillListView.setSelection(nSelection);
		m_nCurrentSelectedGame = nSelection; 
	}

/*	
	private boolean CanSetLevel(int nCurrentLevel)
	{
	    boolean bRet = true;
	    int nTotalScore = ScoreRecord.getTotalWinScore();
	    int nCurrentSkill = CConfiguration.getGameSkill();
	    int nScoreMaxSkill = CConfiguration.getCanGamePlaySkillAtScore(nTotalScore);
	    int nScoreMaxLevel = CConfiguration.getCanGamePlayLevelAtScore(nTotalScore);
	    
	    int nCurrent = nCurrentSkill + nCurrentLevel*3;
	    int nThreshold = nScoreMaxSkill + nScoreMaxLevel*3;
	    
	    if(nThreshold < nCurrent)
	    {
	        bRet = false;
	    }
	    
	    return bRet;
	}

	private boolean CanSetSkill(int nCurrentSkill)
	{
	    boolean bRet = true;
	    int nTotalScore = ScoreRecord.getTotalWinScore();
	    int nCurrentLevel = CConfiguration.getGameLevel();
	    int nScoreMaxSkill = CConfiguration.getCanGamePlaySkillAtScore(nTotalScore);
	    int nScoreMaxLevel = CConfiguration.getCanGamePlayLevelAtScore(nTotalScore);
	    
	    int nCurrent = nCurrentSkill + nCurrentLevel*3;
	    int nThreshold = nScoreMaxSkill + nScoreMaxLevel*3;
	    
	    if(nThreshold < nCurrent)
	    {
	        bRet = false;
	    }
	    
	    return bRet;
	}
*/	
	private void HandleLevelSkillSelection(int nSelection)
	{
	    int nTotalScore = ScoreRecord.getTotalWinScore();
	    int nScoreMaxSkill = CConfiguration.getCanGamePlaySkillAtScore(nTotalScore);
	    int nScoreMaxLevel = CConfiguration.getCanGamePlayLevelAtScore(nTotalScore);
	    
	    int nThreshold = nScoreMaxSkill + nScoreMaxLevel*3;
    	
	    //m_LevelSkillListView.setSelection(nSelection);
	    m_nCurrentSelectedGame = nSelection;
	    
	    if(nThreshold < nSelection)
	    {
	    	PopupCannotPlayAlert();
	    }
	}
	
	private void PopupCannotPlayAlert()
	{
		// custom dialog
		CFCActivity.m_ApplicationController.ShowFullScreenAlertDialog();

		// set the custom dialog components - text, image and button
		TextView text = (TextView)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.fullscreenalerttext);
		String message = StringFactory.GetString_CannotPlayerSelectedGame();
		text.setText(message);

		CustomBitmapButton dialogButton = (CustomBitmapButton)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.fullscreenalertCloseButton);
		dialogButton.SetBitmap(ResourceHelper.GetBlueCloseButtonBitmap());
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				CFCActivity.m_ApplicationController.HideFullScreenAlertDialog();
			}
		});
		
	}
/*	
	private void AskOnlineNickName()
	{
		
	}
*/	
	private void UpdateOnlineSetting()
	{
/*		boolean bState = m_btnOnlineSwitch.GetOnOffState();
		bState = !bState;
		m_btnOnlineSwitch.SetOnOffState(bState);  //??????????
		//???????????
		//???????????
		if(bState == true)
		{
			m_OnlineIcon.setBackgroundResource(R.drawable.onlinestate);
		}
		else
		{
			m_OnlineIcon.setBackgroundResource(R.drawable.onlinedisablestate);
		}*/
	}
	
	private void UpdateThunderTheme()
	{
		boolean bState = m_btnThunderSwitch.GetOnOffState();
		bState = !bState;
		m_btnThunderSwitch.SetOnOffState(bState);
		//CConfiguration.setThunderTheme(bState);
		//???????????
		//???????????
		//if(CConfiguration.getThunderTheme() == true)
		if(bState == true)
		{
			m_ThunderIcon.setBackgroundResource(R.drawable.flashicon);
		}
		else
		{
			m_ThunderIcon.setBackgroundResource(R.drawable.flashdisableicon);
		}
	}
	
	private void OnCancelConfiguration()
	{
		//??????
		//??????
		CloseConfigureView();
	}
	
	private void OnOKConfiguration()
	{
		SetNewGameConfiguration();
	}

	private void CloseConfigureView()
	{
		CFCActivity.m_ApplicationController.GotoMainMenuViewFromSubMenuView();
	}

	private void SetNewGameConfiguration()
	{
		/*boolean bState = m_btnOnlineSwitch.GetOnOffState();
		if(bState == true)
		{
			String szName = m_OnlineNickNameEdit.getText().toString();
	        if(szName == null || szName.length() <= 0)
	        {
	        	//SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.ShowDefaultAlert(StringFactory.GetString_AskOnlineNickName());
	        	AskOnlineNickName();
	        	return;
	        }
		}*/
		
		int nSelection = m_nCurrentSelectedGame;//m_LevelSkillListView.getSelectedItemPosition();
	    int nTotalScore = ScoreRecord.getTotalWinScore();
	    int nScoreMaxSkill = CConfiguration.getCanGamePlaySkillAtScore(nTotalScore);
	    int nScoreMaxLevel = CConfiguration.getCanGamePlayLevelAtScore(nTotalScore);
	    
	    int nThreshold = nScoreMaxSkill + nScoreMaxLevel*3;
	    
	    if(nThreshold < nSelection)
	    {
	    	PopupCannotPlayAlert();
	    	return;
	    }
	    
		int nLevel = nSelection/3;
		int nSkill = nSelection%3;
		CConfiguration.setGameLevel(nLevel);
		CConfiguration.setGameSkill(nSkill);
		boolean bState = m_btnThunderSwitch.GetOnOffState();
		CConfiguration.setThunderTheme(bState);
		
		ScoreRecord.saveRecord();
		
		CloseConfigureView();
	}
	
	public void UpdateConfigurationViewInformationForOpenning()
	{
		int nLevel = CConfiguration.getGameLevel();
		int nSkill = CConfiguration.getGameSkill();
		int nSelection = nSkill + nLevel*3;  
		m_LevelSkillListView.setSelection(nSelection);
		m_nCurrentSelectedGame = nSelection;
		boolean bState = CConfiguration.getThunderTheme();
		if(bState == true)
		{
			m_ThunderIcon.setBackgroundResource(R.drawable.flashicon);
		}
		else
		{
			m_ThunderIcon.setBackgroundResource(R.drawable.flashdisableicon);
		}
		m_btnThunderSwitch.SetOnOffState(bState);
		
	}
}
