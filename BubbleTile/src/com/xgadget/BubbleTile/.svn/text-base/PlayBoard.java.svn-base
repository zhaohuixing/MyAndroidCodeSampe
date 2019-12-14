package com.xgadget.BubbleTile;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Rect;
import android.graphics.PointF;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.View;

public class PlayBoard {
 //   Bitmap					m_BoardImage;
    PlayBoardController		m_GameController;
    Boolean                 m_bAnimation;

    View					m_parent;
/*
public static Bitmap CreateBoardImage()
{
    Bitmap pFrameImage = CResourceHelper.GetBitmap(R.drawable.woodframe);
    Bitmap pBaseImage = CResourceHelper.GetBitmap(R.drawable.woodbase);
    float fFrameWidth = pFrameImage.getWidth();
    float fFrameHeight = pFrameImage.getHeight();
    float fBaseWidth = pBaseImage.getWidth();
    float fBaseHeight = pBaseImage.getHeight();   
   
    float cx = fFrameWidth*0.5f;
    float cy = fFrameHeight*0.5f;
    //RectF baseRect = new RectF(0.0f , 0.0f, fBaseWidth, fBaseHeight);//
    RectF baseRect = new RectF(cx-fBaseWidth*0.5f , cy-fBaseHeight*0.5f, cx-fBaseWidth*0.5f+fBaseWidth, cy-fBaseHeight*0.5f +fBaseHeight);
    RectF frameRect = new RectF(0.0f, 0.0f, fFrameWidth, fFrameHeight);
    
//    CGColorSpaceRef colorSpace = CGColorSpaceCreateDeviceRGB();
//	CGContextRef bitmapContext = CGBitmapContextCreate(nil, fFrameWidth, fFrameHeight, 8, 0, colorSpace, (kCGBitmapByteOrder32Little | kCGImageAlphaPremultipliedFirst));
  
    Bitmap bitmap = Bitmap.createBitmap((int)fFrameWidth, (int)fFrameHeight, Bitmap.Config.ARGB_8888);
    Canvas bitmapContext = new Canvas(bitmap);
    
//    CGContextSaveGState(bitmapContext);
    bitmapContext.save();
    
//    CGContextDrawImage(bitmapContext, baseRect, pBaseImage);
//    Rect src = new Rect(0,0,pBaseImage.getWidth(), pBaseImage.getWidth());
    bitmapContext.drawBitmap(pBaseImage, null, baseRect, null);

    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    mPaint.setAntiAlias(true);
    mPaint.setStrokeWidth(2);
    mPaint.setStrokeCap(Paint.Cap.ROUND);

    
//    CGContextSetLineWidth(bitmapContext, 2);
//    CGContextSetRGBStrokeColor(bitmapContext, 0.3, 0.1, 0.1, 0.8);
    mPaint.setARGB((int)(255*0.3), (int)(255*0.1), (int)(255*0.1), (int)(255*0.8));
    PointF pt1 = new PointF(cx-fBaseWidth*0.5f+1, cy+fBaseHeight*0.5f-1);
    PointF pt2 = new PointF(cx+fBaseWidth*0.5f-1, cy+fBaseHeight*0.5f-1);
    PointF pt3 = new PointF(cx+fBaseWidth*0.5f-1, cy-fBaseHeight*0.5f+1);
//    DrawLine1(bitmapContext, pt1, pt2);
//    DrawLine1(bitmapContext, pt2, pt3);
    bitmapContext.drawLine(pt1.x, pt1.y, pt2.x, pt2.y, mPaint);
    bitmapContext.drawLine(pt2.x, pt2.y, pt3.x, pt3.y, mPaint);
    
//    CGColorSpaceRef		shadowClrSpace;
 //   CGColorRef			shadowClrs;
 //   Point				shadowSize = null;
 //   shadowClrSpace = CGColorSpaceCreateDeviceRGB();
 //   shadowSize = new Point(5, 5);
  //  float clrvals[] = {0.3f, 0.2f, 0.2f, 0.8f};
  //  shadowClrs = CGColorCreate(shadowClrSpace, clrvals);
  //  CGContextSetShadowWithColor(bitmapContext, shadowSize, 5, shadowClrs);
    
    
//	CGContextDrawImage(bitmapContext, frameRect, pFrameImage);
//    Rect srcFrame = new Rect(0,0,pFrameImage.getWidth(), pFrameImage.getHeight());
    bitmapContext.drawBitmap(pFrameImage, null, frameRect, null);
    
    
//    CGContextSetRGBStrokeColor(bitmapContext, 0.2, 0.1, 0.1, 0.5);
    mPaint.setARGB((int)(255*0.3), (int)(255*0.2), (int)(255*0.1), (int)(255*0.5));
    pt1 = new PointF(1, 1);
    pt2 = new PointF(fFrameWidth-1, 1);
    pt3 = new PointF(fFrameWidth-1, fFrameHeight-1);
    PointF pt4 = new PointF(1, fFrameHeight-1);
//    DrawLine1(bitmapContext, pt1, pt2);
 //   DrawLine1(bitmapContext, pt2, pt3);
 //   DrawLine1(bitmapContext, pt3, pt4);
 //   DrawLine1(bitmapContext, pt4, pt1);
    
    bitmapContext.drawLine(pt1.x, pt1.y, pt2.x, pt2.y, mPaint);
    bitmapContext.drawLine(pt2.x, pt2.y, pt3.x, pt3.y, mPaint);
    bitmapContext.drawLine(pt3.x, pt3.y, pt4.x, pt4.y, mPaint);
    bitmapContext.drawLine(pt4.x, pt4.y, pt1.x, pt1.y, mPaint);
    
//	CGColorSpaceRelease(shadowClrSpace);
//	CGColorRelease(shadowClrs);
 //   CGContextRestoreGState(bitmapContext);
    
//	CGImageRef retImage = CGBitmapContextCreateImage(bitmapContext);
 //   CGImageRelease(pFrameImage);
//    CGImageRelease(pBaseImage);
//	CGContextRelease(bitmapContext);
//	CGColorSpaceRelease(colorSpace);

  //  return retImage;
    return bitmap;
}
*/
public void LoadDefautConfigure()
{

	GameScore.InitializeGameScore();
    GameConfiguration.SetGridType(enGridType.values()[GameScore.GetDefaultType()]);
    GameConfiguration.SetGridLayout(enGridLayout.values()[GameScore.GetDefaultLayout()]);
    GameConfiguration.SetBubbleUnit(GameScore.GetDefaultEdge());
    GameConfiguration.SetBubbleType(enBubbleType.values()[GameScore.GetDefaultBubble()]);
}

public PlayBoard(View parent)//RectF frame)
{
	m_parent = parent;
        // Initialization code
        this.LoadDefautConfigure();
//        this..backgroundColor = [UIColor clearColor];
//        m_BoardImage = PlayBoard.CreateBoardImage();//[ImageLoader LoadImageWithName:@"woodtex.png"];
        m_GameController = new PlayBoardController();
        m_bAnimation = false;
        BubbleObject.CreateQmark();
}

public Boolean InAnimation()
{
    return m_bAnimation;
}

public Boolean IsWinAnimation()
{
    return m_GameController.IsWinAnimation();
}

public Boolean IsEasyAnimation()
{
    return m_GameController.IsEasyAnimation();
}

public void StartEasyAnimation()
{
    m_GameController.StartEasyAnimation();
//    this.setNeedsDisplay();
}

public void drawBoard(Canvas context, RectF rect)
{
//    CGContextSaveGState(context);
	context.save();
	
//    RectF newRt = new RectF(rect.left, rect.top, rect.width()-6, rect.height()-6);
//    CGColorSpaceRef		shadowClrSpace;
 //   CGColorRef			shadowClrs;
 //   CGSize				shadowSize;
//    shadowClrSpace = CGColorSpaceCreateDeviceRGB();
//    shadowSize = CGSizeMake(2.5, 2.5);
 //   float clrvals[] = {0.2, 0.1, 0.1, 1.0};
//    shadowClrs = CGColorCreate(shadowClrSpace, clrvals);
//    CGContextSetShadowWithColor(context, shadowSize, 6, shadowClrs);
//    CGContextDrawImage(context, newRt, m_BoardImage);

//    Rect src = new Rect(0, 0, m_BoardImage.getWidth(), m_BoardImage.getHeight());
 //   context.drawBitmap(m_BoardImage, src, newRt, null);
    
    context.restore();
//	CGColorSpaceRelease(shadowClrSpace);
//	CGColorRelease(shadowClrs);
 //   CGContextRestoreGState(context);
}

public void drawPuzzle(Canvas context)
{
    m_GameController.DrawGame(context);
}


// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
public void drawRect(Canvas context,RectF rect)
{
    // Drawing code
//	CGContextRef context = UIGraphicsGetCurrentContext();
	context.save();
//	CGContextSaveGState(context);
    
//    this.drawBoard(context, rect);
	
    this.drawPuzzle(context);
//	CGContextRestoreGState(context);
    context.restore();
}


//public void dealloc()
//{
//    CGImageRelease(m_BoardImage);
 //   [m_GameController release];
  //  [BubbleObject CleanQmark];
   // [super dealloc];
//}

public void UpdateGameViewLayout()
{
    if(m_GameController != null)
        m_GameController.UpdatePuzzleLayout();
    
//    [self setNeedsDisplay];
}

public void OnNewGameBegin()
{
    m_bAnimation = false;
//    this.setNeedsDisplay();
}

public void OnOldGameEnd()//(id)sender
{
    enGridType enType = GameConfiguration.GetGridType();
    enGridLayout enLayout = GameConfiguration.GetGridLayout();
    int nEdge = GameConfiguration.GetGridBubbleUnit(enType);
    m_GameController.InitializePuzzle(enType, enLayout, nEdge);
    GameConfiguration.CleanDirty();
    //this.setNeedsDisplay();
    
//    this.hidden = false;
//    Canvas context = UIGraphicsGetCurrentContext();
    
//    [UIView beginAnimations:nil context:context];
 //   [UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
 //   [UIView setAnimationDuration:1.0];
 //  [UIView setAnimationDelegate:self];
 //   [UIView setAnimationDidStopSelector:@selector(OnNewGameBegin:)];
  //  [UIView setAnimationTransition:UIViewAnimationTransitionFlipFromLeft forView:self cache:YES];
  //  [UIView commitAnimations];
    
}

public void StartNewGame()
{
    if(ApplicationConfigure.GetAdViewsState() == true )//&& [UIDevice networkAvailable] == NO)
    {
        String str = StringFactory.GetString_NetworkWarn();
 //       [ModalAlert say:str];
    }
        
    m_bAnimation = true;
 //   self.hidden = true;

//    Canvas context = this.UIGraphicsGetCurrentContext();

//    [UIView beginAnimations:nil context:context];
 //   [UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
 //   [UIView setAnimationDuration:1.0];
 //   [UIView setAnimationDelegate:self];
 //   [UIView setAnimationDidStopSelector:@selector(OnOldGameEnd:)];
 //   [UIView setAnimationTransition:UIViewAnimationTransitionFlipFromLeft forView:self cache:YES];
 //   [UIView commitAnimations];
}

public void UndoGame()
{
    m_GameController.UndoGame();
//    [self setNeedsDisplay];
}

public void ResetGame()
{
    m_GameController.ResetGame();
//    [self setNeedsDisplay];
}

public void OnTimerEvent()
{
    if(m_GameController != null)
    {    
        if(m_GameController.OnTimerEvent())
 ;//           this.setNeedsDisplay();
    }    
}


public void touchesBegan(ArrayList<PointF> points)
{
    if(this.IsEasyAnimation())
        return;
    
//	NSArray *points = [touches allObjects];
	PointF pt;
	for(int i = 0; i < points.size(); ++i)
	{
		pt = points.get(i);
        if(m_GameController != null && m_GameController.OnTouchBegin(pt))
        {
//            [self setNeedsDisplay];
        	m_parent.invalidate();
            return;
        }    
    }    
}	


public void touchesMoved(ArrayList<PointF> points)
{
    if(this.IsEasyAnimation())
        return;
	
    //NSArray *points = [touches allObjects];
	PointF pt;
	for(int i = 0; i < points.size(); ++i)
	{
		pt = points.get(i);
        if(m_GameController != null && m_GameController.OnTouchMove(pt))
        {
            //this.setNeedsDisplay];
        	m_parent.invalidate();
            return;
        }    
    }    
}	

public void touchesEnded(ArrayList<PointF> points)
{
    if(this.IsEasyAnimation())
        return;
	
    //NSArray *points = [touches allObjects];
	PointF pt;
	for(int i = 0; i < points.size(); ++i)
	{
		pt = points.get(i);
        if(m_GameController!= null && m_GameController.OnTouchEnd(pt))
        {
            //[self setNeedsDisplay];
        	m_parent.invalidate();
            return;
        }    
    }
	m_parent.invalidate();
    //[self setNeedsDisplay];
}	


public void touchesCancelled(ArrayList<PointF> points)
{
    this.touchesEnded(points);
}	

    
public void CheckBubbleState()
{
    if(m_GameController != null)
    {    
        m_GameController.CheckBubbleState();
//        [self setNeedsDisplay];
    }    
}

public void CleanBubbleCheckState()
{
    if(m_GameController!=null)
    {    
        m_GameController.CleanBubbleCheckState();
  //      this.setNeedsDisplay();
    }    
}

/*
-(void)TestSuite
{
#ifdef __RUN_TESTSUITE__
    if(m_GameController)
        [m_GameController TestSuite];
    [self setNeedsDisplay];
#endif    
}
*/   

}
