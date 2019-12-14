package com.xgadget.SimpleGambleWheel;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AbsListView.LayoutParams;
//import android.view.View;
//import android.view.Window;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
//import android.widget.TextView;

//import com.amazonaws.services.simpledb.model.Item;
//import com.xgadget.XSGService.XSGAPIGameServiceManager;
//import com.xgadget.XSGService.XSGAPIReleaseConfigure;
import com.xgadget.XSGService.XSGAPIScoreBoardIntScore;
//import com.xgadget.XSGService.XSGAPIUser;

public class CXSGAPIScoreboardController 
{
/*	
	protected ArrayList<XSGAPIUser>	m_CurrentPlayerList;
	protected boolean				m_bShowingSearching;
	
	private boolean					m_bIndicatorShown;
	private ListView 				m_PlayerListView;
	private XSGAPIUser				m_pSelectedPeer;
	private long 					m_TimeStartShowWarnText;
	private boolean					m_bShowWarnText;*/
	
	protected ArrayList<XSGAPIScoreBoardIntScore>	m_CurrentScoreList;
	private ListView 				m_ScoreListView;
	private boolean					m_bUpdateShown;
	
	public CXSGAPIScoreboardController() 
	{
		// TODO Auto-generated constructor stub
		m_CurrentScoreList = new ArrayList<XSGAPIScoreBoardIntScore>();
		m_ScoreListView = null;
		m_bUpdateShown = false;
	}
	

	public void LoadOnlinePlayerList()
	{
		m_CurrentScoreList.clear();
		m_ScoreListView = null;
		SimpleGambleWheel.m_ApplicationController.m_GameController.LoadXSGScoreBoard();
		m_bUpdateShown = false;
	}
	
	public void ReloadCachedScoreList()
	{
		m_ScoreListView = (ListView) SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.xsgapicoreboardlistview);
		m_ScoreListView.setAdapter(new CXSGAPIScoreboardListAdapter(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity, m_CurrentScoreList));
		m_ScoreListView.setOnItemClickListener(new OnItemClickListener() 
		{
        	@Override
        	public void onItemClick(AdapterView<?> a, View v, int position, long id) 
        	{ 
        		m_ScoreListView.invalidate();
        	}  
        });
//		m_ScoreListView.invalidateViews();
//		LayoutParams param = new LayoutParams(LayoutParams.WRAP_CONTENT , LayoutParams.WRAP_CONTENT);
//		m_ScoreListView.setLayoutParams(param);
		
//		m_bUpdateShown = true;
		
		//View view = (View) SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.XSGAPIScoreBoardViewGroup);
		//if(view != null)
		//{
			//view.layout(0, 0, GameLayoutConstant.GetCurrentScreenWidth(), GameLayoutConstant.GetCurrentScreenHeight());
			//view.measure(GameLayoutConstant.GetCurrentScreenWidth(), GameLayoutConstant.GetCurrentScreenHeight());
		//	ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT);
		//	view.setLayoutParams(param);
		//}
		
/*		m_ScoreListView.setVisibility(View.VISIBLE);
		m_ScoreListView.invalidate();
		if(m_ScoreListView.getParent() != null)
		{	
			m_ScoreListView.getParent().bringChildToFront(m_ScoreListView);
			m_ScoreListView.getParent().childDrawableStateChanged(m_ScoreListView);
		}	
		m_ScoreListView.invalidateViews();*/
	}

	public void OnTimerEvent()
	{
		if(m_bUpdateShown == true)
		{
			if(m_ScoreListView != null)
			{
				m_ScoreListView.invalidateViews();
				m_bUpdateShown = false;
			}
		}
	}
	
	
	public void ProcessXSGAPIScoreList(List<XSGAPIScoreBoardIntScore> scorelist)
	{
		if(scorelist != null && 0 < scorelist.size() )
		{	
			for (XSGAPIScoreBoardIntScore item : scorelist) 
			{
				m_CurrentScoreList.add(new XSGAPIScoreBoardIntScore(item.GetPlayerName(), item.GetPlayerID(), item.GetScore()));
			}
			ReloadCachedScoreList();
			SimpleGambleWheel.m_ApplicationController.m_GameController.LoadRemainScoreList();
			//boolean bRet = SimpleGambleWheel.m_ApplicationController.m_GameController.LoadRemainScoreList();
			//if(bRet == false)
			//	ReloadCachedScoreList();
		}	
	}	
	
	public void ProcessXSGAPIScoreList2(List<XSGAPIScoreBoardIntScore> scorelist, boolean bQueryNext)
	{
		if(scorelist != null && 0 < scorelist.size() )
		{	
			for (XSGAPIScoreBoardIntScore item : scorelist) 
			{
				m_CurrentScoreList.add(new XSGAPIScoreBoardIntScore(item.GetPlayerName(), item.GetPlayerID(), item.GetScore()));
			}
			if(bQueryNext == false)
				ReloadCachedScoreList();
			else
				SimpleGambleWheel.m_ApplicationController.m_GameController.LoadRemainScoreList();
		}	
	}
	
}
