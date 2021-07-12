/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
import java.util.Map;

public final class QRCodeWriter
implements Writer {
    private static final int QUIET_ZONE_SIZE = 4;

    private static BitMatrix renderResult(QRCode object, int n2, int n3, int n4) {
        if ((object = ((QRCode)object).getMatrix()) == null) {
            throw new IllegalStateException();
        }
        int n5 = ((ByteMatrix)object).getWidth();
        int n6 = ((ByteMatrix)object).getHeight();
        int n7 = n5 + n4 * 2;
        int n8 = n6 + n4 * 2;
        n4 = Math.max(n2, n7);
        n3 = Math.max(n3, n8);
        int n9 = Math.min(n4 / n7, n3 / n8);
        n8 = (n4 - n5 * n9) / 2;
        n2 = (n3 - n6 * n9) / 2;
        BitMatrix bitMatrix = new BitMatrix(n4, n3);
        n3 = 0;
        while (n3 < n6) {
            n7 = 0;
            n4 = n8;
            while (n7 < n5) {
                if (((ByteMatrix)object).get(n7, n3) == 1) {
                    bitMatrix.setRegion(n4, n2, n9, n9);
                }
                ++n7;
                n4 += n9;
            }
            ++n3;
            n2 += n9;
        }
        return bitMatrix;
    }

    @Override
    public BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n2, int n3) throws WriterException {
        return this.encode(string2, barcodeFormat, n2, n3, null);
    }

    @Override
    public BitMatrix encode(String string2, BarcodeFormat enum_, int n2, int n3, Map<EncodeHintType, ?> map) throws WriterException {
        if (string2.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }
        if (enum_ != BarcodeFormat.QR_CODE) {
            throw new IllegalArgumentException("Can only encode QR_CODE, but got " + enum_);
        }
        if (n2 < 0 || n3 < 0) {
            throw new IllegalArgumentException("Requested dimensions are too small: " + n2 + 'x' + n3);
        }
        enum_ = ErrorCorrectionLevel.L;
        int n4 = 4;
        Enum enum_2 = enum_;
        int n5 = n4;
        if (map != null) {
            enum_2 = (ErrorCorrectionLevel)((Object)map.get((Object)EncodeHintType.ERROR_CORRECTION));
            if (enum_2 != null) {
                enum_ = enum_2;
            }
            Integer n6 = (Integer)map.get((Object)EncodeHintType.MARGIN);
            enum_2 = enum_;
            n5 = n4;
            if (n6 != null) {
                n5 = n6;
                enum_2 = enum_;
            }
        }
        return QRCodeWriter.renderResult(Encoder.encode(string2, (ErrorCorrectionLevel)enum_2, map), n2, n3, n5);
    }
}

