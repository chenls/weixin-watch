/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

public abstract class OneDimensionalCodeWriter
implements Writer {
    /*
     * Enabled aggressive block sorting
     */
    protected static int appendPattern(boolean[] blArray, int n2, int[] nArray, boolean bl2) {
        int n3 = 0;
        int n4 = nArray.length;
        int n5 = 0;
        while (n5 < n4) {
            int n6 = nArray[n5];
            for (int i2 = 0; i2 < n6; ++i2, ++n2) {
                blArray[n2] = bl2;
            }
            n3 += n6;
            bl2 = !bl2;
            ++n5;
        }
        return n3;
    }

    private static BitMatrix renderResult(boolean[] blArray, int n2, int n3, int n4) {
        int n5 = blArray.length;
        int n6 = n5 + n4;
        int n7 = Math.max(n2, n6);
        n4 = Math.max(1, n3);
        n6 = n7 / n6;
        n2 = (n7 - n5 * n6) / 2;
        BitMatrix bitMatrix = new BitMatrix(n7, n4);
        n3 = 0;
        while (n3 < n5) {
            if (blArray[n3]) {
                bitMatrix.setRegion(n2, 0, n6, n4);
            }
            ++n3;
            n2 += n6;
        }
        return bitMatrix;
    }

    @Override
    public final BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n2, int n3) throws WriterException {
        return this.encode(string2, barcodeFormat, n2, n3, null);
    }

    @Override
    public BitMatrix encode(String string2, BarcodeFormat object, int n2, int n3, Map<EncodeHintType, ?> map) throws WriterException {
        int n4;
        if (string2.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }
        if (n2 < 0 || n3 < 0) {
            throw new IllegalArgumentException("Negative size is not allowed. Input: " + n2 + 'x' + n3);
        }
        int n5 = n4 = this.getDefaultMargin();
        if (map != null) {
            object = (Integer)map.get((Object)EncodeHintType.MARGIN);
            n5 = n4;
            if (object != null) {
                n5 = (Integer)object;
            }
        }
        return OneDimensionalCodeWriter.renderResult(this.encode(string2), n2, n3, n5);
    }

    public abstract boolean[] encode(String var1);

    public int getDefaultMargin() {
        return 10;
    }
}

