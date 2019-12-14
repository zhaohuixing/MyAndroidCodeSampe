package com.xgadget.XSGService;

import java.util.ArrayList;
import java.util.List;

//import android.util.Log;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.services.simpledb.AmazonSimpleDBAsyncClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.DeleteAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesResult;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.amazonaws.services.simpledb.model.SelectResult;
import com.amazonaws.services.simpledb.util.SimpleDBUtils;

public class XSGAPIScoreBoard 
{
	public static final int		XSGAPI_SB_NO_SORT = 0;
	public static final int		XSGAPI_SB_PLAYER_SORT = 1;
	public static final int		XSGAPI_SB_SCORE_SORT = 2;
	
	public static final String 	XSGAPI_HIGH_SCORE_DOMAIN  = "SGWLeaderBoard";

	public static final String 	XSGAPI_PLAYER_ATTRIBUTE  =  "player";
	public static final String 	XSGAPI_PLAYERID_ATTRIBUTE  = "playerid";
	public static final String 	XSGAPI_SCORE_ATTRIBUTE  = "score";

	public static final String 	XSGAPI_COUNT_QUERY = "select count(*) from SGWLeaderBoard";

	public static final String 	XSGAPI_PLAYER_SORT_QUERY = "select player, playerid, score from SGWLeaderBoard where player > '' order by player asc";
	public static final String 	XSGAPI_SCORE_SORT_QUERY = "select player, playerid, score from SGWLeaderBoard where score >= '0' order by score desc";
	public static final String 	XSGAPI_NO_SORT_QUERY  = "select player, playerid, score from SGWLeaderBoard";
	
	protected  	AmazonSimpleDBAsyncClient		m_SDBClient;
	protected 	String               			m_NextToken;
	protected 	int								m_SortMethod;
	protected   XSGAPIScoreBoardDelegate		m_Delegate;
	
	public XSGAPIScoreBoard(XSGAPIScoreBoardDelegate	delegate) 
	{
		// TODO Auto-generated constructor stub
		AWSCredentials credentials = new BasicAWSCredentials(XSGAPIConstants.XGADGET_ACCESS_KEY_ID, XSGAPIConstants.XGADGET_SECRET_KEY );
		m_SDBClient = new AmazonSimpleDBAsyncClient( credentials); 
		m_NextToken = null;
		m_SortMethod = XSGAPI_SB_NO_SORT;
		m_Delegate = delegate;
	}

	public XSGAPIScoreBoard(int nSortMetod, XSGAPIScoreBoardDelegate delegate) 
	{
		// TODO Auto-generated constructor stub
		AWSCredentials credentials = new BasicAWSCredentials(XSGAPIConstants.XGADGET_ACCESS_KEY_ID, XSGAPIConstants.XGADGET_SECRET_KEY );
		m_SDBClient = new AmazonSimpleDBAsyncClient( credentials); 
		m_NextToken = null;
		m_SortMethod = nSortMetod;
		m_Delegate = delegate;
	}
	
	public void SetSortMethod(int nSort)
	{
		this.m_SortMethod = nSort;
	}
	
    /*
     * Extracts the value for the given attribute from the list of attributes.
     * Extracted value is returned as a String.
     */
	protected String getStringValueForAttributeFromList( String attributeName, List<Attribute> attributes ) 
	{
		for ( Attribute attribute : attributes ) 
		{
			if ( attribute.getName().equals( attributeName ) ) 
			{
				return attribute.getValue();
			}
		}
		
		return null;		
	}
	
    /*
     * Extracts the value for the given attribute from the list of attributes.
     * Extracted value is returned as an int.
     */
	protected int getIntValueForAttributeFromList( String attributeName, List<Attribute> attributes ) 
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
	
