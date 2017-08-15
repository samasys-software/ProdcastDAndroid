package prodcastd.prodcast.samayu.com.prodcastd;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.samayu.prodcast.prodcastd.SessionInfo;
import com.samayu.prodcast.prodcastd.dto.ProdcastDTO;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerPasswordActivity extends AppCompatActivity {
   private   EditText oldPassword,newPassword,confirmPassword;
   private Button submit,reset;
   private View focusView = null;
   private boolean cancel = false;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_password_activity);
        oldPassword =(EditText)findViewById(R.id.oldPassword);
        newPassword = (EditText)findViewById(R.id.newPassword);
        confirmPassword=(EditText)findViewById(R.id.confirmPassword);
        submit=(Button)findViewById(R.id.submit);
        reset=(Button)findViewById(R.id.reset);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(attemptChangePassword()){
                    Intent intent=new Intent(CustomerPasswordActivity.this,Home.class);
                    startActivity(intent);
                };

            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldPassword.setText("");
                newPassword.setText("");
                confirmPassword.setText("");
            }
        });

    }
    public boolean checkValue(String oldpass,String newpass, String confirmpass){
        if (TextUtils.isEmpty(oldpass)){
            oldPassword.setError("This Field is Empty");
            focusView = oldPassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(newpass)){
            newPassword.setError("This Field is Empty");
            focusView = newPassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(confirmpass)){
            confirmPassword.setError("This Field is Empty");
            focusView = confirmPassword;
            cancel = true;
        }
        if (oldpass.equals(newpass)){
            newPassword.setError("Your value is same as the old password");
            focusView=newPassword;
            cancel =true;
        }
        if (!confirmpass.equals(newpass)){
            confirmPassword.setError("This is not same as new password");
            focusView = confirmPassword;
            cancel = true;
        }

        else {
            cancel = false;
        }
        return cancel;
    }
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerPasswordActivity.this);
        builder.setTitle("Exit this page!!!")
                .setMessage("Do yo want to go to previous page?")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CustomerPasswordActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("Cancel",null);
        AlertDialog alert = builder.create();

        alert.show();

    }

    private boolean attemptChangePassword(){
        final String oldPass = oldPassword.getText().toString();
        final String newpass = newPassword.getText().toString();
        String confirmpass =confirmPassword.getText().toString();
        ;
        if (checkValue(oldPass,newpass,confirmpass)){
            return cancel;
        }
        String employeeIdString = String.valueOf( SessionInfo.instance().getEmployee().getEmployeeId());
        final Call<ProdcastDTO> prodcastDTOCall = new ProdcastDClient().getClient().changePassword(employeeIdString,oldPass, newpass);
        prodcastDTOCall.enqueue(new Callback<ProdcastDTO>() {
            @Override
            public void onResponse(Call<ProdcastDTO> call, Response<ProdcastDTO> response) {
                if( response.isSuccessful() ){
                    ProdcastDTO dto = response.body();
                    if(dto.isError()) {
                        //TODO Show the ERror Message

                        Toast.makeText(context, "Error in changing the password", Toast.LENGTH_LONG).show();
                    }
                    else{
                     //TODO Show Confirmation MEssage - and clear all the textboxes.
                        Toast.makeText(context, "Password Changed Successfully", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProdcastDTO> call, Throwable t) {

            }
        });
        return cancel;

    }
}
