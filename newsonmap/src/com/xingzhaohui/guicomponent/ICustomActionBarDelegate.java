package com.xingzhaohui.guicomponent;

public interface ICustomActionBarDelegate 
{
	public void HandleActionBarButtonClicked(int nButtonID);
	public ICustomActionBarControllerDelegate GetParent();
}
