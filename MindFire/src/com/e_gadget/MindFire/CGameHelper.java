package com.e_gadget.MindFire;

import java.util.Vector;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;

public class CGameHelper 
{
	//The global context object for card object real-time loading image resource
	public static Context 					m_GameContext = null;
	
	/*
	 * The Andorid screen mode constants 
	 */
	//HVQA portrait mode
	public static final int 						m_SMode_HVQA_P = 0;

	//HVQA landscape mode
	public static final int 						m_SMode_HVQA_L = 1;

	//QVQA portrait mode
	public static final int 						m_SMode_QVQA_P = 2;
	
	//QVQA landscape mode
	public static final int 						m_SMode_QVQA_L = 3;

	//QVQA landscape mode
	public static final int 						m_SMode_VQA_LARGE = 4;

	//Current Andorid system screen mode
	public static int						m_OS_SMode = CGameHelper.m_SMode_HVQA_P;

	//HVQA mode special digit image size
	public static final int 						m_LARGE_SZI_WIDTH = 22;

	public static final int 						m_LARGE_SZI_HEIGHT = 28;

	//HVQA mode card digit image size
	public static final int 						m_LARGE_CDN_WIDTH = 11;

	public static final int 						m_LARGE_CDN_HEIGHT = 14;

	//HVQA mode card large flower image size
	public static final int 						m_LARGE_FL_WIDTH = 39;

	public static final int 						m_LARGE_FL_HEIGHT = 39;

	//HVQA mode card small flower image size
	public static final int 						m_LARGE_FS_WIDTH = 11;

	public static final int 						m_LARGE_FS_HEIGHT = 11;

	//HVQA mode card image size
	public static final int 						m_LARGE_CARD_WIDTH = 71;

	public static final int 						m_LARGE_CARD_HEIGHT = 96;

	//HVQA mode button sign image size
	public static final int 						m_LARGE_SIGN_WIDTH = 28;

	public static final int 						m_LARGE_SIGN_HEIGHT = 28;

	//HVQA mode deal sign image size
	public static final int 						m_LARGE_DEAL_WIDTH = 28;

	public static final int 						m_LARGE_DEAL_HEIGHT = 28;

	public static String 							m_strResultTitle = null;

	public static void SetResultTitle(String str)
	{
		m_strResultTitle = str;
	}
	
	public static void SetGameContext(Context gCOntext)
	{
		CGameHelper.m_GameContext = gCOntext;
	}

	public static Context GetGameContext()
	{
		return CGameHelper.m_GameContext;
	}

	public static boolean CanLoadResource()
	{
		return (CGameHelper.m_GameContext != null);
	}
	
	public static void SetScreenMode(int nMode)
	{
		if(CGameHelper.m_SMode_HVQA_P <= nMode && nMode <= CGameHelper.m_SMode_QVQA_L)
		{
			CGameHelper.m_OS_SMode = nMode;
		}
	}

	public static int GetScreenMode()
	{
		return CGameHelper.m_OS_SMode;
	}
	
	public static boolean IsPortraitMode()
	{
		boolean bRet = (CGameHelper.m_OS_SMode == CGameHelper.m_SMode_HVQA_P || 
						CGameHelper.m_OS_SMode == CGameHelper.m_SMode_QVQA_P || CGameHelper.m_OS_SMode == CGameHelper.m_SMode_VQA_LARGE);
		return bRet;
	}

	public static boolean IsLargeScreenMode()
	{
		boolean bRet = (CGameHelper.m_OS_SMode == CGameHelper.m_SMode_HVQA_P || 
						CGameHelper.m_OS_SMode == CGameHelper.m_SMode_HVQA_L || CGameHelper.m_OS_SMode == CGameHelper.m_SMode_VQA_LARGE);
		return bRet;
	}
	
	private static Drawable GetCardBackSideImage_Internal()
	{
		Drawable image = null;
		if(CGameHelper.m_GameContext != null)
		{
			Bitmap bitmap = null;
			
			Bitmap bmpSrc = CGameHelper.GetCardSoruceBitmap();
			if(bmpSrc != null)
			{	
				Rect rect = CGameHelper.GetCardImageSize(); 
		        int width = rect.width();
		        int height = rect.height();
		        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
				Canvas canvas = new Canvas(bitmap);
		        Rect src = new Rect(0, 0, 0, 0);
		        Rect dst = new Rect(0, 0, width, height);
		        src.left = width; 
		        src.right = src.left + width; 
		        src.bottom = src.top + height;
		        	
				canvas.drawBitmap(bmpSrc, src, dst, null);
				Resources res = CGameHelper.GetSystemResource();
				image = (Drawable)(new BitmapDrawable(res, bitmap));
				
			}
		}
		return image;
	}
	
