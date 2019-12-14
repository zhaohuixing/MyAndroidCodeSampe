package com.xgadget.SimpleGambleWheel;

import android.widget.TextView;

public class CScoreViewController 
{
	public CScoreViewController() 
	{
		// TODO Auto-generated constructor stub
	}

	public void LoadPlayersScore()
	{
		LoadMeScore();
		LoadRoPa1Score();
		LoadRoPa2Score();
		LoadRoPa3Score();
	}

	public void LoadMeScore()
	{
		if(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity == null)
			return;
		
		TextView pText = null;
		
		pText = (TextView)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.me_chipbalancetext);
		if(pText != null)
		{
			int nBalance = ScoreRecord.GetMyChipBalance();
			String str = new String("");
			Integer Value = Integer.valueOf(nBalance);
			str = Value.toString();			
			pText.setText(str);
		}
		
	/*	pText = (TextView)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.me_bigwininfoheaderlabel);
		if(pText != null)
		{
			int nY = ScoreRecord.GetMyMostWinYear();
			Integer YValue = Integer.valueOf(nY);

			int nM = ScoreRecord.GetMyMostWinMonth();
			Integer MValue = Integer.valueOf(nM);

			int nD = ScoreRecord.GetMyMostWinDay();
			Integer DValue = Integer.valueOf(nD);

			String str = new String("");
			str = StringFactory.GetString_BiggestWin() + "(" + YValue.toString() + "-" + MValue.toString() + "-" + DValue.toString() + ")";
			pText.setText(str);
		}	*/	
		
		pText = (TextView)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.me_bigwinchipstext);
		if(pText != null)
		{
			int nBalance = ScoreRecord.GetMyMostWinChips();
			String str = new String("");
			Integer Value = Integer.valueOf(nBalance);
			str = Value.toString();			
			pText.setText(str);
		}
		
		pText = (TextView)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.me_lastwinchipstext);
		if(pText != null)
		{
			int nBalance = ScoreRecord.GetMyLastPlayResult();
			String str = new String("");
			Integer Value = Integer.valueOf(nBalance);
			str = Value.toString();			
			pText.setText(str);
		}
		
	}

	public void LoadRoPa1Score()
	{
		if(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity == null)
			return;

		TextView pText = null;
		
		pText = (TextView)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.ropa1_chipbalancetext);
		if(pText != null)
		{
			int nBalance = ScoreRecord.GetPlayer1ChipBalance();
			String str = new String("");
			Integer Value = Integer.valueOf(nBalance);
			str = Value.toString();			
			pText.setText(str);
		}
		
		pText = (TextView)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.ropa1_bigwinchipstext);
		if(pText != null)
		{
			int nBalance = ScoreRecord.GetPlayer1MostWinChips();
			String str = new String("");
			Integer Value = Integer.valueOf(nBalance);
			str = Value.toString();			
			pText.setText(str);
		}
		
		pText = (TextView)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.ropa1_lastwinchipstext);
		if(pText != null)
		{
			int nBalance = ScoreRecord.GetPlayer1LastPlayResult();
			String str = new String("");
			Integer Value = Integer.valueOf(nBalance);
			str = Value.toString();			
			pText.setText(str);
		}
	}

	public void LoadRoPa2Score()
	{
		if(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity == null)
			return;

		TextView pText = null;
	
		pText = (TextView)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.ropa2_chipbalancetext);
		if(pText != null)
		{
			int nBalance = ScoreRecord.GetPlayer2ChipBalance();
			String str = new String("");
			Integer Value = Integer.valueOf(nBalance);
			str = Value.toString();			
			pText.setText(str);
		}
		
		pText = (TextView)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.ropa2_bigwinchipstext);
		if(pText != null)
		{
			int nBalance = ScoreRecord.GetPlayer2MostWinChips();
			String str = new String("");
			Integer Value = Integer.valueOf(nBalance);
			str = Value.toString();			
			pText.setText(str);
		}
		
		pText = (TextView)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.ropa2_lastwinchipstext);
		if(pText != null)
		{
			int nBalance = ScoreRecord.GetPlayer2LastPlayResult();
			String str = new String("");
			Integer Value = Integer.valueOf(nBalance);
			str = Value.toString();			
			pText.setText(str);
		}
	}

	public void LoadRoPa3Score()
	{
		if(SimpleGambleWheel.m_ApplicationController.m_CurrentActivity == null)
			return;

		TextView pText = null;

		pText = (TextView)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.ropa3_chipbalancetext);
		if(pText != null)
		{
			int nBalance = ScoreRecord.GetPlayer3ChipBalance();
			String str = new String("");
			Integer Value = Integer.valueOf(nBalance);
			str = Value.toString();			
			pText.setText(str);
		}
		
		pText = (TextView)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.ropa3_bigwinchipstext);
		if(pText != null)
		{
			int nBalance = ScoreRecord.GetPlayer3MostWinChips();
			String str = new String("");
			Integer Value = Integer.valueOf(nBalance);
			str = Value.toString();			
			pText.setText(str);
		}
		
		pText = (TextView)SimpleGambleWheel.m_ApplicationController.m_CurrentActivity.findViewById(R.id.ropa3_lastwinchipstext);
		if(pText != null)
		{
			int nBalance = ScoreRecord.GetPlayer3LastPlayResult();
			String str = new String("");
			Integer Value = Integer.valueOf(nBalance);
			str = Value.toString();			
			pText.setText(str);
		}
	}
}
