package com.xingzhaohui.awsservice;

public class NOMSystemConstants 
{
	/**********************************************************************************
	 * NOM DB system configuration DB setting (current using Simple DB domain to store
	 * information)
	***********************************************************************************/
	public static final String NOM_DBSYSTYPECONFIGURE_DOMAIN               ="xxxxxxxxx";

	public static final String NOM_DBSYSTYPE_ATTRIBUTE_TYPE_TAG            ="NEWSONMAP_DBSYSTYPE";

	public static final String NOM_DBSYSTYPE_ATTRIBUTE_CURRENT_IOS_BUILD_TAG           ="CURRENT_IOS_BUILDNO";        //_m_CurrentiOSVersion
	public static final String NOM_DBSYSTYPE_ATTRIBUTE_LEAST_IOS_BUILD_TAG             ="LEAST_IOS_BUILDNO";          //_m_LeastiOSVersion

	public static final String NOM_DBSYSTYPE_ATTRIBUTE_CURRENT_ANDROID_BUILD_TAG           ="CURRENT_ANDROID_BUILDNO";        //_m_CurrentAndroidVersion
	public static final String NOM_DBSYSTYPE_ATTRIBUTE_LEAST_ANDROID_BUILD_TAG             ="LEAST_ANDROID_BUILDNO";          //_m_LeastAndroidVersion


	public static final String NOM_DBSYSTYPE_ATTRIBUTE_OLD_REGIONSDB_COUNT_TAG             ="OLD_REGION_SDB_COUNT";          //_m_OldRegionSDBCount;
	public static final String NOM_DBSYSTYPE_ATTRIBUTE_OLD_REGIONSDB_TIMELINE_TAG          ="OLD_REGION_SDB_TIMELINE_";       //_m_OldRegionSDBTimeLineList;
	public static final String NOM_DBSYSTYPE_ATTRIBUTE_OLD_REGIONSDB_DOMAIN_PREFIX_TAG     ="OLD_REGION_SDB_DOMAIN_PREFIX_";  //_m_OldRegionSDBDomainList;
	public static final String NOM_DBSYSTYPE_ATTRIBUTE_CURRENT_REGIONSDB_DOMAIN_PREFIX_TAG     ="CURRENT_REGION_SDB_DOMAIN_PREFIX";  //_m_CurrentRegionSDBDomain;
	public static final String NOM_DBSYSTYPE_ATTRIBUTE_CURRENT_COMMUNITYREGIONSDB_DOMAIN_PREFIX_TAG     ="CURRENT_REGION_CMT_SDB_DOMAIN_PREFIX";  //_m_CurrentCommunityRegionSDBDomain;
	public static final String NOM_DBSYSTYPE_ATTRIBUTE_CURRENT_TRAFFICREGIONSDB_DOMAIN_PREFIX_TAG     ="CURRENT_REGION_TRA_SDB_DOMAIN_PREFIX";  //_m_CurrentTrafficRegionSDBDomain;


	public static final String NOM_DBSYSTYPE_ATTRIBUTE_APPSTORE_URL_TAG                ="APPSTORE_URL";             //_m_AppStoreURL;
	public static final String NOM_DBSYSTYPE_ATTRIBUTE_GOOGLEPLAY_URL_TAG              ="GOOGLEPLAY_URL";           //_m_GooglePlayURL;
	public static final String NOM_DBSYSTYPE_ATTRIBUTE_AMAZON_URL_TAG                  ="AMAZON_URL";               //_m_AmazonURL;
	public static final String NOM_DBSYSTYPE_ATTRIBUTE_BLACKBERRY_URL_TAG              ="BLACKBERRY_URL";           //_m_BlackberryURL;


	public static final String NOM_DBSYSTYPE_ATTRIBUTE_GOOGLEAD_IOS_BANNER_ID_TAG              ="GAD_IOS_BANNER_ID";         
	public static final String NOM_DBSYSTYPE_ATTRIBUTE_GOOGLEAD_IOS_INTERSTITIAL_ID_TAG        ="GAD_IOS_INTERSTITIAL_ID";
	public static final String NOM_DBSYSTYPE_ATTRIBUTE_GOOGLEAD_ANDROID_BANNER_ID_TAG              ="GAD_ANDROID_BANNER_ID";
	public static final String NOM_DBSYSTYPE_ATTRIBUTE_GOOGLEAD_ANDROID_INTERSTITIAL_ID_TAG        ="GAD_ANDROID_INTERSTITIAL_ID";
	public static final String NOM_DBSYSTYPE_ATTRIBUTE_AMAZONAD_ANDROID_BANNER_ID_TAG              ="AMAZON_BANNER_ID";
	public static final String NOM_DBSYSTYPE_ATTRIBUTE_AMAZONAD_ANDROID_INTERSTITIAL_ID_TAG        ="AMAZON_INTERSTITIAL_ID";



