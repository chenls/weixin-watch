/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 */
package ticwear.design.utils;

import android.graphics.Color;

public class ColorUtils {
    public static int getColor(int n2, float f2) {
        return 0xFFFFFF & n2 | Math.round((float)Color.alpha((int)n2) * f2) << 24;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int getMiddleColor(int n2, int n3, float f2) {
        block5: {
            block4: {
                if (n2 == n3) break block4;
                if (f2 == 0.0f) {
                    return n2;
                }
                if (f2 != 1.0f) break block5;
            }
            return n3;
        }
        return Color.argb((int)ColorUtils.getMiddleValue(Color.alpha((int)n2), Color.alpha((int)n3), f2), (int)ColorUtils.getMiddleValue(Color.red((int)n2), Color.red((int)n3), f2), (int)ColorUtils.getMiddleValue(Color.green((int)n2), Color.green((int)n3), f2), (int)ColorUtils.getMiddleValue(Color.blue((int)n2), Color.blue((int)n3), f2));
    }

    private static int getMiddleValue(int n2, int n3, float f2) {
        return Math.round((float)n2 + (float)(n3 - n2) * f2);
    }
}

