/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.UPCEANReader;

public final class UPCEReader
extends UPCEANReader {
    private static final int[] MIDDLE_END_PATTERN = new int[]{1, 1, 1, 1, 1, 1};
    private static final int[][] NUMSYS_AND_CHECK_DIGIT_PATTERNS = new int[][]{{56, 52, 50, 49, 44, 38, 35, 42, 41, 37}, {7, 11, 13, 14, 19, 25, 28, 21, 22, 26}};
    private final int[] decodeMiddleCounters = new int[4];

    /*
     * Enabled aggressive block sorting
     */
    public static String convertUPCEtoUPCA(String string2) {
        char[] cArray = new char[6];
        string2.getChars(1, 7, cArray, 0);
        StringBuilder stringBuilder = new StringBuilder(12);
        stringBuilder.append(string2.charAt(0));
        char c2 = cArray[5];
        switch (c2) {
            default: {
                stringBuilder.append(cArray, 0, 5);
                stringBuilder.append("0000");
                stringBuilder.append(c2);
                break;
            }
            case '0': 
            case '1': 
            case '2': {
                stringBuilder.append(cArray, 0, 2);
                stringBuilder.append(c2);
                stringBuilder.append("0000");
                stringBuilder.append(cArray, 2, 3);
                break;
            }
            case '3': {
                stringBuilder.append(cArray, 0, 3);
                stringBuilder.append("00000");
                stringBuilder.append(cArray, 3, 2);
                break;
            }
            case '4': {
                stringBuilder.append(cArray, 0, 4);
                stringBuilder.append("00000");
                stringBuilder.append(cArray[4]);
            }
        }
        stringBuilder.append(string2.charAt(7));
        return stringBuilder.toString();
    }

    private static void determineNumSysAndCheckDigit(StringBuilder stringBuilder, int n2) throws NotFoundException {
        for (int i2 = 0; i2 <= 1; ++i2) {
            for (int i3 = 0; i3 < 10; ++i3) {
                if (n2 != NUMSYS_AND_CHECK_DIGIT_PATTERNS[i2][i3]) continue;
                stringBuilder.insert(0, (char)(i2 + 48));
                stringBuilder.append((char)(i3 + 48));
                return;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override
    protected boolean checkChecksum(String string2) throws FormatException {
        return super.checkChecksum(UPCEReader.convertUPCEtoUPCA(string2));
    }

    @Override
    protected int[] decodeEnd(BitArray bitArray, int n2) throws NotFoundException {
        return UPCEReader.findGuardPattern(bitArray, n2, true, MIDDLE_END_PATTERN);
    }

    @Override
    protected int decodeMiddle(BitArray bitArray, int[] nArray, StringBuilder stringBuilder) throws NotFoundException {
        int[] nArray2 = this.decodeMiddleCounters;
        nArray2[0] = 0;
        nArray2[1] = 0;
        nArray2[2] = 0;
        nArray2[3] = 0;
        int n2 = bitArray.getSize();
        int n3 = nArray[1];
        int n4 = 0;
        for (int i2 = 0; i2 < 6 && n3 < n2; ++i2) {
            int n5;
            int n6 = UPCEReader.decodeDigit(bitArray, nArray2, n3, L_AND_G_PATTERNS);
            stringBuilder.append((char)(n6 % 10 + 48));
            int n7 = nArray2.length;
            for (n5 = 0; n5 < n7; ++n5) {
                n3 += nArray2[n5];
            }
            n5 = n4;
            if (n6 >= 10) {
                n5 = n4 | 1 << 5 - i2;
            }
            n4 = n5;
        }
        UPCEReader.determineNumSysAndCheckDigit(stringBuilder, n4);
        return n3;
    }

    @Override
    BarcodeFormat getBarcodeFormat() {
        return BarcodeFormat.UPC_E;
    }
}

