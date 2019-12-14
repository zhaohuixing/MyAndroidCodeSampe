/**
 * 
 */
package com.xgadget.ChuiNiuLite;
import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
//import android.graphics.Bitmap;
//import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;

/**
 * @author zhaohuixing
 *
 */
public class CDogObject implements IGunTarget, IGunOwner, IBlockTarget 
{
	public static final int GAME_TIMER_PLAYER_STEP = 6;//10;
	public static final int GAME_PLAYER_STOP = 0;
	public static final int GAME_PLAYER_MOTION = 1;
	public static final int GAME_PLAYER_JUMP = 2;
	public static final int GAME_PLAYER_SHOOT = 3;
	public static final int GAME_PLAYER_SHOOTANDMOTION = 4;
	public static final int GAME_PLAYER_SHOOTANDJUMP = 5;
	public static final int GAME_PLAYER_DEAD = 6;
	public static final int GAME_SHOOT_FRAME = 5;
	public static final int GAME_TAP_STEP = 40;
	public static final float GAME_DEFAULT_PLAYER_BULLET_SPEED_X = 0.0f; 
	public static final float GAME_DEFAULT_PLAYER_BULLET_SPEED_Y = 20.0f;
	public static final int GAME_DEFAULT_PLAYER_DEATH_STEP = 4;
	public static final float GAME_PLAYER_DEAD_SIZE_FACTOR = 0.4f;
	public static final float GAME_PLAYER_MOUTHSHOOT_Y_RATIO = 1.2f;
	public static final float GAME_PLAYER_WALKSHOOT_X_RATIO = 0.7f;  //Hardcode value 
	public static final float GAME_PLAYER_WALKSHOOT_X_RATIO_HALFREVERSE = 0.15f;  //Hardcode value 
	public static final float GAME_PLAYER_WALKSHOOT_Y_RATIO = 1.4f;  //Hardcode value
	//public static final float GAME_PLAYER_MOUTHSHOOT_Y_RATIO = 1.2f;  //Hardcode value
	
	public static final float DOG_JUMP_X_RATIO = 1.042f;
	public static final float DOG_JUMP_Y_RATIO = 0.88f;
	
	private float m_fWidth;
	private float m_fHeight;
	private PointF m_Center;
	private Rect m_BoundInDevice;
	private PointF m_CenterInDevice;
	private Vector<Drawable> 	m_Animations;
	private Vector<Drawable> 	m_ShootAnimations;
	private CGameScene			m_Controller;
	
	private int	m_nTimerStep;
	private int	m_nAnimationStep;
	private int m_nState;
	//private float[] m_AnimationAngle;

	private int m_nShootStep;
	private int	m_nJumpStep;
	private float m_fJumpAngle;
	private int	m_nJumpShootStep;
	//private Vector<Drawable> m_JumpAnimations;
	//private Vector<Drawable> m_JumpShootAnimations;
	
	
	public PointF	m_touchPoint;
	private int		m_nTapStep1;
	private int		m_nTapStep2;
	private boolean	m_bTouched;
	
	private int		m_nDeadStep;	
	PointF			m_DeathAnimationOffset;
	Drawable		m_DeadAnimation;
	
	public CGunObject		m_Gun;
	public CBlockageObject	m_Blockage;
	
	public CDogObject(CGameScene parent)
	{
		m_Controller = parent;
		m_fHeight = CGameLayout.GetDogHeight();//m_fDogRealYScale*CGameLayout.m_fGameSceneMeasureHeight;
		m_fWidth = CGameLayout.GetDogWidth();//m_fHeight*m_fDogXYScale;
		m_Center = new PointF();
		m_Center.x = 0;
		m_Center.y = m_fHeight/2.0f;
		m_CenterInDevice = new PointF();
		m_BoundInDevice = new Rect();
		m_nTimerStep = 0;
		m_nAnimationStep = 0;
		
		m_Animations = new Vector<Drawable>();
	
		m_Animations.add(CImageLoader.GetDogStopImage());
		m_Animations.add(CImageLoader.GetDogWalk1Image());
		m_Animations.add(CImageLoader.GetDogWalk2Image());
		//m_Animations.add(CImageLoader.GetDogStopImage());
		m_Animations.add(CImageLoader.GetDogWalk3Image());
		m_Animations.add(CImageLoader.GetDogWalk4Image());
		
		m_ShootAnimations = new Vector<Drawable>();
		m_ShootAnimations.add(CImageLoader.GetDogShoot1Image());
		m_ShootAnimations.add(CImageLoader.GetDogShoot2Image());
		m_ShootAnimations.add(CImageLoader.GetDogShoot3Image());
		m_ShootAnimations.add(CImageLoader.GetDogShoot4Image());
		m_ShootAnimations.add(CImageLoader.GetDogShoot1Image());
		
		m_nShootStep = 0;
		m_nJumpStep = 0;
		m_nJumpShootStep = 0;
		m_fJumpAngle = 0.0f;
		m_nState = CDogObject.GAME_PLAYER_STOP;

		m_nTapStep1 = -1;
		m_nTapStep2 = -1;
		
		m_nDeadStep = 0;	
		m_DeathAnimationOffset = new PointF();
		m_DeadAnimation = CImageLoader.GetDeadDogImage();
		
		m_bTouched = false;
		m_touchPoint = new PointF(-1.0f, -1.0f);
	}
	
