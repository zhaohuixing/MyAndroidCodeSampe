package com.xgadget.XSGService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import android.os.Handler;

import com.amazonaws.services.sqs.model.Message;

public class XSGAPIGameServiceManager implements XSGAPIMessagingManagerDelegate, XSGAPIUserDBServiceDelegate 
{
	public static String GenerateGUID()
	{
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        return randomUUIDString;
	}

	public static String GetAWSAccessKey()
	{
	    return XSGAPIConstants.XGADGET_ACCESS_KEY_ID;
	}

	public static String GetAWSSecretKey()
	{
	    return XSGAPIConstants.XGADGET_SECRET_KEY;
	}

	public static String GetXAWSGameUserDBDomain()
	{
	    return XSGAPIDBConstants.CURRENT_USERDBDOMAIN;
	}


	public static String GetAppID()
	{
	    return XSGAPIConstants.CURRENT_APP_ID;
	}

	
	public static String GetXSGAPIMessageQueueName(String szPlayerID)
	{
		String szRet = null;
		
	    if(szPlayerID != null)
	    {
	        szRet = XSGAPIGameServiceManager.GetAppID() + "_" + szPlayerID + "_UNIFIEDMSG_Q";   // [NSString stringWithFormat:@"%@_%@_UNIFIEDMSG_Q", [XAWSGameServiceManager GetAppID], szPlayerID];
	    }
	    
		return szRet;
	}

    public static XSGAPIGameServiceManager g_GameServiceManager;
	
	public static synchronized XSGAPIGameServiceManager getXSGAPIGameManager()
	{
	    if(g_GameServiceManager == null)
	    {
	    	g_GameServiceManager = new XSGAPIGameServiceManager();
	    }
	    return g_GameServiceManager;
	}

	public static synchronized void RegisterServiceManagerDelegate(XSGAPIGameServiceManagerDelegate delegate)
	{
	    if(g_GameServiceManager == null)
	    {
	    	g_GameServiceManager = new XSGAPIGameServiceManager();
	    }
	    g_GameServiceManager.RegisterDelegate(delegate);
	}
	
	private XSGAPIMessagingManager			m_UnifiedGameMessageService = null;
    private XSGAPIUserDBService             m_GamePlayerListService = null;
    private XSGAPIUser                      m_LocalPlayer;
    private XSGAPIUser                      m_PeerPlayer;
    
    private int                             m_GameServiceState;             //Game service state
    
    //Cached invitation sending and recieving list 
    private ArrayList<String>           	_m_InvitationSentList;          //Store ID: NSString
    private ArrayList<XSGAPIUser>           _m_InvitationReceivedList;       //Store player data: XAWSUser
    private boolean                         m_bDonotAcceptInvitation;
    private boolean                         m_bAsGameHost;
    
    //General type online player list
    private int                             _m_CurrentQueryUserType;        //-1: all type; 0: iOS general user; 1: iOS GameCenter user
    private ArrayList<XSGAPIUser>           _m_RegisteredOnlineUserList;
    private int                             _m_RegisteredOnlineUserCount;
    private boolean                         _m_bStillHaveUsers;
    
    private XSGAPIGameServiceManagerDelegate          _m_Delegate;	

    public Handler							m_MsgHandler = null;
    private String                       	m_PeerInitialData;

    
    
    private int ReceivedInvitationIndexFrom(String senderID)
    {
        int nIndex = -1;
        if(_m_InvitationReceivedList != null && 0 < _m_InvitationReceivedList.size() && senderID != null)
        {
            for(int i = 0; i < _m_InvitationReceivedList.size(); ++i)
            
            {
                String szID = ((XSGAPIUser)(_m_InvitationReceivedList.get(i))).GetUserID();
                if(senderID.equals(szID) == true)
                {
                    nIndex = i;
                    break;
                }
            }
        }
        return nIndex;
    }

    private int SentInvitationIndexTo(String recieverID)
    {
        int nIndex = -1;
        if(_m_InvitationSentList != null && 0 < _m_InvitationSentList.size() && recieverID != null)
        {
            for(int i = 0; i < _m_InvitationSentList.size(); ++i)
            {
                if(recieverID.equals(_m_InvitationSentList.get(i)) == true)
                {
                    nIndex = i;
                    break;
                }
            }
        }
        return nIndex;
    }
    
    
	public XSGAPIGameServiceManager() 
	{
		// TODO Auto-generated constructor stub
        m_UnifiedGameMessageService = null;
        m_GamePlayerListService = null;
        m_LocalPlayer = null;
        m_PeerPlayer = null;
        _m_InvitationSentList = new ArrayList<String>();
        
        _m_InvitationReceivedList = new ArrayList<XSGAPIUser>();
        
        _m_RegisteredOnlineUserList = new ArrayList<XSGAPIUser>();
        _m_bStillHaveUsers = false;
        m_GameServiceState = XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_IDLE;
        _m_Delegate = null;
    
        m_bDonotAcceptInvitation = false;
        m_bAsGameHost = false;
        m_PeerInitialData = null;
        
        m_MsgHandler = new Handler()
        {
        	public void handleMessage (android.os.Message msg)
        	{
        		TranslateRawXSAPIMessage(msg.getData().getString(XSGAPIConstants.XSGAPI_THREAD_ONLINE_MSG_TAG));
        	}
		};        
	}
	
	public Handler GetHandler()
	{
		return m_MsgHandler;
	}
	
	public void RegisterDelegate(XSGAPIGameServiceManagerDelegate delegate)
	{
		_m_Delegate = delegate;
	}

	public void IntializeLocalPlayerSetting(XSGAPIUser localPlayer)
	{
	    m_bAsGameHost = false;
	    if(m_LocalPlayer != null && localPlayer!= null && m_LocalPlayer.IsIdenticalTo(localPlayer) == true)
	    {
	        this.RegiserLocalPlayerToGameUserDomain();
	        return;
	    }
	    
	    m_LocalPlayer = localPlayer;
        this.RegiserLocalPlayerToGameUserDomain();
	    m_bAsGameHost = false;
	}
	
	public void ShutDownCompletely()
	{
	    this.RemoveLocalPlayerFromGameUserDomain();
	    if(m_UnifiedGameMessageService != null)
	    {	
	    	m_UnifiedGameMessageService.StopRecieveMessage();
	    	m_UnifiedGameMessageService.DisConnectSendMessageQueue();
	    }	
	}

	public void ShutDownButRegisterOnlineAndListenToInvitation()
	{
	    m_GameServiceState = XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_IDLE;
	    if(m_UnifiedGameMessageService != null)
	    	m_UnifiedGameMessageService.DisConnectSendMessageQueue();
	    if(m_PeerPlayer != null)
	    {
	        m_PeerPlayer = null;
	    }
	    if(m_PeerInitialData != null)
	    {
	        m_PeerInitialData = null;
	    }
	    this.RegiserLocalPlayerToGameUserDomain();
	}

	public void ShutXAWSOnlineGameSession(boolean bTotalShutDown)
	{
	    if(bTotalShutDown == true)
	    {
	        this.ShutDownCompletely();
	    }
	    else
	    {
	    	this.ShutDownButRegisterOnlineAndListenToInvitation();
	    }
	    
	}

