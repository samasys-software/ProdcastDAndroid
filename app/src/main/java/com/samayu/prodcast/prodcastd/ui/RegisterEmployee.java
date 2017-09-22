package com.samayu.prodcast.prodcastd.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.samayu.prodcast.prodcastd.SessionInfo;
import com.samayu.prodcast.prodcastd.dto.Country;
import com.samayu.prodcast.prodcastd.dto.CountryDTO;
import com.samayu.prodcast.prodcastd.dto.Registration;
import com.samayu.prodcast.prodcastd.dto.RegistrationDTO;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;
import com.samayu.prodcast.prodcastd.service.ProdcastDistributorService;

import java.util.List;

import prodcastd.prodcast.samayu.com.prodcastd.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterEmployee extends ProdcastBaseActivity {
      EditText firstName,lastName,emailId,cellPhone;
    boolean cancel=false;
    Spinner country;
    View focusView=null;
    Button submit,reset;
    Context context;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_employee);
        context=this;
        firstName=(EditText)findViewById(R.id.regFirstName);
        lastName=(EditText)findViewById(R.id.regLastName);
        emailId=(EditText)findViewById(R.id.regEmail);
        country=(Spinner) findViewById(R.id.regCountry);
        cellPhone=(EditText)findViewById(R.id.regCellPhone);
        submit=(Button)findViewById(R.id.regSubmit);
        reset=(Button)findViewById(R.id.regReset);
        progress = ProgressDialog.show(RegisterEmployee.this,"In Progress","One moment Please...",true);
        Call<CountryDTO> countryDTOCall = new ProdcastDClient().getClient().getCountries();
        countryDTOCall.enqueue(new Callback<CountryDTO>() {
            @Override
            public void onResponse(Call<CountryDTO> call, Response<CountryDTO> response) {
                if (response.isSuccessful()){
                    CountryDTO countryDTO = response.body();
                    List<Country> countryList= countryDTO.getResult();
                    SessionInfo.instance().setCountries(countryList);
                    Country defaultCountry = new Country();
                    defaultCountry.setCountryId("");
                    defaultCountry.setCountryName("Select Country");
                    countryList.add(0, defaultCountry  );

                    ArrayAdapter<Country> adapter = new ArrayAdapter<Country>(RegisterEmployee.this,R.layout.drop_down_list, countryList);
                    country.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CountryDTO> call, Throwable t) {

              t.printStackTrace();
                progress.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptEmployeeRegister();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptReset();
            }
        });



    }
    public void attemptEmployeeRegister(){
        int ctry=country.getSelectedItemPosition();
        Country selectedItem=(Country)country.getSelectedItem();
        String selectedCountryId=selectedItem.getCountryId();
     final String regFirstName=firstName.getText().toString();
        String regLastName=lastName.getText().toString();
        String regEmail=emailId.getText().toString();
        String regCellPhone=cellPhone.getText().toString();
        boolean cancelled=checkValue(ctry,regFirstName,regLastName,regEmail,regCellPhone);
        if(cancelled){
            focusView.requestFocus();
            return;
        }
        else{
            progress.show();
            Call<RegistrationDTO<List<Registration>>> registrationDTO=new ProdcastDClient().getClient().registration(regFirstName,regLastName,selectedCountryId,regEmail,regCellPhone);
            registrationDTO.enqueue(new Callback<RegistrationDTO<List<Registration>>>() {
                @Override
                public void onResponse(Call<RegistrationDTO<List<Registration>>> call, Response<RegistrationDTO<List<Registration>>> response) {
                    if(response.isSuccessful()){
                        RegistrationDTO<List<Registration>> dto=response.body();
                        if(dto.isError()){
                            progress.dismiss();
                            firstName.setError(dto.getErrorMessage());
                            focusView=firstName;
                            focusView.requestFocus();
                            return;
                        }
                        else{
                            SessionInfo.instance().setEmployeeRegistration(dto.getResult());
                            progress.dismiss();
                            attemptReset();

                        }
                    }
                }

                @Override
                public void onFailure(Call<RegistrationDTO<List<Registration>>> call, Throwable t) {
                    t.printStackTrace();
                    progress.dismiss();

                }
            });

        }
    }
    public String getProdcastTitle(){
        return "New Registration";
    }
    public boolean checkValue(int ctry,String regFirstName,String regLastName,String regEmail,String regCellPhone){
        cancel=false;
        firstName.setError(null);
        lastName.setError(null);
        emailId.setError(null);
        cellPhone.setError(null);
        if(TextUtils.isEmpty(regFirstName)){
            firstName.setError("This field is required");
            focusView=firstName;
            cancel=true;
            return cancel;
        }
        if(TextUtils.isEmpty(regLastName)){
            lastName.setError("This field is required");
            focusView=lastName;
            cancel=true;
            return cancel;

        }
        if(TextUtils.isEmpty(regEmail)){
            emailId.setError("This field is required");
            focusView=emailId;
            cancel=true;
            return cancel;
        }
        if(TextUtils.isEmpty(regCellPhone)){
            cellPhone.setError("This field is required");
            focusView=cellPhone;
            cancel=true;
            return cancel;

        }
        if(ctry<=0){
            TextView errorText=(TextView)country.getSelectedView();
            errorText.setError("This field is required");
            Toast.makeText(this, "This field is reqiured", Toast.LENGTH_SHORT).show();
            focusView=errorText;
            cancel=true;
            return cancel;

        }
        return cancel;
    }
    public void attemptReset(){
        country.setSelection(0);
        firstName.setText("");
        lastName.setText("");
        emailId.setText("");
        cellPhone.setText("");
    }

}
