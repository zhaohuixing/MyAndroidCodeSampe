/**
 * 
 */
package com.xgadget.BubbleTile;

/**
 * @author zhaohuixing
 * 
 * The bubble rendering class, only for rendering
 */

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.Canvas;
import android.graphics.Rect;


public class CBubblePainter 
{
	public static final int BUBBLE_TYPE_COLOR = 0;
	public static final int BUBBLE_TYPE_STAR = 1;
	public static final int BUBBLE_TYPE_FROG = 2;
	
	private int 			m_nBubbleType;
	private BitmapDrawable		m_NumberImage; 
	
	public CBubblePainter()
	{
		m_nBubbleType = BUBBLE_TYPE_COLOR;
		m_NumberImage = null;
	}

	public CBubblePainter(int number)
	{
		m_nBubbleType = BUBBLE_TYPE_COLOR;
		m_NumberImage = CResourceHelper.GetDefaultNumberImage(number, 0xFF000000);
	}

	public CBubblePainter(int nType, int number)
	{
		m_nBubbleType = nType;
		m_NumberImage = CResourceHelper.GetDefaultNumberImage(number, 0xFF000000);
	}
	
	public void SetNumber(int number)
	{
		m_NumberImage = CResourceHelper.GetDefaultNumberImage(number, 0xFF000000);
	}

	public void SetBubbleType(int nType)
	{
		m_nBubbleType = nType;
	}

	public void DrawBubble(Canvas canvas, Rect rect)
	{
		Drawable		BubbleImage = null;
		switch(m_nBubbleType)
		{
			case BUBBLE_TYPE_STAR:
				BubbleImage = CResourceHelper.GetStarBubbleImage();
				break;
			case BUBBLE_TYPE_FROG:	
				BubbleImage = CResourceHelper.GetFrogBubbleImage();
				break;
			case BUBBLE_TYPE_COLOR:
			default:
				BubbleImage = CResourceHelper.GetColorBubbleImage();
				break;
		}
		BubbleImage.setBounds(rect);
		BubbleImage.draw(canvas);
		if(m_NumberImage != null)
		{
			int iw = m_NumberImage.getBitmap().getWidth();
			int ih = m_NumberImage.getBitmap().getHeight();
			int nsize = rect.width()/3;
			float fRatio = ((float)iw)/((float)ih);
			int nw, nh;
			if(fRatio < 1)
			{
				nw = (int)(((float)nsize)*fRatio);
				nh = nsize;
			}
			else
			{	
				nw = nsize;
				nh = nsize;
			}
			int sx = rect.left+(rect.width()-nw)/2;
			int sy = rect.top+(rect.height()-nh)/2;
			Rect rt = new Rect(sx, sy, sx+nw, sy+nh);
			m_NumberImage.setBounds(rt);
			m_NumberImage.draw(canvas);
		}
	}
	
}
