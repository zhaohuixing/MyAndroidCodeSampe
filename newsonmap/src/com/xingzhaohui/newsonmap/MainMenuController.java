package com.xingzhaohui.newsonmap;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.xingzhaohui.awsservice.*;
import com.xingzhaohui.guicomponent.*;


public class MainMenuController implements INOMPopoverMenuDelegate
{
	MainWindowFrame					m_Parent;
	private NOMPopoverMenu			m_SearchMenu;
	private NOMPopoverMenu			m_SettingMenu;
	private NOMPopoverMenu			m_PostMenu;
	
	private Context					m_Context;
	
	public MainMenuController(Context context, MainWindowFrame parent) 
	{
		// TODO Auto-generated constructor stub
		m_Context = context;
		m_Parent = parent;
		m_SearchMenu = new NOMPopoverMenu(context);
		m_SearchMenu.RegisterDelegate(this);
		m_SearchMenu.RegisterMenuID(AppConstants.GUIID_SEARCHMENU_ID);
		m_SearchMenu.SetButtonImages(ResourceHelper.GetClose200Bitmap(), ResourceHelper.GetBack200Bitmap());
		m_Parent.AddChild(m_SearchMenu);
		
		m_SettingMenu = new NOMPopoverMenu(context);
		m_SettingMenu.RegisterDelegate(this);
		m_SettingMenu.RegisterMenuID(AppConstants.GUIID_SETTINGMENU_ID);
		m_SettingMenu.SetButtonImages(ResourceHelper.GetClose200Bitmap(), ResourceHelper.GetBack200Bitmap());
		m_Parent.AddChild(m_SettingMenu);
		
		m_PostMenu = new NOMPopoverMenu(context);
		m_PostMenu.RegisterDelegate(this);
		m_PostMenu.RegisterMenuID(AppConstants.GUIID_POSTMENU_ID);
		m_PostMenu.SetButtonImages(ResourceHelper.GetClose200Bitmap(), ResourceHelper.GetBack200Bitmap());
		m_Parent.AddChild(m_PostMenu);
	}

	public void CloseMenus()
	{
		CloseSearchMenu();
		CloseSettingMenu();
		ClosePostMenu();
	}
	
	public void CloseMenu(int menuID)
	{
		if(AppConstants.GUIID_SEARCHMENU_ID == menuID)
		{
			CloseSearchMenu();
		}
		else if(AppConstants.GUIID_SETTINGMENU_ID == menuID)
		{
			CloseSettingMenu();
		}
		else if(AppConstants.GUIID_POSTMENU_ID == menuID)
		{
			ClosePostMenu();
		}
		//if(m_Parent != null)
		//	m_Parent.CloseMenuAndActionBar();
	}

	private void CloseSearchMenu()
	{
		m_SearchMenu.setVisibility(View.GONE);
	}
	
	private void CloseSettingMenu()
	{
		m_SettingMenu.setVisibility(View.GONE);
	}
	
	private void ClosePostMenu()
	{
		m_PostMenu.setVisibility(View.GONE);
	}

	public void OpenSearchMenu()
	{
		m_SearchMenu.setVisibility(View.VISIBLE);
		m_SearchMenu.OpenMenu();
		m_SettingMenu.setVisibility(View.GONE);
		m_PostMenu.setVisibility(View.GONE);
	}
	
	public void OpenSettingMenu()
	{
		m_SettingMenu.setVisibility(View.VISIBLE);
		m_SettingMenu.OpenMenu();
		m_SearchMenu.setVisibility(View.GONE);
		m_PostMenu.setVisibility(View.GONE);
	}
	
	public void OpenPostMenu()
	{
		m_PostMenu.setVisibility(View.VISIBLE);
		m_PostMenu.OpenMenu();
		m_SearchMenu.setVisibility(View.GONE);
		m_SettingMenu.setVisibility(View.GONE);
	}
	
	public void LoadMenuItems()
	{
		m_SearchMenu.SetScrollSigns(ResourceHelper.GetScrollUpBitmap(), ResourceHelper.GetScrollDownBitmap());
		m_SettingMenu.SetScrollSigns(ResourceHelper.GetScrollUpBitmap(), ResourceHelper.GetScrollDownBitmap());
		m_PostMenu.SetScrollSigns(ResourceHelper.GetScrollUpBitmap(), ResourceHelper.GetScrollDownBitmap());
		
		LoadSearchMenuItems();
		LoadSettingMenuItems();
		LoadPostMenuItems();
	}

