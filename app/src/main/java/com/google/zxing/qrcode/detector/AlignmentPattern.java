/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.qrcode.detector;

import com.google.zxing.ResultPoint;

public final class AlignmentPattern
extends ResultPoint {
    private final float estimatedModuleSize;

    AlignmentPattern(float f2, float f3, float f4) {
        super(f2, f3);
        this.estimatedModuleSize = f4;
    }

    boolean aboutEquals(float f2, float f3, float f4) {
        boolean bl2;
        block2: {
            block3: {
                boolean bl3;
                bl2 = bl3 = false;
                if (!(Math.abs(f3 - this.getY()) <= f2)) break block2;
                bl2 = bl3;
                if (!(Math.abs(f4 - this.getX()) <= f2)) break block2;
                if ((f2 = Math.abs(f2 - this.estimatedModuleSize)) <= 1.0f) break block3;
                bl2 = bl3;
                if (!(f2 <= this.estimatedModuleSize)) break block2;
            }
            bl2 = true;
        }
        return bl2;
    }

    AlignmentPattern combineEstimate(float f2, float f3, float f4) {
        return new AlignmentPattern((this.getX() + f3) / 2.0f, (this.getY() + f2) / 2.0f, (this.estimatedModuleSize + f4) / 2.0f);
    }
}

