package com.samayu.prodcast.prodcastd.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import prodcastd.prodcast.samayu.com.prodcastd.R;

public class PaymentActivity extends AppCompatActivity {
    TextView amountHeading,paymentHeading;
    EditText amountEntry;
    Spinner methodOfPayment;
    Button payButton,newOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        amountHeading = (TextView)findViewById(R.id.amounttv);
        paymentHeading =(TextView)findViewById(R.id.payment);
        amountEntry =(EditText)findViewById(R.id.cash);
        methodOfPayment=(Spinner)findViewById(R.id.paymentType);
        payButton = (Button)findViewById(R.id.pay);
        newOrderButton =(Button)findViewById(R.id.newOrder);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(PaymentActivity.this,R.array.payment_method,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        methodOfPayment.setAdapter(adapter);
        methodOfPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(PaymentActivity.this,adapterView.getItemAtPosition(i)+" selected",Toast.LENGTH_LONG).show();
                Intent intent;
                switch(i) {
                    case 1:
                        intent = new Intent(PaymentActivity.this, CheckNumberActivity.class);
                        startActivity(intent);
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        newOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
