/**
 * 
 */
package com.xgadget.ChuiNiuLite;

import java.util.Random;

import android.graphics.PointF;

/**
 * @author zhaohuixing
 *
 */
public class CConfiguration 
{
	public static final int GAME_MAXMUM_SCORE_NUMBER = 999999;
	
	public static final int GAME_PLAY_LEVELS =	4;
	public static final int GAME_PLAY_LEVEL_ONE = 0;
	public static final int GAME_PLAY_LEVEL_TWO = 1;
	public static final int GAME_PLAY_LEVEL_THREE = 2;
	public static final int GAME_PLAY_LEVEL_FOUR = 3;

	public static final int GAME_SKILL_LEVELS = 3;
	public static final int GAME_SKILL_LEVEL_ONE = 0;
	public static final int GAME_SKILL_LEVEL_TWO = 1;
	public static final int GAME_SKILL_LEVEL_THREE = 2;
	
	public static final int GAME_TIMER_TARGET_STEP = 12;
	public static final int GAME_TIMER_DEFAULT_BULLET_STEP = 8;
	public static final float GAME_DEFAULT_TARGET_SPEED_X = 0.0f;
	public static final float GAME_DEFAULT_TARGET_SPEED_Y = -4.0f;
	public static final float GAME_DEFAULT_TARGET_FLOAT_SPEED_Y	= 16.0f;
	public static final float GAME_DEFAULT_TARGET_FLOAT_SPEED_X	= 0.0f;

	public static final float GAME_DEFAULT_TARGET_BULLET_SPEED_X = 8.0f;
	public static final float GAME_DEFAULT_TARGET_BULLET_SPEED_Y = -16.0f;

	public static final int GAME_DEFAULT_TARGET_SHOOT_THRESHED = 20;
	public static final int GAME_DEFAULT_TARGET_HIT_THRESHED = 10;
	public static final int GAME_DEFAULT_TARGET_HIT_DEDUCABLE =	3;

	public static final int GAME_HITLIMIT_INCREMENTAL_SKILLONE = 0;
	public static final int GAME_HITLIMIT_INCREMENTAL_SKILLTWO = -3;
	public static final int GAME_HITLIMIT_INCREMENTAL_SKILLTHREE = -4;
	public static final int GAME_HIT_DEDUCTABLE_SKILLONE = 0;
	public static final int GAME_HIT_DEDUCTABLE_SKILLTWO = 0;
	public static final int GAME_HIT_DEDUCTABLE_SKILLTHREE = 2;
	
	public static final int GAME_TIMER_INCREMENTAL_LEVELONE = 0;
	public static final int GAME_TIMER_INCREMENTAL_LEVELTWO = 300;//2000;
	public static final int GAME_TIMER_INCREMENTAL_LEVELTHREE = 600;//4000;
	public static final int GAME_TIMER_INCREMENTAL_LEVELFOUR = 1200;//6000;
	public static final int GAME_TIMER_INCREMENTAL_SKILLONE = 0;
	public static final int GAME_TIMER_INCREMENTAL_SKILLTWO	= 600;//6000;
	public static final int GAME_TIMER_INCREMENTAL_SKILLTHREE = 1200;//12000;
	
	public static final float GAME_TIMER_INTERVAL = 0.01f;
	public static final int GAME_TIMER_GAME_TIME = 1500;//600;//12000;
	public static final int GAME_TIMER_PLAYER_STEP = 10;
	public static final int GAME_TIMER_DEFAULT_ALIEN_STEP = 10;//20;       
	public static final int GAME_TIMER_DEFAULT_BLOCK_STEP = 10;//20;       
	public static final int GAME_TIMER_DEFAULT_CLOCK_UPDATE = 10;       
	
