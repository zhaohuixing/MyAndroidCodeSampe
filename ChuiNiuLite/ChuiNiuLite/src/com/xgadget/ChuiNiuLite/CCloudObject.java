/**
 * 
 */
package com.xgadget.ChuiNiuLite;

import java.util.Random;

import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * @author zhaohuixing
 *
 */
public class CCloudObject implements IGunTarget //IBullet, IGunTarget 
{
	public static final int GAME_ALIEN_TYPE_CLOUD = 0;
	public static final int GAME_ALIEN_TYPE_BIRD = 1;
	public static final int GAME_ALIEN_TYPE_BIRD_BUBBLE = 2;
	
	public static final int GAME_ALIEN_BIRD_MOTION = 0;
	public static final int GAME_ALIEN_BIRD_SHOOT = 1;
	
	public static final float BIRD_BUBBLE_SIZE_COUNT_THRESHOLD = 5.0f;
	
	public static final int GAME_ALIEN_STOP = 0;
	public static final int GAME_ALIEN_MOTION = 1;
	public static final int GAME_ALIEN_BLAST = 2;
	public static final int GAME_DEFAULT_PLAYER_BULLET_BLAST_STEP = 3;
	
	public CGameScene		m_Controller;
	public int              m_nTimerElaspe;
	public int				m_nTimerStep;
	public int				m_nBlastAnimationStep;
	public int				m_nBlastCount;
	public Drawable			m_Image;
	public float			m_NormalTypeWidth;
	public float			m_NormalTypeHeight;
	
	public Drawable			m_BlastAnimation;

	public float			m_fShakeY;
	public float			m_nShakingStep;
	public float			m_nShakingCount;

	private	int 			m_enState;
	
	private RectF 			m_BoundInScene;
	private PointF 			m_CenterInScene;
	private Rect 			m_BoundInDevice;
	private Rect 			m_ThreeFourthBoundInDevice;
	private PointF 			m_CenterInDevice;
	private PointF			m_Speed;
	
	private	int 			m_enAlienType;
	
	private	int 			m_enBirdState;

	private int             m_nBirdBubbleSizeChangeCount;
	
    private int            m_nBirdAnimation;
	
	public CCloudObject(int index)
	{
		m_BoundInScene = new RectF();
		m_CenterInScene = new PointF();
		m_BoundInDevice = new Rect();
		m_ThreeFourthBoundInDevice = new Rect();
		m_CenterInDevice = new PointF();
		m_Speed = new PointF();
		
		m_enAlienType = GAME_ALIEN_TYPE_CLOUD;
        m_enBirdState = GAME_ALIEN_BIRD_MOTION;
        m_nBirdBubbleSizeChangeCount = 1;
		
        m_nBirdAnimation = 0;

        
		m_nTimerElaspe = CConfiguration.getAlienTimerElapse();
		m_nTimerStep = 0;
		m_nBlastAnimationStep = 0;
		m_nBlastCount = GAME_DEFAULT_PLAYER_BULLET_BLAST_STEP;
		Random rand = new Random(index%298299);
		int nRand = rand.nextInt();
		if(nRand < 0)
			nRand *= -1;

		float imgw, imgh;
		
		if(nRand%2 == 0)
		{
			if(CConfiguration.getThunderTheme() == false)
			{	
				m_Image = CImageLoader.GetCloudImage1();
				imgw = CImageLoader.GetCloudImage1Width(); 
				imgh = CImageLoader.GetCloudImage1Height();
			}
			else
			{
				m_Image = CImageLoader.GetRainCloudImage1();
				imgw = CImageLoader.GetRainCloudImage1Width(); 
				imgh = CImageLoader.GetRainCloudImage1Height();
			}
		}
		else 
		{
			if(CConfiguration.getThunderTheme() == false)
			{	
				m_Image = CImageLoader.GetCloudImage2();
				imgw = CImageLoader.GetCloudImage2Width(); 
				imgh = CImageLoader.GetCloudImage2Height();
			}	
			else
			{
				m_Image = CImageLoader.GetRainCloudImage2();
				imgw = CImageLoader.GetRainCloudImage2Width(); 
				imgh = CImageLoader.GetRainCloudImage2Height();
			}
		}
		float imgratio = imgw/imgh;
		
		float fWidth = CConfiguration.getRandomCloudWidth(nRand%6);
		rand = new Random(nRand%298299);
		nRand = rand.nextInt();
		if(nRand < 0)
			nRand *= -1;
		float fHeight = CConfiguration.getRandomCloudHeight(nRand%5);
		if(fWidth < fHeight)
		{
			fWidth = fHeight*imgratio;
		}
		else
		{
			fHeight = fWidth/imgratio;
		}
		
		m_NormalTypeWidth = fWidth;
		m_NormalTypeHeight = fHeight;
		
		m_CenterInScene.x = -1000.0f;
		m_CenterInScene.y = -1000.0f;
		m_BoundInScene.left = m_CenterInScene.x-fWidth*0.5f;
		m_BoundInScene.top = m_CenterInScene.y-fHeight*0.5f;
		m_BoundInScene.right = m_CenterInScene.x+fWidth*0.5f;
		m_BoundInScene.bottom = m_CenterInScene.y+fHeight*0.5f;
		
		m_BlastAnimation = CImageLoader.GetBlastImage();

		m_fShakeY = (float)(nRand%10);
		if(nRand%2 == 1)
			m_fShakeY *= -2.0f;
		
		m_nShakingStep = 0;
		m_nShakingCount = nRand%20;
	}
	
