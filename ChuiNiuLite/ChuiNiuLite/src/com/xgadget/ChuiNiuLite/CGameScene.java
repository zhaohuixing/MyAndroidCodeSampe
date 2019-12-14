package com.xgadget.ChuiNiuLite;

import java.util.Calendar;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class CGameScene implements IGameController 
{
	public static final int GAME_PLAY_READY = 0;
	public static final int GAME_PLAY_PLAY = 1;
	public static final int GAME_PLAY_PAUSE = 2;
	public static final int GAME_PLAY_RESULT_WIN =3;
	public static final int GAME_PLAY_RESULT_LOSE = 4;
	public static final int GAME_DEFAULT_PLAYER_BULLET_NUMBER = 15; 
	
	public static final int THUNDER_ENLARGE = 30; //60;
	public static final int THUNDER_THRESHOLD = 10;//20;
	public static final int THUNDER_STEP_THRESHOLD = 13;
	public static final int THUNDER_STEP_ENLARGE = 4;
	
	public static int m_enGamePlayState = GAME_PLAY_READY; 
	
	private CCowObject				m_FlyingCow;
	private CDogObject				m_Dog;
	private CSkyObject				m_Sky;
	private CGroundObject			m_Ground;
	private CGunObject				m_DogGun;
	private CGunObject				m_CowGun;
	private CClockObject			m_Clock;
	private CGameAudio				m_GameAudio;
	private int 					m_nGameTimeCounter;
	private int 					m_nGameTimeLength;
	private boolean 				m_bDirty;
	
	
	private int				m_nThunderThreshod;
	private int				m_nThunderStep;
	private int             m_nThunderLength;
	private int             m_nThunderType;     //0: 1 vertical; 1:1 horizontal; 2: 1 verical, 1 horz; 3 2 vertical; 4 2 horz
	
	private CGameController 		m_Controller;
	
	private boolean                m_bHandlePostWinGameOptions;
	private boolean                m_bHandlePostLostGameAction;
	
	public CGameScene(Context context)
	{
		CGameScene.ResetGameState();

		m_Controller = null;
		
		m_nGameTimeCounter = 0;
		m_nGameTimeLength = CConfiguration.getGameTime();
		m_bDirty = false;
		m_FlyingCow = new CCowObject(this);
		m_Dog = new CDogObject(this);				
		m_Sky = new CSkyObject(this);
		m_Ground = new CGroundObject(this);
		m_DogGun = new CGunObject(this);
		m_CowGun = new CGunObject(this);
		m_Clock = new CClockObject();
		m_GameAudio = new CGameAudio(context);
		ConfigureDogGun();
		ConfigureCowGun();
		ConfigureClouds();
		ConfigureBlockage();
		
		InitializeScore();

		Random rand = new Random();
		int nRand = rand.nextInt();
		if(nRand < 0)
			nRand *= -1;
		
        int n = nRand;
        m_nThunderThreshod = THUNDER_ENLARGE*(1+n%THUNDER_THRESHOLD);
        m_nThunderLength = THUNDER_STEP_ENLARGE*(1+n%THUNDER_STEP_THRESHOLD);
            
        m_nThunderType = 0;     //0: 1 vertical; 1:1 horizontal; 2: 1 verical, 1 horz; 3 2 vertical; 4 2 horz
        m_nThunderStep = -1;
		
        m_bHandlePostWinGameOptions = false;
        m_bHandlePostLostGameAction = false;
        
	}
	
	public void SetController(CGameController 	controller)
	{
		m_Controller = controller;
	}
	
	public void ConfigureDogGun()
	{
		float w = CGameLayout.GetDogBulletMeasureWidth();
		float h = CGameLayout.GetDogBulletMeasureHeight();
		float xper = CGameLayout.GetDogBulletStartRatioX();
		float yper = CGameLayout.GetDogBulletStartRatioY();
		float cxper = CGameLayout.GetDogBulletChangeRatioX();
		float cyper = CGameLayout.GetDogBulletChangeRatioY();
	
		for(int i = 0; i < GAME_DEFAULT_PLAYER_BULLET_NUMBER;++i)
		{
			CBulletObject bullet = new CBulletObject(m_DogGun, w, h); 
			bullet.m_Image = CImageLoader.GetDogBulletImage();
			bullet.m_fStartRatioX = xper;
			bullet.m_fStartRatioY = yper;
			bullet.m_fChangeRatioX = cxper;
			bullet.m_fChangeRatioY = cyper;
			m_DogGun.addBullet(bullet);
		}
		m_Dog.m_Gun = m_DogGun;
		m_DogGun.m_Shooter = m_Dog;
		m_DogGun.m_Target = m_FlyingCow;
		m_DogGun.m_Aliens = m_Sky;
		m_DogGun.m_EmenyGun = m_CowGun;
	}

	public void ConfigureCowGun()
	{
		float w = CGameLayout.GetCowBulletMeasureWidth();
		float h = CGameLayout.GetCowBulletMeasureHeight();
		float xper = CGameLayout.GetCowBulletStartRatioX();
		float yper = CGameLayout.GetCowBulletStartRatioY();
		float cxper = CGameLayout.GetCowBulletChangeRatioX();
		float cyper = CGameLayout.GetCowBulletChangeRatioY();
	
		for(int i = 0; i < GAME_DEFAULT_PLAYER_BULLET_NUMBER;++i)
		{
			CBulletObject bullet = new CBulletObject(m_CowGun, w, h); 
			bullet.m_Image = CImageLoader.GetCowBulletImage();
			bullet.m_fStartRatioX = xper;
			bullet.m_fStartRatioY = yper;
			bullet.m_fChangeRatioX = cxper;
			bullet.m_fChangeRatioY = cyper;
			m_CowGun.addBullet(bullet);
		}	
		m_FlyingCow.m_Gun = m_CowGun;
		m_CowGun.m_Shooter = m_FlyingCow;
		m_CowGun.m_Target = m_Dog;
		m_CowGun.m_EmenyGun = m_DogGun;
	}

	public void ConfigureClouds()
	{
		for(int i = 0; i < CConfiguration.GAME_DEFAULT_ALIEN_NUMBER;++i)
		{
			//AlienObject* alien = [[AlienObject alloc] init:i];
			//alien.m_EventDispatcher = self;
			//[m_Aliens addAlien:alien];
			CCloudObject cloud = new CCloudObject(i);
			m_Sky.addAlien(cloud);
			m_Sky.m_Target = m_FlyingCow;
		}	
	}

	public void ConfigureBlockage()
	{
		for(int i = 0; i < CConfiguration.GAME_DEFAULT_BLOCK_NUMBER;++i)
		{
			CBlockageObject block1 = new CBlockageObject();
			//block1.m_EventDispatcher = self;
			if(i%2 == 0)
			    block1.setImage(CImageLoader.GetRockImage1());
			else
			    block1.setImage(CImageLoader.GetRockImage2());
			m_Ground.addBlocks(block1, CConfiguration.GAME_SKILL_LEVEL_ONE);
			
			CBlockageObject block2 = new CBlockageObject();
			//block2.m_EventDispatcher = self;
			int n = i%3;
			if(n == 0)
			{	
			    block2.setImage(CImageLoader.GetRockImage1());
			}
			else if(n == 1)
			{	
			    block2.setImage(CImageLoader.GetSnailImage());
				block2.setShaking(true);
			}
			else
			{	
			    block2.setImage(CImageLoader.GetRockImage2());
			}
			m_Ground.addBlocks(block2, CConfiguration.GAME_SKILL_LEVEL_TWO);

			CBlockageObject block3 = new CBlockageObject();
			//block3.m_EventDispatcher = self;
		    block3.setImage(CImageLoader.GetSnailImage());
			block3.setShaking(true);
			m_Ground.addBlocks(block3, CConfiguration.GAME_SKILL_LEVEL_THREE);
		}	
		m_Ground.m_BlockTarget = m_Dog;
	}

	public void UpdateLayout()
	{
		m_FlyingCow.UpdateLayout();
		m_Dog.UpdateLayout();				
		m_Sky.UpdateLayout();
		m_Ground.UpdateLayout();
		m_DogGun.UpdateLayout();
		m_CowGun.UpdateLayout();
		m_Clock.UpdateLayout();
	}

	public void Draw(Canvas canvas)
	{
		if(CGameScene.IsGameStateResult() == false)
		{	
			m_FlyingCow.Draw(canvas);
			m_Dog.Draw(canvas);				
			m_Sky.Draw(canvas);
			m_Ground.Draw(canvas);
			m_DogGun.Draw(canvas);
			m_CowGun.Draw(canvas);
			m_Clock.Draw(canvas);
		}
		else
		{
			DrawResult(canvas);
		}
		if(m_Ground != null)
			m_Ground.DrawGrass(canvas);

	    if(CConfiguration.getThunderTheme() == true && 0 <= m_nThunderStep)
	        this.drawThunderStorm(canvas);
		
	}
	
	private void drawThunderBrighter(Canvas canvas)
	{
	    canvas.save();
	    Paint paint = new Paint();
	    
	    if(m_nThunderStep%6 == 0)
	    	paint.setARGB(102, 153, 153, 153);
	    else
	    	paint.setARGB(255, 255, 255, 255);
	    
	    Rect rect = CGameLayout.GetGameSceneDeviceRect();
	    canvas.drawRect(rect, paint);
	    
	    canvas.restore();
	}

	private void drawThunderDarker(Canvas canvas)
	{
	    canvas.save();
	    Paint paint = new Paint();
	    
	    if(m_nThunderStep%6 == 0)
	    	paint.setARGB(179, 77, 77, 7);
	    else
	    	paint.setARGB(255, 0, 0, 0);
	    
	    Rect rect = CGameLayout.GetGameSceneDeviceRect();
	    canvas.drawRect(rect, paint);
	    canvas.restore();
	}

	private void drawLighting(Canvas canvas)
	{
		Drawable lightImage1 = CImageLoader.GetLightImage1();
		Drawable lightImage2 = CImageLoader.GetLightImage2();
	    Rect rect = CGameLayout.GetGameSceneDeviceRect();
		
		
	    if(m_nThunderType == 0)
	    {
	        int cx = rect.left + rect.width()*2/10;
	        int cy = rect.top + rect.height()/10;
	        int w = rect.height()*6/10;
	        int h = w/3;
	        Rect rt = new Rect(cx, cy, cx+w,  cy+h);
	        lightImage1.setBounds(rt);
	        lightImage1.draw(canvas);
	    }
	    else if(m_nThunderType == 1)
	    {
	    	int cx = rect.left + rect.width()/4;
	    	int cy = rect.top + rect.height()/4;
	        int h = rect.height()*3/4;
	        int w = h/2;
	        Rect rt = new Rect(cx, cy, cx+w,  cy+h);
	        lightImage2.setBounds(rt);
	        lightImage2.draw(canvas);
	    }
	    else if(m_nThunderType == 2)
	    {
	    	int cx = rect.left + rect.width()/10;
	    	int cy = rect.top + rect.height()/20;
	        int h = rect.height()*3/4;
	        int w = h/2;
	        Rect rt = new Rect(cx, cy, cx+w,  cy+h);
	        lightImage2.setBounds(rt);
	        lightImage2.draw(canvas);
	        
	        cx = rect.left + rect.width()*6/10;
	        cy = rect.top + rect.height()/10;
	        w = rect.height()*6/10;
	        h = w/3;
	        rt = new Rect(cx, cy, cx+w,  cy+h);
	        lightImage1.setBounds(rt);
	        lightImage1.draw(canvas);
	    }
	    else if(m_nThunderType == 3)
	    {
	    	int cx = rect.left + rect.width()*15/100;
	    	int cy = rect.top + rect.height()/20;
	        int h = rect.height()*6/10;
	        int w = h/2;
	        Rect rt = new Rect(cx, cy, cx+w,  cy+h);
	        lightImage2.setBounds(rt);
	        lightImage2.draw(canvas);
	        
	        h = rect.height()*8/10;
	        w = h/4;
	        cx = rect.left + rect.width()*65/100;
	        cy = rect.top + rect.height()*2/10;
	        rt = new Rect(cx, cy, cx+w,  cy+h);
	        lightImage1.setBounds(rt);
	        lightImage1.draw(canvas);
	    }
	    else
	    {
	    	int cx = rect.left + rect.width()*15/100;
	    	int cy = rect.top + rect.height()/10;
	        int h = rect.height()*6/10;
	        int w = h/3;
	        Rect rt = new Rect(cx, cy, cx+w,  cy+h);
	        lightImage1.setBounds(rt);
	        lightImage1.draw(canvas);
	        
	        
	        cx = rect.left + rect.width()*3/10;
	        cy = rect.top + rect.height()*65/100;
	        w = rect.height()*7/10;
	        h = w/4;
	        rt = new Rect(cx, cy, cx+w,  cy+h);
	        lightImage1.setBounds(rt);
	        lightImage1.draw(canvas);
	    }
	        
	}

	private void drawThunderLighting(Canvas canvas)
	{
	    canvas.save();
	    Paint paint = new Paint();
	    
	    if(m_nThunderStep%5 == 0)
	    {    
	    	paint.setARGB(128, 0, 0, 0);
	    }    
	    else
	    {    
	    	paint.setARGB(204, 77, 77, 77);
	    }    
        drawLighting(canvas);
	    Rect rect = CGameLayout.GetGameSceneDeviceRect();
	    canvas.drawRect(rect, paint);
	    canvas.restore();
	}

	private void drawThunderStorm(Canvas canvas)
	{
	    if(m_nThunderStep < m_nThunderLength/4)
	    {  
	        drawThunderBrighter(canvas);
	    }
	    else if(m_nThunderLength*3/4 <= m_nThunderStep && m_nThunderStep <= m_nThunderLength)
	    {
	        drawThunderDarker(canvas);
	    }
	    else
	    {
	        drawThunderLighting(canvas);
	    }
	}
	
	public void DrawResult(Canvas canvas)
	{
		if(CGameScene.IsGameStateResultWin() == true)
		{	
			DrawResultWin(canvas);
		}
		else if(CGameScene.IsGameStateResultLose() == true)
		{	
			DrawResultLose(canvas);
		}
	}

	public void DrawResultWin(Canvas canvas)
	{
		m_Clock.Draw(canvas);
		m_Sky.DrawWin(canvas);
		m_Dog.DrawWin(canvas);				
		m_FlyingCow.DrawWin(canvas);
	}
	
	public void DrawResultLose(Canvas canvas)
	{
		m_Clock.Draw(canvas);
		m_Dog.DrawLose(canvas);				
		m_FlyingCow.DrawLose(canvas);
	}
	
	//@Override
	public boolean OnTimerEvent()
	{
		boolean bRet = false;
		
		if(CGameScene.IsGameStateResult() == true )
		{
			return true;
		}	
		
		if(CGameScene.IsGameStatePlay() == false)
			return bRet;
		m_nGameTimeCounter += 1;
		if(m_nGameTimeLength <= m_nGameTimeCounter)
		{
			GameWin();
			return true;
		}	
		
		
		
		boolean bCheck = false;
		
		bCheck = m_FlyingCow.OnTimerEvent();
		if(bCheck == true)
			bRet = true;
		
		bCheck = m_Dog.OnTimerEvent();
		if(bCheck == true)
			bRet = true;

		bCheck = m_DogGun.OnTimerEvent();
		if(bCheck == true)
			bRet = true;

		bCheck = m_CowGun.OnTimerEvent();
		if(bCheck == true)
			bRet = true;
		
		bCheck = m_Sky.OnTimerEvent();
		if(bCheck == true)
			bRet = true;

		bCheck = m_Ground.OnTimerEvent();
		if(bCheck == true)
			bRet = true;

		bCheck = m_Clock.OnTimerEvent();
		if(bCheck == true)
			bRet = true;
		
		//if(CConfiguration.IsSoundEnable() == true)
		//	m_GameAudio.PauseBackgroundSound();
	    if(CConfiguration.getThunderTheme() == true /*&& isGamePlayPlaying() == 1*/)
	    {
	        if(m_nThunderStep < 0)
	        {
	            int nCheck = m_nGameTimeCounter%m_nThunderThreshod;
	            if(nCheck == m_nThunderThreshod-1)
	            {    
	                m_nThunderStep = 0;
	        		Random rand = new Random();
	        		int nRand = rand.nextInt();
	        		if(nRand < 0)
	        			nRand *= -1;
	        		
	                m_nThunderType = nRand%5;//m_nGameTimeCounter%5; 
	                this.PlaySound(CGameAudio.GAME_SOUND_ID_THUNDER);
	            }    
	        }
	        else
	        {
	            ++m_nThunderStep;
	            if(m_nThunderLength <= m_nThunderStep)
	            {
	                m_nThunderStep = -1;
	            }
	        }
	    }
		
		return bRet;
	}
	
	public boolean OnTouchEvent(MotionEvent event)
	{
		boolean bRet = false;
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) 
        {
            case MotionEvent.ACTION_DOWN:
            	bRet = OnTouchDown(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
            	bRet = OnTouchMove(x, y);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            	bRet = OnTouchUp(x, y);
                break;
        }
		
		return bRet;
	}
	
	private boolean OnTouchDown(float x, float y)
	{
		boolean bRet = false;

		if(CGameScene.IsGameStateReady() == true)
		{
			m_Dog.m_touchPoint.set(x, y);
			startNewGame();
			return true;
		}
		
		if(CGameScene.IsGameStateResult() == true)
		{
			CGameScene.m_enGamePlayState = CGameScene.GAME_PLAY_READY;
			this.resetGameScene();
			if(m_Controller != null)
				m_Controller.UpdateGameUI();
			return true;
		}

		if(CGameScene.IsGameStatePause() == true)
		{
			this.resumeGame();
			return true;
		}
		
		if(CGameScene.IsGameStatePlay() == false)
		{	
		//	NSLog(@"touchBegan: return in non-playing");
			return true;
		}	
		
		if(m_Dog != null)
		{	
			bRet = m_Dog.HitTestByDevicePoint(x, y);
			if(bRet == true)
			{
				m_Dog.setTouched(true);
				if(CConfiguration.IsFGEanbled() == false)
					m_Dog.startShoot();
				if(m_Dog.isJump() == false && CConfiguration.canPlayerJump() == true && m_Dog.isBlocked() == true)
				{	
					if(m_Dog.isInWaitTap2() == true)
					{
						m_Dog.startJump();
						return bRet;
					}	
					else if(m_Dog.isInTap1() == false)
					{
						m_Dog.enterTap1();
					}	
				}	
				if(m_Dog.isBlocked() == false)
					m_Dog.m_touchPoint.set(x, y);
			}	
			
			return bRet;
		}	
		return bRet;
	}
	
	private boolean OnTouchMove(float x, float y)
	{
		boolean bRet = false;
		if(CGameScene.IsGameStatePlay() == false)
		{	
		//	NSLog(@"touchBegan: return in non-playing");
			return true;
		}	
		
		if(m_Dog != null)
		{	
			bRet = m_Dog.HitTestByDevicePoint(x, y);
			if(bRet == true)
			{
				if(m_Dog.isJump() == false)
				{	
					m_Dog.setTouched(true);
					if(m_Dog.isBlocked() == false && m_Ground.blockageHitTestWithTarget() == false)
					{	
						float oldX = CGameLayout.DeviceToGameSceneX(m_Dog.m_touchPoint.x);
						float newX = CGameLayout.DeviceToGameSceneX(x);
						float dX = oldX - newX;
						float cX = m_Dog.GetCenterX();
						float cY = m_Dog.GetCenterY();
						cX += dX;
						m_Dog.moveTo(cX, cY);
						m_Dog.m_touchPoint.set(x, y);
					}
					else 
					{
						float newx = x - m_Dog.m_touchPoint.x;
						boolean bUpdate = m_Dog.moveBackFromBlockage(newx);
						if(bUpdate)
						{	
							m_Dog.m_touchPoint.set(x, y);
							//[self invalidRender];
						}	
					}
				}	
			}
			else if(m_Dog.isTouched() == true)
			{
				if(m_Dog.isJump() == false)
				{	
					if(m_Dog.isBlocked() == false && m_Ground.blockageHitTestWithTarget() == false)
					{	
						float oldX = CGameLayout.DeviceToGameSceneX(m_Dog.m_touchPoint.x);
						float newX = CGameLayout.DeviceToGameSceneX(x);
						float dX = oldX - newX;
						float cX = m_Dog.GetCenterX();
						float cY = m_Dog.GetCenterY();
						cX += dX;
						m_Dog.moveTo(cX, cY);
						m_Dog.m_touchPoint.set(x, y);
					}
					else 
					{
						float newx = x - m_Dog.m_touchPoint.x;
						boolean bUpdate = m_Dog.moveBackFromBlockage(newx);
						if(bUpdate)
						{	
							m_Dog.m_touchPoint.set(x, y);
							//[self invalidRender];
						}	
					}
				}	
			}	
		}	
		return bRet;
	}
	
	private boolean OnTouchUp(float x, float y)
	{
		boolean bRet = false;
		if(CGameScene.IsGameStatePlay() == false)
		{	
		//	NSLog(@"touchBegan: return in non-playing");
			return true;
		}
		
		if(m_Dog != null)
		{	
			bRet = m_Dog.HitTestByDevicePoint(x, y);
			if(bRet == true && m_Dog.hitHeadByDevicePoint(x, y) == false && m_Dog.isJump() == false)
			{
				if(m_Dog.isInTap1() == true)
				{
					m_Dog.enterTap2();
				}
				
				if(m_Dog.isBlocked() == false && m_Ground.blockageHitTestWithTarget() == false)
				{	
					float oldX = CGameLayout.DeviceToGameSceneX(m_Dog.m_touchPoint.x);
					float newX = CGameLayout.DeviceToGameSceneX(x);
					float dX = oldX - newX;
					float cX = m_Dog.GetCenterX();
					float cY = m_Dog.GetCenterY();
					cX += dX;
					m_Dog.moveTo(cX, cY);
					m_Dog.m_touchPoint.set(x, y);
				}	
			}
			m_Dog.setTouched(false);
		}
		
		return bRet;
	}
	
	public void OnFGEventX(boolean bForward)
	{
		if(m_Dog == null)
			return;
		
		m_Dog.OnFGEventX(bForward);
	}
	
	public void OnFGEventY()
	{
		if(m_Dog == null)
			return;
		
		m_Dog.OnFGEventY();
	}
	
	
	public boolean OnKeyDown(int keyCode)
	{
		boolean bRet = true;

		if(CGameScene.IsGameStateResult() == true)
		{
			startNewGame();
			return bRet;
		}
	
		if(CGameScene.IsGameStatePlay() == false)
			return bRet;
		
		if(m_Dog != null)
			bRet = m_Dog.OnKeyDown(keyCode);
		return bRet;
	}

	public boolean OnKeyUp(int keyCode)
	{
		boolean bRet = true;
		if(CGameScene.IsGameStatePlay() == false)
			return bRet;
		
		if(m_Dog != null)
			bRet = m_Dog.OnKeyUp(keyCode);
		return bRet;
	}

	public void ForceToStartGame()
	{
	    m_bHandlePostWinGameOptions = false;
	    m_bHandlePostLostGameAction = false;
	    startNewGame();
	}
	
	public void startNewGame()
	{
		if(m_bHandlePostWinGameOptions == true || m_bHandlePostLostGameAction == true)
		{
			if(m_Controller != null)
			{
				if(m_bHandlePostWinGameOptions == true)
				{
					m_Controller.HandlePostWinGameAction();
				}
				if(m_bHandlePostLostGameAction == true)
				{
					m_Controller.HandlePostLostGameAction();
				}
				return;
			}
		}
		
		resetGameScene();
		CGameScene.SetGameStatePlay();
		m_Sky.shoot();
		if(CConfiguration.canShootBlock() == true)
			m_Ground.shoot();
		if(m_GameAudio != null && CConfiguration.IsSoundEnable() == true)
			m_GameAudio.PlayBackgroundSound();
		
		if(m_Controller != null)
			m_Controller.HandleGameStartEvent();
	}

	public void pauseGame()
	{
		CGameScene.SetGameStatePause();
		if(m_GameAudio != null && CConfiguration.IsSoundEnable() == true)
			m_GameAudio.PauseAllPlayingSound();		
	}

	public void resumeGame()
	{
		CGameScene.SetGameStatePlay();
		if(m_GameAudio != null && CConfiguration.IsSoundEnable() == true)
			m_GameAudio.ResumeAllPlayingSound();		
		
		if(m_Controller != null)
			m_Controller.HandleGameStartEvent();

	}

	public void resetGameScene()
	{
		CGameScene.ResetGameState();
		m_bDirty = false;
		m_Dog.Reset();
		m_DogGun.reset();
		m_FlyingCow.Reset();
		m_CowGun.reset();
		m_Sky.Reset();
		m_Ground.reset();
		m_Clock.Reset();
		m_nGameTimeCounter = 0;
		m_nGameTimeLength = CConfiguration.getGameTime();
		
		Random rand = new Random();
		int nRand = rand.nextInt();
		if(nRand < 0)
			nRand *= -1;
		
        int n = nRand;
        m_nThunderThreshod = THUNDER_ENLARGE*(1+n%THUNDER_THRESHOLD);
        m_nThunderLength = THUNDER_STEP_ENLARGE*(1+n%THUNDER_STEP_THRESHOLD);
            
        m_nThunderType = 0;     //0: 1 vertical; 1:1 horizontal; 2: 1 verical, 1 horz; 3 2 vertical; 4 2 horz
        m_nThunderStep = -1;
	    m_bHandlePostWinGameOptions = false;
	    m_bHandlePostLostGameAction = false;
	}
	
	public static void ResetGameState()
	{
		m_enGamePlayState = GAME_PLAY_READY;
	}

	public static void SetGameStatePlay()
	{
		m_enGamePlayState = GAME_PLAY_PLAY;
	}

	public static void SetGameStatePause()
	{
		m_enGamePlayState = GAME_PLAY_PAUSE;
	}

	public static void SetGameStateResultWin()
	{
		m_enGamePlayState = GAME_PLAY_RESULT_WIN;
	}

	public static void SetGameStateResultLose()
	{
		m_enGamePlayState = GAME_PLAY_RESULT_LOSE;
	}

	public static boolean IsGameStateReady()
	{
		boolean bRet = false;
		if(m_enGamePlayState == GAME_PLAY_READY)
			bRet = true;
		return bRet;
	}

	public static boolean IsGameStatePlay()
	{
		boolean bRet = false;
		if(m_enGamePlayState == GAME_PLAY_PLAY)
			bRet = true;
		return bRet;
	}

	public static boolean IsGameStatePause()
	{
		boolean bRet = false;
		if(m_enGamePlayState == GAME_PLAY_PAUSE)
			bRet = true;
		return bRet;
	}

	public static boolean IsGameStateResultWin()
	{
		boolean bRet = false;
		if(m_enGamePlayState == GAME_PLAY_RESULT_WIN)
			bRet = true;
		return bRet;
	}

	public static boolean IsGameStateResultLose()
	{
		boolean bRet = false;
		if(m_enGamePlayState == GAME_PLAY_RESULT_LOSE)
			bRet = true;
		return bRet;
	}

	public static boolean IsGameStateResult()
	{
		boolean bRet = false;
		if(m_enGamePlayState == GAME_PLAY_RESULT_LOSE || m_enGamePlayState == GAME_PLAY_RESULT_WIN)
			bRet = true;
		return bRet;
	}

	public void endGame()
	{
		m_nGameTimeCounter = 0;
		CGameScene.ResetGameState();
		if(m_GameAudio != null && CConfiguration.IsSoundEnable() == true)
			m_GameAudio.StopAllPlayingSound();		
		
		if(m_Controller != null)
			m_Controller.HandleGameEndEvent();
		//??
//		CFCActivity.PlayFullScreenAD();
	}
	
	private void handleLoseResult()
	{
		CGameScene.SetGameStateResultLose();
		if(m_GameAudio != null && CConfiguration.IsSoundEnable() == true)
			m_GameAudio.PlayLose();		
		//UpdateTodayGamePlayCount();	
		int nSkill = CConfiguration.getGameSkill();
		int nLevel = CConfiguration.getGameLevel();
	    int nLostScore = CConfiguration.getGameLostPenalityScore(nSkill, nLevel);
	    if(CConfiguration.IsFGEanbled() == false)
	    	ScoreRecord.reduceTotalWinScore(nLostScore);
		//[ScoreRecord saveRecord];
	    
		//if(m_GameView != nil)
		//{
		//	m_GameView.m_nLoseSceneFlash = 0;
		//	[m_GameView updateGameEndState];
		//}	
	    m_bHandlePostWinGameOptions = false;
	    m_bHandlePostLostGameAction = true;
	  
	    //[GUIEventLoop SendEvent:GUIID_EVENT_POSTLOSTMESSAGE eventSender:nil];
	
	}	

	private void handleWinResult()
	{
		CGameScene.SetGameStateResultWin();
		if(m_GameAudio != null && CConfiguration.IsSoundEnable() == true)
			m_GameAudio.PlayWin();		
		
		int nSkill = CConfiguration.getGameSkill();
		int nLevel = CConfiguration.getGameLevel();
		//UpdateTodayGameScore(nSkill, nLevel);
		//int nSkill = [Configuration getGameSkill];
		//int nLevel = [Configuration getGameLevel];
	    int nWinScore = CConfiguration.getGameWinScore(nSkill, nLevel);
	    ScoreRecord.addScore(nSkill, nLevel);
	    //if(CConfiguration.IsFGEanbled() == true)
	    //	nWinScore = nWinScore*3;
	    ScoreRecord.addTotalWinScore(nWinScore);
		//[ScoreRecord saveRecord];
		//if(m_GameView != nil)
		//{
		//	[m_GameView updateGameEndState];
	    //    [m_GameView updatePlayerLobbyWinScore];
		//}
	    m_bHandlePostWinGameOptions = true;
	    m_bHandlePostLostGameAction = false;
	    //[GUIEventLoop SendEvent:GUIID_EVENT_POSTWINMESSAGE eventSender:nil];
	
	}	
	
	public boolean ShouldHandlePostWinningOptions()
	{
	    return m_bHandlePostWinGameOptions;
	}

	public boolean ShouldHandlePostLostAction()
	{
	    return m_bHandlePostLostGameAction;
	}

	public void GameLose()
	{
		if(CGameScene.IsGameStateResultLose() == true)
			return;
		
		endGame();
		handleLoseResult();
	}

	public void GameWin()
	{
		if(CGameScene.IsGameStateResultWin() == true)
			return;
		
		endGame();
		handleWinResult();
	}	
	
	public void SetDirty(boolean bDirty)
	{
		m_bDirty = bDirty;
	}

	public boolean isDirty()
	{
		return m_bDirty;
	}
	
	public void PlaySound(int nSoundID)
	{
		if(m_GameAudio == null || CConfiguration.IsSoundEnable() == false)
			return;		
		
		switch (nSoundID) 
		{
			case CGameAudio.GAME_SOUND_ID_PLAYERSHOOT:
				m_GameAudio.PlayDogBreath();
				break;
			case CGameAudio.GAME_SOUND_ID_TARGETHORN:
				m_GameAudio.PlayCowMeeo();
				break;
			case CGameAudio.GAME_SOUND_ID_TARGETSHOOT:
				m_GameAudio.PlayCowPupu();
				break;
			case CGameAudio.GAME_SOUND_ID_TARGETKNOCKDOWN:
				m_GameAudio.PlayCowKnockdown();
				break;
			case CGameAudio.GAME_SOUND_ID_BLAST:
				m_GameAudio.PlayBlast();
				break;
			case CGameAudio.GAME_SOUND_ID_COLLISION:
				m_GameAudio.PlayCollision();
				break;
			case CGameAudio.GAME_SOUND_ID_JUMP:
				m_GameAudio.PlayJump();
				break;
			case CGameAudio.GAME_SOUND_ID_CRASH:
				m_GameAudio.PlayCrash();
				break;
			case CGameAudio.GAME_SOUND_ID_THUNDER:
				m_GameAudio.PlayThunder();
				break;
		}
	}
	
	public void StopSound(int nSoundID)
	{
		if(m_GameAudio == null || CConfiguration.IsSoundEnable() == false)
			return;		
		
		switch (nSoundID) 
		{
			case CGameAudio.GAME_SOUND_ID_PLAYERSHOOT:
				m_GameAudio.StopDogBreath();
				break;
			case CGameAudio.GAME_SOUND_ID_TARGETHORN:
				m_GameAudio.StopCowMeeo();
				break;
			case CGameAudio.GAME_SOUND_ID_TARGETSHOOT:
				m_GameAudio.StopCowPupu();
				break;
			case CGameAudio.GAME_SOUND_ID_TARGETKNOCKDOWN:
				m_GameAudio.StopCowKnockdown();
				break;
			case CGameAudio.GAME_SOUND_ID_BLAST:
				m_GameAudio.StopBlast();
				break;
			case CGameAudio.GAME_SOUND_ID_COLLISION:
				m_GameAudio.StopCollision();
				break;
			case CGameAudio.GAME_SOUND_ID_JUMP:
				m_GameAudio.StopJump();
				break;
			case CGameAudio.GAME_SOUND_ID_CRASH:
				m_GameAudio.StopCrash();
				break;
			case CGameAudio.GAME_SOUND_ID_THUNDER:
				m_GameAudio.StopThunder();
				break;
		}
	}
	
	public void SwitchToBackgroundSound()
	{
		if(m_GameAudio != null && CConfiguration.IsSoundEnable() == true)
			m_GameAudio.SwitchToBackgroundSound();		
	}
	
	public void PlayBlockageSound()
	{
		if(m_GameAudio != null && CConfiguration.IsSoundEnable() == true)
			m_GameAudio.PlayBlockageSound();		
	}
	
	public void SetLevelOne()
	{
		int nOldLevel = CConfiguration.getGameLevel();
		int nOldSkill = CConfiguration.getGameSkill();

		CConfiguration.setGameLevelOne();
		
		int nNewLevel = CConfiguration.getGameLevel();
		int nNewSkill = CConfiguration.getGameSkill();
		
		boolean bChange = (nOldLevel != nNewLevel || nOldSkill != nNewSkill);
		if(bChange == true)
		{
//			CScoreFactory.SaveScores(m_ApplicationContext, m_GameScore);
		}
		if(CGameScene.IsGameStatePause() == true)
		{
			if(bChange == true)
			{	
				startNewGame();
			}	
			else
				resumeGame();
		}
		else
		{
			if(bChange == true)
				resetGameScene();
		}
	}
	
	public void SetLevelTwo()
	{
		int nOldLevel = CConfiguration.getGameLevel();
		int nOldSkill = CConfiguration.getGameSkill();

		CConfiguration.setGameLevelTwo();
		
		int nNewLevel = CConfiguration.getGameLevel();
		int nNewSkill = CConfiguration.getGameSkill();

		boolean bChange = (nOldLevel != nNewLevel || nOldSkill != nNewSkill);
		if(bChange == true)
		{
//			CScoreFactory.SaveScores(m_ApplicationContext, m_GameScore);
		}
		if(CGameScene.IsGameStatePause() == true)
		{
			if(bChange == true)
				startNewGame();
			else
				resumeGame();
		}
		else
		{
			if(bChange == true)
				resetGameScene();
		}
	}
	
	public void SetLevelThree()
	{
		int nOldLevel = CConfiguration.getGameLevel();
		int nOldSkill = CConfiguration.getGameSkill();

		CConfiguration.setGameLevelThree();
		
		int nNewLevel = CConfiguration.getGameLevel();
		int nNewSkill = CConfiguration.getGameSkill();
	
		boolean bChange = (nOldLevel != nNewLevel || nOldSkill != nNewSkill);
		if(bChange == true)
		{
//			CScoreFactory.SaveScores(m_ApplicationContext, m_GameScore);
		}
		if(CGameScene.IsGameStatePause() == true)
		{
			if(bChange == true)
				startNewGame();
			else
				resumeGame();
		}
		else
		{
			if(bChange == true)
				resetGameScene();
		}
	}
	
	public void SetLevelFour()
	{
		int nOldLevel = CConfiguration.getGameLevel();
		int nOldSkill = CConfiguration.getGameSkill();

		CConfiguration.setGameLevelFour();
		
		int nNewLevel = CConfiguration.getGameLevel();
		int nNewSkill = CConfiguration.getGameSkill();
	
		boolean bChange = (nOldLevel != nNewLevel || nOldSkill != nNewSkill);
		if(bChange == true)
		{
//			CScoreFactory.SaveScores(m_ApplicationContext, m_GameScore);
		}
		if(CGameScene.IsGameStatePause() == true)
		{
			if(bChange == true)
				startNewGame();
			else
				resumeGame();
		}
		else
		{
			if(bChange == true)
				resetGameScene();
		}
	}
	
	public void SetSkillOne()
	{
		int nOldLevel = CConfiguration.getGameLevel();
		int nOldSkill = CConfiguration.getGameSkill();

		CConfiguration.setGameSkillOne();
		
		int nNewLevel = CConfiguration.getGameLevel();
		int nNewSkill = CConfiguration.getGameSkill();
	
		boolean bChange = (nOldLevel != nNewLevel || nOldSkill != nNewSkill);
		if(bChange == true)
		{
//			CScoreFactory.SaveScores(m_ApplicationContext, m_GameScore);
		}
		if(CGameScene.IsGameStatePause() == true)
		{
			if(bChange == true)
				startNewGame();
			else
				resumeGame();
		}
		else
		{
			if(bChange == true)
				resetGameScene();
		}
	}
	
	public void SetSkillTwo()
	{
		int nOldLevel = CConfiguration.getGameLevel();
		int nOldSkill = CConfiguration.getGameSkill();
		
		CConfiguration.setGameSkillTwo();

		int nNewLevel = CConfiguration.getGameLevel();
		int nNewSkill = CConfiguration.getGameSkill();

		boolean bChange = (nOldLevel != nNewLevel || nOldSkill != nNewSkill);
		if(bChange == true)
		{
//			CScoreFactory.SaveScores(m_ApplicationContext, m_GameScore);
		}
		if(CGameScene.IsGameStatePause() == true)
		{
			if(bChange == true)
				startNewGame();
			else
				resumeGame();
		}
		else
		{
			if(bChange == true)
				resetGameScene();
		}
	}
	
	public void SetSkillThree()
	{
		int nOldLevel = CConfiguration.getGameLevel();
		int nOldSkill = CConfiguration.getGameSkill();

		CConfiguration.setGameSkillThree();
		
		int nNewLevel = CConfiguration.getGameLevel();
		int nNewSkill = CConfiguration.getGameSkill();

		boolean bChange = (nOldLevel != nNewLevel || nOldSkill != nNewSkill);
		if(bChange == true)
		{
//			CScoreFactory.SaveScores(m_ApplicationContext, m_GameScore);
		}
		if(CGameScene.IsGameStatePause() == true)
		{
			if(bChange == true)
				startNewGame();
			else
				resumeGame();
		}
		else
		{
			if(bChange == true)
				resetGameScene();
		}
	}
	
	private void InitializeScore()
	{
//		CScoreFactory.LoadScores(m_ApplicationContext, m_GameScore);
        // get the current date
/*        final Calendar c = Calendar.getInstance();
		
		int nYear = c.get(Calendar.YEAR);
		int nMonth = c.get(Calendar.MONTH)+1;
		int nDay = c.get(Calendar.DAY_OF_MONTH);
		
		if(m_GameScore.IsSameDay(nYear, nMonth, nDay) == false)
		{
			m_GameScore.ResetTodayDataAndTime(nYear, nMonth, nDay);
		}
		CConfiguration.setGameLevel(m_GameScore.m_Active_Game_Level);
		CConfiguration.setGameSkill(m_GameScore.m_Active_Game_Skill);*/
	}
	
	private void UpdateTodayGameScore(int nSkill, int nLevel)
	{
/*        final Calendar c = Calendar.getInstance();
		
		int nYear = c.get(Calendar.YEAR);
		int nMonth = c.get(Calendar.MONTH)+1;
		int nDay = c.get(Calendar.DAY_OF_MONTH);
		
		if(m_GameScore.IsSameDay(nYear, nMonth, nDay) == false)
		{
			m_GameScore.ResetTodayDataAndTime(nYear, nMonth, nDay);
		}
		m_GameScore.AddTodayWinCount(nLevel, nSkill);*/
//		CScoreFactory.SaveScores(m_ApplicationContext, m_GameScore);
	}
	
	private void UpdateTodayGamePlayCount()
	{
/*        final Calendar c = Calendar.getInstance();
		
		int nYear = c.get(Calendar.YEAR);
		int nMonth = c.get(Calendar.MONTH)+1;
		int nDay = c.get(Calendar.DAY_OF_MONTH);
		
		if(m_GameScore.IsSameDay(nYear, nMonth, nDay) == false)
		{
			m_GameScore.ResetTodayDataAndTime(nYear, nMonth, nDay);
		}
		m_GameScore.AddTodayLoseCount();*/
//		CScoreFactory.SaveScores(m_ApplicationContext, m_GameScore);
	}
	
	public void ReloadScore()
	{
		InitializeScore();
	}
	
	public void UpdateGameSetting()
	{

		//m_GameScore.m_bSoundEnable = CConfiguration.IsSoundEnable();
		//???????m_GameScore.m_bPaperBackgroundEnable = CConfiguration.IsPaperbackground();
		
//		CScoreFactory.SaveScores(m_ApplicationContext, m_GameScore);
		if(CGameScene.IsGameStatePause() == true)
		{
			resumeGame();
		}
	}
	
	public void ShutDownGame()
	{
		endGame();
		m_GameAudio.StopBackgroundSound();		
		m_GameAudio.ReleaseSoundSource();
	}
	
	public int GetLastWinSkill()
	{
		return ScoreRecord.getLastWinSkill();//m_GameScore.m_Last_Play_Skill;
	}	

	public int GetLastWinLevel()
	{
		return ScoreRecord.getLastWinLevel();//m_GameScore.m_Last_Play_Level;
	}	

	public int GetTotalScore(int nSkill, int nLevel)
	{
		return ScoreRecord.getScore(nSkill, nLevel);  //m_GameScore.GetTotalScore(nSkill, nLevel);
	}

	public int GetTodayScore(int nSkill, int nLevel)
	{
		return ScoreRecord.getTotalWinScore();//m_GameScore.GetTodayScore(nSkill, nLevel);
	}
}
