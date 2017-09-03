package prodcastd.prodcast.samayu.com.prodcastd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.samayu.prodcast.prodcastd.SessionInfo;
import com.samayu.prodcast.prodcastd.dto.Bill;
import com.samayu.prodcast.prodcastd.dto.Customer;
import com.samayu.prodcast.prodcastd.dto.CustomerDTO;
import com.samayu.prodcast.prodcastd.dto.OrderDetailDTO;
import com.samayu.prodcast.prodcastd.dto.OrderEntryDTO;
import com.samayu.prodcast.prodcastd.dto.Product;
import com.samayu.prodcast.prodcastd.dto.ProductListDTO;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;
import com.samayu.prodcast.prodcastd.dto.OrderEntry;
import com.samayu.prodcast.prodcastd.util.Constants;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewOrder extends ProdcastBaseActivity implements
        ProductView.QuantityChangedListener{
//extends ProdcastBaseActivity {


    private AutoCompleteTextView productList;
    private EditText quantity,discountValue;
    private TextView displayTax,displayTotal,tax,total,customerName;
    private FloatingActionButton addToList;
    private SwipeMenuListView addedProducts;
    private Spinner discountType;
    private Button saveOrder,applyDiscount,addDiscount;
    private View focusView = null;
    private Product selectedProductItem;
    private ImageView dropDown;
    private long customerId=0;
    private String value = "0";
    ProgressDialog progress;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_order);
        Customer customerIdCus = SessionInfo.instance().getSelectedCustomer();
        customerId = customerIdCus.getId();
        String customerType = customerIdCus.getCustomerType();
        SessionInfo.instance().setCart(new LinkedList<OrderEntry>());
        displayTax = (TextView) findViewById(R.id.displayTax);
        displayTotal = (TextView) findViewById(R.id.displayTotal);
        customerName = (TextView) findViewById(R.id.customerName);
        discountValue = (EditText)findViewById(R.id.discountValue);
        tax = (TextView) findViewById(R.id.tvTax);
        addDiscount = (Button)findViewById(R.id.addDiscount);
        discountType = (Spinner)findViewById(R.id.discountType);
        total = (TextView) findViewById(R.id.tvTotal);
        quantity = (EditText) findViewById(R.id.etQuantity);
        applyDiscount =(Button)findViewById(R.id.discountButton);
        View bottomSheet = findViewById(R.id.bottomSheet);
        dropDown = (ImageView) findViewById(R.id.dropDown);
        customerName.setText(String.valueOf(customerIdCus));
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        findViewById(R.id.llemptyList).setVisibility(View.VISIBLE);
        behavior.setHideable(false);
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        applyDiscount.setTag(1);
        applyDiscount.setText("Apply Discount");
        addToList = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        addedProducts = (SwipeMenuListView) findViewById(R.id.productList);
        saveOrder = (Button) findViewById(R.id.button);
        productList = (AutoCompleteTextView) findViewById(R.id.productSearch);




        long employeeId = SessionInfo.instance().getEmployee().getEmployeeId();
        final ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(NewOrder.this,R.array.discount_type,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        discountType.setAdapter(arrayAdapter);

        Call<ProductListDTO> productListDTOCall = new ProdcastDClient().getClient().getProducts(employeeId);
        productListDTOCall.enqueue(new Callback<ProductListDTO>() {
            @Override
            public void onResponse(Call<ProductListDTO> call, Response<ProductListDTO> response) {
                if (response.isSuccessful()) {
                    ProductListDTO productListDTO = response.body();

                    SessionInfo.instance().setProductList(productListDTO.getProductList());
                    List<Product> fullProductList = productListDTO.getProductList();

                    ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(NewOrder.this, android.R.layout.select_dialog_item, fullProductList);
                    productList.setThreshold(1);
                    productList.setAdapter(adapter);
                    //productList.showDropDown();
                }
            }


            @Override
            public void onFailure(Call<ProductListDTO> call, Throwable t) {

            }
        });
        dropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productList.showDropDown();
            }
        });

        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedProductItem = null;
                Product selectedProduct = (Product) adapterView.getItemAtPosition(i);
                selectedProductItem = selectedProduct;
            }
        });
        addToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addedProducts.requestFocus();
                if (validate()) {
                    return;
                }
                List<OrderEntry> entries = SessionInfo.instance().getCart();
                if (entries.size() <= 0) {
                    findViewById(R.id.llemptyList).setVisibility(View.VISIBLE);

                }

                item();

                selectedProductItem = null;


            }
        });
        final SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                //deleteItem.setBackground(getDrawable(R.drawable.yellow_background));
                deleteItem.setWidth(170);
                deleteItem.setIcon(R.drawable.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        };
        addedProducts.setMenuCreator(creator);
        addedProducts.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                SessionInfo.instance().getCart().remove(position);
                ProductViewAdapter adapter = new ProductViewAdapter(NewOrder.this, SessionInfo.instance().getCart(), NewOrder.this);
                addedProducts.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                if (SessionInfo.instance().getCart() != null && SessionInfo.instance().getCart().size() == 0) {
                    findViewById(R.id.llemptyList).setVisibility(View.VISIBLE);
                    findViewById(R.id.linearMain).setVisibility(View.GONE);
                    findViewById(R.id.llTotal).setVisibility(View.GONE);
                    findViewById(R.id.llTotalAmount).setVisibility(View.GONE);
                    findViewById(R.id.ccDropDown).setVisibility(View.GONE);
                }
                grandTotal();
                return false;
            }
        });

        addedProducts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


       discountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

           }
           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });


        applyDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.llDiscount).setVisibility(View.VISIBLE);
                findViewById(R.id.llDiscountButton).setVisibility(View.VISIBLE);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                final int status = (Integer) view.getTag();
                if (status == 1) {
                    findViewById(R.id.llDiscount).setVisibility(View.VISIBLE);
                    addDiscount.setVisibility(View.VISIBLE);
                    applyDiscount.setText("Dismiss Discount");
                    applyDiscount.setTag(0);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    findViewById(R.id.llDiscount).setVisibility(View.GONE);
                    addDiscount.setVisibility(View.GONE);
                    discountType.setSelection(0);
                    discountValue.setText("");
                    applyDiscount.setText("Apply Discount");
                    applyDiscount.setTag(1);
                    grandTotal();

                }
            }
        });
        addDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateDiscount()) {
                    return;
                }
                grandTotal();
            }
        });




        saveOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (listValidation()) {
                    return;
                }
                OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
                orderDetailDTO.setEmployeeId(SessionInfo.instance().getEmployee().getEmployeeId());
                orderDetailDTO.setCustomerId(customerId);

                List<OrderEntryDTO> orderEntries = new LinkedList<OrderEntryDTO>();
                List<OrderEntry> cartEntries = SessionInfo.instance().getCart();
                orderDetailDTO.setEntries(orderEntries);
                for (int i = 0; i < cartEntries.size(); i++) {
                    OrderEntry cartEntry = cartEntries.get(i);
                    OrderEntryDTO dto = new OrderEntryDTO();
                    dto.setQuantity(cartEntry.getQuantity());
                    dto.setProductId(cartEntry.getProductId());
                    orderEntries.add(dto);
                }
                float paymentAmount = 0;
                orderDetailDTO.setPaymentAmount(paymentAmount);
                orderDetailDTO.setOrderStatus("F");

                //Make a call to the Service.
                progress = ProgressDialog.show(NewOrder.this, "In Progress",
                        "One moment Please...", true);
                Call<CustomerDTO> customerDTOCall = new ProdcastDClient().getClient().saveOrder(orderDetailDTO);
                customerDTOCall.enqueue(new Callback<CustomerDTO>() {
                    @Override
                    public void onResponse(Call<CustomerDTO> call, Response<CustomerDTO> response) {
                        if (response.isSuccessful()) {
                            CustomerDTO customerDto = response.body();
                            if (!customerDto.isError()) {
                                List<Bill> customerBills = customerDto.getCustomer().getOutstandingBill();

                                List<String> bills = new LinkedList<String>();
                                List<Bill> allOutStandingBills = SessionInfo.instance().getOutStandingBills();
                                SessionInfo.instance().setCustomerBills(customerBills);
                                List<Bill> newOutstandingBills = new LinkedList<Bill>();
                                for (int j = 0; j < allOutStandingBills.size(); j++) {
                                    Bill bill = allOutStandingBills.get(j);
                                    if (bill.getCustomerId() != customerId) {
                                        newOutstandingBills.add(bill);
                                    }
                                }

                                newOutstandingBills.addAll(customerBills);
                                SessionInfo.instance().setSelectedCustomer(customerDto.getCustomer());
                                SessionInfo.instance().setOutStandingBills(newOutstandingBills);
                                Toast.makeText(NewOrder.this, "saved the list", Toast.LENGTH_LONG).show();
                                progress.dismiss();
                                Intent intent = new Intent(NewOrder.this, prodcastd.prodcast.samayu.com.prodcastd.OrderEntry.class);

                                startActivity(intent);
                            } else {
                                Toast.makeText(NewOrder.this, "Error saving the list", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(NewOrder.this, "Error saving the list", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CustomerDTO> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });


    }
    private void grandTotal(){


        List<OrderEntry> items = SessionInfo.instance().getCart();

        float totalPrice = 0;
        float otherTax = 0;
        float salesTax = 0;
        for(int i = 0 ; i < items.size(); i++) {
            totalPrice += items.get(i).getSubtotal();
            otherTax += items.get(i).getOtherTax()*items.get(i).getSubtotal()/100;
            salesTax += items.get(i).getSalesTax()*items.get(i).getSubtotal()/100;
        }
        float totalTax = otherTax + salesTax;
        float totalPriceTax =totalPrice +totalTax;
        String name= null;
        if (discountType != null && discountType.getSelectedItem() != null) {
                name = (String) discountType.getSelectedItem();
                int selectedSpinner = discountType.getSelectedItemPosition();
                OrderDetailDTO dto = new OrderDetailDTO();
                if (selectedSpinner == 1) {
                    value = discountValue.getText().toString();
                    float discountCash = totalPriceTax - (Float.parseFloat(value));
                    float cash = discountCash;
                    displayTotal.setText(Constants.PRICE_FORMAT.format(cash));
                    displayTax.setText(Constants.PRICE_FORMAT.format(totalTax));
                    dto.setDiscountType(name);
                    dto.setDiscountValue(value);


                } else if (selectedSpinner == 2) {
                    value = discountValue.getText().toString();
                    float discountPercentage = totalPriceTax*(1-((Float.parseFloat(value)) / 100));
                    displayTotal.setText(Constants.PRICE_FORMAT.format(discountPercentage));
                    //float discountTax = totalTax *(1-((Float.parseFloat(value)) / 100));
                    displayTax.setText(Constants.PRICE_FORMAT.format(totalTax));
                    dto.setDiscountType(name);
                    dto.setDiscountValue(value);
                } else {
                    displayTotal.setText(Constants.PRICE_FORMAT.format(totalPriceTax));
                    displayTax.setText(Constants.PRICE_FORMAT.format(totalTax));

                }
        }
    }

    @Override
    public void quantityChanged() {
        grandTotal();
    }



    public static  class  ProductViewAdapter extends ArrayAdapter<OrderEntry>{
        private ProductView.QuantityChangedListener listener ;

        public ProductViewAdapter(Context context, List<OrderEntry> orderEntries, ProductView.QuantityChangedListener listener){
            super(context,R.layout.sample_totalproduct_view,R.id.product,orderEntries);
            this.listener = listener;

        }
        public View getView(int position,View convertView,ViewGroup parent){
            ProductView productView = new ProductView(getContext(),getItem(position) , listener );
            productView.setData();
            return productView;
        }
    }
    public boolean validate(){
        boolean cancel = false;
        String quantityEntered = quantity.getText().toString();
        String productEntered = productList.getText().toString();
        if (TextUtils.isEmpty(quantityEntered)) {
            quantity.setError("Enter the quantity");
            focusView = quantity;
            cancel = true;
        }
        if (TextUtils.isEmpty(productEntered)) {
            productList.setError("Choose the product");
            focusView = productList;
            cancel = true;
        }
        return cancel;
    }
    public boolean listValidation(){
        boolean cancel = false;
        List<OrderEntry> entries = SessionInfo.instance().getCart();
        if (entries.size() <= 0) {
            Toast.makeText(NewOrder.this,"The product list is empty",Toast.LENGTH_LONG).show();
            cancel = true;
        }
        return cancel;
    }
    public boolean validateDiscount(){
        boolean cancel = false;
        String discountAmount = discountValue.getText().toString();
        if (TextUtils.isEmpty(discountAmount)) {
            discountValue.setError("Please Enter the Value");
            focusView = discountValue;
            cancel =true;
        }
        if (discountType.getSelectedItem().toString().trim().equals("Type")){
            Toast.makeText(NewOrder.this,"Please select Payment type!!",Toast.LENGTH_LONG).show();
            focusView = discountType;
            cancel = true;
        }
        return cancel;
    }
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(NewOrder.this);
        builder.setTitle("Exit this page!!!")
                .setMessage("Do yo want to Exit without Saving the Order")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        NewOrder.super.onBackPressed();
                    }
                })
                .setNegativeButton("Cancel",null);
        AlertDialog alert = builder.create();

        alert.show();

    }

    @Override
    public String getProdcastTitle() {
        return "New Order";
    }

    public void item(){
        findViewById(R.id.llemptyList).setVisibility(View.GONE);
        findViewById(R.id.linearMain).setVisibility(View.VISIBLE);
        addedProducts.setVisibility(View.VISIBLE);
        findViewById(R.id.llTotal).setVisibility(View.VISIBLE);
        findViewById(R.id.llTotalAmount).setVisibility(View.VISIBLE);
        findViewById(R.id.ccDropDown).setVisibility(View.VISIBLE);
        final int enteredQuantity = Integer.parseInt(quantity.getText().toString());
        List<OrderEntry> entryList = SessionInfo.instance().getCart();
        long allId = 0;
        int allQuantity = 0;
        int totalQuantity = 0;
        OrderEntry newEntry = new OrderEntry();
        for (int i =0; i<entryList.size();i++) {
            OrderEntry inEntry = new OrderEntry();
            inEntry= entryList.get(i);
            allId = inEntry.getProductId();
            if (selectedProductItem.getId() == allId ) {

                allQuantity = entryList.get(i).getQuantity();
                Toast.makeText(NewOrder.this," "+selectedProductItem.getProductName()+" is already in the list so the quantity is added ",Toast.LENGTH_LONG).show();
                SessionInfo.instance().getCart().remove(inEntry);
            }
        }
        totalQuantity = allQuantity + enteredQuantity;

        newEntry.setProductName(selectedProductItem.toString());
        newEntry.setProductId(selectedProductItem.getId());
        newEntry.setUnitPrice(selectedProductItem.getUnitPrice());
        newEntry.setOtherTax((Float.parseFloat(selectedProductItem.getOtherTax())));
        newEntry.setSalesTax((Float.parseFloat(selectedProductItem.getSalesTax())));
        newEntry.setQuantity(totalQuantity);
        newEntry.setSubtotal(totalQuantity * (selectedProductItem.getUnitPrice()));
        entryList.add(newEntry);
        ProductViewAdapter adapter = new ProductViewAdapter(NewOrder.this, SessionInfo.instance().getCart(), NewOrder.this);

        addedProducts.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        productList.clearListSelection();
        productList.setText("");
        focusView = productList;
        quantity.setText("");
        grandTotal();

    }

}
