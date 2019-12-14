package com.xgadget.SimpleGambleWheel;

public class GameBaseObject 
{
	protected GameStateDelegate	m_Delegate;

	public GameBaseObject() 
	{
		// TODO Auto-generated constructor stub
		m_Delegate = null;
	}
	
	public void RegisterDelegate(GameStateDelegate delegate)
	{
		m_Delegate = delegate;
	}

}
