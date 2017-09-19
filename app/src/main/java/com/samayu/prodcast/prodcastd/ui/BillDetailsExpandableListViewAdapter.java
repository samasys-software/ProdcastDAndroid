package com.samayu.prodcast.prodcastd.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.samayu.prodcast.prodcastd.SessionInfo;
import com.samayu.prodcast.prodcastd.dto.Collection;
import com.samayu.prodcast.prodcastd.dto.OrderEntry;
import java.util.List;
import prodcastd.prodcast.samayu.com.prodcastd.R;

/**
 * Created by fgs on 9/18/2017.
 */

public class BillDetailsExpandableListViewAdapter extends BaseExpandableListAdapter {

    List<String> expandableListTitle;
    Context context;
    List<OrderEntry> orderDetails;
    List<Collection> collections;
    List<OrderEntry> returnEntries;
    String  currencySymbol= SessionInfo.instance().getEmployee().getCurrencySymbol();
    public BillDetailsExpandableListViewAdapter(Context context, List<String> titles, List<OrderEntry> orders, List<Collection> collections,List<OrderEntry> returnProduct){

        this.context = context;
        this.orderDetails = orders;
        this.collections = collections;
        this.returnEntries=returnProduct;
        this.expandableListTitle = titles;
    }
    @Override
    public int getGroupCount() {

        return 3;
    }

    @Override
    public int getChildrenCount(int i) {
        if(i==0){
            return orderDetails.size()+1;
        }
        else if(i==1){
            return collections.size()+1;
        }
        else{
            return returnEntries.size()+1;
        }
    }

    @Override
    public Object getGroup(int i) {

        if(i==0){return orderDetails;}

        else if(i==1){return collections;}
        else {return  returnEntries;}
    }

    @Override
    public Object getChild(int i, int j) {
        if(i==0){
            return orderDetails.get(j);
        }
        else if(i==1){
            return collections.get(j);
        }
        else {
            return returnEntries.get(j);
        }
    }

    @Override
    public long getGroupId(int i) {

        return i;
    }

    @Override
    public long getChildId(int i, int j) {
        return j;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View view, ViewGroup parent) {
        String title = expandableListTitle.get(listPosition);
        LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.billdetilsnestedheader, null);
        ((TextView)view.findViewById(R.id.expandableBillDetailsHeader)).setText( title );
        return view;
    }


    @Override
    public View getChildView(int i, int j, boolean t, View view, ViewGroup viewGroup) {
        if(i==0){
            View childView=null;
            // adding orderentry table header
            if(j==0){
                LayoutInflater inflater=(LayoutInflater)this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                childView = inflater.inflate(R.layout.billdetailsorderheader,null);
                TextView subTotal=(TextView)childView.findViewById(R.id.orderSubtotal);
                TextView price=(TextView)childView.findViewById(R.id.orderPriceCurrency);
                subTotal.setText("("+currencySymbol+")");
                price.setText("("+currencySymbol+")");


            }
            else{
                BillDetailsOrderEntryView entry=new BillDetailsOrderEntryView(context,(OrderEntry) getChild(i,j-1));
                entry.setData();
                childView=entry;
            }
            return childView;
        }
        else if(i==1){
            View childView = null;
            if (j==0) {
                LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                childView = inflater.inflate(R.layout.billdetailspaymentheader, null);
                TextView paymentAmount=(TextView)childView.findViewById(R.id.paymentTotalCurrency);
                paymentAmount.setText("("+currencySymbol+")");
            }
            else {
                BillDetailsCollectioEntryView entry=new BillDetailsCollectioEntryView(context,(Collection) getChild(i,j-1));
                entry.setData();
                childView=entry;
            }
            return  childView;

        }
        else{
            View childView=null;
            if(j==0){
                LayoutInflater inflater=(LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                childView=inflater.inflate(R.layout.billdetailsreturnentryheader,null);
                TextView subTotalCurrency=(TextView)childView.findViewById(R.id.subTotalCurrency);
                TextView priceCurrency=(TextView)childView.findViewById(R.id.priceCurrency);
                subTotalCurrency.setText("("+currencySymbol+")");
                priceCurrency.setText("("+currencySymbol+")");
            }
            else {
                ReturnEntryView entry=new ReturnEntryView(context,(OrderEntry) getChild(i,j-1));
                entry.setData();
                childView=entry;
            }
            return childView;
        }
    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return false;
    }


}
