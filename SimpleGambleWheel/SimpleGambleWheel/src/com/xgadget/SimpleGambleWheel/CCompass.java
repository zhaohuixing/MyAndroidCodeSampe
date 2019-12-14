package com.xgadget.SimpleGambleWheel;

public class CCompass extends GameBaseObject 
{
    private CCompassRender     m_Painter;

	public CCompass() 
	{
		// TODO Auto-generated constructor stub
		super();
		m_Painter = null;
	}
	
	public void OnTimerEvent()
	{
		if(m_Painter != null)
		{
			m_Painter.OnTimerEvent();
		}
	}
	
	public void AttachWheel(CCompassRender	wheel)
	{
		m_Painter = wheel;
	}
	
	public void SetGameType(int nType)
	{
		if(m_Painter != null)
		{
			m_Painter.SetGameType(nType);
		}
	}
	
	public void SetGameTheme(int nType)
	{
		if(m_Painter != null)
		{
			m_Painter.SetGameTheme(nType);
		}
	}

}
