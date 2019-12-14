package com.xgadget.SimpleGambleWheel;

import org.json.JSONException;
import org.json.JSONObject;

import com.xgadget.XSGService.XSGAPIReleaseConfigure;

public class GameMessage 
{
    public String                   _m_GameMessage;
    
    
    private JSONObject        		m_MessageStream;
	
	/**
	 * @return the _m_GameMessage
	 */
	public String get_m_GameMessage() 
	{
		return _m_GameMessage;
	}

	/**
	 * @param _m_GameMessage the _m_GameMessage to set
	 */
	public void set_m_GameMessage(String _m_GameMessage) 
	{
		this._m_GameMessage = _m_GameMessage;
	}

	public GameMessage() 
	{
		// TODO Auto-generated constructor stub
	    _m_GameMessage = null;
		m_MessageStream = new JSONObject();
	}

	public void Reset()
	{
		m_MessageStream = null;
		m_MessageStream = new JSONObject();
	}

	public void FormatMessage()
	{
		if(m_MessageStream == null)
		{
			XSGAPIReleaseConfigure.LogDebugInfo("GameMessage.FormatMessage() :", "No m_MessageStream object");
			return;
		}
		_m_GameMessage = m_MessageStream.toString();
	}

	public void AddMessage(String szKey, String szText)
	{
		if(m_MessageStream == null)
		{
			XSGAPIReleaseConfigure.LogDebugInfo("GameMessage.AddMessage(String, String) :", "No m_MessageStream object");
			return;
		}
		if(szKey == null || szKey.length() <= 0)
		{
			XSGAPIReleaseConfigure.LogDebugInfo("GameMessage.AddMessage(String, String) :", "Key is not valid");
			return;
		}
		if(szText == null || szText.length() <= 0)
		{
			XSGAPIReleaseConfigure.LogDebugInfo("GameMessage.AddMessage(String, String) :", "szText is not valid");
			return;
		}
		try
		{
			m_MessageStream.put(szKey, szText);
		}
		catch (JSONException e) 
        {
        	XSGAPIReleaseConfigure.LogDebugInfo("GameMessage.AddMessage(String, String) :", e.getMessage());
        }
	}

	public void AddMessage(String szKey, int number)
	{
		if(m_MessageStream == null)
		{
			XSGAPIReleaseConfigure.LogDebugInfo("GameMessage.AddMessage(String, int) :", "No m_MessageStream object");
			return;
		}
		if(szKey == null || szKey.length() <= 0)
		{
			XSGAPIReleaseConfigure.LogDebugInfo("GameMessage.AddMessage(String, int) :", "Key is not valid");
			return;
		}
		try
		{
			m_MessageStream.put(szKey, number);
		}
		catch (JSONException e) 
        {
        	XSGAPIReleaseConfigure.LogDebugInfo("GameMessage.AddMessage(String, int) :", e.getMessage());
        }
	}

	public void AddMessage(String szKey, float number)
	{
		if(m_MessageStream == null)
		{
			XSGAPIReleaseConfigure.LogDebugInfo("GameMessage.AddMessage(String, float) :", "No m_MessageStream object");
			return;
		}
		if(szKey == null || szKey.length() <= 0)
		{
			XSGAPIReleaseConfigure.LogDebugInfo("GameMessage.AddMessage(String, float) :", "Key is not valid");
			return;
		}
		try
		{
			m_MessageStream.put(szKey, number);
		}
		catch (JSONException e) 
        {
        	XSGAPIReleaseConfigure.LogDebugInfo("GameMessage.AddMessage(String, float) :", e.getMessage());
        }
	}

	public void AddMessage(String szKey, boolean number)
	{
		if(m_MessageStream == null)
		{
			XSGAPIReleaseConfigure.LogDebugInfo("GameMessage.AddMessage(String, boolean) :", "No m_MessageStream object");
			return;
		}
		if(szKey == null || szKey.length() <= 0)
		{
			XSGAPIReleaseConfigure.LogDebugInfo("GameMessage.AddMessage(String, boolean) :", "Key is not valid");
			return;
		}
		try
		{
			int nValue = 1;
			if(number == false)
				nValue = 0;
			m_MessageStream.put(szKey, nValue);
		}
		catch (JSONException e) 
        {
        	XSGAPIReleaseConfigure.LogDebugInfo("GameMessage.AddMessage(String, boolean) :", e.getMessage());
        }
	}
	
/*	
	public void AddMessage(String szKey, List<?> Array)
	{
//	    [m_MessageStream setObject:Array forKey:szKey];
	}

	public void AddMessage(String szKey, JSONObject dict)
	{
	    [m_MessageStream setObject:dict forKey:szKey];
	}
*/
	
}
