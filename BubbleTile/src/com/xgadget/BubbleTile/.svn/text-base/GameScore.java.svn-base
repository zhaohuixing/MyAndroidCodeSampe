package com.xgadget.BubbleTile;

import java.util.ArrayList;

public class GameScore {
	static final int PRODUCT_FREE = 0;
	static final int PRODUCT_PAID = 1;
	static GameScore g_Score = null;
	
	ArrayList<ScoreRecord>		m_Scores;
	
    public int                 m_bPaid;

	public int                 m_bSquarePaid;
	public int                 m_bDiamondPaid;
	public int                 m_bHexagonPaid;
    
	int                 m_nDefaultBubble;
	int                 m_nDefaultType;
	int                 m_nDefaultLayout;
    int                 m_nDefaultEdge;
    
    public GameScore()
    {

    		m_Scores = new ArrayList<ScoreRecord>();
    		//if([ApplicationConfigure GetAdViewsState] == NO)
            //{    
           //     m_bPaid = PRODUCT_PAID;
            //}    
            //else
            //{    
            //    m_bPaid = PRODUCT_FREE;
            //}    
            m_bPaid = PRODUCT_FREE;
            m_bSquarePaid = PRODUCT_FREE;
            m_bDiamondPaid = PRODUCT_FREE;
            m_bHexagonPaid = PRODUCT_FREE;
                
            m_nDefaultType = (int)enGridType.PUZZLE_GRID_TRIANDLE.ordinal();
            m_nDefaultLayout = (int)enGridLayout.PUZZLE_LALOUT_MATRIX.ordinal();
            m_nDefaultEdge = 3;//MIN_BUBBLE_UNIT;
            m_nDefaultBubble = (int)enBubbleType.PUZZLE_BUBBLE_COLOR.ordinal();

    }	


    public void CreateScoreRecord(int nType, int nLayout, int nEdge)
    {
    	ScoreRecord score = new ScoreRecord(nType, nLayout, nEdge);
    	m_Scores.add(score);
    }

    public int GetScoreCount()
    {
    	int nRet = m_Scores.size();
    	return nRet;
    }	

    int CheckScoreRecord(int nType, int nLayout, int nEdge)
    {
    	int nRet = -1;
    	
    	for (int i = 0; i < m_Scores.size(); ++i)
    	{
    		if(((ScoreRecord)m_Scores.get(i)).IsSame(nType, nLayout, nEdge) == true)
    		{
    			nRet = i;
    		}	
    	}	
    	return nRet;
    }

    public void AddScore(int nType, int nLayout, int nEdge, int nStep)
    {
    	int nRet = -1;
    	
    	for (int i = 0; i < m_Scores.size(); ++i)
    	{
    		if(((ScoreRecord)m_Scores.get(i)).IsSame(nType, nLayout, nEdge) == true)
    		{
    			((ScoreRecord)m_Scores.get(i)).AddScore(nStep);
    			nRet = i;
    		}	
    	}	
    	
    	if(nRet == -1)
    	{
    		ScoreRecord score = new ScoreRecord(nType, nLayout, nEdge);
    		score.AddScore(nStep);
    		m_Scores.add(score);
    	}
    	this.SaveScoresToPreference();
    }

    public void Reset()
    {
    	m_Scores.clear();
    }
/*
    public void LoadScore(NSUserDefaults*)prefs withIndex:(int)index
    {
    	ScoreRecord* score = [[[ScoreRecord alloc] init] autorelease];
    	[score LoadScore:prefs scoreIndex:index];
    	[m_Scores addObject:score];
    }	

    -(void)LoadGameScores:(NSUserDefaults*)prefs
    {
    	NSString* sKey = [StringFactory GetString_ScoreNumberKey];	
    	int nCount = [prefs integerForKey:sKey];
    	if(0 < nCount)
    	{	
    		for(int i = 0; i < nCount; ++i)
    		{
    			[self LoadScore:prefs withIndex:i];
    		}	
    	}	
    }

    - (void)LoadDefaultSetting:(NSUserDefaults*)prefs
    {
        NSString* sKey = [StringFactory GetString_DefaultTypeKey];
        m_nDefaultType = [prefs integerForKey:sKey];
        if(m_nDefaultType < 0)
            m_nDefaultType = (int)PUZZLE_GRID_TRIANDLE;
        
        sKey = [StringFactory GetString_DefaultLayoutKey];
        m_nDefaultLayout = [prefs integerForKey:sKey];
        if(m_nDefaultLayout < 0)
            m_nDefaultLayout = (int)PUZZLE_LALOUT_MATRIX;
        
        sKey = [StringFactory GetString_DefaultEdgeKey];
        m_nDefaultEdge = [prefs integerForKey:sKey];
        if(m_nDefaultEdge <= 0)
            m_nDefaultEdge = MIN_BUBBLE_UNIT;
        
        sKey = [StringFactory GetString_BubbleTypeKey];
        m_nDefaultBubble = [prefs integerForKey:sKey]; 
        if(m_nDefaultBubble <= 0)
            m_nDefaultBubble = (int)PUZZLE_BUBBLE_COLOR;
    }
*/
    public void LoadDefaultConfigure()
    {
//    	NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
  //      [self LoadDefaultSetting:prefs];
    }


