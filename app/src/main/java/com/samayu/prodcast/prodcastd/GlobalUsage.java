package com.samayu.prodcast.prodcastd.util;

import java.text.NumberFormat;

/**
 * Created by fgs on 9/18/2017.
 */

public class GlobalUsage {
    private static NumberFormat numberFormat=NumberFormat.getNumberInstance();

    public static NumberFormat getNumberFormat() {

        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);

        return numberFormat;
    }
}
