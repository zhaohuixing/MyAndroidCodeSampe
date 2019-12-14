package com.xgadget.SimpleGambleWheel;

import java.util.Vector;

public class GamblePlaySession 
{
    //private CGameLobby         m_Parent;
    
    private Vector<CLuckScope>         m_Luck2Scopes;
    private Vector<CLuckScope>         m_Luck4Scopes;
    private Vector<CLuckScope>         m_Luck6Scopes;
    private Vector<CLuckScope>         m_Luck8Scopes;

    private int                  m_nGamePlayType;
    private int                  m_nGamePlayState;
    
    private int                  m_nPosition;
    private int                  m_nWinScopeIndex;

    //This is temporary variable for test
    private int                  m_nChips;

    private void initLuck2Scopes()
    {
	    m_Luck2Scopes = new Vector<CLuckScope>();
	    for(int i = 0; i < 2; ++i)
        {
            int nStartAngle = i*180;
            int nEndAngle = nStartAngle + 180;
            CLuckScope scope = new CLuckScope();
            scope.CreateScope(nStartAngle, nEndAngle);
            m_Luck2Scopes.add(scope);
        }    	
    }

    private void initLuck4Scopes()
    {
	    m_Luck4Scopes = new Vector<CLuckScope>();
        for(int i = 0; i < 4; ++i)
        {
            int nStartAngle = i*90;
            int nEndAngle = nStartAngle + 90;
            CLuckScope scope = new CLuckScope();
            scope.CreateScope(nStartAngle, nEndAngle);
            m_Luck4Scopes.add(scope);
        }
    }

    private void initLuck6Scopes()
    {
	    m_Luck6Scopes = new Vector<CLuckScope>();
        for(int i = 0; i < 6; ++i)
        {
            int nStartAngle = i*60;
            int nEndAngle = nStartAngle + 60;
            CLuckScope scope = new CLuckScope();
            scope.CreateScope(nStartAngle, nEndAngle);
            m_Luck6Scopes.add(scope);
        }
    }

    private void initLuck8Scopes()
    {
	    m_Luck8Scopes = new Vector<CLuckScope>();
        for(int i = 0; i < 8; ++i)
        {
            int nStartAngle = i*45;
            int nEndAngle = nStartAngle + 45;
            CLuckScope scope = new CLuckScope();
            scope.CreateScope(nStartAngle, nEndAngle);
            m_Luck8Scopes.add(scope);
        }
    }
    
	private void Initialize()
	{
		//m_Parent = null;
		initLuck2Scopes();
		initLuck4Scopes();
		initLuck6Scopes();
		initLuck8Scopes();
		
        m_nPosition = -1;
        m_nWinScopeIndex = -1;
        
        m_nGamePlayType = GameConstants.GAME_TYPE_8LUCK;
        m_nGamePlayState = GameConstants.GAME_STATE_READY;
        
        //This is temporary variable for test
        m_nChips = 0;
		
	}
    
    public GamblePlaySession() 
	{
		// TODO Auto-generated constructor stub
		Initialize();
	}

    public void PlayCurrentGameStateSound()
    {
        switch(m_nGamePlayState)
        {
            case GameConstants.GAME_STATE_READY:
            case GameConstants.GAME_STATE_RESET:
            {    
                if(SoundSource.IsPlayGameResultSound() == true)
                    SoundSource.StopGameResultSound();
                if(SoundSource.IsPlayPointerSpinSound() == true)
                    SoundSource.StopPointerSpinSound();
                if(SoundSource.IsPlayWheelStaticSound() == false)
                    SoundSource.PlayWheelStaticSound();
                
                break;
            }
            case GameConstants.GAME_STATE_RUN:
            {
                if(SoundSource.IsPlayWheelStaticSound() == true)
                    SoundSource.StopWheelStaticSound();
                if(SoundSource.IsPlayGameResultSound() == true)
                    SoundSource.StopGameResultSound();
                if(SoundSource.IsPlayPointerSpinSound() == false)
                    SoundSource.PlayPointerSpinSound();
                    
                break;
            }
            case GameConstants.GAME_STATE_RESULT:
            {
                if(SoundSource.IsPlayWheelStaticSound() == true)
                    SoundSource.StopWheelStaticSound();
                if(SoundSource.IsPlayPointerSpinSound() == true)
                    SoundSource.StopPointerSpinSound();
                if(SoundSource.IsPlayGameResultSound() == false)
                    SoundSource.PlayGameResultSound();
                break;
            }
        }
    }
    
    //public void RegisterParent(CGameLobby parent)
    //{
        //m_Parent = parent;
    //}

    public void SetGameType(int nType)
    {
        m_nGamePlayType = nType;
    }

    public int GetGameType()
    {
        return m_nGamePlayType;
    }

    public void SetGameState(int nState)
    {
        boolean bChange = (m_nGamePlayState != nState);
        m_nGamePlayState = nState;
        if(bChange == true && Configuration.canPlaySound())
        {
            this.PlayCurrentGameStateSound();
        }
    }

    public int GetGameState()
    {
        return m_nGamePlayState;
    }

    public void CalculateLuck2WinScope()
    {
        for(int i = 0; i < 2; ++i)
        {
            if(m_Luck2Scopes.elementAt(i).InScope(m_nPosition) == true)
            {
                m_nWinScopeIndex = i;
                return;
            }
        }
    }

    public void CalculateLuck4WinScope()
    {
        for(int i = 0; i < 4; ++i)
        {
            if(m_Luck4Scopes.elementAt(i).InScope(m_nPosition) == true)
            {
                m_nWinScopeIndex = i;
                return;
            }
        }
    }

    public void CalculateLuck6WinScope()
    {
        for(int i = 0; i < 6; ++i)
        {
            if(m_Luck6Scopes.elementAt(i).InScope(m_nPosition))
            {
                m_nWinScopeIndex = i;
                return;
            }
        }
    }

    public void CalculateLuck8WinScope()
    {
        for(int i = 0; i < 8; ++i)
        {
            if(m_Luck8Scopes.elementAt(i).InScope(m_nPosition))
            {
                m_nWinScopeIndex = i;
                return;
            }
        }
    }


    public void CalculateWinScope()
    {
        switch(m_nGamePlayType)
        {
            case GameConstants.GAME_TYPE_2LUCK:
                CalculateLuck2WinScope();
                break;
            case GameConstants.GAME_TYPE_4LUCK:
                CalculateLuck4WinScope();
                break;
            case GameConstants.GAME_TYPE_6LUCK:
                CalculateLuck6WinScope();
                break;
            case GameConstants.GAME_TYPE_8LUCK:
                CalculateLuck8WinScope();
                break;
        }
    }

    public void PointerStopAt(int nAngle)
    {
        m_nPosition = nAngle;
        CalculateWinScope();
    }

    public int GetPointerPosition()
    {
        return m_nPosition;
    }

    public int GetWinScopeIndex()
    {
        return m_nWinScopeIndex;
    }

    public void Reset()
    {
        m_nPosition = -1;
        m_nWinScopeIndex = -1;
    }

    public int GetMyCurrentMoney()
    {
        return m_nChips;
    }

    public void AddMoneyToMyAccount(int nChips)
    {
        m_nChips += nChips;
    }
    
}
