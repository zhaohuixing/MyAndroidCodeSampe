package com.xgadget.XSGService;

//import java.util.List;

//import com.amazonaws.services.sqs.model.Message;

public interface XSGAPIGameServiceManagerDelegate 
{
	//Receiving message service handler methods
//	public void HandleXSGAPIUnifiedMessage(List<Message> msglist);
//	public void XSAPIMessageServiceQueueConnected();
//	public void XSAPIMessageServiceStarted();
//	public void XSAPIRecieveMessageServiceStopped();
	
	//Auto response message service handler methods
//	public void AutoResponseDone(int nResponseType);
//	public void AutoResponseFailed(int nResponseType);
	
//	public void XSGAPISendMessageServiceQueueConnected();
//	public void XSGAPISendMessageServiceQueueConnectionFailed();

	public void XSGAPIGamePlayPeerConnected();
	public void XSGAPIGamePlayPeerdisconnected();
	public void XSGAPIHandleGamePlayPeerMessage(String pMsg, XSGAPIUser pUser);
	public void XSGAPISendPlayMessageQueueConnected();
	public void XSGAPIRecievedNewInvitationFrom(XSGAPIUser sender);
	public void XSGAPIInvitationCancelledBy(XSGAPIUser sender);
	public void XSGAPISentInvitationAcceptedBy(XSGAPIUser sender);
	public void XSGAPISentInvitationRejectedBy(XSGAPIUser sender);
	public void XSGAPISendingAcceptInvitionMessageDone();
	public void XSGAPISendingSendInvitionMessageDone();
	public void XSGAPISendingCancelInvitionMessageDone();
	public void XSGAPISendingRejectInvitionMessageDone();
	
	public void XSGAPIConnectDBDomainDone(); //For nofitfication of Connecting DB domain
	public void XSGAPIConnectDBDomainFailed(); //For nofitfication of Connecting DB domain

	public void XSGAPIRegisterUserDone();
	public void XSGAPIRegisterUserFailed();

	public void XSGAPIUnRegisterUserDone();
	public void XSGAPIUnRegisterUserFailed();
	
	public void XSGAPIDisconnectDBDomainDone();

	public void XSGAPIQueryUserCountOperationDone();
	public void XSGAPIQueryBeginUserListOperationDone();
	public void XSGAPIQueryNextUserListOperationDone();
	
	public String GetInvitationSenderInitialData();
	public String GetInvitationReceiverInitialData();
	
}