    /*
     * Converts an array of Items into an array of HighScore objects.
     */
	protected List<XSGAPIScoreBoardIntScore> convertItemListToHighScoreList( List<Item> items ) 
	{
		List<XSGAPIScoreBoardIntScore> scores = new ArrayList<XSGAPIScoreBoardIntScore>( items.size() );
		for ( Item item : items ) 
		{
			scores.add( this.convertItemToHighScore( item ) );
		}
		
		return scores;
	}
	
    /*
     * Converts a single SimpleDB Item into a HighScore object.
     */
	protected XSGAPIScoreBoardIntScore convertItemToHighScore( Item item ) 
	{
		return new XSGAPIScoreBoardIntScore( this.getPlayerNameForItem( item ), this.getPlayerIDForItem( item ), this.getScoreForItem( item ) );
	}	
	
    /*
     * Extracts the 'player' attribute from the SimpleDB Item.
     */
	protected String getPlayerNameForItem( Item item ) 
	{
		return this.getStringValueForAttributeFromList( XSGAPI_PLAYER_ATTRIBUTE, item.getAttributes() );
	}

    /*
     * Extracts the 'player' attribute from the SimpleDB Item.
     */
	protected String getPlayerIDForItem( Item item ) 
	{
		return this.getStringValueForAttributeFromList( XSGAPI_PLAYERID_ATTRIBUTE, item.getAttributes() );
	}
	
    /*
     * Extracts the 'score' attribute from the SimpleDB Item.
     */
	protected int getScoreForItem( Item item ) 
	{
		return this.getIntValueForAttributeFromList( XSGAPI_SCORE_ATTRIBUTE, item.getAttributes() );
	}
	
	/*
	/*
	 * Method returns the number of items in the High Scores Domain.
	 */

	public int highScoreCount()
	{
		int nRet = 0;
		
	    if(m_SDBClient != null)
	    {
	        try
	        {
	            SelectRequest selectRequest = new SelectRequest(XSGAPI_COUNT_QUERY).withConsistentRead( true );
	            
				List<Item> items = this.m_SDBClient.select( selectRequest ).getItems();	
				
				Item countItem = (Item)items.get(0);
				Attribute countAttribute = (Attribute)(((com.amazonaws.services.simpledb.model.Item) countItem).getAttributes().get(0));
				nRet = Integer.parseInt( countAttribute.getValue() );
	        }
	        catch (Exception exception) 
	        {
	        	XSGAPIReleaseConfigure.LogDebugInfo("XSGAPIScoreBoard.highScoreCount(): Exception : ", exception.toString());
	            return -1;
	        }
	    }
		return nRet;
	}

	private void QueryCountOperationDone(int nCount)
	{
		if(m_Delegate != null)
		{
			m_Delegate.ProcessHighScoreCount();
		}
	}
	
	private void QueryPlayerScoreDone(XSGAPIScoreBoardIntScore scoreRecord)
	{
		if(m_Delegate != null)
		{
			m_Delegate.ProcessPlayerScore(scoreRecord);
		}
	}
	
	private void QueryScoreListDone(List<XSGAPIScoreBoardIntScore> scoreList)
	{
		if(m_Delegate != null)
		{
			boolean bQueryNext = (this.m_NextToken != null && 0 < this.m_NextToken.length());
			m_Delegate.ProcessPlayerList2(scoreList, bQueryNext);
		}
	}
	
