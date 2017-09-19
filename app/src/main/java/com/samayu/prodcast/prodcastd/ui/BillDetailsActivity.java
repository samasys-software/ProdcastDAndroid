package com.samayu.prodcast.prodcastd.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.samayu.prodcast.prodcastd.SessionInfo;
import com.samayu.prodcast.prodcastd.dto.Bill;
import com.samayu.prodcast.prodcastd.dto.Employee;
import com.samayu.prodcast.prodcastd.dto.Order;
import com.samayu.prodcast.prodcastd.dto.OrderDTO;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;

import java.util.LinkedList;
import java.util.List;

import prodcastd.prodcast.samayu.com.prodcastd.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillDetailsActivity extends ProdcastBaseActivity {

    ExpandableListView expandableListView;

    Context context;
    private ProgressDialog progress;
    String currencySymbol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);

        currencySymbol= SessionInfo.instance().getEmployee().getCurrencySymbol();
        Intent data = getIntent();
        String billId = data.getStringExtra("billId");
       /* int billDetail = getIntent().getExtras().getInt("billId");
        Bill bill = SessionInfo.instance().getCustomerBills().get(billDetail);
        Long requiredBillId = bill.getBillNumber();*/

        expandableListView=(ExpandableListView) findViewById(R.id.expandableBillDetailsEntryList);
        context=this;
        progress = ProgressDialog.show(BillDetailsActivity.this,"In Progress","One moment Please...",true);
        Employee employeeDetails = SessionInfo.instance().getEmployee();
        long employeeId = employeeDetails.getEmployeeId();
        String userRole = employeeDetails.getUserRole();
        Call<OrderDTO> billDetailsDTO = new ProdcastDClient().getClient().getBillDetails(Long.parseLong(billId),employeeId,userRole);
        billDetailsDTO.enqueue(new Callback<OrderDTO>() {
            @Override
            public void onResponse(Call<OrderDTO> call, Response<OrderDTO> response) {
                String responseString = null;
                if(response.isSuccessful()) {
                    OrderDTO dto = response.body();
                    if (dto.isError()) {
                        Toast.makeText(BillDetailsActivity.this, dto.getErrorMessage(), Toast.LENGTH_LONG).show();
                        progress.dismiss();
                    } else {
                        progress.dismiss();
                        Order order = dto.getOrder();
                        setBillDetails(order);
                        List<String> titles = new LinkedList<String>();
                        titles.add("Order Details");
                        titles.add("Payment Details");
                        titles.add("Return Details");
                        expandableListView.setAdapter(
                                new BillDetailsExpandableListViewAdapter(
                                        BillDetailsActivity.this, titles, order.getOrderEntries(), order.getCollectionEntries(), order.getReturnEntries())
                        );
                        expandableListView.expandGroup(0);
                        expandableListView.expandGroup(1);
                        expandableListView.expandGroup(2);

                    }
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


        tv1.setText(order.getCustomerName());
        tv3.setText(order.getCustomer().getBillingAddress1() + " " + order.getCustomer().getBillingAddress2() + " " + order.getCustomer().getBillingAddress3());
        tv5.setText(order.getCustomer().getCity() + " " + order.getCustomer().getState() + " " + order.getCustomer().getPostalCode());
        tv7.setText(order.getCustomer().getCellPhone());

        orderNo.setText(String.valueOf(order.getBillNumber()));
        billDate.setText(String.valueOf(order.getBillDate()));
        salesRep.setText(order.getEmployeeName());
        total.setText("("+currencySymbol+")"+String.valueOf(order.getTotalAmount()));
        balance.setText("("+currencySymbol+")"+String.valueOf(order.getOutstandingBalance()));
        if(order.getDiscountType()==0){
            discount.setText("("+currencySymbol+")"+0);
        }
        if(order.getDiscountType()==1) {
            discount.setText("("+currencySymbol+")"+String.valueOf(order.getDiscount()));
        }
         if(order.getDiscountType()==2){
            discount.setText(String.valueOf(order.getDiscount())+"%");
        }

    }
}