package com.xgadget.BubbleTile;

public class CUndoCommand {
    public enBubbleMotion      m_Motion;
    public enMotionDirection   m_Direction;
    public int                 m_PositionIndex;
    
    public CUndoCommand()
    {
        m_Motion = enBubbleMotion.BUBBLE_MOTION_NONE;
        m_Direction = enMotionDirection.MOTION_DIRECTION_NONE;
        m_PositionIndex = -1;
    }
    
}
