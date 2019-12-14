package com.xgadget.ChuiNiuLite;

import android.graphics.Rect;

public class CGameLayout 
{
	public static final float m_fDogXYScale = 1.4f;
	public static final float m_fDogRealYScale = 0.18f;
	
	public static final float 	m_fAdViewHeight = 52;  	//The ad view height;
	public static final float 	m_fGameSceneMeasureHeight = 600.0f;  	//The game scene measure height;
	public static final float 	m_fGameSceneMeasureWidth = 800.0f;  	//The game scene measure width;
	public static final float 	m_fGameSceneMeasureRatio = 0.75f;  	//The game scene measure ratio height/width;
	public static final float 	m_fRockMeasureWidth = 80.0f;  	//The game scene measure width;
	public static final float 	m_fRockMeasureHeight = 52.0f;  	//The game scene measure ratio height/width;
	public static final float 	m_fPaperStrokeSize = 2.0f;  	//The game scene measure ratio height/width;

	public static final float GAME_RAINBOW_WIDTH = 450.0f;		
	
	public static final float	m_fDogBulletMeasureWidth = 56.0f;
	public static final float	m_fDogBulletMeasureHeight = 100.0f;
	public static final float	m_fDogBulletStartRatioX = 0.3f;
	public static final float	m_fDogBulletStartRatioY = 0.10f;
	public static final float	m_fDogBulletChangeRatioX = 0.3f;
	public static final float	m_fDogBulletChangeRatioY = 0.3f;

	public static final float	m_fCowBulletMeasureWidth = 40.0f;
	public static final float	m_fCowBulletMeasureHeight = 40.0f;
	public static final float	m_fCowBulletStartRatioX = 0.3f;
	public static final float	m_fCowBulletStartRatioY = 0.3f;
	public static final float	m_fCowBulletChangeRatioX = 0.1f;
	public static final float	m_fCowBulletChangeRatioY = 0.1f;

	public static final float m_fCowXYScale = 1.5f;
	public static final float m_fCowRealYScale = 0.25f; //0.16f;
	
	public static final float	m_fClockRadium	= 40.0f;
	
	public static boolean m_bHasAdBanners = false;  	//The ad view height;
	
	
	private static float m_fDeviceScreenWidth;  			//The real window screen width;
	private static float m_fDeviceScreenHeight;  		//The real window screen height;
	private static float m_fDeviceClientWidth;  		//The real game client area width;
	private static float m_fDeviceClientHeight;  	//The real game client area height;
	private static boolean m_bDeviceLandscape;  	//The real game client area height;

	private static float m_fGameSceneDeviceWidth;  			//The real window screen width;
	private static float m_fGameSceneDeviceHeight;  		//The real window screen height;
	
	private static float m_fGameSceneDMScaleX;  			//The real window screen width;
	private static float m_fGameSceneDMScaleY;  		//The real window screen height;
	
	private static float m_fGameSceneOriginXInDeviceClient;
	private static float m_fGameSceneOriginYInDeviceClient;
	
	private static float m_CurrentXDPI = 1.0f;
	private static float m_CurrentYDPI = 1.0f;
	private static float m_Density = 1.0f;
	
	private static int m_nGrassUnitCount = 0;
	
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
	
	public static float GetDensity()
	{
		return m_Density;
	}
	
	public static void SetShowAdBanners(boolean bShown)
	{
		m_bHasAdBanners = bShown;
	}
	
	public static boolean IsLandscape()
	{
		return m_bDeviceLandscape;
	}
	
	static int GetAdBannerHeight()
	{
		int nRet = (int)(((float)m_fAdViewHeight)*m_CurrentYDPI);
		if(m_Density <= 1.0f)
			nRet = (int)m_fAdViewHeight;
		//int nRet = (int)(((float)m_fAdViewHeight));
		return nRet;
	}
	
