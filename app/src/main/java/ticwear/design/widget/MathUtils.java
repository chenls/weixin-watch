/*
 * Decompiled with CFR 0.151.
 */
package ticwear.design.widget;

class MathUtils {
    MathUtils() {
    }

    static float constrain(float f2, float f3, float f4) {
        if (f2 < f3) {
            return f3;
        }
        if (f2 > f4) {
            return f4;
        }
        return f2;
    }

    static int constrain(int n2, int n3, int n4) {
        if (n2 < n3) {
            return n3;
        }
        if (n2 > n4) {
            return n4;
        }
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean sameSign(int n2, int n3) {
        int n4 = 1;
        n2 = n2 >= 0 ? 1 : 0;
        if (n3 < 0) {
            n3 = n4;
            return (n3 ^ n2) != 0;
        }
        n3 = 0;
        return (n3 ^ n2) != 0;
    }
}

