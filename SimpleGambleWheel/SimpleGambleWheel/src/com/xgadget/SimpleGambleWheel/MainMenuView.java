package com.xgadget.SimpleGambleWheel;

//import java.util.Random;
import com.xgadget.uimodule.*;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Canvas;
//import android.graphics.Paint;
import android.graphics.Rect;
//import android.graphics.Shader;

public class MainMenuView extends BasicLayoutView 
{
    Rect									m_RepaintRect;
    
    
	private void InitializeMembers()
	{
		int w = this.getWidth();
		int h = this.getHeight();
		m_RepaintRect = new Rect(0, 0, w, h);
	}
	
	public MainMenuView(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		if(ResourceHelper.CanLoadResource() == false)
			ResourceHelper.SetResourceContext(context);
		
		InitializeMembers();
	}

	public MainMenuView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		if(ResourceHelper.CanLoadResource() == false)
			ResourceHelper.SetResourceContext(context);
		
		InitializeMembers();
	}

	public MainMenuView(Context context, AttributeSet attrs, int defStyle) 
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
}
