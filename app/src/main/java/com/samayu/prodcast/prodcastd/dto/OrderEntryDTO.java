package com.samayu.prodcast.prodcastd.dto;

/**
 * Created by kathir on 8/24/2017.
 */

public class OrderEntryDTO extends ProdcastDTO {
    private long productId;
    private int quantity;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



}
