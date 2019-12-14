package com.xgadget.BubbleTile;

public class GameConfiguration {
	
	static Boolean ApplicationConfigure_phoneDevice = true;
	
	static int m_nBubbleUnit = GameConstants.MIN_BUBBLE_UNIT;
	static enGridType       m_GridType = enGridType.PUZZLE_GRID_TRIANDLE;
	static enGridLayout     m_GridLayout = enGridLayout.PUZZLE_LALOUT_MATRIX;
	static Boolean             m_bDirty = false;
	static int              m_nPlayStepCount = 0;
	static int              m_nYear = 0;
	static int              m_nMonth = 0;
	static int              m_nDay = 0;

	static enBubbleType      m_BubbleType = enBubbleType.PUZZLE_BUBBLE_COLOR;
	static Boolean             m_bDifficulty = false;
	
	public static void SetBubbleType(enBubbleType bType)
	{
	    if(m_BubbleType != bType)
	        m_bDirty = true;
	    
	    m_BubbleType = bType;
	}

	public static enBubbleType GetBubbleType()
	{
	    return m_BubbleType;
	}

	public static void SetGameDifficulty(Boolean bDifficulty)
	{
	    if(m_bDifficulty != bDifficulty)
	        m_bDirty = true;
	    
	    m_bDifficulty = bDifficulty;
	}

	public static Boolean IsGameDifficulty()
	{
	    return m_bDifficulty;
	}

	public static int GetDefaultBubbleUnit()
	{
	    return GameConstants.MIN_BUBBLE_UNIT;
	}

	public static int GetBubbleUnit()
	{
	    return m_nBubbleUnit;
	}

	public static void SetBubbleUnit(int nEdge)
	{
	    m_nBubbleUnit = nEdge;
	}

	public static void SetGridType(enGridType enType)
	{
	    if(m_GridType != enType)
	        m_bDirty = true;
	    
	    m_GridType = enType;
	}

	public static enGridType GetGridType()
	{
	    return m_GridType;
	}

	public static void SetGridLayout(enGridLayout enLayout)
	{
	    if(m_GridLayout != enLayout)
	        m_bDirty = true;
	    
	    m_GridLayout = enLayout;
	}

	public static enGridLayout GetGridLayout()
	{
	    return m_GridLayout;
	}

	public static int GetMinBubbleUnit(enGridType enType)
	{
	    int nRet = GameConstants.MIN_BUBBLE_UNIT;
	    
	    switch(enType)
	    {
	        case PUZZLE_GRID_TRIANDLE: 
	            nRet = GameConstants.MIN_BUBBLE_UNIT;
	            break;
	        case PUZZLE_GRID_SQUARE:
	            nRet = GameConstants.MIN_BUBBLE_UNIT;
	            break;
	        case PUZZLE_GRID_DIAMOND:
	            nRet = GameConstants.MIN_BUBBLE_UNIT;
	            break;
	        case PUZZLE_GRID_HEXAGON:
	            nRet = GameConstants.MIN_BUBBLE_UNIT-1;
	            break;
	    }
	    
	    return nRet;
	}

	public static int GetMaxBubbleUnit(enGridType enType)
	{
	    int nRet = 8; 
	    if(ApplicationConfigure.iPhoneDevice())
	    {
	        switch(enType)
	        {
	            case PUZZLE_GRID_TRIANDLE: 
	                nRet = 10;
	                break;
	            case PUZZLE_GRID_SQUARE:
	                nRet = 10;
	                break;
	            case PUZZLE_GRID_DIAMOND:
	                nRet = 7;
	                break;
	            case PUZZLE_GRID_HEXAGON:
	                nRet = 7;
	                break;
	        }
	    }
	    else
	    {
	        switch(enType)
	        {
	            case PUZZLE_GRID_TRIANDLE: 
	                nRet = 16;
	                break;
	            case PUZZLE_GRID_SQUARE:
	                nRet = 16;
	                break;
	            case PUZZLE_GRID_DIAMOND:
	                nRet = 12;
	                break;
	            case PUZZLE_GRID_HEXAGON:
	                nRet = 12;
	                break;
	        }
	    }
	    
	    return nRet;
	}

	public static int GetEnabledBubbleUnitCount(enGridType enType)
	{
	    //??????????????????????????????????
	    //TODO: Add real time checking game achievement from saved record
	    //??????????????????????????????????
	    int nMin = GameConfiguration.GetMinBubbleUnit(enType);
	    int nMax = GameConfiguration.GetMaxBubbleUnit(enType);
	    int nCount = nMax-nMin+1;
	    return nCount;
	}

	public static  void SetGridBubbleUnit(enGridType enType, int nEdge)
	{
	    //??????????????????????????????????
	    //TODO: Add real time checking game achievement from saved record
	    //??????????????????????????????????
	    if(GameConfiguration.GetGridBubbleUnit(enType) != nEdge)
	        m_bDirty = true;
	    
	    GameConfiguration.SetBubbleUnit(nEdge);    
	}

	public static int GetGridBubbleUnit(enGridType enType)
	{
	    //??????????????????????????????????
	    //TODO: Add real time checking game achievement from saved record
	    //??????????????????????????????????
	    return GameConfiguration.GetBubbleUnit();    
	}

	public static void CleanDirty()
	{
	    m_bDirty = false;
	}

	public static Boolean IsDirty()
	{
	    return m_bDirty;
	}

	public static void IncrementPlayStep()
	{
	    ++m_nPlayStepCount;
	}

	public static void DecrementPlayStep()
	{
	    --m_nPlayStepCount;
	    if(m_nPlayStepCount < 0)
	        m_nPlayStepCount = 0; 
	}

	public static int GetPlaySteps()
	{
	    return m_nPlayStepCount;
	}

	public static void CleanPlaySteps()
	{
	    m_nPlayStepCount = 0;
	}

	public static void PrepareCurrentDate()
	{
		// will port this later
/*		
		NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
		
		[dateFormatter setDateFormat:@"MM"];
		m_nMonth = [[dateFormatter stringFromDate:[NSDate date]] intValue];
		
		[dateFormatter setDateFormat:@"dd"];
		m_nDay = [[dateFormatter stringFromDate:[NSDate date]] intValue];
		
		[dateFormatter setDateFormat:@"yyyy"];
	
		m_nYear = [[dateFormatter stringFromDate:[NSDate date]] intValue];
		
		[dateFormatter release];
*/		
	}

	public static int GetYearOfCurrentDate()
	{
	    return m_nYear;
	}

	public static int GetMonthOfCurrentDate()
	{
	    return m_nMonth;
	}

	public static int GetDayOfCurrentDate()
	{
	    return m_nDay;
	}
	
	public static int GetCurrentBubbleCount()
	{
		/////????????????????
		/////????????????????
		/////Work out later
		/////????????????????
		/////????????????????
		
		return 9;
	}
	
}
