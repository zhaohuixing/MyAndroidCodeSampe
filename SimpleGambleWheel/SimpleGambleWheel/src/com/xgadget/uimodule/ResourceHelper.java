package com.xgadget.uimodule;

//import java.util.Vector;

//import android.annotation.SuppressLint;
import java.util.HashMap;

import com.xgadget.SimpleGambleWheel.R;

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
	
	private static Bitmap m_Compass8Bitmap = null;
	public static Bitmap GetCompass8Bitmap()
	{
		if(m_Compass8Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_Compass8Bitmap;
		        
		        m_Compass8Bitmap = BitmapFactory.decodeResource(res, R.drawable.compass8);
			}
		}
		return m_Compass8Bitmap;	
	}

	private static Bitmap m_Compass6Bitmap = null;
	public static Bitmap GetCompass6Bitmap()
	{
		if(m_Compass6Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_Compass6Bitmap;
		        
		        m_Compass6Bitmap = BitmapFactory.decodeResource(res, R.drawable.compass6);
			}
		}
		return m_Compass6Bitmap;	
	}

	private static Bitmap m_Compass4Bitmap = null;
	public static Bitmap GetCompass4Bitmap()
	{
		if(m_Compass4Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_Compass4Bitmap;
		        
		        m_Compass4Bitmap = BitmapFactory.decodeResource(res, R.drawable.compass4);
			}
		}
		return m_Compass4Bitmap;	
	}

	private static Bitmap m_Compass2Bitmap = null;
	public static Bitmap GetCompass2Bitmap()
	{
		if(m_Compass2Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_Compass2Bitmap;
		        
		        m_Compass2Bitmap = BitmapFactory.decodeResource(res, R.drawable.compass2);
			}
		}
		return m_Compass2Bitmap;	
	}
	
	public static int GetCompassBitmapSize()
	{
		if(m_Compass8Bitmap != null)
		{
			return m_Compass8Bitmap.getWidth(); 
		}
		else
		{
			Bitmap bitmap = ResourceHelper.GetCompass8Bitmap();
			if(bitmap != null)
				return bitmap.getWidth();
			else
				return 0;
		}
	}

	private static Bitmap m_PointerBitmap = null;
	public static Bitmap GetPointerBitmap()
	{
		if(m_PointerBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_PointerBitmap;
		        
		        m_PointerBitmap = BitmapFactory.decodeResource(res, R.drawable.pointer);
			}
		}
		return m_PointerBitmap;	
	}
	
	public static int GetPointerBitmapWidth()
	{
		if(m_PointerBitmap != null)
		{
			return m_PointerBitmap.getWidth(); 
		}
		else
		{
			Bitmap bitmap = ResourceHelper.GetPointerBitmap();
			if(bitmap != null)
				return bitmap.getWidth();
			else
				return 0;
		}
	}

	public static int GetPointerBitmapHeight()
	{
		if(m_PointerBitmap != null)
		{
			return m_PointerBitmap.getHeight(); 
		}
		else
		{
			Bitmap bitmap = ResourceHelper.GetPointerBitmap();
			if(bitmap != null)
				return bitmap.getHeight();
			else
				return 0;
		}
	}
	
	private static Bitmap m_SystemButtonBitmap = null;
	public static Bitmap GetSystemButtonBitmap()
	{
		if(m_SystemButtonBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_SystemButtonBitmap;
		        
		        m_SystemButtonBitmap = BitmapFactory.decodeResource(res, R.drawable.menuicon);
			}
		}
		return m_SystemButtonBitmap;	
	}

	private static Bitmap m_OnlineOnButtonBitmap = null;
	public static Bitmap GetOnlineOnButtonBitmap()
	{
		if(m_OnlineOnButtonBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_OnlineOnButtonBitmap;
		        
		        m_OnlineOnButtonBitmap = BitmapFactory.decodeResource(res, R.drawable.lobbyicon);
			}
		}
		return m_OnlineOnButtonBitmap;	
	}

	private static Bitmap m_OnlineOffButtonBitmap = null;
	public static Bitmap GetOnlineOffButtonBitmap()
	{
		if(m_OnlineOffButtonBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_OnlineOffButtonBitmap;
		        
		        m_OnlineOffButtonBitmap = BitmapFactory.decodeResource(res, R.drawable.lobbystopicon);
			}
		}
		return m_OnlineOffButtonBitmap;	
	}
	
	public static int GetToolbarButtonImageWidth()
	{
		Bitmap bitmap = ResourceHelper.GetSystemButtonBitmap();
		if(bitmap != null)
			return bitmap.getWidth();
		
		return 0;
	}
	
	public static int GetToolbarButtonImageHeight()
	{
		Bitmap bitmap = ResourceHelper.GetSystemButtonBitmap();
		if(bitmap != null)
			return bitmap.getHeight();
		
		return 0;
	}
	
	private static Bitmap m_MyCryBitmap1 = null;
	public static Bitmap GetMyCryBitmap1()
	{
		if(m_MyCryBitmap1 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_MyCryBitmap1;
		        
		        m_MyCryBitmap1 = BitmapFactory.decodeResource(res, R.drawable.mecryicon1);
			}
		}
		return m_MyCryBitmap1;	
	}
	
	private static Bitmap m_MyCryBitmap2 = null;
	public static Bitmap GetMyCryBitmap2()
	{
		if(m_MyCryBitmap2 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_MyCryBitmap2;
		        
		        m_MyCryBitmap2 = BitmapFactory.decodeResource(res, R.drawable.mecryicon2);
			}
		}
		return m_MyCryBitmap2;	
	}
	
	private static Bitmap m_MyCryBitmap3 = null;
	public static Bitmap GetMyCryBitmap3()
	{
		if(m_MyCryBitmap3 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_MyCryBitmap3;
		        
		        m_MyCryBitmap3 = BitmapFactory.decodeResource(res, R.drawable.mecryicon3);
			}
		}
		return m_MyCryBitmap3;	
	}

	public static Bitmap GetMyCryBitmap(int nIndex)
	{
		if(nIndex == 1)
		{
			return GetMyCryBitmap2();
		}
		else if(nIndex == 2)
		{
			return GetMyCryBitmap3();
		}
		else
		{
			return GetMyCryBitmap1();
		}
	}
	
	private static Bitmap m_MyIdleBitmap1 = null;
	public static Bitmap GetMyIdleBitmap1()
	{
		if(m_MyIdleBitmap1 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_MyIdleBitmap1;
		        
		        m_MyIdleBitmap1 = BitmapFactory.decodeResource(res, R.drawable.meidleicon1);
			}
		}
		return m_MyIdleBitmap1;	
	}

	private static Bitmap m_MyIdleBitmap2 = null;
	public static Bitmap GetMyIdleBitmap2()
	{
		if(m_MyIdleBitmap2 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_MyIdleBitmap2;
		        
		        m_MyIdleBitmap2 = BitmapFactory.decodeResource(res, R.drawable.meidleicon2);
			}
		}
		return m_MyIdleBitmap2;	
	}

	private static Bitmap m_MyIdleBitmap3 = null;
	public static Bitmap GetMyIdleBitmap3()
	{
		if(m_MyIdleBitmap3 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_MyIdleBitmap3;
		        
		        m_MyIdleBitmap3 = BitmapFactory.decodeResource(res, R.drawable.meidleicon3);
			}
		}
		return m_MyIdleBitmap3;	
	}
	
	public static Bitmap GetMyIdleBitmap(int nIndex)
	{
		if(nIndex == 1)
		{
			return GetMyIdleBitmap2();
		}
		else if(nIndex == 2)
		{
			return GetMyIdleBitmap3();
		}
		else
		{
			return GetMyIdleBitmap1();
		}
	}
	
	private static Bitmap m_MyPlayBitmap1 = null;
	public static Bitmap GetMyPlayBitmap1()
	{
		if(m_MyPlayBitmap1 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_MyPlayBitmap1;
		        
		        m_MyPlayBitmap1 = BitmapFactory.decodeResource(res, R.drawable.meplayicon1);
			}
		}
		return m_MyPlayBitmap1;	
	}

	private static Bitmap m_MyPlayBitmap2 = null;
	public static Bitmap GetMyPlayBitmap2()
	{
		if(m_MyPlayBitmap2 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_MyPlayBitmap2;
		        
		        m_MyPlayBitmap2 = BitmapFactory.decodeResource(res, R.drawable.meplayicon2);
			}
		}
		return m_MyPlayBitmap2;	
	}
	
	private static Bitmap m_MyPlayBitmap3 = null;
	public static Bitmap GetMyPlayBitmap3()
	{
		if(m_MyPlayBitmap3 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_MyPlayBitmap3;
		        
		        m_MyPlayBitmap3 = BitmapFactory.decodeResource(res, R.drawable.meplayicon3);
			}
		}
		return m_MyPlayBitmap3;	
	}

	public static Bitmap GetMyPlayBitmap(int nIndex)
	{
		if(nIndex == 1)
		{
			return GetMyPlayBitmap2();
		}
		else if(nIndex == 2)
		{
			return GetMyPlayBitmap3();
		}
		else
		{
			return GetMyPlayBitmap1();
		}
	}
	
	private static Bitmap m_MyWinBitmap1 = null;
	public static Bitmap GetMyWinBitmap1()
	{
		if(m_MyWinBitmap1 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_MyWinBitmap1;
		        
		        m_MyWinBitmap1 = BitmapFactory.decodeResource(res, R.drawable.mewinicon1);
			}
		}
		return m_MyWinBitmap1;	
	}

	private static Bitmap m_MyWinBitmap2 = null;
	public static Bitmap GetMyWinBitmap2()
	{
		if(m_MyWinBitmap2 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_MyWinBitmap2;
		        
		        m_MyWinBitmap2 = BitmapFactory.decodeResource(res, R.drawable.mewinicon2);
			}
		}
		return m_MyWinBitmap2;	
	}

	private static Bitmap m_MyWinBitmap3 = null;
	public static Bitmap GetMyWinBitmap3()
	{
		if(m_MyWinBitmap3 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_MyWinBitmap3;
		        
		        m_MyWinBitmap3 = BitmapFactory.decodeResource(res, R.drawable.mewinicon3);
			}
		}
		return m_MyWinBitmap3;	
	}

	public static Bitmap GetMyWinBitmap(int nIndex)
	{
		if(nIndex == 1)
		{
			return GetMyWinBitmap2();
		}
		else if(nIndex == 2)
		{
			return GetMyWinBitmap3();
		}
		else
		{
			return GetMyWinBitmap1();
		}
	}
	
	private static Bitmap m_RoPaCryBitmap1 = null;
	public static Bitmap GetRoPaCryBitmap1()
	{
		if(m_RoPaCryBitmap1 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_RoPaCryBitmap1;
		        
		        m_RoPaCryBitmap1 = BitmapFactory.decodeResource(res, R.drawable.ropacryicon1);
			}
		}
		return m_RoPaCryBitmap1;	
	}

	private static Bitmap m_RoPaCryBitmap2 = null;
	public static Bitmap GetRoPaCryBitmap2()
	{
		if(m_RoPaCryBitmap2 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_RoPaCryBitmap2;
		        
		        m_RoPaCryBitmap2 = BitmapFactory.decodeResource(res, R.drawable.ropacryicon2);
			}
		}
		return m_RoPaCryBitmap2;	
	}

	private static Bitmap m_RoPaCryBitmap3 = null;
	public static Bitmap GetRoPaCryBitmap3()
	{
		if(m_RoPaCryBitmap3 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_RoPaCryBitmap3;
		        
		        m_RoPaCryBitmap3 = BitmapFactory.decodeResource(res, R.drawable.ropacryicon3);
			}
		}
		return m_RoPaCryBitmap3;	
	}

	public static Bitmap GetRoPaCryBitmap(int nIndex)
	{
		if(nIndex == 1)
		{
			return GetRoPaCryBitmap2();
		}
		else if(nIndex == 2)
		{
			return GetRoPaCryBitmap3();
		}
		else
		{
			return GetRoPaCryBitmap1();
		}
	}
	
	private static Bitmap m_RoPaIdleBitmap1 = null;
	public static Bitmap GetRoPaIdleBitmap1()
	{
		if(m_RoPaIdleBitmap1 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_RoPaIdleBitmap1;
		        
		        m_RoPaIdleBitmap1 = BitmapFactory.decodeResource(res, R.drawable.ropaidleicon1);
			}
		}
		return m_RoPaIdleBitmap1;	
	}

	private static Bitmap m_RoPaIdleBitmap2 = null;
	public static Bitmap GetRoPaIdleBitmap2()
	{
		if(m_RoPaIdleBitmap2 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_RoPaIdleBitmap2;
		        
		        m_RoPaIdleBitmap2 = BitmapFactory.decodeResource(res, R.drawable.ropaidleicon2);
			}
		}
		return m_RoPaIdleBitmap2;	
	}

	private static Bitmap m_RoPaIdleBitmap3 = null;
	public static Bitmap GetRoPaIdleBitmap3()
	{
		if(m_RoPaIdleBitmap3 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_RoPaIdleBitmap3;
		        
		        m_RoPaIdleBitmap3 = BitmapFactory.decodeResource(res, R.drawable.ropaidleicon3);
			}
		}
		return m_RoPaIdleBitmap3;	
	}

	public static Bitmap GetRoPaIdleBitmap(int nIndex)
	{
		if(nIndex == 1)
		{
			return GetRoPaIdleBitmap2();
		}
		else if(nIndex == 2)
		{
			return GetRoPaIdleBitmap3();
		}
		else
		{
			return GetRoPaIdleBitmap1();
		}
	}

	private static Bitmap m_RoPaPlayBitmap1 = null;
	public static Bitmap GetRoPaPlayBitmap1()
	{
		if(m_RoPaPlayBitmap1 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_RoPaPlayBitmap1;
		        
		        m_RoPaPlayBitmap1 = BitmapFactory.decodeResource(res, R.drawable.ropaplayicon1);
			}
		}
		return m_RoPaPlayBitmap1;	
	}

	private static Bitmap m_RoPaPlayBitmap2 = null;
	public static Bitmap GetRoPaPlayBitmap2()
	{
		if(m_RoPaPlayBitmap2 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_RoPaPlayBitmap2;
		        
		        m_RoPaPlayBitmap2 = BitmapFactory.decodeResource(res, R.drawable.ropaplayicon2);
			}
		}
		return m_RoPaPlayBitmap2;	
	}

	private static Bitmap m_RoPaPlayBitmap3 = null;
	public static Bitmap GetRoPaPlayBitmap3()
	{
		if(m_RoPaPlayBitmap3 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_RoPaPlayBitmap3;
		        
		        m_RoPaPlayBitmap3 = BitmapFactory.decodeResource(res, R.drawable.ropaplayicon3);
			}
		}
		return m_RoPaPlayBitmap3;	
	}

	public static Bitmap GetRoPaPlayBitmap(int nIndex)
	{
		if(nIndex == 1)
		{
			return GetRoPaPlayBitmap2();
		}
		else if(nIndex == 2)
		{
			return GetRoPaPlayBitmap3();
		}
		else
		{
			return GetRoPaPlayBitmap1();
		}
	}
	
	private static Bitmap m_RoPaWinBitmap1 = null;
	public static Bitmap GetRoPaWinBitmap1()
	{
		if(m_RoPaWinBitmap1 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_RoPaWinBitmap1;
		        
		        m_RoPaWinBitmap1 = BitmapFactory.decodeResource(res, R.drawable.ropawinicon1);
			}
		}
		return m_RoPaWinBitmap1;	
	}
	
	private static Bitmap m_RoPaWinBitmap2 = null;
	public static Bitmap GetRoPaWinBitmap2()
	{
		if(m_RoPaWinBitmap2 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_RoPaWinBitmap2;
		        
		        m_RoPaWinBitmap2 = BitmapFactory.decodeResource(res, R.drawable.ropawinicon2);
			}
		}
		return m_RoPaWinBitmap2;	
	}

	private static Bitmap m_RoPaWinBitmap3 = null;
	public static Bitmap GetRoPaWinBitmap3()
	{
		if(m_RoPaWinBitmap3 == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_RoPaWinBitmap3;
		        
		        m_RoPaWinBitmap3 = BitmapFactory.decodeResource(res, R.drawable.ropawinicon3);
			}
		}
		return m_RoPaWinBitmap3;	
	}

	public static Bitmap GetRoPaWinBitmap(int nIndex)
	{
		if(nIndex == 1)
		{
			return GetRoPaWinBitmap2();
		}
		else if(nIndex == 2)
		{
			return GetRoPaWinBitmap3();
		}
		else
		{
			return GetRoPaWinBitmap1();
		}
	}
	
	public static Bitmap GetRoPaIconBitmap()
	{
		return GetRoPaIdleBitmap1();
	}

	public static Bitmap GetMyIconBitmap()
	{
		return GetMyIdleBitmap1();
	}

	private static Bitmap m_PledgeSignBitmap = null;
	public static Bitmap GetPledgeSignBitmap()
	{
		if(m_PledgeSignBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_PledgeSignBitmap;
		        
		        m_PledgeSignBitmap = BitmapFactory.decodeResource(res, R.drawable.chipicon);
			}
		}
		return m_PledgeSignBitmap;	
	}

	private static Bitmap m_LuckyNumberSignBitmap = null;
	public static Bitmap GetLuckyNumberSignBitmap()
	{
		if(m_LuckyNumberSignBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_LuckyNumberSignBitmap;
		        
		        m_LuckyNumberSignBitmap = BitmapFactory.decodeResource(res, R.drawable.luckicon);
			}
		}
		return m_LuckyNumberSignBitmap;	
	}

	private static Bitmap m_LuckyIcon2Bitmap = null;
	public static Bitmap GetLuckyIcon2Bitmap()
	{
		if(m_LuckyIcon2Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_LuckyIcon2Bitmap;
		        
		        m_LuckyIcon2Bitmap = BitmapFactory.decodeResource(res, R.drawable.luckicon2);
			}
		}
		return m_LuckyIcon2Bitmap;	
	}
	
	private static Bitmap m_CrossBitmap = null;
	public static Bitmap GetCrossBitmap()
	{
		if(m_CrossBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_CrossBitmap;
		        
		        m_CrossBitmap = BitmapFactory.decodeResource(res, R.drawable.cross);
			}
		}
		return m_CrossBitmap;	
	}

	private static Bitmap m_BalanceBitmap = null;
	public static Bitmap GetBalanceBitmap()
	{
		if(m_CrossBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_BalanceBitmap;
		        
		        m_BalanceBitmap = BitmapFactory.decodeResource(res, R.drawable.cashocticon);
			}
		}
		return m_BalanceBitmap;	
	}

	private static Bitmap m_CloseButtonBitmap = null;
	public static Bitmap GetCloseButtonBitmap()
	{
		if(m_CloseButtonBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_CloseButtonBitmap;
		        
		        m_CloseButtonBitmap = BitmapFactory.decodeResource(res, R.drawable.closeicon);
			}
		}
		return m_CloseButtonBitmap;	
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
	
	private static Bitmap m_PurchaseButtonBitmap = null;
	public static Bitmap GetPurchaseButtonBitmap()
	{
		if(m_PurchaseButtonBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_PurchaseButtonBitmap;
		        
		        m_PurchaseButtonBitmap = BitmapFactory.decodeResource(res, R.drawable.buybtn);
			}
		}
		return m_PurchaseButtonBitmap;	
	}

	private static Bitmap m_OKButtonBitmap = null;
	public static Bitmap GetOKButtonBitmap()
	{
		if(m_OKButtonBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_OKButtonBitmap;
		        
		        m_OKButtonBitmap = BitmapFactory.decodeResource(res, R.drawable.okicon);
			}
		}
		return m_OKButtonBitmap;	
	}
	
	private static Bitmap m_SendButtonBitmap = null;
	public static Bitmap GetSendButtonBitmap()
	{
		if(m_SendButtonBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_SendButtonBitmap;
		        
		        m_SendButtonBitmap = BitmapFactory.decodeResource(res, R.drawable.sendbtn);
			}
		}
		return m_SendButtonBitmap;	
	}
	
	
	private static Bitmap m_MyBetEnableHeadBitmap = null;
	public static Bitmap GetMyBetEnableHeadBitmap()
	{
		if(m_MyBetEnableHeadBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_MyBetEnableHeadBitmap;
		        
		        m_MyBetEnableHeadBitmap = BitmapFactory.decodeResource(res, R.drawable.redeem1icon);
			}
		}
		return m_MyBetEnableHeadBitmap;	
	}

	private static Bitmap m_MyBetDisableHeadBitmap = null;
	public static Bitmap GetMyBetDisableHeadBitmap()
	{
		if(m_MyBetDisableHeadBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_MyBetDisableHeadBitmap;
		        
		        m_MyBetDisableHeadBitmap = BitmapFactory.decodeResource(res, R.drawable.redeem1disableicon);
			}
		}
		return m_MyBetDisableHeadBitmap;	
	}

	private static Bitmap m_RoPaBetEnableHeadBitmap = null;
	public static Bitmap GetRoPaBetEnableHeadBitmap()
	{
		if(m_RoPaBetEnableHeadBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_RoPaBetEnableHeadBitmap;
		        
		        m_RoPaBetEnableHeadBitmap = BitmapFactory.decodeResource(res, R.drawable.redeem2icon);
			}
		}
		return m_RoPaBetEnableHeadBitmap;	
	}

	private static Bitmap m_RoPaBetDisableHeadBitmap = null;
	public static Bitmap GetRoPaBetDisableHeadBitmap()
	{
		if(m_RoPaBetDisableHeadBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_RoPaBetDisableHeadBitmap;
		        
		        m_RoPaBetDisableHeadBitmap = BitmapFactory.decodeResource(res, R.drawable.redeem2disableicon);
			}
		}
		return m_RoPaBetDisableHeadBitmap;	
	}

	private static Bitmap m_HighligherBitmap = null;
	public static Bitmap GetHighligherBitmap()
	{
		if(m_HighligherBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_HighligherBitmap;
		        
		        m_HighligherBitmap = BitmapFactory.decodeResource(res, R.drawable.highlighter);
			}
		}
		return m_HighligherBitmap;	
	}

	private static Bitmap m_CashBitmap = null;
	public static Bitmap GetCashBitmap()
	{
		if(m_CashBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_CashBitmap;
		        
		        m_CashBitmap = BitmapFactory.decodeResource(res, R.drawable.cashicon);
			}
		}
		return m_CashBitmap;	
	}
	
	private static Bitmap m_QMarkGreenBitmap = null;
	public static Bitmap GetQMarkGreenBitmap()
	{
		if(m_QMarkGreenBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_QMarkGreenBitmap;
		        
		        m_QMarkGreenBitmap = BitmapFactory.decodeResource(res, R.drawable.qmarkg);
			}
		}
		return m_QMarkGreenBitmap;	
	}
	
	private static Bitmap m_QMarkYellowBitmap = null;
	public static Bitmap GetQMarkYellowBitmap()
	{
		if(m_QMarkYellowBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_QMarkYellowBitmap;
		        
		        m_QMarkYellowBitmap = BitmapFactory.decodeResource(res, R.drawable.qmarky);
			}
		}
		return m_QMarkYellowBitmap;	
	}

	private static Bitmap m_RedStarBitmap = null;
	public static Bitmap GetRedStarBitmap()
	{
		if(m_RedStarBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_RedStarBitmap;
		        
		        m_RedStarBitmap = BitmapFactory.decodeResource(res, R.drawable.redstar);
			}
		}
		return m_RedStarBitmap;	
	}

	private static Bitmap m_NextButtonBitmap = null;
	public static Bitmap GetNextButtonBitmap()
	{
		if(m_NextButtonBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_NextButtonBitmap;
		        
		        m_NextButtonBitmap = BitmapFactory.decodeResource(res, R.drawable.nexticon);
			}
		}
		return m_NextButtonBitmap;	
	}
	
	private static Bitmap m_PrevButtonBitmap = null;
	public static Bitmap GetPrevButtonBitmap()
	{
		if(m_PrevButtonBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_PrevButtonBitmap;
		        
		        m_PrevButtonBitmap = BitmapFactory.decodeResource(res, R.drawable.previcon);
			}
		}
		return m_PrevButtonBitmap;	
	}
	
	private static Bitmap m_ConnectButtonBitmap = null;
	public static Bitmap GetConnectButtonBitmap()
	{
		if(m_ConnectButtonBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_ConnectButtonBitmap;
		        
		        m_ConnectButtonBitmap = BitmapFactory.decodeResource(res, R.drawable.connecticon);
			}
		}
		return m_ConnectButtonBitmap;	
	}
	
	
	
	private static Paint	m_TextRenderPaint = null;
	public static Paint GetDefaultTextImageRenderPaint()
	{
		if(m_TextRenderPaint == null)
		{
			m_TextRenderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			m_TextRenderPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			m_TextRenderPaint.setAntiAlias(true);
			m_TextRenderPaint.setStrokeWidth(1);
			m_TextRenderPaint.setStrokeCap(Paint.Cap.ROUND);
			m_TextRenderPaint.setTextSize(72);
			m_TextRenderPaint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.BOLD));
		}
		return m_TextRenderPaint;
	}

	private static Paint	m_SemiTransparentPaint = null;
	public static Paint GetSemiTransparentPaint()
	{
		if(m_SemiTransparentPaint == null)
		{
			m_SemiTransparentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			m_SemiTransparentPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			m_SemiTransparentPaint.setAntiAlias(true);
			m_SemiTransparentPaint.setStrokeWidth(1);
			m_SemiTransparentPaint.setStrokeCap(Paint.Cap.ROUND);
			m_SemiTransparentPaint.setColor(1717986918);
		}
		return m_SemiTransparentPaint;
	}
	
	public static Bitmap CreateNumberBitmap(int nNumber, int color)
	{
		String str = new String("");
		Integer Value = Integer.valueOf(nNumber);
		str = Value.toString();
    	
		Bitmap bitmap = CreateDefaultTextBitmap(str, color);
		return bitmap;
	}
	
    public static Bitmap CreateDefaultTextBitmap(String text, int color)
    {
		Bitmap bitmap = null;

        Paint mPaint = GetDefaultTextImageRenderPaint();
		
        Rect    bounds = new Rect();
        mPaint.measureText(text, 0, text.length());
        mPaint.getTextBounds(text, 0, text.length(), bounds);

        int nWidth = (int)(((float)bounds.width())*1.1f);
        int nHeight = bounds.height(); //(int)(((float)bounds.height())*1.1f);

        bitmap = Bitmap.createBitmap(nWidth, nHeight, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
        
		canvas.save();
		canvas.translate(bounds.left, bounds.bottom);
		canvas.scale(1, -1);
        mPaint.setColor(color);
        mPaint.setAlpha(196);
        canvas.drawText(text, 0, 0, mPaint);
        canvas.restore();

        
        Bitmap bitmap2 = Bitmap.createBitmap(nWidth, nHeight, Bitmap.Config.ARGB_8888);
		Canvas canvas2 = new Canvas(bitmap2);
        
		canvas2.translate(0, nHeight);
		canvas2.scale(1, -1);
		
		canvas2.drawBitmap(bitmap, 0, 0, null);
		
		return bitmap2;
    }

	static HashMap<Integer, Bitmap> m_YellowNumberImageMap = null;
	public static Bitmap GetYellowNumberBitmap(int nNumber)
	{
		Bitmap bitmap = null;
		if (m_YellowNumberImageMap == null)
		{
			m_YellowNumberImageMap = new HashMap<Integer, Bitmap> ();
		}
		
		Integer id = Integer.valueOf(nNumber);
		bitmap = m_YellowNumberImageMap.get(id);
		if (bitmap == null)
		{
			bitmap = ResourceHelper.CreateNumberBitmap(nNumber, Color.YELLOW);
			if(bitmap != null)
				m_YellowNumberImageMap.put(id, bitmap);
		}
		
		return bitmap;
	}
	
	
	static HashMap<Integer, Bitmap> m_GreenNumberImageMap = null;
	public static Bitmap GetGreenNumberBitmap(int nNumber)
	{
		Bitmap bitmap = null;
		if (m_GreenNumberImageMap == null)
		{
			m_GreenNumberImageMap = new HashMap<Integer, Bitmap> ();
		}
		
		Integer id = Integer.valueOf(nNumber);
		bitmap = m_GreenNumberImageMap.get(id);
		if (bitmap == null)
		{
			bitmap = ResourceHelper.CreateNumberBitmap(nNumber, Color.GREEN);
			if(bitmap != null)
				m_GreenNumberImageMap.put(id, bitmap);
		}
		
		return bitmap;
	}
    
	//Animal compass
	private static Bitmap m_AnimalCompass8Bitmap = null;
	public static Bitmap GetAnimalCompass8Bitmap()
	{
		if(m_AnimalCompass8Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_AnimalCompass8Bitmap;
		        
		        m_AnimalCompass8Bitmap = BitmapFactory.decodeResource(res, R.drawable.ac8);
			}
		}
		return m_AnimalCompass8Bitmap;	
	}

	private static Bitmap m_AnimalCompass6Bitmap = null;
	public static Bitmap GetAnimalCompass6Bitmap()
	{
		if(m_AnimalCompass6Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_AnimalCompass6Bitmap;
		        
		        m_AnimalCompass6Bitmap = BitmapFactory.decodeResource(res, R.drawable.ac6);
			}
		}
		return m_AnimalCompass6Bitmap;	
	}

	private static Bitmap m_AnimalCompass4Bitmap = null;
	public static Bitmap GetAnimalCompass4Bitmap()
	{
		if(m_AnimalCompass4Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_AnimalCompass4Bitmap;
		        
		        m_AnimalCompass4Bitmap = BitmapFactory.decodeResource(res, R.drawable.ac4);
			}
		}
		return m_AnimalCompass4Bitmap;	
	}

	private static Bitmap m_AnimalCompass2Bitmap = null;
	public static Bitmap GetAnimalCompass2Bitmap()
	{
		if(m_AnimalCompass2Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_AnimalCompass2Bitmap;
		        
		        m_AnimalCompass2Bitmap = BitmapFactory.decodeResource(res, R.drawable.ac2);
			}
		}
		return m_AnimalCompass2Bitmap;	
	}
	
	//Food compass
	private static Bitmap m_FoodCompass8Bitmap = null;
	public static Bitmap GetFoodCompass8Bitmap()
	{
		if(m_FoodCompass8Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_FoodCompass8Bitmap;
		        
		        m_FoodCompass8Bitmap = BitmapFactory.decodeResource(res, R.drawable.fdc8);
			}
		}
		return m_FoodCompass8Bitmap;	
	}

	private static Bitmap m_FoodCompass6Bitmap = null;
	public static Bitmap GetFoodCompass6Bitmap()
	{
		if(m_FoodCompass6Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_FoodCompass6Bitmap;
		        
		        m_FoodCompass6Bitmap = BitmapFactory.decodeResource(res, R.drawable.fdc6);
			}
		}
		return m_FoodCompass6Bitmap;	
	}

	private static Bitmap m_FoodCompass4Bitmap = null;
	public static Bitmap GetFoodCompass4Bitmap()
	{
		if(m_FoodCompass4Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_FoodCompass4Bitmap;
		        
		        m_FoodCompass4Bitmap = BitmapFactory.decodeResource(res, R.drawable.fdc4);
			}
		}
		return m_FoodCompass4Bitmap;	
	}

	private static Bitmap m_FoodCompass2Bitmap = null;
	public static Bitmap GetFoodCompass2Bitmap()
	{
		if(m_FoodCompass2Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_FoodCompass2Bitmap;
		        
		        m_FoodCompass2Bitmap = BitmapFactory.decodeResource(res, R.drawable.fdc2);
			}
		}
		return m_FoodCompass2Bitmap;	
	}
	
	//Flower compass
	private static Bitmap m_FlowerCompass8Bitmap = null;
	public static Bitmap GetFlowerCompass8Bitmap()
	{
		if(m_FlowerCompass8Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_FlowerCompass8Bitmap;
		        
		        m_FlowerCompass8Bitmap = BitmapFactory.decodeResource(res, R.drawable.frc8);
			}
		}
		return m_FlowerCompass8Bitmap;	
	}

	//Flower compass
	private static Bitmap m_FlowerCompass6Bitmap = null;
	public static Bitmap GetFlowerCompass6Bitmap()
	{
		if(m_FlowerCompass6Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_FlowerCompass6Bitmap;
		        
		        m_FlowerCompass6Bitmap = BitmapFactory.decodeResource(res, R.drawable.frc6);
			}
		}
		return m_FlowerCompass6Bitmap;	
	}

	//Flower compass
	private static Bitmap m_FlowerCompass4Bitmap = null;
	public static Bitmap GetFlowerCompass4Bitmap()
	{
		if(m_FlowerCompass4Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_FlowerCompass4Bitmap;
		        
		        m_FlowerCompass4Bitmap = BitmapFactory.decodeResource(res, R.drawable.frc4);
			}
		}
		return m_FlowerCompass4Bitmap;	
	}

	//Flower compass
	private static Bitmap m_FlowerCompass2Bitmap = null;
	public static Bitmap GetFlowerCompass2Bitmap()
	{
		if(m_FlowerCompass2Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_FlowerCompass2Bitmap;
		        
		        m_FlowerCompass2Bitmap = BitmapFactory.decodeResource(res, R.drawable.frc2);
			}
		}
		return m_FlowerCompass2Bitmap;	
	}
	
	//Animal icon images
	private static Bitmap[] m_AnimalIconBitmaps = null;
	public static Bitmap GetAnimalIconBitmap(int index)
	{
		Bitmap bitmap = null;
		if(m_AnimalIconBitmaps == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return bitmap;
		        
		        m_AnimalIconBitmaps = new Bitmap[8];
		        m_AnimalIconBitmaps[0] = BitmapFactory.decodeResource(res, R.drawable.al1);
		        m_AnimalIconBitmaps[1] = BitmapFactory.decodeResource(res, R.drawable.al2);
		        m_AnimalIconBitmaps[2] = BitmapFactory.decodeResource(res, R.drawable.al3);
		        m_AnimalIconBitmaps[3] = BitmapFactory.decodeResource(res, R.drawable.al4);
		        m_AnimalIconBitmaps[4] = BitmapFactory.decodeResource(res, R.drawable.al5);
		        m_AnimalIconBitmaps[5] = BitmapFactory.decodeResource(res, R.drawable.al6);
		        m_AnimalIconBitmaps[6] = BitmapFactory.decodeResource(res, R.drawable.al7);
		        m_AnimalIconBitmaps[7] = BitmapFactory.decodeResource(res, R.drawable.al8);
			}
		}
		if(0 <= index && index < 8)
		{
			bitmap = m_AnimalIconBitmaps[index]; 
		}
		return bitmap;	
	}

	//Animal icon images
	private static Bitmap[] m_FoodIconBitmaps = null;
	public static Bitmap GetFoodIconBitmap(int index)
	{
		Bitmap bitmap = null;
		if(m_FoodIconBitmaps == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return bitmap;
		        
		        m_FoodIconBitmaps = new Bitmap[8];
		        m_FoodIconBitmaps[0] = BitmapFactory.decodeResource(res, R.drawable.fd1);
		        m_FoodIconBitmaps[1] = BitmapFactory.decodeResource(res, R.drawable.fd2);
		        m_FoodIconBitmaps[2] = BitmapFactory.decodeResource(res, R.drawable.fd3);
		        m_FoodIconBitmaps[3] = BitmapFactory.decodeResource(res, R.drawable.fd4);
		        m_FoodIconBitmaps[4] = BitmapFactory.decodeResource(res, R.drawable.fd5);
		        m_FoodIconBitmaps[5] = BitmapFactory.decodeResource(res, R.drawable.fd6);
		        m_FoodIconBitmaps[6] = BitmapFactory.decodeResource(res, R.drawable.fd7);
		        m_FoodIconBitmaps[7] = BitmapFactory.decodeResource(res, R.drawable.fd8);
			}
		}
		if(0 <= index && index < 8)
		{
			bitmap = m_FoodIconBitmaps[index]; 
		}
		return bitmap;	
	}

	//Animal icon images
	private static Bitmap[] m_FlowerIconBitmaps = null;
	public static Bitmap GetFlowerIconBitmap(int index)
	{
		Bitmap bitmap = null;
		if(m_FlowerIconBitmaps == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return bitmap;
		        
		        m_FlowerIconBitmaps = new Bitmap[8];
		        m_FlowerIconBitmaps[0] = BitmapFactory.decodeResource(res, R.drawable.fl1);
		        m_FlowerIconBitmaps[1] = BitmapFactory.decodeResource(res, R.drawable.fl2);
		        m_FlowerIconBitmaps[2] = BitmapFactory.decodeResource(res, R.drawable.fl3);
		        m_FlowerIconBitmaps[3] = BitmapFactory.decodeResource(res, R.drawable.fl4);
		        m_FlowerIconBitmaps[4] = BitmapFactory.decodeResource(res, R.drawable.fl5);
		        m_FlowerIconBitmaps[5] = BitmapFactory.decodeResource(res, R.drawable.fl6);
		        m_FlowerIconBitmaps[6] = BitmapFactory.decodeResource(res, R.drawable.fl7);
		        m_FlowerIconBitmaps[7] = BitmapFactory.decodeResource(res, R.drawable.fl8);
			}
		}
		if(0 <= index && index < 8)
		{
			bitmap = m_FlowerIconBitmaps[index]; 
		}
		return bitmap;	
	}
	
	//Animal banner
	private static Bitmap m_AnimalBannerBitmap = null;
	public static Bitmap GetAnimalBannerBitmap()
	{
		if(m_AnimalBannerBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_AnimalBannerBitmap;
		        
		        m_AnimalBannerBitmap = BitmapFactory.decodeResource(res, R.drawable.abanner);
			}
		}
		return m_AnimalBannerBitmap;	
	}

	//Numeric banner
	private static Bitmap m_NumericBannerBitmap = null;
	public static Bitmap GetNumericBannerBitmap()
	{
		if(m_NumericBannerBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_NumericBannerBitmap;
		        
		        m_NumericBannerBitmap = BitmapFactory.decodeResource(res, R.drawable.nbanner);
			}
		}
		return m_NumericBannerBitmap;	
	}

	//Flower banner
	private static Bitmap m_FlowerBannerBitmap = null;
	public static Bitmap GetFlowerBannerBitmap()
	{
		if(m_FlowerBannerBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_FlowerBannerBitmap;
		        
		        m_FlowerBannerBitmap = BitmapFactory.decodeResource(res, R.drawable.frbanner);
			}
		}
		return m_FlowerBannerBitmap;	
	}

	//Fodd banner
	private static Bitmap m_FoodBannerBitmap = null;
	public static Bitmap GetFoodBannerBitmap()
	{
		if(m_FoodBannerBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_FoodBannerBitmap;
		        
		        m_FoodBannerBitmap = BitmapFactory.decodeResource(res, R.drawable.ftbanner);
			}
		}
		return m_FoodBannerBitmap;	
	}

	private static Bitmap m_SwitchOnBitmap = null;//Update to reduce buffer size
	public static Bitmap GetSwitchOnBitmap()
	{
		if(m_SwitchOnBitmap != null)
			return m_SwitchOnBitmap;
		
		if(m_ResourceContext != null)
		{
	        Resources res = m_ResourceContext.getResources();
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
		
		if(m_ResourceContext != null)
		{
	        Resources res = m_ResourceContext.getResources();
	        if(res == null)
	        	return m_SwitchOffBitmap; 

	        m_SwitchOffBitmap = BitmapFactory.decodeResource(res, R.drawable.offbutton);
		}
		return m_SwitchOffBitmap; 
	}
	
}