	public XSGAPIUser GetConnectedPeer()
	{
	    return m_PeerPlayer;
	}

	public String GetPeerInitialData()
	{
	    return m_PeerInitialData;
	}

	
	public boolean IsGameHost()
	{
	    return m_bAsGameHost;
	}

	public void DeleteAllQueue()
	{
	    this.ShutDownCompletely();
	    if(m_UnifiedGameMessageService != null)
	    	m_UnifiedGameMessageService.DeleteMessageQueue();
	}

	public void ClearAllQueue()
	{
		this.ShutDownCompletely();
	    if(m_UnifiedGameMessageService != null)
	    	m_UnifiedGameMessageService.ClearMessageQueue();
	}
	
	
////////////////////////////////////////////////////////////
//
//Connect user domain functions
//
//////////////////////////////////////////////////////////
	public void ConnectToAppUserDBDomain()
	{
		m_GameServiceState = XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_QUERYONLINEUSERLIST;
		if(m_GamePlayerListService == null)
		{
			m_GamePlayerListService = new XSGAPIUserDBService();
			m_GamePlayerListService.SetDelegate(this);
		}
		if(this.IsConnectToAppUserDBDomain() == false)
		{
			m_GamePlayerListService.SetDomain(XSGAPIGameServiceManager.GetXAWSGameUserDBDomain());
			m_GamePlayerListService.CreateDBDomain();
		}
	}

	public boolean IsConnectToAppUserDBDomain()
	{
		boolean bRet = false;

		if(m_GamePlayerListService != null && m_GamePlayerListService.IsSameDBDomain(XSGAPIGameServiceManager.GetXAWSGameUserDBDomain()) == true)
		{
			bRet = true;
		}

		return bRet;
	}

	public void RegiserLocalPlayerToGameUserDomain()
	{
		m_GameServiceState = XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_QUERYONLINEUSERLIST;
		if(this.IsConnectToAppUserDBDomain() == false)
		{
			if(m_GamePlayerListService == null)
			{
				m_GamePlayerListService = new XSGAPIUserDBService();
				m_GamePlayerListService.SetDelegate(this);
			}
			m_GamePlayerListService.SetDomain(XSGAPIGameServiceManager.GetXAWSGameUserDBDomain());
			m_GamePlayerListService.CreateDBDomain();
			m_GamePlayerListService.RegisterUser(m_LocalPlayer);
		}
		else
		{
			m_GamePlayerListService.RegisterUser(m_LocalPlayer);
		}
	}

	public void RemoveLocalPlayerFromGameUserDomain()
	{
		if(m_GamePlayerListService != null && this.IsConnectToAppUserDBDomain() == true && m_LocalPlayer != null)
		{
			m_GamePlayerListService.UnRegisterUser(m_LocalPlayer.GetUserID());
		}
	}

	public XSGAPIUser GetLocalUser()
	{
		return m_LocalPlayer;
	}

	public boolean IsLocalID(String szID)
	{
		boolean bRet = false;

		if(m_LocalPlayer != null && m_LocalPlayer.GetUserID() != null && m_LocalPlayer.GetUserID().equals(szID) == true)
			bRet = true;

		return bRet;
	}

	public void ResetGameUserDomain()
	{
//		#ifdef DEBUG
//if(m_GamePlayerListService && [self IsConnectToAppUserDBDomain])
//{
//[m_GamePlayerListService DeleteDBDomain];
//}
//#endif
	}
////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////
//
//Query general type online player list
//
////////////////////////////////////////////////////////////
	public void QueryOlineUsersNumber()
	{
		_m_RegisteredOnlineUserCount = 0;
		if(m_GamePlayerListService != null && this.IsConnectToAppUserDBDomain() == true)
		{
			m_GamePlayerListService.StartQueryUserCountOperation();
		}
		//#ifdef DEBUG
		//else
		//{
		//	NSLog(@"Disconnected from User DB domain, cannot call QueryOlineUsersNumber");
		//}
		//#endif
	}	

	public int GetOnlineUserNumber()
	{
		return _m_RegisteredOnlineUserCount;
	}

	public void ResetCachedOnlineUserData()
	{
		_m_bStillHaveUsers = false;
		_m_RegisteredOnlineUserList.clear();
		if(m_GamePlayerListService != null && this.IsConnectToAppUserDBDomain() == true)
		{
			m_GamePlayerListService.ResetGeneralToken();
		}
		//#ifdef DEBUG
		//else
		//{
		//	NSLog(@"Disconnected from User DB domain, cannot call ResetCachedOnlineUserData");
		//}
		//#endif
	}	

	public boolean StillHaveUsers()
	{
		return _m_bStillHaveUsers;
	}

	public void PollOnlineUserList()
	{
		_m_bStillHaveUsers = false;
		if(m_GamePlayerListService != null && this.IsConnectToAppUserDBDomain() == true)
		{
			m_GamePlayerListService.StartQueryBeginGeneralUserListOperation();
		}
		//#ifdef DEBUG
		//else
		//{
		//	NSLog(@"Disconnected from User DB domain, cannot call PollOnlineUserList");
		//}
		//#endif
	}

	public void ContiunePollOnlineUserList()
	{
		if(m_GamePlayerListService != null && this.IsConnectToAppUserDBDomain() == true)
		{
			m_GamePlayerListService.StartQueryNextGeneralUserListOperation();
		}
		//#ifdef DEBUG
		//else
		//{
		//	NSLog(@"Disconnected from User DB domain, cannot call ContiunePollOnlineUserList");
		//}
		//#endif
	}


	public ArrayList<XSGAPIUser> GetCachedOnlineUserList()
	{
		return _m_RegisteredOnlineUserList;
	}
	
//////////////////////////////////////////////////////////
//
//Invitation related functions
//
//////////////////////////////////////////////////////////
	public void UpdateInvitationProcessState()
	{
		if(m_GameServiceState == XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_INVITATIONPROCESS)
		{
			if(_m_InvitationReceivedList.size() <= 0 && _m_InvitationSentList.size() <= 0)
				m_GameServiceState = XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_QUERYONLINEUSERLIST;
		}
	}

	public void ClearCachedInvitationData()
	{
		_m_InvitationSentList.clear();
		_m_InvitationReceivedList.clear();
	}

	public void SetAutoRejectInvitation(boolean bAutoReject)
	{
		m_bDonotAcceptInvitation = bAutoReject;
	}

	public boolean IsAutoRejectInvitation()
	{
		return m_bDonotAcceptInvitation;
	}

/*
-(void)StartInvitationService
{
m_bDonotAcceptInvitation = NO;
if(m_LocalPlayer && [m_LocalPlayer GetUserID])
{
if(!m_InvitationMessageService)
{
m_InvitationMessageService = [[XAWSInvitationMessageManager alloc] init];
[m_InvitationMessageService AssignDelegate:self];
}
NSString* localPlayerInvitationQName = [XAWSGameServiceManager GetPlayerInvitationQName:[m_LocalPlayer GetUserID]];
[m_InvitationMessageService ConnectReceiveMessageQueue:localPlayerInvitationQName withAutoStart:YES];
}
}

-(void)StopInvitationService
{
[m_InvitationMessageService DisconnectRecieveMessageService];
[self ClearCachedInvitationData];
}
*/ 

