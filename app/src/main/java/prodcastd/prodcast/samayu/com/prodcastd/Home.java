package prodcastd.prodcast.samayu.com.prodcastd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Home extends AppCompatActivity {
private ImageView customers ;
    Bundle homebundle = null;
    private ImageView conPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homebundle = getIntent().getExtras();
        setContentView(R.layout.activity_home);
        customers = (ImageView) findViewById(R.id.Customers);
        conPassword =(ImageView)findViewById(R.id.ChangePassword);

        customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b= new Bundle();
                b.putString("employeeId",homebundle.getString("employeeId"));
                Intent i = new Intent(Home.this,CustomersActivity.class);
                i.putExtras(b);
                startActivity(i,b);
            }
        });
        conPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,CustomerPasswordActivity.class);
                startActivity(intent);
            }
        });


    }
}
