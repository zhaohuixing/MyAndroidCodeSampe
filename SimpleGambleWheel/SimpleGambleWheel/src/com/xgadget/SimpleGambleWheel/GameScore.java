package com.xgadget.SimpleGambleWheel;

import com.xgadget.uimodule.ResourceHelper;

import android.content.Context;
import android.content.SharedPreferences;
//import android.content.res.Resources;

public class GameScore 
{
	private static final String SGW_PreferenceKey = "KeySGWScorePref";
	
    public int			m_nMyMostWinYear;
    public int			m_nMyMostWinMonth;
    public int			m_nMyMostWinDay;
    public int			m_nMyMostWinChips;
    public int			m_nMyChipBalance;
    public int         m_nMyLastPlayResult;

    public int			m_nPlayer1MostWinYear;
    public int			m_nPlayer1MostWinMonth;
    public int			m_nPlayer1MostWinDay;
    public int			m_nPlayer1MostWinChips;
    public int			m_nPlayer1ChipBalance;
    public int         m_nPlayer1LastPlayResult;
    
    public int			m_nPlayer2MostWinYear;
    public int			m_nPlayer2MostWinMonth;
    public int			m_nPlayer2MostWinDay;
    public int			m_nPlayer2MostWinChips;
    public int			m_nPlayer2ChipBalance;
    public int         m_nPlayer2LastPlayResult;
    
    public int			m_nPlayer3MostWinYear;
    public int			m_nPlayer3MostWinMonth;
    public int			m_nPlayer3MostWinDay;
    public int			m_nPlayer3MostWinChips;
    public int			m_nPlayer3ChipBalance;
    public int         m_nPlayer3LastPlayResult;
    
    public int         m_nGameType;
    public int         m_nThemeType;
    public int         m_nOfflineBetWay;
    public int         m_nSaveRecord;
    public boolean        m_bSound;
    public int         m_nPlayTurnType;
    
	public GameScore() 
	{
		// TODO Auto-generated constructor stub
        m_nMyMostWinYear = 0;
        m_nMyMostWinMonth = 0;
        m_nMyMostWinDay = 0;
        m_nMyMostWinChips = 0;
        m_nMyChipBalance = 0;
        m_nMyLastPlayResult = 0;
        
        m_nPlayer1MostWinYear = 0;
        m_nPlayer1MostWinMonth = 0;
        m_nPlayer1MostWinDay = 0;
        m_nPlayer1MostWinChips = 0;
        m_nPlayer1ChipBalance = -1;
        m_nPlayer1LastPlayResult = -1;
        
        m_nPlayer2MostWinYear = 0;
        m_nPlayer2MostWinMonth = 0;
        m_nPlayer2MostWinDay = 0;
        m_nPlayer2MostWinChips = 0;
        m_nPlayer2ChipBalance = -1;
        m_nPlayer2LastPlayResult = -1;
        
        m_nPlayer3MostWinYear = 0;
        m_nPlayer3MostWinMonth = 0;
        m_nPlayer3MostWinDay = 0;
        m_nPlayer3MostWinChips = 0;
        m_nPlayer3ChipBalance = -1;
        m_nPlayer3LastPlayResult = -1;
        
        m_nGameType = GameConstants.GAME_TYPE_8LUCK;
        m_nThemeType = GameConstants.GAME_THEME_ANIMAL;
        m_nOfflineBetWay = 0;
        m_nSaveRecord = 0;
        m_bSound = false;
        m_nPlayTurnType = GameConstants.GAME_PLAYTURN_TYPE_SEQUENCE;
	}

	public static Context GetSharedContext()
	{
		return ResourceHelper.GetResourceContext();
	}
	
	public static SharedPreferences GetDefaultPreference()
	{
		Context context = ResourceHelper.GetResourceContext();
		SharedPreferences prefs = context.getSharedPreferences(SGW_PreferenceKey, 0);
		return prefs;
	}
	