	public void Reset()
	{
		m_nTimerStep = 0;
		m_nAnimationStep = 0;
		m_nState = CDogObject.GAME_PLAYER_STOP;
		resetPosition();
	}
	
	public void UpdateLayout()
	{
		float fWidthInDevice = 0.5f*CGameLayout.ObjectMeasureToDevice(m_fWidth);
		float fHeightInDevice = 0.5f*CGameLayout.ObjectMeasureToDevice(m_fHeight);
		m_CenterInDevice.x = CGameLayout.GameSceneToDeviceX(m_Center.x);
		m_CenterInDevice.y = CGameLayout.GameSceneToDeviceY(m_Center.y);
		m_BoundInDevice.left = (int)(m_CenterInDevice.x - fWidthInDevice);
		m_BoundInDevice.right = (int)(m_CenterInDevice.x + fWidthInDevice);
		m_BoundInDevice.top = (int)(m_CenterInDevice.y - fHeightInDevice);
		m_BoundInDevice.bottom = (int)(m_CenterInDevice.y + fHeightInDevice);
	}
	
	public PointF getHeadPosition()
	{
		PointF pt = new PointF(m_Center.x-m_fWidth*0.45f, m_Center.y+m_fHeight/2.0f);
		return pt;
	}

	public PointF getHeadShootPosition()
	{
        float w = m_fWidth*GAME_PLAYER_WALKSHOOT_X_RATIO;
        float h = m_fHeight*GAME_PLAYER_WALKSHOOT_Y_RATIO;
		PointF pt = new PointF(m_Center.x-w*0.45f, m_Center.y+h/2.0f);
		if(CConfiguration.IsMouthMode() == true)
        {
            pt.x = m_Center.x-w*0.05f; //rt.origin.x + rt.size.width*0.4;
            pt.y = m_Center.y-h*0.1f; //rt.origin.y + h*0.4;
        }
		
		return pt;
	}
	
	public PointF GetCenter()
	{
		return m_Center;
	}

	public float GetCenterX()
	{
		return m_Center.x;
	}

	public float GetCenterY()
	{
		return m_Center.y;
	}
	
	public Rect GetBoundInDevice()
	{
		return m_BoundInDevice;
	}

	public PointF GetCenterInDevice()
	{
		return m_CenterInDevice;
	}
	
	public void moveTo(float sceneX, float sceneY)
	{
		m_Center.set(sceneX, sceneY);
		UpdateLayout();
	}

	public void moveTo(PointF pt)
	{
		m_Center.set(pt);
		UpdateLayout();
	}
	
	public boolean HitTestByDevicePoint(float x, float y)
	{
		return m_BoundInDevice.contains((int)x, (int)y);
	}
	
	public boolean hitHeadByDevicePoint(float x, float y)
	{
		boolean bRet = false;
		
		float left = m_BoundInDevice.left;
		float top = m_BoundInDevice.top;
		float right = left + (m_BoundInDevice.right - m_BoundInDevice.left)*0.4f;
		float bottom = top + (m_BoundInDevice.bottom - m_BoundInDevice.top)*0.4f;
		
		if((left <= x && x <= right) && (top <=y && y <= bottom))
			bRet = true;
		
		return bRet;
	}
	
	/* (non-Javadoc)
	 * @see com.xgadget.ChuiNiuLite.IGunTarget#HitByBullet(com.xgadget.ChuiNiuLite.IBullet)
	 */
	//@Override
	public boolean HitByBullet(IBullet bullet) 
	{
		// TODO Auto-generated method stub
		RectF rect = bullet.getBound();
		if(hitTestWithRect(rect) == true)
		{	
			dead();
			return true;
		}	
		return false;
	}

	private void drawNormal(Canvas canvas)
	{
		if(CConfiguration.IsMouthMode() == true)
		{
	        float fAngle = 0.0f;
	        if(m_nAnimationStep == 0 || m_nAnimationStep == 3)
	            fAngle = 0.0f;
	        else if(m_nAnimationStep == 4)
	            fAngle = -10.0f;
	        else 
	            fAngle = 10.0f*m_nAnimationStep;
	        
	        int nAlpha = 255;
			canvas.save();
			canvas.rotate(fAngle, m_CenterInDevice.x, m_CenterInDevice.y);
			Drawable image = CImageLoader.GetMouth1Image();
			image.setBounds(m_BoundInDevice);
			image.setAlpha(nAlpha);
			image.draw(canvas);
			
			canvas.restore();
		}
		else
		{	
			m_Animations.elementAt(m_nAnimationStep).setBounds(m_BoundInDevice);
			m_Animations.elementAt(m_nAnimationStep).draw(canvas);
		}	
	}
		
