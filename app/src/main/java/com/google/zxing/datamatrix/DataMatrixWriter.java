/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.datamatrix;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Dimension;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.encoder.DefaultPlacement;
import com.google.zxing.datamatrix.encoder.ErrorCorrection;
import com.google.zxing.datamatrix.encoder.HighLevelEncoder;
import com.google.zxing.datamatrix.encoder.SymbolInfo;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import java.util.Map;

public final class DataMatrixWriter
implements Writer {
    private static BitMatrix convertByteMatrixToBitMatrix(ByteMatrix byteMatrix) {
        int n2 = byteMatrix.getWidth();
        int n3 = byteMatrix.getHeight();
        BitMatrix bitMatrix = new BitMatrix(n2, n3);
        bitMatrix.clear();
        for (int i2 = 0; i2 < n2; ++i2) {
            for (int i3 = 0; i3 < n3; ++i3) {
                if (byteMatrix.get(i2, i3) != 1) continue;
                bitMatrix.set(i2, i3);
            }
        }
        return bitMatrix;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static BitMatrix encodeLowLevel(DefaultPlacement defaultPlacement, SymbolInfo symbolInfo) {
        int n2 = symbolInfo.getSymbolDataWidth();
        int n3 = symbolInfo.getSymbolDataHeight();
        ByteMatrix byteMatrix = new ByteMatrix(symbolInfo.getSymbolWidth(), symbolInfo.getSymbolHeight());
        int n4 = 0;
        int n5 = 0;
        while (n5 < n3) {
            boolean bl2;
            int n6;
            int n7 = n4;
            if (n5 % symbolInfo.matrixHeight == 0) {
                n6 = 0;
                for (n7 = 0; n7 < symbolInfo.getSymbolWidth(); ++n6, ++n7) {
                    bl2 = n7 % 2 == 0;
                    byteMatrix.set(n6, n4, bl2);
                }
                n7 = n4 + 1;
            }
            n4 = 0;
            for (n6 = 0; n6 < n2; ++n6) {
                int n8 = n4;
                if (n6 % symbolInfo.matrixWidth == 0) {
                    byteMatrix.set(n4, n7, true);
                    n8 = n4 + 1;
                }
                byteMatrix.set(n8, n7, defaultPlacement.getBit(n6, n5));
                n4 = ++n8;
                if (n6 % symbolInfo.matrixWidth != symbolInfo.matrixWidth - 1) continue;
                bl2 = n5 % 2 == 0;
                byteMatrix.set(n8, n7, bl2);
                n4 = n8 + 1;
            }
            n4 = n6 = n7 + 1;
            if (n5 % symbolInfo.matrixHeight == symbolInfo.matrixHeight - 1) {
                n7 = 0;
                for (n4 = 0; n4 < symbolInfo.getSymbolWidth(); ++n7, ++n4) {
                    byteMatrix.set(n7, n6, true);
                }
                n4 = n6 + 1;
            }
            ++n5;
        }
        return DataMatrixWriter.convertByteMatrixToBitMatrix(byteMatrix);
    }

    @Override
    public BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n2, int n3) {
        return this.encode(string2, barcodeFormat, n2, n3, null);
    }

    @Override
    public BitMatrix encode(String object, BarcodeFormat object2, int n2, int n3, Map<EncodeHintType, ?> object3) {
        Object object4;
        if (((String)object).isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }
        if (object2 != BarcodeFormat.DATA_MATRIX) {
            throw new IllegalArgumentException("Can only encode DATA_MATRIX, but got " + object2);
        }
        if (n2 < 0 || n3 < 0) {
            throw new IllegalArgumentException("Requested dimensions are too small: " + n2 + 'x' + n3);
        }
        object2 = SymbolShapeHint.FORCE_NONE;
        Dimension dimension = new Dimension(n2, n3);
        Object object5 = object4 = null;
        Object object6 = dimension;
        Object object7 = object2;
        if (object3 != null) {
            object6 = (SymbolShapeHint)((Object)object3.get((Object)EncodeHintType.DATA_MATRIX_SHAPE));
            if (object6 != null) {
                object2 = object6;
            }
            if ((object6 = (Dimension)object3.get((Object)EncodeHintType.MIN_SIZE)) != null) {
                dimension = object6;
            }
            object3 = (Dimension)object3.get((Object)EncodeHintType.MAX_SIZE);
            object5 = object4;
            object6 = dimension;
            object7 = object2;
            if (object3 != null) {
                object5 = object3;
                object7 = object2;
                object6 = dimension;
            }
        }
        object = HighLevelEncoder.encodeHighLevel((String)object, (SymbolShapeHint)((Object)object7), object6, object5);
        object2 = SymbolInfo.lookup(((String)object).length(), (SymbolShapeHint)((Object)object7), object6, object5, true);
        object = new DefaultPlacement(ErrorCorrection.encodeECC200((String)object, (SymbolInfo)object2), ((SymbolInfo)object2).getSymbolDataWidth(), ((SymbolInfo)object2).getSymbolDataHeight());
        ((DefaultPlacement)object).place();
        return DataMatrixWriter.encodeLowLevel((DefaultPlacement)object, (SymbolInfo)object2);
    }
}