	public static SharedPreferences.Editor GetDefaultPreferenceEditor()
	{
		Context context = ResourceHelper.GetResourceContext();
		SharedPreferences prefs = context.getSharedPreferences(SGW_PreferenceKey, 0);
		return prefs.edit();
	}
	
	
	public void SetGameType(int nType)
	{
	    m_nGameType = nType;
	}

	public int GetGameType()
	{
	    return m_nGameType;
	}

	public void SetThemeType(int nTheme)
	{
	    m_nThemeType = nTheme;
	}

	public int GetThemeType()
	{
	    return m_nThemeType;
	}
	
	
	public int GetSavedRecord()
	{
	    return m_nSaveRecord;
	}

	public void SetOfflineBetMethod(int nWay)
	{
	    m_nOfflineBetWay = nWay;
	}

	public int GetOfflineBetMethod()
	{
	    return m_nOfflineBetWay;
	}

	public void SetSoundEnable(boolean bEnable)
	{
	    m_bSound = bEnable;
	}

	public boolean GetSoundEnable()
	{
	    return m_bSound;
	}

	public void SetPlayTurnType(int nPlayTurnType)
	{
	    m_nPlayTurnType = nPlayTurnType;
	}

	public int GetPlayTurnType()
	{
	    return m_nPlayTurnType;
	}

	public void SetMyChipBalance(int nChips)
	{
	    m_nMyChipBalance = nChips;
	}

	public void SetMyLastPlayResult(int nChips)
	{
	    m_nMyLastPlayResult = nChips;
	    if(m_nMyMostWinChips <= m_nMyLastPlayResult)
	    {
	        m_nMyMostWinChips = m_nMyLastPlayResult;
	        /*NSDateFormatter *dateFormatter = [[[NSDateFormatter alloc] init] autorelease];
	        
	        [dateFormatter setDateFormat:"MM"];
	        m_nMyMostWinMonth = [[dateFormatter stringFromDate:[NSDate date() intValue];
	        
	        [dateFormatter setDateFormat:"dd"];
	        m_nMyMostWinDay = [[dateFormatter stringFromDate:[NSDate date() intValue];
	        
	        [dateFormatter setDateFormat:"yyyy"];
	        m_nMyMostWinYear = [[dateFormatter stringFromDate:[NSDate date() intValue];*/
	    }
	}

	public void SetPlayer1ChipBalance(int nChips)
	{
	    m_nPlayer1ChipBalance = nChips;
	}

	public void SetPlayer1LastPlayResult(int nChips)
	{
	    m_nPlayer1LastPlayResult = nChips;
	    if(m_nPlayer1MostWinChips <= m_nPlayer1LastPlayResult)
	    {
	        m_nPlayer1MostWinChips = m_nPlayer1LastPlayResult;
	        /*NSDateFormatter *dateFormatter = [[[NSDateFormatter alloc] init] autorelease];
	        
	        [dateFormatter setDateFormat:"MM"];
	        m_nPlayer1MostWinMonth = [[dateFormatter stringFromDate:[NSDate date() intValue];
	        
	        [dateFormatter setDateFormat:"dd"];
	        m_nPlayer1MostWinDay = [[dateFormatter stringFromDate:[NSDate date() intValue];
	        
	        [dateFormatter setDateFormat:"yyyy"];
	        m_nPlayer1MostWinYear = [[dateFormatter stringFromDate:[NSDate date() intValue];*/
	    }
	}

	public void SetPlayer2ChipBalance(int nChips)
	{
	    m_nPlayer2ChipBalance = nChips;
	}

