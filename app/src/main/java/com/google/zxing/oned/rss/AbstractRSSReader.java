/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss;

import com.google.zxing.NotFoundException;
import com.google.zxing.oned.OneDReader;

public abstract class AbstractRSSReader
extends OneDReader {
    private static final float MAX_AVG_VARIANCE = 0.2f;
    private static final float MAX_FINDER_PATTERN_RATIO = 0.89285713f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.45f;
    private static final float MIN_FINDER_PATTERN_RATIO = 0.7916667f;
    private final int[] dataCharacterCounters;
    private final int[] decodeFinderCounters = new int[4];
    private final int[] evenCounts;
    private final float[] evenRoundingErrors;
    private final int[] oddCounts;
    private final float[] oddRoundingErrors;

    protected AbstractRSSReader() {
        this.dataCharacterCounters = new int[8];
        this.oddRoundingErrors = new float[4];
        this.evenRoundingErrors = new float[4];
        this.oddCounts = new int[this.dataCharacterCounters.length / 2];
        this.evenCounts = new int[this.dataCharacterCounters.length / 2];
    }

    protected static int count(int[] nArray) {
        int n2 = 0;
        int n3 = nArray.length;
        for (int i2 = 0; i2 < n3; ++i2) {
            n2 += nArray[i2];
        }
        return n2;
    }

    protected static void decrement(int[] nArray, float[] fArray) {
        int n2 = 0;
        float f2 = fArray[0];
        for (int i2 = 1; i2 < nArray.length; ++i2) {
            float f3 = f2;
            if (fArray[i2] < f2) {
                f3 = fArray[i2];
                n2 = i2;
            }
            f2 = f3;
        }
        nArray[n2] = nArray[n2] - 1;
    }

    protected static void increment(int[] nArray, float[] fArray) {
        int n2 = 0;
        float f2 = fArray[0];
        for (int i2 = 1; i2 < nArray.length; ++i2) {
            float f3 = f2;
            if (fArray[i2] > f2) {
                f3 = fArray[i2];
                n2 = i2;
            }
            f2 = f3;
        }
        nArray[n2] = nArray[n2] + 1;
    }

    protected static boolean isFinderPattern(int[] nArray) {
        int n2 = nArray[0] + nArray[1];
        int n3 = nArray[2];
        int n4 = nArray[3];
        float f2 = (float)n2 / (float)(n3 + n2 + n4);
        if (f2 >= 0.7916667f && f2 <= 0.89285713f) {
            n3 = Integer.MAX_VALUE;
            int n5 = Integer.MIN_VALUE;
            for (int n6 : nArray) {
                n4 = n5;
                if (n6 > n5) {
                    n4 = n6;
                }
                int n7 = n3;
                if (n6 < n3) {
                    n7 = n6;
                }
                n5 = n4;
                n3 = n7;
            }
            return n5 < n3 * 10;
        }
        return false;
    }

    protected static int parseFinderValue(int[] nArray, int[][] nArray2) throws NotFoundException {
        for (int i2 = 0; i2 < nArray2.length; ++i2) {
            if (!(AbstractRSSReader.patternMatchVariance(nArray, nArray2[i2], 0.45f) < 0.2f)) continue;
            return i2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    protected final int[] getDataCharacterCounters() {
        return this.dataCharacterCounters;
    }

    protected final int[] getDecodeFinderCounters() {
        return this.decodeFinderCounters;
    }

    protected final int[] getEvenCounts() {
        return this.evenCounts;
    }

    protected final float[] getEvenRoundingErrors() {
        return this.evenRoundingErrors;
    }

    protected final int[] getOddCounts() {
        return this.oddCounts;
    }

    protected final float[] getOddRoundingErrors() {
        return this.oddRoundingErrors;
    }
}

