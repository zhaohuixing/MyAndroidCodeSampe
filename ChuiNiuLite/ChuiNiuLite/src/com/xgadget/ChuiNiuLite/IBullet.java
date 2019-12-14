/**
 * 
 */
package com.xgadget.ChuiNiuLite;

import android.graphics.PointF;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Rect;

/**
 * @author ZXing
 *
 */
public interface IBullet 
{
	public abstract boolean HitTestWithPoint(PointF pt);
	public abstract boolean HitTestWithPoint(Point pt);
	public abstract boolean HitTestWithPoint(float x, float y);
	public abstract boolean HitTestWithPoint(int x, int y);
	public abstract boolean HitTestWithRect(RectF rect);
	public abstract boolean HitTestWithRect(Rect rect);
	public abstract boolean HitTestWithRect(float left, float top, float right, float bottom);
	public abstract boolean HitTestWithRect(int left, int top, int right, int bottom);
	public abstract boolean Hit(IBullet bullet);
	public abstract RectF getBound();
	public abstract void Reset();
}