	private void LaodCommunitySearchMenuItems(NOMMenuCoreView rootMenu, NOMMenuItem commnunityitem)
	{
		NOMMenuCoreView commnityMenu = new NOMMenuCoreView(m_Context);
		commnityMenu.RegisterMenuID(AppConstants.GUIID_SEARCHMENU_COMMUNITYMENU_ID);
		commnityMenu.RegisterParent(rootMenu, m_SearchMenu);
		ArrayList<NOMMenuItem> 	itemList = new ArrayList<NOMMenuItem>();
		
		NOMMenuItem eventitem = new NOMMenuItem(AppConstants.GUIID_SEARCHMENU_COMMUNITYMENU_ITEM_EVENT_ID);
		eventitem.SetLabel(StringFactory.GetString_CommunityTitle(NOMSystemConstants.NOM_NEWSCATEGORY_COMMUNITY_SUBCATEGORY_COMMUNITYEVENT));
		itemList.add(eventitem);
		
		NOMMenuItem saleitem = new NOMMenuItem(AppConstants.GUIID_SEARCHMENU_COMMUNITYMENU_ITEM_YARDSALE_ID);
		saleitem.SetLabel(StringFactory.GetString_CommunityTitle(NOMSystemConstants.NOM_NEWSCATEGORY_COMMUNITY_SUBCATEGORY_COMMUNITYYARDSALE));
		itemList.add(saleitem);
		
		NOMMenuItem wikiitem = new NOMMenuItem(AppConstants.GUIID_SEARCHMENU_COMMUNITYMENU_ITEM_WIKI_ID);
		wikiitem.SetLabel(StringFactory.GetString_CommunityTitle(NOMSystemConstants.NOM_NEWSCATEGORY_COMMUNITY_SUBCATEGORY_COMMUNITYWIKI));
		itemList.add(wikiitem);
		
		commnityMenu.AddMenuList(itemList);
		m_SearchMenu.AddMenu(commnityMenu);
		commnunityitem.SetChildMenu(commnityMenu);
	}
	
	private void LaodLocalNewsSearchMenuItems(NOMMenuCoreView rootMenu, NOMMenuItem localnewsitem)
	{
		NOMMenuCoreView localNewsMenu = new NOMMenuCoreView(m_Context);
		localNewsMenu.RegisterMenuID(AppConstants.GUIID_SEARCHMENU_LOCALNEWSMENU_ID);
		localNewsMenu.RegisterParent(rootMenu, m_SearchMenu);
		ArrayList<NOMMenuItem> 	itemList = new ArrayList<NOMMenuItem>();
		 
		int startID = AppConstants.GUIID_SEARCHMENU_LOCALNEWSMENU_ITEM_PUBLICISSUE_ID;
		int endID = AppConstants.GUIID_SEARCHMENU_LOCALNEWSMENU_ITEM_ALL_ID;
		int nCount = endID - startID;
		int stringID0 = NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_PUBLICISSUE;
		for(int i = 0; i <= nCount; ++i)
		{
			NOMMenuItem item = new NOMMenuItem(i + startID);
			item.SetLabel(StringFactory.GetString_LocalNewsTitle(i+stringID0));
			itemList.add(item);
		}
		
		localNewsMenu.AddMenuList(itemList);
		m_SearchMenu.AddMenu(localNewsMenu);
		localnewsitem.SetChildMenu(localNewsMenu);
	}

