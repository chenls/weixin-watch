/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.qrcode.encoder;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.MaskUtil;
import com.google.zxing.qrcode.encoder.QRCode;

final class MatrixUtil {
    private static final int[][] POSITION_ADJUSTMENT_PATTERN;
    private static final int[][] POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE;
    private static final int[][] POSITION_DETECTION_PATTERN;
    private static final int[][] TYPE_INFO_COORDINATES;
    private static final int TYPE_INFO_MASK_PATTERN = 21522;
    private static final int TYPE_INFO_POLY = 1335;
    private static final int VERSION_INFO_POLY = 7973;

    static {
        POSITION_DETECTION_PATTERN = new int[][]{{1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 0, 0, 1}, {1, 0, 1, 1, 1, 0, 1}, {1, 0, 1, 1, 1, 0, 1}, {1, 0, 1, 1, 1, 0, 1}, {1, 0, 0, 0, 0, 0, 1}, {1, 1, 1, 1, 1, 1, 1}};
        POSITION_ADJUSTMENT_PATTERN = new int[][]{{1, 1, 1, 1, 1}, {1, 0, 0, 0, 1}, {1, 0, 1, 0, 1}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 1}};
        int[] nArray = new int[]{6, 18, -1, -1, -1, -1, -1};
        int[] nArray2 = new int[]{6, 22, -1, -1, -1, -1, -1};
        int[] nArray3 = new int[]{6, 26, -1, -1, -1, -1, -1};
        int[] nArray4 = new int[]{6, 30, -1, -1, -1, -1, -1};
        int[] nArray5 = new int[]{6, 34, -1, -1, -1, -1, -1};
        int[] nArray6 = new int[]{6, 22, 38, -1, -1, -1, -1};
        int[] nArray7 = new int[]{6, 24, 42, -1, -1, -1, -1};
        int[] nArray8 = new int[]{6, 28, 50, -1, -1, -1, -1};
        int[] nArray9 = new int[]{6, 34, 62, -1, -1, -1, -1};
        int[] nArray10 = new int[]{6, 30, 54, 78, -1, -1, -1};
        int[] nArray11 = new int[]{6, 30, 56, 82, -1, -1, -1};
        int[] nArray12 = new int[]{6, 30, 58, 86, -1, -1, -1};
        int[] nArray13 = new int[]{6, 28, 50, 72, 94, -1, -1};
        int[] nArray14 = new int[]{6, 26, 50, 74, 98, -1, -1};
        int[] nArray15 = new int[]{6, 28, 54, 80, 106, -1, -1};
        int[] nArray16 = new int[]{6, 30, 58, 86, 114, -1, -1};
        int[] nArray17 = new int[]{6, 34, 62, 90, 118, -1, -1};
        int[] nArray18 = new int[]{6, 26, 50, 74, 98, 122, -1};
        int[] nArray19 = new int[]{6, 26, 52, 78, 104, 130, -1};
        int[] nArray20 = new int[]{6, 30, 58, 86, 114, 142, -1};
        int[] nArray21 = new int[]{6, 34, 62, 90, 118, 146, -1};
        int[] nArray22 = new int[]{6, 30, 54, 78, 102, 126, 150};
        int[] nArray23 = new int[]{6, 24, 50, 76, 102, 128, 154};
        int[] nArray24 = new int[]{6, 28, 54, 80, 106, 132, 158};
        int[] nArray25 = new int[]{6, 30, 58, 86, 114, 142, 170};
        POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE = new int[][]{{-1, -1, -1, -1, -1, -1, -1}, nArray, nArray2, nArray3, nArray4, nArray5, nArray6, nArray7, {6, 26, 46, -1, -1, -1, -1}, nArray8, {6, 30, 54, -1, -1, -1, -1}, {6, 32, 58, -1, -1, -1, -1}, nArray9, {6, 26, 46, 66, -1, -1, -1}, {6, 26, 48, 70, -1, -1, -1}, {6, 26, 50, 74, -1, -1, -1}, nArray10, nArray11, nArray12, {6, 34, 62, 90, -1, -1, -1}, nArray13, nArray14, {6, 30, 54, 78, 102, -1, -1}, nArray15, {6, 32, 58, 84, 110, -1, -1}, nArray16, nArray17, nArray18, {6, 30, 54, 78, 102, 126, -1}, nArray19, {6, 30, 56, 82, 108, 134, -1}, {6, 34, 60, 86, 112, 138, -1}, nArray20, nArray21, nArray22, nArray23, nArray24, {6, 32, 58, 84, 110, 136, 162}, {6, 26, 54, 82, 110, 138, 166}, nArray25};
        nArray = new int[]{8, 1};
        nArray2 = new int[]{8, 4};
        nArray3 = new int[]{8, 5};
        nArray4 = new int[]{4, 8};
        TYPE_INFO_COORDINATES = new int[][]{{8, 0}, nArray, {8, 2}, {8, 3}, nArray2, nArray3, {8, 7}, {8, 8}, {7, 8}, {5, 8}, nArray4, {3, 8}, {2, 8}, {1, 8}, {0, 8}};
    }

    private MatrixUtil() {
    }

    static void buildMatrix(BitArray bitArray, ErrorCorrectionLevel errorCorrectionLevel, Version version, int n2, ByteMatrix byteMatrix) throws WriterException {
        MatrixUtil.clearMatrix(byteMatrix);
        MatrixUtil.embedBasicPatterns(version, byteMatrix);
        MatrixUtil.embedTypeInfo(errorCorrectionLevel, n2, byteMatrix);
        MatrixUtil.maybeEmbedVersionInfo(version, byteMatrix);
        MatrixUtil.embedDataBits(bitArray, n2, byteMatrix);
    }

    static int calculateBCHCode(int n2, int n3) {
        if (n3 == 0) {
            throw new IllegalArgumentException("0 polynomial");
        }
        int n4 = MatrixUtil.findMSBSet(n3);
        n2 <<= n4 - 1;
        while (MatrixUtil.findMSBSet(n2) >= n4) {
            n2 ^= n3 << MatrixUtil.findMSBSet(n2) - n4;
        }
        return n2;
    }

    static void clearMatrix(ByteMatrix byteMatrix) {
        byteMatrix.clear((byte)-1);
    }

    static void embedBasicPatterns(Version version, ByteMatrix byteMatrix) throws WriterException {
        MatrixUtil.embedPositionDetectionPatternsAndSeparators(byteMatrix);
        MatrixUtil.embedDarkDotAtLeftBottomCorner(byteMatrix);
        MatrixUtil.maybeEmbedPositionAdjustmentPatterns(version, byteMatrix);
        MatrixUtil.embedTimingPatterns(byteMatrix);
    }

    private static void embedDarkDotAtLeftBottomCorner(ByteMatrix byteMatrix) throws WriterException {
        if (byteMatrix.get(8, byteMatrix.getHeight() - 8) == 0) {
            throw new WriterException();
        }
        byteMatrix.set(8, byteMatrix.getHeight() - 8, 1);
    }

    /*
     * Enabled aggressive block sorting
     */
    static void embedDataBits(BitArray bitArray, int n2, ByteMatrix byteMatrix) throws WriterException {
        int n3 = 0;
        int n4 = -1;
        int n5 = byteMatrix.getWidth() - 1;
        int n6 = byteMatrix.getHeight() - 1;
        block0: while (true) {
            int n7;
            int n8;
            int n9;
            if (n5 > 0) {
                n9 = n3;
                n8 = n5;
                n7 = n6;
                if (n5 == 6) {
                    n8 = n5 - 1;
                    n7 = n6;
                    n9 = n3;
                }
            } else {
                if (n3 != bitArray.getSize()) {
                    throw new WriterException("Not all bits consumed: " + n3 + '/' + bitArray.getSize());
                }
                return;
            }
            while (true) {
                if (n7 >= 0 && n7 < byteMatrix.getHeight()) {
                    n6 = n9;
                } else {
                    n4 = -n4;
                    n6 = n7 + n4;
                    n5 = n8 - 2;
                    n3 = n9;
                    continue block0;
                }
                for (n5 = 0; n5 < 2; ++n5) {
                    boolean bl2;
                    n9 = n8 - n5;
                    if (!MatrixUtil.isEmpty(byteMatrix.get(n9, n7))) continue;
                    if (n6 < bitArray.getSize()) {
                        bl2 = bitArray.get(n6);
                        ++n6;
                    } else {
                        bl2 = false;
                    }
                    boolean bl3 = bl2;
                    if (n2 != -1) {
                        bl3 = bl2;
                        if (MaskUtil.getDataMaskBit(n2, n9, n7)) {
                            bl3 = !bl2;
                        }
                    }
                    byteMatrix.set(n9, n7, bl3);
                }
                n7 += n4;
                n9 = n6;
            }
            break;
        }
    }

    private static void embedHorizontalSeparationPattern(int n2, int n3, ByteMatrix byteMatrix) throws WriterException {
        for (int i2 = 0; i2 < 8; ++i2) {
            if (!MatrixUtil.isEmpty(byteMatrix.get(n2 + i2, n3))) {
                throw new WriterException();
            }
            byteMatrix.set(n2 + i2, n3, 0);
        }
    }

    private static void embedPositionAdjustmentPattern(int n2, int n3, ByteMatrix byteMatrix) {
        for (int i2 = 0; i2 < 5; ++i2) {
            for (int i3 = 0; i3 < 5; ++i3) {
                byteMatrix.set(n2 + i3, n3 + i2, POSITION_ADJUSTMENT_PATTERN[i2][i3]);
            }
        }
    }

    private static void embedPositionDetectionPattern(int n2, int n3, ByteMatrix byteMatrix) {
        for (int i2 = 0; i2 < 7; ++i2) {
            for (int i3 = 0; i3 < 7; ++i3) {
                byteMatrix.set(n2 + i3, n3 + i2, POSITION_DETECTION_PATTERN[i2][i3]);
            }
        }
    }

    private static void embedPositionDetectionPatternsAndSeparators(ByteMatrix byteMatrix) throws WriterException {
        int n2 = POSITION_DETECTION_PATTERN[0].length;
        MatrixUtil.embedPositionDetectionPattern(0, 0, byteMatrix);
        MatrixUtil.embedPositionDetectionPattern(byteMatrix.getWidth() - n2, 0, byteMatrix);
        MatrixUtil.embedPositionDetectionPattern(0, byteMatrix.getWidth() - n2, byteMatrix);
        MatrixUtil.embedHorizontalSeparationPattern(0, 7, byteMatrix);
        MatrixUtil.embedHorizontalSeparationPattern(byteMatrix.getWidth() - 8, 7, byteMatrix);
        MatrixUtil.embedHorizontalSeparationPattern(0, byteMatrix.getWidth() - 8, byteMatrix);
        MatrixUtil.embedVerticalSeparationPattern(7, 0, byteMatrix);
        MatrixUtil.embedVerticalSeparationPattern(byteMatrix.getHeight() - 7 - 1, 0, byteMatrix);
        MatrixUtil.embedVerticalSeparationPattern(7, byteMatrix.getHeight() - 7, byteMatrix);
    }

    private static void embedTimingPatterns(ByteMatrix byteMatrix) {
        for (int i2 = 8; i2 < byteMatrix.getWidth() - 8; ++i2) {
            int n2 = (i2 + 1) % 2;
            if (MatrixUtil.isEmpty(byteMatrix.get(i2, 6))) {
                byteMatrix.set(i2, 6, n2);
            }
            if (!MatrixUtil.isEmpty(byteMatrix.get(6, i2))) continue;
            byteMatrix.set(6, i2, n2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    static void embedTypeInfo(ErrorCorrectionLevel errorCorrectionLevel, int n2, ByteMatrix byteMatrix) throws WriterException {
        BitArray bitArray = new BitArray();
        MatrixUtil.makeTypeInfoBits(errorCorrectionLevel, n2, bitArray);
        n2 = 0;
        while (n2 < bitArray.getSize()) {
            boolean bl2 = bitArray.get(bitArray.getSize() - 1 - n2);
            byteMatrix.set(TYPE_INFO_COORDINATES[n2][0], TYPE_INFO_COORDINATES[n2][1], bl2);
            if (n2 < 8) {
                byteMatrix.set(byteMatrix.getWidth() - n2 - 1, 8, bl2);
            } else {
                byteMatrix.set(8, byteMatrix.getHeight() - 7 + (n2 - 8), bl2);
            }
            ++n2;
        }
        return;
    }

    private static void embedVerticalSeparationPattern(int n2, int n3, ByteMatrix byteMatrix) throws WriterException {
        for (int i2 = 0; i2 < 7; ++i2) {
            if (!MatrixUtil.isEmpty(byteMatrix.get(n2, n3 + i2))) {
                throw new WriterException();
            }
            byteMatrix.set(n2, n3 + i2, 0);
        }
    }

    static int findMSBSet(int n2) {
        int n3 = 0;
        int n4 = n2;
        n2 = n3;
        while (n4 != 0) {
            n4 >>>= 1;
            ++n2;
        }
        return n2;
    }

    private static boolean isEmpty(int n2) {
        return n2 == -1;
    }

    static void makeTypeInfoBits(ErrorCorrectionLevel object, int n2, BitArray bitArray) throws WriterException {
        if (!QRCode.isValidMaskPattern(n2)) {
            throw new WriterException("Invalid mask pattern");
        }
        n2 = ((ErrorCorrectionLevel)((Object)object)).getBits() << 3 | n2;
        bitArray.appendBits(n2, 5);
        bitArray.appendBits(MatrixUtil.calculateBCHCode(n2, 1335), 10);
        object = new BitArray();
        ((BitArray)object).appendBits(21522, 15);
        bitArray.xor((BitArray)object);
        if (bitArray.getSize() != 15) {
            throw new WriterException("should not happen but we got: " + bitArray.getSize());
        }
    }

    static void makeVersionInfoBits(Version version, BitArray bitArray) throws WriterException {
        bitArray.appendBits(version.getVersionNumber(), 6);
        bitArray.appendBits(MatrixUtil.calculateBCHCode(version.getVersionNumber(), 7973), 12);
        if (bitArray.getSize() != 18) {
            throw new WriterException("should not happen but we got: " + bitArray.getSize());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void maybeEmbedPositionAdjustmentPatterns(Version object, ByteMatrix byteMatrix) {
        if (((Version)object).getVersionNumber() < 2) {
            return;
        }
        int n2 = ((Version)object).getVersionNumber() - 1;
        object = POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE[n2];
        int n3 = POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE[n2].length;
        n2 = 0;
        while (n2 < n3) {
            for (int i2 = 0; i2 < n3; ++i2) {
                Object object2 = object[n2];
                Object object3 = object[i2];
                if (object3 == -1 || object2 == -1 || !MatrixUtil.isEmpty(byteMatrix.get((int)object3, (int)object2))) continue;
                MatrixUtil.embedPositionAdjustmentPattern((int)(object3 - 2), (int)(object2 - 2), byteMatrix);
            }
            ++n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    static void maybeEmbedVersionInfo(Version version, ByteMatrix byteMatrix) throws WriterException {
        if (version.getVersionNumber() >= 7) {
            BitArray bitArray = new BitArray();
            MatrixUtil.makeVersionInfoBits(version, bitArray);
            int n2 = 17;
            for (int i2 = 0; i2 < 6; ++i2) {
                for (int i3 = 0; i3 < 3; --n2, ++i3) {
                    boolean bl2 = bitArray.get(n2);
                    byteMatrix.set(i2, byteMatrix.getHeight() - 11 + i3, bl2);
                    byteMatrix.set(byteMatrix.getHeight() - 11 + i3, i2, bl2);
                }
            }
        }
    }
}

