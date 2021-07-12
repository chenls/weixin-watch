/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned;

import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.UPCEANExtension2Support;
import com.google.zxing.oned.UPCEANExtension5Support;
import com.google.zxing.oned.UPCEANReader;

final class UPCEANExtensionSupport {
    private static final int[] EXTENSION_START_PATTERN = new int[]{1, 1, 2};
    private final UPCEANExtension5Support fiveSupport;
    private final UPCEANExtension2Support twoSupport = new UPCEANExtension2Support();

    UPCEANExtensionSupport() {
        this.fiveSupport = new UPCEANExtension5Support();
    }

    Result decodeRow(int n2, BitArray bitArray, int n3) throws NotFoundException {
        int[] nArray = UPCEANReader.findGuardPattern(bitArray, n3, false, EXTENSION_START_PATTERN);
        try {
            Result result = this.fiveSupport.decodeRow(n2, bitArray, nArray);
            return result;
        }
        catch (ReaderException readerException) {
            return this.twoSupport.decodeRow(n2, bitArray, nArray);
        }
    }
}

