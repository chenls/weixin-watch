/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.qrcode.decoder.Decoder;
import com.google.zxing.qrcode.decoder.QRCodeDecoderMetaData;
import com.google.zxing.qrcode.detector.Detector;
import java.util.List;
import java.util.Map;

public class QRCodeReader
implements Reader {
    private static final ResultPoint[] NO_POINTS = new ResultPoint[0];
    private final Decoder decoder = new Decoder();

    private static BitMatrix extractPureBits(BitMatrix bitMatrix) throws NotFoundException {
        Object object = bitMatrix.getTopLeftOnBit();
        int[] nArray = bitMatrix.getBottomRightOnBit();
        if (object == null || nArray == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        float f2 = QRCodeReader.moduleSize((int[])object, bitMatrix);
        int n2 = object[1];
        int n3 = nArray[1];
        int n4 = object[0];
        int n5 = nArray[0];
        if (n4 >= n5 || n2 >= n3) {
            throw NotFoundException.getNotFoundInstance();
        }
        int n6 = n5;
        if (n3 - n2 != n5 - n4) {
            n6 = n4 + (n3 - n2);
        }
        int n7 = Math.round((float)(n6 - n4 + 1) / f2);
        int n8 = Math.round((float)(n3 - n2 + 1) / f2);
        if (n7 <= 0 || n8 <= 0) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (n8 != n7) {
            throw NotFoundException.getNotFoundInstance();
        }
        int n9 = (int)(f2 / 2.0f);
        n2 += n9;
        n5 = n4 + n9;
        n4 = (int)((float)(n7 - 1) * f2) + n5 - n6;
        n6 = n5;
        if (n4 > 0) {
            if (n4 > n9) {
                throw NotFoundException.getNotFoundInstance();
            }
            n6 = n5 - n4;
        }
        n3 = (int)((float)(n8 - 1) * f2) + n2 - n3;
        n5 = n2;
        if (n3 > 0) {
            if (n3 > n9) {
                throw NotFoundException.getNotFoundInstance();
            }
            n5 = n2 - n3;
        }
        object = new BitMatrix(n7, n8);
        for (n2 = 0; n2 < n8; ++n2) {
            n9 = (int)((float)n2 * f2);
            for (n3 = 0; n3 < n7; ++n3) {
                if (!bitMatrix.get((int)((float)n3 * f2) + n6, n5 + n9)) continue;
                ((BitMatrix)object).set(n3, n2);
            }
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static float moduleSize(int[] nArray, BitMatrix bitMatrix) throws NotFoundException {
        int n2 = bitMatrix.getHeight();
        int n3 = bitMatrix.getWidth();
        int n4 = nArray[0];
        int n5 = nArray[1];
        boolean bl2 = true;
        int n6 = 0;
        while (true) {
            int n7;
            boolean bl3;
            block6: {
                block7: {
                    block5: {
                        if (n4 >= n3 || n5 >= n2) break block5;
                        bl3 = bl2;
                        n7 = n6;
                        if (bl2 == bitMatrix.get(n4, n5)) break block6;
                        n7 = n6 + 1;
                        if (n7 != 5) break block7;
                    }
                    if (n4 != n3 && n5 != n2) {
                        return (float)(n4 - nArray[0]) / 7.0f;
                    }
                    throw NotFoundException.getNotFoundInstance();
                }
                bl3 = !bl2;
            }
            ++n4;
            ++n5;
            bl2 = bl3;
            n6 = n7;
        }
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, ChecksumException, FormatException {
        return this.decode(binaryBitmap, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final Result decode(BinaryBitmap object, Map<DecodeHintType, ?> object2) throws NotFoundException, ChecksumException, FormatException {
        List<byte[]> list;
        if (object2 != null && object2.containsKey((Object)DecodeHintType.PURE_BARCODE)) {
            object = QRCodeReader.extractPureBits(((BinaryBitmap)object).getBlackMatrix());
            object = this.decoder.decode((BitMatrix)object, (Map<DecodeHintType, ?>)object2);
            object2 = NO_POINTS;
        } else {
            list = new Detector(((BinaryBitmap)object).getBlackMatrix()).detect((Map<DecodeHintType, ?>)object2);
            object = this.decoder.decode(((DetectorResult)((Object)list)).getBits(), (Map<DecodeHintType, ?>)object2);
            object2 = ((DetectorResult)((Object)list)).getPoints();
        }
        if (((DecoderResult)object).getOther() instanceof QRCodeDecoderMetaData) {
            ((QRCodeDecoderMetaData)((DecoderResult)object).getOther()).applyMirroredCorrection((ResultPoint[])object2);
        }
        object2 = new Result(((DecoderResult)object).getText(), ((DecoderResult)object).getRawBytes(), (ResultPoint[])object2, BarcodeFormat.QR_CODE);
        list = ((DecoderResult)object).getByteSegments();
        if (list != null) {
            ((Result)object2).putMetadata(ResultMetadataType.BYTE_SEGMENTS, list);
        }
        if ((list = ((DecoderResult)object).getECLevel()) != null) {
            ((Result)object2).putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, list);
        }
        if (((DecoderResult)object).hasStructuredAppend()) {
            ((Result)object2).putMetadata(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE, ((DecoderResult)object).getStructuredAppendSequenceNumber());
            ((Result)object2).putMetadata(ResultMetadataType.STRUCTURED_APPEND_PARITY, ((DecoderResult)object).getStructuredAppendParity());
        }
        return object2;
    }

    protected final Decoder getDecoder() {
        return this.decoder;
    }

    @Override
    public void reset() {
    }
}

