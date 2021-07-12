/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.io.Serializable;

public class cc
implements Serializable {
    private final boolean a;
    public final byte b;
    private final String c;
    private final boolean d;

    public cc(byte by2) {
        this(by2, false);
    }

    public cc(byte by2, String string2) {
        this.b = by2;
        this.a = true;
        this.c = string2;
        this.d = false;
    }

    public cc(byte by2, boolean bl2) {
        this.b = by2;
        this.a = false;
        this.c = null;
        this.d = bl2;
    }

    public boolean a() {
        return this.a;
    }

    public String b() {
        return this.c;
    }

    public boolean c() {
        return this.b == 12;
    }

    public boolean d() {
        return this.b == 15 || this.b == 13 || this.b == 14;
    }

    public boolean e() {
        return this.d;
    }
}

