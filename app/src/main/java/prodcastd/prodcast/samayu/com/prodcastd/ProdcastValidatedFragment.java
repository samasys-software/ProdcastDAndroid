package prodcastd.prodcast.samayu.com.prodcastd;

import android.support.v4.app.Fragment;

import com.samayu.prodcast.prodcastd.dto.Customer;

/**
 * Created by kdsdh on 8/20/2017.
 */

public abstract class ProdcastValidatedFragment extends Fragment {

    public abstract boolean validate();
    public abstract void setDetailsInCustomer(Customer customer);
    public abstract void setDetailsFromCustomer(Customer customer);
}
