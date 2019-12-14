package com.xgadget.ChuiNiuLite;

//import com.xgadget.uimodule.ResourceHelper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class CImageLoader 
{
	//The global context object for card object real-time loading image resource
	public static Context 					m_GameContext = null;
	public static Resources 				m_Resource = null;

	
	public static void SetGameContext(Context gCOntext)
	{
		m_GameContext = gCOntext;
		m_Resource = m_GameContext.getResources();   
	}
	
	public static Context GetGameContext()
	{
		return m_GameContext;
	}

	public static Resources GetGameResource()
	{
		return m_Resource;
	}
	
    public static Bitmap GetRawCow1Bitmap()
    {
		Bitmap srcBmp = null;
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.cow1);
		}
		return srcBmp; 
    }

	public static Drawable GetClockImage()
	{
		Drawable image = null;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return image; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.roboiconhi);
	        image = (Drawable)bmpimage;	
		}
		
		return image;
	}
    
	public static Drawable GetCow1Image()
	{
		Drawable image = null;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return image; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.cow1);
	        image = (Drawable)bmpimage;	
		}
		
		return image;
	}

	public static Bitmap GetRawCow2Bitmap()
    {
		Bitmap srcBmp = null;
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.cow2);
		}
		return srcBmp; 
    }
    
	public static Drawable GetCow2Image()
	{
		Drawable image = null;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return image; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.cow2);
	        image = (Drawable)bmpimage;	
		}
		
		return image;
	}
	
	public static Bitmap GetRawCow3Bitmap()
    {
		Bitmap srcBmp = null;
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.cow3);
		}
		return srcBmp; 
    }
    
	public static Drawable GetCow3Image()
	{
		Drawable image = null;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return image; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.cow3);
	        image = (Drawable)bmpimage;	
		}
		
		return image;
	}

    public static Bitmap GetRawCow4Bitmap()
    {
		Bitmap srcBmp = null;
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.cow4);
		}
		return srcBmp; 
    }
    
	public static Drawable GetCow4Image()
	{
		Drawable image = null;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return image; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.cow4);
	        image = (Drawable)bmpimage;	
		}
		
		return image;
	}

    public static Bitmap GetRawDeadCowBitmap()
    {
		Bitmap srcBmp = null;
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.deadcow);
		}
		return srcBmp; 
    }
    
	public static Drawable GetDeadCowImage()
	{
		Drawable image = null;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return image; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.deadcow);
	        image = (Drawable)bmpimage;	
		}
		
		return image;
	}

    public static Bitmap GetRawDeadDogBitmap()
    {
		Bitmap srcBmp = null;
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.dogdead);
		}
		return srcBmp; 
    }
    
	public static Drawable GetDeadDogImage()
	{
		Drawable image = null;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return image; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.dogdead);
	        image = (Drawable)bmpimage;	
		}
		
		return image;
	}
	
    public static Bitmap GetRawBlastBitmap()
    {
		Bitmap srcBmp = null;
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.blast);
		}
		return srcBmp; 
    }
    
	private static Drawable m_BlastImage = null;//Update to reduce buffer size
	public static Drawable GetBlastImage()
	{
		if(m_BlastImage != null)
			return m_BlastImage;
		
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_BlastImage; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.blast);
	        m_BlastImage = (Drawable)bmpimage;	
		}
		
		return m_BlastImage;
	}

	
    public static Bitmap GetRawDogStopBitmap()
    {
		Bitmap srcBmp = null;
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.dogwalk2);
		}
		return srcBmp; 
    }
    
	private static Drawable m_DogStopImage = null;//Update to reduce buffer size
	public static Drawable GetDogStopImage()
	{
		if(m_DogStopImage != null)
			return m_DogStopImage;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_DogStopImage; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.dogwalk0);
			m_DogStopImage = (Drawable)bmpimage;	
		}
		
		return m_DogStopImage;
	}

    public static Bitmap GetRawDogWalk1Bitmap()
    {
		Bitmap srcBmp = null;
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.dogwalk1);
		}
		return srcBmp; 
    }
    
	private static Drawable m_DogWalk1Image = null;//Update to reduce buffer size
	public static Drawable GetDogWalk1Image()
	{
		if(m_DogWalk1Image != null)
			return m_DogWalk1Image;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_DogWalk1Image; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.dogwalk1);
			m_DogWalk1Image = (Drawable)bmpimage;	
		}
		
		return m_DogWalk1Image;
	}

    public static Bitmap GetRawDogWalk2Bitmap()
    {
		Bitmap srcBmp = null;
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.dogwalk2);
		}
		return srcBmp; 
    }
    
	private static Drawable m_DogWalk2Image = null;//Update to reduce buffer size
	public static Drawable GetDogWalk2Image()
	{
		if(m_DogWalk2Image != null)
			return m_DogWalk2Image;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_DogWalk2Image; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.dogwalk2);
			m_DogWalk2Image = (Drawable)bmpimage;	
		}
		
		return m_DogWalk2Image;
	}

    public static Bitmap GetRawDogWalk3Bitmap()
    {
		Bitmap srcBmp = null;
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.dogwalk3);
		}
		return srcBmp; 
    }
    
	private static Drawable m_DogWalk3Image = null;//Update to reduce buffer size
	public static Drawable GetDogWalk3Image()
	{
		if(m_DogWalk3Image != null)
			return m_DogWalk3Image;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_DogWalk3Image; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.dogwalk3);
			m_DogWalk3Image = (Drawable)bmpimage;	
		}
		
		return m_DogWalk3Image;
	}

    public static Bitmap GetRawDogWalk4Bitmap()
    {
		Bitmap srcBmp = null;
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.dogwalk4);
		}
		return srcBmp; 
    }
    
	private static Drawable m_DogWalk4Image = null;//Update to reduce buffer size
	public static Drawable GetDogWalk4Image()
	{
		if(m_DogWalk4Image != null)
			return m_DogWalk4Image;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_DogWalk4Image; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.dogwalk4);
			m_DogWalk4Image = (Drawable)bmpimage;	
		}
		
		return m_DogWalk4Image;
	}

	private static Drawable m_DogShoot1Image = null;//Update to reduce buffer size
	public static Drawable GetDogShoot1Image()
	{
		if(m_DogShoot1Image != null)
			return m_DogShoot1Image;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_DogShoot1Image; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.dogshoot1);
			m_DogShoot1Image = (Drawable)bmpimage;	
		}
		
		return m_DogShoot1Image;
	}

	private static Drawable m_DogShoot2Image = null;//Update to reduce buffer size
	public static Drawable GetDogShoot2Image()
	{
		if(m_DogShoot2Image != null)
			return m_DogShoot2Image;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_DogShoot2Image; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.dogshoot2);
			m_DogShoot2Image = (Drawable)bmpimage;	
		}
		
		return m_DogShoot2Image;
	}
	
	private static Drawable m_DogShoot3Image = null;//Update to reduce buffer size
	public static Drawable GetDogShoot3Image()
	{
		if(m_DogShoot3Image != null)
			return m_DogShoot3Image;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_DogShoot3Image; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.dogshoot3);
			m_DogShoot3Image = (Drawable)bmpimage;	
		}
		
		return m_DogShoot3Image;
	}
	
	private static Drawable m_DogShoot4Image = null;//Update to reduce buffer size
	public static Drawable GetDogShoot4Image()
	{
		if(m_DogShoot4Image != null)
			return m_DogShoot4Image;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_DogShoot4Image; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.dogshoot4);
			m_DogShoot4Image = (Drawable)bmpimage;	
		}
		
		return m_DogShoot4Image;
	}

	private static Drawable m_DogJump1Image = null;//Update to reduce buffer size
	public static Drawable GetDogJump1Image()
	{
		if(m_DogJump1Image != null)
			return m_DogJump1Image;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_DogJump1Image; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.dogjum1);
			m_DogJump1Image = (Drawable)bmpimage;	
		}
		
		return m_DogJump1Image;
	}

	private static Drawable m_DogJump2Image = null;//Update to reduce buffer size
	public static Drawable GetDogJump2Image()
	{
		if(m_DogJump2Image != null)
			return m_DogJump2Image;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_DogJump2Image; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.dogjum2);
			m_DogJump2Image = (Drawable)bmpimage;	
		}
		
		return m_DogJump2Image;
	}

	private static Drawable m_DogJumpShoot1Image = null;//Update to reduce buffer size
	public static Drawable GetDogJumpShoot1Image()
	{
		if(m_DogJumpShoot1Image != null)
			return m_DogJumpShoot1Image;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_DogJumpShoot1Image; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.dogjumpshoot1);
			m_DogJumpShoot1Image = (Drawable)bmpimage;	
		}
		
		return m_DogJumpShoot1Image;
	}

	private static Drawable m_DogJumpShoot2Image = null;//Update to reduce buffer size
	public static Drawable GetDogJumpShoot2Image()
	{
		if(m_DogJumpShoot2Image != null)
			return m_DogJumpShoot2Image;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_DogJumpShoot2Image; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.dogjumpshoot2);
			m_DogJumpShoot2Image = (Drawable)bmpimage;	
		}
		
		return m_DogJumpShoot2Image;
	}
	
	
    public static Bitmap GetRawDogBulletBitmap()
    {
		Bitmap srcBmp = null;
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.air);
		}
		return srcBmp; 
    }
    
	private static Drawable m_DogBulletImage = null;//Update to reduce buffer size
	public static Drawable GetDogBulletImage()
	{
		if(m_DogBulletImage != null)
			return m_DogBulletImage;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_BlastImage; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.air);
			m_DogBulletImage = (Drawable)bmpimage;	
		}
		
		return m_DogBulletImage;
	}

    public static Bitmap GetRawCowBulletBitmap()
    {
		Bitmap srcBmp = null;
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.pooop);
		}
		return srcBmp; 
    }
    
	private static Drawable m_CowBulletImage = null;//Update to reduce buffer size
	public static Drawable GetCowBulletImage()
	{
		if(m_CowBulletImage != null)
			return m_CowBulletImage;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_BlastImage; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.pooop);
			m_CowBulletImage = (Drawable)bmpimage;	
		}
		
		return m_CowBulletImage;
	}
	
    public static Bitmap GetRawCloudBitmap1()
    {
		Bitmap srcBmp = null;
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.cloud1);
		}
		return srcBmp; 
    }
    
	private static Drawable m_CloudImage1 = null;//Update to reduce buffer size
	public static Drawable GetCloudImage1()
	{
		if(m_CloudImage1 != null)
			return m_CloudImage1;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_BlastImage; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.cloud1);
			m_CloudImage1 = (Drawable)bmpimage;	
		}
		
		return m_CloudImage1;
	}

	private static int		m_nCloud1ImageWidth = 0;
	private static int		m_nCloud1ImageHeight = 0;
	public static int GetCloudImage1Width()
	{
		if(0 < m_nCloud1ImageWidth)
			return m_nCloud1ImageWidth;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_nCloud1ImageWidth; 
		        
			Bitmap srcBmp = null;
    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.cloud1);
    		
    		m_nCloud1ImageWidth = srcBmp.getWidth();
    		m_nCloud1ImageHeight = srcBmp.getHeight();
		}
		
		return m_nCloud1ImageWidth;
	}
	
	public static int GetCloudImage1Height()
	{
		if(0 < m_nCloud1ImageHeight)
			return m_nCloud1ImageHeight;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_nCloud1ImageHeight; 
		        
			Bitmap srcBmp = null;
    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.cloud1);
    		
    		m_nCloud1ImageWidth = srcBmp.getWidth();
    		m_nCloud1ImageHeight = srcBmp.getHeight();
		}
		
		return m_nCloud1ImageHeight;
	}
	
	
    public static Bitmap GetRawCloudBitmap2()
    {
		Bitmap srcBmp = null;
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.cloud2);
		}
		return srcBmp; 
    }
    
	private static Drawable m_CloudImage2 = null;//Update to reduce buffer size
	public static Drawable GetCloudImage2()
	{
		if(m_CloudImage2 != null)
			return m_CloudImage2;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_BlastImage; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.cloud2);
			m_CloudImage2 = (Drawable)bmpimage;	
		}
		
		return m_CloudImage2;
	}

	private static int		m_nCloud2ImageWidth = 0;
	private static int		m_nCloud2ImageHeight = 0;
	public static int GetCloudImage2Width()
	{
		if(0 < m_nCloud2ImageWidth)
			return m_nCloud2ImageWidth;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_nCloud2ImageWidth; 
		        
			Bitmap srcBmp = null;
    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.cloud2);
    		
    		m_nCloud2ImageWidth = srcBmp.getWidth();
    		m_nCloud2ImageHeight = srcBmp.getHeight();
		}
		
		return m_nCloud2ImageWidth;
	}
	
	public static int GetCloudImage2Height()
	{
		if(0 < m_nCloud2ImageHeight)
			return m_nCloud2ImageHeight;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_nCloud2ImageHeight; 
		        
			Bitmap srcBmp = null;
    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.cloud2);
    		
    		m_nCloud2ImageWidth = srcBmp.getWidth();
    		m_nCloud2ImageHeight = srcBmp.getHeight();
		}
		
		return m_nCloud2ImageHeight;
	}
	
	private static Drawable m_RainCloudImage1 = null;//Update to reduce buffer size
	private static int		m_nRainCloud1ImageWidth = 0;
	private static int		m_nRainCloud1ImageHeight = 0;
	public static Drawable GetRainCloudImage1()
	{
		if(m_RainCloudImage1 != null)
			return m_RainCloudImage1;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_RainCloudImage1; 

			Bitmap srcBmp = null;
    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.rain1);
	   
    		m_nRainCloud1ImageWidth = srcBmp.getWidth();
    		m_nRainCloud1ImageHeight = srcBmp.getHeight();
   		
	        BitmapDrawable bmpimage = new BitmapDrawable(res, srcBmp);
	        m_RainCloudImage1 = (Drawable)bmpimage;	
		}
		
		return m_RainCloudImage1;
	}

	public static int GetRainCloudImage1Width()
	{
		if(0 < m_nRainCloud1ImageWidth)
			return m_nRainCloud1ImageWidth;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_nRainCloud1ImageWidth; 
		        
			Bitmap srcBmp = null;
    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.rain1);
	   
    		m_nRainCloud1ImageWidth = srcBmp.getWidth();
    		m_nRainCloud1ImageHeight = srcBmp.getHeight();
   		
	        BitmapDrawable bmpimage = new BitmapDrawable(res, srcBmp);
	        m_RainCloudImage1 = (Drawable)bmpimage;	
		}
		
		return m_nRainCloud1ImageWidth;
	}
	
	public static int GetRainCloudImage1Height()
	{
		if(0 < m_nRainCloud1ImageHeight)
			return m_nRainCloud1ImageHeight;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_nRainCloud1ImageHeight; 
		        
			Bitmap srcBmp = null;
    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.rain1);
	   
    		m_nRainCloud1ImageWidth = srcBmp.getWidth();
    		m_nRainCloud1ImageHeight = srcBmp.getHeight();
   		
	        BitmapDrawable bmpimage = new BitmapDrawable(res, srcBmp);
	        m_RainCloudImage1 = (Drawable)bmpimage;	
		}
		
		return m_nRainCloud1ImageHeight;
	}
	
	private static Drawable m_RainCloudImage2 = null;//Update to reduce buffer size
	private static int		m_nRainCloud2ImageWidth = 0;
	private static int		m_nRainCloud2ImageHeight = 0;
	public static Drawable GetRainCloudImage2()
	{
		if(m_RainCloudImage2 != null)
			return m_RainCloudImage2;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_RainCloudImage2; 

			Bitmap srcBmp = null;
    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.rain2);
	   
    		m_nRainCloud2ImageWidth = srcBmp.getWidth();
    		m_nRainCloud2ImageHeight = srcBmp.getHeight();
   		
	        BitmapDrawable bmpimage = new BitmapDrawable(res, srcBmp);
	        m_RainCloudImage2 = (Drawable)bmpimage;	
		}
		
		return m_RainCloudImage2;
	}

	public static int GetRainCloudImage2Width()
	{
		if(0 < m_nRainCloud2ImageWidth)
			return m_nRainCloud2ImageWidth;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_nRainCloud2ImageWidth; 
		        
			Bitmap srcBmp = null;
    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.rain2);
	   
    		m_nRainCloud2ImageWidth = srcBmp.getWidth();
    		m_nRainCloud2ImageHeight = srcBmp.getHeight();
   		
	        BitmapDrawable bmpimage = new BitmapDrawable(res, srcBmp);
	        m_RainCloudImage2 = (Drawable)bmpimage;	
		}
		
		return m_nRainCloud2ImageWidth;
	}
	
	public static int GetRainCloudImage2Height()
	{
		if(0 < m_nRainCloud2ImageHeight)
			return m_nRainCloud2ImageHeight;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_nRainCloud2ImageHeight; 
		        
			Bitmap srcBmp = null;
    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.rain2);
	   
    		m_nRainCloud2ImageWidth = srcBmp.getWidth();
    		m_nRainCloud2ImageHeight = srcBmp.getHeight();
   		
	        BitmapDrawable bmpimage = new BitmapDrawable(res, srcBmp);
	        m_RainCloudImage2 = (Drawable)bmpimage;	
		}
		
		return m_nRainCloud2ImageHeight;
	}
	
	private static Drawable m_BirdImage1 = null;//Update to reduce buffer size
	public static Drawable GetBirdImage1()
	{
		if(m_BirdImage1 != null)
			return m_BirdImage1;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_BirdImage1; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.birdfly1);
			m_BirdImage1 = (Drawable)bmpimage;	
		}
		
		return m_BirdImage1;
	}
	
	private static Drawable m_BirdImage2 = null;//Update to reduce buffer size
	public static Drawable GetBirdImage2()
	{
		if(m_BirdImage2 != null)
			return m_BirdImage2;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_BirdImage2; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.birdfly2);
			m_BirdImage2 = (Drawable)bmpimage;	
		}
		
		return m_BirdImage2;
	}

	private static Drawable m_BirdShootImage1 = null;//Update to reduce buffer size
	public static Drawable GetBirdShootImage1()
	{
		if(m_BirdShootImage1 != null)
			return m_BirdShootImage1;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_BirdShootImage1; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.birdshoot1);
	        m_BirdShootImage1 = (Drawable)bmpimage;	
		}
		
		return m_BirdShootImage1;
	}
	
	private static Drawable m_BirdShootImage2 = null;//Update to reduce buffer size
	public static Drawable GetBirdShootImage2()
	{
		if(m_BirdShootImage2 != null)
			return m_BirdShootImage2;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_BirdShootImage2; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.birdshoot2);
	        m_BirdShootImage2 = (Drawable)bmpimage;	
		}
		
		return m_BirdShootImage2;
	}

	private static Drawable m_BirdBubbleImage = null;//Update to reduce buffer size
	public static Drawable GetBirdBubbleImage()
	{
		if(m_BirdBubbleImage != null)
			return m_BirdBubbleImage;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_BirdBubbleImage; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.birdbubble);
	        m_BirdBubbleImage = (Drawable)bmpimage;	
		}
		
		return m_BirdBubbleImage;
	}
	
	
    public static Bitmap GetRawRockBitmap1()
    {
		Bitmap srcBmp = null;
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.rock1);
		}
		return srcBmp; 
    }
    
	private static Drawable m_RockImage1 = null;//Update to reduce buffer size
	public static Drawable GetRockImage1()
	{
		if(m_RockImage1 != null)
			return m_RockImage1;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_BlastImage; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.rock1);
			m_RockImage1 = (Drawable)bmpimage;	
		}
		
		return m_RockImage1;
	}

    public static Bitmap GetRawRockBitmap2()
    {
		Bitmap srcBmp = null;
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.rock2);
		}
		return srcBmp; 
    }
    
	private static Drawable m_RockImage2 = null;//Update to reduce buffer size
	public static Drawable GetRockImage2()
	{
		if(m_RockImage2 != null)
			return m_RockImage2;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_BlastImage; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.rock2);
			m_RockImage2 = (Drawable)bmpimage;	
		}
		
		return m_RockImage2;
	}

    public static Bitmap GetRawSnailBitmap()
    {
		Bitmap srcBmp = null;
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.snail);
		}
		return srcBmp; 
    }
    
	private static Drawable m_SnailImage = null;//Update to reduce buffer size
	public static Drawable GetSnailImage()
	{
		if(m_SnailImage != null)
			return m_SnailImage;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_SnailImage; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.snail);
			m_SnailImage = (Drawable)bmpimage;	
		}
		
		return m_SnailImage;
	}

    public static Bitmap GetRawRainbowBitmap()
    {
		Bitmap srcBmp = null;
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.rainbow);
		}
		return srcBmp; 
    }
    
	public static Drawable GetRainbowImage()
	{
		Drawable image = null;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_BlastImage; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.rainbow);
			image = (Drawable)bmpimage;	
		}
		
		return image;
	}
	
	public static final int BACKGROUND_GRID_SIZE = 16;
	
	private static Bitmap GetCheckPatternBitmap()
	{
        int width = BACKGROUND_GRID_SIZE*2;
        int height = BACKGROUND_GRID_SIZE*2;
        Bitmap destBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(destBmp);
		
	    Paint 	paint = new Paint();
	    paint.setAntiAlias(true);
	    paint.setARGB(255, 255, 255, 255);
		Rect rect = new Rect(0, 0, width, height);
		if(paint != null)
		{
        	canvas.drawRect(rect, paint);
        	paint.setARGB(255, 178, 178, 178);
        	//paint.setStrokeWidth(CGameLayout.getPaperStrokeSize());
    		rect = new Rect(BACKGROUND_GRID_SIZE, 0, width, BACKGROUND_GRID_SIZE);
        	canvas.drawRect(rect, paint);
    		rect = new Rect(0, BACKGROUND_GRID_SIZE, BACKGROUND_GRID_SIZE, height);
        	canvas.drawRect(rect, paint);
		}
        
        return destBmp;
	}
	
	private static Paint	m_PaperGridPaint = null;
	public static Paint GetPaperGridPaint()
	{
		if(m_PaperGridPaint == null)
		{
			m_PaperGridPaint = new Paint();
			m_PaperGridPaint.setAntiAlias(true);
			Bitmap mBitmap = GetCheckPatternBitmap();
			BitmapShader mBitmapShader = new BitmapShader(mBitmap,Shader.TileMode.MIRROR, Shader.TileMode.MIRROR); //Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);//Shader.TileMode.MIRROR);
			m_PaperGridPaint.setShader(mBitmapShader);	
		}
		return m_PaperGridPaint;
	}

	private static Bitmap GetMudTextureBitmap()
	{
		Bitmap srcBmp = null;
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.mudtex);
		}
		return srcBmp; 
	}
	
	private static Paint	m_MudPaint = null;
	public static Paint GetMudPaint()
	{
		if(m_MudPaint == null)
		{
			m_MudPaint = new Paint();
			m_MudPaint.setAntiAlias(true);
			Bitmap mBitmap = GetMudTextureBitmap();
			BitmapShader mBitmapShader = new BitmapShader(mBitmap,Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);//Shader.TileMode.MIRROR);
			m_MudPaint.setShader(mBitmapShader);	
		}
		return m_MudPaint;
	}

	private static Bitmap	m_GrassBitmap = null;
	public static Bitmap GetGrassBitmap()
	{
		if(m_GrassBitmap == null)
		{
			if(m_GameContext != null)
			{
		        Resources res = m_GameContext.getResources();
		        if(res == null)
		        	return m_GrassBitmap;
		        
		        m_GrassBitmap = BitmapFactory.decodeResource(res, R.drawable.grasstex);
				
			}
		}
		return m_GrassBitmap;
	}
	
	private static Bitmap GetStar1Bitmap()
	{
		Bitmap srcBmp = null;
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.star);
		}
		return srcBmp; 
	}
	
	private static Paint	m_Star1Paint = null;
	public static Paint GetStar1Paint()
	{
		if(m_Star1Paint == null)
		{
			m_Star1Paint = new Paint();
			m_Star1Paint.setAntiAlias(true);
			Bitmap mBitmap = GetStar1Bitmap();
			BitmapShader mBitmapShader = new BitmapShader(mBitmap,Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);//Shader.TileMode.MIRROR);
			m_Star1Paint.setShader(mBitmapShader);	
		}
		return m_Star1Paint;
	}

	private static Bitmap GetStar2Bitmap()
	{
		Bitmap srcBmp = null;
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.star2);
		}
		return srcBmp; 
	}
	
	private static Paint	m_Star2Paint = null;
	public static Paint GetStar2Paint()
	{
		if(m_Star2Paint == null)
		{
			m_Star2Paint = new Paint();
			m_Star2Paint.setAntiAlias(true);
			Bitmap mBitmap = GetStar2Bitmap();
			BitmapShader mBitmapShader = new BitmapShader(mBitmap,Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);//Shader.TileMode.MIRROR);
			m_Star2Paint.setShader(mBitmapShader);	
		}
		return m_Star2Paint;
	}
	
	private static Drawable m_MoonImage = null;//Update to reduce buffer size
	public static Drawable GetMoonImage()
	{
		if(m_MoonImage != null)
			return m_MoonImage;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_MoonImage; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.moon);
	        m_MoonImage = (Drawable)bmpimage;	
		}
		
		return m_MoonImage;
	}

	private static Drawable m_LightImage1 = null;//Update to reduce buffer size
	public static Drawable GetLightImage1()
	{
		if(m_LightImage1 != null)
			return m_LightImage1;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_LightImage1; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.light1);
	        m_LightImage1 = (Drawable)bmpimage;	
		}
		
		return m_LightImage1;
	}

	private static Drawable m_LightImage2 = null;//Update to reduce buffer size
	public static Drawable GetLightImage2()
	{
		if(m_LightImage2 != null)
			return m_LightImage2;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_LightImage2; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.light2);
	        m_LightImage2 = (Drawable)bmpimage;	
		}
		
		return m_LightImage2;
	}

	private static Bitmap m_SystemButtonBitmap = null;//Update to reduce buffer size
	public static Bitmap GetSystemButtonBitmap()
	{
		if(m_SystemButtonBitmap != null)
			return m_SystemButtonBitmap;
		
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_SystemButtonBitmap; 

	        m_SystemButtonBitmap = BitmapFactory.decodeResource(res, R.drawable.menuicon);
		}
		return m_SystemButtonBitmap; 
	}

	private static Bitmap m_ScoreHelpBitmap = null;//Update to reduce buffer size
	public static Bitmap GetScoreHelpBitmap()
	{
		if(m_ScoreHelpBitmap != null)
			return m_ScoreHelpBitmap;
		
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_ScoreHelpBitmap; 

	        m_ScoreHelpBitmap = BitmapFactory.decodeResource(res, R.drawable.qbutton);
		}
		return m_ScoreHelpBitmap; 
	}

	private static Bitmap m_OnLineScoreBitmap = null;//Update to reduce buffer size
	public static Bitmap GetOnLineScoreBitmap()
	{
		if(m_OnLineScoreBitmap != null)
			return m_OnLineScoreBitmap;
		
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_OnLineScoreBitmap; 

	        m_OnLineScoreBitmap = BitmapFactory.decodeResource(res, R.drawable.netscore);
		}
		return m_OnLineScoreBitmap; 
	}

	private static Bitmap m_StartGameBitmap = null;//Update to reduce buffer size
	public static Bitmap GetStartGameBitmap()
	{
		if(m_StartGameBitmap != null)
			return m_StartGameBitmap;
		
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_StartGameBitmap; 

	        m_StartGameBitmap = BitmapFactory.decodeResource(res, R.drawable.playback);
		}
		return m_StartGameBitmap; 
	}

	private static Bitmap m_RoundCloseBitmap = null;//Update to reduce buffer size
	public static Bitmap GetRoundCloseBitmap()
	{
		if(m_RoundCloseBitmap != null)
			return m_RoundCloseBitmap;
		
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_RoundCloseBitmap; 

	        m_RoundCloseBitmap = BitmapFactory.decodeResource(res, R.drawable.closeicon);
		}
		return m_RoundCloseBitmap; 
	}

	private static Bitmap m_RoundOKBitmap = null;//Update to reduce buffer size
	public static Bitmap GetRoundOKBitmap()
	{
		if(m_RoundOKBitmap != null)
			return m_RoundOKBitmap;
		
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_RoundOKBitmap; 

	        m_RoundOKBitmap = BitmapFactory.decodeResource(res, R.drawable.okicon);
		}
		return m_RoundOKBitmap; 
	}
	
	private static Bitmap m_SwitchOnBitmap = null;//Update to reduce buffer size
	public static Bitmap GetSwitchOnBitmap()
	{
		if(m_SwitchOnBitmap != null)
			return m_SwitchOnBitmap;
		
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_SwitchOnBitmap; 

	        m_SwitchOnBitmap = BitmapFactory.decodeResource(res, R.drawable.onbutton);
		}
		return m_SwitchOnBitmap; 
	}

	private static Bitmap m_SwitchOffBitmap = null;//Update to reduce buffer size
	public static Bitmap GetSwitchOffBitmap()
	{
		if(m_SwitchOffBitmap != null)
			return m_SwitchOffBitmap;
		
		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_SwitchOffBitmap; 

	        m_SwitchOffBitmap = BitmapFactory.decodeResource(res, R.drawable.offbutton);
		}
		return m_SwitchOffBitmap; 
	}

	private static Bitmap m_FaceButtonBitmap = null;
	public static Bitmap GetFaceButtonBitmap()
	{
		if(m_FaceButtonBitmap == null)
		{
			if(m_GameContext != null)
			{
		        Resources res = m_GameContext.getResources();
		        if(res == null)
		        	return m_FaceButtonBitmap;
		        
		        m_FaceButtonBitmap = BitmapFactory.decodeResource(res, R.drawable.facebtn);
			}
		}
		return m_FaceButtonBitmap;	
	}

	private static Bitmap m_MouthButtonBitmap = null;
	public static Bitmap GetMouthButtonBitmap()
	{
		if(m_MouthButtonBitmap == null)
		{
			if(m_GameContext != null)
			{
		        Resources res = m_GameContext.getResources();
		        if(res == null)
		        	return m_MouthButtonBitmap;
		        
		        m_MouthButtonBitmap = BitmapFactory.decodeResource(res, R.drawable.mouthbtn);
			}
		}
		return m_MouthButtonBitmap;	
	}
	
	private static Bitmap m_Mouth1Bitmap = null;
	public static Bitmap GetMouth1Bitmap()
	{
		if(m_Mouth1Bitmap == null)
		{
			if(m_GameContext != null)
			{
		        Resources res = m_GameContext.getResources();
		        if(res == null)
		        	return m_Mouth1Bitmap;
		        
		        m_Mouth1Bitmap = BitmapFactory.decodeResource(res, R.drawable.mouth1);
			}
		}
		return m_Mouth1Bitmap;	
	}

	private static Bitmap m_Mouth2Bitmap = null;
	public static Bitmap GetMouth2Bitmap()
	{
		if(m_Mouth2Bitmap == null)
		{
			if(m_GameContext != null)
			{
		        Resources res = m_GameContext.getResources();
		        if(res == null)
		        	return m_Mouth2Bitmap;
		        
		        m_Mouth2Bitmap = BitmapFactory.decodeResource(res, R.drawable.mouth2);
			}
		}
		return m_Mouth2Bitmap;	
	}

	private static Bitmap m_Mouth3Bitmap = null;
	public static Bitmap GetMouth3Bitmap()
	{
		if(m_Mouth3Bitmap == null)
		{
			if(m_GameContext != null)
			{
		        Resources res = m_GameContext.getResources();
		        if(res == null)
		        	return m_Mouth3Bitmap;
		        
		        m_Mouth3Bitmap = BitmapFactory.decodeResource(res, R.drawable.mouth3);
			}
		}
		return m_Mouth3Bitmap;	
	}

	private static Bitmap m_Mouth4Bitmap = null;
	public static Bitmap GetMouth4Bitmap()
	{
		if(m_Mouth4Bitmap == null)
		{
			if(m_GameContext != null)
			{
		        Resources res = m_GameContext.getResources();
		        if(res == null)
		        	return m_Mouth4Bitmap;
		        
		        m_Mouth4Bitmap = BitmapFactory.decodeResource(res, R.drawable.mouth4);
			}
		}
		return m_Mouth4Bitmap;	
	}

	private static Drawable m_Mouth1Image = null;//Update to reduce buffer size
	public static Drawable GetMouth1Image()
	{
		if(m_Mouth1Image != null)
			return m_Mouth1Image;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_Mouth1Image; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.mouth1);
	        m_Mouth1Image = (Drawable)bmpimage;	
		}
		
		return m_Mouth1Image;
	}

	private static Drawable m_Mouth2Image = null;//Update to reduce buffer size
	public static Drawable GetMouth2Image()
	{
		if(m_Mouth2Image != null)
			return m_Mouth2Image;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_Mouth2Image; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.mouth2);
	        m_Mouth2Image = (Drawable)bmpimage;	
		}
		
		return m_Mouth2Image;
	}
	
	private static Drawable m_Mouth3Image = null;//Update to reduce buffer size
	public static Drawable GetMouth3Image()
	{
		if(m_Mouth3Image != null)
			return m_Mouth3Image;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_Mouth3Image; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.mouth3);
	        m_Mouth3Image = (Drawable)bmpimage;	
		}
		
		return m_Mouth3Image;
	}

	private static Drawable m_Mouth4Image = null;//Update to reduce buffer size
	public static Drawable GetMouth4Image()
	{
		if(m_Mouth4Image != null)
			return m_Mouth4Image;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_Mouth4Image; 
		        
	        BitmapDrawable bmpimage = (BitmapDrawable)res.getDrawable(R.drawable.mouth4);
	        m_Mouth4Image = (Drawable)bmpimage;	
		}
		
		return m_Mouth4Image;
	}

	private static Bitmap m_SingleQMarkBitmap = null;
	private static int m_SingleQMarkWidth = -1;
	private static int m_SingleQMarkHeight = -1;
	public static Bitmap GetSingleQMarkBitmap()
	{
		if(m_SingleQMarkBitmap == null)
		{
			if(m_GameContext != null)
			{
		        Resources res = m_GameContext.getResources();
		        if(res == null)
		        	return m_SingleQMarkBitmap;
		        
		        m_SingleQMarkBitmap = BitmapFactory.decodeResource(res, R.drawable.sqmark);
		    	m_SingleQMarkWidth = m_SingleQMarkBitmap.getWidth();
		    	m_SingleQMarkHeight = m_SingleQMarkBitmap.getHeight();
		        
			}
		}
		return m_SingleQMarkBitmap;	
	}
	
	public static int GetSingleQMarkWidth()
	{
		if(m_SingleQMarkWidth == -1)
		{
			Bitmap bitmap = GetSingleQMarkBitmap();
	    	m_SingleQMarkWidth = bitmap.getWidth();
		}
		
		return m_SingleQMarkWidth;
	}
	
	public static int GetSingleQMarkHeight()
	{
		if(m_SingleQMarkHeight == -1)
		{
			Bitmap bitmap = GetSingleQMarkBitmap();
	    	m_SingleQMarkHeight = bitmap.getHeight();
		}
		
		return m_SingleQMarkHeight;
	}
	
	private static Bitmap m_SwitchImage = null;//Update to reduce buffer size
	private static int m_SwitchImageWidth = -1;
	private static int m_SwitchImageHeight = -1;
	public static Bitmap GetSwitchImage()
	{
		if(m_SwitchImage != null)
			return m_SwitchImage;

		if(m_GameContext != null)
		{
	        Resources res = m_GameContext.getResources();
	        if(res == null)
	        	return m_SwitchImage; 
		        
	        m_SwitchImage = BitmapFactory.decodeResource(res, R.drawable.onoff);
	        m_SwitchImageWidth = m_SwitchImage.getWidth();
	        m_SwitchImageHeight = m_SwitchImage.getHeight();
		}
		
		return m_SwitchImage;
	}

	public static int GetSwitchImageWidth()
	{
		if(m_SwitchImageWidth == -1)
		{
			Bitmap bitmap = GetSwitchImage();
	    	m_SwitchImageWidth = bitmap.getWidth();
		}
		
		return m_SwitchImageWidth;
	}
	
	public static int GetSwitchImageHeight()
	{
		if(m_SwitchImageHeight == -1)
		{
			Bitmap bitmap = GetSwitchImage();
	    	m_SwitchImageHeight = bitmap.getHeight();
		}
		
		return m_SwitchImageHeight;
	}
}
