/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import u.aly.q;

public class l
implements Serializable {
    private static final long a = 1L;
    private List<String> b = new ArrayList<String>();
    private String c;
    private long d;
    private long e;
    private String f;

    public l(List<String> list, long l2, String string2, long l3) {
        this.b = list;
        this.d = l2;
        this.c = string2;
        this.e = l3;
        this.f();
    }

    private void f() {
        this.f = q.a(this.e);
    }

    public List<String> a() {
        return this.b;
    }

    public String b() {
        return this.c;
    }

    public long c() {
        return this.d;
    }

    public long d() {
        return this.e;
    }

    public String e() {
        return this.f;
    }
}

