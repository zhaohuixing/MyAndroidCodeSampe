package com.xingzhaohui.xingtest.mainmodule.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Date;
import java.util.*;
import java.text.SimpleDateFormat;

import com.xingzhaohui.xingtest.R;
import com.xingzhaohui.xingtest.XingMainActivity;
import com.xingzhaohui.xingtest.mainmodule.data.ScheduleRecord;
import com.xingzhaohui.xingtest.mainmodule.model.AppDataBaseManager;
import com.xingzhaohui.xingtest.mainmodule.model.AppScheduleDBProvider;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SchedulePage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SchedulePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SchedulePage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private GridView m_CalenderDaysView;
    private Calendar m_CurrentMonthCalender = Calendar.getInstance(Locale.ENGLISH);

    private static final int MAX_CALENDAR_DAY_CELLS = 42;

    private TextView    m_MonthLabel;
    private Button      m_PrevButton;
    private Button      m_NextButton;




    public SchedulePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SchedulePage.
     */
    // TODO: Rename and change types and number of parameters
    public static SchedulePage newInstance(String param1, String param2) {
        SchedulePage fragment = new SchedulePage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_schedule_page, container, false);

        m_CalenderDaysView =  (GridView) rootView.findViewById(R.id.calendar_grid);
        m_MonthLabel = (TextView)rootView.findViewById(R.id.display_current_date);
        m_PrevButton = (Button)rootView.findViewById(R.id.previous_month);
        m_NextButton = (Button)rootView.findViewById(R.id.next_month);

        m_PrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoPreviousMonth();
            }
        });

        m_NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoNextMonth();
            }
        });

        return rootView;
    }

    private void GotoPreviousMonth() {
        m_CurrentMonthCalender.add(Calendar.MONTH, -1);
        ReloadScheduleList();
    }

    private void GotoNextMonth() {
        m_CurrentMonthCalender.add(Calendar.MONTH, 1);
        ReloadScheduleList();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onSchedulePageInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume () {
        super.onResume();
        ReloadScheduleList();
    }

    public void ReloadScheduleList()
    {
        Vector<Date> currentMont = new Vector<Date>();
        Calendar tempCal = (Calendar)m_CurrentMonthCalender.clone();
        tempCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfTheMonth = tempCal.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
        tempCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
        int currentMonth = m_CurrentMonthCalender.get(Calendar.MONTH) + 1;
        int currentYear = m_CurrentMonthCalender.get(Calendar.YEAR);
        while(currentMont.size() < MAX_CALENDAR_DAY_CELLS)
        {
            currentMont.add(tempCal.getTime());
            tempCal.add(Calendar.DAY_OF_MONTH, 1);
            int tempMonth = tempCal.get(Calendar.MONTH) + 1;
            int tempYear = tempCal.get(Calendar.YEAR);
            if(tempCal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && (currentMonth < tempMonth || currentYear < tempYear))
            {
                break;
            }
        }

        SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
        String szMondthLabel = formatter.format(m_CurrentMonthCalender.getTime());
        m_MonthLabel.setText(szMondthLabel);

        AppDataBaseManager dbMan = XingMainActivity.GetAppContext().GetDatabaseManager();
        AppScheduleDBProvider scheduleDB = dbMan.GetScheduleDBManager();
        List<ScheduleRecord> slist = scheduleDB.getMonthScheduleList(currentYear, currentMonth);

        CalenderDateCellAdapter calAdapter = new CalenderDateCellAdapter(XingMainActivity.GetAppContext(), currentMont, m_CurrentMonthCalender, slist, mListener);
        m_CalenderDaysView.setAdapter(calAdapter);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    //handle
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        //The response handle function for fragement navigation
        void onSchedulePageInteraction(Uri uri);
        void onScheduleItemClickEvent(int nDay, int nMonth, int nYear, boolean bHaveSchedule);
    }

    /**
     * The GridView base product item icons view adapter class
     *
     */
    class CalenderDateCellAdapter extends BaseAdapter {
        LayoutInflater 				m_Inflater;
        private Vector<Date>        m_CurrentMonth;
        private Calendar            m_CurrentDate;
        private List<ScheduleRecord>   m_Schedules;
        private OnFragmentInteractionListener m_Listener;

        public CalenderDateCellAdapter(Context context, Vector<Date> month, Calendar curDate, List<ScheduleRecord> slist, OnFragmentInteractionListener listener)
        {
            m_CurrentMonth = month;
            m_Inflater = LayoutInflater.from(context);
            m_CurrentDate = curDate;

            m_Schedules = slist;
            m_Listener = listener;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup root) {
            Date tempdate = m_CurrentMonth.get(position);
            Calendar dateCal = Calendar.getInstance();
            dateCal.setTime(tempdate);
            int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
            int displayMonth = dateCal.get(Calendar.MONTH) + 1;
            int displayYear = dateCal.get(Calendar.YEAR);
            int currentMonth = m_CurrentDate.get(Calendar.MONTH) + 1;
            int currentYear = m_CurrentDate.get(Calendar.YEAR);
            Calendar todayCalender = Calendar.getInstance(Locale.ENGLISH);
            final int todayDay = todayCalender.get(Calendar.DAY_OF_MONTH); //????
            final int todayMonth = todayCalender.get(Calendar.MONTH) + 1;
            final int todayYear = todayCalender.get(Calendar.YEAR);

            final SchedulCellViewHolder viewHolder;
            View hostView = convertView;
            if(hostView == null) {
                hostView = m_Inflater.inflate(R.layout.calender_cell_layout, root, false);
                viewHolder = new SchedulCellViewHolder();
                viewHolder.m_DayLabelView = (TextView)hostView.findViewById(R.id.calendar_day_label);
                viewHolder.m_EventNoteView = (ImageView)hostView.findViewById(R.id.eventnote_icon);
                hostView.setTag(viewHolder);
            } else {
                viewHolder = (SchedulCellViewHolder)hostView.getTag();
            }
            viewHolder.m_HoseView = hostView;
            viewHolder.m_nYear = displayYear;
            viewHolder.m_nMonth = displayMonth;
            viewHolder.m_nDay = dayValue;

            viewHolder.m_HoseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean bHasSchedule = hasScheduleInDay(viewHolder.m_nDay, viewHolder.m_nMonth, viewHolder.m_nYear, false);
                    if (m_Listener != null) {
                        m_Listener.onScheduleItemClickEvent(viewHolder.m_nDay, viewHolder.m_nMonth, viewHolder.m_nYear, bHasSchedule);
                    }
                }
            });

            boolean bInList = false;
            if(todayDay == dayValue && todayMonth == displayMonth && todayYear == displayYear)
            {
                hostView.setBackgroundColor(ContextCompat.getColor(XingMainActivity.GetAppContext(), R.color.LightPink));
                if(todayMonth == currentMonth && todayYear == currentYear) {
                    bInList = true;
                }
            }
            else if(displayMonth == currentMonth && displayYear == currentYear)
            {
                bInList = true;
                hostView.setBackgroundColor(ContextCompat.getColor(XingMainActivity.GetAppContext(), R.color.Gainsboro));
            }
            else
            {
                hostView.setBackgroundColor(ContextCompat.getColor(XingMainActivity.GetAppContext(), R.color.WhiteSmoke));
            }

            viewHolder.m_DayLabelView.setText(String.valueOf(dayValue));

            boolean bHaveSchedule = hasScheduleInDay (dayValue, displayMonth, displayYear, bInList);

            if(true == bHaveSchedule)
            {
                viewHolder.m_EventNoteView.setVisibility(View.VISIBLE);
            }
            else
            {
                viewHolder.m_EventNoteView.setVisibility(View.INVISIBLE);
            }

            return hostView;
        }

        private boolean hasScheduleInDay (int nDay, int nMonth, int nYear, boolean inList) {
            boolean bRet = false;

            if(inList == false) {
                AppDataBaseManager dbMan = XingMainActivity.GetAppContext().GetDatabaseManager();
                AppScheduleDBProvider scheduleDB = dbMan.GetScheduleDBManager();
                List<ScheduleRecord> slist = scheduleDB.getDayScheduleList(nYear, nMonth, nDay);
                if(slist != null && 0 < slist.size()) {
                    return true;
                }
                return bRet;
            }

            if(m_Schedules != null && 0 < m_Schedules.size())
            {
                for(int i = 0; i < m_Schedules.size(); ++i)
                {
                    ScheduleRecord tempData = m_Schedules.get(i);
                    if(tempData != null)
                    {
                        if(tempData.m_ScdeduleDay == nDay && tempData.m_ScdeduleMonth == nMonth && tempData.m_ScdeduleYear == nYear)
                        {
                            return true;
                        }
                    }
                }
            }
            return bRet;
        }

        @Override
        public int getCount() {
            if(m_CurrentMonth != null ) {
                return m_CurrentMonth.size();
            }

            return 0;
        }

        @Override
        public Object getItem(int position) {
           if(m_CurrentMonth != null && 0 <= position && position < m_CurrentMonth.size()) {
                return m_CurrentMonth.get(position);
            }

            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

    //The product item icon view group UI placeholder class
    //
    public class SchedulCellViewHolder {
        public View         m_HoseView;
        public TextView     m_DayLabelView;
        public ImageView    m_EventNoteView;
        public int          m_nYear;
        public int          m_nMonth;
        public int          m_nDay;

        public SchedulCellViewHolder() {
        }
    }
}
