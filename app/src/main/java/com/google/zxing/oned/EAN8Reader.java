/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.UPCEANReader;

public final class EAN8Reader
extends UPCEANReader {
    private final int[] decodeMiddleCounters = new int[4];

    @Override
    protected int decodeMiddle(BitArray bitArray, int[] nArray, StringBuilder stringBuilder) throws NotFoundException {
        int n2;
        int n3;
        int n4;
        int[] nArray2 = this.decodeMiddleCounters;
        nArray2[0] = 0;
        nArray2[1] = 0;
        nArray2[2] = 0;
        nArray2[3] = 0;
        int n5 = bitArray.getSize();
        int n6 = nArray[1];
        for (n4 = 0; n4 < 4 && n6 < n5; ++n4) {
            stringBuilder.append((char)(EAN8Reader.decodeDigit(bitArray, nArray2, n6, L_PATTERNS) + 48));
            n3 = nArray2.length;
            for (n2 = 0; n2 < n3; ++n2) {
                n6 += nArray2[n2];
            }
        }
        n6 = EAN8Reader.findGuardPattern(bitArray, n6, true, MIDDLE_PATTERN)[1];
        for (n4 = 0; n4 < 4 && n6 < n5; ++n4) {
            stringBuilder.append((char)(EAN8Reader.decodeDigit(bitArray, nArray2, n6, L_PATTERNS) + 48));
            n3 = nArray2.length;
            for (n2 = 0; n2 < n3; ++n2) {
                n6 += nArray2[n2];
            }
        }
        return n6;
    }

    @Override
    BarcodeFormat getBarcodeFormat() {
        return BarcodeFormat.EAN_8;
    }
}

