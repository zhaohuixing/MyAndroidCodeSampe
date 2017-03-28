package com.xingzhaohui.xingtest;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.content.*;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.view.ViewGroup;

import com.xingzhaohui.xingtest.mainmodule.controller.*;
import com.xingzhaohui.xingtest.mainmodule.data.CustomerRecord;
import com.xingzhaohui.xingtest.mainmodule.data.ScheduleRecord;
import com.xingzhaohui.xingtest.mainmodule.model.AppDataBaseManager;
import com.xingzhaohui.xingtest.mainmodule.view.AddCustomerPage;
import com.xingzhaohui.xingtest.mainmodule.view.AddSchedulePage;
import com.xingzhaohui.xingtest.mainmodule.view.CustomerPage;
import com.xingzhaohui.xingtest.mainmodule.view.SchedulePage;
import com.xingzhaohui.xingtest.mainmodule.view.SettingPage;
import com.xingzhaohui.xingtest.mainmodule.view.ReviewDaySchedulePage;

public class XingMainActivity extends AppCompatActivity
        implements SchedulePage.OnFragmentInteractionListener,
        CustomerPage.OnListFragmentInteractionListener,
        SettingPage.OnFragmentInteractionListener,
        AddCustomerPage.OnFragmentInteractionListener,
        AddSchedulePage.OnFragmentInteractionListener,
        ReviewDaySchedulePage.OnFragmentInteractionListener
{

    public static XingMainActivity g_AppContext = null;
    public static XingMainActivity GetAppContext() {
        return g_AppContext;
    }
    private AccountManager m_AccountMgr = null;
    private AccountAuthenticatorResponse m_AccountAuthRsponse = null;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    //private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    //private ViewPager mViewPager;

    private AppController   m_Controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xing_main);
        //!!!
        //Must be here for child element to access it, otherwise crash
        //!!!
        XingMainActivity.g_AppContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the App Controller to manager UI and Data.

        m_Controller = new AppController(this);
        m_AccountMgr = AccountManager.get(this);

    }

    public FragmentManager GetFragmentManager() {
        return getSupportFragmentManager();
    }

    public AppController GetController() {
        return m_Controller;
    }

    public AppDataBaseManager GetDatabaseManager() {
        return m_Controller.GetDatabaseManager();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_xing_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
   *  Schedule OnFragmentInteractionListener
   * */
    @Override
    public void onSchedulePageInteraction(Uri uri) {

    }

    @Override
    public void onCustomerPageInteraction(CustomerRecord item) {

    }

    @Override
    public void onSettingPageInteraction(Uri uri) {

    }

    @Override
    public void onAddCustomerPageInteraction(Uri uri) {

    }

    @Override
    public void onAddScheduleInteraction(Uri uri) {

    }

    @Override
    public void onCloseCustomerPage() {
        m_Controller.HandleAddCustomerPageClosed();
    }

    @Override
    public void onNewCustomerRecordAdded() {
        m_Controller.HandledNewCustomerAdded();
    }

    @Override
    public void onCloseAddSchedulePage() {
        m_Controller.HandleAddSchedulePageClosed();
    }

    @Override
    public void onNewScheduleRecordAdded() {
        m_Controller.HandleNewScheduleAdded();
    }

    @Override
    public void onScheduleItemClickEvent(int nDay, int nMonth, int nYear, boolean bHaveSchedule) {
        m_Controller.HandleScheduleItemClickEvent(nDay, nMonth, nYear, bHaveSchedule);
    }

    @Override
    public void onReviewSchedulePageFragmentInteraction(Uri uri) {

    }

    @Override
    public void onReviewSchedulePageClose(boolean bMainPageReload) {
        m_Controller.HandleCloseScheduleReviewPage(bMainPageReload);
    }

    @Override
    public void onAddNewSchedule(int nYear, int nMonth, int nDay) {
        m_Controller.HandleAddScheduleAction(nYear, nMonth, nDay);
    }

    final static String USER_ID_KEY = "com.xingzhaohui.xingtest_user_ID";
    @Override
    public void onCreateAccount(String szUser, final String szPW) {
        final String sUser = szUser;
        final String sPW = szPW;

        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                Boolean bRet = false;
                String authToken = ServerSimulator.httpServer.CreateUserAccount(sUser, sPW, "full_access");
                if (TextUtils.isEmpty(authToken) == false)
                {
                    String mAuthTokenType = getText(R.string.account_type).toString();
                    final Account account = new Account(sUser, mAuthTokenType);
                    Bundle userData = new Bundle();
                    int nLastID = GetLastID()+1;
                    userData.putInt(USER_ID_KEY, nLastID);
                    if (m_AccountMgr.addAccountExplicitly(account, sPW, userData) == true) {
                        m_AccountMgr.setAuthToken(account, mAuthTokenType, authToken);
                        bRet = true;
                    }
                }
                return bRet;
            }

            @Override
            protected void onPostExecute(Boolean bResoult) {
                CreateNewwAccountDone(bResoult);
            }

        }.execute();
    }

    public void CreateNewwAccountDone(boolean bResoult) {
        m_Controller.CreateNewwAccountDone(bResoult);

    }

    public void LoginAccountDone(boolean bResoult) {
        m_Controller.LoginAccountDone(bResoult);

    }
    @Override
    public void onLoginAccount(String szUser, String szPW) {
        final String sUser = szUser;
        final String sPW = szPW;

        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                Boolean bRet = false;
                String authToken = ServerSimulator.httpServer.UserLoginAccount(sUser, sPW);
                if (TextUtils.isEmpty(authToken) == false)
                {
                    String mAuthTokenType = getText(R.string.account_type).toString();
                    final Account account = new Account(sUser, mAuthTokenType);
                    m_AccountMgr.setPassword(account, sPW);
                }
                return bRet;
            }

            @Override
            protected void onPostExecute(Boolean bResoult) {
                LoginAccountDone(bResoult);
            }

        }.execute();
    }

    final static String PREF_ID_KEY = "com.xingzhaohui.xingtest_pref_ID";
    final static String PREF_ID_VALUE_KEY = "com.xingzhaohui.xingtest_pref_ID_valuekey";

    private int GetLastID() {
        int nID = 0;
        SharedPreferences prefs = this.getSharedPreferences(PREF_ID_KEY, 0);
        if(prefs != null) {
            nID = prefs.getInt(PREF_ID_VALUE_KEY, 0);
        }
        return nID;
    }

    private void SetLastID(int nID) {
        SharedPreferences prefs = this.getSharedPreferences(PREF_ID_KEY, 0);
        if(prefs != null) {
            SharedPreferences.Editor editor = prefs.edit();
            if(editor != null) {
                editor.putInt(PREF_ID_VALUE_KEY, nID);
                editor.commit();
            }
        }
    }
}
