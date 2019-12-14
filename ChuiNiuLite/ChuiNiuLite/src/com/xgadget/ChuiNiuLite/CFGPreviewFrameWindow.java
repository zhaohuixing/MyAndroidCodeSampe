package com.xgadget.ChuiNiuLite;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;

import com.xgadget.facegesture.*;
import com.xgadget.uimodule.*;

public class CFGPreviewFrameWindow extends MyAbsoluteLayout 
{
	protected MyAbsoluteLayout							m_LayoutContainer;
	private CDumyGreenRoundRectView 					m_DumyBackground;
	private CXFGFakePreview								m_PreviewWindow;
	private CQuestionMarkOverlay						m_QMarkOverlay;
	
	public void Initialize()
	{
		m_LayoutContainer = null;
	}
	
	public int GetEdgeSize()
	{
		int nSize = 0;
		
		if(m_DumyBackground != null)
		{
			nSize = m_DumyBackground.GetEdgeSize();
		}
		
		return nSize;
	}

	public int GetCameraParameterWidth()
	{
		int nWidth = 0;
		
		if(m_PreviewWindow != null)
		{
			nWidth = m_PreviewWindow.m_CameraParameterWidth;
		}
		
		return nWidth;
	}

	public int GetCameraParameterHeight()
	{
		int nHeight = 0;
		
		if(m_PreviewWindow != null)
		{
			nHeight = m_PreviewWindow.m_CameraParameterHeight;
		}
		
		return nHeight;
	}
	
	public CFGPreviewFrameWindow(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize();
		m_DumyBackground = new CDumyGreenRoundRectView(context);
		this.addView(m_DumyBackground);
		m_PreviewWindow = new CXFGFakePreview(context);
		this.addView(m_PreviewWindow);
		m_QMarkOverlay = new CQuestionMarkOverlay(context);
		this.addView(m_QMarkOverlay);
	}

	public CFGPreviewFrameWindow(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize();
		m_DumyBackground = new CDumyGreenRoundRectView(context, attrs);
		this.addView(m_DumyBackground);
		m_PreviewWindow = new CXFGFakePreview(context, attrs);
		this.addView(m_PreviewWindow);
		m_QMarkOverlay = new CQuestionMarkOverlay(context, attrs);
		this.addView(m_QMarkOverlay);
	}

	public CFGPreviewFrameWindow(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize();
		m_DumyBackground = new CDumyGreenRoundRectView(context, attrs, defStyle);
		this.addView(m_DumyBackground);
		m_PreviewWindow = new CXFGFakePreview(context, attrs, defStyle);
		this.addView(m_PreviewWindow);
		m_QMarkOverlay = new CQuestionMarkOverlay(context, attrs, defStyle);
		this.addView(m_QMarkOverlay);
	}

	public void PostOnLayoutHandle()
	{
		int bw = this.getWidth();
		int bh = this.getHeight();
		int nsize = m_DumyBackground.GetEdgeSize();
		this.SetChildNewDemension(m_DumyBackground, bw, bh, 0, 0);
		m_DumyBackground.invalidate();
		this.SetChildNewDemension(m_PreviewWindow, bw-2*nsize, bh-2*nsize, nsize, nsize);
		this.SetChildNewDemension(m_QMarkOverlay, bw-2*nsize, bh-2*nsize, nsize, nsize);
		m_QMarkOverlay.invalidate();
	}

	/**
	 * @return the m_LayoutContainer
	 */
	public MyAbsoluteLayout getM_LayoutContainer() 
	{
		return m_LayoutContainer;
	}

	/**
	 * @param m_LayoutContainer the m_LayoutContainer to set
	 */
	public void setM_LayoutContainer(MyAbsoluteLayout m_LayoutContainer) 
	{
		this.m_LayoutContainer = m_LayoutContainer;
	}
	
	public void StopFacialGesture()
	{
		if(m_PreviewWindow != null)
		{
			m_PreviewWindow.StopFacialGesture();
		}
	}
	
	public boolean StartFacialGesture(Camera camera, Camera.PreviewCallback previewCallback)
	{
		boolean bRet = false;
		if(m_PreviewWindow != null)
		{
			bRet = m_PreviewWindow.StartFacialGesture(camera, previewCallback);
		}
		return bRet;
	}
	
	public void ForceRedraw()
	{
		if(m_PreviewWindow != null)
		{
			m_PreviewWindow.invalidate();
		}
		if(m_QMarkOverlay != null)
		{
			m_QMarkOverlay.invalidate();
		}
	}
}
