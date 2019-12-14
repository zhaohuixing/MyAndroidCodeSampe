/**
 * 
 */
package com.xgadget.ChuiNiuLite;

import java.util.Vector;

//import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
//import android.graphics.Rect;

/**
 * @author zhaohuixing
 *
 */
public class CGunObject implements IGunEventDelegate 
{
	private CGameScene				m_Controller;
	public IGunOwner				m_Shooter;
	public IGunTarget				m_Target;
	public IGunTarget       		m_Aliens;
	public Vector<CBulletObject> 	m_Bullets;
	public CGunObject				m_EmenyGun;
	private PointF 					m_CenterInScene;
	private PointF					m_Speed;
	
	
	
	public CGunObject(CGameScene parent)
	{
		m_Controller = parent;
		m_Bullets = new Vector<CBulletObject>(); 
		m_Shooter = null;
		m_Target = null;
		m_Aliens = null;
		m_EmenyGun = null;
	}

	
	/* (non-Javadoc)
	 * @see com.xgadget.ChuiNiuLite.IGunEventDelegate#bulletBlasted(com.xgadget.ChuiNiuLite.IBullet)
	 */
	//@Override
	public void bulletBlasted(IBullet bullet) 
	{
		// TODO Auto-generated method stub
		if(bullet != null)
			bullet.Reset();
	}

	public void UpdateLayout()
	{
		int nCount = m_Bullets.size();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				m_Bullets.elementAt(i).UpdateLayout();
			}	
		}
	}
	
	public void Draw(Canvas canvas)
	{
		if(CGameScene.IsGameStateResult() == true)
			return;
		
		int nCount = m_Bullets.size();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CBulletObject bullet = 	m_Bullets.elementAt(i);
				if(bullet != null)
				{
					if(bullet.isShoot() == true || bullet.isBlast() == true)
					{	
						bullet.Draw(canvas);
					}	
				}	
			}	
		}
	}
	
	public boolean OnTimerEvent()
	{
		boolean bRet = false;
		int nCount = m_Bullets.size();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CBulletObject bullet = 	m_Bullets.elementAt(i);
				if(bullet != null)
				{
					if(bullet.isShoot() == true || bullet.isBlast() == true)
					{	
						if(bullet.OnTimerEvent() == true)
						{	
							bRet = true;
							if(m_Aliens != null && bullet.isShoot() == true)
							{
								if(m_Aliens.HitByBullet(bullet) == true)
								{
									bullet.blast();
								}	
							}	
							if(m_Target != null && bullet.isShoot() == true)
							{
								if(m_Target.HitByBullet(bullet) == true)
								{
									bullet.blast();
								}	
							}
							
							if(bullet.isShoot() == true)
							{
								if(m_EmenyGun != null)
								{
									if(m_EmenyGun.bulletHitByEmeny(bullet) == true)
									{
										bullet.blast();
									}	
								}	
							}		
						}
					}	
				}	
			}	
		}
		return bRet;
	}
	
	public PointF getSpeed()							//Base on game scence coordinate system
	{
		return m_Speed;
	}
	
	public PointF getPosition()
	{
		return m_CenterInScene;
	}

	public void reset()
	{
		int nCount = m_Bullets.size();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				m_Bullets.elementAt(i).Reset();
			}	
		}
	}	

	public void shoot()
	{
		int nCount = m_Bullets.size();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CBulletObject bullet = 	m_Bullets.elementAt(i);
				if(bullet != null && bullet.isReady() == true)
				{
					bullet.moveTo(getPosition());
					bullet.setSpeed(getSpeed());
					bullet.shoot();
					if(m_Shooter != null)
						m_Shooter.fire();
					
					return;
				}	
			}	
		}
	}

	public void shootAt(PointF fromPt, PointF speed)
	{
		int nCount = m_Bullets.size();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CBulletObject bullet = 	m_Bullets.elementAt(i);
				if(bullet != null && bullet.isReady() == true)
				{
					bullet.moveTo(fromPt);
					bullet.setSpeed(speed);
					bullet.shoot();
					if(m_Shooter != null)
						m_Shooter.fire();
					
					return;
				}	
			}	
		}
	}	

	public int bulletInGun()
	{
		int nRet = 0;
		
		int nCount = m_Bullets.size();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CBulletObject bullet = 	m_Bullets.elementAt(i);
				if(bullet != null)
				{
					if(bullet.isReady() == true)
						++nRet;
				}	
			}	
		}
		return nRet;
	}	

	public boolean bulletHitByEmeny(CBulletObject bullet)
	{
		int nCount = m_Bullets.size();
		boolean bRet = false;
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CBulletObject mybullet = m_Bullets.elementAt(i);
				if(bullet != null)
				{
					if(mybullet.isShoot() == true)
					{	
						if(mybullet.Hit(bullet) == true)
						{
							if(m_Controller != null)
								m_Controller.PlaySound(CGameAudio.GAME_SOUND_ID_COLLISION);
							
							mybullet.blast();
							return true;
						}
					}
				}
			}
		}
		return bRet;
	}
	
	public void addBullet(CBulletObject bullet)
	{
		m_Bullets.add(bullet);
	}	
	
}
