package com.xgadget.XSGService;

import android.content.Context;

public class XSGAPIConstants 
{
	public static final String CURRENT_APP_ID = "com_xgadget_SimpleGambleWheel";

	public static final String XGADGET_ACCESS_KEY_ID = "AKIAIWA36AM3MVUZB3DA";
	public static final String XGADGET_SECRET_KEY = "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";

	public static final int XSGAPIUSER_DEVICETYPE_IOS_NONGK = 0;        //for iOS non Game Center user
	public static final int XSGAPIUSER_DEVICETYPE_IOS_GK = 1;        //for iOS Game Center user
	public static final int XSGAPIUSER_DEVICETYPE_ANDROID = 2;        //for General Android user
	public static final int XSGAPIUSER_DEVICETYPE_KINDLEFIRE = 3;        //for Kindle Fire user
	public static final int XSGAPIUSER_DEVICETYPE_WP = 4;        //for Windows Phone user
	public static final int XSGAPIUSER_DEVICETYPE_XBOX = 5;        //for XBox user
	public static final int XSGAPIUSER_DEVICETYPE_WINDOWS = 6;        //for Windows user
	public static final int XSGAPIUSER_DEVICETYPE_WEB = 7;        //for Web user

	public static final int XSGAPIUSER_DEVICETYPE_DEFAULT = XSGAPIUSER_DEVICETYPE_KINDLEFIRE;

	public static final int XSGAPIGAMEMANAGER_STATE_UNKNOWN = -1;
	public static final int XSGAPIGAMEMANAGER_STATE_IDLE =  0;
	public static final int XSGAPIGAMEMANAGER_STATE_QUERYONLINEUSERLIST = 1;
	public static final int XSGAPIGAMEMANAGER_STATE_INVITATIONPROCESS = 2;
	public static final int XSGAPIGAMEMANAGER_STATE_GAMEPLAYING = 3;

	
	public static final int MAX_RECIEVED_MSG_COUNT = 10;
	public static final int MAX_MSG_TIMEOUT = 43200;
	public static final float MSG_TIMEER_SPEED = 0.4f;
	public static final int MSG_AUTOCLEANTIMEER_SPEED  = 60;
	public static final String XSGAPI_THREAD_ONLINE_MSG_TAG = "xsgapimessage";
	
	private static Context g_Context = null;
	
	public static void RegisterContext(Context context)
	{
		g_Context = context;
	}
	
	public static Context GetContext()
	{
		return g_Context;
	}
	
}
