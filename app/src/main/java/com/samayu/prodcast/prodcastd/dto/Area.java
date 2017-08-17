package com.samayu.prodcast.prodcastd.dto;

/**
 * Created by nandhini on 8/15/17.
 */

public class Area {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;
    public String toString(){
        return description;
    }

}
