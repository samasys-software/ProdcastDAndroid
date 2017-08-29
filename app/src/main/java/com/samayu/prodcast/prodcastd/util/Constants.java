package com.samayu.prodcast.prodcastd.util;

import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;

/**
 * Created by kathir on 8/28/2017.
 */

public class Constants {

    public static final NumberFormat PRICE_FORMAT = NumberFormat.getInstance();

    static
    {
        PRICE_FORMAT.setMinimumFractionDigits(2);
    }
}