	private void LaodPublicTransitSearchMenuItems(NOMMenuCoreView rootMenu, NOMMenuItem ptitem)
	{
		NOMMenuCoreView ptMenu = new NOMMenuCoreView(m_Context);
		ptMenu.RegisterMenuID(AppConstants.GUIID_SEARCHMENU_TRAFFICMENU_PUBLICTRANSITMENU_ID);
		ptMenu.RegisterParent(rootMenu, m_SearchMenu);
		ArrayList<NOMMenuItem> 	itemList = new ArrayList<NOMMenuItem>();
	
		NOMMenuItem delayitem = new NOMMenuItem(AppConstants.GUIID_SEARCHMENU_TRAFFICMENU_PUBLICTRANSITMENU_ITEM_DELAY_ID);
		delayitem.SetLabel(StringFactory.GetString_PublicTransitTypeString(NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_PUBLICTRANSIT_DELAY));
		itemList.add(delayitem);

		NOMMenuItem poitem = new NOMMenuItem(AppConstants.GUIID_SEARCHMENU_TRAFFICMENU_PUBLICTRANSITMENU_ITEM_PASSENGERSTUCK_ID);
		poitem.SetLabel(StringFactory.GetString_PublicTransitTypeString(NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_PUBLICTRANSIT_PASSENGERSTUCK));
		itemList.add(poitem);
		
		NOMMenuItem allitem = new NOMMenuItem(AppConstants.GUIID_SEARCHMENU_TRAFFICMENU_PUBLICTRANSITMENU_ITEM_ALL_ID);
		allitem.SetLabel(StringFactory.GetString_All());
		itemList.add(allitem);
		
		ptMenu.AddMenuList(itemList);
		m_SearchMenu.AddMenu(ptMenu);
		ptitem.SetChildMenu(ptMenu);
	}
	
	private void LaodDriveConditionSearchMenuItems(NOMMenuCoreView rootMenu, NOMMenuItem dcitem)
	{
		NOMMenuCoreView dcMenu = new NOMMenuCoreView(m_Context);
		dcMenu.RegisterMenuID(AppConstants.GUIID_SEARCHMENU_TRAFFICMENU_DRIVINGCONDITIONMENU_ID);
		dcMenu.RegisterParent(rootMenu, m_SearchMenu);
		ArrayList<NOMMenuItem> 	itemList = new ArrayList<NOMMenuItem>();
		
		int startID = AppConstants.GUIID_SEARCHMENU_TRAFFICMENU_DRIVINGCONDITIONMENU_ITEM_JAM_ID;
		int endID = AppConstants.GUIID_SEARCHMENU_TRAFFICMENU_DRIVINGCONDITIONMENU_ITEM_ALL_ID;
		int nCount = endID - startID;
		int stringID0 = NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION_JAM;
		for(int i = 0; i <= nCount; ++i)
		{
			NOMMenuItem item = new NOMMenuItem(i + startID);
			item.SetLabel(StringFactory.GetString_DrivingConditionTypeString(i+stringID0));
			itemList.add(item);
		}
		
		dcMenu.AddMenuList(itemList);
		m_SearchMenu.AddMenu(dcMenu);
		dcitem.SetChildMenu(dcMenu);
	}
	
	private void LaodTrafficSearchMenuItems(NOMMenuCoreView rootMenu, NOMMenuItem trafficitem)
	{
		NOMMenuCoreView trafficMenu = new NOMMenuCoreView(m_Context);
		trafficMenu.RegisterMenuID(AppConstants.GUIID_SEARCHMENU_TRAFFICMENU_ID);
		trafficMenu.RegisterParent(rootMenu, m_SearchMenu);
		ArrayList<NOMMenuItem> 	itemList = new ArrayList<NOMMenuItem>();
		 
		NOMMenuItem ptitem = new NOMMenuItem(AppConstants.GUIID_SEARCHMENU_TRAFFICMENU_ITEM_PUBLICTRANSIT_ID);
		ptitem.SetLabel(StringFactory.GetString_TrafficTitle(NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_PUBLICTRANSIT));
		itemList.add(ptitem);
        LaodPublicTransitSearchMenuItems(trafficMenu, ptitem);

		NOMMenuItem dcitem = new NOMMenuItem(AppConstants.GUIID_SEARCHMENU_TRAFFICMENU_ITEM_DRIVINGCONDITION_ID);
		dcitem.SetLabel(StringFactory.GetString_TrafficTitle(NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION));
		itemList.add(dcitem);
        LaodDriveConditionSearchMenuItems(trafficMenu, dcitem);

		NOMMenuItem allitem = new NOMMenuItem(AppConstants.GUIID_SEARCHMENU_TRAFFICMENU_ITEM_ALL_ID);
		allitem.SetLabel(StringFactory.GetString_All());
		itemList.add(allitem);
		
		trafficMenu.AddMenuList(itemList);
		m_SearchMenu.AddMenu(trafficMenu);
		trafficitem.SetChildMenu(trafficMenu);
	}
	
