package com.xgadget.BubbleTile;

import android.content.Context;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Bitmap;

public class GameView extends View
{
	PlayBoard playboard;
	public GameView(Context context)
	{
		super(context);
		
		playboard = new PlayBoard(this);
	}
	
	public GameView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		playboard = new PlayBoard(this);
	}
	
	protected void onDraw(Canvas canvas)
	{
		RectF rc = new RectF(0,0,500,500);
		playboard.drawBoard(canvas, rc);
/*		
		//Test draw code
		CBubblePainter bubblePaint = new CBubblePainter(); 
		bubblePaint.SetBubbleType(CBubblePainter.BUBBLE_TYPE_COLOR);
		bubblePaint.SetNumber(7);
		bubblePaint.DrawBubble(canvas, new Rect(10, 10, 90, 90));

		bubblePaint.SetBubbleType(CBubblePainter.BUBBLE_TYPE_STAR);
		bubblePaint.SetNumber(20);
		bubblePaint.DrawBubble(canvas, new Rect(10, 100, 90, 180));

		bubblePaint.SetBubbleType(CBubblePainter.BUBBLE_TYPE_FROG);
		bubblePaint.SetNumber(314);
		bubblePaint.DrawBubble(canvas, new Rect(10, 190, 90, 270));
		
		Bitmap bmp = CResourceHelper.GetBitmap(R.drawable.arrow);
		Drawable drawable = new BitmapDrawable(bmp);
		drawable.setBounds(new Rect(100,100, 110, 110));
		drawable.draw(canvas);
*/		
		
	}
}