	public void SetPlayer2LastPlayResult(int nChips)
	{
	    m_nPlayer2LastPlayResult = nChips;
	    if(m_nPlayer2MostWinChips <= m_nPlayer2LastPlayResult)
	    {
	        m_nPlayer2MostWinChips = m_nPlayer2LastPlayResult;
	        /*NSDateFormatter *dateFormatter = [[[NSDateFormatter alloc] init] autorelease];
	        
	        [dateFormatter setDateFormat:"MM"];
	        m_nPlayer2MostWinMonth = [[dateFormatter stringFromDate:[NSDate date() intValue];
	        
	        [dateFormatter setDateFormat:"dd"];
	        m_nPlayer2MostWinDay = [[dateFormatter stringFromDate:[NSDate date() intValue];
	        
	        [dateFormatter setDateFormat:"yyyy"];
	        m_nPlayer2MostWinYear = [[dateFormatter stringFromDate:[NSDate date() intValue];*/
	    }
	}

	public void SetPlayer3ChipBalance(int nChips)
	{
	    m_nPlayer3ChipBalance = nChips;
	}

	public void SetPlayer3LastPlayResult(int nChips)
	{
	    m_nPlayer3LastPlayResult = nChips;
	    if(m_nPlayer3MostWinChips <= m_nPlayer3LastPlayResult)
	    {
	        m_nPlayer3MostWinChips = m_nPlayer3LastPlayResult;
	        /* NSDateFormatter *dateFormatter = [[[NSDateFormatter alloc] init] autorelease];
	        
	        [dateFormatter setDateFormat:"MM"];
	        m_nPlayer3MostWinMonth = [[dateFormatter stringFromDate:[NSDate date() intValue];
	        
	        [dateFormatter setDateFormat:"dd"];
	        m_nPlayer3MostWinDay = [[dateFormatter stringFromDate:[NSDate date() intValue];
	        
	        [dateFormatter setDateFormat:"yyyy"];
	        m_nPlayer3MostWinYear = [[dateFormatter stringFromDate:[NSDate date() intValue];*/
	    }
	}

	public int GetMyMostWinYear()
	{
	    return m_nMyMostWinYear;
	}

	public int GetMyMostWinMonth()
	{
	    return m_nMyMostWinMonth;
	}

	public int GetMyMostWinDay()
	{
	    return m_nMyMostWinDay;
	}

	public int GetMyMostWinChips()
	{
	    return m_nMyMostWinChips;
	}

	public int GetMyChipBalance()
	{
	    return m_nMyChipBalance;
	}

	public int GetMyLastPlayResult()
	{
	    return m_nMyLastPlayResult;
	}

	public int GetPlayer1MostWinYear()
	{
	    return m_nPlayer1MostWinYear;
	}

	public int GetPlayer1MostWinMonth()
	{
	    return m_nPlayer1MostWinMonth;
	}

	public int GetPlayer1MostWinDay()
	{
	    return m_nPlayer1MostWinDay;
	}

	public int GetPlayer1MostWinChips()
	{
	    return m_nPlayer1MostWinChips;
	}

	public int GetPlayer1ChipBalance()
	{
	    return m_nPlayer1ChipBalance;
	}

	public int GetPlayer1LastPlayResult()
	{
	    return m_nPlayer1LastPlayResult;
	}

	public int GetPlayer2MostWinYear()
	{
	    return m_nPlayer2MostWinYear;
	}

	public int GetPlayer2MostWinMonth()
	{
	    return m_nPlayer2MostWinMonth;
	}

	public int GetPlayer2MostWinDay()
	{
	    return m_nPlayer2MostWinDay;
	}

	public int GetPlayer2MostWinChips()
	{
	    return m_nPlayer2MostWinChips;
	}

	public int GetPlayer2ChipBalance()
	{
	    return m_nPlayer2ChipBalance;
	}

	public int GetPlayer2LastPlayResult()
	{
	    return m_nPlayer2LastPlayResult;
	}

	public int GetPlayer3MostWinYear()
	{
	    return m_nPlayer3MostWinYear;
	}

	public int GetPlayer3MostWinMonth()
	{
	    return m_nPlayer3MostWinMonth;
	}

	public int GetPlayer3MostWinDay()
	{
	    return m_nPlayer3MostWinDay;
	}

