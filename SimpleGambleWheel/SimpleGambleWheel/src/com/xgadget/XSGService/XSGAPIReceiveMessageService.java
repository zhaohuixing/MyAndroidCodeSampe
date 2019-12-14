package com.xgadget.XSGService;

import java.util.Collections;
import java.util.List;

import com.amazonaws.services.sqs.model.Message;

import android.os.Bundle;
import android.os.Handler;


public class XSGAPIReceiveMessageService extends Thread
{
	public static final	int		c_ServiceStateNotRun = 0;
	public static final	int		c_ServiceStateConnectToQueue = 1;
	public static final	int		c_ServiceStateQueryMessage = 2;
	private int					m_ServiceState = c_ServiceStateNotRun; 
	
	
	private XSGAPIReceiveMessageServiceDelegate		m_Delegate;
	private XSGAPIMessageService					m_MessageService;
	private String									m_CurrentQueueName;
	private Handler									m_MsgHandler;
	
	public XSGAPIReceiveMessageService(XSGAPIReceiveMessageServiceDelegate	delegate) 
	{
		super();
		// TODO Auto-generated constructor stub
		m_Delegate = delegate;
		m_MessageService = new XSGAPIMessageService();
		m_ServiceState = c_ServiceStateNotRun;
		m_CurrentQueueName = null; 
		m_MsgHandler = null;
	}

	private synchronized void PrintLog(String log)
	{
 		XSGAPIReleaseConfigure.LogDebugInfo("XSGAPIReceiveMessageService log infor:", log);
	}
	
	@Override
	public void run() 
	{
/*		if(m_Delegate != null)
		{
			m_MsgHandler = new Handler(m_Delegate.GetHandlerCallback());
		}
		else
		{
			m_MsgHandler = null;
		}*/
		m_MsgHandler = XSGAPIGameServiceManager.getXSGAPIGameManager().GetHandler();
		while (m_ServiceState != c_ServiceStateNotRun) 
		{
			PrintLog("Thread Run loop is running.");
			try 
			{
				if(m_ServiceState == c_ServiceStateConnectToQueue)
				{
					ConnectToQueue_Interanl();
				}
				else if(m_ServiceState == c_ServiceStateQueryMessage)
				{
					QueryQueue_Interanl();
				}
			} 
			catch (Exception exception) 
			{
				// do this in a finally so that if an exception is thrown
				// during the above, we don't leave the Surface in an
				// inconsistent state
				m_ServiceState = c_ServiceStateNotRun;
				System.out.println("Exception thrown in thread run loop. = " + exception);
			}
		}
		m_ServiceState = c_ServiceStateNotRun;
		m_MsgHandler = null;
		PrintLog("Thread Run loop ended.");
		if(m_Delegate != null)
		{
			m_Delegate.XSAPIMessageServiceDone();
		}
	}

	public void StartThread()
	{
		if(this.isAlive() == false)
			this.start();
	}
    public void SetAutoClean(boolean bAutoClean)
    {
    	
    }
    
    public void setAutoStartService(boolean bAuto)
    {
    	
    }
    
    public boolean IsConnectToQueue(String queueName)
    {
    	boolean bRet = false;
    	
    	if(queueName != null && m_MessageService != null && m_MessageService.IsValid() == true)
    	{
    		String curQueue = m_MessageService.GetQueueName();
    		if(curQueue != null && curQueue.equals(queueName) == true)
    			bRet = true;
    	}
    	return bRet;
    }

    public boolean IsQueueConnected()
    {
    	boolean bRet = false;
    	
    	if(m_MessageService != null && m_MessageService.IsValid() == true)
    		bRet = true;
    	
    	return bRet;
    }
    
    public synchronized void StartService()
    {
    	if(m_CurrentQueueName == null)
    	{
    		m_ServiceState = c_ServiceStateNotRun;
    		PrintLog("StartService() failed by m_CurrentQueueName non-existed.");
    		return;
    	}
    	
    	if(m_ServiceState == c_ServiceStateNotRun)
    	{
    		if(IsConnectToQueue(m_CurrentQueueName) == false)
    			m_ServiceState = c_ServiceStateConnectToQueue;
    		else 
    			m_ServiceState = c_ServiceStateQueryMessage;
    	}
    	else
    	{
    		if(IsConnectToQueue(m_CurrentQueueName) == false)
    			m_ServiceState = c_ServiceStateConnectToQueue;
    		else 
    			m_ServiceState = c_ServiceStateQueryMessage;
    	}
    	
    	StartThread();
    }
    
