package com.samayu.prodcast.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kathir on 8/17/2017.
 */

public class EmailVerification  {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private static Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private static Matcher matcher;

    public static boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
