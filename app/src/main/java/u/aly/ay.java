/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package u.aly;

import android.content.Context;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.b;
import com.umeng.analytics.h;
import u.aly.ao;
import u.aly.as;
import u.aly.t;
import u.aly.x;

public class ay
implements ao {
    private static ay l = null;
    private final long a;
    private final long b;
    private final int c;
    private final int d;
    private h e;
    private as f;
    private long g = 1296000000L;
    private int h = 10000;
    private long i = 0L;
    private long j = 0L;
    private Context k;

    private ay(Context context, as as2) {
        this.a = 1296000000L;
        this.b = 129600000L;
        this.c = 1800000;
        this.d = 10000;
        this.k = context;
        this.e = com.umeng.analytics.h.a(context);
        this.f = as2;
    }

    public static ay a(Context object, as as2) {
        synchronized (ay.class) {
            if (l == null) {
                l = new ay((Context)object, as2);
                l.a(x.a(object).b());
            }
            object = l;
            return object;
        }
    }

    @Override
    public void a(x.a a2) {
        this.g = a2.a(1296000000L);
        int n2 = a2.b(0);
        if (n2 == 0) {
            if (AnalyticsConfig.sLatentWindow <= 0 || AnalyticsConfig.sLatentWindow > 1800000) {
                this.h = 10000;
                return;
            }
            this.h = AnalyticsConfig.sLatentWindow;
            return;
        }
        this.h = n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean a() {
        long l2;
        block5: {
            block4: {
                if (this.e.i() || this.f.f()) break block4;
                l2 = System.currentTimeMillis() - this.f.o();
                if (l2 > this.g) {
                    String string2 = t.a(this.k);
                    this.i = com.umeng.analytics.b.a(this.h, string2);
                    this.j = l2;
                    return true;
                }
                if (l2 > 129600000L) break block5;
            }
            return false;
        }
        this.i = 0L;
        this.j = l2;
        return true;
    }

    public long b() {
        return this.i;
    }

    public long c() {
        return this.j;
    }
}