	private void drawNormalShoot(Canvas canvas)
	{
		if(CConfiguration.IsMouthMode() == true)
		{
			float w = m_BoundInDevice.width();
			float h = ((float)m_BoundInDevice.height())*GAME_PLAYER_MOUTHSHOOT_Y_RATIO;
			float sx = m_BoundInDevice.left;
			float sy = m_BoundInDevice.top + ((float)m_BoundInDevice.height()) - h;
			Rect rt1 = new Rect((int)sx, (int)sy, (int)(sx + w), (int)(sy + h));
			Drawable image = CImageLoader.GetMouth4Image();
	        if(m_nShootStep == 0 || m_nShootStep == 4)
	        	image = CImageLoader.GetMouth2Image();
	        else if(m_nShootStep == 1 || m_nShootStep == 3)
	        	image = CImageLoader.GetMouth3Image();
	        int nAlpha = 255;
			image.setBounds(rt1);
			image.setAlpha(nAlpha);
			image.draw(canvas);
		}
		else
		{	
			float w = m_BoundInDevice.width()*GAME_PLAYER_WALKSHOOT_X_RATIO;
			float h = ((float)m_BoundInDevice.height())*GAME_PLAYER_WALKSHOOT_Y_RATIO;
			float sx = m_BoundInDevice.left + m_BoundInDevice.width()*GAME_PLAYER_WALKSHOOT_X_RATIO_HALFREVERSE;
			float sy = m_BoundInDevice.top + ((float)m_BoundInDevice.height()) - h;
		
			Rect rt1 = new Rect((int)sx, (int)sy, (int)(sx + w), (int)(sy + h));
		
			m_ShootAnimations.elementAt(m_nShootStep).setBounds(rt1);
			m_ShootAnimations.elementAt(m_nShootStep).draw(canvas);
		}
	}

	private void drawNormalJump(Canvas canvas)
	{
		canvas.save();

		canvas.rotate(m_fJumpAngle, m_CenterInDevice.x, m_CenterInDevice.y);
		
        float w = m_BoundInDevice.width();
        float h = m_BoundInDevice.height();
		
	    if(m_nJumpStep == 2 || m_nJumpStep == 3 || m_nJumpStep == 4)
	    {
	        w = w*DOG_JUMP_X_RATIO;
	        h = h*DOG_JUMP_Y_RATIO;
	    }
	        
        float sx = m_BoundInDevice.left;
        float sy = m_BoundInDevice.top;
		
		Rect rt1 = new Rect((int)sx, (int)sy, (int)(sx + w), (int)(sy + h));
		
		Drawable image = null;
		if(CConfiguration.IsMouthMode() == true)
		{
			image = CImageLoader.GetMouth1Image();
	        int nAlpha = 255;
			image.setAlpha(nAlpha);
		}
		else
		{	
			if(m_nJumpStep == 0 || m_nJumpStep == 1)
				image = CImageLoader.GetDogJump1Image();
			else if(m_nJumpStep == 2 || m_nJumpStep == 3 || m_nJumpStep == 4)	
				image = CImageLoader.GetDogJump2Image();
			else 
				image = CImageLoader.GetDogStopImage();
		}
		if(image != null)
		{	
			image.setBounds(rt1);
			image.draw(canvas);
		}
		canvas.restore();
	}

	
	private void drawJumpShoot(Canvas canvas)
	{
		canvas.save();
		canvas.rotate(m_fJumpAngle, m_CenterInDevice.x, m_CenterInDevice.y);
        float w = m_BoundInDevice.width();
        float h = m_BoundInDevice.height();
		
	    if(m_nJumpStep == 2 || m_nJumpStep == 3 || m_nJumpStep == 4)
	    {
	        w = w*DOG_JUMP_X_RATIO;
	        h = h*DOG_JUMP_Y_RATIO;
	    }
	    if(CConfiguration.IsMouthMode() == true)
	    	h = h*GAME_PLAYER_MOUTHSHOOT_Y_RATIO;
	        
        float sx = m_BoundInDevice.left;
        float sy = m_BoundInDevice.top;
		
		Rect rt1 = new Rect((int)sx, (int)sy, (int)(sx + w), (int)(sy + h));
		
		Drawable image = null;
		if(CConfiguration.IsMouthMode() == true)
		{
			image = CImageLoader.GetMouth4Image();
	        if(m_nShootStep == 0 || m_nShootStep == 4)
	        	image = CImageLoader.GetMouth2Image();
	        else if(m_nShootStep == 1 || m_nShootStep == 3)
	        	image = CImageLoader.GetMouth3Image();
	        int nAlpha = 255;
			image.setAlpha(nAlpha);
		}
		else
		{	
			if(m_nJumpStep == 0 || m_nJumpStep == 1)
				image = CImageLoader.GetDogJumpShoot1Image();
			else if(m_nJumpStep == 2 || m_nJumpStep == 3 || m_nJumpStep == 4)	
				image = CImageLoader.GetDogJumpShoot2Image();
			else 
				image = CImageLoader.GetDogStopImage();
		}	
		if(image != null)
		{	
			image.setBounds(rt1);
			image.draw(canvas);
		}	
		canvas.restore();
	}
	
