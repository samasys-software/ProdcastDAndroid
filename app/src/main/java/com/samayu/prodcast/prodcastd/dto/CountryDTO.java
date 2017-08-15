package com.samayu.prodcast.prodcastd.dto;

import java.util.List;

/**
 * Created by nandhini on 8/15/17.
 */

public class CountryDTO extends ProdcastDTO {
    private List<Country> result;
    private List<Timezone> timezones;

    public List<Country> getResult() {
        return result;
    }

    public void setResult(List<Country> result) {
        this.result = result;
    }

    public List<Timezone> getTimezones() {
        return timezones;
    }

    public void setTimezones(List<Timezone> timezones) {
        this.timezones = timezones;
    }
}