	public int getAlienType()
	{
	    return m_enAlienType;
	}

	public void UpdateResourceForThunderThemeChange()
	{
		Random rand = new Random();
		int nRand = rand.nextInt();
		if(nRand < 0)
			nRand *= -1;

		float imgw, imgh;
		
		if(nRand%2 == 0)
		{
			if(CConfiguration.getThunderTheme() == false)
			{	
				m_Image = CImageLoader.GetCloudImage1();
				imgw = CImageLoader.GetCloudImage1Width(); 
				imgh = CImageLoader.GetCloudImage1Height();
			}
			else
			{
				m_Image = CImageLoader.GetRainCloudImage1();
				imgw = CImageLoader.GetRainCloudImage1Width(); 
				imgh = CImageLoader.GetRainCloudImage1Height();
			}
		}
		else 
		{
			if(CConfiguration.getThunderTheme() == false)
			{	
				m_Image = CImageLoader.GetCloudImage2();
				imgw = CImageLoader.GetCloudImage2Width(); 
				imgh = CImageLoader.GetCloudImage2Height();
			}	
			else
			{
				m_Image = CImageLoader.GetRainCloudImage2();
				imgw = CImageLoader.GetRainCloudImage2Width(); 
				imgh = CImageLoader.GetRainCloudImage2Height();
			}
		}
		float imgratio = imgw/imgh;
		
		float fWidth = CConfiguration.getRandomCloudWidth(nRand%6);
		rand = new Random(nRand%298299);
		nRand = rand.nextInt();
		if(nRand < 0)
			nRand *= -1;
		float fHeight = CConfiguration.getRandomCloudHeight(nRand%5);
		if(fWidth < fHeight)
		{
			fWidth = fHeight*imgratio;
		}
		else
		{
			fHeight = fWidth/imgratio;
		}
		
		m_NormalTypeWidth = fWidth;
		m_NormalTypeHeight = fHeight;
		
		if(m_enAlienType == GAME_ALIEN_TYPE_CLOUD)
		{	
			m_BoundInScene.left = m_CenterInScene.x-fWidth*0.5f;
			m_BoundInScene.top = m_CenterInScene.y-fHeight*0.5f;
			m_BoundInScene.right = m_CenterInScene.x+fWidth*0.5f;
			m_BoundInScene.bottom = m_CenterInScene.y+fHeight*0.5f;
		}	
	}
	
	public void updateBirdBubbleSize()
	{
	    if(m_enAlienType == GAME_ALIEN_TYPE_BIRD_BUBBLE && m_nBirdBubbleSizeChangeCount < (int)BIRD_BUBBLE_SIZE_COUNT_THRESHOLD)
	    {
	        ++m_nBirdBubbleSizeChangeCount;
	        float ratio = ((float)m_nBirdBubbleSizeChangeCount)/BIRD_BUBBLE_SIZE_COUNT_THRESHOLD;
			float fWidth = CGameLayout.GetBirdBubbleWidth()*ratio;
			float fHeight = CGameLayout.GetBirdBubbleHeight()*ratio;
			m_BoundInScene.left = m_CenterInScene.x-fWidth*0.5f;
			m_BoundInScene.top = m_CenterInScene.y-fHeight*0.5f;
			m_BoundInScene.right = m_CenterInScene.x+fWidth*0.5f;
			m_BoundInScene.bottom = m_CenterInScene.y+fHeight*0.5f;
	    }
	}
	
