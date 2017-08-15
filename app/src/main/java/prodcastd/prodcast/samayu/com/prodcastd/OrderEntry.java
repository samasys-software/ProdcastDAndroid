package prodcastd.prodcast.samayu.com.prodcastd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.samayu.prodcast.prodcastd.SessionInfo;
import com.samayu.prodcast.prodcastd.dto.Bill;
import com.samayu.prodcast.prodcastd.dto.Customer;
import com.samayu.prodcast.prodcastd.dto.CustomerListDTO;
import com.samayu.prodcast.prodcastd.dto.ProdcastDTO;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;

import java.util.LinkedList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderEntry extends AppCompatActivity {
    private String employeeId;
    private ListView billsView,billDate,billTotal;
    private AutoCompleteTextView customerNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        employeeId=getIntent().getExtras().getString("employeeId");
        setContentView(R.layout.activity_order_entry);
        billsView = (ListView)findViewById(R.id.billsView);
        

        customerNames = (AutoCompleteTextView)findViewById(R.id.acTextViev);
        customerNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Customer customer = null;
                List<Customer> customerList =SessionInfo.instance().getCustomerList();
                String customerName = (String)adapterView.getItemAtPosition(i);
                for (int a =0;a<customerList.size();a++){
                    customer = customerList.get(a);
                    if (customer.getCustomerName().equalsIgnoreCase(customerName)){
                        break;
                    }
                }

                List<String> bills  = new LinkedList<String>();
                List<Bill> allOutStandingBills = SessionInfo.instance().getOutStandingBills();
                for (int j =0;j<allOutStandingBills.size();j++){
                    Bill bill = allOutStandingBills.get(j);
                    if (bill.getCustomerId() == customer.getId()){
                        bills.add(""+bill.getBillNumber()+" "+bill.getBillDate()+" "+bill.getBillAmount());
                    }
                }
                bills.toArray();
                ArrayAdapter<Object> billsAdapter = new ArrayAdapter<Object>(OrderEntry.this
                        ,android.R.layout.simple_list_item_1,bills.toArray());
                billsView.setAdapter(billsAdapter);

            }
        });



        long empId= SessionInfo.instance().getEmployee().getEmployeeId();
        Call<CustomerListDTO> customerListDTOCall = new ProdcastDClient().getClient().getCustomers(empId);
       customerListDTOCall.enqueue(new Callback<CustomerListDTO>() {

            @Override
            public void onResponse(Call<CustomerListDTO> call, Response<CustomerListDTO> response) {
                if (response.isSuccessful()) {
                    CustomerListDTO customerListDTO = response.body();
                    SessionInfo.instance().setCustomerList(customerListDTO.getCustomerList());
                    SessionInfo.instance().setOutStandingBills(customerListDTO.getOutstandingBills());
                        List<Customer> customerList = customerListDTO.getCustomerList();
                    String[] newList = new String[customerList.size()];
                        for (int i = 0; i < customerList.size(); i++) {
                            Customer customer = customerList.get(i);
                            newList[i] = customer.getCustomerName();
                        }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(OrderEntry.this, android.R.layout.select_dialog_item, newList);
                            customerNames.setThreshold(1);
                            customerNames.setAdapter(adapter);



                }
                else {

                    }
            }

            @Override
            public void onFailure(Call<CustomerListDTO> call, Throwable t) {

            }
        });


    }
}

