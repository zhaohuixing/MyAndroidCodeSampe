package com.e_gadget.MindFire;

public class CGameDebugger 
{
	public static final int COMPILER_RELEASE = 0;
	public static final int COMPILER_DEBUG = 1;
	
	public static int COMPILER_MODE = COMPILER_RELEASE;
	
	public static boolean IsDebugMode()
	{
		boolean bRet = (COMPILER_MODE == COMPILER_DEBUG);
		return bRet;
	}

	public static boolean IsReleaseMode()
	{
		boolean bRet = (COMPILER_MODE == COMPILER_RELEASE);
		return bRet;
	}

	public static void SetDebugMode()
	{
		COMPILER_MODE = COMPILER_DEBUG;
	}

	public static void SetReleaseMode()
	{
		COMPILER_MODE = COMPILER_RELEASE;
	}
}