	public void setAlienType(int enType)
	{
	    m_enAlienType = enType;
	    if(m_enAlienType == GAME_ALIEN_TYPE_BIRD)
	    {
			float fWidth = CGameLayout.GetBirdWidth();
			float fHeight = CGameLayout.GetBirdHeight();
			m_BoundInScene.left = m_CenterInScene.x-fWidth*0.5f;
			m_BoundInScene.top = m_CenterInScene.y-fHeight*0.5f;
			m_BoundInScene.right = m_CenterInScene.x+fWidth*0.5f;
			m_BoundInScene.bottom = m_CenterInScene.y+fHeight*0.5f;
	    }
	    else if(m_enAlienType == GAME_ALIEN_TYPE_BIRD_BUBBLE)
	    {
	        float ratio = ((float)m_nBirdBubbleSizeChangeCount)/BIRD_BUBBLE_SIZE_COUNT_THRESHOLD;
			float fWidth = CGameLayout.GetBirdBubbleWidth()*ratio;
			float fHeight = CGameLayout.GetBirdBubbleHeight()*ratio;
			m_BoundInScene.left = m_CenterInScene.x-fWidth*0.5f;
			m_BoundInScene.top = m_CenterInScene.y-fHeight*0.5f;
			m_BoundInScene.right = m_CenterInScene.x+fWidth*0.5f;
			m_BoundInScene.bottom = m_CenterInScene.y+fHeight*0.5f;
	    }
	}
	
	public void setBirdState(int enBirdState)
	{
	    m_enBirdState = enBirdState;
	}

	public int getBirdState()
	{
	    return m_enBirdState;
	}
	
	public boolean HitTestWithPoint(PointF pt) 
	{
		// TODO Auto-generated method stub
		boolean bRet = m_BoundInScene.contains(pt.x, pt.y); 
		return bRet;
	}

	public boolean HitTestWithPoint(Point pt) 
	{
		// TODO Auto-generated method stub
		boolean bRet = m_BoundInScene.contains((float)pt.x, (float)pt.y); 
		return bRet;
	}

	public boolean HitTestWithPoint(float x, float y) 
	{
		// TODO Auto-generated method stub
		boolean bRet = m_BoundInScene.contains(x, y); 
		return bRet;
	}

	public boolean HitTestWithPoint(int x, int y) 
	{
		// TODO Auto-generated method stub
		boolean bRet = m_BoundInScene.contains((float)x, (float)y); 
		return bRet;
	}

	public boolean HitTestWithRect(RectF rect) 
	{
		// TODO Auto-generated method stub
		boolean bRet = m_BoundInScene.contains(rect); 
		return bRet;
	}

	public boolean HitTestWithRect(float left, float top, float right, float bottom) 
	{
		// TODO Auto-generated method stub
		boolean bRet = m_BoundInScene.contains(left, top, right, bottom); 
		return bRet;
	}

	public void Reset() 
	{
		// TODO Auto-generated method stub
		m_enState = GAME_ALIEN_STOP;
		m_nTimerStep = 0;
		m_nTimerElaspe = CConfiguration.getAlienTimerElapse();
		m_nBlastAnimationStep = 0;
		m_nShakingStep = 0;
		//m_fShakeY = Math.abs(m_fShakeY);
		
	    m_nBirdBubbleSizeChangeCount = 1;
	    m_enBirdState = GAME_ALIEN_BIRD_MOTION;
		
		m_enAlienType = GAME_ALIEN_TYPE_CLOUD;
		m_CenterInScene.x = -1000.0f;
		m_CenterInScene.y = -1000.0f;
		m_BoundInScene.left = m_CenterInScene.x-m_NormalTypeWidth*0.5f;
		m_BoundInScene.top = m_CenterInScene.y-m_NormalTypeHeight*0.5f;
		m_BoundInScene.right = m_CenterInScene.x+m_NormalTypeWidth*0.5f;
		m_BoundInScene.bottom = m_CenterInScene.y+m_NormalTypeHeight*0.5f;
	    m_nBirdAnimation = 0;
	    
	}

