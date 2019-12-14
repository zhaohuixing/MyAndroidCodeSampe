package com.xgadget.SimpleGambleWheel;

//import android.view.MotionEvent;

public class GambleWheelGadget 
{
	private CCompass		m_Wheel;
	private CPointer		m_Pointer;
	
	public GambleWheelGadget() 
	{
		m_Wheel = new CCompass();
		m_Pointer = new CPointer();
	}
	
	public void RegisterDelegate(GameStateDelegate delegate)
	{
		if(m_Wheel != null)
		{
			m_Wheel.RegisterDelegate(delegate);
		}
		if(m_Pointer != null)
		{
			m_Pointer.RegisterDelegate(delegate);
		}
	}
	
	public void AttachWheel(CCompassRender	wheel)
	{
		m_Wheel.AttachWheel(wheel);
	}
	
	public void AttachPointer(CPinRender	pointer)
	{
		m_Pointer.AttachPointer(pointer);
	}

	public void OnTimerEvent()
	{
		if(m_Wheel != null)
		{
			m_Wheel.OnTimerEvent();
		}
		if(m_Pointer != null)
		{
			m_Pointer.OnTimerEvent();
		}
	}
	
	public void Reset()
	{
		
	}
	
	public void Stop()
	{
		if(m_Pointer != null)
		{
			Reset();
			m_Pointer.Reset();
		}	
	}
	
	public void SetGameType(int nType)
	{
		if(m_Wheel != null)
			m_Wheel.SetGameType(nType);
	}

	public void SetGameTheme(int nTheme)
	{
		if(m_Wheel != null)
			m_Wheel.SetGameTheme(nTheme);
	}
	
	public void StartSpin(CPinActionLevel action)
	{
		if(m_Pointer != null)
			m_Pointer.StartSpin(action);
	}
	
}
