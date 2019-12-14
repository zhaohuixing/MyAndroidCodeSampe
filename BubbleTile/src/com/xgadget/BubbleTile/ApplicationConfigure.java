package com.xgadget.BubbleTile;

public class ApplicationConfigure {
	//////////////////////////////////////////
	static final int APPLICATION_ACTIVE_DEVICE_TYPE_IPHONE = 0;
	static final int APPLICATION_ACTIVE_DEVICE_TYPE_IPAD = 1;	
	static  int		m_DeviceType = APPLICATION_ACTIVE_DEVICE_TYPE_IPHONE;

	static final String ADMOB_PUBLISHKEY_BUBBLETILE_IPHONE = "a14df6cd186bab6";
	static final String ADMOB_PUBLISHKEY_BUBBLETILE_IPAD	= "a14df6cd4e20e0f";
	static final String MOBCLIX_PUBLISHKEY_BUBBLETILE     = "EC93837C-D2D3-462D-831C-1CE7178ED09E";

	static final String FACEBOOK_APPLICATIONKEY_FLYINGCOW		=	"149005628478705";
	static final String FACEBOOK_APPLICATIONKEY_LUCKYCOMPASS	=	"168374569849620";

	static final String SINA_OAUTHKYEY_FLYINGCOW              =      "2495174824";
	static final String SINA_OAUTHSECRET_FLYINGCOW            =      "76ea441c0e37a13e7ced0a1306d46d10";
	static final String SINA_OAUTHKYEY_LUCKYCOMPASS           =      "555110019";
	static final String SINA_OAUTHSECRET_LUCKYCOMPASS         =      "f395b2205c52cdebf694419430901afc";

	static final int SINA_AUTHERID                            =   1728843907;	

	static final String RENREN_APPLICATIONKEY_FLYINGCOW       =      "89772c6376bf4e21b196bb13752634cc";
	static final String RENREN_APPLICATIONSECRET_FLYINGCOW    =      "adcb1055d8994dc8873f1af91f288bad";

