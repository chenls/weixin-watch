/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.datamatrix;

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
import com.google.zxing.datamatrix.decoder.Decoder;
import com.google.zxing.datamatrix.detector.Detector;
import java.util.List;
import java.util.Map;

public final class DataMatrixReader
implements Reader {
    private static final ResultPoint[] NO_POINTS = new ResultPoint[0];
    private final Decoder decoder = new Decoder();

    private static BitMatrix extractPureBits(BitMatrix bitMatrix) throws NotFoundException {
        Object object = bitMatrix.getTopLeftOnBit();
        int[] nArray = bitMatrix.getBottomRightOnBit();
        if (object == null || nArray == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        int n2 = DataMatrixReader.moduleSize((int[])object, bitMatrix);
        int n3 = object[1];
        int n4 = nArray[1];
        int n5 = object[0];
        int n6 = (nArray[0] - n5 + 1) / n2;
        int n7 = (n4 - n3 + 1) / n2;
        if (n6 <= 0 || n7 <= 0) {
            throw NotFoundException.getNotFoundInstance();
        }
        int n8 = n2 / 2;
        object = new BitMatrix(n6, n7);
        for (n4 = 0; n4 < n7; ++n4) {
            for (int i2 = 0; i2 < n6; ++i2) {
                if (!bitMatrix.get(i2 * n2 + (n5 + n8), n3 + n8 + n4 * n2)) continue;
                ((BitMatrix)object).set(i2, n4);
            }
        }
        return object;
    }

    private static int moduleSize(int[] nArray, BitMatrix bitMatrix) throws NotFoundException {
        int n2;
        int n3 = bitMatrix.getWidth();
        int n4 = nArray[1];
        for (n2 = nArray[0]; n2 < n3 && bitMatrix.get(n2, n4); ++n2) {
        }
        if (n2 == n3) {
            throw NotFoundException.getNotFoundInstance();
        }
        if ((n2 -= nArray[0]) == 0) {
            throw NotFoundException.getNotFoundInstance();
        }
        return n2;
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, ChecksumException, FormatException {
        return this.decode(binaryBitmap, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Result decode(BinaryBitmap object, Map<DecodeHintType, ?> object2) throws NotFoundException, ChecksumException, FormatException {
        if (object2 != null && object2.containsKey((Object)DecodeHintType.PURE_BARCODE)) {
            object = DataMatrixReader.extractPureBits(((BinaryBitmap)object).getBlackMatrix());
            object = this.decoder.decode((BitMatrix)object);
            object2 = NO_POINTS;
        } else {
            object2 = new Detector(((BinaryBitmap)object).getBlackMatrix()).detect();
            object = this.decoder.decode(((DetectorResult)object2).getBits());
            object2 = ((DetectorResult)object2).getPoints();
        }
        object2 = new Result(((DecoderResult)object).getText(), ((DecoderResult)object).getRawBytes(), (ResultPoint[])object2, BarcodeFormat.DATA_MATRIX);
        List<byte[]> list = ((DecoderResult)object).getByteSegments();
        if (list != null) {
            ((Result)object2).putMetadata(ResultMetadataType.BYTE_SEGMENTS, list);
        }
        if ((object = ((DecoderResult)object).getECLevel()) != null) {
            ((Result)object2).putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, object);
        }
        return object2;
    }

    @Override
    public void reset() {
    }
}

