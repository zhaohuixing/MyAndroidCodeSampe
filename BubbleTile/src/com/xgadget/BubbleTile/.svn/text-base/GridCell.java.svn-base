package com.xgadget.BubbleTile;
import android.graphics.PointF;
public class GridCell {
	PointF m_Center;
	
	public GridCell()
	{
		m_Center = new PointF(0.0f, 0.0f);
	}
	public GridCell(float x, float y)
	{
		m_Center = new PointF(x, y);
	}
	public GridCell(PointF pt)
	{
		m_Center = new PointF(pt.x, pt.y);
	}
	public void SetCenter(PointF pt)
	{
		m_Center.x = pt.x;
		m_Center.y = pt.y;
	}
	public void SetCenter(float x, float y)
	{
		m_Center.x = (int)x;
		m_Center.y = (int)y;
	}
	public PointF GetCenter()
	{
		return m_Center;
	}
}
