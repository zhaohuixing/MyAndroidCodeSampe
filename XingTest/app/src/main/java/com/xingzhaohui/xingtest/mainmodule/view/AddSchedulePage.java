package com.xingzhaohui.xingtest.mainmodule.view;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.widget.AdapterView;
import android.widget.AdapterView.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.xingzhaohui.xingtest.R;
import com.xingzhaohui.xingtest.XingMainActivity;
import com.xingzhaohui.xingtest.mainmodule.data.CustomerRecord;
import com.xingzhaohui.xingtest.mainmodule.data.ScheduleRecord;
import com.xingzhaohui.xingtest.mainmodule.model.AppCustomerDBProvider;
import com.xingzhaohui.xingtest.mainmodule.model.AppDataBaseManager;
import com.xingzhaohui.xingtest.mainmodule.model.AppScheduleDBProvider;

import java.util.*;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddSchedulePage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddSchedulePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSchedulePage extends Fragment implements TimePickerDialog.OnTimeSetListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "addschedule_param1";
    private static final String ARG_PARAM2 = "addschedule_param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button              m_CancelButton;
    private Button              m_AddScheduleButton;

    private TextView            m_YearMonthDayLabel;
    private int                 m_nYear;
    private int                 m_nMonth;
    private int                 m_nDay;
    private int                 m_nHour;
    private int                 m_nMinute;

    private int                     m_nSlectedCustomerID;
    private String                  m_SlectedCustomerName;


    private EditText            m_AppointLenghtInput;
    private EditText            m_LocationInput;
    private EditText            m_NoteInput;
    private Spinner             m_CustomerSelector;
    private Button              m_TimePicker;

    private OnFragmentInteractionListener mListener;

    public AddSchedulePage(Context context) {
        // Required empty public constructor
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            mListener = null;
        }
        m_nYear = 0;
        m_nMonth = 0;
        m_nDay = 0;
        m_nHour = 0;
        m_nMinute = 0;
        m_nSlectedCustomerID = -1;
        m_SlectedCustomerName = "";
    }

    public void Release() {
        m_nSlectedCustomerID = -1;
        mListener = null;
    }

    //Type casting: Important! must have this overridden operator for UI use with fragement transaction/fragement manager
    public Fragment operator() {

        return this;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddSchedulePage.
     */
    // TODO: Rename and change types and number of parameters
    public static AddSchedulePage newInstance(String param1, String param2) {
        AddSchedulePage fragment = new AddSchedulePage(XingMainActivity.GetAppContext());
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

    private void LoadUIElement(View view) {
        m_CancelButton = (Button) view.findViewById(R.id.AddScheduleCancel);
        m_CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleCancelButtonClickEvent();
            }
        });

        m_AddScheduleButton = (Button) view.findViewById(R.id.AddScheduleOK);
        m_AddScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleAddScheduleButtonClickEvent();
            }
        });

        m_TimePicker = (Button) view.findViewById(R.id.TimePickControl);
        m_TimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleSelectTimeButtonClickEvent();
            }
        });

        m_AppointLenghtInput = (EditText)view.findViewById(R.id.TimeLastInput);
        m_LocationInput = (EditText)view.findViewById(R.id.LocationInput);
        m_NoteInput = (EditText)view.findViewById(R.id.NoteInput);
        m_YearMonthDayLabel = (TextView)view.findViewById(R.id.DateLabel);
        m_CustomerSelector = (Spinner)view.findViewById(R.id.CustomerList);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_schedule_page, container, false);

        LoadUIElement(view);
        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAddScheduleInteraction(uri);
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

    public void HandleSelectTimeButtonClickEvent() {
        TimePickerDialog timeDlg = new TimePickerDialog(XingMainActivity.GetAppContext(), this, m_nHour, m_nMinute, true);
        timeDlg.setTitle(getString(R.string.SelectTime));
        timeDlg.show();
    }

    public void InitializeTimeSetting(int nYear, int nMonth, int nDay) {
        m_nYear = nYear;
        m_nMonth = nMonth;
        m_nDay = nDay;
        m_nHour = 0;
        m_nMinute = 0;
        m_nSlectedCustomerID = -1;
        m_SlectedCustomerName = "";

        String label = String.valueOf(m_nYear) +"-" + String.valueOf(m_nMonth) + "-" + String.valueOf(m_nDay);
        m_YearMonthDayLabel.setText(label);

        AppDataBaseManager dbMan = XingMainActivity.GetAppContext().GetDatabaseManager();
        AppCustomerDBProvider customerDB = dbMan.GetCustomerDBManager();
        List<CustomerRecord>  customerList = customerDB.getAllCustomersList();

        OnItemSelectedListener onItemSelectedListener =
                new OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        CustomerRecord obj = (CustomerRecord)(parent.getItemAtPosition(position));
                        m_nSlectedCustomerID = obj.m_CustomerID;
                        m_SlectedCustomerName = obj.m_CustomerName;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        m_nSlectedCustomerID = -1;
                        m_SlectedCustomerName = "";
                    }
                };


        CustomerSpinnerAdapter adapter = new CustomerSpinnerAdapter(XingMainActivity.GetAppContext(), android.R.layout.simple_spinner_dropdown_item, customerList);
        m_CustomerSelector.setAdapter(adapter);
        m_CustomerSelector.setOnItemSelectedListener(onItemSelectedListener);
        String szBtn = getString(R.string.SelectTime)+"("+String.valueOf(m_nHour)+":"+String.valueOf(m_nMinute)+")";
        m_TimePicker.setText(szBtn);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        m_nHour = hourOfDay;
        m_nMinute = minute;
        String szBtn = getString(R.string.SelectTime)+"("+String.valueOf(m_nHour)+":"+String.valueOf(m_nMinute)+")";
        m_TimePicker.setText(szBtn);
    }

    private void ClosePage() {
        InputMethodManager imm = (InputMethodManager)XingMainActivity.GetAppContext().getSystemService(XingMainActivity.GetAppContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getView().getWindowToken(), 0);
        if (mListener != null) {
            mListener.onCloseAddSchedulePage();
        }
        m_nYear = 0;
        m_nMonth = 0;
        m_nDay = 0;
        m_nHour = 0;
        m_nMinute = 0;
        m_AppointLenghtInput.setText("");
        m_LocationInput.setText("");
        m_NoteInput.setText("");
        m_YearMonthDayLabel.setText("");

        m_nSlectedCustomerID = -1;
        m_SlectedCustomerName = "";
    }

    private void ShowWarningMessage(String szWarning) {
        AlertDialog alertDialog = new AlertDialog.Builder(XingMainActivity.GetAppContext()).create();
        alertDialog.setTitle(getString(R.string.app_name));
        alertDialog.setMessage(szWarning);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.Close),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int which) {
                        dlg.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void HandleCancelButtonClickEvent() {
        ClosePage();
    }

    private void HandleAddScheduleButtonClickEvent() {
        if(m_nSlectedCustomerID == -1) {
            ShowWarningMessage(getString(R.string.SelectValidCustomer));
            return;
        }
        int nTimeLast = Integer.parseInt(m_AppointLenghtInput.getText().toString());
        if(nTimeLast <= 0) {
            ShowWarningMessage(getString(R.string.SelectValidTimeLast));
            return;
        }

        String szLocation = String.valueOf(m_LocationInput.getText());
        if(szLocation == null || szLocation.isEmpty() == true) {
            ShowWarningMessage(getString(R.string.SelectValidLocation));
            return;
        }

        String szNote = String.valueOf(m_NoteInput.getText());

        ScheduleRecord newSchedule = new ScheduleRecord();
        newSchedule.m_ScdeduleCustomerID = m_nSlectedCustomerID;
        newSchedule.m_ScdeduleCustomerName = m_SlectedCustomerName;
        newSchedule.m_ScdeduleYear = m_nYear;
        newSchedule.m_ScdeduleMonth = m_nMonth;
        newSchedule.m_ScdeduleDay = m_nDay;
        newSchedule.m_ScdeduleHour = m_nHour;
        newSchedule.m_ScdeduleMinute = m_nMinute;
        newSchedule.m_ScdeduleTimeLast = nTimeLast;
        newSchedule.m_ScdeduleLocation =  szLocation;
        newSchedule.m_ScdeduleNote = szNote;

        AppDataBaseManager dbMan = XingMainActivity.GetAppContext().GetDatabaseManager();
        AppScheduleDBProvider scheduleDB = dbMan.GetScheduleDBManager();
        scheduleDB.addSchedule(newSchedule);

        ClosePage();
        if (mListener != null) {
            mListener.onNewScheduleRecordAdded();
        }
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
        void onAddScheduleInteraction(Uri uri);
        void onCloseAddSchedulePage();
        void onNewScheduleRecordAdded();
    }

    public class CustomerSpinnerAdapter extends ArrayAdapter<CustomerRecord>{
        private Context context;
        private List<CustomerRecord>    m_CustomerList;

        public CustomerSpinnerAdapter(Context context, int textViewResourceId, List<CustomerRecord> objs) {
            super(context, textViewResourceId, objs);
            this.context = context;
            this.m_CustomerList = objs;
        }

        public int getCount(){
            return m_CustomerList.size();
        }

        public CustomerRecord getItem(int position){
            return m_CustomerList.get(position);
        }

        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView label = new TextView(context);
            label.setText(m_CustomerList.get(position).m_CustomerName);
            return label;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView label = new TextView(context);
            label.setText(m_CustomerList.get(position).m_CustomerName);
            return label;
        }
    }
}