	public int GetPlayer3MostWinChips()
	{
	    return m_nPlayer3MostWinChips;
	}

	public int GetPlayer3ChipBalance()
	{
	    return m_nPlayer3ChipBalance;
	}

	public int GetPlayer3LastPlayResult()
	{
	    return m_nPlayer3LastPlayResult;
	}
	
	public void SaveMeDate(SharedPreferences.Editor prefs)
	{
		prefs.putInt(GameScore.GetMyMostWinYearKey(), m_nMyMostWinYear);
		prefs.putInt(GameScore.GetMyMostWinMonthKey(), m_nMyMostWinMonth);
		prefs.putInt(GameScore.GetMyMostWinDayKey(), m_nMyMostWinDay);
		prefs.putInt(GameScore.GetMyMostWinChipsKey(), m_nMyMostWinChips);

		prefs.putInt(GameScore.GetMyChipBalanceKey(), m_nMyChipBalance);
		prefs.putInt(GameScore.GetMyLastPlayResultKey(), m_nMyLastPlayResult);
	}

	public void SavePlayer1Date(SharedPreferences.Editor prefs)
	{
		prefs.putInt(GameScore.GetPlayer1MostWinYearKey(), m_nPlayer1MostWinYear);
		prefs.putInt(GameScore.GetPlayer1MostWinMonthKey(), m_nPlayer1MostWinMonth);
		prefs.putInt(GameScore.GetPlayer1MostWinDayKey(), m_nPlayer1MostWinDay);
		prefs.putInt(GameScore.GetPlayer1MostWinChipsKey(), m_nPlayer1MostWinChips);
	    
		prefs.putInt(GameScore.GetPlayer1ChipBalanceKey(), m_nPlayer1ChipBalance);
		prefs.putInt(GameScore.GetPlayer1LastPlayResultKey(), m_nPlayer1LastPlayResult);
	}

	public void SavePlayer2Date(SharedPreferences.Editor prefs)
	{
		prefs.putInt(GameScore.GetPlayer2MostWinYearKey(), m_nPlayer2MostWinYear);
		prefs.putInt(GameScore.GetPlayer2MostWinMonthKey(), m_nPlayer2MostWinMonth);
		prefs.putInt(GameScore.GetPlayer2MostWinDayKey(), m_nPlayer2MostWinDay);
		prefs.putInt(GameScore.GetPlayer2MostWinChipsKey(), m_nPlayer2MostWinChips);
	    
		prefs.putInt(GameScore.GetPlayer2ChipBalanceKey(), m_nPlayer2ChipBalance);
		prefs.putInt(GameScore.GetPlayer2LastPlayResultKey(), m_nPlayer2LastPlayResult);
	}

	public void SavePlayer3Date(SharedPreferences.Editor prefs)
	{
		prefs.putInt(GameScore.GetPlayer3MostWinYearKey(), m_nPlayer3MostWinYear);
		prefs.putInt(GameScore.GetPlayer3MostWinMonthKey(), m_nPlayer3MostWinMonth);
		prefs.putInt(GameScore.GetPlayer3MostWinDayKey(), m_nPlayer3MostWinDay);
		prefs.putInt(GameScore.GetPlayer3MostWinChipsKey(), m_nPlayer3MostWinChips);
	    
		prefs.putInt(GameScore.GetPlayer3ChipBalanceKey(), m_nPlayer3ChipBalance);
		prefs.putInt(GameScore.GetPlayer3LastPlayResultKey(), m_nPlayer3LastPlayResult);
	}

	public void SaveGameType(SharedPreferences.Editor prefs)
	{
		prefs.putInt(GameScore.GetGameTypeKey(), m_nGameType);
		prefs.putInt(GameScore.GetThemeTypeKey(), m_nThemeType);
		prefs.putInt(GameScore.GetOfflineBetKey(), m_nOfflineBetWay);
	    prefs.putBoolean(GameScore.GetSoundKey(), m_bSound);
	    prefs.putInt(GameScore.GetPlayTurnTypeKey(), m_nPlayTurnType);
	    m_nSaveRecord += 1;
	    prefs.putInt(GameScore.GetSaveRecordKey(), m_nSaveRecord);
	}

