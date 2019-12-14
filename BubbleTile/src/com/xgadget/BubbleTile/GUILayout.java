package com.xgadget.BubbleTile;

public class GUILayout {

	static float		m_MainUIWidth = 320;
	static float		m_MainUIHeight = 460;
	static float		m_GlossyMenuSize_IPhone = 40;
	static float		m_GlossyMenuSize_IPad = 60;
	static float		m_GlossyMenuRadium_IPhone = 90;
	static float		m_GlossyMenuRadium_IPad = 140;

	static float		m_AdBannerHeight_IPhone = 50;
	static float		m_AdBannerHeight_IPad = 90;

	static float		m_AdBigBannerHeight_IPhone = 250;
	static float		m_AdBigBannerWidth_IPhone = 300;
	static float		m_AdBigBannerHeight_IPad = 250;
	static float		m_AdBigBannerWidth_IPad = 600;

	static final int TITLEBAR_HEIGHT_DEFAULT = 30;
	static final int LISTCELL_HEIGHT_DEFAULT = 40;
	static final int SWITCHICONCELL_HEIGHT_DEFAULT = 70;
	static final int LISTCELL_MARGIN_DEFAULT = 4;
	static final int LISTCELL_STROKE_DEFAULT = 1;

	static final int EXTEND_ADVIEW_WIDTH_IPHONE       =      300;
	static final int EXTEND_ADVIEW_HEIGHT_IPHONE      =     240;

	static final int EXTEND_ADVIEW_WIDTH_IPAD         =      720;
	static final int EXTEND_ADVIEW_HEIGHT_IPAD        =      538;

	static final int EXTEND_ADVIEW_CORNER_WIDTH_IPHONE =     75;
	static final int EXTEND_ADVIEW_CORNER_WIDTH_IPAD   =     120;
	static final int EXTEND_ADVIEW_CORNER_HEIGHT_IPHONE =    50;       
	static final int EXTEND_ADVIEW_CORNER_HEIGHT_IPAD   =    90;

