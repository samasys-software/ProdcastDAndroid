package prodcastd.prodcast.samayu.com.prodcastd;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.samayu.prodcast.prodcastd.SessionInfo;
import com.samayu.prodcast.prodcastd.dto.AdminDTO;
import com.samayu.prodcast.prodcastd.dto.Area;
import com.samayu.prodcast.prodcastd.dto.Customer;
import com.samayu.prodcast.prodcastd.dto.StoreType;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;
import com.samayu.prodcast.prodcastd.ui.CustomerCreateEditActivity;
import com.samayu.prodcast.prodcastd.ui.OnFragmentInteractionListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static prodcastd.prodcast.samayu.com.prodcastd.R.id.area;
import static prodcastd.prodcast.samayu.com.prodcastd.R.id.selectDay;



public class CustomerCompanyFragment extends ProdcastValidatedFragment {


    private OnFragmentInteractionListener mListener;
    EditText companyName;
    EditText customerId1;
    EditText customerId2;
    Spinner selectDay;
    EditText customerDesc1;
    EditText customerDesc2;
    Spinner area;
    Spinner selectCustomerType;
    Spinner storeType;
    Button resetButton;
    Button nextButton;

    View focusView = null;


    Map<String,String> daysMap = new HashMap<>();
    public CustomerCompanyFragment() {
        // Required empty public constructor
    }


    public static CustomerCompanyFragment newInstance() {
        CustomerCompanyFragment fragment = new CustomerCompanyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_company, container, false);
        daysMap.put("Sunday","SU");
        daysMap.put("Monday","MO");
        daysMap.put("Tuesday","TU");
        daysMap.put("Wednesday","WE");
        daysMap.put("Thursday","TH");
        daysMap.put("Friday", "FR");
        daysMap.put("Saturday","SA");
        daysMap.put("Multiple Days","ML");

        companyName = (EditText) view.findViewById(R.id.companyName);
        customerId1 = (EditText) view.findViewById(R.id.customerId1);
        customerId2 = (EditText) view.findViewById(R.id.customerId2);
        selectDay = (Spinner) view.findViewById(R.id.selectDay);

        ArrayAdapter adapterday = ArrayAdapter.createFromResource(this.getActivity(), R.array.select_day,R.layout.support_simple_spinner_dropdown_item);
        adapterday.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selectDay.setAdapter(adapterday);
        selectDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        customerDesc1 = (EditText) view.findViewById(R.id.customerDesc1);
        customerDesc2 = (EditText) view.findViewById(R.id.customerDesc2);
        area = (Spinner) view.findViewById(R.id.area);

