package com.xgadget.SimpleGambleWheel;

public class ScoreRecord 
{
	static GameScore g_Score = null;

	public static void IntiScore()
	{
		g_Score = new GameScore();
		g_Score.Load();
	}	

	public static void SaveScore()
	{
		g_Score.Save();
	}

	public static void ReleaseScore()
	{
		g_Score.Save();
	    g_Score = null;
	}	

	public static void Reload()
	{
	    if(g_Score != null)
	    {
	        g_Score.Load();
	    }
	    else
	    {
	        ScoreRecord.IntiScore();
	    }
	}

	public static void SetPlayerLastPlayResult(int nChips, int index)
	{
	    if(index == 0)
	    {
	        ScoreRecord.SetMyLastPlayResult(nChips);
	    }
	    else if(index == 1)
	    {
	        ScoreRecord.SetPlayer1LastPlayResult(nChips);
	    }
	    else if(index == 2)
	    {
	        ScoreRecord.SetPlayer2LastPlayResult(nChips);
	    }
	    else if(index == 3)
	    {
	        ScoreRecord.SetPlayer3LastPlayResult(nChips);
	    }
	}

	public static void SetPlayerChipBalance(int nChips, int index)
	{
	    if(index == 0)
	    {
	        ScoreRecord.SetMyChipBalance(nChips);
	    }
	    else if(index == 1)
	    {
	        ScoreRecord.SetPlayer1ChipBalance(nChips);
	    }
	    else if(index == 2)
	    {
	        ScoreRecord.SetPlayer2ChipBalance(nChips);
	    }
	    else if(index == 3)
	    {
	        ScoreRecord.SetPlayer3ChipBalance(nChips);
	    }
	}

	public static void SetMyChipBalance(int nChips)
	{
	    g_Score.SetMyChipBalance(nChips);
	}

	public static void SetMyLastPlayResult(int nChips)
	{
	    g_Score.SetMyLastPlayResult(nChips);
	}


	public static void SetPlayer1ChipBalance(int nChips)
	{
	    g_Score.SetPlayer1ChipBalance(nChips);
	}

	public static void SetPlayer1LastPlayResult(int nChips)
	{
	    g_Score.SetPlayer1LastPlayResult(nChips);
	}


	public static void SetPlayer2ChipBalance(int nChips)
	{
	    g_Score.SetPlayer2ChipBalance(nChips);
	}

	public static void SetPlayer2LastPlayResult(int nChips)
	{
	    g_Score.SetPlayer2LastPlayResult(nChips);
	}

	public static void SetPlayer3ChipBalance(int nChips)
	{
	    g_Score.SetPlayer3ChipBalance(nChips);
	}

	public static void SetPlayer3LastPlayResult(int nChips)
	{
	    g_Score.SetPlayer3LastPlayResult(nChips);
	}

	public static void SetGameType(int nType)
	{
	    g_Score.SetGameType(nType);
	}

	public static int GetGameType()
	{
	    return g_Score.GetGameType();
	}

	public static void SetThemeType(int nType)
	{
	    g_Score.SetThemeType(nType);
	}
	
	public static int GetThemeType()
	{
	    return g_Score.GetThemeType();
	}
	
	public static void SetOfflineBetMethod(int nWay)
	{
	    g_Score.SetOfflineBetMethod(nWay);
	}

	public static int GetOfflineBetMethod()
	{
	    return g_Score.GetOfflineBetMethod();
	}

	public static void SetSoundEnable(boolean bEnable)
	{
	    g_Score.SetSoundEnable(bEnable);
	}

	public static boolean GetSoundEnable()
	{
	    return g_Score.GetSoundEnable();
	}

	public static void SetPlayTurnType(int nPlayTurnType)
	{
	    g_Score.SetPlayTurnType(nPlayTurnType);
	}
	public static int GetPlayTurnType()
	{
	    return g_Score.GetPlayTurnType();
	}

	public static int GetSavedRecord()
	{
	    return g_Score.GetSavedRecord();
	}

	public static int GetMyMostWinYear()
	{
	    return g_Score.GetMyMostWinYear();
	}

	public static int GetMyMostWinMonth()
	{
	    return g_Score.GetMyMostWinMonth();
	}

	public static int GetMyMostWinDay()
	{
	    return g_Score.GetMyMostWinDay();
	}

	public static int GetMyMostWinChips()
	{
	    return g_Score.GetMyMostWinChips();
	}

	public static int GetMyChipBalance()
	{
	    return g_Score.GetMyChipBalance();
	}

	public static int GetMyLastPlayResult()
	{
	    return g_Score.GetMyLastPlayResult();
	}

	public static int GetPlayer1MostWinYear()
	{
	    return g_Score.GetPlayer1MostWinYear();
	}

	public static int GetPlayer1MostWinMonth()
	{
	    return g_Score.GetPlayer1MostWinMonth();
	}

	public static int GetPlayer1MostWinDay()
	{
	    return g_Score.GetPlayer1MostWinDay();
	}

	public static int GetPlayer1MostWinChips()
	{
	    return g_Score.GetPlayer1MostWinChips();
	}

	public static int GetPlayer1ChipBalance()
	{
	    return g_Score.GetPlayer1ChipBalance();
	}

	public static int GetPlayer1LastPlayResult()
	{
	    return g_Score.GetPlayer1LastPlayResult();
	}

	public static int GetPlayer2MostWinYear()
	{
	    return g_Score.GetPlayer2MostWinYear();
	}

	public static int GetPlayer2MostWinMonth()
	{
	    return g_Score.GetPlayer2MostWinMonth();
	}

	public static int GetPlayer2MostWinDay()
	{
	    return g_Score.GetPlayer2MostWinDay();
	}

	public static int GetPlayer2MostWinChips()
	{
	    return g_Score.GetPlayer2MostWinChips();
	}

	public static int GetPlayer2ChipBalance()
	{
	    return g_Score.GetPlayer2ChipBalance();
	}

	public static int GetPlayer2LastPlayResult()
	{
	    return g_Score.GetPlayer2LastPlayResult();
	}

	public static int GetPlayer3MostWinYear()
	{
	    return g_Score.GetPlayer3MostWinYear();
	}

	public static int GetPlayer3MostWinMonth()
	{
	    return g_Score.GetPlayer3MostWinMonth();
	}

	public static int GetPlayer3MostWinDay()
	{
	    return g_Score.GetPlayer3MostWinDay();
	}

	public static int GetPlayer3MostWinChips()
	{
	    return g_Score.GetPlayer3MostWinChips();
	}

	public static int GetPlayer3ChipBalance()
	{
	    return g_Score.GetPlayer3ChipBalance();
	}

	public static int GetPlayer3LastPlayResult()
	{
	    return g_Score.GetPlayer3LastPlayResult();
	}
	
}
