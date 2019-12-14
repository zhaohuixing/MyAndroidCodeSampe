package com.xgadget.XSGService;

import java.util.List;

public interface XSGAPIUserDBServiceDelegate 
{
	public void ConnectDBDomainDone(); //For nofitfication of Connecting DB domain
	public void ConnectDBDomainFailed(); //For nofitfication of Connecting DB domain

	public void RegisterUserDone();
	public void RegisterUserFailed();

	public void UnRegisterUserDone();
	public void UnRegisterUserFailed();
	
	public void DisconnectDBDomainDone();

	public void QueryUserCountOperationDone(int nCount);
	public void QueryBeginUserListOperationDone(List<XSGAPIUser> pLIst);
	public void QueryNextUserListOperationDone(List<XSGAPIUser> pLIst);

}
