package com.xgadget.facegesture;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

public class CXFGCameraHelper 
{
	public static boolean CheckCameraHardware(Context context) 
	{
	    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
	    {
	        // this device has a camera
	        return true;
	    } 
	    else 
	    {
	        // no camera on this device
	        return false;
	    }
	}

	public static int GetCameraNumber() 
	{
		int nRet = Camera.getNumberOfCameras();
		return nRet;
	}
	
	public static int GetFrontCameraID()
	{
		int nIndex = -1;
		int nNumber = CXFGCameraHelper.GetCameraNumber();
		if(0 < nNumber)
		{
			Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
			for(int i = 0; i < nNumber; ++i)
			{
				Camera.getCameraInfo(i, cameraInfo );
		        if ( cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT  ) 
		        {
		        	return i;
		        }				
			}
		}
		
		return nIndex;
	}

	public static Camera GetFrontCamera()
	{
	    int cameraCount = 0;
	    Camera cam = null;
	    Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
	    cameraCount = Camera.getNumberOfCameras();
	    for ( int camIdx = 0; camIdx < cameraCount; camIdx++ ) 
	    {
	        Camera.getCameraInfo( camIdx, cameraInfo );
	        if ( cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT  ) 
	        {
	            try 
	            {
	                cam = Camera.open( camIdx );
	            } 
	            catch (RuntimeException e) 
	            {
	            	cam = null;
	            }
	            return cam;
	        }
	    }

	    return cam;	
	 }
}
