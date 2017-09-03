package prodcastd.prodcast.samayu.com.prodcastd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.samayu.prodcast.prodcastd.dto.Collection;
import com.samayu.prodcast.prodcastd.dto.Order;
import com.samayu.prodcast.prodcastd.ui.CollectionView;
import com.samayu.prodcast.prodcastd.ui.ReportSalesEntry;

import java.util.List;

/**
 * Created by kdsdh on 8/26/2017.
 */

public class ReportExpandableListViewAdapter extends BaseExpandableListAdapter {

    List<String> expandableListTitle ;
    Context context;
    Order[] orders;
    Collection[] collections;

    public ReportExpandableListViewAdapter(Context context, List<String> titles, Order[] orders, Collection[] collections){
        this.context = context;
        this.orders = orders;
        this.collections = collections;
        this.expandableListTitle = titles;
    }

    @Override
    public int getGroupCount() {
        return 2;
    }

    @Override
    public int getChildrenCount(int i) {
        if(i==0){
            return orders.length+1;
        }
        else{
            return collections.length+1;
        }

    }

    @Override
    public Object getGroup(int i) {
        if( i == 0 ) return orders;
        else return collections;

    }

    @Override
    public Object getChild(int i, int i1) {
        if( i==0){
            return orders[i1];
        }
        else{
            return collections[i1];
        }
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View view, ViewGroup parent) {

        String title = expandableListTitle.get(listPosition);
        LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.reportnestedheaderlayout, null);
        ((TextView)view.findViewById(R.id.expandableReportHeader)).setText( title );
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if( i == 0 ){
            View childView = null;
            if( i1 == 0 ){
                //Header
                LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                childView = inflater.inflate( R.layout.reportsalesheader , null );
            }
            else {
                ReportSalesEntry entry = new ReportSalesEntry(context, (Order) getChild(i, i1-1));
                entry.setData();
                childView = entry;
            }

            return childView ;
        }
        else{
            View childView = null;
            if( i1 == 0 ){
                //Header
                LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                childView = inflater.inflate( R.layout.reportpayementheader , null );
            }
            else {
                CollectionView entry = new CollectionView(context, (Collection) getChild(i, i1-1));
                entry.setData();
                childView = entry;
            }

            return childView ;
        }
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
