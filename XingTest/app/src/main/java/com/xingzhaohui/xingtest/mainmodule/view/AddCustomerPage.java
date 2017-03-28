package com.xingzhaohui.xingtest.mainmodule.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.xingzhaohui.xingtest.R;
import com.xingzhaohui.xingtest.XingMainActivity;
import com.xingzhaohui.xingtest.mainmodule.data.CustomerRecord;
import com.xingzhaohui.xingtest.mainmodule.model.AppCustomerDBProvider;
import com.xingzhaohui.xingtest.mainmodule.model.AppDataBaseManager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddCustomerPage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddCustomerPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCustomerPage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Button          m_CancelButton;
    private Button          m_AddCustomerButton;

    private EditText        m_NameInput;
    private EditText        m_AddressInput;
    private EditText        m_PhoneInput;
    private EditText        m_EmailInput;


    public AddCustomerPage(Context context) {
        // Required empty public constructor
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            mListener = null;
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddCustomerPage.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCustomerPage newInstance(String param1, String param2) {
        AddCustomerPage fragment = new AddCustomerPage(XingMainActivity.GetAppContext());
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void Release() {
        mListener = null;
    }

    //Type casting: Important! must have this overridden operator for UI use with fragement transaction/fragement manager
    public Fragment operator() {

        return this;
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
        m_CancelButton = (Button) view.findViewById(R.id.AddCustomerCancel);
        m_CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleCancelButtonClickEvent();
            }
        });

        m_AddCustomerButton = (Button) view.findViewById(R.id.AddCustomOK);
        m_AddCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleAddCustomerButtonClickEvent();
            }
        });

        m_NameInput = (EditText)view.findViewById(R.id.NameInput);
        m_AddressInput = (EditText)view.findViewById(R.id.AddressInput);
        m_PhoneInput = (EditText)view.findViewById(R.id.PhoneInput);
        m_EmailInput = (EditText)view.findViewById(R.id.EmailInput);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_customer_page, container, false);

        LoadUIElement(view);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAddCustomerPageInteraction(uri);
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

    private void ClosePage() {
        m_NameInput.setText("");
        m_AddressInput.setText("");
        m_PhoneInput.setText("");
        m_EmailInput.setText("");
        m_NameInput.clearFocus();
        m_AddressInput.clearFocus();
        m_PhoneInput.clearFocus();
        m_EmailInput.clearFocus();


        InputMethodManager imm = (InputMethodManager)XingMainActivity.GetAppContext().getSystemService(XingMainActivity.GetAppContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getView().getWindowToken(), 0);
        if (mListener != null) {
            mListener.onCloseCustomerPage();
        }
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



    private void HandleAddCustomerButtonClickEvent() {
        String szName = String.valueOf(m_NameInput.getText());
        if(szName == null || szName.isEmpty() == true) {
            ShowWarningMessage(getString(R.string.InputValidName));
            return;
        }

        String szAddress = String.valueOf(m_AddressInput.getText());
        if(szAddress == null || szAddress.isEmpty() == true) {
            ShowWarningMessage(getString(R.string.InputValidAddress));
            return;
        }
        String szPhone = String.valueOf(m_PhoneInput.getText());
        if(szPhone == null || szPhone.isEmpty() == true) {
            ShowWarningMessage(getString(R.string.InputValidPhone));
            return;
        }

        String szEmail = String.valueOf(m_EmailInput.getText());
        if(szEmail == null || szEmail.isEmpty() == true) {
            ShowWarningMessage(getString(R.string.InputValidEmail));
            return;
        }

        CustomerRecord newCustomer = new CustomerRecord();
        newCustomer.m_CustomerName = szName;
        newCustomer.m_CustomerAddress = szAddress;
        newCustomer.m_CustomerPhone = szPhone;
        newCustomer.m_CustomerEmail = szEmail;

        AppDataBaseManager dbMan = XingMainActivity.GetAppContext().GetDatabaseManager();
        AppCustomerDBProvider customerDB = dbMan.GetCustomerDBManager();
        customerDB.addCustomer(newCustomer);

        ClosePage();
        if (mListener != null) {
            mListener.onNewCustomerRecordAdded();
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
        void onAddCustomerPageInteraction(Uri uri);
        void onCloseCustomerPage();
        void onNewCustomerRecordAdded();
    }
}