	public void highScoreCountAsync()
	{
	    if(m_SDBClient != null)
	    {
	        try
	        {
	            SelectRequest selectRequest = new SelectRequest(XSGAPI_COUNT_QUERY).withConsistentRead( true );
	            
	            AsyncHandler<SelectRequest,SelectResult> asyncHandler = new AsyncHandler<SelectRequest,SelectResult>()
	            {		
	    			@Override
					public void onError(Exception exception)
	    			{
	    				XSGAPIReleaseConfigure.LogDebugInfo( "XSGAPIScoreBoard.highScoreCountAsync Exception = ", exception.toString() );
	    		        return;
	    			}
	    			@Override
	    			public void onSuccess(SelectRequest request, SelectResult result)
	    			{
	    				XSGAPIReleaseConfigure.LogDebugInfo( "XSGAPIScoreBoard.highScoreCountAsync() Sucessed", "Done");
	    				if(result != null)
	    				{	
	    					Item countItem = result.getItems().get(0);
	    					Attribute countAttribute = (Attribute)(((com.amazonaws.services.simpledb.model.Item) countItem).getAttributes().get(0));
	    					int nCount = Integer.parseInt( countAttribute.getValue() );
	    					QueryCountOperationDone(nCount);
	    				}	
	    			}
	            };
				this.m_SDBClient.selectAsync(selectRequest, asyncHandler);	
	        }
	        catch (Exception exception) 
	        {
	        	XSGAPIReleaseConfigure.LogDebugInfo("XSGAPIScoreBoard.highScoreCountAsync: Exception : ", exception.toString());
	            //return -1;
	        }
	    }
	}
	
	public void getPlayerScoreAsync(String thePlayerID)
	{
		GetAttributesRequest gar = new GetAttributesRequest(XSGAPI_HIGH_SCORE_DOMAIN, thePlayerID);
		AsyncHandler<GetAttributesRequest, GetAttributesResult> asyncHandler = new AsyncHandler<GetAttributesRequest, GetAttributesResult>()
		{		
			public void onError(Exception exception)
			{
				XSGAPIReleaseConfigure.LogDebugInfo( "XSGAPIScoreBoard.getPlayerScoreAsync Exception = ", exception.toString() );
		        return;
			}
			@Override
			public void onSuccess(GetAttributesRequest request, GetAttributesResult result)
			{
				XSGAPIReleaseConfigure.LogDebugInfo( "XSGAPIScoreBoard.getPlayerScoreAsync() Sucessed", "Done");
				if(result != null)
				{	
					String playerName = getStringValueForAttributeFromList( XSGAPI_PLAYER_ATTRIBUTE, result.getAttributes() );				
					String playerID = getStringValueForAttributeFromList( XSGAPI_PLAYERID_ATTRIBUTE, result.getAttributes() );				
					int score = getIntValueForAttributeFromList( XSGAPI_SCORE_ATTRIBUTE, result.getAttributes() );
					XSGAPIScoreBoardIntScore scoreRecord = new XSGAPIScoreBoardIntScore(playerName, playerID, score);
					QueryPlayerScoreDone(scoreRecord);
				}	
			}
		};
		this.m_SDBClient.getAttributesAsync(gar, asyncHandler);
	}

	public void ClearCache()
	{
		this.m_NextToken = null;
	}
	
	/*
	 * Using the pre-defined query, extracts items from the domain in a determined order using the 'select' operation.
	 */
	public synchronized void getHighScoresAsync()
	{
	    String query = null;
	    
	    switch (m_SortMethod)
	    {
	        case XSGAPI_SB_PLAYER_SORT:
	        {
	            query = XSGAPI_PLAYER_SORT_QUERY;
	            break;
	        }
	            
	        case XSGAPI_SB_SCORE_SORT:
	        {
	            query = XSGAPI_SCORE_SORT_QUERY;
	            break;
	        }
	            
	        default:
	        {
	            query = XSGAPI_NO_SORT_QUERY;
	        }
	    }
	    if(m_SDBClient != null)
	    {
	        try
	        {
	    		SelectRequest selectRequest = new SelectRequest( query ).withConsistentRead( true );
	    		selectRequest.setNextToken( this.m_NextToken );
	            
	            AsyncHandler<SelectRequest,SelectResult> asyncHandler = new AsyncHandler<SelectRequest,SelectResult>()
	            {		
	    			@Override
					public void onError(Exception exception)
	    			{
	    				XSGAPIReleaseConfigure.LogDebugInfo( "XSGAPIScoreBoard.getHighScoresAsync Exception = ", exception.toString() );
	    		        return;
	    			}
	    			@Override
	    			public void onSuccess(SelectRequest request, SelectResult result)
	    			{
	    				XSGAPIReleaseConfigure.LogDebugInfo( "XSGAPIScoreBoard.getHighScoresAsync() Sucessed", "Done");
	    				if(result != null)
	    				{	
	    					m_NextToken = result.getNextToken();
	    		    		List<XSGAPIScoreBoardIntScore> scoreList = convertItemListToHighScoreList(result.getItems());	
	    					QueryScoreListDone(scoreList);
	    				}	
	    			}
	            };
				this.m_SDBClient.selectAsync(selectRequest, asyncHandler);	
	        }
	        catch (Exception exception) 
	        {
	        	XSGAPIReleaseConfigure.LogDebugInfo("XSGAPIScoreBoard.highScoreCountAsync: Exception : ", exception.toString());
	        }
	    }
	}
	
