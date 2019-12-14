/**
 * 
 */
package com.xgadget.XSGService;

import java.util.List;

/**
 * @author zhaohuixing
 *
 */
public interface XSGAPIScoreBoardDelegate 
{
	public void ProcessHighScoreCount();
	public void ProcessPlayerScore(XSGAPIScoreBoardIntScore scoreRecord);
	public void ProcessPlayerList(List<XSGAPIScoreBoardIntScore> scoreList);
	public void ProcessPlayerList2(List<XSGAPIScoreBoardIntScore> scoreList, boolean bQueryNext);
	public void AddHighScoreDone();
	public void CreateDomainDone();
	public void DeleteHighScoreDone();
}