	public void SendInvitationMessageTo(String acceptorID)
	{
		if(m_LocalPlayer != null && m_UnifiedGameMessageService != null)
		{
			if(m_GameServiceState == XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_QUERYONLINEUSERLIST || 
					m_GameServiceState == XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_IDLE ||  
					m_GameServiceState == XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_UNKNOWN)
				m_GameServiceState = XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_INVITATIONPROCESS;

			int nSentIndex = this.SentInvitationIndexTo(acceptorID);

			//Already sent invitation
			if(0 <= nSentIndex)
			{
				return;
			}
			int nGetIndex = this.ReceivedInvitationIndexFrom(acceptorID);
			//Already sent invitation
			if(0 <= nGetIndex)
			{
				//[self SendAcceptInvitationTo:acceptorID];
				this.SendAcceptInvitationToPeer(_m_InvitationReceivedList.get(nGetIndex));
				return;
			}

			String invitationQName = XSGAPIGameServiceManager.GetXSGAPIMessageQueueName(acceptorID);
			m_UnifiedGameMessageService.SendInvitationMessageTo(invitationQName, m_LocalPlayer);
			_m_InvitationSentList.add(acceptorID);
			m_bAsGameHost = true;
		}
	}
	


	public void SendInvitationMessageToPeer(XSGAPIUser acceptor)
	{
		if(m_LocalPlayer != null && m_UnifiedGameMessageService != null && acceptor != null)
		{
			if(m_GameServiceState == XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_QUERYONLINEUSERLIST || 
				m_GameServiceState == XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_IDLE ||  
				m_GameServiceState == XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_UNKNOWN)
				m_GameServiceState = XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_INVITATIONPROCESS;

			//#ifdef DEBUG
			//	NSLog(@"Send invitation message to peer %@", [acceptor GetUserID]);
			//#endif

			int nSentIndex = this.SentInvitationIndexTo(acceptor.GetUserID());
			//Already sent invitation
			if(0 <= nSentIndex)
			{
				return;
			}
			int nGetIndex = this.ReceivedInvitationIndexFrom(acceptor.GetUserID());
			//Already sent invitation
			if(0 <= nGetIndex)
			{
				this.SendAcceptInvitationToPeer(acceptor);
				return;
			}

			String invitationQName = XSGAPIGameServiceManager.GetXSGAPIMessageQueueName(acceptor.GetUserID());
			m_UnifiedGameMessageService.SendInvitationMessageTo(invitationQName, m_LocalPlayer);
			_m_InvitationSentList.add(acceptor.GetUserID());
			if(m_PeerPlayer != null)
			{
				m_PeerPlayer = null;
			}
	        if(m_PeerInitialData != null)
	        {
	            m_PeerInitialData= null;
	        }
			
			m_PeerPlayer = acceptor;
			m_bAsGameHost = true;
		}
	}
	
	public void SendInvitationMessageToPeer2(XSGAPIUser acceptor, String szData)
	{
		if(m_LocalPlayer != null && m_UnifiedGameMessageService != null && acceptor != null)
		{
			if(m_GameServiceState == XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_QUERYONLINEUSERLIST || 
				m_GameServiceState == XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_IDLE ||  
				m_GameServiceState == XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_UNKNOWN)
				m_GameServiceState = XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_INVITATIONPROCESS;

			//#ifdef DEBUG
			//	NSLog(@"Send invitation message to peer %@", [acceptor GetUserID]);
			//#endif

			int nSentIndex = this.SentInvitationIndexTo(acceptor.GetUserID());
			//Already sent invitation
			if(0 <= nSentIndex)
			{
				return;
			}
			int nGetIndex = this.ReceivedInvitationIndexFrom(acceptor.GetUserID());
			//Already sent invitation
			if(0 <= nGetIndex)
			{
	            String szInitialData = null;
	            if(this._m_Delegate != null)
	            	szInitialData = this._m_Delegate.GetInvitationReceiverInitialData();
	            if(szInitialData != null && 0 < szInitialData.length())
	                this.SendAcceptInvitationToPeer2(acceptor, szInitialData);
	            else
	            	this.SendAcceptInvitationToPeer(acceptor);
				return;
			}

			String invitationQName = XSGAPIGameServiceManager.GetXSGAPIMessageQueueName(acceptor.GetUserID());
			m_UnifiedGameMessageService.SendInvitationMessageTo2(invitationQName, m_LocalPlayer, szData);
			_m_InvitationSentList.add(acceptor.GetUserID());
			if(m_PeerPlayer != null)
			{
				m_PeerPlayer = null;
			}
	        if(m_PeerInitialData != null)
	        {
	            m_PeerInitialData= null;
	        }
			m_PeerPlayer = acceptor;
			m_bAsGameHost = true;
		}
	}
	
	
	public void SendCancelInvitationMessageTo(String acceptorID)
	{
		if(m_LocalPlayer != null && m_UnifiedGameMessageService != null && acceptorID != null)
		{
			if(m_GameServiceState == XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_QUERYONLINEUSERLIST || 
					m_GameServiceState == XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_IDLE ||  
					m_GameServiceState == XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_UNKNOWN)
				m_GameServiceState = XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_INVITATIONPROCESS;

			//#ifdef DEBUG
			//	NSLog(@"Send invitation to %@", acceptorID);
			//#endif

			String invitationQName = XSGAPIGameServiceManager.GetXSGAPIMessageQueueName(acceptorID);
			m_UnifiedGameMessageService.SendCancelInvitationMessageTo(invitationQName, m_LocalPlayer);

			int nSentIndex = this.SentInvitationIndexTo(acceptorID);
			if(0 <= nSentIndex)
			{
				_m_InvitationSentList.remove(nSentIndex);
			}
			this.UpdateInvitationProcessState();

			if(m_PeerPlayer != null && acceptorID.equals(m_PeerPlayer.GetUserID()) == true)
			{
				m_PeerPlayer = null;
			}
	        if(m_PeerInitialData != null)
	        {
	            m_PeerInitialData= null;
	        }
			m_bAsGameHost = false;
		}
	}

	public void SendAcceptInvitationTo(String acceptorID)
	{
		if(m_LocalPlayer != null && m_UnifiedGameMessageService != null && acceptorID != null)
		{
			if(m_GameServiceState == XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_QUERYONLINEUSERLIST || 
					m_GameServiceState == XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_IDLE ||  
					m_GameServiceState == XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_UNKNOWN)
				m_GameServiceState = XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_INVITATIONPROCESS;
			//#ifdef DEBUG
			//	NSLog(@"Send invitation acceptation to %@", acceptorID);
			//#endif


			String invitationQName = XSGAPIGameServiceManager.GetXSGAPIMessageQueueName(acceptorID);
			m_UnifiedGameMessageService.SendAcceptInvitationMessage(invitationQName,  m_LocalPlayer);
			m_bAsGameHost = false;
		}
	}

