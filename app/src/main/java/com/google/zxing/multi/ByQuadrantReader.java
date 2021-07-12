/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.multi;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import java.util.Map;

public final class ByQuadrantReader
implements Reader {
    private final Reader delegate;

    public ByQuadrantReader(Reader reader) {
        this.delegate = reader;
    }

    private static void makeAbsolute(ResultPoint[] resultPointArray, int n2, int n3) {
        if (resultPointArray != null) {
            for (int i2 = 0; i2 < resultPointArray.length; ++i2) {
                ResultPoint resultPoint = resultPointArray[i2];
                resultPointArray[i2] = new ResultPoint(resultPoint.getX() + (float)n2, resultPoint.getY() + (float)n3);
            }
        }
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, ChecksumException, FormatException {
        return this.decode(binaryBitmap, null);
    }

    @Override
    public Result decode(BinaryBitmap object, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        int n2 = ((BinaryBitmap)object).getWidth();
        int n3 = ((BinaryBitmap)object).getHeight();
        n2 /= 2;
        n3 /= 2;
        try {
            Result result = this.delegate.decode(((BinaryBitmap)object).crop(0, 0, n2, n3), map);
            return result;
        }
        catch (NotFoundException notFoundException) {
            try {
                Result result = this.delegate.decode(((BinaryBitmap)object).crop(n2, 0, n2, n3), map);
                ByQuadrantReader.makeAbsolute(result.getResultPoints(), n2, 0);
                return result;
            }
            catch (NotFoundException notFoundException2) {
                try {
                    Result result = this.delegate.decode(((BinaryBitmap)object).crop(0, n3, n2, n3), map);
                    ByQuadrantReader.makeAbsolute(result.getResultPoints(), 0, n3);
                    return result;
                }
                catch (NotFoundException notFoundException3) {
                    try {
                        Result result = this.delegate.decode(((BinaryBitmap)object).crop(n2, n3, n2, n3), map);
                        ByQuadrantReader.makeAbsolute(result.getResultPoints(), n2, n3);
                        return result;
                    }
                    catch (NotFoundException notFoundException4) {
                        int n4 = n2 / 2;
                        int n5 = n3 / 2;
                        object = ((BinaryBitmap)object).crop(n4, n5, n2, n3);
                        object = this.delegate.decode((BinaryBitmap)object, map);
                        ByQuadrantReader.makeAbsolute(((Result)object).getResultPoints(), n4, n5);
                        return object;
                    }
                }
            }
        }
    }

    @Override
    public void reset() {
        this.delegate.reset();
    }
}

