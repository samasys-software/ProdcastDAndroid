package com.samayu.prodcast.prodcastd.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.samayu.prodcast.prodcastd.SessionInfo;
import com.samayu.prodcast.prodcastd.dto.Country;
import com.samayu.prodcast.prodcastd.dto.CountryDTO;
import com.samayu.prodcast.prodcastd.dto.Employee;
import com.samayu.prodcast.prodcastd.dto.LoginDTO;
import com.samayu.prodcast.prodcastd.dto.ProdcastDTO;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;
import com.samayu.prodcast.prodcastd.util.EmailVerification;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import prodcastd.prodcast.samayu.com.prodcastd.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    EditText userName;
    Button signInButton,clearButton;
    TextView forgotPin,register;
    View focusView = null;
    EditText password = null;
    Context context;
    public static final String FILE_NAME = "prodcastLogin.txt";
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context=this;
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
                    SessionInfo.instance().setCountries(countryList);


                }
            }

            @Override
            public void onFailure(Call<CountryDTO> call, Throwable t) {

                t.printStackTrace();
                //progress.dismiss();
            }
        });
        Employee ep =loginRetrive();
        if (ep != null){
            SessionInfo.instance().setEmployee(ep);
            Intent intent =new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
        }
        userName = (EditText)findViewById(R.id.logmn);
        password = (EditText)findViewById(R.id.loginPinNumber);
        signInButton = (Button)findViewById(R.id.logIn);
        clearButton = (Button)findViewById(R.id.logClear);
        forgotPin = (TextView)findViewById(R.id.forgotPin);
        register =(TextView)findViewById(R.id.register);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });

        forgotPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRetrive();
            }
        });




        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               attemptLogin();

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerEmployee();

            }
        });
    }

    public void onResume(){

        super.onResume();
        Toast.makeText(LoginActivity.this,"Your Request Has Been Registered Successfully And Prodcast Representative will Contact You Shortly",Toast.LENGTH_LONG);

    }


    public boolean checkValid(String username,String pass){
        boolean cancel = false;
        userName.setError(null);
        password.setError(null);

        if (TextUtils.isEmpty(username)){
            userName.setError("Please enter the user name");
            focusView = userName;
            cancel = true;
        }
        if(pass!=null){

            if(TextUtils.isEmpty(pass)){
                password.setError("Please enter some value");
                focusView = password;
                cancel = true;
                return cancel;

            }
            if (!isPasswordValid(pass)){
                password.setError("Minimun is 5 char");
                focusView=password;
                cancel=true;
                return cancel;
            }

        }
        if (!EmailVerification.validateEmail(username)){
            userName.setText("Enetr Valid email Id");
            focusView = userName;
            cancel=true;
            return cancel;
        }



        return cancel;
    }
    public boolean isPasswordValid(String pass){
        return  password.length()>=5;
    }
    public void loginToFile(Employee employee) {
         File file = new File(getFilesDir(), FILE_NAME);


         FileOutputStream outputStream;

         try {
             outputStream = openFileOutput(FILE_NAME, LoginActivity.this.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(outputStream);
             oos.writeObject(employee);
             outputStream.close();
         } catch (Exception e) {
             e.printStackTrace();
         }

     }
    public Employee loginRetrive(){
     try {
        ObjectInputStream ois =new ObjectInputStream(openFileInput(FILE_NAME));
         Employee r =(Employee)ois.readObject();
         return r;
     }
     catch (Exception e){
         e.printStackTrace();
         return null;
     }

 }

    public void attemptRetrive(){
        String email=userName.getText().toString();
        if(checkValid(email,null)){
            return;
        }
        else{

            progress = ProgressDialog.show(LoginActivity.this,"In Progress","One moment Please......",true);

            Call<ProdcastDTO> retrivePassword = new ProdcastDClient().getClient().retrievePassword(email );
            retrivePassword.enqueue(new Callback<ProdcastDTO>() {
                @Override
                public void onResponse(retrofit2.Call<ProdcastDTO> call, Response<ProdcastDTO> response) {
                    if( response.isSuccessful() ){
                        ProdcastDTO dto = response.body();
                        if(dto.isError()) {
                            progress.dismiss();
                            Toast.makeText(LoginActivity.this,"User is Invalid",Toast.LENGTH_LONG).show();
                            signInButton.setEnabled(true);

                        }
                        else{

                            //TODO Now go to DashBoard.
                            progress.dismiss();
                            Toast.makeText(LoginActivity.this,"Send Successfully",Toast.LENGTH_LONG);
                            signInButton.setVisibility(View.GONE);

                            //new ProdcastDClient().getClient().getCustomers(""+employeeId)
                            //Pass in a Bundle to Dashboard loginDTO.getEmployee().getEmployeeId()
                        }

                    }
                    else{
                        //Do Validation code here.
                        //TODO Error - Message-  Technical Problem. Pls try again.
                        progress.dismiss();
                        Toast.makeText(LoginActivity.this,"Sorry for the technical problem. Please try again",Toast.LENGTH_LONG).show();

                    }

                }

                @Override
                public void onFailure(Call<ProdcastDTO> call, Throwable t) {
                    //DoValidation here.
                    //TODO Error - Message-  Technical Problem. Pls try again.
                    progress.dismiss();
                    t.printStackTrace();

                }
            });

        }

    }

    public  void  clear(){
        userName.setText("");
        password.setText("");
        signInButton.setEnabled(true);
    }

    public void attemptLogin(){
        String username = userName.getText().toString();
        String pass = password.getText().toString();

        if (checkValid(username,pass)){
            return ;

        }
        progress = ProgressDialog.show(LoginActivity.this,"In Progress","One moment Please......",true);
        Call<LoginDTO> loginDTOCall = new ProdcastDClient().getClient().authenticate( username , pass );
        loginDTOCall.enqueue(new Callback<LoginDTO>() {
            @Override
            public void onResponse(retrofit2.Call<LoginDTO> call, Response<LoginDTO> response) {
                if( response.isSuccessful() ){
                    LoginDTO loginDTO = response.body();
                    if( !loginDTO.isError()){
                        //TODO Now go to DashBoard.
                        progress.dismiss();
                        Intent intent =new Intent(LoginActivity.this,HomeActivity.class);
                        SessionInfo.instance().setEmployee( loginDTO.getEmployee());
                        loginToFile(loginDTO.getEmployee());
                        Bundle bundle =  new Bundle();

                        bundle.putString("employeeId",String.valueOf(loginDTO.getEmployee().getEmployeeId()));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        signInButton.setEnabled(false);

                        //new ProdcastDClient().getClient().getCustomers(""+employeeId)
                        //Pass in a Bundle to Dashboard loginDTO.getEmployee().getEmployeeId()
                    }
                    else {
                        //TODO Show error message TextBox that user is invalid
                        Toast.makeText(LoginActivity.this,"User is Invalid",Toast.LENGTH_LONG).show();
                        signInButton.setEnabled(true);
                    }
                }
                else{
                    //Do Validation code here.
                    //TODO Error - Message-  Technical Problem. Pls try again.
                    Toast.makeText(LoginActivity.this,"Sorry for the technical problem. Please try again",Toast.LENGTH_LONG).show();
                    signInButton.setEnabled(true);
                }

            }

            @Override
            public void onFailure(Call<LoginDTO> call, Throwable t) {
                //DoValidation here.
                //TODO Error - Message-  Technical Problem. Pls try again.
                t.printStackTrace();
                signInButton.setEnabled(true);
            }
        });
    }

    public void registerEmployee(){
        Intent intent=new Intent(LoginActivity.this,RegisterEmployee.class);
        startActivity(intent);
    }

}
