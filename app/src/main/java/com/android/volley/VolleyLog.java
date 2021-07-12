/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 *  android.util.Log
 */
package com.android.volley;

import android.os.SystemClock;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VolleyLog {
    public static boolean DEBUG;
    public static String TAG;

    static {
        TAG = "Volley";
        DEBUG = Log.isLoggable((String)TAG, (int)2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String buildMessage(String string2, Object ... object) {
        if (object != null) {
            string2 = String.format(Locale.US, string2, (Object[])object);
        }
        StackTraceElement[] stackTraceElementArray = new Throwable().fillInStackTrace().getStackTrace();
        String string3 = "<unknown>";
        int n2 = 2;
        while (true) {
            block6: {
                block5: {
                    object = string3;
                    if (n2 >= stackTraceElementArray.length) break block5;
                    if (stackTraceElementArray[n2].getClass().equals(VolleyLog.class)) break block6;
                    object = stackTraceElementArray[n2].getClassName();
                    object = ((String)object).substring(((String)object).lastIndexOf(46) + 1);
                    object = ((String)object).substring(((String)object).lastIndexOf(36) + 1);
                    object = (String)object + "." + stackTraceElementArray[n2].getMethodName();
                }
                return String.format(Locale.US, "[%d] %s: %s", Thread.currentThread().getId(), object, string2);
            }
            ++n2;
        }
    }

    public static void d(String string2, Object ... objectArray) {
        Log.d((String)TAG, (String)VolleyLog.buildMessage(string2, objectArray));
    }

    public static void e(String string2, Object ... objectArray) {
        Log.e((String)TAG, (String)VolleyLog.buildMessage(string2, objectArray));
    }

    public static void e(Throwable throwable, String string2, Object ... objectArray) {
        Log.e((String)TAG, (String)VolleyLog.buildMessage(string2, objectArray), (Throwable)throwable);
    }

    public static void setTag(String string2) {
        VolleyLog.d("Changing log tag to %s", string2);
        TAG = string2;
        DEBUG = Log.isLoggable((String)TAG, (int)2);
    }

    public static void v(String string2, Object ... objectArray) {
        if (DEBUG) {
            Log.v((String)TAG, (String)VolleyLog.buildMessage(string2, objectArray));
        }
    }

    public static void wtf(String string2, Object ... objectArray) {
        Log.wtf((String)TAG, (String)VolleyLog.buildMessage(string2, objectArray));
    }

    public static void wtf(Throwable throwable, String string2, Object ... objectArray) {
        Log.wtf((String)TAG, (String)VolleyLog.buildMessage(string2, objectArray), (Throwable)throwable);
    }

    static class MarkerLog {
        public static final boolean ENABLED = DEBUG;
        private static final long MIN_DURATION_FOR_LOGGING_MS = 0L;
        private boolean mFinished = false;
        private final List<Marker> mMarkers = new ArrayList<Marker>();

        MarkerLog() {
        }

        private long getTotalDuration() {
            if (this.mMarkers.size() == 0) {
                return 0L;
            }
            long l2 = this.mMarkers.get((int)0).time;
            return this.mMarkers.get((int)(this.mMarkers.size() - 1)).time - l2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void add(String string2, long l2) {
            synchronized (this) {
                if (this.mFinished) {
                    throw new IllegalStateException("Marker added to finished log");
                }
                this.mMarkers.add(new Marker(string2, l2, SystemClock.elapsedRealtime()));
                return;
            }
        }

        protected void finalize() throws Throwable {
            if (!this.mFinished) {
                this.finish("Request on the loose");
                VolleyLog.e("Marker log finalized without finish() - uncaught exit point for request", new Object[0]);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void finish(String iterator) {
            synchronized (this) {
                this.mFinished = true;
                long l2 = this.getTotalDuration();
                if (l2 > 0L) {
                    long l3 = this.mMarkers.get((int)0).time;
                    VolleyLog.d("(%-4d ms) %s", l2, iterator);
                    for (Marker marker : this.mMarkers) {
                        l2 = marker.time;
                        VolleyLog.d("(+%-4d) [%2d] %s", l2 - l3, marker.thread, marker.name);
                        l3 = l2;
                    }
                }
                return;
            }
        }

        private static class Marker {
            public final String name;
            public final long thread;
            public final long time;

            public Marker(String string2, long l2, long l3) {
                this.name = string2;
                this.thread = l2;
                this.time = l3;
            }
        }
    }
}

