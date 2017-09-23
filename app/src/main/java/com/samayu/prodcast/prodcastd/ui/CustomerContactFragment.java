package com.samayu.prodcast.prodcastd.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.samayu.prodcast.prodcastd.dto.Customer;
import com.samayu.prodcast.prodcastd.util.EmailVerification;

import prodcastd.prodcast.samayu.com.prodcastd.R;


public class CustomerContactFragment extends ProdcastValidatedFragment {
    private OnFragmentInteractionListener mListener;
    private  Customer customer = new Customer();
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
    View.OnKeyListener listener = null;

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

        listener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                mobileNumber.setOnKeyListener(null);

                return false;
            }
        };

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


        if (customer != null) {
            lastName.setText(customer.getLastname());
            firstName.setText(customer.getFirstname());
            phoneNumber.setText(customer.getPhonenumber());
            mobileNumber.setText(customer.getCellPhone());

            mobileNumber.setVisibility(View.VISIBLE);

            emailAddress.setText(customer.getEmailaddress());
            note.setText(customer.getNotes());
            active.setVisibility(View.VISIBLE);
            smsAllowed.setChecked(customer.isSmsAllowed());
            active.setChecked(customer.isActive());

        }

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

    public boolean checkValid(String lstName, String fstName, String phneNumber, String mobNumber, String emailAdd,String nte
                               ) {
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

        if (!EmailVerification.validateEmail(emailAdd)){
            emailAddress.setText("Please Enetr Valid email Id");
            focusView = emailAddress;
            cancel =  true;
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
        boolean activ = true;

        return checkValid(lstName,fstName,phneNumber,mobNumber,emailAdd,nte);
    }

    @Override
    public void setDetailsInCustomer(Customer customer) {

        customer.setFirstname(firstName.getText().toString());
        customer.setLastname(lastName.getText().toString());
        customer.setPhonenumber(phoneNumber.getText().toString());
       // customer.setCellPhone(mobileNumber.setVisibility(View.VISIBLE));
        customer.setCellPhone(mobileNumber.getText().toString());


        customer.setEmailaddress(emailAddress.getText().toString());
        customer.setNotes(note.getText().toString());
        customer.setSmsAllowed(smsAllowed.isChecked());
        customer.setActive(active.isChecked());


    }

    @Override
    public void setDetailsFromCustomer(Customer customer) {

this.customer = customer;
    }


}
