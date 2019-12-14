package com.xgadget.ChuiNiuLite;

import android.widget.TextView;

public class CScoreViewController 
{

	public CScoreViewController() 
	{
		// TODO Auto-generated constructor stub
	}

	public void LoadCurrentScore()
	{
		//TextView  totalscore
		LoadTotalScore();
		LoadLevelOneScores();
		LoadLevelTwoScores();
		LoadLevelThreeScores();
		LoadLevelFourScores();
	}
	
	private void LoadTotalScore()
	{
		TextView text = (TextView)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.totalscore);
        int nTotalScore = ScoreRecord.getTotalWinScore();
		Integer n = nTotalScore;
		String str = n.toString();
	    text.setText(str);	
	}
	
	private void LoadLevelOneScores()
	{
		TextView text = (TextView)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.l1s1score);
        int nScore = ScoreRecord.getScore(CConfiguration.GAME_SKILL_LEVEL_ONE, CConfiguration.GAME_PLAY_LEVEL_ONE);
		Integer n = nScore;
		String str = n.toString();
	    text.setText(str);	

		text = (TextView)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.l1s2score);
        nScore = ScoreRecord.getScore(CConfiguration.GAME_SKILL_LEVEL_TWO, CConfiguration.GAME_PLAY_LEVEL_ONE);
		n = nScore;
		str = n.toString();
	    text.setText(str);	

		text = (TextView)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.l1s3score);
        nScore = ScoreRecord.getScore(CConfiguration.GAME_SKILL_LEVEL_THREE, CConfiguration.GAME_PLAY_LEVEL_ONE);
		n = nScore;
		str = n.toString();
	    text.setText(str);	
	    
	}
	
	private void LoadLevelTwoScores()
	{
		TextView text = (TextView)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.l2s1score);
        int nScore = ScoreRecord.getScore(CConfiguration.GAME_SKILL_LEVEL_ONE, CConfiguration.GAME_PLAY_LEVEL_TWO);
		Integer n = nScore;
		String str = n.toString();
	    text.setText(str);	

		text = (TextView)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.l2s2score);
        nScore = ScoreRecord.getScore(CConfiguration.GAME_SKILL_LEVEL_TWO, CConfiguration.GAME_PLAY_LEVEL_TWO);
		n = nScore;
		str = n.toString();
	    text.setText(str);	

		text = (TextView)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.l2s3score);
        nScore = ScoreRecord.getScore(CConfiguration.GAME_SKILL_LEVEL_THREE, CConfiguration.GAME_PLAY_LEVEL_TWO);
		n = nScore;
		str = n.toString();
	    text.setText(str);	
	}
	
	private void LoadLevelThreeScores()
	{
		TextView text = (TextView)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.l3s1score);
        int nScore = ScoreRecord.getScore(CConfiguration.GAME_SKILL_LEVEL_ONE, CConfiguration.GAME_PLAY_LEVEL_THREE);
		Integer n = nScore;
		String str = n.toString();
	    text.setText(str);	

		text = (TextView)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.l3s2score);
        nScore = ScoreRecord.getScore(CConfiguration.GAME_SKILL_LEVEL_TWO, CConfiguration.GAME_PLAY_LEVEL_THREE);
		n = nScore;
		str = n.toString();
	    text.setText(str);	

		text = (TextView)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.l3s3score);
        nScore = ScoreRecord.getScore(CConfiguration.GAME_SKILL_LEVEL_THREE, CConfiguration.GAME_PLAY_LEVEL_THREE);
		n = nScore;
		str = n.toString();
	    text.setText(str);	
	}

	private void LoadLevelFourScores()
	{
		TextView text = (TextView)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.l4s1score);
        int nScore = ScoreRecord.getScore(CConfiguration.GAME_SKILL_LEVEL_ONE, CConfiguration.GAME_PLAY_LEVEL_FOUR);
		Integer n = nScore;
		String str = n.toString();
	    text.setText(str);	

		text = (TextView)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.l4s2score);
        nScore = ScoreRecord.getScore(CConfiguration.GAME_SKILL_LEVEL_TWO, CConfiguration.GAME_PLAY_LEVEL_FOUR);
		n = nScore;
		str = n.toString();
	    text.setText(str);	

		text = (TextView)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.l4s3score);
        nScore = ScoreRecord.getScore(CConfiguration.GAME_SKILL_LEVEL_THREE, CConfiguration.GAME_PLAY_LEVEL_FOUR);
		n = nScore;
		str = n.toString();
	    text.setText(str);	
	}
}
