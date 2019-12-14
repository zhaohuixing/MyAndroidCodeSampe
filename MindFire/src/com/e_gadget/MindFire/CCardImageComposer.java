package com.e_gadget.MindFire;

import java.util.Vector;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.RoundRectShape;

public class CCardImageComposer 
{
	//The card background image
	private int					m_nValue;
	
    private Paint 				m_TempOverlayPaint;
    
    // Initialize paint for cursor highlight
	public CCardImageComposer(int nValue)
	{
		m_nValue = nValue;
	    m_TempOverlayPaint = new Paint();
	    m_TempOverlayPaint.setAntiAlias(true);
	    m_TempOverlayPaint.setARGB(127, 255, 128, 255);
	}

	private Bitmap CreateCardBlankImage(boolean bBackside)
	{
		Bitmap bitmap = null;
		
		Bitmap bmpSrc = CGameHelper.GetCardSoruceBitmap();
		if(bmpSrc != null)
		{	
			Rect rect = CGameHelper.GetCardImageSize();
	        int width = rect.width();
	        int height = rect.height();
	        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
	        int i;
	        if(bBackside == false)
	        {
	        	i = 0;
	        }
	        else
	        {
	        	i = 1;
	        }
	        Rect src = new Rect(0, 0, 0, 0);
	        Rect dst = new Rect(0, 0, width, height);
	        src.left = i*width; 
	        src.right = src.left + width; 
	        src.bottom = src.top + height;
	        	
			canvas.drawBitmap(bmpSrc, src, dst, null);
		}
		
		return bitmap;
	}
	
