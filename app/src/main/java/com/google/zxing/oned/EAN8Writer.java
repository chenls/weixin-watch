/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.UPCEANReader;
import com.google.zxing.oned.UPCEANWriter;
import java.util.Map;

public final class EAN8Writer
extends UPCEANWriter {
    private static final int CODE_WIDTH = 67;

    @Override
    public BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n2, int n3, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat != BarcodeFormat.EAN_8) {
            throw new IllegalArgumentException("Can only encode EAN_8, but got " + (Object)((Object)barcodeFormat));
        }
        return super.encode(string2, barcodeFormat, n2, n3, map);
    }

    @Override
    public boolean[] encode(String string2) {
        int n2;
        int n3;
        if (string2.length() != 8) {
            throw new IllegalArgumentException("Requested contents should be 8 digits long, but got " + string2.length());
        }
        boolean[] blArray = new boolean[67];
        int n4 = 0 + EAN8Writer.appendPattern(blArray, 0, UPCEANReader.START_END_PATTERN, true);
        for (n3 = 0; n3 <= 3; ++n3) {
            n2 = Integer.parseInt(string2.substring(n3, n3 + 1));
            n4 += EAN8Writer.appendPattern(blArray, n4, UPCEANReader.L_PATTERNS[n2], false);
        }
        n4 += EAN8Writer.appendPattern(blArray, n4, UPCEANReader.MIDDLE_PATTERN, false);
        for (n3 = 4; n3 <= 7; ++n3) {
            n2 = Integer.parseInt(string2.substring(n3, n3 + 1));
            n4 += EAN8Writer.appendPattern(blArray, n4, UPCEANReader.L_PATTERNS[n2], true);
        }
        EAN8Writer.appendPattern(blArray, n4, UPCEANReader.START_END_PATTERN, true);
        return blArray;
    }
}

