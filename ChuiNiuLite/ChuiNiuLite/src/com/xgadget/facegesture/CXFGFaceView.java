package com.xgadget.facegesture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.media.FaceDetector;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import static com.googlecode.javacv.cpp.opencv_core.CvMemStorage;
import static com.googlecode.javacv.cpp.opencv_core.CvSeq;
import static com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_objdetect.CvHaarClassifierCascade;

import com.googlecode.javacpp.Loader;
import com.googlecode.javacv.cpp.opencv_objdetect;

import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_objdetect.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;



public class CXFGFaceView extends View implements PreviewCallback 
{
	public static final int SUBSAMPLING_FACTOR = 4;

    private IplImage 				m_GrayImage;
    private CvHaarClassifierCascade m_Classifier;
    private CvMemStorage 			m_Storage;
    private CvSeq 					m_Faces;

	private static final int MAX_FACES = 1;//10;
	
	public Bitmap 							m_FaceBitmap;
	private FaceDetector 					m_FaceDetector;
	private	FaceDetector.Face [] 			m_FaceCharactors;
	private	int								m_CachedImageWidth;
	private	int								m_CachedImageHeight;
	private PointF 							m_FaceCenter;
	private float 							m_EyeDistance;
    
	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public CXFGFaceView(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		try
		{
			Initialize(context);
		}
		catch(IOException e)
		{
			
		}
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public CXFGFaceView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		try
		{
			Initialize(context);
		}
		catch(IOException e)
		{
			
		}
	}

	public CXFGFaceView(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		try
		{
			Initialize(context);
		}
		catch(IOException e)
		{
			
		}
	}

	private void Initialize(Context context) throws IOException 
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
		
		m_FaceBitmap = null;
		m_FaceDetector = null;
		m_FaceCharactors =  new FaceDetector.Face[MAX_FACES];
		m_CachedImageWidth = 0;
		m_CachedImageHeight = 0;
		m_FaceCenter = new PointF();
		m_EyeDistance = 0.0f;
	}

	
	public void onPreviewFrame(final byte[] data, final Camera camera)
	{
	    try 
	    {
	        Camera.Size size = camera.getParameters().getPreviewSize();
	        processImage(data, size.width, size.height);
	        //handleFaceRecogintionByAndroidAPI(data, size.width, size.height);
	        
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
	    postInvalidate();
	}

    private void handleFaceRecogintionByAndroidAPI(byte[] data, int width, int height)
    {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        m_FaceBitmap = bitmap.copy(Bitmap.Config.RGB_565, true); 
		bitmap.recycle();

		FaceGestureRecognitionByAndroidAPI(width, height);
    }
	
	private void FaceGestureRecognitionByAndroidAPI(int width, int height)
	{
    	if(m_FaceBitmap != null)
    	{
    		int mFaceWidth = width;
    		int mFaceHeight = height;  
    		int count = 0;
  
    		if(m_CachedImageWidth != mFaceWidth || m_CachedImageHeight != mFaceHeight || m_FaceDetector == null)
    		{
        		try 
            	{
        			m_CachedImageWidth = mFaceWidth;
        			m_CachedImageHeight = mFaceHeight;
        			m_FaceDetector = new FaceDetector(mFaceWidth, mFaceHeight, MAX_FACES);  
        			
            	} 
            	catch (Exception e) 
            	{
            		Log.e("CFaceGestureHandler.FaceGestureRecognition() create facedetector", "FaceGestureRecognition() create facedetector: " + e.toString());
            		return;
            	}
    		}

    		
    		try 
        	{
        		count = m_FaceDetector.findFaces(m_FaceBitmap, m_FaceCharactors);
        	} 
        	catch (Exception e) 
        	{
        		Log.e("CFaceGestureHandler.FaceGestureRecognition()", "FaceGestureRecognition(): " + e.toString());
        		return;
        	}
	
    		if(0 < count)
    		{
    			//Identify the first face charactors
    			m_FaceCharactors[0].getMidPoint(m_FaceCenter);   
    			m_EyeDistance = m_FaceCharactors[0].eyesDistance();
    		    postInvalidate();
    			
   /*			float pose2X = m_FaceCharactors[0].pose(FaceDetector.Face.EULER_X);
    			float pose2Y = m_FaceCharactors[0].pose(FaceDetector.Face.EULER_Y);
    			float pose2Z = m_FaceCharactors[0].pose(FaceDetector.Face.EULER_Z);
    	
    			Message msg = m_MsgHandler.obtainMessage();
    			Bundle b = new Bundle();
    			b.putInt(CFaceGestureHandler.CFGH_KEY_IMAGEWIDTH, mFaceWidth);
    			b.putInt(CFaceGestureHandler.CFGH_KEY_IMAGEHEIGHT, mFaceHeight);
    			b.putFloat(CFaceGestureHandler.CFGH_KEY_FACECENTERX, m_FaceCenter.x);
    			b.putFloat(CFaceGestureHandler.CFGH_KEY_FACECENTERY, m_FaceCenter.y);
    			b.putFloat(CFaceGestureHandler.CFGH_KEY_EYEDISTANCE, distance);
    			b.putFloat(CFaceGestureHandler.CFGH_KEY_POSEX, pose2X);
    			b.putFloat(CFaceGestureHandler.CFGH_KEY_POSEY, pose2Y);
    			b.putFloat(CFaceGestureHandler.CFGH_KEY_POSEZ, pose2Z);
    			msg.setData(b);
    			m_MsgHandler.sendMessage(msg);*/
    		}
    	}
	}
	
    
	private void DrawFaceCVAPI(Canvas canvas)
	{
	    if (m_Faces != null) 
	    {
			Paint paint = new Paint();
		    paint.setColor(Color.YELLOW);
		    
	    	paint.setStrokeWidth(4);
	        paint.setStyle(Paint.Style.STROKE);
	        float scaleX = (float)getWidth()/m_GrayImage.width();
	        float scaleY = (float)getHeight()/m_GrayImage.height();
	        int total = m_Faces.total();
	        for (int i = 0; i < total; i++) 
	        {
	        	CvRect r = new CvRect(cvGetSeqElem(m_Faces, i));
	            int x = r.x(), y = r.y(), w = r.width(), h = r.height();
	            canvas.drawRect(x*scaleX, y*scaleY, (x+w)*scaleX, (y+h)*scaleY, paint);
	        }
	    }
	}
	
	private void DrawEyeAndroidAPI(Canvas canvas)
	{
		Paint paint = new Paint();
	    paint.setColor(Color.YELLOW);
    	paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.STROKE);
        //canvas.drawRect(paintRect, m_EdgePaint);
		float x = m_FaceCenter.x - m_EyeDistance/2.0f;
		float y = m_FaceCenter.y;
		RectF paintRect = new RectF(x, y, x + 20, y + 20);
        canvas.drawRect(paintRect, paint);
		x = m_FaceCenter.x + m_EyeDistance/2.0f;
		paintRect = new RectF(x, y, x + 20, y + 20);
        canvas.drawRect(paintRect, paint);
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) 
	{
		Paint paint = new Paint();
	    paint.setColor(Color.RED);
	    paint.setTextSize(20);

	    String s = "FacePreview - This side up.";
	    float textWidth = paint.measureText(s);
	    canvas.drawText(s, (getWidth()-textWidth)/2, 20, paint);

	    DrawFaceCVAPI(canvas);
	   // DrawEyeAndroidAPI(canvas);
	}
}
