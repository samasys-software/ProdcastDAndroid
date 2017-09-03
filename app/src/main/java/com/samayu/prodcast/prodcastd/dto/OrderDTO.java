package com.samayu.prodcast.prodcastd.dto;

/**
 * Created by kathir on 8/30/2017.
 */

public class OrderDTO  extends ProdcastDTO{
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    private Order order ;
}
