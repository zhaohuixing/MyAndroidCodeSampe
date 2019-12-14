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

/**
 * @author zhaohuixing
 *
 */
public class CGroundObject 
{
	public static final int GAME_DEFAULT_BLOCK_SHOOT_THRESHED =	120;
	
	private IGameController		m_Controller;
	public CDogObject 			m_BlockTarget;
	private Vector<CBlockageObject>		m_Skill1Blocks;
	private Vector<CBlockageObject>		m_Skill2Blocks;
	private Vector<CBlockageObject>		m_Skill3Blocks;
	int				m_nShootStep;
	int				m_nShootThreshold;
	int				m_nShootMaxInterval;
	
	int				m_nTimerStep;
	int				m_nAnimationStep;
	
	public CGroundObject(IGameController parent)
	{
		m_Controller = parent;
		m_Skill1Blocks = new Vector<CBlockageObject>();
		m_Skill2Blocks = new Vector<CBlockageObject>();
		m_Skill3Blocks = new Vector<CBlockageObject>();
		m_nShootStep = 0;
		m_nShootMaxInterval = GAME_DEFAULT_BLOCK_SHOOT_THRESHED;
		m_nShootThreshold = CConfiguration.getBlockageShootThreshold();
		m_nTimerStep = 0;
		m_nAnimationStep = 0;
		m_BlockTarget = null;
		
	}
	
	public void UpdateLayout()
	{
		int i;
		for(i = 0; i < m_Skill1Blocks.size(); ++i)
		{
			m_Skill1Blocks.elementAt(i).UpdateLayout();
		}
		for(i = 0; i < m_Skill2Blocks.size(); ++i)
		{
			m_Skill2Blocks.elementAt(i).UpdateLayout();
		}
		for(i = 0; i < m_Skill3Blocks.size(); ++i)
		{
			m_Skill3Blocks.elementAt(i).UpdateLayout();
		}
	}
	
	public void reset()
	{
		int i;
		for(i = 0; i < m_Skill1Blocks.size(); ++i)
		{
			m_Skill1Blocks.elementAt(i).Reset();
		}
		for(i = 0; i < m_Skill2Blocks.size(); ++i)
		{
			m_Skill2Blocks.elementAt(i).Reset();
		}
		for(i = 0; i < m_Skill3Blocks.size(); ++i)
		{
			m_Skill3Blocks.elementAt(i).Reset();
		}
		m_nShootStep = 0;
		m_nTimerStep = 0;
		m_nAnimationStep = 0;
		m_nShootStep = 0;
		m_nShootMaxInterval = GAME_DEFAULT_BLOCK_SHOOT_THRESHED;
		m_nShootThreshold = CConfiguration.getBlockageShootThreshold();
	}
	
	public void Draw(Canvas canvas)
	{
		DrawBlocks(canvas);
	}
	