	public void SendRejectInvitationTo(String acceptorID)
	{
		if(m_LocalPlayer != null && m_UnifiedGameMessageService != null && acceptorID != null)
		{
			//#ifdef DEBUG
			//	NSLog(@"Send invitation rejection to %@", acceptorID);
			//#endif


			String invitationQName = XSGAPIGameServiceManager.GetXSGAPIMessageQueueName(acceptorID);
			m_UnifiedGameMessageService.SendRejectInvitationMessage(invitationQName, m_LocalPlayer);

			int nGetIndex = this.ReceivedInvitationIndexFrom(acceptorID);
			if(0 <= nGetIndex)
			{
				_m_InvitationReceivedList.remove(nGetIndex);
				return;
			}
			this.UpdateInvitationProcessState();
			if(m_PeerPlayer != null && acceptorID.equals(m_PeerPlayer.GetUserID()) == true)
			{
				m_PeerPlayer = null;
			}
	        if(m_PeerInitialData != null)
	        {
	            m_PeerInitialData= null;
	        }
		}
	}

	public void SendAcceptInvitationToPeer(XSGAPIUser acceptor)
	{
		if(m_LocalPlayer != null && m_UnifiedGameMessageService != null)
		{
			//#ifdef DEBUG
			//	NSLog(@"Send invitation acceptation to peer %@", [acceptor GetUserID]);
			//#endif

			if(m_PeerPlayer != null)
			{
				m_PeerPlayer = null;
			}
			m_PeerPlayer = acceptor;
			String invitationQName = XSGAPIGameServiceManager.GetXSGAPIMessageQueueName(acceptor.GetUserID());
			m_UnifiedGameMessageService.SendAcceptInvitationMessage(invitationQName, m_LocalPlayer);

			int nGetIndex = this.ReceivedInvitationIndexFrom(m_PeerPlayer.GetUserID());
			if(0 <= nGetIndex)
			{
				_m_InvitationReceivedList.remove(nGetIndex);
			}
			this.UpdateInvitationProcessState();
			this.StartGamePlayMessageService();
			m_bAsGameHost = false;
		}
	}
	
	public void SendAcceptInvitationToPeer2(XSGAPIUser acceptor, String szData)
	{
		if(m_LocalPlayer != null && m_UnifiedGameMessageService != null)
		{
			//#ifdef DEBUG
			//	NSLog(@"Send invitation acceptation to peer %@%@ withData %@", [acceptor GetUserID], szData);
			//#endif

			if(m_PeerPlayer != null)
			{
				m_PeerPlayer = null;
			}
			m_PeerPlayer = acceptor;
			String invitationQName = XSGAPIGameServiceManager.GetXSGAPIMessageQueueName(acceptor.GetUserID());
			m_UnifiedGameMessageService.SendAcceptInvitationMessage2(invitationQName, m_LocalPlayer, szData);

			int nGetIndex = this.ReceivedInvitationIndexFrom(m_PeerPlayer.GetUserID());
			if(0 <= nGetIndex)
			{
				_m_InvitationReceivedList.remove(nGetIndex);
			}
			this.UpdateInvitationProcessState();
			this.StartGamePlayMessageService();
			m_bAsGameHost = false;
		}
	}
	
//////////////////////////////////////////////////////////


//////////////////////////////////////////////////////////
//
//GamePlay related functions
//
//////////////////////////////////////////////////////////
	public void InitializeGamePlayService()
	{
		//#ifdef DEBUG
		//	NSLog(@"XAWSGameServiceManager InitializeGamePlayService");
		//#endif

		if(m_LocalPlayer != null && m_LocalPlayer.GetUserID() != null)
		{
			//#ifdef DEBUG
			//	NSLog(@"XAWSGameServiceManager InitializeGamePlayService m_LocalPlayer is valid: ID:%@, Name:%@, Type:%i", [m_LocalPlayer GetUserID], [m_LocalPlayer GetUserName], [m_LocalPlayer GetUserType]);
			//#endif
			if(m_UnifiedGameMessageService == null)
			{
				m_UnifiedGameMessageService = new  XSGAPIMessagingManager(this);
			}
			String localPlayerGamePlayQName = XSGAPIGameServiceManager.GetXSGAPIMessageQueueName(m_LocalPlayer.GetUserID());

			//#ifdef DEBUG
			//	NSLog(@"XAWSGameServiceManager InitializeGamePlayService m_LocalPlayer GamePlayQ %@", localPlayerGamePlayQName);
			//#endif
			XSGAPIReleaseConfigure.LogDebugInfo("XSGAPIGameServiceManager InitializeGamePlayService m_LocalPlayer GamePlayQ", localPlayerGamePlayQName);
			m_UnifiedGameMessageService.ConnectReceiveMessageQueue(localPlayerGamePlayQName, true);
		}
	}

	public void StartGamePlayMessageService()
	{
		//#ifdef DEBUG
		//	NSLog(@"XAWSGameServiceManager StartGamePlayMessageService");
		//#endif
		if(m_LocalPlayer == null || m_LocalPlayer.GetUserID() == null || m_PeerPlayer == null || m_PeerPlayer.GetUserID() == null)
			return;

		//#ifdef DEBUG
		//	NSLog(@"XAWSGameServiceManager StartGamePlayMessageService local player game playe ID:%@", [m_LocalPlayer GetUserID]);
		//	NSLog(@"XAWSGameServiceManager StartGamePlayMessageService peer player ID:%@", [m_PeerPlayer GetUserID]);
		//	NSLog(@"XAWSGameServiceManager StartGamePlayMessageService peer player Name:%@", [m_PeerPlayer GetUserName]);
		//	NSLog(@"XAWSGameServiceManager StartGamePlayMessageService peer player Type:%i", [m_PeerPlayer GetUserType]);
		//#endif

		String peerPlayerGamePlayQName = XSGAPIGameServiceManager.GetXSGAPIMessageQueueName(m_PeerPlayer.GetUserID());

		//#ifdef DEBUG
		//	NSLog(@"XAWSGameServiceManager StartGamePlayMessageService peer's gameplay Q's ID:%@", peerPlayerGamePlayQName);
		//#endif
		this.ClearCachedInvitationData();

		m_UnifiedGameMessageService.ConnectSendMessageQueue(peerPlayerGamePlayQName);
		m_GameServiceState = XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_GAMEPLAYING;
	}

	public void SendGamePlayConnectedMessageToPeer()
	{
		if(m_UnifiedGameMessageService != null && m_UnifiedGameMessageService.CanSendMessage() == true && m_LocalPlayer != null)
		{
			//#ifdef DEBUG
			//	NSLog(@"XAWSGameServiceManager SendGamePlayConnectedMessageToPeer peer player ID:%@", [m_PeerPlayer GetUserID]);
			//	NSLog(@"XAWSGameServiceManager SendGamePlayConnectedMessageToPeer peer player Name:%@", [m_PeerPlayer GetUserName]);
			//	NSLog(@"XAWSGameServiceManager SendGamePlayConnectedMessageToPeer peer player Type:%i", [m_PeerPlayer GetUserType]);
			//#endif
			String msg = XSGAPIMessageFormatter.FormatConnectMessage(m_LocalPlayer);
			m_UnifiedGameMessageService.SendMessage(msg);
		}
	}

