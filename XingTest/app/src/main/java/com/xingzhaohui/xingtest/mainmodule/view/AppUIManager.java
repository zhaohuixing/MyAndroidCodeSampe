package com.xingzhaohui.xingtest.mainmodule.view;

/**
 * Created by zxing on 2017.
 */

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.xingzhaohui.xingtest.R;
import com.xingzhaohui.xingtest.XingMainActivity;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Locale;


public class AppUIManager {
    private WeakReference<XingMainActivity> m_MainContext;

    private AppUIPagesManager       m_UIPageManager;
    private AddCustomerPage         m_AddCustomerPage;
    private AddSchedulePage         m_AddSchedulePage;
    private ReviewDaySchedulePage      m_ScheduleReviewPage;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager m_UIPageContainer;

    private FloatingActionButton m_AddActionButton;

    public AppUIManager(XingMainActivity context) {
        m_MainContext = new WeakReference<XingMainActivity>(context);

        m_UIPageManager = new AppUIPagesManager(context.getSupportFragmentManager(), this);

        // Set up the ViewPager with the sections adapter.
        m_UIPageContainer = (ViewPager) context.findViewById(R.id.container);
        m_UIPageContainer.setAdapter(m_UIPageManager);

        TabLayout tabLayout = (TabLayout) context.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(m_UIPageContainer);

        m_AddActionButton = (FloatingActionButton) context.findViewById(R.id.fab);
        m_AddActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HandleAddActionButtonClicked(view);
            }
        });

        InitializeExtraUI();
    }

    public void InitializeExtraUI() {
        FragmentManager fgmMan = m_MainContext.get().getFragmentManager();
        m_AddCustomerPage = new AddCustomerPage(m_MainContext.get());
        FragmentTransaction transAddCustomer = fgmMan.beginTransaction();
        transAddCustomer.replace(R.id.addcustomerpage, (Fragment)m_AddCustomerPage);  //fragment "onAttach" may not be called by this appoach
        transAddCustomer.hide(m_AddCustomerPage);
        transAddCustomer.commit();

        m_AddSchedulePage = new AddSchedulePage(m_MainContext.get());
        FragmentTransaction transAddSchedule = fgmMan.beginTransaction();
        transAddSchedule.replace(R.id.addschedulepage, (Fragment)m_AddSchedulePage);  //fragment "onAttach" may not be called by this appoach
        transAddSchedule.hide(m_AddSchedulePage);
        transAddSchedule.commit();

        m_ScheduleReviewPage = new ReviewDaySchedulePage(m_MainContext.get());
        FragmentTransaction transReviewSchedule = fgmMan.beginTransaction();
        transReviewSchedule.replace(R.id.reviewchedulepage, (Fragment)m_ScheduleReviewPage); //fragment "onAttach" may not be called by this appoach
        transReviewSchedule.hide(m_ScheduleReviewPage);
        transReviewSchedule.commit();
    }

    public void HandlePageSelectionChange(int nSelectedPage) {
        if(m_AddCustomerPage.isVisible() == true || m_AddSchedulePage.isVisible() == true || m_ScheduleReviewPage.isVisible() == true) {
            m_AddActionButton.setVisibility(View.GONE);
            return;
        }

        if(nSelectedPage == 0 || nSelectedPage == 1) {
            m_AddActionButton.setVisibility(View.VISIBLE);
        } else {
            m_AddActionButton.setVisibility(View.GONE); //View.GONE invisablie and not take any layout space, View.INVISIBLE: invisable but still tak space
        }
    }

    public void ShowHideActionButton(boolean bShow) {
        if(bShow == true) {
            m_AddActionButton.setVisibility(View.VISIBLE);
        } else {
            m_AddActionButton.setVisibility(View.GONE); //View.GONE invisablie and not take any layout space, View.INVISIBLE: invisable but still tak space
        }
    }

    public void HandleAddActionButtonClicked(View view) {
        int pageIndex = m_UIPageContainer.getCurrentItem();
        if(pageIndex == 0) {
            //Snackbar.make(view, "Add new schedule", Snackbar.LENGTH_LONG)
            //        .setAction("Action", null).show();
            AddNewSchedule();
        } else if(pageIndex == 1) {
            AddNewCustomer();
        }
    }

    public void AddNewSchedule() {
        OpenAddSchedulePage();
    }

    public void AddNewSchedule(int nYear, int nMonth, int nDay) {
        ShowHideActionButton(false);
        FragmentManager fgmMan = m_MainContext.get().getFragmentManager();
        FragmentTransaction transaction = fgmMan.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.show(m_AddSchedulePage);
        transaction.commit();
        m_AddSchedulePage.InitializeTimeSetting(nYear, nMonth, nDay);
    }

    public void AddNewCustomer() {
        OpenAddCustomerPage();
    }

    public void OpenAddSchedulePage() {
        ShowHideActionButton(false);
        FragmentManager fgmMan = m_MainContext.get().getFragmentManager();
        FragmentTransaction transaction = fgmMan.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.show(m_AddSchedulePage);
        transaction.commit();

        ///...................
        Calendar CurrentMonthCalender = Calendar.getInstance(Locale.ENGLISH);
        int currentMonth = CurrentMonthCalender.get(Calendar.MONTH) + 1;
        int currentYear = CurrentMonthCalender.get(Calendar.YEAR);
        int currentDay = CurrentMonthCalender.get(Calendar.DAY_OF_MONTH);
        m_AddSchedulePage.InitializeTimeSetting(currentYear, currentMonth, currentDay);
    }

    public void OpenAddCustomerPage() {
        ShowHideActionButton(false);
        FragmentManager fgmMan = m_MainContext.get().getFragmentManager();
        FragmentTransaction transaction = fgmMan.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.show(m_AddCustomerPage);
        transaction.commit();
    }

    public void CloseAddSchedulePage() {
        ShowHideActionButton(true);
        FragmentManager fgmMan = m_MainContext.get().getFragmentManager();
        FragmentTransaction transaction = fgmMan.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.hide(m_AddSchedulePage);
        transaction.commit();
    }

    public void CloseAddCustomerPage() {
        ShowHideActionButton(true);
        FragmentManager fgmMan = m_MainContext.get().getFragmentManager();
        FragmentTransaction transaction = fgmMan.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.hide(m_AddCustomerPage);
        transaction.commit();
    }

    public void CloseReviewSchedulePage(boolean bMainPageReload) {
        ShowHideActionButton(true);
        FragmentManager fgmMan = m_MainContext.get().getFragmentManager();
        FragmentTransaction transaction = fgmMan.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.hide(m_ScheduleReviewPage);
        transaction.commit();
        if(bMainPageReload == true) {
            m_UIPageManager.onSchedulePageReload();
        }
    }

    public void OpenReviewSchedulePage() {
        ShowHideActionButton(false);
        FragmentManager fgmMan = m_MainContext.get().getFragmentManager();
        FragmentTransaction transaction = fgmMan.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.show(m_ScheduleReviewPage);
        transaction.commit();
    }

    public void OpenDayScheduleReviewPage(int nYear, int nMonth, int nDay) {
        m_ScheduleReviewPage.LoadSchedules(nYear, nMonth, nDay);
        OpenReviewSchedulePage();
    }

    public void UpdateCustomerPage() {
        m_UIPageManager.onCustomerPageReload();
    }

    public void HandleNewScheduleAdded() {
        m_UIPageManager.onSchedulePageReload();
    }

    public void HandleScheduleItemClickEvent(int nDay, int nMonth, int nYear, boolean bHaveSchedule) {
        if(bHaveSchedule == false) {
            AddNewSchedule(nYear, nMonth, nDay);
        } else {
            OpenDayScheduleReviewPage(nYear, nMonth, nDay);
        }
    }

    public void CreateNewwAccountDone(boolean bResoult) {
        m_UIPageManager.CreateNewwAccountDone(bResoult);

    }

    public void LoginAccountDone(boolean bResoult) {
        m_UIPageManager.LoginAccountDone(bResoult);

    }

}
