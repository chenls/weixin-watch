/*
 * Decompiled with CFR 0.151.
 */
package com.ta.utdid2.android.utils;

public class IntUtils {
    public static byte[] getBytes(int n2) {
        byte by2 = (byte)(n2 % 256);
        byte by3 = (byte)((n2 >>= 8) % 256);
        byte by4 = (byte)((n2 >>= 8) % 256);
        return new byte[]{(byte)((n2 >> 8) % 256), by4, by3, by2};
    }

    public static byte[] getBytes(byte[] byArray, int n2) {
        if (byArray.length == 4) {
            byArray[3] = (byte)(n2 % 256);
            byArray[2] = (byte)((n2 >>= 8) % 256);
            byArray[1] = (byte)((n2 >>= 8) % 256);
            byArray[0] = (byte)((n2 >> 8) % 256);
            return byArray;
        }
        return null;
    }
}

