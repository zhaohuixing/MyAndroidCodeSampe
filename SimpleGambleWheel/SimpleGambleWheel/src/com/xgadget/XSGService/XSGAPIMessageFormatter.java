package com.xgadget.XSGService;

import org.json.*;

public class XSGAPIMessageFormatter 
{
	public static String FormatUserInformation(XSGAPIUser sender)
	{
	    if(sender == null)
	        return null;
	    
	    String pGameMessage = null;
		JSONObject jObject = new JSONObject();
		
		try
		{
			jObject.put(XSGAPIMessage.XSGAPI_MESSAGE_SENDER_ID_TAG, sender.GetUserID());
			jObject.put(XSGAPIMessage.XSGAPI_MESSAGE_SENDER_NAME_TAG, sender.GetUserName());
			jObject.put(XSGAPIMessage.XSGAPI_MESSAGE_SENDER_DEVICE_TYPE_TAG, sender.GetUserType());
			pGameMessage = jObject.toString();
		}
        catch (JSONException e) 
        {
            XSGAPIReleaseConfigure.LogDebugInfo("XSGAPIMessageFormatter.XSGAPIMessageFormatter:", e.getMessage());
        }
	    return pGameMessage;
	}	
	
	public static String FormatInvitationMessage(XSGAPIUser sender)
	{
	    if(sender == null)
	        return null;
	    
	    String pGameMessage = null;
		JSONObject jsonWriter = new JSONObject();
		
		try
		{
			jsonWriter.put(XSGAPIMessage.XSGAPI_MESSAGE_TYPE_TAG, XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVITATION_INVITATION);
			String userInfo = XSGAPIMessageFormatter.FormatUserInformation(sender);
			jsonWriter.put(XSGAPIMessage.XSGAPI_MESSAGE_SENDER_TAG, userInfo);
			pGameMessage = jsonWriter.toString();
		}
        catch (JSONException e) 
        {
        	XSGAPIReleaseConfigure.LogDebugInfo("XSGAPIMessageFormatter.FormatInvitationMessage:", e.getMessage());
        }
		
		return pGameMessage;
	}
	
	public static String FormatCancellationMessage(XSGAPIUser sender)
	{
	    if(sender == null)
	        return null;
	    
	    String pGameMessage = null;
		JSONObject jsonWriter = new JSONObject();
	    
		try
		{
			jsonWriter.put(XSGAPIMessage.XSGAPI_MESSAGE_TYPE_TAG, XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVITATION_CANCELLATION);
			String userInfo = XSGAPIMessageFormatter.FormatUserInformation(sender);
			jsonWriter.put(XSGAPIMessage.XSGAPI_MESSAGE_SENDER_TAG, userInfo);
			pGameMessage = jsonWriter.toString();
		}
        catch (JSONException e) 
        {
        	XSGAPIReleaseConfigure.LogDebugInfo("XSGAPIMessageFormatter.FormatCancellationMessage:", e.getMessage());
        }
		
	    return pGameMessage;
	}

	public static String FormatAcceptionMessage(XSGAPIUser sender)
	{
	    if(sender == null)
	        return null;
	    
	    String pGameMessage = null;
		JSONObject jsonWriter = new JSONObject();
    
		try
		{
			jsonWriter.put(XSGAPIMessage.XSGAPI_MESSAGE_TYPE_TAG, XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVITATION_ACCEPTION);
			String userInfo = XSGAPIMessageFormatter.FormatUserInformation(sender);
			jsonWriter.put(XSGAPIMessage.XSGAPI_MESSAGE_SENDER_TAG, userInfo);
			pGameMessage = jsonWriter.toString();
		}
        catch (JSONException e) 
        {
        	XSGAPIReleaseConfigure.LogDebugInfo("XSGAPIMessageFormatter.FormatAcceptionMessage:", e.getMessage());
        }
		
	    return pGameMessage;
	}
	
	public static String FormatRejecttionMessage(XSGAPIUser sender)
	{
	    if(sender == null)
	        return null;
	    
	    String pGameMessage = null;
		JSONObject jsonWriter = new JSONObject();
    
		try
		{
			jsonWriter.put(XSGAPIMessage.XSGAPI_MESSAGE_TYPE_TAG, XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVITATION_REJECTION);
			String userInfo = XSGAPIMessageFormatter.FormatUserInformation(sender);
			jsonWriter.put(XSGAPIMessage.XSGAPI_MESSAGE_SENDER_TAG, userInfo);
			pGameMessage = jsonWriter.toString();
		}
        catch (JSONException e) 
        {
        	XSGAPIReleaseConfigure.LogDebugInfo("XSGAPIMessageFormatter.FormatRejecttionMessage:", e.getMessage());
        }
		
	    return pGameMessage;
	}
	