	private static Drawable m_CardBackImageProtrait = null;//Update to reduce buffer size
	private static Drawable m_CardBackImageLandscape = null;//Update to reduce buffer size
	public static Drawable GetCardBackSideImage()
	{
		Drawable image = null;
		if(IsPortraitMode())
		{
			if(m_CardBackImageProtrait == null)
			{
				m_CardBackImageProtrait = CGameHelper.GetCardBackSideImage_Internal();
			}
			image = m_CardBackImageProtrait;
		}
		else
		{
			if(m_CardBackImageLandscape == null)
			{
				m_CardBackImageLandscape = CGameHelper.GetCardBackSideImage_Internal();
			}
			image = m_CardBackImageLandscape;
		}
		
		return image;
	}
	
	private static Bitmap m_CardBitmapProtrait = null;//Update to reduce buffer size
	private static Bitmap m_CardBitmapLandscape = null;//Update to reduce buffer size
	public static Bitmap GetCardSoruceBitmap()
	{
		Bitmap image = null;
		if(CGameHelper.m_GameContext != null)
		{
			Resources res = CGameHelper.m_GameContext.getResources();
			if(res == null)
				return image; 

			if(IsPortraitMode())
			{
				if(m_CardBitmapProtrait == null)
				{
					m_CardBitmapProtrait = BitmapFactory.decodeResource(res, R.drawable.card);
				}
				image = m_CardBitmapProtrait;
			}
			else
			{
				if(m_CardBitmapLandscape == null)
				{
					m_CardBitmapLandscape = BitmapFactory.decodeResource(res, R.drawable.card2);
				}
				image = m_CardBitmapLandscape;
			}
		} 
		return image;
	}

	private static Bitmap m_StarsBitmap = null;
	public static Bitmap GetStarsBitmap()
	{
		Bitmap srcBmp = null;
		if(CGameHelper.m_GameContext != null)
		{
	        Resources res = CGameHelper.m_GameContext.getResources();
	        if(res == null)
	        	return srcBmp; 

	        if(m_StarsBitmap == null)
	        {
	        	m_StarsBitmap = BitmapFactory.decodeResource(res, R.drawable.stars);
	        }
    		srcBmp = m_StarsBitmap;
		}
		
		return srcBmp;
	}

	public static int GetStarWidth()
	{
		int nWidth = 20;
		if(m_StarsBitmap != null)
		{
			nWidth = m_StarsBitmap.getWidth()/4;
		}
		return nWidth;
	}

	public static int GetStarHeight()
	{
		int nHeight = 20; 
		if(m_StarsBitmap != null)
		{
			nHeight = m_StarsBitmap.getHeight();
		}
		return nHeight;
	}
	
	public static int GetStarFrame()
	{
		return 6;
	}
	
	private static Bitmap m_BasicCardNumberBitmap = null;//Update to reduce buffer size
	public static Bitmap GetBasicCardNumberBitmap()
	{
		Bitmap image = null;
		if(CGameHelper.m_GameContext != null)
		{
			Resources res = CGameHelper.m_GameContext.getResources();
			if(res == null)
				return image; 

			if(m_BasicCardNumberBitmap == null)
			{
				m_BasicCardNumberBitmap = BitmapFactory.decodeResource(res, R.drawable.number);
			}
	        image = m_BasicCardNumberBitmap;
		} 
		return image;
	}
	
	private static Bitmap m_TempCardNumberBitmap = null;//Update to reduce buffer size
	public static Bitmap GetTempCardNumberBitmap()
	{
		Bitmap image = null;
		if(CGameHelper.m_GameContext != null)
		{
			Resources res = CGameHelper.m_GameContext.getResources();
			if(res == null)
				return image; 

			if(m_TempCardNumberBitmap == null)
			{
				m_TempCardNumberBitmap = BitmapFactory.decodeResource(res, R.drawable.z);
			}
			image = m_TempCardNumberBitmap;
		} 
		return image;
	}

