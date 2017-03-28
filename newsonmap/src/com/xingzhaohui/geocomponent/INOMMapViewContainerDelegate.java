package com.xingzhaohui.geocomponent;

import com.xingzhaohui.awsservice.NOMNewsMetaDataRecord;

public interface INOMMapViewContainerDelegate 
{
	public void HandleMapViewTouchEvent(double latitude, double longitude);
	public void HandleSelectedNewsMetaData(NOMNewsMetaDataRecord data);
}
