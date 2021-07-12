/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.aztec;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.aztec.encoder.AztecCode;
import com.google.zxing.aztec.encoder.Encoder;
import com.google.zxing.common.BitMatrix;
import java.nio.charset.Charset;
import java.util.Map;

public final class AztecWriter
implements Writer {
    private static final Charset DEFAULT_CHARSET = Charset.forName("ISO-8859-1");

    private static BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n2, int n3, Charset charset, int n4, int n5) {
        if (barcodeFormat != BarcodeFormat.AZTEC) {
            throw new IllegalArgumentException("Can only encode AZTEC, but got " + (Object)((Object)barcodeFormat));
        }
        return AztecWriter.renderResult(Encoder.encode(string2.getBytes(charset), n4, n5), n2, n3);
    }

    private static BitMatrix renderResult(AztecCode object, int n2, int n3) {
        if ((object = ((AztecCode)object).getMatrix()) == null) {
            throw new IllegalStateException();
        }
        int n4 = ((BitMatrix)object).getWidth();
        int n5 = ((BitMatrix)object).getHeight();
        int n6 = Math.max(n2, n4);
        n3 = Math.max(n3, n5);
        int n7 = Math.min(n6 / n4, n3 / n5);
        int n8 = (n6 - n4 * n7) / 2;
        n2 = (n3 - n5 * n7) / 2;
        BitMatrix bitMatrix = new BitMatrix(n6, n3);
        n3 = 0;
        while (n3 < n5) {
            int n9 = 0;
            n6 = n8;
            while (n9 < n4) {
                if (((BitMatrix)object).get(n9, n3)) {
                    bitMatrix.setRegion(n6, n2, n7, n7);
                }
                ++n9;
                n6 += n7;
            }
            ++n3;
            n2 += n7;
        }
        return bitMatrix;
    }

    @Override
    public BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n2, int n3) {
        return this.encode(string2, barcodeFormat, n2, n3, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n2, int n3, Map<EncodeHintType, ?> object) {
        int n4;
        Object var10_6 = null;
        Object object2 = object == null ? null : (String)object.get((Object)EncodeHintType.CHARACTER_SET);
        Number number = object == null ? (Number)null : (Number)((Number)object.get((Object)EncodeHintType.ERROR_CORRECTION));
        object = object == null ? var10_6 : (Number)object.get((Object)EncodeHintType.AZTEC_LAYERS);
        object2 = object2 == null ? DEFAULT_CHARSET : Charset.forName((String)object2);
        int n5 = number == null ? 33 : number.intValue();
        if (object == null) {
            n4 = 0;
            return AztecWriter.encode(string2, barcodeFormat, n2, n3, (Charset)object2, n5, n4);
        }
        n4 = ((Number)object).intValue();
        return AztecWriter.encode(string2, barcodeFormat, n2, n3, (Charset)object2, n5, n4);
    }
}

