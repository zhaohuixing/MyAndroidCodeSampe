/**
 * 
 */
package com.xgadget.ChuiNiuLite;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

/**
 * @author zhaohuixing
 *
 */
public class CClockObject 
{
	private float			m_fRadius;
	public int				m_nTimerStep;
	public int				m_nGameTimeLength;
	private PointF 			m_Center;
	private Rect 			m_BoundInDevice;
	private Rect 			m_InnerBoundInDevice;
	private PointF 			m_CenterInDevice;
	//private GradientDrawable m_OutterPaint;
	//private GradientDrawable m_BkgndPaint;
	//private Paint 			  m_OverlayPaint;  
    private Paint   		m_TextPaint;
    private String   		m_Text;
	
	//CGImageRef		m_Label;
	//NSString*		m_szLabel;
	public CClockObject()
	{
		m_fRadius = CGameLayout.GetClockRadius();
		m_nTimerStep = 0;
		m_nGameTimeLength = CConfiguration.getGameTime();
		m_Center = new PointF();
		m_Center.set(CGameLayout.GetGameSceneWidth()*0.5f-(m_fRadius+4), CGameLayout.GetGameSceneHeight()-(m_fRadius+4));
		m_BoundInDevice = new Rect();
		m_InnerBoundInDevice = new Rect();
		m_CenterInDevice = new PointF();
/*		m_OutterPaint = new GradientDrawable(GradientDrawable.Orientation.TL_BR,
                new int[] { 0xFFFF1010, 0xFF0B0B0B, 0xFFA1A1EE });

		m_OutterPaint.setShape(GradientDrawable.OVAL);
		m_OutterPaint.setGradientRadius(m_fRadius);
		
		m_BkgndPaint = new GradientDrawable(GradientDrawable.Orientation.TL_BR,
                new int[] { 0xFFABABAB, 0xFFE0F010});

		m_BkgndPaint.setShape(GradientDrawable.OVAL);
		m_BkgndPaint.setGradientRadius(m_fRadius-10.0f);
		m_OverlayPaint = new Paint();  
		m_OverlayPaint.setAntiAlias(true);
		m_OverlayPaint.setStyle(Paint.Style.FILL);
		m_OverlayPaint.setARGB(255, 255, 64, 64);*/
		m_TextPaint = new Paint();
		m_TextPaint.setAntiAlias(true);
		m_TextPaint.setStrokeWidth(5*CGameLayout.GetDensity());
		m_TextPaint.setStrokeCap(Paint.Cap.ROUND);
		m_TextPaint.setTextSize(16*CGameLayout.GetDensity());
		m_TextPaint.setTypeface(Typeface.create(Typeface.SERIF,Typeface.ITALIC));
		m_TextPaint.setARGB(255, 32, 32, 32);
		m_Text = new String();
	}
	
	public void UpdateLayout()
	{
		float fSize = CGameLayout.ObjectMeasureToDevice(m_fRadius);
		float fSize2 = CGameLayout.ObjectMeasureToDevice(m_fRadius-10.0f);
		Rect rect = CGameLayout.GetGameSceneDeviceRect();
		m_CenterInDevice.x = CGameLayout.GetDeviceScreenWidth()-(fSize+10.0f);//CGameLayout.GameSceneToDeviceX(m_Center.x);
		m_CenterInDevice.y = (float)rect.top + fSize+10.0f;//CGameLayout.GameSceneToDeviceY(m_Center.y);
		
		m_BoundInDevice.left = (int)(m_CenterInDevice.x-fSize);
		m_BoundInDevice.right = (int)(m_CenterInDevice.x+fSize);
		m_BoundInDevice.top = (int)(m_CenterInDevice.y-fSize);
		m_BoundInDevice.bottom = (int)(m_CenterInDevice.y+fSize);
		m_InnerBoundInDevice.left = (int)(m_CenterInDevice.x-fSize2);
		m_InnerBoundInDevice.right = (int)(m_CenterInDevice.x+fSize2);
		m_InnerBoundInDevice.top = (int)(m_CenterInDevice.y-fSize2);
		m_InnerBoundInDevice.bottom = (int)(m_CenterInDevice.y+fSize2);
		
		m_Text = CConfiguration.GetGameSettingString();
	}

	public void Reset()
	{
		m_nTimerStep = 0;
		m_nGameTimeLength = CConfiguration.getGameTime();
		m_Text = CConfiguration.GetGameSettingString();
	}

	private void DrawBackground(Canvas canvas)
	{
//		float fSize = CGameLayout.ObjectMeasureToDevice(m_fRadius);
		float fSize2 = CGameLayout.ObjectMeasureToDevice(m_fRadius-10.0f);
        canvas.save();
        //canvas.translate(m_CenterInDevice.x, m_CenterInDevice.y);
/*        m_OutterPaint.setBounds(m_BoundInDevice);
        m_OutterPaint.setGradientType(GradientDrawable.RADIAL_GRADIENT);
		m_OutterPaint.setGradientRadius(fSize);
        m_OutterPaint.draw(canvas);
        m_BkgndPaint.setBounds(m_InnerBoundInDevice);
        m_BkgndPaint.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        m_BkgndPaint.setGradientRadius(fSize2);
        m_BkgndPaint.draw(canvas);
        
        
		float angle; 
		RectF rt = new RectF();
		rt.left = m_CenterInDevice.x-fSize2;
		rt.right = m_CenterInDevice.x+fSize2;
		rt.top = m_CenterInDevice.y-fSize2;
		rt.bottom = m_CenterInDevice.y+fSize2;
		angle = 360.0f*((float)m_nTimerStep)/((float)m_nGameTimeLength);
        canvas.drawArc(rt, -90.0f, angle, true, m_OverlayPaint);
*/
		float angle; 
		RectF rt = new RectF();
		rt.left = m_CenterInDevice.x-fSize2;
		rt.right = m_CenterInDevice.x+fSize2;
		rt.top = m_CenterInDevice.y-fSize2;
		rt.bottom = m_CenterInDevice.y+fSize2;
		angle = 360.0f*((float)m_nTimerStep)/((float)m_nGameTimeLength);

		Drawable clockimage = CImageLoader.GetClockImage();
//		clockimage.setBounds(m_BoundInDevice);
		clockimage.setBounds(m_InnerBoundInDevice);
		canvas.save();
		canvas.rotate(angle, m_CenterInDevice.x, m_CenterInDevice.y);
		clockimage.draw(canvas);
        canvas.restore();
		
		
        float x = m_BoundInDevice.left+(m_BoundInDevice.width()/2 - 16*CGameLayout.GetDensity());
        float y = m_BoundInDevice.bottom+10.0f*CGameLayout.GetDensity();
        canvas.drawText(m_Text, x, y, m_TextPaint);
		
	}
	
	public void Draw(Canvas canvas)
	{
		DrawBackground(canvas);
	}
	
	public boolean OnTimerEvent()
	{
		boolean bRet = false;
		
		if(CGameScene.IsGameStatePlay() == true)
		{
			++m_nTimerStep;
			if(m_nTimerStep%CConfiguration.GAME_TIMER_DEFAULT_CLOCK_UPDATE == 0)
			{
				bRet = true;
			}	
		}	
		
		return bRet;
	}
	
}
