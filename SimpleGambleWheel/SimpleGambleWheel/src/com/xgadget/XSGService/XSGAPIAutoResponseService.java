package com.xgadget.XSGService;

import android.os.AsyncTask;

public class XSGAPIAutoResponseService 
{
	private XSGAPIAutoResponseServiceDelegate		m_Delegate;
	
	// TODO Auto-generated constructor stub
	class AutoResponseTask extends AsyncTask<String, Void, Boolean> 
	{
		protected String 							m_QueueName;
		protected XSGAPIAutoResponseServiceDelegate		m_Delegate;
		protected int								m_AutoType;
		
		public AutoResponseTask(String queueName, XSGAPIAutoResponseServiceDelegate delegate, int nAutoType)
		{
			m_QueueName = queueName;
			m_Delegate = delegate;
			m_AutoType = nAutoType;
		}
		
	    protected Boolean doInBackground(String... messages) 
	    {
	    	 boolean bRet = true;
	         
	    	 XSGAPIMessageService sendMessage = new XSGAPIMessageService();
	    	 bRet = sendMessage.ConnectToQueue(m_QueueName);
	    	 if(!bRet)
	    		 return bRet;
	    	 String message = messages[0];
	    	 bRet = sendMessage.post(message);
	    	 
	         return bRet;
	     }

	     protected void onPostExecute(Boolean result) 
	     {
	    	 if(result == Boolean.TRUE)
	    	 {	 
	    		 System.out.println("AutoResponseTask Send Message Done!");
	    		 if(m_Delegate != null)
	    		 {
	    			 m_Delegate.AutoResponseDone(m_AutoType);
	    		 }
	    	 }	 
	    	 else
	    	 {	 
	    		 System.out.println("AutoResponseTask Send Message Failed!");
	    		 if(m_Delegate != null)
	    		 {
	    			 m_Delegate.AutoResponseFailed(m_AutoType);
	    		 }
	    	 }	 
	     }
	}

	public XSGAPIAutoResponseService(XSGAPIAutoResponseServiceDelegate	delegate) 
	{
		m_Delegate = delegate;
	}
	
	public void SendAutoResponseMessage(String queueName, XSGAPIUser sender, int nAutoType)
	{
	    if(queueName == null || sender == null || nAutoType == XSGAPIMessage.XSGAPI_MSG_AUTO_NONE)
	        return;
	    String msg = null;
	    switch(nAutoType)
	    {
	        case XSGAPIMessage.XSGAPI_MSG_AUTO_INVITE:
	            msg = XSGAPIMessageFormatter.FormatInvitationMessage(sender);
	            break;
	        case XSGAPIMessage.XSGAPI_MSG_AUTO_CANCEL:
	            msg = XSGAPIMessageFormatter.FormatCancellationMessage(sender);
	            break;
	        case XSGAPIMessage.XSGAPI_MSG_AUTO_ACCEPT:
	            msg = XSGAPIMessageFormatter.FormatAcceptionMessage(sender);
	            break;
	        case XSGAPIMessage.XSGAPI_MSG_AUTO_REJECT:
	            msg = XSGAPIMessageFormatter.FormatRejecttionMessage(sender);
	            break;
	        default:
	            break;
	    }
	    if(msg != null)
	    {
	    	new AutoResponseTask(queueName, m_Delegate, nAutoType).execute(msg);
	    }
	}

	public void SendAutoResponseMessage2(String queueName, XSGAPIUser sender, int nAutoType, String szData)
	{
	    if(queueName == null || sender == null || nAutoType == XSGAPIMessage.XSGAPI_MSG_AUTO_NONE)
	        return;
	    String msg = null;
	    switch(nAutoType)
	    {
	        case XSGAPIMessage.XSGAPI_MSG_AUTO_INVITE:
	        	if(szData != null && 0 < szData.length())
	        	{
	        		msg = XSGAPIMessageFormatter.FormatInvitationMessage2(sender, szData);
	        	}
	        	else
	        	{	
	        		msg = XSGAPIMessageFormatter.FormatInvitationMessage(sender);
	        	}	
	            break;
	        case XSGAPIMessage.XSGAPI_MSG_AUTO_CANCEL:
	            msg = XSGAPIMessageFormatter.FormatCancellationMessage(sender);
	            break;
	        case XSGAPIMessage.XSGAPI_MSG_AUTO_ACCEPT:
	        	if(szData != null && 0 < szData.length())
	        	{
	        		msg = XSGAPIMessageFormatter.FormatAcceptionMessage2(sender, szData);
	        	}
	        	else
	        	{	
	        		msg = XSGAPIMessageFormatter.FormatAcceptionMessage(sender);
	        	}	
	            break;
	        case XSGAPIMessage.XSGAPI_MSG_AUTO_REJECT:
	            msg = XSGAPIMessageFormatter.FormatRejecttionMessage(sender);
	            break;
	        default:
	            break;
	    }
	    if(msg != null)
	    {
	    	new AutoResponseTask(queueName, m_Delegate, nAutoType).execute(msg);
	    }
	}
}
