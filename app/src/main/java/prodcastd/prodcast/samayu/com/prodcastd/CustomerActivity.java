package prodcastd.prodcast.samayu.com.prodcastd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.samayu.prodcast.prodcastd.SessionInfo;
import com.samayu.prodcast.prodcastd.dto.AdminDTO;
import com.samayu.prodcast.prodcastd.dto.Area;
import com.samayu.prodcast.prodcastd.dto.AreaDTO;
import com.samayu.prodcast.prodcastd.dto.Country;
import com.samayu.prodcast.prodcastd.dto.CountryDTO;
import com.samayu.prodcast.prodcastd.dto.Customer;
import com.samayu.prodcast.prodcastd.dto.ProdcastDTO;
import com.samayu.prodcast.prodcastd.dto.StoreType;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;

import java.util.List;

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
    View focusView = null;


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

        ArrayAdapter adapterday = ArrayAdapter.createFromResource(this, R.array.select_day,R.layout.support_simple_spinner_dropdown_item);
        adapterday.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selectDay.setAdapter(adapterday);
        selectDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(CustomerActivity.this, adapterView.getItemAtPosition(i)+" selected",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        customerDesc1 = (EditText) findViewById(R.id.customerDesc1);
        customerDesc2 = (EditText) findViewById(R.id.customerDesc2);
        area = (Spinner) findViewById(R.id.area);

        //SessionInfo.instance().getEmployee().getEmployeeId()
        Call<AdminDTO<List<Area>>> areaDTOCall= new ProdcastDClient().getClient().getAreasForDistributor(621);
        areaDTOCall.enqueue(new Callback<AdminDTO<List<Area>>>() {
            @Override
            public void onResponse(Call<AdminDTO<List<Area>>> call, Response<AdminDTO<List<Area>>> response) {
                if(response.isSuccessful()){
                    AdminDTO<List<Area>> areaDTO = response.body();
                    List<Area> areaList= areaDTO.getResult();
                    String newList[] = new String[areaList.size()];
                    for(int i= 0; i<areaList.size(); i++){
                        Area area  = areaList.get(i);
                        newList[i] = area.getDescription();
                    }
                    ArrayAdapter<Area> adapter = new ArrayAdapter<Area>(CustomerActivity.this, android.R.layout.simple_list_item_1, areaList);
                    area.setAdapter(adapter);
                    }

                }

            @Override
            public void onFailure(Call<AdminDTO<List<Area>>> call, Throwable t) {
                t.printStackTrace();

            }

        });


        selectCustomerType = (Spinner) findViewById(R.id.selectCustomerType);

        ArrayAdapter adapterCustomerType = ArrayAdapter.createFromResource(this, R.array.select_customer_type,R.layout.support_simple_spinner_dropdown_item);
        adapterday.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selectCustomerType.setAdapter(adapterCustomerType);
        selectCustomerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(CustomerActivity.this, adapterView.getItemAtPosition(i)+" selected",Toast.LENGTH_LONG).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        storeType = (Spinner) findViewById(R.id.storetype);


       Call<AdminDTO<List<StoreType>>> adminDTOCall = new ProdcastDClient().getClient().getStoreTypes();
        adminDTOCall.enqueue(new Callback<AdminDTO<List<StoreType>>>() {
            @Override
            public void onResponse(Call<AdminDTO<List<StoreType>>> call, Response<AdminDTO<List<StoreType>>> response) {
                if(response.isSuccessful()){
                    AdminDTO<List<StoreType>> adminDTO= response.body();
                    List<StoreType> storeTypeList = adminDTO.getResult();
                    String newList[] = new String[storeTypeList.size()];
                    for(int i= 0; i<storeTypeList.size(); i++){
                        StoreType storeType  = storeTypeList.get(i);
                        newList[i] = storeType.getStoreTypeName();
                    }
                    ArrayAdapter<StoreType> adapter = new ArrayAdapter<StoreType>(CustomerActivity.this, android.R.layout.simple_list_item_1, storeTypeList);
                    storeType.setAdapter(adapter);
                }
                }



            @Override
            public void onFailure(Call<AdminDTO<List<StoreType>>> call, Throwable t) {
                t.printStackTrace();

            }
        });

        unitNumber = (EditText) findViewById(R.id.unitNumber);
        billingAddress1 = (EditText) findViewById(R.id.billingAddress1);
        billingAddress2 = (EditText) findViewById(R.id.billingAddress2);
        billingAddress3 = (EditText) findViewById(R.id.billingAddress3);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        country = (Spinner) findViewById(R.id.country);
        postalCode = (EditText) findViewById(R.id.postalCode);

         Call<CountryDTO> countryDTOCall = new ProdcastDClient().getClient().getCountries();
        countryDTOCall.enqueue(new Callback<CountryDTO>() {
            @Override
            public void onResponse(Call<CountryDTO> call, Response<CountryDTO> response) {
                if (response.isSuccessful()){
                    CountryDTO countryDTO = response.body();
                    List<Country> countryList= countryDTO.getResult();
                    Country defaultCountry = new Country();
                    defaultCountry.setCountryId("");
                    defaultCountry.setCountryName("Select Country");
                    countryList.add(0, defaultCountry  );
                    ArrayAdapter<Country> adapter = new ArrayAdapter<Country>(CustomerActivity.this, android.R.layout.simple_list_item_1, countryList);
                    country.setAdapter(adapter);

                }

            }

            @Override
            public void onFailure(Call<CountryDTO> call, Throwable t) {
                t.printStackTrace();

            }
        });

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
                saveRegistration();







            }
        });
    }//cpyName, cusId1, cusId2,selectedDay,cusDesc1,cusDesc2,aea,selectCusType,streType,
    // unitNum,billAdd1,billAdd2,billAdd3,stat,cty,contry,pincode,
    // lstName,fstName,phneNumber,mobNumber,emailAdd,nte,smsAllow,activ
    public boolean checkValid( String cpyName, String cusId1, String cusId2,String selectedDay, String cusDesc1, String cusDesc2, int aea, String selectCusType, int streType,
                               String unitNum, String billAdd1, String billAdd2, String billAdd3, String stat, String cty,int contry, String pincode,
                              String lstName, String fstName, String phneNumber, String mobNumber, String emailAdd,String nte,
                              String smsAllow, Boolean activ) {
        boolean cancel = false;
        companyName.setError("");
        customerId1.setError("");
        customerId2.setError("");
        customerDesc1.setError("");
        customerDesc2.setError("");




        unitNumber.setError("");
        billingAddress1.setError("");
        billingAddress2.setError("");
        billingAddress3.setError("");
        city.setError("");
        state.setError("");

        postalCode.setError("");

        firstName.setError("");
        lastName.setError("");
        phoneNumber.setError("");
        mobileNumber.setError("");
        emailAddress.setError("");
        note.setError("");

        //private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";



        if (TextUtils.isEmpty(cpyName)) {
            companyName.setError("Please enter Company Name");
            focusView = companyName;
            cancel = true;
        }
        if (TextUtils.isEmpty(cusId1)) {
            customerId1.setError("Please enter Customer Id1");
            focusView = customerId1;
            cancel = true;
        }
        if (TextUtils.isEmpty(cusId2)) {
            customerId1.setError("Please enter Customer Id2");
            focusView = customerId2;
            cancel = true;
        }
        if(contry <= 0){
            focusView = country;
            cancel = true;
        }
        if (TextUtils.isEmpty(cusDesc1)) {
            customerDesc1.setError("Please Enter Customer Desc1");
            focusView = customerDesc1;
            cancel = true;
        }
        if (TextUtils.isEmpty(cusDesc2)) {
            customerDesc2.setError("Please Enter Customer Desc2");
            focusView = customerDesc2;
            cancel = true;
        }
        if (TextUtils.isEmpty(unitNum)) {
            unitNumber.setError("Please Enter Unit Number");
            focusView = unitNumber;
            cancel = true;
        }
        if (TextUtils.isEmpty(billAdd1)) {
            billingAddress1.setError("Please Enter Billing Address1");
            focusView = billingAddress1;
            cancel = true;
        }
        if (TextUtils.isEmpty(billAdd2)) {
            billingAddress2.setError("Please Enter Billing Address1");
            focusView = billingAddress2;
            cancel = true;
        }
        if (TextUtils.isEmpty(billAdd3)) {
            billingAddress3.setError("Please Enter Billing Address1");
            focusView = billingAddress3;
            cancel = true;
        }
        if (TextUtils.isEmpty(cty)) {
            city.setError("Please Enter City");
            focusView = city;
            cancel = true;
        }
        if (TextUtils.isEmpty(stat)) {
            state.setError("Please Enter State");
            focusView = state;
            cancel = true;
        }
        if (TextUtils.isEmpty(pincode)) {
            postalCode.setError("Please Enter PostalCode");
            focusView = state;
            cancel = true;
        }
        if (TextUtils.isEmpty(lstName)) {
            lastName.setError("Please Enter LastName");
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
    public void saveRegistration(){
        boolean cancel = false;
        String cpyName = companyName.getText().toString();
        String cusId1 = customerId1.getText().toString();
        String cusId2 = customerId2.getText().toString();
        String selectedDay = (String)selectDay.getSelectedItem();
        String cusDesc1= customerDesc1.getText().toString();
        String cusDesc2= customerDesc2.getText().toString();
        int aea = area.getSelectedItemPosition();
        String selectCusType = (String) selectCustomerType.getSelectedItem();
        int streType = storeType.getSelectedItemPosition();

        String unitNum = unitNumber.getText().toString();
        String billAdd1 = billingAddress1.getText().toString();
        String billAdd2 = billingAddress2.getText().toString();
        String billAdd3 = billingAddress3.getText().toString();
        String stat = state.getText().toString();
        String cty = city.getText().toString();
        int contry = country.getSelectedItemPosition();
        String pincode = postalCode.getText().toString();

        String lstName = lastName.getText().toString();
        String fstName = firstName.getText().toString();
        String phneNumber = phoneNumber.getText().toString();
        String mobNumber = mobileNumber.getText().toString();
        String emailAdd = emailAddress.getText().toString();
        String nte = note.getText().toString();
        String smsAllow = String.valueOf(smsAllowed.isChecked());
        boolean activ = true;

        if (checkValid(cpyName, cusId1, cusId2,selectedDay,cusDesc1,cusDesc2,aea,selectCusType,streType, unitNum,billAdd1,billAdd2,billAdd3,stat,cty,contry,pincode,lstName,fstName,phneNumber,mobNumber,emailAdd,nte,smsAllow,activ)){
            return ;
      }
        Call<ProdcastDTO> prodcastDTOCall = new ProdcastDClient().getClient().saveCustomer(cpyName, cusId1, cusId2,cusDesc1,cusDesc2,
                unitNum,billAdd1,billAdd2,billAdd3,stat,cty,pincode,lstName,fstName,phneNumber,mobNumber,emailAdd,nte,smsAllow,activ);
        
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
        });
        return ;

    }

}
