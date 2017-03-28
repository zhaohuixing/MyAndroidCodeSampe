package com.xingzhaohui.newsonmap;

import com.xingzhaohui.awsservice.*;


public class StringFactory 
{
	public static String GetString_OK()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.OK);
		return str;
	}
	
	public static String GetString_Cancel()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.Cancel);
		return str;
	}
	
	public static String GetString_Yes()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.Yes);
		return str;
	}
	
	public static String GetString_No()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.No);
		return str;
	}
	
	public static String GetString_Close()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.Close);
		return str;
	}
	
	public static String GetString_NewsMainTitle(int nCategory)
	{
	    String strTitle = "";
	    
	    switch (nCategory)
	    {
	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS:
	            strTitle = StringFactory.GetString_LocalNews();
	            break;
	        case NOMSystemConstants.NOM_NEWSCATEGORY_COMMUNITY:
	            strTitle = StringFactory.GetString_Community();
	            break;
	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC:
	            strTitle = StringFactory.GetString_Traffic();
	            break;
	        default:
	            break;
	    }
	    
	    return strTitle;
	}

	public static String GetString_LocalNews()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.LocalNews);
		return str;
	}
	
	public static String GetString_Community()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.Community);
		return str;
	}
	
	public static String GetString_Traffic()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.Traffic);
		return str;
	}
	
	public static String GetString_NewsTitle(int nCategory, int nSubCategory)
	{
	    String strTitle = "";
	    
	    switch (nCategory)
	    {
	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS:
	            strTitle = StringFactory.GetString_LocalNewsTitle(nSubCategory);
	            break;
	        case NOMSystemConstants.NOM_NEWSCATEGORY_COMMUNITY:
	            strTitle = StringFactory.GetString_CommunityTitle(nSubCategory);
	            break;
	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC:
	            strTitle = StringFactory.GetString_TrafficTitle(nSubCategory);
	            break;
	            
	        default:
	            break;
	    }
	    
	    return strTitle;
	}
	
	public static String GetString_LocalNewsTitle(int nSubCategory)
	{
	    String strTitle = "";
	    
	    switch (nSubCategory)
	    {
	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_PUBLICISSUE:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.PublicIssue);
	            break;
	        
	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_POLITICS:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.Politics);
	            break;
	        
	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_BUSINESS:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.Business);
	            break;

	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_MONEY:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.Money);
	            break;

	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_HEALTH:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.Health);
	            break;

	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_SPORTS:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.Sports);
	            break;

	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_ARTANDENTERTAINMENT:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.ArtAndEntertainment);
	            break;
	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_EDUCATION:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.Education);
	            break;
	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_TECHNOLOGYANDSCIENCE:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.TechnologyAndScience);
	            break;

	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_FOODANDDRINK:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.FoodAndDrink);
	            break;

	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_TRAVELANDTOURISM:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.TravelAndTourism);
	            break;

	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_LIFESTYLE:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.LifeStyle);
	            break;

	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_REALESTATE:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.RealEstate);
	            break;

	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_AUTO:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.Auto);
	            break;
	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_CRIMEANDDISASTER:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.CrimeAndDisaster);
	            break;
	        
	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_WEATHER:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.Weather);
	            break;
	        
	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_MISC:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.Misc);
	            break;

	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_ALL:
	            strTitle = StringFactory.GetString_All();
	            break;
	        default:
	            break;
	    }
	    
	    return strTitle;
	}
	
	public static String GetString_All()
	{
	    String str = "";

		str = ResourceHelper.GetResourceContext().getString(R.string.All);
	    
	    return str;
	}
	
	public static String GetString_CommunityTitle(int nSubCategory)
	{
	    String strTitle = "";
	    
	    switch (nSubCategory)
	    {
        case NOMSystemConstants.NOM_NEWSCATEGORY_COMMUNITY_SUBCATEGORY_COMMUNITYEVENT:
            strTitle = ResourceHelper.GetResourceContext().getString(R.string.CommunityEvent);
            break;

        case NOMSystemConstants.NOM_NEWSCATEGORY_COMMUNITY_SUBCATEGORY_COMMUNITYYARDSALE:
            strTitle = ResourceHelper.GetResourceContext().getString(R.string.CommunityYardSale);
            break;
    
            
        case NOMSystemConstants.NOM_NEWSCATEGORY_COMMUNITY_SUBCATEGORY_COMMUNITYWIKI:
            strTitle = ResourceHelper.GetResourceContext().getString(R.string.CommunityWiki);
            break;
            
        case NOMSystemConstants.NOM_NEWSCATEGORY_COMMUNITY_SUBCATEGORY_ALL:
            //strTitle = ResourceHelper.GetResourceContext().getString(R.string."Point of Interest", @"Point of Interest label string");
            strTitle = StringFactory.GetString_All();
            break;
	    
        default:
            break;
	    }
    
	    return strTitle;
	}
	
	public static String GetString_TrafficTitle(int nSubCategory)
	{
	    String strTitle = "";
	    
	    switch (nSubCategory)
	    {
	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_PUBLICTRANSIT:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.PublicTransit);
	            break;
	            
	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.DrivingCondition);
	            break;
	        
	        default:
	            break;
	    }
	    
	    return strTitle;
	}
	
	public static String GetString_PublicTransitTypeString(int nType)
	{
	    String strTitle = "";
	    
	    switch (nType)
	    {
	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_PUBLICTRANSIT_DELAY:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.Delay);
	            break;
	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_PUBLICTRANSIT_PASSENGERSTUCK:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.PassengerOvercrowd);
	            break;
	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_PUBLICTRANSIT_ALL:
	            strTitle = StringFactory.GetString_All();
	            break;
	        default:
	            break;
	    }
	    
	    return strTitle;
	}

	public static String GetString_DrivingConditionTypeString(int nType)
	{
	    String strTitle = "";
	    
	    switch (nType)
	    {
	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION_JAM:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.TrafficJam);
	            break;
	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION_CRASH:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.CarCrash);
	            break;
	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION_POLICE:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.PoliceCheckpoint);
	            break;
	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION_CONSTRUCTION:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.ConstructionZone);
	            break;
	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION_ROADCLOSURE:
	            strTitle = ResourceHelper.GetResourceContext().getString(R.string.RoadClosure);
	            break;
	        case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION_ALL:
	            strTitle = StringFactory.GetString_All();
	            break;
	        default:
	            break;
	    }
	    
	    return strTitle;
	}

	public static String GetString_Configuration()
	{
		String str = "Configuration";
		
		//Localization string query here
		str = ResourceHelper.GetResourceContext().getString(R.string.Configuration);
		
		return str;
	}

	public static String GetString_Reload()
	{
		String str = "Reload";
		
		str = ResourceHelper.GetResourceContext().getString(R.string.Reload);
	    
	    return str;
	}

	public static String GetString_ClearMap()
	{
		String str = "ClearMap";
		
		str = ResourceHelper.GetResourceContext().getString(R.string.ClearMap);
	    
	    return str;
	}

	public static String GetString_MyLocation()
	{
		String str = "My Location";
		
		str = ResourceHelper.GetResourceContext().getString(R.string.MyLocation);
	    
	    return str;
	}

	public static String GetString_Privacy()
	{
		String str = "Privacy";
		
		//Localization string query here
		str = ResourceHelper.GetResourceContext().getString(R.string.Privacy);
		
	    return str;
	}

	public static String GetString_TermOfUse()
	{
		String str = "TermOfUse";
		
		//Localization string query here
		str = ResourceHelper.GetResourceContext().getString(R.string.TermOfUse);
		
	    return str;
	}
	
	public static String GetString_MapStandard()
	{
		String str = "Standard";
		
		//Localization string query here
		str = ResourceHelper.GetResourceContext().getString(R.string.Standard);
		
		return str;
	}

	public static String GetString_MapHybrid()
	{
		String str = "Hybird";
		
		//Localization string query here
		str = ResourceHelper.GetResourceContext().getString(R.string.Hybrid);
		
		return str;
	}

	public static String GetString_MapSatellite()
	{
		String str = "Satellite";
		
		//Localization string query here
		str = ResourceHelper.GetResourceContext().getString(R.string.Satellite);
		
		return str;
	}
	
	public static String GetString_QueryConfiguration()
	{
		String str = "Search Setting";
		
	    str = ResourceHelper.GetResourceContext().getString(R.string.SearchSetting);
	    
	    return str;
	}

	public static String GetString_UserConfiguration()
	{
		String str = "User Setting";
		
	    str = ResourceHelper.GetResourceContext().getString(R.string.UserSetting);
	    
	    return str;
	}
	
}
