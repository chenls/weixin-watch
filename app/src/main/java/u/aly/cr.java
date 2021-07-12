/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import u.aly.bv;
import u.aly.ci;
import u.aly.cj;
import u.aly.ck;
import u.aly.cl;
import u.aly.co;
import u.aly.cq;
import u.aly.cs;

public class cr {
    private static int a = Integer.MAX_VALUE;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static cq a(byte[] byArray, cq cq2) {
        if (byArray[0] > 16) {
            return new ci.a();
        }
        cq cq3 = cq2;
        if (byArray.length <= 1) return cq3;
        cq3 = cq2;
        if ((byArray[1] & 0x80) == 0) return cq3;
        return new ci.a();
    }

    public static void a(int n2) {
        a = n2;
    }

    public static void a(co co2, byte by2) throws bv {
        cr.a(co2, by2, a);
    }

    public static void a(co co2, byte by2, int n2) throws bv {
        byte by3 = 0;
        byte by4 = 0;
        byte by5 = 0;
        if (n2 <= 0) {
            throw new bv("Maximum skip depth exceeded");
        }
        switch (by2) {
            default: {
                return;
            }
            case 2: {
                co2.t();
                return;
            }
            case 3: {
                co2.u();
                return;
            }
            case 6: {
                co2.v();
                return;
            }
            case 8: {
                co2.w();
                return;
            }
            case 10: {
                co2.x();
                return;
            }
            case 4: {
                co2.y();
                return;
            }
            case 11: {
                co2.A();
                return;
            }
            case 12: {
                co2.j();
                while (true) {
                    cj cj2 = co2.l();
                    if (cj2.b == 0) {
                        co2.k();
                        return;
                    }
                    cr.a(co2, cj2.b, n2 - 1);
                    co2.m();
                }
            }
            case 13: {
                cl cl2 = co2.n();
                for (by2 = by5; by2 < cl2.c; by2 = (byte)(by2 + 1)) {
                    cr.a(co2, cl2.a, n2 - 1);
                    cr.a(co2, cl2.b, n2 - 1);
                }
                co2.o();
                return;
            }
            case 14: {
                cs cs2 = co2.r();
                for (by2 = by3; by2 < cs2.b; by2 = (byte)(by2 + 1)) {
                    cr.a(co2, cs2.a, n2 - 1);
                }
                co2.s();
                return;
            }
            case 15: 
        }
        ck ck2 = co2.p();
        for (by2 = by4; by2 < ck2.b; by2 = (byte)(by2 + 1)) {
            cr.a(co2, ck2.a, n2 - 1);
        }
        co2.q();
    }
}