	static final int HOUSE_ADVIEW_SIZE_IPHONE           =    48;
	static final int HOUSE_ADVIEW_SIZE_IPAD             =    72;

/*
	public static void SetMainUIDimension(float width, float height)
	{
		m_MainUIWidth = width;
		m_MainUIHeight = height;
	}

	public static float GetMainUIWidth()
	{
		
	    return m_MainUIWidth;
	}

	public static float GetMainUIHeight()
	{
		return m_MainUIHeight;
	}

	public static Boolean IsProtrait()
	{
		Boolean bRet = false;
		if(m_MainUIWidth < m_MainUIHeight)
			bRet = true;
		
		return bRet;
	}

	public static Boolean IsLandscape()
	{
		Boolean bRet = false;
		if(m_MainUIHeight < m_MainUIWidth)
			bRet = true;
		
		return bRet;
	}	


	public static float GetGlossyMenuSize()
	{
		if (ApplicationConfigure.iPhoneDevice())
			return m_GlossyMenuSize_IPhone;
		else 
			return m_GlossyMenuSize_IPad;
	}

	public static float GetDefaultGlossyMenuLayoutRadium()
	{
		if(ApplicationConfigure.iPhoneDevice())
			return m_GlossyMenuRadium_IPhone;
		else 
			return m_GlossyMenuRadium_IPad;
	}

	public static float GetContentViewWidth()
	{
		return GUILayout.GetMainUIWidth();
	}	

	public static float GetContentViewHeight()
	{
		float h = GUILayout.GetMainUIHeight() - GUILayout.GetAdBannerHeight();
		return h;
	}

	public static float GetContentViewMinDimension()
	{
	    float w = GUILayout.GetContentViewWidth();
	    float h = GUILayout.GetContentViewHeight();
//	    float r = MIN(w, h);
	    return (w>h)?h:w;
	}

	public static float GetDefaultTitleBarHeight()
	{
	    return TITLEBAR_HEIGHT_DEFAULT;
	}

	public static float GetTitleBarHeight()
	{
	    if(ApplicationConfigure.iPhoneDevice() == true)
	    {
	        return GUILayout.GetDefaultTitleBarHeight();
	    }
	    else
	    {
	        return 2.0f*GUILayout.GetDefaultTitleBarHeight();
	    }
	}

	public static  float GetAdBannerHeight()
	{
		float h = 0;
		if(ApplicationConfigure.GetAdViewsState() == true)
		{
			if(ApplicationConfigure.iPhoneDevice() == true)
			{
				h = m_AdBannerHeight_IPhone;
			}
			else 
			{
				h = m_AdBannerHeight_IPad;
			}
		}
		
		return h;
	}	

	public static float GetAdBigBannerHeight()
	{
		float h = 0;
		if(ApplicationConfigure.GetAdViewsState() == true)
		{
			if(ApplicationConfigure.iPhoneDevice() == true)
			{
				h = m_AdBigBannerHeight_IPhone;
			}
			else 
			{
				h = m_AdBigBannerHeight_IPad;
			}
		}
		
		return h;
	}

	public static float GetAdBigBannerWidth()
	{
		float w = 0;
		if(ApplicationConfigure.GetAdViewsState() == true)
		{
			if(ApplicationConfigure.iPhoneDevice() == true)
			{
				w = m_AdBigBannerWidth_IPhone;
			}
			else 
			{
				w = m_AdBigBannerWidth_IPad;
			}
		}
		
		return w;
	}


	public static float GetDefaultListCellHeight()
	{
		return LISTCELL_HEIGHT_DEFAULT; 
	}	

	public static float GetDefaultListCellMargin()
	{
		return LISTCELL_MARGIN_DEFAULT;
	}	

	public static float GetDefaultListCellStroke()
	{
		return LISTCELL_STROKE_DEFAULT;
	}

	public static float GetDefaulSwitchIconCellHeight()
	{
		return SWITCHICONCELL_HEIGHT_DEFAULT; 
	}

	public static float GetDefaultExtendAdViewWidth()
	{
	    if(ApplicationConfigure.iPhoneDevice())
	        return EXTEND_ADVIEW_WIDTH_IPHONE;            
	    else
	        return EXTEND_ADVIEW_WIDTH_IPAD;
	}

	public static float GetDefaultExtendAdViewHeight()
	{
	    if(ApplicationConfigure.iPhoneDevice())
	        return EXTEND_ADVIEW_HEIGHT_IPHONE;            
	    else
	        return EXTEND_ADVIEW_HEIGHT_IPAD;
	}

	public static float GetExtendAdViewCornerWidth()
	{
	    if(ApplicationConfigure.iPhoneDevice())
	        return EXTEND_ADVIEW_CORNER_WIDTH_IPHONE;            
	    else
	        return EXTEND_ADVIEW_CORNER_WIDTH_IPAD;
	}

	public static float GetExtendAdViewCornerHeight()
	{
	    if(ApplicationConfigure.iPhoneDevice())
	        return EXTEND_ADVIEW_CORNER_HEIGHT_IPHONE;            
	    else
	        return EXTEND_ADVIEW_CORNER_HEIGHT_IPAD;
	}

	public static float GetHouseAdViewSize()
	{
	    if(ApplicationConfigure.iPhoneDevice())
	        return HOUSE_ADVIEW_SIZE_IPHONE;            
	    else
	        return HOUSE_ADVIEW_SIZE_IPAD;
	}

	public static float GetDefaultExtendAdViewDisplayTime()
	{
	    return 2.0f;
	}

	public static float GetDefaultHouseAdDisplayTime()
	{
	    return 6.0f;
	}

	public static float GetDefaultHouseAdViewDisplayTime()
	{
	    return 24.0f;
	}

	public static float GetDefaultHouseAdViewDisplayInterval()
	{
	    return 300.f;
	}*/
/*
	public static (UIViewController *)GetMainViewController
	{
	    UIViewController *pRet = nil;
	    id<MainStdAdApplicationDelegateTemplate> pDelegate = ((id<MainStdAdApplicationDelegateTemplate>)[[UIApplication sharedApplication] delegate]);
	    
	    if(pDelegate != nil)
	    {
	        pRet = [pDelegate MainViewController];
	    }
	    
	    return pRet;
	}
*/	
}
