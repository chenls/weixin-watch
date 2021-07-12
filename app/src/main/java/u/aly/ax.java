/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package u.aly;

import android.content.Context;
import u.aly.ao;
import u.aly.ar;
import u.aly.av;
import u.aly.x;

public class ax
implements ao {
    private static final int a = 0;
    private static final int b = 1;
    private static final int c = 2;
    private static final int d = 3;
    private static final long e = 14400000L;
    private static final long f = 28800000L;
    private static final long g = 86400000L;
    private static ax j = null;
    private int h = 0;
    private final long i;

    private ax() {
        this.i = 60000L;
    }

    public static ax a(Context object) {
        synchronized (ax.class) {
            if (j == null) {
                j = new ax();
                int n2 = x.a(object).b().a(0);
                j.a(n2);
            }
            object = j;
            return object;
        }
    }

    public long a() {
        switch (this.h) {
            default: {
                return 0L;
            }
            case 1: {
                return 14400000L;
            }
            case 2: {
                return 28800000L;
            }
            case 3: 
        }
        return 86400000L;
    }

    public void a(int n2) {
        if (n2 >= 0 && n2 <= 3) {
            this.h = n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void a(av av2, Context context) {
        if (this.h == 1) {
            av2.b.i = null;
            av2.b.a = null;
            av2.b.b = null;
            av2.b.h = null;
            return;
        } else {
            if (this.h == 2) {
                av2.b.c.clear();
                av2.b.c.add(this.b(context));
                av2.b.i = null;
                av2.b.a = null;
                av2.b.b = null;
                av2.b.h = null;
                return;
            }
            if (this.h != 3) return;
            av2.b.c = null;
            av2.b.i = null;
            av2.b.a = null;
            av2.b.b = null;
            av2.b.h = null;
            return;
        }
    }

    @Override
    public void a(x.a a2) {
        this.a(a2.a(0));
    }

    public long b() {
        if (this.h == 0) {
            return 0L;
        }
        return 300000L;
    }

    public av.o b(Context context) {
        long l2;
        av.o o2 = new av.o();
        o2.b = ar.g(context);
        o2.c = l2 = System.currentTimeMillis();
        o2.d = l2 + 60000L;
        o2.e = 60000L;
        return o2;
    }

    public boolean c() {
        return this.h != 0;
    }
}