	public static final String NOM_DBSYSTYPE_DEFAULTKEY_VALUE              ="current_type";

	public static final String NOM_DBSYSTYPE_SIMPLEDB_VALUE                ="0";
	public static final String NOM_DBSYSTYPE_CURRENT_BUILD                 ="1";


	public static final int NOM_DBSYSTYPE_INVALID                      = -1;
	public static final int NOM_DBSYSTYPE_SIMPLEDB                     = 0;
	public static final int NOM_DBSYSTYPE_DYNAMODB                     = 1;

	/**********************************************************************************/
	/**********************************************************************************/

	/**********************************************************************************
	 * NOM publisher user DB system (current using simple DB domain to store information)
	 **********************************************************************************/
	public static final String NOM_PUBLISHUSERLIST_DOMAIN               ="xxxxxxxxx";


	//Primary key
	//Range key
	public static final String NOM_PUBLISHUSERLIST_EMAIL                ="email";

	public static final String NOM_PUBLISHUSERLIST_PW                   ="pw";

	public static final String NOM_PUBLISHUSERLIST_DISPLAYNAME          ="dname";

	public static final String NOM_PUBLISHUSERLIST_LNAME                ="lname";

	public static final String NOM_PUBLISHUSERLIST_FNAME                ="fname";

	public static final String NOM_PUBLISHUSERLIST_TYPE                 ="type";

	public static final String NOM_PUBLISHUSERLIST_COMPAINSTATE         ="complain";                   //0: Normal. 1: posting offensive post.
    //2: posting middle offensive post.
    //3: posting serious offensive post, and user account disabled.

	public static final int NOM_USERCOMPLAIN_LEVEL_NORMAL               = 0;
	public static final int NOM_USERCOMPLAIN_LEVEL_SLIGHT               = 1;
	public static final int NOM_USERCOMPLAIN_LEVEL_MODERATE             = 2;
	public static final int NOM_USERCOMPLAIN_LEVEL_SEVERE               = 3;
	public static final int NOM_USERPOSTINGBAN_DEFAULT_TIME             = 86400;
	public static final int NOM_USERACCOUNT_UPDATECHECK_TIME            = 900;
	
	/**********************************************************************************/
	/**********************************************************************************/
	
	/**********************************************************************************
	 * NOM news meta DB system (current using simple DB domain to store information)
	 **********************************************************************************/
	public static final String NOM_DEFAULT_NEWSMETA_DOMAIN            ="xxxxdb";
	public static final String NOM_DEFAULT_NEWSMETA_DOMAIN_ROOT            ="xxxxdb";
	public static final String NOM_DEFAULT_COMMUNITYMETA_DOMAIN_ROOT            ="xxxxdb";
	public static final String NOM_DEFAULT_TRAFFICMETA_DOMAIN_ROOT              ="xxxxxdb";

	//Primary key
	public static final String NOM_NEWSMETA_NEWID                          ="nid";

	public static final String NOM_NEWSMETA_PUBLISHEREMAIL                 ="pemail";

	public static final String NOM_NEWSMETA_PUBLISHERDISPLAYNAME           ="pdname";

	public static final String NOM_NEWSMETA_NEWSTIME                       ="ntime";

	public static final String NOM_NEWSMETA_NEWSLATITUDE                   ="nlat";

	public static final String NOM_NEWSMETA_NEWSLONGITUDE                  ="nlon";

	public static final String NOM_NEWSMETA_NEWSTYPE                       ="ntype";

	public static final String NOM_NEWSMETA_NEWSSUBTYPE                    ="ntype2";

	public static final String NOM_NEWSMETA_NEWSDISPLAYSTATEBYCOMPLAIN     ="ndsc";

	public static final String NOM_NEWSMETA_NEWSWEARABLEENABLE             ="nweb";

