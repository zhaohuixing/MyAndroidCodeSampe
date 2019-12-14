package com.xgadget.BubbleTile;
import android.graphics.Canvas;
import android.graphics.PointF;

public class PlayBoardController {

	IPuzzleGrid m_Puzzle;
public PlayBoardController()
{          
    enGridType enType = GameConfiguration.GetGridType();
    enGridLayout enLayout = GameConfiguration.GetGridLayout();
    int nEdge = GameConfiguration.GetGridBubbleUnit(enType);
    m_Puzzle = CPuzzleGrid.CreateGrid(enType, enLayout, nEdge);
}

public void DrawGame(Canvas context)
{
    if(m_Puzzle != null)
        m_Puzzle.DrawGrid(context);
}

public void InitializePuzzle(enGridType enType, enGridLayout enLayout, int nEdge)
{
    if(m_Puzzle != null)
        m_Puzzle = null;
    
    m_Puzzle = CPuzzleGrid.CreateGrid(enType, enLayout, nEdge);
}

public void UndoGame()
{
    if(m_Puzzle != null)
        m_Puzzle.Undo();
}

public void ResetGame()
{
    if(m_Puzzle != null)
    {    
        m_Puzzle.Reset();
    }    
}

public Boolean IsWinAnimation()
{
    Boolean bRet = false;
    if(m_Puzzle != null)
    {    
        bRet = m_Puzzle.IsWinAnimation();
    }    
    return bRet;
}

public Boolean IsEasyAnimation()
{
    Boolean bRet = false;
    if(m_Puzzle != null)
    {    
        bRet = m_Puzzle.IsEasyAnimation();
    }    
    return bRet;
}

public void StartEasyAnimation()
{
    if(m_Puzzle != null)
    {    
        m_Puzzle.StartEasyAnimation();
    }    
}

public void UpdatePuzzleLayout()
{
    if(m_Puzzle != null)
        m_Puzzle.UpdateGridLayout();
}

public Boolean OnTimerEvent()
{
    Boolean bRet = false;
    if(m_Puzzle != null)
        bRet = m_Puzzle.OnTimerEvent();
    return bRet;
}

public Boolean OnTouchBegin(PointF pt)
{
    Boolean bRet = false;
    
    float fSize = CGameLayout.GetPlayBoardSize();
    if(pt.x <= 0 || fSize <= pt.x || pt.y <= 0 || fSize <= pt.y || m_Puzzle == null)
        return bRet;
        
    bRet = m_Puzzle.OnTouchBegin(pt);
    
    return bRet;
}

public Boolean OnTouchMove(PointF pt)
{
    Boolean bRet = false;

    float fSize = CGameLayout.GetPlayBoardSize();
    if(pt.x <= 0 || fSize <= pt.x || pt.y <= 0 || fSize <= pt.y || m_Puzzle == null)
        return bRet;

    bRet = m_Puzzle.OnTouchMove(pt);
    
    return bRet;
}

public Boolean OnTouchEnd(PointF pt)
{
    Boolean bRet = false;
    
    //float fSize = [GameLayout GetPlayBoardSize];
    //if(pt.x <= 0 || fSize <= pt.x || pt.y <= 0 || fSize <= pt.y || m_Puzzle == nil)
    if(m_Puzzle == null)
        return bRet;
  
    bRet = m_Puzzle.OnTouchEnd(pt);
    
    return bRet;
}

public void CheckBubbleState()
{
    if(m_Puzzle!=null)
        m_Puzzle.CheckBubbleState();
}

public void CleanBubbleCheckState()
{
    if(m_Puzzle!=null)
        m_Puzzle.CleanBubbleCheckState();
}
/*
-(void)TestSuite
{
#ifdef __RUN_TESTSUITE__
    if(m_Puzzle)
        [m_Puzzle TestSuite];
#endif    
}
*/
}
