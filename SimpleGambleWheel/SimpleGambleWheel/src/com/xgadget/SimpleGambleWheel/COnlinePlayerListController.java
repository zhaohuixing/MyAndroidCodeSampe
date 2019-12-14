package com.xgadget.SimpleGambleWheel;

import java.util.ArrayList;
import android.view.View;
//import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.xgadget.XSGService.XSGAPIGameServiceManager;
import com.xgadget.XSGService.XSGAPIReleaseConfigure;
import com.xgadget.XSGService.XSGAPIUser;
import com.xgadget.uimodule.CustomBitmapButton;
import com.xgadget.uimodule.ResourceHelper;

public class COnlinePlayerListController 
{
	public static final long INVITATION_RESEND_TIME = 30000;
	
	protected ArrayList<XSGAPIUser>	m_CurrentPlayerList;
	protected boolean				m_bShowingSearching;
	
	private boolean					m_bIndicatorShown;
	private ListView 				m_PlayerListView;
	private XSGAPIUser				m_pSelectedPeer;
	private long 					m_TimeStartShowWarnText;
	private boolean					m_bShowWarnText;
	
	public COnlinePlayerListController() 
	{
		// TODO Auto-generated constructor stub
		m_CurrentPlayerList = new ArrayList<XSGAPIUser>();
		m_bShowingSearching = false;
		m_PlayerListView = null;
		m_pSelectedPeer = null;
		m_TimeStartShowWarnText = 0;
	}
	
	public void StartGameSection()
	{
		//??????????????????????
		//??????????????????????
		//??????????????????????
		//??????????????????????
		//??????????????????????
	}
	
