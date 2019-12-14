package com.xgadget.SimpleGambleWheel;

//import android.util.DisplayMetrics;

public class GameLayoutConstant 
{
	public static final float m_fWheelGadgetSizeRatio = 0.8f;
	private static int m_CurrentGameScreenWidth = 320;
	private static int m_CurrentGameScreenHeight = 480;
	private static final int m_AdBannerWidth = 320;
	private static final int m_AdBannerHeigth = 52;
	public static final int m_nToolbarButtonMinSize = 30;
	public static final int m_nToolbarButtonMaxSize = 100;
	private static int m_CurrentToolbarHeight = m_nToolbarButtonMinSize;
	private static int m_CurrentCashBalanceSignSize = (int)(((float)m_nToolbarButtonMinSize)*1.2f);
	
	public static final float m_fPointerWheelSizeRatio = 0.828f;
	public static final int m_CompassWheelSizeMin = 220;
	private static int m_CurrentCompassWheelSize = m_CompassWheelSizeMin;
	
	
	public static final int m_nAvatarDisplayTopOffsetToDecoration = 10;
	public static final int m_nAvatarDisplayMazSize = 120;
	public static final int m_nAvatarDisplayMinSize = 40;
	public static final float m_fAvatarSizeRatioWithEdge = 0.15f;//0.2f;
	private static int m_CurrentAvatarSize = m_nAvatarDisplayMinSize;
	public static final int m_nAvatarDisplayProtraitVerticalMarginMin = 40;
	public static final int m_nAvatarDisplayProtraitHorizontalMarginMin = 5;
	
	public static final int m_nAvatarDisplayLandscapeVerticalMarginMin = 8;
	public static final int m_nAvatarDisplayLandscapeHorizontalMarginMin = 60;
	
	private static float m_CurrentXDPI = 1.0f;
	private static float m_CurrentYDPI = 1.0f;
	private static float m_Density = 1.0f;
	
	public static final int m_PlayerBetPickerWidthMin = 200;
	public static final int m_PlayerBetPickerWidthMax = 400;
	public static final int m_PlayerBetPickerHeightMin = 160;
	public static final int m_PlayerBetPickerHeightMax = 300;
	public static final float m_PlayerBetPickerHeightWidthRatio = 0.75f;
	public static int m_CuurentPlayerBetPickerHeight = 160;
	public static int m_CuurentPlayerBetPickerWidth = 200;
	
	
	
	public static void SetCurrentScreenDensity(float xdpi, float ydpi, float density)
	{
		m_CurrentXDPI = xdpi/100.0f;
		if(m_CurrentXDPI <= 1.0f)
			m_CurrentXDPI = 1.0f;
		
		m_CurrentYDPI = ydpi/100.0f;
		if(m_CurrentYDPI <= 1.0f)
			m_CurrentYDPI = 1.0f;
		
		m_Density = density;
	}
	
	static int GetAdBannerWidth()
	{
		int nRet = (int)(((float)m_AdBannerWidth)*m_CurrentXDPI);
		if(m_Density <= 1.0f)
			nRet = m_AdBannerWidth;
		return nRet;
	}

	static int GetAdBannerHeight()
	{
		int nRet = (int)(((float)m_AdBannerHeigth)*m_CurrentYDPI);
		if(m_Density <= 1.0f)
			nRet = m_AdBannerHeigth;
		return nRet;
	}

	public static void CalculateAvatarSize()
	{
		float fEdge;
		if(m_CurrentGameScreenWidth < m_CurrentGameScreenHeight)
		{
			fEdge = (float)m_CurrentGameScreenWidth;
		}
		else
		{
			fEdge = (float)m_CurrentGameScreenHeight; 
		}
		float fSize = fEdge*m_fAvatarSizeRatioWithEdge;
		m_CurrentAvatarSize = (int)fSize;
		if(m_CurrentAvatarSize < m_nAvatarDisplayMinSize)
			m_CurrentAvatarSize = m_nAvatarDisplayMinSize;
		if(m_nAvatarDisplayMazSize < m_CurrentAvatarSize)
			m_CurrentAvatarSize = m_nAvatarDisplayMazSize;
	}

	public static void CalculateToolbarSize()
	{
		int screenWidth = GameLayoutConstant.GetCurrentScreenWidth(); //m_LayoutContainer.getWidth(); //MyAbsoluteLayout.GetCurrentScreenWidth();
		int screenHeight = GameLayoutConstant.GetCurrentContentViewHeight(); //m_LayoutContainer.getHeight();  //MyAbsoluteLayout.GetCurrentScreenHeight();
		if(screenWidth < screenHeight)
		{
			m_CurrentToolbarHeight = (int)(((float)screenWidth)*0.1f);
		}
		else
		{
			m_CurrentToolbarHeight = (int)(((float)screenHeight)*0.1f);
		}
		if(m_CurrentToolbarHeight < GameLayoutConstant.m_nToolbarButtonMinSize)
			m_CurrentToolbarHeight = GameLayoutConstant.m_nToolbarButtonMinSize;
		if(GameLayoutConstant.m_nToolbarButtonMaxSize < m_CurrentToolbarHeight)	
			m_CurrentToolbarHeight = GameLayoutConstant.m_nToolbarButtonMaxSize;
		m_CurrentCashBalanceSignSize = (int)(((float)m_CurrentToolbarHeight)*1.5f);
	}
	