	private static Bitmap m_SmallFlowersBitmap = null;//Update to reduce buffer size
	public static Bitmap GetSmallFlowersBitmap()
	{
		Bitmap image = null;
		if(CGameHelper.m_GameContext != null)
		{
			Resources res = CGameHelper.m_GameContext.getResources();
			if(res == null)
				return image; 

			if(m_SmallFlowersBitmap == null)
			{
				m_SmallFlowersBitmap = BitmapFactory.decodeResource(res, R.drawable.flowers);
			}
			image = m_SmallFlowersBitmap;
		} 
		return image;
	}

	private static Bitmap m_LargeFlowersBitmap = null;//Update to reduce buffer size
	public static Bitmap GetLargeFlowersBitmap()
	{
		Bitmap image = null;
		if(CGameHelper.m_GameContext != null)
		{
			Resources res = CGameHelper.m_GameContext.getResources();
			if(res == null)
				return image; 

			if(m_LargeFlowersBitmap == null)
			{
				m_LargeFlowersBitmap = BitmapFactory.decodeResource(res, R.drawable.flowerl);
			}
			image = m_LargeFlowersBitmap;
		} 
		return image;
	}
	
	public static int GetSmallFlowerImageWidth()
	{
		int nRet = m_LARGE_FS_WIDTH;
		
		if(m_SmallFlowersBitmap == null)
		{
			m_SmallFlowersBitmap = GetSmallFlowersBitmap();
		}
		if(m_SmallFlowersBitmap != null)
		{
			nRet = m_SmallFlowersBitmap.getWidth()/4; 
		}
		
		return nRet;
	}

	public static int GetSmallFlowerImageHeight()
	{
		int nRet = m_LARGE_FS_HEIGHT;
		if(m_SmallFlowersBitmap == null)
		{
			m_SmallFlowersBitmap = GetSmallFlowersBitmap();
		}
		if(m_SmallFlowersBitmap != null)
		{
			nRet = m_SmallFlowersBitmap.getHeight()/2; 
		}
		return nRet;
	}
	
	public static int GetLargeFlowerImageWidth()
	{
		int nRet = m_LARGE_FL_WIDTH;
		
		if(m_LargeFlowersBitmap == null)
		{
			m_LargeFlowersBitmap = GetLargeFlowersBitmap();
		}
		if(m_LargeFlowersBitmap != null)
		{
			nRet = m_LargeFlowersBitmap.getWidth()/4;
		}
		
		return nRet;
	}

	public static int GetLargeFlowerImageHeight()
	{
		int nRet = m_LARGE_FL_HEIGHT;
		
		if(m_LargeFlowersBitmap == null)
		{
			m_LargeFlowersBitmap = GetLargeFlowersBitmap();
		}
		if(m_LargeFlowersBitmap != null)
		{
			nRet = m_LargeFlowersBitmap.getHeight();
		}
		return nRet;
	}
	
	public static Drawable GetTempCardImage(int cardValue)
	{
		Drawable image = null;
		if(CGameHelper.m_GameContext != null)
		{
			CCardImageComposer imgcreator = new CCardImageComposer(cardValue);
			image = (Drawable)imgcreator.CreateTempCardImage();
		}
		return image;
	}

	
	public static Drawable GetBasicCardImage(int cardIndex)
	{
		Drawable image = null;
		if(CGameHelper.m_GameContext != null)
		{
			CCardImageComposer imgcreator = new CCardImageComposer(cardIndex);
			image = (Drawable)imgcreator.CreateBasicCardImage();
		}
		return image;
	}
	
	public static int GetDigitCount(int number)
	{
		int nCount = 0;
		number = Math.abs(number);

		do
		{	
			++nCount;
			number = number/10;
		}while(0 < number);
		
		return nCount;
	}
	
	public static int GetDigitAt(int nNumber, int nIndex)
	{
		int nRet = 0;
		
		int nOrder = 1;
		
		for(int i = 0; i <= nIndex; ++i)
		{	
			nOrder = nOrder*10;
		}

		nNumber = Math.abs(nNumber);
		int n1 = nNumber/nOrder;
		n1 *= nOrder;
		int n2 = nNumber - n1;
		nOrder /= 10;
		nRet = n2/nOrder; 
		
		return nRet;
	}
	
