package com.xgadget.XSGService;

public class XSGAPIMessage 
{
	//AWS Message tags 
	//Root tag: type root tag
	public static final String XSGAPI_MESSAGE_TYPE_TAG  = "XAM_TYPE";                      //integer value

	//
	//Sender user info root tag, 
	//
	public static final String XSGAPI_MESSAGE_SENDER_TAG = "XAM_SENDER";                  //sub package

	//Sender user info sub tag
	public static final String XSGAPI_MESSAGE_SENDER_ID_TAG = "XAM_SENDER_ID";            //string value
	public static final String XSGAPI_MESSAGE_SENDER_NAME_TAG = "XAM_SENDER_NAME";          //string value
	public static final String XSGAPI_MESSAGE_SENDER_DEVICE_TYPE_TAG = "XAM_SENDER_DEVICE";           //integer value

	//
	/////////////////////////////////////////

	//
	/////////////////////////////////////////
	//
	//Peer inital data in invitation message,
	//
	public static final String XSGAPI_MESSAGE_PEERINITIAL_TAG  = "XAM_PEERINITIAL";                  //sub package
	//
	/////////////////////////////////////////


	//
	//GamePlay game message tag, type tag
	//The subtags of game message tag will share with GK message tag
	//
	public static final String XSGAPI_MESSAGE_GAMEPLAY_GAMEMSG_TAG = "XAM_GAME_GSG";           //sub package
	//
	/////////////////////////////////////////


	//AWS Message Type value (for XAWS_MESSAGE_TYPE_TAG)
	public static final int XSGAPI_MESSAGE_TYPE_INVALID = -1;
	public static final int XSGAPI_MESSAGE_TYPE_INVITATION_INVITATION = 0;
	public static final int XSGAPI_MESSAGE_TYPE_INVITATION_CANCELLATION = 1;
	public static final int XSGAPI_MESSAGE_TYPE_INVITATION_ACCEPTION = 2;
	public static final int XSGAPI_MESSAGE_TYPE_INVITATION_REJECTION = 3;
	//#define XAWS_MESSAGE_TYPE_INVITATION2PLAY_SHAKEHAND         4

	public static final int XSGAPI_MESSAGE_TYPE_GAMEPLAY_CONNECTED = 4;//5
	public static final int XSGAPI_MESSAGE_TYPE_GAMEPLAY_DISCONNECTED = 5;//6
	public static final int XSGAPI_MESSAGE_TYPE_GAMEPLAY_GAMEMESSAGE = 6;//7

	public static final int XSGAPI_MSG_AUTO_NONE = 0;
	public static final int XSGAPI_MSG_AUTO_INVITE = 1;                   //Send invitation to peer
	public static final int XSGAPI_MSG_AUTO_CANCEL = 2;                   //Cancel previous sending invitation to reciever
	public static final int XSGAPI_MSG_AUTO_ACCEPT = 3;                   //Send accepting invitation message to invitation sender
	public static final int XSGAPI_MSG_AUTO_REJECT = 4;                   //Send rejecting invitation message to invitation sender
}
