package com.shawn.jooo.framework.utils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author shawn
 */
public class DateTimeUtils {

    public static final String DATETIME_YMD = "yyyy-MM-dd";

    public static final String DATETIME_YMDH = "yyyy-MM-dd HH";

    public static final String DATETIME_YMDHM = "yyyy-MM-dd HH:mm";

    public static final String DATETIME_MDHM = "MM-dd HH:mm";

    public static final String DATETIME_YMDHMS = "yyyy-MM-dd HH:mm:ss";

    public static final String DATETIME_YMDHMSS_S = "yyyy-MM-dd HH:mm:ss.S";

    public static final String DATETIME_YMDHMSS_T = "yyyy-MM-dd'T'HH:mm:ssZ";

    public static final String DATETIME_HMS = "HH:mm:ss";

    public static final String DIGIT_YMDHMS = "yyyyMMddHHmmss";

    public static final String[] SHORT_CN_WEEK = {"日", "一", "二", "三", "四", "五", "六"};

    public static final String[] SHORT_EN_WEEK = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    public static final String[] EN_WEEK = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    public static final String[] SHORT_CN_MONTH = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};

    public static final String[] SHORT_EN_MONTH = {"Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec"};

    public static final String[] EN_MONTH = {"January", "February", "March", "April", "May", "June", "July", "Aguest", "September", "October", "November", "December"};


    public static String format(long millis, String pattern) {
        return DateFormatUtils.format(millis, pattern);
    }

    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    public static String format(Calendar calendar, String pattern) {
        return DateFormatUtils.format(calendar, pattern);
    }


    public static Long parseTime(String date, String pattern) {
        Date d;
        try {
            d = DateUtils.parseDate(date, pattern);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
        return d.getTime();
    }

}
