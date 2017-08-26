package com.samayu.prodcast.prodcastd;

import com.samayu.prodcast.prodcastd.dto.Bill;
import com.samayu.prodcast.prodcastd.dto.Customer;
import com.samayu.prodcast.prodcastd.dto.Employee;
import com.samayu.prodcast.prodcastd.dto.OrderEntry;
import com.samayu.prodcast.prodcastd.dto.Product;

import java.util.List;

/**
 * Created by sarathan732 on 8/11/2017.
 */

public class SessionInfo {

    private Employee employee;

    private List<OrderEntry> cart;

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    private float total;
    private Customer selectedCustomer;

    public List<OrderEntry> getCart() {
        return cart;
    }

    public void setCart(List<OrderEntry> cart) {
        this.cart = cart;
    }

    private List<Customer> customerList;

    public List<Bill> getOutStandingBills() {
        return outStandingBills;
    }

    public void setOutStandingBills(List<Bill> outStandingBills) {
        this.outStandingBills = outStandingBills;
    }

    public List<Bill> getCustomerBills() {
        return customerBills;
    }

    public void setCustomerBills(List<Bill> customerBills) {
        this.customerBills = customerBills;
    }

    private List<Bill> customerBills;

    private List<Bill> outStandingBills;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    private List<Product> productList;

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    private static SessionInfo sessionInfo = new SessionInfo();

    public static SessionInfo instance(){
        return sessionInfo;
    }

    public void destroy(){
        sessionInfo = new SessionInfo();
    }



}
