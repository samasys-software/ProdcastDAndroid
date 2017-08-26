package prodcastd.prodcast.samayu.com.prodcastd;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.samayu.prodcast.prodcastd.dto.Collection;
import com.samayu.prodcast.prodcastd.dto.Order;
import com.samayu.prodcast.prodcastd.dto.ReportDTO;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;
import com.samayu.prodcast.prodcastd.ui.CollectionView;
import com.samayu.prodcast.prodcastd.ui.ReportSalesEntry;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReportTypeActivity extends ProdcastBaseActivity {
    Spinner selectReportDate;
    Button advancedReport;
    TextView sale;
    TextView collections;
    TextView balances;

    TextView customer;
    TextView dat;
    TextView total;
    TextView balanc;
    ListView salesList;


    ListView paymentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_type);
        selectReportDate = (Spinner) findViewById(R.id.selectReportDate);
        advancedReport = (Button) findViewById(R.id.advancedReport);
        sale = (TextView) findViewById(R.id.sale);
        collections = (TextView) findViewById(R.id.collections);
        balances = (TextView) findViewById(R.id.balances);

        customer = (TextView) findViewById(R.id.customer);
        dat = (TextView) findViewById(R.id.dat);
        total = (TextView) findViewById(R.id.total);
        balanc = (TextView) findViewById(R.id.balanc);

        salesList = (ListView) findViewById(R.id.salesList);
        paymentList = (ListView) findViewById(R.id.paymentList);

        ArrayAdapter reportDayAdapter = ArrayAdapter.createFromResource(this, R.array.select_report_day, R.layout.support_simple_spinner_dropdown_item);
        reportDayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selectReportDate.setAdapter(reportDayAdapter);


        selectReportDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        try {
            refreshReport();
        }
        catch(Exception er){
            er.printStackTrace();
        }

    }


    public void refreshReport(){

        long employeeId= 621;
        String reportType = selectReportDate.getSelectedItem().toString().toLowerCase();


        Call<ReportDTO> reportDTOCall = new ProdcastDClient().getClient().getReports(reportType , employeeId,null, null , null );
        reportDTOCall.enqueue(new Callback<ReportDTO>() {
            @Override
            public void onResponse(Call<ReportDTO> call, Response<ReportDTO> response) {
                if (response.isSuccessful()) {
                    ReportDTO reportDTO = response.body();
                    sale.setText(String.valueOf(reportDTO.getTotalSales() ));
                    collections.setText(String.valueOf( reportDTO.getTotalCollection()));
                    balances.setText(String.valueOf( reportDTO.getBalance()));

                  //  SalesEntryAdapter salesEntryAdapter = new SalesEntryAdapter(ReportTypeActivity.this, reportDTO.getOrders());
                    //salesEntryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                   // salesList.setAdapter(salesEntryAdapter);
                     CollectionViewAdapter collectionViewAdapter = new CollectionViewAdapter(ReportTypeActivity.this, reportDTO.getCollections());
                    paymentList.setAdapter(collectionViewAdapter);

                }
            }


            @Override
            public void onFailure(Call<ReportDTO> call, Throwable t) {
                t.printStackTrace(
                );
            }
        });

    }
    public static  class  SalesEntryAdapter extends ArrayAdapter<Order>{
        public SalesEntryAdapter(Context context, Order[] orders){
            super(context,R.layout.sample_report_sales_entry,orders);
        }
        public View getView(int position,View converView,ViewGroup parent){
            ReportSalesEntry reportSalesEntry = new ReportSalesEntry(getContext(),getItem(position));
            reportSalesEntry.setData();
            return reportSalesEntry;
        }
    }

    public static  class  CollectionViewAdapter extends ArrayAdapter<Collection>{
        public CollectionViewAdapter(Context context, Collection[] collections){
            super(context,R.layout.sample_report_sales_entry,collections);
        }
        public View getView(int position,View converView,ViewGroup parent){
            CollectionView collectionView= new CollectionView(getContext(),getItem(position));
            collectionView.setData();
            return collectionView;
        }
    }



}