	public static final String NOM_NEWSMETA_S3RESOURCEURL                  ="s3storageKey";

	public static final String NOM_NEWSMETA_NEWSTHIRDTYPE                  ="ntype3";

	/**********************************************************************************/
	/**********************************************************************************/

	/**********************************************************************************
	 * NOM news meta DB system (current using dynamo DB domain to store information)
	 **********************************************************************************/
	public static final String NOM_NEWSRESOURCE_STORAGE_ROOT               ="xxxxxx";  
	
	public static final String NOM_NEWSRESOURCE_STORAGE_NEWS_FOLDER        ="news";

	public static final String NOM_NEWSRESOURCE_STORAGE_NEWSIMAGE_FOLDER   ="nimages";

	public static final String NOM_NEWSRESOURCE_NEWFILE_POSTFIX            ="news";

	public static final String NOM_NEWSRESOURCE_STORAGE_PUBLISH_FOLDER        ="publish";

	public static final String NOM_NEWSRESOURCE_STORAGE_MAINJSON_FILENAME     ="newsmainfile.json";

	public static final String NOM_NEWSRESOURCE_STORAGE_IMAGE_FILEEXT          ="jpg";

	public static final String NOM_NEWSRESOURCE_STORAGE_IMAGE_FILEPREFIX       ="newsimage_";

	/**********************************************************************************/
	/**********************************************************************************/
	
	/**********************************************************************************
	 * NOM news main json file tags
	 **********************************************************************************/
	public static final String NOM_NEWSJSON_TAG_NEWID                          ="nid";                  //string value
	public static final String NOM_NEWSJSON_TAG_PUBLISHERDISPLAYNAME           ="pdname";               //string value
	public static final String NOM_NEWSJSON_TAG_NEWSTITLE                      ="ntile";                //string value
	public static final String NOM_NEWSJSON_TAG_NEWSBODY                       ="nbody";                //string value
	public static final String NOM_NEWSJSON_TAG_NEWSKEYWORDS                   ="nkeywords";            //string value
	public static final String NOM_NEWSJSON_TAG_NEWSLATITUDE                   ="nlat";                 //number value
	public static final String NOM_NEWSJSON_TAG_NEWSLONGITUDE                  ="nlon";                 //number value
	public static final String NOM_NEWSJSON_TAG_NEWSTIME                       ="ntime";                 //number value
	public static final String NOM_NEWSJSON_TAG_NEWSCATEGORY                   ="ncategory";            //number value
	public static final String NOM_NEWSJSON_TAG_NEWSSUBCATEGORY                ="nsubcategory";         //number value
	public static final String NOM_NEWSJSON_TAG_NEWSCOPYRIGHT                  ="ncopyright";           //string value
	public static final String NOM_NEWSJSON_TAG_NEWSIMAGECOUNT                 ="nimages";              //number value
	public static final String NOM_NEWSJSON_TAG_NEWSIMAGEKEYPREFIX             ="nimage_";              //string value



	/**********************************************************************************/
	/**********************************************************************************/

	public static final String NOM_NEWSJSON_CONTENTTYPE                         ="text/plain";
	public static final String NOM_NEWSIMAGE_CONTENTTYPE                         ="image/jpeg";

	/**********************************************************************************
	 * NOM news main category id
	 **********************************************************************************/
	public static final int NOM_NEWSCATEGORY_LOCALNEWS                         = 0;
	public static final int NOM_NEWSCATEGORY_COMMUNITY                         = 1;
	public static final int NOM_NEWSCATEGORY_LOCALTRAFFIC                      = 2;

	/**********************************************************************************/
	/**********************************************************************************/

	/**********************************************************************************
	 * NOM sub news category id in local news category
	 **********************************************************************************/
	public static final int NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_PUBLICISSUE                   = 0;
	public static final int NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_POLITICS                      = 1;
	public static final int NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_BUSINESS                      = 2;
	public static final int NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_MONEY                         = 3;
	public static final int NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_HEALTH                        = 4;
	public static final int NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_SPORTS                        = 5;
	public static final int NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_ARTANDENTERTAINMENT           = 6;
	public static final int NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_EDUCATION                     = 7;
	public static final int NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_TECHNOLOGYANDSCIENCE          = 8;
	public static final int NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_FOODANDDRINK                  = 9;
	public static final int NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_TRAVELANDTOURISM              = 10;
	public static final int NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_LIFESTYLE                     = 11;
	public static final int NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_REALESTATE                    = 12;
	public static final int NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_AUTO                          = 13;
	public static final int NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_CRIMEANDDISASTER              = 14;
	public static final int NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_WEATHER                       = 15;
	public static final int NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_MISC                          = 16;
	public static final int NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_ALL                           = 17;

