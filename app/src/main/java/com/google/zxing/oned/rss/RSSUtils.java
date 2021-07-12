/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss;

public final class RSSUtils {
    private RSSUtils() {
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int combins(int n2, int n3) {
        int n4;
        int n5;
        int n6;
        if (n2 - n3 > n3) {
            n6 = n3;
            n5 = n2 - n3;
        } else {
            n6 = n2 - n3;
            n5 = n3;
        }
        n3 = 1;
        int n7 = 1;
        int n8 = n2;
        n2 = n7;
        while (true) {
            n7 = n2;
            n4 = n3;
            if (n8 <= n5) break;
            n4 = n3 * n8;
            n7 = n2;
            n3 = n4;
            if (n2 <= n6) {
                n3 = n4 / n2;
                n7 = n2 + 1;
            }
            --n8;
            n2 = n7;
        }
        while (n7 <= n6) {
            n4 /= n7;
            ++n7;
        }
        return n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int getRSSvalue(int[] nArray, int n2, boolean bl2) {
        int n3;
        int n4 = nArray.length;
        int n5 = 0;
        int n6 = nArray.length;
        for (n3 = 0; n3 < n6; n5 += nArray[n3], ++n3) {
        }
        int n7 = 0;
        n3 = 0;
        int n8 = 0;
        int n9 = n5;
        while (n8 < n4 - 1) {
            int n10;
            n5 = n3 | 1 << n8;
            for (n10 = 1; n10 < nArray[n8]; n7 += n6, ++n10, n5 &= ~(1 << n8)) {
                n3 = n6 = RSSUtils.combins(n9 - n10 - 1, n4 - n8 - 2);
                if (bl2) {
                    n3 = n6;
                    if (n5 == 0) {
                        n3 = n6;
                        if (n9 - n10 - (n4 - n8 - 1) >= n4 - n8 - 1) {
                            n3 = n6 - RSSUtils.combins(n9 - n10 - (n4 - n8), n4 - n8 - 2);
                        }
                    }
                }
                if (n4 - n8 - 1 > 1) {
                    int n11 = 0;
                    for (n6 = n9 - n10 - (n4 - n8 - 2); n6 > n2; n11 += RSSUtils.combins(n9 - n10 - n6 - 1, n4 - n8 - 3), --n6) {
                    }
                    n6 = n3 - (n4 - 1 - n8) * n11;
                    continue;
                }
                n6 = n3;
                if (n9 - n10 <= n2) continue;
                n6 = n3 - 1;
            }
            n9 -= n10;
            ++n8;
            n3 = n5;
        }
        return n7;
    }
}