	public void LoadSearchMenuItems()
	{
		NOMMenuCoreView rootMenu = new NOMMenuCoreView(m_Context);
		rootMenu.RegisterMenuID(AppConstants.GUIID_SEARCHMENU_ROOTMENU_ID);
		rootMenu.RegisterParent(null, m_SearchMenu);
		ArrayList<NOMMenuItem> 	itemList = new ArrayList<NOMMenuItem>();
		NOMMenuItem commnunityitem = new NOMMenuItem(AppConstants.GUIID_SEARCHMENU_ITEM_COMMUNITY_ID);
		commnunityitem.SetLabel(StringFactory.GetString_NewsMainTitle(NOMSystemConstants.NOM_NEWSCATEGORY_COMMUNITY));
		itemList.add(commnunityitem);
        LaodCommunitySearchMenuItems(rootMenu, commnunityitem);
		
		NOMMenuItem localnewsitem = new NOMMenuItem(AppConstants.GUIID_SEARCHMENU_ITEM_LOCALNEWS_ID);
		localnewsitem.SetLabel(StringFactory.GetString_NewsMainTitle(NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS));
		itemList.add(localnewsitem);
        LaodLocalNewsSearchMenuItems(rootMenu, localnewsitem);

		NOMMenuItem trafficitem = new NOMMenuItem(AppConstants.GUIID_SEARCHMENU_ITEM_TRAFFIC_ID);
		trafficitem.SetLabel(StringFactory.GetString_NewsMainTitle(NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC));
		itemList.add(trafficitem);
        LaodTrafficSearchMenuItems(rootMenu, trafficitem);

		rootMenu.AddMenuList(itemList);
		m_SearchMenu.AddRootMenu(rootMenu);
	}
	
	private void LaodCommunityPostMenuItems(NOMMenuCoreView rootMenu, NOMMenuItem commnunityitem)
	{
		NOMMenuCoreView commnityMenu = new NOMMenuCoreView(m_Context);
		commnityMenu.RegisterMenuID(AppConstants.GUIID_POSTMENU_COMMUNITYMENU_ID);
		commnityMenu.RegisterParent(rootMenu, m_PostMenu);
		ArrayList<NOMMenuItem> 	itemList = new ArrayList<NOMMenuItem>();
		
		NOMMenuItem eventitem = new NOMMenuItem(AppConstants.GUIID_POSTMENU_COMMUNITYMENU_ITEM_EVENT_ID);
		eventitem.SetLabel(StringFactory.GetString_CommunityTitle(NOMSystemConstants.NOM_NEWSCATEGORY_COMMUNITY_SUBCATEGORY_COMMUNITYEVENT));
		itemList.add(eventitem);
		
		NOMMenuItem saleitem = new NOMMenuItem(AppConstants.GUIID_POSTMENU_COMMUNITYMENU_ITEM_YARDSALE_ID);
		saleitem.SetLabel(StringFactory.GetString_CommunityTitle(NOMSystemConstants.NOM_NEWSCATEGORY_COMMUNITY_SUBCATEGORY_COMMUNITYYARDSALE));
		itemList.add(saleitem);
		
		NOMMenuItem wikiitem = new NOMMenuItem(AppConstants.GUIID_POSTMENU_COMMUNITYMENU_ITEM_WIKI_ID);
		wikiitem.SetLabel(StringFactory.GetString_CommunityTitle(NOMSystemConstants.NOM_NEWSCATEGORY_COMMUNITY_SUBCATEGORY_COMMUNITYWIKI));
		itemList.add(wikiitem);
		
		commnityMenu.AddMenuList(itemList);
		m_PostMenu.AddMenu(commnityMenu);
		commnunityitem.SetChildMenu(commnityMenu);
	}
	
