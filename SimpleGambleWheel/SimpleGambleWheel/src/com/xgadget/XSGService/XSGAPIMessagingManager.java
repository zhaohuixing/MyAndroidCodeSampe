package com.xgadget.XSGService;

import java.util.List;


import com.amazonaws.services.sqs.model.Message;
//import android.os.Message;

public class XSGAPIMessagingManager implements XSGAPIReceiveMessageServiceDelegate, XSGAPIAutoResponseServiceDelegate, XSGAPISendMessageServiceDelegate//, Handler.Callback
{
    //This message service statically hookup with owner's message queue and listen to the message from other user
    private XSGAPIReceiveMessageService          	m_GetMsgService;
    
    //This message service dymantically hookup with peer's message queue and send message to peer
    private XSGAPISendMessageService             	m_SendMsgService;

    //This auto repsone message service for sending invitation auto response message to invitors
    private XSGAPIAutoResponseService             	m_AutoRespnseSrvice;
    
    //Delegate
    private XSGAPIMessagingManagerDelegate      	m_Delegate;
    
    
    //private Handler									m_MsgHandler;
    public boolean handleMessage (android.os.Message msg)
    {
		TranslateRawXSAPIMessage(msg.getData().getString(XSGAPIConstants.XSGAPI_THREAD_ONLINE_MSG_TAG));
    	return true;
    }
    
    //public Handler.Callback GetHandlerCallback()
    //{
    //	return this;
    //}
    
	public XSGAPIMessagingManager(XSGAPIMessagingManagerDelegate delegate)
	{
		m_Delegate = delegate;
		m_AutoRespnseSrvice = new XSGAPIAutoResponseService(this);      
	}

	//
	//Recieve message service
	//
	public void ConnectReceiveMessageQueue(String queueName, boolean bAutoClean)
	{
	    if(m_GetMsgService == null)
	    {
	    	m_GetMsgService = new XSGAPIReceiveMessageService(this);
	    	
	    	m_GetMsgService.ConnectToQueue(queueName);
	    }
	    else
	    {
	//#ifdef DEBUG
	//        [m_GetMsgService SetRealTimeDebugTrack:YES];
	//#endif
	//        [m_GetMsgService SetAutoClean:YES];
	//        [m_GetMsgService setAutoStartService:bAuto];
	        if(m_GetMsgService.IsConnectToQueue(queueName) == true)
	            m_GetMsgService.StartService();
	        else
	        	m_GetMsgService.ConnectToQueue(queueName);
	    }
	}
	
	public void DeleteMessageQueue()
	{
	    if(m_GetMsgService != null)
	        m_GetMsgService.DeleteQueue();
	}

	public void ClearMessageQueue()
	{
	    if(m_GetMsgService != null)
	        m_GetMsgService.ClearQueue();
	}

	public boolean CanRecieveMessage()
	{
	    boolean bRet = false;
	    if(m_GetMsgService != null)
	    {
	        bRet = m_GetMsgService.IsQueueConnected();
	    }
	    
	    return bRet;
	}

	public void StartRecieveMessage()
	{
	//#ifdef DEBUG
	//    [m_GetMsgService SetRealTimeDebugTrack:YES];
	//#endif
	    if(m_GetMsgService != null)
	    {
	        m_GetMsgService.StartService();
	    }
	}

	public void StopRecieveMessage()
	{
	    if(m_GetMsgService != null)
	    {
	        m_GetMsgService.StopService();
	    }
	}

	public void DisconnectRecieveMessageService()
	{
	    if(m_GetMsgService != null)
	    {
	        m_GetMsgService.Disconnect();
	    }
	}

