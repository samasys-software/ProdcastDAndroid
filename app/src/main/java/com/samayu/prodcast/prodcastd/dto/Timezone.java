package com.samayu.prodcast.prodcastd.dto;

/**
 * Created by nandhini on 8/15/17.
 */

public class Timezone {
    private long timezoneId;
    private String countryId,timezone;

    public long getTimezoneId() {
        return timezoneId;
    }

    public void setTimezoneId(long timezoneId) {
        this.timezoneId = timezoneId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
