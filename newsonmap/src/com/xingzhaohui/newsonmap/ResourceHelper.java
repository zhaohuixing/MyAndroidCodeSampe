package com.xingzhaohui.newsonmap;

import com.xingzhaohui.awsservice.*;
import com.xingzhaohui.newsonmap.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import android.graphics.BitmapShader;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Rect;
//import android.graphics.Shader;
//import android.graphics.Typeface;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.graphics.RectF;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.graphics.drawable.ShapeDrawable;
//import android.graphics.drawable.shapes.RoundRectShape;
//import android.util.DisplayMetrics;

@SuppressLint("UseSparseArrays")
public class ResourceHelper 
{
	public static Context 					m_ResourceContext = null;

	public static void SetResourceContext(Context gContext)
	{
		ResourceHelper.m_ResourceContext = gContext;
	}

	public static Context GetResourceContext()
	{
		return ResourceHelper.m_ResourceContext;
	}

	public static Bitmap GetBitmapByResourceID(int resID)
	{
		Bitmap bitmap = null;
		if(m_ResourceContext != null)
		{
	        Resources res = m_ResourceContext.getResources();
	        if(res == null)
	        	return bitmap;
	        
	        bitmap = BitmapFactory.decodeResource(res, resID);
			
		}
		
		return bitmap;
	}
	
	public static Resources GetResources()
	{
		return ResourceHelper.m_ResourceContext.getResources();
	}
	
	public static boolean CanLoadResource()
	{
		return (ResourceHelper.m_ResourceContext != null);
	}
	
