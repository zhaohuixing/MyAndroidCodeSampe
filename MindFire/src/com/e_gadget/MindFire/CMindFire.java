package com.e_gadget.MindFire;

import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class CMindFire extends Activity implements OnSharedPreferenceChangeListener
{
	private static final int MENU_LEVEL = 1;
	private static final int MENU_SCORE = 2;
	private static final int MENU_MISC = 3;
	private static final int MENU_NEWGAME = 4;
	private static final int MENU_HELP = 5;
	private static final int MENU_EXIT = 6;
	
	private static final String MF24_KEY = "Key_MindFire24_Restore";
	private static final String MF24_POINTPREF = "Key_MindFire24_Point_Preference";
	private static final String MF24_POINTKEY = "Key_MindFire24_Current_Point";
	private static final int MF24_INVALID_POINT = 0;
	private static final int MF24_ACTIVITY_LEVEL = 1;
	private static final int MF24_ACTIVITY_SCORE = 2;
	private static CMindFire  	m_activeApp;
	private static int  		m_GameScore;
	
	public static void SetActiveApp(CMindFire app)
	{
		m_activeApp = app;
	}

	public static void SetGameScore(int nScore)
	{
		m_GameScore = nScore;
	}
	
	/* The game view to host the game thread. */
	private CGameView m_GameView;
	
	public CMindFire()
	{
		super();
	}

    private void initializeGamePoints(Context context)
    {
    	if(context != null)
    	{	
    		SharedPreferences prefs = context.getSharedPreferences(MF24_POINTPREF, 0);
    		if(prefs != null)
    		{	
    			//String prefix = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
    			int nPoint = prefs.getInt(MF24_POINTKEY, MF24_INVALID_POINT);
    			if(MF24_INVALID_POINT < nPoint)
    			{
    				CGameType.SetPoints(nPoint);
    			}
    			else
    			{
    				CGameType.SetPoints(CGameType.GAME_POINT24_ANSWER);
    			}
    		}	
    	}
    }
	
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) 
    {
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, 
                                    Intent intent) 
    {
        super.onActivityResult(requestCode, resultCode, intent);
		CGameState.SetGameActivityState();
		if(requestCode == MF24_ACTIVITY_LEVEL)
		{	
			Context context = CMindFire.this;
			if(context != null)
			{	
				SharedPreferences prefs = context.getSharedPreferences(MF24_POINTPREF, 0);
				int nPoint = prefs.getInt(MF24_POINTKEY, CGameType.GAME_POINT24_ANSWER);
				if(nPoint != CGameType.GetPoints())
				{
					CGameType.SetPoints(nPoint);
					NewGame();
				}
			}				
		}
    }
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        initializeGamePoints(CMindFire.this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		CGameState.SetGameActivityState();
        setContentView(R.layout.main);
		m_GameView = (CGameView) findViewById(R.id.game);
		
		m_GameView.AttachApplication(this);
		
		// give the LunarView a handle to the TextView used for messages
		m_GameView.setStatusText((TextView) findViewById(R.id.text));
		if(savedInstanceState != null)
		{	
            Bundle map = savedInstanceState.getBundle(MF24_KEY);
            //if (map != null) 
            //{
            m_GameView.SetSavedInstanceState(map);
            //}
		}			
		
		CMindFire.SetActiveApp(CMindFire.this);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		super.onCreateOptionsMenu(menu);
		menu.add(1, MENU_LEVEL, 0, R.string.menu_level);
		menu.add(2, MENU_SCORE, 0, R.string.menu_score);
		SubMenu menuMisc = menu.addSubMenu(4, MENU_MISC, 3, R.string.menu_misc);
		menuMisc.add(1, MENU_NEWGAME, 2, R.string.menu_new);
		menuMisc.add(2, MENU_HELP, 2, R.string.menu_help);
		menuMisc.add(3, MENU_EXIT, 2, R.string.menu_exit);
	
		return true;
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
			case MENU_LEVEL:
				SetLevel();
				return true;
			case MENU_SCORE:
				ShowScore();
				return true;
			case MENU_EXIT:
				ExitGame();
				return true;
			case MENU_HELP:
				ShowHelpText();
				return true;
			case MENU_NEWGAME:
				NewGame();
				return true;
		}

		return false;
	}
	
	private void NewGame()
	{
		if(m_GameView != null)
			m_GameView.NewGame();
	}
	
	public void ExitGame()
	{
		ShutdownGameThread();
		this.finish();
	}

	private void ShutdownGameThread()
	{
		//if(m_GameView != null)
		//	m_GameView.KillThread();
	}

	private void ShowWarnMessage()
	{
		if(m_GameView != null)
		{
			m_GameView.ShowWarnMessage();//ShowTextMessage(R.string.text_warn);
		}
	}
	
	private void ShowHelpText()
	{
		if(m_GameView != null)
		{
			m_GameView.ShowHelpText();//ShowTextMessage(R.string.text_warn);
		}
	}
	
    @Override
    protected void onSaveInstanceState(Bundle outState) 
    {
        // just have the View's thread save its state into our Bundle
		if(m_GameView != null)
		{
	        Bundle map = new Bundle();
			m_GameView.SaveInstanceState(map);
			outState.putBundle(MF24_KEY, map); 
		}
        //super.onSaveInstanceState(outState);
    }
    
	private void SetLevel()
	{
		CGameState.SetInfoActivityState();
        Intent i = new Intent(this, CLevelSetup.class);
        startActivityForResult(i, MF24_ACTIVITY_LEVEL);
	}
	
	private void ShowScore()
	{
		//CGameState.SetInfoActivityState();
		CGameState.SetInfoActivityState();
        Intent i = new Intent(this, CScorePanel.class);
        startActivityForResult(i, MF24_ACTIVITY_SCORE);
	}
 
	public void SaveGameScore(int nScore)
	{
		CMindFire.SetGameScore(nScore);
		
       	Context context = this;
       	Resources res = context.getResources();
		String strMsg = res.getText(R.string.saveconfirmation).toString();
		String strOK = res.getText(R.string.confirm).toString();
		String strCancel = res.getText(R.string.cancel).toString();
		            
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(strMsg)
			   .setCancelable(true)
		       .setPositiveButton(strOK, new DialogInterface.OnClickListener() 
		       {
		             public void onClick(DialogInterface dialog, int id) 
		             {
		            	 CMindFire.DoAcitveGameScoreSave();
		             }
		        })
		        .setNegativeButton(strCancel, new DialogInterface.OnClickListener()
			    {
		             public void onClick(DialogInterface dialog, int id) 
		             {
		             }
		        });
        AlertDialog alert = builder.create();
        alert.show();
	}

	public static void DoAcitveGameScoreSave()
	{
		if(m_activeApp != null)
		{	
			m_activeApp.DoSaveGameScore(m_GameScore);
		}			
	}
	
	private void DoSaveGameScore(int nScore)
	{
        Context context = this;
        Vector<CGameScore> scores = new Vector<CGameScore>();
        CScoreFactory.LoadScores(context, scores);
        CScoreFactory.UpdateScoresList(scores, CGameType.GetPoints(), nScore);
        CScoreFactory.SaveScores(context, scores);
	}
}