package prodcastd.prodcast.samayu.com.prodcastd;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.samayu.prodcast.prodcastd.dto.Customer;

import prodcastd.prodcast.samayu.com.prodcastd.ui.NavigationDrawerActivity;


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
                        Intent i = new Intent(Home.this, CustomersActivity.class);
                        i.putExtras(bundle);
                        startActivity(i, bundle);
                    }
                });
    }


}
