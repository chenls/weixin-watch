/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 *  android.util.Base64
 */
package u.aly;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.ReportPolicy;
import com.umeng.analytics.b;
import com.umeng.analytics.f;
import com.umeng.analytics.g;
import com.umeng.analytics.h;
import java.util.List;
import u.aly.ah;
import u.aly.ai;
import u.aly.ak;
import u.aly.ao;
import u.aly.ap;
import u.aly.aq;
import u.aly.as;
import u.aly.av;
import u.aly.aw;
import u.aly.ax;
import u.aly.ay;
import u.aly.bj;
import u.aly.bl;
import u.aly.bp;
import u.aly.by;
import u.aly.t;
import u.aly.v;
import u.aly.x;

public final class ad
implements ah,
ao {
    private static Context o;
    private final long a;
    private final int b;
    private ak c = null;
    private h d = null;
    private as e = null;
    private ax f = null;
    private aw g = null;
    private ay h = null;
    private a i = null;
    private x.a j = null;
    private int k = 10;
    private long l = 0L;
    private int m = 0;
    private int n = 0;

    public ad(Context context) {
        this.a = 28800000L;
        this.b = 5000;
        o = context;
        this.c = new ak(context);
        this.e = new as(context);
        this.d = com.umeng.analytics.h.a(context);
        this.j = x.a(context).b();
        this.i = new a();
        this.g = aw.a(o);
        this.f = ax.a(o);
        this.h = ay.a(o, this.e);
        context = ap.a(o);
        this.l = context.getLong("thtstart", 0L);
        this.m = context.getInt("gkvc", 0);
        this.n = context.getInt("ekvc", 0);
    }

    private void a(int n2) {
        this.a(this.a(n2, (int)(System.currentTimeMillis() - this.e.o())));
        com.umeng.analytics.f.a(new g(){

            @Override
            public void a() {
                ad.this.a();
            }
        }, n2);
    }

    private void a(int n2, int n3, av list) {
        int n4;
        if (n2 > 0) {
            List<av.h> list2 = list.b.b;
            if (list2.size() >= n2) {
                int n5 = list2.size();
                for (n4 = list2.size() - 1; n4 >= n5 - n2; --n4) {
                    list2.remove(n4);
                }
            } else {
                list2.size();
                list2.clear();
            }
        }
        if (n3 > 0) {
            list = list.b.a;
            if (list.size() >= n3) {
                n4 = list.size();
                for (n2 = list.size() - 1; n2 >= n4 - n3; --n2) {
                    list.remove(n2);
                }
            } else {
                list.size();
                list.clear();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void a(av object) {
        Object object2;
        v v2;
        if (object == null) return;
        try {
            v2 = v.a(o);
            v2.a();
        }
        catch (Exception exception) {
            return;
        }
        try {
            object2 = v2.b();
            object2 = new by().a((bp)object2);
            object.a.O = Base64.encodeToString((byte[])object2, (int)0);
        }
        catch (Exception exception) {}
        object = com.umeng.analytics.h.a(o).b(this.c((av)object));
        if (object == null) {
            return;
        }
        if (com.umeng.analytics.b.a(o, (byte[])object)) return;
        object = this.g() ? t.b(o, AnalyticsConfig.getAppkey(o), (byte[])object) : t.a(o, AnalyticsConfig.getAppkey(o), (byte[])object);
        object = ((t)object).c();
        object2 = com.umeng.analytics.h.a(o);
        ((h)object2).h();
        ((h)object2).a((byte[])object);
        v2.d();
        av.c = 0L;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a(boolean bl2) {
        boolean bl3 = this.e.f();
        if (bl3) {
            av.c = this.e.n();
        }
        if (this.b(bl2)) {
            this.f();
            return;
        } else {
            if (!bl3 && !this.e()) return;
            this.b();
            return;
        }
    }

    private void b(int n2) {
        this.a(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean b(av av2) {
        return av2 != null && av2.a();
    }

    private boolean b(boolean bl2) {
        if (!bj.o(o)) {
            bl.b("network is unavailable");
            return false;
        }
        if (this.e.f()) {
            return true;
        }
        return this.i.b(bl2).a(bl2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private av c(av av2) {
        long l2;
        int n2;
        int n3;
        int n4;
        int n5 = 5000;
        if (av2.b.a != null) {
            n4 = 0;
            n3 = 0;
            while (true) {
                n2 = n3;
                if (n4 < av2.b.a.size()) {
                    n3 += av2.b.a.get((int)n4).b.size();
                    ++n4;
                    continue;
                }
                break;
            }
        } else {
            n2 = 0;
        }
        n3 = n2;
        if (av2.b.b != null) {
            n4 = 0;
            while (true) {
                n3 = n2;
                if (n4 >= av2.b.b.size()) break;
                n2 += av2.b.b.get((int)n4).b.size();
                ++n4;
            }
        }
        if ((l2 = System.currentTimeMillis()) - this.l > 28800000L) {
            n2 = n3 - 5000;
            if (n2 > 0) {
                this.a(-5000, n2, av2);
            }
            this.m = 0;
            if (n2 > 0) {
                n3 = 5000;
            }
            this.n = n3;
            this.l = l2;
            return av2;
        }
        n4 = this.m > 5000 ? 0 : this.m + 0 - 5000;
        n2 = this.n > 5000 ? n3 : this.n + n3 - 5000;
        if (n4 > 0 || n2 > 0) {
            this.a(n4, n2, av2);
        }
        n4 = n4 > 0 ? 5000 : this.m + 0;
        this.m = n4;
        n3 = n2 > 0 ? n5 : this.n + n3;
        this.n = n3;
        return av2;
    }

    private boolean e() {
        return this.c.b() > this.k;
    }

    private void f() {
        block7: {
            try {
                if (this.d.i()) {
                    aq aq2 = new aq(o, this.e);
                    aq2.a(this);
                    if (this.f.c()) {
                        aq2.b(true);
                    }
                    aq2.a();
                    return;
                }
                av av2 = this.a(new int[0]);
                if (this.b(av2)) {
                    aq aq3 = new aq(o, this.e);
                    aq3.a(this);
                    if (this.f.c()) {
                        aq3.b(true);
                    }
                    aq3.a(this.c(av2));
                    aq3.a(this.g());
                    aq3.a();
                    return;
                }
            }
            catch (Throwable throwable) {
                if (throwable instanceof OutOfMemoryError) {
                    // empty if block
                }
                if (throwable == null) break block7;
                throwable.printStackTrace();
            }
        }
    }

    private boolean g() {
        switch (this.j.c(-1)) {
            default: {
                return false;
            }
            case 1: {
                return true;
            }
            case -1: 
        }
        return AnalyticsConfig.sEncrypt;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected av a(int ... nArray) {
        boolean bl2 = false;
        try {
            if (TextUtils.isEmpty((CharSequence)AnalyticsConfig.getAppkey(o))) {
                bl.e("Appkey is missing ,Please check AndroidManifest.xml");
                return null;
            }
            Object object = com.umeng.analytics.h.a(o).g();
            if (object == null && this.c.b() == 0) {
                return null;
            }
            av av2 = object;
            if (object == null) {
                av2 = new av();
            }
            this.c.a(av2);
            if (av2.b.c != null && bl.a && av2.b.c.size() > 0) {
                object = av2.b.c.iterator();
                while (object.hasNext()) {
                    if (((av.o)object.next()).h.size() <= 0) continue;
                    bl2 = true;
                }
                if (!bl2) {
                    bl.d("missing Activities or PageViews");
                }
            }
            this.f.a(av2, o);
            if (nArray != null && nArray.length == 2) {
                av2.b.e.a = nArray[0] / 1000;
                av2.b.e.b = nArray[1];
                av2.b.e.c = true;
            }
            return av2;
        }
        catch (Exception exception) {
            bl.e("Fail to construct message ...", exception);
            com.umeng.analytics.h.a(o).h();
            bl.e(exception);
            return null;
        }
    }

    @Override
    public void a() {
        if (bj.o(o)) {
            this.f();
            return;
        }
        bl.b("network is unavailable");
    }

    @Override
    public void a(ai ai2) {
        if (ai2 != null) {
            this.c.a(ai2);
        }
        this.a(ai2 instanceof av.o);
    }

    @Override
    public void a(x.a a2) {
        this.g.a(a2);
        this.f.a(a2);
        this.h.a(a2);
        this.i.a(a2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void b() {
        block4: {
            if (this.c.b() > 0) {
                try {
                    this.d.a(this.a(new int[0]));
                }
                catch (Throwable throwable) {
                    bl.e(throwable);
                    if (throwable instanceof OutOfMemoryError) {
                        this.d.h();
                    }
                    if (throwable == null) break block4;
                    throwable.printStackTrace();
                }
            }
        }
        ap.a(o).edit().putLong("thtstart", this.l).putInt("gkvc", this.m).putInt("ekvc", this.n).commit();
    }

    @Override
    public void b(ai ai2) {
        this.c.a(ai2);
    }

    @Override
    public void c() {
        this.a(this.a(new int[0]));
    }

    public class a {
        private ReportPolicy.i b;
        private int c = -1;
        private int d = -1;
        private int e = -1;
        private int f = -1;

        public a() {
            ad.this = ((ad)ad.this).j.a(-1, -1);
            this.c = (int)ad.this[0];
            this.d = (int)ad.this[1];
        }

        private ReportPolicy.i b(int n2, int n3) {
            switch (n2) {
                default: {
                    if (!(this.b instanceof ReportPolicy.d)) break;
                    return this.b;
                }
                case 1: {
                    if (this.b instanceof ReportPolicy.d) {
                        return this.b;
                    }
                    return new ReportPolicy.d();
                }
                case 6: {
                    if (this.b instanceof ReportPolicy.e) {
                        ReportPolicy.i i2 = this.b;
                        ((ReportPolicy.e)i2).a(n3);
                        return i2;
                    }
                    return new ReportPolicy.e(ad.this.e, n3);
                }
                case 4: {
                    if (this.b instanceof ReportPolicy.g) {
                        return this.b;
                    }
                    return new ReportPolicy.g(ad.this.e);
                }
                case 0: {
                    if (this.b instanceof ReportPolicy.h) {
                        return this.b;
                    }
                    return new ReportPolicy.h();
                }
                case 5: {
                    if (this.b instanceof ReportPolicy.j) {
                        return this.b;
                    }
                    return new ReportPolicy.j(o);
                }
                case 8: {
                    if (this.b instanceof ReportPolicy.k) {
                        return this.b;
                    }
                    return new ReportPolicy.k(ad.this.e);
                }
            }
            return new ReportPolicy.d();
        }

        public void a(int n2, int n3) {
            this.e = n2;
            this.f = n3;
        }

        public void a(x.a object) {
            object = ((x.a)object).a(-1, -1);
            this.c = (int)object[0];
            this.d = (int)object[1];
        }

        /*
         * Enabled aggressive block sorting
         */
        protected void a(boolean bl2) {
            int n2 = 1;
            int n3 = 1;
            int n4 = 0;
            if (ad.this.f.c()) {
                if (!(this.b instanceof ReportPolicy.b) || !this.b.a()) {
                    n3 = 0;
                }
                ReportPolicy.i i2 = n3 != 0 ? this.b : new ReportPolicy.b(ad.this.e, ad.this.f);
                this.b = i2;
            } else {
                n3 = this.b instanceof ReportPolicy.c && this.b.a() ? n2 : 0;
                if (n3 == 0) {
                    if (bl2 && ad.this.h.a()) {
                        this.b = new ReportPolicy.c((int)ad.this.h.b());
                        ad.this.b((int)ad.this.h.b());
                    } else if (bl.a && ad.this.j.b()) {
                        bl.b("Debug: send log every 15 seconds");
                        this.b = new ReportPolicy.a(ad.this.e);
                    } else if (ad.this.g.a()) {
                        bl.b("Start A/B Test");
                        n3 = n4;
                        if (ad.this.g.b() == 6) {
                            n3 = ad.this.j.a() ? ad.this.j.d(90000) : (this.d > 0 ? this.d : this.f);
                        }
                        this.b = this.b(ad.this.g.b(), n3);
                    } else {
                        n4 = this.e;
                        n3 = this.f;
                        if (this.c != -1) {
                            n4 = this.c;
                            n3 = this.d;
                        }
                        this.b = this.b(n4, n3);
                    }
                }
            }
            bl.b("Report policy : " + this.b.getClass().getSimpleName());
        }

        public ReportPolicy.i b(boolean bl2) {
            this.a(bl2);
            return this.b;
        }
    }
}

