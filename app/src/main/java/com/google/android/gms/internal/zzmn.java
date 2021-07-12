/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzw;
import java.util.ArrayList;

public final class zzmn {
    /*
     * Enabled aggressive block sorting
     */
    public static <T> int zza(T[] TArray, T t2) {
        int n2 = 0;
        int n3 = TArray != null ? TArray.length : 0;
        while (n2 < n3) {
            if (zzw.equal(TArray[n2], t2)) {
                return n2;
            }
            ++n2;
        }
        return -1;
    }

    public static void zza(StringBuilder stringBuilder, double[] dArray) {
        int n2 = dArray.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            if (i2 != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Double.toString(dArray[i2]));
        }
    }

    public static void zza(StringBuilder stringBuilder, float[] fArray) {
        int n2 = fArray.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            if (i2 != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Float.toString(fArray[i2]));
        }
    }

    public static void zza(StringBuilder stringBuilder, int[] nArray) {
        int n2 = nArray.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            if (i2 != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Integer.toString(nArray[i2]));
        }
    }

    public static void zza(StringBuilder stringBuilder, long[] lArray) {
        int n2 = lArray.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            if (i2 != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Long.toString(lArray[i2]));
        }
    }

    public static <T> void zza(StringBuilder stringBuilder, T[] TArray) {
        int n2 = TArray.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            if (i2 != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(TArray[i2].toString());
        }
    }

    public static void zza(StringBuilder stringBuilder, String[] stringArray) {
        int n2 = stringArray.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            if (i2 != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append("\"").append(stringArray[i2]).append("\"");
        }
    }

    public static void zza(StringBuilder stringBuilder, boolean[] blArray) {
        int n2 = blArray.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            if (i2 != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Boolean.toString(blArray[i2]));
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Integer[] zza(int[] nArray) {
        if (nArray == null) {
            return null;
        }
        int n2 = nArray.length;
        Integer[] integerArray = new Integer[n2];
        int n3 = 0;
        while (true) {
            Integer[] integerArray2 = integerArray;
            if (n3 >= n2) return integerArray2;
            integerArray[n3] = nArray[n3];
            ++n3;
        }
    }

    public static <T> ArrayList<T> zzb(T[] TArray) {
        int n2 = TArray.length;
        ArrayList<T> arrayList = new ArrayList<T>(n2);
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList.add(TArray[i2]);
        }
        return arrayList;
    }

    public static <T> boolean zzb(T[] TArray, T t2) {
        return zzmn.zza(TArray, t2) >= 0;
    }

    public static <T> ArrayList<T> zzsa() {
        return new ArrayList();
    }
}

