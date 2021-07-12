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

public final class Code39Reader
extends OneDReader {
    private static final char[] ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".toCharArray();
    static final String ALPHABET_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%";
    private static final int ASTERISK_ENCODING;
    static final int[] CHARACTER_ENCODINGS;
    private final int[] counters;
    private final StringBuilder decodeRowResult;
    private final boolean extendedMode;
    private final boolean usingCheckDigit;

    static {
        CHARACTER_ENCODINGS = new int[]{52, 289, 97, 352, 49, 304, 112, 37, 292, 100, 265, 73, 328, 25, 280, 88, 13, 268, 76, 28, 259, 67, 322, 19, 274, 82, 7, 262, 70, 22, 385, 193, 448, 145, 400, 208, 133, 388, 196, 148, 168, 162, 138, 42};
        ASTERISK_ENCODING = CHARACTER_ENCODINGS[39];
    }

    public Code39Reader() {
        this(false);
    }

    public Code39Reader(boolean bl2) {
        this(bl2, false);
    }

    public Code39Reader(boolean bl2, boolean bl3) {
        this.usingCheckDigit = bl2;
        this.extendedMode = bl3;
        this.decodeRowResult = new StringBuilder(20);
        this.counters = new int[9];
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
            if (c2 == '+' || c2 == '$' || c2 == '%' || c2 == '/') {
                char c3 = charSequence.charAt(n3 + 1);
                int n4 = 0;
                switch (c2) {
                    case '+': {
                        if (c3 >= 'A' && c3 <= 'Z') {
                            n4 = (char)(c3 + 32);
                            break;
                        }
                        throw FormatException.getFormatInstance();
                    }
                    case '$': {
                        if (c3 >= 'A' && c3 <= 'Z') {
                            n4 = (char)(c3 - 64);
                            break;
                        }
                        throw FormatException.getFormatInstance();
                    }
                    case '%': {
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
                    case '/': {
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
    private static int[] findAsteriskPattern(BitArray bitArray, int[] nArray) throws NotFoundException {
        int n2 = bitArray.getSize();
        int n3 = bitArray.getNextSet(0);
        int n4 = 0;
        int n5 = n3;
        boolean bl2 = false;
        int n6 = nArray.length;
        while (true) {
            if (n3 >= n2) {
                throw NotFoundException.getNotFoundInstance();
            }
            if (bitArray.get(n3) ^ bl2) {
                nArray[n4] = nArray[n4] + 1;
            } else {
                if (n4 == n6 - 1) {
                    if (Code39Reader.toNarrowWidePattern(nArray) == ASTERISK_ENCODING && bitArray.isRange(Math.max(0, n5 - (n3 - n5) / 2), n5, false)) {
                        return new int[]{n5, n3};
                    }
                    n5 += nArray[0] + nArray[1];
                    System.arraycopy(nArray, 2, nArray, 0, n6 - 2);
                    nArray[n6 - 2] = 0;
                    nArray[n6 - 1] = 0;
                    --n4;
                } else {
                    ++n4;
                }
                nArray[n4] = 1;
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

    private static int toNarrowWidePattern(int[] nArray) {
        int n2;
        int n3 = nArray.length;
        int n4 = 0;
        do {
            int n5;
            int n6;
            int n7;
            int n82;
            int n9 = Integer.MAX_VALUE;
            for (int n82 : nArray) {
                n7 = n9;
                if (n82 < n9) {
                    n7 = n9;
                    if (n82 > n4) {
                        n7 = n82;
                    }
                }
                n9 = n7;
            }
            n4 = n9;
            n2 = 0;
            n7 = 0;
            n9 = 0;
            for (n82 = 0; n82 < n3; ++n82) {
                int n10 = nArray[n82];
                int n11 = n9;
                n6 = n7;
                n5 = n2;
                if (n10 > n4) {
                    n11 = n9 | 1 << n3 - 1 - n82;
                    n5 = n2 + 1;
                    n6 = n7 + n10;
                }
                n9 = n11;
                n7 = n6;
                n2 = n5;
            }
            if (n2 != 3) continue;
            n5 = 0;
            n82 = n2;
            n2 = n5;
            while (true) {
                block11: {
                    block10: {
                        n5 = n9;
                        if (n2 >= n3) break block10;
                        n5 = n9;
                        if (n82 <= 0) break block10;
                        n6 = nArray[n2];
                        n5 = n82;
                        if (n6 <= n4) break block11;
                        n5 = n82 - 1;
                        if (n6 * 2 < n7) break block11;
                        n5 = -1;
                    }
                    return n5;
                }
                ++n2;
                n82 = n5;
            }
        } while (n2 > 3);
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Result decodeRow(int n2, BitArray object, Map<DecodeHintType, ?> object2) throws NotFoundException, ChecksumException, FormatException {
        int n3;
        int n4;
        int n5;
        char c2;
        int[] nArray = this.counters;
        Arrays.fill(nArray, 0);
        StringBuilder stringBuilder = this.decodeRowResult;
        stringBuilder.setLength(0);
        object2 = Code39Reader.findAsteriskPattern((BitArray)object, nArray);
        int n6 = ((BitArray)object).getNextSet((int)object2[1]);
        int n7 = ((BitArray)object).getSize();
        do {
            n5 = n6;
            Code39Reader.recordPattern((BitArray)object, n5, nArray);
            n6 = Code39Reader.toNarrowWidePattern(nArray);
            if (n6 < 0) {
                throw NotFoundException.getNotFoundInstance();
            }
            c2 = Code39Reader.patternToChar(n6);
            stringBuilder.append(c2);
            n4 = nArray.length;
            n6 = n5;
            for (n3 = 0; n3 < n4; n6 += nArray[n3], ++n3) {
            }
            n6 = n4 = ((BitArray)object).getNextSet(n6);
        } while (c2 != '*');
        stringBuilder.setLength(stringBuilder.length() - 1);
        n6 = 0;
        int n8 = nArray.length;
        for (n3 = 0; n3 < n8; n6 += nArray[n3], ++n3) {
        }
        if (n4 != n7 && (n4 - n5 - n6) * 2 < n6) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (this.usingCheckDigit) {
            n7 = stringBuilder.length() - 1;
            n4 = 0;
            for (n3 = 0; n3 < n7; n4 += ALPHABET_STRING.indexOf(this.decodeRowResult.charAt(n3)), ++n3) {
            }
            if (stringBuilder.charAt(n7) != ALPHABET[n4 % 43]) {
                throw ChecksumException.getChecksumInstance();
            }
            stringBuilder.setLength(n7);
        }
        if (stringBuilder.length() == 0) {
            throw NotFoundException.getNotFoundInstance();
        }
        object = this.extendedMode ? Code39Reader.decodeExtended(stringBuilder) : stringBuilder.toString();
        float f2 = (float)(object2[1] + object2[0]) / 2.0f;
        float f3 = n5;
        float f4 = (float)n6 / 2.0f;
        object2 = new ResultPoint(f2, n2);
        ResultPoint resultPoint = new ResultPoint(f3 + f4, n2);
        BarcodeFormat barcodeFormat = BarcodeFormat.CODE_39;
        return new Result((String)object, null, new ResultPoint[]{object2, resultPoint}, barcodeFormat);
    }
}

