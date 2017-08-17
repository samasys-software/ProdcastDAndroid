package prodcastd.prodcast.samayu.com.prodcastd;

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

import com.samayu.prodcast.prodcastd.dto.Bill;

/**
 * TODO: document your custom view class.
 */
public class BillView extends FrameLayout {

   private Bill bill;

    public BillView(Context context, Bill bill) {
        super(context);
        this.bill = bill;

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sample_bill_view, this );
    }

    public void setData(){
        ((TextView)findViewById(R.id.billNumber)).setText(String.valueOf( bill.getBillNumber()));
        ((TextView)findViewById(R.id.bDate)).setText(String.valueOf(bill.getBillDate()));
        ((TextView)findViewById(R.id.bTotalAmount)).setText(String.valueOf(bill.getBillAmount()));
        ((TextView)findViewById(R.id.bOutstandingAmount)).setText(String.valueOf(bill.getOutstandingBalance()));
    }


}