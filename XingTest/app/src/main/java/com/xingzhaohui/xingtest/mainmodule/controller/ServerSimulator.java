package com.xingzhaohui.xingtest.mainmodule.controller;

import com.xingzhaohui.xingtest.XingMainActivity;
import com.xingzhaohui.xingtest.R;
/**
 * Created by zhaohuixing on 2017-03-23.
 */

public class ServerSimulator {

    public final static ServerSimulator httpServer = new ServerSimulator();

    public ServerSimulator () {

    }

    public String CreateUserAccount(String userName, String userPW, String accessLevel) {
        //Simulate server authentication
        String oAuthToken = XingMainActivity.GetAppContext().getText(R.string.deafulttoken).toString();
        return oAuthToken;
    }

    public String UserLoginAccount(String userName, String userPW) {
        //Simulate server authentication
        String oAuthToken = XingMainActivity.GetAppContext().getText(R.string.deafulttoken).toString();
        return oAuthToken;
    }

}