	public static final float GAME_DEFAULT_PLAYER_MOTION_STEP_X_BASE = 10.0f;
	
	
	public static final int GAME_DEFAULT_ALIEN_NUMBER = 16;//24;
	public static final float GAME_DEFAULT_ALIEN_SPEED_X = 5.0f;//10f;
	public static final float GAME_DEFAULT_ALIEN_SPEED_Y = 0.0f;
	public static final float GAME_DEFAULT_ALIEN_SIZE_WIDTH	= 240.0f;
	public static final float GAME_DEFAULT_ALIEN_SIZE_HEIGHT = 160.0f;
	public static final float GAME_DEFAULT_ALIEN_POINT_OFFSET = 100.0f; 	
	public static final int GAME_DEFAULT_ALIEN_SHOOT_THRESHED = 50; //60

	public static final int GAME_DEFAULT_BLOCK_NUMBER = 5;
	public static final float GAME_DEFAULT_BLOCK_SPEED_X = 6.0f; //12.0f;
	public static final float GAME_DEFAULT_BLOCK_SPEED_Y = 0.0f;
	public static final int GAME_DEFAULT_BLOCK_SHOOT_THRESHED = 80;

	public static final float GAME_RAINBOW_DEFAULT_SPEED = 10.0f;
	
	public static final int GAME_BACKGROUND_BLUESKY = 0;
	public static final int GAME_BACKGROUND_NIGHTSKY = 1;
	public static final int GAME_BACKGROUND_CHECKPATTERN = 2;
	
	public static final int m_WinningScore[][] = 
	{
		{1, 2, 3, 6},
		{2, 3, 6, 10},
		{3, 4, 10, 20},
	};

	public static final int m_PLayThesholdScore[][] =
	{
	    {0, 6, 23, 42},
	    {1, 16, 26, 48},
	    {3, 19, 32, 58},
	};
	
	public static final int m_LostPenalityScore[][] =
	{
		{80, 9, 6, 3},
		{40, 8, 5, 2},
		{20, 7, 4, 1},
	};
	
	
	private static int	m_GameLevel = GAME_PLAY_LEVEL_ONE;
	private static int	m_GameSkill = GAME_SKILL_LEVEL_ONE;
	private static boolean	m_bSoundEnable = false;
	private static int	m_nGameBackground = GAME_BACKGROUND_BLUESKY;
	
	private static boolean m_bMouthMode = false; 
	private static boolean m_bFacialGestureEnable = false; 
	private static boolean m_bFGTracking = false; 
	
	
	
	public static void SetSoundEnable(boolean bEnable)
	{
		m_bSoundEnable = bEnable;
	}

	public static boolean IsSoundEnable()
	{
		return m_bSoundEnable;
	}

	public static void SetGameBackground(int nType)
	{
		m_nGameBackground = nType;
	}

	public static int GetGameBackground()
	{
		return m_nGameBackground;
	}
	
	private static int getGameTimeIncrementalByLevel()
	{
		int nRet = 0;
		
		if(m_GameLevel == GAME_PLAY_LEVEL_ONE)
			nRet = GAME_TIMER_INCREMENTAL_LEVELONE;
		else if(m_GameLevel == GAME_PLAY_LEVEL_TWO)
			nRet = GAME_TIMER_INCREMENTAL_LEVELTWO;
		else if(m_GameLevel == GAME_PLAY_LEVEL_THREE)
			nRet = GAME_TIMER_INCREMENTAL_LEVELTHREE;
		else if(m_GameLevel == GAME_PLAY_LEVEL_FOUR)
			nRet = GAME_TIMER_INCREMENTAL_LEVELFOUR;
		
		return nRet;
	}

	private static int getGameTimeIncrementalBySkill()
	{
		int nRet = 0;
		
		if(m_GameSkill == GAME_SKILL_LEVEL_ONE)
			nRet = GAME_TIMER_INCREMENTAL_SKILLONE;
		else if(m_GameSkill == GAME_SKILL_LEVEL_TWO)
			nRet = GAME_TIMER_INCREMENTAL_SKILLTWO;
		else if(m_GameSkill == GAME_SKILL_LEVEL_THREE)
			nRet = GAME_TIMER_INCREMENTAL_SKILLTHREE;
		
		return nRet;
	}
	
	public static int getGameTimerClickThreshold()
	{
		return GAME_TIMER_GAME_TIME;
	}

	

