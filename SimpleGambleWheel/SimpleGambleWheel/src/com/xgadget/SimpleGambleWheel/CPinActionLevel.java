package com.xgadget.SimpleGambleWheel;

public class CPinActionLevel 
{
	public static final int TOUCH_SPEED_FAST_THRESHOLD = 20;
	public static final int TOUCH_SPEED_MEDIUM_THRESHOLD = 30;
	public static final int TOUCH_SPEED_SLOW_THRESHOLD = 50;
	
	public int     	m_nFastCycle;
	public int     	m_nMediumCycle;
	public int     	m_nSlowCycle;
	public int     	m_nSlowAngle;
	public int     	m_nVibCycle;
	public boolean    	m_bClockwise;

	public CPinActionLevel() 
	{
		// TODO Auto-generated constructor stub
	    m_nFastCycle = 0;
	    m_nMediumCycle = 0;
	    m_nSlowCycle = 0;
	    m_nSlowAngle = 0;
	    m_nVibCycle = 0;
	    m_bClockwise = false;
	}

	private void GenerateDefaultSlowLevelPinAction(boolean bClockWise)
	{
		int nRand = GameUitltyHelper.CreateRandomNumber();
		if(nRand < 0)
			nRand *= -1;
		
		m_nFastCycle = 0;
		m_nMediumCycle = 0; 
		m_nSlowCycle = 1 + nRand%2;		
		m_nSlowAngle = nRand%360; 
		m_nVibCycle = 0 + nRand%4;
		m_bClockwise = bClockWise;
	}

/*	
	private void GenerateDefaultSlowLevelPinAction2(boolean bClockWise)
	{
		int nRand = GameUitltyHelper.CreateRandomNumber();//GetRandNumber();
		if(nRand < 0)
			nRand *= -1;
		
		m_nFastCycle = 0;
		m_nMediumCycle = 0; 
		m_nSlowCycle = 1 + nRand%3;		
		m_nSlowAngle = nRand%360; 
		m_nVibCycle = nRand%4;
		m_bClockwise = bClockWise;
	}
*/

	private void GenerateDefaultMeduimLevelPinAction(boolean bClockWise)
	{
		int nRand = GameUitltyHelper.CreateRandomNumber();//GetRandNumber();
		if(nRand < 0)
			nRand *= -1;
	    
		m_nFastCycle = 0;
		m_nMediumCycle = nRand%4 + 3; 
		m_nSlowCycle = 1 + nRand%2;		
		m_nSlowAngle = nRand%360; 
		m_nVibCycle = 0 + nRand%3;
		m_bClockwise = bClockWise;
	}

	private void GenerateSuperFastPinAction(boolean bClockWise)
	{
		int nRand = GameUitltyHelper.CreateRandomNumber();//GetRandNumber();
		if(nRand < 0)
			nRand *= -1;
	    
		m_nFastCycle = nRand%8 + 12;
		m_nMediumCycle = nRand%6 + 5; 
		m_nSlowCycle = 1 + nRand%3;		
		m_nSlowAngle = nRand%360; 
		m_nVibCycle = 0 + nRand%6;
		m_bClockwise = bClockWise;
	}

	private void GenerateFastPinAction(boolean bClockWise)
	{
		int nRand = GameUitltyHelper.CreateRandomNumber();//GetRandNumber();
		if(nRand < 0)
			nRand *= -1;
	    
		m_nFastCycle = nRand%6 + 6;
		m_nMediumCycle = nRand%5 + 5; 
		m_nSlowCycle = 1 + nRand%3;		
		m_nSlowAngle = nRand%360; 
		m_nVibCycle = 0 + nRand%4;
		m_bClockwise = bClockWise;
	}

	private boolean GetTouchClockWise(float x1, float y1, float x2, float y2)
	{
	    boolean bRet = true;
	    
		float cx = ((float)GameLayoutConstant.GetCurrentScreenWidth())/2.0f;
		float cy = ((float)GameLayoutConstant.GetCurrentContentViewHeight())/2.0f;
		if(GameUitltyHelper.CounterClockWise(x1, y1, x2, y2, cx, cy))
	    {
	        bRet = true;
	    }
	    else
	    {
	        bRet = false;
	    }
	    
	    return bRet;
	}

	private void InternalCreateAction(float x1, float y1, float x2, float y2, float time)
	{
		float fx = x2 - x1;
		float fy = y2 - y1;
	    float dFactor = GameUitltyHelper.GetDistanceFactor(fx, fy);
		if(dFactor < 0)
		{
			GenerateDefaultMeduimLevelPinAction(true);
			return; 
		}
	    
	    boolean bClockWise = GetTouchClockWise(x1, y1, x2, y2);

		float dThreshold = dFactor*time; 
		
		
		if(dThreshold <= CPinActionLevel.TOUCH_SPEED_FAST_THRESHOLD)
		{
			GenerateSuperFastPinAction(bClockWise);
		}
		else if(CPinActionLevel.TOUCH_SPEED_FAST_THRESHOLD < dThreshold && dThreshold <= CPinActionLevel.TOUCH_SPEED_MEDIUM_THRESHOLD)
		{
			GenerateFastPinAction(bClockWise);
		}
		else if(CPinActionLevel.TOUCH_SPEED_MEDIUM_THRESHOLD < dThreshold && dThreshold <= CPinActionLevel.TOUCH_SPEED_SLOW_THRESHOLD)
		{
			GenerateDefaultMeduimLevelPinAction(bClockWise);
		}
		else
		{
			GenerateDefaultSlowLevelPinAction(bClockWise);
		}
	}

	public void CreateLevel(float x1, float y1, float x2, float y2, float time)
	{
	    InternalCreateAction(x1, y1, x2, y2, time);
	}

	private void InternalRandomCreateAction()
	{
		int nRand = GameUitltyHelper.CreateRandomNumber();//GetRandNumber();
		float sw = ((float)GameLayoutConstant.GetCurrentScreenWidth());
		float sh = ((float)GameLayoutConstant.GetCurrentContentViewHeight());
	    float ds = sw*sw + sh*sh;

	    int ns = (int)ds;
	    int dr = nRand%ns;
	    
	    float dFactor = ((float)dr)/ds;
	    boolean bClockWise = ((nRand%2) == 0 ? true:false);
	    int nTime = nRand%TOUCH_SPEED_SLOW_THRESHOLD;
	    float fTime = (float)nTime;
	    
		float dThreshold = dFactor*fTime; 
		if(dThreshold <= TOUCH_SPEED_FAST_THRESHOLD)
		{
			GenerateSuperFastPinAction(bClockWise);
		}
		else if(TOUCH_SPEED_FAST_THRESHOLD < dThreshold && dThreshold <= TOUCH_SPEED_MEDIUM_THRESHOLD)
		{
			GenerateFastPinAction(bClockWise);
		}
		else if(TOUCH_SPEED_MEDIUM_THRESHOLD < dThreshold && dThreshold <= TOUCH_SPEED_SLOW_THRESHOLD)
		{
			GenerateDefaultMeduimLevelPinAction(bClockWise);
		}
		else
		{
			GenerateDefaultSlowLevelPinAction(bClockWise);
		}
	}

	public void CreateRandomLevel()
	{
	     InternalRandomCreateAction();
	}

	
}
