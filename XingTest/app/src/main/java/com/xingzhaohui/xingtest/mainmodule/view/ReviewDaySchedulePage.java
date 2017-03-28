package com.xingzhaohui.xingtest.mainmodule.view;

import android.content.*;
import android.net.Uri;
import android.os.Bundle;
import android.app.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.AdapterView.*;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

import com.xingzhaohui.xingtest.R;
import com.xingzhaohui.xingtest.XingMainActivity;
import com.xingzhaohui.xingtest.mainmodule.data.ScheduleRecord;
import com.xingzhaohui.xingtest.mainmodule.model.AppDataBaseManager;
import com.xingzhaohui.xingtest.mainmodule.model.AppScheduleDBProvider;
import com.xingzhaohui.xingtest.mainmodule.model.DeleteScheduleTask;

import java.util.*;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReviewDaySchedulePage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReviewDaySchedulePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewDaySchedulePage extends Fragment
    implements AppScheduleDBProvider.IScheduleDBActionHandler
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private int                 m_nYear;
    private int                 m_nMonth;
    private int                 m_nDay;

    private Button              m_CloseButton;
    private Button              m_DeleteButton;
    private Button              m_AddButton;
    private ListView            m_ScheduleView;
    private List<ScheduleRecord>  m_ScheduleList;
    boolean                     m_bMainPageReload;
    int                         m_nSelection;


    private  static final       boolean m_UseAysncDeletion = true;

    public ReviewDaySchedulePage(Context context) {
        // Required empty public constructor
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            mListener = null;
        }
        m_nYear = 0;
        m_nMonth = 0;
        m_nDay = 0;
        m_ScheduleList = null;
        m_bMainPageReload = false;
        m_nSelection = -1;
    }

    public ReviewDaySchedulePage() {
        // Required empty public constructor
        m_nYear = 0;
        m_nMonth = 0;
        m_nDay = 0;
        m_ScheduleList = null;
        m_bMainPageReload = false;
        m_nSelection = -1;
    }

    public Fragment operator() {

        return this;
    }

    private void LoadUIElement(View view) {
        m_CloseButton = (Button) view.findViewById(R.id.closebtn);
        m_CloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleCloseButtonClickEvent();
            }
        });

        m_DeleteButton = (Button) view.findViewById(R.id.deletebtn);
        m_DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleDeleteButtonClickEvent();
            }
        });

        m_AddButton = (Button) view.findViewById(R.id.addbtn);
        m_AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleAddScheduleButtonClickEvent();
            }
        });

        m_ScheduleView = (ListView)view.findViewById(R.id.schedulelist);
        m_ScheduleList = null;
    }

    public void LoadSchedules(int nYear, int nMonth, int nDay) {
        m_nSelection = -1;
        m_bMainPageReload = false;
        m_nYear = nYear;
        m_nMonth = nMonth;
        m_nDay = nDay;

        AppDataBaseManager dbMan = XingMainActivity.GetAppContext().GetDatabaseManager();
        AppScheduleDBProvider scheduleDB = dbMan.GetScheduleDBManager();
        m_ScheduleList = scheduleDB.getDayScheduleList(nYear, nMonth, nDay);
        CScheduleListItemAdapter adapter = new CScheduleListItemAdapter(XingMainActivity.GetAppContext(), m_ScheduleList);
        m_ScheduleView.setAdapter(adapter);

        m_ScheduleView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        m_ScheduleView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                m_ScheduleView.setSelection(-1);
                m_ScheduleView.setSelection(position);
                m_nSelection = position;
                view.setBackgroundColor(R.color.DarkBlue);  //XingMainActivity.GetAppContext().getColor(R.color.DarkBlue));
            }
        });
    }

    private void ClosePage()
    {
        if (mListener != null) {
            mListener.onReviewSchedulePageClose(m_bMainPageReload);
        }

    }

    private void HandleCloseButtonClickEvent() {
        ClosePage();
    }

    private void HandleDeleteSelectedSchedule() {
        if(m_ScheduleList == null || m_ScheduleList.size() == 0) {
            return;
        }
        int nSelection = m_nSelection;
        if(nSelection < 0 || m_ScheduleList.size() <= nSelection) {
            return;
        }

        ScheduleRecord schedule = m_ScheduleList.get(nSelection);
        if(schedule == null) {
            return;
        }
        AppDataBaseManager dbMan = XingMainActivity.GetAppContext().GetDatabaseManager();
        AppScheduleDBProvider scheduleDB = dbMan.GetScheduleDBManager();

        if(ReviewDaySchedulePage.m_UseAysncDeletion == true) {
            //DeleteScheduleTask deletion = new DeleteScheduleTask(this, scheduleDB, schedule.m_ScdeduleID);
            //deletion.execute();
           new DeleteScheduleTask(this, scheduleDB, schedule.m_ScdeduleID).execute();
        } else {
            scheduleDB.deleteSchedule(schedule);
            LoadSchedules(m_nYear, m_nMonth, m_nDay);
            m_bMainPageReload = true;
        }
    }

    public void onScheduleRecordDeleted(boolean bResult) {
        if(bResult == true) {
            LoadSchedules(m_nYear, m_nMonth, m_nDay);
            m_bMainPageReload = true;
        }
    }


    private void HandleDeleteButtonClickEvent() {
        if(m_ScheduleList == null || m_ScheduleList.size() == 0) {
            return;
        }
        int nSelection = m_nSelection; //m_ScheduleView.getSelectedItemPosition();
        if(nSelection < 0 || m_ScheduleList.size() <= nSelection) {
            return;
        }

        ScheduleRecord schedule = m_ScheduleList.get(nSelection);
        if(schedule == null) {
            return;
        }

        Calendar cal = Calendar.getInstance();
        cal.set(schedule.m_ScdeduleYear, schedule.m_ScdeduleMonth-1, schedule.m_ScdeduleDay, schedule.m_ScdeduleHour, schedule.m_ScdeduleMinute);
        Date tempDate = cal.getTime();
        String szTime = tempDate.toString();
        String warning = XingMainActivity.GetAppContext().getText(R.string.DoUWantDeleteSchedule) + szTime;

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        HandleDeleteSelectedSchedule();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getView().getContext());
        builder.setMessage(warning).setPositiveButton(XingMainActivity.GetAppContext().getText(R.string.Yes), dialogClickListener)
                .setNegativeButton(XingMainActivity.GetAppContext().getText(R.string.Cancel), dialogClickListener).show();
    }

    private void HandleAddScheduleButtonClickEvent() {
        ClosePage();
        if (mListener != null) {
            mListener.onAddNewSchedule(m_nYear, m_nMonth, m_nDay);
        }
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReviewDaySchedulePage.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewDaySchedulePage newInstance(String param1, String param2) {
        ReviewDaySchedulePage fragment = new ReviewDaySchedulePage();
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
        View view = inflater.inflate(R.layout.fragment_review_day_schedule_page, container, false);

        LoadUIElement(view);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onReviewSchedulePageFragmentInteraction(uri);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onReviewSchedulePageFragmentInteraction(Uri uri);
        void onReviewSchedulePageClose(boolean bMainPageReload);
        void onAddNewSchedule(int nYear, int nMonth, int nDay);
    }

    public class CScheduleListItemAdapter extends BaseAdapter {
        private Context 			    m_Context;
        private List<ScheduleRecord> 	m_ScheduleList;
        LayoutInflater 				    m_Inflater;

        public CScheduleListItemAdapter(Context context,List<ScheduleRecord> list)
        {
            this.m_Context = context;
            this.m_ScheduleList = list;
            m_Inflater = LayoutInflater.from(this.m_Context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup root) {
            final ViewHolder viewHolder;
            View hostView = convertView;
            if(hostView == null) {
                hostView = m_Inflater.inflate(R.layout.reviewschedulepage_listitem_layout, root, false);
                viewHolder = new ViewHolder(hostView);
                hostView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder)hostView.getTag();
            }
            ScheduleRecord item = (ScheduleRecord)getItem(position);
            viewHolder.LoadSchedule(item);
            return hostView;
        }

        public int getCount()
        {
            if(m_ScheduleList == null) {
                return 0;
            }

            return m_ScheduleList.size();
        }

        public Object getItem(int position)
        {
            if(m_ScheduleList == null || m_ScheduleList.size() < position) {
                return null;
            }
            return m_ScheduleList.get(position);
        }

        public long getItemId(int position)
        {
            return position;
        }

///////////////////////////////////////////
        public class ViewHolder {
            public final View m_View;
            public final TextView m_CustomeNameText;
            public final TextView m_StarTimeText;
            public final TextView m_TimeLastText;
            public final TextView m_AddressText;
            public final TextView m_NoteText;
            public ScheduleRecord m_Item;

            public ViewHolder(View view) {
                m_View = view;
                m_CustomeNameText = (TextView) view.findViewById(R.id.customername);
                m_AddressText = (TextView) view.findViewById(R.id.location);
                m_StarTimeText = (TextView) view.findViewById(R.id.starttime);
                m_TimeLastText = (TextView) view.findViewById(R.id.lasttime);
                m_NoteText = (TextView) view.findViewById(R.id.note);
            }

            public void LoadSchedule(ScheduleRecord item) {
                m_Item = item;
                if(m_Item != null) {
                    Context context = XingMainActivity.GetAppContext();
                    m_CustomeNameText.setText(m_Item.m_ScdeduleCustomerName);
                    Calendar cal = Calendar.getInstance();
                    cal.set(m_Item.m_ScdeduleYear, m_Item.m_ScdeduleMonth-1, m_Item.m_ScdeduleDay, m_Item.m_ScdeduleHour, m_Item.m_ScdeduleMinute);
                    Date tempDate = cal.getTime();
                    String szTime = tempDate.toString();
                    m_StarTimeText.setText(context.getText(R.string.StartTime) + " : " + szTime);
                    m_TimeLastText.setText(context.getText(R.string.Time) + " : " + String.valueOf(m_Item.m_ScdeduleTimeLast) + " " + context.getText(R.string.Minutes));
                    m_AddressText.setText(m_Item.m_ScdeduleLocation);
                    m_NoteText.setText(m_Item.m_ScdeduleNote);
                } else {
                    m_CustomeNameText.setText("");
                    m_StarTimeText.setText("");
                    m_TimeLastText.setText("");
                    m_AddressText.setText("");
                    m_NoteText.setText("");
                }
            }
        }

///////////////////////////////////////////
    }
}
