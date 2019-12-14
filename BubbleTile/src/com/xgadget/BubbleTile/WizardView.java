package com.xgadget.BubbleTile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class WizardView extends View 
{
	public WizardView(Context context)
	{
		super(context);
	}
	
	public WizardView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	
	protected void onDraw(Canvas canvas)
	{
		//Test draw code
		CBubblePainter bubblePaint = new CBubblePainter(); 
		bubblePaint.SetBubbleType(CBubblePainter.BUBBLE_TYPE_COLOR);
		bubblePaint.SetNumber(20);
		bubblePaint.DrawBubble(canvas, new Rect(210, 10, 290, 90));

		bubblePaint.SetBubbleType(CBubblePainter.BUBBLE_TYPE_STAR);
		bubblePaint.SetNumber(18);
		bubblePaint.DrawBubble(canvas, new Rect(210, 100, 290, 180));

		bubblePaint.SetBubbleType(CBubblePainter.BUBBLE_TYPE_FROG);
		bubblePaint.SetNumber(3);
		bubblePaint.DrawBubble(canvas, new Rect(210, 190, 290, 270));
	}

}
