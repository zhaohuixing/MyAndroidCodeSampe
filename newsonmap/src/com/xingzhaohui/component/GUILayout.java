package com.xingzhaohui.component;

public class GUILayout 
{
	public static int g_ScreenWidth = 320;
	public static int g_ScreenHeight = 480;

	public static void SetScreenSize(int w, int h)
	{
		g_ScreenWidth = w;
		g_ScreenHeight = h;
	}

	public static int GetScreenWidth()
	{
		return g_ScreenWidth;
	}

	public static int GetScreenHeight()
	{
		return g_ScreenHeight;
	}
	
}
