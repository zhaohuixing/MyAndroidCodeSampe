package com.xgadget.ChuiNiuLite;

import com.xgadget.uimodule.ResourceHelper;

import android.content.Context;
import android.content.SharedPreferences;

public class ScoreRecord 
{
	private static final String FLYC_PreferenceKey = "KeyFLCScorePref";
	private static final String XGADGET_ONLINE_USER_NICKNAME_KEY = "GAME_ONLINE_USER_NICKNAME_KEY_20120806121618_0001";
	private static final String XGADGET_ONLINE_USER_PLAYERID_KEY = "GAME_ONLINE_USER_PLAYERID_KEY_20120806121730_0002";


	private static int		g_score[];     
	private static int		g_point[];     
	private static int		g_lastWinLevel = -1;
	private static int		g_lastWinSkill = -1;
	private static int		g_TotalScore = 0;
	private static boolean  g_bShouldAchievement1Report = false;
	private static boolean  g_bShouldAchievement2Report = false;
	private static boolean 	g_bShouldAchievement3Report = false;
	private static boolean  g_bShouldAchievement4Report = false;
	private static boolean  g_bDisableAWSService = false;

	public static void InitializeScoreRecord() 
	{
		// TODO Auto-generated constructor stub
		g_score = new int[12];     
		for(int i = 0; i < 12; ++i)
			g_score[i] = 0;
		
		g_point = new int[12];     
		for(int i = 0; i < 12; ++i)
			g_point[i] = 0;
		
		ScoreRecord.loadRecord();
	}
	
	public static Context GetSharedContext()
	{
		return ResourceHelper.GetResourceContext();
	}
	
	public static SharedPreferences GetDefaultPreference()
	{
		Context context = ResourceHelper.GetResourceContext();
		SharedPreferences prefs = context.getSharedPreferences(FLYC_PreferenceKey, 0);
		return prefs;
	}
	
	public static SharedPreferences.Editor GetDefaultPreferenceEditor()
	{
		Context context = ResourceHelper.GetResourceContext();
		SharedPreferences prefs = context.getSharedPreferences(FLYC_PreferenceKey, 0);
		return prefs.edit();
	}
	
	public static int getLastWinSkill()
	{
		return g_lastWinSkill;
	}	

	public static int getLastWinLevel()
	{
		return g_lastWinLevel;
	}	


	public static void SaveLastWinSkill(SharedPreferences.Editor prefs)
	{
		String sKey = StringFactory.GetString_PreferenceLastWinSkillKey();	
		prefs.putInt(sKey,g_lastWinSkill);
		prefs.commit();
	}

	public static void SaveLastWinLevel(SharedPreferences.Editor prefs)
	{
		String sKey = StringFactory.GetString_PreferenceLastWinLevelKey();	
		prefs.putInt(sKey,g_lastWinLevel);
		prefs.commit();
	}


	public static void SaveSkill(SharedPreferences.Editor prefs)
	{
		int nSkill = CConfiguration.getGameSkill();
		
		String sKey = StringFactory.GetString_PreferenceSkillKey();	
		prefs.putInt(sKey,nSkill);
		prefs.commit();
	}

	public static void SaveLevel(SharedPreferences.Editor prefs)
	{
		int nLevel = CConfiguration.getGameLevel();
		String sKey = StringFactory.GetString_PreferenceLevelKey();	
		prefs.putInt(sKey,nLevel);
		prefs.commit();
	}

	public static void SaveSound(SharedPreferences.Editor prefs)
	{
		boolean bEnable = CConfiguration.IsSoundEnable();
		
		String sKey = StringFactory.GetString_PreferenceSoundKey();	
		prefs.putBoolean(sKey, bEnable);
	}

	public static void SaveBackground(SharedPreferences.Editor prefs)
	{
		//BOOL bEnable = [Configuration isPaperBackground];
		int nSetting = CConfiguration.GetGameBackground();
		String sKey = StringFactory.GetString_PreferenceBackgroundKey();	
		prefs.putInt(sKey, nSetting);
	}

	public static int getScore(int nSkill, int nLevel)
	{
		int nRet = 0;
	    
		for(int i = 0; i < CConfiguration.GAME_SKILL_LEVELS; ++i)
		{
			for(int j = 0; j < CConfiguration.GAME_PLAY_LEVELS; ++j)
			{
				if(i == nSkill && j == nLevel)
				{
					int k = nSkill*CConfiguration.GAME_PLAY_LEVELS + nLevel;
					if(12 <= k)
						return nRet;
					nRet = g_score[k];
					break;
				}	
			}	
		}	
						
		return nRet;
	}

