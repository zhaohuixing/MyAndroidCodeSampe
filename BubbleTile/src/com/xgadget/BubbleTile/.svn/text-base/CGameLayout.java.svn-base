package com.xgadget.BubbleTile;
import android.graphics.PointF;
import android.graphics.RectF;
public class CGameLayout 
{
	public static final float ST_SCREEN_LONGER_EDGE = 1024.0f;  //The standard screen longer edge;
	public static final float ST_SCREEN_SHORTER_EDGE = 768.0f;  //The standard screen shorter edge;


	public static final float ST_TITLEBAR_SIZE = 100;
	
	public static final float ST_ADBANNER_HEIGHT = 50.0f;  //The standard screen longer edge;

	
	private static float m_Real_Screen_Width = 0;		//Real window width including ad banner
	private static float m_Real_Screen_Height = 0;    //Real window height including ad banner
	
	private static float m_Valid_Layout_Width = 0;   //Real valid GUI layout width without ad banner
	private static float m_Valid_Layout_Height = 0;  //Real valid GUI layout height without ad banner
	
	private static float m_SizeRatio = 0;
	
	public static void SetScreenSize(int w, int h)
	{
		m_Real_Screen_Width = w;
		m_Real_Screen_Height = h;

		m_Valid_Layout_Width = m_Real_Screen_Width;   //Real valid GUI layout width without ad banner
		m_Valid_Layout_Height = m_Real_Screen_Height - ST_ADBANNER_HEIGHT;  //Real valid GUI layout height without ad banner
	
		boolean bProtrait = CGameLayout.IsProtrait(m_Valid_Layout_Width, m_Valid_Layout_Height);
        if(bProtrait)
        {
        	float tw = ST_SCREEN_SHORTER_EDGE;
        	float th = ST_SCREEN_LONGER_EDGE;
        	float r1 = m_Valid_Layout_Width/tw;
        	float r2 = m_Valid_Layout_Height/th;
        	m_SizeRatio = r1 < r2 ? r1:r2;
        }
        else
        {
        	float th = ST_SCREEN_SHORTER_EDGE;
        	float tw = ST_SCREEN_LONGER_EDGE;
        	float r1 = m_Valid_Layout_Width/tw;
        	float r2 = m_Valid_Layout_Height/th;
        	m_SizeRatio = r1 < r2 ? r1:r2;
        }
        if(1 < m_SizeRatio)
        {
        	m_SizeRatio = 1;
        }
	}
	
	public static float SizeRatio()
	{
		return m_SizeRatio;
	}
	
	public static boolean IsProtrait(float w, float h)
	{
		boolean bRet = false;
		
		if(w < h)
			bRet = true;
		
		return bRet;
	}

	public static float GetTitleBarSize()
	{
		float fRet = ST_TITLEBAR_SIZE*m_SizeRatio;
		return fRet;
	}

	public static float GetRealScreenWidth()
	{
		return m_Real_Screen_Width;
	}
	
	public static float GetRealScreenHeight()
	{
		return m_Real_Screen_Height;
	}
	
	public static float GetValidLayoutWidth()
	{
		return m_Valid_Layout_Width;
	}
	
	public static float GetValidLayoutHeight()
	{
		return m_Valid_Layout_Height;
	}
	
