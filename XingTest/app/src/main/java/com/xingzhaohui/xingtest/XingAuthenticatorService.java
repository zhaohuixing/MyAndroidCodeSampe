package com.xingzhaohui.xingtest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.xingzhaohui.xingtest.mainmodule.model.XingAccountAuthenticator;

public class XingAuthenticatorService extends Service {
    private XingAccountAuthenticator m_Authenticator;

    public XingAuthenticatorService() {
        m_Authenticator = new XingAccountAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        IBinder binder = m_Authenticator.getIBinder();
        return binder;
    }
}
