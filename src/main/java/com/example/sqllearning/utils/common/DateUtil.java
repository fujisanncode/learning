package com.example.sqllearning.utils.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtil {

    public final static String dateFormat1 = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static Date parse(String dateStr, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            log.error("date {} parse error {}", dateStr, e);
        }
        return date;
    }
}
