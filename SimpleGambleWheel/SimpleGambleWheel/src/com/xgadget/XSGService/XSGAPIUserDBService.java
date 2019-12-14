package com.xgadget.XSGService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.services.simpledb.AmazonSimpleDBAsyncClient;
//import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.DeleteAttributesRequest;
import com.amazonaws.services.simpledb.model.DeleteDomainRequest;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.amazonaws.services.simpledb.model.SelectResult;

public class XSGAPIUserDBService 
{
	//protected AmazonSimpleDBClient 				_m_SDBClient;
	protected  AmazonSimpleDBAsyncClient		_m_SDBClient;
	protected String               				_m_DBDomain;
	protected String               				_m_NextGeneralToken;
	protected XSGAPIUser               			_m_CachedUserInfo;
	protected String               				_m_CachedUserID;
	protected XSGAPIUserDBServiceDelegate      	_m_Delegate;
	
	public XSGAPIUserDBService() 
	{
		// TODO Auto-generated constructor stub
		AWSCredentials credentials = new BasicAWSCredentials(XSGAPIConstants.XGADGET_ACCESS_KEY_ID, XSGAPIConstants.XGADGET_SECRET_KEY );
		_m_SDBClient = new AmazonSimpleDBAsyncClient( credentials); 
		_m_DBDomain = null;
		_m_NextGeneralToken = null;
		_m_CachedUserInfo = null;
		_m_CachedUserID = null;
		_m_Delegate = null;
	}

	protected XSGAPIUser convertSimpleDBItemToUser(Item theItem)
	{
		XSGAPIUser pUser = null;
		
		if(theItem != null && theItem.getAttributes() != null)
		{	
			pUser = new XSGAPIUser(this.getStringValueForAttribute(XSGAPIUser.XSGAPI_PLAYER_ID_ATTRIBUTE, theItem.getAttributes()), 
					this.getStringValueForAttribute(XSGAPIUser.XSGAPI_PLAYER_NAME_ATTRIBUTE, theItem.getAttributes()),
					this.getIntValueForAttribute(XSGAPIUser.XSGAPI_USER_TYPE_ATTRIBUTE, theItem.getAttributes())); 
		}
		
		return pUser;
	}

	protected List<XSGAPIUser> convertItemsToUserList(List<Item> theItems)
	{
		List<XSGAPIUser> scores = null;
		if(theItems != null && 0 < theItems.size())
		{	
			scores = new ArrayList<XSGAPIUser>( theItems.size() );
			for ( Item item : theItems) 
			{
				scores.add( this.convertSimpleDBItemToUser( item ) );
			}
		}
		return scores;
	}	
	
    /*
     * Extracts the value for the given attribute from the list of attributes.
     * Extracted value is returned as a String.
     */
	protected String getStringValueForAttribute( String attributeName, List<Attribute> attributes ) 
	{
		for ( Attribute attribute : attributes ) 
		{
			if ( attribute.getName().equals( attributeName ) )
			{
				return attribute.getValue();
			}
		}
		
		return "";		
	}
	
    /*
     * Extracts the value for the given attribute from the list of attributes.
     * Extracted value is returned as an int.
     */
	protected int getIntValueForAttribute(String attributeName, List<Attribute> attributes) 
	{
		for ( Attribute attribute : attributes ) 
		{
			if ( attribute.getName().equals( attributeName ) )
			{
				return Integer.parseInt( attribute.getValue() );
			}
		}
		
		return 0;		
	}	
	
	public boolean IsConnectDBDomain()
	{
	    boolean bRet = false;
	    if(_m_SDBClient != null && _m_DBDomain != null)
	        bRet = true;

	    return bRet;
	}

	public boolean IsSameDBDomain(String dbDomain)
	{
	    boolean bRet = false;
	    if(_m_SDBClient != null && _m_DBDomain != null)
	    {
	        bRet = _m_DBDomain.equals(dbDomain);
	    }
	    
	    return bRet;
	}
	

