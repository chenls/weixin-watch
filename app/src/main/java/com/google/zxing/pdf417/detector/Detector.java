/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.pdf417.detector;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.detector.PDF417DetectorResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class Detector {
    private static final int BARCODE_MIN_HEIGHT = 10;
    private static final int[] INDEXES_START_PATTERN = new int[]{0, 4, 1, 5};
    private static final int[] INDEXES_STOP_PATTERN = new int[]{6, 2, 7, 3};
    private static final float MAX_AVG_VARIANCE = 0.42f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.8f;
    private static final int MAX_PATTERN_DRIFT = 5;
    private static final int MAX_PIXEL_DRIFT = 3;
    private static final int ROW_STEP = 5;
    private static final int SKIPPED_ROW_COUNT_MAX = 25;
    private static final int[] START_PATTERN = new int[]{8, 1, 1, 1, 1, 1, 1, 3};
    private static final int[] STOP_PATTERN = new int[]{7, 1, 1, 3, 1, 1, 1, 2, 1};

    private Detector() {
    }

    private static void copyToResult(ResultPoint[] resultPointArray, ResultPoint[] resultPointArray2, int[] nArray) {
        for (int i2 = 0; i2 < nArray.length; ++i2) {
            resultPointArray[nArray[i2]] = resultPointArray2[i2];
        }
    }

    public static PDF417DetectorResult detect(BinaryBitmap object, Map<DecodeHintType, ?> list, boolean bl2) throws NotFoundException {
        List<ResultPoint[]> list2;
        BitMatrix bitMatrix = ((BinaryBitmap)object).getBlackMatrix();
        list = list2 = Detector.detect(bl2, bitMatrix);
        object = bitMatrix;
        if (list2.isEmpty()) {
            object = bitMatrix.clone();
            ((BitMatrix)object).rotate180();
            list = Detector.detect(bl2, (BitMatrix)object);
        }
        return new PDF417DetectorResult((BitMatrix)object, list);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static List<ResultPoint[]> detect(boolean bl2, BitMatrix bitMatrix) {
        ArrayList<ResultPoint[]> arrayList = new ArrayList<ResultPoint[]>();
        int n2 = 0;
        int n3 = 0;
        boolean bl3 = false;
        while (n2 < bitMatrix.getHeight()) {
            ResultPoint[] resultPointArray = Detector.findVertices(bitMatrix, n2, n3);
            if (resultPointArray[0] == null && resultPointArray[3] == null) {
                if (!bl3) break;
                bl3 = false;
                int n4 = 0;
                for (ResultPoint[] resultPointArray2 : arrayList) {
                    n3 = n2;
                    if (resultPointArray2[1] != null) {
                        n3 = (int)Math.max((float)n2, resultPointArray2[1].getY());
                    }
                    n2 = n3;
                    if (resultPointArray2[3] == null) continue;
                    n2 = Math.max(n3, (int)resultPointArray2[3].getY());
                }
                n2 += 5;
                n3 = n4;
                continue;
            }
            bl3 = true;
            arrayList.add(resultPointArray);
            if (!bl2) break;
            if (resultPointArray[2] != null) {
                n3 = (int)((ResultPoint)resultPointArray[2]).getX();
                n2 = (int)((ResultPoint)resultPointArray[2]).getY();
                continue;
            }
            n3 = (int)((ResultPoint)resultPointArray[4]).getX();
            n2 = (int)((ResultPoint)resultPointArray[4]).getY();
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int[] findGuardPattern(BitMatrix bitMatrix, int n2, int n3, int n4, boolean bl2, int[] nArray, int[] nArray2) {
        int n5;
        int n6;
        Arrays.fill(nArray2, 0, nArray2.length, 0);
        int n7 = nArray.length;
        for (n6 = 0; bitMatrix.get(n2, n3) && n2 > 0 && n6 < 3; --n2, ++n6) {
        }
        n6 = 0;
        for (n5 = n2; n5 < n4; ++n5) {
            if (bitMatrix.get(n5, n3) ^ bl2) {
                nArray2[n6] = nArray2[n6] + 1;
                continue;
            }
            if (n6 == n7 - 1) {
                if (Detector.patternMatchVariance(nArray2, nArray, 0.8f) < 0.42f) {
                    return new int[]{n2, n5};
                }
                n2 += nArray2[0] + nArray2[1];
                System.arraycopy(nArray2, 2, nArray2, 0, n7 - 2);
                nArray2[n7 - 2] = 0;
                nArray2[n7 - 1] = 0;
                --n6;
            } else {
                ++n6;
            }
            nArray2[n6] = 1;
            bl2 = !bl2;
        }
        if (n6 == n7 - 1 && Detector.patternMatchVariance(nArray2, nArray, 0.8f) < 0.42f) {
            return new int[]{n2, n5 - 1};
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static ResultPoint[] findRowsWithPattern(BitMatrix bitMatrix, int n2, int n3, int n4, int n5, int[] nArray) {
        int n6;
        ResultPoint[] resultPointArray;
        block10: {
            int[] nArray2;
            int[] nArray3;
            int n7;
            resultPointArray = new ResultPoint[4];
            int n8 = 0;
            int[] nArray4 = new int[nArray.length];
            while (true) {
                block12: {
                    block11: {
                        block9: {
                            n7 = n8;
                            n6 = n4;
                            if (n4 >= n2) break block11;
                            nArray3 = Detector.findGuardPattern(bitMatrix, n5, n4, n3, false, nArray, nArray4);
                            if (nArray3 == null) break block12;
                            n6 = n4;
                            while (true) {
                                n4 = n6;
                                if (n6 <= 0) break block9;
                                if ((nArray2 = Detector.findGuardPattern(bitMatrix, n5, --n6, n3, false, nArray, nArray4)) == null) break;
                                nArray3 = nArray2;
                            }
                            n4 = n6 + 1;
                        }
                        resultPointArray[0] = new ResultPoint(nArray3[0], n4);
                        resultPointArray[1] = new ResultPoint(nArray3[1], n4);
                        n7 = 1;
                        n6 = n4;
                    }
                    n5 = n4 = n6 + 1;
                    if (n7 != 0) {
                        n7 = 0;
                        nArray3 = new int[]{(int)resultPointArray[0].getX(), (int)resultPointArray[1].getX()};
                        break;
                    }
                    break block10;
                }
                n4 += 5;
            }
            for (n5 = n4; n5 < n2; ++n5) {
                nArray2 = Detector.findGuardPattern(bitMatrix, nArray3[0], n5, n3, false, nArray, nArray4);
                if (nArray2 != null && Math.abs(nArray3[0] - nArray2[0]) < 5 && Math.abs(nArray3[1] - nArray2[1]) < 5) {
                    nArray3 = nArray2;
                    n4 = 0;
                } else {
                    if (n7 > 25) break;
                    n4 = n7 + 1;
                }
                n7 = n4;
            }
            resultPointArray[2] = new ResultPoint(nArray3[0], n5 -= n7 + 1);
            resultPointArray[3] = new ResultPoint(nArray3[1], n5);
        }
        if (n5 - n6 < 10) {
            for (n2 = 0; n2 < resultPointArray.length; ++n2) {
                resultPointArray[n2] = null;
            }
        }
        return resultPointArray;
    }

    private static ResultPoint[] findVertices(BitMatrix bitMatrix, int n2, int n3) {
        int n4 = bitMatrix.getHeight();
        int n5 = bitMatrix.getWidth();
        ResultPoint[] resultPointArray = new ResultPoint[8];
        Detector.copyToResult(resultPointArray, Detector.findRowsWithPattern(bitMatrix, n4, n5, n2, n3, START_PATTERN), INDEXES_START_PATTERN);
        if (resultPointArray[4] != null) {
            n3 = (int)resultPointArray[4].getX();
            n2 = (int)resultPointArray[4].getY();
        }
        Detector.copyToResult(resultPointArray, Detector.findRowsWithPattern(bitMatrix, n4, n5, n2, n3, STOP_PATTERN), INDEXES_STOP_PATTERN);
        return resultPointArray;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static float patternMatchVariance(int[] nArray, int[] nArray2, float f2) {
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
}

