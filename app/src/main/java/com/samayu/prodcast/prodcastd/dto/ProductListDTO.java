package com.samayu.prodcast.prodcastd.dto;

import java.util.List;

/**
 * Created by kathir on 8/24/2017.
 */

public class ProductListDTO extends ProdcastDTO {

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    private List<Product> productList;
}