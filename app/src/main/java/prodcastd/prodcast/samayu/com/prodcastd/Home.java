package prodcastd.prodcast.samayu.com.prodcastd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Home extends AppCompatActivity {
private ImageView customers ;
    private Bundle homeBundle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBundle = getIntent().getExtras();
        setContentView(R.layout.activity_home);
        customers = (ImageView) findViewById(R.id.Customers);


        customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("employeeId",homeBundle.getString("employeeId"));
                Intent i = new Intent(Home.this,customer_activity.class);
                i.putExtras(bundle);
                startActivity(i,bundle);


            }
        });

    }
}
