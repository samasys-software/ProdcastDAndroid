package prodcastd.prodcast.samayu.com.prodcastd;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class home extends AppCompatActivity {
private ImageView customers ;
    Bundle homebundle = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homebundle = getIntent().getExtras();
        setContentView(R.layout.activity_home);
        customers = (ImageView) findViewById(R.id.Customers);


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

    }
}
