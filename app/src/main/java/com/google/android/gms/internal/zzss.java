/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzso;
import java.util.Arrays;

public final class zzss {
    public static final Object zzbut = new Object();

    public static boolean equals(float[] fArray, float[] fArray2) {
        if (fArray == null || fArray.length == 0) {
            return fArray2 == null || fArray2.length == 0;
        }
        return Arrays.equals(fArray, fArray2);
    }

    public static boolean equals(int[] nArray, int[] nArray2) {
        if (nArray == null || nArray.length == 0) {
            return nArray2 == null || nArray2.length == 0;
        }
        return Arrays.equals(nArray, nArray2);
    }

    public static boolean equals(long[] lArray, long[] lArray2) {
        if (lArray == null || lArray.length == 0) {
            return lArray2 == null || lArray2.length == 0;
        }
        return Arrays.equals(lArray, lArray2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean equals(Object[] objectArray, Object[] objectArray2) {
        boolean bl2 = false;
        int n2 = objectArray == null ? 0 : objectArray.length;
        int n3 = objectArray2 == null ? 0 : objectArray2.length;
        int n4 = 0;
        int n5 = 0;
        while (true) {
            if (n5 < n2 && objectArray[n5] == null) {
                ++n5;
                continue;
            }
            while (n4 < n3 && objectArray2[n4] == null) {
                ++n4;
            }
            boolean bl3 = n5 >= n2;
            boolean bl4 = n4 >= n3;
            if (bl3 && bl4) {
                return true;
            }
            boolean bl5 = bl2;
            if (bl3 != bl4) return bl5;
            bl5 = bl2;
            if (!objectArray[n5].equals(objectArray2[n4])) return bl5;
            ++n4;
            ++n5;
        }
    }

    public static int hashCode(float[] fArray) {
        if (fArray == null || fArray.length == 0) {
            return 0;
        }
        return Arrays.hashCode(fArray);
    }

    public static int hashCode(int[] nArray) {
        if (nArray == null || nArray.length == 0) {
            return 0;
        }
        return Arrays.hashCode(nArray);
    }

    public static int hashCode(long[] lArray) {
        if (lArray == null || lArray.length == 0) {
            return 0;
        }
        return Arrays.hashCode(lArray);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int hashCode(Object[] objectArray) {
        int n2 = 0;
        int n3 = objectArray == null ? 0 : objectArray.length;
        int n4 = 0;
        while (n4 < n3) {
            Object object = objectArray[n4];
            int n5 = n2;
            if (object != null) {
                n5 = n2 * 31 + object.hashCode();
            }
            ++n4;
            n2 = n5;
        }
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int zza(byte[][] byArray) {
        int n2 = 0;
        int n3 = byArray == null ? 0 : byArray.length;
        int n4 = 0;
        while (n4 < n3) {
            byte[] byArray2 = byArray[n4];
            int n5 = n2;
            if (byArray2 != null) {
                n5 = n2 * 31 + Arrays.hashCode(byArray2);
            }
            ++n4;
            n2 = n5;
        }
        return n2;
    }

    public static void zza(zzso zzso2, zzso zzso3) {
        if (zzso2.zzbuj != null) {
            zzso3.zzbuj = zzso2.zzbuj.zzJq();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean zza(byte[][] byArray, byte[][] byArray2) {
        boolean bl2 = false;
        int n2 = byArray == null ? 0 : byArray.length;
        int n3 = byArray2 == null ? 0 : byArray2.length;
        int n4 = 0;
        int n5 = 0;
        while (true) {
            if (n5 < n2 && byArray[n5] == null) {
                ++n5;
                continue;
            }
            while (n4 < n3 && byArray2[n4] == null) {
                ++n4;
            }
            boolean bl3 = n5 >= n2;
            boolean bl4 = n4 >= n3;
            if (bl3 && bl4) {
                return true;
            }
            boolean bl5 = bl2;
            if (bl3 != bl4) return bl5;
            bl5 = bl2;
            if (!Arrays.equals(byArray[n5], byArray2[n4])) return bl5;
            ++n4;
            ++n5;
        }
    }
}

