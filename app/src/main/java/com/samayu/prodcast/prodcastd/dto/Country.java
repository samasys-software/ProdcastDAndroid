package com.samayu.prodcast.prodcastd.dto;

/**
 * Created by nandhini on 8/15/17.
 */

public class Country {
    private String countryId,countryName,currecyId,currencyName;

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCurrecyId() {
        return currecyId;
    }

    public void setCurrecyId(String currecyId) {
        this.currecyId = currecyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
}