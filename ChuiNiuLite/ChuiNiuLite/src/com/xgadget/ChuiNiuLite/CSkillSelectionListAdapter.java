package com.xgadget.ChuiNiuLite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CSkillSelectionListAdapter extends BaseAdapter
{
	private Context 			m_Context;
	LayoutInflater 				m_Inflater;
	
	public CSkillSelectionListAdapter(Context context)
    {
		this.m_Context = context;
		m_Inflater = LayoutInflater.from(this.m_Context);
	}

	public View getView(int position, View convertView, ViewGroup root) 
    {
        convertView = m_Inflater.inflate(R.layout.online_player_item, null);
		
		ImageView playeravatar = (ImageView) convertView.findViewById(R.id.deviceicon);
		TextView playername =(TextView) convertView.findViewById(R.id.playername);

		playeravatar.setImageResource(R.drawable.ic_launcher);

		int nLevel = position/3;
		int nSkill = position%3;
		
		String text = StringFactory.GetString_LevelSkillLabel(nLevel, nSkill);
		
		playername.setText(text);
		
		return convertView;
	}
    
	public int getCount() 
    {
		return 12;
	}

	public Object getItem(int position) 
    {
		Integer nRet = position;
		return nRet;//this.m_PlayerList.get(position);
	}

	public long getItemId(int position) 
    {
		return position;
	}
}
