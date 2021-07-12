/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.umeng.analytics.game;

import android.content.Context;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.game.c;
import com.umeng.analytics.social.UMPlatformData;
import com.umeng.analytics.social.UMSocialService;
import com.umeng.analytics.social.e;
import u.aly.bl;

public class UMGameAgent
extends MobclickAgent {
    private static final String a = "Input string is null or empty";
    private static final String b = "Input string must be less than 64 chars";
    private static final String c = "Input value type is negative";
    private static final String d = "The int value for 'Pay Channels' ranges between 1 ~ 99 ";
    private static final c e = new c();
    private static Context f;

    /*
     * Enabled aggressive block sorting
     */
    private static boolean a(String string2) {
        return string2 == null || string2.trim().length() <= 0;
    }

    public static void bonus(double d2, int n2) {
        if (d2 < 0.0) {
            bl.e(c);
            return;
        }
        if (n2 <= 0 || n2 >= 100) {
            bl.e(d);
            return;
        }
        e.a(d2, n2);
    }

    public static void bonus(String string2, int n2, double d2, int n3) {
        if (UMGameAgent.a(string2)) {
            bl.e(a);
            return;
        }
        if (n2 < 0 || d2 < 0.0) {
            bl.e(c);
            return;
        }
        if (n3 <= 0 || n3 >= 100) {
            bl.e(d);
            return;
        }
        e.a(string2, n2, d2, n3);
    }

    public static void buy(String string2, int n2, double d2) {
        if (UMGameAgent.a(string2)) {
            bl.e(a);
            return;
        }
        if (n2 < 0 || d2 < 0.0) {
            bl.e(c);
            return;
        }
        e.a(string2, n2, d2);
    }

    public static void exchange(double d2, String string2, double d3, int n2, String string3) {
        if (d2 < 0.0 || d3 < 0.0) {
            bl.e(c);
            return;
        }
        if (n2 <= 0 || n2 >= 100) {
            bl.e(d);
            return;
        }
        e.a(d2, string2, d3, n2, string3);
    }

    public static void failLevel(String string2) {
        if (UMGameAgent.a(string2)) {
            bl.e(a);
            return;
        }
        if (string2.length() > 64) {
            bl.e(b);
            return;
        }
        e.d(string2);
    }

    public static void finishLevel(String string2) {
        if (UMGameAgent.a(string2)) {
            bl.e(a);
            return;
        }
        if (string2.length() > 64) {
            bl.e(b);
            return;
        }
        e.c(string2);
    }

    public static void init(Context context) {
        e.a(context);
        f = context.getApplicationContext();
    }

    public static void onEvent(String string2, String string3) {
        UMGameAgent.onEvent(f, string2, string3);
    }

    public static void onSocialEvent(Context context, String string2, UMPlatformData ... uMPlatformDataArray) {
        if (context == null) {
            bl.e("context is null in onShareEvent");
            return;
        }
        com.umeng.analytics.social.e.e = "4";
        UMSocialService.share(context, string2, uMPlatformDataArray);
    }

    public static void onSocialEvent(Context context, UMPlatformData ... uMPlatformDataArray) {
        if (context == null) {
            bl.e("context is null in onShareEvent");
            return;
        }
        com.umeng.analytics.social.e.e = "4";
        UMSocialService.share(context, uMPlatformDataArray);
    }

    public static void pay(double d2, double d3, int n2) {
        if (n2 <= 0 || n2 >= 100) {
            bl.e(d);
            return;
        }
        if (d2 < 0.0 || d3 < 0.0) {
            bl.e(c);
            return;
        }
        e.a(d2, d3, n2);
    }

    public static void pay(double d2, String string2, int n2, double d3, int n3) {
        if (n3 <= 0 || n3 >= 100) {
            bl.e(d);
            return;
        }
        if (d2 < 0.0 || n2 < 0 || d3 < 0.0) {
            bl.e(c);
            return;
        }
        if (UMGameAgent.a(string2)) {
            bl.e(a);
            return;
        }
        e.a(d2, string2, n2, d3, n3);
    }

    public static void setPlayerLevel(int n2) {
        e.a(String.valueOf(n2));
    }

    public static void setTraceSleepTime(boolean bl2) {
        e.a(bl2);
    }

    public static void startLevel(String string2) {
        if (UMGameAgent.a(string2)) {
            bl.e(a);
            return;
        }
        if (string2.length() > 64) {
            bl.e(b);
            return;
        }
        e.b(string2);
    }

    public static void use(String string2, int n2, double d2) {
        if (UMGameAgent.a(string2)) {
            bl.e(a);
            return;
        }
        if (n2 < 0 || d2 < 0.0) {
            bl.e(c);
            return;
        }
        e.b(string2, n2, d2);
    }
}

