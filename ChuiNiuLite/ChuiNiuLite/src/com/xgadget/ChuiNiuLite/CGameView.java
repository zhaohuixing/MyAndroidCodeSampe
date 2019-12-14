package com.xgadget.ChuiNiuLite;

import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_core.cvClearMemStorage;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSeqElem;
import static com.googlecode.javacv.cpp.opencv_core.cvLoad;
import static com.googlecode.javacv.cpp.opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING;
import static com.googlecode.javacv.cpp.opencv_objdetect.cvHaarDetectObjects;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import android.content.Context;
//import android.content.res.Resources;
import android.graphics.Canvas;
import android.media.FaceDetector;
import android.os.Bundle;
import android.os.SystemClock;
//import android.os.Handler;
//import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
//import android.view.View;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.view.View;
//import android.view.ViewGroup.LayoutParams;

import com.googlecode.javacpp.Loader;
import com.googlecode.javacv.cpp.opencv_objdetect;
import com.googlecode.javacv.cpp.opencv_core.CvMemStorage;
import com.googlecode.javacv.cpp.opencv_core.CvRect;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_objdetect.CvHaarClassifierCascade;
import com.xgadget.facegesture.CXFGCameraHelper;
//import com.mobclix.android.sdk.MobclixAdView;
//import com.mobclix.android.sdk.MobclixAdViewListener;
//import com.mobclix.android.sdk.MobclixIABRectangleMAdView;
//import com.mobclix.android.sdk.MobclixMMABannerXLAdView;
import com.xgadget.uimodule.BasicLayoutView;

public class CGameView extends BasicLayoutView implements PreviewCallback /*implements IGameEventListener*/
{
	public CGameScene 		m_Game;
	boolean					m_bInitialized;
    Paint 	m_BkGndGreenPaint;
    Paint 	m_BkGndBluePaint;
	
    //Camera preview view callback process parameters
    
	public static final int SUBSAMPLING_FACTOR = 4;

    private IplImage 				m_GrayImage;
    private CvHaarClassifierCascade m_Classifier;
    private CvMemStorage 			m_Storage;
    private CvSeq 					m_Faces;
    private int						m_FGCenterX;
    private int						m_FGCenterY;
    private float					m_fFGXFactor;
    private float					m_fFGYFactor;
    
    public static final int			FGX_EVENT_STATUS_LEFT = 1;
    public static final int			FGX_EVENT_STATUS_CENTER = 0;
    public static final int			FGX_EVENT_STATUS_RIGHT = -1;
    private int 					m_FGEventState = FGX_EVENT_STATUS_CENTER;
    
    private CFGPreviewFrameWindow	m_FGPreview;
    
	private void Initialize(Context context)
	{
		m_bInitialized = false;
		m_BkGndGreenPaint = new Paint();
		m_BkGndGreenPaint.setAntiAlias(true);
		m_BkGndGreenPaint.setARGB(255, 0, 180, 0);
		
		m_BkGndBluePaint  = new Paint();
		m_BkGndBluePaint.setAntiAlias(true);
		m_BkGndBluePaint.setARGB(255, 128, 128, 254);
		
		//m_bShowHelp = false;
		
		setFocusable(true); // make sure we get key events
		requestFocus();
		m_Game = new CGameScene(context);
		m_Game.resetGameScene();
		this.m_LayoutContainer = null;
	    m_fFGXFactor = 0.0f;
	    m_fFGYFactor = 0.0f;
		try
		{
			InitializePreviewCallback(context);
		}
		catch(IOException e)
		{
		}
		
	}
	
	private void InitializePreviewCallback(Context context) throws IOException 
	{
        // Load the classifier file from Java resources.
        File classifierFile = Loader.extractResource(context.getClass(), "/com/xgadget/facegesture/haarcascade_frontalface_alt.xml", context.getCacheDir(), "classifier", ".xml");
        if (classifierFile == null || classifierFile.length() <= 0) 
        {
            throw new IOException("Could not extract the classifier file from Java resource.");
        }

        // Preload the opencv_objdetect module to work around a known bug.
        Loader.load(opencv_objdetect.class);
        m_Classifier = new CvHaarClassifierCascade(cvLoad(classifierFile.getAbsolutePath()));
        classifierFile.delete();
        if (m_Classifier.isNull()) 
        {
            throw new IOException("Could not load the classifier file.");
        }
        m_Storage = CvMemStorage.create();
        m_FGCenterX = -1;
        m_FGCenterY = -1;
        
	}
	
