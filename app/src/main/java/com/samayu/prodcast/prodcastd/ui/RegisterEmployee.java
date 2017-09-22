package com.samayu.prodcast.prodcastd.ui;

import android.content.Context;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.samayu.prodcast.prodcastd.SessionInfo;
import com.samayu.prodcast.prodcastd.dto.Country;
import com.samayu.prodcast.prodcastd.dto.RegistrationDTO;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;

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
        //progress = ProgressDialog.show(RegisterEmployee.this,"In Progress","One moment Please...",true);

        List<Country> countryList= SessionInfo.instance().getCountries();
        ArrayAdapter<Country> adapter = new ArrayAdapter<Country>(RegisterEmployee.this,R.layout.drop_down_list, countryList);
        country.setAdapter(adapter);
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
            //progress.show();
            Call<RegistrationDTO> registrationDTO=new ProdcastDClient().getClient().newRegistration(regFirstName,regLastName,selectedCountryId,regEmail,regCellPhone);
            registrationDTO.enqueue(new Callback<RegistrationDTO>() {
                @Override
                public void onResponse(Call<RegistrationDTO> call, Response<RegistrationDTO> response) {

                        RegistrationDTO dto=response.body();
                        if(dto.isError()){
                            Log.e("error",dto.getErrorMessage());
                        }
                        else{
                            //SessionInfo.instance().setEmployeeRegistration(dto.getResult());
                            //progress.dismiss();
                            //attemptReset();
                            finish();
                           // Toast.makeText(context,"Your Request Has Been Registered Successfully And Prodcast Representative will Contact You Shortly",Toast.LENGTH_LONG);



                        }
                    }


                @Override
                public void onFailure(Call<RegistrationDTO> call, Throwable t) {
                    t.printStackTrace();
                    //progress.dismiss();

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
