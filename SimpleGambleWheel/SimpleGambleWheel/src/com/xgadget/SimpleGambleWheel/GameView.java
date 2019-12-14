package com.xgadget.SimpleGambleWheel;

//import java.util.Random;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
//import android.widget.ImageView;
//import android.graphics.Bitmap;
//import android.graphics.BitmapShader;
import android.graphics.Canvas;
//import android.graphics.Paint;
import android.graphics.Rect;
//import android.graphics.Shader;
import com.xgadget.uimodule.*;

public class GameView extends BasicLayoutView 
{
    Rect									m_RepaintRect;
    //MyAbsoluteLayout						m_TestCompoment;
    
    
	private void InitializeMembers()
	{
		int w = this.getWidth();
		int h = this.getHeight();
		m_RepaintRect = new Rect(0, 0, w, h);
/*		m_TestCompoment = new MyAbsoluteLayout(getContext());
		GameTBSystemButton testView = new GameTBSystemButton(getContext());//(ImageView)getContext().getResource().findViewById(R.id.TestButton);
		testView.setId(R.id.TestButton);
		testView.SetToolbarIndex(0);
    	MyAbsoluteLayout.LayoutParams lp = new MyAbsoluteLayout.LayoutParams (0, 0, 100, 100);
    	testView.setLayoutParams(lp);
    	m_TestCompoment.addView(testView);*/
	}
	
	public GameView(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		if(ResourceHelper.CanLoadResource() == false)
			ResourceHelper.SetResourceContext(context);
		
		InitializeMembers();
	}

	public GameView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		if(ResourceHelper.CanLoadResource() == false)
			ResourceHelper.SetResourceContext(context);
		
		InitializeMembers();
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		if(ResourceHelper.CanLoadResource() == false)
			ResourceHelper.SetResourceContext(context);
			
		InitializeMembers();
	}

	public void PostOnLayoutHandle()
	{
		int w = this.getWidth();
		int h = this.getHeight();
		m_RepaintRect = new Rect(0, 0, w, h);	

		invalidate();
	}	

	protected void onDraw(Canvas canvas)
	{
        canvas.drawRect(m_RepaintRect, ResourceHelper.GetRedTexturePaint());
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		// TODO Auto-generated method stub
		boolean bRet = true;
		if( SimpleGambleWheel.m_ApplicationController != null)
			bRet =  SimpleGambleWheel.m_ApplicationController.HandleTouchEvent(event);
		else
			bRet = super.onTouchEvent(event);
		return bRet;
	}
	
}
