package com.samayu.prodcast.prodcastd.dto;

import com.samayu.prodcast.prodcastd.dto.ProdcastDTO;

/**
 * Created by nandhini on 8/15/17.
 */

public class AdminDTO<T> extends ProdcastDTO {

    private T result;
    private String successMessage;

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public T getResult(){
        return result;
    }

    public void setResult(T result){
        this.result = result;
    }
}
