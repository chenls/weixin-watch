/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.OneDReader;
import java.util.Arrays;
import java.util.Map;

public final class Code93Reader
extends OneDReader {
    private static final char[] ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".toCharArray();
    private static final String ALPHABET_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*";
    private static final int ASTERISK_ENCODING;
    private static final int[] CHARACTER_ENCODINGS;
    private final int[] counters;
    private final StringBuilder decodeRowResult = new StringBuilder(20);

    static {
        CHARACTER_ENCODINGS = new int[]{276, 328, 324, 322, 296, 292, 290, 336, 274, 266, 424, 420, 418, 404, 402, 394, 360, 356, 354, 308, 282, 344, 332, 326, 300, 278, 436, 434, 428, 422, 406, 410, 364, 358, 310, 314, 302, 468, 466, 458, 366, 374, 430, 294, 474, 470, 306, 350};
        ASTERISK_ENCODING = CHARACTER_ENCODINGS[47];
    }

    public Code93Reader() {
        this.counters = new int[6];
    }

    private static void checkChecksums(CharSequence charSequence) throws ChecksumException {
        int n2 = charSequence.length();
        Code93Reader.checkOneChecksum(charSequence, n2 - 2, 20);
        Code93Reader.checkOneChecksum(charSequence, n2 - 1, 15);
    }

    private static void checkOneChecksum(CharSequence charSequence, int n2, int n3) throws ChecksumException {
        int n4 = 1;
        int n5 = 0;
        for (int i2 = n2 - 1; i2 >= 0; --i2) {
            int n6;
            n5 += ALPHABET_STRING.indexOf(charSequence.charAt(i2)) * n4;
            n4 = n6 = n4 + 1;
            if (n6 <= n3) continue;
            n4 = 1;
        }
        if (charSequence.charAt(n2) != ALPHABET[n5 % 47]) {
            throw ChecksumException.getChecksumInstance();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String decodeExtended(CharSequence charSequence) throws FormatException {
        int n2 = charSequence.length();
        StringBuilder stringBuilder = new StringBuilder(n2);
        int n3 = 0;
        while (n3 < n2) {
            char c2 = charSequence.charAt(n3);
            if (c2 >= 'a' && c2 <= 'd') {
                if (n3 >= n2 - 1) {
                    throw FormatException.getFormatInstance();
                }
                char c3 = charSequence.charAt(n3 + 1);
                int n4 = 0;
                switch (c2) {
                    case 'd': {
                        if (c3 >= 'A' && c3 <= 'Z') {
                            n4 = (char)(c3 + 32);
                            break;
                        }
                        throw FormatException.getFormatInstance();
                    }
                    case 'a': {
                        if (c3 >= 'A' && c3 <= 'Z') {
                            n4 = (char)(c3 - 64);
                            break;
                        }
                        throw FormatException.getFormatInstance();
                    }
                    case 'b': {
                        if (c3 >= 'A' && c3 <= 'E') {
                            n4 = (char)(c3 - 38);
                            break;
                        }
                        if (c3 >= 'F' && c3 <= 'W') {
                            n4 = (char)(c3 - 11);
                            break;
                        }
                        throw FormatException.getFormatInstance();
                    }
                    case 'c': {
                        if (c3 >= 'A' && c3 <= 'O') {
                            n4 = (char)(c3 - 32);
                            break;
                        }
                        if (c3 != 'Z') {
                            throw FormatException.getFormatInstance();
                        }
                        n4 = 58;
                        break;
                    }
                }
                stringBuilder.append((char)n4);
                ++n3;
            } else {
                stringBuilder.append(c2);
            }
            ++n3;
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    private int[] findAsteriskPattern(BitArray bitArray) throws NotFoundException {
        int n2 = bitArray.getSize();
        int n3 = bitArray.getNextSet(0);
        Arrays.fill(this.counters, 0);
        int[] nArray = this.counters;
        int n4 = n3;
        boolean bl2 = false;
        int n5 = nArray.length;
        int n6 = 0;
        while (true) {
            if (n3 >= n2) {
                throw NotFoundException.getNotFoundInstance();
            }
            if (bitArray.get(n3) ^ bl2) {
                nArray[n6] = nArray[n6] + 1;
            } else {
                if (n6 == n5 - 1) {
                    if (Code93Reader.toPattern(nArray) == ASTERISK_ENCODING) {
                        return new int[]{n4, n3};
                    }
                    n4 += nArray[0] + nArray[1];
                    System.arraycopy(nArray, 2, nArray, 0, n5 - 2);
                    nArray[n5 - 2] = 0;
                    nArray[n5 - 1] = 0;
                    --n6;
                } else {
                    ++n6;
                }
                nArray[n6] = 1;
                bl2 = !bl2;
            }
            ++n3;
        }
    }

    private static char patternToChar(int n2) throws NotFoundException {
        for (int i2 = 0; i2 < CHARACTER_ENCODINGS.length; ++i2) {
            if (CHARACTER_ENCODINGS[i2] != n2) continue;
            return ALPHABET[i2];
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int toPattern(int[] nArray) {
        int n2;
        int n3 = nArray.length;
        int n4 = 0;
        int n5 = nArray.length;
        for (n2 = 0; n2 < n5; ++n2) {
            n4 += nArray[n2];
        }
        n2 = 0;
        int n6 = 0;
        while (true) {
            int n7;
            block11: {
                block10: {
                    n5 = n2;
                    if (n6 >= n3) break block10;
                    n7 = Math.round((float)nArray[n6] * 9.0f / (float)n4);
                    if (n7 >= 1 && n7 <= 4) break block11;
                    n5 = -1;
                }
                return n5;
            }
            if ((n6 & 1) == 0) {
                int n8 = 0;
                while (true) {
                    n5 = n2;
                    if (n8 < n7) {
                        n2 = n2 << 1 | 1;
                        ++n8;
                        continue;
                    }
                    break;
                }
            } else {
                n5 = n2 << n7;
            }
            ++n6;
            n2 = n5;
        }
    }

    @Override
    public Result decodeRow(int n2, BitArray object, Map<DecodeHintType, ?> object2) throws NotFoundException, ChecksumException, FormatException {
        int n3;
        int n4;
        int n5;
        char c2;
        object2 = this.findAsteriskPattern((BitArray)object);
        int n6 = ((BitArray)object).getNextSet((int)object2[1]);
        int n7 = ((BitArray)object).getSize();
        Object object3 = this.counters;
        Arrays.fill(object3, 0);
        Object object4 = this.decodeRowResult;
        object4.setLength(0);
        do {
            n5 = n6;
            Code93Reader.recordPattern((BitArray)object, n5, object3);
            n6 = Code93Reader.toPattern(object3);
            if (n6 < 0) {
                throw NotFoundException.getNotFoundInstance();
            }
            c2 = Code93Reader.patternToChar(n6);
            object4.append(c2);
            n4 = ((int[])object3).length;
            n6 = n5;
            for (n3 = 0; n3 < n4; ++n3) {
                n6 += object3[n3];
            }
            n6 = n4 = ((BitArray)object).getNextSet(n6);
        } while (c2 != '*');
        object4.deleteCharAt(object4.length() - 1);
        n3 = 0;
        int n8 = ((int[])object3).length;
        for (n6 = 0; n6 < n8; ++n6) {
            n3 += object3[n6];
        }
        if (n4 == n7 || !((BitArray)object).get(n4)) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (object4.length() < 2) {
            throw NotFoundException.getNotFoundInstance();
        }
        Code93Reader.checkChecksums(object4);
        object4.setLength(object4.length() - 2);
        object = Code93Reader.decodeExtended(object4);
        float f2 = (float)(object2[1] + object2[0]) / 2.0f;
        float f3 = n5;
        float f4 = (float)n3 / 2.0f;
        object2 = new ResultPoint(f2, n2);
        object3 = new ResultPoint(f3 + f4, n2);
        object4 = BarcodeFormat.CODE_93;
        return new Result((String)object, null, new ResultPoint[]{object2, object3}, (BarcodeFormat)((Object)object4));
    }
}

