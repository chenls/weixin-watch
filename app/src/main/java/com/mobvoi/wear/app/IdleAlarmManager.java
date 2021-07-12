/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.AlarmManager
 *  android.app.PendingIntent
 *  android.util.Log
 */
package com.mobvoi.wear.app;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class IdleAlarmManager {
    private static Method a;
    private static Method b;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Method a() {
        if (a != null) return a;
        try {
            a = AlarmManager.class.getMethod("setAndAllowWhileIdle", Integer.TYPE, Long.TYPE, PendingIntent.class);
            return a;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            a = null;
            Log.w((String)"IdleAlarmManager", (String)"current sdk version may lower than '23'");
            return a;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Method b() {
        if (b != null) return b;
        try {
            b = AlarmManager.class.getMethod("setExactAndAllowWhileIdle", Integer.TYPE, Long.TYPE, PendingIntent.class);
            return b;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            b = null;
            Log.w((String)"IdleAlarmManager", (String)"current sdk version may lower than '23'");
            return b;
        }
    }

    /*
     * Unable to fully structure code
     */
    @SuppressLint(value={"NewApi"})
    public static void setAndAllowWhileIdle(AlarmManager var0, int var1_1, long var2_2, PendingIntent var4_3) {
        try {
            IdleAlarmManager.a().invoke(var0, new Object[]{var1_1, var2_2, var4_3});
            return;
        }
        catch (NullPointerException var5_4) lbl-1000:
        // 3 sources

        {
            while (true) {
                var0.set(var1_1, var2_2, var4_3);
                return;
            }
        }
        catch (IllegalAccessException var5_5) {
            ** GOTO lbl-1000
        }
        catch (InvocationTargetException var5_6) {
            ** continue;
        }
    }

    /*
     * Unable to fully structure code
     */
    @SuppressLint(value={"NewApi"})
    public static void setExactAndAllowWhileIdle(AlarmManager var0, int var1_1, long var2_2, PendingIntent var4_3) {
        try {
            IdleAlarmManager.b().invoke(var0, new Object[]{var1_1, var2_2, var4_3});
            return;
        }
        catch (NullPointerException var5_4) lbl-1000:
        // 3 sources

        {
            while (true) {
                var0.setExact(var1_1, var2_2, var4_3);
                return;
            }
        }
        catch (IllegalAccessException var5_5) {
            ** GOTO lbl-1000
        }
        catch (InvocationTargetException var5_6) {
            ** continue;
        }
    }
}