	public static int getPoint(int nSkill, int nLevel)
	{
		int nRet = 0;
		
		for(int i = 0; i < CConfiguration.GAME_SKILL_LEVELS; ++i)
		{
			for(int j = 0; j < CConfiguration.GAME_PLAY_LEVELS; ++j)
			{
				if(i == nSkill && j == nLevel)
				{
					int k = nSkill*CConfiguration.GAME_PLAY_LEVELS + nLevel;
					if(12 <= k)
						return nRet;
					nRet = g_point[k];
					break;
				}	
			}	
		}	
		
		return nRet;
	}

	public static void SavePoint(SharedPreferences.Editor prefs, int nSkill, int nLevel)
	{
		int nPoint = ScoreRecord.getPoint(nSkill, nLevel);
		int k = nSkill*CConfiguration.GAME_PLAY_LEVELS + nLevel;
		if(12 <= k)
			return;
		
		String sKey = StringFactory.GetString_PreferencePointKey(k);	
		prefs.putInt(sKey, nPoint);
	}

	public static void SaveScore(SharedPreferences.Editor prefs, int nSkill, int nLevel)
	{
		int nScore = ScoreRecord.getScore(nSkill, nLevel);
		int k = nSkill*CConfiguration.GAME_PLAY_LEVELS + nLevel;
		if(12 <= k)
			return;
		
		String sKey = StringFactory.GetString_PreferenceScoreKey(k);	
		prefs.putInt(sKey, nScore);
	}


	public static void SaveThunderTheme(SharedPreferences.Editor prefs)
	{
		boolean bEnable = CConfiguration.getThunderTheme();
		String sKey = StringFactory.GetString_PreferenceThunderThemeKey();	
		prefs.putBoolean(sKey, bEnable);
	}

	public static void SaveClock(SharedPreferences.Editor prefs)
	{
//		boolean bEnable = !CConfiguration.isClockShown();
//		String sKey = StringFactory.GetString_PreferenceClockKey];	
//		[prefs setBool:bEnable forKey:sKey];
	}

	public static void SaveTotalScore(SharedPreferences.Editor prefs)
	{
		String sKey = StringFactory.GetString_PreferenceTotalScoreKey();	
		prefs.putInt(sKey, g_TotalScore);
	}

	public static void SaveActievementReportState(SharedPreferences.Editor prefs)
	{
//		String sKey = StringFactory.GetString_Achievement1Key();
//		prefs.putBoolean(sKey, g_bShouldAchievement1Report);
//		sKey = StringFactory.GetString_Achievement2Key];
//		[prefs setBool:g_bShouldAchievement2Report forKey:sKey];
//		sKey = StringFactory.GetString_Achievement3Key];
//		[prefs setBool:g_bShouldAchievement3Report forKey:sKey];
//		sKey = StringFactory.GetString_Achievement4Key];
//		[prefs setBool:g_bShouldAchievement4Report forKey:sKey];
	}


	public static void LoadLastWinSkill(SharedPreferences prefs)
	{
		String sKey = StringFactory.GetString_PreferenceLastWinSkillKey();	
		g_lastWinSkill = prefs.getInt(sKey, -1);
	}

	public static void LoadLastWinLevel(SharedPreferences prefs)
	{
		String sKey = StringFactory.GetString_PreferenceLastWinLevelKey();	
		g_lastWinLevel = prefs.getInt(sKey, -1);
	}

	public static void LoadSkill(SharedPreferences prefs)
	{
		String sKey = StringFactory.GetString_PreferenceSkillKey();	
		int nSkill = prefs.getInt(sKey, 0);
		CConfiguration.setGameSkill(nSkill);
	}

	public static void LoadLevel(SharedPreferences prefs)
	{
		String sKey = StringFactory.GetString_PreferenceLevelKey();	
		int nLevel = prefs.getInt(sKey, 0);
		CConfiguration.setGameLevel(nLevel);
	}

	public static void LoadSound(SharedPreferences prefs)
	{
		String sKey = StringFactory.GetString_PreferenceSoundKey();	
		boolean bEnable = prefs.getBoolean(sKey, false);
		CConfiguration.SetSoundEnable(bEnable);
	}

	public static void LoadBackground(SharedPreferences prefs)
	{
		String sKey = StringFactory.GetString_PreferenceBackgroundKey();	
		int nSetting = prefs.getInt(sKey, 0);
		CConfiguration.SetGameBackground(nSetting);
	}

	
	public static void LoadThunderTheme(SharedPreferences prefs)
	{
		String sKey = StringFactory.GetString_PreferenceThunderThemeKey();	
		boolean bEnable = prefs.getBoolean(sKey, false);;
		CConfiguration.setThunderTheme(bEnable);
	}