    public void LoadScoresFromPreference()
    {/*
    	[m_Scores removeAllObjects];
    	NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
    	
    	m_bPaid = 0;
    	m_bPaid = [prefs integerForKey:[StringFactory GetString_PurchaseStateKey]];
    	
        m_bSquarePaid = PRODUCT_FREE;
        m_bSquarePaid = [prefs integerForKey:[StringFactory GetString_PurchaseSquareStateKey]];
        m_bDiamondPaid = PRODUCT_FREE;
        m_bDiamondPaid = [prefs integerForKey:[StringFactory GetString_PurchaseDiamondStateKey]];
        m_bHexagonPaid = PRODUCT_FREE;
        m_bHexagonPaid = [prefs integerForKey:[StringFactory GetString_PurchaseDiamondStateKey]];
        
        [self LoadDefaultSetting:prefs];
        
        [self LoadGameScores:prefs];
      */  
    }

    public ScoreRecord GetScoreRecord(int index)
    {
    	ScoreRecord score = null;
        
    	if(0 <= index && index < m_Scores.size())
    	{
    		score = (ScoreRecord)m_Scores.get(index);
    	}	
    	
    	return score;
    }

    public ScoreRecord GetScoreRecordByInfo(int nType, int nLayout, int nEdge)
    {
    	ScoreRecord score = null;
        
    	for (int i = 0; i < m_Scores.size(); ++i)
    	{
    		if(((ScoreRecord)m_Scores.get(i)).IsSame(nType, nLayout, nEdge) == true)
    		{
                score = (ScoreRecord)m_Scores.get(i); 
    			return score;
    		}	
    	}	
    	
    	return score;
    }


    public void LoadPaymentState()
    {
//    	NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
    	
    	m_bPaid = 0;
 //   	m_bPaid = [prefs integerForKey:[StringFactory GetString_PurchaseStateKey]];
    }
/*
    -(void)SaveGameScores:(NSUserDefaults*)prefs
    {
    	int nCount = [m_Scores count];
    	
    	if(0 < nCount)
    	{	
    		NSString* sKey = [StringFactory GetString_ScoreNumberKey];	
    		[prefs setInteger:nCount forKey:sKey];
    		for(int i = 0; i < nCount; ++i)
    		{
    			[(ScoreRecord*)[m_Scores objectAtIndex:i] SaveScore:prefs scoreIndex:i];
    		}	
    	}
    }	


    -(void)SaveDefaultSetting:(NSUserDefaults*)prefs
    {
        NSString* sKey = [StringFactory GetString_DefaultTypeKey];
        [prefs setInteger:m_nDefaultType forKey:sKey];
        
        sKey = [StringFactory GetString_DefaultLayoutKey];
        [prefs setInteger:m_nDefaultLayout forKey:sKey];
        
        sKey = [StringFactory GetString_DefaultEdgeKey];
        [prefs setInteger:m_nDefaultEdge forKey:sKey];

        sKey = [StringFactory GetString_BubbleTypeKey];
        [prefs setInteger:m_nDefaultBubble forKey:sKey];
    }
*/
    public void SavePaymentState()
    {
//    	[NSUserDefaults resetStandardUserDefaults];
//    	NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
//    	[prefs setInteger:m_bPaid forKey:[StringFactory GetString_PurchaseStateKey]];
    }

    public void SaveSquarePaymentState()
    {
//    	[NSUserDefaults resetStandardUserDefaults];
 //   	NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
   // 	[prefs setInteger:m_bSquarePaid forKey:[StringFactory GetString_PurchaseSquareStateKey]];
    }

    public void SaveDiamondPaymentState()
    {
  //  	[NSUserDefaults resetStandardUserDefaults];
   // 	NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
   // 	[prefs setInteger:m_bDiamondPaid forKey:[StringFactory GetString_PurchaseDiamondStateKey]];
    }

    public void SaveHexagonPaymentState()
    {
//    	[NSUserDefaults resetStandardUserDefaults];
 //   	NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
  //  	[prefs setInteger:m_bHexagonPaid forKey:[StringFactory GetString_PurchaseHexagonStateKey]];
        
    }

    public void LoadSquarePaymentState()
    {
//    	NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
   	
//        m_bSquarePaid = PRODUCT_FREE;
 //   	m_bSquarePaid = [prefs integerForKey:[StringFactory GetString_PurchaseSquareStateKey]];
    }

    public void LoadDiamondPaymentState()
    {
//    	NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
    	
 //       m_bDiamondPaid = PRODUCT_FREE;
   // 	m_bDiamondPaid = [prefs integerForKey:[StringFactory GetString_PurchaseDiamondStateKey]];
    }

