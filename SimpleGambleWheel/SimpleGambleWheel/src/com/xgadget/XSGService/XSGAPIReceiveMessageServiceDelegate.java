package com.xgadget.XSGService;

import java.util.List;

import com.amazonaws.services.sqs.model.Message;

public interface XSGAPIReceiveMessageServiceDelegate 
{
	public void HandleXSAPIMessage(List<Message> msglist);
	public void TranslateXSAPIMessage(Message msg);
	public void XSAPIMessageServiceQueueConnected();
	public void XSAPIMessageServiceStarted();
	public void XSAPIMessageServiceDone();
	//public Handler.Callback GetHandlerCallback();

}
