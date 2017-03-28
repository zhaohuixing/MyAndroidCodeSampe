package com.xingzhaohui.xingtest.mainmodule.model;

/**
 * Created by zhaohuixing on 2017-03-19.
 */
import android.os.AsyncTask;
import com.xingzhaohui.xingtest.mainmodule.model.AppScheduleDBProvider;


public class DeleteScheduleTask extends AsyncTask<Void, Void, Boolean> {
    AppScheduleDBProvider.IScheduleDBActionHandler m_Handler;
    AppScheduleDBProvider           m_DBEnginer;
    int                             m_nRecordID;

    public DeleteScheduleTask(AppScheduleDBProvider.IScheduleDBActionHandler handler, AppScheduleDBProvider db, int nRecordID) {
        m_Handler = handler;
        m_DBEnginer = db;
        m_nRecordID = nRecordID;
    }

    protected Boolean doInBackground(Void...voids)
    {
        boolean bRet = false;

        if(m_DBEnginer != null) {
            m_DBEnginer.deleteSchedule(m_nRecordID);
            bRet = true;
        }

        return bRet;
    }

    protected void onPostExecute(Boolean result)
    {
        if(m_Handler != null) {
            m_Handler.onScheduleRecordDeleted(result);
        }
    }
}
