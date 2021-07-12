/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Reader;
import com.google.zxing.oned.UPCEANReader;
import com.google.zxing.oned.UPCEANWriter;
import java.util.Map;

public final class EAN13Writer
extends UPCEANWriter {
    private static final int CODE_WIDTH = 95;

    @Override
    public BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n2, int n3, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat != BarcodeFormat.EAN_13) {
            throw new IllegalArgumentException("Can only encode EAN_13, but got " + (Object)((Object)barcodeFormat));
        }
        return super.encode(string2, barcodeFormat, n2, n3, map);
    }

    @Override
    public boolean[] encode(String string2) {
        int n2;
        if (string2.length() != 13) {
            throw new IllegalArgumentException("Requested contents should be 13 digits long, but got " + string2.length());
        }
        try {
            if (!UPCEANReader.checkStandardUPCEANChecksum(string2)) {
                throw new IllegalArgumentException("Contents do not pass checksum");
            }
        }
        catch (FormatException formatException) {
            throw new IllegalArgumentException("Illegal contents");
        }
        int n3 = Integer.parseInt(string2.substring(0, 1));
        int n4 = EAN13Reader.FIRST_DIGIT_ENCODINGS[n3];
        boolean[] blArray = new boolean[95];
        int n5 = 0 + EAN13Writer.appendPattern(blArray, 0, UPCEANReader.START_END_PATTERN, true);
        for (n3 = 1; n3 <= 6; ++n3) {
            int n6;
            n2 = n6 = Integer.parseInt(string2.substring(n3, n3 + 1));
            if ((n4 >> 6 - n3 & 1) == 1) {
                n2 = n6 + 10;
            }
            n5 += EAN13Writer.appendPattern(blArray, n5, UPCEANReader.L_AND_G_PATTERNS[n2], false);
        }
        n5 += EAN13Writer.appendPattern(blArray, n5, UPCEANReader.MIDDLE_PATTERN, false);
        for (n3 = 7; n3 <= 12; ++n3) {
            n2 = Integer.parseInt(string2.substring(n3, n3 + 1));
            n5 += EAN13Writer.appendPattern(blArray, n5, UPCEANReader.L_PATTERNS[n2], true);
        }
        EAN13Writer.appendPattern(blArray, n5, UPCEANReader.START_END_PATTERN, true);
        return blArray;
    }
}