	public static int getGameTime()
	{
		int nRet = getGameTimerClickThreshold() + getGameTimeIncrementalByLevel() + getGameTimeIncrementalBySkill(); 
		return nRet;
	}
	
	public static void setGameLevel(int nLevel)
	{
		if(GAME_PLAY_LEVEL_ONE <= nLevel && nLevel <= GAME_PLAY_LEVEL_FOUR)
		{
			m_GameLevel = nLevel; 
		}
	}
	
	public static void setGameLevelOne()
	{
		m_GameLevel = GAME_PLAY_LEVEL_ONE;
	}
	
	public static void setGameLevelTwo()
	{
		m_GameLevel = GAME_PLAY_LEVEL_TWO;
	}
	
	public static void setGameLevelThree()
	{
		m_GameLevel = GAME_PLAY_LEVEL_THREE;
	}
	
	public static void setGameLevelFour()
	{
		m_GameLevel = GAME_PLAY_LEVEL_FOUR;
	}
	
	public static int getGameLevel()
	{
		return m_GameLevel;
	}
	
	public static boolean isGameLevelOne()
	{
		boolean bRet = false;
		if(m_GameLevel == GAME_PLAY_LEVEL_ONE)
			bRet = true;
		
		return bRet;
	}
	
	public static boolean isGameLevelTwo()
	{
		boolean bRet = false;
		if(m_GameLevel == GAME_PLAY_LEVEL_TWO)
			bRet = true;
		
		return bRet;
	}
	
	public static boolean isGameLevelThree()
	{
		boolean bRet = false;
		if(m_GameLevel == GAME_PLAY_LEVEL_THREE)
			bRet = true;
		
		return bRet;
	}
	
	public static boolean isGameLevelFour()
	{
		boolean bRet = false;
		if(m_GameLevel == GAME_PLAY_LEVEL_FOUR)
			bRet = true;
		
		return bRet;
	}

	public static String GetGameLevelString()
	{
		String strLevel = new String("");
		
		if(m_GameLevel == GAME_PLAY_LEVEL_ONE)
			strLevel = "L1";
		else if(m_GameLevel == GAME_PLAY_LEVEL_TWO)
			strLevel = "L2";
		else if(m_GameLevel == GAME_PLAY_LEVEL_THREE)
			strLevel = "L3";
		else if(m_GameLevel == GAME_PLAY_LEVEL_FOUR)
			strLevel = "L4";
		
		return strLevel;
	}
	
	public static void setGameSkill(int nSkill)
	{
		if(GAME_SKILL_LEVEL_ONE <= nSkill && nSkill <= GAME_SKILL_LEVEL_THREE)
		{	
			m_GameSkill = nSkill;
		}
	}
	
	public static void setGameSkillOne()
	{
		m_GameSkill = GAME_SKILL_LEVEL_ONE;
	}
	
	public static void setGameSkillTwo()
	{
		m_GameSkill = GAME_SKILL_LEVEL_TWO;
	}
	
	public static void setGameSkillThree()
	{
		m_GameSkill = GAME_SKILL_LEVEL_THREE;
	}
	

	public static int getGameSkill()
	{
		return m_GameSkill;
	}
	
	public static boolean isGameSkillOne()
	{
		boolean bRet = false;
		if(m_GameSkill == GAME_SKILL_LEVEL_ONE)
			bRet = true;
		
		return bRet;
	}
	
	public static boolean isGameSkillTwo()
	{
		boolean bRet = false;
		if(m_GameSkill == GAME_SKILL_LEVEL_TWO)
			bRet = true;
		
		return bRet;
	}
	
	public static boolean isGameSkillThree()
	{
		boolean bRet = false;
		if(m_GameSkill == GAME_SKILL_LEVEL_THREE)
			bRet = true;
		
		return bRet;
	}
	
	public static String GetGameSkillString()
	{
		String strLevel = new String("");
		
		if(m_GameSkill == GAME_SKILL_LEVEL_ONE)
			strLevel = "S1";
		else if(m_GameSkill == GAME_SKILL_LEVEL_TWO)
			strLevel = "S2";
		else if(m_GameSkill == GAME_SKILL_LEVEL_THREE)
			strLevel = "S3";
		
		return strLevel;
	}

