package com.xingzhaohui.guicomponent;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class NOMMenuAdapter extends BaseAdapter 
{
    private Context 			m_Context;
    private List<NOMMenuItem> 	m_ItemList;

	public NOMMenuAdapter(Context context, List<NOMMenuItem> itemList) 
	{
		// TODO Auto-generated constructor stub
		m_Context = context;
		m_ItemList = itemList;
	}

	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
		if(m_ItemList != null && 0 < m_ItemList.size())
		{
			return m_ItemList.size();
		}
		
		return 1;
	}

	@Override
	public Object getItem(int position) 
	{
		// TODO Auto-generated method stub
		if(m_ItemList != null && 0 < m_ItemList.size())
		{
			return m_ItemList.get(position);
		}
		
		return null;
	}

	@Override
	public long getItemId(int position) 
	{
		// TODO Auto-generated method stub
		if(m_ItemList != null && 0 < m_ItemList.size())
		{
			NOMMenuItem item = m_ItemList.get(position);
			if(item != null)
			{
				int nID = item.GetMenuID();
				return nID;
			}
		}
		
		return -1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		// TODO Auto-generated method stub
		NOMMenuItemContainerView view;
		if(convertView != null)
			view = (NOMMenuItemContainerView)convertView;
		else 
			view = new NOMMenuItemContainerView(m_Context);
		if(m_ItemList != null && 0 < m_ItemList.size())
		{
			NOMMenuItem item = m_ItemList.get(position);
			if(item != null)
			{
				if(item.GetIcon() != null)
					view.SetIcon(item.GetIcon());
				if(item.GetLabel() != null && 0 < item.GetLabel().length())
					view.SetLabel(item.GetLabel());
			}
		}
		int t = position*((int)NOMPopoverMenu.GetMenuItemHeight());
		int b = (position+1)*((int)NOMPopoverMenu.GetMenuItemHeight());
		view.layout(0, t, (int)NOMPopoverMenu.GetMenuItemWidth(), b);
		return view;
	}

}