	public void Save()
	{
		SharedPreferences.Editor prefs = GameScore.GetDefaultPreferenceEditor();
		SaveMeDate(prefs);
		SavePlayer1Date(prefs);
		SavePlayer2Date(prefs);
		SavePlayer3Date(prefs);
	    SaveGameType(prefs);
	    prefs.commit();
	}
	
	public void LoadMeDate(SharedPreferences prefs)
	{
		m_nMyMostWinYear = prefs.getInt(GameScore.GetMyMostWinYearKey(), 0);
		m_nMyMostWinMonth = prefs.getInt(GameScore.GetMyMostWinMonthKey(), 0);
		m_nMyMostWinDay = prefs.getInt(GameScore.GetMyMostWinDayKey(), 0);
		m_nMyMostWinChips = prefs.getInt(GameScore.GetMyMostWinChipsKey(), -1);
	    
		m_nMyChipBalance = prefs.getInt(GameScore.GetMyChipBalanceKey(), -1);
		m_nMyLastPlayResult = prefs.getInt(GameScore.GetMyLastPlayResultKey(), -1);
	}

	public void LoadPlayer1Date(SharedPreferences prefs)
	{
		m_nPlayer1MostWinYear = prefs.getInt(GameScore.GetPlayer1MostWinYearKey(), 0);
		m_nPlayer1MostWinMonth = prefs.getInt(GameScore.GetPlayer1MostWinMonthKey(), 0);
		m_nPlayer1MostWinDay = prefs.getInt(GameScore.GetPlayer1MostWinDayKey(), 0);
		m_nPlayer1MostWinChips = prefs.getInt(GameScore.GetPlayer1MostWinChipsKey(), -1);
	    
		m_nPlayer1ChipBalance = prefs.getInt(GameScore.GetPlayer1ChipBalanceKey(), -1);
		m_nPlayer1LastPlayResult = prefs.getInt(GameScore.GetPlayer1LastPlayResultKey(), -1);
	}

	public void LoadPlayer2Date(SharedPreferences prefs)
	{
		m_nPlayer2MostWinYear = prefs.getInt(GameScore.GetPlayer2MostWinYearKey(), 0);
		m_nPlayer2MostWinMonth = prefs.getInt(GameScore.GetPlayer2MostWinMonthKey(), 0);
		m_nPlayer2MostWinDay = prefs.getInt(GameScore.GetPlayer2MostWinDayKey(), 0);
		m_nPlayer2MostWinChips = prefs.getInt(GameScore.GetPlayer2MostWinChipsKey(), -1);
	    
		m_nPlayer2ChipBalance = prefs.getInt(GameScore.GetPlayer2ChipBalanceKey(), -1);
		m_nPlayer2LastPlayResult = prefs.getInt(GameScore.GetPlayer2LastPlayResultKey(), -1);
	}

	public void LoadPlayer3Date(SharedPreferences prefs)
	{
		m_nPlayer3MostWinYear = prefs.getInt(GameScore.GetPlayer3MostWinYearKey(), 0);
		m_nPlayer3MostWinMonth = prefs.getInt(GameScore.GetPlayer3MostWinMonthKey(), 0);
		m_nPlayer3MostWinDay = prefs.getInt(GameScore.GetPlayer3MostWinDayKey(), 0);
		m_nPlayer3MostWinChips = prefs.getInt(GameScore.GetPlayer3MostWinChipsKey(), -1);
	    
		m_nPlayer3ChipBalance = prefs.getInt(GameScore.GetPlayer3ChipBalanceKey(), -1);
		m_nPlayer3LastPlayResult = prefs.getInt(GameScore.GetPlayer3LastPlayResultKey(), -1);
	}

