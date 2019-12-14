/**
 * 
 */
package com.xgadget.SimpleGambleWheel;

/**
 * @author zhaohuixing
 *
 */
public class Configuration 
{
	public static boolean		m_bGameSoundEffect = false;
	public static boolean		m_bDirty = false;
	public static int      		m_nGameType = GameConstants.GAME_TYPE_8LUCK;
	public static int      		m_nThemeType = GameConstants.GAME_THEME_ANIMAL;
	public static boolean		m_bRoPaAutoBet = true;

	public static boolean		m_bCacheOnlineMode = false;
	public static boolean		m_bGameOnline = false;
	public static int      		m_nPlayTurnType = GameConstants.GAME_PLAYTURN_TYPE_SEQUENCE;

	public static int      m_GKGameCenterTry = 0;

	public static boolean isDirty()
	{
	    return m_bDirty;
	}

	public static void resetDirty()
	{
	    m_bDirty = false;
	}

	public static void setDirty()
	{
	    m_bDirty = true;
	}

	public static void enablePlaySound()
	{
	    if(m_bGameSoundEffect == false)
	        m_bDirty = true;
	    
		m_bGameSoundEffect = true;
	}	

	public static void disablePlaySound()
	{
	    if(m_bGameSoundEffect == true)
	        m_bDirty = true;
		m_bGameSoundEffect = false;
	}	

	public static void setPlaySoundEffect(boolean enable)
	{
	    if(m_bGameSoundEffect != enable)
	        m_bDirty = true;
	    
		m_bGameSoundEffect = enable;
	}	

	public static boolean canPlaySound()
	{
		return m_bGameSoundEffect;
	}	

	public static boolean isOnline()
	{
	    return m_bGameOnline;
	}

	public static void setOnline(boolean bOnline)
	{
	    if(m_bGameOnline != bOnline)
	        m_bDirty = true;
	    
	    m_bGameOnline = bOnline;
	}

	public static int getCurrentGameType()
	{
	    return m_nGameType;
	}

	public static void setCurrentGameType(int nType)
	{
	    if(m_nGameType != nType)
	        m_bDirty = true;
	        
	    m_nGameType = nType;
	}

	public static int getCurrentThemeType()
	{
	    return m_nThemeType;
	}

	public static void setCurrentThemeType(int nType)
	{
	    if(m_nThemeType != nType)
	        m_bDirty = true;
	        
	    m_nThemeType = nType;
	}
	
	public static boolean isRoPaAutoBet()
	{
	    return m_bRoPaAutoBet;
	}

	public static void setRoPaAutoBet(boolean bAuto)
	{
	    if(m_bRoPaAutoBet != bAuto)
	        m_bDirty = true;
	    m_bRoPaAutoBet = bAuto;
	}

	public static void cacheOnlineSetting()
	{
	    m_bCacheOnlineMode = m_bGameOnline;
	}

	public static boolean isOnlineSettingChange()
	{
	    boolean bRet = (m_bCacheOnlineMode != m_bGameOnline);
	    return bRet;
	}

	public static void setPlayTurn(int nPlayTurnType)
	{
	    if(m_nPlayTurnType != nPlayTurnType)
	        m_bDirty = true;
	    m_nPlayTurnType = nPlayTurnType;
	}

	public static int getPlayTurnType()
	{
	    return m_nPlayTurnType;
	}

	public static boolean isPlayTurnBySequence()
	{
	    return (m_nPlayTurnType == GameConstants.GAME_PLAYTURN_TYPE_SEQUENCE);
	}

	public static void AddGKGameCenterAccessTry()
	{
	    ++m_GKGameCenterTry;
	}

	public static int GetGKGameCenterAccessTry()
	{
	    return m_GKGameCenterTry;
	}

	public static void ClearGKGameCenterAccessTry()
	{
	    m_GKGameCenterTry = 0;
	}
	
}
