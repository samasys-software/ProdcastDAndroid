package prodcastd.prodcast.samayu.com.prodcastd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.samayu.prodcast.prodcastd.SessionInfo;
import com.samayu.prodcast.prodcastd.ui.CustomerCreateEditActivity;
import com.samayu.prodcast.prodcastd.ui.CustomerListActivity;


public class Home extends ProdcastBaseActivity  {
    private ImageView customers, orderEntry,changePassword;
    Bundle homeBundle = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SessionInfo.instance().getEmployee().getEmployeeId();

               homeBundle = getIntent().getExtras();
                customers = (ImageView) findViewById(R.id.Customers);
               orderEntry =(ImageView)findViewById(R.id.OrderIcon);
        changePassword=(ImageView)findViewById(R.id.ChangePassword);
                orderEntry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Home.this,OrderEntry.class);

                        startActivity(intent);
                    }
                });


                customers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent i = new Intent(Home.this, CustomerListActivity.class);

                        startActivity(i );
                    }
                });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,CustomerPasswordActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public String getProdcastTitle() {
        return "Dashboard";
    }


}
