package prodcastd.prodcast.samayu.com.prodcastd;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.samayu.prodcast.prodcastd.SessionInfo;
import com.samayu.prodcast.prodcastd.dto.LoginDTO;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    EditText userName;

    Button signInButton,clearButton;
    TextView forgotPin,register;

    View focusView = null;



    EditText password = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         userName = (EditText)findViewById(R.id.logmn);
        password = (EditText)findViewById(R.id.loginPinNumber);
        signInButton = (Button)findViewById(R.id.logIn);
        clearButton = (Button)findViewById(R.id.logClear);
        forgotPin = (TextView)findViewById(R.id.forgotPin);
        register =(TextView)findViewById(R.id.register);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName.setText("");
                password.setText("");
            }
        });



        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = userName.getText().toString();
                String pass = password.getText().toString();

                if (checkValid(username,pass)){
                    return ;

                }

                signInButton.setEnabled(false);


                Call<LoginDTO> loginDTOCall = new ProdcastDClient().getClient().authenticate( userName.getText().toString() , password.getText().toString() );

                loginDTOCall.enqueue(new Callback<LoginDTO>() {
                    @Override
                    public void onResponse(retrofit2.Call<LoginDTO> call, Response<LoginDTO> response) {
                        if( response.isSuccessful() ){

                            LoginDTO loginDTO = response.body();
                            if( !loginDTO.isError()){
                                //TODO Now go to DashBoard.
                                Intent intent =new Intent(LoginActivity.this,home.class);
                                SessionInfo.instance().setEmployee( loginDTO.getEmployee());
                                Bundle bundle =  new Bundle();
                                bundle.putString("employeeId",String.valueOf(loginDTO.getEmployee().getEmployeeId()));
                                intent.putExtras(bundle);
                                startActivity(intent,bundle);
                                signInButton.setEnabled(true);
                                //new ProdcastDClient().getClient().getCustomers(""+employeeId)



                                //Pass in a Bundle to Dashboard loginDTO.getEmployee().getEmployeeId()
                            }
                            else {
                                //TODO Show error message TextBox that user is invalid
                                userName.setError("User is invalid");
                            }
                        }
                        else{
                            //Do Validation code here.
                            //TODO Error - Message-  Technical Problem. Pls try again.
                            signInButton.setError("Sorry for the technical problem. Please try again");
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginDTO> call, Throwable t) {
                        //DoValidation here.
                        //TODO Error - Message-  Technical Problem. Pls try again.
                        signInButton.setEnabled(true);
                    }
                });

            }
        });
    }
    public boolean checkValid(String username,String pass){
        boolean cancel = false;
        userName.setError(null);
        password.setError(null);
        if(TextUtils.isEmpty(pass)){
            password.setError("Please enter some value");
            focusView = password;
            password.setText("");
            cancel = true;

        }
        else if (!isPasswordValid(pass)){
            password.setError("Minimun is 5 char");
        }
        if (TextUtils.isEmpty(username)){
            userName.setError("Please enter the user name");
            focusView = userName;
            cancel = true;
        }
        return cancel;
    }
    public boolean isPasswordValid(String pass){
        return  password.length()>=5;
    }


}
