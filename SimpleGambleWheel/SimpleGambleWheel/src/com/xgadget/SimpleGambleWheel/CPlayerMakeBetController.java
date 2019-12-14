package com.xgadget.SimpleGambleWheel;

import com.xgadget.uimodule.CustomBitmapButton;
import com.xgadget.uimodule.ResourceHelper;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class CPlayerMakeBetController 
{
	private PlayerPledgeHeadView			m_HeadView;
	private PlayerBetLuckyNumberPicker		m_LuckyNumberPicker;
	private PlayerBetChipsPicker			m_ChipsPicker;
	private TextView						m_PlayerNameTag;
	private CustomBitmapButton				m_CloseButton;
	
	private	boolean 						m_bShown;
	public	int								m_nWorkingPlayerSeatID;
	
	public  int								m_nSelectedNumber;
	public  int								m_nSelectedChips;
	private int								m_nPlayerCurrentChipBalance;
	
	public CPlayerMakeBetController()
	{
		m_HeadView = null;
		m_LuckyNumberPicker = null;
		m_ChipsPicker = null;
		m_PlayerNameTag = null;
		m_bShown = false;
		m_nWorkingPlayerSeatID = -1;
		m_nSelectedNumber = 1;
		m_nSelectedChips = 1;
		m_nPlayerCurrentChipBalance = 0;
		m_CloseButton = null;
	}
	
	public void Open(int nPlayerSeat)
	{
		m_bShown = true;
		m_nWorkingPlayerSeatID = nPlayerSeat;
		m_nSelectedNumber = 1;
		m_nSelectedChips = 1;
		LoadActivePlayerNumber();
		LoadActivePlayerChips();
		LoadActivePlayerImage();
		LoadActivePlayerNameText();
	}

	public void Close()
	{
		m_bShown = false;
		m_nWorkingPlayerSeatID = -1;
	}
	
	private void LoadActivePlayerNumber()
	{
		int nType = SimpleGambleWheel.m_ApplicationController.m_GameController.GetGameType();
		int nNumber = GameUitltyHelper.GetGameLuckNumberThreshold(nType);
		m_LuckyNumberPicker.SetCurrentPickerState(nNumber, m_nSelectedNumber);
	}
	
	private void LoadActivePlayerChips()
	{
		GamePlayer player = SimpleGambleWheel.m_ApplicationController.m_GameController.GetPlayer(m_nWorkingPlayerSeatID);
		if(player != null)
		{
			m_nPlayerCurrentChipBalance = player.GetPacketBalance();
			m_ChipsPicker.SetCurrentPickerState(m_nPlayerCurrentChipBalance, m_nSelectedChips);
		}	
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
	
	public void AttachNumberPicker(PlayerBetLuckyNumberPicker	picker)
	{
		m_LuckyNumberPicker = picker;
		m_LuckyNumberPicker.AttachController(this);
		if(m_bShown == true)
		{
			LoadActivePlayerNumber();
		}
	}

	public void AttachChipsPicker(PlayerBetChipsPicker	picker)
	{
		m_ChipsPicker = picker;
		m_ChipsPicker.AttachController(this);
		if(m_bShown == true)
		{
			LoadActivePlayerChips();
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

	public void AttachCloseButton(CustomBitmapButton closeBtn)
	{
		m_CloseButton = closeBtn;
		m_CloseButton.SetBitmap(ResourceHelper.GetCloseButtonBitmap());
		if(m_CloseButton != null)
		{
			m_CloseButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					CloseCurrentPledgeView();
				}
			});
		}
	}
	
	public void CloseCurrentPledgeView()
	{
		SimpleGambleWheel.m_ApplicationController.HandlePlayerBetViewClose();
	}
	
}
