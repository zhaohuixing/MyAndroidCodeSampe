package com.xingzhaohui.xingtest.mainmodule.controller;

import com.xingzhaohui.xingtest.XingMainActivity;
import com.xingzhaohui.xingtest.mainmodule.model.AppCustomerDBProvider;
import com.xingzhaohui.xingtest.mainmodule.model.AppDataBaseManager;
import com.xingzhaohui.xingtest.mainmodule.model.AppScheduleDBProvider;
import com.xingzhaohui.xingtest.mainmodule.view.AppUIManager;

import java.lang.ref.WeakReference;

/**
 * Created by zxing on 2017.
 */

public class AppController {
    private WeakReference<XingMainActivity>         m_MainContext;
    private AppUIManager                            m_UIManager;

    private AppDataBaseManager                      m_DBManger;

    public AppController(XingMainActivity context) {
        m_MainContext = new WeakReference<XingMainActivity>(context);
        m_UIManager = new AppUIManager(context);
        m_DBManger = new AppDataBaseManager(context);
    }

    public AppDataBaseManager GetDatabaseManager() {
        return m_DBManger;
    }

    public AppCustomerDBProvider GetCustomerDBManager() {
        return m_DBManger.GetCustomerDBManager();
    }

    public AppScheduleDBProvider GetScheduleDBManager() {
        return m_DBManger.GetScheduleDBManager();
    }

    public void HandleAddCustomerPageClosed() {
        m_UIManager.CloseAddCustomerPage();
    }

    public void HandledNewCustomerAdded() {
        m_UIManager.UpdateCustomerPage();
    }

    public void HandleAddSchedulePageClosed() {
        m_UIManager.CloseAddSchedulePage();
    }

    public void HandleNewScheduleAdded() {
        m_UIManager.HandleNewScheduleAdded();
    }

    public void HandleScheduleItemClickEvent(int nDay, int nMonth, int nYear, boolean bHaveSchedule) {
        m_UIManager.HandleScheduleItemClickEvent(nDay, nMonth, nYear, bHaveSchedule);
    }

    public void HandleCloseScheduleReviewPage(boolean bMainPageReload) {
        m_UIManager.CloseReviewSchedulePage(bMainPageReload);
    }

    public void HandleAddScheduleAction(int nYear, int nMonth, int nDay) {
        m_UIManager.AddNewSchedule(nYear, nMonth, nDay);
    }

    public void CreateNewwAccountDone(boolean bResoult) {
        m_UIManager.CreateNewwAccountDone(bResoult);

    }

    public void LoginAccountDone(boolean bResoult) {
        m_UIManager.LoginAccountDone(bResoult);

    }

}