	private void UpdateWarningTextSearchingStatus()
	{
		m_bShowWarnText = false;
		TextView warnText = (TextView)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.OnlinePlayerLoadingWarning);
		if(warnText != null)
		{	
			if(m_bShowingSearching == true)
			{	
				warnText.setVisibility(View.VISIBLE);
				warnText.setText(StringFactory.GetString_SearchingOnlinePlayers());
			}	
			else 
				warnText.setVisibility(View.GONE);
		}
	}

	private void CloseWarningText()
	{
		TextView warnText = (TextView)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.OnlinePlayerLoadingWarning);
		warnText.setVisibility(View.GONE);
		m_bShowWarnText = false;
	}
	
	private void ShowCancelInvitationWarnText()
	{
		m_bShowWarnText = true;
		TextView warnText = (TextView)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.OnlinePlayerLoadingWarning);
		warnText.setVisibility(View.VISIBLE);
		warnText.setText(R.string.SentInvitationCancelled);
        m_TimeStartShowWarnText = CApplicationController.GetSystemTimerClick();
	}
	
	private void ShowInvitationRejectedWarnText()
	{
		m_bShowWarnText = true;
		TextView warnText = (TextView)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.OnlinePlayerLoadingWarning);
		warnText.setVisibility(View.VISIBLE);
		warnText.setText(R.string.SentInvitationRejected);
        m_TimeStartShowWarnText = CApplicationController.GetSystemTimerClick();
	}
	
	public void LoadOnlinePlayerList()
	{
		m_pSelectedPeer = null;
		m_bIndicatorShown = false;
		m_CurrentPlayerList.clear();
		XSGAPIGameServiceManager.getXSGAPIGameManager().ResetCachedOnlineUserData();
		XSGAPIGameServiceManager.getXSGAPIGameManager().PollOnlineUserList();
		m_bShowingSearching = true;
		UpdateWarningTextSearchingStatus();
	}
	
	public void ReloadCachedPlayerList()
	{
		m_PlayerListView = (ListView) SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.onlineplayerlistview);
		m_PlayerListView.setAdapter(new COnlinePlayerListAdapter(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity, m_CurrentPlayerList));
		m_PlayerListView.setOnItemClickListener(new OnItemClickListener() 
		{
        	@Override
        	public void onItemClick(AdapterView<?> a, View v, int position, long id) 
        	{ 
        		if(0 <= position && 0 < m_CurrentPlayerList.size() && position < m_CurrentPlayerList.size())
        			m_pSelectedPeer = m_CurrentPlayerList.get(position);
        	}  
        });
		
		
		UpdateWarningTextSearchingStatus();
		if(m_bIndicatorShown == true && m_pSelectedPeer != null)
		{
    		this.ShowInvitationIndicator(m_pSelectedPeer.GetUserName());
		}
	}
	
	public void DumpOnlineUserList()
	{
		m_pSelectedPeer = null;
		m_CurrentPlayerList.clear();
		ArrayList<XSGAPIUser> pList = XSGAPIGameServiceManager.getXSGAPIGameManager().GetCachedOnlineUserList();
		m_CurrentPlayerList.addAll(pList);
		
		XSGAPIReleaseConfigure.LogDebugInfo("COnlinePlayerListController::DumpOnlineUserList", Integer.toString(m_CurrentPlayerList.size()));
		m_bShowingSearching = false;
		ReloadCachedPlayerList();
	}
	
	public void RegisterLayoutControls()
	{
    	if(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_OnlinePlayerListCloseButton != null)
    	{
    		SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_OnlinePlayerListCloseButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					HandleCloseButtonClicked();
				}
			});
    	}

    	if(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_OnlinePlayerListConnectButton != null)
    	{
    		SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.m_OnlinePlayerListConnectButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					HandleConnectButtonClicked();
				}
			});
    	}
	}
	
	private void HandleCloseButtonClicked()
	{
		CloseOnlinePlayerListView();
	}
	
	private void SendInvitationToPeer(XSGAPIUser pUser)
	{
		if(pUser != null)
		{
	        String szData = null;

	        szData = SimpleGambleWheel.m_ApplicationController.m_GameController.GetInvitationSenderInitialData();
	        
	        		
	        if(szData != null && 0 < szData.length())
	        {
	            XSGAPIGameServiceManager.getXSGAPIGameManager().SendInvitationMessageToPeer2(pUser, szData);
	        }
	        else
	        {
	            XSGAPIGameServiceManager.getXSGAPIGameManager().SendInvitationMessageToPeer(pUser);
	        }
	        
	        
			
		}
	}
	
	private void HandleConnectButtonClicked()
	{
        if(0 < m_CurrentPlayerList.size())
        {
        	if(m_pSelectedPeer != null && m_pSelectedPeer.GetUserID() != null)
        	{
      			this.SendInvitationToPeer(m_pSelectedPeer);
       			this.ShowInvitationIndicator(m_pSelectedPeer.GetUserName());
        	}	
        }
		
	}

	protected void CancelSendingInvitation()
	{
		SimpleGambleWheel.m_ApplicationController.HideFullScreenAlertDialog();
		m_bIndicatorShown = false;
	    if(m_pSelectedPeer != null)
	    {
	    	XSGAPIGameServiceManager.getXSGAPIGameManager().SendCancelInvitationMessageTo(m_pSelectedPeer.GetUserID());
	    	ShowCancelInvitationWarnText();
	    }
	}
	
	private void ShowInvitationIndicator(String playerName)
	{
        SimpleGambleWheel.m_ApplicationController.ShowFullScreenAlertDialog();
		
		
		m_bIndicatorShown = true;
		TextView text = (TextView) SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.fullscreenalerttext);
		
		String szText = StringFactory.GetString_InvitationSentTo() + " \"" +playerName+ "\""+ StringFactory.GetString_WaitingForResponse();
		
		text.setText(szText);

		CustomBitmapButton dialogButton = (CustomBitmapButton)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.fullscreenalertCloseButton);
		dialogButton.SetBitmap(ResourceHelper.GetBlueCloseButtonBitmap());
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				CancelSendingInvitation();
			}
		});
    	
	}
	
	public void CloseOnlinePlayerListView()
	{
		if(m_bIndicatorShown == true)
		{
	        SimpleGambleWheel.m_ApplicationController.HideFullScreenAlertDialog();
		}
		
		m_bIndicatorShown = false;
		SimpleGambleWheel.m_ApplicationController.ShiftToMainView();
	}

	public void SentInvitationRejected()
	{
        SimpleGambleWheel.m_ApplicationController.HideFullScreenAlertDialog();
		
		m_bIndicatorShown = false;
		ShowInvitationRejectedWarnText();
	}
	
	public void OnTimerEvent()
	{
		if(m_bShowWarnText == true)
		{
	        long currentTime = CApplicationController.GetSystemTimerClick();
	        if(INVITATION_RESEND_TIME <= (currentTime -  m_TimeStartShowWarnText))
	        {
	        	this.CloseWarningText();
	        }
		}
	}
}
