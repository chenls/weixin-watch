/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.multi.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.detector.FinderPattern;
import com.google.zxing.qrcode.detector.FinderPatternFinder;
import com.google.zxing.qrcode.detector.FinderPatternInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

final class MultiFinderPatternFinder
extends FinderPatternFinder {
    private static final float DIFF_MODSIZE_CUTOFF = 0.5f;
    private static final float DIFF_MODSIZE_CUTOFF_PERCENT = 0.05f;
    private static final FinderPatternInfo[] EMPTY_RESULT_ARRAY = new FinderPatternInfo[0];
    private static final float MAX_MODULE_COUNT_PER_EDGE = 180.0f;
    private static final float MIN_MODULE_COUNT_PER_EDGE = 9.0f;

    MultiFinderPatternFinder(BitMatrix bitMatrix) {
        super(bitMatrix);
    }

    MultiFinderPatternFinder(BitMatrix bitMatrix, ResultPointCallback resultPointCallback) {
        super(bitMatrix, resultPointCallback);
    }

    /*
     * Unable to fully structure code
     */
    private FinderPattern[][] selectMutipleBestPatterns() throws NotFoundException {
        var9_1 = this.getPossibleCenters();
        var8_2 = var9_1.size();
        if (var8_2 < 3) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (var8_2 == 3) {
            return new FinderPattern[][]{{var9_1.get(0), var9_1.get(1), var9_1.get(2)}};
        }
        Collections.sort(var9_1, new ModuleSizeComparator());
        var10_3 = new ArrayList<ResultPoint[]>();
        block0: for (var5_4 = 0; var5_4 < var8_2 - 2; ++var5_4) {
            var11_11 = var9_1.get(var5_4);
            if (var11_11 == null) lbl-1000:
            // 3 sources

            {
                continue block0;
            }
            var6_9 = var5_4 + 1;
            block2: while (true) {
                if (var6_9 >= var8_2 - 1) ** GOTO lbl-1000
                var12_12 = var9_1.get(var6_9);
                if (var12_12 != null) break;
lbl19:
                // 3 sources

                while (true) {
                    ++var6_9;
                    continue block2;
                    break;
                }
                break;
            }
            var1_5 = (var11_11.getEstimatedModuleSize() - var12_12.getEstimatedModuleSize()) / Math.min(var11_11.getEstimatedModuleSize(), var12_12.getEstimatedModuleSize());
            if (!(Math.abs(var11_11.getEstimatedModuleSize() - var12_12.getEstimatedModuleSize()) > 0.5f) || !(var1_5 >= 0.05f)) ** break;
            ** continue;
            var7_10 = var6_9 + 1;
            block4: while (true) {
                if (var7_10 >= var8_2) ** GOTO lbl19
                var14_14 = var9_1.get(var7_10);
                if (var14_14 != null) break;
lbl30:
                // 3 sources

                while (true) {
                    ++var7_10;
                    continue block4;
                    break;
                }
                break;
            }
            var1_5 = (var12_12.getEstimatedModuleSize() - var14_14.getEstimatedModuleSize()) / Math.min(var12_12.getEstimatedModuleSize(), var14_14.getEstimatedModuleSize());
            if (!(Math.abs(var12_12.getEstimatedModuleSize() - var14_14.getEstimatedModuleSize()) > 0.5f) || !(var1_5 >= 0.05f)) ** break;
            ** continue;
            var13_13 = new FinderPattern[]{var11_11, var12_12, var14_14};
            ResultPoint.orderBestPatterns(var13_13);
            var14_14 = new FinderPatternInfo((FinderPattern[])var13_13);
            var2_6 = ResultPoint.distance(var14_14.getTopLeft(), var14_14.getBottomLeft());
            var1_5 = ResultPoint.distance(var14_14.getTopRight(), var14_14.getBottomLeft());
            var3_7 = ResultPoint.distance(var14_14.getTopLeft(), var14_14.getTopRight());
            var4_8 = (var2_6 + var3_7) / (var11_11.getEstimatedModuleSize() * 2.0f);
            if (var4_8 > 180.0f || var4_8 < 9.0f || Math.abs((var2_6 - var3_7) / Math.min(var2_6, var3_7)) >= 0.1f || Math.abs((var1_5 - (var2_6 = (float)Math.sqrt(var2_6 * var2_6 + var3_7 * var3_7))) / Math.min(var1_5, var2_6)) >= 0.1f) ** GOTO lbl30
            var10_3.add(var13_13);
            ** continue;
        }
        if (!var10_3.isEmpty()) {
            return (FinderPattern[][])var10_3.toArray((T[])new FinderPattern[var10_3.size()][]);
        }
        throw NotFoundException.getNotFoundInstance();
    }

    /*
     * Enabled aggressive block sorting
     */
    public FinderPatternInfo[] findMulti(Map<DecodeHintType, ?> object) throws NotFoundException {
        int n2 = object != null && object.containsKey((Object)DecodeHintType.TRY_HARDER) ? 1 : 0;
        boolean bl2 = object != null && object.containsKey((Object)DecodeHintType.PURE_BARCODE);
        object = this.getImage();
        int n3 = ((BitMatrix)object).getHeight();
        int n4 = ((BitMatrix)object).getWidth();
        int n5 = (int)((float)n3 / 228.0f * 3.0f);
        if (n5 < 3 || n2 != 0) {
            n5 = 3;
        }
        Object object2 = new int[5];
        int n6 = n5 - 1;
        while (true) {
            if (n6 < n3) {
                object2[0] = 0;
                object2[1] = 0;
                object2[2] = 0;
                object2[3] = 0;
                object2[4] = false;
                n2 = 0;
            } else {
                object = this.selectMutipleBestPatterns();
                object2 = new ArrayList();
                for (Object object3 : object) {
                    ResultPoint.orderBestPatterns((ResultPoint[])object3);
                    object2.add(new FinderPatternInfo((FinderPattern[])object3));
                }
                if (object2.isEmpty()) {
                    return EMPTY_RESULT_ARRAY;
                }
                return object2.toArray(new FinderPatternInfo[object2.size()]);
            }
            for (int i2 = 0; i2 < n4; ++i2) {
                if (((BitMatrix)object).get(i2, n6)) {
                    int n7 = n2;
                    if ((n2 & 1) == 1) {
                        n7 = n2 + 1;
                    }
                    object2[n7] = object2[n7] + true;
                    n2 = n7;
                    continue;
                }
                if ((n2 & 1) == 0) {
                    if (n2 == 4) {
                        if (MultiFinderPatternFinder.foundPatternCross((int[])object2) && this.handlePossibleCenter((int[])object2, n6, i2, bl2)) {
                            n2 = 0;
                            object2[0] = false;
                            object2[1] = false;
                            object2[2] = false;
                            object2[3] = false;
                            object2[4] = false;
                            continue;
                        }
                        object2[0] = object2[2];
                        object2[1] = object2[3];
                        object2[2] = object2[4];
                        object2[3] = true;
                        object2[4] = false;
                        n2 = 3;
                        continue;
                    }
                    object2[++n2] = object2[n2] + true;
                    continue;
                }
                object2[n2] = object2[n2] + true;
            }
            if (MultiFinderPatternFinder.foundPatternCross((int[])object2)) {
                this.handlePossibleCenter((int[])object2, n6, n4, bl2);
            }
            n6 += n5;
        }
    }

    private static final class ModuleSizeComparator
    implements Comparator<FinderPattern>,
    Serializable {
        private ModuleSizeComparator() {
        }

        @Override
        public int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            float f2 = finderPattern2.getEstimatedModuleSize() - finderPattern.getEstimatedModuleSize();
            if ((double)f2 < 0.0) {
                return -1;
            }
            if ((double)f2 > 0.0) {
                return 1;
            }
            return 0;
        }
    }
}