	public static void InitializeLayout(int w, int h)
	{
		m_fDeviceScreenWidth = (float)w;  			//The real window screen width;
		m_fDeviceScreenHeight = (float)h;  		//The real window screen height;
		m_fDeviceClientWidth = (float)w;  		//The real game client area width;
		
		if(w < h) //device orientation: protrait
			m_bDeviceLandscape = false;
		else
			m_bDeviceLandscape = true;
		
		if(m_bHasAdBanners == true)
		{	
			//if(m_bDeviceLandscape == true)
				m_fDeviceClientHeight = (float)(h-GetAdBannerHeight());  	//The real game client area height;
			//else
			//	m_fDeviceClientHeight = (float)h-2.0f*m_fAdViewHeight;  	//The real game client area height;
		}	
		else 
			m_fDeviceClientHeight = (float)h;  	//The real game client area height;
	    
		
		if(m_bDeviceLandscape == true)
		{
			m_fGameSceneDeviceWidth = m_fDeviceClientWidth;  								//The real window screen width;
			m_fGameSceneDeviceHeight = m_fDeviceClientHeight;  								//The real window screen height;
			m_fGameSceneDMScaleX = m_fGameSceneDeviceWidth/m_fGameSceneMeasureWidth;  			//The real window screen width;
			m_fGameSceneDMScaleY = m_fDeviceClientHeight/m_fGameSceneMeasureHeight;  		//The real window screen height;
		}
		else
		{
			m_fGameSceneDeviceHeight = m_fDeviceClientHeight;  								//The real window screen height;
			m_fGameSceneDeviceWidth = m_fGameSceneDeviceHeight/m_fGameSceneMeasureRatio;  		//The real window screen width;
			m_fGameSceneDMScaleX = m_fGameSceneDeviceWidth/m_fGameSceneMeasureWidth;  			//The real window screen width;
			m_fGameSceneDMScaleY = m_fDeviceClientHeight/m_fGameSceneMeasureHeight;  		//The real window screen height;
		}
		
		m_fGameSceneOriginXInDeviceClient = m_fDeviceClientWidth/2.0f;
		if(m_bDeviceLandscape == true)
			m_fGameSceneOriginYInDeviceClient = m_fDeviceClientHeight;
		else 
		{
			//if(m_bHasAdBanners == true)
			//	m_fGameSceneOriginYInDeviceClient = m_fDeviceClientHeight+m_fAdViewHeight;
			//else
				m_fGameSceneOriginYInDeviceClient = m_fDeviceClientHeight;
		}	
		
		int nWidth = (int)GetGrassUnitWidth();
		m_nGrassUnitCount = (2 + ((int)m_fGameSceneMeasureWidth)/nWidth)*((int)(m_fGameSceneDMScaleX+1)) + 10;
	}

	public static float GameSceneToDeviceX(float measureX)
	{
		float x = measureX*m_fGameSceneDMScaleX;
		float fRet = x + m_fGameSceneOriginXInDeviceClient;
		return fRet;
	}

	public static float GameSceneToDeviceY(float measureY)
	{
		float y = measureY*m_fGameSceneDMScaleY;
		float fRet = m_fGameSceneOriginYInDeviceClient - y;
		return fRet;
	}

	public static float DeviceToGameSceneX(float x)
	{
		float fRet = (m_fGameSceneOriginXInDeviceClient-x)/m_fGameSceneDMScaleX;
		return fRet;
	}

	public static float DeviceToGameSceneY(float y)
	{
		float fRet = (m_fGameSceneOriginYInDeviceClient - y)/m_fGameSceneDMScaleY;
		return fRet;
	}
	
	//Convert game object size to device unit, using Y scale since
	//Game scene physic layout is take device height as reference.
	public static float ObjectMeasureToDevice(float v)
	{
		float fRet = v*m_fGameSceneDMScaleY;
		return fRet;
	}

	public static float DeviceToObjectMeasure(float v)
	{
		float fRet = v/m_fGameSceneDMScaleY;
		return fRet;
	}
	
	public static float GetGameSceneOriginXInDevice()
	{
		return m_fGameSceneOriginXInDeviceClient;
	}

	public static float GetGameSceneOriginYInDevice()
	{
		return m_fGameSceneOriginYInDeviceClient;
	}
	
	public static float GetDeviceScreenWidth()
	{
		return m_fDeviceScreenWidth;
	}

	public static float GetDeviceScreenHeight()
	{
		return m_fDeviceScreenHeight;
	}
	
	public static float GetGameClientDeviceWidth()
	{
		return m_fDeviceClientWidth;
	}

	public static float GetGameClientDeviceHeight()
	{
		return m_fDeviceClientHeight;
	}
	
	public static float GetGameSceneDeviceWidth()
	{
		return m_fGameSceneDeviceWidth;
	}

	public static float GetGameSceneDeviceHeight()
	{
		return m_fGameSceneDeviceHeight;
	}

	public static float GetGameSceneWidth()
	{
		return m_fGameSceneMeasureWidth;
	}

	public static float GetGameSceneHeight()
	{
		return m_fGameSceneMeasureHeight;
	}
	
	public static float GetGameSceneDMScaleX()
	{
		return m_fGameSceneDMScaleX;
	}

	public static float GetGameSceneDMScaleY()
	{
		return m_fGameSceneDMScaleY;
	}

	public static float GetGameSceneDMScaleMin()
	{
		if(m_fGameSceneDMScaleY <= m_fGameSceneDMScaleX)
			return m_fGameSceneDMScaleY;
		else
			return m_fGameSceneDMScaleX;
	}
	
	public static float GetRockMeasureWidth()
	{
		return m_fRockMeasureWidth;
	}