	public Bitmap CreateCardBitmap(boolean bBasic)
	{
		Bitmap destBmp = null; 
		Rect rect = CGameHelper.GetCardImageSize();
		int nWidth = rect.width();
		int nHeight = rect.height();
		destBmp = Bitmap.createBitmap(nWidth, nHeight, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(destBmp);
		DrawCardSource(canvas, bBasic);
		return destBmp;
	}

	public BitmapDrawable CreateTempCardImage()
	{
		BitmapDrawable image = null;
		Bitmap bitmap = CreateCardBitmap(false);
		if(bitmap != null)
		{
			Resources res = CGameHelper.GetSystemResource();
			image = new BitmapDrawable(res, bitmap);
			int density = bitmap.getDensity();
			image.setTargetDensity(density);
		}
		return image;
	}

	public BitmapDrawable CreateBasicCardImage()
	{
		BitmapDrawable image = null;
		Bitmap bitmap = CreateCardBitmap(true);
		if(bitmap != null)
		{
			Resources res = CGameHelper.GetSystemResource();
			image = new BitmapDrawable(res, bitmap);
			int density = bitmap.getDensity();
			image.setTargetDensity(density);
		}
		return image;
	}
	
	
	private void DrawCardSource(Canvas canvas, boolean bBasicCard)
	{
		Bitmap bitmap = CreateCardBlankImage(false);
		
		if(bitmap != null)
		{
			Resources res = CGameHelper.GetSystemResource();
			BitmapDrawable image = new BitmapDrawable(res, bitmap);
			int density = bitmap.getDensity();
			image.setTargetDensity(density);
			Rect rtCard = CGameHelper.GetCardImageSize();
			
			if(image != null)
			{
				image.setBounds(rtCard);
				image.draw(canvas);
			}	
			DrawDigitsSource(canvas, bBasicCard);
			if(bBasicCard == false)
			{
				RoundRectShape shape =  CGameHelper.GetPhantomCardDShape();
				shape.resize((float)rtCard.width(), (float)rtCard.height());
				shape.draw(canvas, m_TempOverlayPaint);
			}
		}
	}

	private void DrawDigitsSource(Canvas canvas, boolean bBasicCard)
	{
		if(bBasicCard)
		{
			DrawBasicCardContent(canvas);
		}
		else
		{
			DrawTempCardContent(canvas);
		}
	}
	
	private void DrawBasicCardContent(Canvas canvas)
	{
		DrawBasicCardDigits(canvas);
		DrawBasicCardFlower(canvas);
	}

	private void DrawBasicCardDigits(Canvas canvas)
	{
		Rect rtCard = CGameHelper.GetCardImageSize();
		int cWidth = rtCard.width();
		int cHeight = rtCard.height();
		int dWidth = CGameHelper.GetBasicCardDigitImageWidth();
		int dHeight = CGameHelper.GetBasicCardDigitImageHeight();

		Drawable dImage = GetBasicCardDigitImage(m_nValue, false);
		Rect src;
		if(dImage != null)
		{	
			src = new Rect(2, 4, dWidth+2, dHeight+4);
			dImage.setBounds(src);
			dImage.draw(canvas);
		}			

		dImage = GetBasicCardDigitImage(m_nValue, true);
		if(dImage != null)
		{	
			src = new Rect(cWidth-2-dWidth , cHeight-4-dHeight, cWidth-2, cHeight-4);
			dImage.setBounds(src);
			dImage.draw(canvas);
		}			
	}

	private void DrawBasicCardFlower(Canvas canvas)
	{
		DrawBasicCardSmallFlower(canvas, m_nValue);
		DrawBasicCardLargeFlower(canvas, m_nValue);
	}

	private BitmapDrawable GetBasicCardDigitImage(int Index, boolean bFlip)
	{
		int dWidth = CGameHelper.GetBasicCardDigitImageWidth();
		int dHeight = CGameHelper.GetBasicCardDigitImageHeight();
		int nType = CCardBase.GetCardType(Index);
		int nValue = CCardBase.GetCardValue(Index);
	
		BitmapDrawable image = null;
		Bitmap bmpSrc = CGameHelper.GetBasicCardNumberBitmap();
		if(bmpSrc != null)
		{
	        Bitmap bitmap = Bitmap.createBitmap(dWidth, dHeight, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			
			int i = 0;
			int j = 0;
			if(nType == CCardBase.DIAMOND || nType == CCardBase.CLUB)
				i = 1;
			if(bFlip == true)
				j = 2;

	        Rect src = new Rect(0, 0, 0, 0);
	        Rect dst = new Rect(0, 0, dWidth, dHeight);

	        src.left = (nValue-1)*dWidth; 
	        src.right = src.left + dWidth;
	        src.top = (i+j)*dHeight;
	        src.bottom = src.top + dHeight;

			canvas.drawBitmap(bmpSrc, src, dst, null);
			Resources res = CGameHelper.GetSystemResource();
			image = new BitmapDrawable(res, bitmap);
			int density = bitmap.getDensity();
			image.setTargetDensity(density);
		}
		
		return image;
	}
	
	private void DrawBasicCardSmallFlower(Canvas canvas, int index)
	{
		int nType = CCardBase.GetCardType(index);
		Rect rtCard = CGameHelper.GetCardImageSize();
        Rect rect = new Rect(0, 0, 0, 0);
        int width = CGameHelper.GetSmallFlowerImageWidth();
        int height = CGameHelper.GetSmallFlowerImageHeight();
        BitmapDrawable image = GetSmallFlowerImage(nType, false);
        rect.top = 19;
        rect.left = 3;
        rect.bottom = rect.top + height;
        rect.right = rect.left + width;
        image.setBounds(rect);
        image.draw(canvas);

        image = GetSmallFlowerImage(nType, true);
        rect.right = rtCard.width() - 3;
        rect.left = rect.right - width;
        rect.bottom = rtCard.height() - 19;
        rect.top = rect.bottom - height;
        image.setBounds(rect);
        image.draw(canvas);
	}
	
	private void DrawBasicCardLargeFlower(Canvas canvas, int index)
	{
		int nType = CCardBase.GetCardType(index);
		Rect rtCard = CGameHelper.GetCardImageSize();
        Rect rect = new Rect(0, 0, 0, 0);
        int width = CGameHelper.GetLargeFlowerImageWidth();
        int height = CGameHelper.GetLargeFlowerImageHeight();
        BitmapDrawable image = GetLargeFlowerImage(nType);
        int x = rtCard.centerX();
        int y = rtCard.centerY();
        rect.top = y - height/2;
        rect.left = x - width/2;
        rect.bottom = rect.top + height;
        rect.right = rect.left + width;
        image.setBounds(rect);
        image.draw(canvas);
	}

/*	private void DrawResultBasicCardLargeFlower(Canvas canvas, int index)
	{
		int nType = CCardBase.GetCardType(index);
        int width = CGameHelper.GetLargeFlowerImageWidth();
        int height = CGameHelper.GetLargeFlowerImageHeight();
        BitmapDrawable image = GetLargeFlowerImage(nType);
        Rect rect = new Rect(0, 0, width, height);
        image.setBounds(rect);
        image.draw(canvas);
	}
	
	private void DrawResultTempCardLargeFlower(Canvas canvas)
	{
        int width = CGameHelper.GetLargeFlowerImageWidth();
        int height = CGameHelper.GetLargeFlowerImageHeight();
        BitmapDrawable image = GetLargeFlowerImage(4);
        Rect rect = new Rect(0, 0, width, height);
        image.setBounds(rect);
        image.draw(canvas);
	} */
	
	private void DrawTempCardContent(Canvas canvas)
	{
		DrawTempCardDigits(canvas);
		DrawTempCardFlower(canvas);
	}
	
	private void DrawTempCardDigits(Canvas canvas)
	{
		Rect rtCard = CGameHelper.GetCardImageSize();
		Rect rtDigit = CGameHelper.GetAverageDigitSize();
		int cX = rtCard.centerX(); 
		int cY = rtCard.centerY(); 
		int cWidth = rtCard.width();
		int cTWidth = cWidth*8/10;
		int dWidth = rtDigit.width();
		int dHeight = rtDigit.height();

		int nDigits = CGameHelper.GetDigitCount(m_nValue);
		int cTRWidth = nDigits*dWidth;
		if(cTWidth < cTRWidth)
		{
			cTRWidth = cTWidth;
			dWidth = cTRWidth/nDigits; 
		}
		int nLeft, nTop;
		nLeft = cX - cTRWidth/2;   
		nTop = cY - dHeight/2;
		
		for(int i = nDigits-1; 0 <=i; --i)
		{
			int nTemp = CGameHelper.GetDigitAt(m_nValue, i);
			Drawable dImage = GetTempCardDigitImage(nTemp);
			if(dImage != null)
			{	
				dImage.setBounds(nLeft, nTop, nLeft+dWidth, nTop+dHeight);
				dImage.draw(canvas);
				dImage.invalidateSelf();
				nLeft += dWidth;
			}
		}
	}

	private BitmapDrawable GetTempCardDigitImage(int nDigit)
	{
		BitmapDrawable image = null;
		Bitmap bmpSrc = CGameHelper.GetTempCardNumberBitmap();
		if(bmpSrc != null)
		{	
			Rect rtDigit = CGameHelper.GetAverageDigitSize();
	        int width = rtDigit.width();
	        int height = rtDigit.height();
	        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
	        Rect src = new Rect(0, 0, 0, 0);
	        Rect dst = new Rect(0, 0, width, height);
	        src.left = nDigit*width; 
	        src.right = src.left + width; 
	        src.bottom = src.top + height;
	        	
			canvas.drawBitmap(bmpSrc, src, dst, null);
			Resources res = CGameHelper.GetSystemResource();
			image = new BitmapDrawable(res, bitmap);
			int density = bitmap.getDensity();
			image.setTargetDensity(density);
		}
		
		return image;
	}
	
	private void DrawTempCardFlower(Canvas canvas)
	{
		Rect rtCard = CGameHelper.GetCardImageSize();
        Rect rect = new Rect(0, 0, 0, 0);
        int width = CGameHelper.GetSmallFlowerImageWidth();
        int height = CGameHelper.GetSmallFlowerImageHeight();
        BitmapDrawable image = GetSmallFlowerImage(CCardBase.HEART, false);
        rect.top = 4;
        rect.left = 2;
        rect.bottom = rect.top + height;
        rect.right = rect.left + width;
        image.setBounds(rect);
        image.draw(canvas);

        image = GetSmallFlowerImage(CCardBase.CLUB, false);
        rect.top = 4;
        rect.bottom = rect.top + height;
        rect.right = rtCard.width() - 2;
        rect.left = rect.right - width;
        image.setBounds(rect);
        image.draw(canvas);

        image = GetSmallFlowerImage(CCardBase.DIAMOND, true);
        rect.left = 2;
        rect.right = rect.left + width;
        rect.bottom = rtCard.height() - 4;
        rect.top = rect.bottom - height;
        image.setBounds(rect);
        image.draw(canvas);

        image = GetSmallFlowerImage(CCardBase.SPADE, true);
        rect.right = rtCard.width() - 2;
        rect.left = rect.right - width;
        rect.bottom = rtCard.height() - 4;
        rect.top = rect.bottom - height;
        image.setBounds(rect);
        image.draw(canvas);
	}
	
	private BitmapDrawable GetSmallFlowerImage(int nType, boolean bFlip)
	{
		BitmapDrawable image = null;
		Bitmap bmpSrc = CGameHelper.GetSmallFlowersBitmap();
		if(bmpSrc != null)
		{	
	        int width = CGameHelper.GetSmallFlowerImageWidth();
	        int height = CGameHelper.GetSmallFlowerImageHeight();
	        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
	        Rect src = new Rect(0, 0, 0, 0);
	        Rect dst = new Rect(0, 0, width, height);
	        
	        src.left = nType*width; 
	        src.right = src.left + width;
	        
	        if(bFlip == true)
	        	src.top += height;
	        src.bottom = src.top + height;
	        	
			canvas.drawBitmap(bmpSrc, src, dst, null);
			Resources res = CGameHelper.GetSystemResource();
			image = new BitmapDrawable(res, bitmap);
			int density = bitmap.getDensity();
			image.setTargetDensity(density);
		}
		
		return image;
	}

	private BitmapDrawable GetLargeFlowerImage(int nType)
	{
		BitmapDrawable image = null;
		Bitmap bmpSrc = CGameHelper.GetLargeFlowersBitmap();
		if(bmpSrc != null)
		{	
	        int width = CGameHelper.GetLargeFlowerImageWidth();
	        int height = CGameHelper.GetLargeFlowerImageHeight();
	        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
	        Rect src = new Rect(0, 0, 0, 0);
	        Rect dst = new Rect(0, 0, width, height);
	        
	        src.left = nType*width; 
	        src.right = src.left + width;
	        src.bottom = src.top + height;
	        	
			canvas.drawBitmap(bmpSrc, src, dst, null);
			Resources res = CGameHelper.GetSystemResource();
			image = new BitmapDrawable(res, bitmap);
			int density = bitmap.getDensity();
			image.setTargetDensity(density);
		}
		
		return image;
	}

}
