/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.OneDReader;
import java.util.Map;

public final class ITFReader
extends OneDReader {
    private static final int[] DEFAULT_ALLOWED_LENGTHS = new int[]{6, 8, 10, 12, 14};
    private static final int[] END_PATTERN_REVERSED;
    private static final float MAX_AVG_VARIANCE = 0.38f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.78f;
    private static final int N = 1;
    static final int[][] PATTERNS;
    private static final int[] START_PATTERN;
    private static final int W = 3;
    private int narrowLineWidth = -1;

    static {
        START_PATTERN = new int[]{1, 1, 1, 1};
        END_PATTERN_REVERSED = new int[]{1, 1, 3};
        int[] nArray = new int[]{1, 1, 3, 1, 3};
        PATTERNS = new int[][]{{1, 1, 3, 3, 1}, {3, 1, 1, 1, 3}, {1, 3, 1, 1, 3}, {3, 3, 1, 1, 1}, nArray, {3, 1, 3, 1, 1}, {1, 3, 3, 1, 1}, {1, 1, 1, 3, 3}, {3, 1, 1, 3, 1}, {1, 3, 1, 3, 1}};
    }

    private static int decodeDigit(int[] nArray) throws NotFoundException {
        float f2 = 0.38f;
        int n2 = -1;
        int n3 = PATTERNS.length;
        for (int i2 = 0; i2 < n3; ++i2) {
            float f3 = ITFReader.patternMatchVariance(nArray, PATTERNS[i2], 0.78f);
            float f4 = f2;
            if (f3 < f2) {
                f4 = f3;
                n2 = i2;
            }
            f2 = f4;
        }
        if (n2 >= 0) {
            return n2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static void decodeMiddle(BitArray bitArray, int n2, int n3, StringBuilder stringBuilder) throws NotFoundException {
        int[] nArray = new int[10];
        int[] nArray2 = new int[5];
        int[] nArray3 = new int[5];
        block0: while (n2 < n3) {
            int n4;
            int n5;
            ITFReader.recordPattern(bitArray, n2, nArray);
            for (n5 = 0; n5 < 5; ++n5) {
                n4 = n5 * 2;
                nArray2[n5] = nArray[n4];
                nArray3[n5] = nArray[n4 + 1];
            }
            stringBuilder.append((char)(ITFReader.decodeDigit(nArray2) + 48));
            stringBuilder.append((char)(ITFReader.decodeDigit(nArray3) + 48));
            int n6 = nArray.length;
            n5 = 0;
            n4 = n2;
            while (true) {
                n2 = n4;
                if (n5 >= n6) continue block0;
                n4 += nArray[n5];
                ++n5;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int[] findGuardPattern(BitArray bitArray, int n2, int[] nArray) throws NotFoundException {
        int n3 = nArray.length;
        int[] nArray2 = new int[n3];
        int n4 = bitArray.getSize();
        boolean bl2 = false;
        int n5 = 0;
        int n6 = n2;
        int n7 = n2;
        n2 = n6;
        n6 = n5;
        while (true) {
            if (n7 >= n4) {
                throw NotFoundException.getNotFoundInstance();
            }
            if (bitArray.get(n7) ^ bl2) {
                nArray2[n6] = nArray2[n6] + 1;
            } else {
                if (n6 == n3 - 1) {
                    if (ITFReader.patternMatchVariance(nArray2, nArray, 0.78f) < 0.38f) {
                        return new int[]{n2, n7};
                    }
                    n2 += nArray2[0] + nArray2[1];
                    System.arraycopy(nArray2, 2, nArray2, 0, n3 - 2);
                    nArray2[n3 - 2] = 0;
                    nArray2[n3 - 1] = 0;
                    --n6;
                } else {
                    ++n6;
                }
                nArray2[n6] = 1;
                bl2 = !bl2;
            }
            ++n7;
        }
    }

    private static int skipWhiteSpace(BitArray bitArray) throws NotFoundException {
        int n2 = bitArray.getSize();
        int n3 = bitArray.getNextSet(0);
        if (n3 == n2) {
            throw NotFoundException.getNotFoundInstance();
        }
        return n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void validateQuietZone(BitArray bitArray, int n2) throws NotFoundException {
        int n3 = this.narrowLineWidth * 10;
        if (n3 >= n2) {
            n3 = n2;
        }
        --n2;
        while (true) {
            if (n3 <= 0 || n2 < 0 || bitArray.get(n2)) {
                if (n3 == 0) break;
                throw NotFoundException.getNotFoundInstance();
            }
            --n3;
            --n2;
        }
    }

    int[] decodeEnd(BitArray bitArray) throws NotFoundException {
        bitArray.reverse();
        int[] nArray = ITFReader.findGuardPattern(bitArray, ITFReader.skipWhiteSpace(bitArray), END_PATTERN_REVERSED);
        this.validateQuietZone(bitArray, nArray[0]);
        int n2 = nArray[0];
        try {
            nArray[0] = bitArray.getSize() - nArray[1];
            nArray[1] = bitArray.getSize() - n2;
            return nArray;
        }
        finally {
            bitArray.reverse();
        }
    }

    @Override
    public Result decodeRow(int n2, BitArray object, Map<DecodeHintType, ?> object2) throws FormatException, NotFoundException {
        int[] nArray = this.decodeStart((BitArray)object);
        Object object3 = this.decodeEnd((BitArray)object);
        CharSequence charSequence = new StringBuilder(20);
        ITFReader.decodeMiddle((BitArray)object, nArray[1], object3[0], (StringBuilder)charSequence);
        charSequence = ((StringBuilder)charSequence).toString();
        object = null;
        if (object2 != null) {
            object = (int[])object2.get((Object)DecodeHintType.ALLOWED_LENGTHS);
        }
        object2 = object;
        if (object == null) {
            object2 = DEFAULT_ALLOWED_LENGTHS;
        }
        int n3 = ((String)charSequence).length();
        int n4 = 0;
        int n5 = 0;
        int n6 = ((Object)object2).length;
        int n7 = 0;
        while (true) {
            Object object4;
            Object object5;
            block10: {
                block9: {
                    object5 = n4;
                    if (n7 >= n6) break block9;
                    object4 = object2[n7];
                    if (n3 != object4) break block10;
                    object5 = 1;
                }
                n7 = object5;
                if (object5 == 0) {
                    n7 = object5;
                    if (n3 > n5) {
                        n7 = 1;
                    }
                }
                if (n7 != 0) break;
                throw FormatException.getFormatInstance();
            }
            object5 = n5;
            if (object4 > n5) {
                object5 = object4;
            }
            ++n7;
            n5 = object5;
        }
        object = new ResultPoint(nArray[1], n2);
        object2 = new ResultPoint(object3[0], n2);
        object3 = (Object)BarcodeFormat.ITF;
        return new Result((String)charSequence, null, new ResultPoint[]{object, object2}, (BarcodeFormat)((Object)object3));
    }

    int[] decodeStart(BitArray bitArray) throws NotFoundException {
        int[] nArray = ITFReader.findGuardPattern(bitArray, ITFReader.skipWhiteSpace(bitArray), START_PATTERN);
        this.narrowLineWidth = (nArray[1] - nArray[0]) / 4;
        this.validateQuietZone(bitArray, nArray[0]);
        return nArray;
    }
}

