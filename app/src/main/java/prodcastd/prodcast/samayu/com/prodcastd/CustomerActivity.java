package prodcastd.prodcast.samayu.com.prodcastd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class CustomerActivity extends AppCompatActivity {
    EditText companyName;
    EditText customerId1;
    EditText customerId2;
    Spinner selectDay;
    EditText customerDesc1;
    EditText customerDesc2;
    Spinner area;
    Spinner selectCustomerType;
    Spinner storeType;

    EditText unitNumber;
    EditText billingAddress1;
    EditText billingAddress2;
    EditText billingAddress3;
    EditText city;
    EditText state;
    Spinner country;
    EditText postalCode;
    View focusView = null;

    EditText firstName;
    EditText lastName;
    EditText phoneNumber;
    EditText mobileNumber;
    EditText emailAddress;
    EditText note;
    CheckBox smsAllowed;
    CheckBox active;

    Button resetButton;
    Button nextButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setLogo(R.drawable.logo);
        companyName = (EditText) findViewById(R.id.companyName);
        customerId1 = (EditText) findViewById(R.id.customerId1);
        customerId2 = (EditText) findViewById(R.id.customerId2);
        selectDay = (Spinner) findViewById(R.id.selectDay);
        customerDesc1 = (EditText) findViewById(R.id.customerDesc1);
        customerDesc2 = (EditText) findViewById(R.id.customerDesc2);
        area = (Spinner) findViewById(R.id.area);
        selectCustomerType = (Spinner) findViewById(R.id.selectCustomerType);
        storeType = (Spinner) findViewById(R.id.storetype);

        unitNumber = (EditText) findViewById(R.id.unitNumber);
        billingAddress1 = (EditText) findViewById(R.id.billingAddress1);
        billingAddress2 = (EditText) findViewById(R.id.billingAddress2);
        billingAddress3 = (EditText) findViewById(R.id.billingAddress3);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        country = (Spinner) findViewById(R.id.country);
        postalCode = (EditText) findViewById(R.id.postalCode);

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        mobileNumber = (EditText) findViewById(R.id.mobileNumber);
        emailAddress = (EditText) findViewById(R.id.emailAddress);
        note = (EditText) findViewById(R.id.note);
        smsAllowed = (CheckBox) findViewById(R.id.smsAllowed);
        active = (CheckBox) findViewById(R.id.active);

        resetButton = (Button)findViewById(R.id.reset);
        nextButton = (Button)findViewById(R.id.next);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                companyName.setText("");
                customerId1.setText("");
                customerId2.setText("");
                selectDay.setSelection(0);
                customerDesc1.setText("");
                customerDesc2.setText("");
                area.setSelection(0);
                selectCustomerType.setSelection(0);
                storeType.setSelection(0);

                unitNumber.setText("");
                billingAddress1.setText("");
                billingAddress2.setText("");
                billingAddress3.setText("");
                city.setText("");
                state.setText("");
                country.setSelection(0);
                postalCode.setText("");

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
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cpyName = companyName.getText().toString();
                String cusId1 = customerId1.getText().toString();
                String cusId2 = customerId2.getText().toString();
                String cusDesc1= customerDesc1.getText().toString();
                String cusDesc2= customerDesc2.getText().toString();

                if (checkValid(cpyName, cusId1, cusId2,cusDesc1,cusDesc2 )){
                    return;
                }
            }
        });
    }
    public boolean checkValid(String cpyName,String cusId1,String cusId2,String cusDesc1,String cusDesc2) {
        boolean cancel = false;
        companyName.setError(null);
        customerId1.setError(null);
        customerId2.setError(null);
        customerDesc1.setError(null);
        customerDesc2.setError(null);


        if (TextUtils.isEmpty(cpyName)) {
            companyName.setError("Please enter COMPANY NAME");
            focusView = companyName;
            cancel = true;

        }


        if (TextUtils.isEmpty(cusId1)) {
            customerId1.setError("Please enter CUSTOMER ID1");
            focusView = customerId1;
            cancel = true;
        }
        if (TextUtils.isEmpty(cusId2)) {
            customerId1.setError("Please enter CUSTOMER ID2");
            focusView = customerId2;
            cancel = true;
        }
        if (TextUtils.isEmpty(cusDesc1)) {
            customerDesc1.setError("Please enter CUSTOMER DESC1");
            focusView = customerDesc1;
            cancel = true;
        }
        if (TextUtils.isEmpty(cusDesc2)) {
            customerDesc2.setError("Please enter CUSTOMER DESC2");
            focusView = customerDesc2;
            cancel = true;
        }
        return cancel;

    }





}