	/* (non-Javadoc)
	 * @see com.xgadget.ChuiNiuLite.IGunTarget#HitByBullet(com.xgadget.ChuiNiuLite.IBullet)
	 */
	//@Override
	public boolean HitByBullet(IBullet bullet) 
	{
		// TODO Auto-generated method stub
		boolean bRet = false;
		
		if(bullet != null)
		{
			bRet = bullet.HitTestWithRect(m_BoundInScene);
			if(bRet)
			{
				blast();
			}
		}
		
		return bRet;
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
		m_ThreeFourthBoundInDevice.left = (int)(m_CenterInDevice.x - fWidthInDevice*0.8f);
		m_ThreeFourthBoundInDevice.right = (int)(m_CenterInDevice.x + fWidthInDevice*0.8f);
		m_ThreeFourthBoundInDevice.top = (int)(m_CenterInDevice.y - fHeightInDevice*0.8f);
		m_ThreeFourthBoundInDevice.bottom = (int)(m_CenterInDevice.y + fHeightInDevice*0.8f);
	}
	
	private void DrawMotion(Canvas canvas)
	{
	    if(this.m_enAlienType == GAME_ALIEN_TYPE_CLOUD)
	    {
	    	m_Image.setBounds(m_BoundInDevice);
	    	m_Image.draw(canvas);
	    }
	    else if(this.m_enAlienType == GAME_ALIEN_TYPE_BIRD)
	    {
            int enState = getBirdState();
            if(enState == GAME_ALIEN_BIRD_MOTION)
            {
            	Drawable image = null;
                if(m_nBirdAnimation == 0)
                	image = CImageLoader.GetBirdImage1();
                else if(m_nBirdAnimation == 1)
                	image = CImageLoader.GetBirdImage2();
                else if(m_nBirdAnimation == 2)
                	image = CImageLoader.GetBirdShootImage1();
                else
                	image = CImageLoader.GetBirdShootImage2();
                
                if(image != null)
                {
                	image.setBounds(m_BoundInDevice);
                	image.draw(canvas);
                }
                
            }
	    }
	    else if(this.m_enAlienType == GAME_ALIEN_TYPE_BIRD_BUBBLE)
	    {
        	Drawable image = CImageLoader.GetBirdBubbleImage();
            if(image != null)
            {
            	image.setBounds(m_BoundInDevice);
            	image.draw(canvas);
            }
	    }
	    
	}

	private void DrawBlast(Canvas canvas)
	{
		if(m_nBlastCount <= m_nBlastAnimationStep)
			return;
		
		float fAngle = ((float)(m_nBlastAnimationStep+1))/((float)m_nBlastCount);
		float w1 = m_BoundInDevice.width()*(1-fAngle);
		float h1 = m_BoundInDevice.height()*(1-fAngle);
		float w2 = m_BoundInDevice.width()*fAngle;
		float h2 = m_BoundInDevice.width()*fAngle;
		
		if(0 < w1 && 0 < h1)
		{	
			Rect rt1 = new Rect((int)(m_CenterInDevice.x-w1*0.5), (int)(m_CenterInDevice.y-h1*0.5), (int)(m_CenterInDevice.x+w1*0.5), (int)(m_CenterInDevice.y+h1*0.5)); 
		    if(this.m_enAlienType == GAME_ALIEN_TYPE_CLOUD)
		    {
				m_Image.setBounds(rt1);
				m_Image.draw(canvas);
		    }
		    else if(this.m_enAlienType == GAME_ALIEN_TYPE_BIRD)
		    {
	            int enState = getBirdState();
	            if(enState == GAME_ALIEN_BIRD_MOTION)
	            {
	            	Drawable image = null;
	                if(m_nBirdAnimation == 0)
	                	image = CImageLoader.GetBirdImage1();
	                else if(m_nBirdAnimation == 1)
	                	image = CImageLoader.GetBirdImage2();
	                else if(m_nBirdAnimation == 2)
	                	image = CImageLoader.GetBirdShootImage1();
	                else
	                	image = CImageLoader.GetBirdShootImage2();
	                
	                if(image != null)
	                {
	                	image.setBounds(rt1);
	                	image.draw(canvas);
	                }
	                
	            }
		    }
		    else if(this.m_enAlienType == GAME_ALIEN_TYPE_BIRD_BUBBLE)
		    {
	        	Drawable image = CImageLoader.GetBirdBubbleImage();
	            if(image != null)
	            {
	            	image.setBounds(rt1);
	            	image.draw(canvas);
	            }
		    }
			
		}
		Rect rt2 = new Rect((int)(m_CenterInDevice.x-w2*0.5), (int)(m_CenterInDevice.y-h2*0.5), (int)(m_CenterInDevice.x+w2*0.5), (int)(m_CenterInDevice.y+h2*0.5)); 
		m_BlastAnimation.setBounds(rt2);
		m_BlastAnimation.draw(canvas);
	}
	
