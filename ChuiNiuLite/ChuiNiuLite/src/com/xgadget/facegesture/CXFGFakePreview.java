package com.xgadget.facegesture;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.graphics.ImageFormat;

public class CXFGFakePreview extends SurfaceView implements Callback 
{
    SurfaceHolder 				m_Holder;
    Camera 						m_Camera;
    Camera.PreviewCallback 		m_PreviewCallback;
    boolean						m_bCameraSetCallback;
    boolean						m_bCameraStartPreview;
    public int							m_CameraParameterWidth;
    public int							m_CameraParameterHeight;
    

	public CXFGFakePreview(Context context, Camera.PreviewCallback previewCallback) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		m_PreviewCallback = previewCallback;
		m_Camera = null;
        m_Holder = getHolder();
        m_Holder.addCallback(this);
        m_Holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        m_bCameraSetCallback = false;
        m_bCameraStartPreview = false;
	}
	
	public CXFGFakePreview(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		Initialize();
	}
	
	public CXFGFakePreview(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize();
	}


	public CXFGFakePreview(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize();
	}
	
	private void Initialize()
	{
		m_PreviewCallback = null;
		m_Camera = null;
        m_Holder = getHolder();
        m_Holder.addCallback(this);
        m_Holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        m_bCameraSetCallback = false;
        m_bCameraStartPreview = false;
        m_CameraParameterWidth = 0;
        m_CameraParameterHeight = 0;
	}
	
	public void StopFacialGesture()
	{
        m_bCameraSetCallback = false;
        m_bCameraStartPreview = false;
        if(m_Camera != null)
        {
        	m_Camera.stopPreview();
            m_Camera.release();
            m_Camera = null;
        }
        if(m_PreviewCallback != null)
        {
        	m_PreviewCallback = null;
        }
	}
	
	public boolean StartFacialGesture(Camera camera, Camera.PreviewCallback previewCallback)
	{
        m_Camera = camera;
		m_PreviewCallback = previewCallback;
	
        try 
        {
            m_Camera.setPreviewDisplay(m_Holder);
        } 
        catch (IOException exception) 
        {
            m_Camera.release();
            m_Camera = null;
            // TODO: add more exception handling logic here
            m_bCameraSetCallback = false;
            m_bCameraStartPreview = false;
            return false;
        }
        m_bCameraSetCallback = true;
		
		boolean bRet = StartCameraCapture();
		return bRet;
	}
	
	public void RegisterCamera(Camera camera)
	{
		m_Camera = camera;
	}
	
	private boolean StartCameraCapture()
	{
		if(m_Camera == null || m_bCameraSetCallback == false || m_bCameraStartPreview == true)
			return false;
        
		Camera.Parameters parameters = m_Camera.getParameters();

        List<Size> sizes = parameters.getSupportedPreviewSizes();
        Size optimalSize = GetMinmumPreviewSize(sizes);//getOptimalPreviewSize(sizes, width, height);
        parameters.setPreviewSize(optimalSize.width, optimalSize.height);
        m_CameraParameterWidth = optimalSize.width;
        m_CameraParameterHeight = optimalSize.height;

        m_Camera.setParameters(parameters);
        if (m_PreviewCallback != null) 
        {
            m_Camera.setPreviewCallbackWithBuffer(m_PreviewCallback);
            Camera.Size size = parameters.getPreviewSize();
            byte[] data = new byte[size.width*size.height*ImageFormat.getBitsPerPixel(parameters.getPreviewFormat())/8];
            m_Camera.addCallbackBuffer(data);
        }
        m_bCameraStartPreview = true;
        m_Camera.startPreview();
        return true;
	}
	
	

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) 
	{
		if(m_Camera == null || m_bCameraSetCallback == false || m_bCameraStartPreview == true)
			return;
		
		// TODO Auto-generated method stub
        Camera.Parameters parameters = m_Camera.getParameters();

        List<Size> sizes = parameters.getSupportedPreviewSizes();
        Size optimalSize = GetMinmumPreviewSize(sizes);//getOptimalPreviewSize(sizes, width, height);
        parameters.setPreviewSize(optimalSize.width, optimalSize.height);
        m_CameraParameterWidth = optimalSize.width;
        m_CameraParameterHeight = optimalSize.height;

        m_Camera.setParameters(parameters);
        if (m_PreviewCallback != null) 
        {
            m_Camera.setPreviewCallbackWithBuffer(m_PreviewCallback);
            Camera.Size size = parameters.getPreviewSize();
            byte[] data = new byte[size.width*size.height*ImageFormat.getBitsPerPixel(parameters.getPreviewFormat())/8];
            m_Camera.addCallbackBuffer(data);
        }
        m_bCameraStartPreview = true;
        m_Camera.startPreview();

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) 
	{
		if(m_Camera == null || m_PreviewCallback == null || m_bCameraSetCallback == true)
			return;
		// TODO Auto-generated method stub
        try 
        {
            m_Camera.setPreviewDisplay(holder);
        } 
        catch (IOException exception) 
        {
            m_Camera.release();
            m_Camera = null;
            // TODO: add more exception handling logic here
            m_bCameraSetCallback = false;
            return;
        }
        m_bCameraSetCallback = true;

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) 
	{
		StopFacialGesture();	
	}
/*
    private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) 
    {
        final double ASPECT_TOLERANCE = 0.05;
        double targetRatio = (double) w / h;
        if (sizes == null) 
        	return null;

        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Size size : sizes) 
        {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) 
            	continue;
            if (Math.abs(size.height - targetHeight) < minDiff) 
            {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) 
        {
            minDiff = Double.MAX_VALUE;
            for (Size size : sizes) 
            {
                if (Math.abs(size.height - targetHeight) < minDiff) 
                {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }*/

    public static Size GetMinmumPreviewSize(List<Size> sizes) 
    {
        Size optimalSize = null;
        int nMinSize = -1;

        // Try to find an size match aspect ratio and size
        for (Size size : sizes) 
        {
            int nSize = size.width * size.height;
            if (nMinSize == -1 || nSize < nMinSize) 
            {
            	nMinSize = nSize;
                optimalSize = size;
            }
        }

        return optimalSize;
    }

    public static Size GetMaxmumPreviewSize(List<Size> sizes) 
    {
        Size optimalSize = null;
        int nMaxSize = -1;

        // Try to find an size match aspect ratio and size
        for (Size size : sizes) 
        {
            int nSize = size.width * size.height;
            if (nMaxSize == -1 || nMaxSize < nSize) 
            {
            	nMaxSize = nSize;
                optimalSize = size;
            }
        }

        return optimalSize;
    }
    
}
