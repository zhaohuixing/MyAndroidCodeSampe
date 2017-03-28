package com.xingzhaohui.xingtest.mainmodule.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xingzhaohui.xingtest.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingPage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingPage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private Button              m_CreateAccountButton;
    private Button              m_LoginAccountButton;


    private EditText            m_CreateUserInput;
    private EditText            m_CreatePWInput;
    private EditText            m_ConfirmPWInput;

    private EditText            m_LoginUserInput;
    private EditText            m_LoginPWInput;

    private TextView            m_CreateAccountResult;
    private TextView            m_LoginAccountResult;

    private OnFragmentInteractionListener mListener;

    public SettingPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingPage.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingPage newInstance(String param1, String param2) {
        SettingPage fragment = new SettingPage();
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
        m_CreateAccountButton = (Button) view.findViewById(R.id.createaccountbtn);
        m_CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleCreateAccountButtonClickEvent();
            }
        });

        m_LoginAccountButton = (Button) view.findViewById(R.id.loginaccountbtn);
        m_LoginAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleLoginAccountButtonClickEvent();
            }
        });

        m_CreateUserInput = (EditText) view.findViewById(R.id.createuserinput);
        m_CreatePWInput = (EditText) view.findViewById(R.id.createpwinput);
        m_ConfirmPWInput = (EditText) view.findViewById(R.id.confimpwinput);

        m_LoginUserInput = (EditText) view.findViewById(R.id.loginuserinput);
        m_LoginPWInput = (EditText) view.findViewById(R.id.loginpwinput);

        m_CreateAccountResult = (TextView) view.findViewById(R.id.resultlabel);
        m_LoginAccountResult = (TextView) view.findViewById(R.id.loginresultlabel);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting_page, container, false);

        LoadUIElement(view);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onSettingPageInteraction(uri);
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

    private void HandleCreateAccountButtonClickEvent() {
        String szUser = String.valueOf(m_CreateUserInput.getText());
        String szPW = String.valueOf(m_CreatePWInput.getText());

        if (mListener != null) {
            mListener.onCreateAccount(szUser, szPW);
        }
    }

    private void HandleLoginAccountButtonClickEvent() {
        String szUser = String.valueOf(m_LoginUserInput.getText());
        String szPW = String.valueOf(m_LoginPWInput.getText());

        if (mListener != null) {
            mListener.onLoginAccount(szUser, szPW);
        }
    }

    public void CreateNewwAccountDone(boolean bResoult) {
       if(bResoult == true) {
           m_CreateAccountResult.setText("Create New Account Succeed!");
       } else {
           m_CreateAccountResult.setText("Create New Account Failed!");
       }
    }

    public void LoginAccountDone(boolean bResoult) {
        if(bResoult == true) {
            m_LoginAccountResult.setText("Login Account Succeed!");
        } else {
            m_LoginAccountResult.setText("Login Account Failed!");
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
        void onSettingPageInteraction(Uri uri);
        void onCreateAccount(String szUser, String szPW);
        void onLoginAccount(String szUser, String szPW);
    }
}
