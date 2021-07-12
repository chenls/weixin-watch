/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.common.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.detector.MathUtils;

public final class WhiteRectangleDetector {
    private static final int CORR = 1;
    private static final int INIT_SIZE = 10;
    private final int downInit;
    private final int height;
    private final BitMatrix image;
    private final int leftInit;
    private final int rightInit;
    private final int upInit;
    private final int width;

    public WhiteRectangleDetector(BitMatrix bitMatrix) throws NotFoundException {
        this(bitMatrix, 10, bitMatrix.getWidth() / 2, bitMatrix.getHeight() / 2);
    }

    public WhiteRectangleDetector(BitMatrix bitMatrix, int n2, int n3, int n4) throws NotFoundException {
        this.image = bitMatrix;
        this.height = bitMatrix.getHeight();
        this.width = bitMatrix.getWidth();
        this.leftInit = n3 - (n2 /= 2);
        this.rightInit = n3 + n2;
        this.upInit = n4 - n2;
        this.downInit = n4 + n2;
        if (this.upInit < 0 || this.leftInit < 0 || this.downInit >= this.height || this.rightInit >= this.width) {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    private ResultPoint[] centerEdges(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4) {
        float f2 = resultPoint.getX();
        float f3 = resultPoint.getY();
        float f4 = resultPoint2.getX();
        float f5 = resultPoint2.getY();
        float f6 = resultPoint3.getX();
        float f7 = resultPoint3.getY();
        float f8 = resultPoint4.getX();
        float f9 = resultPoint4.getY();
        if (f2 < (float)this.width / 2.0f) {
            return new ResultPoint[]{new ResultPoint(f8 - 1.0f, 1.0f + f9), new ResultPoint(1.0f + f4, 1.0f + f5), new ResultPoint(f6 - 1.0f, f7 - 1.0f), new ResultPoint(1.0f + f2, f3 - 1.0f)};
        }
        return new ResultPoint[]{new ResultPoint(1.0f + f8, 1.0f + f9), new ResultPoint(1.0f + f4, f5 - 1.0f), new ResultPoint(f6 - 1.0f, 1.0f + f7), new ResultPoint(f2 - 1.0f, f3 - 1.0f)};
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean containsBlackPoint(int n2, int n3, int n4, boolean bl2) {
        if (bl2) {
            while (n2 <= n3) {
                if (this.image.get(n2, n4)) return true;
                ++n2;
            }
            return false;
        }
        while (n2 <= n3) {
            if (this.image.get(n4, n2)) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    private ResultPoint getBlackPointOnSegment(float f2, float f3, float f4, float f5) {
        int n2 = MathUtils.round(MathUtils.distance(f2, f3, f4, f5));
        f4 = (f4 - f2) / (float)n2;
        f5 = (f5 - f3) / (float)n2;
        for (int i2 = 0; i2 < n2; ++i2) {
            int n3;
            int n4 = MathUtils.round((float)i2 * f4 + f2);
            if (!this.image.get(n4, n3 = MathUtils.round((float)i2 * f5 + f3))) continue;
            return new ResultPoint(n4, n3);
        }
        return null;
    }

    /*
     * Unable to fully structure code
     */
    public ResultPoint[] detect() throws NotFoundException {
        block27: {
            block26: {
                var6_1 = this.leftInit;
                var8_2 = this.rightInit;
                var5_3 = this.upInit;
                var7_4 = this.downInit;
                var20_5 = 0;
                var18_6 = 1;
                var10_7 = false;
                var16_8 = false;
                var15_9 = false;
                var12_10 = false;
                var11_11 = false;
                block0: while (true) {
                    block32: {
                        block31: {
                            block30: {
                                block29: {
                                    block28: {
                                        var9_16 = var7_4;
                                        var3_14 = var6_1;
                                        var2_13 = var8_2;
                                        var1_12 = var20_5;
                                        var4_15 = var5_3;
                                        if (var18_6 == 0) break block28;
                                        var3_14 = 0;
                                        var21_21 = true;
                                        var2_13 = var8_2;
                                        var13_17 = var16_8;
                                        while ((var21_21 || !var13_17) && var2_13 < this.width) {
                                            var22_22 = this.containsBlackPoint(var5_3, var7_4, var2_13, false);
                                            if (var22_22) {
                                                ++var2_13;
                                                var3_14 = 1;
                                                var13_17 = true;
                                                var21_21 = var22_22;
                                                continue;
                                            }
                                            var21_21 = var22_22;
                                            if (var13_17) continue;
                                            ++var2_13;
                                            var21_21 = var22_22;
                                        }
                                        if (var2_13 < this.width) break block29;
                                        var1_12 = 1;
                                        var4_15 = var5_3;
                                        var3_14 = var6_1;
                                        var9_16 = var7_4;
                                    }
lbl42:
                                    // 4 sources

                                    while (var1_12 == 0 && var10_7) {
                                        var5_3 = var2_13 - var3_14;
                                        var23_23 = null;
                                        var1_12 = 1;
lbl46:
                                        // 2 sources

                                        while (true) {
                                            var24_24 = var23_23;
                                            if (var1_12 < var5_3) {
                                                var23_23 = this.getBlackPointOnSegment(var3_14, var9_16 - var1_12, var3_14 + var1_12, var9_16);
                                                if (var23_23 == null) break block0;
                                                var24_24 = var23_23;
                                            }
                                            if (var24_24 == null) {
                                                throw NotFoundException.getNotFoundInstance();
                                            }
                                            break block26;
                                            break;
                                        }
                                    }
                                    break block27;
                                }
                                var21_21 = true;
                                var1_12 = var7_4;
                                var14_18 = var15_9;
                                var4_15 = var3_14;
                                while ((var21_21 || !var14_18) && var1_12 < this.height) {
                                    var22_22 = this.containsBlackPoint(var6_1, var2_13, var1_12, true);
                                    if (var22_22) {
                                        ++var1_12;
                                        var4_15 = 1;
                                        var14_18 = true;
                                        var21_21 = var22_22;
                                        continue;
                                    }
                                    var21_21 = var22_22;
                                    if (var14_18) continue;
                                    ++var1_12;
                                    var21_21 = var22_22;
                                }
                                if (var1_12 < this.height) break block30;
                                var4_15 = 1;
                                var9_16 = var1_12;
                                var3_14 = var6_1;
                                var1_12 = var4_15;
                                var4_15 = var5_3;
                                ** GOTO lbl42
                            }
                            var21_21 = true;
                            var3_14 = var6_1;
                            var17_19 = var12_10;
                            var9_16 = var4_15;
                            while ((var21_21 || !var17_19) && var3_14 >= 0) {
                                var22_22 = this.containsBlackPoint(var5_3, var1_12, var3_14, false);
                                if (var22_22) {
                                    --var3_14;
                                    var9_16 = 1;
                                    var17_19 = true;
                                    var21_21 = var22_22;
                                    continue;
                                }
                                var21_21 = var22_22;
                                if (var17_19) continue;
                                --var3_14;
                                var21_21 = var22_22;
                            }
                            if (var3_14 >= 0) break block31;
                            var4_15 = 1;
                            var9_16 = var1_12;
                            var1_12 = var4_15;
                            var4_15 = var5_3;
                            ** GOTO lbl42
                        }
                        var21_21 = true;
                        var4_15 = var5_3;
                        var19_20 = var11_11;
                        while ((var21_21 || !var19_20) && var4_15 >= 0) {
                            var22_22 = this.containsBlackPoint(var3_14, var2_13, var4_15, true);
                            if (var22_22) {
                                --var4_15;
                                var9_16 = 1;
                                var19_20 = true;
                                var21_21 = var22_22;
                                continue;
                            }
                            var21_21 = var22_22;
                            if (var19_20) continue;
                            --var4_15;
                            var21_21 = var22_22;
                        }
                        if (var4_15 >= 0) break block32;
                        var5_3 = 1;
                        var9_16 = var1_12;
                        var1_12 = var5_3;
                        ** GOTO lbl42
                    }
                    var18_6 = var9_16;
                    var15_9 = var14_18;
                    var12_10 = var17_19;
                    var16_8 = var13_17;
                    var11_11 = var19_20;
                    var7_4 = var1_12;
                    var6_1 = var3_14;
                    var8_2 = var2_13;
                    var5_3 = var4_15;
                    if (var9_16 == 0) continue;
                    var10_7 = true;
                    var18_6 = var9_16;
                    var15_9 = var14_18;
                    var12_10 = var17_19;
                    var16_8 = var13_17;
                    var11_11 = var19_20;
                    var7_4 = var1_12;
                    var6_1 = var3_14;
                    var8_2 = var2_13;
                    var5_3 = var4_15;
                }
                ++var1_12;
                ** while (true)
            }
            var23_23 = null;
            var1_12 = 1;
            while (true) {
                block34: {
                    block33: {
                        var25_25 = var23_23;
                        if (var1_12 >= var5_3) break block33;
                        var23_23 = this.getBlackPointOnSegment(var3_14, var4_15 + var1_12, var3_14 + var1_12, var4_15);
                        if (var23_23 == null) break block34;
                        var25_25 = var23_23;
                    }
                    if (var25_25 != null) break;
                    throw NotFoundException.getNotFoundInstance();
                }
                ++var1_12;
            }
            var23_23 = null;
            var1_12 = 1;
            while (true) {
                block36: {
                    block35: {
                        var26_26 = var23_23;
                        if (var1_12 >= var5_3) break block35;
                        var23_23 = this.getBlackPointOnSegment(var2_13, var4_15 + var1_12, var2_13 - var1_12, var4_15);
                        if (var23_23 == null) break block36;
                        var26_26 = var23_23;
                    }
                    if (var26_26 != null) break;
                    throw NotFoundException.getNotFoundInstance();
                }
                ++var1_12;
            }
            var23_23 = null;
            var1_12 = 1;
            while (true) {
                if (var1_12 >= var5_3 || (var23_23 = this.getBlackPointOnSegment(var2_13, var9_16 - var1_12, var2_13 - var1_12, var9_16)) != null) {
                    if (var23_23 != null) break;
                    throw NotFoundException.getNotFoundInstance();
                }
                ++var1_12;
            }
            return this.centerEdges(var23_23, var24_24, var26_26, var25_25);
        }
        throw NotFoundException.getNotFoundInstance();
    }
}