	public static void LoadClock(SharedPreferences.Editor prefs)
	{
//		String sKey = StringFactory.GetString_PreferenceClockKey];	
//		BOOL bEnable = [prefs boolForKey:sKey];
//		[Configuration setClockShown:!bEnable];
	}

	public static void LoadTotalScore(SharedPreferences prefs)
	{
		String sKey = StringFactory.GetString_PreferenceTotalScoreKey();	
		g_TotalScore = prefs.getInt(sKey, 0);
	    if(g_TotalScore < 0)
	        g_TotalScore = 0;
	}

	public static void LoadPoint(SharedPreferences prefs, int nSkill, int nLevel)
	{
		int k = nSkill*CConfiguration.GAME_PLAY_LEVELS + nLevel;
		if(12 <= k)
			return;
		
		String sKey = StringFactory.GetString_PreferencePointKey(k);	
		
		int nPoint = prefs.getInt(sKey, 0);
		if(nPoint <= 0)
		{
			nPoint = 0;
		}	
		g_point[k] = nPoint; 
	}

	public static void LoadScore(SharedPreferences prefs, int nSkill, int nLevel)
	{
		int k = nSkill*CConfiguration.GAME_PLAY_LEVELS + nLevel;
		if(12 <= k)
			return;
		
		String sKey = StringFactory.GetString_PreferenceScoreKey(k);	
		int nScore = prefs.getInt(sKey, 0);;
		if(nScore <= 0)
		{
			nScore = 0;
		}	
		g_score[k] = nScore; 
	}

	public static void LoadActievementReportState(SharedPreferences prefs)
	{
//		String sKey = StringFactory.GetString_Achievement1Key];
//		g_bShouldAchievement1Report = [prefs boolForKey:sKey];
//		sKey = StringFactory.GetString_Achievement2Key];
//		g_bShouldAchievement2Report = [prefs boolForKey:sKey];
//		sKey = StringFactory.GetString_Achievement3Key];
//		g_bShouldAchievement3Report = [prefs boolForKey:sKey];
//		sKey = StringFactory.GetString_Achievement4Key];
//		g_bShouldAchievement4Report = [prefs boolForKey:sKey];
	}

	public static void handleAchivementReportStatus(int nSkill, int nLevel)
	{
//	    if(nSkill == GAME_SKILL_LEVEL_THREE && nLevel == GAME_PLAY_LEVEL_THREE)
//	    {
//	        g_bShouldAchievement1Report = YES;
//	    }
//	    else if(nSkill == GAME_SKILL_LEVEL_ONE && nLevel == GAME_PLAY_LEVEL_FOUR)
//	    {
//	        g_bShouldAchievement2Report = YES;
//	    }
//	    else if(nSkill == GAME_SKILL_LEVEL_TWO && nLevel == GAME_PLAY_LEVEL_FOUR)
//	    {
//	        g_bShouldAchievement3Report = YES;
//	    }
//	    else if(nSkill == GAME_SKILL_LEVEL_THREE && nLevel == GAME_PLAY_LEVEL_FOUR)
//	    {
//	        g_bShouldAchievement4Report = YES;
//	    }
	}

	public static void addScore(int nSkill, int nLevel)
	{
		g_lastWinLevel = nLevel;
		g_lastWinSkill = nSkill;
		
		for(int i = 0; i < CConfiguration.GAME_SKILL_LEVELS; ++i)
		{
			for(int j = 0; j < CConfiguration.GAME_PLAY_LEVELS; ++j)
			{
				if(i == nSkill && j == nLevel)
				{
					int k = nSkill*CConfiguration.GAME_PLAY_LEVELS + nLevel;
					if(12 <= k)
						return;
					g_score[k] = g_score[k]+1;
					if(CConfiguration.GAME_MAXMUM_SCORE_NUMBER < g_score[k])
					{
						g_score[k] = 0;
						g_point[k] = g_point[k]+1;
					}	
	                //ScoreRecord.handleAchivementReportStatus:nSkill atLevel:nLevel];
					break;
				}	
			}	
		}	
	}