	public void DrawBlocks(Canvas canvas)
	{
		int nCount = GetCurrentBlockCount();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CBlockageObject block = GetCurrentBlockElement(i); 
				if(block != null && block.isMotion() == true)
				{
					block.Draw(canvas);
				}
			}
		}
	}	

	public void CalculateGrassLandMotion()
	{
	    float xoffset = CGameLayout.GetGrassUnitWidth();
	    float xspeed = CConfiguration.getBlockageSpeed().x/CConfiguration.getBlockageTimerElapse();
	    if(xoffset < ((float)m_nAnimationStep)*xspeed)
	        m_nAnimationStep = 0;
	}

	
	public void DrawGrass(Canvas canvas)
	{
		Bitmap bitmap = CImageLoader.GetGrassBitmap();
		if(bitmap == null)
			return;
	
		int imgw = bitmap.getWidth();
		int imgh = bitmap.getHeight();
		Rect srcRect = new Rect(0, 0, imgw, imgh);
		//int n = CGameLayout.GetGrassUnitNumber();
		float xoffset = CGameLayout.GetGrassUnitWidth();
		float xspeed = CConfiguration.getBlockageSpeed().x;///CConfiguration.getBlockageTimerElapse();
		float v = xoffset/xspeed;
		float sx = /*(((float)(m_nAnimationStep-n))*xspeed);*/ (((float)m_nAnimationStep)*v) - xoffset*6.0f;
		float sy = CGameLayout.GetGrassUnitHeight();
		float width = xoffset;
		float height = CGameLayout.GetGrassUnitHeight();
    
		float x = sx*CGameLayout.GetGameSceneDMScaleX();
		float y = CGameLayout.GameSceneToDeviceY(sy);
		float w = CGameLayout.ObjectMeasureToDevice(width);
		float h = CGameLayout.ObjectMeasureToDevice(height);
		RectF rt;
    
		for(int i = 0; i < CGameLayout.GetGrassUnitNumber(); ++i)
		{
			float left = x + w*((float)i);
			
			rt = new RectF(left, y, left + w, y + h);
			canvas.drawBitmap(bitmap, srcRect, rt, null);
		}
	}
	
	public boolean OnTimerEvent()
	{
		boolean bRet = false;
		++m_nTimerStep;
		if(CConfiguration.GAME_TIMER_PLAYER_STEP <= m_nTimerStep)
		{
			m_nTimerStep = 0;
			if(timerEventUpdate() == true)
				bRet = true;
		}	
		
		int nCount = GetCurrentBlockCount();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CBlockageObject block = GetCurrentBlockElement(i); 
				if(block != null && block.isMotion() == true)
				{
					if(block.OnTimerEvent() == true)
					{
						bRet = true;
					}	
				}
			}	
		}
		
		return bRet;
	}
	
	public boolean timerEventUpdate()
	{
		boolean bRet = false;
		int n = CGameLayout.GetGrassUnitNumber();
		m_nAnimationStep = (m_nAnimationStep+1)%n;
		CalculateGrassLandMotion();
		if(CConfiguration.canShootBlock() == true)
		{
			++m_nShootStep;
			if(m_nShootThreshold <= m_nShootStep)
			{	
				m_nShootThreshold = CConfiguration.getBlockageShootThreshold();
				m_nShootStep = 0;
				bRet = shoot();
			}	
			
			if(m_BlockTarget != null)
			{
				if(checkBlockTarget() == true)
				{
					bRet = true;
				}	
			}
			
		}	
		
		return bRet;
	}

	public boolean shoot()
	{
		boolean bRet = false;
		int nCount = GetCurrentBlockCount();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CBlockageObject block = GetCurrentBlockElement(i); 
				if(block != null && block.isStop() == true)
				{
//					float w = CGameLayout.m_fRockMeasureWidth;
					float h = CGameLayout.m_fRockMeasureHeight;
					float x = CGameLayout.m_fGameSceneMeasureWidth*(-0.5f);
					float y = h*0.5f;
					block.moveTo(x, y);
					PointF speed = CConfiguration.getBlockageSpeed();
					if(CConfiguration.IsFGEanbled() == true)
						speed.x = speed.x/3.0f;
					block.setSpeed(speed);
					block.startMotion();
					return true;
				}
			}
		}	
		return bRet;
	}
	
	public void shootAt(PointF fromPt, PointF speed)
	{
		int nCount = GetCurrentBlockCount();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CBlockageObject block = GetCurrentBlockElement(i); 
				if(block != null && block.isStop() == true)
				{
					block.moveTo(fromPt);
					if(CConfiguration.IsFGEanbled() == true)
						speed.x = speed.x/3.0f;
					block.setSpeed(speed);
					block.startMotion();
					return;
				}
			}
		}	
	}
	
	public boolean checkBlockTarget()
	{
		boolean bRet = false;
		if(m_BlockTarget == null)
			return bRet;
		
		int nCount = GetCurrentBlockCount();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CBlockageObject block = GetCurrentBlockElement(i); 
				if(block != null && block.isMotion() == true)
				{
					bRet = m_BlockTarget.blockBy(block);
					if(bRet == true)
						break;
				}
			}
		}	
		return bRet;
	}
	
	public int GetCurrentBlockCount()
	{
		int nCount = 0;
		int nSkill = CConfiguration.getGameSkill();
	
		switch(nSkill)
		{
			case CConfiguration.GAME_SKILL_LEVEL_ONE:
				nCount = m_Skill1Blocks.size();
				break;
			case CConfiguration.GAME_SKILL_LEVEL_TWO:
				nCount = m_Skill2Blocks.size();
				break;
			case CConfiguration.GAME_SKILL_LEVEL_THREE:
				nCount = m_Skill3Blocks.size();
				break;
		}
		
		return nCount;
	}		

	public CBlockageObject GetCurrentBlockElement(int i)
	{
		CBlockageObject block = null; 
		int nSkill = CConfiguration.getGameSkill();
	
		switch(nSkill)
		{
			case CConfiguration.GAME_SKILL_LEVEL_ONE:
				block = m_Skill1Blocks.elementAt(i);
				break;
			case CConfiguration.GAME_SKILL_LEVEL_TWO:
				block = m_Skill2Blocks.elementAt(i);
				break;
			case CConfiguration.GAME_SKILL_LEVEL_THREE:
				block = m_Skill3Blocks.elementAt(i);
				break;
		}
		
		return block;
	}		

	public boolean blockageHitTestWithTarget()
	{
		boolean bRet = false;
		
		if(CConfiguration.canShootBlock() == true)
		{
			int nCount = GetCurrentBlockCount();
			if(0 < nCount)
			{
				for(int i = 0; i < nCount; ++i)
				{
					CBlockageObject block = GetCurrentBlockElement(i); 
					if(block != null && block.isMotion() == true)
					{
						if(m_BlockTarget.hitTestWithBlockage(block) == true)
						{	
							bRet = true;
							break;
						}	
					}
				}
			}	
		}	
		
		return bRet;
	}
	
	public void addBlocks(CBlockageObject block, int nSkill)
	{
		switch(nSkill)
		{
			case CConfiguration.GAME_SKILL_LEVEL_ONE:
				m_Skill1Blocks.add(block);
				break;
			case CConfiguration.GAME_SKILL_LEVEL_TWO:
				m_Skill2Blocks.add(block);
				break;
			case CConfiguration.GAME_SKILL_LEVEL_THREE:
				m_Skill3Blocks.add(block);
				break;
		}		
	}	

	public void adjustTargetFreePosition()
	{
		int nCount = GetCurrentBlockCount();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CBlockageObject block = GetCurrentBlockElement(i); 
				if(block != null && block.isMotion() == true)
				{
					if(m_BlockTarget.hitTestWithBlockage(block) == true)
						m_BlockTarget.escapeFromBlockage(block);
				}
			}
		}	
	}	

	public int blocksInQueue()
	{
		int nRet = 0;
		int nCount = GetCurrentBlockCount();
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; ++i)
			{
				CBlockageObject block = GetCurrentBlockElement(i); 
				if(block != null && block.isStop() == true)
				{
					++nRet;
				}	
			}	
		}
		return nRet;
	}
}