	private void LaodLocalNewsPostMenuItems(NOMMenuCoreView rootMenu, NOMMenuItem localnewsitem)
	{
		NOMMenuCoreView localNewsMenu = new NOMMenuCoreView(m_Context);
		localNewsMenu.RegisterMenuID(AppConstants.GUIID_POSTMENU_LOCALNEWSMENU_ID);
		localNewsMenu.RegisterParent(rootMenu, m_PostMenu);
		ArrayList<NOMMenuItem> 	itemList = new ArrayList<NOMMenuItem>();
		 
		int startID = AppConstants.GUIID_POSTMENU_LOCALNEWSMENU_ITEM_PUBLICISSUE_ID;
		int endID = AppConstants.GUIID_POSTMENU_LOCALNEWSMENU_ITEM_MISC_ID;
		int nCount = endID - startID;
		int stringID0 = NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_PUBLICISSUE;
		for(int i = 0; i <= nCount; ++i)
		{
			NOMMenuItem item = new NOMMenuItem(i + startID);
			item.SetLabel(StringFactory.GetString_LocalNewsTitle(i+stringID0));
			itemList.add(item);
		}
		
		localNewsMenu.AddMenuList(itemList);
		m_PostMenu.AddMenu(localNewsMenu);
		localnewsitem.SetChildMenu(localNewsMenu);
	}
	
	private void LaodPublicTransitPostMenuItems(NOMMenuCoreView rootMenu, NOMMenuItem ptitem)
	{
		NOMMenuCoreView ptMenu = new NOMMenuCoreView(m_Context);
		ptMenu.RegisterMenuID(AppConstants.GUIID_POSTMENU_TRAFFICMENU_PUBLICTRANSITMENU_ID);
		ptMenu.RegisterParent(rootMenu, m_PostMenu);
		ArrayList<NOMMenuItem> 	itemList = new ArrayList<NOMMenuItem>();
	
		NOMMenuItem delayitem = new NOMMenuItem(AppConstants.GUIID_POSTMENU_TRAFFICMENU_PUBLICTRANSITMENU_ITEM_DELAY_ID);
		delayitem.SetLabel(StringFactory.GetString_PublicTransitTypeString(NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_PUBLICTRANSIT_DELAY));
		itemList.add(delayitem);

		NOMMenuItem poitem = new NOMMenuItem(AppConstants.GUIID_POSTMENU_TRAFFICMENU_PUBLICTRANSITMENU_ITEM_PASSENGERSTUCK_ID);
		poitem.SetLabel(StringFactory.GetString_PublicTransitTypeString(NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_PUBLICTRANSIT_PASSENGERSTUCK));
		itemList.add(poitem);
		
		ptMenu.AddMenuList(itemList);
		m_PostMenu.AddMenu(ptMenu);
		ptitem.SetChildMenu(ptMenu);
	}
	
	private void LaodDriveConditionPostMenuItems(NOMMenuCoreView rootMenu, NOMMenuItem dcitem)
	{
		NOMMenuCoreView dcMenu = new NOMMenuCoreView(m_Context);
		dcMenu.RegisterMenuID(AppConstants.GUIID_POSTMENU_TRAFFICMENU_DRIVINGCONDITIONMENU_ID);
		dcMenu.RegisterParent(rootMenu, m_PostMenu);
		ArrayList<NOMMenuItem> 	itemList = new ArrayList<NOMMenuItem>();
		
		int startID = AppConstants.GUIID_POSTMENU_TRAFFICMENU_DRIVINGCONDITIONMENU_ITEM_JAM_ID;
		int endID = AppConstants.GUIID_POSTMENU_TRAFFICMENU_DRIVINGCONDITIONMENU_ITEM_ROADCLOSURE_ID;
		int nCount = endID - startID;
		int stringID0 = NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION_JAM;
		for(int i = 0; i <= nCount; ++i)
		{
			NOMMenuItem item = new NOMMenuItem(i + startID);
			item.SetLabel(StringFactory.GetString_DrivingConditionTypeString(i+stringID0));
			itemList.add(item);
		}
		
		dcMenu.AddMenuList(itemList);
		m_PostMenu.AddMenu(dcMenu);
		dcitem.SetChildMenu(dcMenu);
	}
	
