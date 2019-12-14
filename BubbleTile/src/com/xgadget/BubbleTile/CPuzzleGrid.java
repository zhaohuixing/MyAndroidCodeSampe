package com.xgadget.BubbleTile;
import java.util.ArrayList;
import java.lang.Math;
import android.util.FloatMath;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class CPuzzleGrid implements IPuzzleGrid
{
    enGridLayout        m_LayoutType;
    int                 m_nEdge;
    float               m_fBubbleSize;
    
    enBubbleMotion      m_Motion;
    enMotionDirection   m_Direction;
    
	ArrayList<BubbleObject>			m_Bubbles;
	ArrayList<GridCell>				m_Cells;
	ArrayList<IndexObject>			m_IndexListForReset;
	ArrayList<CUndoCommand>			m_UndoCommandList;

    float               m_fOffset;
    PointF   	        m_ptTouchPoint;
    int                 m_nTouchedCellIndex;
    
    //For icon template image
    RectF	            m_IconBound;
    Boolean             m_bIconTemplate;
    
    PointF             m_ptArrowStart;
    PointF             m_ptArrowEnd;
    PointF             m_ptArrowChange;
    PointF             m_ptArrowPosition;
//    Drawable          m_ArrowAnimation;
    Bitmap				m_ArrowAnimation;
//    Drawable          m_StarBall;
    Bitmap				m_StarBall;
    
    //Drawable          m_GreenLED;
    Bitmap				m_GreenLED;
    
    //Drawable          m_RedLED;
    Bitmap				m_RedLED;
    
    Boolean                m_bWinState;
    
//@protected
    protected EasyShuffleSuite   m_EasySuite;
    
    Boolean             	m_bEasyAnimation;
    Boolean             	m_bNeedReset;
    ArrayList<IndexObject>	m_SnapshotInEasyAnimation;
    int                 	m_nEasyAnimationStep;
    int                 	m_nLEDFlashStep;
    
    int                 	m_nGestureDelay;
  
    public void UpdateGridLayout()
    {
    	
    }

// empty implementation
public enGridType GetGridType()
{
	return enGridType.PUZZLE_GRID_TRIANDLE;
}

public int GetGridColumne(int nIndex)
{
	return 0;
}


public int GetUndoLocationInfo(){return 0;}

public int GetGridRow(int nIndex){return 0;}

public void DrawMotionGrid(Canvas contex){}

public int GetBubbleNumberAtRow(int nRowIndex){return 0;}
public int GetFirstIndexAtRow(int nRowIndex){return 0;}
public void InitializeGridCells(){}
public void InitializeMatrixLayout(){}
public void InitializeSnakeLayout(){}
public void InitializeSpiralLayout(){}
public void CalculateBubbleSize(){}
public void ExceuteUndoCommand(enBubbleMotion enMotion, enMotionDirection enDir, int nIndex){}
public void CalculateCurrentTouchGesture(){}
// empty implementation end


public CPuzzleGrid()
{
//    if((self = [super init]))
    {
        m_LayoutType = enGridLayout.PUZZLE_LALOUT_MATRIX;
        m_nEdge = GameConfiguration.GetDefaultBubbleUnit();
        m_fBubbleSize = 128.0f;
        m_Bubbles = new ArrayList<BubbleObject>();//[[NSMutableArray alloc] init]; //[[[NSMutableArray alloc] init] retain];
        m_Cells = new ArrayList<GridCell>();//[[NSMutableArray alloc] init]; //[[[NSMutableArray alloc] init] retain];
        m_IndexListForReset = new ArrayList<IndexObject>();//[[NSMutableArray alloc] init];//[[[NSMutableArray alloc] init] retain];     
        m_UndoCommandList = new ArrayList<CUndoCommand>();//[[NSMutableArray alloc] init];//[[[NSMutableArray alloc] init] retain];

        m_bIconTemplate = false;

        m_ArrowAnimation = CResourceHelper.GetBitmap(R.drawable.arrow);//[ImageLoader LoadResourceImage:@"arrow.png"];
        m_StarBall = CResourceHelper.GetBitmap(R.drawable.starballs);//[ImageLoader LoadResourceImage:@"starballs.png"];

        m_GreenLED = CResourceHelper.GetBitmap(R.drawable.gled);//[ImageLoader LoadResourceImage:@"gled.png"];
        m_RedLED = CResourceHelper.GetBitmap(R.drawable.rled);//[ImageLoader LoadResourceImage:@"rled.png"];
        
        m_bWinState = false;
        m_ptTouchPoint = new PointF(0,0);
        m_ptArrowStart = new PointF(0,0);
        m_ptArrowEnd = new PointF(0,0);
        m_ptArrowChange = new PointF(0,0);
        m_ptArrowPosition = new PointF(0,0);
        CleanTouchState();
        m_EasySuite = new EasyShuffleSuite();//[[EasyShuffleSuite alloc] init];
        
        m_bEasyAnimation = false;
        m_SnapshotInEasyAnimation = new ArrayList<IndexObject>();//[[NSMutableArray alloc] init];
        m_nEasyAnimationStep = 0;
        m_bNeedReset = false;
    }
}

//-(void)Dealloc
//{
//	[m_Bubbles removeAllObjects];
//	[m_Bubbles release];
//	[m_Cells removeAllObjects];
//	[m_Cells release];
//	[m_IndexListForReset removeAllObjects];
//	[m_IndexListForReset release];
//	[m_UndoCommandList removeAllObjects];
//   [m_UndoCommandList release];
//    CGImageRelease(m_ArrowAnimation);
//    CGImageRelease(m_StarBall);
//    CGImageRelease(m_GreenLED);
//    CGImageRelease(m_RedLED);
//    [m_EasySuite release];
//    [m_SnapshotInEasyAnimation release];
 //   [self release];
//}

public enGridLayout GetGridLayout()
{
    return m_LayoutType;
}

public void SetGridLayout(enGridLayout gLayout)
{
    m_LayoutType = gLayout;
}

public void SetBubbleUnit(int nEdge)
{
    m_nEdge = nEdge;
}

public int GetBubbleUnit()
{
    return m_nEdge;
}

public float GetBubbleDiameter()
{
    return m_fBubbleSize;
}

public PointF GetBubbleCenter(int nIndex)
{
    PointF pt = new PointF(-1, -1);
    
    int nCount = m_Cells.size();
    if(0 < nCount && 0 <= nIndex && nIndex < nCount)
    {
        GridCell pc = (GridCell)m_Cells.get(nIndex);
        if(!pc.equals(null))
        {
            pt = pc.GetCenter();
        }
    }
    
    return pt;
}

public void DrawStaticGrid(Canvas context)
{
    int nCount = m_Bubbles.size();
    if(0 < nCount)
    {
        RectF rect;
        float fRad = (float)(m_fBubbleSize/2.0);
        for(int i = 0; i < nCount; ++i)
        {
            BubbleObject pb = (BubbleObject)m_Bubbles.get(i);
            if(!pb.equals(null))
            {
                PointF pt = this.GetBubbleCenter(pb.m_nCurrentLocationIndex);
                if(0 <= pt.x && 0 <= pt.y)
                {
                    rect = new RectF(pt.x-fRad, pt.y-fRad, pt.x-fRad + m_fBubbleSize, pt.y-fRad + m_fBubbleSize);
                    pb.DrawBubble(context, rect);
                }
            }
        }
    }
}

public void DrawWinLayout(Canvas context)
{
    this.DrawStaticGrid(context);
    int nCount = m_Bubbles.size();
    if(0 < nCount)
    {
//        RectF rect;
        float fRad = m_fBubbleSize/10.0f;
        float fStoke = 2.4f;
  //      if ([ApplicationConfigure iPhoneDevice])
    //        fStoke = 2;
        
        context.save();
        Paint paint = new Paint();
        paint.setStrokeWidth(fStoke);
//        CGContextSaveGState(context);
//        CGContextSetLineWidth(context, fStoke);
 //       CGContextSetRGBStrokeColor(context, 1, 1, 0.5, 1);
        paint.setColor(0xffff80ff);
        
//        CGColorSpaceRef		shadowClrSpace;
//        CGColorRef			shadowClrs;
//        CGSize				shadowSize;
//        shadowClrSpace = CGColorSpaceCreateDeviceRGB();
 //       shadowSize = CGSizeMake(5, 5);
 //       float clrvals[] = {0.1, 0.1, 0.1, 0.9};
 //       shadowClrs = CGColorCreate(shadowClrSpace, clrvals);
 //       CGContextSetShadowWithColor(context, shadowSize, 6.0, shadowClrs);
        ///
  //      CGContextSetAlpha(context, 0.8);

        for(int i = 0; i < nCount; ++i)
        {
            int nLabel1 = i+1;
            int index1 = this.GetBubbleDestinationIndexByLabelValue(nLabel1);
            BubbleObject pb = (BubbleObject)m_Bubbles.get(index1);
            if(!pb.equals(null))
            {
                PointF pt = this.GetBubbleCenter(pb.m_nCurrentLocationIndex);
                if((i+1) < nCount)
                {
                    int nLabel2 = nLabel1+1;
                    int index2 = this.GetBubbleDestinationIndexByLabelValue(nLabel2);
                    BubbleObject pb2 = m_Bubbles.get(index2);
                    if(!pb2.equals(null))
                    {
                        PointF pt2 = this.GetBubbleCenter(pb2.m_nCurrentLocationIndex);
                        ///
                        context.drawLine(pt.x, pt.y, pt2.x, pt2.y, paint);
                    }
                }
                if(0 <= pt.x && 0 <= pt.y)
                {
                    RectF rect = new RectF(pt.x-fRad, pt.y-fRad, pt.x-fRad + fRad*2.0f, pt.y-fRad + fRad*2.0f);
//                    CGContextDrawImage(context, rect, m_StarBall);
                    Rect rcSrc = new Rect(0,0, m_StarBall.getWidth(), m_StarBall.getHeight());
                    context.drawBitmap(m_StarBall, rcSrc, rect, paint);
                }
                
            }
        }
//        CGContextRestoreGState(context);
 //       CGColorSpaceRelease(shadowClrSpace);
 //       CGColorRelease(shadowClrs);
        
    }
}


public void DrawGameTypeIndicator(Canvas context)
{
    RectF rt = CGameLayout.GetGameDifficultyIndicatorRect();
    Bitmap bitmap = null;
    if(GameConfiguration.IsGameDifficulty() == true)
    {
//        CGContextDrawImage(context, rt, m_RedLED);
    	bitmap = m_RedLED;
    }
    else
    {
        if(m_bNeedReset)
        {
            if(m_nLEDFlashStep == 0)
 //               CGContextDrawImage(context, rt, m_GreenLED);
            	bitmap = m_GreenLED;
        }
        else
        {    
 //           CGContextDrawImage(context, rt, m_GreenLED);
        	bitmap = m_GreenLED;
        }    
    }
    
    if (bitmap != null)
    {
//      CGContextDrawImage(context, rect, m_StarBall);
      Rect rcSrc = new Rect(0,0, bitmap.getWidth(), bitmap.getHeight());
      context.drawBitmap(m_StarBall, rcSrc, rt, null);	
    }
}


//This is default implementation and need to be updated to render
//the bobbule motion with the bubble motion algorithm.
public void DrawGrid(Canvas context)
{
    this.DrawGameTypeIndicator(context);
    if (m_Motion == enBubbleMotion.BUBBLE_MOTION_NONE)
    {
        if (m_bWinState == true)
            this.DrawWinLayout(context);
        else
            this.DrawStaticGrid(context);
    }
    else
    {
        this.DrawMotionGrid(context);
    }
}

public void DrawArrowAnimation(Canvas context)
{
}
/*	
public void DrawArrowAnimation(Canvas context)
	{
    CGContextSaveGState(context);
    float fAngle = 0.0;
    CGRect rect = CGRectMake(m_ptArrowPosition.x-m_fBubbleSize*0.5, m_ptArrowPosition.y-m_fBubbleSize*0.5, m_fBubbleSize, m_fBubbleSize);

    if(m_Motion == BUBBLE_MOTION_HORIZONTAL)
    {
        if(m_Direction == MOTION_DIRECTION_FORWARD)
            fAngle = 90.0;
        else if(m_Direction == MOTION_DIRECTION_BACKWARD)
            fAngle = 270.0;
    }
    else if(m_Motion == BUBBLE_MOTION_VERTICAL)
    {
        if(m_Direction == MOTION_DIRECTION_BACKWARD)
            fAngle = 180.0;
    }
    else if(m_Motion == BUBBLE_MOTION_60DIAGONAL)
    {
        if(m_Direction == MOTION_DIRECTION_FORWARD)
            fAngle = 30.0;
        else if(m_Direction == MOTION_DIRECTION_BACKWARD)
            fAngle = 210.0;
    }
    else if(m_Motion == BUBBLE_MOTION_120DIAGONAL)
    {
        if(m_Direction == MOTION_DIRECTION_FORWARD)
            fAngle = 330.0;
        else if(m_Direction == MOTION_DIRECTION_BACKWARD)
            fAngle = 150.0;
    }
    if(fAngle != 0.0)
    {
		CGContextTranslateCTM(context, m_ptArrowPosition.x, m_ptArrowPosition.y);
		CGContextRotateCTM(context, fAngle*M_PI/180.0f);
		CGContextTranslateCTM(context, -m_ptArrowPosition.x, -m_ptArrowPosition.y);
    }

    CGContextSetAlpha(context, 0.3);
    CGContextDrawImage(context, rect, m_ArrowAnimation);
    CGContextRestoreGState(context);
}
*/
public void InitializeArrowAnimationHorizontal()
{
    PointF ptCenter = this.GetBubbleCenter(m_nTouchedCellIndex);
    float fDist = m_fBubbleSize*CGameLayout.GetArrowAnimationLimitRatio();
    
    m_ptArrowChange.y = 0;
    m_ptArrowStart.y = ptCenter.y;
    m_ptArrowEnd.y = ptCenter.y;
    if(m_Direction == enMotionDirection.MOTION_DIRECTION_FORWARD)
    {
        m_ptArrowStart.x = ptCenter.x-fDist;
        m_ptArrowEnd.x = ptCenter.x+fDist;
        m_ptArrowChange.x = fDist*2.0f/CGameLayout.GetArrowAnimationStep();
    }
    else
    {    
        m_ptArrowStart.x = ptCenter.x+fDist;
        m_ptArrowEnd.x = ptCenter.x-fDist;
        m_ptArrowChange.x = fDist*(-2.0f)/CGameLayout.GetArrowAnimationStep();
    }
    m_ptArrowPosition.x = m_ptArrowStart.x;        
    m_ptArrowPosition.y = m_ptArrowStart.y;        
}

public void InitializeArrowAnimationVertical()
{
    PointF ptCenter = this.GetBubbleCenter(m_nTouchedCellIndex);
    float fDist = m_fBubbleSize*CGameLayout.GetArrowAnimationLimitRatio();
    
    m_ptArrowChange.x = 0;
    m_ptArrowStart.x = ptCenter.x;
    m_ptArrowEnd.x = ptCenter.x;
    if(m_Direction == enMotionDirection.MOTION_DIRECTION_FORWARD)
    {
        m_ptArrowStart.y = ptCenter.y+fDist;
        m_ptArrowEnd.y = ptCenter.y-fDist;
        m_ptArrowChange.y = fDist*(-2.0f)/CGameLayout.GetArrowAnimationStep();
    }
    else
    {    
        m_ptArrowStart.y = ptCenter.y-fDist;
        m_ptArrowEnd.y = ptCenter.y+fDist;
        m_ptArrowChange.y = fDist*2.0f/CGameLayout.GetArrowAnimationStep();
    }
    m_ptArrowPosition.x = m_ptArrowStart.x;        
    m_ptArrowPosition.y = m_ptArrowStart.y;        
}

public void InitializeArrowAnimation60Diagonal()
{
    PointF ptCenter = this.GetBubbleCenter(m_nTouchedCellIndex);
    float fDist = m_fBubbleSize*CGameLayout.GetArrowAnimationLimitRatio();
    float fDistX = (float)(fDist*0.5f);
    float fDistY = (float)(fDist*SQURT_3*0.5f);
    
    if(m_Direction == enMotionDirection.MOTION_DIRECTION_FORWARD)
    {
        m_ptArrowStart.x = ptCenter.x-fDistX;
        m_ptArrowEnd.x = ptCenter.x+fDistX;
        m_ptArrowStart.y = ptCenter.y+fDistY;
        m_ptArrowEnd.y = ptCenter.y-fDistY;
        m_ptArrowChange.y = fDistY*(-2.0f)/CGameLayout.GetArrowAnimationStep();
        m_ptArrowChange.x = fDistX*2.0f/CGameLayout.GetArrowAnimationStep();
    }
    else
    {    
        m_ptArrowStart.x = ptCenter.x+fDistX;
        m_ptArrowEnd.x = ptCenter.x-fDistX;
        m_ptArrowStart.y = ptCenter.y-fDistY;
        m_ptArrowEnd.y = ptCenter.y+fDistY;
        m_ptArrowChange.y = fDistY*2.0f/CGameLayout.GetArrowAnimationStep();
        m_ptArrowChange.x = fDistX*(-2.0f)/CGameLayout.GetArrowAnimationStep();
    }
    m_ptArrowPosition.x = m_ptArrowStart.x;        
    m_ptArrowPosition.y = m_ptArrowStart.y;        
}

public void InitializeArrowAnimation120Diagonal()
{
    PointF ptCenter = this.GetBubbleCenter(m_nTouchedCellIndex);
    float fDist = m_fBubbleSize*CGameLayout.GetArrowAnimationLimitRatio();
    float fDistX = fDist*0.5f;
    float fDistY = (float)(fDist*SQURT_3*0.5f);
    
    if(m_Direction == enMotionDirection.MOTION_DIRECTION_FORWARD)
    {
        m_ptArrowStart.x = ptCenter.x+fDistX;
        m_ptArrowEnd.x = ptCenter.x-fDistX;
        m_ptArrowStart.y = ptCenter.y+fDistY;
        m_ptArrowEnd.y = ptCenter.y-fDistY;
        m_ptArrowChange.y = fDistY*(-2.0f)/CGameLayout.GetArrowAnimationStep();
        m_ptArrowChange.x = fDistX*(-2.0f)/CGameLayout.GetArrowAnimationStep();
    }
    else
    {    
        m_ptArrowStart.x = ptCenter.x-fDistX;
        m_ptArrowEnd.x = ptCenter.x+fDistX;
        m_ptArrowStart.y = ptCenter.y-fDistY;
        m_ptArrowEnd.y = ptCenter.y+fDistY;
        m_ptArrowChange.y = fDistY*2.0f/CGameLayout.GetArrowAnimationStep();
        m_ptArrowChange.x = fDistX*2.0f/CGameLayout.GetArrowAnimationStep();
    }
    m_ptArrowPosition.x = m_ptArrowStart.x;        
    m_ptArrowPosition.y = m_ptArrowStart.y;        
}


public void InitializeArrowAnimation()
{
    if(m_Motion == enBubbleMotion.BUBBLE_MOTION_HORIZONTAL)
    {
        this.InitializeArrowAnimationHorizontal();
    }
    else if(m_Motion == enBubbleMotion.BUBBLE_MOTION_VERTICAL)
    {
        this.InitializeArrowAnimationVertical();
    }
    else if(m_Motion == enBubbleMotion.BUBBLE_MOTION_60DIAGONAL)
    {
        this.InitializeArrowAnimation60Diagonal();
    }
    else if(m_Motion == enBubbleMotion.BUBBLE_MOTION_120DIAGONAL)
    {
    	this.InitializeArrowAnimation120Diagonal();
    }

}

public void UpdateMotionIndictor()
{
    m_ptArrowPosition.x += m_ptArrowChange.x;
    m_ptArrowPosition.y += m_ptArrowChange.y;

    float dX = m_ptArrowEnd.x - m_ptArrowStart.x;
    float dY = m_ptArrowEnd.y - m_ptArrowStart.y;
    float dDelta = FloatMath.sqrt(dX*dX+dY*dY);

    float dX1 = m_ptArrowPosition.x - m_ptArrowStart.x;
    float dY1 = m_ptArrowPosition.y - m_ptArrowStart.y;
    
    float dChange = FloatMath.sqrt(dX1*dX1 + dY1*dY1);
    if(dDelta <= dChange)
    {
        m_ptArrowPosition.x = m_ptArrowStart.x;
        m_ptArrowPosition.y = m_ptArrowStart.y;
    }
}

public Boolean OnTimerEvent()
{
    Boolean bRet = false;
    
    if(m_bNeedReset)
    {
        m_nLEDFlashStep = (m_nLEDFlashStep+1)%2;
        if(m_nLEDFlashStep == 0)
            return true;
        else
            bRet = true;
    }
    
    if(m_bEasyAnimation == true)
    {
        this.OnTimerEventForEasyAnimation();
        return true;
    }
    
    if(m_Motion != enBubbleMotion.BUBBLE_MOTION_NONE)
    {
        this.UpdateMotionIndictor();
        bRet = true;
    }

    return bRet;
}

public void ShuffleBubble()
{
//#ifdef __ALG_DEV__
//    return;   //For verify index initialization code only
//#endif    
    if(m_Bubbles.equals(null))
        return;

	m_IndexListForReset.clear();
	m_UndoCommandList.clear();
    int nCount = m_Bubbles.size();
    if(nCount > 0)
    {
    	ShuffleSuite pShuffle = new ShuffleSuite(nCount);
        for(int i = 0; i < nCount; ++i)
        {
            int n = pShuffle.GetValue(i);
            ((BubbleObject)m_Bubbles.get(i)).m_nCurrentLocationIndex = n;
            IndexObject index = new IndexObject(n);
            m_IndexListForReset.add(index);
        }
    }
}

public void EasyShuffleBubble()
{
    enGridType enType = this.GetGridType();
    int nCount = m_Bubbles.size();
    m_EasySuite.Shuffle(enType, nCount);
    int i;
    for(i = 0; i < m_EasySuite.GetSteps(); ++i)
    {
        CUndoCommand pStep = m_EasySuite.GetStep(i);
        if(!pStep.equals(null))
        {
            int index = pStep.m_PositionIndex;
            if(0 <= index && index < nCount)
                m_nTouchedCellIndex = ((BubbleObject)m_Bubbles.get(index)).m_nCurrentLocationIndex;
            else
                m_nTouchedCellIndex = 0;
            m_Motion = pStep.m_Motion;
            if(pStep.m_Direction == enMotionDirection.MOTION_DIRECTION_FORWARD)
                m_Direction = enMotionDirection.MOTION_DIRECTION_BACKWARD;
            else
                m_Direction = enMotionDirection.MOTION_DIRECTION_FORWARD;
            
            this.MoveDestinationBubbles(false);
        }
    }
    if(this.MatchCheck() == true)
    {    
        this.CleanTouchState();
        this.StartWinAnimation();
        return;
    }    
    int n;
    for(i = 0; i < nCount; ++i)
    {
        n = ((BubbleObject)m_Bubbles.get(i)).m_nCurrentLocationIndex;
        IndexObject index = new IndexObject(n);
        m_IndexListForReset.add(index);
    }
    m_Motion = enBubbleMotion.BUBBLE_MOTION_NONE;
    m_Direction = enMotionDirection.MOTION_DIRECTION_NONE;
    m_nTouchedCellIndex = -1;
}

public void InitializeGrid()
{
    if(m_bEasyAnimation)
    {    
        m_bEasyAnimation = false;
        this.CleanTouchState();
    }
    
    this.CalculateBubbleSize();
    this.InitializeGridCells();
    
    switch(m_LayoutType)
    {
        case PUZZLE_LALOUT_MATRIX:
            this.InitializeMatrixLayout();
            break;
        case PUZZLE_LALOUT_SNAKE:
            this.InitializeSnakeLayout();
            break;
        case PUZZLE_LALOUT_SPIRAL: 
            this.InitializeSpiralLayout();
            break;
    }
    if(m_bIconTemplate == false)
    {
        if(GameConfiguration.IsGameDifficulty())
        {    
            this.ShuffleBubble();
            if(this.MatchCheck() == true)
                this.ShuffleBubble();
        }
        else
        {
            this.EasyShuffleBubble();
        }
    }    
}

public Boolean MatchCheck()
{
    Boolean bRet = true;
    
    int nCount = m_Bubbles.size();
    if(0 < nCount)
    {
        for(int i = 0; i < nCount; ++i)
        {
            BubbleObject pb = (BubbleObject)m_Bubbles.get(i);
            if(!pb.equals(null))
            {
                if(pb.m_nCurrentLocationIndex != i)
                {
                    bRet = false;
                    break;
                }
            }
        }
    }
    return bRet;
}

public void CheckBubbleState()
{
    int nCount = m_Bubbles.size();
    if(0 < nCount)
    {
        for(int i = 0; i < nCount; ++i)
        {
            BubbleObject pb = (BubbleObject)m_Bubbles.get(i);
            if(!pb.equals(null))
            {
                if(pb.m_nCurrentLocationIndex != i)
                {
                    pb.SetQMark(true);
                }
                else
                {
                    pb.SetQMark(false);
                }
            }
        }
    }
}

public void CleanBubbleCheckState()
{
    if(m_bNeedReset)
    {
        int nCount1 = m_Bubbles.size();
        int nCount2 = m_SnapshotInEasyAnimation.size();
        if(nCount1 == nCount2 && 0 < nCount1)
        {
            for(int i = 0; i < nCount1; ++i)
            {
                IndexObject pIndex = (IndexObject)m_SnapshotInEasyAnimation.get(i);
                ((BubbleObject) m_Bubbles.get(i)).m_nCurrentLocationIndex = pIndex.GetIndex();
            }
        }
        m_SnapshotInEasyAnimation.clear();
        m_bNeedReset = false;
        m_nLEDFlashStep = 0;
    }
    
    int nCount = m_Bubbles.size();
    if(0 < nCount)
    {
        for(int i = 0; i < nCount; ++i)
        {
            BubbleObject pb = (BubbleObject)m_Bubbles.get(i);
            if(!pb.equals(null))
            {
                pb.SetQMark(false);
            }
        }
    }
}


public Boolean OnTouchBegin(PointF pt)
{
    Boolean bRet = false;

    if(m_bWinState == true)
        return true;
    
    this.CleanBubbleCheckState();
    
    int k = 0;
    RectF rect;
    PointF ptCenter;
    float fRad = m_fBubbleSize/2.0f;
    int nCount = m_Cells.size();
    for (k = 0; k < nCount; ++k) 
    {
        ptCenter = this.GetBubbleCenter(k);
        rect = new RectF(ptCenter.x-fRad, ptCenter.y-fRad, ptCenter.x-fRad + m_fBubbleSize, ptCenter.y-fRad + m_fBubbleSize);
        if(rect.contains(pt.x, pt.y) == true)
        {
            m_fOffset = 0;
            m_ptTouchPoint = pt;
            m_nTouchedCellIndex = k;
            m_nGestureDelay = 0;
            return true;
        }
    }
    
    return bRet;
}

public Boolean OnTouchMove(PointF pt)
{
    Boolean bRet = false;
 
    if(m_bWinState == true)
        return true;
    
    if(0 <= m_nTouchedCellIndex)
    {
        if(m_nGestureDelay < 1)
        {
            ++m_nGestureDelay;
            return true;
        }
        m_ptTouchPoint = pt;
        this.CalculateCurrentTouchGesture();
        return true;
    }
    else
    {
        int k = 0;
        RectF rect;
        PointF ptCenter;
        float fRad = m_fBubbleSize/2.0f;
        int nCount = m_Cells.size();
        for (k = 0; k < nCount; ++k) 
        {
            ptCenter = this.GetBubbleCenter(k);
            rect = new RectF(ptCenter.x-fRad, ptCenter.y-fRad, ptCenter.x-fRad + m_fBubbleSize, ptCenter.y-fRad + m_fBubbleSize);
            if(rect.contains(pt.x, pt.y) == true)
            {
                m_fOffset = 0;
                m_ptTouchPoint = pt;
                m_nTouchedCellIndex = k;
                return true;
            }
        }
    }
    return bRet;
}

public Boolean OnTouchEnd(PointF pt)
{
    Boolean bRet = false;
   
    if(m_bWinState == true)
        return true;
    
    if(m_Motion != enBubbleMotion.BUBBLE_MOTION_NONE)
    {
        if(CGameLayout.GetBubbleMotionThreshold(m_fBubbleSize) <= m_fOffset) 
        {
            this.AddUndoData();
            this.ShiftBubbles();
            this.MoveDestinationBubbles(true);
        }
    }
    this.CleanTouchState();
    if (this.MatchCheck() == true)
    {
        int nStep = GameConfiguration.GetPlaySteps();
        int nType = (int)GameConfiguration.GetGridType().ordinal();
        int nLayout = (int)GameConfiguration.GetGridLayout().ordinal();
        int nEdge = GameConfiguration.GetBubbleUnit();
//        [GameScore AddScore:nType withLayout:nLayout withEdge:nEdge withScore:nStep];
        this.StartWinAnimation();
        bRet = true;
    }
    
    m_nGestureDelay = 0;
    
    return bRet;
}

public void StartWinAnimation()
{
    m_bWinState = true;
}

public void StopWinAnimation()
{
    m_bWinState = false;
}

public Boolean IsWinAnimation()
{
    return m_bWinState;
}

public Boolean IsEasyAnimation()
{
    return m_bEasyAnimation;
}

public void StartEasyAnimationStep()
{
    int nCount = m_Bubbles.size();
    CUndoCommand pStep = m_EasySuite.GetStep(m_nEasyAnimationStep);
    if(!pStep.equals(null))
    {
        int index = pStep.m_PositionIndex;
        if(0 <= index && index < nCount)
        {    
            m_nTouchedCellIndex = ((BubbleObject)m_Bubbles.get(index)).m_nCurrentLocationIndex;
        }
        else
        {
            m_nTouchedCellIndex = 0;
        }
        m_Motion = pStep.m_Motion;
        m_Direction = pStep.m_Direction;
        this.InitializeArrowAnimation();
    }
}


public void HandleEasyAnimationStep()
{
    this.ShiftBubbles();
    this.CleanTouchState();
    this.CheckBubbleState();
    --m_nEasyAnimationStep;
    m_fOffset = 0;
    this.StartEasyAnimationStep();
}

public void OnTimerEventForEasyAnimation()
{
    m_ptArrowPosition.x += m_ptArrowChange.x;
    m_ptArrowPosition.y += m_ptArrowChange.y;
    
    float dX = m_ptArrowEnd.x - m_ptArrowStart.x;
    float dY = m_ptArrowEnd.y - m_ptArrowStart.y;
    float dDelta = FloatMath.sqrt(dX*dX+dY*dY);
    
    float dX1 = m_ptArrowPosition.x - m_ptArrowStart.x;
    float dY1 = m_ptArrowPosition.y - m_ptArrowStart.y;
    
    float dChange = FloatMath.sqrt(dX1*dX1 + dY1*dY1);
    m_fOffset += 2*Math.sqrt(m_ptArrowChange.x*m_ptArrowChange.x + m_ptArrowChange.y*m_ptArrowChange.y)/CGameLayout.GetArrowAnimationStep();
    if(dDelta <= dChange)
    {
        if(m_nEasyAnimationStep < 0)
        {
            this.StopEasyAnimation();
        }
        else
        {
            this.HandleEasyAnimationStep();
        }    
    }
}

public void StartEasyAnimation()
{
    m_SnapshotInEasyAnimation.clear();
    int nCount = m_Bubbles.size();
    int n;
    for(int i = 0; i < nCount; ++i)
    {
        n = ((BubbleObject)m_Bubbles.get(i)).m_nCurrentLocationIndex;
        IndexObject index = new IndexObject(n);
        m_SnapshotInEasyAnimation.add(index);
    }
    int nCount1 = m_Bubbles.size();
    int nCount2 = m_IndexListForReset.size();
    if(nCount1 == nCount2 && 0 < nCount1)
    {
        for(int i = 0; i < nCount1; ++i)
        {
            IndexObject pIndex = (IndexObject)m_IndexListForReset.get(i);
            ((BubbleObject)m_Bubbles.get(i)).m_nCurrentLocationIndex = pIndex.GetIndex();
        }
    }
    this.CleanTouchState();
    this.CheckBubbleState();
    m_nEasyAnimationStep = m_EasySuite.GetSteps()-1;
    m_fOffset = 0;
    this.StartEasyAnimationStep();
    m_bEasyAnimation = true;
    m_bNeedReset = true;
    m_nLEDFlashStep = 0;
}

public void StopEasyAnimation()
{
    m_fOffset = 0;
    m_bEasyAnimation = false;
    this.CleanTouchState();
    this.CheckBubbleState();
}

public void AddUndoData()
{
    if(m_Motion != enBubbleMotion.BUBBLE_MOTION_NONE && m_Direction != enMotionDirection.MOTION_DIRECTION_NONE && 0 <= m_nTouchedCellIndex)
    {    
        int nIndex = 0;
        nIndex = this.GetUndoLocationInfo();
        if(nIndex < 0)
            return;

        CUndoCommand pUndo = new CUndoCommand();
        pUndo.m_Motion = m_Motion;
        pUndo.m_Direction = m_Direction;
        pUndo.m_PositionIndex = nIndex;
        m_UndoCommandList.add(pUndo);
    }    
}

public void ShiftBubbles()
{
    GameConfiguration.IncrementPlayStep();
 //   [GUIEventLoop SendEvent:GUIID_EVENT_PLAYSTEPCHANGE eventSender:self];
}

public void MoveDestinationBubbles(Boolean bSendMesg)
{
    this.ShiftBubbles();
    if(bSendMesg)
    {    
        GameConfiguration.IncrementPlayStep();
 //       [GUIEventLoop SendEvent:GUIID_EVENT_PLAYSTEPCHANGE eventSender:self];
    }    
}

public void CleanTouchState()
{
    m_fOffset = 0;
    m_ptTouchPoint.x = -1;
    m_ptTouchPoint.y = -1;
    m_nTouchedCellIndex = -1;
    m_Motion = enBubbleMotion.BUBBLE_MOTION_NONE;
    m_Direction = enMotionDirection.MOTION_DIRECTION_NONE;
    m_ptArrowStart.x = 0;
    m_ptArrowStart.y = 0;
    m_ptArrowEnd.x = 0;
    m_ptArrowEnd.y = 0;
    m_ptArrowChange.x = 0;
    m_ptArrowChange.y = 0;
    m_ptArrowPosition.x = 0;
    m_ptArrowPosition.y = 0;
}

public int GetBubbleCurrentLocationIndex(int nDestIndex)
{
    int nRet = -1;
    
    int nCount = m_Bubbles.size();
    if(0 <= nDestIndex && nDestIndex < nCount)
    {
        BubbleObject pb = (BubbleObject)m_Bubbles.get(nDestIndex);
        nRet = pb.m_nCurrentLocationIndex;
    }
    
    return nRet;
}

public int GetBubbleDestinationIndex(int nCurrentLocationIndex)
{
    int nRet = -1;
    
    int nCount = m_Bubbles.size();
    if(0 <= nCurrentLocationIndex && nCurrentLocationIndex < nCount)
    {
        for(int i = 0; i < nCount; ++i)
        {    
            BubbleObject pb = (BubbleObject)m_Bubbles.get(i);
            if(pb.m_nCurrentLocationIndex == nCurrentLocationIndex)
            {
                return i;
            }
        }    
    }
    
    return nRet;
}

public int GetBubbleCurrentLocationIndexByLabelValue(int nLable)
{
    int nRet = -1;
    
    int nCount = m_Bubbles.size();
    for(int i = 0; i < nCount; ++i)
    {    
        BubbleObject pb = (BubbleObject)m_Bubbles.get(i);
        if(pb.GetLabelValue() == nLable)
        {
            return pb.m_nCurrentLocationIndex;
        }
    }
    
    return nRet;
    
}

public int GetBubbleDestinationIndexByLabelValue(int nLable)
{
    int nRet = -1;
    
    int nCount = m_Bubbles.size();
    for(int i = 0; i < nCount; ++i)
    {    
        BubbleObject pb = (BubbleObject)m_Bubbles.get(i);
        if(pb.GetLabelValue() == nLable)
        {
            return i;
        }
    }
    
    return nRet;
    
}


public PointF GetTemplateCenter()
{
    float x = m_IconBound.left + m_IconBound.width()*0.5f;
    float y = m_IconBound.top + m_IconBound.height()*0.5f;
    PointF pt = new PointF(x, y);
    return pt;
}

public PointF GetGridCenter()
{
    PointF pt = null;
    if(m_bIconTemplate)
        pt = this.GetTemplateCenter();
    else
        pt = CGameLayout.GetGridCenter();
    
    return pt;
}

public float GetGridMaxSize(float fMaxSize)
{
    float fRet = 0.0f;
    if(m_bIconTemplate)
        fRet = Math.min(m_IconBound.width(), m_IconBound.height());
    else
        fRet = CGameLayout.GetGridMaxSize(fMaxSize);
    return fRet;
}

public void CreateAsTemplate(PointF size, enGridLayout enLayout, int nEdge)
{
    m_bIconTemplate = true;
    m_IconBound = new RectF(0, 0, size.x, size.y);
    m_Bubbles = new ArrayList<BubbleObject>();//[[[NSMutableArray alloc] init] retain];
    m_Cells = new ArrayList<GridCell>();//[[[NSMutableArray alloc] init] retain];
    this.SetGridLayout(enLayout);
    this.SetBubbleUnit(nEdge);
    this.InitializeGrid();
    
	/*
    self = [super init];
    if(self)
    {   
        m_bIconTemplate = YES;
        m_IconBound = CGRectMake(0, 0, size.width, size.height);
        m_Bubbles = [[[NSMutableArray alloc] init] retain];
        m_Cells = [[[NSMutableArray alloc] init] retain];
        [self SetGridLayout:enLayout];
        [self SetBubbleUnit:nEdge];
        [self InitializeGrid];
    }
    return self;
    */
}

public void DrawSampleLayout(Canvas context)
{
    int nCount = m_Bubbles.size();
    if(0 < nCount)
    {
        RectF rect;
        float fRad = m_fBubbleSize/2.5f; ///4.0;
        if(ApplicationConfigure.iPhoneDevice())
            fRad = m_fBubbleSize/3.0f;
        
        for(int i = 0; i < nCount; ++i)
        {
            int nLabel1 = i+1;
            int index1 = this.GetBubbleDestinationIndexByLabelValue(nLabel1);
            BubbleObject pb = (BubbleObject)m_Bubbles.get(index1);
            if(!pb.equals(null))
            {
                PointF pt = this.GetBubbleCenter(pb.m_nCurrentLocationIndex);
                if((i+1) < nCount)
                {
                    int nLabel2 = nLabel1+1;
                    int index2 = this.GetBubbleDestinationIndexByLabelValue(nLabel2);
                    BubbleObject pb2 = (BubbleObject)m_Bubbles.get(index2);
                    if(!pb2.equals(null))
                    {
                        PointF pt2 = this.GetBubbleCenter(pb2.m_nCurrentLocationIndex);
                        
                        context.save();
//                        CGContextSaveGState(context);
//                        CGContextSetLineWidth(context, 3);
//                        CGContextSetRGBStrokeColor(context, 1, 1, 0.5, 1);
                        
                        
                        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                        mPaint.setStrokeWidth(3);
                        mPaint.setColor(0xffff80ff);
                     
                        ///
//                        CGColorSpaceRef		shadowClrSpace;
  //                      CGColorRef			shadowClrs;
    //                    CGSize				shadowSize;
      //                  shadowClrSpace = CGColorSpaceCreateDeviceRGB();
        //                shadowSize = CGSizeMake(5, 5);
          //              float clrvals[] = {0.1, 0.1, 0.1, 0.9};
            //            shadowClrs = CGColorCreate(shadowClrSpace, clrvals);
              //          CGContextSetShadowWithColor(context, shadowSize, 6.0, shadowClrs);
                        ///
           //             DrawLine1(context, pt, pt2);
                        context.drawLine(pt.x, pt.y, pt2.x, pt2.y, mPaint);
                        
 //                       CGContextRestoreGState(context);
                     
//                        CGColorSpaceRelease(shadowClrSpace);
//                        CGColorRelease(shadowClrs);
                       
                    }
                }
                
                if(0 <= pt.x && 0 <= pt.y)
                {
                    rect = new RectF(pt.x-fRad, pt.y-fRad, pt.x-fRad + fRad*2.0f, pt.y-fRad + fRad*2.0f);
                    pb.DrawBubble(context, rect);
                }
            }
        }
    }
}

public void Undo()
{
    if(this.IsWinAnimation())
        return;
    
    int nCount = m_UndoCommandList.size();
    if(0 < nCount)
    {
        CUndoCommand pUndo = (CUndoCommand)m_UndoCommandList.get(nCount-1);
        if (!pUndo.equals(null))
        {
            this.ExceuteUndoCommand(pUndo.m_Motion, pUndo.m_Direction, pUndo.m_PositionIndex);
            m_UndoCommandList.remove(m_UndoCommandList.size() - 1);
            GameConfiguration.DecrementPlayStep();
//            [GUIEventLoop SendEvent:GUIID_EVENT_PLAYSTEPCHANGE eventSender:self];
        }
    }
}

public void Reset()
{
    if(this.IsWinAnimation())
        return;
    
    int nCount1 = m_Bubbles.size();
    int nCount2 = m_IndexListForReset.size();
    if(nCount1 == nCount2 && 0 < nCount1)
    {
        for(int i = 0; i < nCount1; ++i)
        {
            IndexObject pIndex = (IndexObject)m_IndexListForReset.get(i);
            ((BubbleObject)m_Bubbles.get(i)).m_nCurrentLocationIndex = pIndex.GetIndex();
        }
    }
    m_UndoCommandList.clear();
    GameConfiguration.CleanPlaySteps();
 //   [GUIEventLoop SendEvent:GUIID_EVENT_PLAYSTEPCHANGE eventSender:self];
}

public static IPuzzleGrid CreateGrid(enGridType enType, enGridLayout enLayout, int nEdge)
{
    IPuzzleGrid pRet = null;
    
    switch(enType)
    {
        case PUZZLE_GRID_TRIANDLE:
            pRet = new TriangleGrid();
            break;
        case PUZZLE_GRID_SQUARE:
            pRet = new SquareGrid();
            break;
        case PUZZLE_GRID_DIAMOND:
            pRet = new DiamondGrid();
            break;
        case PUZZLE_GRID_HEXAGON:
            pRet = new HexagonGrid();
            break;
    }
    
    if(pRet != null)
    {
        pRet.SetGridLayout(enLayout);
        pRet.SetBubbleUnit(nEdge);
        pRet.InitializeGrid();
    }
    
    return pRet;
}

public static int GetTriangleGridFirstIndexAtRow(int nRow)
{
    int nRet = (nRow+1)*nRow/2;
    return nRet;
}

public static int GetTriangleGridLastIndexAtRow(int nRow)
{
    int nRet = CPuzzleGrid.GetTriangleGridFirstIndexAtRow(nRow+1) - 1;
    return nRet;
}

public static void DrawSampleGrid(Canvas context, RectF rect, enGridType enType, int nEdge)
{
    switch (enType) 
    {
        case PUZZLE_GRID_TRIANDLE:
            TriangleGrid.DrawSample(context, rect, nEdge);
            break;
        case PUZZLE_GRID_SQUARE:
            SquareGrid.DrawSample(context, rect, nEdge);
            break;
        case PUZZLE_GRID_DIAMOND:
            DiamondGrid.DrawSample(context, rect, nEdge);
            break;
        case PUZZLE_GRID_HEXAGON:
            HexagonGrid.DrawSample(context, rect, nEdge);
            break;
    }
}

public static Drawable GetDefaultGridImage(enGridType enType, int nEdge)
{	
    float fSize = CGameLayout.GetDefaultIconImageSize();
    RectF rect = new RectF(0, 0, fSize, fSize);
   
    Bitmap bitmap = Bitmap.createBitmap((int)fSize, (int)fSize, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    canvas.save();
//    CGColorSpaceRef colorSpace = CGColorSpaceCreateDeviceRGB();
//	CGContextRef bitmapContext = CGBitmapContextCreate(nil, fSize, fSize, 8, 0, colorSpace, (kCGBitmapByteOrder32Little | kCGImageAlphaPremultipliedFirst));
    
//    CGContextSaveGState(bitmapContext);
	CPuzzleGrid.DrawSampleGrid(canvas, rect, enType, nEdge);
	
	canvas.restore();
	
//    CGContextRestoreGState(bitmapContext);
//	CGImageRef retImage = CGBitmapContextCreateImage(bitmapContext);
//	CGContextRelease(bitmapContext);
//	CGColorSpaceRelease(colorSpace);
//	return retImage;
	
	return new BitmapDrawable(bitmap);
}

public static void DrawGridLayout(Canvas context, PointF size, enGridType enType, enGridLayout enLayout, int nEdge)
{
    CPuzzleGrid pTemp = null;
    switch (enType) 
    {
        case PUZZLE_GRID_TRIANDLE:
            pTemp = new TriangleGrid();//[[[TriangleGrid alloc] CreateAsTemplate:size withLayout:enLayout withEdge:nEdge] autorelease];
            break;
        case PUZZLE_GRID_SQUARE:
            pTemp = new SquareGrid();//[[[SquareGrid alloc] CreateAsTemplate:size withLayout:enLayout withEdge:nEdge] autorelease];
            break;
        case PUZZLE_GRID_DIAMOND:
            pTemp = new DiamondGrid();//[[[DiamondGrid alloc] CreateAsTemplate:size withLayout:enLayout withEdge:nEdge] autorelease];
            break;
        case PUZZLE_GRID_HEXAGON:
            pTemp = new HexagonGrid();//[[[HexagonGrid alloc] CreateAsTemplate:size withLayout:enLayout withEdge:nEdge] autorelease];
            break;
    }
    if(!pTemp.equals(null))
    {
    	pTemp.CreateAsTemplate(size, enLayout, nEdge);
        pTemp.DrawSampleLayout(context);
    }
}

public Drawable GetDefaultLayoutImage( enGridType enType, enGridLayout enLayout, int nEdge)
{
    float fSize = CGameLayout.GetDefaultIconImageSize();
    PointF size = new PointF(fSize, fSize);
    
    Bitmap bitmap = Bitmap.createBitmap((int)fSize, (int)fSize, Bitmap.Config.ARGB_8888);
	Canvas canvas = new Canvas(bitmap);
	
	canvas.save();
	CPuzzleGrid.DrawGridLayout(canvas, size, enType, enLayout, nEdge);
	canvas.restore();

	
	return new BitmapDrawable(bitmap);
}

public static int GetDiamonGridBubbleNumber(int nEdge)
{
    int nRet = nEdge*nEdge;
    return nRet;
}

public static int GetHexagonGridBubbleNumber(int nEdge)
{
    int nRet = -1;
    if(0 < nEdge)
    {
        nRet = nEdge*nEdge + nEdge*(nEdge-1) + (nEdge-1)*(nEdge-1);
    }
    return nRet;
}
   
}
