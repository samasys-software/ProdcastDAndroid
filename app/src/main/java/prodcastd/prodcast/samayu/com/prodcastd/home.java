package prodcastd.prodcast.samayu.com.prodcastd;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.samayu.prodcast.prodcastd.SessionInfo;

public class home extends AppCompatActivity {
private ImageView customers ;
    Bundle homebundle = null;
    private ImageView conPassword;
    private ImageView orderList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homebundle = getIntent().getExtras();
        setContentView(R.layout.activity_home);
        customers = (ImageView) findViewById(R.id.Customers);
        conPassword =(ImageView)findViewById(R.id.ChangePassword);
        orderList = (ImageView)findViewById(R.id.OrderIcon);

        customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b= new Bundle();
                b.putString("employeeId",homebundle.getString("employeeId"));
                Intent i = new Intent(home.this,customer_activity.class);
                i.putExtras(b);
                startActivity(i,b);
            }
        });
        conPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this,CustomerPasswordActivity.class);
                startActivity(intent);
            }
        });
        orderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b= new Bundle();
                b.putString("employeeId",homebundle.getString("employeeId"));
                Intent intent = new Intent(home.this,OrderEntry.class);
                intent.putExtras(b);
                startActivity(intent,b);
            }
        });

    }
}