	public boolean getNextPageOfScores()
	{
	    if (this.m_NextToken == null || this.m_NextToken.length() <= 0)
	    {
	        return false;
	    }
	    else
	    {
	        this.getHighScoresAsync();
	        return true;
	    }
	}

	


	/*
	 * Creates a new item and adds it to the HighScores domain.
	 */
	public void addHighScore(XSGAPIScoreBoardIntScore score)
	{
		String paddedScore = SimpleDBUtils.encodeZeroPadding(score.GetScore(), 10 );
		ReplaceableAttribute playerNameAttribute = new ReplaceableAttribute( XSGAPI_PLAYER_ATTRIBUTE, score.GetPlayerName(), Boolean.TRUE );
		ReplaceableAttribute playerIDAttribute = new ReplaceableAttribute( XSGAPI_PLAYERID_ATTRIBUTE, score.GetPlayerID(), Boolean.TRUE );
		ReplaceableAttribute scoreAttribute = new ReplaceableAttribute( XSGAPI_SCORE_ATTRIBUTE, paddedScore, Boolean.TRUE );
		
		List<ReplaceableAttribute> attrs = new ArrayList<ReplaceableAttribute>(2);
		attrs.add( playerNameAttribute );
		attrs.add( playerIDAttribute );
		attrs.add( scoreAttribute );
		
		PutAttributesRequest par = new PutAttributesRequest(XSGAPI_HIGH_SCORE_DOMAIN, score.GetPlayerID(), attrs);		
		try 
		{
            AsyncHandler<PutAttributesRequest, Void> asyncHandler = new AsyncHandler<PutAttributesRequest, Void>()
    	    {		
    	    	@Override
    			public void onError(Exception exception)
    	    	{
    	    		XSGAPIReleaseConfigure.LogDebugInfo( "XSGAPIScoreBoard.addHighScore Exception = ", exception.toString() );
    	    		return;
    	    	}
    	    	@Override
    	    	public void onSuccess(PutAttributesRequest par, Void arg)
    	    	{
    	    		XSGAPIReleaseConfigure.LogDebugInfo( "XSGAPIScoreBoard.addHighScore() Sucessed", "Done");
    	    		if(m_Delegate != null)
    	    			m_Delegate.AddHighScoreDone();
    	    	}
    	    };
			
			this.m_SDBClient.putAttributesAsync(par, asyncHandler); 
		}
		catch ( Exception exception ) 
		{
			XSGAPIReleaseConfigure.LogDebugInfo( "XSGAPIScoreBoard.addHighScore EXCEPTION = ", exception.toString() );
		}
	}