	public void SendGamePlayDisconnectedMessageToPeer()
	{
		if(m_UnifiedGameMessageService != null && m_UnifiedGameMessageService.CanSendMessage() == true && m_LocalPlayer != null && m_PeerPlayer != null)
		{
			//#ifdef DEBUG
			//	NSLog(@"XAWSGameServiceManager SendGamePlayDisconnectedMessageToPeer peer player ID:%@", [m_PeerPlayer GetUserID]);
			//	NSLog(@"XAWSGameServiceManager SendGamePlayDisconnectedMessageToPeer peer player Name:%@", [m_PeerPlayer GetUserName]);
			//	NSLog(@"XAWSGameServiceManager SendGamePlayDisconnectedMessageToPeer peer player Type:%i", [m_PeerPlayer GetUserType]);
			//#endif
			String msg = XSGAPIMessageFormatter.FormatDisconnectMessage(m_LocalPlayer);
			String cachedQueue = m_UnifiedGameMessageService.GetCachedSendQueuURL();
			m_UnifiedGameMessageService.SendMessageToCachedQueue(msg, cachedQueue);//SendMessage(msg);
		}
	}

	public void SendGamePlayingMessage(String pMsg)
	{
		if(m_UnifiedGameMessageService != null && m_UnifiedGameMessageService.CanSendMessage() == true && m_LocalPlayer != null && pMsg != null)
		{
			//#ifdef DEBUG
			//	NSLog(@"XAWSGameServiceManager SendGamePlayingMessge msg:%@", pMsg);
			//	NSLog(@"XAWSGameServiceManager SendGamePlayingMessge peer player ID:%@", [m_PeerPlayer GetUserID]);
			//	NSLog(@"XAWSGameServiceManager SendGamePlayingMessge peer player Name:%@", [m_PeerPlayer GetUserName]);
			//	NSLog(@"XAWSGameServiceManager SendGamePlayingMessge peer player Type:%i", [m_PeerPlayer GetUserType]);
			//#endif
			String msg = XSGAPIMessageFormatter.FormatGamePlayMessage(m_LocalPlayer, pMsg);
			m_UnifiedGameMessageService.SendMessage(msg);
		}
	}

	public boolean InOnlineGamePlay()
	{
		boolean bRet = false;

		if(m_GameServiceState == XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_GAMEPLAYING)
			bRet = true;

		return bRet;
	}

	public void EndGamePlaying()
	{
		//#ifdef DEBUG
		//	NSLog(@"XAWSGameServiceManager EndGamePlaying");
		//#endif
		m_GameServiceState = XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_IDLE;
		m_UnifiedGameMessageService.DisConnectSendMessageQueue();
		if(m_PeerPlayer != null)
		{
			m_PeerPlayer = null;
		}
		this.RegiserLocalPlayerToGameUserDomain();
	}
	
	public void AbsoultExitGameService()
	{
		//#ifdef DEBUG
		//	NSLog(@"XAWSGameServiceManager AbsoultExitGameService");
		//#endif
		m_GameServiceState = XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_IDLE;
		m_UnifiedGameMessageService.DisConnectSendMessageQueue();
		if(m_PeerPlayer != null)
		{
			m_PeerPlayer = null;
		}
		this.RemoveLocalPlayerFromGameUserDomain();
	}
////////////////////////////////////////////////////////////
	
//////////////////////////////////////////////////////////
//
//XAWSPlayMessageManagerDelegate methods
//
//////////////////////////////////////////////////////////
	public void GamePlayConnectedToPeer(XSGAPIUser pUser)
	{
		if(pUser == null)
		{
			//#ifdef DEBUG
			//NSLog(@"GamePlayConnectedToPeer from unknow player");
			//#endif
			return;
		}
		//#ifdef DEBUG
		//	NSLog(@"GamePlayConnectedToPeer to peer %@", [pUser GetUserID]);
		//#endif
		this.ClearCachedInvitationData();
		if(m_PeerPlayer == null)
		{
			m_PeerPlayer = pUser;
		}
		if(m_PeerPlayer.GetUserID().equals(pUser.GetUserID()) == false)
		{
			//#ifdef DEBUG
			//NSLog(@"GamePlayConnectedToPeer from the player not same as invited one");
			//#endif
			return;
		}
		if(m_GameServiceState != XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_GAMEPLAYING)
			m_GameServiceState = XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_GAMEPLAYING;

		if(this._m_Delegate != null)
		{
			this._m_Delegate.XSGAPIGamePlayPeerConnected();
		}
	}
	
	public void GamePlayDisconnectedFromPeer(XSGAPIUser pUser)
	{
		if(pUser == null)
		{
			//#ifdef DEBUG
			//	NSLog(@"GamePlayDisconnectedFromPeer from unknow player");
			//#endif
			return;
		}
		//#ifdef DEBUG
		//	NSLog(@"GamePlayDisconnectedFromPeer to peer %@", [pUser GetUserID]);
		//#endif
		if(m_PeerPlayer == null || m_PeerPlayer.GetUserID() == null || m_PeerPlayer.GetUserID().equals(pUser.GetUserID()) == false)
		{
			//#ifdef DEBUG
			//	NSLog(@"GamePlayDisconnectedFromPeer from the player not same as invited one");
			//#endif
			return;
		}
		this.EndGamePlaying();
		if(this._m_Delegate != null)
		{
			this._m_Delegate.XSGAPIGamePlayPeerdisconnected();
		}
	}	

	public void HandleXSGAPIGamePlayMessage(String pMsg, XSGAPIUser pUser)
	{
		if(pUser == null)
		{
			//#ifdef DEBUG
			//	NSLog(@"HandleAWSGamePlayMessage from unknow player");
			//#endif
			return;
		}

		if(pMsg == null)
		{
			//#ifdef DEBUG
			//	NSLog(@"HandleAWSGamePlayMessage invalid game play message from %@(%@).", [pUser GetUserID], [pUser GetUserName]);
			//#endif
			return;
		}

		//#ifdef DEBUG
		//	NSLog(@"HandleAWSGamePlayMessage from peer %@ with message:\n%@", [pUser GetUserID], pMsg);
		//#endif

		if(m_PeerPlayer == null)
		{
			m_PeerPlayer = pUser;
		}
		
		if(m_PeerPlayer.GetUserID().equals(pUser.GetUserID()) == false)
		{
			//#ifdef DEBUG
			//	NSLog(@"HandleAWSGamePlayMessage from the player not same as invited one");
			//#endif
			return;
		}

		if(m_GameServiceState != XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_GAMEPLAYING)
			m_GameServiceState = XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_GAMEPLAYING;

		if(this._m_Delegate != null)
		{
			this._m_Delegate.XSGAPIHandleGamePlayPeerMessage(pMsg, pUser);
		}
	}
	
	@Override
	public void XSGAPIMessageServiceQueueConnected()
	{
		
	}
	
	@Override
	public void XSGAPIMessageServiceStarted()
	{
		
	}
	
