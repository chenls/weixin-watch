/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 */
package android.support.v4.graphics;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

public final class ColorUtils {
    private static final int MIN_ALPHA_SEARCH_MAX_ITERATIONS = 10;
    private static final int MIN_ALPHA_SEARCH_PRECISION = 1;
    private static final ThreadLocal<double[]> TEMP_ARRAY = new ThreadLocal();
    private static final double XYZ_EPSILON = 0.008856;
    private static final double XYZ_KAPPA = 903.3;
    private static final double XYZ_WHITE_REFERENCE_X = 95.047;
    private static final double XYZ_WHITE_REFERENCE_Y = 100.0;
    private static final double XYZ_WHITE_REFERENCE_Z = 108.883;

    private ColorUtils() {
    }

    /*
     * Enabled aggressive block sorting
     */
    @ColorInt
    public static int HSLToColor(@NonNull float[] fArray) {
        float f2 = fArray[0];
        float f3 = fArray[1];
        float f4 = fArray[2];
        f3 = (1.0f - Math.abs(2.0f * f4 - 1.0f)) * f3;
        f4 -= 0.5f * f3;
        float f5 = f3 * (1.0f - Math.abs(f2 / 60.0f % 2.0f - 1.0f));
        int n2 = (int)f2 / 60;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        switch (n2) {
            case 0: {
                n3 = Math.round(255.0f * (f3 + f4));
                n4 = Math.round(255.0f * (f5 + f4));
                n5 = Math.round(255.0f * f4);
                return Color.rgb((int)ColorUtils.constrain(n3, 0, 255), (int)ColorUtils.constrain(n4, 0, 255), (int)ColorUtils.constrain(n5, 0, 255));
            }
            case 1: {
                n3 = Math.round(255.0f * (f5 + f4));
                n4 = Math.round(255.0f * (f3 + f4));
                n5 = Math.round(255.0f * f4);
                return Color.rgb((int)ColorUtils.constrain(n3, 0, 255), (int)ColorUtils.constrain(n4, 0, 255), (int)ColorUtils.constrain(n5, 0, 255));
            }
            case 2: {
                n3 = Math.round(255.0f * f4);
                n4 = Math.round(255.0f * (f3 + f4));
                n5 = Math.round(255.0f * (f5 + f4));
                return Color.rgb((int)ColorUtils.constrain(n3, 0, 255), (int)ColorUtils.constrain(n4, 0, 255), (int)ColorUtils.constrain(n5, 0, 255));
            }
            case 3: {
                n3 = Math.round(255.0f * f4);
                n4 = Math.round(255.0f * (f5 + f4));
                n5 = Math.round(255.0f * (f3 + f4));
                return Color.rgb((int)ColorUtils.constrain(n3, 0, 255), (int)ColorUtils.constrain(n4, 0, 255), (int)ColorUtils.constrain(n5, 0, 255));
            }
            case 4: {
                n3 = Math.round(255.0f * (f5 + f4));
                n4 = Math.round(255.0f * f4);
                n5 = Math.round(255.0f * (f3 + f4));
                return Color.rgb((int)ColorUtils.constrain(n3, 0, 255), (int)ColorUtils.constrain(n4, 0, 255), (int)ColorUtils.constrain(n5, 0, 255));
            }
            case 5: 
            case 6: {
                n3 = Math.round(255.0f * (f3 + f4));
                n4 = Math.round(255.0f * f4);
                n5 = Math.round(255.0f * (f5 + f4));
                return Color.rgb((int)ColorUtils.constrain(n3, 0, 255), (int)ColorUtils.constrain(n4, 0, 255), (int)ColorUtils.constrain(n5, 0, 255));
            }
        }
        return Color.rgb((int)ColorUtils.constrain(n3, 0, 255), (int)ColorUtils.constrain(n4, 0, 255), (int)ColorUtils.constrain(n5, 0, 255));
    }

