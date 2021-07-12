/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.common;

public final class PerspectiveTransform {
    private final float a11;
    private final float a12;
    private final float a13;
    private final float a21;
    private final float a22;
    private final float a23;
    private final float a31;
    private final float a32;
    private final float a33;

    private PerspectiveTransform(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
        this.a11 = f2;
        this.a12 = f5;
        this.a13 = f8;
        this.a21 = f3;
        this.a22 = f6;
        this.a23 = f9;
        this.a31 = f4;
        this.a32 = f7;
        this.a33 = f10;
    }

    public static PerspectiveTransform quadrilateralToQuadrilateral(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16, float f17) {
        PerspectiveTransform perspectiveTransform = PerspectiveTransform.quadrilateralToSquare(f2, f3, f4, f5, f6, f7, f8, f9);
        return PerspectiveTransform.squareToQuadrilateral(f10, f11, f12, f13, f14, f15, f16, f17).times(perspectiveTransform);
    }

    public static PerspectiveTransform quadrilateralToSquare(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        return PerspectiveTransform.squareToQuadrilateral(f2, f3, f4, f5, f6, f7, f8, f9).buildAdjoint();
    }

    public static PerspectiveTransform squareToQuadrilateral(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        float f10 = f2 - f4 + f6 - f8;
        float f11 = f3 - f5 + f7 - f9;
        if (f10 == 0.0f && f11 == 0.0f) {
            return new PerspectiveTransform(f4 - f2, f6 - f4, f2, f5 - f3, f7 - f5, f3, 0.0f, 0.0f, 1.0f);
        }
        float f12 = f4 - f6;
        float f13 = f8 - f6;
        f6 = f5 - f7;
        float f14 = f9 - f7;
        f7 = f12 * f14 - f13 * f6;
        f13 = (f10 * f14 - f13 * f11) / f7;
        f6 = (f12 * f11 - f10 * f6) / f7;
        return new PerspectiveTransform(f4 - f2 + f13 * f4, f8 - f2 + f6 * f8, f2, f13 * f5 + (f5 - f3), f6 * f9 + (f9 - f3), f3, f13, f6, 1.0f);
    }

    PerspectiveTransform buildAdjoint() {
        return new PerspectiveTransform(this.a22 * this.a33 - this.a23 * this.a32, this.a23 * this.a31 - this.a21 * this.a33, this.a21 * this.a32 - this.a22 * this.a31, this.a13 * this.a32 - this.a12 * this.a33, this.a11 * this.a33 - this.a13 * this.a31, this.a12 * this.a31 - this.a11 * this.a32, this.a12 * this.a23 - this.a13 * this.a22, this.a13 * this.a21 - this.a11 * this.a23, this.a11 * this.a22 - this.a12 * this.a21);
    }

    PerspectiveTransform times(PerspectiveTransform perspectiveTransform) {
        return new PerspectiveTransform(this.a11 * perspectiveTransform.a11 + this.a21 * perspectiveTransform.a12 + this.a31 * perspectiveTransform.a13, this.a11 * perspectiveTransform.a21 + this.a21 * perspectiveTransform.a22 + this.a31 * perspectiveTransform.a23, this.a11 * perspectiveTransform.a31 + this.a21 * perspectiveTransform.a32 + this.a31 * perspectiveTransform.a33, this.a12 * perspectiveTransform.a11 + this.a22 * perspectiveTransform.a12 + this.a32 * perspectiveTransform.a13, this.a12 * perspectiveTransform.a21 + this.a22 * perspectiveTransform.a22 + this.a32 * perspectiveTransform.a23, this.a12 * perspectiveTransform.a31 + this.a22 * perspectiveTransform.a32 + this.a32 * perspectiveTransform.a33, this.a13 * perspectiveTransform.a11 + this.a23 * perspectiveTransform.a12 + this.a33 * perspectiveTransform.a13, this.a13 * perspectiveTransform.a21 + this.a23 * perspectiveTransform.a22 + this.a33 * perspectiveTransform.a23, this.a13 * perspectiveTransform.a31 + this.a23 * perspectiveTransform.a32 + this.a33 * perspectiveTransform.a33);
    }

    public void transformPoints(float[] fArray) {
        int n2 = fArray.length;
        float f2 = this.a11;
        float f3 = this.a12;
        float f4 = this.a13;
        float f5 = this.a21;
        float f6 = this.a22;
        float f7 = this.a23;
        float f8 = this.a31;
        float f9 = this.a32;
        float f10 = this.a33;
        for (int i2 = 0; i2 < n2; i2 += 2) {
            float f11 = fArray[i2];
            float f12 = fArray[i2 + 1];
            float f13 = f4 * f11 + f7 * f12 + f10;
            fArray[i2] = (f2 * f11 + f5 * f12 + f8) / f13;
            fArray[i2 + 1] = (f3 * f11 + f6 * f12 + f9) / f13;
        }
    }

    public void transformPoints(float[] fArray, float[] fArray2) {
        int n2 = fArray.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            float f2 = fArray[i2];
            float f3 = fArray2[i2];
            float f4 = this.a13 * f2 + this.a23 * f3 + this.a33;
            fArray[i2] = (this.a11 * f2 + this.a21 * f3 + this.a31) / f4;
            fArray2[i2] = (this.a12 * f2 + this.a22 * f3 + this.a32) / f4;
        }
    }
}