	private void drawDeath(Canvas canvas)
	{
		float left = m_BoundInDevice.left;
		float top = m_BoundInDevice.top+m_BoundInDevice.height()*(1.0f-GAME_PLAYER_DEAD_SIZE_FACTOR)/1.1f;
		float right = m_BoundInDevice.left+m_BoundInDevice.width()*1.2f;
		float bottom = top+1.2f*m_BoundInDevice.height()*GAME_PLAYER_DEAD_SIZE_FACTOR;
		
		Rect nrt = new Rect((int)left, (int)top, (int)right, (int)bottom);
		if(CConfiguration.IsMouthMode() == true)
		{
	        int nAlpha = 255;
			Drawable image = CImageLoader.GetMouth1Image();
			image.setBounds(nrt);
			image.setAlpha(nAlpha);
			image.draw(canvas);
		}
		else
		{	
			m_DeadAnimation.setBounds(nrt);
			m_DeadAnimation.draw(canvas);
		}	
	}
	
	public void Draw(Canvas canvas)
	{
		if(isNormalAnimation() == true)
			drawNormal(canvas);
		else if(isNormalShootAnimation() == true)
			drawNormalShoot(canvas);
		else if(isNormalJumpAnimation() == true)
			drawNormalJump(canvas);
		else if(isJumpShootAnimation() == true)
			drawJumpShoot(canvas);
		else if(isDead() == true)
			drawDeath(canvas);
	}	
	
	public void DrawWin(Canvas canvas)
	{
		if(CConfiguration.IsMouthMode() == true)
		{
	        int nAlpha = 255;
			float y = CGameLayout.getDogWinY();
			float x = 0.0f;
			float fWidthInDevice = 0.5f*CGameLayout.ObjectMeasureToDevice(m_fWidth);
			float fHeightInDevice = 0.5f*CGameLayout.ObjectMeasureToDevice(m_fHeight);
			float cx = CGameLayout.GameSceneToDeviceX(x);
			float cy = CGameLayout.GameSceneToDeviceY(y);
			Rect rt = new Rect();

			rt.left = (int)(cx - fWidthInDevice);
			rt.right = (int)(cx + fWidthInDevice);
			rt.top = (int)(cy - fHeightInDevice);
			rt.bottom = (int)(cy + fHeightInDevice);
			Drawable image = CImageLoader.GetMouth4Image();
			image.setBounds(rt);
			image.setAlpha(nAlpha);
			image.draw(canvas);
		}
		else
		{	
			float y = CGameLayout.getDogWinY();
			float x = 0.0f;
			float fWidthInDevice = 0.5f*CGameLayout.ObjectMeasureToDevice(m_fWidth*GAME_PLAYER_WALKSHOOT_X_RATIO);
			float fHeightInDevice = 0.5f*CGameLayout.ObjectMeasureToDevice(m_fHeight*GAME_PLAYER_WALKSHOOT_Y_RATIO);
			float cx = CGameLayout.GameSceneToDeviceX(x);
			float cy = CGameLayout.GameSceneToDeviceY(y);
			Rect rt = new Rect();

			rt.left = (int)(cx - fWidthInDevice);
			rt.right = (int)(cx + fWidthInDevice);
			rt.top = (int)(cy - fHeightInDevice);
			rt.bottom = (int)(cy + fHeightInDevice);
			m_ShootAnimations.elementAt(2).setBounds(rt);
			m_ShootAnimations.elementAt(2).draw(canvas);
		}	
	}
	
	public void DrawLose(Canvas canvas)
	{
		drawDeath(canvas);	
	}
	
	public boolean OnTimerEvent()
	{
		boolean bRet = true;
		
		++m_nTimerStep;

		if(GAME_TIMER_PLAYER_STEP <= m_nTimerStep)
		{
			m_nTimerStep = 0;
			bRet = UpdateTimerEvent();
		}
		
		return bRet;
	}
	
	private boolean UpdateTimerEvent()
	{
		boolean bRet = true;
		
		if(isNormalAnimation() == true)
		{	
			m_nAnimationStep = (m_nAnimationStep+1)%5;
			float fbaseY = m_fHeight/2.0f;
			PointF pt = GetCenter();
			if(fbaseY < pt.y)
			{
				pt.y = fbaseY;
				moveTo(pt);
			}	
		}
		else if(isNormalShootAnimation() == true)
		{
			++m_nShootStep;
			if(GAME_SHOOT_FRAME <= m_nShootStep)
			{
				stopShoot();
			}	
			float fbaseY = m_fHeight/2.0f;
			PointF pt = GetCenter();
			if(fbaseY < pt.y)
			{
				pt.y = fbaseY;
				moveTo(pt);
			}	
		}	
		else if(isJump() == true)
		{
			updateJumpState();
		}
		else if(isDead() == true)
		{
			updateDeadState();
		}	
		
		return bRet;
	}

