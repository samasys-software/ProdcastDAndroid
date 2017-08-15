package prodcastd.prodcast.samayu.com.prodcastd;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.samayu.prodcast.prodcastd.dto.Customer;
import com.samayu.prodcast.prodcastd.dto.CustomerListDTO;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomersActivity extends AppCompatActivity {
    private String employeeId;
    private ListView cuslist;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_activity);
        employeeId = getIntent().getExtras().getString("employeeId");
        cuslist =(ListView)findViewById(R.id.customerListView);
        EditText empTextBox = (EditText)findViewById(R.id.employeeId);
        empTextBox.setText(employeeId);
        Call<CustomerListDTO> customerListDTOCall = new ProdcastDClient().getClient().getCustomers(employeeId);
        customerListDTOCall.enqueue(new Callback<CustomerListDTO>() {
            @Override
            public void onResponse(Call<CustomerListDTO> call, Response<CustomerListDTO> response) {
                if (response.isSuccessful()){
                    CustomerListDTO customerListDTO =response.body();
                    List<Customer> customerList= customerListDTO.getCustomerList();
                    for(int i =0;i< customerList.size();i++){
                    customerList.get(i);
                    }

                }
            }

            @Override
            public void onFailure(Call<CustomerListDTO> call, Throwable t) {

            }
        });
    }
}
