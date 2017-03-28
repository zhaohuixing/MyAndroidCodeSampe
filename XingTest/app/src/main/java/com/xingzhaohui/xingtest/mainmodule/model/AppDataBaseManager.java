package com.xingzhaohui.xingtest.mainmodule.model;


/**
 * Created by zxing on 2017-03.
 */
import android.content.Context;

import com.xingzhaohui.xingtest.mainmodule.data.CustomerRecord;

import java.util.Vector;


public class AppDataBaseManager {
    private AppCustomerDBProvider           m_CustomerDBEngine;
    private AppScheduleDBProvider           m_ScheduleDBEngine;


    public AppDataBaseManager(Context context) {
        m_CustomerDBEngine = new AppCustomerDBProvider(context, null);
        m_ScheduleDBEngine = new AppScheduleDBProvider(context, null);
    }

    public AppCustomerDBProvider GetCustomerDBManager() {
        return m_CustomerDBEngine;
    }

    public AppScheduleDBProvider GetScheduleDBManager() {
        return m_ScheduleDBEngine;
    }


    public interface ICustomerDBDataReceiver {
        // TODO: Update argument type and name
        void HandleRetreiveAllCustomers(Vector<CustomerRecord> customerList);
    }


}
