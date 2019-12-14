package com.xgadget.SimpleGambleWheel;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.xgadget.uimodule.ResourceHelper;
import com.xgadget.widget.adapters.AbstractWheelAdapter;

public class GraphicThemeSelectionPickerAdapter extends AbstractWheelAdapter 
{
    private final int IMAGE_WIDTH = 240;
    private final int IMAGE_HEIGHT = 60;
    // Cached images
    private List<SoftReference<Bitmap>> m_IconList;

    /**
     * Constructor
     */
    public GraphicThemeSelectionPickerAdapter() 
    {
        m_IconList = new ArrayList<SoftReference<Bitmap>>();
        for (int i = 0; i < GameConstants.GAME_THEMECOUNT; i++) 
        {
            m_IconList.add(new SoftReference<Bitmap>(loadImage(i)));
        }
    }
    
    /**
     * Loads image from resources
     */
    private Bitmap loadImage(int index) 
    {
        Bitmap bitmap = null;
        if(index == GameConstants.GAME_THEME_ANIMAL)
        	bitmap = ResourceHelper.GetAnimalBannerBitmap();
        else if(index == GameConstants.GAME_THEME_FOOD)
        	bitmap = ResourceHelper.GetFoodBannerBitmap();
        else if(index == GameConstants.GAME_THEME_FLOWER)
        	bitmap = ResourceHelper.GetFlowerBannerBitmap();
        else
        	bitmap = ResourceHelper.GetNumericBannerBitmap();
        return bitmap;
    }

    @Override
    public int getItemsCount() 
    {
        return GameConstants.GAME_THEMECOUNT;
    }

    // Layout params for image view
    final LayoutParams params = new LayoutParams(IMAGE_WIDTH, IMAGE_HEIGHT);
    
    @Override
    public View getItem(int index, View cachedView, ViewGroup parent) 
    {
        ImageView img;
        if (cachedView != null) 
        {
            img = (ImageView) cachedView;
        } 
        else 
        {
            img = new ImageView(ResourceHelper.GetResourceContext());
        }
        img.setLayoutParams(params);
        img. setScaleType (ImageView.ScaleType.FIT_CENTER);
        SoftReference<Bitmap> bitmapRef = m_IconList.get(index);
        Bitmap bitmap = bitmapRef.get();
        if (bitmap == null) 
        {
            bitmap = loadImage(index);
            m_IconList.set(index, new SoftReference<Bitmap>(bitmap));
        }
        img.setImageBitmap(bitmap);
        
        return img;
    }

}
