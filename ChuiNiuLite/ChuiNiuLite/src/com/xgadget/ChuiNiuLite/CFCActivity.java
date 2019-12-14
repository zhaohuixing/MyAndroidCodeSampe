package com.xgadget.ChuiNiuLite;

import com.mobclix.android.sdk.MobclixAdView;
import com.mobclix.android.sdk.MobclixAdViewListener;
import com.mobclix.android.sdk.MobclixMMABannerXLAdView;
import com.xgadget.uimodule.ResourceHelper;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;

public class CFCActivity extends Activity implements MobclixAdViewListener 
{
	public static CApplicationController    m_ApplicationController; 

	public static void InitializeGlobalApplicationController(CFCActivity activity)
	{
		if(m_ApplicationController == null)
		{
			m_ApplicationController = new CApplicationController();
		}
		m_ApplicationController.SetCurrentActivity(activity);
	}
    
	private void InitializeGUILayout()
	{
		CFCActivity.m_ApplicationController.InitalizeGUIComponents();
		CFCActivity.m_ApplicationController.RestoreDisplayView();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	    CImageLoader.SetGameContext(this);
	    ResourceHelper.SetResourceContext(this);
		CFCActivity.InitializeGlobalApplicationController(this);
		CGameLayout.SetShowAdBanners(true);

		setContentView(R.layout.main);
	
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        CGameLayout.SetCurrentScreenDensity(dm.xdpi, dm.ydpi, dm.density);
        
        View mainView = getWindow().getDecorView();
		Rect dim = new Rect();
		mainView.getWindowVisibleDisplayFrame(dim);		
		int w = dim.width(); //m_GameView.getWidth();
		int h = dim.height(); //m_GameView.getHeight();
		CGameLayout.InitializeLayout(w, h);
	
		ScoreRecord.InitializeScoreRecord();
		
		InitializeGUILayout();
		RefreshAdViews();
	}

    @Override
    protected void onPause ()
    {
    	//XSGAPIGameServiceManager.getXSGAPIGameManager().RemoveLocalPlayerFromGameUserDomain();
    	//cleanwebcache();
    	//XSGAPIReleaseConfigure.LogDebugInfo("SimpleGambleWheel.onPause ()", "onPause ()");
    	//m_bUserRegistered = false;
    	ScoreRecord.saveRecord();
    	cleanwebcache();
    	super.onPause ();
    }
	
	public void onSuccessfulLoad(MobclixAdView view) 
	{
		view.setVisibility(View.VISIBLE);
	}

	public void onFailedLoad(MobclixAdView view, int errorCode) 
	{
	}

	public void onAdClick(MobclixAdView adView) 
	{
	}

	public void onCustomAdTouchThrough(MobclixAdView adView, String string) 
	{
	}

	public boolean onOpenAllocationLoad(MobclixAdView adView, int openAllocationCode) 
	{
		return false;
	}
	
	public String keywords()	
	{ 
		return "business, consumer, commerical, rental, real estate, electronic, soical, applicance, casino, gamble, xgadget, game, easy gamble wheel, easy, wheel";
	}
	
	public String query()		
	{ 
		return "business, consumer, commerical, rental, real estate, electronic, soical, applicance, local business, business, restraunt, consumer, game, soical";
	}
    
	private void cleanwebcache()
	{
		WebView clearview = (WebView) findViewById(R.id.fakewebview);
		clearview.clearCache(true);		
	}
	
	private void RefreshAdViews()
	{
		//cleanwebcache();
    	MobclixMMABannerXLAdView banner1 = (MobclixMMABannerXLAdView)findViewById(R.id.banner_adview_p_0);
    	if(banner1 != null)
    	{	
        	banner1.addMobclixAdViewListener(this);
    		banner1.getAd();
    	}

    	MobclixMMABannerXLAdView banner2 = (MobclixMMABannerXLAdView)findViewById(R.id.banner_adview_l_0);
    	if(banner2 != null)
    	{	
        	banner2.addMobclixAdViewListener(this);
    		banner2.getAd();
    	}

    	MobclixMMABannerXLAdView banner3 = (MobclixMMABannerXLAdView)findViewById(R.id.banner_adview_p_1);
    	if(banner3 != null)
    	{	
        	banner3.addMobclixAdViewListener(this);
    		banner3.getAd();
    	}

    	MobclixMMABannerXLAdView banner4 = (MobclixMMABannerXLAdView)findViewById(R.id.banner_adview_l_1);
    	if(banner4 != null)
    	{	
        	banner4.addMobclixAdViewListener(this);
    		banner4.getAd();
    	}
	}
}
