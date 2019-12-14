package com.xgadget.XSGService;

import android.util.Log;
//import android.os.Build.VERSION_CODES;

public class XSGAPIReleaseConfigure 
{
	private static boolean 					m_bDebugMode = true;
	
	
	public static boolean IsDebugMode()
	{
		return m_bDebugMode;
	}
	
	private static final int	m_DeviceType = 0; //XSGAPIConstants.XSGAPIUSER_DEVICETYPE_ANDROID;
	private static final int	m_DeviceType2 = 1;// XSGAPIConstants.XSGAPIUSER_DEVICETYPE_KINDLEFIRE;
	private static final int	m_DeviceType3 = 2;// XSGAPIConstants.XSGAPIUSER_DEVICETYPE_BLACKBERRY;

	public static int GetCurrentDeviceType()
	{
		return m_DeviceType;
	}
	
	public static void LogDebugInfo(String key, String msg)
	{
		if(m_bDebugMode == true)
		{
			Log.w(key, msg);
		}
	}
	
	public static int GetCurrentOSVersion()
	{
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		return currentapiVersion;
	}
	
	public static boolean SupportAndroidFaceDetection()
	{
		boolean bRet = false;
		
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if(android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH <= currentapiVersion)
		{
			bRet = true;
		}
		return bRet;
	}
}
