/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.Base64
 */
package u.aly;

import android.content.Context;
import android.util.Base64;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.b;
import com.umeng.analytics.h;
import java.io.File;
import java.io.FileInputStream;
import u.aly.al;
import u.aly.ao;
import u.aly.as;
import u.aly.av;
import u.aly.bf;
import u.aly.bk;
import u.aly.bl;
import u.aly.bp;
import u.aly.bs;
import u.aly.by;
import u.aly.ch;
import u.aly.t;
import u.aly.v;
import u.aly.x;

public class aq {
    private static final int a = 1;
    private static final int b = 2;
    private static final int c = 3;
    private static Context g;
    private v d;
    private x e;
    private final int f;
    private as h;
    private al i;
    private av j;
    private boolean k = false;
    private boolean l;

    public aq(Context context, as as2) {
        this.f = 1;
        this.d = v.a(context);
        this.e = x.a(context);
        g = context;
        this.h = as2;
        this.i = new al(context);
        this.i.a(this.h);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int a(byte[] byArray) {
        bf bf2 = new bf();
        bs bs2 = new bs(new ch.a());
        try {
            bs2.a((bp)bf2, byArray);
            if (bf2.a == 1) {
                this.e.b(bf2.i());
                this.e.d();
            }
            bl.c("send log:" + bf2.f());
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (bf2.a == 1) {
            return 2;
        }
        return 3;
    }

    private void b() {
        com.umeng.analytics.h.a(g).j().a(new h.b(){

            @Override
            public void a(File file) {
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean b(File object) {
                void var1_2;
                FileInputStream fileInputStream;
                block8: {
                    try {
                        fileInputStream = new FileInputStream((File)object);
                    }
                    catch (Throwable throwable) {
                        fileInputStream = null;
                        break block8;
                    }
                    try {
                        object = bk.b(fileInputStream);
                    }
                    catch (Throwable throwable) {
                        break block8;
                    }
                    try {}
                    catch (Exception exception) {
                        return false;
                    }
                    bk.c(fileInputStream);
                    object = aq.this.i.a((byte[])object);
                    int n2 = object == null ? 1 : aq.this.a((byte[])object);
                    if (n2 == 2 && aq.this.h.m()) {
                        aq.this.h.l();
                    }
                    if (aq.this.l) {
                        return true;
                    }
                    return n2 != 1;
                }
                bk.c(fileInputStream);
                throw var1_2;
            }

            @Override
            public void c(File file) {
                aq.this.h.k();
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void c() {
        Object object;
        this.d.a();
        Object object2 = this.j;
        try {
            object = this.d.b();
            object = new by().a((bp)object);
            object2.a.O = Base64.encodeToString((byte[])object, (int)0);
        }
        catch (Exception exception) {
            bl.e(exception);
        }
        if (com.umeng.analytics.b.a(g, (byte[])(object2 = (Object)com.umeng.analytics.h.a(g).b((av)object2)))) {
            return;
        }
        if (object2 == null) {
            bl.e("message is null");
            return;
        }
        object2 = !this.k ? t.a(g, AnalyticsConfig.getAppkey(g), (byte[])object2) : t.b(g, AnalyticsConfig.getAppkey(g), (byte[])object2);
        object2 = ((t)object2).c();
        com.umeng.analytics.h.a(g).h();
        object = this.i.a((byte[])object2);
        int n2 = object == null ? 1 : this.a((byte[])object);
        switch (n2) {
            default: {
                return;
            }
            case 1: {
                if (this.l) return;
                com.umeng.analytics.h.a(g).a((byte[])object2);
                return;
            }
            case 2: {
                if (this.h.m()) {
                    this.h.l();
                }
                this.d.d();
                this.h.k();
                av.c = 0L;
                return;
            }
            case 3: 
        }
        this.h.k();
    }

    public void a() {
        if (this.j != null) {
            this.c();
            return;
        }
        this.b();
    }

    public void a(ao ao2) {
        this.e.a(ao2);
    }

    public void a(av av2) {
        this.j = av2;
    }

    public void a(boolean bl2) {
        this.k = bl2;
    }

    public void b(boolean bl2) {
        this.l = bl2;
    }
}

