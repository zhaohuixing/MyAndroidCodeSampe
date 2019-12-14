package com.xgadget.SimpleGambleWheel;

import com.xgadget.uimodule.CustomBitmapButton;
import com.xgadget.uimodule.ResourceHelper;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class CPlayerOfflineTransferChipsController 
{
	private PlayerPledgeHeadView			m_HeadView;
	private PlayerOfflineTransferChipsReceiverPicker			m_ChipsReceiverListPicker;
	private PlayerOfflineTransferChipsAmountPicker			m_TransferChipsAmountPicker;
	private TextView						m_PlayerNameTag;
	private CustomBitmapButton				m_OKButton;
	private CustomBitmapButton				m_CancelButton;
	
	
	private	boolean 						m_bShown;
	public	int								m_nWorkingPlayerSeatID;
	
	public  int								m_nSelectedTransferAmount;
	public  int								m_nSelectedTransferReceiverIDIndex;
	private int								m_nPlayerCurrentChipBalance;
	private boolean							m_bOnlineTransfer;
	
	public CPlayerOfflineTransferChipsController()
	{
		m_HeadView = null;
		m_ChipsReceiverListPicker = null;
		m_TransferChipsAmountPicker = null;
		m_PlayerNameTag = null;
		m_bShown = false;
		m_nWorkingPlayerSeatID = 0;
		m_nSelectedTransferAmount = 1;
		m_nSelectedTransferReceiverIDIndex = 0;
		m_nPlayerCurrentChipBalance = 1000;
		m_OKButton = null;
		m_CancelButton = null;
		m_bOnlineTransfer = false;
	}
	
	public int GetReceiverListIndex(int nSenderID, int nReceiverID)
	{
		int nRet = 0;
		
		if(nSenderID == 1)
		{
			if(nReceiverID == 0)
				nRet = 0;
			else if(nReceiverID == 2)
				nRet = 1;
			else if(nReceiverID == 3)
				nRet = 2;
		}
		else if(nSenderID == 2)
		{
			if(nReceiverID == 0)
				nRet = 0;
			else if(nReceiverID == 1)
				nRet = 1;
			else if(nReceiverID == 3)
				nRet = 2;
		}
		else if(nSenderID == 3)
		{
			if(nReceiverID == 0)
				nRet = 0;
			else if(nReceiverID == 1)
				nRet = 1;
			else if(nReceiverID == 2)
				nRet = 2;
		}
		else
		{
			if(nReceiverID == 1)
				nRet = 0;
			else if(nReceiverID == 2)
				nRet = 1;
			else if(nReceiverID == 3)
				nRet = 2;
		}
		
		return nRet;
	}

	public int GetCurrentReceiverIDFromList()
	{
		int nRet = GetReceiverIDFromList(m_nWorkingPlayerSeatID, m_nSelectedTransferReceiverIDIndex);
		return nRet;
	}
	
	public int GetReceiverIDFromList(int nSenderID, int nListIndex)
	{
		int nRet = 0;
		
		if(nSenderID == 1)
		{
			if(nListIndex == 0)
				nRet = 0;
			else if(nListIndex == 1)
				nRet = 2;
			else if(nListIndex == 2)
				nRet = 3;
		}
		else if(nSenderID == 2)
		{
			if(nListIndex == 0)
				nRet = 0;
			else if(nListIndex == 1)
				nRet = 1;
			else if(nListIndex == 2)
				nRet = 3;
		}
		else if(nSenderID == 3)
		{
			if(nListIndex == 0)
				nRet = 0;
			else if(nListIndex == 1)
				nRet = 1;
			else if(nListIndex == 2)
				nRet = 2;
		}
		else
		{
			if(nListIndex == 0)
				nRet = 1;
			else if(nListIndex == 1)
				nRet = 2;
			else if(nListIndex == 2)
				nRet = 3;
		}
		
		return nRet;
	}
	
	public void Open(int nPlayerSeat)
	{
		m_bShown = true;
		m_nWorkingPlayerSeatID = nPlayerSeat;
		m_nSelectedTransferAmount = 1;
		m_nSelectedTransferReceiverIDIndex = 0;
		m_nPlayerCurrentChipBalance = SimpleGambleWheel.m_ApplicationController.m_GameController.GetPlayerCurrentMoney(m_nWorkingPlayerSeatID);
		LoadActiveTransferReceiverList();
		LoadActivePlayerChips();
		LoadActivePlayerImage();
		LoadActivePlayerNameText();
		m_bOnlineTransfer = false;
	}
	
	public void OpenOnlineTransferView()
	{
		m_bShown = true;
		m_nWorkingPlayerSeatID = 0;
		m_nSelectedTransferAmount = 1;
		m_nSelectedTransferReceiverIDIndex = 0;
		m_nPlayerCurrentChipBalance = SimpleGambleWheel.m_ApplicationController.m_GameController.GetMyCurrentMoney();
		LoadOnlineTransferReceiverList();
		LoadActivePlayerChips();
		LoadOnlinePlayerImage();
		LoadOnlinePlayerNameText();
		m_bOnlineTransfer = true;
	}

	private void LoadDefault()	
	{
		LoadActiveTransferReceiverList();
		LoadActivePlayerChips();
		LoadActivePlayerImage();
		LoadActivePlayerNameText();
	}
	
	public void Close()
	{
		m_bShown = false;
		m_nWorkingPlayerSeatID = 0;
		m_nSelectedTransferAmount = 1;
		m_nSelectedTransferReceiverIDIndex = 0;
		m_nPlayerCurrentChipBalance = 1000;
	}
	
	private void LoadOnlineTransferReceiverList()
	{
		if(m_ChipsReceiverListPicker != null)
		{	
			String szReceiverName = SimpleGambleWheel.m_ApplicationController.m_GameController.GetOnlinePeerName();
			m_ChipsReceiverListPicker.ReloadOnlinePlayerTransferSetting(szReceiverName);
		}	
	}

	private void LoadOnlinePlayerImage()
	{
		GamePlayer player = SimpleGambleWheel.m_ApplicationController.m_GameController.GetMyself();
		if(player != null)
		{
			m_HeadView.SetAsMyself(player.IsMySelf());
		}	
	}
	
	private void LoadOnlinePlayerNameText()
	{
		GamePlayer player = SimpleGambleWheel.m_ApplicationController.m_GameController.GetMyself();
		if(player != null)
		{
			m_PlayerNameTag.setText(player.GetPlayerName());
		}	
	}
	
	private void LoadActiveTransferReceiverList()
	{
		if(m_ChipsReceiverListPicker != null)
			m_ChipsReceiverListPicker.ReloadPlayerTransferSetting(m_nWorkingPlayerSeatID, m_nSelectedTransferReceiverIDIndex);
	}
	
	private void LoadActivePlayerChips()
	{
		if(m_TransferChipsAmountPicker != null)
			m_TransferChipsAmountPicker.ReloadPlayerTransferAmount(m_nPlayerCurrentChipBalance, m_nSelectedTransferAmount-1);
	}
	
	private void LoadActivePlayerImage()
	{
		GamePlayer player = SimpleGambleWheel.m_ApplicationController.m_GameController.GetPlayer(m_nWorkingPlayerSeatID);
		if(player != null)
		{
			m_HeadView.SetAsMyself(player.IsMySelf());
		}	
	}
	
	private void LoadActivePlayerNameText()
	{
		GamePlayer player = SimpleGambleWheel.m_ApplicationController.m_GameController.GetPlayer(m_nWorkingPlayerSeatID);
		if(player != null)
		{
			m_PlayerNameTag.setText(player.GetPlayerName());
		}	
	}
	
	public void AttachChipsReceiverPicker(PlayerOfflineTransferChipsReceiverPicker	ChipsReceiverListPicker)
	{
		m_ChipsReceiverListPicker = ChipsReceiverListPicker;
		m_ChipsReceiverListPicker.AttachController(this);
		if(m_bShown == true)
		{
			//LoadActivePlayerNumber();
		}
	}

	public void AttachChipsAmountPicker(PlayerOfflineTransferChipsAmountPicker	ChipsAmountPicker)
	{
		m_TransferChipsAmountPicker = ChipsAmountPicker;
		m_TransferChipsAmountPicker.AttachController(this);
		if(m_bShown == true)
		{
			//LoadActivePlayerChips();
		}
	}
	
	public void AttachPlayerHeadImage(PlayerPledgeHeadView headImage)
	{
		m_HeadView = headImage;
		if(m_bShown == true)
		{
			LoadActivePlayerImage();
		}
	}

	public void AttachPlayerNameTag(TextView nameTag)
	{
		m_PlayerNameTag = nameTag;
		if(m_bShown == true)
		{
			LoadActivePlayerNameText();
		}
	}

	public void AttachCancelButton(CustomBitmapButton cancelBtn)
	{
		m_CancelButton = cancelBtn;
		//m_CancelButton.SetLabelText(StringFactory.GetString_Cancel());
		//m_CancelButton.PostOnLayoutHandle();
		m_CancelButton.SetBitmap(ResourceHelper.GetCloseButtonBitmap());
		if(m_CancelButton != null)
		{
			m_CancelButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					ClosePlayerOfflineTransferViewByCancel();
				}
			});
		}
	}

	public void AttachOKButton(CustomBitmapButton okBtn)
	{
		m_OKButton = okBtn;
		m_OKButton.SetBitmap(ResourceHelper.GetOKButtonBitmap());
		//m_OKButton.SetLabelText(StringFactory.GetString_OK());
		//m_OKButton.PostOnLayoutHandle();
		if(m_OKButton != null)
		{
			m_OKButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					ClosePlayerOfflineTransferViewByOK();
				}
			});
		}
	}
	
	public void ClosePlayerOfflineTransferViewByCancel()
	{
		SimpleGambleWheel.m_ApplicationController.ClosePlayerOfflineTransferViewByCancel();
	}
	
    public void ClosePlayerOfflineTransferViewByOK()  
    {
    	if(m_bOnlineTransfer == false)
    		SimpleGambleWheel.m_ApplicationController.ClosePlayerOfflineTransferViewByOK();
    	else
    		SimpleGambleWheel.m_ApplicationController.ClosePlayerOnlineTransferViewByOK();
    		
    }

    public void RegisterPlayerOfflineTransferChipsViewItems()
    {
    	if(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity != null)
    	{
    		AttachChipsReceiverPicker((PlayerOfflineTransferChipsReceiverPicker)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.PlayerOfflineTransferReceiverPicker));
    		AttachChipsAmountPicker((PlayerOfflineTransferChipsAmountPicker)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.PlayerOfflineTransferAmountPicker));
    		AttachPlayerHeadImage((PlayerPledgeHeadView)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.PlayerOfflineTransferChipHeadView));
    		AttachPlayerNameTag((TextView)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.PlayerOfflineTransferChipNameTag));
    		AttachCancelButton((CustomBitmapButton)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.PlayerOfflineTransferCancelButton));
     		AttachOKButton((CustomBitmapButton)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.PlayerOfflineTransferOKButton));
     		LoadDefault();
    	}	
    }
    
}
