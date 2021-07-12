/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import u.aly.ba;
import u.aly.bb;
import u.aly.bc;
import u.aly.bp;

public abstract class r {
    private final int a;
    private final int b;
    private final String c;
    private List<ba> d;
    private bb e;

    public r(String string2) {
        this.a = 10;
        this.b = 20;
        this.c = string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean g() {
        boolean bl2 = false;
        bp<bb, bb.e> bp2 = this.e;
        String string2 = bp2 == null ? null : ((bb)bp2).c();
        int n2 = bp2 == null ? 0 : ((bb)bp2).i();
        String string3 = this.a(this.f());
        boolean bl3 = bl2;
        if (string3 == null) return bl3;
        bl3 = bl2;
        if (string3.equals(string2)) return bl3;
        bb bb2 = bp2;
        if (bp2 == null) {
            bb2 = new bb();
        }
        bb2.a(string3);
        bb2.a(System.currentTimeMillis());
        bb2.a(n2 + 1);
        bp2 = new ba();
        ((ba)bp2).a(this.c);
        ((ba)bp2).c(string3);
        ((ba)bp2).b(string2);
        ((ba)bp2).a(bb2.f());
        if (this.d == null) {
            this.d = new ArrayList<ba>(2);
        }
        this.d.add((ba)bp2);
        if (this.d.size() > 10) {
            this.d.remove(0);
        }
        this.e = bb2;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String a(String string2) {
        if (string2 == null || (string2 = string2.trim()).length() == 0 || "0".equals(string2) || "unknown".equals(string2.toLowerCase(Locale.US))) {
            return null;
        }
        return string2;
    }

    public void a(List<ba> list) {
        this.d = list;
    }

    public void a(bb bb2) {
        this.e = bb2;
    }

    public void a(bc iterator) {
        this.e = ((bc)((Object)iterator)).d().get(this.c);
        if ((iterator = ((bc)((Object)iterator)).i()) != null && iterator.size() > 0) {
            if (this.d == null) {
                this.d = new ArrayList<ba>();
            }
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                ba ba2 = (ba)iterator.next();
                if (!this.c.equals(ba2.a)) continue;
                this.d.add(ba2);
            }
        }
    }

    public boolean a() {
        return this.g();
    }

    public String b() {
        return this.c;
    }

    public boolean c() {
        return this.e == null || this.e.i() <= 20;
    }

    public bb d() {
        return this.e;
    }

    public List<ba> e() {
        return this.d;
    }

    public abstract String f();
}