	private static Bitmap m_PopButtonBitmap = null;
	public static Bitmap GetPopButtonBitmap()
	{
		if(m_PopButtonBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_PopButtonBitmap;
		        
		        m_PopButtonBitmap = BitmapFactory.decodeResource(res, R.drawable.pbtn200);
				
			}
		}
		return m_PopButtonBitmap;	
	}

	private static Bitmap m_Find200Bitmap = null;
	public static Bitmap GetFind200Bitmap()
	{
		if(m_Find200Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_Find200Bitmap;
		        
		        m_Find200Bitmap = BitmapFactory.decodeResource(res, R.drawable.find200);
				
			}
		}
		return m_Find200Bitmap;	
	}

	private static Bitmap m_Setting200Bitmap = null;
	public static Bitmap GetSetting200Bitmap()
	{
		if(m_Setting200Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_Setting200Bitmap;
		        
		        m_Setting200Bitmap = BitmapFactory.decodeResource(res, R.drawable.setting200);
				
			}
		}
		return m_Setting200Bitmap;	
	}

	private static Bitmap m_Post200Bitmap = null;
	public static Bitmap GetPost200Bitmap()
	{
		if(m_Post200Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_Post200Bitmap;
		        
		        m_Post200Bitmap = BitmapFactory.decodeResource(res, R.drawable.post200);
				
			}
		}
		return m_Post200Bitmap;	
	}

	private static Bitmap m_Back200Bitmap = null;
	public static Bitmap GetBack200Bitmap()
	{
		if(m_Back200Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_Back200Bitmap;
		        
		        m_Back200Bitmap = BitmapFactory.decodeResource(res, R.drawable.back200);
				
			}
		}
		return m_Back200Bitmap;	
	}

	private static Bitmap m_Close200Bitmap = null;
	public static Bitmap GetClose200Bitmap()
	{
		if(m_Close200Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_Close200Bitmap;
		        
		        m_Close200Bitmap = BitmapFactory.decodeResource(res, R.drawable.close200);
				
			}
		}
		return m_Close200Bitmap;	
	}

	private static Bitmap m_StdMap200Bitmap = null;
	public static Bitmap GetStdMap200Bitmap()
	{
		if(m_StdMap200Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_StdMap200Bitmap;
		        
		        m_StdMap200Bitmap = BitmapFactory.decodeResource(res, R.drawable.std200);
				
			}
		}
		return m_StdMap200Bitmap;	
	}

	private static Bitmap m_Satellite200Bitmap = null;
	public static Bitmap GetSatellite200Bitmap()
	{
		if(m_Satellite200Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_Satellite200Bitmap;
		        
		        m_Satellite200Bitmap = BitmapFactory.decodeResource(res, R.drawable.satellite200);
				
			}
		}
		return m_Satellite200Bitmap;	
	}

	private static Bitmap m_Hybird200Bitmap = null;
	public static Bitmap GetHybird200Bitmap()
	{
		if(m_Hybird200Bitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_Hybird200Bitmap;
		        
		        m_Hybird200Bitmap = BitmapFactory.decodeResource(res, R.drawable.hybird200);
				
			}
		}
		return m_Hybird200Bitmap;	
	}

	private static Bitmap m_ScrollUpBitmap = null;
	public static Bitmap GetScrollUpBitmap()
	{
		if(m_ScrollUpBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_ScrollUpBitmap;
		        
		        m_ScrollUpBitmap = BitmapFactory.decodeResource(res, R.drawable.scrollup);
				
			}
		}
		return m_ScrollUpBitmap;	
	}

	private static Bitmap m_ScrollDownBitmap = null;
	public static Bitmap GetScrollDownBitmap()
	{
		if(m_ScrollDownBitmap == null)
		{
			if(m_ResourceContext != null)
			{
		        Resources res = m_ResourceContext.getResources();
		        if(res == null)
		        	return m_ScrollDownBitmap;
		        
		        m_ScrollDownBitmap = BitmapFactory.decodeResource(res, R.drawable.scrolldown);
				
			}
		}
		return m_ScrollDownBitmap;	
	}
	
    public static int GetPublicIssueSignID()
    {
    	return R.drawable.pi400;
    }
    
    public static int GetPoliticsSignID()
    {
    	return R.drawable.politics400;
    }
    
    public static int GetBusinessSignID()
    {
    	return R.drawable.buz400;
    }
    
    public static int GetMoneySignID()
    {
    	return R.drawable.money400;
    }

    public static int GetHealthSignID()
    {
    	return R.drawable.health400;
    }
    
    public static int GetSportsSignID()
    {
    	return R.drawable.sport400;
    }

    public static int GetArtSignID()
    {
    	return R.drawable.art400;
    }

    public static int GetEducationSignID()

    {
    	return R.drawable.edu400;
    }

    public static int GetScienceSignID()
    {
    	return R.drawable.science400;
    }

    public static int GetDrinkSignID()
    {
    	return R.drawable.drink400;
    }

    public static int GetTourSignID()
    {
    	return R.drawable.tour400;
    }

    public static int GetStyleSignID()
    {
    	return R.drawable.style400;
    }

    public static int GetHouseSignID()
    {
    	return R.drawable.house400;
    }

    public static int GetAutoSignID()
    {
    	return R.drawable.auto400;
    }

    public static int GetCrimeSignID()
    {
    	return R.drawable.crime400;
    }
   
    public static int GetWeatherSignID()
    {
    	return R.drawable.weather400;
    }

    public static int GetMiscSignID()
    {
    	return R.drawable.misc400;
    }
	
    public static int GetCommunityEventSignID()
    {
    	return R.drawable.compin400;
    }
    
    public static int GetCommunityWikiSignID()
    {
    	return R.drawable.wiki400;
    }
    
    public static int GetDelaySignID()
    {
    	return R.drawable.delay400;
    }

    public static int GetCrowdSignID()
    {
    	return R.drawable.crowd400;
    }

    public static int GetJamSignID()
    {
    	return R.drawable.jam400;
    }

    public static int GetCrashSignID()
    {
    	return R.drawable.crash400;
    }

    public static int GetPoliceSignID()
    {
    	return R.drawable.police400;
    }

    public static int GetConstructSignID()
    {
    	return R.drawable.construct400;
    }

    public static int GetRoadClosureSignID()
    {
    	return R.drawable.roadclose400;
    }

    public static int GetTrafficSignID()
    {
    	return R.drawable.traffic400;
    }
    
    public static int GetMapMarkerImageID(int nMainCate, int nSubCate, int thirdCate)
    {
    	int nRet = R.drawable.blankpin400;
    	
    	if(nMainCate == NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS)
    	{
    		switch(nSubCate)
    		{
    		case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_PUBLICISSUE:
    			nRet =  GetPublicIssueSignID();
    			break;
    		case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_POLITICS:
    			nRet =  GetPoliticsSignID();
    			break;
    		case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_BUSINESS:
    			nRet =  GetBusinessSignID();
    			break;
    		case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_MONEY:
    			nRet =  GetMoneySignID();
    			break;
    		case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_HEALTH:
    			nRet =  GetHealthSignID();
    			break;
    		case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_SPORTS:
    			nRet =  GetSportsSignID();
    			break;
    		case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_ARTANDENTERTAINMENT:
    			nRet =  GetArtSignID();
    			break;
    		case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_EDUCATION:
    			nRet =  GetEducationSignID();
    			break;
    		case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_TECHNOLOGYANDSCIENCE:
    			nRet =  GetScienceSignID();
    			break;
    		case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_FOODANDDRINK:
    			nRet =  GetDrinkSignID();
    			break;
    		case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_TRAVELANDTOURISM:
    			nRet =  GetTourSignID();
    			break;
    		case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_LIFESTYLE:
    			nRet =  GetStyleSignID();
    			break;
    		case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_REALESTATE:
    			nRet =  GetHouseSignID();
    			break;
    		case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_AUTO:
    			nRet =  GetAutoSignID();
    			break;
    		case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_CRIMEANDDISASTER:
    			nRet =  GetCrimeSignID();
   			break;
    		case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_WEATHER:
    			nRet =  GetWeatherSignID();
    			break;
    		case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALNEWS_SUBCATEGORY_MISC:
    			nRet =  GetMiscSignID();
    			break;
    		}
    	}
    	else if(nMainCate == NOMSystemConstants.NOM_NEWSCATEGORY_COMMUNITY)
    	{
    		switch(nSubCate)
    		{
    			case NOMSystemConstants.NOM_NEWSCATEGORY_COMMUNITY_SUBCATEGORY_COMMUNITYEVENT:
    				nRet =  GetCommunityEventSignID();
    				break;
    			case NOMSystemConstants.NOM_NEWSCATEGORY_COMMUNITY_SUBCATEGORY_COMMUNITYWIKI:
    				nRet =  GetCommunityWikiSignID();
    				break;
    			case NOMSystemConstants.NOM_NEWSCATEGORY_COMMUNITY_SUBCATEGORY_COMMUNITYYARDSALE:
    				//????????nRet =  GetCommunityEventSignID();
    				//????????
    				//????????
    				break;
   		
    		}
    	}
    	else if(nMainCate == NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC)
    	{
    		if(nSubCate == NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_PUBLICTRANSIT)
    		{
    			switch(thirdCate)
    			{
    			case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_PUBLICTRANSIT_DELAY:
    				nRet =  GetDelaySignID();
    				break;
    			case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_PUBLICTRANSIT_PASSENGERSTUCK:
    				nRet =  GetCrowdSignID();
    				break;
   			
    			}
    		}
    		else if(nSubCate == NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION)
    		{
    			switch(thirdCate)
    			{
    			case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION_JAM:
    				nRet =  GetJamSignID();
    				break;
    			case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION_CRASH:
    				nRet =  GetCrashSignID();
    				break;
    			case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION_POLICE:
    				nRet =  GetPoliceSignID();
    				break;
    			case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION_CONSTRUCTION:
    				nRet =  GetConstructSignID();
    				break;
    			case NOMSystemConstants.NOM_NEWSCATEGORY_LOCALTRAFFIC_SUBCATEGORY_DRIVINGCONDITION_ROADCLOSURE:
    				nRet = GetRoadClosureSignID();
   				break;
    			}
    		}
    	}
    	return nRet;
    }
    
	public static Bitmap GetMapMarkerImage(int nMainCate, int nSubCate, int thirdCate)
	{
		int nID = GetMapMarkerImageID(nMainCate, nSubCate, thirdCate);
		Bitmap bmp = null;
		if(m_ResourceContext != null)
		{
	        Resources res = m_ResourceContext.getResources();
	        if(res == null)
	        	return bmp;
	        
	        bmp = BitmapFactory.decodeResource(res, nID);
			
		}
		return bmp;	
	}
    
}
