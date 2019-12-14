package com.xgadget.XSGService;

public class XSGAPIScoreBoardIntScore 
{
    private String 	m_PlayerName;
    private String 	m_PlayerID;
    private int     m_Score;

	public XSGAPIScoreBoardIntScore(String playerName, String playerID, int nScore) 
	{
		// TODO Auto-generated constructor stub
		m_PlayerName = playerName;
		m_PlayerID = playerID;
	    m_Score = nScore;
	}

	public String GetPlayerName() 
	{
		// TODO Auto-generated constructor stub
		return m_PlayerName;
	}

	public String GetPlayerID() 
	{
		// TODO Auto-generated constructor stub
		return m_PlayerID;
	}
	
	public int GetScore()
	{
		return m_Score;
	}
}