	public void CreateDBDomain() throws AmazonServiceException
	{
	    if(_m_DBDomain != null && _m_SDBClient != null)
	    {
	    	try 
	    	{
	    		CreateDomainRequest createDomain = new CreateDomainRequest( _m_DBDomain );
	    		 AsyncHandler<CreateDomainRequest,Void> asyncHandler = new  AsyncHandler<CreateDomainRequest,Void>()
	    		{
	    			@Override
					public void onError(Exception exception)
	    			{
	    				Log.w( " CreateDBDomain() EXCEPTION = ", exception.toString() );
	    		        ConnectDBDomainFailed();
	    		        return;
	    			}
	    			@Override
	    			public void onSuccess(CreateDomainRequest param1, Void param2)
	    			{
	    				Log.w( "CreateDBDomain() Sucessed", "ConnectDBDomainDone");
	    				ConnectDBDomainDone();
	    			}
	    		};
	    		
	    		this._m_SDBClient.createDomainAsync(createDomain, asyncHandler); //createDomain(createDomain);
	    	}
	    	catch (Throwable e) 
	    	{
				Log.w( " CreateDBDomain() EXCEPTION = ", e.toString() );
		        ConnectDBDomainFailed();
		        return;
			}
	    }
	}

	public void SetDomain(String dbDomain)
	{
	    _m_DBDomain = dbDomain;
	}

	public void SetDelegate(XSGAPIUserDBServiceDelegate delegate)
	{
	    _m_Delegate = delegate;
	}

	public void RegisterUser(XSGAPIUser userInfo)
	{
	    if(_m_DBDomain != null && _m_SDBClient != null && userInfo != null)
	    {
	        try
	        {
	        	int nType = userInfo.GetUserType();
				Integer deviceType = Integer.valueOf(nType);
				String szType = deviceType.toString();			

	            ReplaceableAttribute playerIdAttribute = new ReplaceableAttribute(XSGAPIUser.XSGAPI_PLAYER_ID_ATTRIBUTE, userInfo.GetUserID(), Boolean.TRUE);
	            ReplaceableAttribute playerNameAttribute = new ReplaceableAttribute(XSGAPIUser.XSGAPI_PLAYER_NAME_ATTRIBUTE, userInfo.GetUserName(), Boolean.TRUE);
	            ReplaceableAttribute playerTypeAttribute  = new ReplaceableAttribute(XSGAPIUser.XSGAPI_USER_TYPE_ATTRIBUTE, szType, Boolean.TRUE);
	            List<ReplaceableAttribute> attributes = new ArrayList<ReplaceableAttribute>(3);
	            attributes.add(playerIdAttribute);
	            attributes.add(playerNameAttribute);
	            attributes.add(playerTypeAttribute);
	            PutAttributesRequest putAttributesRequest = new PutAttributesRequest(_m_DBDomain, userInfo.GetUserID(), attributes);
	          
	            XSGAPIReleaseConfigure.LogDebugInfo("RegisterUser UserID", userInfo.GetUserID());
	            
	            AsyncHandler<PutAttributesRequest,Void> asyncHandler = new AsyncHandler<PutAttributesRequest,Void>()
	            {
	    			@Override
					public void onError(Exception exception)
	    			{
	    				Log.w( "RegisterUser Exception = ", exception.toString() );
	    				RegisterUserFailed();
	    		        return;
	    			}
	    			@Override
	    			public void onSuccess(PutAttributesRequest param1, Void param2)
	    			{
	    				Log.w( "RegisterUser() Sucessed", "RegisterUserDone");
	    				RegisterUserDone();
	    			}
	            };
	            
	            // _m_SDBClient.putAttributes(putAttributesRequest);
	            _m_SDBClient.putAttributesAsync(putAttributesRequest, asyncHandler);
	        }
	        catch (Exception exception) 
	        {
				System.out.println("RegisterUser Exception = " + exception);
	            this.RegisterUserFailed();
	            return;
	        }
	    }
	}

