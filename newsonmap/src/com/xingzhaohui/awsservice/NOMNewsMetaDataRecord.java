package com.xingzhaohui.awsservice;

public class NOMNewsMetaDataRecord 
{
    public String                   m_NewsID;
    public String                   m_NewsPosterEmail;
    public String                   m_NewsPosterDisplayName;
    public long                     m_nNewsTime;                  //Since 1970
    public double                   m_NewsLatitude;
    public double                   m_NewsLongitude;
    public int                      m_NewsMainCategory;
    public int                      m_NewsSubCategory;
    public int                      m_NewsThirdCategory;           //For traffic
    public String                   m_NewsResourceURL;
    public int                      m_DisplayStateByComplain;      // = 0: no reader complaint and display it; 0 <: reader complainit, don't display
    public int                      m_DisplayForWearable;          // = 0: can not display on wearable device; 0 <: can display on wearable device

    public String                   m_NewsDomainURL;
    
	public NOMNewsMetaDataRecord() 
	{
		// TODO Auto-generated constructor stub
	    m_NewsID = null;
	    m_NewsPosterEmail = null;
	    m_NewsPosterDisplayName = null;
	    m_nNewsTime = 0;                  //Since 1970
	    m_NewsLatitude = 0;
	    m_NewsLongitude = 0;
	    m_NewsMainCategory = 0;
	    m_NewsSubCategory = 0;
	    m_NewsThirdCategory = 0;           //For traffic
	    m_NewsResourceURL = null;
	    m_DisplayStateByComplain = 0;      // = 0: no reader complaint and display it; 0 <: reader complainit, don't display
	    m_DisplayForWearable = 0;          // = 0: can not display on wearable device; 0 <: can display on wearable device
	    m_NewsDomainURL = null;
	}

	public NOMNewsMetaDataRecord(String newsID, long nTime, double lat, double lon,  String pEmail, String pDName, int nCategory, int nSubCategory, String resURL, int support)
	{
	    m_NewsID = newsID;
	    m_NewsPosterEmail = pEmail;
	    m_NewsPosterDisplayName = pDName;
	    m_nNewsTime = nTime;                  //Since 1970
	    m_NewsLatitude = lat;
	    m_NewsLongitude = lon;
	    m_NewsMainCategory = nCategory;
	    m_NewsSubCategory = nSubCategory;
	    m_NewsThirdCategory = 0;           //For traffic
	    m_NewsResourceURL = resURL;
	    m_DisplayStateByComplain = 0;      // = 0: no reader complaint and display it; 0 <: reader complainit, don't display
	    m_DisplayForWearable = support;          // = 0: can not display on wearable device; 0 <: can display on wearable device
	    m_NewsDomainURL = null;
	}

	public NOMNewsMetaDataRecord(String newsID, long nTime, double lat, double lon, String pEmail, String pDName, int nCategory, int nSubCategory, int nThirdCategory, String resURL, int support)
	{
	    m_NewsID = newsID;
	    m_NewsPosterEmail = pEmail;
	    m_NewsPosterDisplayName = pDName;
	    m_nNewsTime = nTime;                  //Since 1970
	    m_NewsLatitude = lat;
	    m_NewsLongitude = lon;
	    m_NewsMainCategory = nCategory;
	    m_NewsSubCategory = nSubCategory;
	    m_NewsThirdCategory = nThirdCategory;           //For traffic
	    m_NewsResourceURL = resURL;
	    m_DisplayStateByComplain = 0;      // = 0: no reader complaint and display it; 0 <: reader complainit, don't display
	    m_DisplayForWearable = support;          // = 0: can not display on wearable device; 0 <: can display on wearable device
	    m_NewsDomainURL = null;
	}
}
