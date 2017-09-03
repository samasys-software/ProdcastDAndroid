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
import android.widget.FrameLayout;
import android.widget.TextView;

import com.samayu.prodcast.prodcastd.dto.Collection;
import com.samayu.prodcast.prodcastd.dto.Order;

import prodcastd.prodcast.samayu.com.prodcastd.R;

/**
 * TODO: document your custom view class.
 */
public class CollectionView extends FrameLayout {
    private Collection collection;
    LayoutInflater inflater;

    public CollectionView(Context context, Collection collection) {
        super(context);
        this.collection = collection;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sample_collection, this );
    }
    //@Override
    // public View getView(final int position, View convertView, ViewGroup parent){

    // }

    public void setData(){
        ((TextView)findViewById(R.id.pCustomer)).setText(String.valueOf(collection.getCustomerName()));
        ((TextView)findViewById(R.id.pDate)).setText(String.valueOf(collection.getPaymentDate()));
        ((TextView)findViewById(R.id.pAmount)).setText(String.valueOf(collection.getAmountPaid()));
        ((TextView)findViewById(R.id.pType)).setText(String.valueOf(collection.getPaymentType()));
    }

}
