/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.umeng.analytics;

import android.content.Context;
import u.aly.ak;
import u.aly.as;
import u.aly.ax;
import u.aly.bj;

public class ReportPolicy {
    public static final int BATCH_AT_LAUNCH = 1;
    public static final int BATCH_BY_INTERVAL = 6;
    public static final int DAILY = 4;
    public static final int REALTIME = 0;
    public static final int SMART_POLICY = 8;
    public static final int WIFIONLY = 5;
    static final int a = 2;
    static final int b = 3;

    public static boolean a(int n2) {
        switch (n2) {
            default: {
                return false;
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 8: 
        }
        return true;
    }

    public static class a
    extends i {
        private final long a;
        private as b;

        public a(as as2) {
            this.a = 15000L;
            this.b = as2;
        }

        @Override
        public boolean a(boolean bl2) {
            return System.currentTimeMillis() - this.b.c >= 15000L;
        }
    }

    public static class b
    extends i {
        private ax a;
        private as b;

        public b(as as2, ax ax2) {
            this.b = as2;
            this.a = ax2;
        }

        @Override
        public boolean a() {
            return this.a.c();
        }

        @Override
        public boolean a(boolean bl2) {
            long l2;
            long l3 = System.currentTimeMillis();
            return l3 - this.b.c >= (l2 = this.a.a());
        }
    }

    public static class c
    extends i {
        private long a;
        private long b = 0L;

        public c(int n2) {
            this.a = n2;
            this.b = System.currentTimeMillis();
        }

        @Override
        public boolean a() {
            return System.currentTimeMillis() - this.b < this.a;
        }

        @Override
        public boolean a(boolean bl2) {
            return System.currentTimeMillis() - this.b >= this.a;
        }
    }

    public static class d
    extends i {
        @Override
        public boolean a(boolean bl2) {
            return bl2;
        }
    }

    public static class e
    extends i {
        private static long a = 90000L;
        private static long b = 86400000L;
        private long c;
        private as d;

        public e(as as2, long l2) {
            this.d = as2;
            this.a(l2);
        }

        public static boolean a(int n2) {
            return (long)n2 >= a;
        }

        public void a(long l2) {
            if (l2 >= a && l2 <= b) {
                this.c = l2;
                return;
            }
            this.c = a;
        }

        @Override
        public boolean a(boolean bl2) {
            return System.currentTimeMillis() - this.d.c >= this.c;
        }

        public long b() {
            return this.c;
        }
    }

    public static class f
    extends i {
        private final int a;
        private ak b;

        public f(ak ak2, int n2) {
            this.a = n2;
            this.b = ak2;
        }

        @Override
        public boolean a(boolean bl2) {
            return this.b.b() > this.a;
        }
    }

    public static class g
    extends i {
        private long a = 86400000L;
        private as b;

        public g(as as2) {
            this.b = as2;
        }

        @Override
        public boolean a(boolean bl2) {
            return System.currentTimeMillis() - this.b.c >= this.a;
        }
    }

    public static class h
    extends i {
        @Override
        public boolean a(boolean bl2) {
            return true;
        }
    }

    public static class i {
        public boolean a() {
            return true;
        }

        public boolean a(boolean bl2) {
            return true;
        }
    }

    public static class j
    extends i {
        private Context a = null;

        public j(Context context) {
            this.a = context;
        }

        @Override
        public boolean a(boolean bl2) {
            return bj.n(this.a);
        }
    }

    public static class k
    extends i {
        private final long a;
        private as b;

        public k(as as2) {
            this.a = 10800000L;
            this.b = as2;
        }

        @Override
        public boolean a(boolean bl2) {
            return System.currentTimeMillis() - this.b.c >= 10800000L;
        }
    }
}

