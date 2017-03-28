package com.xingzhaohui.xingtest.mainmodule.data;

/**
 * Created by zxing on 2017-03.
 */

public class ScheduleRecord {
    public int      m_ScdeduleID;
    public int      m_ScdeduleYear;
    public int      m_ScdeduleMonth;
    public int      m_ScdeduleDay;
    public int      m_ScdeduleHour;
    public int      m_ScdeduleMinute;
    public int      m_ScdeduleTimeLast;
    public int      m_ScdeduleCustomerID;
    public String   m_ScdeduleCustomerName;
    public String   m_ScdeduleLocation;
    public String   m_ScdeduleNote;


    public ScheduleRecord() {
        m_ScdeduleID = 0;
        m_ScdeduleYear = 0;
        m_ScdeduleMonth = 0;
        m_ScdeduleDay = 0;
        m_ScdeduleHour = 0;
        m_ScdeduleMinute = 0;
        m_ScdeduleTimeLast = 0;
        m_ScdeduleCustomerID = 0;
        m_ScdeduleCustomerName = "";
        m_ScdeduleLocation = "";
        m_ScdeduleNote = "";
    }
}
