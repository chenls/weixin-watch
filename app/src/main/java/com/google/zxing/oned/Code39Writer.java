/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code39Reader;
import com.google.zxing.oned.OneDimensionalCodeWriter;
import java.util.Map;

public final class Code39Writer
extends OneDimensionalCodeWriter {
    /*
     * Enabled aggressive block sorting
     */
    private static void toIntArray(int n2, int[] nArray) {
        int n3 = 0;
        while (n3 < 9) {
            int n4 = (n2 & 1 << 8 - n3) == 0 ? 1 : 2;
            nArray[n3] = n4;
            ++n3;
        }
        return;
    }

    @Override
    public BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n2, int n3, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat != BarcodeFormat.CODE_39) {
            throw new IllegalArgumentException("Can only encode CODE_39, but got " + (Object)((Object)barcodeFormat));
        }
        return super.encode(string2, barcodeFormat, n2, n3, map);
    }

    @Override
    public boolean[] encode(String string2) {
        int n2;
        int n3;
        int n4 = string2.length();
        if (n4 > 80) {
            throw new IllegalArgumentException("Requested contents should be less than 80 digits long, but got " + n4);
        }
        int[] nArray = new int[9];
        int n5 = n4 + 25;
        for (n3 = 0; n3 < n4; ++n3) {
            n2 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf(string2.charAt(n3));
            if (n2 < 0) {
                throw new IllegalArgumentException("Bad contents: " + string2);
            }
            Code39Writer.toIntArray(Code39Reader.CHARACTER_ENCODINGS[n2], nArray);
            int n6 = nArray.length;
            for (n2 = 0; n2 < n6; ++n2) {
                n5 += nArray[n2];
            }
        }
        boolean[] blArray = new boolean[n5];
        Code39Writer.toIntArray(Code39Reader.CHARACTER_ENCODINGS[39], nArray);
        n3 = Code39Writer.appendPattern(blArray, 0, nArray, true);
        int[] nArray2 = new int[]{1};
        n5 = n3 + Code39Writer.appendPattern(blArray, n3, nArray2, false);
        for (n3 = 0; n3 < n4; ++n3) {
            n2 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf(string2.charAt(n3));
            Code39Writer.toIntArray(Code39Reader.CHARACTER_ENCODINGS[n2], nArray);
            n5 += Code39Writer.appendPattern(blArray, n5, nArray, true);
            n5 += Code39Writer.appendPattern(blArray, n5, nArray2, false);
        }
        Code39Writer.toIntArray(Code39Reader.CHARACTER_ENCODINGS[39], nArray);
        Code39Writer.appendPattern(blArray, n5, nArray, true);
        return blArray;
    }
}

