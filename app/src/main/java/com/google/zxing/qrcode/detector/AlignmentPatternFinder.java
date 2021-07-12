/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.qrcode.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.detector.AlignmentPattern;
import java.util.ArrayList;
import java.util.List;

final class AlignmentPatternFinder {
    private final int[] crossCheckStateCount;
    private final int height;
    private final BitMatrix image;
    private final float moduleSize;
    private final List<AlignmentPattern> possibleCenters;
    private final ResultPointCallback resultPointCallback;
    private final int startX;
    private final int startY;
    private final int width;

    AlignmentPatternFinder(BitMatrix bitMatrix, int n2, int n3, int n4, int n5, float f2, ResultPointCallback resultPointCallback) {
        this.image = bitMatrix;
        this.possibleCenters = new ArrayList<AlignmentPattern>(5);
        this.startX = n2;
        this.startY = n3;
        this.width = n4;
        this.height = n5;
        this.moduleSize = f2;
        this.crossCheckStateCount = new int[3];
        this.resultPointCallback = resultPointCallback;
    }

    private static float centerFromEnd(int[] nArray, int n2) {
        return (float)(n2 - nArray[2]) - (float)nArray[1] / 2.0f;
    }

    /*
     * Enabled aggressive block sorting
     */
    private float crossCheckVertical(int n2, int n3, int n4, int n5) {
        int[] nArray;
        block7: {
            block6: {
                int n6;
                BitMatrix bitMatrix = this.image;
                int n7 = bitMatrix.getHeight();
                nArray = this.crossCheckStateCount;
                nArray[0] = 0;
                nArray[1] = 0;
                nArray[2] = 0;
                for (n6 = n2; n6 >= 0 && bitMatrix.get(n3, n6) && nArray[1] <= n4; --n6) {
                    nArray[1] = nArray[1] + 1;
                }
                if (n6 < 0 || nArray[1] > n4) break block6;
                while (n6 >= 0 && !bitMatrix.get(n3, n6) && nArray[0] <= n4) {
                    nArray[0] = nArray[0] + 1;
                    --n6;
                }
                if (nArray[0] > n4) break block6;
                ++n2;
                while (n2 < n7 && bitMatrix.get(n3, n2) && nArray[1] <= n4) {
                    nArray[1] = nArray[1] + 1;
                    ++n2;
                }
                if (n2 == n7 || nArray[1] > n4) break block6;
                while (n2 < n7 && !bitMatrix.get(n3, n2) && nArray[2] <= n4) {
                    nArray[2] = nArray[2] + 1;
                    ++n2;
                }
                if (nArray[2] <= n4 && Math.abs(nArray[0] + nArray[1] + nArray[2] - n5) * 5 < n5 * 2 && this.foundPatternCross(nArray)) break block7;
            }
            return Float.NaN;
        }
        return AlignmentPatternFinder.centerFromEnd(nArray, n2);
    }

    private boolean foundPatternCross(int[] nArray) {
        float f2 = this.moduleSize;
        float f3 = f2 / 2.0f;
        for (int i2 = 0; i2 < 3; ++i2) {
            if (!(Math.abs(f2 - (float)nArray[i2]) >= f3)) continue;
            return false;
        }
        return true;
    }

    private AlignmentPattern handlePossibleCenter(int[] object, int n2, int n3) {
        int n4 = object[0];
        int n5 = object[1];
        int n6 = object[2];
        float f2 = AlignmentPatternFinder.centerFromEnd((int[])object, n3);
        float f3 = this.crossCheckVertical(n2, (int)f2, object[1] * 2, n4 + n5 + n6);
        if (!Float.isNaN(f3)) {
            float f4 = (float)(object[0] + object[1] + object[2]) / 3.0f;
            object = this.possibleCenters.iterator();
            while (object.hasNext()) {
                AlignmentPattern alignmentPattern = (AlignmentPattern)object.next();
                if (!alignmentPattern.aboutEquals(f4, f3, f2)) continue;
                return alignmentPattern.combineEstimate(f3, f2, f4);
            }
            object = new AlignmentPattern(f2, f3, f4);
            this.possibleCenters.add((AlignmentPattern)object);
            if (this.resultPointCallback != null) {
                this.resultPointCallback.foundPossibleResultPoint((ResultPoint)object);
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    AlignmentPattern find() throws NotFoundException {
        int n2 = this.startX;
        int n3 = this.height;
        int n4 = n2 + this.width;
        int n5 = this.startY;
        int n6 = n3 / 2;
        int[] nArray = new int[3];
        int n7 = 0;
        while (true) {
            block17: {
                AlignmentPattern alignmentPattern;
                block13: {
                    AlignmentPattern alignmentPattern2;
                    int n8;
                    int n9;
                    int n10;
                    int n11;
                    if (n7 < n3) {
                        n11 = (n7 & 1) == 0 ? (n7 + 1) / 2 : -((n7 + 1) / 2);
                        n10 = n5 + n6 + n11;
                        nArray[0] = 0;
                        nArray[1] = 0;
                        nArray[2] = 0;
                        for (n11 = n2; n11 < n4 && !this.image.get(n11, n10); ++n11) {
                        }
                        n9 = 0;
                        n8 = n11;
                        n11 = n9;
                    } else {
                        if (!this.possibleCenters.isEmpty()) {
                            return this.possibleCenters.get(0);
                        }
                        throw NotFoundException.getNotFoundInstance();
                    }
                    while (n8 < n4) {
                        block16: {
                            block14: {
                                block15: {
                                    if (!this.image.get(n8, n10)) break block14;
                                    if (n11 != 1) break block15;
                                    nArray[n11] = nArray[n11] + 1;
                                    break block16;
                                }
                                if (n11 == 2) {
                                    if (this.foundPatternCross(nArray) && (alignmentPattern = this.handlePossibleCenter(nArray, n10, n8)) != null) break block13;
                                    nArray[0] = nArray[2];
                                    nArray[1] = 1;
                                    nArray[2] = 0;
                                    n11 = 1;
                                    break block16;
                                } else {
                                    nArray[++n11] = nArray[n11] + 1;
                                }
                                break block16;
                            }
                            n9 = n11;
                            if (n11 == 1) {
                                n9 = n11 + 1;
                            }
                            nArray[n9] = nArray[n9] + 1;
                            n11 = n9;
                        }
                        ++n8;
                    }
                    if (!this.foundPatternCross(nArray)) break block17;
                    alignmentPattern = alignmentPattern2 = this.handlePossibleCenter(nArray, n10, n4);
                    if (alignmentPattern2 == null) break block17;
                }
                return alignmentPattern;
            }
            ++n7;
        }
    }
}