	private void updateJumpState()
	{
		if(CConfiguration.canPlayerJump() == false)
			return;
		
		++m_nJumpStep;
	    if(isShoot() == true)
		{
			++m_nJumpShootStep;
			if(GAME_SHOOT_FRAME <= m_nJumpShootStep)
			{
				stopShoot();
				m_nJumpShootStep = 0;
			}	
		}
		
		if(GAME_SHOOT_FRAME <= m_nJumpStep)
		{
			stopJump();
			return;
		}	
		else 
		{	
			switch (m_nJumpStep) 
			{
				case 0:
					m_fJumpAngle = 60.0f;
					break;
				case 1:
					m_fJumpAngle = 30.0f;
					break;
				case 2:
					m_fJumpAngle = 0.0f;
					break;
				case 3:
					m_fJumpAngle = -60.0f;//30;//210.0f;//-60;
					break;
				case 4:
					m_fJumpAngle = -45.0f;//15;//225.0f;//-45;
					break;
			}
			updateJumpPosition();
		}
	}
	
	private void updateDeadState()
	{
		if(m_nDeadStep < GAME_DEFAULT_PLAYER_DEATH_STEP)
		{
			++m_nDeadStep;
			PointF pt = GetCenter();
			pt.x += m_DeathAnimationOffset.x;
			pt.y += m_DeathAnimationOffset.y;
			moveTo(pt);
			
			if(m_nDeadStep == GAME_DEFAULT_PLAYER_DEATH_STEP)
			{
				pt = GetCenter();
				pt.y = m_fHeight*0.5f;
				moveTo(pt);
				if(m_Controller != null)
					m_Controller.GameLose();
			}	
		}	
		
	}
	
	public boolean isMotion()
	{
		boolean bRet = false;
		if(m_nState == GAME_PLAYER_MOTION || m_nState == GAME_PLAYER_SHOOTANDMOTION)
			bRet = true;
		return bRet;
	}
	
	public boolean isJump()
	{
		boolean bRet = false;
		if(m_nState == GAME_PLAYER_JUMP || m_nState == GAME_PLAYER_SHOOTANDJUMP)
			bRet = true;
		return bRet;
	}
	
	public boolean isShoot()
	{
		boolean bRet = false;
		
		if(m_nState == GAME_PLAYER_SHOOT || m_nState == GAME_PLAYER_SHOOTANDMOTION ||
				   m_nState == GAME_PLAYER_SHOOTANDJUMP)
			bRet = true;
		
		return bRet;
	}
	
	public boolean isShootAndMotion()
	{
		boolean bRet = false;
		if(m_nState == GAME_PLAYER_SHOOTANDMOTION)
			bRet = true;
		
		return bRet;
	}
	
	public boolean isShootAndJump()
	{
		boolean bRet = false;
		if(m_nState == GAME_PLAYER_SHOOTANDJUMP)
			bRet = true;
		return bRet;
	}
	
	public boolean isStop()
	{
		boolean bRet = false;
		
		if(m_nState == GAME_PLAYER_STOP)
			bRet = true;
		
		return bRet;
	}
	
	public boolean isDead()
	{
		boolean bRet = false;
		
		if(m_nState == GAME_PLAYER_DEAD)
			bRet = true;
		
		return bRet;
	}
	
	public boolean isBlocked()
	{
		if(m_Blockage != null)
			return true;
		return false;
	}
	
	public boolean isNormalAnimation()
	{
		boolean bRet = false;
		if(isStop() == true || (isMotion() == true && isShootAndMotion() == false))
		{
			bRet = true;
		}	
		return bRet;
	}	

	public boolean isNormalShootAnimation()
	{
		boolean bRet = false;
		if(isShoot() == true && isShootAndJump() == false)
		{
			bRet = true;
		}	
		return bRet;
	}	

	public boolean isNormalJumpAnimation()
	{
		boolean bRet = false;
		if(isJump() == true && isShootAndJump() == false)
		{
			bRet = true;
		}	
		return bRet;
	}	

	public boolean isJumpShootAnimation()
	{
		boolean bRet = false;
		if(isShootAndJump() == true)
		{
			bRet = true;
		}	
		return bRet;
	}	
	
	public boolean isInTap1()
	{
		boolean bRet = false;
		if(0 <= m_nTapStep1 && m_nTapStep1 <= GAME_TAP_STEP)
		{
			bRet = true;
		}	
		return bRet;
	}

	public void enterTap1()
	{
		m_nTapStep1 = 0;
		m_nTapStep2 = -1;
	}

	public boolean isInWaitTap2()
	{
		boolean bRet = false;
		if(0 <= m_nTapStep2 && m_nTapStep2 <= GAME_TAP_STEP)
		{
			bRet = true;
		}	
		return bRet;
	}

