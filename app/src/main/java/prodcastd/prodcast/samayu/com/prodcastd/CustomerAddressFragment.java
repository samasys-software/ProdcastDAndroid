package prodcastd.prodcast.samayu.com.prodcastd;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.samayu.prodcast.prodcastd.dto.Country;
import com.samayu.prodcast.prodcastd.dto.CountryDTO;
import com.samayu.prodcast.prodcastd.dto.Customer;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;
import com.samayu.prodcast.prodcastd.ui.CustomerCreateEditActivity;
import com.samayu.prodcast.prodcastd.ui.OnFragmentInteractionListener;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class CustomerAddressFragment extends ProdcastValidatedFragment {
    private OnFragmentInteractionListener mListener;
private Customer customer = new Customer();
    EditText unitNumber;
    EditText billingAddress1;
    EditText billingAddress2;
    EditText billingAddress3;
    EditText city;
    EditText state;
    Spinner country;
    EditText postalCode;
    TextView currentLocation;
    Button reset1;
    Button next1;
    View focusView = null;

Context context;


    public CustomerAddressFragment() {
        // Required empty public constructor
    }

    public static CustomerAddressFragment newInstance() {
        CustomerAddressFragment fragment = new CustomerAddressFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_address, container, false);
        // Inflate the layout for this fragment

        currentLocation = (TextView) view.findViewById(R.id.currentLocation);
        unitNumber = (EditText) view.findViewById(R.id.unitNumber);
        billingAddress1 = (EditText) view.findViewById(R.id.billingAddress1);
        billingAddress2 = (EditText) view.findViewById(R.id.billingAddress2);
        billingAddress3 = (EditText) view.findViewById(R.id.billingAddress3);
        city = (EditText) view.findViewById(R.id.city);
        state = (EditText) view.findViewById(R.id.state);
        country = (Spinner) view.findViewById(R.id.country);
        postalCode = (EditText) view.findViewById(R.id.postalCode);
        next1 = (Button) view.findViewById(R.id.next1);
        reset1 = (Button) view.findViewById(R.id.reset1);


        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validate()){
                    ((CustomerCreateEditActivity)getActivity()).getmViewPager().setCurrentItem(2);
                }
                //((CustomerCreateEditActivity)getActivity()).getmViewPager().setCurrentItem(2);
            }
        });

        reset1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unitNumber.setText("");
                billingAddress1.setText("");
                billingAddress2.setText("");
                billingAddress3.setText("");
                city.setText("");
                state.setText("");
                country.setSelection(0);
                postalCode.setText("");


            }

    });


        Call<CountryDTO> countryDTOCall = new ProdcastDClient().getClient().getCountries();
        countryDTOCall.enqueue(new Callback<CountryDTO>() {
            @Override
            public void onResponse(Call<CountryDTO> call, Response<CountryDTO> response) {
                if (response.isSuccessful()){
                    CountryDTO countryDTO = response.body();
                    List<Country> countryList= countryDTO.getResult();
                    Country defaultCountry = new Country();
                    defaultCountry.setCountryId("");
                    defaultCountry.setCountryName("Select Country");
                    countryList.add(0, defaultCountry  );
                    ArrayAdapter<Country> adapter = new ArrayAdapter<Country>(CustomerAddressFragment.this.getActivity(), android.R.layout.simple_list_item_1, countryList);
                    country.setAdapter(adapter);
                    if (customer != null) {

                        String selectedCountry = customer.getCountry();
                        Country aCountry = new Country();
                        aCountry.setCountryId(selectedCountry);
                        int totalCountry = ((ArrayAdapter) country.getAdapter()).getPosition(aCountry);
                        country.setSelection(totalCountry);
                    }
                }

            }

            @Override
            public void onFailure(Call<CountryDTO> call, Throwable t) {
                t.printStackTrace();

            }
        });
        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask task = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                        String locationProvider = LocationManager.GPS_PROVIDER;
                        String fullAddress = "";
                        if (ActivityCompat.checkSelfPermission(CustomerAddressFragment.this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(CustomerAddressFragment.this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            fullAddress = "No Permission available";
                            ActivityCompat.requestPermissions(CustomerAddressFragment.this.getActivity(), new String[] {
                                            Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_COARSE_LOCATION },
                                    1);
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                        }
                        {

                            try {
                                Looper.prepare();
                                manager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
                                    @Override
                                    public void onLocationChanged(Location location) {
                                        System.out.println("Location Changed ");
                                    }

                                    @Override
                                    public void onStatusChanged(String s, int i, Bundle bundle) {
                                        System.out.println("Location Status Changed ");
                                    }

                                    @Override
                                    public void onProviderEnabled(String s) {
                                        System.out.println("Provider Enabled ");
                                    }

                                    @Override
                                    public void onProviderDisabled(String s) {
                                        System.out.println("Provider Disabled");
                                    }
                                }, null);
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                            Location currentLocation = manager.getLastKnownLocation(locationProvider);
                            if(currentLocation==null){
                                return "No Permission Available";
                            }
                            Geocoder coder = new Geocoder(CustomerAddressFragment.this.getActivity());
                            try {
                                List<Address> addresses = coder.getFromLocation(currentLocation.getLatitude() , currentLocation.getLongitude() , 20 );
                                //List<Address> addresses = coder.getFromLocation(10.828547,78.666821,20);
                                if(addresses.size()>3){
                                    Address address = addresses.get(0);

                                    return address;
                                }



                            } catch (IOException e) {
                                fullAddress+="Error "+e;
                                e.printStackTrace();
                            }

                        }
                        return fullAddress;
                    }
                };

                task.execute((Object[])null);
                Address address = null;
                try {
                    Object result = task.get();
                    if( result instanceof  Address ){
                        address = (Address) result;
                        postalCode.setText( address.getPostalCode());
                        String  address1 = address.getAddressLine(0);
                        String address2 = address.getAddressLine(1);
                        String address3 = address.getAddressLine(2);
                        String[] address1Array = address1.split(" ");
                        unitNumber.setText ( address1Array[0] );
                        billingAddress1.setText(address1Array[1]);
                        StringTokenizer st = new StringTokenizer(address2,", ");
                        String[] address2Array = address2.split(", ");

                        city.setText(st.nextToken());
                        state.setText(st.nextToken());
                        ArrayAdapter<Country> adapter =(ArrayAdapter<Country>) country.getAdapter();
                        int count =  adapter.getCount();
                        for(int i = 0; i<count; i++){
                            Country aCountry= adapter.getItem(i);
                            if(aCountry.getCountryName().equalsIgnoreCase(address3)){
                                country.setSelection(i);
                                break;
                            }
                        }
                    }
                    else if( result instanceof  String ) {
                        String errorText = (String) task.get();
                        //TODO UnitNumber setError.
                    }
                } catch (InterruptedException e) {

                    e.printStackTrace();
                } catch (ExecutionException e) {

                    e.printStackTrace();
                }

            }
        });

        if (customer != null) {
            unitNumber.setText(customer.getUnitNumber());
            billingAddress1.setText(customer.getBillingAddress1());
            billingAddress2.setText(customer.getBillingAddress2());
            billingAddress3.setText(customer.getBillingAddress3());
            state.setText(customer.getState());
            city.setText(customer.getCity());



            postalCode.setText(customer.getPostalCode());
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
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
    public boolean checkValid( String unitNum, String billAdd1,   String cty,String stat,int contry, String pincode) {
        boolean cancel = false;
        unitNumber.setError(null);
        billingAddress1.setError(null);
        city.setError(null);
        state.setError(null);
        postalCode.setError(null);
        if (TextUtils.isEmpty(unitNum)) {
            unitNumber.setError("Please Enter Unit Number");
            focusView = unitNumber;
            cancel = true;
        }
        if (TextUtils.isEmpty(billAdd1)) {
            billingAddress1.setError("Please Enter Billing Address1");
            focusView = billingAddress1;
            cancel = true;
        }

        if (TextUtils.isEmpty(cty)) {
            city.setError("Please Enter City");
            focusView = city;
            cancel = true;
        }
        if (TextUtils.isEmpty(stat)) {
            state.setError("Please Enter State");
            focusView = state;
            cancel = true;
        }
        if (contry <= 0) {
            focusView = country;
            cancel = true;
        }
        if (TextUtils.isEmpty(pincode)) {
            postalCode.setError("Please Enter PostalCode");
            focusView = postalCode;
            cancel = true;
        }


        return cancel;
    }


    public boolean validate(){
        boolean cancel = false;
        String unitNum = unitNumber.getText().toString();
        String billAdd1 = billingAddress1.getText().toString();
        //String billAdd2 = billingAddress2.getText().toString();
        //String billAdd3 = billingAddress3.getText().toString();
        String stat = state.getText().toString();
        String cty = city.getText().toString();
        int contry = country.getSelectedItemPosition();
        String pincode = postalCode.getText().toString();

        return checkValid(unitNum, billAdd1, stat, cty, contry, pincode);
    }

    @Override
    public void setDetailsInCustomer(Customer customer) {
     customer.setUnitNumber(unitNumber.getText().toString());
        customer.setBillingAddress1(billingAddress1.getText().toString());
        customer.setBillingAddress2(billingAddress2.getText().toString());
        customer.setBillingAddress3( billingAddress3.getText().toString());
        customer.setState(state.getText().toString());
        customer.setCity(city.getText().toString());
        Country selectedCountry = (Country) country.getSelectedItem();
        String selectedCountryId = selectedCountry.getCountryId();
        customer.setCountry(selectedCountryId);
        customer.setPostalCode(postalCode.getText().toString());
    }

    @Override
    public void setDetailsFromCustomer(Customer customer) {
       this.customer = customer;

    }

}