	public static Rect GetCardImageSize()
	{
		Rect rect = new Rect(0, 0, 0, 0);
		
		if(IsPortraitMode())
		{
			rect.bottom = m_LARGE_CARD_HEIGHT;
			rect.right = m_LARGE_CARD_WIDTH;
			if(m_CardBitmapProtrait != null)
			{
				rect.bottom = m_CardBitmapProtrait.getHeight();
				rect.right = m_CardBitmapProtrait.getWidth()/2;
			}
		}
		else
		{
			rect.bottom = m_LARGE_CARD_WIDTH;
			rect.right = m_LARGE_CARD_HEIGHT;
			if(m_CardBitmapLandscape != null)
			{
				rect.bottom = m_CardBitmapLandscape.getHeight();
				rect.right = m_CardBitmapLandscape.getWidth()/2;
			}
		}
		return rect;
	}
	
	public static int GetCardImageWidth()
	{
		Rect rect = CGameHelper.GetCardImageSize();
		int nWidth = rect.width();
		return nWidth;
	}

	public static int GetCardImageHeight()
	{
		Rect rect = CGameHelper.GetCardImageSize();
		int nHeight = rect.height();
		return nHeight;
	}

	public static int GetBasicCardDigitImageWidth()
	{
		int nWidth = m_LARGE_CDN_WIDTH;
		if(m_BasicCardNumberBitmap != null)
		{
			nWidth = m_BasicCardNumberBitmap.getWidth()/13; 
		}
		return nWidth;
	}

	public static int GetBasicCardDigitImageHeight()
	{
		int nHeight = m_LARGE_CDN_HEIGHT;
		if(m_BasicCardNumberBitmap != null)
		{
			nHeight = m_BasicCardNumberBitmap.getHeight()/4; 
		}
		return nHeight;
	}
	
	public static int GetRealCardWidth()
	{
		int nMode = CGameHelper.GetScreenMode();
		int nWidth = 0;
		switch(nMode)
		{
			case CGameHelper.m_SMode_HVQA_P:
			case CGameHelper.m_SMode_VQA_LARGE:
				nWidth = m_LARGE_CARD_WIDTH;
				break;
			case CGameHelper.m_SMode_HVQA_L:
				nWidth = m_LARGE_CARD_HEIGHT;
				break;
			case CGameHelper.m_SMode_QVQA_P:
				nWidth = (m_LARGE_CARD_WIDTH+1)*3/4;
				break;
			case CGameHelper.m_SMode_QVQA_L:
				nWidth = m_LARGE_CARD_HEIGHT*3/4;
				break;
		}
		return nWidth;
	}

	public static int GetRealCardHeight()
	{
		int nMode = CGameHelper.GetScreenMode();
		int nHeight = 0;
		switch(nMode)
		{
			case CGameHelper.m_SMode_HVQA_P:
			case CGameHelper.m_SMode_VQA_LARGE:
				nHeight = m_LARGE_CARD_HEIGHT;
				break;
			case CGameHelper.m_SMode_HVQA_L:
				nHeight = m_LARGE_CARD_WIDTH;
				break;
			case CGameHelper.m_SMode_QVQA_P:
				nHeight = m_LARGE_CARD_HEIGHT*3/4;
				break;
			case CGameHelper.m_SMode_QVQA_L:
				nHeight = (m_LARGE_CARD_WIDTH+1)*3/4;
				break;
		}
		return nHeight;
	}

	public static Rect GetCardLayoutSize()
	{
		Rect rect = new Rect(0, 0, 0, 0);
		
		int nMode = CGameHelper.GetScreenMode();
		switch(nMode)
		{
			case CGameHelper.m_SMode_HVQA_P:
			case CGameHelper.m_SMode_VQA_LARGE:
				rect.bottom = m_LARGE_CARD_HEIGHT;
				rect.right = m_LARGE_CARD_WIDTH;
				break;
			case CGameHelper.m_SMode_HVQA_L:
				rect.bottom = m_LARGE_CARD_WIDTH;
				rect.right = m_LARGE_CARD_HEIGHT;
				break;
			case CGameHelper.m_SMode_QVQA_P:
				rect.bottom = m_LARGE_CARD_HEIGHT*3/4;
				rect.right = (m_LARGE_CARD_WIDTH+1)*3/4;
				break;
			case CGameHelper.m_SMode_QVQA_L:
				rect.bottom = (m_LARGE_CARD_WIDTH+1)*3/4;
				rect.right = m_LARGE_CARD_HEIGHT*3/4;
				break;
		}
		return rect;
		
	}

	
	public static Rect GetAverageDigitSize()
	{
		Rect rect = new Rect(0, 0, 0, 0);
		
		rect.bottom = m_LARGE_SZI_HEIGHT;
		rect.right = m_LARGE_SZI_WIDTH;
		if(m_TempCardNumberBitmap != null)
		{
			rect.bottom = m_TempCardNumberBitmap.getHeight();
			rect.right = m_TempCardNumberBitmap.getWidth()/10;
		}
		return rect;
	}

