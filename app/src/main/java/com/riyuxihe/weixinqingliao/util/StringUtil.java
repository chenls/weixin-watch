package com.riyuxihe.weixinqingliao.util;

public class StringUtil {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean notNullOrEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    public static String filterHtml(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        return input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "").replaceAll("[(/>)<]", "");
    }
}
