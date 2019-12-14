package com.xgadget.BubbleTile;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.google.ads.*;
import com.mobclix.android.sdk.MobclixAdView;
import com.mobclix.android.sdk.MobclixAdViewListener;
import com.mobclix.android.sdk.MobclixFullScreenAdView;
import com.mobclix.android.sdk.MobclixFullScreenAdViewListener;
import com.mobclix.android.sdk.MobclixIABRectangleMAdView;
import com.mobclix.android.sdk.MobclixMMABannerXLAdView;

public class CGameActivity extends Activity implements MobclixAdViewListener, MobclixFullScreenAdViewListener
{
    /** Called when the activity is first created. */
	private GameView		m_GameView;
	private WizardView		m_WizardView;
    private ViewGroup 		m_ViewContainer;
	
	private static final int MENU_GAMEMENU = 1;
	private static final int MENU_STARTGAME = 2;
	private static final int MENU_STARTWIZARD = 3;

	private static final int VIEW_INDEX_GAMEVIEW = 0;
	private static final int VIEW_INDEX_WIZARDVIEW = 1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		CGameLayout.SetScreenSize(1024, 768);
		
        CResourceHelper.SetGameContext(this);
        setContentView(R.layout.main);
        cleanwebcache();
        MobclixMMABannerXLAdView banner_adview_p_0 = (MobclixMMABannerXLAdView) findViewById(R.id.banner_adview_p_0);
        MobclixMMABannerXLAdView banner_adview_l_0 = (MobclixMMABannerXLAdView) findViewById(R.id.banner_adview_l_0);
		banner_adview_l_0.addMobclixAdViewListener(this);
		banner_adview_l_0.getAd();
		banner_adview_l_0.bringToFront();
		banner_adview_l_0.setVisibility(View.VISIBLE);
		banner_adview_p_0.addMobclixAdViewListener(this);
		banner_adview_p_0.getAd();
		banner_adview_p_0.bringToFront();
		banner_adview_p_0.setVisibility(View.VISIBLE);
    
		m_GameView = (GameView)findViewById(R.id.gameview);
		m_GameView.setVisibility(View.VISIBLE);
		m_WizardView = (WizardView)findViewById(R.id.wizardview);
		m_WizardView.setVisibility(View.GONE);
		m_ViewContainer = (ViewGroup)findViewById(R.id.container);
		m_ViewContainer.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		super.onCreateOptionsMenu(menu);
		MenuItem item;
		SubMenu menuGame = menu.addSubMenu(0, MENU_GAMEMENU, 0, R.string.Menu_Game);
		item = menuGame.add(1, MENU_STARTGAME, 0, R.string.MenuItem_StartGame);
		item.setCheckable(false);
		item = menuGame.add(2, MENU_STARTWIZARD, 0, R.string.MenuItem_StartWizard);
		item.setCheckable(false);
		return true;
	}

@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		///???????????????????
		return true;
	}

