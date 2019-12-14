package com.xgadget.XSGService;

public class XSGAPIUser 
{
	public static final String XSGAPI_PLAYER_ID_ATTRIBUTE = "XAWS_PLAYER_ID_TAG";
	public static final String XSGAPI_PLAYER_NAME_ATTRIBUTE = "XAWS_PLAYER_NAME_TAG";
	public static final String XSGAPI_USER_TYPE_ATTRIBUTE = "XAWS_USER_TYPE_TAG";	
    
	private String  	_m_PlayerID;
    private String   	_m_PlayerName;
    private int         _m_UserType;

    private XSGAPIUserDelegate    m_Delegate;
	
    private static XSGAPIUser    g_SharedLocalUser = null;
   
    public synchronized static XSGAPIUser localUser()
    {
        if(g_SharedLocalUser == null)
        {
             g_SharedLocalUser = new XSGAPIUser();
        }
        
        return g_SharedLocalUser;
    }

    public synchronized static void registerLocalUserDelegate(XSGAPIUserDelegate delegate)
    {
        if(g_SharedLocalUser != null)
            g_SharedLocalUser.RegisterDelegate(delegate);
        else
        {
            g_SharedLocalUser = new XSGAPIUser();
            g_SharedLocalUser.RegisterDelegate(delegate);
        }
    }
    
	public XSGAPIUser() 
	{
		// TODO Auto-generated constructor stub
	    _m_PlayerID = null;
	    _m_PlayerName = null;
	    _m_UserType = XSGAPIReleaseConfigure.GetCurrentDeviceType();

	    m_Delegate = null;
	}

	public XSGAPIUser(String szPlayerID, String szPlayerName, int userType)
	{
	     _m_PlayerName = szPlayerName;
	     _m_PlayerID = szPlayerID;
	     _m_UserType = userType;
	     m_Delegate = null;
	}    		 
	    		 
	/*-(id)initFromData:(NSDictionary*) msgData
	{
	    self = [super init];
	    if(self)
	    {
	        self->_m_PlayerName = nil;
	        self->_m_PlayerID = nil;
	        self->_m_UserType = XSGAPIUSER_DEVICETYPE_DEFAULT;
	        m_Delegate = nil;
	        if(msgData)
	        {
	            self->_m_PlayerID = [[msgData valueForKey:XSGAPI_MESSAGE_SENDER_ID_TAG] copy];
	            self->_m_PlayerName = [[msgData valueForKey:XSGAPI_MESSAGE_SENDER_NAME_TAG] copy];
	            NSNumber* number = [msgData valueForKey:XSGAPI_MESSAGE_SENDER_DEVICE_TYPE_TAG];
	            if(number)
	            {
	                self->_m_UserType = [number intValue];
	            }
	        }
	    }
	    
	    return self;
	}*/

	public XSGAPIUser Clone()
	{
	    XSGAPIUser pRet = new XSGAPIUser(_m_PlayerID, _m_PlayerName, _m_UserType);
	    pRet.RegisterDelegate(m_Delegate);
	    return pRet;
	}
	
	
	public void RegisterDelegate(XSGAPIUserDelegate delegate)
	{
	    m_Delegate = delegate;
	    if(m_Delegate != null)
	    {
	    	this.LoadCurrentUserInformation();
	    }
	}

	public boolean LoadCurrentUserInformation()
	{
		boolean bRet = false;
	    
	    if(m_Delegate != null)
	    {
	        this._m_PlayerName = m_Delegate.GetCurrentUserName();
	        this._m_PlayerID = m_Delegate.GetCurrentUserID();
	        this._m_UserType = m_Delegate.GetCurrentUserType();
	    }
	    
	    return bRet;
	}

	public String GetUserName()
	{
	    return this._m_PlayerName;
	}

	public String GetUserID()
	{
	    return this._m_PlayerID;
	}

	public int GetUserType()
	{
	    return this._m_UserType;
	}

	public boolean IsIdenticalTo(XSGAPIUser user)
	{
	    boolean bRet = false;
	    if(user != null && _m_PlayerID != null && _m_PlayerName != null && _m_PlayerID.equals(user.GetUserID()) == true && _m_PlayerName.equals(user.GetUserName()) == true && _m_UserType == user.GetUserType())
	    {
	        bRet = true;
	    }
	        
	    return bRet;
	}
	
}
