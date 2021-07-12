/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public abstract class OneDReader
implements Reader {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Result doDecode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException {
        int n2 = binaryBitmap.getWidth();
        int n3 = binaryBitmap.getHeight();
        Object object = new BitArray(n2);
        int n4 = map != null && map.containsKey((Object)DecodeHintType.TRY_HARDER) ? 1 : 0;
        int n5 = n4 != 0 ? 8 : 5;
        int n6 = Math.max(1, n3 >> n5);
        n4 = n4 != 0 ? n3 : 15;
        n5 = 0;
        while (true) {
            ResultPoint[] resultPointArray;
            Map<DecodeHintType, ?> map2;
            block14: {
                int n7;
                int n8;
                block13: {
                    if (n5 < n4) {
                        n8 = (n5 + 1) / 2;
                        n7 = (n5 & 1) == 0 ? 1 : 0;
                        n8 = (n3 >> 1) + n6 * (n7 = n7 != 0 ? n8 : -n8);
                        if (n8 >= 0 && n8 < n3) {
                            try {
                                map2 = binaryBitmap.getBlackRow(n8, (BitArray)object);
                                object = map2;
                                n7 = 0;
                                break block13;
                            }
                            catch (NotFoundException notFoundException) {
                                map2 = map;
                                resultPointArray = object;
                                break block14;
                            }
                        }
                    }
                    throw NotFoundException.getNotFoundInstance();
                }
                while (true) {
                    resultPointArray = object;
                    map2 = map;
                    if (n7 >= 2) break;
                    map2 = map;
                    if (n7 == 1) {
                        ((BitArray)object).reverse();
                        map2 = map;
                        if (map != null) {
                            map2 = map;
                            if (map.containsKey((Object)DecodeHintType.NEED_RESULT_POINT_CALLBACK)) {
                                map2 = new EnumMap(DecodeHintType.class);
                                map2.putAll(map);
                                map2.remove((Object)DecodeHintType.NEED_RESULT_POINT_CALLBACK);
                            }
                        }
                    }
                    try {
                        map = this.decodeRow(n8, (BitArray)object, map2);
                        if (n7 == 1) {
                            ((Result)((Object)map)).putMetadata(ResultMetadataType.ORIENTATION, 180);
                            resultPointArray = ((Result)((Object)map)).getResultPoints();
                            if (resultPointArray != null) {
                                resultPointArray[0] = new ResultPoint((float)n2 - resultPointArray[0].getX() - 1.0f, resultPointArray[0].getY());
                                resultPointArray[1] = new ResultPoint((float)n2 - resultPointArray[1].getX() - 1.0f, resultPointArray[1].getY());
                            }
                        }
                        return map;
                    }
                    catch (ReaderException readerException) {
                        ++n7;
                        map = map2;
                        continue;
                    }
                    break;
                }
            }
            ++n5;
            object = resultPointArray;
            map = map2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected static float patternMatchVariance(int[] nArray, int[] nArray2, float f2) {
        int n2;
        int n3 = nArray.length;
        int n4 = 0;
        int n5 = 0;
        for (n2 = 0; n2 < n3; n4 += nArray[n2], n5 += nArray2[n2], ++n2) {
        }
        if (n4 >= n5) {
            float f3 = (float)n4 / (float)n5;
            float f4 = 0.0f;
            n2 = 0;
            while (true) {
                if (n2 >= n3) {
                    return f4 / (float)n4;
                }
                n5 = nArray[n2];
                float f5 = (float)nArray2[n2] * f3;
                f5 = (float)n5 > f5 ? (float)n5 - f5 : (f5 -= (float)n5);
                if (f5 > f2 * f3) break;
                f4 += f5;
                ++n2;
            }
        }
        return Float.POSITIVE_INFINITY;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected static void recordPattern(BitArray bitArray, int n2, int[] nArray) throws NotFoundException {
        int n3 = nArray.length;
        Arrays.fill(nArray, 0, n3, 0);
        int n4 = bitArray.getSize();
        if (n2 >= n4) {
            throw NotFoundException.getNotFoundInstance();
        }
        int n5 = !bitArray.get(n2) ? 1 : 0;
        int n6 = 0;
        int n7 = n5;
        n5 = n2;
        n2 = n6;
        while (true) {
            block7: {
                block8: {
                    block5: {
                        block6: {
                            n6 = n2;
                            if (n5 >= n4) break block5;
                            if ((bitArray.get(n5) ^ n7) == 0) break block6;
                            nArray[n2] = nArray[n2] + 1;
                            n6 = n2;
                            break block7;
                        }
                        n6 = n2 + 1;
                        if (n6 != n3) break block8;
                    }
                    if (n6 == n3 || n6 == n3 - 1 && n5 == n4) break;
                    throw NotFoundException.getNotFoundInstance();
                }
                nArray[n6] = 1;
                n2 = n7 == 0 ? 1 : 0;
                n7 = n2;
            }
            ++n5;
            n2 = n6;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected static void recordPatternInReverse(BitArray bitArray, int n2, int[] nArray) throws NotFoundException {
        int n3 = nArray.length;
        boolean bl2 = bitArray.get(n2);
        while (n2 > 0 && n3 >= 0) {
            int n4;
            n2 = n4 = n2 - 1;
            if (bitArray.get(n4) == bl2) continue;
            --n3;
            bl2 = !bl2;
            n2 = n4;
        }
        if (n3 >= 0) {
            throw NotFoundException.getNotFoundInstance();
        }
        OneDReader.recordPattern(bitArray, n2 + 1, nArray);
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, FormatException {
        return this.decode(binaryBitmap, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Result decode(BinaryBitmap object, Map<DecodeHintType, ?> object2) throws NotFoundException, FormatException {
        try {
            Result result = this.doDecode((BinaryBitmap)object, (Map<DecodeHintType, ?>)object2);
            return result;
        }
        catch (NotFoundException notFoundException) {
            int n2;
            int n3 = object2 != null && object2.containsKey((Object)DecodeHintType.TRY_HARDER) ? 1 : 0;
            if (n3 == 0) throw notFoundException;
            if (!((BinaryBitmap)object).isRotateSupported()) throw notFoundException;
            BinaryBitmap binaryBitmap = ((BinaryBitmap)object).rotateCounterClockwise();
            object2 = this.doDecode(binaryBitmap, (Map<DecodeHintType, ?>)object2);
            object = ((Result)object2).getResultMetadata();
            n3 = n2 = 270;
            if (object != null) {
                n3 = n2;
                if (object.containsKey((Object)ResultMetadataType.ORIENTATION)) {
                    n3 = ((Integer)object.get((Object)ResultMetadataType.ORIENTATION) + 270) % 360;
                }
            }
            ((Result)object2).putMetadata(ResultMetadataType.ORIENTATION, n3);
            ResultPoint[] resultPointArray = ((Result)object2).getResultPoints();
            object = object2;
            if (resultPointArray == null) return object;
            n2 = binaryBitmap.getHeight();
            n3 = 0;
            while (true) {
                object = object2;
                if (n3 >= resultPointArray.length) return object;
                resultPointArray[n3] = new ResultPoint((float)n2 - resultPointArray[n3].getY() - 1.0f, resultPointArray[n3].getX());
                ++n3;
            }
        }
    }

    public abstract Result decodeRow(int var1, BitArray var2, Map<DecodeHintType, ?> var3) throws NotFoundException, ChecksumException, FormatException;

    @Override
    public void reset() {
    }
}

