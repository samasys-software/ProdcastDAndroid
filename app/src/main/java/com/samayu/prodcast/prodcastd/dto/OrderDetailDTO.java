package com.samayu.prodcast.prodcastd.dto;

import java.util.List;

/**
 * Created by kathir on 8/24/2017.
 */

public class OrderDetailDTO extends ProdcastDTO{
    private long customerId;
    private long employeeId;
    private float paymentAmount;
    private String discountValue;
    private String discountType;
    private String paymentType;
    private String refNO;
    private String refDetail;
    private String orderStatus;

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    private List<OrderEntryDTO> entries;

    public String getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(String discountValue) {
        this.discountValue = discountValue;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public float getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(float paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }



    public List<OrderEntryDTO> getEntries() {
        return entries;
    }

    public void setEntries(List<OrderEntryDTO> entries) {
        this.entries = entries;
    }



    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getRefNO() {
        return refNO;
    }

    public void setRefNO(String refNO) {
        this.refNO = refNO;
    }

    public String getRefDetail() {
        return refDetail;
    }

    public void setRefDetail(String refDetail) {
        this.refDetail = refDetail;
    }
}
