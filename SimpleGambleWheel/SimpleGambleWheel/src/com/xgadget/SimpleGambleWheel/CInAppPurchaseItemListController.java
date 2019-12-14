package com.xgadget.SimpleGambleWheel;

import java.util.ArrayList;

import com.xgadget.uimodule.CustomBitmapButton;
import com.xgadget.uimodule.ResourceHelper;

import android.view.View;
//import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CInAppPurchaseItemListController 
{
	
	protected ArrayList<String>		m_InAppPurchaseItemList;
	
	private ListView 				m_PurchaseItemListView;
	
	private int						m_nCurrentStoreID;
	
	private int						m_nCurrentSelectedItemIndex = -1;
	
	private CustomBitmapButton 				m_CloseButton;
	private CustomBitmapButton 				m_PurchaseButton;
	
	public CInAppPurchaseItemListController() 
	{
		// TODO Auto-generated constructor stub
		m_InAppPurchaseItemList = new ArrayList<String>();
		for(int i = 0; i < InAppPurchaseConstants.BUYITEMCOUNT; ++i)
		{
			m_InAppPurchaseItemList.add(InAppPurchaseConstants.GetID(i));
		}
		
		m_nCurrentSelectedItemIndex = -1;	
		m_PurchaseItemListView = null;
		//m_pSelectedPeer = null;
		m_nCurrentStoreID = InAppPurchaseConstants.GOOGLEPLAYID;
		m_CloseButton = null;
		m_PurchaseButton = null;
		
	}
	
	
	public void LoadInAppPurchaseItemList(int nStoreID)
	{
		//if(m_nCurrentStoreID != nStoreID)
		//{
		m_nCurrentSelectedItemIndex = -1;	
		m_nCurrentStoreID = nStoreID;
		LoadPurchaseItemList();
		UpdatePurchaseButtonVisibilty();	
		//}
	}
	
	public void LoadPurchaseItemList()
	{
		m_PurchaseItemListView = (ListView) SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.inapppurchaseitemlistview);
		m_PurchaseItemListView.setAdapter(new CInAppPurchaseListAdapter(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity, m_InAppPurchaseItemList, m_nCurrentStoreID));
		m_PurchaseItemListView.setOnItemClickListener(new OnItemClickListener() 
		{
        	@Override
        	public void onItemClick(AdapterView<?> a, View v, int position, long id) 
        	{ 
        		if(0 <= position && 0 < m_InAppPurchaseItemList.size() && position < m_InAppPurchaseItemList.size())
        		{	
        			m_nCurrentSelectedItemIndex = position;
        			UpdatePurchaseButtonVisibilty();
        		}	
        	}  
        });
	}
	
	
	public void RegisterLayoutControls()
	{
		m_nCurrentSelectedItemIndex = -1;	
		if(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity != null)
		{
			m_CloseButton = (CustomBitmapButton)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.InAppPurchaseCancelButton);
			m_CloseButton.SetBitmap(ResourceHelper.GetCloseButtonBitmap());
			// if button is clicked, close the custom dialog
			m_CloseButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					HandleCloseButtonClicked();
				}
			});

			m_PurchaseButton = (CustomBitmapButton)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.InAppPurchaseOKButton);
			m_PurchaseButton.SetBitmap(ResourceHelper.GetPurchaseButtonBitmap());
			// if button is clicked, close the custom dialog
			m_PurchaseButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					HandlePurchaseButtonClicked();
				}
			});
			LoadPurchaseItemList();
			UpdatePurchaseButtonVisibilty();
		}
	}

	private void UpdatePurchaseButtonVisibilty()
	{
		if(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity != null)
		{
			if(m_PurchaseButton != null)
			{	
				if(m_nCurrentSelectedItemIndex < 0 || InAppPurchaseConstants.BUYITEMCOUNT <= m_nCurrentSelectedItemIndex)
					m_PurchaseButton.setVisibility(View.GONE);
				else
					m_PurchaseButton.setVisibility(View.VISIBLE);
			}
		}	
	}
	
	private void HandleCloseButtonClicked()
	{
		CloseInAppPruchaseListView();
	}
	
	private void HandlePurchaseButtonClicked()
	{
		if(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity != null)
		{
			if(0 <= m_nCurrentSelectedItemIndex && m_nCurrentSelectedItemIndex <= InAppPurchaseConstants.BUYITEMCOUNT)
			{
				if(m_nCurrentStoreID == InAppPurchaseConstants.GOOGLEPLAYID)
				{	
					CloseInAppPruchaseListView();
					SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.GooglePlayInAppPurchaseForChips(this.m_nCurrentSelectedItemIndex);
				}		
				if(m_nCurrentStoreID == InAppPurchaseConstants.AMAZONAPPSTOREID)
				{	
					CloseInAppPruchaseListView();
					SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.AmazonInAppPurchaseForChips(this.m_nCurrentSelectedItemIndex);
				}		
			}
		}	
	}

	public void CloseInAppPruchaseListView()
	{
		SimpleGambleWheel.m_ApplicationController.ConfigureSubViewCloseButtonClicked();
	}

	
}
