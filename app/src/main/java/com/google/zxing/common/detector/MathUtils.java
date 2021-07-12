/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.common.detector;

public final class MathUtils {
    private MathUtils() {
    }

    public static float distance(float f2, float f3, float f4, float f5) {
        return (float)Math.sqrt((f2 -= f4) * f2 + (f3 -= f5) * f3);
    }

    public static float distance(int n2, int n3, int n4, int n5) {
        return (float)Math.sqrt((n2 -= n4) * n2 + (n3 -= n5) * n3);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int round(float f2) {
        float f3;
        if (f2 < 0.0f) {
            f3 = -0.5f;
            return (int)(f3 + f2);
        }
        f3 = 0.5f;
        return (int)(f3 + f2);
    }
}

