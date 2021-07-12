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

final class UPCEANExtension2Support {
    private final int[] decodeMiddleCounters = new int[4];
    private final StringBuilder decodeRowStringBuffer = new StringBuilder();

    UPCEANExtension2Support() {
    }

    private static Map<ResultMetadataType, Object> parseExtensionString(String string2) {
        if (string2.length() != 2) {
            return null;
        }
        EnumMap<ResultMetadataType, Object> enumMap = new EnumMap<ResultMetadataType, Object>(ResultMetadataType.class);
        enumMap.put(ResultMetadataType.ISSUE_NUMBER, Integer.valueOf(string2));
        return enumMap;
    }

    int decodeMiddle(BitArray bitArray, int[] nArray, StringBuilder stringBuilder) throws NotFoundException {
        int[] nArray2 = this.decodeMiddleCounters;
        nArray2[0] = 0;
        nArray2[1] = 0;
        nArray2[2] = 0;
        nArray2[3] = 0;
        int n2 = bitArray.getSize();
        int n3 = nArray[1];
        int n4 = 0;
        for (int i2 = 0; i2 < 2 && n3 < n2; ++i2) {
            int n5;
            int n6 = UPCEANReader.decodeDigit(bitArray, nArray2, n3, UPCEANReader.L_AND_G_PATTERNS);
            stringBuilder.append((char)(n6 % 10 + 48));
            int n7 = nArray2.length;
            for (n5 = 0; n5 < n7; ++n5) {
                n3 += nArray2[n5];
            }
            n7 = n4;
            if (n6 >= 10) {
                n7 = n4 | 1 << 1 - i2;
            }
            n5 = n3;
            if (i2 != 1) {
                n5 = bitArray.getNextUnset(bitArray.getNextSet(n3));
            }
            n4 = n7;
            n3 = n5;
        }
        if (stringBuilder.length() != 2) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (Integer.parseInt(stringBuilder.toString()) % 4 != n4) {
            throw NotFoundException.getNotFoundInstance();
        }
        return n3;
    }

    Result decodeRow(int n2, BitArray object, int[] object2) throws NotFoundException {
        CharSequence charSequence = this.decodeRowStringBuffer;
        charSequence.setLength(0);
        int n3 = this.decodeMiddle((BitArray)object, (int[])object2, (StringBuilder)charSequence);
        charSequence = charSequence.toString();
        object = UPCEANExtension2Support.parseExtensionString((String)charSequence);
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