	public static String GetGameSettingString()
	{
		String strLevel = new String("");
		
		strLevel += GetGameLevelString();
		strLevel += ",";
		strLevel += GetGameSkillString();
		
		return strLevel;
	}
	
	public static boolean canTargetBlast()
	{
		boolean bRet = false;
		
		if(m_GameLevel != GAME_PLAY_LEVEL_ONE)
			bRet = true;
		
		return bRet;
	}

	public static boolean canPlayerJump()
	{
		boolean bRet = false;
		
		if(m_GameLevel == GAME_PLAY_LEVEL_FOUR)
			bRet = true;
		
		return bRet;
	}
	
	public static int getBulletTimerElapse()
	{
		int nRet = GAME_TIMER_DEFAULT_BULLET_STEP;
		int nFactor = 1;
		
		if(m_GameSkill == GAME_SKILL_LEVEL_TWO)
		{	
			nFactor = 2;
		}	
		else if(m_GameSkill == GAME_SKILL_LEVEL_THREE)
		{
			nFactor = 4;
		}
		
		nRet = (int)(((float)nRet)/((float)nFactor));
		
		return nRet;
	}	
	
	private static int getTargetIncrementalBySkill()
	{
		int nRet = 0;
		
		if(m_GameSkill == GAME_SKILL_LEVEL_ONE)
			nRet = GAME_HITLIMIT_INCREMENTAL_SKILLONE;
		else if(m_GameSkill == GAME_SKILL_LEVEL_TWO)
			nRet = GAME_HITLIMIT_INCREMENTAL_SKILLTWO;
		else if(m_GameSkill == GAME_SKILL_LEVEL_THREE)
			nRet = GAME_HITLIMIT_INCREMENTAL_SKILLTHREE;
		
		return nRet;
	}

	public static int getTargetHitLimit()
	{
		int nRet = GAME_DEFAULT_TARGET_SHOOT_THRESHED+getTargetIncrementalBySkill();
        return nRet;		
	}
	
	private static int getTargetHitDeductableBySkill()
	{
		int nRet = 0;
		
		if(m_GameSkill == GAME_SKILL_LEVEL_ONE)
			nRet = GAME_HIT_DEDUCTABLE_SKILLONE;
		else if(m_GameSkill == GAME_SKILL_LEVEL_TWO)
			nRet = GAME_HIT_DEDUCTABLE_SKILLTWO;
		else if(m_GameSkill == GAME_SKILL_LEVEL_THREE)
			nRet = GAME_HIT_DEDUCTABLE_SKILLTHREE;
		
		return nRet;
	}
	
	public static int getTargetHitDeductable()
	{
		int nRet = GAME_DEFAULT_TARGET_HIT_DEDUCABLE + getTargetHitDeductableBySkill();
		return nRet;
	}

	public static int getTargetAnimationDelayThreshold()
	{
		int nRet = 0;
			
		if(m_GameSkill == GAME_SKILL_LEVEL_TWO)
			nRet += 1;
		else if(m_GameSkill == GAME_SKILL_LEVEL_THREE)
			nRet += 2;

		return nRet;
	}
	
	public static int getTargetTimerStep()
	{
		int nRet = GAME_TIMER_TARGET_STEP;
		int nFactor = 0;
		
		if(m_GameSkill == GAME_SKILL_LEVEL_TWO)
		{
			nFactor = 7;
		}	
		else if(m_GameSkill == GAME_SKILL_LEVEL_THREE)
		{	
			nFactor = 10;
		}
		
		nRet = nRet - nFactor;
		
		return nRet;
		
	}
	
