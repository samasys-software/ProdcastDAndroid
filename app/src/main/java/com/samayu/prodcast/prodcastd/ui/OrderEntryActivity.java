package com.samayu.prodcast.prodcastd.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.samayu.prodcast.prodcastd.SessionInfo;
import com.samayu.prodcast.prodcastd.dto.Bill;
import com.samayu.prodcast.prodcastd.dto.Customer;
import com.samayu.prodcast.prodcastd.dto.CustomerDTO;
import com.samayu.prodcast.prodcastd.dto.CustomerListDTO;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;

import java.util.LinkedList;
import java.util.List;


import prodcastd.prodcast.samayu.com.prodcastd.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderEntryActivity extends ProdcastBaseActivity {
    private ListView billsView,payment;
    private LinearLayout checkPanel;
    private AutoCompleteTextView customerNames;
    private Spinner methodOfPayment;
    private int selectedBillIndex;
    private FloatingActionButton fabNewOrder;
    private TextView name;
    View focusView;
    EditText cashPay, checkNumber,checkComments ;
    private Customer selectedCustomer;
    Button payButton;
    View.OnKeyListener listener = null;
    Bundle productBundle = null;
    private String screenName;
    boolean paymentScreen=false;
    private ProgressDialog progress;

    TextView totalCurrencySymbol, oTotalCurrencySymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_entry);

        productBundle = getIntent().getExtras();

        screenName=getIntent().getStringExtra("Screen Name");
        name =(TextView)findViewById(R.id.tvName);
        checkPanel=(LinearLayout)findViewById(R.id.checkPanel);
        billsView = (ListView)findViewById(R.id.billsView);
        methodOfPayment=(Spinner)findViewById(R.id.paymentType);
        fabNewOrder = (FloatingActionButton)findViewById(R.id.fab);
        checkNumber =(EditText)findViewById(R.id.checkNumber);
        checkComments=(EditText) findViewById(R.id.checkComments);
        cashPay = (EditText)findViewById(R.id.cash);
        Button newOrder = (Button)findViewById(R.id.newOrder);
        customerNames = (AutoCompleteTextView)findViewById(R.id.acTextViev);

        String currencySymbol = SessionInfo.instance().getEmployee().getCurrencySymbol();

        totalCurrencySymbol = (TextView) findViewById(R.id.totalCurrencySymbol);
        oTotalCurrencySymbol = (TextView) findViewById(R.id.oTotalCurrencySymbol);

        totalCurrencySymbol.setText("("+currencySymbol+")");
        oTotalCurrencySymbol.setText("("+currencySymbol+")");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(OrderEntryActivity.this,R.array.payment_method,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        methodOfPayment.setAdapter(adapter);
        if(screenName.equals("paymentScreen")){
            paymentScreen=true;

        }

        listener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                customerNames.setOnKeyListener(null);
                findViewById(R.id.llName).setVisibility(View.GONE);
                findViewById(R.id.llbills).setVisibility(View.GONE);
                findViewById(R.id.llnoOutstandingBills).setVisibility(View.GONE);
                fabNewOrder.setVisibility(View.GONE);
                findViewById(R.id.llpayment).setVisibility(View.GONE);
                return false;
            }
        };

        methodOfPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 2){
                    checkPanel.setVisibility(View.VISIBLE);
                }
                else {
                    checkPanel.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        customerNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCustomer= null;
                customerNames.setOnKeyListener(listener);
                findViewById(R.id.llbills).setVisibility(View.GONE);
                findViewById(R.id.llName).setVisibility(View.GONE);
                List<Customer> customerList =SessionInfo.instance().getCustomerList();
                selectedCustomer  = (Customer)adapterView.getItemAtPosition(i);
                name.setText(selectedCustomer.getCustomerName());
                SessionInfo.instance().setSelectedCustomer( selectedCustomer );
                recreateOutstandingBills();

            }
        });
        billsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBillIndex = i;
                if(!paymentScreen)
                    fabNewOrder.setVisibility(View.VISIBLE);
                View b4 = findViewById(R.id.llpayment);
                b4.setVisibility(View.VISIBLE);
                payButton.setEnabled(true);


            }
        });
        billsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(OrderEntryActivity.this, BillDetailsActivity.class);
                bundle.putInt("billId",selectedBillIndex);
                intent.putExtras(bundle);
                startActivity(intent,bundle);
                return false;
            }
        });
        payButton = (Button)findViewById(R.id.pay);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payButton.setEnabled(true);
                makePayment();
            }
        });
        fabNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(OrderEntryActivity.this,NewOrderActivity.class);
                SessionInfo.instance().setSelectedCustomer(selectedCustomer);
                startActivity(i);
            }
        });



        selectedCustomer = SessionInfo.instance().getSelectedCustomer();


        if( selectedCustomer == null ){
            fetchCustomers();
        }
        else{

            findViewById(R.id.llName).setVisibility(View.VISIBLE);

            ArrayAdapter<Customer> customeAdapter = new ArrayAdapter<Customer>(OrderEntryActivity.this, android.R.layout.select_dialog_item,SessionInfo.instance().getCustomerList());
            customerNames.setThreshold(1);
            customerNames.setAdapter(customeAdapter);
            name.setText(selectedCustomer.getCustomerName());
            recreateOutstandingBills();


        }

    }

    @Override
    public String getProdcastTitle() {
        return "Order Entry";
    }

    public void fetchCustomers(){
        long empId= SessionInfo.instance().getEmployee().getEmployeeId();
        findViewById(R.id.llName).setVisibility(View.VISIBLE);
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
                    ArrayAdapter<Customer> adapter = new ArrayAdapter<Customer>(OrderEntryActivity.this, android.R.layout.select_dialog_item,SessionInfo.instance().getCustomerList());
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
    public void recreateOutstandingBills() {
        billsView.clearChoices();
        customerNames.setText("");
        findViewById(R.id.llName).setVisibility(View.VISIBLE);
        if(!paymentScreen)
            fabNewOrder.setVisibility(View.VISIBLE);
        findViewById(R.id.llpayment).setVisibility(View.GONE);
        findViewById(R.id.checkPanel).setVisibility(View.GONE);
        cashPay.setText("");
        methodOfPayment.setSelection(0);


        List<String> bills = new LinkedList<String>();
        List<Bill> allOutStandingBills = SessionInfo.instance().getOutStandingBills();
        List<Bill> customerBills = new LinkedList<Bill>();
        SessionInfo.instance().setCustomerBills(customerBills);
        for (int j = 0; j < allOutStandingBills.size(); j++) {
            Bill bill = allOutStandingBills.get(j);
            if (bill.getCustomerId() == selectedCustomer.getId()) {
                bills.add("" + bill.getBillNumber() + " " + bill.getBillDate() + " " + bill.getBillAmount() + " " + bill.getOutstandingBalance());
                customerBills.add(bill);
            }
        }


        Bill[] billArray = new Bill[customerBills.size()];
        if (customerBills.size() != 0) {
                findViewById(R.id.llName).setVisibility(View.VISIBLE);
            findViewById(R.id.llnoOutstandingBills).setVisibility(View.GONE);
            if(!paymentScreen)
                fabNewOrder.setVisibility(View.VISIBLE);
            for (int i = 0; i < customerBills.size(); i++) {
                billArray[i] = customerBills.get(i);
            }
            BillViewAdapter adapter = new BillViewAdapter(OrderEntryActivity.this, billArray);
            billsView.setAdapter(adapter);
            findViewById(R.id.llName).setVisibility(View.VISIBLE);
            findViewById(R.id.llbills).setVisibility(View.VISIBLE);
            if(!paymentScreen)
                fabNewOrder.setVisibility(View.VISIBLE);
            name.setText(selectedCustomer.getCustomerName());
        }
        else {
            findViewById(R.id.llName).setVisibility(View.VISIBLE);
            findViewById(R.id.llnoOutstandingBills).setVisibility(View.VISIBLE);
            findViewById(R.id.llbills).setVisibility(View.GONE);
            if(!paymentScreen)
                fabNewOrder.setVisibility(View.VISIBLE);
            name.setText(selectedCustomer.getCustomerName());
        }
    }
    private boolean validatePayment(){
        final String cash = cashPay.getText().toString();
        final String checkNo=checkNumber.getText().toString();
        final  String checkCmt=checkComments.getText().toString();
        boolean cancel = false;
        cashPay.setError(null);
        if (TextUtils.isEmpty(cash)){
            cashPay.setError("Please Enter the Amount");
              focusView = cashPay;
            cancel = true;
        }
        if (methodOfPayment.getSelectedItem().toString().trim().equals("PaymentActivity Type")){
            Toast.makeText(OrderEntryActivity.this,"Please select PaymentActivity type!!",Toast.LENGTH_LONG).show();
            focusView = methodOfPayment;
            cancel = true;
        }
        if (methodOfPayment.getSelectedItemPosition() == 2) {
            if (TextUtils.isEmpty(checkNo)) {
                checkNumber.setError("Please Enter the check number");
                focusView = checkNumber;
                cancel = true;
            }
            if (TextUtils.isEmpty(checkCmt)) {
                checkComments.setError("Please Enter some comments");
                focusView = checkComments;
                cancel = true;
            }
        }
        try {
            double paymentAmount = Double.parseDouble(cash.trim());
            Bill bill = SessionInfo.instance().getCustomerBills().get(selectedBillIndex);
            if (paymentAmount > bill.getOutstandingBalance()){
                cashPay.setError("The Amount should be less than "+bill.getOutstandingBalance());
                focusView = cashPay;
                cancel = true;
            }
        }
        catch (NumberFormatException e){
            cancel = true;
            cashPay.setError("Please Enter a Valid Amount");
            focusView = cashPay;
        }
        return cancel;
    }
    private void makePayment(){
        payButton.setEnabled(false);
        final String cash = cashPay.getText().toString();
        if (validatePayment()){
            payButton.setEnabled(true);
            return;
        }
        long employeeId =SessionInfo.instance().getEmployee().getEmployeeId();
        final Bill bill = SessionInfo.instance().getCustomerBills().get(selectedBillIndex);
        long billId = bill.getBillNumber();
        double amount = Double.parseDouble(cash);
        long customerId =bill.getCustomerId();
        String message = ""+bill.getBillNumber()+" "+bill.getBillDate()+" "+bill.getBillAmount();
        String checkNo = checkNumber.getText().toString();
        String checkCmt = checkComments.getText().toString();
        payButton.setEnabled(false);
        progress = ProgressDialog.show(OrderEntryActivity.this,"In Progress","One moment Please......",true);
        final Call<CustomerDTO> customerDTOCall = new ProdcastDClient().getClient().makePayment(methodOfPayment.getSelectedItemPosition(), employeeId,billId,amount,customerId,checkNo,checkCmt);
        customerDTOCall.enqueue(new Callback<CustomerDTO>() {
            @Override
            public void onResponse(Call<CustomerDTO> call, Response<CustomerDTO> response) {
                if (response.isSuccessful()){
                    CustomerDTO dto = response.body();
                    if (!dto.isError()){
                        progress.dismiss();
                        Toast.makeText(OrderEntryActivity.this,"PaymentActivity Successful for Bill no : "+bill.getBillNumber(),Toast.LENGTH_LONG).show();

                        SessionInfo.instance().setCustomerBills(dto.getCustomer().getOutstandingBill());
                        List<Bill> newAllOutstandingBills = new LinkedList<Bill>();
                        for(Bill aBill: SessionInfo.instance().getOutStandingBills()){
                            if( selectedCustomer.getId() != aBill.getCustomerId() ){
                                newAllOutstandingBills.add( aBill );
                            }
                        }
                        newAllOutstandingBills.addAll( dto.getCustomer().getOutstandingBill() );
                        SessionInfo.instance().setOutStandingBills( newAllOutstandingBills );
                        recreateOutstandingBills();
                        }
                    else {
                        Toast.makeText(OrderEntryActivity.this,"PaymentActivity Not Success",Toast.LENGTH_LONG).show();
                        payButton.setEnabled(true);
                    }
                }
            }
            @Override
            public void onFailure(Call<CustomerDTO> call, Throwable t) {
                Toast.makeText(OrderEntryActivity.this,"Sorry!! Due to technical failure payment is not successful ",Toast.LENGTH_LONG).show();
                payButton.setEnabled(true);
            }
        });
    }

    public static  class  BillViewAdapter extends ArrayAdapter<Bill>{
        public BillViewAdapter(Context context,Bill[] bills){
            super(context,R.layout.sample_bill_view,bills);
        }
        public View getView(int position,View converView,ViewGroup parent){
            BillView billView = new BillView(getContext(),getItem(position));
            billView.setData();
            return billView;
        }
    }
}

