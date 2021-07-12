/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.EANManufacturerOrgSupport;
import com.google.zxing.oned.OneDReader;
import com.google.zxing.oned.UPCEANExtensionSupport;
import java.util.Arrays;
import java.util.Map;

public abstract class UPCEANReader
extends OneDReader {
    static final int[][] L_AND_G_PATTERNS;
    static final int[][] L_PATTERNS;
    private static final float MAX_AVG_VARIANCE = 0.48f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.7f;
    static final int[] MIDDLE_PATTERN;
    static final int[] START_END_PATTERN;
    private final StringBuilder decodeRowStringBuffer = new StringBuilder(20);
    private final EANManufacturerOrgSupport eanManSupport;
    private final UPCEANExtensionSupport extensionReader = new UPCEANExtensionSupport();

    static {
        START_END_PATTERN = new int[]{1, 1, 1};
        MIDDLE_PATTERN = new int[]{1, 1, 1, 1, 1};
        int[] nArray = new int[]{3, 2, 1, 1};
        int[] nArray2 = new int[]{1, 4, 1, 1};
        int[] nArray3 = new int[]{1, 1, 3, 2};
        int[] nArray4 = new int[]{1, 1, 1, 4};
        int[] nArray5 = new int[]{1, 3, 1, 2};
        int[] nArray6 = new int[]{1, 2, 1, 3};
        int[] nArray7 = new int[]{3, 1, 1, 2};
        L_PATTERNS = new int[][]{nArray, {2, 2, 2, 1}, {2, 1, 2, 2}, nArray2, nArray3, {1, 2, 3, 1}, nArray4, nArray5, nArray6, nArray7};
        L_AND_G_PATTERNS = new int[20][];
        System.arraycopy(L_PATTERNS, 0, L_AND_G_PATTERNS, 0, 10);
        for (int i2 = 10; i2 < 20; ++i2) {
            nArray = L_PATTERNS[i2 - 10];
            nArray2 = new int[nArray.length];
            for (int i3 = 0; i3 < nArray.length; ++i3) {
                nArray2[i3] = nArray[nArray.length - i3 - 1];
            }
            UPCEANReader.L_AND_G_PATTERNS[i2] = nArray2;
        }
    }

    protected UPCEANReader() {
        this.eanManSupport = new EANManufacturerOrgSupport();
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean checkStandardUPCEANChecksum(CharSequence charSequence) throws FormatException {
        block5: {
            block4: {
                int n2;
                int n3;
                int n4 = charSequence.length();
                if (n4 == 0) break block4;
                int n5 = 0;
                for (n2 = n4 - 2; n2 >= 0; n5 += n3, n2 -= 2) {
                    n3 = charSequence.charAt(n2) - 48;
                    if (n3 >= 0 && n3 <= 9) continue;
                    throw FormatException.getFormatInstance();
                }
                n5 *= 3;
                for (n2 = n4 - 1; n2 >= 0; n5 += n4, n2 -= 2) {
                    n4 = charSequence.charAt(n2) - 48;
                    if (n4 >= 0 && n4 <= 9) continue;
                    throw FormatException.getFormatInstance();
                }
                if (n5 % 10 == 0) break block5;
            }
            return false;
        }
        return true;
    }

    static int decodeDigit(BitArray bitArray, int[] nArray, int n2, int[][] nArray2) throws NotFoundException {
        UPCEANReader.recordPattern(bitArray, n2, nArray);
        float f2 = 0.48f;
        int n3 = -1;
        int n4 = nArray2.length;
        for (n2 = 0; n2 < n4; ++n2) {
            float f3 = UPCEANReader.patternMatchVariance(nArray, nArray2[n2], 0.7f);
            float f4 = f2;
            if (f3 < f2) {
                f4 = f3;
                n3 = n2;
            }
            f2 = f4;
        }
        if (n3 >= 0) {
            return n3;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    static int[] findGuardPattern(BitArray bitArray, int n2, boolean bl2, int[] nArray) throws NotFoundException {
        return UPCEANReader.findGuardPattern(bitArray, n2, bl2, nArray, new int[nArray.length]);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int[] findGuardPattern(BitArray bitArray, int n2, boolean bl2, int[] nArray, int[] nArray2) throws NotFoundException {
        int n3 = nArray.length;
        int n4 = bitArray.getSize();
        boolean bl3 = bl2;
        n2 = bl2 ? bitArray.getNextUnset(n2) : bitArray.getNextSet(n2);
        int n5 = 0;
        int n6 = n2;
        int n7 = n2;
        n2 = n6;
        n6 = n5;
        while (true) {
            if (n7 >= n4) {
                throw NotFoundException.getNotFoundInstance();
            }
            if (bitArray.get(n7) ^ bl3) {
                nArray2[n6] = nArray2[n6] + 1;
                bl2 = bl3;
            } else {
                if (n6 == n3 - 1) {
                    if (UPCEANReader.patternMatchVariance(nArray2, nArray, 0.7f) < 0.48f) {
                        return new int[]{n2, n7};
                    }
                    n2 += nArray2[0] + nArray2[1];
                    System.arraycopy(nArray2, 2, nArray2, 0, n3 - 2);
                    nArray2[n3 - 2] = 0;
                    nArray2[n3 - 1] = 0;
                    --n6;
                } else {
                    ++n6;
                }
                nArray2[n6] = 1;
                bl2 = !bl3;
            }
            ++n7;
            bl3 = bl2;
        }
    }

    static int[] findStartGuardPattern(BitArray bitArray) throws NotFoundException {
        boolean bl2 = false;
        int[] nArray = null;
        int n2 = 0;
        int[] nArray2 = new int[START_END_PATTERN.length];
        while (!bl2) {
            Arrays.fill(nArray2, 0, START_END_PATTERN.length, 0);
            int[] nArray3 = UPCEANReader.findGuardPattern(bitArray, n2, false, START_END_PATTERN, nArray2);
            int n3 = nArray3[0];
            int n4 = nArray3[1];
            int n5 = n3 - (n4 - n3);
            n2 = n4;
            nArray = nArray3;
            if (n5 < 0) continue;
            bl2 = bitArray.isRange(n5, n3, false);
            n2 = n4;
            nArray = nArray3;
        }
        return nArray;
    }

    boolean checkChecksum(String string2) throws FormatException {
        return UPCEANReader.checkStandardUPCEANChecksum(string2);
    }

    int[] decodeEnd(BitArray bitArray, int n2) throws NotFoundException {
        return UPCEANReader.findGuardPattern(bitArray, n2, false, START_END_PATTERN);
    }

    protected abstract int decodeMiddle(BitArray var1, int[] var2, StringBuilder var3) throws NotFoundException;

    @Override
    public Result decodeRow(int n2, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        return this.decodeRow(n2, bitArray, UPCEANReader.findStartGuardPattern(bitArray), map);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Result decodeRow(int n2, BitArray object, int[] object2, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        Object object3;
        Object object4;
        block13: {
            int n3;
            object4 = map == null ? null : (ResultPointCallback)map.get((Object)DecodeHintType.NEED_RESULT_POINT_CALLBACK);
            if (object4 != null) {
                object4.foundPossibleResultPoint(new ResultPoint((float)(object2[0] + object2[1]) / 2.0f, n2));
            }
            object3 = this.decodeRowStringBuffer;
            object3.setLength(0);
            int n4 = this.decodeMiddle((BitArray)object, (int[])object2, (StringBuilder)object3);
            if (object4 != null) {
                object4.foundPossibleResultPoint(new ResultPoint(n4, n2));
            }
            int[] nArray = this.decodeEnd((BitArray)object, n4);
            if (object4 != null) {
                object4.foundPossibleResultPoint(new ResultPoint((float)(nArray[0] + nArray[1]) / 2.0f, n2));
            }
            if ((n3 = (n4 = nArray[1]) + (n4 - nArray[0])) >= ((BitArray)object).getSize() || !((BitArray)object).isRange(n4, n3, false)) {
                throw NotFoundException.getNotFoundInstance();
            }
            object4 = object3.toString();
            if (((String)object4).length() < 8) {
                throw FormatException.getFormatInstance();
            }
            if (!this.checkChecksum((String)object4)) {
                throw ChecksumException.getChecksumInstance();
            }
            float f2 = (float)(object2[1] + object2[0]) / 2.0f;
            float f3 = (float)(nArray[1] + nArray[0]) / 2.0f;
            object3 = this.getBarcodeFormat();
            object2 = new Result((String)object4, null, new ResultPoint[]{new ResultPoint(f2, n2), new ResultPoint(f3, n2)}, (BarcodeFormat)((Object)object3));
            n4 = 0;
            try {
                object = this.extensionReader.decodeRow(n2, (BitArray)object, nArray[1]);
                ((Result)object2).putMetadata(ResultMetadataType.UPC_EAN_EXTENSION, ((Result)object).getText());
                ((Result)object2).putAllMetadata(((Result)object).getResultMetadata());
                ((Result)object2).addResultPoints(((Result)object).getResultPoints());
                n2 = ((Result)object).getText().length();
            }
            catch (ReaderException readerException) {
                n2 = n4;
            }
            object = map == null ? null : (Object)((int[])map.get((Object)DecodeHintType.ALLOWED_EAN_EXTENSIONS));
            if (object == null) break block13;
            int n5 = 0;
            int n6 = ((Object)object).length;
            n4 = 0;
            while (true) {
                block15: {
                    block14: {
                        n3 = n5;
                        if (n4 >= n6) break block14;
                        if (n2 != object[n4]) break block15;
                        n3 = 1;
                    }
                    if (n3 != 0) break;
                    throw NotFoundException.getNotFoundInstance();
                }
                ++n4;
            }
        }
        if ((object3 == BarcodeFormat.EAN_13 || object3 == BarcodeFormat.UPC_A) && (object = this.eanManSupport.lookupCountryIdentifier((String)object4)) != null) {
            ((Result)object2).putMetadata(ResultMetadataType.POSSIBLE_COUNTRY, object);
        }
        return object2;
    }

    abstract BarcodeFormat getBarcodeFormat();
}

