/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.qrcode.encoder;

import com.google.zxing.qrcode.encoder.ByteMatrix;

final class MaskUtil {
    private static final int N1 = 3;
    private static final int N2 = 3;
    private static final int N3 = 40;
    private static final int N4 = 10;

    private MaskUtil() {
    }

    static int applyMaskPenaltyRule1(ByteMatrix byteMatrix) {
        return MaskUtil.applyMaskPenaltyRule1Internal(byteMatrix, true) + MaskUtil.applyMaskPenaltyRule1Internal(byteMatrix, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int applyMaskPenaltyRule1Internal(ByteMatrix object, boolean bl2) {
        int n2 = 0;
        int n3 = bl2 ? ((ByteMatrix)object).getHeight() : ((ByteMatrix)object).getWidth();
        int n4 = bl2 ? ((ByteMatrix)object).getWidth() : ((ByteMatrix)object).getHeight();
        object = ((ByteMatrix)object).getArray();
        int n5 = 0;
        while (n5 < n3) {
            Object object2;
            int n6 = 0;
            int n7 = -1;
            for (int i2 = 0; i2 < n4; ++i2) {
                object2 = bl2 ? (Object)object[n5][i2] : (Object)object[i2][n5];
                if (object2 == n7) {
                    object2 = n7;
                    n7 = n2;
                    n2 = ++n6;
                } else {
                    n7 = n2;
                    if (n6 >= 5) {
                        n7 = n2 + (n6 - 5 + 3);
                    }
                    n2 = 1;
                }
                n6 = n2;
                n2 = n7;
                n7 = object2;
            }
            object2 = n2;
            if (n6 >= 5) {
                object2 = n2 + (n6 - 5 + 3);
            }
            ++n5;
            n2 = object2;
        }
        return n2;
    }

    static int applyMaskPenaltyRule2(ByteMatrix byteMatrix) {
        int n2 = 0;
        byte[][] byArray = byteMatrix.getArray();
        int n3 = byteMatrix.getWidth();
        int n4 = byteMatrix.getHeight();
        for (int i2 = 0; i2 < n4 - 1; ++i2) {
            for (int i3 = 0; i3 < n3 - 1; ++i3) {
                byte by2 = byArray[i2][i3];
                int n5 = n2;
                if (by2 == byArray[i2][i3 + 1]) {
                    n5 = n2;
                    if (by2 == byArray[i2 + 1][i3]) {
                        n5 = n2;
                        if (by2 == byArray[i2 + 1][i3 + 1]) {
                            n5 = n2 + 1;
                        }
                    }
                }
                n2 = n5;
            }
        }
        return n2 * 3;
    }

    static int applyMaskPenaltyRule3(ByteMatrix object) {
        int n2 = 0;
        byte[][] byArray = ((ByteMatrix)object).getArray();
        int n3 = ((ByteMatrix)object).getWidth();
        int n4 = ((ByteMatrix)object).getHeight();
        for (int i2 = 0; i2 < n4; ++i2) {
            for (int i3 = 0; i3 < n3; ++i3) {
                int n5;
                block6: {
                    block7: {
                        object = byArray[i2];
                        n5 = n2;
                        if (i3 + 6 >= n3) break block6;
                        n5 = n2;
                        if (object[i3] != true) break block6;
                        n5 = n2;
                        if (object[i3 + 1] != false) break block6;
                        n5 = n2;
                        if (object[i3 + 2] != true) break block6;
                        n5 = n2;
                        if (object[i3 + 3] != true) break block6;
                        n5 = n2;
                        if (object[i3 + 4] != true) break block6;
                        n5 = n2;
                        if (object[i3 + 5] != false) break block6;
                        n5 = n2;
                        if (object[i3 + 6] != true) break block6;
                        if (MaskUtil.isWhiteHorizontal((byte[])object, i3 - 4, i3)) break block7;
                        n5 = n2;
                        if (!MaskUtil.isWhiteHorizontal((byte[])object, i3 + 7, i3 + 11)) break block6;
                    }
                    n5 = n2 + 1;
                }
                n2 = n5;
                if (i2 + 6 >= n4) continue;
                n2 = n5;
                if (byArray[i2][i3] != 1) continue;
                n2 = n5;
                if (byArray[i2 + 1][i3] != 0) continue;
                n2 = n5;
                if (byArray[i2 + 2][i3] != 1) continue;
                n2 = n5;
                if (byArray[i2 + 3][i3] != 1) continue;
                n2 = n5;
                if (byArray[i2 + 4][i3] != 1) continue;
                n2 = n5;
                if (byArray[i2 + 5][i3] != 0) continue;
                n2 = n5;
                if (byArray[i2 + 6][i3] != 1) continue;
                if (!MaskUtil.isWhiteVertical(byArray, i3, i2 - 4, i2)) {
                    n2 = n5;
                    if (!MaskUtil.isWhiteVertical(byArray, i3, i2 + 7, i2 + 11)) continue;
                }
                n2 = n5 + 1;
            }
        }
        return n2 * 40;
    }

    static int applyMaskPenaltyRule4(ByteMatrix byteMatrix) {
        int n2;
        int n3 = 0;
        byte[][] byArray = byteMatrix.getArray();
        int n4 = byteMatrix.getWidth();
        int n5 = byteMatrix.getHeight();
        for (n2 = 0; n2 < n5; ++n2) {
            byte[] byArray2 = byArray[n2];
            for (int i2 = 0; i2 < n4; ++i2) {
                int n6 = n3;
                if (byArray2[i2] == 1) {
                    n6 = n3 + 1;
                }
                n3 = n6;
            }
        }
        n2 = byteMatrix.getHeight() * byteMatrix.getWidth();
        return Math.abs(n3 * 2 - n2) * 10 / n2 * 10;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean getDataMaskBit(int n2, int n3, int n4) {
        switch (n2) {
            default: {
                throw new IllegalArgumentException("Invalid mask pattern: " + n2);
            }
            case 0: {
                n2 = n4 + n3 & 1;
                return n2 == 0;
            }
            case 1: {
                n2 = n4 & 1;
                return n2 == 0;
            }
            case 2: {
                n2 = n3 % 3;
                return n2 == 0;
            }
            case 3: {
                n2 = (n4 + n3) % 3;
                return n2 == 0;
            }
            case 4: {
                n2 = n4 / 2 + n3 / 3 & 1;
                return n2 == 0;
            }
            case 5: {
                n2 = n4 * n3;
                n2 = (n2 & 1) + n2 % 3;
                return n2 == 0;
            }
            case 6: {
                n2 = n4 * n3;
                n2 = (n2 & 1) + n2 % 3 & 1;
                return n2 == 0;
            }
            case 7: {
                n2 = n4 * n3 % 3 + (n4 + n3 & 1) & 1;
                return n2 == 0;
            }
        }
    }

    private static boolean isWhiteHorizontal(byte[] byArray, int n2, int n3) {
        boolean bl2 = true;
        while (true) {
            block4: {
                boolean bl3;
                block3: {
                    bl3 = bl2;
                    if (n2 >= n3) break block3;
                    if (n2 < 0 || n2 >= byArray.length || byArray[n2] != 1) break block4;
                    bl3 = false;
                }
                return bl3;
            }
            ++n2;
        }
    }

    private static boolean isWhiteVertical(byte[][] byArray, int n2, int n3, int n4) {
        boolean bl2 = true;
        while (true) {
            block4: {
                boolean bl3;
                block3: {
                    bl3 = bl2;
                    if (n3 >= n4) break block3;
                    if (n3 < 0 || n3 >= byArray.length || byArray[n3][n2] != 1) break block4;
                    bl3 = false;
                }
                return bl3;
            }
            ++n3;
        }
    }
}