	/**********************************************************************************/
	/**********************************************************************************/


	/**********************************************************************************
	 * NOM sub news category id in community category
	 **********************************************************************************/
	public static final int NOM_NEWSCATEGORY_COMMUNITY_SUBCATEGORY_COMMUNITYEVENT                = 0;
	public static final int NOM_NEWSCATEGORY_COMMUNITY_SUBCATEGORY_COMMUNITYWIKI                 = 1;
	public static final int NOM_NEWSCATEGORY_COMMUNITY_SUBCATEGORY_COMMUNITYYARDSALE             = 2;
	public static final int NOM_NEWSCATEGORY_COMMUNITY_SUBCATEGORY_ALL                           = 3;

	/**********************************************************************************/
	/**********************************************************************************/

	/**********************************************************************************
	 * NOM sub news category id in traffic category
	 **********************************************************************************/
	public static final int NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_PUBLICTRANSIT                = 0;
	public static final int NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION             = 1;
	public static final int NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_ALL                          = 2;

	/**********************************************************************************/
	/**********************************************************************************/

	/**********************************************************************************
	 * NOM sub news category id in traffic category
	 **********************************************************************************/
	public static final int NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_PUBLICTRANSIT_DELAY                = 0;
	public static final int NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_PUBLICTRANSIT_PASSENGERSTUCK       = 1;
	public static final int NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_PUBLICTRANSIT_ALL                  = 2;

	public static final int NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION_JAM               = 0;
	public static final int NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION_CRASH             = 1;
	public static final int NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION_POLICE            = 2;
	public static final int NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION_CONSTRUCTION      = 3;
	public static final int NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION_ROADCLOSURE       = 4;
	public static final int NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION_ALL               = 5;

	/**********************************************************************************/
	/**********************************************************************************/


	/**********************************************************************************
	 * NOM local news minmum query time interval (in second)
	 **********************************************************************************/
	public static final int NOM_LOCALNEWS_PUBLICISSUE_QUERYTIME_MIN                                = 1800;
	public static final int NOM_LOCALNEWS_POLITICS_QUERYTIME_MIN                                   = 1800;
	public static final int NOM_LOCALNEWS_BUSINESS_QUERYTIME_MIN                                   = 1800;
	public static final int NOM_LOCALNEWS_MONEY_QUERYTIME_MIN                                      = 1800;
	public static final int NOM_LOCALNEWS_HEALTH_QUERYTIME_MIN                                     = 1800;
	public static final int NOM_LOCALNEWS_SPORTS_QUERYTIME_MIN                                     = 1800;
	public static final int NOM_LOCALNEWS_ARTANDENTERTAINMENT_QUERYTIME_MIN                        = 1800;
	public static final int NOM_LOCALNEWS_EDUCATION_QUERYTIME_MIN                                  = 1800;
	public static final int NOM_LOCALNEWS_TECHNOLOGYANDSCIENCE_QUERYTIME_MIN                       = 1800;
	public static final int NOM_LOCALNEWS_FOODANDDRINK_QUERYTIME_MIN                               = 1800;
	public static final int NOM_LOCALNEWS_TRAVELANDTOURISM_QUERYTIME_MIN                           = 1800;
	public static final int NOM_LOCALNEWS_LIFESTYLE_QUERYTIME_MIN                                  = 1800;
	public static final int NOM_LOCALNEWS_REALESTATE_QUERYTIME_MIN                                 = 1800;
	public static final int NOM_LOCALNEWS_AUTO_QUERYTIME_MIN                                       = 1800;
	public static final int NOM_LOCALNEWS_CRIMEANDDISASTER_QUERYTIME_MIN                           = 1800;
	public static final int NOM_LOCALNEWS_WEATHER_QUERYTIME_MIN                                    = 1800;
	public static final int NOM_LOCALNEWS_MISC_QUERYTIME_MIN                                       = 1800;

