/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.UPCEANReader;
import java.util.EnumMap;
import java.util.Map;

final class UPCEANExtension5Support {
    private static final int[] CHECK_DIGIT_ENCODINGS = new int[]{24, 20, 18, 17, 12, 6, 3, 10, 9, 5};
    private final int[] decodeMiddleCounters = new int[4];
    private final StringBuilder decodeRowStringBuffer = new StringBuilder();

    UPCEANExtension5Support() {
    }

    private static int determineCheckDigit(int n2) throws NotFoundException {
        for (int i2 = 0; i2 < 10; ++i2) {
            if (n2 != CHECK_DIGIT_ENCODINGS[i2]) continue;
            return i2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int extensionChecksum(CharSequence charSequence) {
        int n2;
        int n3 = charSequence.length();
        int n4 = 0;
        for (n2 = n3 - 2; n2 >= 0; n2 -= 2) {
            n4 += charSequence.charAt(n2) - 48;
        }
        n4 *= 3;
        for (n2 = n3 - 1; n2 >= 0; n2 -= 2) {
            n4 += charSequence.charAt(n2) - 48;
        }
        return n4 * 3 % 10;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String parseExtension5String(String string2) {
        String string3;
        switch (string2.charAt(0)) {
            default: {
                string3 = "";
                break;
            }
            case '0': {
                string3 = "\u00a3";
                break;
            }
            case '5': {
                string3 = "$";
                break;
            }
            case '9': {
                if ("90000".equals(string2)) {
                    return null;
                }
                if ("99991".equals(string2)) {
                    return "0.00";
                }
                if ("99990".equals(string2)) {
                    return "Used";
                }
                string3 = "";
            }
        }
        int n2 = Integer.parseInt(string2.substring(1));
        int n3 = n2 / 100;
        if ((n2 %= 100) < 10) {
            string2 = "0" + n2;
            return string3 + String.valueOf(n3) + '.' + string2;
        }
        string2 = String.valueOf(n2);
        return string3 + String.valueOf(n3) + '.' + string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Map<ResultMetadataType, Object> parseExtensionString(String string2) {
        if (string2.length() != 5 || (string2 = UPCEANExtension5Support.parseExtension5String(string2)) == null) {
            return null;
        }
        EnumMap<ResultMetadataType, Object> enumMap = new EnumMap<ResultMetadataType, Object>(ResultMetadataType.class);
        enumMap.put(ResultMetadataType.SUGGESTED_PRICE, string2);
        return enumMap;
    }

    int decodeMiddle(BitArray bitArray, int[] nArray, StringBuilder stringBuilder) throws NotFoundException {
        int n2;
        int[] nArray2 = this.decodeMiddleCounters;
        nArray2[0] = 0;
        nArray2[1] = 0;
        nArray2[2] = 0;
        nArray2[3] = 0;
        int n3 = bitArray.getSize();
        int n4 = nArray[1];
        int n5 = 0;
        for (int i2 = 0; i2 < 5 && n4 < n3; ++i2) {
            int n6 = UPCEANReader.decodeDigit(bitArray, nArray2, n4, UPCEANReader.L_AND_G_PATTERNS);
            stringBuilder.append((char)(n6 % 10 + 48));
            int n7 = nArray2.length;
            for (n2 = 0; n2 < n7; ++n2) {
                n4 += nArray2[n2];
            }
            n7 = n5;
            if (n6 >= 10) {
                n7 = n5 | 1 << 4 - i2;
            }
            n2 = n4;
            if (i2 != 4) {
                n2 = bitArray.getNextUnset(bitArray.getNextSet(n4));
            }
            n5 = n7;
            n4 = n2;
        }
        if (stringBuilder.length() != 5) {
            throw NotFoundException.getNotFoundInstance();
        }
        n2 = UPCEANExtension5Support.determineCheckDigit(n5);
        if (UPCEANExtension5Support.extensionChecksum(stringBuilder.toString()) != n2) {
            throw NotFoundException.getNotFoundInstance();
        }
        return n4;
    }

    Result decodeRow(int n2, BitArray object, int[] object2) throws NotFoundException {
        CharSequence charSequence = this.decodeRowStringBuffer;
        charSequence.setLength(0);
        int n3 = this.decodeMiddle((BitArray)object, (int[])object2, (StringBuilder)charSequence);
        charSequence = charSequence.toString();
        object = UPCEANExtension5Support.parseExtensionString((String)charSequence);
        object2 = new ResultPoint((float)(object2[0] + object2[1]) / 2.0f, n2);
        ResultPoint resultPoint = new ResultPoint(n3, n2);
        BarcodeFormat barcodeFormat = BarcodeFormat.UPC_EAN_EXTENSION;
        object2 = new Result((String)charSequence, null, new ResultPoint[]{object2, resultPoint}, barcodeFormat);
        if (object != null) {
            ((Result)object2).putAllMetadata((Map<ResultMetadataType, Object>)object);
        }
        return object2;
    }
}

