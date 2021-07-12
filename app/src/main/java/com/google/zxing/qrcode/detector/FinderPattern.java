/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.qrcode.detector;

import com.google.zxing.ResultPoint;

public final class FinderPattern
extends ResultPoint {
    private final int count;
    private final float estimatedModuleSize;

    FinderPattern(float f2, float f3, float f4) {
        this(f2, f3, f4, 1);
    }

    private FinderPattern(float f2, float f3, float f4, int n2) {
        super(f2, f3);
        this.estimatedModuleSize = f4;
        this.count = n2;
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

    FinderPattern combineEstimate(float f2, float f3, float f4) {
        int n2 = this.count + 1;
        return new FinderPattern(((float)this.count * this.getX() + f3) / (float)n2, ((float)this.count * this.getY() + f2) / (float)n2, ((float)this.count * this.estimatedModuleSize + f4) / (float)n2, n2);
    }

    int getCount() {
        return this.count;
    }

    public float getEstimatedModuleSize() {
        return this.estimatedModuleSize;
    }
}

