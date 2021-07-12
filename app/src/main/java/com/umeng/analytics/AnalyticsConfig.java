/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 */
package com.umeng.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.h;
import u.aly.bj;
import u.aly.bl;

public class AnalyticsConfig {
    public static boolean ACTIVITY_DURATION_OPEN;
    public static boolean CATCH_EXCEPTION;
    public static boolean COMPRESS_DATA;
    public static boolean ENABLE_MEMORY_BUFFER;
    public static final boolean FLAG_INTERNATIONAL = false;
    public static String GPU_RENDERER;
    public static String GPU_VENDER;
    static double[] a;
    private static String b;
    private static String c;
    private static String d;
    private static int e;
    public static long kContinueSessionMillis;
    public static String mWrapperType;
    public static String mWrapperVersion;
    public static boolean sEncrypt;
    public static int sLatentWindow;

    static {
        b = null;
        c = null;
        d = null;
        mWrapperType = null;
        mWrapperVersion = null;
        e = 0;
        GPU_VENDER = "";
        GPU_RENDERER = "";
        ACTIVITY_DURATION_OPEN = true;
        COMPRESS_DATA = true;
        ENABLE_MEMORY_BUFFER = true;
        CATCH_EXCEPTION = true;
        kContinueSessionMillis = 30000L;
        sEncrypt = false;
        a = null;
        sEncrypt = false;
    }

    static void a(Context context, int n2) {
        e = n2;
        h.a(context).a(e);
    }

    /*
     * Enabled aggressive block sorting
     */
    static void a(Context context, String string2) {
        if (context == null) {
            b = string2;
            return;
        }
        String string3 = bj.s(context);
        if (!TextUtils.isEmpty((CharSequence)string3)) {
            b = string3;
            if (string3.equals(string2)) return;
            bl.d("Appkey\u548cAndroidManifest.xml\u4e2d\u914d\u7f6e\u7684\u4e0d\u4e00\u81f4 ");
            return;
        }
        string3 = h.a(context).c();
        if (!TextUtils.isEmpty((CharSequence)string3)) {
            if (!string3.equals(string2)) {
                bl.d("Appkey\u548c\u4e0a\u6b21\u914d\u7f6e\u7684\u4e0d\u4e00\u81f4 ");
                h.a(context).a(string2);
            }
        } else {
            h.a(context).a(string2);
        }
        b = string2;
    }

    static void a(String string2) {
        c = string2;
    }

    static void a(boolean bl2) {
        sEncrypt = bl2;
    }

    static void b(Context context, String string2) {
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            d = string2;
            h.a(context).c(d);
        }
    }

    public static String getAppkey(Context context) {
        if (TextUtils.isEmpty((CharSequence)b) && TextUtils.isEmpty((CharSequence)(b = bj.s(context)))) {
            b = h.a(context).c();
        }
        return b;
    }

    public static String getChannel(Context context) {
        if (TextUtils.isEmpty((CharSequence)c)) {
            c = bj.y(context);
        }
        return c;
    }

    public static double[] getLocation() {
        return a;
    }

    public static String getSDKVersion(Context context) {
        return "6.0.1";
    }

    public static String getSecretKey(Context context) {
        if (TextUtils.isEmpty((CharSequence)d)) {
            d = h.a(context).e();
        }
        return d;
    }

    public static int getVerticalType(Context context) {
        if (e == 0) {
            e = h.a(context).f();
        }
        return e;
    }
}

