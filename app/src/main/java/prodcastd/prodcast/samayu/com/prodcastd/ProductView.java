package prodcastd.prodcast.samayu.com.prodcastd;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.samayu.prodcast.prodcastd.dto.OrderEntry;

/**
 * Created by kathir on 8/24/2017.
 */

public class ProductView extends FrameLayout {
    private com.samayu.prodcast.prodcastd.dto.OrderEntry product;

    public ProductView(Context context, OrderEntry product) {
        super(context);
        this.product = product;

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sample_totalproduct_view, this );
    }

    public void setData(){
        ((TextView)findViewById(R.id.product)).setText(String.valueOf( product.getProductName()));
        ((EditText)findViewById(R.id.quantityEdit)).setText(String.valueOf(product.getQuantity()));
        ((TextView)findViewById(R.id.subTotalAmount)).setText(String.valueOf(product.getSubtotal()));

    }
}