	public static boolean IsProtraitScreenLayout()
	{
		boolean bRet = (m_Real_Screen_Width < m_Real_Screen_Height);
		return bRet;
	}
	

static final int PLAYVIEW_MARGIN        =     10;
static final float GRID_SIZE_RATIO_TO_BOARD =   0.7f;
static final float PLAYAREA_RATIO_BOARD     =   0.9f;
static final float ARROW_MOTION_STEP        =   20.0f;
static final float ARROW_MOTION_RATIO       =   1.0f;
static final float BUBLE_MOTION_THRESHOLD_RATIO =     0.01f;

static final int DEFAULT_SCORELABEL_HEIGHT    =  90;

static final int DEFAULT_INDICATOR_SIZE_IPHONE =  15;
static final int DEFAULT_INDICATOR_SIZE_IPAD   =  30;


public static float GetPlayBoardSize()
{
	float w = CGameLayout. GetValidLayoutWidth();
	float h = CGameLayout. GetValidLayoutHeight();
	
    float fSize = Math.min(w, h) - 2*PLAYVIEW_MARGIN;
    if(CGameLayout.IsProtraitScreenLayout())
        fSize -= CGameLayout.GetTitleBarSize();
    
    return fSize;
}

//This is related to Application main window coordinate 
public static RectF GetPlayBoardRect()
{
    PointF pt = CGameLayout.GetPlayBoardCenter();
    float f = CGameLayout.GetPlayBoardSize();
    RectF rect = new RectF(pt.x-f/2, pt.y-f/2, pt.x-f/2 + f, pt.y-f/2 + f);
    return rect;
}

//This is related to Application main window coordinate 
public static PointF GetPlayBoardCenter()
{
    float x =  CGameLayout.GetValidLayoutWidth()/2; 
    float y = CGameLayout.GetValidLayoutHeight()/2;
    if(CGameLayout.IsProtraitScreenLayout())
        y = (CGameLayout.GetValidLayoutHeight() + CGameLayout.GetTitleBarSize())/2;
    
    PointF pt = new PointF(x, y);
    return pt;
}

/*
//This is related to Play board coordinate 
+(float)GetGridMaxSize
{
    float fRet = [GameLayout GetPlayBoardSize]*GRID_SIZE_RATIO_TO_BOARD;
    return fRet;
}
*/

public static float GetGridMaxSize(float nGrid)
{
    float fSize = CGameLayout.GetPlayBoardSize()*PLAYAREA_RATIO_BOARD;
    float fRet = fSize*nGrid/(nGrid+1.0f);
    return fRet;
}

//This is related to Play board coordinate 
public static PointF GetGridCenter()
{
    float f = CGameLayout.GetPlayBoardSize();
    PointF pt = new PointF(f/2.0f, f/2.0f);
    return pt;
}

public static float GetTouchSensitivity()
{
    return 0.5f;
}

public static float GetIconViewSize()
{
	float w = CGameLayout. GetValidLayoutWidth();
	float h = CGameLayout. GetValidLayoutHeight();
	
    float fSize = Math.min(w, h) - 4*PLAYVIEW_MARGIN;
    if(CGameLayout.IsProtraitScreenLayout())
        fSize -= CGameLayout.GetTitleBarSize();
	
    fSize /= 2.0;
     
    return fSize;
}

public static float GetPlayBoardMargin()
{
    return PLAYVIEW_MARGIN;
}

public static  float GetDefaultIconImageSize()
{
    if(ApplicationConfigure.iPhoneDevice())
        return 160.0f;
    else
        return 380.0f;
}

public static float GetLayoutSignSize()
{
	return 2.0f*CGameLayout.GetTitleBarSize();
}

public static float GetArrowAnimationStep()
{
    return ARROW_MOTION_STEP;
}

public static float GetArrowAnimationLimitRatio()
{
    return ARROW_MOTION_RATIO;
}

public static float GetBubbleMotionThreshold(float fBubbleSize)
{
    float fRet = Math.max(fBubbleSize*0.2f, BUBLE_MOTION_THRESHOLD_RATIO*CGameLayout.GetPlayBoardSize());
    return fRet;
}

public static float GetDefaultScoreLabelHeight()
{
    return DEFAULT_SCORELABEL_HEIGHT;
}

public static float GetGameDifficultyIndicatorSize()
{
    if(ApplicationConfigure.iPhoneDevice())
        return DEFAULT_INDICATOR_SIZE_IPHONE;
    else
        return DEFAULT_INDICATOR_SIZE_IPAD;
}

public static RectF GetGameDifficultyIndicatorRect()
{
    float s = 2.0f;//[GameLayout GetPlayBoardSize]*(1-PLAYAREA_RATIO_BOARD)*0.5;
    if(ApplicationConfigure.iPhoneDevice())
        s = 1;
    float f = CGameLayout.GetGameDifficultyIndicatorSize();
    RectF rect = new RectF(s, s, s + f, s + f);
    return rect;
}
	
	
}
