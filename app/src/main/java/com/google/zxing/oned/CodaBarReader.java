/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.OneDReader;
import java.util.Arrays;
import java.util.Map;

public final class CodaBarReader
extends OneDReader {
    static final char[] ALPHABET = "0123456789-$:/.+ABCD".toCharArray();
    private static final String ALPHABET_STRING = "0123456789-$:/.+ABCD";
    static final int[] CHARACTER_ENCODINGS = new int[]{3, 6, 9, 96, 18, 66, 33, 36, 48, 72, 12, 24, 69, 81, 84, 21, 26, 41, 11, 14};
    private static final float MAX_ACCEPTABLE = 2.0f;
    private static final int MIN_CHARACTER_LENGTH = 3;
    private static final float PADDING = 1.5f;
    private static final char[] STARTEND_ENCODING = new char[]{'A', 'B', 'C', 'D'};
    private int counterLength = 0;
    private int[] counters;
    private final StringBuilder decodeRowResult = new StringBuilder(20);

    public CodaBarReader() {
        this.counters = new int[80];
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean arrayContains(char[] cArray, char c2) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (cArray == null) return bl3;
        int n2 = cArray.length;
        int n3 = 0;
        while (true) {
            bl3 = bl2;
            if (n3 >= n2) return bl3;
            if (cArray[n3] == c2) {
                return true;
            }
            ++n3;
        }
    }

    private void counterAppend(int n2) {
        this.counters[this.counterLength] = n2;
        ++this.counterLength;
        if (this.counterLength >= this.counters.length) {
            int[] nArray = new int[this.counterLength * 2];
            System.arraycopy(this.counters, 0, nArray, 0, this.counterLength);
            this.counters = nArray;
        }
    }

    private int findStartPattern() throws NotFoundException {
        for (int i2 = 1; i2 < this.counterLength; i2 += 2) {
            int n2 = this.toNarrowWidePattern(i2);
            if (n2 == -1 || !CodaBarReader.arrayContains(STARTEND_ENCODING, ALPHABET[n2])) continue;
            int n3 = 0;
            for (n2 = i2; n2 < i2 + 7; ++n2) {
                n3 += this.counters[n2];
            }
            if (i2 != 1 && this.counters[i2 - 1] < n3 / 2) continue;
            return i2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setCounters(BitArray bitArray) throws NotFoundException {
        int n2;
        this.counterLength = 0;
        int n3 = bitArray.getNextUnset(0);
        if (n3 >= (n2 = bitArray.getSize())) {
            throw NotFoundException.getNotFoundInstance();
        }
        int n4 = 1;
        int n5 = 0;
        while (true) {
            int n6;
            if (n3 >= n2) {
                this.counterAppend(n5);
                return;
            }
            if (bitArray.get(n3) ^ n4) {
                n6 = n4;
                n4 = ++n5;
            } else {
                this.counterAppend(n5);
                n5 = 1;
                n6 = n4 == 0 ? 1 : 0;
                n4 = n5;
            }
            ++n3;
            n5 = n4;
            n4 = n6;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private int toNarrowWidePattern(int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = n2 + 7;
        if (n6 >= this.counterLength) {
            return -1;
        }
        int[] nArray = this.counters;
        int n7 = 0;
        int n8 = Integer.MAX_VALUE;
        for (n5 = n2; n5 < n6; n5 += 2) {
            n4 = nArray[n5];
            n3 = n8;
            if (n4 < n8) {
                n3 = n4;
            }
            n8 = n7;
            if (n4 > n7) {
                n8 = n4;
            }
            n7 = n8;
            n8 = n3;
        }
        int n9 = (n8 + n7) / 2;
        n7 = 0;
        n8 = Integer.MAX_VALUE;
        for (n5 = n2 + 1; n5 < n6; n5 += 2) {
            n4 = nArray[n5];
            n3 = n8;
            if (n4 < n8) {
                n3 = n4;
            }
            n8 = n7;
            if (n4 > n7) {
                n8 = n4;
            }
            n7 = n8;
            n8 = n3;
        }
        n6 = (n8 + n7) / 2;
        n3 = 128;
        n5 = 0;
        for (n7 = 0; n7 < 7; ++n7) {
            n4 = (n7 & 1) == 0 ? n9 : n6;
            n3 >>= 1;
            n8 = n5;
            if (nArray[n2 + n7] > n4) {
                n8 = n5 | n3;
            }
            n5 = n8;
        }
        n2 = 0;
        while (n2 < CHARACTER_ENCODINGS.length) {
            n7 = n2;
            if (CHARACTER_ENCODINGS[n2] == n5) return n7;
            ++n2;
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Result decodeRow(int n2, BitArray object, Map<DecodeHintType, ?> object2) throws NotFoundException {
        int n3;
        int n4;
        int n5;
        Arrays.fill(this.counters, 0);
        this.setCounters((BitArray)object);
        int n6 = n5 = this.findStartPattern();
        this.decodeRowResult.setLength(0);
        do {
            if ((n3 = this.toNarrowWidePattern(n6)) == -1) {
                throw NotFoundException.getNotFoundInstance();
            }
            this.decodeRowResult.append((char)n3);
            n4 = n6 + 8;
            if (this.decodeRowResult.length() > 1 && CodaBarReader.arrayContains(STARTEND_ENCODING, ALPHABET[n3])) break;
            n6 = n4;
        } while (n4 < this.counterLength);
        int n7 = this.counters[n4 - 1];
        n3 = 0;
        for (n6 = -8; n6 < -1; n3 += this.counters[n4 + n6], ++n6) {
        }
        if (n4 < this.counterLength && n7 < n3 / 2) {
            throw NotFoundException.getNotFoundInstance();
        }
        this.validatePattern(n5);
        for (n6 = 0; n6 < this.decodeRowResult.length(); ++n6) {
            this.decodeRowResult.setCharAt(n6, ALPHABET[this.decodeRowResult.charAt(n6)]);
        }
        char c2 = this.decodeRowResult.charAt(0);
        if (!CodaBarReader.arrayContains(STARTEND_ENCODING, c2)) {
            throw NotFoundException.getNotFoundInstance();
        }
        c2 = this.decodeRowResult.charAt(this.decodeRowResult.length() - 1);
        if (!CodaBarReader.arrayContains(STARTEND_ENCODING, c2)) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (this.decodeRowResult.length() <= 3) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (object2 == null || !object2.containsKey((Object)DecodeHintType.RETURN_CODABAR_START_END)) {
            this.decodeRowResult.deleteCharAt(this.decodeRowResult.length() - 1);
            this.decodeRowResult.deleteCharAt(0);
        }
        n6 = 0;
        for (n3 = 0; n3 < n5; n6 += this.counters[n3], ++n3) {
        }
        float f2 = n6;
        while (true) {
            if (n5 >= n4 - 1) {
                float f3 = n6;
                object = this.decodeRowResult.toString();
                object2 = new ResultPoint(f2, n2);
                ResultPoint resultPoint = new ResultPoint(f3, n2);
                BarcodeFormat barcodeFormat = BarcodeFormat.CODABAR;
                return new Result((String)object, null, new ResultPoint[]{object2, resultPoint}, barcodeFormat);
            }
            n6 += this.counters[n5];
            ++n5;
        }
    }

    void validatePattern(int n2) throws NotFoundException {
        float[] fArray;
        float[] fArray2;
        int n3;
        int n4;
        int n5;
        int[] nArray;
        int[] nArray2;
        int[] nArray3 = nArray2 = new int[4];
        nArray2[0] = 0;
        nArray3[1] = 0;
        nArray3[2] = 0;
        nArray3[3] = 0;
        int[] nArray4 = nArray = new int[4];
        nArray[0] = 0;
        nArray4[1] = 0;
        nArray4[2] = 0;
        nArray4[3] = 0;
        int n6 = this.decodeRowResult.length() - 1;
        int n7 = n2;
        int n8 = 0;
        while (true) {
            n5 = CHARACTER_ENCODINGS[this.decodeRowResult.charAt(n8)];
            for (n4 = 6; n4 >= 0; --n4) {
                n3 = (n4 & 1) + (n5 & 1) * 2;
                nArray2[n3] = nArray2[n3] + this.counters[n7 + n4];
                nArray[n3] = nArray[n3] + 1;
                n5 >>= 1;
            }
            if (n8 >= n6) {
                fArray2 = new float[4];
                fArray = new float[4];
                for (n8 = 0; n8 < 2; ++n8) {
                    fArray[n8] = 0.0f;
                    fArray[n8 + 2] = ((float)nArray2[n8] / (float)nArray[n8] + (float)nArray2[n8 + 2] / (float)nArray[n8 + 2]) / 2.0f;
                    fArray2[n8] = fArray[n8 + 2];
                    fArray2[n8 + 2] = ((float)nArray2[n8 + 2] * 2.0f + 1.5f) / (float)nArray[n8 + 2];
                }
                break;
            }
            n7 += 8;
            ++n8;
        }
        n7 = 0;
        n8 = n2;
        n2 = n7;
        while (true) {
            n4 = CHARACTER_ENCODINGS[this.decodeRowResult.charAt(n2)];
            for (n7 = 6; n7 >= 0; --n7) {
                n3 = this.counters[n8 + n7];
                n5 = (n7 & 1) + (n4 & 1) * 2;
                if ((float)n3 < fArray[n5] || (float)n3 > fArray2[n5]) {
                    throw NotFoundException.getNotFoundInstance();
                }
                n4 >>= 1;
            }
            if (n2 >= n6) {
                return;
            }
            n8 += 8;
            ++n2;
        }
    }
}