	public void XSGAPIRecieveMessageServiceStopped()
	{
	//#ifdef DEBUG
	//    NSLog(@"XAWSGameServiceManager AWSRecieveMessageServiceStopped");
	//#endif
	//    if(self->_m_Delegate && [self->_m_Delegate respondsToSelector:@selector(AWSRecievePlayMessageServiceStopped)])
	//    {
	//        [self->_m_Delegate performSelector:@selector(AWSRecievePlayMessageServiceStopped)];
	//   }
	}

	public void XSGAPIRecieveMessageQueueConnected()
	{
	//#ifdef DEBUG
	//    NSLog(@"XAWSGameServiceManager AWSRecieveMessageQueueConnected");
	//#endif
	//    if(self->_m_Delegate && [self->_m_Delegate respondsToSelector:@selector(AWSRecievePlayMessageQueueConnected)])
	//    {
	//        [self->_m_Delegate performSelector:@selector(AWSRecievePlayMessageQueueConnected)];
	//    }
	}

	public void XSGAPIRecieveMessageServiceStarted()
	{
	//#ifdef DEBUG
	//    NSLog(@"XAWSGameServiceManager AWSRecieveMessageServiceStarted");
	//#endif
	//    if(self->_m_Delegate && [self->_m_Delegate respondsToSelector:@selector(AWSRecievePlayMessageServiceStarted)])
	//    {
	//        [self->_m_Delegate performSelector:@selector(AWSRecievePlayMessageServiceStarted)];
	//    }
	}

	public void XSGAPIRecieveMessageServiceDisconnected()
	{
	    //if(self->_m_Delegate && [self->_m_Delegate respondsToSelector:@selector(AWSRecievePlayMessageServiceDisconnected)])
	    //{
	    //    [self->_m_Delegate performSelector:@selector(AWSRecievePlayMessageServiceDisconnected)];
	   // }
	}
//////////////////////////////////////////////////////////


//////////////////////////////////////////////////////////
//
//XAWSInvitationMessageDelegate methods
//
//////////////////////////////////////////////////////////
	public void InvitationRecievedFrom(XSGAPIUser sender)
	{
		if(sender == null || sender.GetUserID() == null)
			return;
		
	    if(m_bDonotAcceptInvitation == true)
	    {
	        this.SendRejectInvitationTo(sender.GetUserID());
	        return;
	    }
	    
	    if(m_GameServiceState == XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_QUERYONLINEUSERLIST || 
	    		m_GameServiceState == XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_IDLE ||  
	    		m_GameServiceState == XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_UNKNOWN)
	        m_GameServiceState = XSGAPIConstants.XSGAPIGAMEMANAGER_STATE_INVITATIONPROCESS;
	    
	    int nSendCount = _m_InvitationSentList.size();
	    int nGetCount = _m_InvitationReceivedList.size();
	    
	    //Already sent/recieved invitation and waiting for next steps
	    if(0 < nSendCount || 0 < nGetCount)
	    {
	        if(sender != null)
	        {
	            int nSentIndex = this.SentInvitationIndexTo(sender.GetUserID());
	            int nGetIndex = this.ReceivedInvitationIndexFrom(sender.GetUserID());
	            
	            //If already send invitation
	            if(0 <= nSentIndex)
	            {
	                String szData = this._m_Delegate.GetInvitationReceiverInitialData();
	                this.SendAcceptInvitationToPeer2(sender, szData);
	                return;
	            }
	            else if(0 <= nGetIndex && this._m_Delegate != null)
	            {
	            	this._m_Delegate.XSGAPIRecievedNewInvitationFrom(sender);
	                return;
	            }
	            else if(nGetIndex < 0 && nSentIndex < 0)
	            {
	                //If not send invitation to this peer and also not receive from this peer, then reject
	                this.SendRejectInvitationTo(sender.GetUserID());
	                return;
	            }
	        }
	    }
	    
	    if(sender != null)
	    {
	        if(this.ReceivedInvitationIndexFrom(sender.GetUserID()) < 0)
	        {
	            _m_InvitationReceivedList.add(sender);
	            if(this._m_Delegate != null)
	            {
	            	this._m_Delegate.XSGAPIRecievedNewInvitationFrom(sender);
	            }
	        }
	    }
	}

	public void InvitationCancelledBy(XSGAPIUser sender)
	{
	    if(sender != null)
	    {
	        int nIndex = this.ReceivedInvitationIndexFrom(sender.GetUserID());
	        if(0 <= nIndex  || (m_PeerPlayer != null && m_PeerPlayer.GetUserID().equals(sender.GetUserID()) == true))
	        {
	        	if(0 <= nIndex)
	        			_m_InvitationReceivedList.remove(nIndex);
	            nIndex = this.SentInvitationIndexTo(sender.GetUserID());
	            if(0 <= nIndex)
	                _m_InvitationSentList.remove(nIndex);
	            
	            //[self UpdateDonnotAcceptInvitationState];
	            this.UpdateInvitationProcessState();
	            if(this._m_Delegate != null)
	            {
	            	this._m_Delegate.XSGAPIInvitationCancelledBy(sender);
	            }
	        }
	    }
	}

	public void PostHandleInvitationAccepted(XSGAPIUser user)
	{
	    //Start Game play recieving service
	    //(Once recieving queue created, send shake hand message to peer)
		//#ifdef DEBUG
	    //	NSLog(@"PostHandleInvitationAccepted %@", [user GetUserID]);
		//#endif    
	    this.UpdateInvitationProcessState();
	    if(m_PeerPlayer != null)
	    {
	        m_PeerPlayer = null;
	    }
	    m_PeerPlayer = user;
	    
	    this.StartGamePlayMessageService();
	    //[self SendGamePlayConnectedMessageToPeer];
	    //[self RemoveLocalPlayerFromGameUserDomain];

	    if(this._m_Delegate != null)
	    {
	    	this._m_Delegate.XSGAPISentInvitationAcceptedBy(user);
	    }
	}

	/*
	-(void)PrepareOnlineGameWithPeer:(XAWSUser*)user
	{
	    //Shutdown Online user DB domain connection
	    //Disconnect invitation sending/recieving
	    //Connect game play sending queue to peer game play recieving queue
	    //Send game connected messge to peer game play recieving queue.
	    
	    //[self ]
	}
	*/

	//
	//Called by meesage parsing function for handling invitation accepted
	public void SentInvitationAcceptedBy(XSGAPIUser user)
	{
	    if(user != null)
	    {
	//#ifdef DEBUG
	//        NSLog(@"Invitation accepted by %@", [user GetUserID]);
	//#endif
	        
	        int nIndex = this.SentInvitationIndexTo(user.GetUserID());
	        if(0 <= nIndex)
	        {
	            _m_InvitationSentList.remove(nIndex);
	            this.PostHandleInvitationAccepted(user);
	        }
	    }
	}

