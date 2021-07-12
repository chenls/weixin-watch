/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.multi.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.multi.MultipleBarcodeReader;
import com.google.zxing.multi.qrcode.detector.MultiDetector;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.decoder.QRCodeDecoderMetaData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public final class QRCodeMultiReader
extends QRCodeReader
implements MultipleBarcodeReader {
    private static final Result[] EMPTY_RESULT_ARRAY = new Result[0];
    private static final ResultPoint[] NO_POINTS = new ResultPoint[0];

    private static List<Result> processStructuredAppend(List<Result> object) {
        int n2;
        int n3;
        Object object2;
        Object object3;
        int n4;
        Object object4;
        int n5;
        block8: {
            n5 = 0;
            object4 = object.iterator();
            do {
                n4 = n5;
                if (!object4.hasNext()) break block8;
            } while (!object4.next().getResultMetadata().containsKey((Object)ResultMetadataType.STRUCTURED_APPEND_SEQUENCE));
            n4 = 1;
        }
        if (n4 == 0) {
            return object;
        }
        object4 = new ArrayList();
        ArrayList<Object> arrayList = new ArrayList<Result>();
        object = object.iterator();
        while (object.hasNext()) {
            object3 = (Result)object.next();
            object4.add(object3);
            if (!((Result)object3).getResultMetadata().containsKey((Object)ResultMetadataType.STRUCTURED_APPEND_SEQUENCE)) continue;
            arrayList.add(object3);
        }
        Collections.sort(arrayList, new SAComparator());
        object = new StringBuilder();
        n5 = 0;
        n4 = 0;
        object3 = arrayList.iterator();
        block2: while (object3.hasNext()) {
            object2 = (Result)object3.next();
            ((StringBuilder)object).append(((Result)object2).getText());
            n5 = n3 = n5 + ((Result)object2).getRawBytes().length;
            if (!((Result)object2).getResultMetadata().containsKey((Object)ResultMetadataType.BYTE_SEGMENTS)) continue;
            object2 = ((Iterable)((Result)object2).getResultMetadata().get((Object)ResultMetadataType.BYTE_SEGMENTS)).iterator();
            n2 = n4;
            while (true) {
                n4 = n2;
                n5 = n3;
                if (!object2.hasNext()) continue block2;
                n2 += ((byte[])object2.next()).length;
            }
        }
        object2 = new byte[n5];
        object3 = new byte[n4];
        n5 = 0;
        n2 = 0;
        block4: for (Object object5 : arrayList) {
            int n6;
            System.arraycopy(((Result)object5).getRawBytes(), 0, object2, n5, ((Result)object5).getRawBytes().length);
            n5 = n6 = n5 + ((Result)object5).getRawBytes().length;
            if (!((Result)object5).getResultMetadata().containsKey((Object)ResultMetadataType.BYTE_SEGMENTS)) continue;
            object5 = ((Iterable)((Result)object5).getResultMetadata().get((Object)ResultMetadataType.BYTE_SEGMENTS)).iterator();
            n3 = n2;
            while (true) {
                n2 = n3;
                n5 = n6;
                if (!object5.hasNext()) continue block4;
                byte[] byArray = (byte[])object5.next();
                System.arraycopy(byArray, 0, object3, n3, byArray.length);
                n3 += byArray.length;
            }
        }
        object = new Result(((StringBuilder)object).toString(), (byte[])object2, NO_POINTS, BarcodeFormat.QR_CODE);
        if (n4 > 0) {
            arrayList = new ArrayList<Object>();
            arrayList.add(object3);
            ((Result)object).putMetadata(ResultMetadataType.BYTE_SEGMENTS, arrayList);
        }
        object4.add(object);
        return object4;
    }

    @Override
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap) throws NotFoundException {
        return this.decodeMultiple(binaryBitmap, null);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Result[] decodeMultiple(BinaryBitmap object, Map<DecodeHintType, ?> map) throws NotFoundException {
        ArrayList<Result> arrayList = new ArrayList<Result>();
        object = new MultiDetector(((BinaryBitmap)object).getBlackMatrix()).detectMulti(map);
        int n2 = ((Object)object).length;
        int n3 = 0;
        while (true) {
            if (n3 < n2) {
                Object object2 = object[n3];
                DecoderResult decoderResult = this.getDecoder().decode(((DetectorResult)object2).getBits(), map);
                ResultPoint[] resultPointArray = ((DetectorResult)object2).getPoints();
                if (decoderResult.getOther() instanceof QRCodeDecoderMetaData) {
                    ((QRCodeDecoderMetaData)decoderResult.getOther()).applyMirroredCorrection(resultPointArray);
                }
                Result result = new Result(decoderResult.getText(), decoderResult.getRawBytes(), resultPointArray, BarcodeFormat.QR_CODE);
                List<byte[]> list = decoderResult.getByteSegments();
                if (list != null) {
                    result.putMetadata(ResultMetadataType.BYTE_SEGMENTS, list);
                }
                if ((list = decoderResult.getECLevel()) != null) {
                    result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, list);
                }
                if (decoderResult.hasStructuredAppend()) {
                    result.putMetadata(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE, decoderResult.getStructuredAppendSequenceNumber());
                    result.putMetadata(ResultMetadataType.STRUCTURED_APPEND_PARITY, decoderResult.getStructuredAppendParity());
                }
                arrayList.add(result);
            } else {
                if (arrayList.isEmpty()) {
                    return EMPTY_RESULT_ARRAY;
                }
                object = QRCodeMultiReader.processStructuredAppend(arrayList);
                return object.toArray(new Result[object.size()]);
                catch (ReaderException readerException) {}
            }
            ++n3;
        }
    }

    private static final class SAComparator
    implements Comparator<Result>,
    Serializable {
        private SAComparator() {
        }

        @Override
        public int compare(Result result, Result result2) {
            int n2;
            int n3 = (Integer)result.getResultMetadata().get((Object)ResultMetadataType.STRUCTURED_APPEND_SEQUENCE);
            if (n3 < (n2 = ((Integer)result2.getResultMetadata().get((Object)ResultMetadataType.STRUCTURED_APPEND_SEQUENCE)).intValue())) {
                return -1;
            }
            if (n3 > n2) {
                return 1;
            }
            return 0;
        }
    }
}

