/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.aztec.AztecWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.DataMatrixWriter;
import com.google.zxing.oned.CodaBarWriter;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.Code39Writer;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.EAN8Writer;
import com.google.zxing.oned.ITFWriter;
import com.google.zxing.oned.UPCAWriter;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import java.util.Map;

public final class MultiFormatWriter
implements Writer {
    @Override
    public BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n2, int n3) throws WriterException {
        return this.encode(string2, barcodeFormat, n2, n3, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n2, int n3, Map<EncodeHintType, ?> map) throws WriterException {
        Writer writer;
        switch (1.$SwitchMap$com$google$zxing$BarcodeFormat[barcodeFormat.ordinal()]) {
            default: {
                throw new IllegalArgumentException("No encoder available for format " + (Object)((Object)barcodeFormat));
            }
            case 1: {
                writer = new EAN8Writer();
                return writer.encode(string2, barcodeFormat, n2, n3, map);
            }
            case 2: {
                writer = new EAN13Writer();
                return writer.encode(string2, barcodeFormat, n2, n3, map);
            }
            case 3: {
                writer = new UPCAWriter();
                return writer.encode(string2, barcodeFormat, n2, n3, map);
            }
            case 4: {
                writer = new QRCodeWriter();
                return writer.encode(string2, barcodeFormat, n2, n3, map);
            }
            case 5: {
                writer = new Code39Writer();
                return writer.encode(string2, barcodeFormat, n2, n3, map);
            }
            case 6: {
                writer = new Code128Writer();
                return writer.encode(string2, barcodeFormat, n2, n3, map);
            }
            case 7: {
                writer = new ITFWriter();
                return writer.encode(string2, barcodeFormat, n2, n3, map);
            }
            case 8: {
                writer = new PDF417Writer();
                return writer.encode(string2, barcodeFormat, n2, n3, map);
            }
            case 9: {
                writer = new CodaBarWriter();
                return writer.encode(string2, barcodeFormat, n2, n3, map);
            }
            case 10: {
                writer = new DataMatrixWriter();
                return writer.encode(string2, barcodeFormat, n2, n3, map);
            }
            case 11: 
        }
        writer = new AztecWriter();
        return writer.encode(string2, barcodeFormat, n2, n3, map);
    }
}

