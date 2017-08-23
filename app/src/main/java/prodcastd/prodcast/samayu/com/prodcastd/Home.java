package prodcastd.prodcast.samayu.com.prodcastd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.samayu.prodcast.prodcastd.ui.CustomerCreateEditActivity;
import com.samayu.prodcast.prodcastd.ui.CustomerListActivity;


public class Home extends ProdcastBaseActivity  {
    private ImageView customers, orderEntry;
    Bundle homeBundle = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

               homeBundle = getIntent().getExtras();
                customers = (ImageView) findViewById(R.id.Customers);
               orderEntry =(ImageView)findViewById(R.id.OrderIcon);
                orderEntry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("employeeId",homeBundle.getString("employeeId"));
                        Intent intent = new Intent(Home.this,OrderEntry.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });


                customers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Bundle bundle = new Bundle();
                        bundle.putString("employeeId", homeBundle.getString("employeeId"));
                        Intent i = new Intent(Home.this, CustomerListActivity.class);
                        i.putExtras(bundle);
                        startActivity(i, bundle);
                    }
                });
    }


}
