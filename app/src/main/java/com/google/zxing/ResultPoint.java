/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing;

import com.google.zxing.common.detector.MathUtils;

public class ResultPoint {
    private final float x;
    private final float y;

    public ResultPoint(float f2, float f3) {
        this.x = f2;
        this.y = f3;
    }

    private static float crossProductZ(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3) {
        float f2 = resultPoint2.x;
        float f3 = resultPoint2.y;
        return (resultPoint3.x - f2) * (resultPoint.y - f3) - (resultPoint3.y - f3) * (resultPoint.x - f2);
    }

    public static float distance(ResultPoint resultPoint, ResultPoint resultPoint2) {
        return MathUtils.distance(resultPoint.x, resultPoint.y, resultPoint2.x, resultPoint2.y);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void orderBestPatterns(ResultPoint[] resultPointArray) {
        ResultPoint resultPoint;
        ResultPoint resultPoint2;
        ResultPoint resultPoint3;
        float f2 = ResultPoint.distance(resultPointArray[0], resultPointArray[1]);
        float f3 = ResultPoint.distance(resultPointArray[1], resultPointArray[2]);
        float f4 = ResultPoint.distance(resultPointArray[0], resultPointArray[2]);
        if (f3 >= f2 && f3 >= f4) {
            resultPoint3 = resultPointArray[0];
            resultPoint2 = resultPointArray[1];
            resultPoint = resultPointArray[2];
        } else if (f4 >= f3 && f4 >= f2) {
            resultPoint3 = resultPointArray[1];
            resultPoint2 = resultPointArray[0];
            resultPoint = resultPointArray[2];
        } else {
            resultPoint3 = resultPointArray[2];
            resultPoint2 = resultPointArray[0];
            resultPoint = resultPointArray[1];
        }
        ResultPoint resultPoint4 = resultPoint2;
        ResultPoint resultPoint5 = resultPoint;
        if (ResultPoint.crossProductZ(resultPoint2, resultPoint3, resultPoint) < 0.0f) {
            resultPoint5 = resultPoint2;
            resultPoint4 = resultPoint;
        }
        resultPointArray[0] = resultPoint4;
        resultPointArray[1] = resultPoint3;
        resultPointArray[2] = resultPoint5;
    }

    public final boolean equals(Object object) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (object instanceof ResultPoint) {
            object = (ResultPoint)object;
            bl3 = bl2;
            if (this.x == ((ResultPoint)object).x) {
                bl3 = bl2;
                if (this.y == ((ResultPoint)object).y) {
                    bl3 = true;
                }
            }
        }
        return bl3;
    }

    public final float getX() {
        return this.x;
    }

    public final float getY() {
        return this.y;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.x) * 31 + Float.floatToIntBits(this.y);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder(25);
        stringBuilder.append('(');
        stringBuilder.append(this.x);
        stringBuilder.append(',');
        stringBuilder.append(this.y);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}