	public void Draw(Canvas canvas)
	{
		if(isMotion() == true)
		{
			DrawMotion(canvas);
		}	
		else if(isBlast() == true)
		{
			DrawBlast(canvas);
		}
		
		//Paint paint = new Paint();
		//paint.setColor(Color.RED);
		//canvas.drawRect(m_BoundInDevice, paint);
	}
	
	private void updateTimerEvent()
	{
		if(isMotion() == true)
		{
			PointF pt = getPosition();
			PointF v = getSpeed();
			pt.x += v.x;
			pt.y += v.y;
			
			if(CConfiguration.canAlienShaking() == true)
			{
				pt.y += m_fShakeY;
				if(CGameLayout.GetGameSceneHeight() <= pt.y)
				{
					pt.y = CGameLayout.GetGameSceneHeight()-1.0f; 
				}	
				++m_nShakingStep;
				if(this.m_enAlienType == CCloudObject.GAME_ALIEN_TYPE_CLOUD)
				{	
					if(m_nShakingCount <= m_nShakingStep)
					{
						m_nShakingStep = 0;
						m_fShakeY *= -1.0;
					}
				}
				else if(this.m_enAlienType == CCloudObject.GAME_ALIEN_TYPE_BIRD)
				{
					if(m_nShakingCount*2 <= m_nShakingStep)
					{
						m_nShakingStep = 0;
						m_fShakeY *= -1.0;
					}
				}
			}	
		    m_nBirdAnimation = (m_nBirdAnimation+1)%4;
			this.updateBirdBubbleSize();
			moveTo(pt);
			if(outOfSceneBound() == true)
			{
				Reset();
			}	
		}
		else if(isBlast() == true)
		{
			++m_nBlastAnimationStep;
			if(m_nBlastCount <= m_nBlastAnimationStep)
			{
				Reset();
			}
		}	
	}
	
	public boolean OnTimerEvent()
	{
		boolean bRet = false; //?????????
		if(isBlast() == true || isMotion() == true)
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

	public void blast()
	{
		m_enState = GAME_ALIEN_BLAST;
		++m_nBlastAnimationStep;
		if(m_Controller != null)
			m_Controller.PlaySound(CGameAudio.GAME_SOUND_ID_BLAST);
		
	}

	public void startMotion()
	{
		m_enState = GAME_ALIEN_MOTION;
	    m_enBirdState = GAME_ALIEN_BIRD_MOTION;
	    m_nBirdBubbleSizeChangeCount = 1;
	}

	public boolean isBlast()
	{
		boolean bRet = false;
		
		if(m_enState == GAME_ALIEN_BLAST)
			bRet = true;
		
		return bRet;
	}

	public boolean isMotion()
	{
		boolean bRet = false;
		
		if(m_enState == GAME_ALIEN_MOTION)
			bRet = true;
		
		return bRet;
	}	

	public boolean isStop()
	{
		boolean bRet = false;
		
		if(m_enState == GAME_ALIEN_STOP)
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
	
	public Rect getBoundIndevice()					//Base on game view coordinate system
	{
		return m_BoundInDevice;
	}

	public Rect getThreeFourthBoundIndevice()					//Base on game view coordinate system
	{
		return m_ThreeFourthBoundInDevice;
	}
	
	public PointF getPositionInView()				//Base on game view coordinate system
	{
		return m_CenterInDevice;
	}
	
	public void setTimerElaspe(int nInterval)
	{
		m_nTimerElaspe = nInterval;
	}

	
}
