/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package u.aly;

import android.content.Context;
import com.umeng.analytics.h;
import u.aly.aj;
import u.aly.ap;
import u.aly.av;

public class as
implements aj {
    private static final String h = "successful_request";
    private static final String i = "failed_requests ";
    private static final String j = "last_request_spent_ms";
    private static final String k = "last_request_time";
    private static final String l = "first_activate_time";
    private static final String m = "last_req";
    public int a;
    public int b;
    public long c;
    private final int d;
    private int e;
    private long f = 0L;
    private long g = 0L;
    private Context n;

    public as(Context context) {
        this.d = 3600000;
        this.a(context);
    }

    private void a(Context context) {
        this.n = context.getApplicationContext();
        context = ap.a(context);
        this.a = context.getInt(h, 0);
        this.b = context.getInt(i, 0);
        this.e = context.getInt(j, 0);
        this.c = context.getLong(k, 0L);
        this.f = context.getLong(m, 0L);
    }

    public static void a(Context context, av av2) {
        context = ap.a(context);
        av2.a.L = context.getInt(i, 0);
        av2.a.K = context.getInt(h, 0);
        av2.a.M = context.getInt(j, 0);
    }

    @Override
    public void a() {
        this.i();
    }

    @Override
    public void b() {
        this.j();
    }

    @Override
    public void c() {
        this.g();
    }

    @Override
    public void d() {
        this.h();
    }

    public int e() {
        if (this.e > 3600000) {
            return 3600000;
        }
        return this.e;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean f() {
        boolean bl2 = this.c == 0L;
        boolean bl3 = !com.umeng.analytics.h.a(this.n).i();
        return bl2 && bl3;
    }

    public void g() {
        ++this.a;
        this.c = this.f;
    }

    public void h() {
        ++this.b;
    }

    public void i() {
        this.f = System.currentTimeMillis();
    }

    public void j() {
        this.e = (int)(System.currentTimeMillis() - this.f);
    }

    public void k() {
        ap.a(this.n).edit().putInt(h, this.a).putInt(i, this.b).putInt(j, this.e).putLong(k, this.c).putLong(m, this.f).commit();
    }

    public void l() {
        ap.a(this.n).edit().putLong(l, System.currentTimeMillis()).commit();
    }

    public boolean m() {
        if (this.g == 0L) {
            this.g = ap.a(this.n).getLong(l, 0L);
        }
        return this.g == 0L;
    }

    public long n() {
        if (this.m()) {
            return System.currentTimeMillis();
        }
        return this.g;
    }

    public long o() {
        return this.f;
    }
}

