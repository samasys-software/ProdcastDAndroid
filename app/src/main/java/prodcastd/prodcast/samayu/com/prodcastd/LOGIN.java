package prodcastd.prodcast.samayu.com.prodcastd;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.widget.Spinner;
import android.widget.TextView;

public class LOGIN extends AppCompatActivity {

    EditText userName,password;

    Button signInButton,clearButton;
    TextView forgotPin,register;
    boolean cancel = false;
    View focusView = null;
    Context context;
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

        userId = (EditText)findViewById(R.id.logmn);
        password = (EditText)findViewById(R.id.loginPinNumber);

        ((Button)findViewById(R.id.logIn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<LoginDTO> loginDTOCall = new ProdcastDClient().getClient().authenticate( userId.getText().toString() , password.getText().toString() );

                loginDTOCall.enqueue(new Callback<LoginDTO>() {
                    @Override
                    public void onResponse(Call<LoginDTO> call, Response<LoginDTO> response) {
                        if( response.isSuccessful() ){

                            LoginDTO loginDTO = response.body();
                            if( !loginDTO.isError()){
                                //Now go to DashBoard.
                            }
                        }
                        else{
                            //Do Validation code here.
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginDTO> call, Throwable t) {
                        //DoValidation here.
                    System.out.println("Failed");
                    }
                });
            }
        });
    }
}
