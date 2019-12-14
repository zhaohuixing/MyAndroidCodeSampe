/**
 * 
 */
package com.xgadget.ChuiNiuLite;

/**
 * @author zhaohuixing
 *
 */
public interface IBlockTarget 
{
	public abstract void pushBack(float x);
	public abstract boolean blockBy(IBlockage blockage);
	public abstract boolean hitTestWithBlockage(IBlockage blockage);
	public abstract void escapeFromBlockage(IBlockage blockage);
}
