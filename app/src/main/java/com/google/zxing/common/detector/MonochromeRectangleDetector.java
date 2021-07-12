/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.common.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

public final class MonochromeRectangleDetector {
    private static final int MAX_MODULES = 32;
    private final BitMatrix image;

    public MonochromeRectangleDetector(BitMatrix bitMatrix) {
        this.image = bitMatrix;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int[] blackWhiteRange(int n2, int n3, int n4, int n5, boolean bl2) {
        int n6;
        int n7;
        block10: {
            int n8;
            int n9;
            block9: {
                n8 = n9 = (n4 + n5) / 2;
                while (true) {
                    n8 = n7 = n8;
                    if (n7 < n4) break block9;
                    if (bl2 ? this.image.get(n7, n2) : this.image.get(n2, n7)) {
                        n8 = n7 - 1;
                        continue;
                    }
                    n8 = n7;
                    while ((n6 = n8 - 1) >= n4) {
                        if (bl2) {
                            n8 = n6;
                            if (!this.image.get(n6, n2)) continue;
                            break;
                        }
                        n8 = n6;
                        if (!this.image.get(n2, n6)) continue;
                    }
                    if (n6 < n4) break;
                    n8 = n6;
                    if (n7 - n6 > n3) break;
                }
                n8 = n7;
            }
            n6 = n8 + 1;
            n7 = n9;
            while (true) {
                n7 = n4 = n7;
                if (n4 >= n5) break block10;
                if (bl2 ? this.image.get(n4, n2) : this.image.get(n2, n4)) {
                    n7 = n4 + 1;
                    continue;
                }
                n7 = n4;
                while ((n8 = n7 + 1) < n5) {
                    if (bl2) {
                        n7 = n8;
                        if (!this.image.get(n8, n2)) continue;
                        break;
                    }
                    n7 = n8;
                    if (!this.image.get(n2, n8)) continue;
                }
                if (n8 >= n5) break;
                n7 = n8;
                if (n8 - n4 > n3) break;
            }
            n7 = n4;
        }
        if ((n2 = n7 - 1) > n6) {
            return new int[]{n6, n2};
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private ResultPoint findCornerFromCenter(int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10) throws NotFoundException {
        int[] nArray = null;
        int n11 = n6;
        int n12 = n2;
        while (n11 < n9) {
            if (n11 < n8) throw NotFoundException.getNotFoundInstance();
            if (n12 >= n5) throw NotFoundException.getNotFoundInstance();
            if (n12 < n4) throw NotFoundException.getNotFoundInstance();
            int[] nArray2 = n3 == 0 ? this.blackWhiteRange(n11, n10, n4, n5, true) : this.blackWhiteRange(n12, n10, n8, n9, false);
            if (nArray2 == null) {
                float f2;
                if (nArray == null) {
                    throw NotFoundException.getNotFoundInstance();
                }
                if (n3 == 0) {
                    float f3;
                    n3 = n11 - n7;
                    if (nArray[0] >= n2) return new ResultPoint(nArray[1], n3);
                    if (nArray[1] <= n2) return new ResultPoint(nArray[0], n3);
                    if (n7 > 0) {
                        f3 = nArray[0];
                        return new ResultPoint(f3, n3);
                    }
                    f3 = nArray[1];
                    return new ResultPoint(f3, n3);
                }
                n2 = n12 - n3;
                if (nArray[0] >= n6) return new ResultPoint(n2, nArray[1]);
                if (nArray[1] <= n6) return new ResultPoint(n2, nArray[0]);
                float f4 = n2;
                if (n3 < 0) {
                    f2 = nArray[0];
                    return new ResultPoint(f4, f2);
                }
                f2 = nArray[1];
                return new ResultPoint(f4, f2);
            }
            n11 += n7;
            n12 += n3;
            nArray = nArray2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public ResultPoint[] detect() throws NotFoundException {
        int n2 = this.image.getHeight();
        int n3 = this.image.getWidth();
        int n4 = n2 / 2;
        int n5 = n3 / 2;
        int n6 = Math.max(1, n2 / 256);
        int n7 = Math.max(1, n3 / 256);
        int n8 = (int)this.findCornerFromCenter(n5, 0, 0, n3, n4, -n6, 0, n2, n5 / 2).getY() - 1;
        ResultPoint resultPoint = this.findCornerFromCenter(n5, -n7, 0, n3, n4, 0, n8, n2, n4 / 2);
        int n9 = (int)resultPoint.getX() - 1;
        ResultPoint resultPoint2 = this.findCornerFromCenter(n5, n7, n9, n3, n4, 0, n8, n2, n4 / 2);
        n3 = (int)resultPoint2.getX() + 1;
        ResultPoint resultPoint3 = this.findCornerFromCenter(n5, 0, n9, n3, n4, n6, n8, n2, n5 / 2);
        n2 = (int)resultPoint3.getY();
        return new ResultPoint[]{this.findCornerFromCenter(n5, 0, n9, n3, n4, -n6, n8, n2 + 1, n5 / 4), resultPoint, resultPoint2, resultPoint3};
    }
}

