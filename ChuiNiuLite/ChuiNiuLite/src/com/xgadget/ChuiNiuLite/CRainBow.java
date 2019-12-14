package com.xgadget.ChuiNiuLite;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
/**
 * 
 */

/**
 * @author zhaohuixing
 *
 */
public class CRainBow 
{
	protected static final float BIRD_WIDTH_HEIGHT_RATIO = 0.6f;
	
	private Drawable		m_Image;
	private int             m_nTimerElaspe;
	private int				m_nTimerStep;
	public CGameScene		m_Controller;

	private	int 			m_enState;
	private RectF 			m_BoundInScene;
	private PointF 			m_CenterInScene;
	private Rect 			m_BoundInDevice;
	private PointF 			m_CenterInDevice;
	private PointF			m_Speed;
    private int             m_nBirdStep;
	
	public CRainBow(CGameScene parent)
	{
		m_Controller = parent;
		m_BoundInScene = new RectF();
		m_CenterInScene = new PointF();
		m_BoundInDevice = new Rect();
		m_CenterInDevice = new PointF();
		m_Speed = new PointF();
		m_nTimerElaspe = CConfiguration.getRainBowTimerStep();
		m_nTimerStep = 0;
		m_enState = CCloudObject.GAME_ALIEN_STOP;
		m_Image = CImageLoader.GetRainbowImage();
		m_CenterInScene.x = CGameLayout.GetGameSceneWidth()*(-0.5f);
		m_CenterInScene.y = CGameLayout.getRainBowWinY();//CGameLayout.GetGameSceneHeight()*0.5f;
		float w = CGameLayout.getRainBowWidth();
		float h = w*0.537777777777778f;
		m_BoundInScene.left = m_CenterInScene.x-0.5f*w;
		m_BoundInScene.right = m_CenterInScene.x+0.5f*w;
		m_BoundInScene.top = m_CenterInScene.y-0.5f*h;
		m_BoundInScene.bottom = m_CenterInScene.y+0.5f*h;
        m_nBirdStep = 0;
		
	}
	
	public void UpdateLayout()
	{
		float fWidthInDevice = 0.5f*CGameLayout.ObjectMeasureToDevice(m_BoundInScene.width());
		float fHeightInDevice = 0.5f*CGameLayout.ObjectMeasureToDevice(m_BoundInScene.height());
		m_CenterInDevice.x = CGameLayout.GameSceneToDeviceX(m_CenterInScene.x);
		m_CenterInDevice.y = CGameLayout.GameSceneToDeviceY(m_CenterInScene.y);
		m_BoundInDevice.left = (int)(m_CenterInDevice.x - fWidthInDevice);
		m_BoundInDevice.right = (int)(m_CenterInDevice.x + fWidthInDevice);
		m_BoundInDevice.top = (int)(m_CenterInDevice.y - fHeightInDevice);
		m_BoundInDevice.bottom = (int)(m_CenterInDevice.y + fHeightInDevice);
	}
	
	public void setToStartPosition()
	{
		float w = CGameLayout.getRainBowWidth();
		float h = w*0.537777777777778f;
		float fSW = CGameLayout.GetGameSceneWidth();
		float fSH = CGameLayout.GetGameSceneHeight();
		int nTH = (int)(2.0*CGameLayout.GetCowHeight());
		//Random rand = new Random();
		//int nR = rand.nextInt();
		float y = CGameLayout.getRainBowWinY();//(float)(nR%nTH);
		PointF pt = new PointF((fSW+w)/(-2.0f), fSH-(y+h/2.0f));
		moveTo(pt);
	}

	public void reset()
	{
		m_nTimerElaspe = CConfiguration.getRainBowTimerStep();
		m_nTimerStep = 0;
		m_enState = CCloudObject.GAME_ALIEN_STOP;
		setToStartPosition();
        m_nBirdStep = 0;
	}

	public void startMotion()
	{
		setToStartPosition();
		PointF p = new PointF(CConfiguration.getRainBowSpeed(), 0);
		setSpeed(p);
		m_enState = CCloudObject.GAME_ALIEN_MOTION;
	}

	public void stopAtWin()
	{
		m_enState = CCloudObject.GAME_ALIEN_BLAST;
	}

	public boolean isMotion()
	{
		boolean bRet = (m_enState == CCloudObject.GAME_ALIEN_MOTION);
		
		return bRet;
	}

	public boolean isWin()
	{
		boolean bRet = (m_enState == CCloudObject.GAME_ALIEN_BLAST);
		
		return bRet;
	}	

