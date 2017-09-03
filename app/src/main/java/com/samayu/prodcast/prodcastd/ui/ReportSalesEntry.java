package com.samayu.prodcast.prodcastd.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.samayu.prodcast.prodcastd.dto.Bill;
import com.samayu.prodcast.prodcastd.dto.Order;

import prodcastd.prodcast.samayu.com.prodcastd.R;

/**
 * TODO: document your custom view class.
 */
public class ReportSalesEntry extends FrameLayout {
    private Order order ;
    LayoutInflater inflater;

    public ReportSalesEntry(Context context, Order order) {
        super(context);
        this.order = order;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sample_report_sales_entry, this );
    }
    //@Override
   // public View getView(final int position, View convertView, ViewGroup parent){

   // }

    public void setData(){
        ((TextView)findViewById(R.id.sCustomer)).setText(String.valueOf( order.getCustomerName()));
        ((TextView)findViewById(R.id.sDate)).setText(String.valueOf(order.getBillDate()));
        ((TextView)findViewById(R.id.sTotal)).setText(String.valueOf(order.getTotalAmount()));
        ((TextView)findViewById(R.id.SBalance)).setText(String.valueOf(order.getOutstandingBalance()));
    }


   }
