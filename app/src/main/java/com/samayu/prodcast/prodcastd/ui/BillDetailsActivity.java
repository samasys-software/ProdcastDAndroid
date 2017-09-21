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
//                        titles.add("Return Details");
                        expandableListView.setAdapter(
                                new BillDetailsExpandableListViewAdapter(
                                        BillDetailsActivity.this, titles, order.getOrderEntries(), order.getCollectionEntries())
                        );
                        expandableListView.expandGroup(0);
                        expandableListView.expandGroup(1);


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

        TextView custName = (TextView) findViewById(R.id.custName);
        TextView custAddress1 = (TextView) findViewById(R.id.custAddress1);
        TextView custAddress2 = (TextView) findViewById(R.id.custAddress2);
        TextView custAddress3 = (TextView) findViewById(R.id.custAddress3);
        TextView custPhoneNo = (TextView) findViewById(R.id.custPhonenumber);
        TextView orderNo = (TextView) findViewById(R.id.orderNo);
        TextView billDate = (TextView) findViewById(R.id.billDate);
        TextView total = (TextView) findViewById(R.id.total);
        TextView balance = (TextView) findViewById(R.id.balance);
        TextView discount = (TextView) findViewById(R.id.discount);
        custName.setText(order.getCustomerName());
        custAddress1.setText(order.getCustomer().getBillingAddress1() + " " + order.getCustomer().getBillingAddress2() + " " + order.getCustomer().getBillingAddress3());
        custAddress2.setText(order.getCustomer().getCity());
        custAddress3.setText(order.getCustomer().getState() + " " + order.getCustomer().getPostalCode());
        custPhoneNo.setText(order.getCustomer().getCellPhone());
        orderNo.setText("Order No:"+" "+String.valueOf(order.getBillNumber()));
        billDate.setText("BillDate:"+" "+String.valueOf(order.getBillDate()));
        total.setText("Total:"+" "+currencySymbol+String.valueOf(order.getTotalAmount()));
        balance.setText("Balance:"+" "+currencySymbol+String.valueOf(order.getOutstandingBalance()));
        if(order.getDiscountType()==0){
            discount.setText("Discount:"+" "+currencySymbol+0);
        }
        if(order.getDiscountType()==1) {
            discount.setText("Discount:"+" "+currencySymbol+String.valueOf(order.getDiscount()));
        }
         if(order.getDiscountType()==2){
            discount.setText("Discount:"+" "+String.valueOf(order.getDiscount())+"%");
        }

    }
}