        //SessionInfo.instance().getEmployee().getEmployeeId()
        Call<AdminDTO<List<Area>>> areaDTOCall= new ProdcastDClient().getClient().getAreasForDistributor(621);
        areaDTOCall.enqueue(new Callback<AdminDTO<List<Area>>>() {
            @Override
            public void onResponse(Call<AdminDTO<List<Area>>> call, Response<AdminDTO<List<Area>>> response) {
                if(response.isSuccessful()){
                    AdminDTO<List<Area>> areaDTO = response.body();
                    List<Area> areaList= areaDTO.getResult();
                    Area defaultArea = new Area();
                    defaultArea.setId(-1l);
                    defaultArea.setDescription("Select Area");
                    areaList.add(0, defaultArea);

                    ArrayAdapter<Area> adapter = new ArrayAdapter<Area>(CustomerCompanyFragment.this.getActivity(), android.R.layout.simple_list_item_1, areaList);
                    area.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<AdminDTO<List<Area>>> call, Throwable t) {
                t.printStackTrace();

            }

        });

        selectCustomerType = (Spinner) view.findViewById(R.id.selectCustomerType);

        ArrayAdapter adapterCustomerType = ArrayAdapter.createFromResource(this.getActivity(), R.array.select_customer_type,R.layout.support_simple_spinner_dropdown_item);
        adapterday.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selectCustomerType.setAdapter(adapterCustomerType);
        selectCustomerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        storeType = (Spinner) view.findViewById(R.id.storetype);

        Call<AdminDTO<List<StoreType>>> adminDTOCall = new ProdcastDClient().getClient().getStoreTypes();
        adminDTOCall.enqueue(new Callback<AdminDTO<List<StoreType>>>() {
            @Override
            public void onResponse(Call<AdminDTO<List<StoreType>>> call, Response<AdminDTO<List<StoreType>>> response) {
                if(response.isSuccessful()){
                    AdminDTO<List<StoreType>> adminDTO= response.body();
                    List<StoreType> storeTypeList = adminDTO.getResult();

                    StoreType defaultStoreType = new StoreType();
                    defaultStoreType.setStoreTypeId(-1l);
                    defaultStoreType.setStoreTypeName("Select Store Type");
                    storeTypeList.add(0, defaultStoreType  );


                    ArrayAdapter<StoreType> adapter = new ArrayAdapter<StoreType>(CustomerCompanyFragment.this.getActivity(), android.R.layout.simple_list_item_1, storeTypeList);
                    storeType.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<AdminDTO<List<StoreType>>> call, Throwable t) {
                t.printStackTrace();

            }
        });
        nextButton = (Button) view.findViewById(R.id.next);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validate())
                ((CustomerCreateEditActivity)getActivity()).getmViewPager().setCurrentItem(1);
            }
        });



        resetButton = (Button) view.findViewById(R.id.reset) ;

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                companyName.setText("");
                customerId1.setText("");
                customerId2.setText("");
                selectDay.setSelection(0);
                customerDesc1.setText("");
                customerDesc2.setText("");
                area.setSelection(0);
                selectCustomerType.setSelection(0);
                storeType.setSelection(0);
            }
        });




                return view;
       }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public boolean checkValid( String cpyName, String cusId1, String cusId2,String selectedDay, String cusDesc1, String cusDesc2, long aea,
                               String selectCusType, int streType){
        boolean cancel = false;
        companyName.setError("");
        customerId1.setError("");
        customerId2.setError("");
        customerDesc1.setError("");
        customerDesc2.setError("");


        if (TextUtils.isEmpty(cpyName)) {
            companyName.setError("Please enter Company Name");
            focusView = companyName;
            cancel = true;
        }
        if (TextUtils.isEmpty(cusId1)) {
            customerId1.setError("Please enter Customer Id1");
            focusView = customerId1;
            cancel = true;
        }
        if (TextUtils.isEmpty(cusId2)) {
            customerId1.setError("Please enter Customer Id2");
            focusView = customerId2;
            cancel = true;
        }
        if(selectDay.getSelectedItem().toString().trim().equals("Select Day")){
            focusView = selectDay;
            cancel = true;
        }

        if (TextUtils.isEmpty(cusDesc1)) {
            customerDesc1.setError("Please Enter Customer Desc1");
            focusView = customerDesc1;
            cancel = true;
        }
        if (TextUtils.isEmpty(cusDesc2)) {
            customerDesc2.setError("Please Enter Customer Desc2");
            focusView = customerDesc2;
            cancel = true;
        }
        if(aea <= 0){
            focusView = area;
            cancel = true;
        }
        if(selectCustomerType.getSelectedItem().toString().trim().equals("Select Customer Type")){
            focusView = selectCustomerType;
            cancel = true;
        }
        if(streType <= 0){
            focusView = storeType;
            cancel = true;
        }
        return cancel;
    }


    public boolean validate(){

        boolean cancel = false;
        String cpyName = companyName.getText().toString();
        String cusId1 = customerId1.getText().toString();
        String cusId2 = customerId2.getText().toString();
        String selectedDay = (String)selectDay.getSelectedItem();
        String cusDesc1= customerDesc1.getText().toString();
        String cusDesc2= customerDesc2.getText().toString();
        long aea = area.getSelectedItemPosition();
        String selectCusType = (String) selectCustomerType.getSelectedItem();
        int streType = storeType.getSelectedItemPosition();

        return checkValid(cpyName, cusId1, cusId2,selectedDay,cusDesc1,cusDesc2,aea,selectCusType,streType);

    }

    @Override
    public void setDetailsInCustomer(Customer customer) {

    }

}