	public void enterTap2()
	{
		m_nTapStep2 = 0;
		m_nTapStep1 = -1;
	}

	public boolean isInTapState()
	{
		boolean bRet = false;
		if(isInTap1() == true || isInWaitTap2() == true)
		{
			bRet = true;
		}	
		return bRet;
	}

	public void cleanTapFlags()
	{
		m_nTapStep1 = -1;
		m_nTapStep2 = -1;
	}	

	public void setTouched(boolean bTouched)
	{
		m_bTouched = bTouched;
	}

	public boolean isTouched()
	{
		return m_bTouched;
	}	

	private void tryToJump()
	{
		if(isJump() == false && CConfiguration.canPlayerJump() == true && isBlocked() == true)
		{	
			startJump();
			setTouched(false);
		}	
		
	}
	
	public void OnFGEventX(boolean bForward)
	{
		float w = CGameLayout.GetGameSceneWidth()*0.5f;
		
		float fStep = CConfiguration.getPlayerMotionStep()*2.0f;

		if(((m_Center.x - m_fWidth/2) <= -w || (m_Center.x - fStep- m_fWidth/2) <= -w) && bForward == true)
			return;
				
		if((w <= (m_Center.x + m_fWidth/2) || w <= (m_Center.x + fStep + m_fWidth/2)) && bForward == false)
			return;
		
		if(bForward == true)
		{
			if(isBlocked() == false)
			{	
				float cX = GetCenterX();
				float cY = GetCenterY();
				cX -= fStep;
				moveTo(cX, cY);
			}
			if((m_Center.x - m_fWidth/2) <= -w || (m_Center.x - fStep- m_fWidth/2) <= -w)
				m_Center.x =  fStep+m_fWidth/2-w;
		}
		else
		{
			m_Center.x += fStep;
			if(w <= (m_Center.x + m_fWidth/2) || w <= (m_Center.x + fStep + m_fWidth/2))
				m_Center.x = w  - fStep - m_fWidth/2;
		}
		
		UpdateLayout();
		
	}
	
	public void OnFGEventY()
	{
		startShoot();
		tryToJump();
	}
	
	public boolean OnKeyDown(int keyCode)
	{
		boolean bRet = true;
		switch(keyCode)
		{
			case KeyEvent.KEYCODE_DPAD_CENTER:
				startShoot();
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT: 
				bRet = MoveBack();
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN: 
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT: 
				bRet = MoveForward();
				break;
			case KeyEvent.KEYCODE_DPAD_UP:
				tryToJump();
				break;
		}
		return bRet;
	}

	public boolean OnKeyUp(int keyCode)
	{
		boolean bRet = true;
		
		switch(keyCode)
		{
			case KeyEvent.KEYCODE_DPAD_CENTER:
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT: 
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN: 
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT: 
				break;
			case KeyEvent.KEYCODE_DPAD_UP:
				break;
		}
		
		return bRet;
	}

	private void setStateToStartShoot()
	{
		if(m_nState == GAME_PLAYER_DEAD)
			return;
		
		if(m_nState == GAME_PLAYER_MOTION)
			m_nState = GAME_PLAYER_SHOOTANDMOTION;
		if(m_nState == GAME_PLAYER_JUMP)
			m_nState = GAME_PLAYER_SHOOTANDJUMP;
		else 
			m_nState = GAME_PLAYER_SHOOT;
	}

	private void setStateToStopShoot()
	{
		if(m_nState == GAME_PLAYER_DEAD)
			return;
		
		if(m_nState == GAME_PLAYER_SHOOTANDMOTION)
			m_nState = GAME_PLAYER_MOTION;
		else if(m_nState == GAME_PLAYER_SHOOTANDJUMP)
			m_nState = GAME_PLAYER_JUMP;
		else 
			m_nState = GAME_PLAYER_STOP;
	}
	
	public void startShoot()
	{
		m_nShootStep = 0;
		if(m_Gun != null && 0 < m_Gun.bulletInGun())
		{
			if(m_Controller != null)
				m_Controller.PlaySound(CGameAudio.GAME_SOUND_ID_PLAYERSHOOT);
			setStateToStartShoot();
			//PointF pt = getHeadPosition();
			PointF pt = getHeadShootPosition();
			PointF v = new PointF(GAME_DEFAULT_PLAYER_BULLET_SPEED_X, GAME_DEFAULT_PLAYER_BULLET_SPEED_Y);
			m_Gun.shootAt(pt,  v);
	}	
	}

	public void stopShoot()
	{
		setStateToStopShoot();
		m_nShootStep = 0;
	}
	
	private void setStateToDead()
	{
		m_nState = GAME_PLAYER_DEAD;
	}
	
	private void setStateToStartJump()
	{
		if(m_nState == GAME_PLAYER_DEAD)
			return;
		
		if(m_nState == GAME_PLAYER_SHOOT)
			m_nState = GAME_PLAYER_SHOOTANDJUMP;
		else 
			m_nState = GAME_PLAYER_JUMP;
	}
	
