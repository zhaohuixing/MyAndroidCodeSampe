package com.xgadget.uimodule;

//import java.util.Vector;

//import android.annotation.SuppressLint;
import java.util.HashMap;

import com.xgadget.ChuiNiuLite.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
//import android.graphics.Canvas;
import android.graphics.Paint;
//import android.graphics.Rect;
//import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.graphics.drawable.ShapeDrawable;
//import android.graphics.drawable.shapes.RoundRectShape;
//import android.util.DisplayMetrics;

@SuppressLint("UseSparseArrays")
public class ResourceHelper 
{
	public static Context 					m_ResourceContext = null;

	public static void SetResourceContext(Context gCOntext)
	{
		ResourceHelper.m_ResourceContext = gCOntext;
	}

	public static Context GetResourceContext()
	{
		return ResourceHelper.m_ResourceContext;
	}

	public static Bitmap GetBitmapByResourceID(int resID)
	{
		Bitmap bitmap = null;
		if(m_ResourceContext != null)
		{
	        Resources res = m_ResourceContext.getResources();
	        if(res == null)
	        	return bitmap;
	        
	        bitmap = BitmapFactory.decodeResource(res, resID);
			
		}
		
		return bitmap;
	}
	
	public static Resources GetResources()
	{
		return ResourceHelper.m_ResourceContext.getResources();
	}
	
	public static boolean CanLoadResource()
	{
		return (ResourceHelper.m_ResourceContext != null);
	}
	
	private static Bitmap m_RedTextureBitmap = null;
	public static Bitmap GetRedTextureBitmap()
	{
		if(m_RedTextureBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_RedTextureBitmap;
		        
		        m_RedTextureBitmap = BitmapFactory.decodeResource(res, R.drawable.redtext);
				
			}
		}
		return m_RedTextureBitmap;	
	}

	public static BitmapDrawable GetRedTextureDrawable()
	{
		BitmapDrawable retDrawable = null;
		if(m_ResourceContext != null)
		{
			Resources res = m_ResourceContext.getResources();
		    if(res == null)
		        return retDrawable;
		        
		    retDrawable = (BitmapDrawable)res.getDrawable(R.drawable.redtext);
		}
		return retDrawable;	
	}
	
	private static Paint	m_RedTexturePaint = null;
	public static Paint GetRedTexturePaint()
	{
		if(m_RedTexturePaint == null)
		{
			m_RedTexturePaint = new Paint();
			m_RedTexturePaint.setAntiAlias(true);
			Bitmap mBitmap = ResourceHelper.GetRedTextureBitmap();
			BitmapShader mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);
			m_RedTexturePaint.setShader(mBitmapShader);	
		}
		return m_RedTexturePaint;
	}

	private static Bitmap m_BlueTextureBitmap = null;
	public static Bitmap GetBlueTextureBitmap()
	{
		if(m_BlueTextureBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_BlueTextureBitmap;
		        
		        m_BlueTextureBitmap = BitmapFactory.decodeResource(res, R.drawable.bluetex);
				
			}
		}
		return m_BlueTextureBitmap;	
	}
	
	private static Paint	m_BlueTexturePaint = null;
	public static Paint GetBlueTexturePaint()
	{
		if(m_BlueTexturePaint == null)
		{
			m_BlueTexturePaint = new Paint();
			m_BlueTexturePaint.setAntiAlias(true);
			Bitmap mBitmap = ResourceHelper.GetBlueTextureBitmap();
			BitmapShader mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);
			m_BlueTexturePaint.setShader(mBitmapShader);	
		}
		return m_BlueTexturePaint;
	}

	private static Bitmap m_GreenTextureBitmap = null;
	public static Bitmap GetGreenTextureBitmap()
	{
		if(m_GreenTextureBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_GreenTextureBitmap;
		        
		        m_GreenTextureBitmap = BitmapFactory.decodeResource(res, R.drawable.greentex);
				
			}
		}
		return m_GreenTextureBitmap;	
	}

	private static Paint	m_GreenTexturePaint = null;
	public static Paint GetGreenTexturePaint()
	{
		if(m_GreenTexturePaint == null)
		{
			m_GreenTexturePaint = new Paint();
			m_GreenTexturePaint.setAntiAlias(true);
			Bitmap mBitmap = ResourceHelper.GetGreenTextureBitmap();
			BitmapShader mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);
			m_GreenTexturePaint.setShader(mBitmapShader);	
		}
		return m_GreenTexturePaint;
	}
	
	private static Bitmap m_BrownTextureBitmap = null;
	public static Bitmap GetBrownTextureBitmap()
	{
		if(m_BrownTextureBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_BrownTextureBitmap;
		        
		        m_BrownTextureBitmap = BitmapFactory.decodeResource(res, R.drawable.browntex);
				
			}
		}
		return m_BrownTextureBitmap;	
	}

	private static Paint	m_BrownTexturePaint = null;
	public static Paint GetBrownTexturePaint()
	{
		if(m_BrownTexturePaint == null)
		{
			m_BrownTexturePaint = new Paint();
			m_BrownTexturePaint.setAntiAlias(true);
			Bitmap mBitmap = ResourceHelper.GetBrownTextureBitmap();
			BitmapShader mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);
			m_BrownTexturePaint.setShader(mBitmapShader);	
		}
		return m_BrownTexturePaint;
	}
	
	private static Paint	m_PointerTransparentPaint = null;
	public static Paint GetMostTransparentPointerPaint()
	{
		if(m_PointerTransparentPaint == null)
		{
			m_PointerTransparentPaint = new Paint();
			m_PointerTransparentPaint.setAntiAlias(true);
			m_PointerTransparentPaint.setAlpha(77);	
		}
		return m_PointerTransparentPaint;
	}
	
	private static Bitmap m_BlueCloseButtonBitmap = null;
	public static Bitmap GetBlueCloseButtonBitmap()
	{
		if(m_BlueCloseButtonBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_BlueCloseButtonBitmap;
		        
		        m_BlueCloseButtonBitmap = BitmapFactory.decodeResource(res, R.drawable.blueclose);
			}
		}
		return m_BlueCloseButtonBitmap;	
	}

	
}
