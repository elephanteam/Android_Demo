package com.elephantgroup.one.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateFormatUtil {

    /**
     * Record application error log when used
     */
    public static String getSimpleDate() {
        Date currentDate;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss",Locale.getDefault());
        currentDate = Calendar.getInstance().getTime();
        return formatter.format(currentDate);
    }

    /**
     * System time is a wonderful work of 10 digits and 10 digits seconds value < / font >
     */
    public static String yMdHm(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm",Locale.getDefault());
        return format.format(time * 1000);
    }
}
