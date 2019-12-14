package com.xgadget.BubbleTile;

import java.lang.Math;
import android.util.FloatMath;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class SquareGrid extends CPuzzleGrid{	
	public SquareGrid()
	{
	}

	public enGridType GetGridType()
	{
	    return enGridType.PUZZLE_GRID_SQUARE;
	}

	public void InitializeMatrixLayout()
	{
	    int k = 0;
	    for (int i = 0; i < m_nEdge; ++i) 
	    {
	        for(int j = 0; j < m_nEdge; ++j)
	        {
	            BubbleObject pb = new BubbleObject(k+1, m_bIconTemplate);
	            pb.m_nCurrentLocationIndex = k;
	            m_Bubbles.add(pb);
	            ++k;
	        }
	    }
	}

	public void InitializeSnakeLayout()
	{
	    int k = 0;
	    for (int i = 0; i < m_nEdge; ++i) 
	    {
	        if(i%2 == 0)
	        {    
	            for(int j = 0; j < m_nEdge; ++j)
	            {
	                BubbleObject pb = new BubbleObject(k+1, m_bIconTemplate);
	                pb.m_nCurrentLocationIndex = k;
	                m_Bubbles.add(pb);
	                ++k;
	            }
	        }
	        else
	        {
	            int n = k+m_nEdge;
	            for(int j = 0; j < m_nEdge; ++j)
	            {
	                BubbleObject pb = new BubbleObject(n-j, m_bIconTemplate);
	                pb.m_nCurrentLocationIndex = k;
	                m_Bubbles.add(pb);
	                ++k;
	            }
	        }
	    }
	}

	public void InitializeSpiralLayout()
	{
	    if(m_nEdge <= 0)
	        return;
	    
	    int n = 0;
	    int i, j, k, l, m;
	    for (i = 0; i < m_nEdge; ++i) 
	    {
	        for(j = 0; j < m_nEdge; ++j)
	        {
	            BubbleObject pb = new BubbleObject();
	            pb.SetIconTemplate(m_bIconTemplate);
	            pb.m_nCurrentLocationIndex = n;
	            m_Bubbles.add(pb);
	            ++n;
	        }
	    }

	    n = 1;
	    int index;
	    int nThreshed = m_nEdge-1;
	    for (i = 0; i <= nThreshed-i; ++i) 
	    {
	        //upper bound of circle
	        for(j = i; j <= nThreshed-i; j++)
	        {
	            index = i*m_nEdge + j;
	            m_Bubbles.get(index).SetLabel(n);
	            ++n;
	        }
	        //right bound of circle
	        for(k = i+1; k <= nThreshed-i; k++)
	        {
	            index = k*m_nEdge + (nThreshed-i);
	            m_Bubbles.get(index).SetLabel(n);
	            ++n;
	        }
	        //lower bound of circle
	        for(l = nThreshed-i-1; i <=l; --l)
	        {
	            index = (nThreshed-i)*m_nEdge + l;
	            m_Bubbles.get(index).SetLabel(n);
	            ++n;
	        }
	        //left bound of circle
	        for(m = nThreshed-i-1; i < m; --m)
	        {
	            index = m*m_nEdge + i;
	            m_Bubbles.get(index).SetLabel(n);
	            ++n;
	        }
	    }
	    
	}

	public void CalculateBubbleSize()
	{
	    float gridSize = this.GetGridMaxSize(m_nEdge);
	    m_fBubbleSize = gridSize/((float)m_nEdge);
	}

	public void InitializeGridCells()
	{
	    float fInnerSize = m_fBubbleSize*(m_nEdge-1);
	    PointF ptCenter = this.GetGridCenter();
	    float startX = ptCenter.x - fInnerSize*0.5f;
	    float startY = ptCenter.y - fInnerSize*0.5f;
	    
	    for (int i = 0; i < m_nEdge; ++i) 
	    {
	        for(int j = 0; j < m_nEdge; ++j)
	        {
	            float x = startX + m_fBubbleSize*j;
	            float y = startY + m_fBubbleSize*i;
	            GridCell pc = new GridCell(x, y);
	            m_Cells.add(pc);
	        }
	    }
	    
	}

	public int GetGridRow(int nIndex)
	{
	    int nRet = -1;

	    if(0 <= nIndex && nIndex < m_nEdge*m_nEdge)
	    {
	        float f1 = (float)nIndex;
	        float f2 = (float)m_nEdge;
	        float f3 = f1/f2;
	        nRet = (int)f3;
	    }    
	    return nRet;
	}

	public int GetGridColumne(int nIndex)
	{
	    int nRet = -1;
	    
	    if(0 <= nIndex && nIndex < m_nEdge*m_nEdge)
	    {
	        nRet = nIndex%m_nEdge;
	    }    
	    return nRet;
	}

	public int GetBubbleNumberAtRow(int nRowIndex)
	{
	    int nRet = -1;
	    
	    if(0 <= nRowIndex && nRowIndex < m_nEdge)
	        nRet = m_nEdge;
	    
	    return nRet;
	}

	public int GetFirstIndexAtRow(int nRowIndex)
	{
	    int nRet = -1;
	    
	    if(0 <= nRowIndex && nRowIndex < m_nEdge)
	        nRet = nRowIndex*m_nEdge;
	    
	    return nRet;
	}

	public void UpdateGridLayout()
	{
	    super.UpdateGridLayout();

	    this.CalculateBubbleSize();

	    float fInnerSize = m_fBubbleSize*(m_nEdge-1);
	    PointF ptCenter = this.GetGridCenter();
	    float startX = ptCenter.x - fInnerSize*0.5f;
	    float startY = ptCenter.y - fInnerSize*0.5f;

	    int nIndex = 0;
	    for (int i = 0; i < m_nEdge; ++i) 
	    {
	        for(int j = 0; j < m_nEdge; ++j)
	        {
	            float x = startX + m_fBubbleSize*j;
	            float y = startY + m_fBubbleSize*i;
	            m_Cells.get(nIndex).SetCenter(x, y);
	            ++nIndex;
	        }
	    }
	}

	public void CalculateCurrentTouchGesture()
	{
	    if(0 <= m_nTouchedCellIndex)
	    {
	        PointF ptCenter = this.GetBubbleCenter(m_nTouchedCellIndex);
	        float x1 = ptCenter.x;
	        float y1 = ptCenter.y;
	        float x2 = m_ptTouchPoint.x;
	        float y2 = m_ptTouchPoint.y;
	        float dx = x2 - x1;
	        float dx2 = dx*dx;
	        float dy = y2 - y1;
	        float dy2 = dy*dy;
	        
	        if(dy2 == 0 && dx2 == 0)
	            return;
	        
	        Boolean bChange = false;
	        if(m_Motion == enBubbleMotion.BUBBLE_MOTION_NONE)
	        {    
	            float temp = FloatMath.sqrt(dy2+dx2);
	            if(temp < CGameLayout.GetBubbleMotionThreshold(m_fBubbleSize))
	                return;
	            
	            if(dy2 <= dx2)
	            {
	                m_Motion = enBubbleMotion.BUBBLE_MOTION_HORIZONTAL;
	                if(0 < dx)
	                    m_Direction = enMotionDirection.MOTION_DIRECTION_FORWARD;
	                else
	                    m_Direction = enMotionDirection.MOTION_DIRECTION_BACKWARD;
	                m_fOffset = FloatMath.sqrt(dx2);
	            }
	            else
	            {
	                m_Motion = enBubbleMotion.BUBBLE_MOTION_VERTICAL;
	                if(dy < 0)
	                    m_Direction = enMotionDirection.MOTION_DIRECTION_FORWARD;
	                else
	                    m_Direction = enMotionDirection.MOTION_DIRECTION_BACKWARD;
	                m_fOffset = FloatMath.sqrt(dy2);
	            }
	            bChange = true;
	        }
	        else if(m_Motion == enBubbleMotion.BUBBLE_MOTION_HORIZONTAL)
	        {
	            if(0 < dx)
	            {    
	                if(m_Direction != enMotionDirection.MOTION_DIRECTION_FORWARD)
	                    bChange = true;
	                m_Direction = enMotionDirection.MOTION_DIRECTION_FORWARD;
	            }    
	            else
	            {    
	                if(m_Direction != enMotionDirection.MOTION_DIRECTION_BACKWARD)
	                    bChange = true;
	                m_Direction = enMotionDirection.MOTION_DIRECTION_BACKWARD;
	            }    
	            m_fOffset = FloatMath.sqrt(dx2);
	        }
	        else if(m_Motion == enBubbleMotion.BUBBLE_MOTION_VERTICAL)
	        {
	            if(dy < 0)
	            {    
	                if(m_Direction != enMotionDirection.MOTION_DIRECTION_FORWARD)
	                    bChange = true;
	                m_Direction = enMotionDirection.MOTION_DIRECTION_FORWARD;
	            }    
	            else
	            {    
	                if(m_Direction != enMotionDirection.MOTION_DIRECTION_BACKWARD)
	                    bChange = true;
	                m_Direction = enMotionDirection.MOTION_DIRECTION_BACKWARD;
	            }    
	            m_fOffset = FloatMath.sqrt(dy2);
	        }
	        if(bChange)    
	            super.InitializeArrowAnimation();
	            
	        if(m_fBubbleSize*CGameLayout.GetTouchSensitivity() <= m_fOffset)
	        {
	            m_fOffset = m_fBubbleSize*CGameLayout.GetTouchSensitivity(); 
	        }
	    }
	}

	private void RowShiftForward(int nRowIndex)
	{
	    int nCount = this.GetBubbleNumberAtRow(nRowIndex);
	    
	    if(nCount <= 1)
	        return;
	    
	    int nStartIndex = this.GetFirstIndexAtRow(nRowIndex);
	    
	    int b1 = this.GetBubbleDestinationIndex(nStartIndex + nCount - 1);
	    BubbleObject pbFirst = m_Bubbles.get(b1);
	    
	    for(int i = (nStartIndex + nCount - 2); nStartIndex <= i; --i)
	    {    
	        b1 = this.GetBubbleDestinationIndex(i);
	        BubbleObject pb = m_Bubbles.get(b1);
	        pb.m_nCurrentLocationIndex = i+1;
	    }
	    pbFirst.m_nCurrentLocationIndex = nStartIndex;
	}

	private void HorzShiftBubblesForward()
	{
	    if(m_nTouchedCellIndex < 0)
	        return;
	    int nRowIndex = this.GetGridRow(m_nTouchedCellIndex);

	    this.RowShiftForward(nRowIndex);
	}

	private void RowShiftBackward(int nRowIndex)
	{
	    int nCount = this.GetBubbleNumberAtRow(nRowIndex);
	    
	    if(nCount <= 1)
	        return;
	    
	    int nStartIndex = this.GetFirstIndexAtRow(nRowIndex);
	    
	    int b1 = this.GetBubbleDestinationIndex(nStartIndex);
	    BubbleObject pbFirst = m_Bubbles.get(b1);
	    
	    for(int i = nStartIndex + 1; i <= nStartIndex+nCount-1; ++i)
	    {    
	        b1 = this.GetBubbleDestinationIndex(i);
	        BubbleObject pb = m_Bubbles.get(b1);
	        pb.m_nCurrentLocationIndex = i-1;
	    }
	    pbFirst.m_nCurrentLocationIndex = nStartIndex+nCount-1;
	}

	private void HorzShiftBubblesBackward()
	{
	    if(m_nTouchedCellIndex < 0)
	        return;
	    int nRowIndex = this.GetGridRow(m_nTouchedCellIndex);

	    this.RowShiftBackward(nRowIndex);
	}

	private void HorzShiftBubbles()
	{
	    switch(m_Direction)
	    {
	        case MOTION_DIRECTION_FORWARD:
	            this.HorzShiftBubblesForward();
	            break;
	        case MOTION_DIRECTION_BACKWARD:
	            this.HorzShiftBubblesBackward();
	            break;
	    }
	}

	private void ColumnShiftForward(int nColIndex)
	{
	    int nStartIndex = nColIndex;
	    
	    int b1 = this.GetBubbleDestinationIndex(nStartIndex);
	    BubbleObject pbFirst = m_Bubbles.get(b1);
	    
	    for(int i = 1; i < m_nEdge; ++i)
	    {
	        int k = i*m_nEdge+nColIndex;
	        b1 = this.GetBubbleDestinationIndex(k);
	        BubbleObject pb = m_Bubbles.get(b1);
	        pb.m_nCurrentLocationIndex = k-m_nEdge;
	    }
	    pbFirst.m_nCurrentLocationIndex = (m_nEdge-1)*m_nEdge+nColIndex;
	}

	private void VertShiftBubblesForward()
	{
	    if(m_nTouchedCellIndex < 0)
	        return;
	    int nColIndex = this.GetGridColumne(m_nTouchedCellIndex);

	    this.ColumnShiftForward(nColIndex);
	}

	private void ColumnShiftBackward(int nColIndex)
	{
	    int b1 = this.GetBubbleDestinationIndex((m_nEdge-1)*m_nEdge+nColIndex);
	    BubbleObject pbFirst = m_Bubbles.get(b1);
	    
	    for(int i = m_nEdge-2; 0 <= i; --i)
	    {
	        int k = i*m_nEdge+nColIndex;
	        b1 = this.GetBubbleDestinationIndex(k);
	        BubbleObject pb = m_Bubbles.get(b1);
	        pb.m_nCurrentLocationIndex = k+m_nEdge;
	    }
	    pbFirst.m_nCurrentLocationIndex = nColIndex;
	}

	private void VertShiftBubblesBackward()
	{
	    if(m_nTouchedCellIndex < 0)
	        return;
	    int nColIndex = this.GetGridColumne(m_nTouchedCellIndex);

	    this.ColumnShiftBackward(nColIndex);
	}


	private void VerticalShiftBubbles()
	{
	    switch(m_Direction)
	    {
	        case MOTION_DIRECTION_FORWARD:
	            this.VertShiftBubblesForward();
	            break;
	        case MOTION_DIRECTION_BACKWARD:
	            this.VertShiftBubblesBackward();
	            break;
	    }
	}

	public void ShiftBubbles()
	{
	    switch(m_Motion)
	    {
	        case BUBBLE_MOTION_HORIZONTAL:
	            this.HorzShiftBubbles();
	            break;
	        case BUBBLE_MOTION_VERTICAL:
	            this.VerticalShiftBubbles();
	            break;
	    }
	    super.ShiftBubbles();
	}

	public void ExceuteUndoCommand(enBubbleMotion enMotion, enMotionDirection enDir, int nIndex)
	{
	    switch(enMotion)
	    {
	        case BUBBLE_MOTION_HORIZONTAL:
	        {
	            if(enDir == enMotionDirection.MOTION_DIRECTION_FORWARD)
	            {
	                this.RowShiftBackward(nIndex);
	            }
	            else if(enDir == enMotionDirection.MOTION_DIRECTION_BACKWARD)
	            {
	                this.RowShiftForward(nIndex);
	            }
	            break;
	        }    
	        case BUBBLE_MOTION_VERTICAL:
	        {    
	            if(enDir == enMotionDirection.MOTION_DIRECTION_FORWARD)
	            {
	                this.ColumnShiftBackward(nIndex);
	            }
	            else if(enDir == enMotionDirection.MOTION_DIRECTION_BACKWARD)
	            {
	                this.ColumnShiftForward(nIndex);
	            }
	            break;
	        }    
	    }
	}

	private void DrawMotionGridHorizontal(Canvas context)
	{
	    if(m_nTouchedCellIndex < 0)
	    {
	        this.DrawStaticGrid(context);
	        return;
	    }
	    
	    int nRowIndex = this.GetGridRow(m_nTouchedCellIndex);
	    float xOffset = m_fOffset;
	    if(m_Direction == enMotionDirection.MOTION_DIRECTION_BACKWARD)
	    {
	        xOffset *= -1.0;
	    }
	    
	    int k = 0;
	    RectF rect;
	    PointF ptCenter;
	    float fRad = m_fBubbleSize/2.0f;
	    for (int i = 0; i < m_nEdge; ++i) 
	    {
	        for(int j = 0; j < m_nEdge; ++j)
	        {
	            ptCenter = this.GetBubbleCenter(k);
	            if(nRowIndex == i)
	            {
	                ptCenter.x += xOffset;
	            }
	            rect = new RectF(ptCenter.x-fRad, ptCenter.y-fRad, ptCenter.x-fRad + m_fBubbleSize, ptCenter.y-fRad + m_fBubbleSize);
	            int nIndex = this.GetBubbleDestinationIndex(k);
	            BubbleObject pb = m_Bubbles.get(nIndex);
	            if(!pb.equals(null))
	                pb.DrawBubble(context, rect);
	            ++k;
	        }
	    }
	}


	private void DrawMotionGridVertical(Canvas context)
	{
	    if(m_nTouchedCellIndex < 0)
	    {
	        this.DrawStaticGrid(context);
	        return;
	    }
	    
	    int nColIndex = this.GetGridColumne(m_nTouchedCellIndex);
	    float yOffset = m_fOffset;
	    if(m_Direction == enMotionDirection.MOTION_DIRECTION_FORWARD)
	    {
	        yOffset *= -1.0;
	    }
	    
	    int k = 0;
	    RectF rect;
	    PointF ptCenter;
	    float fRad = m_fBubbleSize/2.0f;
	    for (int i = 0; i < m_nEdge; ++i) 
	    {
	        for(int j = 0; j < m_nEdge; ++j)
	        {
	            ptCenter = this.GetBubbleCenter(k);
	            if(nColIndex == j)
	            {
	                ptCenter.y += yOffset;
	            }
	            
	            rect = new RectF(ptCenter.x-fRad, ptCenter.y-fRad, ptCenter.x-fRad + m_fBubbleSize, ptCenter.y-fRad + m_fBubbleSize);
	            int nIndex = this.GetBubbleDestinationIndex(k);
	            BubbleObject pb = m_Bubbles.get(nIndex);
	            if(!pb.equals(null))
	                pb.DrawBubble(context, rect);
	            ++k;
	        }
	    }
	}

	public void DrawMotionGrid(Canvas context)
	{
	    switch(m_Motion)
	    {
	        case BUBBLE_MOTION_HORIZONTAL:
	            this.DrawMotionGridHorizontal(context);
	            break;
	        case BUBBLE_MOTION_VERTICAL:
	            this.DrawMotionGridVertical(context);
	            break;
	    }
	    if(m_Motion != enBubbleMotion.BUBBLE_MOTION_NONE)
	        super.DrawArrowAnimation(context);
	}

	public int GetUndoLocationInfo()
	{
	    int nRet = -1;
	    
	    if(m_Motion == enBubbleMotion.BUBBLE_MOTION_HORIZONTAL)
	    {
	        nRet = this.GetGridRow(m_nTouchedCellIndex);
	    }
	    else if(m_Motion == enBubbleMotion.BUBBLE_MOTION_VERTICAL)
	    {
	        nRet = this.GetGridColumne(m_nTouchedCellIndex);
	    }
	    
	    return nRet;
	}

	public void TestSuite()
	{
	    
	}

	public static void DrawSample(Canvas context, RectF rect, int nEdge)
	{
	    float fBubbleSize = Math.min(rect.width(), rect.height())/((float)nEdge);
	    float fInnerSize = fBubbleSize*((float)(nEdge-1));
	    PointF ptCenter = new PointF(rect.left+rect.width()*0.5f, rect.top+rect.height()*0.5f);
	    float startX = ptCenter.x - fInnerSize*0.5f;
	    float startY = ptCenter.y - fInnerSize*0.5f;
	    float fRad = fBubbleSize*0.5f;
	    
	    Drawable  bkImage = null;
	    enBubbleType enBType = GameConfiguration.GetBubbleType();
	    
	    if(enBType == enBubbleType.PUZZLE_BUBBLE_STAR)
	    {
	        bkImage = CResourceHelper.GetStarBubbleImage();//[ImageLoader LoadImageWithName:@"starballs.png"];
	    }
	    else if(enBType == enBubbleType.PUZZLE_BUBBLE_WOOD)
	    {
	    	bkImage = CResourceHelper.GetFrogBubbleImage();//[ImageLoader LoadImageWithName:@"woodball.png"];
	    }
	    else
	    {    
	        bkImage = CResourceHelper.GetColorBubbleImage();//[ImageLoader LoadImageWithName:@"bubble.png"];
	    }    
	    for (int i = 0; i < nEdge; ++i) 
	    {
	        for(int j = 0; j < nEdge; ++j)
	        {
	            float x = startX + fBubbleSize*j;
	            float y = startY + fBubbleSize*i;
	            Rect rt = new Rect((int)(x-fRad), (int)(y-fRad), (int)(x-fRad + fBubbleSize), (int)(y-fRad + fBubbleSize));
	            bkImage.setBounds(rt);
	            bkImage.draw(context);
	        }
	    }
//	    CGImageRelease(bkImage);
	}

	
}