	public void startJump()
	{
		if(CConfiguration.canPlayerJump() == false)
			return;
		
		m_nJumpStep = 0;
		m_fJumpAngle = 60.0f;
		m_nJumpShootStep = 0;
		if(isNormalShootAnimation() == true)
		{
			m_nJumpShootStep = m_nShootStep;
			m_nShootStep = 0; 
			if(GAME_SHOOT_FRAME <= m_nJumpShootStep)
			{
				stopShoot();
			}	
		}	
		if(m_Controller != null)
			m_Controller.PlaySound(CGameAudio.GAME_SOUND_ID_JUMP);
		
		setStateToStartJump();
		updateJumpPosition();
	}
	
	private void setStateToStopJump()
	{
		if(m_nState == GAME_PLAYER_DEAD || isMotion() == true)
			return;
		
		switch (m_nState)
		{
			case GAME_PLAYER_SHOOTANDJUMP:
				m_nState = GAME_PLAYER_SHOOT;
				break;
			default:	
				m_nState = GAME_PLAYER_STOP;
				break;
		}		
	}
	
	public void stopJump()
	{
		setStateToStopJump();
		
		m_nJumpStep = 0;
		m_fJumpAngle = 0.0f;
		cleanTapFlags();
		updateJumpPosition();
		if(isShoot() == true)
		{
			m_nShootStep = m_nJumpShootStep;
			m_nJumpShootStep = 0; 
			if(GAME_SHOOT_FRAME <= m_nJumpShootStep)
			{
				stopShoot();
				m_nJumpShootStep = 0;
			}	
		}	
		if(m_Blockage != null)
			m_Blockage.detachTarget();
		
		m_Blockage = null;
		if(m_Controller != null)
			m_Controller.SwitchToBackgroundSound();
	}	
	
	public boolean MoveBack()
	{
		boolean bRet = false;
		
		m_Center.x += CConfiguration.getPlayerMotionStep();
		UpdateLayout();
		bRet = true;
		
		return bRet;
	}
	
	public boolean MoveForward()
	{
		boolean bRet = false;
		m_Center.x -= CConfiguration.getPlayerMotionStep();
		UpdateLayout();
		bRet = true;
		return bRet;
	}

	public void updateJumpPosition()
	{
		float fbaseY = m_fHeight/2.0f;//GetCenterY();
		float fv = CGameLayout.GetRockMeasureWidth() + m_fWidth;
		float fh = CGameLayout.GetRockMeasureHeight()*2.0f;
		if(m_Blockage != null)
		{	
			fv = m_Blockage.getBound().width()+m_fWidth;
			fh = m_Blockage.getBound().height()*2.0f;
		}
		
		float fx = fv/5.0f;
		float fy = fh/3.0f;

		PointF pt = new PointF(m_Center.x, m_Center.y);
		
		switch (m_nJumpStep) 
		{
			case 0:
				pt.x -= fx;
				pt.y += fy;
				break;
			case 1:
				pt.x -= fx;
				pt.y += fy;
				break;
			case 2:
				pt.x -= fx;
				pt.y += fy;
				break;
			case 3:
				pt.x -= fx;
				pt.y -= fy;
				break;
			case 4:
				pt.x -= fx;
				pt.y -= fy;
				break;
			case 5:
				pt.x -= fx;
				pt.y -= fy;
				break;
		}
		if(isJump() == false || pt.y < fbaseY)
			pt.y = fbaseY;
		
		moveTo(pt);
		if(pt.x < (CGameLayout.GetGameSceneWidth()*(-0.5f)))
		{
			pt.x = CGameLayout.GetGameSceneWidth()*(-0.5f);
			moveTo(pt);
		}	
	}
	
	public void fire()
	{
		setStateToStartShoot();
	}
	
	public int bulletIlnGun()
	{
		int nRet = 0;
		if(m_Gun != null)
		{
			nRet = m_Gun.bulletInGun();
		}	
		return nRet;
	}

	public RectF getBound()
	{
		float left = m_Center.x - m_fWidth*0.5f;
		float right = m_Center.x + m_fWidth*0.5f;
		float top = m_Center.y - m_fHeight*0.5f;
		float bottom = m_Center.y + m_fHeight*0.5f;
		
		RectF rect = new RectF(left, top, right, bottom);
		return rect;
	}
	
	public void deadToGround()
	{
		if(m_Controller != null)
			m_Controller.PlaySound(CGameAudio.GAME_SOUND_ID_CRASH);
		
		PointF pt = GetCenter();
		pt.y = m_fHeight*0.5f;
		moveTo(pt);
		
		m_nDeadStep = GAME_DEFAULT_PLAYER_DEATH_STEP;
		setStateToDead();
		if(m_Controller != null)
			m_Controller.GameLose();
		
	}
	
