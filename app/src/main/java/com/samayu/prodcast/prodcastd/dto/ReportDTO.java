package com.samayu.prodcast.prodcastd.dto;




/**
 * Created by kdsdh on 8/24/2017.
 */

public class ReportDTO extends ProdcastDTO {

    private float totalSales;
    private float totalCollection;

    private String reportName;
    private float amount;
    private float outstandingBalance;
    private String reportDates;
    private String  customerName;

    public Collection[] getCollections() {
        return collections;
    }

    public void setCollections(Collection[] collections) {
        this.collections = collections;
    }

    private Collection[] collections;

    public Order[] getOrders() {
        return orders;
    }

    public void setOrders(Order[] orders) {
        this.orders = orders;
    }

    private Order[] orders;

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    private float balance;

    public ReportDTO(){

    }

    public float getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(float totalSales) {
        this.totalSales = totalSales;
    }

    public float getTotalCollection() {
        return totalCollection;
    }

    public void setTotalCollection(float totalCollection) {
        this.totalCollection = totalCollection;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
    public String getReportDates() {
        return reportDates;
    }

    public void setReportDates(String reportDates) {
        this.reportDates = reportDates;
    }
    public Object getTotalAmount()
    {
        return amount;
    }
    public Object setTotalAmount(float amount)
    {
        return  this.amount=amount;
    }
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public float getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(float outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }





}