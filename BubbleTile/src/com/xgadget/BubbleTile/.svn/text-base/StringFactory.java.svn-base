package com.xgadget.BubbleTile;

import android.content.Context;
import android.content.res.Resources;

public class StringFactory {
	
	static final int LANGID_EN = 0;
	static final int LANGID_FR	= 1;
	static final int LANGID_GR	= 2;
	static final int LANGID_JP	= 3;
	static final int LANGID_ES	= 4;
	static final int LANGID_IT	= 5;
	static final int LANGID_PT	= 6;
	static final int LANGID_KO	= 7;
	static final int LANGID_ZH	= 8;
	static final int LANGID_RU	= 9;	

	static Context 					m_GameContext = null;
	static Resources					m_Resources = null;
	
	static public void SetContext(Context context)
	{
		if (context != null)
		{
			m_GameContext = context;
			m_Resources = m_GameContext.getResources();
		}
	}
static public Boolean IsNeedToCareLang()
{
    Boolean bRet = false;
    
	int nID = StringFactory.GetString_OSLangID();
	if(nID == LANGID_ZH || nID == LANGID_RU || nID == LANGID_ES || nID == LANGID_PT || nID == LANGID_KO)
		bRet = true;
    
    return bRet;
}

static public Boolean IsOSLangZH()
{
	Boolean bRet = false;
	
	int nID = StringFactory.GetString_OSLangID();
	if(nID == LANGID_ZH)
		bRet = true;
	
	return bRet;
}

static public Boolean IsOSLangES()
{
	Boolean bRet = false;
	
	int nID = StringFactory.GetString_OSLangID();
	if(nID == LANGID_ES)
		bRet = true;
	
	return bRet;
}

static public Boolean IsOSLangIT()
{
	Boolean bRet = false;
	
	int nID = StringFactory.GetString_OSLangID();
	if(nID == LANGID_IT)
		bRet = true;
	
	return bRet;
}

static public Boolean IsOSLangPT()
{
	Boolean bRet = false;
	
	int nID = StringFactory.GetString_OSLangID();
	if(nID == LANGID_PT)
		bRet = true;
	
	return bRet;
}

static public Boolean IsOSLangEN()
{
	Boolean bRet = false;
	
	int nID = StringFactory.GetString_OSLangID();
	if(nID == LANGID_EN)
		bRet = true;
	
	return bRet;
}

static public Boolean IsOSLangFR()
{
	Boolean bRet = false;
	
	int nID = StringFactory.GetString_OSLangID();
	if(nID == LANGID_FR)
		bRet = true;
	
	return bRet;
}

static public Boolean IsOSLangGR()
{
	Boolean bRet = false;
	
	int nID = StringFactory.GetString_OSLangID();
	if(nID == LANGID_GR)
		bRet = true;
	
	return bRet;
}

static public Boolean IsOSLangJP()
{
	Boolean bRet = false;
	
	int nID = StringFactory.GetString_OSLangID();
	if(nID == LANGID_JP)
		bRet = true;
	
	return bRet;
}

static public Boolean IsOSLangRU()
{
	Boolean bRet = false;
	
	int nID = StringFactory.GetString_OSLangID();
	if(nID == LANGID_RU)
		bRet = true;
	
	return bRet;
}

static public Boolean IsOSLangKO()
{
	Boolean bRet = false;
	
	int nID = StringFactory.GetString_OSLangID();
	if(nID == LANGID_KO)
		bRet = true;
	
	return bRet;
}


static public int GetString_OSLangID()
{
	int nRet = LANGID_EN;
	if (m_GameContext == null)
		return nRet;
//	String str ="OS_LANGSETTING";
	
	Resources res = m_GameContext.getResources();
	//Localization string query here
	String str = res.getString(R.string.OS_LANGSETTING);
	
	if(str.equals("0") == true)
		nRet = LANGID_EN;
	else if(str.equals("1") == true)
		nRet = LANGID_FR;
	else if(str.equals("2") == true)
		nRet = LANGID_GR;
	else if(str.equals("3") == true)
		nRet = LANGID_JP;
	else if(str.equals("4") == true)
		nRet = LANGID_ES;
	else if(str.equals("5") == true)
		nRet = LANGID_IT;
	else if(str.equals("6") == true)
		nRet = LANGID_PT;
	else if(str.equals("7") == true)
		nRet = LANGID_KO;
	else if(str.equals("8") == true)
		nRet = LANGID_ZH;
	else if(str.equals("9") == true)
		nRet = LANGID_RU;
	
	return nRet;
}	


static public String GetString_LeastStepLabel()
{
	String str = "Least Step";
	
	//Localization string query here
	if (m_Resources != null)
		str = m_Resources.getString(R.string.Least_Step);
	
	return str;
}

static public String GetString_WinCountLabel()
{
	String str = "Total Archivement";
	
	//Localization string query here
	if (m_Resources != null)
		str = m_Resources.getString(R.string.Total_Archivement);
	
	return str;
}


//The game key prefix for preference storage
static public String GetString_PerfKeyPrefix()
{
	String str = "BUBBLETILE_KEY_";
	
	return str;
}	

//The game score record number key for preference storage
static public String GetString_ScoreNumberKey()
{
	String str = "BUBBLETILE_KEY_SCORENUMBER";
	
	return str;
}	

static public String GetString_PurchaseStateKey()
{
	String str = "BUBBLETILE_KEY_PURCHASESTATE";
	
	return str;
}	

static public String GetString_PurchaseSquareStateKey()
{
	String str = "BUBBLETILE_KEY_PURCHASESQUARESTATE";
	
	return str;
}

static public String GetString_PurchaseDiamondStateKey()
{
	String str = "BUBBLETILE_KEY_PURCHASEDIAMONDSTATE";
	
	return str;
    
}

static public String GetString_PurchaseHexagonStateKey()
{
	String str = "BUBBLETILE_KEY_PURCHASEHEXAGONSTATE";
	
	return str;
}


static public String GetString_DefaultTypeKey()
{
	String str = "BUBBLETILE_KEY_DEFAULT_TYPE";
	
	return str;
}

static public String GetString_DefaultLayoutKey()
{
	String str = "BUBBLETILE_KEY_DEFAULT_LAYOUT";
	
	return str;
}

static public String GetString_DefaultEdgeKey()
{
	String str = "BUBBLETILE_KEY_DEFAULT_EDGE";
	
	return str;
}


static public String GetString_ScoreIndexKeyPrefix(int scoreIndex)
{
	String str = String.format("BUBBLETILE_KEY_SCOREINDEX_%i_", scoreIndex); 
	
	return str;
}	

static public String GetString_GridTypeKey(int scoreIndex)
{
	String str = String.format("%s%s", StringFactory.GetString_ScoreIndexKeyPrefix(scoreIndex), "TYPE"); 
	
	return str;
}

static public String GetString_GridLayoutKey(int scoreIndex)
{
	String str = String.format("%s%s", StringFactory.GetString_ScoreIndexKeyPrefix(scoreIndex), "LAYOUT"); 
	
	return str;
}

static public String GetString_GridEdgeKey(int scoreIndex)
{
	String str = String.format("%s%s", StringFactory.GetString_ScoreIndexKeyPrefix(scoreIndex), "EDGE"); 
	
	return str;
}

static public String GetString_GameLeastKey(int scoreIndex)
{
	String str = String.format("%s%s", StringFactory.GetString_ScoreIndexKeyPrefix(scoreIndex), "LEASTSTEP"); 
	
	return str;
}

static public String GetString_GameYearLeastKey(int scoreIndex)
{
	String str = String.format("%s%s", StringFactory.GetString_ScoreIndexKeyPrefix(scoreIndex), "LEASTSTEP_YEAR"); 
	
	return str;
}

static public String GetString_GameMonthLeastKey(int scoreIndex)
{
	String str = String.format("%s%s", StringFactory.GetString_ScoreIndexKeyPrefix(scoreIndex), "LEASTSTEP_MONTH"); 
	
	return str;
}

static public String GetString_GameDayLeastKey(int scoreIndex)
{
	String str = String.format("%s%s", StringFactory.GetString_ScoreIndexKeyPrefix(scoreIndex), "LEASTSTEP_DAY"); 
	
	return str;
}

static public String GetString_ScoreTotalWinCountKey(int scoreIndex)
{
	String str = String.format("%s%s", StringFactory.GetString_ScoreIndexKeyPrefix(scoreIndex), "WINCOUNT"); 
	
	return str;
}

static public String GetString_ScoreLevelKey(int scoreIndex)
{
	String str = String.format("%s%s", StringFactory.GetString_ScoreIndexKeyPrefix(scoreIndex), "LEVEL"); 
	
	return str;
}

static public String GetString_BubbleTypeKey()
{
	String str = "BUBBLETILE_KEY_BUBBLE_TYPE";
	
	return str;
}

static public String GetString_AskString()
{
	String str = "AskPurchase";
	
	//Localization string query here
	if (m_Resources != null)
		str = m_Resources.getString(R.string.AskPurchase);
	
	return str;
}

static public String GetString_Purchase()
{
	String str = "Purchase";
	
	//Localization string query here
	if (m_Resources != null)
		str = m_Resources.getString(R.string.Purchase);
	
	return str;
}

static public String GetString_NoThanks()
{
	String str = "No thanks!";
	
	//Localization string query here
	if (m_Resources != null)
		str = m_Resources.getString(R.string.No_Thanks);
	
	return str;
}	

static public String GetString_CannotPayment()
{
	String str = "CannotPayment";
	
	//Localization string query here
	if (m_Resources != null)
		str = m_Resources.getString(R.string.CannotPayment);
	
	return str;
}	

static public String GetString_BuyConfirm()
{
	String str = "BuyConfirm";
	
	//Localization string query here
	if (m_Resources != null)
		str = m_Resources.getString(R.string.BuyConfirm);
	
	return str;
}

static public String GetString_BuyFailure()
{
	String str = "TransactionFailure";
	
	//Localization string query here
	if (m_Resources != null)
		str = m_Resources.getString(R.string.TransactionFailure);
	
	return str;
}	

static public String GetString_BuyIt()
{
	String str = "Buy it!";
	
	//Localization string query here
	if (m_Resources != null)
		str = m_Resources.getString(R.string.Buy_It);
	
	return str;
}	


static public String GetString_PaidFeatureAsk()
{
	String str = "This feature is available in paid version. Do you want to purchase paid version?";
	
	//Localization string query here
	if (m_Resources != null)
		str = m_Resources.getString(R.string.PaidFeatureAsk);	
	
	return str;
}

static public String GetString_GameTitle(Boolean bDefault)
{
	String str = "Bubble Tile";
	
	//Localization string query here
	if(bDefault == false){
		if (m_Resources != null)
			str = m_Resources.getString(R.string.Bubble_Tile);
	}
	
	if(ApplicationConfigure.iPADDevice())
		str = str + " iPad";
	else	
		str = str + " iPhone";
    
	return str;
}	

static public String GetString_GameURL()
{
	String str = "http://itunes.apple.com/us/app/bubble-tile/id445302495";
	return str;
}	

static public String GetString_FBUserMsgPrompt(Boolean bDefault)
{
	String str = "Share On Facebook";
	
	//Localization string query here
	if(bDefault == false)
	{
		if (m_Resources != null)
			str = m_Resources.getString(R.string.Share_On_Facebook);		
	}
	return str;
}	

static public String GetString_PostTitle(Boolean bDefault)
{
	String str = "Playing game <Bubble Tile>";
	
	//Localization string query here
	if(bDefault == false)
	{
		if (m_Resources != null)
			str = m_Resources.getString(R.string.Playing_game);		
	}
	
	if(ApplicationConfigure.iPADDevice())
		str = str + " iPad";
	else	
		str = str + " iPhone";
    
    
	return str;
}	

static public String GetString_NetworkWarn()
{
	String str = "NetworkWarn";
	
	//Localization string query here
	if (m_Resources != null)
		str = m_Resources.getString(R.string.NetworkWarn);	
	
	if(str == "")
    {
        str = "This free version game requires internet connection。";
    }
        
	return str;
}

static public String GetString_SocialNetwork()
{
    String str = "Social network";
	if (m_Resources != null)
		str = m_Resources.getString(R.string.Social_network);	    

    return str;
}

static public String GetString_Triangle()
{
    String str = "Triangle";
	if (m_Resources != null)
		str = m_Resources.getString(R.string.Triangle);	    

    return str;
}

static public String GetString_Square()
{
    String str = "Square";
	if (m_Resources != null)
		str = m_Resources.getString(R.string.Square);    

    return str;
}

static public String GetString_Diamond()
{
    String  str = "Diamond";
	if (m_Resources != null)
		str = m_Resources.getString(R.string.Diamond);    
	
    return str;
}

static public String GetString_Hexagon()
{
    String str = "Hexagon";
	if (m_Resources != null)
		str = m_Resources.getString(R.string.Hexagon);     

    return str;
}

static public String GetString_Parallel()
{
    String str = "Parallel";
	if (m_Resources != null)
		str = m_Resources.getString(R.string.Parallel);
	
    return str;
}

static public String GetString_Zigzag()
{
    String str = "Zigzag";
	if (m_Resources != null)
		str = m_Resources.getString(R.string.Zigzag); 
	
    return str;
}

static public String GetString_Spiral()
{
    String str = "Spiral";
	if (m_Resources != null)
		str = m_Resources.getString(R.string.Spiral); 
	
    return str;
}

static public String GetString_GridTypeString(int nType)
{
    String sType = "Triangle";
    switch (enGridType.values()[nType])
    {
        case PUZZLE_GRID_TRIANDLE:
            sType = StringFactory.GetString_Triangle();
            break;
        case PUZZLE_GRID_SQUARE:
            sType = StringFactory.GetString_Square();
            break;
        case PUZZLE_GRID_DIAMOND:
            sType = StringFactory.GetString_Diamond();
            break;
        case PUZZLE_GRID_HEXAGON:
            sType = StringFactory.GetString_Hexagon();
            break;
    }
    return sType;
}

static public String GetString_PuzzleString(int nType, int nLayout, int nEdge)
{
    String sType = "Triangle";
    switch (enGridType.values()[nType])
    {
        case PUZZLE_GRID_TRIANDLE:
            sType = StringFactory.GetString_Triangle();
            break;
        case PUZZLE_GRID_SQUARE:
            sType = StringFactory.GetString_Square();
            break;
        case PUZZLE_GRID_DIAMOND:
            sType = StringFactory.GetString_Diamond();
            break;
        case PUZZLE_GRID_HEXAGON:
            sType = StringFactory.GetString_Hexagon();
            break;
    }
    String sLayout = "Parallel";
    switch(enGridLayout.values()[nLayout])
    {
        case PUZZLE_LALOUT_MATRIX:
            sLayout = StringFactory.GetString_Parallel();
            break;
        case PUZZLE_LALOUT_SNAKE:
            sLayout = StringFactory.GetString_Zigzag();
            break;
        case PUZZLE_LALOUT_SPIRAL:
            sLayout = StringFactory.GetString_Spiral();
            break;
    }
    String szRet = String.format("%s-%i-%s", sType, nEdge, sLayout);
    return szRet;
}

static public String GetString_PurchaseFullFeature()
{
    String str = "Full Game Feature";
	if (m_Resources != null)
		str = m_Resources.getString(R.string.Full_Game_Feature); 
	
    return str;
}

static public String GetString_SquarePuzzle()
{
    String str = "Square Puzzle";
	if (m_Resources != null)
		str = m_Resources.getString(R.string.Square_Puzzle); 
	
    return str;
}

static public String GetString_DiamondPuzzle()
{
    String str = "Diamond Puzzle";
	if (m_Resources != null)
		str = m_Resources.getString(R.string.Diamond_Puzzle);
	
    return str;
}

static public String GetString_HexagonPuzzle()
{
    String str = "Hexagon Puzzle";
	if (m_Resources != null)
		str = m_Resources.getString(R.string.Hexagon_Puzzle);    

    return str;
}

static public String GetString_FullPrice()
{
    String str = "Full Price";
	if (m_Resources != null)
		str = m_Resources.getString(R.string.Full_Price);
	
    return str;
    
}

static public String GetString_Price()
{
    String str = "Price";
	if (m_Resources != null)
		str = m_Resources.getString(R.string.Price);    

    return str;
}

static public String GetString_Easy()
{
    String str = "Easy";
	if (m_Resources != null)
		str = m_Resources.getString(R.string.Easy);
    return str;
}

static public String GetString_Difficult()
{
    String str = "Difficult";
	if (m_Resources != null)
		str = m_Resources.getString(R.string.Difficult);    

    return str;
}

static public String GetString_FriendLabel()
{
	String str = "Friends";
	if (m_Resources != null)
		str = m_Resources.getString(R.string.Friends);

    return str;
}

static public String GetString_PlayerListLabel()
{
	String str = "Other players";
	if (m_Resources != null)
		str = m_Resources.getString(R.string.Other_players);
	
    return str;
}

static public String GetString_OverallLabel()
{
	String str = "Overall";
	if (m_Resources != null)
		str = m_Resources.getString(R.string.Overall);

    return str;
}
	
}
