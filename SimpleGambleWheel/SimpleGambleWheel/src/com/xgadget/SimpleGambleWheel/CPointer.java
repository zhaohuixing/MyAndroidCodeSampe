package com.xgadget.SimpleGambleWheel;

public class CPointer extends GameBaseObject 
{
	private CPinRender			m_Painter;
	
	public int                 	m_nFastCycle;
	public int                 	m_nMediumCycle;
	public int                 	m_nSlowCycle;
	public int                 	m_nSlowAngle;
	public int                 	m_nVibCycle;
	public boolean             	m_bClockwise;

    //Annimation timing parameters
	//Fast speed
	public int					m_nFastStep;
	public float				m_fFastUnit;
	
    //Medium speed
	public float				m_fMediumStep;
	public int					m_nMediumCount;
	
    //Slow speed
	public int                 	m_nSlowCycleStep;
	public float				m_fSlowStep;
	
    //Vibration
	public int					m_nVibrationCount;
	public int					m_nVibrationStep;
	
    //Result position
	public int					m_nPosition;
    

	public int                 m_nPointerState;
	
	public CPointer() 
	{
		// TODO Auto-generated constructor stub
		super();
		m_Painter = null;
		Reset();
	}

	public void AttachPointer(CPinRender	pointer)
	{
		m_Painter = pointer;
		m_Painter.AttachContainer(this);
	}
	
	private void OnFastStateTimerEvent()
	{
		++m_nFastStep;
		if(m_nFastCycle <= m_nFastStep)
		{
			m_nPointerState = GameConstants.GAME_POINTER_SPIN_MEDIUM;
			m_fMediumStep = 0;
			m_nMediumCount = 0;
		}
	}

	private void OnMediumStateTimerEvent()
	{
		float fStep = ((float)GameConstants.GAME_POINTER_MEDIUM_ANGLE_UNIT)/((float)m_nMediumCycle);
		float fAngle = (((float)(m_nMediumCycle)) - ((float)(m_nMediumCount)))*fStep;
		
		m_fMediumStep += fAngle;   
		if(360 <= m_fMediumStep)
		{
			++m_nMediumCount;
			m_fMediumStep -= 360;
		}
		
		if(m_nMediumCycle <= m_nMediumCount)
		{
			m_nPointerState = GameConstants.GAME_POINTER_SPIN_SLOW;
			m_nSlowCycleStep = 0;
			m_fSlowStep = m_fMediumStep;
		}	
	}

	private void OnSlowStateTimerEvent()
	{
		m_fSlowStep += GameConstants.GAME_POINTER_RUN_ANGLE_STEP;
		if((m_nSlowCycle-1) <= m_nSlowCycleStep)
		{
			float fAngle;
			float fAngleThreshold;
			boolean bFinished = false;
			if(m_bClockwise == true)
			{	
				fAngle = m_fSlowStep;
				fAngleThreshold = m_nSlowAngle;
				if(fAngleThreshold <= fAngle)
					bFinished = true;
			}				
			else
			{				
				fAngle = 360-m_fSlowStep;
				fAngleThreshold = 360-m_nSlowAngle; 
				if(fAngle <= fAngleThreshold)
					bFinished = true;
			}				
			
			
			if(bFinished == true)
			{
				m_nPosition = (int)fAngleThreshold; 
				if(0 < m_nVibCycle)
				{	
					m_nPointerState = GameConstants.GAME_POINTER_SPIN_VIBRATION;
					m_nVibrationCount = 0;
					m_nVibrationStep = 0;
					return;
				}
				else
				{
	                if(m_Delegate != null)
	                {    
	                    m_Delegate.PointerStopAt(m_nPosition);
	                    m_Delegate.SetGameState(GameConstants.GAME_STATE_RESULT);
	                }    
					return;
				}
			}
		}
		else if(360 <= m_fSlowStep)
		{
			++m_nSlowCycleStep;
			m_fSlowStep -= 360;
		}
	}

