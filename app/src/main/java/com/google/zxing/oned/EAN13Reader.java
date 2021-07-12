/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.UPCEANReader;

public final class EAN13Reader
extends UPCEANReader {
    static final int[] FIRST_DIGIT_ENCODINGS = new int[]{0, 11, 13, 14, 19, 25, 28, 21, 22, 26};
    private final int[] decodeMiddleCounters = new int[4];

    private static void determineFirstDigit(StringBuilder stringBuilder, int n2) throws NotFoundException {
        for (int i2 = 0; i2 < 10; ++i2) {
            if (n2 != FIRST_DIGIT_ENCODINGS[i2]) continue;
            stringBuilder.insert(0, (char)(i2 + 48));
            return;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override
    protected int decodeMiddle(BitArray bitArray, int[] nArray, StringBuilder stringBuilder) throws NotFoundException {
        int n2;
        int n3;
        int[] nArray2 = this.decodeMiddleCounters;
        nArray2[0] = 0;
        nArray2[1] = 0;
        nArray2[2] = 0;
        nArray2[3] = 0;
        int n4 = bitArray.getSize();
        int n5 = nArray[1];
        int n6 = 0;
        for (n3 = 0; n3 < 6 && n5 < n4; ++n3) {
            int n7 = EAN13Reader.decodeDigit(bitArray, nArray2, n5, L_AND_G_PATTERNS);
            stringBuilder.append((char)(n7 % 10 + 48));
            int n8 = nArray2.length;
            for (n2 = 0; n2 < n8; ++n2) {
                n5 += nArray2[n2];
            }
            n2 = n6;
            if (n7 >= 10) {
                n2 = n6 | 1 << 5 - n3;
            }
            n6 = n2;
        }
        EAN13Reader.determineFirstDigit(stringBuilder, n6);
        n6 = EAN13Reader.findGuardPattern(bitArray, n5, true, MIDDLE_PATTERN)[1];
        for (n3 = 0; n3 < 6 && n6 < n4; ++n3) {
            stringBuilder.append((char)(EAN13Reader.decodeDigit(bitArray, nArray2, n6, L_PATTERNS) + 48));
            n2 = nArray2.length;
            for (n5 = 0; n5 < n2; ++n5) {
                n6 += nArray2[n5];
            }
        }
        return n6;
    }

    @Override
    BarcodeFormat getBarcodeFormat() {
        return BarcodeFormat.EAN_13;
    }
}