	public void LoadGameType(SharedPreferences  prefs)
	{
	    m_nGameType = prefs.getInt(GameScore.GetGameTypeKey(), GameConstants.GAME_TYPE_8LUCK);
	    m_nThemeType = prefs.getInt(GameScore.GetThemeTypeKey(), GameConstants.GAME_THEME_ANIMAL);
		m_nOfflineBetWay = prefs.getInt(GameScore.GetOfflineBetKey(), 0);
	    m_bSound = prefs.getBoolean(GameScore.GetSoundKey(), false);
	    m_nSaveRecord = prefs.getInt(GameScore.GetSaveRecordKey(), 0);
	    m_nPlayTurnType = prefs.getInt(GameScore.GetPlayTurnTypeKey(), GameConstants.GAME_PLAYTURN_TYPE_SEQUENCE);
	    if(m_nPlayTurnType < 0)
	        m_nPlayTurnType = GameConstants.GAME_PLAYTURN_TYPE_SEQUENCE;
	}

	public void Load()
	{
		SharedPreferences prefs = GameScore.GetDefaultPreference();
		LoadMeDate(prefs);
		LoadPlayer1Date(prefs);
		LoadPlayer2Date(prefs);
		LoadPlayer3Date(prefs);
	    LoadGameType(prefs);
	}	

	
	public static String GetMyMostWinYearKey()
	{
	    return "sgw_Key_mymostwin_year";
	}

	public static String GetMyMostWinMonthKey()
	{
	    return "sgw_Key_mymostwin_month";
	}

	public static String GetMyMostWinDayKey()
	{
	    return "sgw_Key_mymostwin_day";
	}

	public static String GetMyMostWinChipsKey()
	{
	    return "sgw_Key_mymostwin_chips";
	}

	public static String GetMyChipBalanceKey()
	{
	    return "sgw_Key_mychipbalance";
	}

	public static String GetMyLastPlayResultKey()
	{
	    return "sgw_Key_mylastresult";
	}

	public static String GetPlayer1MostWinYearKey()
	{
	    return "sgw_Key_player1mostwin_year";
	}

	public static String GetPlayer1MostWinMonthKey()
	{
	    return "sgw_Key_player1mostwin_month";
	}

	public static String GetPlayer1MostWinDayKey()
	{
	    return "sgw_Key_player1mostwin_day";
	}

	public static String GetPlayer1MostWinChipsKey()
	{
	    return "sgw_Key_player1mostwin_chips";
	}

	public static String GetPlayer1ChipBalanceKey()
	{
	    return "sgw_Key_player1chipbalance";
	}

	public static String GetPlayer1LastPlayResultKey()
	{
	    return "sgw_Key_player1lastresult";
	}

	public static String GetPlayer2MostWinYearKey()
	{
	    return "sgw_Key_player2mostwin_year";
	}

	public static String GetPlayer2MostWinMonthKey()
	{
	    return "sgw_Key_player2mostwin_month";
	}

	public static String GetPlayer2MostWinDayKey()
	{
	    return "sgw_Key_player2mostwin_day";
	}

	public static String GetPlayer2MostWinChipsKey()
	{
	    return "sgw_Key_player2mostwin_chips";
	}

	public static String GetPlayer2ChipBalanceKey()
	{
	    return "sgw_Key_player2chipbalance";
	}

	public static String GetPlayer2LastPlayResultKey()
	{
	    return "sgw_Key_player2lastresult";
	}

	public static String GetPlayer3MostWinYearKey()
	{
	    return "sgw_Key_player3mostwin_year";
	}

	public static String GetPlayer3MostWinMonthKey()
	{
	    return "sgw_Key_player3mostwin_month";
	}

	public static String GetPlayer3MostWinDayKey()
	{
	    return "sgw_Key_player3mostwin_day";
	}

	public static String GetPlayer3MostWinChipsKey()
	{
	    return "sgw_Key_player3mostwin_chips";
	}

