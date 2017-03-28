package com.xingzhaohui.xingtest.splashscreen;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.xingzhaohui.xingtest.R;

/**
 * TODO: document your custom view class.
 */
public class SplashScreenView extends View {
    private Drawable m_LogoDrawable;
    private int m_LogoDrawableSoruceWidth;
    private int m_LogoDrawableSoruceHeight;

    private int m_AnimationStep;

    public static int g_AnimationSequence = 40;

    public SplashScreenView(Context context) {
        super(context);
        init(null, 0);
    }

    public SplashScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SplashScreenView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.SplashScreenView, defStyle, 0);

        m_AnimationStep = -1;

        //load "logo" Drawable image from attribute array
        if(a.hasValue(R.styleable.SplashScreenView_logoDrawable)) {
            m_LogoDrawable = a.getDrawable(R.styleable.SplashScreenView_logoDrawable);

            //Query "logo" drawable image resource original size
            m_LogoDrawableSoruceWidth = m_LogoDrawable.getIntrinsicWidth();
            m_LogoDrawableSoruceHeight = m_LogoDrawable.getIntrinsicHeight();
        }

        a.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        DrawLogo(canvas);
    }

    //Draw logo image animation
    private void DrawLogo(Canvas canvas) {
        //Left, Right position
        int left = (getWidth() - m_LogoDrawableSoruceWidth)/2;
        int right = (getWidth() + m_LogoDrawableSoruceWidth)/2;
        int top = (getHeight() - m_LogoDrawableSoruceHeight)/2;
        int bottom = (getHeight() + m_LogoDrawableSoruceHeight)/2;

        //Draw logo in center after logo image animated
        if(g_AnimationSequence/2 <= m_AnimationStep ) {
            if (m_LogoDrawable != null) {
                m_LogoDrawable.setBounds(left, top, right, bottom);
                m_LogoDrawable.draw(canvas);
            }
        } else { //Start animation, draw logo image animation sequence
            int nAnimDistance = (getHeight() + m_LogoDrawableSoruceHeight)/2;
            int nAnimStep = nAnimDistance/(g_AnimationSequence/2);
            int nStep = g_AnimationSequence/2-m_AnimationStep;
            int nIncrement = nStep*nAnimStep;
            top -= nIncrement;
            bottom -= nIncrement;
            if (m_LogoDrawable != null) {
                m_LogoDrawable.setBounds(left, top, right, bottom);
                m_LogoDrawable.draw(canvas);
            }
        }
    }

    //Check animation step
    public int GetAnimationStep() {
        return m_AnimationStep;
    }

    //Set animation step
    public void SetAnimationStep(int nStep) {
        m_AnimationStep = nStep;
        invalidate();
    }

}
