package com.xgadget.ChuiNiuLite;

//import java.util.Random;
import com.xgadget.uimodule.*;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
//import android.graphics.Paint;
import android.graphics.Rect;
//import android.graphics.Shader;
import android.graphics.drawable.Drawable;

public class MainMenuView extends BasicLayoutView 
{
    Paint 	m_BkGndGreenPaint;
    Paint 	m_BkGndBluePaint;
    
	private void InitializeMembers()
	{
		m_BkGndGreenPaint = new Paint();
		m_BkGndGreenPaint.setAntiAlias(true);
		m_BkGndGreenPaint.setARGB(255, 0, 180, 0);
		
		m_BkGndBluePaint  = new Paint();
		m_BkGndBluePaint.setAntiAlias(true);
		m_BkGndBluePaint.setARGB(255, 128, 128, 254);
	}
	
	public MainMenuView(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		if(ResourceHelper.CanLoadResource() == false)
			ResourceHelper.SetResourceContext(context);
		
		InitializeMembers();
	}

	public MainMenuView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		if(ResourceHelper.CanLoadResource() == false)
			ResourceHelper.SetResourceContext(context);
		
		InitializeMembers();
	}

	public MainMenuView(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		if(ResourceHelper.CanLoadResource() == false)
			ResourceHelper.SetResourceContext(context);
			
		InitializeMembers();
	}

	public void PostOnLayoutHandle()
	{
		int w = this.getWidth();
		int h = this.getHeight();

		invalidate();
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
	
	private void DrawNightBackground(Canvas canvas)
	{
		canvas.save();
		
		Rect rect = CGameLayout.GetGameSceneDeviceRect();//new Rect(0, 0, (int)CGameLayout.GetGameClientDeviceWidth(), (int)CGameLayout.GetGameClientDeviceHeight());
	    Paint 	paint = CImageLoader.GetStar1Paint();
		if(paint != null)
		{
        	canvas.drawRect(rect, paint);
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
	}

	private void DrawBackground(Canvas canvas)
	{
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
		DrawGrass(canvas);
	}
	
	private void DrawGrass(Canvas canvas)
	{
		Bitmap bitmap = CImageLoader.GetGrassBitmap();
		if(bitmap == null)
			return;
	
		int imgw = bitmap.getWidth();
		int imgh = bitmap.getHeight();
		Rect srcRect = new Rect(0, 0, imgw, imgh);
		float xoffset = CGameLayout.GetGrassUnitWidth();
		//float xspeed = CConfiguration.getBlockageSpeed().x;
		//float v = xoffset/xspeed;
		float sx = -xoffset*2.0f;
		float sy = CGameLayout.GetGrassUnitHeight();
		float width = xoffset;
		float height = CGameLayout.GetGrassUnitHeight();
    
		float x = sx*CGameLayout.GetGameSceneDMScaleX();
		float y = CGameLayout.GameSceneToDeviceY(sy);
		float w = CGameLayout.ObjectMeasureToDevice(width);
		float h = CGameLayout.ObjectMeasureToDevice(height);
		RectF rt;
    
		for(int i = 0; i < CGameLayout.GetGrassUnitNumber(); ++i)
		{
			float left = x + w*((float)i);
			
			rt = new RectF(left, y, left + w, y + h);
			canvas.drawBitmap(bitmap, srcRect, rt, null);
		}
	}

}
