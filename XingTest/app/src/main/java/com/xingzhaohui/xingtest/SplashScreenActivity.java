package com.xingzhaohui.xingtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.view.Window;
import com.xingzhaohui.xingtest.splashscreen.*;


public class SplashScreenActivity extends AppCompatActivity {

    private Handler 		m_TimerHandler;
    private Runnable 		m_UpdateTimeTask;

    //Animation timing event interval: 10 seconds
    private static final int  TIMER_INTERVAL_MS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
    }

    //When the splash screen activity entered forground, start splash screen animation
    @Override
    protected void onResume() {
        super.onResume();
        //Start splash screen animation
        m_TimerHandler = new Handler();
        m_UpdateTimeTask = null;
        StartSplashScreenAnimation();
    }

    //Splash screen animation implemented by runnable handler event
    private void StartSplashScreenAnimation() {
        //Create runnable instance to trigger animation sequence
        m_UpdateTimeTask = new Runnable()
        {

            public void run()
            {
                if(m_TimerHandler != null)
                {
                    m_TimerHandler.postDelayed(m_UpdateTimeTask, TIMER_INTERVAL_MS);
                    OnTimerEvent();
                }
            }
        };

        m_TimerHandler.removeCallbacks(m_UpdateTimeTask);
        m_TimerHandler.postDelayed(m_UpdateTimeTask, TIMER_INTERVAL_MS);
    }

    //Animation timing event handler function
    private void OnTimerEvent() {
        SplashScreenView splashScreenView = (SplashScreenView)findViewById(R.id.splashscreen);

        //Executing the animation sequence
        if(splashScreenView != null) {
            int nAnimStep = splashScreenView.GetAnimationStep();
            if(SplashScreenView.g_AnimationSequence < nAnimStep)
                StopSplashScreenAnimation();
            else
                splashScreenView.SetAnimationStep(nAnimStep + 1);
        }
    }

    private void StopSplashScreenAnimation() {

        //
        // Stop runnable timer
        //
        m_TimerHandler.removeCallbacks(m_UpdateTimeTask);
        //
        //Start main app activity after splash screen animation completed
        //Note: Using explicit intent to start main activity!
        //
        Intent mainActivity= new Intent(this, XingMainActivity.class);
        startActivity(mainActivity);

        //Exit splash screen activity
        finish();
    }

}