	public void UnRegisterUser(String userID)
	{
	    if(_m_DBDomain != null && _m_SDBClient != null && userID != null)
	    {
	        try
	        {
	        	DeleteAttributesRequest deleteItem = new DeleteAttributesRequest(_m_DBDomain, userID);
	            //_m_SDBClient.deleteAttributes(deleteItem);
	        	AsyncHandler<DeleteAttributesRequest,Void> asyncHandler = new AsyncHandler<DeleteAttributesRequest,Void>()
	        	{
	    			@Override
					public void onError(Exception exception)
	    			{
	    				Log.w( "UnRegisterUser Exception = ", exception.toString() );
	    				UnRegisterUserFailed();
	    		        return;
	    			}
	    			@Override
	    			public void onSuccess(DeleteAttributesRequest param1, Void param2)
	    			{
	    				Log.w( "UnRegisterUser() Sucessed", "RegisterUserDone");
	    				UnRegisterUserDone();
	    			}
	        	};
	        	_m_SDBClient.deleteAttributesAsync(deleteItem, asyncHandler); 
	        	
	        }
	        catch (Exception exception) 
	        {
				System.out.println("UnRegisterUser Exception = " + exception);
	            this.UnRegisterUserFailed();
	            return;
	        }
	        //this.UnRegisterUserDone();
	    }
	}

	public int GetUserCount()
	{
	    int nCount = 0;
	    
	    if(_m_DBDomain != null && _m_SDBClient != null)
	    {
	        try
	        {
	            String strQuery = "select count(*) from `" + _m_DBDomain + "`";
	            SelectRequest selectRequest = new SelectRequest(strQuery).withConsistentRead( true );
	            
				List<Item> items = this._m_SDBClient.select( selectRequest ).getItems();	
				
				Item countItem = (Item)items.get(0);
				Attribute countAttribute = (Attribute)(((com.amazonaws.services.simpledb.model.Item) countItem).getAttributes().get(0));
				nCount = Integer.parseInt( countAttribute.getValue() );
	        }
	        catch (Exception exception) 
	        {
	        	System.out.println("XSGAPIUserDBService::GetUserCount: Exception : " + exception);
	            return -1;
	        }
	    }
	    
	    return nCount;
	}

	public void GetUserCountAsync()
	{
	    if(_m_DBDomain != null && _m_SDBClient != null)
	    {
	        try
	        {
	            String strQuery = "select count(*) from `" + _m_DBDomain + "`";
	            SelectRequest selectRequest = new SelectRequest(strQuery).withConsistentRead( true );
	            
	            AsyncHandler<SelectRequest,SelectResult> asyncHandler = new AsyncHandler<SelectRequest,SelectResult>()
	            {		
	    			@Override
					public void onError(Exception exception)
	    			{
	    				Log.w( "GetUserCountAsync Exception = ", exception.toString() );
	    				//UnRegisterUserFailed();
	    		        return;
	    			}
	    			@Override
	    			public void onSuccess(SelectRequest request, SelectResult result)
	    			{
	    				Log.w( "GetUserCountAsync() Sucessed", "Done");
	    				if(result != null)
	    				{	
	    					Item countItem = result.getItems().get(0);
	    					Attribute countAttribute = (Attribute)(((com.amazonaws.services.simpledb.model.Item) countItem).getAttributes().get(0));
	    					int nCount = Integer.parseInt( countAttribute.getValue() );
	    					QueryUserCountOperationDone(nCount);
	    				}	
	    				//UnRegisterUserDone();
	    			}
	            };
				this._m_SDBClient.selectAsync(selectRequest, asyncHandler);	
	        }
	        catch (Exception exception) 
	        {
	        	System.out.println("XSGAPIUserDBService::GetUserCount: Exception : " + exception);
	            //return -1;
	        }
	    }
	    
	    //return nCount;
	}

