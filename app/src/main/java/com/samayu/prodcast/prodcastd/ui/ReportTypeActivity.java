package com.samayu.prodcast.prodcastd.ui;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;


import com.samayu.prodcast.prodcastd.dto.ReportDTO;
import com.samayu.prodcast.prodcastd.service.ProdcastDClient;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import prodcastd.prodcast.samayu.com.prodcastd.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class ReportTypeActivity extends ProdcastBaseActivity  {
    Spinner selectReportDate;
    Button refresh;
    TextView sale;
    TextView collections;
    TextView balances;

    TextView sCustomer;
    TextView sDate;
    TextView sTotal;
    TextView sBalance;

    DatePicker startDatePicker = null;
    DatePicker endDatePicker = null;

    ExpandableListView reportLists;
    private TextView startDate;
    private TextView endDate;
    View.OnKeyListener listener = null;
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_type);

        selectReportDate = (Spinner) findViewById(R.id.selectReportDate);

        refresh = (Button) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshReport();
            }
        });

        sale = (TextView) findViewById(R.id.sale);
        collections = (TextView) findViewById(R.id.collections);
        balances = (TextView) findViewById(R.id.balances);
        reportLists = (ExpandableListView)findViewById(
                R.id.expandableSalesList
        ) ;
        sCustomer = (TextView) findViewById(R.id.sCustomer);
        sDate = (TextView) findViewById(R.id.sDate);
        sTotal = (TextView) findViewById(R.id.sTotal);
        sBalance = (TextView) findViewById(R.id.saleBalance);
        startDate = (TextView)findViewById(R.id.startDate);
        endDate = (TextView)findViewById(R.id.endDate);
        startDatePicker = (DatePicker)findViewById(R.id.startDatePicker);
        endDatePicker = (DatePicker) findViewById(R.id.endDatePicker);

        listener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                startDate.setOnKeyListener(null);
                findViewById(R.id.startDate).setVisibility(View.GONE);
                findViewById(R.id.startDatePicker).setVisibility(View.GONE);
                findViewById(R.id.endDate).setVisibility(View.GONE);
                findViewById(R.id.endDatePicker).setVisibility(View.GONE);
                return false;
            }
        };

        ArrayAdapter reportDayAdapter = ArrayAdapter.createFromResource(this, R.array.select_report_day, R.layout.support_simple_spinner_dropdown_item);
        reportDayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selectReportDate.setAdapter(reportDayAdapter);

        startDatePicker = ((DatePicker)findViewById(R.id.startDatePicker));
        endDatePicker = ((DatePicker)findViewById(R.id.endDatePicker));

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDatePicker.setVisibility(View.VISIBLE);
            }
        });
        startDatePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                startDate.setText( datePicker.getDayOfMonth()+"/"+(datePicker.getMonth()+1)+"/"+datePicker.getYear());
                datePicker.setVisibility(View.GONE);
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endDatePicker.setVisibility(View.VISIBLE);
            }
        });

        endDatePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                endDate.setText( datePicker.getDayOfMonth()+"/"+(datePicker.getMonth()+1)+"/"+datePicker.getYear());
                datePicker.setVisibility(View.GONE);
            }
        });




        selectReportDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i<=2){
                    refreshReport();
                }



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

    @Override
    public String getProdcastTitle() {
        return "Reports";
    }

    public boolean  dateValidate (){
        boolean cancel = false;
        startDate.setError(null);
        endDate.setError(null);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String strStartDate = startDate.getText().toString();
        String strEndDate = endDate.getText().toString();

        try {
            Date dtStartDate = dateFormat.parse(strStartDate);
            Date dtEndDate = dateFormat.parse(strEndDate);

            if( dtStartDate.after( dtEndDate )){
                startDate.setError("Start Date cannot be after end Date");
                cancel = true;
            }

            if( dtEndDate.after(new Date())){
                endDate.setError("End Date cannot be after current Date");
                cancel = true;
            }
        }
        catch(ParseException er){
            startDate.setError("Please select a Date");
            endDate.setError("Please select a Date");
            cancel = true;
        }

return cancel;

    }


    public void refreshReport(){
        //SessionInfo.instance().getEmployee().getEmployeeId()
        long employeeId= 621;
        String reportType = selectReportDate.getSelectedItem().toString().toLowerCase();

        if(selectReportDate.getSelectedItemPosition()==3){
            if(dateValidate())
                return;
            reportType = "custom";

        }

        progress = ProgressDialog.show(ReportTypeActivity.this,"In Progress","One moment Please....",true);

        Call<ReportDTO> reportDTOCall = new ProdcastDClient().getClient().getReports(reportType , employeeId,null, startDate.getText().toString() , endDate.getText().toString() );

        reportDTOCall.enqueue(new Callback<ReportDTO>() {
            @Override
            public void onResponse(Call<ReportDTO> call, Response<ReportDTO> response) {
                if (response.isSuccessful()) {
                    progress.dismiss();
                    ReportDTO reportDTO = response.body();
                    sale.setText(String.valueOf(reportDTO.getTotalSales() ));
                    collections.setText(String.valueOf( reportDTO.getTotalCollection()));
                    balances.setText(String.valueOf( reportDTO.getBalance()));
                    List<String> titles = new LinkedList<String>();
                    titles.add("Sales Details");
                    titles.add("Payement Details");

                    reportLists.setAdapter(
                            new ReportExpandableListViewAdapter(
                                    ReportTypeActivity.this , titles , reportDTO.getOrders() , reportDTO.getCollections()
                            )
                    );
                    reportLists.expandGroup(0);
                    reportLists.expandGroup(1);

                }
            }


            @Override
            public void onFailure(Call<ReportDTO> call, Throwable t) {
                t.printStackTrace(
                );
            }
        });

    }


}
