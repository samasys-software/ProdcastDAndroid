package com.samayu.prodcast.prodcastd.dto;

/**
 * Created by nandhini on 8/15/17.
 */

public class CustomerDTO extends ProdcastDTO{
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    private Customer customer;
}
