package com.e_gadget.MindFire;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class CLevelSetup extends Activity 
{
	private static final String MF24_POINTPREF = "Key_MindFire24_Point_Preference";
	private static final String MF24_POINTKEY = "Key_MindFire24_Current_Point";
	
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
			if(m_nLevel == CGameType.GAME_POINT24_ANSWER || m_nLevel == CGameType.GAME_POINT18_ANSWER 
			|| m_nLevel == CGameType.GAME_POINT27_ANSWER || m_nLevel == CGameType.GAME_POINT21_ANSWER)
			{
				m_nLevel = CGameType.GAME_POINT30_ANSWER;
			}
			
			final EditText editNumber = (EditText) findViewById(R.id.editpoints);
			editNumber.setVisibility(View.VISIBLE);
	        Integer nLevel = m_nLevel;
	        String szLevel = nLevel.toString();
	        editNumber.setText(szLevel);
		}
	};
	
	public CLevelSetup()
	{
		super();
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_setup);
    
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

		final EditText editNumber = (EditText) findViewById(R.id.editpoints);
		editNumber.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        
		Button confirmButton = (Button) findViewById(R.id.confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() 
        {
        	public void onClick(View view) 
        	{
        		boolean bRet = updateLevel();
        		if(bRet)
        		{	
        			CLevelSetup.this.setResult(RESULT_OK);
        			CLevelSetup.this.finish();
        		}        			
        	}
        });	
		
        loadLevel();
    }
    
    public void setLevel(int points)
    {
    	m_nLevel = points;
		final EditText editNumber = (EditText) findViewById(R.id.editpoints);
		if(m_nLevel == CGameType.GAME_POINT24_ANSWER)
		{
			editNumber.setVisibility(View.INVISIBLE);
		}
		else if(m_nLevel == CGameType.GAME_POINT18_ANSWER)
		{
			editNumber.setVisibility(View.INVISIBLE);
		}
		else if(m_nLevel == CGameType.GAME_POINT27_ANSWER)
		{
			editNumber.setVisibility(View.INVISIBLE);
		}
		else if(m_nLevel == CGameType.GAME_POINT21_ANSWER)
		{
			editNumber.setVisibility(View.INVISIBLE);
		}
    }
    
    public int getLevel()
    {
		return m_nLevel;
    } 
     
    public boolean updateLevel()
    {
    	boolean bRet = true;
       	Context context = CLevelSetup.this;
           	
		if(m_nLevel != CGameType.GAME_POINT24_ANSWER && m_nLevel != CGameType.GAME_POINT18_ANSWER 
		&& m_nLevel != CGameType.GAME_POINT27_ANSWER && m_nLevel != CGameType.GAME_POINT21_ANSWER)
		{
			final EditText editNumber = (EditText) findViewById(R.id.editpoints);
			String szText = editNumber.getText().toString();
			boolean bOK = true;
			
            try 
            {
            	m_nLevel = Integer.parseInt(szText);				
            } 
            catch (NumberFormatException e) 
            {
            	bOK = false;
            }
			
			if(m_nLevel < 0 || 28561 < m_nLevel || bOK == false)
			{
				if(context != null)
				{
					Resources res = context.getResources();
		            String strMsg = res.getText(R.string.invalid_point).toString();
		            String strOK = res.getText(R.string.confirm).toString();
		            
		            AlertDialog.Builder builder = new AlertDialog.Builder(CLevelSetup.this);
		            builder.setMessage(strMsg)
		                   .setPositiveButton(strOK, new DialogInterface.OnClickListener() 
		                   {
		                       public void onClick(DialogInterface dialog, int id) 
		                       {
		                       }
		                   });
		            AlertDialog alert = builder.create();
		            alert.show();
				}
				
				return false;
			}	
		}
    	
    	
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
    	
    	return bRet;
    }

    public void loadLevel()
    {
    	m_nLevel = CGameType.GetPoints();

    	Context context = CLevelSetup.this;
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
		final EditText editNumber = (EditText) findViewById(R.id.editpoints);
		if(m_nLevel == CGameType.GAME_POINT24_ANSWER)
		{
			final RadioButton radio_24 = (RadioButton) findViewById(R.id.easy_24);
			radio_24.setChecked(true);
			editNumber.setVisibility(View.INVISIBLE);
		}
		else if(m_nLevel == CGameType.GAME_POINT18_ANSWER)
		{
			final RadioButton radio_18 = (RadioButton) findViewById(R.id.difficulty_18);
			radio_18.setChecked(true);
			editNumber.setVisibility(View.INVISIBLE);
		}
		else if(m_nLevel == CGameType.GAME_POINT27_ANSWER)
		{
			final RadioButton radio_27 = (RadioButton) findViewById(R.id.difficulty_27);
			radio_27.setChecked(true);
			editNumber.setVisibility(View.INVISIBLE);
		}
		else if(m_nLevel == CGameType.GAME_POINT21_ANSWER)
		{
			final RadioButton radio_21 = (RadioButton) findViewById(R.id.medium_21);
			radio_21.setChecked(true);
			editNumber.setVisibility(View.INVISIBLE);
		}
		else
		{
			final RadioButton radio_misc = (RadioButton) findViewById(R.id.difficulty_misc);
			radio_misc.setChecked(true);
			editNumber.setVisibility(View.VISIBLE);
	        Integer nLevel = m_nLevel;
	        String szLevel = nLevel.toString();
	        editNumber.setText(szLevel);
		}
	}
    
}