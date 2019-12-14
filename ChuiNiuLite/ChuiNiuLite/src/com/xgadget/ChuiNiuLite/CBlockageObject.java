/**
 * 
 */
package com.xgadget.ChuiNiuLite;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * @author zhaohuixing
 *
 */
public class CBlockageObject implements IBlockage 
{
	public static final int GAME_BLOCKAGE_STOP = 0;
	public static final int GAME_BLOCKAGE_MOTION = 1;
	public static final float GAME_BLOCKAGE_SHAKING_ANGLE = 10.0f;
	
	public CDogObject			m_BlockTarget;
	
	private int                 m_nTimerElaspe;
	private int					m_nTimerStep;
	public	Drawable			m_Image;
	private boolean				m_bShaking;
	private float				m_fShakingAngle;
	private int					m_nShakeStep;

	private RectF 				m_BoundInScene;
	private PointF 				m_CenterInScene;
	private Rect 				m_BoundInDevice;
	private PointF 				m_CenterInDevice;
	private int					m_enState;
	private PointF				m_Speed;
	
	
	public CBlockageObject()
	{
		m_nTimerElaspe = CConfiguration.getBlockageTimerElapse();
		m_enState = GAME_BLOCKAGE_STOP;
		m_nTimerStep = 0;
		float h = CGameLayout.m_fRockMeasureHeight;
		float w = CGameLayout.m_fRockMeasureWidth;
		m_BoundInScene = new RectF();
		m_CenterInScene = new PointF();
		m_BoundInDevice = new Rect();
		m_CenterInDevice = new PointF();
		m_Speed = new PointF();
		
		m_CenterInScene.x = CGameLayout.m_fGameSceneMeasureWidth*(-0.5f);
		m_CenterInScene.y = h*0.5f;
		m_BoundInScene.left = m_CenterInScene.x-w*0.5f;
		m_BoundInScene.right = m_CenterInScene.x+w*0.5f;
		m_BoundInScene.top = m_CenterInScene.y-h*0.5f;
		m_BoundInScene.bottom = m_CenterInScene.y+h*0.5f;
		
		m_bShaking = false;
		m_fShakingAngle = GAME_BLOCKAGE_SHAKING_ANGLE;
		m_nShakeStep = 0;
		m_Image = null;
		UpdateLayout();
	}

	public void setImage(Drawable image)
	{
		m_Image = image;
	}	
	
	/* (non-Javadoc)
	 * @see com.xgadget.ChuiNiuLite.IBlockage#detachTarget()
	 */
	//@Override
	public void detachTarget() 
	{
		// TODO Auto-generated method stub
		if(m_BlockTarget != null)
			m_BlockTarget = null;
	}

	public void setTimerElaspe(int nInterval)
	{
		m_nTimerElaspe = nInterval;
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
	
	public void DrawMotion(Canvas canvas)
	{
		canvas.save();
		if(m_bShaking == true && m_nShakeStep != 1)
		{
			float fSign = ((float)m_nShakeStep)-1.0f;
			float angle = fSign*m_fShakingAngle;
			canvas.rotate(angle, m_CenterInDevice.x, m_CenterInDevice.y);
		}
		m_Image.setBounds(m_BoundInDevice);
		m_Image.draw(canvas);
		canvas.restore();
	}

	public void Draw(Canvas canvas)
	{
		if(isMotion() == true)
		{
			DrawMotion(canvas);
		}	
	}
	
	public void updateTimerEvent()
	{
		if(isMotion() == true)
		{
			PointF pt = getPosition();
			PointF v = getSpeed();

			if(m_BlockTarget != null)
				m_BlockTarget.pushBack(v.x);
		
			pt.x += v.x;
			pt.y += v.y;
			moveTo(pt);
		
			if(m_bShaking == true)
			{
				m_nShakeStep = (m_nShakeStep+1)%3;
			}	
		
			if(outOfSceneBound() == true)
			{
				Reset();
			}	
		}
	}

	public boolean OnTimerEvent()
	{
		boolean bRet = false;
	
		if(/*isBlast() == true ||*/ isMotion() == true)
		{
			++m_nTimerStep;
			if(m_nTimerElaspe <= m_nTimerStep)
			{
				m_nTimerStep = 0;
				updateTimerEvent();
				bRet = true;
			}	
		}
		return bRet;
	}
	
	private void updateBoundForCenterChange()
	{
		float fWidth = 0.5f*CGameLayout.m_fRockMeasureWidth;
		float fHeight = 0.5f*CGameLayout.m_fRockMeasureHeight;
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
	

	public boolean outOfSceneBound()
	{
		boolean bRet = false;
		
		float w = 0.5f*CGameLayout.GetGameSceneWidth();
		float h = CGameLayout.GetGameSceneHeight();
		float x = m_CenterInScene.x;
		float y = m_CenterInScene.y;
		if(x < -w || w < x || y < 0 || h < y)
		{
			bRet = true;
		}	
		
		return bRet;
	}	
	
	public void setShaking(boolean bShaking)
	{
		m_bShaking = bShaking;
	}	

	//Alien Data functions
	public void Reset()
	{
		m_enState = GAME_BLOCKAGE_STOP;
		m_nTimerStep = 0;
		m_BlockTarget = null;
		m_nTimerElaspe = CConfiguration.getBlockageTimerElapse();
		m_nShakeStep = 0;
	}

	public void startMotion()
	{
		m_enState = GAME_BLOCKAGE_MOTION;
	}	

	public boolean isMotion()
	{
		boolean bRet = (m_enState == GAME_BLOCKAGE_MOTION);
		return bRet;	
	}	

	public boolean isStop()
	{
		boolean bRet = (m_enState == GAME_BLOCKAGE_STOP);
		return bRet;	
	}	

	public boolean hitTestWithPoint(PointF pt) 
	{
		// TODO Auto-generated method stub
		boolean bRet = false;
		
		if(m_BoundInScene.contains(pt.x, pt.y) == true)
			bRet = true;
		
		return bRet;
	}
	
	public boolean hitTestWithRect(RectF rect) 
	{
		// TODO Auto-generated method stub
		boolean bRet = false;
		
		if(m_BoundInScene.intersect(rect) == true)
			bRet = true;
		
		return bRet;
	}
	
}
