/**
 * 
 */
package com.xgadget.ChuiNiuLite;

import java.util.Random;
import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.PointF;

/**
 * @author zhaohuixing
 *
 */
public class CSkyObject implements IGunTarget 
{
	private CGameScene			m_Controller;
	Vector<CCloudObject>		m_Aliens;
	int                         m_nTimerElaspe;
	int							m_nTimerStep;
	int							m_nShootStep;
	int							m_nShootThreshold;
	public CCowObject			m_Target;
	CRainBow					m_Rainbow;
	int							m_nShootRainBowTime;
	
	public CSkyObject(CGameScene parent)
	{
		m_Controller = parent;
		m_Aliens = new Vector<CCloudObject>();
		m_nShootStep = 0;
		m_nShootThreshold = CConfiguration.getAlienShootThreshold(false); //(getRandNumber()%m_nShootMaxInterval+1)%5;
		m_nTimerElaspe = CConfiguration.GAME_TIMER_DEFAULT_ALIEN_STEP;
		m_nTimerStep = 0;
		m_Rainbow = new CRainBow(parent);
		m_nShootRainBowTime = CConfiguration.getRainRowStartTime();
	}
	
	public void Reset()
	{
		int nCount = m_Aliens.size();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				m_Aliens.elementAt(i).Reset();
			}	
		}
		m_nTimerStep = 0;
		m_nShootStep = 0;
		m_nShootThreshold = CConfiguration.getAlienShootThreshold(true); 
		m_nShootRainBowTime = CConfiguration.getRainRowStartTime();
		m_Rainbow.reset();
		
	}

	/* (non-Javadoc)
	 * @see com.xgadget.ChuiNiuLite.IGunTarget#HitByBullet(com.xgadget.ChuiNiuLite.IBullet)
	 */
	//@Override
	public boolean HitByBullet(IBullet bullet)
	{
		boolean bRet = false;

		int nCount = m_Aliens.size();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CCloudObject alien = m_Aliens.elementAt(i);
				if(alien != null && alien.isMotion() == true)
				{
					if(alien.HitByBullet(bullet) == true)
					{
						alien.blast();
						bRet = true;
					}	
				}
			}	
		}
		
		return bRet;
	}

	public void UpdateLayout()
	{
		int nCount = m_Aliens.size();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				m_Aliens.elementAt(i).UpdateLayout();
			}	
		}
	}

	public void Draw(Canvas canvas)
	{
		int nCount = m_Aliens.size();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CCloudObject alien = m_Aliens.elementAt(i);
				if(alien != null && (alien.isMotion() == true || alien.isBlast() == true))
				{
					alien.Draw(canvas);
				}
			}	
		}
		if(m_Rainbow != null && (m_Rainbow.isMotion() == true || m_Rainbow.isWin() == true))
		{
			m_Rainbow.Draw(canvas);
		}	
	}	

	public void DrawWin(Canvas canvas)
	{
		if(m_Rainbow != null)
		{
			m_Rainbow.DrawWin(canvas);
		}	
	}	

	public void shootAt(PointF fromPt, PointF speed)
	{
		int nCount = m_Aliens.size();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CCloudObject alien = m_Aliens.elementAt(i);
				if(alien != null && alien.isStop() == true)
				{
					alien.moveTo(fromPt);
					if(CConfiguration.IsFGEanbled() == true)
						speed.x = speed.x/5.0f;
					alien.setSpeed(speed);
					alien.startMotion();
					return;
				}	
			}	
		}
	}

	public int aliensInQueue()
	{
		int nRet = 0;
		
		int nCount = m_Aliens.size();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CCloudObject alien = m_Aliens.elementAt(i);
				if(alien != null && alien.isStop() == true)
				{
					++nRet;
				}	
			}	
		}
		
		return nRet;
	}

	private int getCanShootBird()
	{
	    int index = -1;
	   
		int nCount = m_Aliens.size();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CCloudObject alien = m_Aliens.elementAt(i);
				if(alien != null && alien.isMotion() == true && alien.getAlienType() == CCloudObject.GAME_ALIEN_TYPE_BIRD)
				{
	                if(alien.getPosition().x < 0)
	                    return i;
				}
			}	
		}
	    
	    return index;
	}
	
	public boolean shoot()
	{
		int nCount = m_Aliens.size();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CCloudObject alien = m_Aliens.elementAt(i);
	            float sFactor = 1.0f;
				if(alien != null && alien.isStop() == true)
				{
					float x = CGameLayout.GetGameSceneWidth()*(-0.5f);
					Random rand = new Random();
					int n = rand.nextInt();
					float r = (float)(n%4+1);
					float y = CGameLayout.GetGameSceneHeight()- CConfiguration.GAME_DEFAULT_ALIEN_POINT_OFFSET/r;
                    if(i%2 == 0)
                    {    
                        alien.setAlienType(CCloudObject.GAME_ALIEN_TYPE_BIRD);
                    }    
                    if(CConfiguration.canBirdShoot() == true)
                    {
                        if(i%3 == 1)
                        {
                            int nIndex = this.getCanShootBird();
                            if(0 <= nIndex)
                            {
                            	CCloudObject pBird = m_Aliens.elementAt(nIndex);
                                if(pBird != null)
                                {
                                    x = pBird.getPosition().x+ pBird.getBound().width()*0.5f;
                                    y = pBird.getPosition().y;
                                    sFactor = 2.0f;//(1.0 + [Configuration getBirdFlyingRatio])*1.6;
                                    alien.setAlienType(CCloudObject.GAME_ALIEN_TYPE_BIRD_BUBBLE);
                                }
                            }
                        }
                    }
					
					
					alien.moveTo(x, y);
					float speedx = CConfiguration.GAME_DEFAULT_ALIEN_SPEED_X*sFactor;
					float speedy = CConfiguration.GAME_DEFAULT_ALIEN_SPEED_Y;
					if(CConfiguration.IsFGEanbled() == true)
						speedx = speedx/2.5f; 
					alien.setSpeed(speedx, speedy);
					alien.startMotion();
					return true;
				}	
			}	
		}
		
		return false;
	}	

	public void addAlien(CCloudObject alien)
	{
		alien.m_Controller = m_Controller;
		m_Aliens.add(alien);
	}

	public boolean knockDown(CCowObject target)
	{
		boolean bRet = false;
	    if(CConfiguration.canKnockDownTarget() == false)
			return bRet;
		
		int nCount = m_Aliens.size();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CCloudObject alien = m_Aliens.elementAt(i);
				if(alien != null && alien.isMotion() == true)
				{
					bRet = target.knockdownBy(alien);
					if(bRet == true)
						break;
				}	
			}	
		}
		return bRet;
	}
	
	public boolean updateTimerEvent()
	{
		boolean bRet = false;

		++m_nShootStep;
		if(m_nShootThreshold <= m_nShootStep)
		{	
			m_nShootThreshold = CConfiguration.getAlienShootThreshold(true); 
			m_nShootStep = 0;
			bRet = shoot();
		}	
		
		if(m_Target != null)
		{
			if(knockDown(m_Target) == true)
			{
				bRet = true;
			}	
		}
		
		return bRet;
	}
	
	public boolean OnTimerEvent()
	{
		boolean bRet = false;
		
		int nCount = m_Aliens.size();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CCloudObject alien = m_Aliens.elementAt(i);
				if(alien != null && (alien.isMotion() == true || alien.isBlast() == true))
				{
					if(alien.OnTimerEvent() == true)
					{
						bRet = true;
					}	
				}
			}	
		}
		++m_nTimerStep;
		if(m_nTimerElaspe <= (m_nTimerStep%(m_nTimerElaspe+1)))
		{
			if(updateTimerEvent() == true)
				bRet = true;
		}	
		
		if(m_nShootRainBowTime == m_nTimerStep)
		{
			ShootRainBow();
			bRet = true;
		}	
		else if(m_nShootRainBowTime < m_nTimerStep) 
		{
			if(m_Rainbow != null)
			{
				if(m_Rainbow.onTimerEvent() == true)
					bRet = true;
			}	
		}
		return bRet;
	}

	public void ShootRainBow()
	{
		if(m_Rainbow != null)
			m_Rainbow.startMotion();
	}
}