	@SuppressWarnings("unchecked")
	public synchronized List<XSGAPIUser> GetGeneralUserList()
	{
	    if(_m_DBDomain != null && _m_SDBClient != null)
	    {
	        String query = "select * from `"+ _m_DBDomain + "` where `" + XSGAPIUser.XSGAPI_PLAYER_NAME_ATTRIBUTE + "` > '' order by `" + XSGAPIUser.XSGAPI_PLAYER_NAME_ATTRIBUTE +"` asc";
	        try
	        {
	    		SelectRequest selectRequest = new SelectRequest( query ).withConsistentRead( true );
	    		selectRequest.setNextToken( this._m_NextGeneralToken );
	    		
	    		SelectResult response = this._m_SDBClient.select( selectRequest );
	    		this._m_NextGeneralToken = response.getNextToken();
	    		
	    		return this.convertItemsToUserList(response.getItems());	
	        	
	        }
	        catch (Exception exception) 
	        {
	        	System.out.println("XSGAPIUserDBService::GetGeneralUserList: Exception : " + exception);
	            _m_NextGeneralToken = null;
	            return (List<XSGAPIUser>)Collections.EMPTY_LIST;
	        }
	    }
	    _m_NextGeneralToken = null;
	    return (List<XSGAPIUser>)Collections.EMPTY_LIST;
	}


	@SuppressWarnings("unchecked")
	public synchronized List<XSGAPIUser> GetNextPageGeneralUserList()
	{
	    if (_m_NextGeneralToken == null)
	    {
	        return Collections.EMPTY_LIST;
	    }
	    else
	    {
	        return this.GetGeneralUserList();
	    }
	}


	public void ResetGeneralToken()
	{
	    if(_m_NextGeneralToken != null)
	    	_m_NextGeneralToken = null;
	}


	public boolean HasGeneralToken()
	{
	    if (_m_NextGeneralToken != null)
	        return true;
	    else
	        return false;
	}

	public void DeleteDBDomain()
	{
	    if(_m_DBDomain != null && _m_SDBClient != null)
	    {
	        try
	        {
	    		DeleteDomainRequest ddr = new DeleteDomainRequest(_m_DBDomain);
	    		this._m_SDBClient.deleteDomain(ddr);
	    		
	    		CreateDomainRequest createDomain = new CreateDomainRequest( _m_DBDomain );
	    		this._m_SDBClient.createDomain(createDomain);
	        }
	        catch (Exception exception) 
	        {
	        	System.out.println("XSGAPIUserDBService::DeleteDBDomain: Exception : " + exception);
	        }
	    }
	}

	public void ResetCachedUserInfo()
	{
	    if(_m_CachedUserInfo != null)
	    {
	        _m_CachedUserInfo = null;
	    }
	}

	public void ResetCachedUserID()
	{
	    if(_m_CachedUserID != null)
	    {
	        _m_CachedUserID = null;
	    }
	}


	//Function delegate call back
	public void ConnectDBDomainDone()
	{
		if(this._m_Delegate != null)
			this._m_Delegate.ConnectDBDomainDone();
	}

	public void ConnectDBDomainFailed()
	{
		_m_DBDomain = null; 
		if(this._m_Delegate != null)
			this._m_Delegate.ConnectDBDomainFailed();
	}


	public void DisconnectDBDomainDone()
	{
		_m_DBDomain = null; 
		if(this._m_Delegate != null)
			this._m_Delegate.DisconnectDBDomainDone();
	}

	public void RegisterUserDone()
	{
		if(this._m_Delegate != null)
			this._m_Delegate.RegisterUserDone();
	}

	public void RegisterUserFailed()
	{
		if(this._m_Delegate != null)
			this._m_Delegate.RegisterUserFailed();
	}

