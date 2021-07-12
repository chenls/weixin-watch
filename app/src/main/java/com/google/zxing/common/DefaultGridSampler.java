/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.common;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.PerspectiveTransform;

public final class DefaultGridSampler
extends GridSampler {
    @Override
    public BitMatrix sampleGrid(BitMatrix bitMatrix, int n2, int n3, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16, float f17) throws NotFoundException {
        return this.sampleGrid(bitMatrix, n2, n3, PerspectiveTransform.quadrilateralToQuadrilateral(f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16, f17));
    }

    @Override
    public BitMatrix sampleGrid(BitMatrix bitMatrix, int n2, int n3, PerspectiveTransform perspectiveTransform) throws NotFoundException {
        if (n2 <= 0 || n3 <= 0) {
            throw NotFoundException.getNotFoundInstance();
        }
        BitMatrix bitMatrix2 = new BitMatrix(n2, n3);
        float[] fArray = new float[n2 * 2];
        for (n2 = 0; n2 < n3; ++n2) {
            int n4;
            int n5 = fArray.length;
            float f2 = n2;
            for (n4 = 0; n4 < n5; n4 += 2) {
                fArray[n4] = (float)(n4 / 2) + 0.5f;
                fArray[n4 + 1] = f2 + 0.5f;
            }
            perspectiveTransform.transformPoints(fArray);
            DefaultGridSampler.checkAndNudgePoints(bitMatrix, fArray);
            for (n4 = 0; n4 < n5; n4 += 2) {
                try {
                    if (!bitMatrix.get((int)fArray[n4], (int)fArray[n4 + 1])) continue;
                    bitMatrix2.set(n4 / 2, n2);
                    continue;
                }
                catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                    throw NotFoundException.getNotFoundInstance();
                }
            }
        }
        return bitMatrix2;
    }
}

