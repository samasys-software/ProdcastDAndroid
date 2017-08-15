package prodcastd.prodcast.samayu.com.prodcastd;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.samayu.prodcast.prodcastd.dto.Customer;
import com.samayu.prodcast.prodcastd.dto.CustomerListDTO;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;






public class customer_activity extends AppCompatActivity {
    private String employeeId;
    private ListView cuslist;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_activity);


        employeeId = getIntent().getExtras().getString("employeeId");


        listView = (ListView) findViewById(R.id.customerList);
        ((Button)findViewById(R.id.newCustomerButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(customer_activity.this, CustomerActivity.class );
                startActivity(intent);
            }
        });
        Call<CustomerListDTO> customerListDTOCall = new ProdcastDClient().getClient().getCustomers(employeeId);
        customerListDTOCall.enqueue(new Callback<CustomerListDTO>() {
            @Override
            public void onResponse(Call<CustomerListDTO> call, Response<CustomerListDTO> response) {
                if (response.isSuccessful()) {
                    CustomerListDTO customerListDTO = response.body();
                    List<Customer> customerList = customerListDTO.getCustomerList();
                    String newList[] = new String[customerList.size()];
                    for (int i = 0; i < customerList.size(); i++) {
                        Customer customer = customerList.get(i);
                        newList[i] = customer.getCustomerName();

                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(customer_activity.this,
                            android.R.layout.simple_list_item_1, android.R.id.text1, newList);
                    listView.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<CustomerListDTO> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }
}

            /*employeeId = getIntent().getExtras().getString("employeeId");
            cuslist = (ListView) findViewById(R.id.customerListView);
            EditText empTextBox = (EditText) findViewById(R.id.employeeId);
            empTextBox.setText(employeeId);
            Call<CustomerListDTO> customerListDTOCall1 = new ProdcastDClient().getClient().getCustomers(employeeId);
            customerListDTOCall.enqueue(new Callback<CustomerListDTO>() {
                @Override
                public void onResponse(Call<CustomerListDTO> call, Response<CustomerListDTO> response) {
                    if (response.isSuccessful()) {
                        CustomerListDTO customerListDTO = response.body();
                        List<Customer> customerList = customerListDTO.getCustomerList();
                        for (int i = 0; i < customerList.size(); i++) {
                            customerList.get(i);
                        }

                    }
                }

                @Override
                public void onFailure(Call<CustomerListDTO> call, Throwable t) {
                    Toast.makeText(customer_activity.this,"Techical Error Please Try Again",Toast.LENGTH_LONG).show();

                }
            });
        }*/


