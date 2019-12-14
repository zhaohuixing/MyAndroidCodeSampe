package com.xgadget.SimpleGambleWheel;

import java.util.Random;

import android.os.SystemClock;

public class GameUitltyHelper 
{
	public static int GetGameLuckNumberThreshold(int nType)
	{
	    int nRet = -1;
	 
	    switch(nType)
	    {
	        case GameConstants.GAME_TYPE_8LUCK:
	            nRet = 8;
	            break;
	        case GameConstants.GAME_TYPE_6LUCK:
	            nRet = 6;
	            break;
	        case GameConstants.GAME_TYPE_4LUCK:
	            nRet = 4;
	            break;
	        case GameConstants.GAME_TYPE_2LUCK:
	            nRet = 2;
	            break;
	    }
	    
	    return nRet;
	}

	public static int GetGameBetPledgeThreshold(int nType)
	{
	    int nRet = -1;
	    
	    switch(nType)
	    {
	        case GameConstants.GAME_TYPE_8LUCK:
	            nRet = GameConstants.GAME_BET_THRESHOLD_8LUCK;
	            break;
	        case GameConstants.GAME_TYPE_6LUCK:
	            nRet = GameConstants.GAME_BET_THRESHOLD_6LUCK;
	            break;
	        case GameConstants.GAME_TYPE_4LUCK:
	            nRet = GameConstants.GAME_BET_THRESHOLD_4LUCK;
	            break;
	        case GameConstants.GAME_TYPE_2LUCK:
	            nRet = GameConstants.GAME_BET_THRESHOLD_2LUCK;
	            break;
	    }
	    
	    return nRet;
	}

	public static int CreateRandomNumber()
	{
		long currentTime = SystemClock.currentThreadTimeMillis();
		Random rand = new Random(currentTime);
		int nRand = rand.nextInt();
		return nRand;
	}

	public static int CreateRandomNumberWithSeed(int nSeed)
	{
		long nRandSeed = SystemClock.currentThreadTimeMillis();
		if(0 < nSeed)
	        nRandSeed = nRandSeed/nSeed;
	    else
	        nRandSeed = nRandSeed/2;

		Random rand = new Random(nRandSeed);
		int nRand = rand.nextInt();
		return nRand;
	}
	
    public static float GetDistanceFactor(float x, float y)
    {
        float dt = x*x + y*y;
        if(dt == 0.0)
            return -1.0f;
        
        float sw = (float)GameLayoutConstant.GetCurrentScreenWidth();
        float sh = (float)GameLayoutConstant.GetCurrentContentViewHeight();
        
        float ds = sw*sw + sh*sh;
            
        float fRet = ds/dt;
        
        return fRet;
    }
    
    public static boolean CounterClockWise(float x1, float y1, float x2, float y2, float xc, float yc)
    {
    	boolean bRet = false;
        
    	float dx1, dx2, dy1, dy2;
    	dx1 = x2 - x1;
    	dx2 = xc - x1;
    	dy1 = y2 - y1;
    	dy2 = yc - y1;
        
    	if(dx1*dy2 > dy1*dx2)
    	{
       		bRet = true;
        	return bRet;
    	}
        
    	if(dx1*dy2 < dy1*dx2)
    	{
        	bRet = false;
        	return bRet;
        }
        
    	if((dx1*dx2 < 0.0f) || (dy1*dy2 < 0.0f))
        {
        	bRet = false;
        	return bRet;
        }
        
    	if((dx1*dx1 + dy1*dy1) < (dx2*dx2 + dy2*dy2))
        {
        	bRet = true;
        	return bRet;
        }
        
        if(y1 == y2)
        {
        	if(x2 < x1)
        	{
           		bRet = true;
            	return bRet;
        	}
        	else
        	{
            	bRet = false;
            	return bRet;
        	}
        }
        
       	if(y2 < y1)
       	{
          	bRet = false;
           	return bRet;
       	}
       	else
       	{
           	bRet = true;
           	return bRet;
       	}
    }
    
	
}