	public boolean IsConnectReceiveMessageQueue(String queueName)
	{
	    boolean bRet = false;
//	#ifdef DEBUG
//	    NSLog(@"XAWSUnifiedMessageManager IsConnectReceiveMessageQueue Q's ID:%@", queueName);
//	#endif
	    
	    if(m_GetMsgService == null)
	    {
//	#ifdef DEBUG
//	        NSLog(@"XAWSUnifiedMessageManager IsConnectReceiveMessageQueue m_GetMsgService == nil, return NO");
//	#endif
	        return bRet;
	    }
	    bRet = m_GetMsgService.IsConnectToQueue(queueName);
//	#ifdef DEBUG
//	    if(bRet)
//	        NSLog(@"XAWSUnifiedMessageManager IsConnectReceiveMessageQueue [m_GetMsgService IsConnectToQueue:queueName] == YES");
//	    else
//	        NSLog(@"XAWSUnifiedMessageManager IsConnectReceiveMessageQueue [m_GetMsgService IsConnectToQueue:queueName] == NO");
//	#endif
	    return bRet;
	}
	
	
	//XSAPIReceiveMessageServiceDelegate methods
	@Override
	public void HandleXSAPIMessage(List<Message> msglist)
	{
//		#ifdef DEBUG
//	    NSLog(@"XAWSUnifiedMessageManager HandleAWSMessage");
//	#endif
	    if(m_Delegate != null)
	    {
	        m_Delegate.HandleXSGAPIUnifiedMessage(msglist);
	    }		
	}
	
	@Override
	public void TranslateXSAPIMessage(Message msg)
	{
	    if(m_Delegate != null)
	    {
	        m_Delegate.TranslateXSAPIMessage(msg);
	    }		
	}

	public void TranslateRawXSAPIMessage(String msg)
	{
	    if(m_Delegate != null)
	    {
	        m_Delegate.TranslateRawXSAPIMessage(msg);
	    }		
	}
	
	@Override
	public void XSAPIMessageServiceQueueConnected()
	{
//		#ifdef DEBUG
//	    NSLog(@"XAWSUnifiedMessageManager AWSMessageServiceQueueConnected");
//	#endif
	    if(m_Delegate != null)
	    {
	        m_Delegate.XSGAPIMessageServiceQueueConnected();
	    }
	}
	
	@Override
	public void XSAPIMessageServiceStarted()
	{
//		#ifdef DEBUG
//	    NSLog(@"XAWSUnifiedMessageManager AWSMessageServiceStarted");
//	#endif
	    if(m_Delegate != null)
	    {
	        m_Delegate.XSGAPIMessageServiceStarted();
	    }
	}
	
	@Override
	public void XSAPIMessageServiceDone()
	{
//		#ifdef DEBUG
//	    NSLog(@"XAWSUnifiedMessageManager AWSMessageServiceDone");
//	#endif
	    if(m_Delegate != null)
	    {
	        m_Delegate.XSGAPIRecieveMessageServiceStopped();
	    }
		
	}
	
	//
	//Send Message service
	public void ConnectSendMessageQueue(String queueName)
	{
	//#ifdef DEBUG
	//    NSLog(@"XAWSUnifiedMessageManager ConnectSendMessageQueue Q's ID:%@", queueName);
	//#endif
	    if(m_SendMsgService == null)
	    {
	//#ifdef DEBUG
	//        NSLog(@"XAWSUnifiedMessageManager ConnectSendMessageQueue Q's ID:%@", queueName);
	//#endif
	        m_SendMsgService = new XSGAPISendMessageService(this);
	        if(m_SendMsgService != null)
	        	m_SendMsgService.ConnectToQueue(queueName);
	    }
	    else
	    {
	        if(m_SendMsgService.IsConnectedToQueue(queueName) == false)
	            m_SendMsgService.ConnectToQueue(queueName);
	        else
	        	this.XSGAPISendMessageServiceQueueConnected();
	            
	    }
	}

	public void DisConnectSendMessageQueue()
	{
	    if(m_SendMsgService != null)
	    {
	        m_SendMsgService.Disconnect();
	    }
	}
	
	//Send message queue functions
	public boolean CanSendMessage()
	{
	    boolean bRet = false;
	    if(m_SendMsgService != null)
	    {
	        bRet = m_SendMsgService.IsQueueConnected();
	    }
	    
	    return bRet;
	}

	public void SendMessage(String msg)
	{
	//#ifdef DEBUG
	//    NSLog(@"XAWSUnifiedMessageManager SendMessage");
	//#endif
	    if(m_SendMsgService != null)
	    {
	        m_SendMsgService.SendMessage(msg);
	    }
	}

	public void SendMessageToCachedQueue(String msg, String cachedQueueURL)
	{
	//#ifdef DEBUG
	//    NSLog(@"XAWSUnifiedMessageManager SendMessage");
	//#endif
	    if(m_SendMsgService != null)
	    {
	        m_SendMsgService.SendMessageToCachedQueue(msg, cachedQueueURL);
	    }
	}
	