	private void LaodTrafficPostMenuItems(NOMMenuCoreView rootMenu, NOMMenuItem trafficitem)
	{
		NOMMenuCoreView trafficMenu = new NOMMenuCoreView(m_Context);
		trafficMenu.RegisterMenuID(AppConstants.GUIID_POSTMENU_TRAFFICMENU_ID);
		trafficMenu.RegisterParent(rootMenu, m_PostMenu);
		ArrayList<NOMMenuItem> 	itemList = new ArrayList<NOMMenuItem>();
		 
		NOMMenuItem ptitem = new NOMMenuItem(AppConstants.GUIID_POSTMENU_TRAFFICMENU_ITEM_PUBLICTRANSIT_ID);
		ptitem.SetLabel(StringFactory.GetString_TrafficTitle(NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_PUBLICTRANSIT));
		itemList.add(ptitem);
        LaodPublicTransitPostMenuItems(trafficMenu, ptitem);

		NOMMenuItem dcitem = new NOMMenuItem(AppConstants.GUIID_POSTMENU_TRAFFICMENU_ITEM_DRIVINGCONDITION_ID);
		dcitem.SetLabel(StringFactory.GetString_TrafficTitle(NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION));
		itemList.add(dcitem);
        LaodDriveConditionPostMenuItems(trafficMenu, dcitem);

		
		trafficMenu.AddMenuList(itemList);
		m_PostMenu.AddMenu(trafficMenu);
		trafficitem.SetChildMenu(trafficMenu);
	}
	
	public void LoadPostMenuItems()
	{
		NOMMenuCoreView rootMenu = new NOMMenuCoreView(m_Context);
		rootMenu.RegisterMenuID(AppConstants.GUIID_POSTMENU_ROOTMENU_ID);
		rootMenu.RegisterParent(null, m_PostMenu);
		ArrayList<NOMMenuItem> 	itemList = new ArrayList<NOMMenuItem>();
		NOMMenuItem commnunityitem = new NOMMenuItem(AppConstants.GUIID_POSTMENU_ITEM_COMMUNITY_ID);
		commnunityitem.SetLabel(StringFactory.GetString_NewsMainTitle(NOMSystemConstants.NOM_NEWSCATEGORY_COMMUNITY));
		itemList.add(commnunityitem);
        LaodCommunityPostMenuItems(rootMenu, commnunityitem);

		NOMMenuItem localnewsitem = new NOMMenuItem(AppConstants.GUIID_POSTMENU_ITEM_LOCALNEWS_ID);
		localnewsitem.SetLabel(StringFactory.GetString_NewsMainTitle(NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS));
		itemList.add(localnewsitem);
        LaodLocalNewsPostMenuItems(rootMenu, localnewsitem);

		NOMMenuItem trafficitem = new NOMMenuItem(AppConstants.GUIID_POSTMENU_ITEM_TRAFFIC_ID);
		trafficitem.SetLabel(StringFactory.GetString_NewsMainTitle(NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC));
		itemList.add(trafficitem);
        LaodTrafficPostMenuItems(rootMenu, trafficitem);
		
		
		rootMenu.AddMenuList(itemList);
		m_PostMenu.AddRootMenu(rootMenu);
	}
	
