/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.expanded.decoders.AI01decoder;

abstract class AI01weightDecoder
extends AI01decoder {
    AI01weightDecoder(BitArray bitArray) {
        super(bitArray);
    }

    protected abstract void addWeightCode(StringBuilder var1, int var2);

    protected abstract int checkWeight(int var1);

    protected final void encodeCompressedWeight(StringBuilder stringBuilder, int n2, int n3) {
        n2 = this.getGeneralDecoder().extractNumericValueFromBitArray(n2, n3);
        this.addWeightCode(stringBuilder, n2);
        int n4 = this.checkWeight(n2);
        n3 = 100000;
        for (n2 = 0; n2 < 5; ++n2) {
            if (n4 / n3 == 0) {
                stringBuilder.append('0');
            }
            n3 /= 10;
        }
        stringBuilder.append(n4);
    }
}