	private static Bitmap m_SignsBitmap = null;//Update to reduce buffer size
	public static Bitmap GetSignsBitmap()
	{
		Bitmap signBmp = null;
		if(CGameHelper.m_GameContext != null)
		{
	        Resources res = CGameHelper.m_GameContext.getResources();
	        if(res == null)
	        	return signBmp;
	        
	        if(m_SignsBitmap == null)
	        {	
	        	m_SignsBitmap = BitmapFactory.decodeResource(res, R.drawable.signs);
	        }
	        signBmp = m_SignsBitmap;
		}
		return signBmp;
	}	
	
	public static int GetSignImageWidth()
	{
		int width = m_LARGE_SIGN_WIDTH;
		if(m_SignsBitmap != null)
		{
			width = m_SignsBitmap.getWidth()/8;
		}
		return width;
	}

	public static int GetSignImageHeight()
	{
		int height = m_LARGE_SIGN_HEIGHT;
		if(m_SignsBitmap != null)
		{
			height = m_SignsBitmap.getHeight()/2;
		}
		return height;
	}

	public static int GetRealSignWidth()
	{
		int width = 0;
		int nMode = CGameHelper.GetScreenMode();
		switch(nMode)
		{
			case CGameHelper.m_SMode_HVQA_P:
			case CGameHelper.m_SMode_VQA_LARGE:
				width = m_LARGE_SIGN_WIDTH;
				break;
			case CGameHelper.m_SMode_HVQA_L:
				width = m_LARGE_SIGN_HEIGHT;
				break;
			case CGameHelper.m_SMode_QVQA_P:
				width = m_LARGE_SIGN_WIDTH*3/4;
				break;
			case CGameHelper.m_SMode_QVQA_L:
				width = m_LARGE_SIGN_HEIGHT*3/4;
				break;
		}
		return width;
	}

	public static int GetRealSignHeight()
	{
		int nMode = CGameHelper.GetScreenMode();
		int height = 0;
		switch(nMode)
		{
			case CGameHelper.m_SMode_HVQA_P:
			case CGameHelper.m_SMode_VQA_LARGE:
				height = m_LARGE_SIGN_HEIGHT;
				break;
			case CGameHelper.m_SMode_HVQA_L:
				height = m_LARGE_SIGN_HEIGHT;
				break;
			case CGameHelper.m_SMode_QVQA_P:
				 height = m_LARGE_SIGN_HEIGHT*3/4;
				break;
			case CGameHelper.m_SMode_QVQA_L:
				height = m_LARGE_SIGN_HEIGHT*3/4;
				break;
		}
		return height;
	}
	
	public static int GetDealImageWidth()
	{
		int width = m_LARGE_DEAL_WIDTH;
		return width;
	}

	public static int GetDealImageHeight()
	{
		int height = m_LARGE_DEAL_HEIGHT;
		return height;
	}

	public static int GetRealDealWidth()
	{
		int width = 0;
		int nMode = CGameHelper.GetScreenMode();
		switch(nMode)
		{
			case CGameHelper.m_SMode_HVQA_P:
			case CGameHelper.m_SMode_VQA_LARGE:
				width = m_LARGE_DEAL_WIDTH;
				break;
			case CGameHelper.m_SMode_HVQA_L:
				width = m_LARGE_DEAL_WIDTH;
				break;
			case CGameHelper.m_SMode_QVQA_P:
				width = m_LARGE_DEAL_WIDTH*3/4;
				break;
			case CGameHelper.m_SMode_QVQA_L:
				width = m_LARGE_DEAL_WIDTH*3/4;
				break;
		}
		return width;
	}

