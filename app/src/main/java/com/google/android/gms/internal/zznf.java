/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.ActivityManager
 *  android.app.ActivityManager$RunningAppProcessInfo
 *  android.content.Context
 *  android.os.Binder
 */
package com.google.android.gms.internal;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Binder;

public class zznf {
    private static String zza(StackTraceElement[] object, int n2) {
        if (n2 + 4 >= ((StackTraceElement[])object).length) {
            return "<bottom of call stack>";
        }
        object = object[n2 + 4];
        return ((StackTraceElement)object).getClassName() + "." + ((StackTraceElement)object).getMethodName() + ":" + ((StackTraceElement)object).getLineNumber();
    }

    public static String zzaz(Context context) {
        return zznf.zzi(context, Binder.getCallingPid());
    }

    public static String zzi(Context object, int n2) {
        if ((object = ((ActivityManager)object.getSystemService("activity")).getRunningAppProcesses()) != null) {
            object = object.iterator();
            while (object.hasNext()) {
                ActivityManager.RunningAppProcessInfo runningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)object.next();
                if (runningAppProcessInfo.pid != n2) continue;
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    public static String zzn(int n2, int n3) {
        StackTraceElement[] stackTraceElementArray = Thread.currentThread().getStackTrace();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = n2; i2 < n3 + n2; ++i2) {
            stringBuffer.append(zznf.zza(stackTraceElementArray, i2)).append(" ");
        }
        return stringBuffer.toString();
    }
}

