package com.samayu.prodcast.prodcastd.dto;


/**
 * Created by kathir on 8/30/2017.
 */

import java.sql.Date;
import java.sql.Timestamp;


/**
 * Created by SamayuSoftcorp on 29-08-2017.
 */
public class Collection {

    private long billId;
    private float amountPaid;
    private long employeeId;
    private String employeeName;
    private String paymentType;
    private String refNo;
    private String refDetail;

    public Collection(){

    }
    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getRefDetail() {
        return refDetail;
    }

    public void setRefDetail(String refDetail) {
        this.refDetail = refDetail;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    private String customerName;

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public float getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(float amountPaid) {
        this.amountPaid = amountPaid;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(java.sql.Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    private Date paymentDate;
    private String entryTime;

}
