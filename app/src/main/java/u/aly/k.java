/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.io.Serializable;

public class k
implements Serializable {
    private static final long a = 1L;
    private String b = null;
    private long c = 0L;
    private long d = 0L;
    private String e = null;

    private k() {
    }

    public k(String string2, long l2, long l3) {
        this(string2, l2, l3, null);
    }

    public k(String string2, long l2, long l3, String string3) {
        this.b = string2;
        this.c = l2;
        this.d = l3;
        this.e = string3;
    }

    public k a() {
        ++this.d;
        return this;
    }

    public k a(k k2) {
        this.d = k2.e() + this.d;
        this.c = k2.d();
        return this;
    }

    public void a(String string2) {
        this.e = string2;
    }

    public String b() {
        return this.e;
    }

    public void b(String string2) {
        this.b = string2;
    }

    public String c() {
        return this.b;
    }

    public long d() {
        return this.c;
    }

    public long e() {
        return this.d;
    }
}

