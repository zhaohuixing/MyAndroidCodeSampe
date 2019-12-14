/**
 * 
 */
package com.xgadget.ChuiNiuLite;
import java.util.Random;
import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
//import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
//import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

/**
 * @author ZXing
 *
 */
public class CCowObject implements IGunTarget, IGunOwner
{
	public static final int GAME_TARGET_NORMAL = 0;
	public static final int GAME_TARGET_SHOOT = 1;		// target shoot out			
	public static final int GAME_TARGET_BLOWOUT = 2;	// player blow out too much to break the target			
	public static final int GAME_TARGET_CRASH = 3;		// fallingdwon naturally by not knockdown			
	public static final int GAME_TARGET_KNOCKDOWN = 4;	// hit by alien
	public static final int GAME_TARGET_ANIMATION_FRAME = 6;
	public static final int GAME_TARGET_KNOCKDOWN_FRAME = 12;
	public static final int GAME_TARGET_BLOWOUT_FRAME = 3;
	
	public static final float GAME_DEFAULT_TARGET_SPEED_X = 0.0f;
	public static final float GAME_DEFAULT_TARGET_SPEED_Y = -1.0f;
	public static final float GAME_DEFAULT_TARGET_FLOAT_SPEED_Y	= 16.0f;
	public static final float GAME_DEFAULT_TARGET_FLOAT_SPEED_X	= 0.0f;

	public static final float GAME_DEFAULT_TARGET_BULLET_SPEED_X = 8.0f;
	public static final float GAME_DEFAULT_TARGET_BULLET_SPEED_Y = -16.0f;


	private float m_fWidth;
	private float m_fHeight;
	private PointF m_Center;
	private Rect m_BoundInDevice;
	private PointF m_CenterInDevice;
	private Vector<Bitmap> 	m_CowBitmaps;
	private CGameScene		m_Controller;
	
	private int	m_nTimerStep;
	private int	m_nTimerElapse;
	private int m_nState;
	private float[] m_AnimationAngle;
	public CGunObject	m_Gun;
	private PointF	m_Speed;
	

	private float			m_fBack;
	private int				m_nShootStep;
	private int				m_nShootThreshold;
	private int				m_nShootMaxInterval;
	private int				m_nAnimationStep;
	private int				m_nAnimationDelay;
	private int				m_nAnimationDelayThreshold;
	private int				m_nKnockDownStep;
	private float           m_fKnockDownStepLenght;
	private Drawable		m_DeathAnimation;
	
	private int				m_nHitThreshold;
	private int				m_nHitCount;
	private int				m_nHitCountStep;
	private int				m_nHitDeducable;
	
	private int				m_nBlowOutStep;
	private Drawable		m_BlastAnimation;

