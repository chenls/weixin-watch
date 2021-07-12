/*
 * Decompiled with CFR 0.151.
 */
package com.riyuxihe.weixinqingliao.util;

public class StringUtil {
    public static String filterHtml(String string2) {
        if (string2 == null || string2.isEmpty()) {
            return "";
        }
        return string2.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "").replaceAll("[(/>)<]", "");
    }

    public static boolean isNullOrEmpty(String string2) {
        return string2 == null || string2.isEmpty();
    }

    public static boolean notNullOrEmpty(String string2) {
        return string2 != null && !string2.isEmpty();
    }
}

