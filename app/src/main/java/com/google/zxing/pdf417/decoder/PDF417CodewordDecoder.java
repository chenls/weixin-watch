/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.pdf417.PDF417Common;
import java.lang.reflect.Array;

final class PDF417CodewordDecoder {
    private static final float[][] RATIOS_TABLE;

    static {
        int n2 = PDF417Common.SYMBOL_TABLE.length;
        RATIOS_TABLE = (float[][])Array.newInstance(Float.TYPE, n2, 8);
        for (n2 = 0; n2 < PDF417Common.SYMBOL_TABLE.length; ++n2) {
            int n3 = PDF417Common.SYMBOL_TABLE[n2];
            int n4 = n3 & 1;
            for (int i2 = 0; i2 < 8; ++i2) {
                float f2 = 0.0f;
                while ((n3 & 1) == n4) {
                    f2 += 1.0f;
                    n3 >>= 1;
                }
                n4 = n3 & 1;
                PDF417CodewordDecoder.RATIOS_TABLE[n2][8 - i2 - 1] = f2 / 17.0f;
            }
        }
    }

    private PDF417CodewordDecoder() {
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int getBitValue(int[] nArray) {
        long l2 = 0L;
        int n2 = 0;
        while (n2 < nArray.length) {
            for (int i2 = 0; i2 < nArray[n2]; ++i2) {
                int n3 = n2 % 2 == 0 ? 1 : 0;
                l2 = l2 << 1 | (long)n3;
            }
            ++n2;
        }
        return (int)l2;
    }

    private static int getClosestDecodedValue(int[] objectArray) {
        int n2;
        int n3 = PDF417Common.getBitCountSum(objectArray);
        float[] fArray = new float[8];
        for (n2 = 0; n2 < fArray.length; ++n2) {
            fArray[n2] = (float)objectArray[n2] / (float)n3;
        }
        float f2 = Float.MAX_VALUE;
        n3 = -1;
        block1: for (n2 = 0; n2 < RATIOS_TABLE.length; ++n2) {
            float f3 = 0.0f;
            objectArray = RATIOS_TABLE[n2];
            int n4 = 0;
            while (true) {
                block8: {
                    float f4;
                    block7: {
                        f4 = f3;
                        if (n4 >= 8) break block7;
                        f4 = objectArray[n4] - fArray[n4];
                        if (!((f3 += f4 * f4) >= f2)) break block8;
                        f4 = f3;
                    }
                    f3 = f2;
                    if (f4 < f2) {
                        f3 = f4;
                        n3 = PDF417Common.SYMBOL_TABLE[n2];
                    }
                    f2 = f3;
                    continue block1;
                }
                ++n4;
            }
        }
        return n3;
    }

    private static int getDecodedCodewordValue(int[] nArray) {
        int n2;
        int n3 = n2 = PDF417CodewordDecoder.getBitValue(nArray);
        if (PDF417Common.getCodeword(n2) == -1) {
            n3 = -1;
        }
        return n3;
    }

    static int getDecodedValue(int[] nArray) {
        int n2 = PDF417CodewordDecoder.getDecodedCodewordValue(PDF417CodewordDecoder.sampleBitCounts(nArray));
        if (n2 != -1) {
            return n2;
        }
        return PDF417CodewordDecoder.getClosestDecodedValue(nArray);
    }

    private static int[] sampleBitCounts(int[] nArray) {
        float f2 = PDF417Common.getBitCountSum(nArray);
        int[] nArray2 = new int[8];
        int n2 = 0;
        int n3 = 0;
        for (int i2 = 0; i2 < 17; ++i2) {
            float f3 = f2 / 34.0f;
            float f4 = (float)i2 * f2 / 17.0f;
            int n4 = n2;
            int n5 = n3;
            if ((float)(nArray[n2] + n3) <= f3 + f4) {
                n5 = n3 + nArray[n2];
                n4 = n2 + 1;
            }
            nArray2[n4] = nArray2[n4] + 1;
            n2 = n4;
            n3 = n5;
        }
        return nArray2;
    }
}

