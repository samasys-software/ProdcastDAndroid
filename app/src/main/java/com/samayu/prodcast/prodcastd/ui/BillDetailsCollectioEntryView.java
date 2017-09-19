package com.samayu.prodcast.prodcastd.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.samayu.prodcast.prodcastd.dto.Collection;
import com.samayu.prodcast.prodcastd.util.GlobalUsage;

import java.text.NumberFormat;

import prodcastd.prodcast.samayu.com.prodcastd.R;

/**
 * Created by fgs on 9/18/2017.
 */

public class BillDetailsCollectioEntryView extends FrameLayout {
    private Collection collection;
    LayoutInflater inflater;
    NumberFormat numberFormat= GlobalUsage.getNumberFormat();
    public BillDetailsCollectioEntryView(Context context, Collection collection) {
        super(context);
        this.collection=collection;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.billdetails_paymententryview,this);

    }
    public void setData(){
        ((TextView)findViewById(R.id.paymentDate)).setText(String.valueOf(collection.getPaymentDate()));
        ((TextView)findViewById(R.id.paymentReceiver)).setText(collection.getEmployeeName());
        ((TextView)findViewById(R.id.paymentAmount)).setText(numberFormat.format(collection.getAmountPaid()));

    }
}