	public void SetupFGPreviewObject()
	{
        m_FGPreview = (CFGPreviewFrameWindow)CFCActivity.m_ApplicationController.m_CurrentActivity.findViewById(R.id.fgpreview);
		//if(this.m_LayoutContainer != null && this.m_FGPreview != null)
		//{
		//	//this.m_LayoutContainer.bringChildToFront(this);
		//}
	}
	
	public CGameView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		Initialize(context);
	}
	
	public CGameView(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}


	public CGameView(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}
	
	
	private void DrawBackground(Canvas canvas)
	{
		/*Rect rect = CGameLayout.GetDeviceScreenRect(); ///CGameLayout.GetDeviceScreenRect();//new Rect(0, 0, (int)CGameLayout.GetGameClientDeviceWidth(), (int)CGameLayout.GetGameClientDeviceHeight());
		if(m_BkGndGreenPaint != null)
		{
        	canvas.drawRect(rect, m_BkGndGreenPaint);
		}	*/
		
		if(CGameLayout.m_bHasAdBanners == true)
		{	
			int nBarHeight = CGameLayout.GetAdBannerHeight();
			int nHeight = (int)CGameLayout.GetDeviceScreenHeight();
			int nWidth = (int)CGameLayout.GetDeviceScreenWidth();
			Rect rect = new Rect();
			rect.left = 0;
			rect.top = nHeight - nBarHeight;
			rect.right = nWidth;
			rect.bottom = nHeight;  		//The real window screen height;
			Paint paint = CImageLoader.GetMudPaint();
			canvas.drawRect(rect, paint);
		}
	}
	
	private void DrawBlueBackground(Canvas canvas)
	{
		Rect rect = CGameLayout.GetDeviceScreenRect(); //CGameLayout.GetGameSceneDeviceRect();//new Rect(0, 0, (int)CGameLayout.GetGameClientDeviceWidth(), (int)CGameLayout.GetGameClientDeviceHeight());
		if(m_BkGndBluePaint != null)
		{
        	canvas.drawRect(rect, m_BkGndBluePaint);
		}	
	}

	private void DrawPaperBackground(Canvas canvas)
	{
		Paint paint = CImageLoader.GetPaperGridPaint();
		Rect rect = CGameLayout.GetDeviceScreenRect(); //CGameLayout.GetGameSceneDeviceRect();//new Rect(0, 0, (int)CGameLayout.GetGameClientDeviceWidth(), (int)CGameLayout.GetGameClientDeviceHeight());
		if(paint != null)
		{
        	canvas.drawRect(rect, paint);
		}	
		
	}
	
	private void DrawLoseStatePaint(Canvas canvas)
	{
	    Paint 	m_BkGndPaint = new Paint();
		m_BkGndPaint.setAntiAlias(true);
		m_BkGndPaint.setARGB(128, 0, 0, 0);
		Rect rect = CGameLayout.GetGameSceneDeviceRect();//new Rect(0, 0, (int)CGameLayout.GetGameClientDeviceWidth(), (int)CGameLayout.GetGameClientDeviceHeight());
		if(m_BkGndPaint != null)
		{
        	canvas.drawRect(rect, m_BkGndPaint);
		}	
	}
	
	private void DrawNightBackground(Canvas canvas)
	{
		canvas.save();
		
		int fAlpha = 255;
		
		Rect rect = CGameLayout.GetGameSceneDeviceRect();//new Rect(0, 0, (int)CGameLayout.GetGameClientDeviceWidth(), (int)CGameLayout.GetGameClientDeviceHeight());
	    Paint 	paint = CImageLoader.GetStar1Paint();
		if(paint != null)
		{
        	canvas.drawRect(rect, paint);
		}	

	    float miny = rect.height(); 
	    float moonh = miny/10.0f;
	    float moonw = moonh*0.6f; 
	    miny = miny/2.0f;
	    
	    int minx = rect.width()/4; 
	    paint = CImageLoader.GetStar2Paint();
	    Rect rt;
	    
	    for(int i = 0; i < 4; ++i)
	    { 
	    	fAlpha = 128 + (i%3)*52;  // 0.5f+0.2f*(i%3);
	    	paint.setAlpha(fAlpha);
	        int x = rect.left + minx*i;
	        int y = rect.top + (int)(miny*0.1f*(i%2));
	        
	        rt = new Rect(x, y, x+minx*8/10, y + (int)(miny*0.6f));
	       	canvas.drawRect(rt, paint);
	    }
	    if(CConfiguration.getThunderTheme() == false)
	    {	
	    	rt = new Rect(rect.left+minx/2, rect.top+(int)(miny*0.3f), rect.left+minx/2 + (int)moonw, rect.top+(int)(miny*0.3f) + (int)moonh);
	    	Drawable image = CImageLoader.GetMoonImage();
	    	image.setBounds(rt);
	    	image.draw(canvas);
	    } 
	    canvas.restore();
	}
	
	protected void onDraw(Canvas canvas)
	{
		int nType = CConfiguration.GetGameBackground();
		if(nType == CConfiguration.GAME_BACKGROUND_NIGHTSKY)
			DrawNightBackground(canvas);
		else if(nType == CConfiguration.GAME_BACKGROUND_CHECKPATTERN)
			DrawPaperBackground(canvas);
		else 
			DrawBlueBackground(canvas);

		DrawBackground(canvas);
		
		if(m_Game != null)
		{
			m_Game.Draw(canvas);
		}
//		if(CGameScene.IsGameStateResultLose())
//		{
//			DrawLoseStatePaint(canvas);
//		}
//		DrawFacialGestTestInformation(canvas);
	}	

	protected void  onLayout(boolean changed, int left, int top, int right, int bottom)
	{
		super.onLayout(changed, left, top, right, bottom);
		m_Game.UpdateLayout();
		UpdateLayout();
		invalidate();
	}
	
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		m_Game.UpdateLayout();
		UpdateLayout();
		invalidate();
	}
	
	public void PostOnLayoutHandle()
	{
		
	}
	
	public void SetSavedInstanceState(Bundle savedState)
	{
		invalidate();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		boolean bRet = false;
		
		bRet = onGameTouchEvent(event);	
		if(bRet == true)
			invalidate(); 
		return bRet;
	}

	private boolean onGameTouchEvent(MotionEvent event)
	{
		boolean bRet = false;
	
		if(m_Game != null)
		{
			bRet = m_Game.OnTouchEvent(event);
		}
		
		return bRet;
	}
	
	/*
	 * Key-down events.
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent msg) 
	{
		boolean bRet = false;
		
		if(m_Game != null)
		{
			bRet = m_Game.OnKeyDown(keyCode);
		}		
		invalidate(); 
		return bRet;
	}

	/*
	 * Key-up event.
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent msg) 
	{
		boolean bRet = false;
		
		if(m_Game != null)
		{
			bRet = m_Game.OnKeyUp(keyCode);
		}
				
		invalidate(); 
		return bRet;
	}
	

	public void OnTimerEvent()
	{
		boolean bRet = m_Game.OnTimerEvent();
		if(bRet == true)
			invalidate(); 
	}
	
	public void ForceToStartGame()
	{
		if(m_Game != null)
		{
			m_Game.ForceToStartGame();
		}
	}
	
	public void GameStart()
	{
		if(m_Game != null)
		{
			m_Game.startNewGame();
		}
	}
	
	public void GamePause()
	{
		if(m_Game != null)
		{
			m_Game.pauseGame();
		}
	}
	
	public void GameResume()
	{
		if(m_Game != null)
		{
			m_Game.resumeGame();
		}
	}
	
	public void ResetGame()
	{
		if(m_Game != null)
		{
			m_Game.resetGameScene();
		}
	}
	
	public void EndGame()
	{
		m_Game.ShutDownGame();
	}
	
	public void SetLevelOne()
	{
		m_Game.SetLevelOne();
	}
	
	public void SetLevelTwo()
	{
		m_Game.SetLevelTwo();
	}
	
	public void SetLevelThree()
	{
		m_Game.SetLevelThree();
	}
	
	public void SetLevelFour()
	{
		m_Game.SetLevelFour();
	}
	
	public void SetSkillOne()
	{
		m_Game.SetSkillOne();
	}
	
	public void SetSkillTwo()
	{
		m_Game.SetSkillTwo();
	}
	
	public void SetSkillThree()
	{
		m_Game.SetSkillThree();
	}
	
	public void ReloadScore()
	{
		m_Game.ReloadScore();
	}
	
	public void UpdateGameSetting()
	{
		m_Game.UpdateGameSetting();
	}


	public void UpdateLayout()
	{
//		m_Application.UpdateADViewLayout();
	}
	
	public int GetLastWinSkill()
	{
		return m_Game.GetLastWinSkill();
	}	

	public int GetLastWinLevel()
	{
		return m_Game.GetLastWinLevel();
	}	
	
	public int GetTotalScore(int nSkill, int nLevel)
	{
		return m_Game.GetTotalScore(nSkill, nLevel);
	}

	public int GetTodayScore(int nSkill, int nLevel)
	{
		return m_Game.GetTodayScore(nSkill, nLevel);
	}

	@Override
	public void onPreviewFrame(byte[] data, Camera camera) 
	{
		// TODO Auto-generated method stub
	    try 
	    {
	        Camera.Size size = camera.getParameters().getPreviewSize();
	        processImage(data, size.width, size.height);
	        camera.addCallbackBuffer(data);
	    } 
	    catch (RuntimeException e) 
	    {
	            // The camera has probably just been released, ignore.
	    }
	}


	protected void processImage(byte[] data, int width, int height) 
	{
	    // First, downsample our image and convert it into a grayscale IplImage
		if(CGameScene.IsGameStatePlay() == false)
			return;
		
		int f = SUBSAMPLING_FACTOR;
	    if (m_GrayImage == null || m_GrayImage.width() != width/f || m_GrayImage.height() != height/f) 
	    {
	      	m_GrayImage = IplImage.create(width/f, height/f, IPL_DEPTH_8U, 1);
	    }
	    int imageWidth  = m_GrayImage.width();
	    int imageHeight = m_GrayImage.height();
	    int dataStride = f*width;
	    int imageStride = m_GrayImage.widthStep();
	    ByteBuffer imageBuffer = m_GrayImage.getByteBuffer();
	    for (int y = 0; y < imageHeight; y++) 
	    {
	    	int dataLine = y*dataStride;
	        int imageLine = y*imageStride;
	        for (int x = 0; x < imageWidth; x++) 
	        {
	        	imageBuffer.put(imageLine + x, data[dataLine + f*x]);
	        }
	    }

	    cvClearMemStorage(m_Storage);
	    m_Faces = cvHaarDetectObjects(m_GrayImage, m_Classifier, m_Storage, 1.1, 3, CV_HAAR_DO_CANNY_PRUNING);
	    FacialGestureEventHandle();
	}
	
	private void FacialGestureEventHandle()
	{
		//long nFGEventTiming = SystemClock.uptimeMillis();//CApplicationController.GetSystemTimerClick();
	    boolean bTracking = false;
		if (m_Faces != null) 
	    {
	        int total = m_Faces.total();
	        if(0 < total)
	        {
	    		bTracking = true;
	        	CvRect r = new CvRect(cvGetSeqElem(m_Faces, 0));
		        int cx = r.x() + r.width()/2;
		        if(m_FGCenterX == -1)
		        {
		        	m_FGCenterX = cx;
	        		m_FGEventState = FGX_EVENT_STATUS_CENTER;
		        }
		        else
		        {	
		        	int ndx = (cx - m_FGCenterX); 
		        	int frameW = m_FGPreview.GetCameraParameterWidth();
		        	float fdx = ((float)ndx)/((float)frameW);
		        	m_fFGXFactor = fdx*1000.0f;//*fTimeFactor;
		        	m_FGCenterX = cx;
		        	if(2.0f <= Math.abs(m_fFGXFactor) && m_FGEventState == FGX_EVENT_STATUS_CENTER)
		        	{
		        		if(0 < m_fFGXFactor)
		        		{
		        			HandleFGHorizontalEvent(true);
		        		}
		        		else if(m_fFGXFactor < 0)
		        		{
		        			HandleFGHorizontalEvent(false);
		        		}
		        	}
		        	else
		        	{
		        		m_FGEventState = FGX_EVENT_STATUS_CENTER;
		        	}
		        }
		        
		        int cy = r.y() + r.height()/2;
		        if(m_FGCenterY == -1)
		        {
		        	m_FGCenterY = cy;
		        }
		        else
		        {	
		        	int frameH = m_FGPreview.GetCameraParameterHeight();
		        	int ndy = (cy - m_FGCenterY); 
		        	float fdy = ((float)ndy)/((float)frameH);
		        	m_fFGYFactor = fdy*1000.0f;//*fTimeFactor;
		        	if(2.0f <= m_fFGYFactor)
		        	{
		        		HandleFGVerticalUpEvent();
		        	}
		        	m_FGCenterY = cy;
		        }	
	        }
	        else
	        {
	        	m_FGCenterX = -1;//m_nFGCameraFrameCenterX;	        	
	        	m_FGCenterY = -1;//m_nFGCameraFrameCenterY;	 
	        	m_FGEventState = FGX_EVENT_STATUS_CENTER;
	        }
	    }
		else
		{
        	m_FGCenterX = -1;//m_nFGCameraFrameCenterX;	        	
        	m_FGCenterY = -1;//m_nFGCameraFrameCenterY;	 
        	m_FGEventState = FGX_EVENT_STATUS_CENTER;
		}
		CConfiguration.SetFGTrackingState(bTracking);
	    HandlePlayerMotionByFacialGesture();
	    m_FGPreview.ForceRedraw();
	    postInvalidate();
	}
	
	private void HandleFGHorizontalEvent(boolean bForward)
	{
		if(bForward == true)
		{
			m_FGEventState = FGX_EVENT_STATUS_LEFT;
			//m_Game.OnKeyDown(KeyEvent.KEYCODE_DPAD_LEFT);
		}
		else
		{
			m_FGEventState = FGX_EVENT_STATUS_RIGHT;
			//m_Game.OnKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT);
		}
		m_Game.OnFGEventX(bForward);
	}
	
	private void HandleFGVerticalUpEvent()
	{
		m_Game.OnKeyDown(KeyEvent.KEYCODE_DPAD_CENTER);
		m_Game.OnFGEventY();
	}
	
	private void HandlePlayerMotionByFacialGesture()
	{
		
	}

	/*
	private void DrawFacialGestTestInformation(Canvas canvas)
	{
		Paint paint = new Paint();
	    paint.setColor(Color.BLACK);
	    paint.setTextSize(40);

	    String s = "Center:(" + Integer.toString(m_FGCenterX) + "," +Integer.toString(m_FGCenterY) +")";    //"FacePreview - This side up.";
    	s += "Factor:(" + Float.toString(m_fFGXFactor) + "," + Float.toString(m_fFGYFactor) +")"; 
	    float textWidth = paint.measureText(s);
	    canvas.drawText(s, (getWidth()-textWidth)/2, 40, paint);
	}*/
	
	public void StartFacialGestureProcess()
	{
	    m_FGCenterX = -1;
	    m_FGCenterY = -1;
		CConfiguration.SetMouthMode(true);
		Camera frontCamera = CXFGCameraHelper.GetFrontCamera();
    	if(frontCamera != null)
    	{	
    		m_FGPreview.setVisibility(View.VISIBLE);
    		this.m_LayoutContainer.bringChildToFront(m_FGPreview);
    		boolean bRet = m_FGPreview.StartFacialGesture(frontCamera, this);
    		int width = this.getWidth();
    		int height = this.getHeight();
    		int nprevieww = m_FGPreview.GetCameraParameterWidth();
    		if(nprevieww == 0)
    			return;
    		int npreviewh = m_FGPreview.GetCameraParameterHeight();
            if(width/5 < nprevieww)
            {
            	int th = npreviewh*(width/5)/nprevieww; 
            	npreviewh = th;
            	nprevieww = width/5;
            }
    		int cw = nprevieww + m_FGPreview.GetEdgeSize()*2;
    		int ch = npreviewh + m_FGPreview.GetEdgeSize()*2;
    		
    		int x = width - cw - m_FGPreview.GetEdgeSize();
    		int y = (height -ch)/2;
    		this.m_LayoutContainer.SetChildNewDemension(m_FGPreview, cw, ch, x, y);
    		
    		CConfiguration.SetFGEnable(bRet);
    		if(bRet == true)
    		{
    			
    		}
    		m_FGEventState = FGX_EVENT_STATUS_CENTER;
    		m_FGPreview.ForceRedraw();
    		this.GameStart();
    	}
    	else
    	{
    		CConfiguration.SetFGEnable(false);
    	}
	}
	
	public void StopFacialGestureProcess()
	{
	    m_FGCenterX = -1;
	    m_FGCenterY = -1;
		if(CConfiguration.IsFGEanbled() == true)
		{	
			m_FGPreview.StopFacialGesture();
    		CConfiguration.SetFGEnable(false);
    		m_FGPreview.invalidate();
		}
		m_FGPreview.ForceRedraw();
		CConfiguration.SetMouthMode(false);
		CFCActivity.m_ApplicationController.m_GameController.HideFGPreviewWindow();
		m_FGPreview.setVisibility(View.GONE);
		invalidate();
	}
	
}
