package com.samayu.prodcast.prodcastd.dto;

import java.util.List;

/**
 * Created by nandhini on 22/09/17.
 */

public class RegistrationDTO<T> extends ProdcastDTO{
    public List<Registration> getResult() {
        return result;
    }

    public void setResult(List<Registration> result) {
        this.result = result;
    }

    private List<Registration> result;
}
