/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package ticwear.design.utils;

import android.content.Context;

public class MetricsUtils {
    public static float convertDpToPixel(float f2, Context context) {
        return f2 * ((float)context.getResources().getDisplayMetrics().densityDpi / 160.0f);
    }

    public static float convertPixelsToDp(float f2, Context context) {
        return f2 / ((float)context.getResources().getDisplayMetrics().densityDpi / 160.0f);
    }
}