	public static PointF getTargetBulletSpeed()
	{
		PointF pt = new PointF(GAME_DEFAULT_TARGET_BULLET_SPEED_X, GAME_DEFAULT_TARGET_BULLET_SPEED_Y);
		
		float fFactor = 1.0f;
		
		if(m_GameSkill == GAME_SKILL_LEVEL_TWO)
		{	
			fFactor = 1.4f;
		}	
		else if(m_GameSkill == GAME_SKILL_LEVEL_THREE)
		{	
			fFactor = 1.8f;
		}
		
		
		float fSign = 1.0f;
		Random rand = new Random();
		int nr = rand.nextInt();
		int n = nr%3;
		if(n == 0)
			fSign = 0.0f;
		else if(n == 1)
			fSign = -1.0f;
		
		float fRatio = ((float)(nr%4+1))/4.0f;
		pt.x = pt.x*fSign*fRatio/fFactor;
		pt.y = pt.y/fFactor;
		
		return pt;
		
	}

	public static float getTargetSpeedY()
	{
		float fRet = GAME_DEFAULT_TARGET_SPEED_Y;
		if(m_GameSkill == GAME_SKILL_LEVEL_TWO)
		{
			fRet *= 1.3;
		}	
		else if(m_GameSkill == GAME_SKILL_LEVEL_THREE)
		{
			fRet *= 1.6;
		}
		if(m_GameLevel == GAME_PLAY_LEVEL_TWO)
		{
			fRet *= 1.2;
		}	
		else if(m_GameLevel == GAME_PLAY_LEVEL_THREE)
		{
			fRet *= 1.4;
		}	
		if(m_GameLevel == GAME_PLAY_LEVEL_FOUR)
		{
			fRet *= 1.6;
		}	
		return fRet;
	}
	
	public static float getPlayerMotionStep()
	{
		float fRet = GAME_DEFAULT_PLAYER_MOTION_STEP_X_BASE;
		
		if(m_GameSkill == GAME_SKILL_LEVEL_TWO)
		{
			fRet *= 1.5f;
		}	
		else if(m_GameSkill == GAME_SKILL_LEVEL_THREE)
		{	
			fRet *= 2.0f;
		}
		
		return fRet;
	}
	
	public static boolean canTargetShoot()
	{
		boolean bRet = false;
		
		if(m_GameLevel == GAME_PLAY_LEVEL_FOUR || m_GameLevel == GAME_PLAY_LEVEL_THREE)
			bRet = true;
		
		return bRet;
	}
	
	public static int getAlienTimerElapse()
	{
		int nRet = GAME_TIMER_DEFAULT_ALIEN_STEP;
		int nFactor = 1;
		
		if(m_GameSkill == GAME_SKILL_LEVEL_TWO)
			nFactor = 3;
		else if(m_GameSkill == GAME_SKILL_LEVEL_THREE)
			nFactor = 6;
		
		nRet = (int)(((float)nRet)/((float)nFactor));
		
		return nRet;
	}
	
	public static float getRandomCloudWidth(int nRand)
	{
		float fRet = GAME_DEFAULT_ALIEN_SIZE_WIDTH;
		float fRatioiPhone[] = {1.1f, 1.05f, 1.0f, 0.7f, 0.6f, 0.5f};
		
		if(0 <= nRand && nRand < 6)
		{
			fRet *= fRatioiPhone[nRand];
		}
		
		return fRet;
	}
	
	public static float getRandomCloudHeight(int nRand)
	{
		float fRet = GAME_DEFAULT_ALIEN_SIZE_HEIGHT;
		float fRatioiPhone[] = {1.1f, 1.05f, 1.0f, 0.8f, 0.5f};
		
		if(0 <= nRand && nRand < 5)
		{	
			fRet *= fRatioiPhone[nRand];
		}	
		
		return fRet;
	}
	
	public static boolean canAlienShaking()
	{
		boolean bRet = false;
		
		if(m_GameLevel != GAME_PLAY_LEVEL_ONE)
			bRet = true;
		
		return bRet;
	}	
	
