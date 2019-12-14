package com.xgadget.SimpleGambleWheel;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CInAppPurchaseListAdapter extends BaseAdapter
{
	private Context 			m_Context;
	private List<String> 		m_ItemList;
	LayoutInflater 				m_Inflater;
	private int					m_nStoreID;
	
	public CInAppPurchaseListAdapter(Context context,List<String> list, int nStoreID)
    {
		this.m_Context = context;
		this.m_ItemList = list;
		m_Inflater = LayoutInflater.from(this.m_Context);
		m_nStoreID = nStoreID;
	}

	public View getView(int position, View convertView, ViewGroup root) 
    {
        convertView = m_Inflater.inflate(R.layout.online_player_item, null);
		
		ImageView storeimahe = (ImageView) convertView.findViewById(R.id.deviceicon);
		TextView tiemname =(TextView) convertView.findViewById(R.id.playername);

		if(this.m_nStoreID == InAppPurchaseConstants.GOOGLEPLAYID)
			storeimahe.setImageResource(R.drawable.gp_64);
		else
			storeimahe.setImageResource(R.drawable.aicon_64);//?????????????????
			
		String text = String.valueOf(InAppPurchaseConstants.GetBuyChipsByIndex(position)) + " " + StringFactory.GetString_Chips();
		tiemname.setText(text);
		
		return convertView;
	}
    
	public int getCount() 
    {
		return this.m_ItemList.size();
	}

	public Object getItem(int position) 
    {
		return this.m_ItemList.get(position);
	}

	public long getItemId(int position) 
    {
		return position;
	}
}
