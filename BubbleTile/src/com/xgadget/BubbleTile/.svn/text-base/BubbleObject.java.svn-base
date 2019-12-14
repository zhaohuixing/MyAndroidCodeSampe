package com.xgadget.BubbleTile;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
public class BubbleObject {
    int         m_nCurrentLocationIndex;
  //  CGImageRef  m_Bubble;
    Boolean     m_bIconTemplate;
    int         m_nLabelValue;
    Boolean     m_bShowQmark;
    private void CreateBubbleImage(int nLabel)
    {
    // need to implement	
    }
    
    public BubbleObject()
    {
        m_nCurrentLocationIndex = -1;
//        m_Bubble = NULL;    
        m_bIconTemplate = false;
        m_nLabelValue = -1;
        m_bShowQmark = false;    	
    }
    public BubbleObject(int nLabel, Boolean isTemplate)
    {
        m_nCurrentLocationIndex = -1;
        m_bIconTemplate = isTemplate;
        m_nLabelValue = nLabel;
        m_bShowQmark = false;
        CreateBubbleImage(nLabel);      	
    }
    
    public int GetCurrentLocationIndex()
    {
    	return m_nCurrentLocationIndex;
    }
    
    public void SetCurrentLocationIndex(int nIdx)
    {
    	m_nCurrentLocationIndex = nIdx;
    }
    
    public void SetLabel(int nLabel)
    {
//        if(m_Bubble != NULL)
  //          CGImageRelease(m_Bubble);
    	
    	m_nLabelValue = nLabel;
    	CreateBubbleImage(nLabel);
    }
    
    public int GetLabelValue()
    {
    	return m_nLabelValue;
    }   
    
    public void DrawBubble(Canvas context, RectF rect)
    {
		CBubblePainter bubblePaint = new CBubblePainter(); 
		bubblePaint.SetBubbleType(CBubblePainter.BUBBLE_TYPE_COLOR);
		bubblePaint.SetNumber(m_nLabelValue);
		bubblePaint.DrawBubble(context, new Rect((int)rect.left, (int)rect.top, (int)rect.right, (int)rect.bottom));    	
    }
    
    public void SetIconTemplate(Boolean bIcon)
    {
    	m_bIconTemplate = bIcon;
    }
    
    public void SetQMark(Boolean bShow)
    {
    	m_bShowQmark = bShow;
    }
    
// static CGImageRef m_QImage = NULL;
    //static public void DrawQmark(){}
    static public void CreateQmark()
    {
    	
    }
    
    static public void ClearQmark()
    {
    	
    }
    
}
