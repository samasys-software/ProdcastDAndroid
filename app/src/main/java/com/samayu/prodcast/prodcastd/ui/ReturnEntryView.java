package com.samayu.prodcast.prodcastd.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.samayu.prodcast.prodcastd.dto.OrderEntry;
import com.samayu.prodcast.prodcastd.util.GlobalUsage;

import java.text.NumberFormat;

import prodcastd.prodcast.samayu.com.prodcastd.R;

/**
 * Created by fgs on 9/18/2017.
 */

public class ReturnEntryView extends FrameLayout {
    private OrderEntry retuenProduct;
    LayoutInflater inflater;
    NumberFormat numberFormat= GlobalUsage.getNumberFormat();

    public ReturnEntryView(Context context, OrderEntry orders) {
        super(context);
        this.retuenProduct=orders;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.billdetails_orderentryview,this);
    }
    public void setData(){
        ((TextView)findViewById(R.id.rproductName)).setText(String.valueOf( retuenProduct.getProductName()));
        ((TextView)findViewById(R.id.rqty)).setText(String.valueOf(retuenProduct.getQuantity()));
        ((TextView)findViewById(R.id.rprice)).setText(numberFormat.format(retuenProduct.getUnitPrice()));
        ((TextView)findViewById(R.id.rsubTotal)).setText(numberFormat.format(retuenProduct.getSubtotal()));


    }
}
