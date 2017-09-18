package com.samayu.prodcast.prodcastd.ui;





import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.ListView;

import com.samayu.prodcast.prodcastd.SessionInfo;
import com.samayu.prodcast.prodcastd.dto.Customer;
import com.samayu.prodcast.prodcastd.dto.CustomerListDTO;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;

import java.util.List;

import prodcastd.prodcast.samayu.com.prodcastd.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;






public class CustomersActivity extends ProdcastBaseActivity {
    private long employeeId;
    private ListView cuslist;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_activity);
        
        employeeId = SessionInfo.instance().getEmployee().getEmployeeId();

        listView = (ListView) findViewById(R.id.customerListView);

        findViewById(R.id.newCustomerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomersActivity.this , CustomerActivity.class);
                startActivity(intent );
            }
        });
        Call<CustomerListDTO> customerListDTOCall = new ProdcastDClient().getClient().getCustomers(SessionInfo.instance().getEmployee().getEmployeeId());
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(CustomersActivity.this, android.R.layout.simple_list_item_1, newList);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CustomerListDTO> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }

    @Override
    public String getProdcastTitle() {
        return "My Customers";
    }
}