	public void dead()
	{
		if(m_Controller != null)
			m_Controller.PlaySound(CGameAudio.GAME_SOUND_ID_CRASH);
		
		float x = 0.0f;
		float y = 0.0f;
		if(isJump() == true)
		{
			//Blockage* block = (Blockage*)m_Blockage;
			float ylast = m_fHeight*0.5f;
			RectF brt = m_Blockage.getBound();
			PointF cpt = GetCenter();
			float xlast;
			if(m_nJumpStep == 0 || m_nJumpStep == 1) 
			{
				xlast = brt.left + brt.width()+m_fWidth*0.5f;
				x = (xlast - cpt.x)/(float)(GAME_DEFAULT_PLAYER_DEATH_STEP-1);
				y = (ylast - cpt.y)/(float)(GAME_DEFAULT_PLAYER_DEATH_STEP-1);
				stopJump();
			}
			else
			{
				xlast = brt.left - m_fWidth*0.5f;
				x = (xlast - cpt.x)/(float)(GAME_DEFAULT_PLAYER_DEATH_STEP-1);
				y = (ylast - cpt.y)/(float)(GAME_DEFAULT_PLAYER_DEATH_STEP-1);
				stopJump();
			}	
		}
		m_DeathAnimationOffset.set(x, y);
		setStateToDead();
		m_nDeadStep = 0;	
	}
	
	public void pushBack(float x)
	{
		PointF pt = GetCenter();
		pt.x += x;
		moveTo(pt);
		if(outOfSceneBound() == true)
		{
			if(m_Controller != null)
				m_Controller.GameLose();
		}
	}

	public boolean hitTestWithRect(RectF rect) 
	{
		// TODO Auto-generated method stub
		boolean bRet = false;
		
		if(rect.intersect(m_Center.x-m_fWidth*0.5f, m_Center.y-m_fHeight*0.5f, m_Center.x+m_fWidth*0.5f, m_Center.y+m_fHeight*0.5f) == true)
			bRet = true;
		
		return bRet;
	}
	
	public boolean hitTestWithPoint(PointF pt)
	{
		boolean bRet = false;
		
		if((m_Center.x-m_fWidth*0.5f) <= pt.x && (m_Center.y-m_fHeight*0.5f) <= pt.y && pt.x <= (m_Center.x+m_fWidth*0.5f) && pt.y <= (m_Center.y+m_fHeight*0.5f))
			bRet = true;
		
		return bRet;
	}

	public boolean blockBy(IBlockage blockage)
	{
		boolean bRet = false;
	
		CBlockageObject block = (CBlockageObject)blockage;
		if(block != null)
		{
			PointF pt = block.getPosition();
			PointF mypt = GetCenter();
			if((block.hitTestWithPoint(mypt) == true || hitTestWithPoint(pt) == true) && pt.x <= mypt.x)
			{
				bRet = true;
				block.m_BlockTarget = this;
				m_Blockage = block;
				mypt.x = pt.x + (block.getBound().width()+m_fWidth)*0.5f;
				moveTo(mypt);
				if(m_Controller != null)
					m_Controller.PlayBlockageSound();
			}	
		}	
		return bRet;
	}

	public boolean hitTestWithBlockage(IBlockage blockage)
	{
		boolean bRet = false;
	
		CBlockageObject block = (CBlockageObject)blockage;
		if(block != null)
		{
			RectF rt = block.getBound();
			if(hitTestWithRect(rt) == true)
			{
				bRet = true;
			}	
		}	
	
		return bRet;
	}

	public void escapeFromBlockage(IBlockage blockage)
	{
		CBlockageObject block = (CBlockageObject)blockage;
		if(block != null)
		{
			RectF rt = block.getBound();
			PointF pt = GetCenter();
			pt.x = rt.left - m_fWidth*0.5f;
			moveTo(pt);
		}	
	}

	public boolean moveBackFromBlockage(float x)
	{
		if(m_Blockage != null && 0 < x)
		{
			RectF blkrt = m_Blockage.getBound();
			if((blkrt.left+blkrt.width()) < (m_Center.x - m_fWidth*0.5f + x))
			{
				PointF mypt = GetCenter();
				mypt.x += x;
				moveTo(mypt);
				m_Blockage.m_BlockTarget = null;
				m_Blockage = null;
				if(m_Controller != null)
					m_Controller.SwitchToBackgroundSound();
				return true;
			}	
		}	
		return false;
	}

	public boolean resetPosition()
	{
		if(m_Blockage == null)
		{	
			setToStartPosition();
			return true;
		}
	
		return false;
	}	

	public void setToStartPosition()
	{
		m_Center.x = 0;
		m_Center.y = m_fHeight/2.0f;
		UpdateLayout();
	}
	
	public boolean outOfSceneBound()
	{
		boolean bRet = false;

		float w = CGameLayout.GetGameSceneWidth()*0.5f;
		float h = CGameLayout.GetGameSceneHeight();
		
		if(m_Center.x < -w || w < m_Center.x || m_Center.y < 0 || h < m_Center.y)
			bRet = true;
		return bRet;
	}
}
