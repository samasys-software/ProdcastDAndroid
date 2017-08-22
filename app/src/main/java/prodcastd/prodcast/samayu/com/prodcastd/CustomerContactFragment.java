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
import com.samayu.prodcast.prodcastd.dto.Customer;
import com.samayu.prodcast.prodcastd.dto.ProdcastDTO;
import com.samayu.prodcast.prodcastd.dto.StoreType;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;
import com.samayu.prodcast.prodcastd.ui.CustomerCreateEditActivity;
import com.samayu.prodcast.prodcastd.ui.OnFragmentInteractionListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static prodcastd.prodcast.samayu.com.prodcastd.R.id.area;
import static prodcastd.prodcast.samayu.com.prodcastd.R.id.companyName;
import static prodcastd.prodcast.samayu.com.prodcastd.R.id.country;
import static prodcastd.prodcast.samayu.com.prodcastd.R.id.customer;
import static prodcastd.prodcast.samayu.com.prodcastd.R.id.customerId1;
import static prodcastd.prodcast.samayu.com.prodcastd.R.id.selectDay;



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
               mListener.validateAndSave();

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

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

    public boolean checkValid(String lstName, String fstName, String phneNumber, String mobNumber, String emailAdd,String nte,
                               String smsAllow, Boolean activ) {
        boolean cancel = false;
        firstName.setError (null);
        lastName.setError(null);
        phoneNumber.setError(null);
        mobileNumber.setError(null);
        emailAddress.setError(null);
        note.setError(null);
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

    @Override
    public void setDetailsInCustomer(Customer customer) {
        customer.setLastname(lastName.getText().toString());
    }


}
