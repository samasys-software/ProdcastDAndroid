package com.samayu.prodcast.prodcastd.ui;

import android.content.Context;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.samayu.prodcast.prodcastd.SessionInfo;
import com.samayu.prodcast.prodcastd.dto.OrderEntry;
import com.samayu.prodcast.prodcastd.dto.OrderEntryDTO;
import com.samayu.prodcast.prodcastd.util.Constants;

import java.util.List;

import prodcastd.prodcast.samayu.com.prodcastd.R;

/**
 * Created by kathir on 8/24/2017.
 */

public class ProductView extends FrameLayout {
    private com.samayu.prodcast.prodcastd.dto.OrderEntry product;
    private QuantityChangedListener quantityChangedListener ;

    public ProductView(Context context, OrderEntry product, QuantityChangedListener listener ) {
        super(context);
        this.product = product;
        this.quantityChangedListener = listener;


        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sample_totalproduct_view, this );
    }

    public void setData(){
        ((TextView)findViewById(R.id.product)).setText(String.valueOf( product.getProductName()));
        ((EditText)findViewById(R.id.quantityEdit)).setText(String.valueOf(product.getQuantity()));

        ((TextView)findViewById(R.id.subTotalAmount)).setText(Constants.PRICE_FORMAT.format( product.getSubtotal() ));


        ((EditText)findViewById(R.id.quantityEdit)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                charSequence.toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                   String newQuantity = editable.toString();

                   int intQuantity =0;

                    try{
                        intQuantity = Integer.parseInt(newQuantity);


                    }
                    catch(Exception er){
                        ((EditText)findViewById(R.id.quantityEdit)).setText("0");
                        return;
                    }
                product.setQuantity( intQuantity );
                product.setSubtotal( intQuantity* product.getUnitPrice());

                ((TextView)findViewById(R.id.subTotalAmount)).setText(Constants.PRICE_FORMAT.format( product.getSubtotal() ));

                quantityChangedListener.quantityChanged();
            }
        });
        ((TextView)findViewById(R.id.product)).setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return false;
            }
        });
    }

    public static interface QuantityChangedListener{
        public void quantityChanged();
    }

}
