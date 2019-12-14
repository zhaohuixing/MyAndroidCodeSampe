package com.xgadget.XSGService;

import android.os.AsyncTask;

public class XSGAPISendMessageService 
{
	private XSGAPIMessageService					m_MessageService;
	private boolean									m_bMessageSent;
	private XSGAPISendMessageServiceDelegate		m_Delegate;
	
	public XSGAPISendMessageService(XSGAPISendMessageServiceDelegate	delegate) 
	{
		// TODO Auto-generated constructor stub
		m_MessageService = null;
		m_bMessageSent = false;
		m_Delegate = delegate;
	}

	public XSGAPISendMessageService(String 	queueName) 
	{
		// TODO Auto-generated constructor stub
		m_MessageService = null;
		m_bMessageSent = false;
		ConnectToQueue(queueName);
	}
	
	public boolean ConnectToQueue_Internal(String 	queueName)
	{
		boolean bRet = false;
		
		m_MessageService = new XSGAPIMessageService();
		bRet = m_MessageService.ConnectToQueue(queueName);
		
		return bRet;
	}

	public void ConnectToQueue_Result(boolean bOK)
	{
		if(bOK == true)
		{
			if(m_Delegate != null)
				m_Delegate.XSGAPISendMessageServiceQueueConnected();
		}
		else
		{
			if(m_Delegate != null)
				m_Delegate.XSGAPISendMessageServiceQueueConnectionFailed();
		}
	}
	
	public boolean SendMessage_Internal(String 	message)
	{
		m_bMessageSent = false;
		
		if(m_MessageService != null && message != null)
		{
			m_bMessageSent = m_MessageService.post(message);
		}
		
		return m_bMessageSent;
	}

	public boolean SendMessageToCachedQueue_Internal(String message, String cachedQueueURL)
	{
		m_bMessageSent = false;
		
		if(m_MessageService != null && message != null && cachedQueueURL != null && 0 < cachedQueueURL.length())
		{
			m_bMessageSent = m_MessageService.postToCachedQueue(message, cachedQueueURL);
		}
		
		return m_bMessageSent;
	}

	public String GetCachedQueuURL()
	{
		String strRet = null;
		
		if(m_MessageService != null)
		{
			strRet = m_MessageService.cacheQueueURL();
		}
		
		return strRet;
	}
	
	class ConnectQueueTask extends AsyncTask<String, Void, Boolean> 
	{
		protected XSGAPISendMessageService 			m_Processor;
		
		public ConnectQueueTask(XSGAPISendMessageService messageService)
		{
			m_Processor = messageService;
		}
		
	    protected Boolean doInBackground(String... queues) 
	    {
	    	boolean bRet = false;
	    	if(m_Processor != null)
	    	{
		    	 String queueName = queues[0];
		    	 bRet = m_Processor.ConnectToQueue_Internal(queueName);
	    	}
	    	 
	    	return bRet;
	     }

	     protected void onPostExecute(Boolean result) 
	     {
	    	 if(result == Boolean.TRUE)
	    	 {	 
	    		 System.out.println("ConnectQueueTask Done!");
	    		 if(m_Processor != null)
	    			 m_Processor.ConnectToQueue_Result(true);
	    	 }	 
	    	 else
	    	 {	 
	    		 System.out.println("ConnectQueueTask Failed!");
	    		 if(m_Processor != null)
	    			 m_Processor.ConnectToQueue_Result(false);
	    	 }		 
	     }
	}

	class SendMessageTask extends AsyncTask<String, Void, Boolean> 
	{
		protected XSGAPISendMessageService 			m_Processor;
		private String m_CachedQueueURL;
		
		public SendMessageTask(XSGAPISendMessageService messageService)
		{
			m_Processor = messageService;
			m_CachedQueueURL =null;
		}

		public SendMessageTask(XSGAPISendMessageService messageService, String cachedQueueURL)
		{
			m_Processor = messageService;
			m_CachedQueueURL =cachedQueueURL;
		}
		
	    protected Boolean doInBackground(String... messages) 
	    {
	    	boolean bRet = false;
	    	if(m_Processor != null)
	    	{
		    	 String message = messages[0];
		    	 if(m_CachedQueueURL == null || m_CachedQueueURL.length() <= 0)
		    		 bRet = m_Processor.SendMessage_Internal(message);
		    	 else 
		    		 bRet = m_Processor.SendMessageToCachedQueue_Internal(message, m_CachedQueueURL);//SendMessage_Internal(message);
	    	}
	    	 
	    	return bRet;
	     }

	     protected void onPostExecute(Boolean result) 
	     {
	    	 if(result == Boolean.TRUE)
	    	 {	 
	    		 System.out.println("SendMessageTask Done!");
	    	 }	 
	    	 else
	    	 {	 
	    		 System.out.println("SendMessageTask Failed!");
	    	 }		 
	     }
	}
	
	
	public boolean ConnectToQueue(String 	queueName)
	{
		boolean bRet = false;
		
		new ConnectQueueTask(this).execute(queueName);
		
		bRet = (m_MessageService != null && m_MessageService.IsValid() == true);
		
		return bRet;
	}

	public boolean SendMessage(String message)
	{
		new SendMessageTask(this).execute(message);
		//this.SendMessage_Internal(message);
		return m_bMessageSent;
	}
	
	public void SendMessageToCachedQueue(String message, String cachedQueueURL)
	{
		new SendMessageTask(this, cachedQueueURL).execute(message);
		//this.SendMessage_Internal(message);
		return;
	}
	public synchronized void Disconnect()
	{
		if(m_MessageService != null)
		{
			m_MessageService.disconnectQueue();
		}
	}
	
	public synchronized boolean IsConnectedToQueue(String queueName)
	{
		boolean bRet = false;
		
		if(m_MessageService != null && m_MessageService.isConnectQueue(queueName))
			bRet = true;
		
		return bRet;
	}
	
	public synchronized boolean IsQueueConnected()
	{
		boolean bRet = false;
		
		if(m_MessageService != null && m_MessageService.IsValid() == true)
			bRet = true;
		
		return bRet;
	}
}