@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
			case MENU_STARTGAME:
				GotoGameView();
				return true;
			case MENU_STARTWIZARD:
				GotoWizardView();
				return true;
		}

		return false;
	}

	public void GotoGameView()
	{
        applyRotation(VIEW_INDEX_GAMEVIEW, VIEW_INDEX_WIZARDVIEW, 0, 90);
	}
	
	public void GotoWizardView()
	{
        applyRotation(VIEW_INDEX_WIZARDVIEW, VIEW_INDEX_GAMEVIEW, 0, 90);
	}

	public void finish()
	{
		cleanwebcache();
		super.finish();
	}
    
	public void onSuccessfulLoad(MobclixAdView view) 
	{
		//Log.v(TAG, "The ad request was successful!");
		view.setVisibility(View.VISIBLE);
	}

	public void onFailedLoad(MobclixAdView view, int errorCode) 
	{
		//Log.v(TAG, "The ad request failed with error code: " + errorCode);
		view.setVisibility(View.GONE);
	}

	public void onAdClick(MobclixAdView adView) 
	{
		//Log.v(TAG, "Ad clicked!");
	}

	public void onCustomAdTouchThrough(MobclixAdView adView, String string) 
	{
		//Log.v(TAG, "The custom ad responded with '" + string + "' when touched!");
	}

	public boolean onOpenAllocationLoad(MobclixAdView adView, int openAllocationCode) 
	{
		//Log.v(TAG, "The ad request returned open allocation code: " + openAllocationCode);
		return false;
	}
	
	public String keywords()	
	{ 
		return "Bubble Tile";
	}
	public String query()		
	{ 
		return "query";
	}
	
	public void onDismissAd(MobclixFullScreenAdView adview) 
	{
		//Log.v(TAG, "MobclixFullScreenAdView dismissed.");
	}

	public void onFailedLoad(MobclixFullScreenAdView adview, int errorCode) 
	{
		//Log.v(TAG, "MobclixFullScreenAdView failed to load with error code: " + errorCode);
	}

	public void onFinishLoad(MobclixFullScreenAdView adview) 
	{
		//Log.v(TAG, "MobclixFullScreenAdView loaded.");
	}

	public void onPresentAd(MobclixFullScreenAdView adview)
	{
		//Log.v(TAG, "MobclixFullScreenAdView presented.");
	}

	//Clear ad banner cached data to save storage
	private void cleanwebcache()
	{
		WebView clearview = (WebView) findViewById(R.id.fakewebview);
		if (clearview != null)
		clearview.clearCache(true);		
	}
	
    private void applyRotation(int showViewIndex, int goneViewIndex, float start, float end) 
    {
        // Find the center of the container
        final float centerX = m_ViewContainer.getWidth() / 2.0f;
        final float centerY = m_ViewContainer.getHeight() / 2.0f;

        // Create a new 3D rotation with the supplied parameter
        // The animation listener is used to trigger the next animation
        final Rotate3dAnimation rotation =
                new Rotate3dAnimation(start, end, centerX, centerY, 310.0f, true);
        rotation.setDuration(500);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new DisplayNextView(showViewIndex, goneViewIndex));

        m_ViewContainer.startAnimation(rotation);
    }
	
    /**
     * This class listens for the end of the first half of the animation.
     * It then posts a new action that effectively swaps the views when the container
     * is rotated 90 degrees and thus invisible.
     */
    private final class DisplayNextView implements Animation.AnimationListener 
    {
        private final int m_showViewIndex;
        private final int m_goneViewIndex;

        private DisplayNextView(int showViewIndex, int goneViewIndex) 
        {
        	m_showViewIndex = showViewIndex;
           	m_goneViewIndex = goneViewIndex;
        }

        public void onAnimationStart(Animation animation) 
        {
        }

        public void onAnimationEnd(Animation animation) 
        {
        	m_ViewContainer.post(new SwapViews(m_showViewIndex, m_goneViewIndex));
        }

        public void onAnimationRepeat(Animation animation) 
        {
        }
    }

    /**
     * This class is responsible for swapping the views and start the second
     * half of the animation.
     */
    private final class SwapViews implements Runnable 
    {
        private final int m_showViewIndex;
        private final int m_goneViewIndex;

        private SwapViews(int showViewIndex, int goneViewIndex) 
        {
        	m_showViewIndex = showViewIndex;
           	m_goneViewIndex = goneViewIndex;
        }

        public void run() 
        {
            final float centerX = m_ViewContainer.getWidth() / 2.0f;
            final float centerY = m_ViewContainer.getHeight() / 2.0f;
            Rotate3dAnimation rotation;
            
        	if(m_goneViewIndex == VIEW_INDEX_GAMEVIEW)
        	{
        		m_GameView.setVisibility(View.GONE);
        	}
        	else if(m_goneViewIndex == VIEW_INDEX_WIZARDVIEW)
        	{
        		m_WizardView.setVisibility(View.GONE);
        	}
 
           	if(m_showViewIndex == VIEW_INDEX_GAMEVIEW)
        	{
        		m_GameView.setVisibility(View.VISIBLE);
        		m_GameView.requestFocus();
        	}
        	else if(m_showViewIndex == VIEW_INDEX_WIZARDVIEW)
        	{
        		m_WizardView.setVisibility(View.VISIBLE);
        		m_WizardView.requestFocus();
        	}
        	
            rotation = new Rotate3dAnimation(90, 180, centerX, centerY, 310.0f, false);
            rotation.setDuration(500);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new DecelerateInterpolator());
            m_ViewContainer.startAnimation(rotation);
        }
    }
    
}