	public static float GetRockMeasureHeight()
	{
		return m_fRockMeasureHeight;
	}

	public static float GetDogBulletMeasureWidth()
	{
		return m_fDogBulletMeasureWidth;
	}

	public static float GetDogBulletMeasureHeight()
	{
		return m_fDogBulletMeasureHeight;
	}

	public static float GetDogBulletStartRatioX()
	{
		return m_fDogBulletStartRatioX;
	}

	public static float GetDogBulletStartRatioY()
	{
		return m_fDogBulletStartRatioY;
	}

	public static float GetDogBulletChangeRatioX()
	{
		return m_fDogBulletChangeRatioX;
	}

	public static float GetDogBulletChangeRatioY()
	{
		return m_fDogBulletChangeRatioY;
	}

	public static float GetCowBulletMeasureWidth()
	{
		return m_fCowBulletMeasureWidth;
	}

	public static float GetCowBulletMeasureHeight()
	{
		return m_fCowBulletMeasureHeight;
	}

	public static float GetCowBulletStartRatioX()
	{
		return m_fCowBulletStartRatioX;
	}

	public static float GetCowBulletStartRatioY()
	{
		return m_fCowBulletStartRatioY;
	}

	public static float GetCowBulletChangeRatioX()
	{
		return m_fCowBulletChangeRatioX;
	}

	public static float GetCowBulletChangeRatioY()
	{
		return m_fCowBulletChangeRatioY;
	}

	public static float GetClockRadius()
	{
		return m_fClockRadium;
	}

	public static float GetCowHeight()
	{
		return m_fCowRealYScale*m_fGameSceneMeasureHeight;
	}

	public static float GetCowWidth()
	{
		return m_fCowXYScale*GetCowHeight();
	}
	
	public static float getRainBowWidth()
	{
		return GAME_RAINBOW_WIDTH;
	}
	
	public static float getDogWinY()
	{
		return 250.0f;
	}

	public static float getRainBowWinY()
	{
		return 300.0f;
	}

	public static float getCowWinY()
	{
		return 450.0f;
	}
	
	public static float getPaperStrokeSize()
	{
		float fRet = CGameLayout.ObjectMeasureToDevice(m_fPaperStrokeSize);
		if(fRet < 1.0f)
			fRet = 1.0f;
		
		return fRet;
	}
	
	public static Rect GetDeviceScreenRect()
	{
		Rect rect = new Rect();
		rect.left = 0;
		rect.top = 0;
		rect.right = (int)m_fDeviceScreenWidth;
		rect.bottom = (int)m_fDeviceScreenHeight;  		//The real window screen height;
	
		return rect;
	}

	public static Rect GetGameSceneDeviceRect()
	{
		Rect rect = new Rect();
		rect.left = 0;
		//if(m_bDeviceLandscape == true || m_bHasAdBanners == false)
			rect.top = 0;
		//else
		//	rect.top = (int)m_fAdViewHeight;
				
		rect.right = (int)m_fGameSceneDeviceWidth;
		rect.bottom = rect.top + (int)m_fGameSceneDeviceHeight;  		//The real window screen height;
	
		return rect;
	}

	public static float GetDogHeight()
	{
		float fRet =m_fDogRealYScale*m_fGameSceneMeasureHeight;
		return fRet;
	}
	
	public static float GetDogWidth()
	{
		return m_fDogXYScale*GetDogHeight();
	}
	
	public static float GetGrassUnitHeight()
	{
	    return CGameLayout.GetDogHeight()*0.25f;
	}

	public static float GetGrassUnitWidth()
	{
	    return CGameLayout.GetGrassUnitHeight()*3.0f;
	}

	public static int GetGrassUnitNumber()
	{
		//int nWidth = (int)GetGrassUnitWidth();
		//int nCount = 2 + ((int)m_fGameSceneMeasureWidth)/nWidth;
		
		//return nCount*((int)(m_fGameSceneDMScaleX+1));
		return m_nGrassUnitCount;
	}

	public static float GetBirdWidth()
	{
		float fRet = GetCowWidth()*0.5f;
		return fRet;
	}

	public static float GetBirdHeight()
	{
		float fRet = GetBirdWidth()*0.6f;
		return fRet;
	}

	public static float GetBirdBubbleWidth()
	{
		float fRet = GetCowWidth()*0.2f;
		return fRet;
	}

	public static float GetBirdBubbleHeight()
	{
		float fRet = GetBirdBubbleWidth()*0.5f;
		return fRet;
	}
	
//	#define			GAME_BIRD_WIDTH                     150 //200
//	#define			GAME_BIRD_HEIGHT                    90 //120

//	#define			GAME_BIRD_BUBBLE_WIDTH              60
//	#define			GAME_BIRD_BUBBLE_HEIGHT             30
	
}
