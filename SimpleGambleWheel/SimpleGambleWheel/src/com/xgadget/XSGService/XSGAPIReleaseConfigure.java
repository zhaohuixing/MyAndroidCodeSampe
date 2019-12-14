package com.xgadget.XSGService;

import android.util.Log;

public class XSGAPIReleaseConfigure 
{
	private static boolean 					m_bDebugMode = false;//true;
	
	
	public static boolean IsDebugMode()
	{
		return m_bDebugMode;
	}
	
	private static final int	m_DeviceType = XSGAPIConstants.XSGAPIUSER_DEVICETYPE_ANDROID;
	private static final int	m_DeviceType2 = XSGAPIConstants.XSGAPIUSER_DEVICETYPE_KINDLEFIRE;

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
}
