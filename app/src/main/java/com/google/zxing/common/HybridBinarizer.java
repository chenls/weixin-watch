/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.common;

import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GlobalHistogramBinarizer;
import java.lang.reflect.Array;

public final class HybridBinarizer
extends GlobalHistogramBinarizer {
    private static final int BLOCK_SIZE = 8;
    private static final int BLOCK_SIZE_MASK = 7;
    private static final int BLOCK_SIZE_POWER = 3;
    private static final int MINIMUM_DIMENSION = 40;
    private static final int MIN_DYNAMIC_RANGE = 24;
    private BitMatrix matrix;

    public HybridBinarizer(LuminanceSource luminanceSource) {
        super(luminanceSource);
    }

    private static int[][] calculateBlackPoints(byte[] byArray, int n2, int n3, int n4, int n5) {
        int[][] nArray = (int[][])Array.newInstance(Integer.TYPE, n3, n2);
        for (int i2 = 0; i2 < n3; ++i2) {
            int n6 = i2 << 3;
            int n7 = n5 - 8;
            int n8 = n6;
            if (n6 > n7) {
                n8 = n7;
            }
            for (int i3 = 0; i3 < n2; ++i3) {
                n7 = i3 << 3;
                int n9 = n4 - 8;
                n6 = n7;
                if (n7 > n9) {
                    n6 = n9;
                }
                n7 = 0;
                int n10 = 255;
                int n11 = 0;
                n9 = 0;
                int n12 = n8 * n4 + n6;
                n6 = n7;
                n7 = n12;
                while (n9 < 8) {
                    int n13;
                    int n14;
                    for (n12 = 0; n12 < 8; ++n12) {
                        n14 = byArray[n7 + n12] & 0xFF;
                        n13 = n6 + n14;
                        n6 = n10;
                        if (n14 < n10) {
                            n6 = n14;
                        }
                        n10 = n11;
                        if (n14 > n11) {
                            n10 = n14;
                        }
                        n11 = n10;
                        n10 = n6;
                        n6 = n13;
                    }
                    n14 = n7;
                    n12 = n6;
                    n13 = n9++;
                    if (n11 - n10 > 24) {
                        n12 = n7 + n4;
                        n7 = n9;
                        n9 = n6;
                        n6 = n12;
                        while (true) {
                            n14 = n6;
                            n12 = n9;
                            n13 = ++n7;
                            if (n7 >= 8) break;
                            for (n12 = 0; n12 < 8; ++n12) {
                                n9 += byArray[n6 + n12] & 0xFF;
                            }
                            n6 += n4;
                        }
                    }
                    n9 = n13 + 1;
                    n7 = n14 + n4;
                    n6 = n12;
                }
                n6 >>= 6;
                if (n11 - n10 <= 24) {
                    n6 = n7 = n10 / 2;
                    if (i2 > 0) {
                        n6 = n7;
                        if (i3 > 0) {
                            n9 = (nArray[i2 - 1][i3] + nArray[i2][i3 - 1] * 2 + nArray[i2 - 1][i3 - 1]) / 4;
                            n6 = n7;
                            if (n10 < n9) {
                                n6 = n9;
                            }
                        }
                    }
                }
                nArray[i2][i3] = n6;
            }
        }
        return nArray;
    }

    private static void calculateThresholdForBlock(byte[] byArray, int n2, int n3, int n4, int n5, int[][] nArray, BitMatrix bitMatrix) {
        for (int i2 = 0; i2 < n3; ++i2) {
            int n6 = i2 << 3;
            int n7 = n5 - 8;
            int n8 = n6;
            if (n6 > n7) {
                n8 = n7;
            }
            for (n6 = 0; n6 < n2; ++n6) {
                int n9 = n6 << 3;
                int n10 = n4 - 8;
                n7 = n9;
                if (n9 > n10) {
                    n7 = n10;
                }
                int n11 = HybridBinarizer.cap(n6, 2, n2 - 3);
                int n12 = HybridBinarizer.cap(i2, 2, n3 - 3);
                n10 = 0;
                for (n9 = -2; n9 <= 2; ++n9) {
                    int[] nArray2 = nArray[n12 + n9];
                    n10 += nArray2[n11 - 2] + nArray2[n11 - 1] + nArray2[n11] + nArray2[n11 + 1] + nArray2[n11 + 2];
                }
                HybridBinarizer.thresholdBlock(byArray, n7, n8, n10 / 25, n4, bitMatrix);
            }
        }
    }

    private static int cap(int n2, int n3, int n4) {
        if (n2 < n3) {
            return n3;
        }
        if (n2 > n4) {
            return n4;
        }
        return n2;
    }

    private static void thresholdBlock(byte[] byArray, int n2, int n3, int n4, int n5, BitMatrix bitMatrix) {
        int n6 = 0;
        int n7 = n3 * n5 + n2;
        while (n6 < 8) {
            for (int i2 = 0; i2 < 8; ++i2) {
                if ((byArray[n7 + i2] & 0xFF) > n4) continue;
                bitMatrix.set(n2 + i2, n3 + n6);
            }
            ++n6;
            n7 += n5;
        }
    }

    @Override
    public Binarizer createBinarizer(LuminanceSource luminanceSource) {
        return new HybridBinarizer(luminanceSource);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public BitMatrix getBlackMatrix() throws NotFoundException {
        if (this.matrix != null) {
            return this.matrix;
        }
        Object object = this.getLuminanceSource();
        int n2 = ((LuminanceSource)object).getWidth();
        int n3 = ((LuminanceSource)object).getHeight();
        if (n2 >= 40 && n3 >= 40) {
            int n4;
            int n5;
            object = ((LuminanceSource)object).getMatrix();
            int n6 = n5 = n2 >> 3;
            if ((n2 & 7) != 0) {
                n6 = n5 + 1;
            }
            n5 = n4 = n3 >> 3;
            if ((n3 & 7) != 0) {
                n5 = n4 + 1;
            }
            int[][] nArray = HybridBinarizer.calculateBlackPoints((byte[])object, n6, n5, n2, n3);
            BitMatrix bitMatrix = new BitMatrix(n2, n3);
            HybridBinarizer.calculateThresholdForBlock((byte[])object, n6, n5, n2, n3, nArray, bitMatrix);
            this.matrix = bitMatrix;
            return this.matrix;
        }
        this.matrix = super.getBlackMatrix();
        return this.matrix;
    }
}

