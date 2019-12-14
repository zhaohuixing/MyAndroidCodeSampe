package com.xgadget.SimpleGambleWheel;

import java.util.List;

import com.xgadget.XSGService.XSGAPIConstants;
import com.xgadget.XSGService.XSGAPIUser;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class COnlinePlayerListAdapter extends BaseAdapter
{
	private Context 			m_Context;
	private List<XSGAPIUser> 	m_PlayerList;
	LayoutInflater 				m_Inflater;
	
	public COnlinePlayerListAdapter(Context context,List<XSGAPIUser> list)
    {
		this.m_Context = context;
		this.m_PlayerList = list;
		m_Inflater = LayoutInflater.from(this.m_Context);
	}

	public View getView(int position, View convertView, ViewGroup root) 
    {
        convertView = m_Inflater.inflate(R.layout.online_player_item, null);
		
		ImageView playeravatar = (ImageView) convertView.findViewById(R.id.deviceicon);
		TextView playername =(TextView) convertView.findViewById(R.id.playername);

		XSGAPIUser user =m_PlayerList.get(position);
		int id = user.GetUserType();
		if(id == XSGAPIConstants.XSGAPIUSER_DEVICETYPE_IOS_GK || id == XSGAPIConstants.XSGAPIUSER_DEVICETYPE_IOS_NONGK)
		{
			playeravatar.setImageResource(R.drawable.apple_64);
		}
		else if(id == XSGAPIConstants.XSGAPIUSER_DEVICETYPE_ANDROID)
		{
			playeravatar.setImageResource(R.drawable.android_64);
		}
		else if(id == XSGAPIConstants.XSGAPIUSER_DEVICETYPE_KINDLEFIRE)
		{
			playeravatar.setImageResource(R.drawable.kindlefire_64);
		}
		else
		{
			playeravatar.setImageResource(R.drawable.player_64);
		}
		
		String text = user.GetUserName();
		if(user.GetUserID().equals(XSGAPIUser.localUser().GetUserID()) == true)
		{
			
			text = text + " (" + StringFactory.GetString_Me() + ")";
			playername.setTextColor(Color.BLUE);
		}
		
		playername.setText(text);
		
		String szID = user.GetUserID();
		String localID = XSGAPIUser.localUser().GetUserID();
		if(szID.equals(localID) == true)
		{
//			convertView.setEnabled(false);
			convertView.setFocusable(true);
		}
		else
		{
//			convertView.setEnabled(true);
			convertView.setFocusable(false);
		}
		
       	//nick.setText(be.getNick());
		//trends.setText(be.getTrends());
		
		return convertView;
	}
    
	public int getCount() 
    {
		return this.m_PlayerList.size();
	}

	public Object getItem(int position) 
    {
		return this.m_PlayerList.get(position);
	}

	public long getItemId(int position) 
    {
		return position;
	}
}
