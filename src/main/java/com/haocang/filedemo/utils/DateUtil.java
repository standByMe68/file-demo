package com.haocang.filedemo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final String DEFAULT_YMD = "yyyy-MM-dd";
    private static final String DEFAULT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    private static final String NAME_YMD = "yyyyMMdd";

    public static String getYMD() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(NAME_YMD);
        return simpleDateFormat.format(new Date());
    }

    public static String getYMD(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

    public static String getComplateDate(String hmsss) {
        return getYMD(DEFAULT_YMD)+" "+hmsss;
    }

}
