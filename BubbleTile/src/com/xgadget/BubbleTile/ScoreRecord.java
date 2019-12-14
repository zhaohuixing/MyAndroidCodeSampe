package com.xgadget.BubbleTile;

public class ScoreRecord {
	//Grid Type
	int			m_nGridType;
    
	//Grid Layout
	int			m_nGridlayout;
	
	//Grid edge
    int         m_nEdge;
    
    //The record of least step to win the game
	int			m_nLeastRecord;
    
    //Total win count
    int         m_nTotalWinCount; 

	//The date when got least score
	int		m_nYear4Least;
	int		m_nMonth4Least;
	int		m_nDay4Least;
    
    int     m_nLevel;
    
    
public ScoreRecord()
    {
    		m_nGridType = -1;
            m_nGridlayout = -1; 
    		m_nEdge = 0;
            m_nLeastRecord = 0;
            m_nTotalWinCount = 0;
            m_nYear4Least = 0;
            m_nMonth4Least = 0;
            m_nDay4Least = 0;
            m_nLevel = 0;

    }

public ScoreRecord(int nGridType, int nLayout, int nEdge)
    {

    		m_nGridType = nGridType;
            m_nGridlayout = nLayout; 
    		m_nEdge = nEdge;
            m_nLeastRecord = 0;
            m_nTotalWinCount = 0;
            m_nYear4Least = 0;
            m_nMonth4Least = 0;
            m_nDay4Least = 0;
            m_nLevel = 0;
    }

public void AddScore(int nStep)
    {
    	++m_nTotalWinCount;
    	
    	if(nStep < m_nLeastRecord)
    	{
    		m_nLeastRecord = nStep;
            GameConfiguration.PrepareCurrentDate();
            m_nYear4Least = GameConfiguration.GetYearOfCurrentDate();
            m_nMonth4Least = GameConfiguration.GetMonthOfCurrentDate();
            m_nDay4Least = GameConfiguration.GetDayOfCurrentDate();
    	}	
        else if(m_nLeastRecord == 0 && 0 < nStep)
        {
    		m_nLeastRecord = nStep;
            GameConfiguration.PrepareCurrentDate();
            m_nYear4Least = GameConfiguration.GetYearOfCurrentDate();
            m_nMonth4Least = GameConfiguration.GetMonthOfCurrentDate();
            m_nDay4Least = GameConfiguration.GetDayOfCurrentDate();
        }
    }	

