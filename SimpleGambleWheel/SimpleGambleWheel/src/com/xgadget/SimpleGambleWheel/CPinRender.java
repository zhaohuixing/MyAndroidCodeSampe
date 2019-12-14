package com.xgadget.SimpleGambleWheel;

//import java.util.Random;

import com.xgadget.uimodule.ResourceHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

public class CPinRender extends BasicLayoutView 
{
	private Rect		m_SrcImageRect;	
	private CPointer    m_Container;
	
	private void Initialize()
	{
		this.setBackgroundColor(17170445);
		int imagew = ResourceHelper.GetPointerBitmapWidth();
		int imageh = ResourceHelper.GetPointerBitmapHeight();
		m_SrcImageRect = new Rect(0, 0, imagew, imageh);
		m_Container = null;
	}

	public CPinRender(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public CPinRender(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize();
	}

	public CPinRender(Context context, AttributeSet attrs,
			int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize();
	}
	
	void AttachContainer(CPointer container)
	{
		m_Container = container;
	}

	public void PostOnLayoutHandle()
	{
		int screenWidth = GameLayoutConstant.GetCurrentScreenWidth(); //m_LayoutContainer.getWidth(); //MyAbsoluteLayout.GetCurrentScreenWidth();
		int screenHeight = GameLayoutConstant.GetCurrentContentViewHeight(); //m_LayoutContainer.getHeight();  //MyAbsoluteLayout.GetCurrentScreenHeight();
		int size = (int)(((float)GameLayoutConstant.GetCurrentCompassWheelSize())*GameLayoutConstant.m_fPointerWheelSizeRatio); 
		int x, y;
		if(screenWidth < screenHeight)
		{
			x = (screenWidth - size)/2;
			y = (screenHeight - size)/2;
		}
		else
		{
			x = (screenWidth - size)/2;
			y = (screenHeight - size)/2;
		}
		if(m_LayoutContainer != null)
			m_LayoutContainer.SetChildNewDemension(this, size, size, x, y);		
		
		invalidate();
	}	

	protected void onDraw(Canvas canvas)
	{
		//DrawStaticPointer(canvas);
		//TestDrawPointerAnimation(canvas);
		if(m_Container != null)
		{
			int nState = m_Container.GetGameState();
			if(nState == GameConstants.GAME_STATE_RUN)
			{
				DrawAnimationPointer(canvas);
			}
			else if(nState == GameConstants.GAME_STATE_RESULT)
			{
				DrawResultPointer(canvas);
			}
			else
			{
				DrawStaticPointer(canvas);
			}
		}
		else
		{
			DrawStaticPointer(canvas);
		}
 	}

	private void DrawAnimationFast(Canvas canvas)
	{
		Bitmap bitmap =  ResourceHelper.GetPointerBitmap();
		if(bitmap != null)
		{
			int nRand = GameUitltyHelper.CreateRandomNumber();
			if(nRand < 0)
				nRand *= -1;
			nRand = nRand%360+1;
			
			float r = ((float)m_SrcImageRect.width())/((float)m_SrcImageRect.height());
			float w = (float)getWidth();
			float imageh = (float)getHeight()/2.0f;
			float imagew = imageh*r;
			float x = (w - imagew)/2.0f;
			RectF dstRect = new RectF(x, 0, x+imagew, imageh);
			
			float fStep = m_Container.m_fFastUnit*((float)m_Container.m_nFastStep);
			int nCount = GameConstants.GAME_POINTER_FAST_ANGLE_MAXCOUNT - ((int)fStep);  
			float angle;		
			nCount /= 3;  
			angle = 360.0f/((float)nCount);
			
			canvas.save();
			canvas.rotate((float)nRand+angle, w/2.0f, w/2.0f);
			for(int i = 0; i < nCount; ++i)
			{
				canvas.drawBitmap(bitmap, m_SrcImageRect, dstRect, ResourceHelper.GetMostTransparentPointerPaint());
				canvas.rotate(angle, w/2.0f, w/2.0f);
			}
			canvas.restore();
		}
	}
	
	private void DrawAnimationMedium(Canvas canvas)
	{
		Bitmap bitmap =  ResourceHelper.GetPointerBitmap();
		if(bitmap != null && m_Container != null)
		{
			float r = ((float)m_SrcImageRect.width())/((float)m_SrcImageRect.height());
			float w = (float)getWidth();
			float imageh = (float)getHeight()/2.0f;
			float imagew = imageh*r;
			float x = (w - imagew)/2.0f;
			RectF dstRect = new RectF(x, 0, x+imagew, imageh);
			
		    float angle = m_Container.m_fMediumStep;
			if(m_Container.m_bClockwise == false)
				angle = 360-angle;
			canvas.save();
			canvas.rotate(angle, w/2.0f, w/2.0f);
			canvas.drawBitmap(bitmap, m_SrcImageRect, dstRect, null);
			canvas.restore();
		}
	}
	
	private void DrawAnimationSlow(Canvas canvas)
	{
		Bitmap bitmap =  ResourceHelper.GetPointerBitmap();
		if(bitmap != null && m_Container != null)
		{
			float r = ((float)m_SrcImageRect.width())/((float)m_SrcImageRect.height());
			float w = (float)getWidth();
			float imageh = (float)getHeight()/2.0f;
			float imagew = imageh*r;
			float x = (w - imagew)/2.0f;
			RectF dstRect = new RectF(x, 0, x+imagew, imageh);
			
		    float angle = m_Container.m_fSlowStep;
			if(m_Container.m_bClockwise == false)
				angle = 360-angle;
			canvas.save();
			canvas.rotate(angle, w/2.0f, w/2.0f);
			canvas.drawBitmap(bitmap, m_SrcImageRect, dstRect, null);
			canvas.restore();
		}
	}
	
	private void DrawAnimationVibration(Canvas canvas)
	{
		Bitmap bitmap =  ResourceHelper.GetPointerBitmap();
		if(bitmap != null && m_Container != null)
		{
			float r = ((float)m_SrcImageRect.width())/((float)m_SrcImageRect.height());
			float w = (float)getWidth();
			float imageh = (float)getHeight()/2.0f;
			float imagew = imageh*r;
			float x = (w - imagew)/2.0f;
			RectF dstRect = new RectF(x, 0, x+imagew, imageh);
			
			int nStep;
			nStep = m_Container.m_nVibrationStep;
			
			float angle = (float)((m_Container.m_nPosition+nStep)%360);
			
			canvas.save();
			canvas.rotate(angle, w/2.0f, w/2.0f);
			canvas.drawBitmap(bitmap, m_SrcImageRect, dstRect, null);
			canvas.restore();
		}
	}
	
	private void DrawAnimationPointer(Canvas canvas)
	{
		if(m_Container == null)
		{
			DrawStaticPointer(canvas);
			return;
		}
		
		if(m_Container.m_nPointerState == GameConstants.GAME_POINTER_SPIN_FAST)
		{
			DrawAnimationFast(canvas);
		}
		else if(m_Container.m_nPointerState == GameConstants.GAME_POINTER_SPIN_MEDIUM)
		{
			DrawAnimationMedium(canvas);
		}
		else if(m_Container.m_nPointerState == GameConstants.GAME_POINTER_SPIN_SLOW)
		{	
			DrawAnimationSlow(canvas);
		}
		else
		{
			DrawAnimationVibration(canvas);
		}
		
	}
	
	private void DrawResultPointer(Canvas canvas)
	{
		Bitmap bitmap =  ResourceHelper.GetPointerBitmap();
		if(bitmap != null && m_Container != null)
		{
			float r = ((float)m_SrcImageRect.width())/((float)m_SrcImageRect.height());
			float w = (float)getWidth();
			float imageh = (float)getHeight()/2.0f;
			float imagew = imageh*r;
			float x = (w - imagew)/2.0f;
			RectF dstRect = new RectF(x, 0, x+imagew, imageh);
			
			canvas.save();
			canvas.rotate((float)m_Container.m_nPosition, w/2.0f, w/2.0f);
			canvas.drawBitmap(bitmap, m_SrcImageRect, dstRect, null);
			canvas.restore();
		}
	}
	
	private void DrawStaticPointer(Canvas canvas)
	{
		Bitmap bitmap =  ResourceHelper.GetPointerBitmap();
		if(bitmap != null)
		{
			float r = ((float)m_SrcImageRect.width())/((float)m_SrcImageRect.height());
			float w = (float)getWidth();
			float imageh = (float)getHeight()/2.0f;
			float imagew = imageh*r;
			float x = (w - imagew)/2.0f;
			RectF dstRect = new RectF(x, 0, x+imagew, imageh);
			canvas.drawBitmap(bitmap, m_SrcImageRect, dstRect, null);
		}
	}

/*	
	private void TestDrawPointerAnimation(Canvas canvas)
	{
		Bitmap bitmap =  ResourceHelper.GetPointerBitmap();
		if(bitmap != null)
		{
			Random rand = new Random();
			int nRand = rand.nextInt();
			if(nRand < 0)
				nRand *= -1;
			nRand = nRand%360+1;
			
			float r = ((float)m_SrcImageRect.width())/((float)m_SrcImageRect.height());
			float w = (float)getWidth();
			float imageh = (float)getHeight()/2.0f;
			float imagew = imageh*r;
			float x = (w - imagew)/2.0f;
			RectF dstRect = new RectF(x, 0, x+imagew, imageh);
			
			canvas.save();
			canvas.rotate((float)nRand, w/2.0f, w/2.0f);
			for(int i = 0; i < 36; ++i)
			{
				canvas.drawBitmap(bitmap, m_SrcImageRect, dstRect, ResourceHelper.GetMostTransparentPointerPaint());
				canvas.rotate(10.0f, w/2.0f, w/2.0f);
			}
			canvas.restore();
		}
	}*/
	
	/*public void OnTimerEvent()
	{
		invalidate();
	}*/
	
}