	private void LoadSettingChildMenuItems(NOMMenuCoreView rootMenu, NOMMenuItem settingitem)
	{
		NOMMenuCoreView settingMenu = new NOMMenuCoreView(m_Context);
		settingMenu.RegisterMenuID(AppConstants.GUIID_SETTINGMENU_CONFIGURATIONMENU_ID);
		settingMenu.RegisterParent(rootMenu, m_SettingMenu);
		ArrayList<NOMMenuItem> 	itemList = new ArrayList<NOMMenuItem>();
		 
		NOMMenuItem queryitem = new NOMMenuItem(AppConstants.GUIID_SETTINGMENU_CONFIGURATIONMENU_ITEM_QUERYCONFIGURATION_ID);
		queryitem.SetLabel(StringFactory.GetString_QueryConfiguration());
		itemList.add(queryitem);

		NOMMenuItem useritem = new NOMMenuItem(AppConstants.GUIID_SETTINGMENU_CONFIGURATIONMENU_ITEM_USERCONFIGURATION_ID);
		useritem.SetLabel(StringFactory.GetString_UserConfiguration());
		itemList.add(useritem);

		
        settingMenu.AddMenuList(itemList);
		m_SettingMenu.AddMenu(settingMenu);
		settingitem.SetChildMenu(settingMenu);
	}

	
	public void LoadSettingMenuItems()
	{
		NOMMenuCoreView rootMenu = new NOMMenuCoreView(m_Context);
		rootMenu.RegisterMenuID(AppConstants.GUIID_SETTINGMENU_ROOTMENU_ID);
		rootMenu.RegisterParent(null, m_SettingMenu);
		ArrayList<NOMMenuItem> 	itemList = new ArrayList<NOMMenuItem>();

		NOMMenuItem stditem = new NOMMenuItem(AppConstants.GUIID_SETTINGMENU_ITEM_MAPSTANDARD_ID);
		stditem.SetLabel(StringFactory.GetString_MapStandard());
		itemList.add(stditem);

		NOMMenuItem sateitem = new NOMMenuItem(AppConstants.GUIID_SETTINGMENU_ITEM_MAPSATELLITE_ID);
		sateitem.SetLabel(StringFactory.GetString_MapSatellite());
		itemList.add(sateitem);

		NOMMenuItem hybriditem = new NOMMenuItem(AppConstants.GUIID_SETTINGMENU_ITEM_MAPHYBRID_ID);
		hybriditem.SetLabel(StringFactory.GetString_MapHybrid());
		itemList.add(hybriditem);
		
		NOMMenuItem settingitem = new NOMMenuItem(AppConstants.GUIID_SETTINGMENU_ITEM_CONFIGURATION_ID);
		settingitem.SetLabel(StringFactory.GetString_Configuration());
		itemList.add(settingitem);
        LoadSettingChildMenuItems(rootMenu, settingitem);
		
		NOMMenuItem reloarditem = new NOMMenuItem(AppConstants.GUIID_SETTINGMENU_ITEM_RELOAD_ID);
		reloarditem.SetLabel(StringFactory.GetString_Reload());
		itemList.add(reloarditem);

		NOMMenuItem cleanmapitem = new NOMMenuItem(AppConstants.GUIID_SETTINGMENU_ITEM_CLEARMAP_ID);
		cleanmapitem.SetLabel(StringFactory.GetString_ClearMap());
		itemList.add(cleanmapitem);

		NOMMenuItem mylocationitem = new NOMMenuItem(AppConstants.GUIID_SETTINGMENU_ITEM_SHOWMYLOCATION_ID);
		mylocationitem.SetLabel(StringFactory.GetString_MyLocation());
		itemList.add(mylocationitem);

		NOMMenuItem privacyitem = new NOMMenuItem(AppConstants.GUIID_SETTINGMENU_ITEM_OPENPRIVACYVIEW_ID);
		privacyitem.SetLabel(StringFactory.GetString_Privacy());
		itemList.add(privacyitem);
		
		NOMMenuItem termitem = new NOMMenuItem(AppConstants.GUIID_SETTINGMENU_ITEM_OPENTERMSOFUSEVIEW_ID);
		termitem.SetLabel(StringFactory.GetString_TermOfUse());
		itemList.add(termitem);
		
		rootMenu.AddMenuList(itemList);
		m_SettingMenu.AddRootMenu(rootMenu);
	}
	
	
	public void HandleMenuItemEvent(int menuItemID)
	{
		if(m_Parent != null)
		{	
			m_Parent.CloseMenuAndActionBar();
			m_Parent.HandleMenuItemEvent(menuItemID);
		}	
		
	}
	
	public void UpdateLayout()
	{
		if(m_Parent == null)
			return;
		int bottom = m_Parent.GetActionBarButtonTop();
		int cx;
		int w = (int)NOMPopoverMenu.GetContainerViewWidth();
		int left;
		int h;
		int top;
		int right;
		if(m_SearchMenu != null && m_SearchMenu.getVisibility() == View.VISIBLE)
		{
			cx = m_Parent.GetSearchButtonCenterX();
			left = cx - w/2;
			h = m_SearchMenu.GetLayoutHeight();
			top = bottom - h;
			right = left + w;
			m_SearchMenu.layout(left, top, right, bottom);
		}
		if(m_SettingMenu != null && m_SettingMenu.getVisibility() == View.VISIBLE)
		{
			cx = m_Parent.GetSettingButtonCenterX();
			left = cx - w/2;
			h = m_SettingMenu.GetLayoutHeight();
			top = bottom - h;
			right = left + w;
			m_SettingMenu.layout(left, top, right, bottom);
		}
		if(m_PostMenu != null && m_PostMenu.getVisibility() == View.VISIBLE)
		{
			cx = m_Parent.GetPostButtonCenterX();
			left = cx - w/2;
			h = m_PostMenu.GetLayoutHeight();
			top = bottom - h;
			right = left + w;
			m_PostMenu.layout(left, top, right, bottom);
		}
	}
}
