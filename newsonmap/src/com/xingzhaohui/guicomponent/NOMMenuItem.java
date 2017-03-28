package com.xingzhaohui.guicomponent;

import android.graphics.Bitmap;

public class NOMMenuItem 
{
	private NOMMenuCoreView			m_ChildMenu;
	private int 					m_MenuID;
	private Bitmap					m_Icon;
	private String					m_Label;
	
	public NOMMenuItem(int nID) 
	{
		// TODO Auto-generated constructor stub
		m_MenuID = nID;
		m_ChildMenu = null;
		m_Icon = null;
		m_Label = null;
	}
	
	public void SetMenuID(int nID)
	{
		m_MenuID = nID;
	}
	
	public int GetMenuID()
	{
		return m_MenuID;
	}

	public void SetChildMenu(NOMMenuCoreView child)
	{
		m_ChildMenu = child;
	}
	
	public NOMMenuCoreView GetChildMenu()
	{
		return m_ChildMenu;
	}
	
	public Bitmap GetIcon()
	{
		return m_Icon;
	}

	public void SetIcon(Bitmap icon)
	{
		m_Icon = icon;
	}

	public String GetLabel()
	{
		return m_Label;
	}
	
	public void SetLabel(String label)
	{
		m_Label = label;
	}
}
