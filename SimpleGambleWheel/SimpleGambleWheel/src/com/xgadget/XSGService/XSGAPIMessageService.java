package com.xgadget.XSGService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.DeleteQueueRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.Message;

public class XSGAPIMessageService 
{
    private AmazonSQSClient    	sqsClient;
    private String           	queueUrl;
    private String           	_queueName;

    public XSGAPIMessageService()
    {
		AWSCredentials credentials = new BasicAWSCredentials(XSGAPIConstants.XGADGET_ACCESS_KEY_ID, XSGAPIConstants.XGADGET_SECRET_KEY);
		this.sqsClient = new AmazonSQSClient(credentials);
		queueUrl = null;
		_queueName = null;

		// Find the Queue for this App or create one.
		/*this.queueUrl = this.findQueueUrl();
		if (this.queueUrl == null) 
		{
			this.queueUrl = this.createQueue();

			// Allow time for the queue to be created.
			try 
			{
				Thread.sleep(4 * 1000);
			} catch (Exception exception) 
			{
			}
		}*/
    }

    public XSGAPIMessageService(String queueName)
    {
		AWSCredentials credentials = new BasicAWSCredentials(XSGAPIConstants.XGADGET_ACCESS_KEY_ID, XSGAPIConstants.XGADGET_SECRET_KEY);
		this.sqsClient = new AmazonSQSClient(credentials);
		queueUrl = null;
		_queueName = queueName;

		// Find the Queue for this App or create one.
		this.queueUrl = this.findQueueUrl();
		if (this.queueUrl == null) 
		{
			this.queueUrl = this.createQueue();

			// Allow time for the queue to be created.
			try 
			{
				if(this.queueUrl != null)
					Thread.sleep(4 * 1000);
			} 
			catch (Exception exception) 
			{
				System.out.println("XSGAPIMessageService constructor Exception = " + exception);
				queueUrl = null;
			}
		}
    }
    
    public boolean IsValid()
    {
    	if(this.queueUrl != null)
    		return true;
    	else
    		return false;
    }
    
    public String GetQueueName()
    {
    	return _queueName;
    }
    
    protected String GetQueueUrl()
    {
    	return this.queueUrl;
    }
    
    public boolean ConnectToQueue(String queueName)
    {
		_queueName = queueName;
		this.queueUrl = this.findQueueUrl();
		if (this.queueUrl == null) 
		{
			this.queueUrl = this.createQueue();

			// Allow time for the queue to be created.
			try 
			{
				if(this.queueUrl != null)
					Thread.sleep(4 * 1000);
			} 
			catch (Exception exception) 
			{
				System.out.println("XSGAPIMessageService ConnectToQueue Exception = " + exception);
			}
		}
		if(this.queueUrl == null)
			return false;
		else
			return true;
    }

    
    public String createQueue() 
    {
    	String qUrl = null;
    	if(_queueName != null)
    	{	
    		try
    		{
    			CreateQueueRequest req = new CreateQueueRequest(_queueName);
    			CreateQueueResult result = this.sqsClient.createQueue(req);
    
    			qUrl = result.getQueueUrl();
    		} 
    		catch (Exception exception) 
    		{
    			System.out.println("createQueue Exception = " + exception);
    			return null;
    		}
    	}	
    	
    	return qUrl;
    }
    
    public void deleteQueue()
    {
        try 
        {
            DeleteQueueRequest request = new DeleteQueueRequest(queueUrl);
            this.sqsClient.deleteQueue(request);
        }
        catch (Exception exception)  
        {
            this.postFailureProcess();
            System.out.println("deleteQueue Exception = " + exception);
        }
    }    
    
	// Determine if a queue exists with the given queue name.
	// The queue name is assigned in the Constants.java file.
	protected String findQueueUrl() 
	{
		try 
		{
			String queueNameToFind = "/" + _queueName;
			ListQueuesResult queuesResult = this.sqsClient.listQueues();

			for (String queueUrl : (List<String>) queuesResult.getQueueUrls()) 
			{
				if (queueUrl.endsWith(queueNameToFind)) 
				{
					return queueUrl;
				}
			}

			return null;
		} 
		catch (Exception exception) 
		{
			System.out.println("Exception  = " + exception);
			return null;
		}
	}

	public String cacheQueueURL() 
	{
		return queueUrl;
	}
	
	public boolean postToCachedQueue(String message, String cachedQueueUrl)
	{
		boolean bRet = true;
		try 
		{
			SendMessageRequest req = new SendMessageRequest(cachedQueueUrl, message);
			this.sqsClient.sendMessage(req);
		} 
		catch (Exception exception)
		{
			XSGAPIReleaseConfigure.LogDebugInfo(" XSGAPIMessageService post Exception  = ", exception.toString());
			bRet = false;
		}
		return bRet;
	}
	
	public boolean post(String message) 
	{
		boolean bRet = true;
		try 
		{
			SendMessageRequest req = new SendMessageRequest(queueUrl, message);
			this.sqsClient.sendMessage(req);
		} 
		catch (Exception exception)
		{
			XSGAPIReleaseConfigure.LogDebugInfo(" XSGAPIMessageService post Exception  = ", exception.toString());
			bRet = false;
		}
		return bRet;
	}
    
	public void deleteMessageFromQueue(Message message) 
	{
		try 
		{
			DeleteMessageRequest request = new DeleteMessageRequest(this.queueUrl, message.getReceiptHandle());
			this.sqsClient.deleteMessage(request);
		} 
		catch (Exception exception) 
		{
			System.out.println("deleteMessageFromQueue Exception = " + exception);
		}
	}
	
	private void postFailureProcess()
	{
		
	}
	
	public List<Message> getMessagesFromQueue()
	{
		try 
		{
			ReceiveMessageRequest rmr = new ReceiveMessageRequest(this.queueUrl);
			rmr.setMaxNumberOfMessages(XSGAPIConstants.MAX_RECIEVED_MSG_COUNT);
			rmr.setVisibilityTimeout(XSGAPIConstants.MAX_MSG_TIMEOUT);
			rmr.setWaitTimeSeconds(20);
 
			ArrayList<Message> allMessages = null;
			List<Message> messages = null;
			ReceiveMessageResult result = this.sqsClient.receiveMessage(rmr);
			messages = result.getMessages();
			if(messages != null && 0 < messages.size())
			{	
				allMessages = new ArrayList<Message>(100);
				allMessages.addAll(messages);
				return allMessages;
			}
			else
			{
				return Collections.<Message>emptyList();
			}
			
		} 
		catch (Exception exception) 
		{
			System.out.println("Exception  = " + exception);
			return Collections.<Message>emptyList();
		}
	}
	
	public void disconnectQueue()
	{
	    queueUrl = null;
	    _queueName = null;
	}
	
	public boolean isConnectQueue(String queueName)
	{
		boolean bRet = false;
		
		if(queueName != null && _queueName != null && queueUrl != null && _queueName.equals(queueName) == true)
			bRet = true;
		
		return bRet;
	}
}