	/**********************************************************************************/
	/**********************************************************************************/

	/**********************************************************************************
	 * NOM local news maxmum query time interval (in second)
	 **********************************************************************************/
	public static final int NOM_LOCALNEWS_PUBLICISSUE_QUERYTIME_MAX                                 = 604800;
	public static final int NOM_LOCALNEWS_POLITICS_QUERYTIME_MAX                                    = 604800;
	public static final int NOM_LOCALNEWS_BUSINESS_QUERYTIME_MAX                                    = 604800;
	public static final int NOM_LOCALNEWS_MONEY_QUERYTIME_MAX                                       = 604800;
	public static final int NOM_LOCALNEWS_HEALTH_QUERYTIME_MAX                                      = 604800;
	public static final int NOM_LOCALNEWS_SPORTS_QUERYTIME_MAX                                      = 604800;
	public static final int NOM_LOCALNEWS_ARTANDENTERTAINMENT_QUERYTIME_MAX                         = 604800;
	public static final int NOM_LOCALNEWS_EDUCATION_QUERYTIME_MAX                                   = 604800;
	public static final int NOM_LOCALNEWS_TECHNOLOGYANDSCIENCE_QUERYTIME_MAX                        = 604800;
	public static final int NOM_LOCALNEWS_FOODANDDRINK_QUERYTIME_MAX                                = 604800;
	public static final int NOM_LOCALNEWS_TRAVELANDTOURISM_QUERYTIME_MAX                            = 604800;
	public static final int NOM_LOCALNEWS_LIFESTYLE_QUERYTIME_MAX                                   = 604800;
	public static final int NOM_LOCALNEWS_REALESTATE_QUERYTIME_MAX                                  = 604800;
	public static final int NOM_LOCALNEWS_AUTO_QUERYTIME_MAX                                        = 604800;
	public static final int NOM_LOCALNEWS_CRIMEANDDISASTER_QUERYTIME_MAX                            = 604800;
	public static final int NOM_LOCALNEWS_WEATHER_QUERYTIME_MAX                                     = 604800;
	public static final int NOM_LOCALNEWS_MISC_QUERYTIME_MAX                                        = 604800;

	/**********************************************************************************/
	/**********************************************************************************/

	/**********************************************************************************
	 * NOM community query time interval (in second)
	 **********************************************************************************/
	public static final int NOM_COMMUNITY_EVENT_QUERYTIME_MIN                                 = 86400;
	public static final int NOM_COMMUNITY_EVENT_QUERYTIME_MAX                                 = 2592000;
	/**********************************************************************************/
	/**********************************************************************************/

	/**********************************************************************************
	 * NOM local news minmum query time interval (in second)
	 **********************************************************************************/
	public static final int NOM_TRAFFIC_PUBLICTRANSIT_DELAY_QUERYTIME_MIN                              = 600;
	public static final int NOM_TRAFFIC_PUBLICTRANSIT_PASSENGERSTUCK_QUERYTIME_MIN                     = 600;

	public static final int NOM_TRAFFIC_DRIVINGCONDITION_JAM_QUERYTIME_MIN                             = 600;
	public static final int NOM_TRAFFIC_DRIVINGCONDITION_CRASH_QUERYTIME_MIN                           = 600;
	public static final int NOM_TRAFFIC_DRIVINGCONDITION_POLICE_QUERYTIME_MIN                          = 600;
	public static final int NOM_TRAFFIC_DRIVINGCONDITION_CONSTRUCTION_QUERYTIME_MIN                    = 600;
	public static final int NOM_TRAFFIC_DRIVINGCONDITION_ROADCLOSURE_QUERYTIME_MIN                     = 600;
	/**********************************************************************************/
	/**********************************************************************************/

	/**********************************************************************************
	 * NOM local news minmum query time interval (in second)
	 **********************************************************************************/
	public static final int NOM_TRAFFIC_PUBLICTRANSIT_DELAY_QUERYTIME_MAX                              = 7200;
	public static final int NOM_TRAFFIC_PUBLICTRANSIT_PASSENGERSTUCK_QUERYTIME_MAX                     = 7200;