	private void updateBoundForCenterChange()
	{
		float fWidth = 0.5f*m_BoundInScene.width();
		float fHeight = 0.5f*m_BoundInScene.height();
		m_BoundInScene.left = m_CenterInScene.x-fWidth;
		m_BoundInScene.top = m_CenterInScene.y-fHeight;
		m_BoundInScene.right = m_CenterInScene.x+fWidth;
		m_BoundInScene.bottom = m_CenterInScene.y+fHeight;
		UpdateLayout();
	}
	
	public void moveTo(PointF pt)				//Base on game scence coordinate system
	{
		m_CenterInScene.set(pt);
		updateBoundForCenterChange();
	}

	public void moveTo(float x, float y)				//Base on game scence coordinate system
	{
		m_CenterInScene.set(x, y);
		updateBoundForCenterChange();
	}
	
	public void setSpeed(PointF pt)				//Base on game scence coordinate system
	{
		m_Speed.set(pt);
	}

	public void setSpeed(float x, float y)				//Base on game scence coordinate system
	{
		m_Speed.set(x, y);
	}
	
	public PointF getSpeed()							//Base on game scence coordinate system
	{
		return m_Speed;
	}
	
	public PointF getPosition()
	{
		return m_CenterInScene;
	}

	public RectF getBound()
	{
		return m_BoundInScene;
	}

	public boolean onTimerEvent()
	{
		boolean bRet = false;
		
		++m_nTimerStep;
		if(m_nTimerElaspe <= m_nTimerStep)
		{
			m_nTimerStep = 0;
			PointF speed = getSpeed();
			PointF pt = getPosition();
			pt.x += speed.x;
			moveTo(pt);
			bRet = true;
		}
	    m_nBirdStep = (m_nBirdStep+1)%4;
		
		return bRet;
	}

	private void drawBird(Canvas canvas, Rect rt, int index)
	{
		Drawable image = null;
	    if(index == 0)
	    	image = CImageLoader.GetBirdImage1();
	    else if(index == 1)
	    	image = CImageLoader.GetBirdImage2();
	    else if(index == 2)
	    	image = CImageLoader.GetBirdShootImage1();
	    else 
	    	image = CImageLoader.GetBirdShootImage2();
	    if(image != null)
	    {
			image.setBounds(rt);
			image.draw(canvas);
	    }
	}
	
	public void DrawNormal(Canvas canvas)
	{
		m_Image.setBounds(m_BoundInDevice);
		m_Image.draw(canvas);
		
	    float sx = m_BoundInDevice.left;
	    float w = m_BoundInDevice.width()/4.0f;//rt.size.width/4.0;
	    float h = w*BIRD_WIDTH_HEIGHT_RATIO;
	    float sy = m_BoundInDevice.top + (m_BoundInDevice.height() -h)/2.0f;
	    Rect rt2 = new Rect((int)sx, (int)sy, (int)(w+sx), (int)(sy+h));
	    this.drawBird(canvas, rt2, m_nBirdStep);
	    sx = m_BoundInDevice.left+m_BoundInDevice.width()-w;
	    rt2 = new Rect((int)sx, (int)sy, (int)(w+sx), (int)(sy+h));
	    this.drawBird(canvas, rt2, (m_nBirdStep+1)%4);
		
	}	

	public void Draw(Canvas canvas)
	{
		DrawNormal(canvas);
	}	

	public void DrawWin(Canvas canvas)
	{
	
		float y = CGameLayout.getRainBowWinY();
		float x = 0.0f;

		float fWidthInDevice = 0.5f*CGameLayout.ObjectMeasureToDevice(m_BoundInScene.width());
		float fHeightInDevice = 0.5f*CGameLayout.ObjectMeasureToDevice(m_BoundInScene.height());
		float cx = CGameLayout.GameSceneToDeviceX(x);
		float cy = CGameLayout.GameSceneToDeviceY(y);
		
		Rect rt = new Rect();

		rt.left = (int)(cx - fWidthInDevice);
		rt.right = (int)(cx + fWidthInDevice);
		rt.top = (int)(cy - fHeightInDevice);
		rt.bottom = (int)(cy + fHeightInDevice);
	
		m_Image.setBounds(rt);
		m_Image.draw(canvas);
		
	    float sx = rt.left;
	    float w = rt.width()/4.0f;//rt.size.width/4.0;
	    float h = w*BIRD_WIDTH_HEIGHT_RATIO;
	    float sy = rt.top + (rt.height() -h)/2.0f;
	    Rect rt2 = new Rect((int)sx, (int)sy, (int)(w+sx), (int)(sy+h));
	    this.drawBird(canvas, rt2, 3);
	    sx = rt.left+rt.width()-w;
	    rt2 = new Rect((int)sx, (int)sy, (int)(w+sx), (int)(sy+h));
	    this.drawBird(canvas, rt2, 3);
		
	}
	
}
