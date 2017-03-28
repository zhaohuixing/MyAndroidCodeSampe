package com.xingzhaohui.guicomponent;

import android.app.Activity;
import android.view.View;

public interface ICustomActionBarControllerDelegate 
{
	public Activity GetApplicationActivity();
	public void AddChild(View view);
	public int GetContainerHeight();
	public int GetContainerWidth();
	public int GetContainerLeft();
	public int GetContainerTop();
	public void HandleSearchButtonClicked();
	public void HandleSettingButtonClicked();
	public void HandlePostButtonClicked();
}
