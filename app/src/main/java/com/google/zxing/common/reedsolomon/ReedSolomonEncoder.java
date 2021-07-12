/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.common.reedsolomon;

import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.GenericGFPoly;
import java.util.ArrayList;
import java.util.List;

public final class ReedSolomonEncoder {
    private final List<GenericGFPoly> cachedGenerators;
    private final GenericGF field;

    public ReedSolomonEncoder(GenericGF genericGF) {
        this.field = genericGF;
        this.cachedGenerators = new ArrayList<GenericGFPoly>();
        this.cachedGenerators.add(new GenericGFPoly(genericGF, new int[]{1}));
    }

    private GenericGFPoly buildGenerator(int n2) {
        if (n2 >= this.cachedGenerators.size()) {
            GenericGFPoly genericGFPoly = this.cachedGenerators.get(this.cachedGenerators.size() - 1);
            for (int i2 = this.cachedGenerators.size(); i2 <= n2; ++i2) {
                genericGFPoly = genericGFPoly.multiply(new GenericGFPoly(this.field, new int[]{1, this.field.exp(i2 - 1 + this.field.getGeneratorBase())}));
                this.cachedGenerators.add(genericGFPoly);
            }
        }
        return this.cachedGenerators.get(n2);
    }

    public void encode(int[] nArray, int n2) {
        if (n2 == 0) {
            throw new IllegalArgumentException("No error correction bytes");
        }
        int n3 = nArray.length - n2;
        if (n3 <= 0) {
            throw new IllegalArgumentException("No data bytes provided");
        }
        Object object = this.buildGenerator(n2);
        int[] nArray2 = new int[n3];
        System.arraycopy(nArray, 0, nArray2, 0, n3);
        object = new GenericGFPoly(this.field, nArray2).multiplyByMonomial(n2, 1).divide((GenericGFPoly)object)[1].getCoefficients();
        int n4 = n2 - ((Object)object).length;
        for (n2 = 0; n2 < n4; ++n2) {
            nArray[n3 + n2] = 0;
        }
        System.arraycopy(object, 0, nArray, n3 + n4, ((Object)object).length);
    }
}

