/*
package prodcastd.prodcast.samayu.com.prodcastd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.samayu.prodcast.prodcastd.dto.ProdcastDTO;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    Button saveButton;




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
        //nextButton = (Button)findViewById(R.id.next);
        saveButton = (Button)findViewById(R.id.save);

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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveRegistration()){
                    Intent intent=new Intent(CustomerActivity.this,CustomersActivity.class);
                    startActivity(intent);
                };




            }
        });
    }
    public boolean checkValid(String cpyName, String cusId1, String cusId2, String cusDesc1, String cusDesc2, String unitNum,String billAdd1,String billAdd2,String billAdd3,String stat, String cty, String pincode, String lstName, String fstName, String phneNumber, String mobNumber, String emailAdd) {
        boolean cancel = false;
        companyName.setError("");
        customerId1.setError("");
        customerId2.setError("");
        selectDay.setSelection(0);
        customerDesc1.setError("");
        customerDesc2.setError("");
        area.setSelection(0);
        selectCustomerType.setSelection(0);
        storeType.setSelection(0);

        unitNumber.setError("");
        billingAddress1.setError("");
        billingAddress2.setError("");
        billingAddress3.setError("");
        city.setError("");
        state.setError("");
        country.setSelection(0);
        postalCode.setError("");

        firstName.setError("");
        lastName.setError("");
        phoneNumber.setError("");
        mobileNumber.setError("");
        emailAddress.setError("");



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
        //if(Integer.)
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
        if (TextUtils.isEmpty(unitNum)) {
            unitNumber.setError("Please enter UNIT NUMBER");
            focusView = unitNumber;
            cancel = true;
        }
        if (TextUtils.isEmpty(billAdd1)) {
            billingAddress1.setError("Please enter BILLING ADDRESS1");
            focusView = billingAddress1;
            cancel = true;
        }
        if (TextUtils.isEmpty(billAdd2)) {
            billingAddress2.setError("Please enter BILLING ADDRESS1");
            focusView = billingAddress2;
            cancel = true;
        }
        if (TextUtils.isEmpty(billAdd3)) {
            billingAddress3.setError("Please enter BILLING ADDRESS1");
            focusView = billingAddress3;
            cancel = true;
        }
        if (TextUtils.isEmpty(cty)) {
            city.setError("Please enter CITY");
            focusView = city;
            cancel = true;
        }
        if (TextUtils.isEmpty(stat)) {
            state.setError("Please enter STATE");
            focusView = state;
            cancel = true;
        }
        if (TextUtils.isEmpty(pincode)) {
            postalCode.setError("Please enter POSTALCODE");
            focusView = state;
            cancel = true;
        }
        if (TextUtils.isEmpty(lstName)) {
            lastName.setError("Please enter LASTNAME");
            focusView = lastName;
            cancel = true;
        }
        if (TextUtils.isEmpty(fstName)) {
            firstName.setError("Please enter FIRSTNAME");
            focusView = firstName;
            cancel = true;
        }
        if (TextUtils.isEmpty(phneNumber)) {
            phoneNumber.setError("Please enter PHONE NUMBER");
            focusView = phoneNumber;
            cancel = true;
        }
        if (TextUtils.isEmpty(mobNumber)) {
            mobileNumber.setError("Please enter MOBILE NUMBER");
            focusView = mobileNumber;
            cancel = true;
        }
        if (TextUtils.isEmpty(emailAdd)) {
            emailAddress.setError("Please enter EMAIL ADDRESS");
            focusView = emailAddress;
            cancel = true;
        }

        return cancel;

    }
    public boolean saveRegistration(){
        boolean cancel = false;
        String cpyName = companyName.getText().toString();
        String cusId1 = customerId1.getText().toString();
        String cusId2 = customerId2.getText().toString();
        String selectedDay = (String)selectDay.getSelectedItem();
        String cusDesc1= customerDesc1.getText().toString();
        String cusDesc2= customerDesc2.getText().toString();
        String aea = (String)area.getSelectedItem();
        String selectCusType = (String) selectCustomerType.getSelectedItem();
        String streType = (String)storeType.getSelectedItem();

        String unitNum = unitNumber.getText().toString();
        String billAdd1 = billingAddress1.getText().toString();
        String billAdd2 = billingAddress2.getText().toString();
        String billAdd3 = billingAddress3.getText().toString();
        String stat = state.getText().toString();
        String cty = city.getText().toString();
        String contry = (String) country.getSelectedItem();
        String pincode = postalCode.getText().toString();

        String lstName = lastName.getText().toString();
        String fstName = firstName.getText().toString();
        String phneNumber = phoneNumber.getText().toString();
        String mobNumber = mobileNumber.getText().toString();
        String emailAdd = emailAddress.getText().toString();
        String nte = note.getText().toString();
        String smsAllow = String.valueOf(smsAllowed.isChecked());
        boolean activ = true;

        if (checkValid(cpyName, cusId1, cusId2,selectedDay,cusDesc1,cusDesc2,aea,selectCusType,streType unitNum,billAdd1,billAdd2,billAdd3,stat,cty,contry,pincode,lstName,fstName,phneNumber,mobNumber,emailAdd,)){
            return cancel;
        }
        */
/*Call<ProdcastDTO> prodcastDTOCall = new ProdcastDClient().getClient().saveCustomer(cpyName, cusId1, cusId2,cusDesc1,cusDesc2, unitNum,billAdd1,billAdd2,billAdd3,stat,cty,pincode,lstName,fstName,phneNumber,mobNumber,emailAdd);
        prodcastDTOCall.enqueue(new Callback<ProdcastDTO>() {
            @Override
            public void onResponse(Call<ProdcastDTO> call, Response<ProdcastDTO> response) {
                if(response.isSuccessful()){
                    ProdcastDTO prodcastDTO = response.body();

                    if(prodcastDTO.isError()) {
                        //TODO Show the ERror Message

                        Toast.makeText(CustomerActivity.this, "Customer Registration is not saved", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(CustomerActivity.this, "Customer Registration is saved Successfully", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProdcastDTO> call, Throwable t) {

            }
        });*//*

        return cancel;




    }
}
*/
