/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzsm;
import java.io.IOException;

public final class zzsx {
    public static final boolean[] zzbuA;
    public static final String[] zzbuB;
    public static final byte[][] zzbuC;
    public static final byte[] zzbuD;
    public static final int[] zzbuw;
    public static final long[] zzbux;
    public static final float[] zzbuy;
    public static final double[] zzbuz;

    static {
        zzbuw = new int[0];
        zzbux = new long[0];
        zzbuy = new float[0];
        zzbuz = new double[0];
        zzbuA = new boolean[0];
        zzbuB = new String[0];
        zzbuC = new byte[0][];
        zzbuD = new byte[0];
    }

    static int zzF(int n2, int n3) {
        return n2 << 3 | n3;
    }

    public static boolean zzb(zzsm zzsm2, int n2) throws IOException {
        return zzsm2.zzmo(n2);
    }

    public static final int zzc(zzsm zzsm2, int n2) throws IOException {
        int n3 = 1;
        int n4 = zzsm2.getPosition();
        zzsm2.zzmo(n2);
        while (zzsm2.zzIX() == n2) {
            zzsm2.zzmo(n2);
            ++n3;
        }
        zzsm2.zzms(n4);
        return n3;
    }

    static int zzmI(int n2) {
        return n2 & 7;
    }

    public static int zzmJ(int n2) {
        return n2 >>> 3;
    }
}

