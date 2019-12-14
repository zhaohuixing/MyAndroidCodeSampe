package com.xgadget.SimpleGambleWheel;

public interface GameStateDelegate 
{
	public void SetGameType(int nType);
	public int GetGameType();
	public void SetGameState(int nState);
	public int GetGameState();
	public void PointerStopAt(int nAngle);
	public int GetPointerPosition();
	public int GetWinScopeIndex();
}