	public void SentInvitationRejectedBy(XSGAPIUser user)
	{
	    if(user != null)
	    {
	//#ifdef DEBUG
	//        NSLog(@"Invitation rejected by %@", [user GetUserID]);
	//#endif
	        
	        int nIndex = this.SentInvitationIndexTo(user.GetUserID());
	        if(0 <= nIndex)
	        {
	            _m_InvitationSentList.remove(nIndex);
	        }
	        nIndex = this.ReceivedInvitationIndexFrom(user.GetUserID());
	        if(0 <= nIndex)
	        {
	            _m_InvitationReceivedList.remove(nIndex);
	        }
	        this.UpdateInvitationProcessState();
	        m_UnifiedGameMessageService.PostHandleSentInvitationRejectedBy(user.GetUserID());
		    if(this._m_Delegate != null)
	        {
		    	this._m_Delegate.XSGAPISentInvitationRejectedBy(user);
	        }
	    }
	}

	public void TranslateAWSUnifiedMessage(Message message)
	{
	//#ifdef DEBUG
	//    NSLog(@"XAWSGameServiceManager TranslateAWSUnifiedMessage");
	//#endif
	    if(message != null && message.getBody() != null)
	    {
	        int nMsgType = XSGAPIMessageParser.ParseMessageType(message.getBody());
	        if(nMsgType != XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVALID)
	        {
	            XSGAPIUser pUser = XSGAPIMessageParser.ParseMessageSender(message.getBody());
	            switch(nMsgType)
	            {
	                case XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVITATION_INVITATION:
	                {
	                	String szData = XSGAPIMessageParser.ParsePeerInitialData(message.getBody());
	                    if(szData != null && 0 < szData.length())
	                    {
	                        m_PeerInitialData = szData;
	                    }
	                    else
	                    {
	                        if(m_PeerInitialData != null)
	                        {
	                            m_PeerInitialData = null;
	                        }
	                    }
	                	
	                    this.InvitationRecievedFrom(pUser);
	                }    
	                    break;
	                case XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVITATION_CANCELLATION:
	                    this.InvitationCancelledBy(pUser);
	                    break;
	                case XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVITATION_ACCEPTION:
	                { 
	                	String szData = XSGAPIMessageParser.ParsePeerInitialData(message.getBody());
	                    if(szData != null && 0 < szData.length())
	                    {
	                        m_PeerInitialData = szData;
	                    }
	                    else
	                    {
	                        if(m_PeerInitialData != null)
	                        {
	                            m_PeerInitialData = null;
	                        }
	                    }
	                    this.SentInvitationAcceptedBy(pUser);
	                } 
	                    break;
	                case XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVITATION_REJECTION:
	                    this.SentInvitationRejectedBy(pUser);
	                    break;
	                case XSGAPIMessage.XSGAPI_MESSAGE_TYPE_GAMEPLAY_CONNECTED:
	                    this.GamePlayConnectedToPeer(pUser);
	                    break;
	                case XSGAPIMessage.XSGAPI_MESSAGE_TYPE_GAMEPLAY_DISCONNECTED:
	                    this.GamePlayDisconnectedFromPeer(pUser);
	                    break;
	                case XSGAPIMessage.XSGAPI_MESSAGE_TYPE_GAMEPLAY_GAMEMESSAGE:
	                {
	                    String pMsg = XSGAPIMessageParser.ParseGamePlayMessage(message.getBody());;
	                    if(pMsg != null)
	                    {
	                        this.HandleXSGAPIGamePlayMessage(pMsg, pUser);
	                    }
	                }
	                    break;
	                default:
	                    break;
	            }
	        }
	    }
	}
	
	
	
////////////////////////////////////////////////////
//
//XSGAPIMessagingManagerDelegate
//
////////////////////////////////////////////////////
	//Receiving message service handler methods
	@Override
	public void HandleXSGAPIUnifiedMessage(List<Message> msglist)
	{
		//#ifdef DEBUG
	    //NSLog(@"XAWSGameServiceManager HandleAWSMessage");
		//#endif
	    if(msglist != null && 0 < msglist.size())
	    {
	        for(int i = 0; i < msglist.size(); ++i)
	        {
	            Message msg = msglist.get(i);
	            if(msg != null)
	                this.TranslateAWSUnifiedMessage(msg);
	        }
	    }
		
	}
	
	@Override
	public void TranslateXSAPIMessage(Message message)
	{
	    if(message != null && message.getBody() != null)
	    {
	    	XSGAPIMessageParser parser = new XSGAPIMessageParser(message.getBody()); 
	    	int nMsgType = parser.GetMessageType();
	        if(nMsgType != XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVALID)
	        {
	            XSGAPIUser pUser = parser.GetMessageSender();
	            switch(nMsgType)
	            {
	                case XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVITATION_INVITATION:
	                {
	                	String szData = XSGAPIMessageParser.ParsePeerInitialData(message.getBody());
	                    if(szData != null && 0 < szData.length())
	                    {
	                        m_PeerInitialData = szData;
	                    }
	                    else
	                    {
	                        if(m_PeerInitialData != null)
	                        {
	                            m_PeerInitialData = null;
	                        }
	                    }
	                	
	                    this.InvitationRecievedFrom(pUser);
	                }    
	                    break;
	                case XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVITATION_CANCELLATION:
	                    this.InvitationCancelledBy(pUser);
	                    break;
	                case XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVITATION_ACCEPTION:
	                {
	                	String szData = XSGAPIMessageParser.ParsePeerInitialData(message.getBody());
	                    if(szData != null && 0 < szData.length())
	                    {
	                        m_PeerInitialData = szData;
	                    }
	                    else
	                    {
	                        if(m_PeerInitialData != null)
	                        {
	                            m_PeerInitialData = null;
	                        }
	                    }
	                    this.SentInvitationAcceptedBy(pUser);
	                }  
	                    break;
	                case XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVITATION_REJECTION:
	                    this.SentInvitationRejectedBy(pUser);
	                    break;
	                case XSGAPIMessage.XSGAPI_MESSAGE_TYPE_GAMEPLAY_CONNECTED:
	                    this.GamePlayConnectedToPeer(pUser);
	                    break;
	                case XSGAPIMessage.XSGAPI_MESSAGE_TYPE_GAMEPLAY_DISCONNECTED:
	                    this.GamePlayDisconnectedFromPeer(pUser);
	                    break;
	                case XSGAPIMessage.XSGAPI_MESSAGE_TYPE_GAMEPLAY_GAMEMESSAGE:
	                {
	                    String pMsg = parser.GetGamePlayMessage();
	                    if(pMsg != null)
	                    {
	                        this.HandleXSGAPIGamePlayMessage(pMsg, pUser);
	                    }
	                }
	                    break;
	                default:
	                    break;
	            }
	        }
	    }
	}
	
