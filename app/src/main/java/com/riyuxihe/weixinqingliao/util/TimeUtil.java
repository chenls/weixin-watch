/*
 * Decompiled with CFR 0.151.
 */
package com.riyuxihe.weixinqingliao.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

    public static String dateToStr(Date date) {
        return simpleDateFormat.format(date);
    }

    public static String getDate() {
        return TimeUtil.dateToStr(new Date(System.currentTimeMillis()));
    }

    public static void main(String[] stringArray) {
        TimeUtil.getDate();
    }

    public static String timeToStr(long l2) {
        return simpleDateFormat.format(new Date(l2));
    }

    public static long toCeilSecondsFromMillis(long l2) {
        return (long)Math.ceil((double)l2 / 1000.0);
    }

    public static long toTimeMillis(long l2) {
        return 1000L * l2;
    }
}

