/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.multi;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.multi.MultipleBarcodeReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class GenericMultipleBarcodeReader
implements MultipleBarcodeReader {
    private static final int MAX_DEPTH = 4;
    private static final int MIN_DIMENSION_TO_RECUR = 100;
    private final Reader delegate;

    public GenericMultipleBarcodeReader(Reader reader) {
        this.delegate = reader;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void doDecodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map, List<Result> list, int n2, int n3, int n4) {
        float f2;
        int n5;
        int n6;
        block19: {
            block18: {
                int n7;
                ResultPoint[] resultPointArray;
                block17: {
                    Iterator<Result> iterator;
                    if (n4 > 4) break block18;
                    try {
                        resultPointArray = this.delegate.decode(binaryBitmap, map);
                        n6 = 0;
                        iterator = list.iterator();
                    }
                    catch (ReaderException readerException) {
                        return;
                    }
                    do {
                        n7 = n6;
                        if (!iterator.hasNext()) break block17;
                    } while (!iterator.next().getText().equals(resultPointArray.getText()));
                    n7 = 1;
                }
                if (n7 == 0) {
                    list.add(GenericMultipleBarcodeReader.translateResultPoints((Result)resultPointArray, n2, n3));
                }
                if ((resultPointArray = resultPointArray.getResultPoints()) == null || resultPointArray.length == 0) break block18;
                n6 = binaryBitmap.getWidth();
                n5 = binaryBitmap.getHeight();
                float f3 = n6;
                float f4 = n5;
                float f5 = 0.0f;
                f2 = 0.0f;
                for (ResultPoint resultPoint : resultPointArray) {
                    float f6;
                    float f7;
                    float f8;
                    if (resultPoint == null) {
                        f8 = f4;
                        f7 = f3;
                        f6 = f2;
                    } else {
                        f8 = resultPoint.getX();
                        float f9 = resultPoint.getY();
                        float f10 = f3;
                        if (f8 < f3) {
                            f10 = f8;
                        }
                        f3 = f4;
                        if (f9 < f4) {
                            f3 = f9;
                        }
                        f4 = f5;
                        if (f8 > f5) {
                            f4 = f8;
                        }
                        f5 = f4;
                        f6 = f2;
                        f7 = f10;
                        f8 = f3;
                        if (f9 > f2) {
                            f5 = f4;
                            f6 = f9;
                            f7 = f10;
                            f8 = f3;
                        }
                    }
                    f2 = f6;
                    f3 = f7;
                    f4 = f8;
                }
                if (f3 > 100.0f) {
                    this.doDecodeMultiple(binaryBitmap.crop(0, 0, (int)f3, n5), map, list, n2, n3, n4 + 1);
                }
                if (f4 > 100.0f) {
                    this.doDecodeMultiple(binaryBitmap.crop(0, 0, n6, (int)f4), map, list, n2, n3, n4 + 1);
                }
                if (f5 < (float)(n6 - 100)) {
                    this.doDecodeMultiple(binaryBitmap.crop((int)f5, 0, n6 - (int)f5, n5), map, list, n2 + (int)f5, n3, n4 + 1);
                }
                if (f2 < (float)(n5 - 100)) break block19;
            }
            return;
        }
        this.doDecodeMultiple(binaryBitmap.crop(0, (int)f2, n6, n5 - (int)f2), map, list, n2, n3 + (int)f2, n4 + 1);
    }

    private static Result translateResultPoints(Result result, int n2, int n3) {
        Object object = result.getResultPoints();
        if (object == null) {
            return result;
        }
        ResultPoint[] resultPointArray = new ResultPoint[((ResultPoint[])object).length];
        for (int i2 = 0; i2 < ((ResultPoint[])object).length; ++i2) {
            ResultPoint resultPoint = object[i2];
            if (resultPoint == null) continue;
            resultPointArray[i2] = new ResultPoint(resultPoint.getX() + (float)n2, resultPoint.getY() + (float)n3);
        }
        object = new Result(result.getText(), result.getRawBytes(), resultPointArray, result.getBarcodeFormat());
        ((Result)object).putAllMetadata(result.getResultMetadata());
        return object;
    }

    @Override
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap) throws NotFoundException {
        return this.decodeMultiple(binaryBitmap, null);
    }

    @Override
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException {
        ArrayList<Result> arrayList = new ArrayList<Result>();
        this.doDecodeMultiple(binaryBitmap, map, arrayList, 0, 0, 0);
        if (arrayList.isEmpty()) {
            throw NotFoundException.getNotFoundInstance();
        }
        return arrayList.toArray(new Result[arrayList.size()]);
    }
}