	public static final int NOM_TRAFFIC_DRIVINGCONDITION_JAM_QUERYTIME_MAX                             = 7200;
	public static final int NOM_TRAFFIC_DRIVINGCONDITION_CRASH_QUERYTIME_MAX                           = 3600;
	public static final int NOM_TRAFFIC_DRIVINGCONDITION_POLICE_QUERYTIME_MAX                          = 3600;
	public static final int NOM_TRAFFIC_DRIVINGCONDITION_CONSTRUCTION_QUERYTIME_MAX                    = 86400;
	public static final int NOM_TRAFFIC_DRIVINGCONDITION_ROADCLOSURE_QUERYTIME_MAX                     = 172800;
	/**********************************************************************************/
	/**********************************************************************************/


	/**********************************************************************************
	 * NOM local news minmum query time interval (in second)
	 **********************************************************************************/
	public static final int NOM_LOCALNEWS_PUBLICISSUE_FRESHRATE_MIN                                = 600;
	public static final int NOM_LOCALNEWS_POLITICS_FRESHRATE_MIN                                   = 600;
	public static final int NOM_LOCALNEWS_BUSINESS_FRESHRATE_MIN                                   = 600;
	public static final int NOM_LOCALNEWS_MONEY_FRESHRATE_MIN                                      = 600;
	public static final int NOM_LOCALNEWS_HEALTH_FRESHRATE_MIN                                     = 600;
	public static final int NOM_LOCALNEWS_SPORTS_FRESHRATE_MIN                                     = 600;
	public static final int NOM_LOCALNEWS_ARTANDENTERTAINMENT_FRESHRATE_MIN                        = 600;
	public static final int NOM_LOCALNEWS_EDUCATION_FRESHRATE_MIN                                  = 600;
	public static final int NOM_LOCALNEWS_TECHNOLOGYANDSCIENCE_FRESHRATE_MIN                       = 600;
	public static final int NOM_LOCALNEWS_FOODANDDRINK_FRESHRATE_MIN                               = 600;
	public static final int NOM_LOCALNEWS_TRAVELANDTOURISM_FRESHRATE_MIN                           = 600;
	public static final int NOM_LOCALNEWS_LIFESTYLE_FRESHRATE_MIN                                  = 600;
	public static final int NOM_LOCALNEWS_REALESTATE_FRESHRATE_MIN                                 = 600;
	public static final int NOM_LOCALNEWS_AUTO_FRESHRATE_MIN                                       = 600;
	public static final int NOM_LOCALNEWS_CRIMEANDDISASTER_FRESHRATE_MIN                           = 600;
	public static final int NOM_LOCALNEWS_WEATHER_FRESHRATE_MIN                                    = 600;
	public static final int NOM_LOCALNEWS_MISC_FRESHRATE_MIN                                       = 600;

	/**********************************************************************************/
	/**********************************************************************************/

	/**********************************************************************************
	 * NOM local news maxmum query time interval (in second)
	 **********************************************************************************/
	public static final int NOM_LOCALNEWS_PUBLICISSUE_FRESHRATE_MAX                                = 7200;
	public static final int NOM_LOCALNEWS_POLITICS_FRESHRATE_MAX                                   = 7200;
	public static final int NOM_LOCALNEWS_BUSINESS_FRESHRATE_MAX                                   = 7200;
	public static final int NOM_LOCALNEWS_MONEY_FRESHRATE_MAX                                      = 7200;
	public static final int NOM_LOCALNEWS_HEALTH_FRESHRATE_MAX                                     = 7200;
	public static final int NOM_LOCALNEWS_SPORTS_FRESHRATE_MAX                                     = 7200;
	public static final int NOM_LOCALNEWS_ARTANDENTERTAINMENT_FRESHRATE_MAX                        = 7200;
	public static final int NOM_LOCALNEWS_EDUCATION_FRESHRATE_MAX                                  = 7200;
	public static final int NOM_LOCALNEWS_TECHNOLOGYANDSCIENCE_FRESHRATE_MAX                       = 7200;
	public static final int NOM_LOCALNEWS_FOODANDDRINK_FRESHRATE_MAX                               = 7200;
	public static final int NOM_LOCALNEWS_TRAVELANDTOURISM_FRESHRATE_MAX                           = 7200;
	public static final int NOM_LOCALNEWS_LIFESTYLE_FRESHRATE_MAX                                  = 7200;
	public static final int NOM_LOCALNEWS_REALESTATE_FRESHRATE_MAX                                 = 7200;
	public static final int NOM_LOCALNEWS_AUTO_FRESHRATE_MAX                                       = 7200;
	public static final int NOM_LOCALNEWS_CRIMEANDDISASTER_FRESHRATE_MAX                           = 7200;
	public static final int NOM_LOCALNEWS_WEATHER_FRESHRATE_MAX                                    = 7200;
	public static final int NOM_LOCALNEWS_MISC_FRESHRATE_MAX                                       = 7200;