	public CCowObject(CGameScene parent)
	{
		m_Controller = parent;
		m_fHeight = CGameLayout.GetCowHeight();
		m_fWidth = CGameLayout.GetCowWidth();
		m_Center = new PointF();
		m_Center.x = 0;
		m_Center.y = CGameLayout.m_fGameSceneMeasureHeight-m_fHeight/2.0f;
		m_CenterInDevice = new PointF();
		m_BoundInDevice = new Rect();
		m_CowBitmaps = new Vector<Bitmap>();
		m_CowBitmaps.add(CImageLoader.GetRawCow1Bitmap());
		m_CowBitmaps.add(CImageLoader.GetRawCow2Bitmap());
		m_CowBitmaps.add(CImageLoader.GetRawCow3Bitmap());
		m_CowBitmaps.add(CImageLoader.GetRawCow2Bitmap());
		m_CowBitmaps.add(CImageLoader.GetRawCow1Bitmap());
		m_CowBitmaps.add(CImageLoader.GetRawCow4Bitmap());
		
		m_AnimationAngle = new float[GAME_TARGET_ANIMATION_FRAME];
		m_AnimationAngle[0] = 0.0f;
		m_AnimationAngle[1] = 8.0f;
		m_AnimationAngle[2] = 16.0f;
		m_AnimationAngle[3] = 8.0f;
		m_AnimationAngle[4] = 0.0f;
		m_AnimationAngle[5] = -8.0f;
		
		m_nTimerStep = 0;
		m_nTimerElapse = CConfiguration.getTargetTimerStep();

		m_fBack = GAME_DEFAULT_TARGET_FLOAT_SPEED_Y;
		
		m_nShootStep = 0;
		m_nShootMaxInterval = CConfiguration.GAME_DEFAULT_TARGET_SHOOT_THRESHED;
		Random rand = new Random();
		int nRand = rand.nextInt();
		if(nRand < 0)
			nRand *= -1;
		m_nShootThreshold = nRand%m_nShootMaxInterval+1;
		
		m_nAnimationStep = 0;
		m_nKnockDownStep = 0;
		m_fKnockDownStepLenght = 0.0f;
		
		m_nHitThreshold = CConfiguration.getTargetHitLimit();
		m_nHitCount = 0;
		m_nHitCountStep = 0;
		m_nHitDeducable = CConfiguration.getTargetHitDeductable();
	
		m_nAnimationDelay = 0;
		m_nAnimationDelayThreshold = CConfiguration.getTargetAnimationDelayThreshold();
	
		m_DeathAnimation = CImageLoader.GetDeadCowImage();
		m_BlastAnimation = CImageLoader.GetBlastImage();

		m_Speed = new PointF();
		m_Speed.set(CConfiguration.GAME_DEFAULT_TARGET_SPEED_X, CConfiguration.getTargetSpeedY());
		m_nState = CCowObject.GAME_TARGET_NORMAL;
	}
	
	public void Reset()
	{
		m_nTimerStep = 0;
		m_nTimerElapse = CConfiguration.getTargetTimerStep();
		m_nState = CCowObject.GAME_TARGET_NORMAL;
	
	
		setToStartPosition();
		
		m_fBack = GAME_DEFAULT_TARGET_FLOAT_SPEED_Y;
		
		m_nShootStep = 0;
		m_nShootMaxInterval = CConfiguration.GAME_DEFAULT_TARGET_SHOOT_THRESHED;
		Random rand = new Random();
		int nRand = rand.nextInt();
		if(nRand < 0)
			nRand *= -1;
		m_nShootThreshold = nRand%m_nShootMaxInterval+1;
		
		m_nAnimationStep = 0;
		m_nKnockDownStep = 0;
		m_fKnockDownStepLenght = 0.0f;
		
		m_nHitThreshold = CConfiguration.getTargetHitLimit();
		m_nHitCount = 0;
		m_nHitCountStep = 0;
		m_nHitDeducable = CConfiguration.getTargetHitDeductable();
	
		m_nAnimationDelay = 0;
		m_nAnimationDelayThreshold = CConfiguration.getTargetAnimationDelayThreshold();
		setSpeed(CConfiguration.GAME_DEFAULT_TARGET_SPEED_X, CConfiguration.getTargetSpeedY());
	}
	