	public void UnRegisterUserDone()
	{
		if(this._m_Delegate != null)
			this._m_Delegate.UnRegisterUserDone();
	}

	public void UnRegisterUserFailed()
	{
		if(this._m_Delegate != null)
			this._m_Delegate.UnRegisterUserFailed();
	}

	public void QueryUserCountOperationDone(int nCount)
	{
		if(this._m_Delegate != null)
			this._m_Delegate.QueryUserCountOperationDone(nCount);
	}
	
	public void QueryBeginUserListOperationDone(List<XSGAPIUser> pLIst)
	{
		if(this._m_Delegate != null)
			this._m_Delegate.QueryBeginUserListOperationDone(pLIst);
	}
	
	public void QueryNextUserListOperationDone(List<XSGAPIUser> pLIst)
	{
		if(this._m_Delegate != null)
			this._m_Delegate.QueryNextUserListOperationDone(pLIst);
	}
	
	public void StartQueryUserCountOperation()
	{
		new DBQueryTask(this, XSGAPIDB_OPERATION_QUERYUSERCOUNT).execute();
	}
	
	public void StartQueryBeginGeneralUserListOperation()
	{
		new DBQueryTask(this, XSGAPIDB_OPERATION_QUERYUSERLISTBEGIN).execute();
	}
	
	public void StartQueryNextGeneralUserListOperation()
	{
		new DBQueryTask(this, XSGAPIDB_OPERATION_QUERYUSERLISTNEXT).execute();
	}
	
	public static final int XSGAPIDB_OPERATION_QUERYUSERCOUNT = 0;
	public static final int XSGAPIDB_OPERATION_QUERYUSERLISTBEGIN = 1;
	public static final int XSGAPIDB_OPERATION_QUERYUSERLISTNEXT = 2;

	class DBQueryTask extends AsyncTask<Void, Void, Boolean> 
	{
		protected XSGAPIUserDBService		m_DBService;
		protected int						m_QueryType;
		List<XSGAPIUser>					m_UserList;
		int 								m_nUserCount;
		
		public DBQueryTask(XSGAPIUserDBService dbService, int nQueryType)
		{
			m_DBService = dbService;
			m_QueryType = nQueryType;
			m_UserList = null;
			m_nUserCount = 0;
		}
		
	    protected Boolean doInBackground(Void...voids) 
	    {
	    	 boolean bRet = true;
	         
	    	 if(m_QueryType == XSGAPIDB_OPERATION_QUERYUSERCOUNT && m_DBService != null)
	    	 {
	    		 m_nUserCount = m_DBService.GetUserCount();
	    	 }
	    	 else if(m_QueryType == XSGAPIDB_OPERATION_QUERYUSERLISTBEGIN && m_DBService != null)
	    	 {
	    		 m_UserList = m_DBService.GetGeneralUserList();
	    	 }
	    	 else if(m_QueryType == XSGAPIDB_OPERATION_QUERYUSERLISTNEXT && m_DBService != null)
	    	 {
	    		 m_UserList = m_DBService.GetNextPageGeneralUserList();
	    	 }
	    	 
	         return bRet;
	     }

	     protected void onPostExecute(Boolean result) 
	     {
	    	 if(m_QueryType == XSGAPIDB_OPERATION_QUERYUSERCOUNT && m_DBService != null)
	    	 {
	    		 m_DBService.QueryUserCountOperationDone(m_nUserCount);
	    	 }
	    	 else if(m_QueryType == XSGAPIDB_OPERATION_QUERYUSERLISTBEGIN && m_DBService != null)
	    	 {
	    		 m_DBService.QueryBeginUserListOperationDone(m_UserList);
	    	 }
	    	 else if(m_QueryType == XSGAPIDB_OPERATION_QUERYUSERLISTNEXT && m_DBService != null)
	    	 {
	    		 m_DBService.QueryNextUserListOperationDone(m_UserList);
	    	 }
	     }
	}
	
}
