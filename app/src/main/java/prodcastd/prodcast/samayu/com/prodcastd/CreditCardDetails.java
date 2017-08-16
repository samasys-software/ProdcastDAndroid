package prodcastd.prodcast.samayu.com.prodcastd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;

public class CreditCardDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_details);
        CardForm cardForm=(CardForm)findViewById(R.id.cardDetails);
        TextView paymentAmount=(TextView)findViewById(R.id.payment_amount);
        Button pay=(Button)findViewById(R.id.btn_pay);
        cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {

            }
        });

    }
}