	public String GetCachedSendQueuURL()
	{
		String strURL = null;
	    if(m_SendMsgService != null)
	    {
	    	strURL = m_SendMsgService.GetCachedQueuURL();
	    }
		return strURL;
	}
	//
	//Send message service delegate
	//
	public void XSGAPISendMessageServiceQueueConnected()
	{
		if(this.m_Delegate != null)
			this.m_Delegate.XSGAPISendMessageServiceQueueConnected();
	}
	
	public void XSGAPISendMessageServiceQueueConnectionFailed()
	{
		if(this.m_Delegate != null)
			this.m_Delegate.XSGAPISendMessageServiceQueueConnectionFailed();
	}
	
	//
	//Auto response methods
	//
	public void SendInvitationMessageTo(String invitatorqueueName, XSGAPIUser sender)
	{
		if(m_AutoRespnseSrvice != null)
		{
			m_AutoRespnseSrvice.SendAutoResponseMessage(invitatorqueueName, sender, XSGAPIMessage.XSGAPI_MSG_AUTO_INVITE);
		}
	}

	public void SendInvitationMessageTo2(String invitatorqueueName, XSGAPIUser sender, String szData)
	{
		if(m_AutoRespnseSrvice != null)
		{
			m_AutoRespnseSrvice.SendAutoResponseMessage2(invitatorqueueName, sender, XSGAPIMessage.XSGAPI_MSG_AUTO_INVITE, szData);
		}
	}
	
	public void SendCancelInvitationMessageTo(String invitatorqueueName, XSGAPIUser sender)
	{
		if(m_AutoRespnseSrvice != null)
		{
			m_AutoRespnseSrvice.SendAutoResponseMessage(invitatorqueueName, sender, XSGAPIMessage.XSGAPI_MSG_AUTO_CANCEL);
		}
	}

	public void SendAcceptInvitationMessage(String invitatorqueueName, XSGAPIUser sender)
	{
		if(m_AutoRespnseSrvice != null)
		{
			m_AutoRespnseSrvice.SendAutoResponseMessage(invitatorqueueName, sender, XSGAPIMessage.XSGAPI_MSG_AUTO_ACCEPT);
		}
	}

	public void SendAcceptInvitationMessage2(String invitatorqueueName, XSGAPIUser sender, String szData)
	{
		if(m_AutoRespnseSrvice != null)
		{
			m_AutoRespnseSrvice.SendAutoResponseMessage2(invitatorqueueName, sender, XSGAPIMessage.XSGAPI_MSG_AUTO_ACCEPT, szData);
		}
	}
	
	
	public void SendRejectInvitationMessage(String invitatorqueueName, XSGAPIUser sender)
	{
		if(m_AutoRespnseSrvice != null)
		{
			m_AutoRespnseSrvice.SendAutoResponseMessage(invitatorqueueName, sender, XSGAPIMessage.XSGAPI_MSG_AUTO_REJECT);
		}
	}	
	
	public void PostHandleSentInvitationRejectedBy(String userID)
	{
	    String szQueueName = XSGAPIGameServiceManager.GetXSGAPIMessageQueueName(userID);
	    if(m_SendMsgService != null && m_SendMsgService.IsConnectedToQueue(szQueueName) == true)
	        m_SendMsgService.Disconnect();
	}
	
	//
	//Auto response message service handler methods
	//
	@Override
	public void AutoResponseDone(int nResponseType)
	{
		if(m_Delegate != null)
			m_Delegate.AutoResponseDone(nResponseType);
	}
	
	@Override
	public void AutoResponseFailed(int nResponseType)
	{
		if(m_Delegate != null)
			m_Delegate.AutoResponseFailed(nResponseType);
	}
	
/*	private Handler    m_msgHandler = null;
	
	@Override
	public Handler GetThreadMessageHandler()
	{
		if(m_msgHandler == null)
		{	
			m_msgHandler = new Handler() 
			{
				@Override
				public void handleMessage(android.os.Message m) 
				{
				}
			};
		}	
		
		return m_msgHandler;
	}*/
}