	public static String FormatConnectMessage(XSGAPIUser sender)
	{
	    if(sender == null)
	        return null;
	    
	    String pGameMessage = null;
		JSONObject jsonWriter = new JSONObject();

		try
		{
			jsonWriter.put(XSGAPIMessage.XSGAPI_MESSAGE_TYPE_TAG, XSGAPIMessage.XSGAPI_MESSAGE_TYPE_GAMEPLAY_CONNECTED);
			String userInfo = XSGAPIMessageFormatter.FormatUserInformation(sender);
			jsonWriter.put(XSGAPIMessage.XSGAPI_MESSAGE_SENDER_TAG, userInfo);
			pGameMessage = jsonWriter.toString();
		}
        catch (JSONException e) 
        {
        	XSGAPIReleaseConfigure.LogDebugInfo("XSGAPIMessageFormatter.FormatConnectMessage:", e.getMessage());
        }
		
	    return pGameMessage;
	}	
	
	public static String FormatDisconnectMessage(XSGAPIUser sender)
	{
	    if(sender == null)
	        return null;
	    
	    String pGameMessage = null;
		JSONObject jsonWriter = new JSONObject();

		try
		{
			jsonWriter.put(XSGAPIMessage.XSGAPI_MESSAGE_TYPE_TAG, XSGAPIMessage.XSGAPI_MESSAGE_TYPE_GAMEPLAY_DISCONNECTED);
			String userInfo = XSGAPIMessageFormatter.FormatUserInformation(sender);
			jsonWriter.put(XSGAPIMessage.XSGAPI_MESSAGE_SENDER_TAG, userInfo);
			pGameMessage = jsonWriter.toString();
		}
        catch (JSONException e) 
        {
        	XSGAPIReleaseConfigure.LogDebugInfo("XSGAPIMessageFormatter.FormatDisconnectMessage:", e.getMessage());
        }
		
	    return pGameMessage;
	}
	
	public static String FormatGamePlayMessage(XSGAPIUser sender, String message)
	{
	    if(sender == null && message == null)
	        return null;

	    String pGameMessage = null;
		JSONObject jsonWriter = new JSONObject();

		try
		{
			jsonWriter.put(XSGAPIMessage.XSGAPI_MESSAGE_TYPE_TAG, XSGAPIMessage.XSGAPI_MESSAGE_TYPE_GAMEPLAY_GAMEMESSAGE);
			String userInfo = XSGAPIMessageFormatter.FormatUserInformation(sender);
			jsonWriter.put(XSGAPIMessage.XSGAPI_MESSAGE_SENDER_TAG, userInfo);
			jsonWriter.put(XSGAPIMessage.XSGAPI_MESSAGE_GAMEPLAY_GAMEMSG_TAG, message);
			pGameMessage = jsonWriter.toString();
		}
        catch (JSONException e) 
        {
        	XSGAPIReleaseConfigure.LogDebugInfo("XSGAPIMessageFormatter.FormatGamePlayMessage:", e.getMessage());
        }
	    return pGameMessage;
	}
	
	public static String FormatInvitationMessage2(XSGAPIUser sender, String message)
	{
	    if(sender == null)
	        return null;
	    
	    String pGameMessage = null;
		JSONObject jsonWriter = new JSONObject();
		
		try
		{
			jsonWriter.put(XSGAPIMessage.XSGAPI_MESSAGE_TYPE_TAG, XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVITATION_INVITATION);
			String userInfo = XSGAPIMessageFormatter.FormatUserInformation(sender);
			jsonWriter.put(XSGAPIMessage.XSGAPI_MESSAGE_SENDER_TAG, userInfo);
		    
			if(message != null && 0 < message.length())
		    {
		    	jsonWriter.put(XSGAPIMessage.XSGAPI_MESSAGE_PEERINITIAL_TAG, message);
		    }
			
			pGameMessage = jsonWriter.toString();
		}
        catch (JSONException e) 
        {
        	XSGAPIReleaseConfigure.LogDebugInfo("XSGAPIMessageFormatter.FormatInvitationMessage2:", e.getMessage());
        }
		
		return pGameMessage;
		
	}
	
	public static String FormatAcceptionMessage2(XSGAPIUser sender, String message)
	{
	    if(sender == null)
	        return null;
	    
	    String pGameMessage = null;
		JSONObject jsonWriter = new JSONObject();
    
		try
		{
			jsonWriter.put(XSGAPIMessage.XSGAPI_MESSAGE_TYPE_TAG, XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVITATION_ACCEPTION);
			String userInfo = XSGAPIMessageFormatter.FormatUserInformation(sender);
			jsonWriter.put(XSGAPIMessage.XSGAPI_MESSAGE_SENDER_TAG, userInfo);
			
		    if(message != null && 0 < message.length())
		    {
		    	jsonWriter.put(XSGAPIMessage.XSGAPI_MESSAGE_PEERINITIAL_TAG, message);
		    }
			pGameMessage = jsonWriter.toString();
		}
        catch (JSONException e) 
        {
        	XSGAPIReleaseConfigure.LogDebugInfo("XSGAPIMessageFormatter.FormatAcceptionMessage2:", e.getMessage());
        }
		
	    return pGameMessage;
	}
	
}
