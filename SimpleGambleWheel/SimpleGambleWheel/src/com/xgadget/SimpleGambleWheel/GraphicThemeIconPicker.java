package com.xgadget.SimpleGambleWheel;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.xgadget.uimodule.ResourceHelper;
import com.xgadget.widget.adapters.AbstractWheelAdapter;

public class GraphicThemeIconPicker extends AbstractWheelAdapter 
{
    private final int IMAGE_SIZE = 60;
    // Cached images
    private List<SoftReference<Bitmap>> m_IconList;

    private int	m_ListCount = 0;
    private int	m_ThemeType = GameConstants.GAME_THEME_ANIMAL;
    
    /**
     * Constructor
     */
    public GraphicThemeIconPicker(int nCount, int nTheme) 
    {
    	if(nCount != 8 && nCount != 6 && nCount != 4 && nCount != 2)
    	{
            this.m_ListCount = nCount;
            m_IconList = null;
            return;
    	}
        this.m_ListCount = nCount;
        this.m_ThemeType = nTheme;
        m_IconList = new ArrayList<SoftReference<Bitmap>>();
        for (int i = 0; i < this.m_ListCount; i++) 
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
        if(m_ThemeType == GameConstants.GAME_THEME_FLOWER)
        	bitmap = ResourceHelper.GetFlowerIconBitmap(index);
        else if(m_ThemeType == GameConstants.GAME_THEME_FOOD)
        	bitmap = ResourceHelper.GetFoodIconBitmap(index);
        else
        	bitmap = ResourceHelper.GetAnimalIconBitmap(index);
        	
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, IMAGE_SIZE, IMAGE_SIZE, true);
        return scaled;
    }

    @Override
    public int getItemsCount() 
    {
        return this.m_ListCount;
    }

    // Layout params for image view
    final LayoutParams params = new LayoutParams(IMAGE_SIZE, IMAGE_SIZE);
    
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
