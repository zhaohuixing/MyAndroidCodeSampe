package com.xgadget.SimpleGambleWheel;

import java.util.List;

//import com.xgadget.XSGService.XSGAPIConstants;
import com.xgadget.XSGService.XSGAPIScoreBoardIntScore;
import com.xgadget.XSGService.XSGAPIUser;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
//import android.widget.ImageView;
import android.widget.TextView;

public class CXSGAPIScoreboardListAdapter extends BaseAdapter
{
	private Context 			m_Context;
	private List<XSGAPIScoreBoardIntScore> 	m_PlayerScoreList;
	LayoutInflater 				m_Inflater;
	
	public CXSGAPIScoreboardListAdapter(Context context,List<XSGAPIScoreBoardIntScore> list)
    {
		this.m_Context = context;
		this.m_PlayerScoreList = list;
		m_Inflater = LayoutInflater.from(this.m_Context);
	}

	public View getView(int position, View convertView, ViewGroup root) 
    {
        convertView = m_Inflater.inflate(R.layout.scoreboard_item_view, null);
		
		TextView playername =(TextView) convertView.findViewById(R.id.sbplayername);
		TextView playerscore =(TextView) convertView.findViewById(R.id.sbplayerscore);

		playerscore.setGravity(Gravity.CENTER);
		RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 80);
		playername.setLayoutParams(param);
				
		XSGAPIScoreBoardIntScore userscore = m_PlayerScoreList.get(position);
		
		String text = userscore.GetPlayerName();
		if(userscore.GetPlayerID().equals(XSGAPIUser.localUser().GetUserID()) == true)
		{
			
			text = text + " (" + StringFactory.GetString_Me() + ")";
			playername.setTextColor(Color.BLUE);
		}
		
		playername.setText(text);
		
		String szScore = String.valueOf(userscore.GetScore());
		playerscore.setText(szScore);
		//convertView.setFocusable(false);
		//convertView.setEnabled(false);
		convertView.setVisibility(View.VISIBLE);
		convertView.invalidate();
		convertView.setFocusable(true);
		
		return convertView;
	}
    
	public int getCount() 
    {
		return this.m_PlayerScoreList.size();
	}

	public Object getItem(int position) 
    {
		return this.m_PlayerScoreList.get(position);
	}

	public long getItemId(int position) 
    {
		return position;
	}
}