	public static int GetRealDealHeight()
	{
		int nMode = CGameHelper.GetScreenMode();
		int height = 0;
		switch(nMode)
		{
			case CGameHelper.m_SMode_HVQA_P:
			case CGameHelper.m_SMode_VQA_LARGE:
				height = m_LARGE_DEAL_HEIGHT;
				break;
			case CGameHelper.m_SMode_HVQA_L:
				height = m_LARGE_DEAL_HEIGHT;
				break;
			case CGameHelper.m_SMode_QVQA_P:
				 height = m_LARGE_DEAL_HEIGHT*3/4;
				break;
			case CGameHelper.m_SMode_QVQA_L:
				height = m_LARGE_DEAL_HEIGHT*3/4;
				break;
		}
		return height;
	}
	
	public static ShapeDrawable GetPhantomCardDrawable()
	{
        float[] outerR = new float[8];
        RectF   inset = new RectF(0, 0, 0, 0);
        float[] innerR = new float[8];
        
        int i;
        for(i = 0; i < 8; ++i)
        {
        	outerR[i] = CDealLayout.m_OuterR;
        	innerR[i] = CDealLayout.m_InnerR;
        }
        
        inset.left = CDealLayout.m_Inset;
        inset.top = CDealLayout.m_Inset;
        inset.right = CDealLayout.m_Inset;
        inset.bottom = CDealLayout.m_Inset;
        
        ShapeDrawable shapeimage = new ShapeDrawable(new RoundRectShape(outerR, inset, innerR));
        
        return shapeimage;
	}

	public static RoundRectShape GetPhantomCardDShape()
	{
        float[] outerR = new float[8];
        RectF   inset = new RectF(0, 0, 0, 0);
        float[] innerR = new float[8];
        
        int i;
        for(i = 0; i < 8; ++i)
        {
        	outerR[i] = CDealLayout.m_OuterR;
        	innerR[i] = CDealLayout.m_InnerR;
        }
        
        RoundRectShape shape = new RoundRectShape(outerR, inset, innerR);
        
        return shape;
	}
	
	public static void DrawPhantomCard(Canvas canvas, Rect rect)
	{
		ShapeDrawable shape = GetPhantomCardDrawable();
		shape.setBounds(rect);
		shape.draw(canvas);
	}
	
	public static void DrawHighligCursor(Canvas canvas, Paint CursorPaint, CLayoutCursor cursor)
	{
		Rect rect = CDealLayout.GetBoundary(cursor);
        canvas.drawRect(rect, CursorPaint);
	}

	public static void DrawHighligCursor(Canvas canvas, Paint CursorPaint, Rect rect)
	{
        canvas.drawRect(rect, CursorPaint);
	}
	
	public static Drawable GetSignImage(int nIndex, boolean bPressed)
	{
		Drawable image = null;

		Bitmap signBmp = CGameHelper.GetSignsBitmap();
		if(signBmp != null)
		{
	        Resources res = CGameHelper.m_GameContext.getResources();
	        if(res == null)
	        	return image; 

	        int width = GetSignImageWidth();
	        int height = GetSignImageHeight();
	        Bitmap destBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(destBmp);
	        int y;
	        if(bPressed == false)
	        {
	        	y = 0;
	        }
	        else
	        {
	        	y = 1;
	        }
	        Rect src = new Rect(0, 0, 0, 0);
	        Rect dst = new Rect(0, 0, width, height);
	        src.left = nIndex*width; 
	        src.top = y*height;
	        src.right = src.left + width; 
	        src.bottom = src.top + height;
	        	
			//canvas.drawBitmap(signBmp, src, dst, myPaint);
			canvas.drawBitmap(signBmp, src, dst, null);
			BitmapDrawable bmpimage = new BitmapDrawable(res, destBmp);
			image = (Drawable)bmpimage;	
		}
		
		return image;
	}
	
	public static int GetDealSpriteFrames()
	{
		return 5;
	}

	private static Bitmap m_DealSpriteBitmap = null;//Update to reduce buffer size
	public static Bitmap GetDealSpriteBitmap()
	{
		// Load background desktop image from resouce
		Bitmap image = null;
		if(CGameHelper.m_GameContext != null)
		{
			Resources res = CGameHelper.m_GameContext.getResources();
			if(res == null)
				return image; 

			if(m_DealSpriteBitmap == null)
			{
				m_DealSpriteBitmap = BitmapFactory.decodeResource(res, R.drawable.face);
			}
			
			image = m_DealSpriteBitmap;
		} 
		return image;
	}
	
