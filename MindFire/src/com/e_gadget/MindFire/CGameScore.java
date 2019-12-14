package com.e_gadget.MindFire;

public class CGameScore 
{
	private int 		m_Point;
	private int 		m_nLastScore;
	private float 		m_fLastScore;
	private float 		m_fHighScore;
	private float 		m_fAveScore;
	private int 		m_nPlays;
	
	public CGameScore()
	{
		m_Point = -1;
		m_nLastScore = 0;
		m_fLastScore = 0.0f;
		m_fHighScore = 0.0f;
		m_fAveScore = 0.0f;
		m_nPlays = -1;
	}

	public CGameScore(int nPoint, int nScore)
	{
		m_Point = nPoint;
		m_nLastScore = nScore;
		m_fLastScore = ((float)nScore)/((float)CDeal.CARD_NUMBER_DEAL);
		m_fHighScore = m_fLastScore;
		m_fAveScore = m_fLastScore;
		m_nPlays = 1;
	}
	
	public CGameScore(int np, int nls, float fls, float fhs, float fas, int n)
	{
		m_Point = np;
		m_nLastScore = nls;
		m_fLastScore = fls;
		m_fHighScore = fhs;
		m_fAveScore = fas;
		m_nPlays = n;
	}
	
	public void Set(int np, int nls, float fls, float fhs, float fas, int n)
	{
		m_Point = np;
		m_nLastScore = nls;
		m_fLastScore = fls;
		m_fHighScore = fhs;
		m_fAveScore = fas;
		m_nPlays = n;
	}
	
	public String GetPointText()
	{
		Integer n = m_Point;
		String text = n.toString();
		return text;
	}

	public String GetPlayText()
	{
		Integer n = m_nPlays;
		String text = n.toString();
		return text;
	}
	
	public String GetLastScoreIntText()
	{
		Integer n = m_nLastScore;
		String text = n.toString();
		return text;
	}

	public String GetLastScorePercentText()
	{
		Integer n = (int)(m_fLastScore*100);
		String text = n.toString();
		return text;
	}

	public String GetHighScorePercentText()
	{
		Integer n = (int)(m_fHighScore*100);
		String text = n.toString();
		return text;
	}

	public String GetAveScorePercentText()
	{
		Integer n = (int)(m_fAveScore*100);
		String text = n.toString();
		return text;
	}
	
	public int GetPoint()
	{
		return m_Point;
	}

	public int GetPlay()
	{
		return m_nPlays;
	}
	
	public int GetLastScore()
	{
		return m_nLastScore;
	}

	public float GetLastScorePercent()
	{
		return m_fLastScore;
	}

	public float GetHighScorePercent()
	{
		return m_fHighScore;
	}

	public float GetAveScorePercent()
	{
		return m_fAveScore;
	}
	
	public boolean IsValid()
	{
		boolean bRet = (0 <= m_Point && m_Point <= 28561);
		
		return bRet;
	}
	
	public void UpdateScore(int nLastScore)
	{
		m_nLastScore = nLastScore;
		m_fLastScore = ((float)nLastScore)/((float)CDeal.CARD_NUMBER_DEAL);
		
		if(m_fHighScore < m_fLastScore)
			m_fHighScore = m_fLastScore;
		
		float fTotal = m_fAveScore*((float)m_nPlays) + m_fLastScore; 
		m_fAveScore = fTotal/((float)(m_nPlays+1));

		m_nPlays += 1;
	}
	
}
