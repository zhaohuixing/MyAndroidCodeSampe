package com.xingzhaohui.xingtest.mainmodule.view;


/**
 * Created by zxing on 2017.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xingzhaohui.xingtest.XingMainActivity;
import com.xingzhaohui.xingtest.R;

public class AppUIPagesManager extends FragmentPagerAdapter

{
    private AppUIManager        m_Parent;
    private SchedulePage        m_ScheduleTabPage;
    private CustomerPage        m_CustomerTabPage;
    private SettingPage         m_SettingTabPage;


    public AppUIPagesManager(FragmentManager fm, AppUIManager parent) {
        super(fm);
        m_Parent = parent;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                return GetSchedulePage();
            case 1:
                return GetCustomerPage();
            case 2:
                return GetSettingPage();
        }

        return TestFragment.newInstance(position + 1);
    }

    //Initialize and Query schedule page instance
    private SchedulePage GetSchedulePage () {
        if(m_ScheduleTabPage == null) {
            m_ScheduleTabPage = new SchedulePage();
        }
        return m_ScheduleTabPage;
    }

    private CustomerPage GetCustomerPage () {
        if(m_CustomerTabPage == null) {
            m_CustomerTabPage = new CustomerPage();
        }
        return m_CustomerTabPage;
    }

    private SettingPage GetSettingPage () {
        if(m_SettingTabPage == null) {
            m_SettingTabPage = new SettingPage();
        }
        return m_SettingTabPage;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return XingMainActivity.GetAppContext().getString(R.string.Calender); //"Calender";
            case 1:
                return XingMainActivity.GetAppContext().getString(R.string.Customer);//"Customer";
            case 2:
                return XingMainActivity.GetAppContext().getString(R.string.Setting);//"Setting";
        }
        return null;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if(m_Parent != null) {
            m_Parent.HandlePageSelectionChange(position);
        }
    }

    public void onCustomerPageReload() {
        m_CustomerTabPage.ReloadCustomerList();
    }

    public void onSchedulePageReload() {
        m_ScheduleTabPage.ReloadScheduleList();
    }

    public void CreateNewwAccountDone(boolean bResoult) {
        m_SettingTabPage.CreateNewwAccountDone(bResoult);
    }

    public void LoginAccountDone(boolean bResoult) {
        m_SettingTabPage.LoginAccountDone(bResoult);
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class TestFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_TAB_NUMBER = "tab_number";

        public TestFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static TestFragment newInstance(int sectionNumber) {

            TestFragment fragment = new TestFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_TAB_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_xing_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_TAB_NUMBER)));
            return rootView;
        }
    }
}
