package prodcastd.prodcast.samayu.com.prodcastd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;




        public class Home extends AppCompatActivity {
            private ImageView customers,orderEntry;
            Bundle homeBundle = null;

            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                homeBundle = getIntent().getExtras();

                setContentView(R.layout.activity_home);
                customers = (ImageView) findViewById(R.id.Customers);
                orderEntry =(ImageView)findViewById(R.id.OrderIcon);
                orderEntry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("employeeId",homeBundle.getString("employeeId"));
                        Intent intent = new Intent(Home.this,OrderEntry.class);
                        intent.putExtras(bundle);
                        startActivity(intent,bundle);
                    }
                });


                customers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Bundle bundle = new Bundle();
                        bundle.putString("employeeId", homeBundle.getString("employeeId"));
                        Intent i = new Intent(Home.this, CustomersActivity.class);
                        i.putExtras(bundle);
                        startActivity(i, bundle);


                       // Bundle b = new Bundle();
                        //b.putString("employeeId", homeBundle.getString("employeeId"));
                        //Intent i = new Intent(home.this, customer_activity.class);
                        //i.putExtras(b);
                        //startActivity(i, b);

                    }
                });

            }
        }
