package com.samayu.prodcast.prodcastd.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import prodcastd.prodcast.samayu.com.prodcastd.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.samayu.prodcast.prodcastd.SessionInfo;
import com.samayu.prodcast.prodcastd.dto.Customer;
import com.samayu.prodcast.prodcastd.dto.CustomerListDTO;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;

import java.util.List;


public class CustomerListActivity extends ProdcastBaseActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        boolean useCache = false;
        if( savedInstanceState!=null ){
            useCache = savedInstanceState.getBoolean("useCache");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerListActivity.this, CustomerCreateEditActivity.class );
                startActivity(intent);
            }
        });

        View recyclerView = findViewById(R.id.customer_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView, useCache);

        if (findViewById(R.id.customer_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    @Override
    public String getProdcastTitle() {
        return "My Customers";
    }

    private void setupRecyclerView(@NonNull final RecyclerView recyclerView, boolean useCache) {
        if( !useCache) {
            Call<CustomerListDTO> customerListDTOCall = new ProdcastDClient().getClient().getCustomers(SessionInfo.instance().getEmployee().getEmployeeId());
            customerListDTOCall.enqueue(new Callback<CustomerListDTO>() {
                @Override
                public void onResponse(Call<CustomerListDTO> call, Response<CustomerListDTO> response) {
                    if (response.isSuccessful()) {
                        CustomerListDTO customerListDTO = response.body();
                        List<Customer> customerList = customerListDTO.getCustomerList();
                        SessionInfo.instance().setCustomerList(customerList);
                        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(customerList));

                    }
                }

                @Override
                public void onFailure(Call<CustomerListDTO> call, Throwable t) {
                    t.printStackTrace();

                }
            });
        }
        else{
            recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(SessionInfo.instance().getCustomerList()));

        }

    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Customer> mValues;

        public SimpleItemRecyclerViewAdapter(List<Customer> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.customer_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(String.valueOf(mValues.get(position).getId()));
            holder.mContentView.setText(mValues.get(position).getCustomerName());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(CustomerDetailFragment.ARG_ITEM_ID, String.valueOf(holder.mItem.getId()));
                        CustomerDetailFragment fragment = new CustomerDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.customer_detail_container, fragment)
                                .commit();
                    } else */
                    {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, CustomerCreateEditActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putLong("customerId", holder.mItem.getId());
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public Customer mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
