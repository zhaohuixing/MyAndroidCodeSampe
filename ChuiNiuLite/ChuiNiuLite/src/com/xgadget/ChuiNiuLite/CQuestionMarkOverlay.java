package com.xgadget.ChuiNiuLite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CQuestionMarkOverlay extends View 
{

	public CQuestionMarkOverlay(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		this.setBackgroundColor(Color.TRANSPARENT);
	}

	public CQuestionMarkOverlay(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.setBackgroundColor(Color.TRANSPARENT);
	}

	public CQuestionMarkOverlay(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.setBackgroundColor(Color.TRANSPARENT);
	}

	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) 
	{
		// TODO Auto-generated method stub
		if(CGameScene.IsGameStatePlay() == true && CConfiguration.IsMouthMode() == true && CConfiguration.IsFGEanbled() == true && CConfiguration.IsHoldFGTracking() == false)
		{
			DrawQMark(canvas);
		}
		if(CGameScene.IsGameStatePlay() == false)
		{
			DrawSwitch(canvas);
		}
	}

	private void DrawQMark(Canvas canvas)
	{
    	int nImageWidth = CImageLoader.GetSingleQMarkWidth();
    	int nImageHeight = CImageLoader.GetSingleQMarkHeight();
    	Rect imgRt = new Rect(0, 0, nImageWidth, nImageHeight);
    	int nHeight = this.getHeight();
    	if(nImageHeight < nHeight)
    	{
    		nHeight = nImageHeight; 
    	}
    	int nWidth = nImageWidth*nHeight/nImageHeight;
    	int x = (this.getWidth() - nWidth)/2;
    	RectF paintRT = new RectF(x, 0, x + nWidth, nHeight);
        Bitmap bmp = CImageLoader.GetSingleQMarkBitmap();
        canvas.drawBitmap(bmp, imgRt, paintRT, null);
	}
	
	private void DrawSwitch(Canvas canvas)
	{
    	int nImageWidth = CImageLoader.GetSwitchImageWidth();
    	int nImageHeight = CImageLoader.GetSwitchImageHeight();
    	Rect imgRt = new Rect(0, 0, nImageWidth, nImageHeight);
    	int nHeight = this.getHeight()/4;
    	if(nImageHeight < nHeight)
    	{
    		nHeight = nImageHeight; 
    	}
    	int nWidth = nImageWidth*nHeight/nImageHeight;
    	RectF paintRT = new RectF(0, 0, nWidth, nHeight);
        Bitmap bmp = CImageLoader.GetSwitchImage();
        canvas.drawBitmap(bmp, imgRt, paintRT, null);
	}
	
}
