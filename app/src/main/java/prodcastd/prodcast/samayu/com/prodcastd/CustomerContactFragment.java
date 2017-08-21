package prodcastd.prodcast.samayu.com.prodcastd;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.samayu.prodcast.prodcastd.SessionInfo;
import com.samayu.prodcast.prodcastd.dto.Area;
import com.samayu.prodcast.prodcastd.dto.Country;
import com.samayu.prodcast.prodcastd.dto.ProdcastDTO;
import com.samayu.prodcast.prodcastd.dto.StoreType;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;
import com.samayu.prodcast.prodcastd.ui.CustomerCreateEditActivity;

import retrofit2.Call;
import retrofit2.Callback;

import static prodcastd.prodcast.samayu.com.prodcastd.R.id.area;
import static prodcastd.prodcast.samayu.com.prodcastd.R.id.country;
import static prodcastd.prodcast.samayu.com.prodcastd.R.id.selectDay;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CustomerContactFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CustomerContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerContactFragment extends ProdcastValidatedFragment {
    private OnFragmentInteractionListener mListener;
    EditText firstName;
    EditText lastName;
    EditText phoneNumber;
    EditText mobileNumber;
    EditText emailAddress;
    EditText note;
    CheckBox smsAllowed;
    CheckBox active;

    Button resetButton;
    Button saveButton;

    View focusView = null;

    public CustomerContactFragment() {
        // Required empty public constructor
    }

    public static CustomerContactFragment newInstance() {
        CustomerContactFragment fragment = new CustomerContactFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_customer_contact, container, false);
        // Inflate the layout for this fragment

        firstName = (EditText) view.findViewById(R.id.firstName);
        lastName = (EditText) view.findViewById(R.id.lastName);
        phoneNumber = (EditText) view.findViewById(R.id.phoneNumber);
        mobileNumber = (EditText) view.findViewById(R.id.mobileNumber);
        emailAddress = (EditText) view.findViewById(R.id.emailAddress);
        note = (EditText) view.findViewById(R.id.note);
        smsAllowed = (CheckBox) view.findViewById(R.id.smsAllowed);
        active = (CheckBox) view.findViewById(R.id.active);

        resetButton = (Button)view.findViewById(R.id.reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName.setText("");
                lastName.setText("");
                phoneNumber.setText("");
                mobileNumber.setText("");
                emailAddress.setText("");
                note.setText("");
                smsAllowed.setChecked(false);
                active.setChecked(false);

            }
        });
                //nextButton = (Button)findViewById(R.id.next);
        saveButton = (Button)view.findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View view) {
                CustomerCompanyFragment cc=new  CustomerCompanyFragment();
                cc.validate();
                CustomerAddressFragment ca =new CustomerAddressFragment();
                ca.validate();
                CustomerContactFragment cco = new CustomerContactFragment();
                cco.validate();

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        void onFragmentInteraction(Uri uri);
    }
    public boolean checkValid(String lstName, String fstName, String phneNumber, String mobNumber, String emailAdd,String nte,
                               String smsAllow, Boolean activ) {
        boolean cancel = false;
        firstName.setError("");
        lastName.setError("");
        phoneNumber.setError("");
        mobileNumber.setError("");
        emailAddress.setError("");
        note.setError("");
        if (TextUtils.isEmpty(lstName)) {
            lastName.setError("Please Enter Last Name");
            focusView = lastName;
            cancel = true;
        }
        if (TextUtils.isEmpty(fstName)) {
            firstName.setError("Please Enter First Name");
            focusView = firstName;
            cancel = true;
        }
        if (TextUtils.isEmpty(phneNumber)) {
            phoneNumber.setError("Please Enter Phone Number");
            focusView = phoneNumber;
            cancel = true;
        }
        if (TextUtils.isEmpty(mobNumber)) {
            mobileNumber.setError("Please Enter Mobile Number");
            focusView = mobileNumber;
            cancel = true;
        }
        if (TextUtils.isEmpty(emailAdd)) {
            emailAddress.setError("Please Enter Email Address");
            focusView = emailAddress;
            cancel = true;
        }
        if (TextUtils.isEmpty(nte)) {
            note.setError("Please Enter Note");
            focusView = note;
            cancel = true;
        }
        if(smsAllowed.isChecked()){
            smsAllowed.setError("Please Select");
        }
        if(active.isChecked()){
            active.setError("Please Select");
        }

        return cancel;

    }
    public boolean validate() {

        boolean cancel = false;
        String lstName = lastName.getText().toString();
        String fstName = firstName.getText().toString();
        String phneNumber = phoneNumber.getText().toString();
        String mobNumber = mobileNumber.getText().toString();
        String emailAdd = emailAddress.getText().toString();
        String nte = note.getText().toString();
        String smsAllow = String.valueOf(smsAllowed.isChecked());
        boolean activ = true;

        return checkValid(lstName,fstName,phneNumber,mobNumber,emailAdd,nte,smsAllow,activ);
    }


}
