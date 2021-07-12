package com.riyuxihe.weixinqingliao.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

    public static String timeToStr(long time) {
        return simpleDateFormat.format(new Date(time));
    }

    public static String dateToStr(Date data) {
        return simpleDateFormat.format(data);
    }

    public static String getDate() {
        return dateToStr(new Date(System.currentTimeMillis()));
    }

    public static long toCeilSecondsFromMillis(long millis) {
        return (long) Math.ceil(((double) millis) / 1000.0d);
    }

    public static long toTimeMillis(long times) {
        return 1000 * times;
    }

    public static void main(String[] args) {
        getDate();
    }
}