	public static int getAlienShootThreshold(boolean bUseRand)
	{
		int nRet = GAME_DEFAULT_ALIEN_SHOOT_THRESHED;
		if(m_GameSkill == GAME_SKILL_LEVEL_TWO)
			nRet = GAME_DEFAULT_ALIEN_SHOOT_THRESHED/2;
		else if(m_GameSkill == GAME_SKILL_LEVEL_THREE)
			nRet = GAME_DEFAULT_ALIEN_SHOOT_THRESHED/4;

		if(m_GameLevel == GAME_PLAY_LEVEL_TWO)
		{
			nRet -= 4;
		}	
		else if(m_GameLevel == GAME_PLAY_LEVEL_THREE)
		{
			nRet -= 8;
		}	
		if(m_GameLevel == GAME_PLAY_LEVEL_FOUR)
		{
			nRet -= 12;
		}	
		if(nRet < 2)
			nRet = 2;
		
		if(bUseRand == true)
		{	
			Random rand = new Random();
			int n = rand.nextInt();
			nRet = n%nRet + 2;
		}
		
		return nRet;
	}
	
	public static int getRainRowStartTime()
	{
		int nTime = getGameTime();
		int nRBTime = getRainBowPlayTime();
		int nRet = nTime - nRBTime;
		return nRet;
		
	}
	
	public static int getBlockageTimerElapse()
	{
		int nRet = GAME_TIMER_DEFAULT_BLOCK_STEP;
		if(m_GameSkill == GAME_SKILL_LEVEL_TWO)
			nRet = 6*nRet/8;
		else if(m_GameSkill == GAME_SKILL_LEVEL_THREE)
			nRet = nRet/2;
		
		return nRet;
	}
	
	public static boolean canKnockDownTarget()
	{
		boolean bRet = true;
		
		if(m_GameLevel == GAME_PLAY_LEVEL_ONE)
			bRet = false;
		
		return bRet;
	}
	
	public static int getBlockageShootThreshold()
	{
		int nRet = GAME_DEFAULT_BLOCK_SHOOT_THRESHED;
		float fTemp = GAME_DEFAULT_BLOCK_SHOOT_THRESHED;  
		
		if(m_GameSkill == GAME_SKILL_LEVEL_TWO)
			fTemp = fTemp*0.6f;
		else if(m_GameSkill == GAME_SKILL_LEVEL_THREE)
			fTemp *= 0.4f;
		nRet = (int)fTemp;
		
		return nRet;
	}
	
	public static boolean canShootBlock()
	{
		return CConfiguration.canPlayerJump();
	}
	
	public static PointF getBlockageSpeed()
	{
		PointF pt = new PointF(GAME_DEFAULT_BLOCK_SPEED_X, GAME_DEFAULT_BLOCK_SPEED_Y);
		
		if(m_GameSkill == GAME_SKILL_LEVEL_TWO)
			pt.x += 5.0f;
		else if(m_GameSkill == GAME_SKILL_LEVEL_THREE)
			pt.x += 7.0f;
		
		return pt;
	}
	
	public static int getRainBowTimerStep()
	{
		int nRet = 2; //GAME_TIMER_DEFAULT_ALIEN_STEP/20;
		
		return nRet;
	}
	
	public static float getRainBowSpeed()
	{
		float fRet = GAME_RAINBOW_DEFAULT_SPEED;
		
		return fRet;
	}
	
	public static int getRainBowPlayTime()
	{
		int nRet = 0;
		float fRainbowWidth = CGameLayout.getRainBowWidth();
		float fSceneWidth = CGameLayout.GetGameSceneWidth();
		float fSpeed = getRainBowSpeed();
		float fMovement = (fRainbowWidth+fSceneWidth)*0.5f;
		float fTiming = fMovement/fSpeed;
		int nStep = getRainBowTimerStep();
		
		nRet =(int)(((float)nStep)*fTiming);
		return nRet;
	}	
	
	public static boolean canBirdShoot()
	{
		boolean bRet = false;
		
		if(m_GameLevel == GAME_PLAY_LEVEL_FOUR || m_GameLevel == GAME_PLAY_LEVEL_THREE || (m_GameLevel == GAME_PLAY_LEVEL_TWO && m_GameSkill != GAME_SKILL_LEVEL_ONE))
			bRet = true;
		
		return bRet;
	}
	
	private static boolean  m_bThunderTheme = false;
	public static void setThunderTheme(boolean bYes)
	{
	    m_bThunderTheme = bYes;
	}

	public static boolean getThunderTheme()
	{
	    return m_bThunderTheme;
	}
	
