package com.xgadget.BubbleTile;
import android.graphics.PointF;
import android.graphics.Canvas;

public interface IPuzzleGrid {
	public final double SQURT_3 = 1.732050807568877;
	public final float  SHIFT_FACTOR0 = 1.95f;
	public final float  SHIFT_FACTOR1 = 4.2f;
	public final float	SHIFT_FACTOR2 = 4.4f;
	
	public void CreateAsTemplate( PointF size, enGridLayout enLayout, int nEdge);    
	public enGridType GetGridType();
	public enGridLayout GetGridLayout();
	public void SetGridLayout(enGridLayout gLayout);
	public void SetBubbleUnit( int nEdge);
	public void InitializeGrid();
	public int GetBubbleUnit();

	public int GetBubbleNumberAtRow(int nRowIndex);
	public int GetFirstIndexAtRow(int nRowIndex);

	public int GetGridRow(int nIndex);
	public int GetGridColumne(int nIndex);
	public float GetBubbleDiameter();
	public PointF GetBubbleCenter(int nIndex);
	public void DrawGrid( Canvas context);
	public void DrawMotionGrid(Canvas context);
	public void DrawStaticGrid(Canvas context);
	public void DrawSampleLayout(Canvas context);

	public void ShuffleBubble();
	public Boolean MatchCheck();
	public void CheckBubbleState();
	public void CleanBubbleCheckState();
	public void StartWinAnimation();
	public void StopWinAnimation();
	public Boolean IsWinAnimation();
	public Boolean IsEasyAnimation();
	public void StartEasyAnimation();

	public void CalculateBubbleSize();
	public void InitializeGridCells();
	public void InitializeMatrixLayout();
	public void InitializeSnakeLayout();
	public void InitializeSpiralLayout();

	public void UpdateGridLayout();
	public void Undo();
	public void Reset();
	public int GetUndoLocationInfo();
	public void ExceuteUndoCommand(enBubbleMotion enMotion, enMotionDirection enDir, int nIndex);


	public Boolean OnTouchBegin(PointF pt);
	public Boolean OnTouchMove(PointF pt);
	public Boolean OnTouchEnd(PointF pt);
	
	public void ShiftBubbles();
	public Boolean OnTimerEvent();

	public void CleanTouchState();
	public int GetBubbleCurrentLocationIndex(int nDestIndex);
	public int GetBubbleDestinationIndex(int nCurrentLocationIndex);
	public void CalculateCurrentTouchGesture();
}