    //
    //Save to storage 
    //
/*
public  void SaveType((NSUserDefaults*)prefs int index)
    {
    	NSString* sKey = [StringFactory GetString_GridTypeKey:index];	
    	[prefs setInteger:m_nGridType forKey:sKey];
    }


    -(void)SaveLayout:(NSUserDefaults*)prefs scoreIndex:(int)index
    {
    	NSString* sKey = [StringFactory GetString_GridLayoutKey:index];	
    	[prefs setInteger:m_nGridlayout forKey:sKey];
    }


    -(void)SaveEdge:(NSUserDefaults*)prefs scoreIndex:(int)index
    {
    	NSString* sKey = [StringFactory GetString_GridEdgeKey:index];	
    	[prefs setInteger:m_nEdge forKey:sKey];
    }


    -(void)SaveLeastStep:(NSUserDefaults*)prefs scoreIndex:(int)index
    {
    	NSString* sKey = [StringFactory GetString_GameLeastKey:index];	
    	[prefs setInteger:m_nLeastRecord forKey:sKey];
    }


    -(void)SaveTotalWinScore:(NSUserDefaults*)prefs scoreIndex:(int)index
    {
    	NSString* sKey = [StringFactory GetString_ScoreTotalWinCountKey:index];	
    	[prefs setInteger: m_nTotalWinCount forKey:sKey];
    }

    -(void)SaveLevel:(NSUserDefaults*)prefs scoreIndex:(int)index
    {
    	NSString* sKey = [StringFactory GetString_ScoreLevelKey:index];	
    	[prefs setInteger: m_nLevel forKey:sKey];
    }


    -(void)SaveLeastTime:(NSUserDefaults*)prefs scoreIndex:(int)index
    {
    	NSString* sKey = [StringFactory GetString_GameYearLeastKey:index];	
    	[prefs setInteger:m_nYear4Least forKey:sKey];

    	sKey = [StringFactory GetString_GameMonthLeastKey:index];	
    	[prefs setInteger:m_nMonth4Least forKey:sKey];

    	sKey = [StringFactory GetString_GameDayLeastKey:index];	
    	[prefs setInteger:m_nDay4Least forKey:sKey];
    	
    }



    -(void)SaveScore:(NSUserDefaults*)prefs scoreIndex:(int)index
    {
    	[self SaveType:prefs scoreIndex: index];
    	[self SaveLayout:prefs scoreIndex: index];
    	[self SaveEdge:prefs scoreIndex: index];
    	[self SaveLeastStep:prefs scoreIndex: index];
        [self SaveTotalWinScore:prefs scoreIndex: index];
    	[self SaveLeastTime:prefs scoreIndex: index];
        [self SaveLevel :prefs scoreIndex: index];
    }

    //
    //Load from storage
    //

    -(void)LoadType:(NSUserDefaults*)prefs scoreIndex:(int)index
    {
    	NSString* sKey = [StringFactory GetString_GridTypeKey:index];	
    	m_nGridType = [prefs integerForKey:sKey];
    	if(m_nGridType <= 0)
    	{
    		m_nGridType = (int)PUZZLE_GRID_TRIANDLE;
    	}	
    }


    -(void)LoadLayout:(NSUserDefaults*)prefs scoreIndex:(int)index
    {
    	NSString* sKey = [StringFactory GetString_GridLayoutKey:index];	
    	m_nGridlayout = [prefs integerForKey:sKey];
    	if(m_nGridlayout <= 0)
    		m_nGridlayout = (int)PUZZLE_LALOUT_MATRIX;
    }

    -(void)LoadEdge:(NSUserDefaults*)prefs scoreIndex:(int)index
    {
    	NSString* sKey = [StringFactory GetString_GridEdgeKey:index];	
    	m_nEdge = [prefs integerForKey:sKey];
    	if(m_nEdge <= 0)
    		m_nEdge = MIN_BUBBLE_UNIT;
    }


    -(void)LoadLeastStep:(NSUserDefaults*)prefs scoreIndex:(int)index
    {
    	NSString* sKey = [StringFactory GetString_GameLeastKey:index];	
    	m_nLeastRecord = [prefs integerForKey:sKey];
    	if(m_nLeastRecord <= 0)
    		m_nLeastRecord = 0;
    }


    -(void)LoadTotalWinScore:(NSUserDefaults*)prefs scoreIndex:(int)index
    {
    	NSString* sKey = [StringFactory GetString_ScoreTotalWinCountKey:index];	
    	m_nTotalWinCount = [prefs integerForKey:sKey];
    	if(m_nTotalWinCount <= 0)
    		m_nTotalWinCount = 0;
    }

    -(void)LoadLevel:(NSUserDefaults*)prefs scoreIndex:(int)index
    {
    	NSString* sKey = [StringFactory GetString_ScoreLevelKey:index];	
    	m_nLevel = [prefs integerForKey:sKey];
    	if(m_nLevel <= 0)
    		m_nLevel = 0;
    }

    -(void)LoadLeastTime:(NSUserDefaults*)prefs scoreIndex:(int)index
    {
    	NSString* sKey = [StringFactory GetString_GameYearLeastKey:index];	
    	m_nYear4Least = [prefs integerForKey:sKey];
    	if(m_nYear4Least <= 0)
    	{
    		m_nYear4Least = 0;
    		m_nMonth4Least = 0;
    		m_nDay4Least = 0;
    		return;
    	}	
    	
    	sKey = [StringFactory GetString_GameMonthLeastKey:index];	
    	m_nMonth4Least = [prefs integerForKey:sKey];
    	if(m_nMonth4Least <= 0)
    	{
    		m_nYear4Least = 0;
    		m_nMonth4Least = 0;
    		m_nDay4Least = 0;
    		return;
    	}	
    	
    	sKey = [StringFactory GetString_GameDayLeastKey:index];	
    	m_nDay4Least = [prefs integerForKey:sKey];
    	if(m_nDay4Least <= 0)
    	{	
    		m_nYear4Least = 0;
    		m_nMonth4Least = 0;
    		m_nDay4Least = 0;
    		return;
    	}
    }


    -(void)LoadScore:(NSUserDefaults*)prefs scoreIndex:(int)index
    {
    	[self LoadType:prefs scoreIndex: index];
    	[self LoadLayout:prefs scoreIndex: index];
    	[self LoadEdge:prefs scoreIndex: index];
    	[self LoadLeastStep:prefs scoreIndex: index];
        [self LoadTotalWinScore:prefs scoreIndex:index];
    	[self LoadLeastTime:prefs scoreIndex: index];
        [self LoadLevel: prefs scoreIndex: index];
    }	
*/
    public Boolean IsSame(int nGridType, int nLayout, int nEdge)
    {
        Boolean bRet = (m_nGridType == nGridType && m_nGridlayout == nLayout && m_nEdge == nEdge);
        return bRet;
    }
       
}
