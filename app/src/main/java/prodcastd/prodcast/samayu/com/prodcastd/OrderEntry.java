package prodcastd.prodcast.samayu.com.prodcastd;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.samayu.prodcast.prodcastd.SessionInfo;
import com.samayu.prodcast.prodcastd.dto.Bill;
import com.samayu.prodcast.prodcastd.dto.Customer;
import com.samayu.prodcast.prodcastd.dto.CustomerDTO;
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
    private ListView billsView;
    private AutoCompleteTextView customerNames;
    private Spinner methodOfPayment;
    private int selectedBillIndex;
    View focusView;
    EditText cashPay, checkNumber,checkComments ;
    private Customer selectedCustomer;
    Button payButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        employeeId=getIntent().getExtras().getString("employeeId");
        setContentView(R.layout.activity_order_entry);
        billsView = (ListView)findViewById(R.id.billsView);
        methodOfPayment=(Spinner)findViewById(R.id.paymentType);
          checkNumber =(EditText)findViewById(R.id.checkNumber);
         checkComments=(EditText) findViewById(R.id.checkComments);
        cashPay = (EditText)findViewById(R.id.cash);
        Button newOrder = (Button)findViewById(R.id.newOrder);
        customerNames = (AutoCompleteTextView)findViewById(R.id.acTextViev);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(OrderEntry.this,R.array.payment_method,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        methodOfPayment.setAdapter(adapter);
        methodOfPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 2){
                    findViewById(R.id.checkPanel).setVisibility(View.VISIBLE);
                }
                else {
                    findViewById(R.id.checkPanel).setVisibility(View.GONE);
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
                List<Customer> customerList =SessionInfo.instance().getCustomerList();
                String customerName = (String)adapterView.getItemAtPosition(i);
                for (int a =0;a<customerList.size();a++){
                    selectedCustomer = customerList.get(a);
                    if (selectedCustomer.getCustomerName().equalsIgnoreCase(customerName)){
                        break;
                    }
                }

                recreateOutstandingBills();
            }
        });
        billsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                selectedBillIndex = i;
                    View b4 = findViewById(R.id.llpayment);
                    b4.setVisibility(View.VISIBLE);

            }
        });
        payButton = (Button)findViewById(R.id.pay);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePayment();

            }
        });
        newOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderEntry.this,NewOrder.class);
                startActivity(intent);
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

    public void recreateOutstandingBills(){
        billsView.clearChoices();
        findViewById(R.id.llpayment).setVisibility(View.GONE);
        findViewById(R.id.checkPanel).setVisibility(View.GONE);
        cashPay.setText("");
        methodOfPayment.setSelection(0);
        List<String> bills  = new LinkedList<String>();
        List<Bill> allOutStandingBills = SessionInfo.instance().getOutStandingBills();
        List<Bill> customerBills = new LinkedList<Bill>();
        SessionInfo.instance().setCustomerBills(customerBills);
        for (int j =0;j<allOutStandingBills.size();j++){
            Bill bill = allOutStandingBills.get(j);
            if (bill.getCustomerId() == selectedCustomer.getId()){
                bills.add(""+bill.getBillNumber()+" "+bill.getBillDate()+" "+bill.getBillAmount()+" "+bill.getOutstandingBalance());
                customerBills.add(bill);
            }
        }

        Bill[] billArray = new Bill[customerBills.size()];
        for(int i=0;i<customerBills.size();i++)
        {
            billArray[i] = customerBills.get(i);
        }
        BillViewAdapter adapter = new BillViewAdapter(OrderEntry.this , billArray );
        billsView.setAdapter( adapter );
        /*ArrayAdapter<Object> billsAdapter = new ArrayAdapter<Object>(OrderEntry.this
                ,android.R.layout.simple_list_item_1,bills.toArray());
        billsView.setAdapter(billsAdapter);
        billsAdapter.notifyDataSetChanged();*/
        findViewById(R.id.llbills).setVisibility(View.VISIBLE);

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
        if (methodOfPayment.getSelectedItemPosition() == 0){
            focusView = methodOfPayment;
            cancel = true;
        }
        if (TextUtils.isEmpty(checkNo)){
            checkNumber.setError("Please Enter the check number");
            focusView = checkNumber;
            cancel = true;
        }
        if (TextUtils.isEmpty(checkCmt)){
            checkComments.setError("Please Enter some comments");
            focusView = checkComments;
            cancel = true;
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
        final String cash = cashPay.getText().toString();
        if (validatePayment()){
            return;
        }
        long employeeId =SessionInfo.instance().getEmployee().getEmployeeId();
        Bill bill = SessionInfo.instance().getCustomerBills().get(selectedBillIndex);
        long billId = bill.getBillNumber();
        double amount = Double.parseDouble(cash);
        long customerId =bill.getCustomerId();
        String message = ""+bill.getBillNumber()+" "+bill.getBillDate()+" "+bill.getBillAmount();
        Toast.makeText(OrderEntry.this,message,Toast.LENGTH_LONG).show();

        String checkNo = checkNumber.getText().toString();
        String checkCmt = checkComments.getText().toString();
        final Call<CustomerDTO> customerDTOCall = new ProdcastDClient().getClient().makePayment(methodOfPayment.getSelectedItemPosition(), employeeId,billId,amount,customerId,checkNo,checkCmt);
        customerDTOCall.enqueue(new Callback<CustomerDTO>() {
            @Override
            public void onResponse(Call<CustomerDTO> call, Response<CustomerDTO> response) {
                if (response.isSuccessful()){
                    CustomerDTO dto = response.body();
                    if (!dto.isError()){

                        Toast.makeText(OrderEntry.this,"Payment Successful",Toast.LENGTH_LONG).show();
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


                        //Payment Hide
                        //Reset Spinner, Text

                    }
                    else {
                        Toast.makeText(OrderEntry.this,"Payment Not Success",Toast.LENGTH_LONG).show();
                        payButton.setEnabled(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerDTO> call, Throwable t) {
                Toast.makeText(OrderEntry.this,"Sorry!! Due to technical failure payment is not successful ",Toast.LENGTH_LONG).show();
                payButton.setEnabled(true);

            }
        });
    }
    public static  class  BillViewAdapter extends ArrayAdapter<Bill>{
        public BillViewAdapter(Context context,Bill[] bills){
            super(context,R.layout.sample_bill_view,R.id.billNumber,bills);
        }
        public View getView(int position,View converView,ViewGroup parent){
            BillView billView = new BillView(getContext(),getItem(position));
            billView.setData();
            return billView;
        }
    }
}

