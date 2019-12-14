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
public class CBulletObject implements IBullet 
{
	public static final int GAME_BULLET_READY = 0;
	public static final int GAME_BULLET_SHOOTTING = 1;
	public static final int GAME_BULLET_BLAST = 2;
	public static final int GAME_DEFAULT_PLAYER_BULLET_BLAST_STEP = 3;

	private RectF m_BoundInScene;
	private PointF m_CenterInScene;
	private Rect m_BoundInDevice;
	private PointF m_CenterInDevice;
	private int	m_enState;
	private IGunEventDelegate m_Gun;
	private PointF	m_Speed;
	
	private int                 m_nTimerElaspe;
	private int					m_nTimerStep;
	private int					m_nBlastAnimationStep;
	private int					m_nBlastCount;
	public Drawable				m_Image;
	private float				m_fOriginalWith;
	private float				m_fOriginalHeight;
	public float				m_fStartRatioX;
	public float				m_fStartRatioY;
	public float				m_fChangeRatioX;
	public float				m_fChangeRatioY;
	
	
	public CBulletObject(IGunEventDelegate gun, float w, float h)
	{
		m_enState = GAME_BULLET_READY;
		m_Gun = gun;
		m_BoundInScene = new RectF();
		m_CenterInScene = new PointF();
		m_BoundInDevice = new Rect();
		m_CenterInDevice = new PointF();
		m_Speed = new PointF();
		
		m_nTimerElaspe = CConfiguration.getBulletTimerElapse();
		m_nTimerStep = 0;
		m_nBlastAnimationStep = 0;
		m_nBlastCount = GAME_DEFAULT_PLAYER_BULLET_BLAST_STEP;
		m_fStartRatioX = 1.0f;
		m_fStartRatioY = 1.0f;
		m_fChangeRatioX = 0.0f;
		m_fChangeRatioY = 0.0f;
		m_fOriginalWith = w;
		m_fOriginalHeight = h;
		m_CenterInScene.x = -1000.0f;
		m_CenterInScene.y = -1000.0f;
		m_BoundInScene.left = m_CenterInScene.x-m_fOriginalWith*0.5f;
		m_BoundInScene.top = m_CenterInScene.y-m_fOriginalHeight*0.5f;
		m_BoundInScene.right = m_CenterInScene.x+m_fOriginalWith*0.5f;
		m_BoundInScene.bottom = m_CenterInScene.y+m_fOriginalHeight*0.5f;
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

	/* (non-Javadoc)
	 * @see com.xgadget.ChuiNiuLite.IBullet#Reset()
	 */
	//@Override
	public void Reset()
	{
		m_enState = GAME_BULLET_READY;
		m_Speed.x = 0;
		m_Speed.y = 0;
		m_nTimerElaspe = CConfiguration.getBulletTimerElapse();
		m_nTimerStep = 0;
		m_nBlastAnimationStep = 0;
		m_CenterInScene.x = -1000.0f;
		m_CenterInScene.y = -1000.0f;
		m_BoundInScene.left = m_CenterInScene.x-m_fOriginalWith*0.5f;
		m_BoundInScene.top = m_CenterInScene.y-m_fOriginalHeight*0.5f;
		m_BoundInScene.right = m_CenterInScene.x+m_fOriginalWith*0.5f;
		m_BoundInScene.bottom = m_CenterInScene.y+m_fOriginalHeight*0.5f;
		UpdateLayout();
	}

	//Bullet data function
	public void shootAt(float startx, float starty)
	{
		m_CenterInScene.x = startx;
		m_CenterInScene.y = starty;
		float w = m_fOriginalWith*m_fStartRatioX;
		float h = m_fOriginalWith*m_fStartRatioY;
		m_BoundInScene.left = m_CenterInScene.x-w*0.5f;
		m_BoundInScene.top = m_CenterInScene.y-h*0.5f;
		m_BoundInScene.right = m_CenterInScene.x+w*0.5f;
		m_BoundInScene.bottom = m_CenterInScene.y+h*0.5f;
		UpdateLayout();
		SetShootState();
	}

	//Bullet data function
	public void shoot()
	{
		float w = m_fOriginalWith*m_fStartRatioX;
		float h = m_fOriginalWith*m_fStartRatioY;
		m_BoundInScene.left = m_CenterInScene.x-w*0.5f;
		m_BoundInScene.top = m_CenterInScene.y-h*0.5f;
		m_BoundInScene.right = m_CenterInScene.x+w*0.5f;
		m_BoundInScene.bottom = m_CenterInScene.y+h*0.5f;
		UpdateLayout();
		SetShootState();
	}
	
	public void blast()
	{
		SetBlastState();
		m_nBlastAnimationStep = 0;
	}
	
	
	/* (non-Javadoc)
	 * @see com.xgadget.ChuiNiuLite.IBullet#Hit(com.xgadget.ChuiNiuLite.IBullet)
	 */
	//@Override
	public boolean Hit(IBullet bullet)
	{
		boolean bRet = false;
		
		if(bullet != null)
		{
			bRet = bullet.HitTestWithRect(m_BoundInScene);
		}
		
		return bRet;
	}
	
	/* (non-Javadoc)
	 * @see com.xgadget.ChuiNiuLite.IBullet#HitTestWithPoint(android.graphics.PointF)
	 */
	//@Override
	public boolean HitTestWithPoint(PointF pt) 
	{
		// TODO Auto-generated method stub
		boolean bRet = false;
		
		if(m_BoundInScene.contains(pt.x, pt.y) == true)
			bRet = true;
		
		return bRet;
	}

	/* (non-Javadoc)
	 * @see com.xgadget.ChuiNiuLite.IBullet#HitTestWithPoint(android.graphics.Point)
	 */
	//@Override
	public boolean HitTestWithPoint(Point pt) 
	{
		// TODO Auto-generated method stub
		boolean bRet = false;
		
		if(m_BoundInScene.contains((float)pt.x, (float)pt.y) == true)
			bRet = true;
		
		return bRet;
	}

	/* (non-Javadoc)
	 * @see com.xgadget.ChuiNiuLite.IBullet#HitTestWithPoint(float, float)
	 */
	//@Override
	public boolean HitTestWithPoint(float x, float y) 
	{
		// TODO Auto-generated method stub
		boolean bRet = false;
		
		if(m_BoundInScene.contains(x, y) == true)
			bRet = true;
		
		return bRet;
	}

	/* (non-Javadoc)
	 * @see com.xgadget.ChuiNiuLite.IBullet#HitTestWithPoint(int, int)
	 */
	//@Override
	public boolean HitTestWithPoint(int x, int y) 
	{
		// TODO Auto-generated method stub
		boolean bRet = false;
		
		if(m_BoundInScene.contains((float)x, (float)y) == true)
			bRet = true;
		
		return bRet;
	}

	/* (non-Javadoc)
	 * @see com.xgadget.ChuiNiuLite.IBullet#HitTestWithRect(android.graphics.RectF)
	 */
	//@Override
	public boolean HitTestWithRect(RectF rect) 
	{
		// TODO Auto-generated method stub
		boolean bRet = false;
		
		if(m_BoundInScene.intersect(rect) == true)
			bRet = true;
		
		return bRet;
	}

	/* (non-Javadoc)
	 * @see com.xgadget.ChuiNiuLite.IBullet#HitTestWithRect(android.graphics.Rect)
	 */
	//@Override
	public boolean HitTestWithRect(Rect rect) 
	{
		// TODO Auto-generated method stub
		boolean bRet = false;
		
		if(m_BoundInScene.intersect((float)rect.left, (float)rect.top, (float)rect.right, (float)rect.bottom) == true)
			bRet = true;
		
		return bRet;
	}

	/* (non-Javadoc)
	 * @see com.xgadget.ChuiNiuLite.IBullet#HitTestWithRect(float, float, float, float)
	 */
	//@Override
	public boolean HitTestWithRect(float left, float top, float right,
			float bottom) 
	{
		// TODO Auto-generated method stub
		boolean bRet = false;
		
		if(m_BoundInScene.intersect(left, top, right, bottom) == true)
			bRet = true;
		
		return bRet;
	}

	/* (non-Javadoc)
	 * @see com.xgadget.ChuiNiuLite.IBullet#HitTestWithRect(int, int, int, int)
	 */
	//@Override
	public boolean HitTestWithRect(int left, int top, int right, int bottom) 
	{
		// TODO Auto-generated method stub
		boolean bRet = false;
		
		if(m_BoundInScene.intersect((float)left, (float)top, (float)right, (float)bottom) == true)
			bRet = true;
		
		return bRet;
	}

	private void drawShoot(Canvas canvas)
	{
		if(m_Image == null)
			return;
		
		m_Image.setBounds(m_BoundInDevice);
		m_Image.draw(canvas);
	}
	
	private void drawBlast(Canvas canvas)
	{
		if(m_Image == null)
			return;
		
		m_Image.setBounds(m_BoundInDevice);
		m_Image.draw(canvas);
	}
	
	public void Draw(Canvas canvas)
	{
		if(isShoot() == true)
		{
			drawShoot(canvas);
		}	
		else if(isBlast() == true)
		{
			drawBlast(canvas);
		}	
	}

	private void updateTimerEvent()
	{
		if(isShoot() == true)
		{
			float x = m_CenterInScene.x;
			float y = m_CenterInScene.y;
			PointF v = getSpeed();
			float w = m_BoundInScene.width();
			float h = m_BoundInScene.height();
			x += v.x;
			y += v.y;
			w += m_fChangeRatioX*m_fOriginalWith;
		    if(m_fOriginalWith <= w)
				w = m_fOriginalWith;
			h += m_fChangeRatioY*m_fOriginalHeight;
		    if(m_fOriginalHeight <= h)
				h = m_fOriginalHeight;

		    m_BoundInScene.right = m_BoundInScene.left + w;
		    m_BoundInScene.bottom = m_BoundInScene.top + h;
			moveTo(x, y);
			
			if(outOfSceneBound() == true)
			{
				blast();
			}	
		}
		else if(isBlast() == true)
		{
			++m_nBlastAnimationStep;
			float fRatio = ((float)(m_nBlastAnimationStep-m_nBlastAnimationStep))/((float)m_nBlastAnimationStep);
			float w = m_BoundInScene.width();
			float h = m_BoundInScene.height();
			w *= fRatio;
			h *= fRatio;
			m_BoundInScene.left = m_CenterInScene.x-w*0.5f;
			m_BoundInScene.top = m_CenterInScene.y-h*0.5f;
			m_BoundInScene.right = m_CenterInScene.x+w*0.5f;
			m_BoundInScene.bottom = m_CenterInScene.y+h*0.5f;
			if(m_nBlastCount <= m_nBlastAnimationStep)
			{
				m_Gun.bulletBlasted(this);
			}
			UpdateLayout();
		}	
	}
	
	public boolean OnTimerEvent()
	{
		boolean bRet = false;
		
		if(isBlast() == true || isShoot() == true)
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
	
	public int getBulletState()
	{
		return m_enState;
	}	

	public void SetShootState()
	{
		m_enState = GAME_BULLET_SHOOTTING;
	}

	public void SetBlastState()
	{
		m_enState = GAME_BULLET_BLAST;
	}

	public boolean isShoot()
	{
		boolean bRet = false;

		if(m_enState == GAME_BULLET_SHOOTTING)
			bRet = true;
		
		return bRet;
	}

	public boolean isBlast()
	{
		boolean bRet = false;
		
		if(m_enState == GAME_BULLET_BLAST)
			bRet = true;
		
		return bRet;
	}

	public boolean isReady()
	{
		boolean bRet = false;
		
		if(m_enState == GAME_BULLET_READY)
			bRet = true;
		
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
	
}