	/*
	 * Removes the item from the HighScores domain.
	 * The item removes is the item whose 'player' matches the theHighScore submitted.
	 */
	public void removeHighScore(XSGAPIScoreBoardIntScore score ) 
	{
		DeleteAttributesRequest dar = new DeleteAttributesRequest( XSGAPI_HIGH_SCORE_DOMAIN, score.GetPlayerID() );
		try 
		{
            AsyncHandler<DeleteAttributesRequest, Void> asyncHandler = new AsyncHandler<DeleteAttributesRequest, Void>()
    	    {		
    	    	@Override
    			public void onError(Exception exception)
    	    	{
    	    		XSGAPIReleaseConfigure.LogDebugInfo( "XSGAPIScoreBoard.removeHighScore Exception = ", exception.toString() );
    	    		return;
    	    	}
    	    	@Override
    	    	public void onSuccess(DeleteAttributesRequest par, Void arg)
    	    	{
    	    		XSGAPIReleaseConfigure.LogDebugInfo( "XSGAPIScoreBoard.removeHighScore() Sucessed", "Done");
    	    		if(m_Delegate != null)
    	    			m_Delegate.DeleteHighScoreDone();
    	    	}
    	    };
			
			this.m_SDBClient.deleteAttributesAsync(dar, asyncHandler); 
		}
		catch ( Exception exception ) 
		{
			XSGAPIReleaseConfigure.LogDebugInfo( "XSGAPIScoreBoard.removeHighScore EXCEPTION = ", exception.toString() );
		}
	}

	/*
	 * Creates the HighScore domain.
	 */
	public void createHighScoresDomain()
	{
		//SimpleDBCreateDomainRequest *createDomain = [[[SimpleDBCreateDomainRequest alloc] initWithDomainName:HIGH_SCORE_DOMAIN] autorelease];
		//SimpleDBCreateDomainResponse *createDomainResponse = [sdbClient createDomain:createDomain];
		//if(createDomainResponse.error != nil)
		//{
		//	NSLog(@"Error: %@", createDomainResponse.error);
		//}
		CreateDomainRequest cdr = new CreateDomainRequest( XSGAPI_HIGH_SCORE_DOMAIN );
		try
		{	
            AsyncHandler<CreateDomainRequest, Void> asyncHandler = new AsyncHandler<CreateDomainRequest, Void>()
    	    {		
    	    	@Override
    			public void onError(Exception exception)
    	    	{
    	    		XSGAPIReleaseConfigure.LogDebugInfo( "XSGAPIScoreBoard.createHighScoresDomain Exception = ", exception.toString() );
    	    		return;
    	    	}
    	    	@Override
    	    	public void onSuccess(CreateDomainRequest par, Void arg)
    	    	{
    	    		XSGAPIReleaseConfigure.LogDebugInfo( "XSGAPIScoreBoard.createHighScoresDomain() Sucessed", "Done");
    	    		if(m_Delegate != null)
    	    			m_Delegate.CreateDomainDone();
    	    	}
    	    };
			
			this.m_SDBClient.createDomainAsync(cdr, asyncHandler);
		}
		catch ( Exception exception ) 
		{
			XSGAPIReleaseConfigure.LogDebugInfo( "XSGAPIScoreBoard.createHighScoresDomain EXCEPTION = ", exception.toString() );
		}
	}

/*
 * Deletes the HighScore domain.
 */
/*-(void)clearHighScores
{
    SimpleDBDeleteDomainRequest *deleteDomain = [[[SimpleDBDeleteDomainRequest alloc] initWithDomainName:HIGH_SCORE_DOMAIN] autorelease];
    SimpleDBDeleteDomainResponse *deleteDomainResponse = [sdbClient deleteDomain:deleteDomain];
    if(deleteDomainResponse.error != nil)
    {
        NSLog(@"Error: %@", deleteDomainResponse.error);
    }
    
    SimpleDBCreateDomainRequest *createDomain = [[[SimpleDBCreateDomainRequest alloc] initWithDomainName:HIGH_SCORE_DOMAIN] autorelease];
    SimpleDBCreateDomainResponse *createDomainResponse = [sdbClient createDomain:createDomain];
    if(createDomainResponse.error != nil)
    {
        NSLog(@"Error: %@", createDomainResponse.error);
    }
}
*/
	
}
