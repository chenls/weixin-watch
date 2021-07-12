/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.aztec.encoder;

import com.google.zxing.aztec.encoder.AztecCode;
import com.google.zxing.aztec.encoder.HighLevelEncoder;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;

public final class Encoder {
    public static final int DEFAULT_AZTEC_LAYERS = 0;
    public static final int DEFAULT_EC_PERCENT = 33;
    private static final int MAX_NB_BITS = 32;
    private static final int MAX_NB_BITS_COMPACT = 4;
    private static final int[] WORD_SIZE = new int[]{4, 6, 6, 8, 8, 8, 8, 8, 8, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};

    private Encoder() {
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int[] bitsToWords(BitArray bitArray, int n2, int n3) {
        int[] nArray = new int[n3];
        n3 = 0;
        int n4 = bitArray.getSize() / n2;
        while (n3 < n4) {
            int n5;
            int n6 = 0;
            for (int i2 = 0; i2 < n2; n6 |= n5, ++i2) {
                n5 = bitArray.get(n3 * n2 + i2) ? 1 << n2 - i2 - 1 : 0;
            }
            nArray[n3] = n6;
            ++n3;
        }
        return nArray;
    }

    private static void drawBullsEye(BitMatrix bitMatrix, int n2, int n3) {
        for (int i2 = 0; i2 < n3; i2 += 2) {
            for (int i3 = n2 - i2; i3 <= n2 + i2; ++i3) {
                bitMatrix.set(i3, n2 - i2);
                bitMatrix.set(i3, n2 + i2);
                bitMatrix.set(n2 - i2, i3);
                bitMatrix.set(n2 + i2, i3);
            }
        }
        bitMatrix.set(n2 - n3, n2 - n3);
        bitMatrix.set(n2 - n3 + 1, n2 - n3);
        bitMatrix.set(n2 - n3, n2 - n3 + 1);
        bitMatrix.set(n2 + n3, n2 - n3);
        bitMatrix.set(n2 + n3, n2 - n3 + 1);
        bitMatrix.set(n2 + n3, n2 + n3 - 1);
    }

    private static void drawModeMessage(BitMatrix bitMatrix, boolean bl2, int n2, BitArray bitArray) {
        int n3 = n2 / 2;
        if (bl2) {
            for (n2 = 0; n2 < 7; ++n2) {
                int n4 = n3 - 3 + n2;
                if (bitArray.get(n2)) {
                    bitMatrix.set(n4, n3 - 5);
                }
                if (bitArray.get(n2 + 7)) {
                    bitMatrix.set(n3 + 5, n4);
                }
                if (bitArray.get(20 - n2)) {
                    bitMatrix.set(n4, n3 + 5);
                }
                if (!bitArray.get(27 - n2)) continue;
                bitMatrix.set(n3 - 5, n4);
            }
        } else {
            for (n2 = 0; n2 < 10; ++n2) {
                int n5 = n3 - 5 + n2 + n2 / 5;
                if (bitArray.get(n2)) {
                    bitMatrix.set(n5, n3 - 7);
                }
                if (bitArray.get(n2 + 10)) {
                    bitMatrix.set(n3 + 7, n5);
                }
                if (bitArray.get(29 - n2)) {
                    bitMatrix.set(n5, n3 + 7);
                }
                if (!bitArray.get(39 - n2)) continue;
                bitMatrix.set(n3 - 7, n5);
            }
        }
    }

    public static AztecCode encode(byte[] byArray) {
        return Encoder.encode(byArray, 33, 0);
    }

    /*
     * Unable to fully structure code
     */
    public static AztecCode encode(byte[] var0, int var1_1, int var2_2) {
        block34: {
            block33: {
                block38: {
                    block39: {
                        block31: {
                            block32: {
                                block37: {
                                    block30: {
                                        block29: {
                                            var14_3 = new HighLevelEncoder((byte[])var0).encode();
                                            var7_4 = var14_3.getSize() * var1_1 / 100 + 11;
                                            var6_5 = var14_3.getSize();
                                            if (var2_2 == 0) break block37;
                                            if (var2_2 < 0) {
                                                var11_6 = true;
lbl7:
                                                // 2 sources

                                                while (true) {
                                                    var4_7 = Math.abs(var2_2);
                                                    if (!var11_6) break block29;
                                                    var1_1 = 4;
lbl11:
                                                    // 2 sources

                                                    while (var4_7 > var1_1) {
                                                        throw new IllegalArgumentException(String.format("Illegal value %s for layers", new Object[]{var2_2}));
                                                    }
                                                    break block30;
                                                    break;
                                                }
                                            }
                                            var11_6 = false;
                                            ** while (true)
                                        }
                                        var1_1 = 32;
                                        ** GOTO lbl11
                                    }
                                    var5_8 = Encoder.totalBitsInLayer(var4_7, var11_6);
                                    var6_5 = Encoder.WORD_SIZE[var4_7];
                                    var13_9 = Encoder.stuffBits(var14_3, var6_5);
                                    if (var13_9.getSize() + var7_4 > var5_8 - var5_8 % var6_5) {
                                        throw new IllegalArgumentException("Data to large for user specified layer");
                                    }
                                    var12_10 = var11_6;
                                    var3_11 = var4_7;
                                    var0 = var13_9;
                                    var2_2 = var5_8;
                                    var1_1 = var6_5;
                                    if (var11_6) {
                                        var12_10 = var11_6;
                                        var3_11 = var4_7;
                                        var0 = var13_9;
                                        var2_2 = var5_8;
                                        var1_1 = var6_5;
                                        if (var13_9.getSize() > var6_5 * 64) {
                                            throw new IllegalArgumentException("Data to large for user specified layer");
                                        }
                                    }
                                    break block38;
                                }
                                var4_7 = 0;
                                var0 = null;
                                var2_2 = 0;
                                block2: while (true) {
                                    if (var2_2 > 32) {
                                        throw new IllegalArgumentException("Data too large for an Aztec code");
                                    }
                                    if (var2_2 > 3) break;
                                    var11_6 = true;
lbl48:
                                    // 2 sources

                                    while (var11_6) {
                                        var3_11 = var2_2 + 1;
lbl50:
                                        // 2 sources

                                        while (var6_5 + var7_4 > (var5_8 = Encoder.totalBitsInLayer(var3_11, var11_6))) {
                                            var13_9 = var0;
lbl52:
                                            // 3 sources

                                            while (true) {
                                                ++var2_2;
                                                var0 = var13_9;
                                                continue block2;
                                                break;
                                            }
                                            continue block2;
                                        }
                                        break block31;
                                    }
                                    break block32;
                                    break;
                                }
                                var11_6 = false;
                                ** GOTO lbl48
                            }
                            var3_11 = var2_2;
                            ** GOTO lbl50
                        }
                        var1_1 = var4_7;
                        if (var4_7 != Encoder.WORD_SIZE[var3_11]) {
                            var1_1 = Encoder.WORD_SIZE[var3_11];
                            var0 = Encoder.stuffBits(var14_3, var1_1);
                        }
                        if (!var11_6) break block39;
                        var13_9 = var0;
                        var4_7 = var1_1;
                        if (var0.getSize() > var1_1 * 64) ** GOTO lbl52
                    }
                    var13_9 = var0;
                    var4_7 = var1_1;
                    if (var0.getSize() + var7_4 <= var5_8 - var5_8 % var1_1) ** break;
                    ** while (true)
                    var2_2 = var5_8;
                    var12_10 = var11_6;
                }
                var13_9 = Encoder.generateCheckWords((BitArray)var0, var2_2, var1_1);
                var9_12 = var0.getSize() / var1_1;
                var14_3 = Encoder.generateModeMessage(var12_10, var3_11, var9_12);
                if (var12_10) {
                    var1_1 = var3_11 * 4 + 11;
lbl86:
                    // 2 sources

                    while (true) {
                        var15_13 = new int[var1_1];
                        if (!var12_10) break block33;
                        var4_7 = var1_1;
                        var5_8 = 0;
                        while (true) {
                            var2_2 = var4_7;
                            if (var5_8 < var15_13.length) {
                                var15_13[var5_8] = var5_8;
                                ++var5_8;
                                continue;
                            }
                            break block34;
                            break;
                        }
                        break;
                    }
                }
                var1_1 = var3_11 * 4 + 14;
                ** while (true)
            }
            var5_8 = var1_1 + 1 + (var1_1 / 2 - 1) / 15 * 2;
            var6_5 = var1_1 / 2;
            var7_4 = var5_8 / 2;
            var4_7 = 0;
            while (true) {
                var2_2 = var5_8;
                if (var4_7 >= var6_5) break;
                var2_2 = var4_7 + var4_7 / 15;
                var15_13[var6_5 - var4_7 - 1] = var7_4 - var2_2 - 1;
                var15_13[var6_5 + var4_7] = var7_4 + var2_2 + 1;
                ++var4_7;
            }
        }
        var0 = new BitMatrix(var2_2);
        var5_8 = 0;
        for (var4_7 = 0; var4_7 < var3_11; ++var4_7) {
            block36: {
                block35: {
                    if (var12_10) {
                        var6_5 = (var3_11 - var4_7) * 4 + 9;
lbl119:
                        // 2 sources

                        while (true) {
                            var7_4 = 0;
lbl121:
                            // 2 sources

                            while (var7_4 < var6_5) {
                                var10_15 = var7_4 * 2;
                                for (var8_14 = 0; var8_14 < 2; ++var8_14) {
                                    if (var13_9.get(var5_8 + var10_15 + var8_14)) {
                                        var0.set(var15_13[var4_7 * 2 + var8_14], var15_13[var4_7 * 2 + var7_4]);
                                    }
                                    if (var13_9.get(var6_5 * 2 + var5_8 + var10_15 + var8_14)) {
                                        var0.set(var15_13[var4_7 * 2 + var7_4], var15_13[var1_1 - 1 - var4_7 * 2 - var8_14]);
                                    }
                                    if (var13_9.get(var6_5 * 4 + var5_8 + var10_15 + var8_14)) {
                                        var0.set(var15_13[var1_1 - 1 - var4_7 * 2 - var8_14], var15_13[var1_1 - 1 - var4_7 * 2 - var7_4]);
                                    }
                                    if (!var13_9.get(var6_5 * 6 + var5_8 + var10_15 + var8_14)) continue;
                                    var0.set(var15_13[var1_1 - 1 - var4_7 * 2 - var7_4], var15_13[var4_7 * 2 + var8_14]);
                                }
                                break block35;
                            }
                            break block36;
                            break;
                        }
                    }
                    var6_5 = (var3_11 - var4_7) * 4 + 12;
                    ** continue;
                }
                ++var7_4;
                ** GOTO lbl121
            }
            var5_8 += var6_5 * 8;
        }
        Encoder.drawModeMessage((BitMatrix)var0, var12_10, var2_2, var14_3);
        if (var12_10) {
            Encoder.drawBullsEye((BitMatrix)var0, var2_2 / 2, 5);
            while (true) {
                var13_9 = new AztecCode();
                var13_9.setCompact(var12_10);
                var13_9.setSize(var2_2);
                var13_9.setLayers(var3_11);
                var13_9.setCodeWords(var9_12);
                var13_9.setMatrix((BitMatrix)var0);
                return var13_9;
            }
        }
        Encoder.drawBullsEye((BitMatrix)var0, var2_2 / 2, 7);
        var5_8 = 0;
        var4_7 = 0;
        while (true) {
            if (var5_8 >= var1_1 / 2 - 1) ** continue;
            for (var6_5 = var2_2 / 2 & 1; var6_5 < var2_2; var6_5 += 2) {
                var0.set(var2_2 / 2 - var4_7, var6_5);
                var0.set(var2_2 / 2 + var4_7, var6_5);
                var0.set(var6_5, var2_2 / 2 - var4_7);
                var0.set(var6_5, var2_2 / 2 + var4_7);
            }
            var5_8 += 15;
            var4_7 += 16;
        }
    }

    private static BitArray generateCheckWords(BitArray object, int n2, int n3) {
        int n4 = 0;
        int n5 = ((BitArray)object).getSize() / n3;
        Object object2 = new ReedSolomonEncoder(Encoder.getGF(n3));
        int n6 = n2 / n3;
        object = Encoder.bitsToWords((BitArray)object, n3, n6);
        ((ReedSolomonEncoder)object2).encode((int[])object, n6 - n5);
        object2 = new BitArray();
        ((BitArray)object2).appendBits(0, n2 % n3);
        n5 = ((Object)object).length;
        for (n2 = n4; n2 < n5; ++n2) {
            ((BitArray)object2).appendBits((int)object[n2], n3);
        }
        return object2;
    }

    static BitArray generateModeMessage(boolean bl2, int n2, int n3) {
        BitArray bitArray = new BitArray();
        if (bl2) {
            bitArray.appendBits(n2 - 1, 2);
            bitArray.appendBits(n3 - 1, 6);
            return Encoder.generateCheckWords(bitArray, 28, 4);
        }
        bitArray.appendBits(n2 - 1, 5);
        bitArray.appendBits(n3 - 1, 11);
        return Encoder.generateCheckWords(bitArray, 40, 4);
    }

    private static GenericGF getGF(int n2) {
        switch (n2) {
            default: {
                return null;
            }
            case 4: {
                return GenericGF.AZTEC_PARAM;
            }
            case 6: {
                return GenericGF.AZTEC_DATA_6;
            }
            case 8: {
                return GenericGF.AZTEC_DATA_8;
            }
            case 10: {
                return GenericGF.AZTEC_DATA_10;
            }
            case 12: 
        }
        return GenericGF.AZTEC_DATA_12;
    }

    /*
     * Enabled aggressive block sorting
     */
    static BitArray stuffBits(BitArray bitArray, int n2) {
        BitArray bitArray2 = new BitArray();
        int n3 = bitArray.getSize();
        int n4 = (1 << n2) - 2;
        int n5 = 0;
        while (n5 < n3) {
            int n6 = 0;
            for (int i2 = 0; i2 < n2; ++i2) {
                int n7;
                block11: {
                    block10: {
                        if (n5 + i2 >= n3) break block10;
                        n7 = n6;
                        if (!bitArray.get(n5 + i2)) break block11;
                    }
                    n7 = n6 | 1 << n2 - 1 - i2;
                }
                n6 = n7;
            }
            if ((n6 & n4) == n4) {
                bitArray2.appendBits(n6 & n4, n2);
                --n5;
            } else if ((n6 & n4) == 0) {
                bitArray2.appendBits(n6 | 1, n2);
                --n5;
            } else {
                bitArray2.appendBits(n6, n2);
            }
            n5 += n2;
        }
        return bitArray2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int totalBitsInLayer(int n2, boolean bl2) {
        int n3;
        if (bl2) {
            n3 = 88;
            return (n3 + n2 * 16) * n2;
        }
        n3 = 112;
        return (n3 + n2 * 16) * n2;
    }
}

