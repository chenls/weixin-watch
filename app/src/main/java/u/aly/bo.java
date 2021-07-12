/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import u.aly.bv;
import u.aly.cj;
import u.aly.co;
import u.aly.cr;
import u.aly.ct;

public class bo
extends bv {
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 3;
    public static final int e = 4;
    public static final int f = 5;
    public static final int g = 6;
    public static final int h = 7;
    private static final ct j = new ct("TApplicationException");
    private static final cj k = new cj("message", 11, 1);
    private static final cj l = new cj("type", 8, 2);
    private static final long m = 1L;
    protected int i = 0;

    public bo() {
    }

    public bo(int n2) {
        this.i = n2;
    }

    public bo(int n2, String string2) {
        super(string2);
        this.i = n2;
    }

    public bo(String string2) {
        super(string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static bo a(co co2) throws bv {
        co2.j();
        String string2 = null;
        int n2 = 0;
        while (true) {
            cj cj2 = co2.l();
            if (cj2.b == 0) {
                co2.k();
                return new bo(n2, string2);
            }
            switch (cj2.c) {
                default: {
                    cr.a(co2, cj2.b);
                    break;
                }
                case 1: {
                    if (cj2.b == 11) {
                        string2 = co2.z();
                        break;
                    }
                    cr.a(co2, cj2.b);
                    break;
                }
                case 2: {
                    if (cj2.b == 8) {
                        n2 = co2.w();
                        break;
                    }
                    cr.a(co2, cj2.b);
                }
            }
            co2.m();
        }
    }

    public int a() {
        return this.i;
    }

    public void b(co co2) throws bv {
        co2.a(j);
        if (this.getMessage() != null) {
            co2.a(k);
            co2.a(this.getMessage());
            co2.c();
        }
        co2.a(l);
        co2.a(this.i);
        co2.c();
        co2.d();
        co2.b();
    }
}