	public void setToStartPosition()
	{
		m_Center.x = 0;
		m_Center.y = CGameLayout.m_fGameSceneMeasureHeight-m_fHeight/2.0f;
		UpdateLayout();
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
	
	public PointF GetCenter()
	{
		return m_Center;
	}

	public Rect GetBoundInDevice()
	{
		return m_BoundInDevice;
	}

	public PointF GetCenterInDevice()
	{
		return m_CenterInDevice;
	}

	private Bitmap GetCowBitmap(int nIndex)
	{
		int n = 0;
		if(0 <= nIndex && nIndex < GAME_TARGET_ANIMATION_FRAME)
			n = nIndex;
		
		return m_CowBitmaps.elementAt(n);
	}
	
	private void DrawNormalNonHit(Canvas canvas)
	{
		//int index = m_nTimerStep%GAME_TARGET_ANIMATION_FRAME; 
		Bitmap bmp = GetCowBitmap(m_nAnimationStep);
		if(bmp != null)
		{
			Drawable image = new BitmapDrawable(CImageLoader.GetGameResource(), bmp);
			image.setBounds(m_BoundInDevice);
			canvas.save();
			canvas.rotate(m_AnimationAngle[m_nAnimationStep], m_CenterInDevice.x, m_CenterInDevice.y);
			image.draw(canvas);
			canvas.restore();
			
		}
	}
	
	private void DrawNormalHitted(Canvas canvas)
	{
		//int index = m_nTimerStep%GAME_TARGET_ANIMATION_FRAME; 
		Bitmap bmp = GetCowBitmap(m_nAnimationStep);
		if(bmp != null)
		{
			float fRatio = 255.0f*((float)m_nHitCount)/((float)m_nHitThreshold);
			int alpha = (int)fRatio;
			alpha = (alpha+1)%255+1;
			
            canvas.save();
			canvas.rotate(m_AnimationAngle[m_nAnimationStep], m_CenterInDevice.x, m_CenterInDevice.y);
			Rect srcrt = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
            Paint p = new Paint();
            LightingColorFilter cf = new LightingColorFilter(Color.rgb(255-alpha, 255-alpha, 255-alpha), Color.rgb(255-alpha, 255-alpha, 255-alpha));
            p.setColorFilter(cf);
			canvas.drawBitmap(bmp, srcrt, m_BoundInDevice, p);
			canvas.restore();
			
		}
	}
	
	private void DrawNormal(Canvas canvas)
	{
		if(CConfiguration.canTargetBlast() == false || m_nHitCount == 0)
		{
			DrawNormalNonHit(canvas);
		}
		else
		{
			DrawNormalHitted(canvas);
		}
	}
	
	private void DrawKnockout(Canvas canvas)
	{
		float fAngle = (-60)*((float)(m_nKnockDownStep+1));

		m_DeathAnimation.setBounds(m_BoundInDevice);
		canvas.save();
		canvas.rotate(fAngle, m_CenterInDevice.x, m_CenterInDevice.y);
		m_DeathAnimation.draw(canvas);
		canvas.restore();
	}
	
	private void DrawCrash(Canvas canvas)
	{
		m_DeathAnimation.setBounds(m_BoundInDevice);
		m_DeathAnimation.draw(canvas);
	}
	
	private void DrawBlast(Canvas canvas)
	{
		if(GAME_TARGET_BLOWOUT_FRAME <= m_nBlowOutStep)
			return;
		
		float cx = m_CenterInDevice.x;
		float cy = m_CenterInDevice.y;
		float fAngle = ((float)(m_nBlowOutStep))/((float)GAME_TARGET_BLOWOUT_FRAME);
		float w1 = ((float)m_BoundInDevice.width())*(1.0f-fAngle);
		float h1 = ((float)m_BoundInDevice.height())*(1.0f-fAngle);
		float w2 = ((float)m_BoundInDevice.width())*(fAngle+0.3f);
		float h2 = ((float)m_BoundInDevice.height())*(fAngle+0.3f);
		if(0 < w1 && 0 < h1)
		{	
			Rect rt1 = new Rect((int)(cx-w1*0.5), (int)(cy-h1*0.5), (int)(cx+w1*0.5), (int)(cy+h1*0.5)); 
			int index = m_nTimerStep%GAME_TARGET_ANIMATION_FRAME; 
			Bitmap bmp = GetCowBitmap(index);
			if(bmp != null)
			{
				Drawable image = new BitmapDrawable(CImageLoader.GetGameResource(), bmp);
				image.setBounds(rt1);
				image.draw(canvas);
			}
		}
		Rect rt2 = new Rect((int)(cx-w2*0.5), (int)(cy-h2*0.5), (int)(cx+w2*0.5), (int)(cy+h2*0.5)); 
		m_BlastAnimation.setBounds(rt2);
		m_BlastAnimation.draw(canvas);
	}
	
	public void Draw(Canvas canvas)
	{
		if(isNormal() == true)
			DrawNormal(canvas);
		else if(isKnockout() == true)
			DrawKnockout(canvas);
		else if(isCrash() == true)
			DrawCrash(canvas);
		else if(isBlowout() == true)
			DrawBlast(canvas);
	}	
	
	public void DrawWin(Canvas canvas)
	{
		float y = CGameLayout.getCowWinY();
		float x = -15.0f;
		float fWidthInDevice = 0.5f*CGameLayout.ObjectMeasureToDevice(m_fWidth);
		float fHeightInDevice = 0.5f*CGameLayout.ObjectMeasureToDevice(m_fHeight);
		float cx = CGameLayout.GameSceneToDeviceX(x);
		float cy = CGameLayout.GameSceneToDeviceY(y);
		Rect rt = new Rect();

		rt.left = (int)(cx - fWidthInDevice);
		rt.right = (int)(cx + fWidthInDevice);
		rt.top = (int)(cy - fHeightInDevice);
		rt.bottom = (int)(cy + fHeightInDevice);
	
		Bitmap bmp = GetCowBitmap(5);
		if(bmp != null)
		{
			Drawable image = new BitmapDrawable(CImageLoader.GetGameResource(), bmp);
			image.setBounds(rt);
			canvas.save();
			
			float angle; 
			//angle = 360.0f*m_AnimationAngle[5];
			angle = m_AnimationAngle[5];
			canvas.rotate(angle, cx, cy);
			
			image.draw(canvas);
			canvas.restore();
		}
	}
	
	public void DrawLose(Canvas canvas)
	{
		PointF pt = GetCenter();
		pt.y = m_fHeight*0.5f;
		float fWidthInDevice = 0.5f*CGameLayout.ObjectMeasureToDevice(m_fWidth);
		float fHeightInDevice = 0.5f*CGameLayout.ObjectMeasureToDevice(m_fHeight);
		float cx = CGameLayout.GameSceneToDeviceX(pt.x);
		float cy = CGameLayout.GameSceneToDeviceY(pt.y);
		Rect rt = new Rect();
		rt.left = (int)(cx - fWidthInDevice);
		rt.right = (int)(cx + fWidthInDevice);
		rt.top = (int)(cy - fHeightInDevice);
		rt.bottom = (int)(cy + fHeightInDevice);
		m_DeathAnimation.setBounds(rt);
		m_DeathAnimation.draw(canvas);
	}
	
	
	public boolean OnTimerEvent()
	{
		boolean bRet = true;
		
		++m_nTimerStep;

		if(m_nTimerElapse <= m_nTimerStep)
		{
			m_nTimerStep = 0;
			UpdateTimerEvent();
			bRet = true;
		}
		
		return bRet;
	}
	
	private void updateKnockDownTimerEvent()
	{
		if(m_nKnockDownStep < GAME_TARGET_KNOCKDOWN_FRAME)
		{
			++m_nKnockDownStep;
			updateKnockDownPosition();
			if(m_nKnockDownStep == GAME_TARGET_KNOCKDOWN_FRAME && isCrash() == false)
			{
				crash();
			}	
		}
	}

	private void updateBlowoutTimerEvent()
	{
		++m_nHitCountStep;

		if(4 <=	m_nHitCountStep)
		{	
			m_nHitCountStep = 0;
			m_nHitCount = m_nHitCount - m_nHitDeducable;
			if(m_nHitCount < 0)
				m_nHitCount = 0;
		}	
	}	
	 
	private void updateBlastTimerEvent()
	{
		++m_nBlowOutStep;
		if(GAME_TARGET_BLOWOUT_FRAME <= m_nBlowOutStep)
		{
			if(m_Controller != null)
				m_Controller.GameLose();
		}
	}
		
	private boolean detectHitGround()
	{
		PointF pt = GetCenter();
		if((pt.y - m_fHeight*0.5) <= 0.0f)
			return true;

		return false;
	}
	
	
	private void UpdateTimerEvent()
	{
		if(isNormal() == true)
		{	
			PointF pt = GetCenter();
			PointF v = getSpeed();
			pt.x += v.x;
			pt.y += v.y;
			if(pt.y <= 0)
				pt.y = 0;
			
			moveTo(pt);
			if(detectHitGround() == true)
			{
				crash();
				return;
			}	
			
			++m_nAnimationDelay;
			if(m_nAnimationDelayThreshold <= m_nAnimationDelay)
			{
				m_nAnimationDelay = 0;
				m_nAnimationStep = (m_nAnimationStep+1)%GAME_TARGET_ANIMATION_FRAME;
			}
			
			if(CConfiguration.canTargetBlast() == true)
			{
				updateBlowoutTimerEvent();
			}	
			if(detectHitPlayer() == true)
				return;
		}
		else if(isKnockout() == true) 
		{
			updateKnockDownTimerEvent();
		}
		else 
		{
			updateBlastTimerEvent();
		}
		if(CConfiguration.canTargetShoot() == true && isNormal() == true)
		{
			++m_nShootStep;
			if(m_nShootThreshold <= m_nShootStep)
			{	
				Random rand = new Random();
				int nRand = rand.nextInt();
				if(nRand < 0)
					nRand *= -1;
				m_nShootThreshold = nRand%m_nShootMaxInterval+1;
				m_nShootStep = 0;
				shoot();
			}	
		}	
	}
	
	public PointF getGunPoint()
	{
		PointF pt = new PointF();
		pt.x = m_Center.x + m_fWidth*0.2f;//*0.25f;
		pt.y = m_Center.y - m_fHeight*0.45f;//*0.3f;
		return pt;
	}
	
	public void shoot()
	{
		m_nState = GAME_TARGET_SHOOT;
		if(m_Gun != null && 0 < m_Gun.bulletInGun())
		{
			if(m_Controller != null)
				m_Controller.PlaySound(CGameAudio.GAME_SOUND_ID_TARGETSHOOT);
			PointF pt = getGunPoint();
			PointF speed = CConfiguration.getTargetBulletSpeed();
			speed.x = speed.x/4.0f;
			speed.y = speed.y/4.0f;
			
			m_Gun.shootAt(pt, speed);
		}	
	}

	public void crash()
	{
		PointF pt = GetCenter();
		pt.y = m_fHeight*0.5f;
		moveTo(pt);
		m_nState = GAME_TARGET_CRASH;
		if(m_Controller != null)
			m_Controller.GameLose();
	}

	public void blowout()
	{
		if(m_Controller != null)
		{	
			m_Controller.StopSound(CGameAudio.GAME_SOUND_ID_TARGETHORN);
			m_Controller.PlaySound(CGameAudio.GAME_SOUND_ID_BLAST);
		}	
		m_nBlowOutStep = 0;
		m_nState = GAME_TARGET_BLOWOUT;
	}

	public void knockdown()
	{
		m_nState = GAME_TARGET_KNOCKDOWN;
	}
	
	private boolean hitTestWithRectInDevice(Rect rectInDevice)
	{
		boolean bRet = false;
		
		if(this.m_BoundInDevice.intersect(rectInDevice) == true)
			bRet = true;
		
		return bRet;
	}
	public boolean knockdownBy(CCloudObject alien)
	{
		//RectF rect = alien.getBound();
		Rect rectInDevice = alien.getThreeFourthBoundIndevice();
		
		//if(hitTestWithRect(rect) == true)
		if(hitTestWithRectInDevice(rectInDevice) == true)
		{
			if(m_Controller != null)
				m_Controller.PlaySound(CGameAudio.GAME_SOUND_ID_TARGETKNOCKDOWN);
			m_nKnockDownStep = 0;
			m_fKnockDownStepLenght = (m_fHeight*0.5f-m_Center.y)/((float)GAME_TARGET_KNOCKDOWN_FRAME);
			knockdown();
			updateKnockDownPosition();
			return true;
		}	
		
		return false;
	}
	
	public boolean detectHitPlayer()
	{
		CDogObject plyerobj = (CDogObject)m_Gun.m_Target;
		if(plyerobj != null)
		{	
			RectF port = plyerobj.getBound();
			if(hitTestWithRect(port) == true)
			{
				plyerobj.deadToGround();
			    crash();
				return true;
			}	
		}	
		return false;
	}	

	public void updateKnockDownPosition()
	{
		PointF pt = GetCenter();
	    pt.y += m_fKnockDownStepLenght;
		moveTo(pt);
		detectHitPlayer();
	}

	private void updateHitBearingState()
	{
		if(m_nHitThreshold < m_nHitCount)
		{
			blowout();
		}
		else if(m_nHitThreshold*3/5 <= m_nHitCount)
		{
			if(m_Controller != null)
				m_Controller.PlaySound(CGameAudio.GAME_SOUND_ID_TARGETHORN);
		}

	}
	
	//@Override
	public boolean HitByBullet(IBullet bullet)
	{
		boolean bRet = false;
		
		RectF rect = bullet.getBound();
		if(isNormal() == true && hitTestWithRect(rect) == true)
		{	
			PointF pt = GetCenter();
			pt.y += m_fBack;
			moveTo(pt);
			pt = GetCenter();
			float h = CGameLayout.GetGameSceneHeight();
			float myh = m_fHeight*0.5f;
			if(h < pt.y+myh)
			{	
				pt.y = h-myh; 
				moveTo(pt);
			}
			
			if(CConfiguration.canTargetBlast() == true)
			{
				++m_nHitCount;
				updateHitBearingState();
			}	
			return true;
		}	
		
		return bRet;
	}
	
	public boolean isShoot()
	{
		boolean bRet = false;
		
		if(m_nState == CCowObject.GAME_TARGET_SHOOT)
			bRet = true;
		
		return bRet;
	}

	public boolean isCrash()
	{
		boolean bRet = false;
		
		if(m_nState == CCowObject.GAME_TARGET_CRASH)
			bRet = true;
		
		return bRet;
	}

	public boolean isBlowout()
	{
		boolean bRet = false;
		
		if(m_nState == CCowObject.GAME_TARGET_BLOWOUT)
			bRet = true;
		
		return bRet;
	}

	public boolean isKnockout()
	{
		boolean bRet = false;
		
		if(m_nState == CCowObject.GAME_TARGET_KNOCKDOWN)
			bRet = true;
		
		return bRet;
	}	

	public boolean isNormal()
	{
		boolean bRet = false;
		
		if(m_nState == CCowObject.GAME_TARGET_NORMAL)
			bRet = true;
		
		return bRet;
	}
	
	public void fire()
	{
		m_nState = CCowObject.GAME_TARGET_NORMAL;
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
		if(CConfiguration.IsMouthMode() == true && CConfiguration.IsFGEanbled() == true)
		{
			if(CConfiguration.IsHoldFGTracking() == true)
			{
				PointF speed = new PointF(m_Speed.x, m_Speed.y/5.0f);
				return speed;
			}
			else
			{
				PointF speed = new PointF(m_Speed.x, 0.0f);
				return speed;
			}
		}

		return m_Speed;
	}
	
	public void moveTo(PointF pt)				//Base on game scence coordinate system
	{
		m_Center.set(pt);
		UpdateLayout();
	}

	public void moveTo(float x, float y)				//Base on game scence coordinate system
	{
		m_Center.set(x, y);
		UpdateLayout();
	}
	
	
	public boolean hitTestWithPoint(PointF pt)
	{
		boolean bRet = false;
		
		float left = m_Center.x - m_fWidth*0.5f;
		float right = m_Center.x + m_fWidth*0.5f;
		float top = m_Center.y - m_fHeight*0.5f;
		float bottom = m_Center.y + m_fHeight*0.5f;
		
		if(left <= pt.x && pt.x <= right && top <= pt.y && pt.y <= bottom)
			bRet = true;
		
		return bRet;
	}

	public boolean hitTestWithRect(RectF rect)
	{
		boolean bRet = false;
		
		float left = m_Center.x - m_fWidth*0.5f;
		float right = m_Center.x + m_fWidth*0.5f;
		float top = m_Center.y - m_fHeight*0.5f;
		float bottom = m_Center.y + m_fHeight*0.5f;
		RectF bound = new RectF(left, top, right, bottom);
		
		if(bound.intersect(rect) == true)
			bRet = true;
		
		return bRet;
	}
	
}