	/**********************************************************************************/
	/**********************************************************************************/

	/**********************************************************************************
	 * NOM community query time interval (in second)
	 **********************************************************************************/
	public static final int NOM_COMMUNITY_EVENT_FRESHRATE_MIN                                = 1800;
	public static final int NOM_COMMUNITY_EVENT_FRESHRATE_MAX                                = 86400;
	/**********************************************************************************/
	/**********************************************************************************/

	/**********************************************************************************
	 * NOM local news minmum query time interval (in second)
	 **********************************************************************************/
	public static final int NOM_TRAFFIC_PUBLICTRANSIT_DELAY_FRESHRATE_MIN                              = 60;
	public static final int NOM_TRAFFIC_PUBLICTRANSIT_PASSENGERSTUCK_FRESHRATE_MIN                     = 60;

	public static final int NOM_TRAFFIC_DRIVINGCONDITION_JAM_FRESHRATE_MIN                             = 60;
	public static final int NOM_TRAFFIC_DRIVINGCONDITION_CRASH_FRESHRATE_MIN                           = 60;
	public static final int NOM_TRAFFIC_DRIVINGCONDITION_POLICE_FRESHRATE_MIN                          = 60;
	public static final int NOM_TRAFFIC_DRIVINGCONDITION_CONSTRUCTION_FRESHRATE_MIN                    = 60;
	public static final int NOM_TRAFFIC_DRIVINGCONDITION_ROADCLOSURE_FRESHRATE_MIN                     = 60;
	/**********************************************************************************/
	/**********************************************************************************/

	/**********************************************************************************
	 * NOM local news minmum query time interval (in second)
	 **********************************************************************************/
	public static final int NOM_TRAFFIC_PUBLICTRANSIT_DELAY_FRESHRATE_MAX                              = 3600;
	public static final int NOM_TRAFFIC_PUBLICTRANSIT_PASSENGERSTUCK_FRESHRATE_MAX                     = 3600;

	public static final int NOM_TRAFFIC_DRIVINGCONDITION_JAM_FRESHRATE_MAX                             = 3600;
	public static final int NOM_TRAFFIC_DRIVINGCONDITION_CRASH_FRESHRATE_MAX                           = 3600;
	public static final int NOM_TRAFFIC_DRIVINGCONDITION_POLICE_FRESHRATE_MAX                          = 3600;
	public static final int NOM_TRAFFIC_DRIVINGCONDITION_CONSTRUCTION_FRESHRATE_MAX                    = 86400;
	public static final int NOM_TRAFFIC_DRIVINGCONDITION_ROADCLOSURE_FRESHRATE_MAX                     = 86400;
	/**********************************************************************************/
	/**********************************************************************************/

	/**********************************************************************************
	 * NOM user login state
	 **********************************************************************************/
	public static final int NOM_USER_LOGIN_OK                                                          = 0;
	public static final int NOM_USER_LOGIN_FAILED_UNKNOWN                                              = 1;
	public static final int NOM_USER_LOGIN_FAILED_INVALIDUSER                                          = 2;
	public static final int NOM_USER_LOGIN_FAILED_INVALIDPW                                            = 3;
	public static final int NOM_USER_LOGIN_CANCEL                                                      = -1;
	/**********************************************************************************/
	/**********************************************************************************/

	/**********************************************************************************
	 * NOM user login state
	 **********************************************************************************/
	public static final String  NOM_CUSTOMER_EMAIL              = "xxxxxx@xxxx.com";
	/**********************************************************************************/
	/**********************************************************************************/


	public static final int GAME_TIMER_INTERVAL                               =  30;
	public static final int GAME_ADBANNER_AUTOHIDE_TIMER_INTERVAL             =  1;

	public static final int NOM_POSTREGION_OFFSET_LIMIT                       =  50000;
	
}