	public static void setScore(int nScore, int nSkill, int nLevel)
	{
		for(int i = 0; i < CConfiguration.GAME_SKILL_LEVELS; ++i)
		{
			for(int j = 0; j < CConfiguration.GAME_PLAY_LEVELS; ++j)
			{
				if(i == nSkill && j == nLevel)
				{
					int k = nSkill*CConfiguration.GAME_PLAY_LEVELS + nLevel;
					
					if(12 <= k)
						return;
					
					g_score[k] = nScore;
					if(CConfiguration.GAME_MAXMUM_SCORE_NUMBER < g_score[k])
					{
						g_score[k] = 0;
						g_point[k] = g_point[k]+1;
					}	
					break;
				}	
			}	
		}	
	}

	public static void setPoint(int nPoint, int nSkill, int nLevel)
	{
		for(int i = 0; i < CConfiguration.GAME_SKILL_LEVELS; ++i)
		{
			for(int j = 0; j < CConfiguration.GAME_PLAY_LEVELS; ++j)
			{
				if(i == nSkill && j == nLevel)
				{
					int k = nSkill*CConfiguration.GAME_PLAY_LEVELS + nLevel;
					if(12 <= k)
						return;
					g_point[k] = nPoint;
				}	
			}	
		}	
	}	


	public static void saveRecord()
	{
		//[NSUserDefaults resetStandardUserDefaults];
		//NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
		SharedPreferences.Editor prefs = GetDefaultPreferenceEditor();
		
		ScoreRecord.SaveLastWinSkill(prefs);
		ScoreRecord.SaveLastWinLevel(prefs);
		ScoreRecord.SaveSkill(prefs);
		ScoreRecord.SaveLevel(prefs);
		ScoreRecord.SaveSound(prefs);
		ScoreRecord.SaveBackground(prefs);
	    ScoreRecord.SaveThunderTheme(prefs);
	    ScoreRecord.SaveClock(prefs);
	    ScoreRecord.SaveTotalScore(prefs);
	    ScoreRecord.SaveActievementReportState(prefs);
	    
		for(int i = 0; i < CConfiguration.GAME_SKILL_LEVELS; ++i)
		{
			for(int j = 0; j < CConfiguration.GAME_PLAY_LEVELS; ++j)
			{
				ScoreRecord.SavePoint(prefs, i, j);
				ScoreRecord.SaveScore(prefs, i, j);
			}	
		}	
	    //[prefs synchronize];
	}

	public static void loadRecord()
	{
		SharedPreferences prefs = GetDefaultPreference();

		
		ScoreRecord.LoadLastWinSkill(prefs);
		ScoreRecord.LoadLastWinLevel(prefs);
		ScoreRecord.LoadSkill(prefs);
		ScoreRecord.LoadLevel(prefs);
		ScoreRecord.LoadSound(prefs);
		ScoreRecord.LoadBackground(prefs);
		ScoreRecord.LoadThunderTheme(prefs);
		//ScoreRecord.LoadClock(prefs);
	    ScoreRecord.LoadTotalScore(prefs);
	    ScoreRecord.LoadActievementReportState(prefs);
		for(int i = 0; i < CConfiguration.GAME_SKILL_LEVELS; ++i)
		{
			for(int j = 0; j < CConfiguration.GAME_PLAY_LEVELS; ++j)
			{
				ScoreRecord.LoadPoint(prefs, i, j);
				ScoreRecord.LoadScore(prefs, i, j);
			}	
		}	
	}	

	public static boolean CheckPaymentState()
	{
//		[NSUserDefaults resetStandardUserDefaults];
//		NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
		SharedPreferences prefs = GetDefaultPreference();
		boolean bPaid = prefs.getBoolean(StringFactory.GetString_PurchaseStateKey(), false);
	    return bPaid;
	}

	public static void SavePaidState()
	{
	    boolean bRet = true;
	    //[NSUserDefaults resetStandardUserDefaults];
	    //NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
		SharedPreferences.Editor prefs = GetDefaultPreferenceEditor();

	    prefs.putBoolean(StringFactory.GetString_PurchaseStateKey(), bRet);
		prefs.commit();
	    //[prefs synchronize];
	}

	public static void addTotalWinScore(int nWinScore)
	{
	    g_TotalScore += nWinScore;
	}

	public static void reduceTotalWinScore(int nLostScore)
	{
	    g_TotalScore -= nLostScore;
	    if(g_TotalScore < 0)
	        g_TotalScore = 0;
	}

	public static int getTotalWinScore()
	{
	    return g_TotalScore;
	}

	public static boolean shouldAchievement1Reported()
	{
	    return g_bShouldAchievement1Report;
	}

	public static boolean shouldAchievement2Reported()
	{
	    return g_bShouldAchievement2Report;
	}

	public static boolean shouldAchievement3Reported()
	{
	    return g_bShouldAchievement3Report;
	}

	public static boolean shouldAchievement4Reported()
	{
	    return g_bShouldAchievement4Report;
	}