    public void LoadHexagonPaymentState()
    {
//    	NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
    	
 //       m_bHexagonPaid = PRODUCT_FREE;
   // 	m_bHexagonPaid = [prefs integerForKey:[StringFactory GetString_PurchaseHexagonStateKey]];
    }

    public void SaveDefaultConfigure()
    {
//    	NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
  //      [self SaveDefaultSetting:prefs];
    }

    public void SaveScoresToPreference()
    {
//    	[NSUserDefaults resetStandardUserDefaults];
//    	NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
    	
//        //
//    	//Don't save any payment information here!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        //
//        //[prefs setInteger:m_bPaid forKey:[StringFactory GetString_PurchaseStateKey]];
        
//        [self SaveDefaultSetting:prefs];
    	
//        [self SaveGameScores:prefs];
    }	


   public static  Boolean CheckPaymentState()
    {
        GameScore scores = new GameScore();
        scores.LoadScoresFromPreference();
        if(scores.m_bPaid == PRODUCT_PAID)
        {    
            return true;
        }    
        else
        {    
            return false;
        }
    }

    public static Boolean CheckSquarePaymentState()
    {
        GameScore scores = new GameScore();
        scores.LoadSquarePaymentState();
        if(scores.m_bSquarePaid == PRODUCT_PAID)
        {    
            return true;
        }    
        else
        {    
            return false;
        }
    }

    public Boolean CheckDiamondPaymentState()
    {
        GameScore scores = new GameScore();
        scores.LoadDiamondPaymentState();
        if(scores.m_bDiamondPaid == PRODUCT_PAID)
        {    
            return true;
        }    
        else
        {    
            return false;
        }
    }

    public static Boolean CheckHexagonPaymentState()
    {
        GameScore scores = new GameScore();
        scores.LoadHexagonPaymentState();
        if(scores.m_bHexagonPaid == PRODUCT_PAID)
        {    
            return true;
        }    
        else
        {    
            return false;
        }
    }

    public static Boolean HasPurchasedProduct()
    {
        GameScore scores = new  GameScore();
        scores.LoadPaymentState();
        scores.LoadSquarePaymentState();
        scores.LoadDiamondPaymentState();
        scores.LoadHexagonPaymentState();
        if(scores.m_bPaid!=0 || scores.m_bSquarePaid!=0 || scores.m_bDiamondPaid!=0 || scores.m_bHexagonPaid!=0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static void SavePaidState()
    {
        GameScore scores = new GameScore();
        scores.m_bPaid = PRODUCT_PAID;
        scores.SavePaymentState();
    }

    public static void SaveSquarePaidState()
    {
        GameScore scores = new GameScore();
        scores.m_bSquarePaid = PRODUCT_PAID;
        scores.SaveSquarePaymentState();
    }

    public static void SaveDiamondPaidState()
    {
        GameScore scores = new GameScore();
        scores.m_bDiamondPaid = PRODUCT_PAID;
        scores.SaveDiamondPaymentState();
    }

    public void SaveHexagonPaidState()
    {
        GameScore scores = new GameScore();
        scores.m_bHexagonPaid = PRODUCT_PAID;
        scores.SaveHexagonPaymentState();
    }

    public static void InitializeGameScore()
    {
    	if (g_Score == null)
    	{
    		g_Score = new GameScore();
    		g_Score.LoadScoresFromPreference();
    	}
    }

    public static void CleanGameScore()
    {
        g_Score.SaveScoresToPreference();
        g_Score = null;
    }

    public static int _GetScoreCount()
    {
        int nRet = 0;
        
        nRet = g_Score.GetScoreCount();
        
        return nRet;
    }

    public static Boolean HasScoreRecord(int nType, int nLayout, int nEdge)
    {
        Boolean bRet = false;
        
        bRet = g_Score.CheckScoreRecord(nType, nLayout, nEdge) !=0;
        
        return bRet;
    }

    public static ScoreRecord GetScore(int nType, int nLayout, int nEdge)
    {
        return g_Score.GetScoreRecordByInfo(nType, nLayout, nEdge);
    }

    public static ScoreRecord GetScoreAt(int nIndex)
    {
        return g_Score.GetScoreRecord(nIndex);
    }

    public static void _AddScore(int nType, int nLayout, int nEdge, int nStep)
    {
        g_Score.AddScore(nType, nLayout, nEdge, nStep);
    }

    public static int GetDefaultType()
    {
        return g_Score.m_nDefaultType;
    }

    public static int GetDefaultLayout()
    {
        return g_Score.m_nDefaultLayout;
    }

    public static int GetDefaultEdge()
    {
        return g_Score.m_nDefaultEdge;
    }

    public static int GetDefaultBubble()
    {
        return g_Score.m_nDefaultBubble;
    }

    public static void SetDefaultConfigure(int nType, int nLayout, int nEdge, int nBubbleType)
    {
        g_Score.m_nDefaultType = nType;
        g_Score.m_nDefaultLayout = nLayout;
        g_Score.m_nDefaultEdge = nEdge;
        g_Score.m_nDefaultBubble = nBubbleType;
        g_Score.SaveDefaultConfigure();
    }
    
    
}
