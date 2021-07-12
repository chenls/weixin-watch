/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.pdf417.PDF417Common;
import com.google.zxing.pdf417.decoder.BarcodeMetadata;
import com.google.zxing.pdf417.decoder.BarcodeValue;
import com.google.zxing.pdf417.decoder.BoundingBox;
import com.google.zxing.pdf417.decoder.Codeword;
import com.google.zxing.pdf417.decoder.DecodedBitStreamParser;
import com.google.zxing.pdf417.decoder.DetectionResult;
import com.google.zxing.pdf417.decoder.DetectionResultColumn;
import com.google.zxing.pdf417.decoder.DetectionResultRowIndicatorColumn;
import com.google.zxing.pdf417.decoder.PDF417CodewordDecoder;
import com.google.zxing.pdf417.decoder.ec.ErrorCorrection;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Formatter;

public final class PDF417ScanningDecoder {
    private static final int CODEWORD_SKEW_SIZE = 2;
    private static final int MAX_EC_CODEWORDS = 512;
    private static final int MAX_ERRORS = 3;
    private static final ErrorCorrection errorCorrection = new ErrorCorrection();

    private PDF417ScanningDecoder() {
    }

    /*
     * Enabled aggressive block sorting
     */
    private static BoundingBox adjustBoundingBox(DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn) throws NotFoundException, FormatException {
        Codeword[] codewordArray;
        int n2;
        int[] nArray;
        if (detectionResultRowIndicatorColumn == null || (nArray = detectionResultRowIndicatorColumn.getRowHeights()) == null) {
            return null;
        }
        int n3 = PDF417ScanningDecoder.getMax(nArray);
        int n4 = 0;
        int n5 = nArray.length;
        int n6 = 0;
        while (true) {
            block8: {
                block7: {
                    n2 = n4;
                    if (n6 >= n5) break block7;
                    n2 = nArray[n6];
                    n4 += n3 - n2;
                    if (n2 <= 0) break block8;
                    n2 = n4;
                }
                codewordArray = detectionResultRowIndicatorColumn.getCodewords();
                n4 = 0;
                for (n6 = n2; n6 > 0 && codewordArray[n4] == null; --n6, ++n4) {
                }
                break;
            }
            ++n6;
        }
        n2 = 0;
        n5 = nArray.length - 1;
        while (true) {
            block10: {
                block9: {
                    n4 = n2;
                    if (n5 < 0) break block9;
                    n4 = n2 + (n3 - nArray[n5]);
                    if (nArray[n5] <= 0) break block10;
                }
                n2 = codewordArray.length - 1;
                while (n4 > 0 && codewordArray[n2] == null) {
                    --n4;
                    --n2;
                }
                return detectionResultRowIndicatorColumn.getBoundingBox().addMissingRows(n6, n4, detectionResultRowIndicatorColumn.isLeft());
            }
            --n5;
            n2 = n4;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void adjustCodewordCount(DetectionResult detectionResult, BarcodeValue[][] barcodeValueArray) throws NotFoundException {
        int[] nArray = barcodeValueArray[0][1].getValue();
        int n2 = detectionResult.getBarcodeColumnCount() * detectionResult.getBarcodeRowCount() - PDF417ScanningDecoder.getNumberOfECCodeWords(detectionResult.getBarcodeECLevel());
        if (nArray.length == 0) {
            if (n2 < 1 || n2 > 928) {
                throw NotFoundException.getNotFoundInstance();
            }
            barcodeValueArray[0][1].setValue(n2);
            return;
        } else {
            if (nArray[0] == n2) return;
            barcodeValueArray[0][1].setValue(n2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int adjustCodewordStartColumn(BitMatrix bitMatrix, int n2, int n3, boolean bl2, int n4, int n5) {
        int n6 = n4;
        int n7 = bl2 ? -1 : 1;
        int n8 = 0;
        int n9 = n7;
        n7 = n8;
        while (n7 < 2) {
            while ((bl2 && n6 >= n2 || !bl2 && n6 < n3) && bl2 == bitMatrix.get(n6, n5)) {
                if (Math.abs(n4 - n6) > 2) {
                    return n4;
                }
                n6 += n9;
            }
            n9 = -n9;
            bl2 = !bl2;
            ++n7;
        }
        return n6;
    }

    private static boolean checkCodewordSkew(int n2, int n3, int n4) {
        return n3 - 2 <= n2 && n2 <= n4 + 2;
    }

    private static int correctErrors(int[] nArray, int[] nArray2, int n2) throws ChecksumException {
        if (nArray2 != null && nArray2.length > n2 / 2 + 3 || n2 < 0 || n2 > 512) {
            throw ChecksumException.getChecksumInstance();
        }
        return errorCorrection.decode(nArray, n2, nArray2);
    }

    private static BarcodeValue[][] createBarcodeMatrix(DetectionResult detectionResultColumnArray) throws FormatException {
        int n2;
        BarcodeValue[][] barcodeValueArray = (BarcodeValue[][])Array.newInstance(BarcodeValue.class, detectionResultColumnArray.getBarcodeRowCount(), detectionResultColumnArray.getBarcodeColumnCount() + 2);
        for (int i2 = 0; i2 < barcodeValueArray.length; ++i2) {
            for (n2 = 0; n2 < barcodeValueArray[i2].length; ++n2) {
                barcodeValueArray[i2][n2] = new BarcodeValue();
            }
        }
        n2 = 0;
        for (DetectionResultColumn detectionResultColumn : detectionResultColumnArray.getDetectionResultColumns()) {
            if (detectionResultColumn != null) {
                for (Codeword codeword : detectionResultColumn.getCodewords()) {
                    int n4;
                    if (codeword == null || (n4 = codeword.getRowNumber()) < 0) continue;
                    if (n4 >= barcodeValueArray.length) {
                        throw FormatException.getFormatInstance();
                    }
                    barcodeValueArray[n4][n2].setValue(codeword.getValue());
                }
            }
            ++n2;
        }
        return barcodeValueArray;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static DecoderResult createDecoderResult(DetectionResult detectionResult) throws FormatException, ChecksumException, NotFoundException {
        BarcodeValue[][] barcodeValueArray = PDF417ScanningDecoder.createBarcodeMatrix(detectionResult);
        PDF417ScanningDecoder.adjustCodewordCount(detectionResult, barcodeValueArray);
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        int[] nArray = new int[detectionResult.getBarcodeRowCount() * detectionResult.getBarcodeColumnCount()];
        ArrayList<int[]> arrayList2 = new ArrayList<int[]>();
        ArrayList<Integer> arrayList3 = new ArrayList<Integer>();
        int n2 = 0;
        while (true) {
            if (n2 < detectionResult.getBarcodeRowCount()) {
            } else {
                int[][] nArrayArray = new int[arrayList2.size()][];
                n2 = 0;
                while (true) {
                    if (n2 >= nArrayArray.length) {
                        return PDF417ScanningDecoder.createDecoderResultFromAmbiguousValues(detectionResult.getBarcodeECLevel(), nArray, PDF417Common.toIntArray(arrayList), PDF417Common.toIntArray(arrayList3), nArrayArray);
                    }
                    nArrayArray[n2] = (int[])arrayList2.get(n2);
                    ++n2;
                }
            }
            for (int i2 = 0; i2 < detectionResult.getBarcodeColumnCount(); ++i2) {
                int[] nArray2 = barcodeValueArray[n2][i2 + 1].getValue();
                int n3 = detectionResult.getBarcodeColumnCount() * n2 + i2;
                if (nArray2.length == 0) {
                    arrayList.add(n3);
                    continue;
                }
                if (nArray2.length == 1) {
                    nArray[n3] = nArray2[0];
                    continue;
                }
                arrayList3.add(n3);
                arrayList2.add(nArray2);
            }
            ++n2;
        }
    }

    private static DecoderResult createDecoderResultFromAmbiguousValues(int n2, int[] nArray, int[] nArray2, int[] nArray3, int[][] nArray4) throws FormatException, ChecksumException {
        int[] nArray5 = new int[nArray3.length];
        block2: for (int i2 = 100; i2 > 0; --i2) {
            int n3;
            for (n3 = 0; n3 < nArray5.length; ++n3) {
                nArray[nArray3[n3]] = nArray4[n3][nArray5[n3]];
            }
            try {
                DecoderResult decoderResult = PDF417ScanningDecoder.decodeCodewords(nArray, n2, nArray2);
                return decoderResult;
            }
            catch (ChecksumException checksumException) {
                if (nArray5.length == 0) {
                    throw ChecksumException.getChecksumInstance();
                }
                n3 = 0;
                while (true) {
                    if (n3 >= nArray5.length) continue block2;
                    if (nArray5[n3] < nArray4[n3].length - 1) {
                        nArray5[n3] = nArray5[n3] + 1;
                        continue block2;
                    }
                    nArray5[n3] = 0;
                    if (n3 == nArray5.length - 1) {
                        throw ChecksumException.getChecksumInstance();
                    }
                    ++n3;
                }
            }
        }
        throw ChecksumException.getChecksumInstance();
    }

    /*
     * Unable to fully structure code
     */
    public static DecoderResult decode(BitMatrix var0, ResultPoint var1_1, ResultPoint var2_2, ResultPoint var3_3, ResultPoint var4_4, int var5_5, int var6_6) throws NotFoundException, FormatException, ChecksumException {
        block20: {
            block21: {
                block22: {
                    block19: {
                        block18: {
                            block17: {
                                var18_7 = new BoundingBox(var0, (ResultPoint)var1_1, (ResultPoint)var2_2, var3_3, (ResultPoint)var4_4);
                                var4_4 = null;
                                var2_2 = null;
                                var17_8 = null;
                                var7_9 = 0;
                                while (true) {
                                    var20_11 = var4_4;
                                    var19_10 = var2_2;
                                    if (var7_9 >= 2) break block17;
                                    if (var1_1 != null) {
                                        var4_4 = PDF417ScanningDecoder.getRowIndicatorColumn(var0, var18_7, (ResultPoint)var1_1, true, var5_5, var6_6);
                                    }
                                    if (var3_3 != null) {
                                        var2_2 = PDF417ScanningDecoder.getRowIndicatorColumn(var0, var18_7, var3_3, false, var5_5, var6_6);
                                    }
                                    if ((var17_8 = PDF417ScanningDecoder.merge((DetectionResultRowIndicatorColumn)var4_4, (DetectionResultRowIndicatorColumn)var2_2)) == null) {
                                        throw NotFoundException.getNotFoundInstance();
                                    }
                                    if (var7_9 != 0 || var17_8.getBoundingBox() == null || var17_8.getBoundingBox().getMinY() >= var18_7.getMinY() && var17_8.getBoundingBox().getMaxY() <= var18_7.getMaxY()) break;
                                    var18_7 = var17_8.getBoundingBox();
                                    ++var7_9;
                                }
                                var17_8.setBoundingBox(var18_7);
                                var19_10 = var2_2;
                                var20_11 = var4_4;
                            }
                            var14_12 = var17_8.getBarcodeColumnCount() + 1;
                            var17_8.setDetectionResultColumn(0, (DetectionResultColumn)var20_11);
                            var17_8.setDetectionResultColumn(var14_12, (DetectionResultColumn)var19_10);
                            if (var20_11 != null) {
                                var15_13 = true;
lbl29:
                                // 2 sources

                                while (true) {
                                    var8_14 = 1;
                                    var7_9 = var5_5;
                                    block2: for (var5_5 = var8_14; var5_5 <= var14_12; ++var5_5) {
                                        if (!var15_13) break block18;
                                        var10_16 = var5_5;
lbl35:
                                        // 2 sources

                                        while (var17_8.getDetectionResultColumn(var10_16) != null) {
                                            var9_15 = var6_6;
                                            var12_18 = var7_9;
                                            while (true) {
                                                var7_9 = var12_18;
                                                var6_6 = var9_15;
                                                continue block2;
                                                break;
                                            }
                                        }
                                        break block19;
                                    }
                                    break block20;
                                    break;
                                }
                            }
                            var15_13 = false;
                            ** while (true)
                        }
                        var10_16 = var14_12 - var5_5;
                        ** GOTO lbl35
                    }
                    if (var10_16 == 0 || var10_16 == var14_12) {
                        if (var10_16 == 0) {
                            var16_20 = true;
lbl53:
                            // 2 sources

                            while (true) {
                                var1_1 = new DetectionResultRowIndicatorColumn(var18_7, var16_20);
lbl55:
                                // 2 sources

                                while (true) {
                                    var17_8.setDetectionResultColumn(var10_16, (DetectionResultColumn)var1_1);
                                    var8_14 = -1;
                                    var11_17 = var18_7.getMinY();
                                    block7: while (true) {
                                        var12_18 = var7_9;
                                        var9_15 = var6_6;
                                        if (var11_17 > var18_7.getMaxY()) ** continue;
                                        var12_18 = PDF417ScanningDecoder.getStartColumn(var17_8, var10_16, var11_17, var15_13);
                                        if (var12_18 >= 0) {
                                            var9_15 = var12_18;
                                            if (var12_18 <= var18_7.getMaxX()) break block21;
                                        }
                                        if (var8_14 == -1) {
                                            var13_19 = var6_6;
                                            var12_18 = var7_9;
lbl70:
                                            // 3 sources

                                            while (true) {
                                                ++var11_17;
                                                var7_9 = var12_18;
                                                var6_6 = var13_19;
                                                continue block7;
                                                break;
                                            }
                                        }
                                        break block22;
                                        break;
                                    }
                                    break;
                                }
                                break;
                            }
                        }
                        var16_20 = false;
                        ** continue;
                    }
                    var1_1 = new DetectionResultColumn(var18_7);
                    ** while (true)
                }
                var9_15 = var8_14;
            }
            var2_2 = PDF417ScanningDecoder.detectCodeword(var0, var18_7.getMinX(), var18_7.getMaxX(), var15_13, var9_15, var11_17, var7_9, var6_6);
            var12_18 = var7_9;
            var13_19 = var6_6;
            if (var2_2 == null) ** GOTO lbl70
            var1_1.setCodeword(var11_17, (Codeword)var2_2);
            var8_14 = var9_15;
            var12_18 = Math.min(var7_9, var2_2.getWidth());
            var13_19 = Math.max(var6_6, var2_2.getWidth());
            ** while (true)
        }
        return PDF417ScanningDecoder.createDecoderResult(var17_8);
    }

    private static DecoderResult decodeCodewords(int[] object, int n2, int[] nArray) throws FormatException, ChecksumException {
        if (((int[])object).length == 0) {
            throw FormatException.getFormatInstance();
        }
        int n3 = 1 << n2 + 1;
        int n4 = PDF417ScanningDecoder.correctErrors((int[])object, nArray, n3);
        PDF417ScanningDecoder.verifyCodewordCount((int[])object, n3);
        object = DecodedBitStreamParser.decode((int[])object, String.valueOf(n2));
        ((DecoderResult)object).setErrorsCorrected(n4);
        ((DecoderResult)object).setErasures(nArray.length);
        return object;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private static Codeword detectCodeword(BitMatrix object, int n2, int n3, boolean bl2, int n4, int object2, int n5, int n6) {
        void var7_9;
        void var6_8;
        if ((object = (Object)PDF417ScanningDecoder.getModuleBitCount((BitMatrix)object, n2, n3, bl2, n4 = PDF417ScanningDecoder.adjustCodewordStartColumn((BitMatrix)object, n2, n3, bl2, n4, object2), object2)) == null) {
            return null;
        }
        n3 = PDF417Common.getBitCountSum((int[])object);
        if (bl2) {
            n2 = n4 + n3;
        } else {
            for (n2 = 0; n2 < ((Object)object).length / 2; ++n2) {
                Object object3 = object[n2];
                object[n2] = object[((Object)object).length - 1 - n2];
                object[((Object)object).length - 1 - n2] = object3;
            }
            n2 = n4;
            n4 = n2 - n3;
        }
        if (!PDF417ScanningDecoder.checkCodewordSkew(n3, (int)var6_8, (int)var7_9)) {
            return null;
        }
        n3 = PDF417CodewordDecoder.getDecodedValue((int[])object);
        int n7 = PDF417Common.getCodeword(n3);
        if (n7 == -1) {
            return null;
        }
        return new Codeword(n4, n2, PDF417ScanningDecoder.getCodewordBucketNumber(n3), n7);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static BarcodeMetadata getBarcodeMetadata(DetectionResultRowIndicatorColumn object, DetectionResultRowIndicatorColumn object2) {
        if (object == null || (object = ((DetectionResultRowIndicatorColumn)object).getBarcodeMetadata()) == null) {
            if (object2 == null) return null;
            return ((DetectionResultRowIndicatorColumn)object2).getBarcodeMetadata();
        }
        if (object2 == null || (object2 = ((DetectionResultRowIndicatorColumn)object2).getBarcodeMetadata()) == null) {
            return object;
        }
        if (((BarcodeMetadata)object).getColumnCount() == ((BarcodeMetadata)object2).getColumnCount() || ((BarcodeMetadata)object).getErrorCorrectionLevel() == ((BarcodeMetadata)object2).getErrorCorrectionLevel() || ((BarcodeMetadata)object).getRowCount() == ((BarcodeMetadata)object2).getRowCount()) return object;
        return null;
    }

    private static int[] getBitCountForCodeword(int n2) {
        int[] nArray = new int[8];
        int n3 = 0;
        int n4 = nArray.length - 1;
        while (true) {
            int n5 = n4--;
            int n6 = n3;
            if ((n2 & 1) != n3) {
                n6 = n2 & 1;
                n5 = n4;
                if (n4 < 0) {
                    return nArray;
                }
            }
            nArray[n5] = nArray[n5] + 1;
            n2 >>= 1;
            n4 = n5;
            n3 = n6;
        }
    }

    private static int getCodewordBucketNumber(int n2) {
        return PDF417ScanningDecoder.getCodewordBucketNumber(PDF417ScanningDecoder.getBitCountForCodeword(n2));
    }

    private static int getCodewordBucketNumber(int[] nArray) {
        return (nArray[0] - nArray[2] + nArray[4] - nArray[6] + 9) % 9;
    }

    private static int getMax(int[] nArray) {
        int n2 = -1;
        int n3 = nArray.length;
        for (int i2 = 0; i2 < n3; ++i2) {
            n2 = Math.max(n2, nArray[i2]);
        }
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int[] getModuleBitCount(BitMatrix bitMatrix, int n2, int n3, boolean bl2, int n4, int n5) {
        int n6 = n4;
        int[] nArray = new int[8];
        int n7 = 0;
        n4 = bl2 ? 1 : -1;
        boolean bl3 = bl2;
        while ((bl2 && n6 < n3 || !bl2 && n6 >= n2) && n7 < nArray.length) {
            if (bitMatrix.get(n6, n5) == bl3) {
                nArray[n7] = nArray[n7] + 1;
                n6 += n4;
                continue;
            }
            ++n7;
            if (!bl3) {
                bl3 = true;
                continue;
            }
            bl3 = false;
        }
        if (n7 == nArray.length || (bl2 && n6 == n3 || !bl2 && n6 == n2) && n7 == nArray.length - 1) {
            return nArray;
        }
        return null;
    }

    private static int getNumberOfECCodeWords(int n2) {
        return 2 << n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static DetectionResultRowIndicatorColumn getRowIndicatorColumn(BitMatrix bitMatrix, BoundingBox boundingBox, ResultPoint resultPoint, boolean bl2, int n2, int n3) {
        DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn = new DetectionResultRowIndicatorColumn(boundingBox, bl2);
        int n4 = 0;
        while (n4 < 2) {
            int n5 = n4 == 0 ? 1 : -1;
            int n6 = (int)resultPoint.getX();
            for (int i2 = (int)resultPoint.getY(); i2 <= boundingBox.getMaxY() && i2 >= boundingBox.getMinY(); i2 += n5) {
                Codeword codeword = PDF417ScanningDecoder.detectCodeword(bitMatrix, 0, bitMatrix.getWidth(), bl2, n6, i2, n2, n3);
                if (codeword == null) continue;
                detectionResultRowIndicatorColumn.setCodeword(i2, codeword);
                n6 = bl2 ? codeword.getStartX() : codeword.getEndX();
            }
            ++n4;
        }
        return detectionResultRowIndicatorColumn;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private static int getStartColumn(DetectionResult detectionResult, int n2, int n3, boolean bl2) {
        void var7_10;
        void var7_7;
        int n4 = bl2 ? 1 : -1;
        Object var7_5 = null;
        if (PDF417ScanningDecoder.isValidBarcodeColumn(detectionResult, n2 - n4)) {
            Codeword codeword = detectionResult.getDetectionResultColumn(n2 - n4).getCodeword(n3);
        }
        if (var7_7 != null) {
            if (!bl2) return var7_7.getStartX();
            return var7_7.getEndX();
        }
        Codeword codeword = detectionResult.getDetectionResultColumn(n2).getCodewordNearby(n3);
        if (codeword != null) {
            if (!bl2) return codeword.getEndX();
            return codeword.getStartX();
        }
        if (PDF417ScanningDecoder.isValidBarcodeColumn(detectionResult, n2 - n4)) {
            Codeword codeword2 = detectionResult.getDetectionResultColumn(n2 - n4).getCodewordNearby(n3);
        }
        if (var7_10 != null) {
            if (!bl2) return var7_10.getStartX();
            return var7_10.getEndX();
        }
        int n5 = 0;
        n3 = n2;
        n2 = n5;
        while (true) {
            if (!PDF417ScanningDecoder.isValidBarcodeColumn(detectionResult, n3 - n4)) {
                if (!bl2) return detectionResult.getBoundingBox().getMaxX();
                return detectionResult.getBoundingBox().getMinX();
            }
            n5 = n3 - n4;
            Codeword[] codewordArray = detectionResult.getDetectionResultColumn(n5).getCodewords();
            int n6 = codewordArray.length;
            for (n3 = 0; n3 < n6; ++n3) {
                Codeword codeword3 = codewordArray[n3];
                if (codeword3 == null) continue;
                if (bl2) {
                    n3 = codeword3.getEndX();
                    return n3 + n4 * n2 * (codeword3.getEndX() - codeword3.getStartX());
                }
                n3 = codeword3.getStartX();
                return n3 + n4 * n2 * (codeword3.getEndX() - codeword3.getStartX());
            }
            ++n2;
            n3 = n5;
        }
    }

    private static boolean isValidBarcodeColumn(DetectionResult detectionResult, int n2) {
        return n2 >= 0 && n2 <= detectionResult.getBarcodeColumnCount() + 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static DetectionResult merge(DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn, DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn2) throws NotFoundException, FormatException {
        BarcodeMetadata barcodeMetadata;
        if (detectionResultRowIndicatorColumn == null && detectionResultRowIndicatorColumn2 == null || (barcodeMetadata = PDF417ScanningDecoder.getBarcodeMetadata(detectionResultRowIndicatorColumn, detectionResultRowIndicatorColumn2)) == null) {
            return null;
        }
        return new DetectionResult(barcodeMetadata, BoundingBox.merge(PDF417ScanningDecoder.adjustBoundingBox(detectionResultRowIndicatorColumn), PDF417ScanningDecoder.adjustBoundingBox(detectionResultRowIndicatorColumn2)));
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String toString(BarcodeValue[][] object) {
        Formatter formatter = new Formatter();
        int n2 = 0;
        while (true) {
            if (n2 >= ((BarcodeValue[][])object).length) {
                String string2 = formatter.toString();
                formatter.close();
                return string2;
            }
            formatter.format("Row %2d: ", n2);
            for (int i2 = 0; i2 < object[n2].length; ++i2) {
                BarcodeValue barcodeValue = object[n2][i2];
                if (barcodeValue.getValue().length == 0) {
                    formatter.format("        ", (Object[])null);
                    continue;
                }
                formatter.format("%4d(%2d)", barcodeValue.getValue()[0], barcodeValue.getConfidence(barcodeValue.getValue()[0]));
            }
            formatter.format("%n", new Object[0]);
            ++n2;
        }
    }

    private static void verifyCodewordCount(int[] nArray, int n2) throws FormatException {
        block6: {
            block5: {
                if (nArray.length < 4) {
                    throw FormatException.getFormatInstance();
                }
                int n3 = nArray[0];
                if (n3 > nArray.length) {
                    throw FormatException.getFormatInstance();
                }
                if (n3 != 0) break block5;
                if (n2 >= nArray.length) break block6;
                nArray[0] = nArray.length - n2;
            }
            return;
        }
        throw FormatException.getFormatInstance();
    }
}