    public synchronized void StopService()
    {
    	QueryQueue_Interanl();
		m_CurrentQueueName = null; 
    	m_ServiceState = c_ServiceStateNotRun;
    }
    
    public synchronized void ConnectToQueue(String queueName)
    {
		m_CurrentQueueName = queueName; 
		StartService();
    }
    
    public synchronized void Disconnect()
    {
    	StopService();
    	//??????????????
    	//??????????????
    	//??????????????
    	//??????????????
    	//??????????????
    	//??????????????
    	//??????????????
    	//??????????????
    }
    
    private synchronized void ConnectToQueue_Interanl()
    {
    	if(m_CurrentQueueName == null)
    	{
    		m_ServiceState = c_ServiceStateNotRun;
    		PrintLog("ConnectToQueue_Interanl() failed by m_CurrentQueueName non-existed.");
    		return;
    	}
    			
		if(m_MessageService == null)
			new XSGAPIMessageService();
		if(IsConnectToQueue(m_CurrentQueueName) == false)
		{	
			m_MessageService.ConnectToQueue(m_CurrentQueueName);
			ConnectToQueue_Succeeded();		
		}
    	m_ServiceState = c_ServiceStateQueryMessage;
		DeletePreviousLeftoverMessage();
    	PrintLog("ConnectToQueue_Interanl is OK.");
    }

    private synchronized void DeletePreviousLeftoverMessage()
    {
    	if(m_MessageService != null && m_MessageService.IsValid() == true)
    	{
    		List<Message> pMessages =  m_MessageService.getMessagesFromQueue();
    		if(pMessages != null && pMessages != Collections.<Message>emptyList() && 0 < pMessages.size())
    		{
    			PrintLog("DeletePreviousLeftoverMessage get message data.");
    			
    			for(int i = 0; i < pMessages.size(); ++i)
    			{
    				Message msg = pMessages.get(i);
    				m_MessageService.deleteMessageFromQueue(msg);
    			}
    		}
    		else
    		{
    			PrintLog("DeletePreviousLeftoverMessage get empty message.");
    		}
    	}
    }
    
    private synchronized void QueryQueue_Interanl()
    {
    	if(m_MessageService != null && m_MessageService.IsValid() == true)
    	{
    		List<Message> pMessages =  m_MessageService.getMessagesFromQueue();
    		if(pMessages != null && pMessages != Collections.<Message>emptyList() && 0 < pMessages.size())
    		{
    			PrintLog("QueryQueue_Interanl get message data.");
    			
    			
    			for(int i = 0; i < pMessages.size(); ++i)
    			{
    				Message msg = pMessages.get(i);
    				if(msg != null && msg.getBody() != null && m_MsgHandler != null)
    				{
    					android.os.Message osMsg = m_MsgHandler.obtainMessage();
    					String newMsg = new String(msg.getBody());
    					Bundle b = new Bundle();
    		            b.putString(XSGAPIConstants.XSGAPI_THREAD_ONLINE_MSG_TAG,newMsg);
    		            osMsg.setData(b);
    					m_MsgHandler.sendMessage(osMsg);
    				}
    				m_MessageService.deleteMessageFromQueue(msg);
    			}
    		}
    		else
    		{
    			PrintLog("QueryQueue_Interanl get empty message.");
    		}
    	}
    }
    
    public void DeleteQueue()
    {
    	if(m_MessageService != null)
    		m_MessageService.deleteQueue();
    }
    
    public void ClearQueue()
    {
    	if(m_MessageService != null)
    	{	
    		try
    		{
    			m_MessageService.deleteQueue();
    			Thread.sleep(1 * 1000);
    			m_MessageService.createQueue();
    		}
    		catch(Exception e)
    		{
    			PrintLog(e.getMessage());
    		}
    	}	
    }
   
	public void ClearServiceState()
	{
		m_ServiceState = c_ServiceStateNotRun;
	}
	
	public void SetServiceStateQueryMessage()
	{
		m_ServiceState = c_ServiceStateQueryMessage;
	}

	public void SetServiceStateConnectToQueue()
	{
		m_ServiceState = c_ServiceStateConnectToQueue;
	}
	
	public void ConnectToQueue_Succeeded()
	{
		SetServiceStateQueryMessage();
		if(this.m_Delegate != null)
			this.m_Delegate.XSAPIMessageServiceQueueConnected();
	}
    
}