	static final String RENREN_APPLICATIONKEY_LUCKYCOMPASS    =      "6abcd316b83a41b9884b5381798da710";
	static final String RENREN_APPLICATIONSECRET_LUCKYCOMPASS =      "613dac4dc9b744f1b77791e01bdf4c61";	
	
	
static final int ADVIEW_TYPE_NONE = -1;
static final int ADVIEW_TYPE_MOBCLIX = 0;
static final int ADVIEW_TYPE_GOOGLE = 1;
static final int ADVIEW_TYPE_APPLE = 2;



static final int APPLICATION_PRODUCT_FLYINGCOW		=			0;
static final int APPLICATION_PRODUCT_LUCKYCOMPASS	=			1;
static final int APPLICATION_PRODUCT_MINDFIRE       =           2;
static  int		m_ProductID = APPLICATION_PRODUCT_FLYINGCOW;
static  Boolean	m_AdViewsEnable = false;
static  Boolean	m_GameCenterEnable = false;
static  Boolean m_bShouldShutdown = true;

static int      m_nAdViewType = ADVIEW_TYPE_NONE;

public static void SetActiveDeviceType(int type)
{
	m_DeviceType = type;
}

public static Boolean iPhoneDevice()
{
	Boolean bRet = (m_DeviceType == APPLICATION_ACTIVE_DEVICE_TYPE_IPHONE);
	return bRet;
}

public static Boolean iPADDevice()
{
	Boolean bRet = (m_DeviceType == APPLICATION_ACTIVE_DEVICE_TYPE_IPAD);
	return bRet;
}

public static void SetCurrentProduct(int productID)
{
	m_ProductID = productID;
}

public static int GetCurrentProduct()
{
	return m_ProductID; 
}	

public static String GetCurrentAdMobPublishKey()
{
	String szKey = new String("");
	
	if(m_DeviceType == APPLICATION_ACTIVE_DEVICE_TYPE_IPHONE)
	{
		szKey = ADMOB_PUBLISHKEY_BUBBLETILE_IPHONE;
	}
	else 
	{
        szKey = ADMOB_PUBLISHKEY_BUBBLETILE_IPAD;
	}

	return szKey;
}

public static String GetCurrentMobClixPublishKey()
{
	String szKey = new String("");
	
	szKey = MOBCLIX_PUBLISHKEY_BUBBLETILE;
    
	return szKey;
}

public static String GetAdMobPublishKey()
{
	return ApplicationConfigure.GetCurrentAdMobPublishKey();
}

public static String GetMobClixPublishKey()
{
	return ApplicationConfigure.GetCurrentMobClixPublishKey();
}	

public static void SetAdViewsState(Boolean bEnable)
{
	m_AdViewsEnable = bEnable;
}

public static Boolean GetAdViewsState()
{
	return m_AdViewsEnable;
}	

public static String GetFacebookKey()
{
	if(m_ProductID == APPLICATION_PRODUCT_LUCKYCOMPASS)
	{
		return FACEBOOK_APPLICATIONKEY_LUCKYCOMPASS;
	}	

    return FACEBOOK_APPLICATIONKEY_FLYINGCOW;
}

public static String GetSinaKey()
{
	if(m_ProductID == APPLICATION_PRODUCT_LUCKYCOMPASS)
	{
		return SINA_OAUTHKYEY_LUCKYCOMPASS;
	}	
    
    return SINA_OAUTHKYEY_FLYINGCOW;
}

public static String GetSinaSecret()
{
	if(m_ProductID == APPLICATION_PRODUCT_LUCKYCOMPASS)
	{
		return SINA_OAUTHSECRET_LUCKYCOMPASS;
	}	
        
    return SINA_OAUTHSECRET_FLYINGCOW;
}

public static int GetSinaAutherID()
{
    return SINA_AUTHERID;
}

public static String GetRenRenKey()
{
	if(m_ProductID == APPLICATION_PRODUCT_LUCKYCOMPASS)
	{
		return RENREN_APPLICATIONKEY_LUCKYCOMPASS;
	}	
    return RENREN_APPLICATIONKEY_FLYINGCOW;
}

public static String GetRenRenSecret()
{
	if(m_ProductID == APPLICATION_PRODUCT_LUCKYCOMPASS)
	{
		return RENREN_APPLICATIONSECRET_LUCKYCOMPASS;
	}	
    return RENREN_APPLICATIONSECRET_FLYINGCOW;
}

public static void SetGameCenterEnable(Boolean bEnable)
{
    m_GameCenterEnable = bEnable;
}

public static Boolean IsGameCenterEnable()
{
    return m_GameCenterEnable;
}

public static Boolean ShouldShutdownGame()
{
    return m_bShouldShutdown;    
}

public static void SetShutdownGame(Boolean bShutdown)
{
    m_bShouldShutdown = bShutdown;    
}

public static Boolean IsOnSimulator()
{
    Boolean bRet = false;
/*    
    String szDevice = [[UIDevice currentDevice] model];
    if([szDevice rangeOfString:@"Simulator"].location == NSNotFound)
    {    
        bRet = NO;
    }
    else
    {
        NSLog(@"Test Current Running Env: %@",szDevice);
        bRet = YES;
    }
 */   
    return bRet;
}


public static void SetMobclixAdviewType()
{
    m_nAdViewType = ADVIEW_TYPE_MOBCLIX;
}

public static Boolean IsMobclixAdviewType()
{
    Boolean bRet = (m_nAdViewType == ADVIEW_TYPE_MOBCLIX);
    return bRet;
}

public static void SetGoogleAdviewType()
{
    m_nAdViewType = ADVIEW_TYPE_GOOGLE;
}

public static Boolean IsGoogleAdviewType()
{
    Boolean bRet = (m_nAdViewType == ADVIEW_TYPE_GOOGLE);
    return bRet;
}

public static void SetAppleAdviewType()
{
    m_nAdViewType = ADVIEW_TYPE_APPLE;
}

public static Boolean IsAppleAdviewType()
{
    Boolean bRet = (m_nAdViewType == ADVIEW_TYPE_APPLE);
    return bRet;
}

public static void ClearAdViewType()
{
    m_nAdViewType = ADVIEW_TYPE_NONE;
}


static Boolean m_bCanLaunchHouseAd = false;
public static void EnableLaunchHouseAd()
{
    m_bCanLaunchHouseAd = true;
}

public static void DisableLaunchHouseAd()
{
    m_bCanLaunchHouseAd = false;
}

public static Boolean CanLaunchHouseAd()
{
    return m_bCanLaunchHouseAd;
}

static Boolean  m_bChinese = false;
public static Boolean IsChineseVersion()
{
    return m_bChinese;
}

public static void SetChineseVersion()
{
    m_bChinese = true;
}

}
