package com.samayu.prodcast.prodcastd.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import prodcastd.prodcast.samayu.com.prodcastd.R;

public class ViewOrdersActivity extends ProdcastBaseActivity {
    EditText billNumber;
    Button goButton;
    boolean cancel = false;
    View focusView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        billNumber=(EditText)findViewById(R.id.searchBillNo);
        goButton=(Button)findViewById(R.id.go);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedBillIndex = billNumber.getText().toString();

                    if(checkValue(selectedBillIndex)){
                        return ;
                    }

                    Intent intent = new Intent(ViewOrdersActivity.this, BillDetailsActivity.class);
                    intent.putExtra("billId", selectedBillIndex);
                    startActivity(intent);



            }
        });
    }
    public boolean checkValue(String billNo) {


        // Reset errors.
        cancel = false;
        billNumber.setError(null);

        if (TextUtils.isEmpty(billNo)) {
            billNumber.setError("This field is required");
            focusView = billNumber;
            cancel = true;
            return cancel;
        }
        return cancel;

    }

    @Override
    public String getProdcastTitle() {
        return "View Orders";
    }
}
