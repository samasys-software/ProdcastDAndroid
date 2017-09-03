package prodcastd.prodcast.samayu.com.prodcastd.ui.dummy;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.samayu.prodcast.prodcastd.SessionInfo;
import com.samayu.prodcast.prodcastd.dto.Collection;
import com.samayu.prodcast.prodcastd.dto.Order;
import com.samayu.prodcast.prodcastd.dto.OrderDTO;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;

import java.util.ArrayList;

import java.util.List;

import prodcastd.prodcast.samayu.com.prodcastd.ProdcastBaseActivity;
import prodcastd.prodcast.samayu.com.prodcastd.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillDetailsActivity extends ProdcastBaseActivity {
    ListView orderListView;
    ListView paymentListView;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);
        Long billId = getIntent().getExtras().getLong("billId");
        paymentListView=(ListView) findViewById(R.id.paymentEntriesAdapter);
        orderListView=(ListView) findViewById(R.id.orderEntriesAdapter);
        context=this;
        Call<OrderDTO> billDetailsDTO = new ProdcastDClient().getClient().getBillDetails(billId,SessionInfo.instance().getEmployee().getEmployeeId(),"D");
        billDetailsDTO.enqueue(new Callback<OrderDTO>() {
            @Override
            public void onResponse(Call<OrderDTO> call, Response<OrderDTO> response) {
                String responseString = null;
                OrderDTO dto = response.body();
                if (dto.isError()) {
                    Toast.makeText(BillDetailsActivity.this, dto.getErrorMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Order order=dto.getOrder();
                    setBillDetails(order);
                    Toast.makeText(context, "vvgfv", Toast.LENGTH_LONG).show();


                        paymentListView.setAdapter(new PaymentDetailsList(BillDetailsActivity.this,order.getCollectionEntries()));

                        orderListView.setAdapter(new BillDetailList(BillDetailsActivity.this,order.getOrderEntries()));

                }
            }
            @Override
            public void onFailure(Call<OrderDTO> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }

    @Override
    public String getProdcastTitle() {
        return "Bill Details";
    }
public void setBillDetails(Order order){
    TextView tv = (TextView) findViewById(R.id.distName);
    TextView tv1 = (TextView) findViewById(R.id.custName);
    TextView tv2 = (TextView) findViewById(R.id.distAddress);
    TextView tv3 = (TextView) findViewById(R.id.custAddress);
    TextView tv4 = (TextView) findViewById(R.id.distAddress1);
    TextView tv5 = (TextView) findViewById(R.id.custAddress1);
    TextView tv6 = (TextView) findViewById(R.id.distPhonenumber);
    TextView tv7 = (TextView) findViewById(R.id.custPhonenumber);
    TextView orderNo = (TextView) findViewById(R.id.orderNo);
    TextView billDate = (TextView) findViewById(R.id.billDate);
    TextView total = (TextView) findViewById(R.id.total);
    TextView salesRep = (TextView) findViewById(R.id.salesRep);
    TextView balance = (TextView) findViewById(R.id.balance);
    TextView discount = (TextView) findViewById(R.id.discount);
    tv.setText(order.getDistributorName());
    tv2.setText(order.getDistributor().getAddress1() + " " + order.getDistributor().getAddress2() + " " + order.getDistributor().getAddress3());
    tv4.setText(order.getDistributor().getCity() + " " + order.getDistributor().getState() + " " + order.getDistributor().getPostalCode());
    tv6.setText(order.getDistributor().getHomePhone());
    // tv4.setText(distributorDetails.get(position).getCity()+" "+distributorDetails.get(position).getState()+" "+distributorDetails.get(position).getPostalCode());
    //  tv2.setText(distributorDetails.get(position).getAddress1()+" "+distributorDetails.get(position).getAddress2()+" "+distributorDetails.get(position).getAddress3());
    // tv6.setText(distributorDetails.get(position).getHomePhone());

    tv1.setText(order.getCustomerName());
    tv3.setText(order.getCustomer().getBillingAddress1() + " " + order.getCustomer().getBillingAddress2() + " " + order.getCustomer().getBillingAddress3());
    tv5.setText(order.getCustomer().getCity() + " " + order.getCustomer().getState() + " " + order.getCustomer().getPostalCode());
    tv7.setText(order.getCustomer().getCellPhone());
    // tv5.setText(customerDetails.get(position).getCity()+" "+customerDetails.get(position).getState()+" "+customerDetails.get(position).getPostalCode());

    // tv7.setText(customerDetails.get(position).getCellPhone());
    orderNo.setText(String.valueOf(order.getBillNumber()));
    billDate.setText(String.valueOf(order.getBillDate()));
    salesRep.setText(order.getEmployeeName());
    total.setText(String.valueOf(order.getTotalAmount()));
    balance.setText(String.valueOf(order.getOutstandingBalance()));
    discount.setText(String.valueOf(order.getDiscount()));

}
}