	public static int GetDealSpriteImageWidth()
	{
		int nWidth = 50;
		
		if(m_DealSpriteBitmap != null)
		{
			nWidth = m_DealSpriteBitmap.getWidth()/5;
		}
		
		return nWidth;
	}
	
	public static int GetDealSpriteImageHeight()
	{
		int nHeight = 50;

		if(m_DealSpriteBitmap != null)
		{
			nHeight = m_DealSpriteBitmap.getHeight()/2;
		}
		
		return nHeight;
	}

	public static Drawable GetDealSpriteAnimationImage(int row, int nFrame)
	{
		Drawable image = null;
		if(CGameHelper.m_GameContext != null && 0 <= nFrame && nFrame < GetDealSpriteFrames())
		{
			Bitmap bitmap = null;
			
			Bitmap bmpSrc = CGameHelper.GetDealSpriteBitmap();
			if(bmpSrc != null)
			{	
		        int width = CGameHelper.GetDealSpriteImageWidth();
		        int height = CGameHelper.GetDealSpriteImageHeight();
		        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
				Canvas canvas = new Canvas(bitmap);
		        Rect dst = new Rect(0, 0, width, height);
		        Rect src = new Rect(nFrame*width, row*height, (nFrame+1)*width, (row+1)*height);
				canvas.drawBitmap(bmpSrc, src, dst, null);
				Resources res = CGameHelper.GetSystemResource();
				image = (Drawable)(new BitmapDrawable(res, bitmap));
			}
		}
		return image;
	}
	
	private static Bitmap m_ScoreSourceBitmap = null;//Update to reduce buffer size
	public static Bitmap GetScoreSourceBitmap()
	{
		Bitmap image = null;
		if(CGameHelper.m_GameContext != null)
		{
			Resources res = CGameHelper.m_GameContext.getResources();
			if(res == null)
				return image; 

			//image = BitmapFactory.decodeResource(res, R.drawable.scores);
			if(m_ScoreSourceBitmap == null)
			{
				m_ScoreSourceBitmap = BitmapFactory.decodeResource(res, R.drawable.scores);
			}
			image = m_ScoreSourceBitmap;
		} 
		return image;
	}

	public static int GetScoreImageWidth()
	{
		int nRet = 24;
		
		if(m_ScoreSourceBitmap != null)
		{
			nRet = m_ScoreSourceBitmap.getWidth()/3;
		}
		
		return nRet;
	}

	public static int GetScoreImageHeight()
	{
		int nRet = 24;
		
		if(m_ScoreSourceBitmap != null)
		{
			nRet = m_ScoreSourceBitmap.getHeight()/2;
		}
		
		return nRet;
	}
	
	public static int GetScoreAnimatorFrames()
	{
		return 3;
	}

	public static Drawable GetStarImage(int i)
	{
		Drawable image = null;

		Bitmap srcBmp = null;
		if(CGameHelper.m_GameContext != null && 0 <= i && i < GetStarAnimationFrame())
		{
	        Resources res = CGameHelper.m_GameContext.getResources();
	        if(res == null)
	        	return image; 

    		srcBmp = BitmapFactory.decodeResource(res, R.drawable.stars);
	        int width = GetStarImageWidth();
	        int height = GetStarImageHeight();
	        Bitmap destBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(destBmp);
	        Rect src = new Rect(width*i, 0, (i+1)*width, height);
	        Rect dst = new Rect(0, 0, width, height);
			canvas.drawBitmap(srcBmp, src, dst, null);
			BitmapDrawable bmpimage = new BitmapDrawable(res, destBmp);
			image = (Drawable)bmpimage;	
		}
		
		return image;
	}
    
	public static void LoadStarsImage(Vector<Drawable> stars)
	{
		for(int i = 0; i  < GetStarAnimationFrame(); ++i)
		{
			stars.add(GetStarImage(i));
		}
	}
	
	public static int GetStarAnimationFrame()
	{
		return 4;
	}

	public static int GetStarImageWidth()
	{
		return 15;
	}

	public static int GetStarImageHeight()
	{
		return 15;
	}

	public static Resources GetSystemResource()
	{
		Resources res = null;
		
		if(CGameHelper.m_GameContext != null)
		{
			res = CGameHelper.m_GameContext.getResources();
		}
		return res;
	}
	
}
