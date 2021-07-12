/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.pdf417;

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
import com.google.zxing.common.DecoderResult;
import com.google.zxing.multi.MultipleBarcodeReader;
import com.google.zxing.pdf417.PDF417ResultMetadata;
import com.google.zxing.pdf417.decoder.PDF417ScanningDecoder;
import com.google.zxing.pdf417.detector.Detector;
import com.google.zxing.pdf417.detector.PDF417DetectorResult;
import java.util.ArrayList;
import java.util.Map;

public final class PDF417Reader
implements Reader,
MultipleBarcodeReader {
    private static Result[] decode(BinaryBitmap object, Map<DecodeHintType, ?> object2, boolean bl2) throws NotFoundException, FormatException, ChecksumException {
        ArrayList<Result> arrayList = new ArrayList<Result>();
        object = Detector.detect((BinaryBitmap)object, object2, bl2);
        for (ResultPoint[] resultPointArray : ((PDF417DetectorResult)object).getPoints()) {
            Object object4 = PDF417ScanningDecoder.decode(((PDF417DetectorResult)object).getBits(), resultPointArray[4], resultPointArray[5], resultPointArray[6], resultPointArray[7], PDF417Reader.getMinCodewordWidth(resultPointArray), PDF417Reader.getMaxCodewordWidth(resultPointArray));
            Result object3 = new Result(((DecoderResult)object4).getText(), ((DecoderResult)object4).getRawBytes(), resultPointArray, BarcodeFormat.PDF_417);
            object3.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, ((DecoderResult)object4).getECLevel());
            object4 = (PDF417ResultMetadata)((DecoderResult)object4).getOther();
            if (object4 != null) {
                object3.putMetadata(ResultMetadataType.PDF417_EXTRA_METADATA, object4);
            }
            arrayList.add(object3);
        }
        return arrayList.toArray(new Result[arrayList.size()]);
    }

    private static int getMaxCodewordWidth(ResultPoint[] resultPointArray) {
        return Math.max(Math.max(PDF417Reader.getMaxWidth(resultPointArray[0], resultPointArray[4]), PDF417Reader.getMaxWidth(resultPointArray[6], resultPointArray[2]) * 17 / 18), Math.max(PDF417Reader.getMaxWidth(resultPointArray[1], resultPointArray[5]), PDF417Reader.getMaxWidth(resultPointArray[7], resultPointArray[3]) * 17 / 18));
    }

    private static int getMaxWidth(ResultPoint resultPoint, ResultPoint resultPoint2) {
        if (resultPoint == null || resultPoint2 == null) {
            return 0;
        }
        return (int)Math.abs(resultPoint.getX() - resultPoint2.getX());
    }

    private static int getMinCodewordWidth(ResultPoint[] resultPointArray) {
        return Math.min(Math.min(PDF417Reader.getMinWidth(resultPointArray[0], resultPointArray[4]), PDF417Reader.getMinWidth(resultPointArray[6], resultPointArray[2]) * 17 / 18), Math.min(PDF417Reader.getMinWidth(resultPointArray[1], resultPointArray[5]), PDF417Reader.getMinWidth(resultPointArray[7], resultPointArray[3]) * 17 / 18));
    }

    private static int getMinWidth(ResultPoint resultPoint, ResultPoint resultPoint2) {
        if (resultPoint == null || resultPoint2 == null) {
            return Integer.MAX_VALUE;
        }
        return (int)Math.abs(resultPoint.getX() - resultPoint2.getX());
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, FormatException, ChecksumException {
        return this.decode(binaryBitmap, null);
    }

    @Override
    public Result decode(BinaryBitmap resultArray, Map<DecodeHintType, ?> map) throws NotFoundException, FormatException, ChecksumException {
        if ((resultArray = PDF417Reader.decode((BinaryBitmap)resultArray, map, false)) == null || resultArray.length == 0 || resultArray[0] == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        return resultArray[0];
    }

    @Override
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap) throws NotFoundException {
        return this.decodeMultiple(binaryBitmap, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Result[] decodeMultiple(BinaryBitmap resultArray, Map<DecodeHintType, ?> map) throws NotFoundException {
        try {
            return PDF417Reader.decode((BinaryBitmap)resultArray, map, true);
        }
        catch (FormatException formatException) {
            throw NotFoundException.getNotFoundInstance();
        }
        catch (ChecksumException checksumException) {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    @Override
    public void reset() {
    }
}

