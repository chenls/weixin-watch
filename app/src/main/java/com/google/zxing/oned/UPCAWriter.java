/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import java.util.Map;

public final class UPCAWriter
implements Writer {
    private final EAN13Writer subWriter = new EAN13Writer();

    /*
     * Enabled aggressive block sorting
     */
    private static String preencode(String string2) {
        String string3;
        int n2 = string2.length();
        if (n2 != 11) {
            string3 = string2;
            if (n2 == 12) return '0' + string3;
            throw new IllegalArgumentException("Requested contents should be 11 or 12 digits long, but got " + string2.length());
        } else {
            char c2;
            int n3;
            int n4 = 0;
            for (n2 = 0; n2 < 11; n4 += n3 * (c2 - 48), ++n2) {
                c2 = string2.charAt(n2);
                n3 = n2 % 2 == 0 ? 3 : 1;
            }
            string3 = string2 + (1000 - n4) % 10;
        }
        return '0' + string3;
    }

    @Override
    public BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n2, int n3) throws WriterException {
        return this.encode(string2, barcodeFormat, n2, n3, null);
    }

    @Override
    public BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n2, int n3, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat != BarcodeFormat.UPC_A) {
            throw new IllegalArgumentException("Can only encode UPC-A, but got " + (Object)((Object)barcodeFormat));
        }
        return this.subWriter.encode(UPCAWriter.preencode(string2), BarcodeFormat.EAN_13, n2, n3, map);
    }
}

