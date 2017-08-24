package prodcastd.prodcast.samayu.com.prodcastd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.samayu.prodcast.prodcastd.SessionInfo;
import com.samayu.prodcast.prodcastd.dto.Employee;
import com.samayu.prodcast.prodcastd.dto.LoginDTO;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;
import com.samayu.prodcast.prodcastd.util.EmailVerification;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    EditText userName;
    Button signInButton,clearButton;
    TextView forgotPin,register;
    View focusView = null;
    EditText password = null;
    public static final String FILE_NAME = "prodcastLogin.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Employee ep =loginRetrive();
        if (ep != null){
            SessionInfo.instance().setEmployee(ep);
            Intent intent =new Intent(LoginActivity.this,Home.class);
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
                userName.setText("");
                password.setText("");
                signInButton.setEnabled(true);
            }
        });




        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = userName.getText().toString();
                String pass = password.getText().toString();
                String email = userName.getText().toString();
               if (!EmailVerification.validateEmail(email)){
                   userName.setText("Enetr Valid email Id");
                   return;
               }

                if (checkValid(username,pass)){
                    return ;

                }
                Call<LoginDTO> loginDTOCall = new ProdcastDClient().getClient().authenticate( userName.getText().toString() , password.getText().toString() );
                loginDTOCall.enqueue(new Callback<LoginDTO>() {
                    @Override
                    public void onResponse(retrofit2.Call<LoginDTO> call, Response<LoginDTO> response) {
                        if( response.isSuccessful() ){
                            LoginDTO loginDTO = response.body();
                            if( !loginDTO.isError()){
                                //TODO Now go to DashBoard.
                                Intent intent =new Intent(LoginActivity.this,Home.class);
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
        if (!isPasswordValid(pass)){
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

}
