package com.xgadget.BubbleTile;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.*;
import java.util.HashMap;

public class CResourceHelper 
{
	public static Context 					m_GameContext = null;
	
	static HashMap<Integer, Bitmap> _resourceMap = null;
	
	public static Bitmap GetBitmap(int nId)
	{
		if (_resourceMap == null)
		{
			_resourceMap = new HashMap<Integer, Bitmap> ();
		}
		
		Integer id = new Integer(nId);
		Bitmap bmp = _resourceMap.get(id);
		if (bmp == null)
		{
			Resources res = GetSystemResource();
			if(res == null)
				return bmp; 
			
			bmp = BitmapFactory.decodeResource(res, id);
			_resourceMap.put(id, bmp);
		}
		return bmp;
	}
	
	public static void SetGameContext(Context gCOntext)
	{
		m_GameContext = gCOntext;
	}

	public static Context GetGameContext()
	{
		return m_GameContext;
	}

	public static boolean CanLoadResource()
	{
		return (m_GameContext != null);
	}
	
	public static Resources GetSystemResource()
	{
		Resources res = null;
		
		if(m_GameContext != null)
		{
			res = m_GameContext.getResources();
		}
		return res;
	}

	private static Drawable m_ColorBubbleImage = null; 
	public static Drawable GetColorBubbleImage()
	{
		Drawable image = null;
		
		if(m_ColorBubbleImage == null)
		{
			Resources res = GetSystemResource();
			if(res == null)
				return image;
			
			Bitmap bitmap = GetColorBubbleBitmap();
			m_ColorBubbleImage = (Drawable)(new BitmapDrawable(res, bitmap));
		}
		image = m_ColorBubbleImage;
		return image;
	}
	
	private static Bitmap m_ColorBubbleBitmap = null; 
	public static Bitmap GetColorBubbleBitmap()
	{
		Bitmap image = null;
		Resources res = GetSystemResource();
		if(res == null)
			return image; 
			
		if(m_ColorBubbleBitmap == null)
		{
			m_ColorBubbleBitmap = BitmapFactory.decodeResource(res, R.drawable.bubble);
		}
		image = m_ColorBubbleBitmap;
		return image;
	}

	private static Drawable m_StarBubbleImage = null; 
	public static Drawable GetStarBubbleImage()
	{
		Drawable image = null;
		
		if(m_StarBubbleImage == null)
		{
			Resources res = GetSystemResource();
			if(res == null)
				return image;
			
			Bitmap bitmap = GetStarBubbleBitmap();
			m_StarBubbleImage = (Drawable)(new BitmapDrawable(res, bitmap));
		}
		image = m_StarBubbleImage;
		return image;
	}
	
	private static Bitmap m_StarBubbleBitmap = null; 
	public static Bitmap GetStarBubbleBitmap()
	{
		Bitmap image = null;
		Resources res = GetSystemResource();
		if(res == null)
			return image; 
			
		if(m_StarBubbleBitmap == null)
		{
			m_StarBubbleBitmap = BitmapFactory.decodeResource(res, R.drawable.sbubble);
		}
		image = m_StarBubbleBitmap;
		return image;
	}

	private static Drawable m_FrogBubbleImage = null; 
	public static Drawable GetFrogBubbleImage()
	{
		Drawable image = null;
		
		if(m_FrogBubbleImage == null)
		{
			Resources res = GetSystemResource();
			if(res == null)
				return image;
			
			Bitmap bitmap = GetFrogBubbleBitmap();
			m_FrogBubbleImage = (Drawable)(new BitmapDrawable(res, bitmap));
		}
		image = m_FrogBubbleImage;
		return image;
	}
	
	private static Bitmap m_FrogBubbleBitmap = null; 
	public static Bitmap GetFrogBubbleBitmap()
	{
		Bitmap image = null;
		Resources res = GetSystemResource();
		if(res == null)
			return image; 
			
		if(m_FrogBubbleBitmap == null)
		{
			m_FrogBubbleBitmap = BitmapFactory.decodeResource(res, R.drawable.frogicon1);
		}
		image = m_FrogBubbleBitmap;
		
		return image;
	}

	private static Drawable m_FrogMotionBubbleImage = null; 
	public static Drawable GetFrogMotionBubbleImage()
	{
		Drawable image = null;
		
		if(m_FrogMotionBubbleImage == null)
		{
			Resources res = GetSystemResource();
			if(res == null)
				return image;
			
			Bitmap bitmap = GetFrogMotionBubbleBitmap();
			m_FrogMotionBubbleImage = (Drawable)(new BitmapDrawable(res, bitmap));
		}
		image = m_FrogMotionBubbleImage;
		return image;
	}
	
	private static Bitmap m_FrogMotionBubbleBitmap = null; 
	public static Bitmap GetFrogMotionBubbleBitmap()
	{
		Bitmap image = null;
		Resources res = GetSystemResource();
		if(res == null)
			return image; 
			
		if(m_FrogMotionBubbleBitmap == null)
		{
			m_FrogMotionBubbleBitmap = BitmapFactory.decodeResource(res, R.drawable.frogicon2);
		}
		image = m_FrogMotionBubbleBitmap;
		
		return image;
	}
	
	private static Bitmap m_WoodTextureBitmap = null; 
	public static Bitmap GetWoodTextureBitmap()
	{
		Bitmap image = null;
		Resources res = GetSystemResource();
		if(res == null)
			return image; 
			
		if(m_WoodTextureBitmap == null)
		{
			m_WoodTextureBitmap = BitmapFactory.decodeResource(res, R.drawable.bktex);
		}
		image = m_WoodTextureBitmap;
		
		return image;
	}

    public static BitmapDrawable GetDefaultNumberImage(int number, int color)
    {
		String str = new String("");
		Integer Value = new Integer(number);
		str = Value.toString();
    	
		BitmapDrawable image = GetDefaultTextImage(str, color);
		return image;
    }
	
    public static BitmapDrawable GetDefaultTextImage(String text, int color)
    {
		BitmapDrawable image = null;
		Bitmap bitmap = CreateDefaultTextBitmap(text, color);
		if(bitmap != null)
		{
			Resources res = GetSystemResource();
			image = new BitmapDrawable(res, bitmap);
		}
		return image;
    }
    
    public static Bitmap CreateDefaultTextBitmap(String text, int color)
    {
		Bitmap bitmap = null;

        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setTextSize(480);
        mPaint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.BOLD));
		
        Rect    bounds = new Rect();
        mPaint.measureText(text, 0, text.length());
        mPaint.getTextBounds(text, 0, text.length(), bounds);

        int nWidth = bounds.width()+10;
        int nHeight = bounds.height()+10;

        bitmap = Bitmap.createBitmap(nWidth, nHeight, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
        
		canvas.save();
		canvas.translate(bounds.left, bounds.bottom);
		canvas.scale(1, -1);
        mPaint.setColor(color);
        canvas.drawText(text, 0, 0, mPaint);
        canvas.restore();

        Bitmap bitmap2 = Bitmap.createBitmap(nWidth, nHeight, Bitmap.Config.ARGB_8888);
		Canvas canvas2 = new Canvas(bitmap2);
        
		canvas2.translate(0, nHeight);
		canvas2.scale(1, -1);
		
		canvas2.drawBitmap(bitmap, 0, 0, null);
		
		return bitmap2;
    }
	
}
