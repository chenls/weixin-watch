/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder;

abstract class AI01decoder
extends AbstractExpandedDecoder {
    protected static final int GTIN_SIZE = 40;

    AI01decoder(BitArray bitArray) {
        super(bitArray);
    }

    private static void appendCheckDigit(StringBuilder stringBuilder, int n2) {
        int n3;
        int n4 = 0;
        for (n3 = 0; n3 < 13; ++n3) {
            int n5;
            int n6 = n5 = stringBuilder.charAt(n3 + n2) - 48;
            if ((n3 & 1) == 0) {
                n6 = n5 * 3;
            }
            n4 += n6;
        }
        n2 = n3 = 10 - n4 % 10;
        if (n3 == 10) {
            n2 = 0;
        }
        stringBuilder.append(n2);
    }

    protected final void encodeCompressedGtin(StringBuilder stringBuilder, int n2) {
        stringBuilder.append("(01)");
        int n3 = stringBuilder.length();
        stringBuilder.append('9');
        this.encodeCompressedGtinWithoutAI(stringBuilder, n2, n3);
    }

    protected final void encodeCompressedGtinWithoutAI(StringBuilder stringBuilder, int n2, int n3) {
        for (int i2 = 0; i2 < 4; ++i2) {
            int n4 = this.getGeneralDecoder().extractNumericValueFromBitArray(i2 * 10 + n2, 10);
            if (n4 / 100 == 0) {
                stringBuilder.append('0');
            }
            if (n4 / 10 == 0) {
                stringBuilder.append('0');
            }
            stringBuilder.append(n4);
        }
        AI01decoder.appendCheckDigit(stringBuilder, n3);
    }
}