	@Override
	public void TranslateRawXSAPIMessage(String message)
	{
	    if(message != null)
	    {
	    	XSGAPIMessageParser parser = new XSGAPIMessageParser(message); 
	    	int nMsgType = parser.GetMessageType();
	        if(nMsgType != XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVALID)
	        {
	            XSGAPIUser pUser = parser.GetMessageSender();
	            switch(nMsgType)
	            {
	                case XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVITATION_INVITATION:
	                {
	                	String szData = parser.GetPeerInitialData();
	                    if(szData != null && 0 < szData.length())
	                    {
	                        m_PeerInitialData = szData;
	                    }
	                    else
	                    {
	                        if(m_PeerInitialData != null)
	                        {
	                            m_PeerInitialData = null;
	                        }
	                    }
	                    this.InvitationRecievedFrom(pUser);
	                }     
	                    break;
	                case XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVITATION_CANCELLATION:
	                    this.InvitationCancelledBy(pUser);
	                    break;
	                case XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVITATION_ACCEPTION:
	                {
	                	String szData = parser.GetPeerInitialData();
	                    if(szData != null && 0 < szData.length())
	                    {
	                        m_PeerInitialData = szData;
	                    }
	                    else
	                    {
	                        if(m_PeerInitialData != null)
	                        {
	                            m_PeerInitialData = null;
	                        }
	                    }
	                    
	                	this.SentInvitationAcceptedBy(pUser);
	                }	
	                    break;
	                case XSGAPIMessage.XSGAPI_MESSAGE_TYPE_INVITATION_REJECTION:
	                    this.SentInvitationRejectedBy(pUser);
	                    break;
	                case XSGAPIMessage.XSGAPI_MESSAGE_TYPE_GAMEPLAY_CONNECTED:
	                    this.GamePlayConnectedToPeer(pUser);
	                    break;
	                case XSGAPIMessage.XSGAPI_MESSAGE_TYPE_GAMEPLAY_DISCONNECTED:
	                    this.GamePlayDisconnectedFromPeer(pUser);
	                    break;
	                case XSGAPIMessage.XSGAPI_MESSAGE_TYPE_GAMEPLAY_GAMEMESSAGE:
	                {
	                    String pMsg = parser.GetGamePlayMessage();
	                    if(pMsg != null)
	                    {
	                        this.HandleXSGAPIGamePlayMessage(pMsg, pUser);
	                    }
	                }
	                    break;
	                default:
	                    break;
	            }
	        }
	    }
	}

	
	private void HandleSendingAcceptInvitationMessageDone()
	{
	    this.StartGamePlayMessageService();
	    //[self SendGamePlayConnectedMessageToPeer];
	    //[self RemoveLocalPlayerFromGameUserDomain];
	    if(this._m_Delegate != null)
	    {
	    	this._m_Delegate.XSGAPISendingAcceptInvitionMessageDone();
	    }
	}

	
	//Auto response message service handler methods
	@Override
	public void AutoResponseDone(int nResponseType)
	{
        switch(nResponseType)
        {
            case XSGAPIMessage.XSGAPI_MSG_AUTO_INVITE:
                {
                	if(this._m_Delegate != null)
                    {
                		this._m_Delegate.XSGAPISendingSendInvitionMessageDone();
                    }
                }
                break;
            case XSGAPIMessage.XSGAPI_MSG_AUTO_CANCEL:
                {
                	if(this._m_Delegate != null)
                    {
                		this._m_Delegate.XSGAPISendingCancelInvitionMessageDone();
                    }
                }
                break;
            case XSGAPIMessage.XSGAPI_MSG_AUTO_ACCEPT:
                {
                    this.HandleSendingAcceptInvitationMessageDone();
                }
                break;
            case XSGAPIMessage.XSGAPI_MSG_AUTO_REJECT:
                {
                	if(this._m_Delegate != null)
                    {
                		this._m_Delegate.XSGAPISendingRejectInvitionMessageDone();
                    }
                }
                break;
            default:
                break;
        }		
	}
	
	@Override
	public void AutoResponseFailed(int nResponseType)
	{
		
	}
	
	@Override
	public void XSGAPISendMessageServiceQueueConnected()
	{
	//#ifdef DEBUG
	//    NSLog(@"XAWSGameServiceManager AWSSendMessageServiceQueueConnected");
	//#endif
	    this.SendGamePlayConnectedMessageToPeer();
	    this.RemoveLocalPlayerFromGameUserDomain();
	    if(this._m_Delegate != null)
	    {
	    	this._m_Delegate.XSGAPISendPlayMessageQueueConnected();
	    }
	}
	
	@Override
	public void XSGAPISendMessageServiceQueueConnectionFailed()
	{
		
	}
	
	////////////////////////////////////////////////////
	//
	//XSGAPIUserDBServiceDelegate methods
	//
	////////////////////////////////////////////////////
	@Override
	public void ConnectDBDomainDone() //For nofitfication of Connecting DB domain
	{
	    if(this._m_Delegate != null)
	    {
	    	this._m_Delegate.XSGAPIConnectDBDomainDone();
	    }
	}
	
	@Override
	public void ConnectDBDomainFailed() //For nofitfication of Connecting DB domain
	{
	    if(this._m_Delegate != null)
	    {
	    	this._m_Delegate.XSGAPIConnectDBDomainFailed();
	    }
	}
	
	@Override
	public void RegisterUserDone()
	{
	    if(this._m_Delegate != null)
	    {
	    	this._m_Delegate.XSGAPIRegisterUserDone();
	    }
	}
	
	@Override
	public void RegisterUserFailed()
	{
	    if(this._m_Delegate != null)
	    {
	    	this._m_Delegate.XSGAPIRegisterUserFailed();
	    }
	}
	
	@Override
	public void UnRegisterUserDone()
	{
	    if(this._m_Delegate != null)
	    {
	    	this._m_Delegate.XSGAPIUnRegisterUserDone();
	    }
	}
	
	@Override
	public void UnRegisterUserFailed()
	{
	    if(this._m_Delegate != null)
	    {
	    	this._m_Delegate.XSGAPIUnRegisterUserFailed();
	    }
	}
	
	public void DisconnectDBDomainDone()
	{
	    if(this._m_Delegate != null)
	    {
	    	this._m_Delegate.XSGAPIDisconnectDBDomainDone();
	    }
	}
	
	public void QueryUserCountOperationDone(int nCount)
	{
		this._m_RegisteredOnlineUserCount = nCount;
	    if(this._m_Delegate != null)
	    {
	    	this._m_Delegate.XSGAPIQueryUserCountOperationDone();
	    }
	}
	
	public void QueryBeginUserListOperationDone(List<XSGAPIUser> pList)
	{
		if((pList != null || pList != Collections.EMPTY_LIST) && 0 < pList.size())
		{
			this._m_RegisteredOnlineUserList.addAll(pList);
			this._m_bStillHaveUsers = true;
		}
		else
		{
			this._m_bStillHaveUsers = false;
		}
	    if(this._m_Delegate != null)
	    {
	    	this._m_Delegate.XSGAPIQueryBeginUserListOperationDone();
	    }
	}
	
	public void QueryNextUserListOperationDone(List<XSGAPIUser> pList)
	{
		if((pList != null || pList != Collections.EMPTY_LIST) && 0 < pList.size())
		{
			this._m_RegisteredOnlineUserList.addAll(pList);
			this._m_bStillHaveUsers = true;
		}
		else
		{
			this._m_bStillHaveUsers = false;
		}
	    if(this._m_Delegate != null)
	    {
	    	this._m_Delegate.XSGAPIQueryNextUserListOperationDone();
	    }
	}

	
}
