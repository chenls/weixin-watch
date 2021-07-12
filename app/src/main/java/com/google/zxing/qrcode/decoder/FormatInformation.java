/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

final class FormatInformation {
    private static final int[] BITS_SET_IN_HALF_BYTE;
    private static final int[][] FORMAT_INFO_DECODE_LOOKUP;
    private static final int FORMAT_INFO_MASK_QR = 21522;
    private final byte dataMask;
    private final ErrorCorrectionLevel errorCorrectionLevel;

    static {
        int[] nArray = new int[]{20773, 1};
        int[] nArray2 = new int[]{24188, 2};
        int[] nArray3 = new int[]{17913, 4};
        int[] nArray4 = new int[]{20375, 6};
        int[] nArray5 = new int[]{25368, 13};
        int[] nArray6 = new int[]{5769, 16};
        int[] nArray7 = new int[]{6608, 19};
        int[] nArray8 = new int[]{1890, 20};
        int[] nArray9 = new int[]{2107, 23};
        int[] nArray10 = new int[]{12392, 25};
        int[] nArray11 = new int[]{8579, 29};
        FORMAT_INFO_DECODE_LOOKUP = new int[][]{{21522, 0}, nArray, nArray2, {23371, 3}, nArray3, {16590, 5}, nArray4, {19104, 7}, {30660, 8}, {29427, 9}, {32170, 10}, {30877, 11}, {26159, 12}, nArray5, {27713, 14}, {26998, 15}, nArray6, {5054, 17}, {7399, 18}, nArray7, nArray8, {597, 21}, {3340, 22}, nArray9, {13663, 24}, nArray10, {16177, 26}, {14854, 27}, {9396, 28}, nArray11, {11994, 30}, {11245, 31}};
        BITS_SET_IN_HALF_BYTE = new int[]{0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4};
    }

    private FormatInformation(int n2) {
        this.errorCorrectionLevel = ErrorCorrectionLevel.forBits(n2 >> 3 & 3);
        this.dataMask = (byte)(n2 & 7);
    }

    static FormatInformation decodeFormatInformation(int n2, int n3) {
        FormatInformation formatInformation = FormatInformation.doDecodeFormatInformation(n2, n3);
        if (formatInformation != null) {
            return formatInformation;
        }
        return FormatInformation.doDecodeFormatInformation(n2 ^ 0x5412, n3 ^ 0x5412);
    }

    private static FormatInformation doDecodeFormatInformation(int n2, int n3) {
        int n4 = Integer.MAX_VALUE;
        int n5 = 0;
        for (int[] nArray : FORMAT_INFO_DECODE_LOOKUP) {
            int n6 = nArray[0];
            if (n6 == n2 || n6 == n3) {
                return new FormatInformation(nArray[1]);
            }
            int n7 = FormatInformation.numBitsDiffering(n2, n6);
            int n8 = n4;
            if (n7 < n4) {
                n5 = nArray[1];
                n8 = n7;
            }
            n4 = n8;
            n7 = n5;
            if (n2 != n3) {
                n6 = FormatInformation.numBitsDiffering(n3, n6);
                n4 = n8;
                n7 = n5;
                if (n6 < n8) {
                    n7 = nArray[1];
                    n4 = n6;
                }
            }
            n5 = n7;
        }
        if (n4 <= 3) {
            return new FormatInformation(n5);
        }
        return null;
    }

    static int numBitsDiffering(int n2, int n3) {
        return BITS_SET_IN_HALF_BYTE[(n2 ^= n3) & 0xF] + BITS_SET_IN_HALF_BYTE[n2 >>> 4 & 0xF] + BITS_SET_IN_HALF_BYTE[n2 >>> 8 & 0xF] + BITS_SET_IN_HALF_BYTE[n2 >>> 12 & 0xF] + BITS_SET_IN_HALF_BYTE[n2 >>> 16 & 0xF] + BITS_SET_IN_HALF_BYTE[n2 >>> 20 & 0xF] + BITS_SET_IN_HALF_BYTE[n2 >>> 24 & 0xF] + BITS_SET_IN_HALF_BYTE[n2 >>> 28 & 0xF];
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block3: {
            block2: {
                if (!(object instanceof FormatInformation)) break block2;
                object = (FormatInformation)object;
                if (this.errorCorrectionLevel == ((FormatInformation)object).errorCorrectionLevel && this.dataMask == ((FormatInformation)object).dataMask) break block3;
            }
            return false;
        }
        return true;
    }

    byte getDataMask() {
        return this.dataMask;
    }

    ErrorCorrectionLevel getErrorCorrectionLevel() {
        return this.errorCorrectionLevel;
    }

    public int hashCode() {
        return this.errorCorrectionLevel.ordinal() << 3 | this.dataMask;
    }
}