    @ColorInt
    public static int LABToColor(@FloatRange(from=0.0, to=100.0) double d2, @FloatRange(from=-128.0, to=127.0) double d3, @FloatRange(from=-128.0, to=127.0) double d4) {
        double[] dArray = ColorUtils.getTempDouble3Array();
        ColorUtils.LABToXYZ(d2, d3, d4, dArray);
        return ColorUtils.XYZToColor(dArray[0], dArray[1], dArray[2]);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void LABToXYZ(@FloatRange(from=0.0, to=100.0) double d2, @FloatRange(from=-128.0, to=127.0) double d3, @FloatRange(from=-128.0, to=127.0) double d4, @NonNull double[] dArray) {
        double d5 = (16.0 + d2) / 116.0;
        double d6 = d3 / 500.0 + d5;
        double d7 = d5 - d4 / 200.0;
        d3 = Math.pow(d6, 3.0);
        if (!(d3 > 0.008856)) {
            d3 = (116.0 * d6 - 16.0) / 903.3;
        }
        d2 = d2 > 7.9996247999999985 ? Math.pow(d5, 3.0) : (d2 /= 903.3);
        d4 = Math.pow(d7, 3.0);
        if (!(d4 > 0.008856)) {
            d4 = (116.0 * d7 - 16.0) / 903.3;
        }
        dArray[0] = 95.047 * d3;
        dArray[1] = 100.0 * d2;
        dArray[2] = 108.883 * d4;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void RGBToHSL(@IntRange(from=0L, to=255L) int n2, @IntRange(from=0L, to=255L) int n3, @IntRange(from=0L, to=255L) int n4, @NonNull float[] fArray) {
        float f2 = (float)n2 / 255.0f;
        float f3 = (float)n3 / 255.0f;
        float f4 = (float)n4 / 255.0f;
        float f5 = Math.max(f2, Math.max(f3, f4));
        float f6 = Math.min(f2, Math.min(f3, f4));
        float f7 = f5 - f6;
        float f8 = (f5 + f6) / 2.0f;
        if (f5 == f6) {
            f2 = 0.0f;
            f7 = 0.0f;
        } else {
            f2 = f5 == f2 ? (f3 - f4) / f7 % 6.0f : (f5 == f3 ? (f4 - f2) / f7 + 2.0f : (f2 - f3) / f7 + 4.0f);
            f3 = f7 / (1.0f - Math.abs(2.0f * f8 - 1.0f));
            f7 = f2;
            f2 = f3;
        }
        f7 = f3 = 60.0f * f7 % 360.0f;
        if (f3 < 0.0f) {
            f7 = f3 + 360.0f;
        }
        fArray[0] = ColorUtils.constrain(f7, 0.0f, 360.0f);
        fArray[1] = ColorUtils.constrain(f2, 0.0f, 1.0f);
        fArray[2] = ColorUtils.constrain(f8, 0.0f, 1.0f);
    }

    public static void RGBToLAB(@IntRange(from=0L, to=255L) int n2, @IntRange(from=0L, to=255L) int n3, @IntRange(from=0L, to=255L) int n4, @NonNull double[] dArray) {
        ColorUtils.RGBToXYZ(n2, n3, n4, dArray);
        ColorUtils.XYZToLAB(dArray[0], dArray[1], dArray[2], dArray);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void RGBToXYZ(@IntRange(from=0L, to=255L) int n2, @IntRange(from=0L, to=255L) int n3, @IntRange(from=0L, to=255L) int n4, @NonNull double[] dArray) {
        if (dArray.length != 3) {
            throw new IllegalArgumentException("outXyz must have a length of 3.");
        }
        double d2 = (double)n2 / 255.0;
        d2 = d2 < 0.04045 ? (d2 /= 12.92) : Math.pow((0.055 + d2) / 1.055, 2.4);
        double d3 = (double)n3 / 255.0;
        d3 = d3 < 0.04045 ? (d3 /= 12.92) : Math.pow((0.055 + d3) / 1.055, 2.4);
        double d4 = (double)n4 / 255.0;
        d4 = d4 < 0.04045 ? (d4 /= 12.92) : Math.pow((0.055 + d4) / 1.055, 2.4);
        dArray[0] = 100.0 * (0.4124 * d2 + 0.3576 * d3 + 0.1805 * d4);
        dArray[1] = 100.0 * (0.2126 * d2 + 0.7152 * d3 + 0.0722 * d4);
        dArray[2] = 100.0 * (0.0193 * d2 + 0.1192 * d3 + 0.9505 * d4);
    }

    /*
     * Enabled aggressive block sorting
     */
    @ColorInt
    public static int XYZToColor(@FloatRange(from=0.0, to=95.047) double d2, @FloatRange(from=0.0, to=100.0) double d3, @FloatRange(from=0.0, to=108.883) double d4) {
        double d5 = (3.2406 * d2 + -1.5372 * d3 + -0.4986 * d4) / 100.0;
        double d6 = (-0.9689 * d2 + 1.8758 * d3 + 0.0415 * d4) / 100.0;
        d4 = (0.0557 * d2 + -0.204 * d3 + 1.057 * d4) / 100.0;
        d2 = d5 > 0.0031308 ? 1.055 * Math.pow(d5, 0.4166666666666667) - 0.055 : d5 * 12.92;
        d3 = d6 > 0.0031308 ? 1.055 * Math.pow(d6, 0.4166666666666667) - 0.055 : d6 * 12.92;
        if (d4 > 0.0031308) {
            d4 = 1.055 * Math.pow(d4, 0.4166666666666667) - 0.055;
            return Color.rgb((int)ColorUtils.constrain((int)Math.round(255.0 * d2), 0, 255), (int)ColorUtils.constrain((int)Math.round(255.0 * d3), 0, 255), (int)ColorUtils.constrain((int)Math.round(255.0 * d4), 0, 255));
        }
        return Color.rgb((int)ColorUtils.constrain((int)Math.round(255.0 * d2), 0, 255), (int)ColorUtils.constrain((int)Math.round(255.0 * d3), 0, 255), (int)ColorUtils.constrain((int)Math.round(255.0 * (d4 *= 12.92)), 0, 255));
    }

    public static void XYZToLAB(@FloatRange(from=0.0, to=95.047) double d2, @FloatRange(from=0.0, to=100.0) double d3, @FloatRange(from=0.0, to=108.883) double d4, @NonNull double[] dArray) {
        if (dArray.length != 3) {
            throw new IllegalArgumentException("outLab must have a length of 3.");
        }
        d2 = ColorUtils.pivotXyzComponent(d2 / 95.047);
        d3 = ColorUtils.pivotXyzComponent(d3 / 100.0);
        d4 = ColorUtils.pivotXyzComponent(d4 / 108.883);
        dArray[0] = Math.max(0.0, 116.0 * d3 - 16.0);
        dArray[1] = 500.0 * (d2 - d3);
        dArray[2] = 200.0 * (d3 - d4);
    }

    @ColorInt
    public static int blendARGB(@ColorInt int n2, @ColorInt int n3, @FloatRange(from=0.0, to=1.0) float f2) {
        float f3 = 1.0f - f2;
        float f4 = Color.alpha((int)n2);
        float f5 = Color.alpha((int)n3);
        float f6 = Color.red((int)n2);
        float f7 = Color.red((int)n3);
        float f8 = Color.green((int)n2);
        float f9 = Color.green((int)n3);
        float f10 = Color.blue((int)n2);
        float f11 = Color.blue((int)n3);
        return Color.argb((int)((int)(f4 * f3 + f5 * f2)), (int)((int)(f6 * f3 + f7 * f2)), (int)((int)(f8 * f3 + f9 * f2)), (int)((int)(f10 * f3 + f11 * f2)));
    }

    public static void blendHSL(@NonNull float[] fArray, @NonNull float[] fArray2, @FloatRange(from=0.0, to=1.0) float f2, @NonNull float[] fArray3) {
        if (fArray3.length != 3) {
            throw new IllegalArgumentException("result must have a length of 3.");
        }
        float f3 = 1.0f - f2;
        fArray3[0] = ColorUtils.circularInterpolate(fArray[0], fArray2[0], f2);
        fArray3[1] = fArray[1] * f3 + fArray2[1] * f2;
        fArray3[2] = fArray[2] * f3 + fArray2[2] * f2;
    }

    public static void blendLAB(@NonNull double[] dArray, @NonNull double[] dArray2, @FloatRange(from=0.0, to=1.0) double d2, @NonNull double[] dArray3) {
        if (dArray3.length != 3) {
            throw new IllegalArgumentException("outResult must have a length of 3.");
        }
        double d3 = 1.0 - d2;
        dArray3[0] = dArray[0] * d3 + dArray2[0] * d2;
        dArray3[1] = dArray[1] * d3 + dArray2[1] * d2;
        dArray3[2] = dArray[2] * d3 + dArray2[2] * d2;
    }

    public static double calculateContrast(@ColorInt int n2, @ColorInt int n3) {
        if (Color.alpha((int)n3) != 255) {
            throw new IllegalArgumentException("background can not be translucent: #" + Integer.toHexString(n3));
        }
        int n4 = n2;
        if (Color.alpha((int)n2) < 255) {
            n4 = ColorUtils.compositeColors(n2, n3);
        }
        double d2 = ColorUtils.calculateLuminance(n4) + 0.05;
        double d3 = ColorUtils.calculateLuminance(n3) + 0.05;
        return Math.max(d2, d3) / Math.min(d2, d3);
    }

    @FloatRange(from=0.0, to=1.0)
    public static double calculateLuminance(@ColorInt int n2) {
        double[] dArray = ColorUtils.getTempDouble3Array();
        ColorUtils.colorToXYZ(n2, dArray);
        return dArray[1] / 100.0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int calculateMinimumAlpha(@ColorInt int n2, @ColorInt int n3, float f2) {
        if (Color.alpha((int)n3) != 255) {
            throw new IllegalArgumentException("background can not be translucent: #" + Integer.toHexString(n3));
        }
        if (ColorUtils.calculateContrast(ColorUtils.setAlphaComponent(n2, 255), n3) < (double)f2) {
            return -1;
        }
        int n4 = 0;
        int n5 = 0;
        int n6 = 255;
        while (true) {
            int n7 = n6;
            if (n4 > 10) return n7;
            n7 = n6;
            if (n6 - n5 <= 1) return n7;
            n7 = (n5 + n6) / 2;
            if (ColorUtils.calculateContrast(ColorUtils.setAlphaComponent(n2, n7), n3) < (double)f2) {
                n5 = n7;
            } else {
                n6 = n7;
            }
            ++n4;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @VisibleForTesting
    static float circularInterpolate(float f2, float f3, float f4) {
        float f5;
        float f6;
        block4: {
            block3: {
                f6 = f2;
                f5 = f3;
                if (!(Math.abs(f3 - f2) > 180.0f)) break block3;
                if (!(f3 > f2)) break block4;
                f6 = f2 + 360.0f;
                f5 = f3;
            }
            return ((f5 - f6) * f4 + f6) % 360.0f;
        }
        f5 = f3 + 360.0f;
        f6 = f2;
        return ((f5 - f6) * f4 + f6) % 360.0f;
    }

    public static void colorToHSL(@ColorInt int n2, @NonNull float[] fArray) {
        ColorUtils.RGBToHSL(Color.red((int)n2), Color.green((int)n2), Color.blue((int)n2), fArray);
    }

    public static void colorToLAB(@ColorInt int n2, @NonNull double[] dArray) {
        ColorUtils.RGBToLAB(Color.red((int)n2), Color.green((int)n2), Color.blue((int)n2), dArray);
    }

    public static void colorToXYZ(@ColorInt int n2, @NonNull double[] dArray) {
        ColorUtils.RGBToXYZ(Color.red((int)n2), Color.green((int)n2), Color.blue((int)n2), dArray);
    }

    private static int compositeAlpha(int n2, int n3) {
        return 255 - (255 - n3) * (255 - n2) / 255;
    }

    public static int compositeColors(@ColorInt int n2, @ColorInt int n3) {
        int n4 = Color.alpha((int)n3);
        int n5 = Color.alpha((int)n2);
        int n6 = ColorUtils.compositeAlpha(n5, n4);
        return Color.argb((int)n6, (int)ColorUtils.compositeComponent(Color.red((int)n2), n5, Color.red((int)n3), n4, n6), (int)ColorUtils.compositeComponent(Color.green((int)n2), n5, Color.green((int)n3), n4, n6), (int)ColorUtils.compositeComponent(Color.blue((int)n2), n5, Color.blue((int)n3), n4, n6));
    }

    private static int compositeComponent(int n2, int n3, int n4, int n5, int n6) {
        if (n6 == 0) {
            return 0;
        }
        return (n2 * 255 * n3 + n4 * n5 * (255 - n3)) / (n6 * 255);
    }

    private static float constrain(float f2, float f3, float f4) {
        if (f2 < f3) {
            return f3;
        }
        if (f2 > f4) {
            return f4;
        }
        return f2;
    }

    private static int constrain(int n2, int n3, int n4) {
        if (n2 < n3) {
            return n3;
        }
        if (n2 > n4) {
            return n4;
        }
        return n2;
    }

    public static double distanceEuclidean(@NonNull double[] dArray, @NonNull double[] dArray2) {
        return Math.sqrt(Math.pow(dArray[0] - dArray2[0], 2.0) + Math.pow(dArray[1] - dArray2[1], 2.0) + Math.pow(dArray[2] - dArray2[2], 2.0));
    }

    private static double[] getTempDouble3Array() {
        double[] dArray;
        double[] dArray2 = dArray = TEMP_ARRAY.get();
        if (dArray == null) {
            dArray2 = new double[3];
            TEMP_ARRAY.set(dArray2);
        }
        return dArray2;
    }

    private static double pivotXyzComponent(double d2) {
        if (d2 > 0.008856) {
            return Math.pow(d2, 0.3333333333333333);
        }
        return (903.3 * d2 + 16.0) / 116.0;
    }

    @ColorInt
    public static int setAlphaComponent(@ColorInt int n2, @IntRange(from=0L, to=255L) int n3) {
        if (n3 < 0 || n3 > 255) {
            throw new IllegalArgumentException("alpha must be between 0 and 255.");
        }
        return 0xFFFFFF & n2 | n3 << 24;
    }
}

