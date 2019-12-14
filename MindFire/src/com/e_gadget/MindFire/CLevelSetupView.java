package com.e_gadget.MindFire;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class CLevelSetupView  extends View 
{
	private static final String MF24_POINTPREF = "Key_MindFire24_Point_Preference";
	private static final String MF24_POINTKEY = "Key_MindFire24_Current_Point";
	private final Context m_Context;
	
	private Context GetContext()
	{
		return m_Context;
	}
	
	public CLevelSetupView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		m_Context = context; 
		final RadioButton radio_24 = (RadioButton) findViewById(R.id.easy_24);
		final RadioButton radio_21 = (RadioButton) findViewById(R.id.medium_21);
		final RadioButton radio_27 = (RadioButton) findViewById(R.id.difficulty_27);
		final RadioButton radio_18 = (RadioButton) findViewById(R.id.difficulty_18);
		final RadioButton radio_misc = (RadioButton) findViewById(R.id.difficulty_misc);
		radio_24.setOnClickListener(radio24_listener);
		radio_21.setOnClickListener(radio21_listener);
		radio_27.setOnClickListener(radio27_listener);
		radio_18.setOnClickListener(radio18_listener);
		radio_misc.setOnClickListener(radiomisc_listener);
		
        Button confirmButton = (Button) findViewById(R.id.confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() 
        {
        	public void onClick(View view) 
        	{
        		updateLevel();
        	}
        });	
		
        loadLevel();
		
	}
	
	private int  m_nLevel;
	
	OnClickListener radio24_listener = new OnClickListener() 
	{
		public void onClick(View v) 
		{
			// Perform action on clicks
			setLevel(24);
		}
	};

	OnClickListener radio21_listener = new OnClickListener() 
	{
		public void onClick(View v) 
		{
			// Perform action on clicks
			setLevel(21);
		}
	};

	OnClickListener radio27_listener = new OnClickListener() 
	{
		public void onClick(View v) 
		{
			// Perform action on clicks
			setLevel(27);
		}
	};
	
	OnClickListener radio18_listener = new OnClickListener() 
	{
		public void onClick(View v) 
		{
			// Perform action on clicks
			setLevel(18);
		}
	};

	OnClickListener radiomisc_listener = new OnClickListener() 
	{
		public void onClick(View v) 
		{
			// Perform action on clicks
		}
	};
	
    public void setLevel(int points)
    {
    	m_nLevel = points;
    }
    
    public int getLevel()
    {
		return m_nLevel;
    } 
     
    public void updateLevel()
    {
    	Context context = GetContext();
    	if(context != null)
    	{	
    		SharedPreferences prefs = context.getSharedPreferences(MF24_POINTPREF, 0);
    		if(prefs != null)
    		{	
    			
    	        SharedPreferences.Editor editor = prefs.edit();
    	        editor.putInt(MF24_POINTKEY, m_nLevel);
    	        editor.commit();
    		}	
    	}
    }

    public void loadLevel()
    {
    	m_nLevel = CGameType.GetPoints();

    	Context context = GetContext();
    	if(context != null)
    	{	
    		SharedPreferences prefs = context.getSharedPreferences(MF24_POINTPREF, 0);
    		if(prefs != null)
    		{	
    			m_nLevel = prefs.getInt(MF24_POINTKEY, m_nLevel);
    			setCtrlSelected();
    		}	
    	}
    }

	private void setCtrlSelected()
	{
		if(m_nLevel == CGameType.GAME_POINT24_ANSWER)
		{
			final RadioButton radio_24 = (RadioButton) findViewById(R.id.easy_24);
			radio_24.setChecked(true);
		}
		else if(m_nLevel == CGameType.GAME_POINT18_ANSWER)
		{
			final RadioButton radio_18 = (RadioButton) findViewById(R.id.difficulty_18);
			radio_18.setChecked(true);
		}
		else if(m_nLevel == CGameType.GAME_POINT27_ANSWER)
		{
			final RadioButton radio_27 = (RadioButton) findViewById(R.id.difficulty_27);
			radio_27.setChecked(true);
		}
		else if(m_nLevel == CGameType.GAME_POINT21_ANSWER)
		{
			final RadioButton radio_21 = (RadioButton) findViewById(R.id.medium_21);
			radio_21.setChecked(true);
		}
	}
	
}
