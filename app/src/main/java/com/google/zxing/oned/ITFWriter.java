/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.ITFReader;
import com.google.zxing.oned.OneDimensionalCodeWriter;
import java.util.Map;

public final class ITFWriter
extends OneDimensionalCodeWriter {
    private static final int[] END_PATTERN;
    private static final int[] START_PATTERN;

    static {
        START_PATTERN = new int[]{1, 1, 1, 1};
        END_PATTERN = new int[]{3, 1, 1};
    }

    @Override
    public BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n2, int n3, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat != BarcodeFormat.ITF) {
            throw new IllegalArgumentException("Can only encode ITF, but got " + (Object)((Object)barcodeFormat));
        }
        return super.encode(string2, barcodeFormat, n2, n3, map);
    }

    @Override
    public boolean[] encode(String string2) {
        int n2 = string2.length();
        if (n2 % 2 != 0) {
            throw new IllegalArgumentException("The lenght of the input should be even");
        }
        if (n2 > 80) {
            throw new IllegalArgumentException("Requested contents should be less than 80 digits long, but got " + n2);
        }
        boolean[] blArray = new boolean[n2 * 9 + 9];
        int n3 = ITFWriter.appendPattern(blArray, 0, START_PATTERN, true);
        for (int i2 = 0; i2 < n2; i2 += 2) {
            int n4 = Character.digit(string2.charAt(i2), 10);
            int n5 = Character.digit(string2.charAt(i2 + 1), 10);
            int[] nArray = new int[18];
            for (int i3 = 0; i3 < 5; ++i3) {
                nArray[i3 * 2] = ITFReader.PATTERNS[n4][i3];
                nArray[i3 * 2 + 1] = ITFReader.PATTERNS[n5][i3];
            }
            n3 += ITFWriter.appendPattern(blArray, n3, nArray, true);
        }
        ITFWriter.appendPattern(blArray, n3, END_PATTERN, true);
        return blArray;
    }
}

