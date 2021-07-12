/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.umeng.analytics.social;

import android.util.Log;
import com.umeng.analytics.social.e;

public class b {
    public static void a(String string2, String string3) {
        if (e.v) {
            Log.i((String)string2, (String)string3);
        }
    }

    public static void a(String string2, String string3, Exception exception) {
        if (e.v) {
            Log.i((String)string2, (String)(exception.toString() + ":  [" + string3 + "]"));
        }
    }

    public static void b(String string2, String string3) {
        if (e.v) {
            Log.e((String)string2, (String)string3);
        }
    }

    public static void b(String string2, String stackTraceElementArray, Exception serializable2) {
        if (e.v) {
            Log.e((String)string2, (String)(((Throwable)serializable2).toString() + ":  [" + (String)stackTraceElementArray + "]"));
            for (StackTraceElement stackTraceElement : ((Throwable)serializable2).getStackTrace()) {
                Log.e((String)string2, (String)("        at\t " + stackTraceElement.toString()));
            }
        }
    }

    public static void c(String string2, String string3) {
        if (e.v) {
            Log.d((String)string2, (String)string3);
        }
    }

    public static void c(String string2, String string3, Exception exception) {
        if (e.v) {
            Log.d((String)string2, (String)(exception.toString() + ":  [" + string3 + "]"));
        }
    }

    public static void d(String string2, String string3) {
        if (e.v) {
            Log.v((String)string2, (String)string3);
        }
    }

    public static void d(String string2, String string3, Exception exception) {
        if (e.v) {
            Log.v((String)string2, (String)(exception.toString() + ":  [" + string3 + "]"));
        }
    }

    public static void e(String string2, String string3) {
        if (e.v) {
            Log.w((String)string2, (String)string3);
        }
    }

    public static void e(String string2, String stackTraceElementArray, Exception serializable2) {
        if (e.v) {
            Log.w((String)string2, (String)(((Throwable)serializable2).toString() + ":  [" + (String)stackTraceElementArray + "]"));
            for (StackTraceElement stackTraceElement : ((Throwable)serializable2).getStackTrace()) {
                Log.w((String)string2, (String)("        at\t " + stackTraceElement.toString()));
            }
        }
    }
}

