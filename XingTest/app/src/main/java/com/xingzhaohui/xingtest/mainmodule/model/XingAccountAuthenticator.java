package com.xingzhaohui.xingtest.mainmodule.model;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.text.TextUtils;

import com.xingzhaohui.xingtest.R;
import com.xingzhaohui.xingtest.XingMainActivity;

/**
 * Created by zhaohuixing on 2017-03-22.
 */

public class XingAccountAuthenticator extends AbstractAccountAuthenticator {
    private final Context m_Context;


    public XingAccountAuthenticator(Context context) {
        super(context);
        m_Context = context;
    }


    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response,
                             String accountType,
                             String authTokenType,
                             String[] requiredFeatures,
                             Bundle options) throws NetworkErrorException {
        //Log.d(AccountAuthenticatorService.LOG_TAG, "Adding new account");
        Bundle reply = new Bundle();

        ///Log.d(AccountAuthenticatorService.LOG_TAG, "The auth token type is " + authTokenType);

        Intent i = new Intent(m_Context, XingMainActivity.class);
        //i.setAction("com.gowex.pista.addnewaccount");
        i.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        i.putExtra(m_Context.getText(R.string.intentaccounttokenkey).toString(), authTokenType);
        i.putExtra(m_Context.getText(R.string.intentaccounttypekey).toString(), accountType);
;

        // This key can be anything too. Used for your reference. Can skip it too.
        i.putExtra(m_Context.getText(R.string.addnewaccountkey).toString(), true);
        reply.putParcelable(AccountManager.KEY_INTENT, i);

        return reply;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse arg0, Account arg1,
                                     Bundle arg2) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse arg0, String arg1) {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle bundle) throws NetworkErrorException {
        AccountManager am = AccountManager.get(m_Context);

        String authToken = am.peekAuthToken(account, authTokenType);

        if (TextUtils.isEmpty(authToken)) {
            authToken = m_Context.getText(R.string.deafulttoken).toString(); //"XingtestDefaultOAuthToken";
        }


        if (!TextUtils.isEmpty(authToken)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;
        }

        // If you reach here, person needs to login again. or sign up

        // If we get here, then we couldn't access the user's password - so we
        // need to re-prompt them for their credentials. We do that by creating
        // an intent to display our AuthenticatorActivity which is the AccountsActivity in my case.
        Intent i = new Intent(m_Context, XingMainActivity.class);
        i.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        i.putExtra(m_Context.getText(R.string.intentaccounttypekey).toString(), account.type);
        i.putExtra(m_Context.getText(R.string.intentaccounttokenkey).toString(), authTokenType);

        Bundle retBundle = new Bundle();
        retBundle.putParcelable(AccountManager.KEY_INTENT, i);
        return retBundle;
    }

    @Override
    public String getAuthTokenLabel(String arg0) {

        return "XingTest.Default.Auth.Label";
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse arg0, Account arg1,
                              String[] arg2) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse arg0, Account arg1, String arg2, Bundle arg3) throws NetworkErrorException {
        return null;
    }

    // Handle a user logging out here.
    @Override
    public Bundle getAccountRemovalAllowed(AccountAuthenticatorResponse response, Account account) throws NetworkErrorException {
        return super.getAccountRemovalAllowed(response, account);
    }
}