	public static void CalculateCompassSize()
	{
		int screenWidth = GameLayoutConstant.GetCurrentScreenWidth(); //m_LayoutContainer.getWidth(); //MyAbsoluteLayout.GetCurrentScreenWidth();
		int screenHeight = GameLayoutConstant.GetCurrentContentViewHeight(); //m_LayoutContainer.getHeight();  //MyAbsoluteLayout.GetCurrentScreenHeight();
		if(screenWidth < screenHeight)
		{
			m_CurrentCompassWheelSize = (int)(((float)screenWidth)*GameLayoutConstant.m_fWheelGadgetSizeRatio);
		}
		else
		{
			m_CurrentCompassWheelSize = (int)(((float)screenHeight)*GameLayoutConstant.m_fWheelGadgetSizeRatio);
		}
		if(m_CurrentCompassWheelSize < GameLayoutConstant.m_CompassWheelSizeMin)
			m_CurrentCompassWheelSize = GameLayoutConstant.m_CompassWheelSizeMin;
	}
	
	public static void CalculatePlayerBetViewSize()
	{
		int screenWidth = GameLayoutConstant.GetCurrentScreenWidth(); 
		int screenHeight = GameLayoutConstant.GetCurrentContentViewHeight();
		if(screenWidth < screenHeight)
		{
			m_CuurentPlayerBetPickerWidth = (int)(((float)screenWidth)*0.75f);
		}
		else
		{
			m_CuurentPlayerBetPickerWidth = (int)(((float)screenWidth)*0.33f);
		}
		if(m_CuurentPlayerBetPickerWidth < m_PlayerBetPickerWidthMin)
			m_CuurentPlayerBetPickerWidth = m_PlayerBetPickerWidthMin;
		if(m_PlayerBetPickerWidthMax < m_CuurentPlayerBetPickerWidth)
			m_CuurentPlayerBetPickerWidth = m_PlayerBetPickerWidthMax; 
		
		m_CuurentPlayerBetPickerHeight = (int)(((float)m_CuurentPlayerBetPickerWidth)*m_PlayerBetPickerHeightWidthRatio);
		if(m_CuurentPlayerBetPickerHeight < m_PlayerBetPickerHeightMin)
			m_CuurentPlayerBetPickerHeight = m_PlayerBetPickerHeightMin;
		if(m_PlayerBetPickerHeightMax < m_CuurentPlayerBetPickerHeight)
			m_CuurentPlayerBetPickerHeight = m_PlayerBetPickerHeightMax;
	}
	
	public static void SetCurrentScreenDemension(int newWidth, int newHeight)
	{
		m_CurrentGameScreenWidth = newWidth;
		m_CurrentGameScreenHeight = newHeight;
		CalculateCompassSize();
		CalculateAvatarSize();
		CalculateToolbarSize();
		CalculatePlayerBetViewSize();
	}
	
	public static int GetCurrentAvatarSize()
	{
		return m_CurrentAvatarSize;
	}

	public static int GetActivePlayerIndicatorSize()
	{
		return m_CurrentAvatarSize/2;
	}
	
	public static int GetCurrentToolbarHeight()
	{
		return m_CurrentToolbarHeight;
	}
	
	public static int GetCurrentBalanceSignSize()
	{
		return m_CurrentCashBalanceSignSize;
	}
	
	public static int GetCurrentCompassWheelSize()
	{
		return m_CurrentCompassWheelSize;
	}
	
	public static int GetCurrentCashBalanceSignSize()
	{
		return m_CurrentCashBalanceSignSize;
	}

	public static int GetCurrentMenuItemSize()
	{
		return (int)(((float)m_CurrentToolbarHeight)*1.2f);
	}
	
	public static int GetCurrentPlayerBetPickerHeight()
	{
		return m_CuurentPlayerBetPickerHeight;
	}
	
	public static int GetCurrentPlayerBetPickerWidth()
	{
		return m_CuurentPlayerBetPickerWidth;
	}
	
	public static int GetCurrentScreenWidth()
	{
		return m_CurrentGameScreenWidth;
	}

	public static int GetCurrentScreenHeight()
	{
		return m_CurrentGameScreenHeight;
	}

	static int GetCurrentContentViewHeight()
	{
		//return (m_CurrentGameScreenHeight - m_AdBannerHeigth);
		int nScreenHeight = GetCurrentScreenHeight();
		int nBannerHeight = GetAdBannerHeight();
		int nRet = nScreenHeight - nBannerHeight;
		return nRet;
	}
	
	static Boolean IsScreenProtraitMode()
	{
		Boolean bRet = true;
		
		if(m_CurrentGameScreenHeight < m_CurrentGameScreenWidth)
			bRet = false;
		
		return bRet;
	}

	static Boolean IsScreenLandscapeMode()
	{
		Boolean bRet = !GameLayoutConstant.IsScreenProtraitMode();
		
		return bRet;
	}
	

}
