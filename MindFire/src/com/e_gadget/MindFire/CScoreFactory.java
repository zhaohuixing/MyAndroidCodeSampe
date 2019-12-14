package com.e_gadget.MindFire;

import java.util.Vector;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

public class CScoreFactory 
{
	private static final String MF24_SCOREPREF = "Key_MindFire24_Score_Preference";
	private static final String MF24_SCORECOUNT = "Key_MindFire24_Score_Count";
	
	public static final String MF24_SCOREPOINT_PREF = "Key_MF24S_POINT_";
	
	public static final String MF24_SCORELASTSCORE_I_PREF = "Key_MF24S_LASTSCORE_I_";
	
	public static final String MF24_SCORELASTSCORE_P_PREF = "Key_MF24S_LASTSCORE_P_";
	
	public static final String MF24_SCOREHIGHSCORE_P_PREF = "Key_MF24S_HIGHSCORE_P_";

	public static final String MF24_SCOREAVESCORE_P_PREF = "Key_MF24S_AVESCORE_P_";

	public static final String MF24_SCOREPLAY_PREF = "Key_MF24S_PLAYS_";


	public static void UpdateScoresList(Vector<CGameScore> scores, int nPoint, int nScore)
	{
        int nCount = scores.size();
        boolean bAddNew = true;
        if(0 < nCount)
        {
			for(int i = 0; i < nCount; i++)
			{	
				CGameScore score = scores.elementAt(i);
				if(score != null && score.GetPoint() == nPoint)
				{
					scores.elementAt(i).UpdateScore(nScore);
					bAddNew = false;
					break;
				}
			}					
        }
        if(bAddNew == true)
        {
        	CGameScore score = new CGameScore(nPoint, nScore);
        	scores.add(score);
        }
	}
	
	public static void LoadScores(Context context, Vector<CGameScore> scores)
	{
		SharedPreferences prefs = context.getSharedPreferences(MF24_SCOREPREF, 0);
		if(prefs != null)
		{
			int nCount = prefs.getInt(MF24_SCORECOUNT, 0);
			if(0 < nCount)
			{
				for(int i = 0; i < nCount; i++)
				{	
					CGameScore score = LoadScoreData(prefs, i);
					if(score != null && score.IsValid() == true)
					{
						scores.add(score);
					}
				}					
			}
		}
	}
	
	public static CGameScore LoadScoreData(SharedPreferences prefs, int i)
	{
		CGameScore score = null;
		
		Integer n = i;
		String szIndex = n.toString();
		String szKey = MF24_SCOREPOINT_PREF+szIndex;
		int nPoint = prefs.getInt(szKey, -1);
			
		szKey = MF24_SCORELASTSCORE_I_PREF+szIndex;
		int nLast = prefs.getInt(szKey, -1);
			
		szKey = MF24_SCORELASTSCORE_P_PREF+szIndex;
		float fLast = prefs.getFloat(szKey, 0.0f);
			
		szKey = MF24_SCOREHIGHSCORE_P_PREF+szIndex;
		float fHigh = prefs.getFloat(szKey, 0.0f);
		
		szKey = MF24_SCOREAVESCORE_P_PREF+szIndex;
		float fAve = prefs.getFloat(szKey, 0.0f);
		
		szKey = MF24_SCOREPLAY_PREF+szIndex;
		int nPlay = prefs.getInt(szKey, -1);
		
		if(0 <= nPoint && nPoint <= 28561)
		{	
			score = new CGameScore(nPoint, nLast, fLast, fHigh, fAve, nPlay); 
		}
			
		return score;
	}

	public static void SaveScores(Context context, Vector<CGameScore> scores)
	{
		SharedPreferences prefs = context.getSharedPreferences(MF24_SCOREPREF, 0);
        SharedPreferences.Editor editor = prefs.edit();
        
        int nCount = scores.size();
        editor.putInt(MF24_SCORECOUNT, nCount);
		if(0 < nCount)
		{
			for(int i = 0; i < nCount; i++)
			{	
				CGameScore score = scores.elementAt(i);
				if(score != null && score.IsValid() == true)
				{
					SaveScore(editor, score, i);
				}
			}					
		}
        editor.commit();
	}
	
	public static void SaveScore(SharedPreferences.Editor editor , CGameScore score, int i)
	{
		Integer n = i;
		String szIndex = n.toString();
		String szKey = MF24_SCOREPOINT_PREF+szIndex;
		editor.putInt(szKey, score.GetPoint());
			
		szKey = MF24_SCORELASTSCORE_I_PREF+szIndex;
		editor.putInt(szKey, score.GetLastScore());
			
		szKey = MF24_SCORELASTSCORE_P_PREF+szIndex;
		editor.putFloat(szKey, score.GetLastScorePercent());
			
		szKey = MF24_SCOREHIGHSCORE_P_PREF+szIndex;
		editor.putFloat(szKey, score.GetHighScorePercent());
		
		szKey = MF24_SCOREAVESCORE_P_PREF+szIndex;
		editor.putFloat(szKey, score.GetAveScorePercent());
		
		szKey = MF24_SCOREPLAY_PREF+szIndex;
		editor.putInt(szKey, score.GetPlay());
	}
	
	public static String GetPointText(Context context)
	{
		String text;
		Resources res = context.getResources();
		text = res.getText(R.string.pointlabel).toString();
		return text;
	}

	public static String GetLatestText(Context context)
	{
		String text;
		Resources res = context.getResources();
		text = res.getText(R.string.latestlabel).toString();
		return text;
	}

	public static String GetLatestText2(Context context)
	{
		String text;
		Resources res = context.getResources();
		text = res.getText(R.string.latestlabel2).toString();
		return text;
	}
	
	public static String GetHighText(Context context)
	{
		String text;
		Resources res = context.getResources();
		text = res.getText(R.string.highlabel).toString();
		return text;
	}

	public static String GetAveText(Context context)
	{
		String text;
		Resources res = context.getResources();
		text = res.getText(R.string.avelabel).toString();
		return text;
	}

	public static String GetPlayText(Context context)
	{
		String text;
		Resources res = context.getResources();
		text = res.getText(R.string.playlabel).toString();
		return text;
	}
}