	public static int getGameWinScore(int nSkill, int nLevel)
	{
	    int nRet = -1;
	    
	    if(0 <= nSkill && nSkill <= 2 && 0 <= nLevel && nLevel <= 3)
	    {
	        nRet = m_WinningScore[nSkill][nLevel];
	    }
	    
	    return nRet;
	}
	
	public static int getGamePLayThesholdScore(int nSkill, int nLevel)
	{
	    int nRet = -1;

	    if(0 <= nSkill && nSkill <= 2 && 0 <= nLevel && nLevel <= 3)
	    {
	        nRet = m_PLayThesholdScore[nSkill][nLevel];
	    }
	    
	    return nRet;
	}
	
	public static int getGameLostPenalityScore(int nSkill, int nLevel)
	{
	    int nRet = -1;

	    if(0 <= nSkill && nSkill <= 2 && 0 <= nLevel && nLevel <= 3)
	    {
	        nRet = m_LostPenalityScore[nSkill][nLevel];
	    }
	    
	    return nRet;
	}

	public static int getCanGamePlaySkillAtScore(int nScore)
	{
	    int nSkill = 0;
	    int nValue = -1;
	    boolean bSmall = true;
	    
	    for(int nLevelIndex = 0; nLevelIndex <= 3; ++nLevelIndex)
	    {
	        for(int nSkillIndex = 0; nSkillIndex <= 2; ++nSkillIndex)
	        {
	            nSkill = nSkillIndex;
	            nValue = m_PLayThesholdScore[nSkillIndex][nLevelIndex];
	            if(nValue < nScore)
	            {
	                bSmall = false;
	            }
	            else if(nValue == nScore)
	            {
	                return nSkill;
	            }
	            else
	            {
	                if(bSmall == false)
	                {
	                    nSkill = nSkillIndex-1;
	                    if(nSkill < 0)
	                        nSkill = 0;
	                    return nSkill;
	                }
	            }
	        }
	    }
	    if(GAME_SKILL_LEVEL_THREE < nSkill)
	        nSkill = GAME_SKILL_LEVEL_THREE;
	    
	    
	    return nSkill;
	}

	public static int getCanGamePlayLevelAtScore(int nScore)
	{
	    int nLevel = 0;
	    int nValue = -1;
	    boolean bSmall = false;
	    
	    for(int nLevelIndex = 0; nLevelIndex <= 3; ++nLevelIndex)
	    {
	        for(int nSkillIndex = 0; nSkillIndex <= 2; ++nSkillIndex)
	        {
	            nLevel = nLevelIndex;
	            nValue = m_PLayThesholdScore[nSkillIndex][nLevelIndex];
	            if(nValue < nScore)
	            {
	                bSmall = false;
	            }
	            else if(nValue == nScore)
	            {
	                return nLevel;
	            }
	            else
	            {
	                if(bSmall == false)
	                {
	                    return nLevel;
	                }
	            }
	        }
	    }
	    if(GAME_PLAY_LEVEL_FOUR < nLevel)
	        nLevel = GAME_PLAY_LEVEL_FOUR;
	    
	    
	    return nLevel;
	}
	
	public static boolean IsMouthMode()
	{
		return m_bMouthMode; 
	}

	public static void SetMouthMode(boolean bEnable)
	{
		m_bMouthMode = bEnable;
		if(m_bMouthMode == false)
		{	
			m_bFacialGestureEnable = false; 
			m_bFGTracking = false;
		}
	}

	public static boolean IsFGEanbled()
	{
		if(m_bMouthMode == false)
		{	
			return false;
		}
		
		return m_bFacialGestureEnable; 
	}
	
	public static void SetFGEnable(boolean bEnable)
	{
		m_bFacialGestureEnable = bEnable; 
	}

	public static void SetFGTrackingState(boolean bEnable)
	{
		m_bFGTracking = bEnable; 
	}
	
	public static boolean IsHoldFGTracking()
	{
		if(m_bMouthMode == false || m_bFacialGestureEnable == false)
		{	
			return false;
		}
		
		return m_bFGTracking; 
	}
}
