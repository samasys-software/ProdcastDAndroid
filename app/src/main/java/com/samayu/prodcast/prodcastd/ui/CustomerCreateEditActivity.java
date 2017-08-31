package com.samayu.prodcast.prodcastd.ui;

import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.samayu.prodcast.prodcastd.SessionInfo;
import com.samayu.prodcast.prodcastd.dto.Area;
import com.samayu.prodcast.prodcastd.dto.Country;
import com.samayu.prodcast.prodcastd.dto.Customer;
import com.samayu.prodcast.prodcastd.dto.CustomerListDTO;
import com.samayu.prodcast.prodcastd.dto.ProdcastDTO;
import com.samayu.prodcast.prodcastd.dto.StoreType;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;

import java.util.List;

import prodcastd.prodcast.samayu.com.prodcastd.CustomerActivity;
import prodcastd.prodcast.samayu.com.prodcastd.CustomerAddressFragment;
import prodcastd.prodcast.samayu.com.prodcastd.CustomerCompanyFragment;
import prodcastd.prodcast.samayu.com.prodcastd.CustomerContactFragment;
import prodcastd.prodcast.samayu.com.prodcastd.CustomersActivity;
import prodcastd.prodcast.samayu.com.prodcastd.ProdcastBaseActivity;
import prodcastd.prodcast.samayu.com.prodcastd.ProdcastValidatedFragment;
import prodcastd.prodcast.samayu.com.prodcastd.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static prodcastd.prodcast.samayu.com.prodcastd.R.layout.fragment_customer_address;

public class CustomerCreateEditActivity extends ProdcastBaseActivity implements OnFragmentInteractionListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    private SectionsPagerAdapter mSectionsPagerAdapter;

    public ViewPager getmViewPager() {

        return mViewPager;
    }

    public void setmViewPager(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
    }

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Customer currentCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_create_edit);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout=(TabLayout) findViewById(R.id.customersTab);
        tabLayout.setupWithViewPager(mViewPager);
       Bundle bundle = getIntent().getExtras();
        currentCustomer = null;
        if(bundle != null) {
            long customerId = bundle.getLong("customerId");

            List<Customer> customers = SessionInfo.instance().getCustomerList();

            for (int i = 0; i < customers.size(); i++) {
                if (customers.get(i).getId() == customerId) {
                    currentCustomer = customers.get(i);
                    break;
                }
            }

        }





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_customer_create_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public String getProdcastTitle() {
        return "Customer Activity";
    }


    @Override
    public void validateAndSave() {
            //Validate Each Fragment.


boolean cancel = false;
        Customer customer = new Customer();
        List<Fragment> allFragments=getSupportFragmentManager().getFragments();
        for (int i = 0;i< allFragments.size();i++) {
          Fragment fragment  = allFragments.get(i);
            if (fragment instanceof ProdcastValidatedFragment) {
                ProdcastValidatedFragment prodcastValidatedFragment =(ProdcastValidatedFragment)fragment;
                if (prodcastValidatedFragment.validate()) {
                    cancel = true;
                    getmViewPager().setCurrentItem(i);
                }
            }
        }
        if (!cancel) {

            for (int i = 0; i < allFragments.size(); i++) {
                Fragment fragment = allFragments.get(i);
                if (fragment instanceof ProdcastValidatedFragment) {
                    ProdcastValidatedFragment prodcastValidatedFragment = (ProdcastValidatedFragment) fragment;
                    prodcastValidatedFragment.setDetailsInCustomer(customer);

                }
            }


            String cpyName = customer.getCustomerName();
            String employeeIdString = String.valueOf(SessionInfo.instance().getEmployee().getEmployeeId());
            String selectCusType = customer.getCustomerType();
            Long selectedAreaId = Long.parseLong(customer.getArea());
            String selectedDay = String.valueOf(customer.getWeekday());
            String fstName = customer.getFirstname();

            long customerId = 0;
            boolean active =true;
            if( currentCustomer != null ){
                customerId = currentCustomer.getId();
                active = customer.isActive();
            }

            Call<CustomerListDTO> prodcastDTOCall = new ProdcastDClient().getClient().saveCustomer(employeeIdString, cpyName,
                    selectCusType, selectedAreaId, selectedDay, fstName, customer.getLastname(), customer.getEmailaddress(),
                    customer.getCellPhone(), customer.getPhonenumber(), customer.getUnitNumber(),
                    customer.getBillingAddress1(), customer.getBillingAddress2(), customer.getBillingAddress3(), customer.getCity(),
                    customer.getState(), customer.getCountry(), customer.isSmsAllowed()  ,
                    customer.getPostalCode(), customer.getNotes(),
                     customer.getCustomerId1() , customer.getCustomerId2(),customer.getCustomerDesc1() ,
                    customer.getCustomerDesc2(), customerId, active, customer.getStoreType());


            prodcastDTOCall.enqueue(new Callback<CustomerListDTO>() {
                @Override
                public void onResponse(Call<CustomerListDTO> call, Response<CustomerListDTO> response) {
                    if (response.isSuccessful()) {
                        CustomerListDTO prodcastDTO = response.body();

                        if (prodcastDTO.isError()) {
                            //TODO Show the ERror Message

                            Toast.makeText(CustomerCreateEditActivity.this, "Customer Registration is not saved", Toast.LENGTH_LONG).show();
                        } else {
                            List<Customer> customerList = prodcastDTO.getCustomerList();
                            SessionInfo.instance().setCustomerList( customerList );
                            Toast.makeText(CustomerCreateEditActivity.this, "Customer Registration is saved Successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(CustomerCreateEditActivity.this, CustomerListActivity.class);
                            Bundle bundle= new Bundle();
                            bundle.putBoolean("useCache" , true );
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                }

                @Override
                public void onFailure(Call<CustomerListDTO> call, Throwable t) {

                }

            });
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if( position == 0 ) {
                try {
                    CustomerCompanyFragment ccf = new CustomerCompanyFragment();
                    ccf.setDetailsFromCustomer(currentCustomer);
                    return ccf;
                }
                catch(Error er_){
                    er_.printStackTrace();
                    return null;
                }

            }
            if(position ==1 ){
                CustomerAddressFragment caf = new CustomerAddressFragment();
                caf.setDetailsFromCustomer(currentCustomer);
                return caf;
            }
            if ( position == 2 ) {
                CustomerContactFragment customerContactFragment = new CustomerContactFragment();
                customerContactFragment.setDetailsFromCustomer(currentCustomer);
                return customerContactFragment;
            }

            return null;
        }


        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Company";
                case 1:
                    return "Address";
                case 2:
                    return "Contact";
            }
            return null;
        }
    }
}
