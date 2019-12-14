package com.xgadget.XSGService;

import java.util.List;

import com.amazonaws.services.sqs.model.Message;

public interface XSGAPIMessagingManagerDelegate 
{
	//Receiving message service handler methods
	public void HandleXSGAPIUnifiedMessage(List<Message> msglist);
	public void TranslateXSAPIMessage(Message msg);
	public void TranslateRawXSAPIMessage(String msg);
	public void XSGAPIMessageServiceQueueConnected();
	public void XSGAPIMessageServiceStarted();
	public void XSGAPIRecieveMessageServiceStopped();
	
	//Auto response message service handler methods
	public void AutoResponseDone(int nResponseType);
	public void AutoResponseFailed(int nResponseType);
	
	public void XSGAPISendMessageServiceQueueConnected();
	public void XSGAPISendMessageServiceQueueConnectionFailed();
	
}