	public static String GetPlayer3ChipBalanceKey()
	{
	    return "sgw_Key_player3chipbalance";
	}

	public static String GetPlayer3LastPlayResultKey()
	{
	    return "sgw_Key_player3lastresult";
	}

	public static String GetGameTypeKey()
	{
	    return "sgw_Key_gametype";
	}

	public static String GetThemeTypeKey()
	{
	    return "sgw_Key_themetype";
	}
	
	public static String GetOfflineBetKey()
	{
	    return "sgw_Key_offlinebetway";
	}

	public static String GetSaveRecordKey()
	{
	    return "sgw_Key_savedrecord";
	}

	public static String GetSoundKey()
	{
	    return "sgw_Key_soundenable";
	}

	public static String GetPlayTurnTypeKey()
	{
	    return "sgw_Key_playturntype";
	}
	
	private static final String XGADGET_ONLINE_USER_NICKNAME_KEY = "GAME_ONLINE_USER_NICKNAME_KEY_20120806121618_0001";
	private static final String XGADGET_ONLINE_USER_PLAYERID_KEY = "GAME_ONLINE_USER_PLAYERID_KEY_20120806121730_0002";
	public static void SetOnlineNickName(String szName)
	{
		if(szName != null && 0 < szName.length())
		{	
			SharedPreferences.Editor prefs = GameScore.GetDefaultPreferenceEditor();
			prefs.putString(XGADGET_ONLINE_USER_NICKNAME_KEY,szName);
			prefs.commit();
		}
	}

	public static String GetOnlineNickName()
	{
	    String szName = null;
		SharedPreferences prefs = GameScore.GetDefaultPreference();
		szName = prefs.getString(XGADGET_ONLINE_USER_NICKNAME_KEY, null);
	    return szName;
	}

	public static boolean HasOnlineNickName()
	{
	    String szName = null;
		SharedPreferences prefs = GameScore.GetDefaultPreference();
		szName = prefs.getString(XGADGET_ONLINE_USER_NICKNAME_KEY, null);
		if(szName != null && 0 < szName.length())
			return true;
		
	    return false;
	}
	
	public static void SetOnlinePlayerID(String szID)
	{
		if(szID != null && 0 < szID.length())
		{	
			SharedPreferences.Editor prefs = GameScore.GetDefaultPreferenceEditor();
			prefs.putString(XGADGET_ONLINE_USER_PLAYERID_KEY,szID);
			prefs.commit();
		}
	}

	public static String GetOnlinePlayerID()
	{
	    String szID = null; 
		SharedPreferences prefs = GameScore.GetDefaultPreference();
		szID = prefs.getString(XGADGET_ONLINE_USER_PLAYERID_KEY, null);
	    return szID;
	}

	public static boolean HasOnlinePlayerID()
	{
	    String szID = null; 
		SharedPreferences prefs = GameScore.GetDefaultPreference();
		szID = prefs.getString(XGADGET_ONLINE_USER_PLAYERID_KEY, null);
		if(szID != null && 0 < szID.length())
			return true;
	    
	    return false;
	}

	private static final String XGADGET_GAMBLEWHEEL_AWSSERVICE_KEY = "GAME_GAMBLEWHEEL_ONLINE_ENABLE_KEY_20120806171440_0001";
	public static void SetOnlineServiceEnable(boolean bEnable)
	{
		SharedPreferences.Editor prefs = GameScore.GetDefaultPreferenceEditor();
		prefs.putBoolean(XGADGET_GAMBLEWHEEL_AWSSERVICE_KEY ,bEnable);
		prefs.commit();
	}

	public static boolean IsOnlineServiceEnable()
	{
	    boolean bRet = true;
		SharedPreferences prefs = GameScore.GetDefaultPreference();
		bRet = prefs.getBoolean(XGADGET_GAMBLEWHEEL_AWSSERVICE_KEY, true);
	    return bRet;
	}
}
