/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.detector.FinderPattern;
import com.google.zxing.qrcode.detector.FinderPatternInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FinderPatternFinder {
    private static final int CENTER_QUORUM = 2;
    protected static final int MAX_MODULES = 57;
    protected static final int MIN_SKIP = 3;
    private final int[] crossCheckStateCount;
    private boolean hasSkipped;
    private final BitMatrix image;
    private final List<FinderPattern> possibleCenters;
    private final ResultPointCallback resultPointCallback;

    public FinderPatternFinder(BitMatrix bitMatrix) {
        this(bitMatrix, null);
    }

    public FinderPatternFinder(BitMatrix bitMatrix, ResultPointCallback resultPointCallback) {
        this.image = bitMatrix;
        this.possibleCenters = new ArrayList<FinderPattern>();
        this.crossCheckStateCount = new int[5];
        this.resultPointCallback = resultPointCallback;
    }

    private static float centerFromEnd(int[] nArray, int n2) {
        return (float)(n2 - nArray[4] - nArray[3]) - (float)nArray[2] / 2.0f;
    }

    private boolean crossCheckDiagonal(int n2, int n3, int n4, int n5) {
        int n6;
        int n7;
        int n8;
        int[] nArray;
        block19: {
            block18: {
                int n9;
                block17: {
                    block16: {
                        nArray = this.getCrossCheckStateCount();
                        for (n9 = 0; n2 >= n9 && n3 >= n9 && this.image.get(n3 - n9, n2 - n9); ++n9) {
                            nArray[2] = nArray[2] + 1;
                        }
                        if (n2 < n9) break block16;
                        if (n3 >= n9) break block17;
                    }
                    return false;
                }
                for (n8 = n9; n2 >= n8 && n3 >= n8 && !this.image.get(n3 - n8, n2 - n8) && nArray[1] <= n4; ++n8) {
                    nArray[1] = nArray[1] + 1;
                }
                if (n2 < n8 || n3 < n8 || nArray[1] > n4) {
                    return false;
                }
                while (n2 >= n8 && n3 >= n8 && this.image.get(n3 - n8, n2 - n8) && nArray[0] <= n4) {
                    nArray[0] = nArray[0] + 1;
                    ++n8;
                }
                if (nArray[0] > n4) {
                    return false;
                }
                n7 = this.image.getHeight();
                n6 = this.image.getWidth();
                n9 = 1;
                while (n2 + n9 < n7 && n3 + n9 < n6 && this.image.get(n3 + n9, n2 + n9)) {
                    nArray[2] = nArray[2] + 1;
                    ++n9;
                }
                if (n2 + n9 >= n7) break block18;
                n8 = n9;
                if (n3 + n9 < n6) break block19;
            }
            return false;
        }
        while (n2 + n8 < n7 && n3 + n8 < n6 && !this.image.get(n3 + n8, n2 + n8) && nArray[3] < n4) {
            nArray[3] = nArray[3] + 1;
            ++n8;
        }
        if (n2 + n8 >= n7 || n3 + n8 >= n6 || nArray[3] >= n4) {
            return false;
        }
        while (n2 + n8 < n7 && n3 + n8 < n6 && this.image.get(n3 + n8, n2 + n8) && nArray[4] < n4) {
            nArray[4] = nArray[4] + 1;
            ++n8;
        }
        if (nArray[4] >= n4) {
            return false;
        }
        return Math.abs(nArray[0] + nArray[1] + nArray[2] + nArray[3] + nArray[4] - n5) < n5 * 2 && FinderPatternFinder.foundPatternCross(nArray);
    }

    private float crossCheckHorizontal(int n2, int n3, int n4, int n5) {
        int n6;
        int n7;
        BitMatrix bitMatrix = this.image;
        int n8 = bitMatrix.getWidth();
        int[] nArray = this.getCrossCheckStateCount();
        for (n7 = n2; n7 >= 0 && bitMatrix.get(n7, n3); --n7) {
            nArray[2] = nArray[2] + 1;
        }
        if (n7 < 0) {
            return Float.NaN;
        }
        for (n6 = n7; n6 >= 0 && !bitMatrix.get(n6, n3) && nArray[1] <= n4; --n6) {
            nArray[1] = nArray[1] + 1;
        }
        if (n6 < 0 || nArray[1] > n4) {
            return Float.NaN;
        }
        while (n6 >= 0 && bitMatrix.get(n6, n3) && nArray[0] <= n4) {
            nArray[0] = nArray[0] + 1;
            --n6;
        }
        if (nArray[0] > n4) {
            return Float.NaN;
        }
        ++n2;
        while (n2 < n8 && bitMatrix.get(n2, n3)) {
            nArray[2] = nArray[2] + 1;
            ++n2;
        }
        if (n2 == n8) {
            return Float.NaN;
        }
        for (n7 = n2; n7 < n8 && !bitMatrix.get(n7, n3) && nArray[3] < n4; ++n7) {
            nArray[3] = nArray[3] + 1;
        }
        if (n7 == n8 || nArray[3] >= n4) {
            return Float.NaN;
        }
        while (n7 < n8 && bitMatrix.get(n7, n3) && nArray[4] < n4) {
            nArray[4] = nArray[4] + 1;
            ++n7;
        }
        if (nArray[4] >= n4) {
            return Float.NaN;
        }
        if (Math.abs(nArray[0] + nArray[1] + nArray[2] + nArray[3] + nArray[4] - n5) * 5 >= n5) {
            return Float.NaN;
        }
        if (FinderPatternFinder.foundPatternCross(nArray)) {
            return FinderPatternFinder.centerFromEnd(nArray, n7);
        }
        return Float.NaN;
    }

    private float crossCheckVertical(int n2, int n3, int n4, int n5) {
        int n6;
        int n7;
        BitMatrix bitMatrix = this.image;
        int n8 = bitMatrix.getHeight();
        int[] nArray = this.getCrossCheckStateCount();
        for (n7 = n2; n7 >= 0 && bitMatrix.get(n3, n7); --n7) {
            nArray[2] = nArray[2] + 1;
        }
        if (n7 < 0) {
            return Float.NaN;
        }
        for (n6 = n7; n6 >= 0 && !bitMatrix.get(n3, n6) && nArray[1] <= n4; --n6) {
            nArray[1] = nArray[1] + 1;
        }
        if (n6 < 0 || nArray[1] > n4) {
            return Float.NaN;
        }
        while (n6 >= 0 && bitMatrix.get(n3, n6) && nArray[0] <= n4) {
            nArray[0] = nArray[0] + 1;
            --n6;
        }
        if (nArray[0] > n4) {
            return Float.NaN;
        }
        ++n2;
        while (n2 < n8 && bitMatrix.get(n3, n2)) {
            nArray[2] = nArray[2] + 1;
            ++n2;
        }
        if (n2 == n8) {
            return Float.NaN;
        }
        for (n7 = n2; n7 < n8 && !bitMatrix.get(n3, n7) && nArray[3] < n4; ++n7) {
            nArray[3] = nArray[3] + 1;
        }
        if (n7 == n8 || nArray[3] >= n4) {
            return Float.NaN;
        }
        while (n7 < n8 && bitMatrix.get(n3, n7) && nArray[4] < n4) {
            nArray[4] = nArray[4] + 1;
            ++n7;
        }
        if (nArray[4] >= n4) {
            return Float.NaN;
        }
        if (Math.abs(nArray[0] + nArray[1] + nArray[2] + nArray[3] + nArray[4] - n5) * 5 >= n5 * 2) {
            return Float.NaN;
        }
        if (FinderPatternFinder.foundPatternCross(nArray)) {
            return FinderPatternFinder.centerFromEnd(nArray, n7);
        }
        return Float.NaN;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int findRowSkip() {
        if (this.possibleCenters.size() > 1) {
            FinderPattern finderPattern = null;
            for (FinderPattern finderPattern2 : this.possibleCenters) {
                if (finderPattern2.getCount() < 2) continue;
                if (finderPattern != null) {
                    this.hasSkipped = true;
                    return (int)(Math.abs(finderPattern.getX() - finderPattern2.getX()) - Math.abs(finderPattern.getY() - finderPattern2.getY())) / 2;
                }
                finderPattern = finderPattern2;
            }
        }
        return 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected static boolean foundPatternCross(int[] nArray) {
        boolean bl2 = true;
        int n2 = 0;
        for (int i2 = 0; i2 < 5; ++i2) {
            int n3 = nArray[i2];
            if (n3 == 0) {
                return false;
            }
            n2 += n3;
        }
        if (n2 < 7) return false;
        float f2 = (float)n2 / 7.0f;
        float f3 = f2 / 2.0f;
        if (!(Math.abs(f2 - (float)nArray[0]) < f3)) return false;
        if (!(Math.abs(f2 - (float)nArray[1]) < f3)) return false;
        if (!(Math.abs(3.0f * f2 - (float)nArray[2]) < 3.0f * f3)) return false;
        if (!(Math.abs(f2 - (float)nArray[3]) < f3)) return false;
        if (!(Math.abs(f2 - (float)nArray[4]) < f3)) return false;
        return bl2;
    }

    private int[] getCrossCheckStateCount() {
        this.crossCheckStateCount[0] = 0;
        this.crossCheckStateCount[1] = 0;
        this.crossCheckStateCount[2] = 0;
        this.crossCheckStateCount[3] = 0;
        this.crossCheckStateCount[4] = 0;
        return this.crossCheckStateCount;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean haveMultiplyConfirmedCenters() {
        block5: {
            block4: {
                int n2 = 0;
                float f2 = 0.0f;
                int n3 = this.possibleCenters.size();
                for (FinderPattern finderPattern : this.possibleCenters) {
                    if (finderPattern.getCount() < 2) continue;
                    ++n2;
                    f2 += finderPattern.getEstimatedModuleSize();
                }
                if (n2 < 3) break block4;
                float f3 = f2 / (float)n3;
                float f4 = 0.0f;
                Iterator<FinderPattern> iterator = this.possibleCenters.iterator();
                while (iterator.hasNext()) {
                    f4 += Math.abs(iterator.next().getEstimatedModuleSize() - f3);
                }
                if (f4 <= 0.05f * f2) break block5;
            }
            return false;
        }
        return true;
    }

    private FinderPattern[] selectBestPatterns() throws NotFoundException {
        Iterator<FinderPattern> iterator;
        float f2;
        int n2 = this.possibleCenters.size();
        if (n2 < 3) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (n2 > 3) {
            float f3 = 0.0f;
            f2 = 0.0f;
            iterator = this.possibleCenters.iterator();
            while (iterator.hasNext()) {
                float f4 = iterator.next().getEstimatedModuleSize();
                f3 += f4;
                f2 += f4 * f4;
            }
            f2 = (float)Math.sqrt(f2 / (float)n2 - (f3 /= (float)n2) * f3);
            Collections.sort(this.possibleCenters, new FurthestFromAverageComparator(f3));
            f2 = Math.max(0.2f * f3, f2);
            n2 = 0;
            while (n2 < this.possibleCenters.size() && this.possibleCenters.size() > 3) {
                int n3 = n2;
                if (Math.abs(this.possibleCenters.get(n2).getEstimatedModuleSize() - f3) > f2) {
                    this.possibleCenters.remove(n2);
                    n3 = n2 - 1;
                }
                n2 = n3 + 1;
            }
        }
        if (this.possibleCenters.size() > 3) {
            f2 = 0.0f;
            iterator = this.possibleCenters.iterator();
            while (iterator.hasNext()) {
                f2 += iterator.next().getEstimatedModuleSize();
            }
            Collections.sort(this.possibleCenters, new CenterComparator(f2 /= (float)this.possibleCenters.size()));
            this.possibleCenters.subList(3, this.possibleCenters.size()).clear();
        }
        return new FinderPattern[]{this.possibleCenters.get(0), this.possibleCenters.get(1), this.possibleCenters.get(2)};
    }

    /*
     * Enabled aggressive block sorting
     */
    final FinderPatternInfo find(Map<DecodeHintType, ?> resultPointArray) throws NotFoundException {
        int n2 = resultPointArray != null && resultPointArray.containsKey((Object)DecodeHintType.TRY_HARDER) ? 1 : 0;
        boolean bl2 = resultPointArray != null && resultPointArray.containsKey((Object)DecodeHintType.PURE_BARCODE);
        int n3 = this.image.getHeight();
        int n4 = this.image.getWidth();
        int n5 = n3 * 3 / 228;
        if (n5 < 3 || n2 != 0) {
            n5 = 3;
        }
        boolean bl3 = false;
        resultPointArray = (ResultPoint[])new int[5];
        int n6 = n5 - 1;
        while (true) {
            boolean bl4;
            Object object;
            if (n6 < n3 && !bl3) {
                resultPointArray[0] = (ResultPoint)false;
                resultPointArray[1] = (ResultPoint)false;
                resultPointArray[2] = (ResultPoint)false;
                resultPointArray[3] = (ResultPoint)false;
                resultPointArray[4] = (ResultPoint)false;
                n2 = 0;
            } else {
                resultPointArray = this.selectBestPatterns();
                ResultPoint.orderBestPatterns(resultPointArray);
                return new FinderPatternInfo((FinderPattern[])resultPointArray);
            }
            for (object = 0; object < n4; ++object) {
                int n7;
                if (this.image.get((int)object, n6)) {
                    n7 = n2;
                    if ((n2 & 1) == 1) {
                        n7 = n2 + 1;
                    }
                    resultPointArray[n7] = resultPointArray[n7] + true;
                    n2 = n7;
                    continue;
                }
                if ((n2 & 1) == 0) {
                    if (n2 == 4) {
                        if (FinderPatternFinder.foundPatternCross((int[])resultPointArray)) {
                            if (this.handlePossibleCenter((int[])resultPointArray, n6, (int)object, bl2)) {
                                n7 = 2;
                                if (this.hasSkipped) {
                                    bl4 = this.haveMultiplyConfirmedCenters();
                                    n5 = n6;
                                } else {
                                    n2 = this.findRowSkip();
                                    bl4 = bl3;
                                    n5 = n6;
                                    if (n2 > resultPointArray[2]) {
                                        n5 = n6 + (n2 - resultPointArray[2] - 2);
                                        object = n4 - 1;
                                        bl4 = bl3;
                                    }
                                }
                                n2 = 0;
                                resultPointArray[0] = (ResultPoint)false;
                                resultPointArray[1] = (ResultPoint)false;
                                resultPointArray[2] = (ResultPoint)false;
                                resultPointArray[3] = (ResultPoint)false;
                                resultPointArray[4] = (ResultPoint)false;
                                bl3 = bl4;
                                n6 = n5;
                                n5 = n7;
                                continue;
                            }
                            resultPointArray[0] = resultPointArray[2];
                            resultPointArray[1] = resultPointArray[3];
                            resultPointArray[2] = resultPointArray[4];
                            resultPointArray[3] = (ResultPoint)true;
                            resultPointArray[4] = (ResultPoint)false;
                            n2 = 3;
                            continue;
                        }
                        resultPointArray[0] = resultPointArray[2];
                        resultPointArray[1] = resultPointArray[3];
                        resultPointArray[2] = resultPointArray[4];
                        resultPointArray[3] = (ResultPoint)true;
                        resultPointArray[4] = (ResultPoint)false;
                        n2 = 3;
                        continue;
                    }
                    resultPointArray[++n2] = resultPointArray[n2] + true;
                    continue;
                }
                resultPointArray[n2] = resultPointArray[n2] + true;
            }
            bl4 = bl3;
            n2 = n5;
            if (FinderPatternFinder.foundPatternCross((int[])resultPointArray)) {
                bl4 = bl3;
                n2 = n5;
                if (this.handlePossibleCenter((int[])resultPointArray, n6, n4, bl2)) {
                    object = resultPointArray[0];
                    bl4 = bl3;
                    n2 = object;
                    if (this.hasSkipped) {
                        bl4 = this.haveMultiplyConfirmedCenters();
                        n2 = object;
                    }
                }
            }
            n6 += n2;
            bl3 = bl4;
            n5 = n2;
        }
    }

    protected final BitMatrix getImage() {
        return this.image;
    }

    protected final List<FinderPattern> getPossibleCenters() {
        return this.possibleCenters;
    }

    protected final boolean handlePossibleCenter(int[] object, int n2, int n3, boolean bl2) {
        block6: {
            int n4 = object[0] + object[1] + object[2] + object[3] + object[4];
            float f2 = FinderPatternFinder.centerFromEnd((int[])object, n3);
            float f3 = this.crossCheckVertical(n2, (int)f2, object[2], n4);
            if (Float.isNaN(f3) || Float.isNaN(f2 = this.crossCheckHorizontal((int)f2, (int)f3, object[2], n4)) || bl2 && !this.crossCheckDiagonal((int)f3, (int)f2, object[2], n4)) break block6;
            float f4 = (float)n4 / 7.0f;
            n4 = 0;
            n2 = 0;
            while (true) {
                block8: {
                    block7: {
                        n3 = n4;
                        if (n2 >= this.possibleCenters.size()) break block7;
                        object = this.possibleCenters.get(n2);
                        if (!((FinderPattern)object).aboutEquals(f4, f3, f2)) break block8;
                        this.possibleCenters.set(n2, ((FinderPattern)object).combineEstimate(f3, f2, f4));
                        n3 = 1;
                    }
                    if (n3 == 0) {
                        object = new FinderPattern(f2, f3, f4);
                        this.possibleCenters.add((FinderPattern)object);
                        if (this.resultPointCallback != null) {
                            this.resultPointCallback.foundPossibleResultPoint((ResultPoint)object);
                        }
                    }
                    return true;
                }
                ++n2;
            }
        }
        return false;
    }

    private static final class CenterComparator
    implements Comparator<FinderPattern>,
    Serializable {
        private final float average;

        private CenterComparator(float f2) {
            this.average = f2;
        }

        @Override
        public int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            if (finderPattern2.getCount() == finderPattern.getCount()) {
                float f2;
                float f3 = Math.abs(finderPattern2.getEstimatedModuleSize() - this.average);
                if (f3 < (f2 = Math.abs(finderPattern.getEstimatedModuleSize() - this.average))) {
                    return 1;
                }
                if (f3 == f2) {
                    return 0;
                }
                return -1;
            }
            return finderPattern2.getCount() - finderPattern.getCount();
        }
    }

    private static final class FurthestFromAverageComparator
    implements Comparator<FinderPattern>,
    Serializable {
        private final float average;

        private FurthestFromAverageComparator(float f2) {
            this.average = f2;
        }

        @Override
        public int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            float f2;
            float f3 = Math.abs(finderPattern2.getEstimatedModuleSize() - this.average);
            if (f3 < (f2 = Math.abs(finderPattern.getEstimatedModuleSize() - this.average))) {
                return -1;
            }
            if (f3 == f2) {
                return 0;
            }
            return 1;
        }
    }
}

