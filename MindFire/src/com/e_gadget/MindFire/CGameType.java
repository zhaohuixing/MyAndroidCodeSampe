package com.e_gadget.MindFire;

public class CGameType 
{
	public static final int VERSION_TYPE_REGULAR = 0;
	public static final int VERSION_TYPE_TRIAL = 1;
	private static int	m_ReleaseType = VERSION_TYPE_REGULAR;

	public static final int BUILD_TYPE_RELEASE = 0;
	public static final int BUILD_TYPE_DEBUG = 1;
	private static int	m_BuildType = BUILD_TYPE_DEBUG;

	
	public static final int GAME_POINT18_ANSWER = 18;
	public static final int GAME_POINT21_ANSWER = 21;
	public static final int GAME_POINT24_ANSWER = 24;
	public static final int GAME_POINT27_ANSWER = 27;
	public static final int GAME_POINT30_ANSWER = 30;

	private static int GAME_POINTS = GAME_POINT24_ANSWER;

	public static boolean IsLevelEasy()
	{
		boolean bRet = (GAME_POINTS == GAME_POINT24_ANSWER);
		return bRet;
	}

	public static boolean IsLevelMedium()
	{
		boolean bRet = (GAME_POINTS == GAME_POINT21_ANSWER || GAME_POINTS == GAME_POINT18_ANSWER);
		return bRet;
	}
	
	public static void SetPoints(int nPoints)
	{
		GAME_POINTS = nPoints;
	}

	public static int GetPoints()
	{
		return GAME_POINTS;
	}
	
	private static boolean m_bSplashScreenEnable = true;

	public static void EnableSplashScreen(boolean bEanble)
	{
		m_bSplashScreenEnable = bEanble;
	}
	
	public static boolean IsSplashScreenEnable()
	{
		return m_bSplashScreenEnable;
	}
	
	public static void Initialize()
	{
		m_BuildType = BUILD_TYPE_DEBUG;
		//GAME_POINTS = GAME_POINT24_ANSWER;
		m_bSplashScreenEnable = true;
	}
	public static boolean IsReleaseVersion()
	{
		boolean bRet = (m_ReleaseType == VERSION_TYPE_REGULAR);
		return bRet;
	}

	public static boolean IsTrialVersion()
	{
		boolean bRet = (m_ReleaseType == VERSION_TYPE_TRIAL);
		return bRet;
	}

	
	public static boolean IsDebugBuild()
	{
		boolean bRet = (m_BuildType == BUILD_TYPE_DEBUG);
		return bRet;
	}

	public static boolean IsReleaseBuild()
	{
		boolean bRet = (m_BuildType == BUILD_TYPE_RELEASE);
		return bRet;
	}
	
	public static void SetAsReleaseVersion()
	{
		m_ReleaseType = VERSION_TYPE_REGULAR;
	}

	public static void SetAsTrialVersion()
	{
		m_ReleaseType = VERSION_TYPE_TRIAL;
	}
	
	/*public static void SetGameAnswer(int n) 
	{
		GAME_ANSWER = n;
	}

	public static int GetGameAnswer() 
	{
		return GAME_ANSWER;
	}*/
	
}
