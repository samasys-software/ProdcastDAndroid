package prodcastd.prodcast.samayu.com.prodcastd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.samayu.prodcast.prodcastd.dto.LoginDTO;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LOGIN extends AppCompatActivity {

    EditText userId =null;
    EditText password = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
