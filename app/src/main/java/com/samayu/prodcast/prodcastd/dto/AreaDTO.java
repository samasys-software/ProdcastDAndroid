package com.samayu.prodcast.prodcastd.dto;

import java.util.List;

/**
 * Created by nandhini on 8/15/17.
 */

public class AreaDTO extends ProdcastDTO{

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    private List<Area> areas;
}
