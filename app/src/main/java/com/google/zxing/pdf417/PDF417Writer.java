/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.pdf417;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.encoder.Compaction;
import com.google.zxing.pdf417.encoder.Dimensions;
import com.google.zxing.pdf417.encoder.PDF417;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.Map;

public final class PDF417Writer
implements Writer {
    static final int WHITE_SPACE = 30;

    /*
     * Enabled aggressive block sorting
     */
    private static BitMatrix bitMatrixFromEncoder(PDF417 object, String object2, int n2, int n3, int n4) throws WriterException {
        ((PDF417)object).generateBarcodeLogic((String)object2, 2);
        byte[][] byArray = ((PDF417)object).getBarcodeMatrix().getScaledMatrix(2, 8);
        boolean bl2 = false;
        boolean bl3 = n3 > n2;
        boolean bl4 = byArray[0].length < byArray.length;
        object2 = byArray;
        if (bl3 ^ bl4) {
            object2 = PDF417Writer.rotateArray(byArray);
            bl2 = true;
        }
        if ((n2 /= ((Object)object2[0]).length) >= (n3 /= ((Object)object2).length)) {
            n2 = n3;
        }
        if (n2 <= 1) {
            return PDF417Writer.bitMatrixFrombitArray((byte[][])object2, n4);
        }
        object = object2 = (Object)((PDF417)object).getBarcodeMatrix().getScaledMatrix(n2 * 2, n2 * 4 * 2);
        if (bl2) {
            object = PDF417Writer.rotateArray((byte[][])object2);
        }
        return PDF417Writer.bitMatrixFrombitArray((byte[][])object, n4);
    }

    private static BitMatrix bitMatrixFrombitArray(byte[][] byArray, int n2) {
        BitMatrix bitMatrix = new BitMatrix(byArray[0].length + n2 * 2, byArray.length + n2 * 2);
        bitMatrix.clear();
        int n3 = 0;
        int n4 = bitMatrix.getHeight() - n2 - 1;
        while (n3 < byArray.length) {
            for (int i2 = 0; i2 < byArray[0].length; ++i2) {
                if (byArray[n3][i2] != 1) continue;
                bitMatrix.set(i2 + n2, n4);
            }
            ++n3;
            --n4;
        }
        return bitMatrix;
    }

    private static byte[][] rotateArray(byte[][] byArray) {
        int n2 = byArray[0].length;
        int n3 = byArray.length;
        byte[][] byArray2 = (byte[][])Array.newInstance(Byte.TYPE, n2, n3);
        for (n2 = 0; n2 < byArray.length; ++n2) {
            int n4 = byArray.length;
            for (n3 = 0; n3 < byArray[0].length; ++n3) {
                byArray2[n3][n4 - n2 - 1] = byArray[n2][n3];
            }
        }
        return byArray2;
    }

    @Override
    public BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n2, int n3) throws WriterException {
        return this.encode(string2, barcodeFormat, n2, n3, null);
    }

    @Override
    public BitMatrix encode(String string2, BarcodeFormat object, int n2, int n3, Map<EncodeHintType, ?> map) throws WriterException {
        int n4;
        if (object != BarcodeFormat.PDF_417) {
            throw new IllegalArgumentException("Can only encode PDF_417, but got " + object);
        }
        object = new PDF417();
        int n5 = n4 = 30;
        if (map != null) {
            if (map.containsKey((Object)EncodeHintType.PDF417_COMPACT)) {
                ((PDF417)object).setCompact((Boolean)map.get((Object)EncodeHintType.PDF417_COMPACT));
            }
            if (map.containsKey((Object)EncodeHintType.PDF417_COMPACTION)) {
                ((PDF417)object).setCompaction((Compaction)((Object)map.get((Object)EncodeHintType.PDF417_COMPACTION)));
            }
            if (map.containsKey((Object)EncodeHintType.PDF417_DIMENSIONS)) {
                Dimensions dimensions = (Dimensions)map.get((Object)EncodeHintType.PDF417_DIMENSIONS);
                ((PDF417)object).setDimensions(dimensions.getMaxCols(), dimensions.getMinCols(), dimensions.getMaxRows(), dimensions.getMinRows());
            }
            if (map.containsKey((Object)EncodeHintType.MARGIN)) {
                n4 = ((Number)map.get((Object)EncodeHintType.MARGIN)).intValue();
            }
            n5 = n4;
            if (map.containsKey((Object)EncodeHintType.CHARACTER_SET)) {
                ((PDF417)object).setEncoding(Charset.forName((String)map.get((Object)EncodeHintType.CHARACTER_SET)));
                n5 = n4;
            }
        }
        return PDF417Writer.bitMatrixFromEncoder((PDF417)object, string2, n2, n3, n5);
    }
}