	public static void resetAchievement1Reported()
	{
//	    g_bShouldAchievement1Report = NO;
//		[NSUserDefaults resetStandardUserDefaults];
//		NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
//		String sKey = StringFactory.GetString_Achievement1Key];
//		[prefs setBool:g_bShouldAchievement1Report forKey:sKey];
	}

	public static void resetAchievement2Reported()
	{
//	    g_bShouldAchievement2Report = NO;
//		[NSUserDefaults resetStandardUserDefaults];
//		NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
//		String sKey = StringFactory.GetString_Achievement2Key];
//		[prefs setBool:g_bShouldAchievement2Report forKey:sKey];
	}

	public static void resetAchievement3Reported()
	{
//	    g_bShouldAchievement3Report = NO;
//		[NSUserDefaults resetStandardUserDefaults];
//		NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
//		String sKey = StringFactory.GetString_Achievement3Key];
//		[prefs setBool:g_bShouldAchievement3Report forKey:sKey];
	}


	public static void resetAchievement4Reported()
	{
//	    g_bShouldAchievement4Report = NO;
//		[NSUserDefaults resetStandardUserDefaults];
//		NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
//		String sKey = StringFactory.GetString_Achievement4Key];
//		[prefs setBool:g_bShouldAchievement4Report forKey:sKey];
	}

	public static void checkAWSServiceEnable()
	{
	    g_bDisableAWSService = false;
		//[NSUserDefaults resetStandardUserDefaults];
		//NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
		SharedPreferences prefs = GetDefaultPreference();
		boolean bPaid = prefs.getBoolean(StringFactory.GetString_PurchaseStateKey(), false);
		
	    g_bDisableAWSService = prefs.getBoolean(StringFactory.GetString_AskAWSServiceEnableKey(), false);
	}

	public static void setAWSServiceEnable(boolean bEnable)
	{
	    if(bEnable)
	        g_bDisableAWSService = false;
	    else
	        g_bDisableAWSService = true;
		//[NSUserDefaults resetStandardUserDefaults];
		//NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
		String sKey = StringFactory.GetString_AskAWSServiceEnableKey();
		SharedPreferences.Editor prefs = GetDefaultPreferenceEditor();
		prefs.putBoolean(sKey, g_bDisableAWSService);
		prefs.commit();
	}

	public static boolean isAWSServiceEnabled()
	{
	    if(g_bDisableAWSService == false)
	        return true;
	    else
	        return false;
	}

	public static void SetOnlineNickName(String szName)
	{
	   // [[NSUserDefaults standardUserDefaults] setObject:szName forKey:XGADGET_ONLINE_USER_NICKNAME_KEY];
		SharedPreferences.Editor prefs = GetDefaultPreferenceEditor();
		prefs.putString(XGADGET_ONLINE_USER_NICKNAME_KEY, szName);
		prefs.commit();
	}

	public static String GetOnlineNickName()
	{
		SharedPreferences prefs = GetDefaultPreference();

		String nsName = prefs.getString(XGADGET_ONLINE_USER_NICKNAME_KEY, null);
	    return nsName;
	}

	public static boolean HasOnlineNickName()
	{
		SharedPreferences prefs = GetDefaultPreference();

		String nsName = prefs.getString(XGADGET_ONLINE_USER_NICKNAME_KEY, null);
	    if(nsName != null && 0 < nsName.length())
	        return true;
	    
	    return false;
	}

	public static void SetOnlinePlayerID(String szID)
	{
	    //[[NSUserDefaults standardUserDefaults] setObject:szID forKey:XGADGET_ONLINE_USER_PLAYERID_KEY];
		SharedPreferences.Editor prefs = GetDefaultPreferenceEditor();
		prefs.putString(XGADGET_ONLINE_USER_PLAYERID_KEY, szID);
		prefs.commit();
	}

	public static String GetOnlinePlayerID()
	{
	    //String nsID = [[NSUserDefaults standardUserDefaults] stringForKey:XGADGET_ONLINE_USER_PLAYERID_KEY];
		SharedPreferences prefs = GetDefaultPreference();

		String nsID = prefs.getString(XGADGET_ONLINE_USER_PLAYERID_KEY, null);
	    
		return nsID;
	}

	public static boolean HasOnlinePlayerID()
	{
		SharedPreferences prefs = GetDefaultPreference();

		String nsID = prefs.getString(XGADGET_ONLINE_USER_PLAYERID_KEY, null);
	    if(nsID != null && 0 < nsID.length())
	        return true;
	    
	    return false;
	}
	
}