	private void OnVibrationStateTimerEvent()
	{
		if(m_nVibCycle <= 0)
		{
	        if(m_Delegate != null)
	        {    
	            m_Delegate.PointerStopAt(m_nPosition);
	            m_Delegate.SetGameState(GameConstants.GAME_STATE_RESULT);
	        }    
			return;
		}
		
		int dir = m_nVibrationCount%2;
		if(dir == 0)
		{	
			++m_nVibrationStep;
		}			
		else
		{	
			--m_nVibrationStep;
		}			
		if(m_nVibCycle == 0 && m_nVibrationStep == 0)
		{
	        if(m_Delegate != null)
	        {    
	            m_Delegate.PointerStopAt(m_nPosition);
	            m_Delegate.SetGameState(GameConstants.GAME_STATE_RESULT);
	        }    
			return;
		}
		
		int nAngle = m_nVibCycle*GameConstants.GAME_POINTER_VIBRATION_ANGLE_UNIT;
		if(nAngle <= Math.abs(m_nVibrationStep))
		{
			++m_nVibrationCount;
		}
		
		
		if(m_nVibrationStep == 0)
		{
			--m_nVibCycle;
		}
	}

	private void OnRunningTimerEvent()
	{
		if(m_nPointerState == GameConstants.GAME_POINTER_SPIN_FAST)
		{
			OnFastStateTimerEvent();
		}
		else if(m_nPointerState == GameConstants.GAME_POINTER_SPIN_MEDIUM)
		{
			OnMediumStateTimerEvent();
		}
		else if(m_nPointerState == GameConstants.GAME_POINTER_SPIN_SLOW)
		{	
			OnSlowStateTimerEvent();
		}
		else
		{
			OnVibrationStateTimerEvent();
		}
	}	


	
	public void OnTimerEvent()
	{
	    if(m_Delegate != null && m_Delegate.GetGameState() == GameConstants.GAME_STATE_RUN)
	    {
	        OnRunningTimerEvent();
	        if(m_Painter != null)
	        {
	        	m_Painter.invalidate();
	        }
	    }
	}
	
	public void Reset()
	{
	    m_nFastCycle = 0;
	    m_nMediumCycle = 0;
	    m_nSlowCycle = 0;
	    m_nSlowAngle = 0;
	    m_nVibCycle = 0;
	    m_bClockwise = false;
	    
	    m_nFastStep = 0;
	    m_fFastUnit = 0;
	    m_fMediumStep = 0;
	    m_nMediumCount = 0;
	    m_nSlowCycleStep = 0;
	    m_fSlowStep = 0;
	    m_nVibrationCount = 0;
	    m_nVibrationStep = 0;
	    m_nPosition = 0;
	    m_nPointerState = GameConstants.GAME_POINTER_SPIN_FAST;
	    if(m_Painter != null)
	    	m_Painter.invalidate();
	}
	
	public void StartSpin(CPinActionLevel action)
	{
	    m_nFastCycle = action.m_nFastCycle;
	    m_nMediumCycle = action.m_nMediumCycle;
	    m_nSlowCycle = action.m_nSlowCycle;
	    m_nSlowAngle = action.m_nSlowAngle;
	    m_nVibCycle = action.m_nVibCycle;
	    m_bClockwise = action.m_bClockwise;
	    
	    m_nFastStep = 0;
	    m_fFastUnit = 0;
	    m_fMediumStep = 0;
	    m_nMediumCount = 0;
	    m_nSlowCycleStep = 0;
	    m_fSlowStep = 0;
	    m_nVibrationCount = 0;
	    m_nVibrationStep = 0;
	    m_nPosition = 0;
	    m_nPointerState = GameConstants.GAME_POINTER_SPIN_FAST;
	   
		if(0 < m_nFastCycle)
		{
			m_fFastUnit = ((float)GameConstants.GAME_POINTER_FAST_ANGLE_UNIT)/((float)m_nFastCycle);
		}
		
		if(m_nFastCycle == 0 && m_nMediumCycle != 0)
		{
			m_nPointerState = GameConstants.GAME_POINTER_SPIN_MEDIUM;
		}
		else if(m_nFastCycle == 0 && m_nMediumCycle == 0 && m_nSlowCycle != 0)
		{
			m_nPointerState = GameConstants.GAME_POINTER_SPIN_SLOW;
		}
		else if(m_nFastCycle == 0 && m_nMediumCycle == 0 && m_nSlowCycle == 0)
		{
			Reset();
			return;
		}
	    
	    if(m_Delegate != null)
	    {    
	        //m_Delegate.SetGameState(GameConstants.GAME_STATE_RUN);
	        SimpleGambleWheel.m_ApplicationController.SetGameState(GameConstants.GAME_STATE_RUN);
	    }
	    if(m_Painter != null)
	    {
	    	m_Painter.invalidate();
	    }
	}
	
	public int GetGameState()
	{
		int nState = GameConstants.GAME_STATE_READY;
		
		if(m_Delegate != null)
		{
			nState = m_Delegate.GetGameState();
		}
		
		return nState;
	}
}
