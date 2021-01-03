package com.shawn.jooo.generator.utils;

import java.text.DateFormat;
import java.util.Date;

/**
 *
 * @author shawn
 */
public class DateUtils {
    public static String now() {
        return DateFormat.getDateTimeInstance().format(new Date());
    }
}
