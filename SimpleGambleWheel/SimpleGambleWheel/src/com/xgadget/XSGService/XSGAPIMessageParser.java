package com.xgadget.XSGService;
import org.json.*;

import android.util.Log;

public class XSGAPIMessageParser 
{
	public static int ParseMessageType(String msg)
	{
	    int nMsgType = XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVALID;
	    
	    if(msg != null && 0 < msg.length())
	    {
	    	try
	    	{
		    	JSONObject jsonReader  = new JSONObject(msg);
	    		nMsgType = jsonReader.getInt(XSGAPIMessage.XSGAPI_MESSAGE_TYPE_TAG);
	    	}
	        catch (JSONException e) 
	        {
	            Log.w("XSGAPIMessageParser.ParseMessageType:", e.getMessage());
	            nMsgType = XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVALID;
	        }
	    	
	    }
	    
	    return nMsgType;
	}

	public static XSGAPIUser ParseMessageSender(String msg)
	{
		XSGAPIUser pSender = null;

	    if(msg != null && 0 < msg.length())
	    {
	    	try
	    	{
		    	JSONObject jsonReader  = new JSONObject(msg);
	    		String sendJsonString  = jsonReader.getString(XSGAPIMessage.XSGAPI_MESSAGE_SENDER_TAG);
	    		if(sendJsonString != null && 0 < sendJsonString.length())
	    		{
			    	JSONObject senderReader  = new JSONObject(sendJsonString);
	    			String playerID = senderReader.getString(XSGAPIMessage.XSGAPI_MESSAGE_SENDER_ID_TAG);
	    			String playerName = senderReader.getString(XSGAPIMessage.XSGAPI_MESSAGE_SENDER_NAME_TAG);
	    			int nPlayerDevice = senderReader.getInt(XSGAPIMessage.XSGAPI_MESSAGE_SENDER_DEVICE_TYPE_TAG);
	    			if(playerID != null && 0 < playerID.length())
	    			{
	    				pSender = new XSGAPIUser(playerID, playerName, nPlayerDevice);
	    			}
	    		}
	    	}
	        catch (JSONException e) 
	        {
	            Log.w("XSGAPIMessageParser.ParseMessageSender:", e.getMessage());
	            pSender = null;
	        }
	    	
	    }
		
	    return pSender;
	}

	public static String ParseGamePlayMessage(String msg)
	{
	    String pMsg = null;
	    
	    if(msg != null && 0 < msg.length())
	    {
	    	try
	    	{
		    	JSONObject jsonReader  = new JSONObject(msg);
		    	pMsg = jsonReader.getString(XSGAPIMessage.XSGAPI_MESSAGE_GAMEPLAY_GAMEMSG_TAG);
	    	}
	        catch (JSONException e) 
	        {
	            Log.w("XSGAPIMessageParser.ParseGamePlayMessage:", e.getMessage());
	            pMsg = null;
	        }
	    	
	    }	    
	    return pMsg;
	}	
	
	public static String ParsePeerInitialData(String msg)
	{
	    String pMsg = null;
	    
	    if(msg != null && 0 < msg.length())
	    {
	    	try
	    	{
		    	JSONObject jsonReader  = new JSONObject(msg);
		    	pMsg = jsonReader.getString(XSGAPIMessage.XSGAPI_MESSAGE_PEERINITIAL_TAG);
	    	}
	        catch (JSONException e) 
	        {
	            Log.w("XSGAPIMessageParser.ParsePeerInitialData:", e.getMessage());
	            pMsg = null;
	        }
	    	
	    }	    
	    return pMsg;
	}
	
	private JSONObject  m_JsonReader = null;
	
	public XSGAPIMessageParser(String src)
	{
		m_JsonReader = null;
		if(src != null && 0 < src.length())
		{	
	    	try
	    	{
	    		m_JsonReader  = new JSONObject(src);
	    	}
	        catch (JSONException e) 
	        {
	            XSGAPIReleaseConfigure.LogDebugInfo("XSGAPIMessageParser.Constructor:", e.getMessage());
	            m_JsonReader = null;
	        }
	    }	    
	}
	
	public int GetMessageType()
	{
	    int nMsgType = XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVALID;
	    
	    if( m_JsonReader != null)
	    {
	    	try
	    	{
	    		nMsgType = m_JsonReader.getInt(XSGAPIMessage.XSGAPI_MESSAGE_TYPE_TAG);
	    	}
	        catch (JSONException e) 
	        {
	        	XSGAPIReleaseConfigure.LogDebugInfo("XSGAPIMessageParser.GetMessageType():", e.getMessage());
	            nMsgType = XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVALID;
	        }
	    	
	    }
	    
	    return nMsgType;
	}
	
	public XSGAPIUser GetMessageSender()
	{
		XSGAPIUser pSender = null;

	    if( m_JsonReader != null)
	    {
	    	try
	    	{
	    		String sendJsonString  = m_JsonReader.getString(XSGAPIMessage.XSGAPI_MESSAGE_SENDER_TAG);
	    		if(sendJsonString != null && 0 < sendJsonString.length())
	    		{
			    	JSONObject senderReader  = new JSONObject(sendJsonString);
	    			String playerID = senderReader.getString(XSGAPIMessage.XSGAPI_MESSAGE_SENDER_ID_TAG);
	    			String playerName = senderReader.getString(XSGAPIMessage.XSGAPI_MESSAGE_SENDER_NAME_TAG);
	    			int nPlayerDevice = senderReader.getInt(XSGAPIMessage.XSGAPI_MESSAGE_SENDER_DEVICE_TYPE_TAG);
	    			if(playerID != null && 0 < playerID.length())
	    			{
	    				pSender = new XSGAPIUser(playerID, playerName, nPlayerDevice);
	    			}
	    		}
	    	}
	        catch (JSONException e) 
	        {
	        	XSGAPIReleaseConfigure.LogDebugInfo("XSGAPIMessageParser.GetMessageSender:", e.getMessage());
	            pSender = null;
	        }
	    	
	    }
		
	    return pSender;
	}

	public String GetGamePlayMessage()
	{
	    String pMsg = null;
	    
	    if( m_JsonReader != null)
	    {
	    	try
	    	{
		    	pMsg = m_JsonReader.getString(XSGAPIMessage.XSGAPI_MESSAGE_GAMEPLAY_GAMEMSG_TAG);
	    	}
	        catch (JSONException e) 
	        {
	        	XSGAPIReleaseConfigure.LogDebugInfo("XSGAPIMessageParser.GetGamePlayMessage:", e.getMessage());
	            pMsg = null;
	        }
	    	
	    }	    
	    return pMsg;
	}	
	
	public String GetPeerInitialData()
	{
	    String pMsg = null;
	    
	    if( m_JsonReader != null)
	    {
	    	try
	    	{
		    	pMsg = m_JsonReader.getString(XSGAPIMessage.XSGAPI_MESSAGE_PEERINITIAL_TAG);
	    	}
	        catch (JSONException e) 
	        {
	        	XSGAPIReleaseConfigure.LogDebugInfo("XSGAPIMessageParser.ParsePeerInitialData:", e.getMessage());
	            pMsg = null;
	        }
	    	
	    }	    
	    return pMsg;
	}
	
}
