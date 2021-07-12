/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

import android.support.v4.util.ArrayMap;
import com.google.android.gms.internal.zzmm;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class zzmr {
    public static <T> Set<T> zzA(T t2) {
        return Collections.singleton(t2);
    }

    public static <K, V> Map<K, V> zza(K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7) {
        ArrayMap<K, V> arrayMap = new ArrayMap<K, V>(6);
        arrayMap.put(k2, v2);
        arrayMap.put(k3, v3);
        arrayMap.put(k4, v4);
        arrayMap.put(k5, v5);
        arrayMap.put(k6, v6);
        arrayMap.put(k7, v7);
        return Collections.unmodifiableMap(arrayMap);
    }

    public static <T> Set<T> zza(T t2, T t3, T t4) {
        zzmm<T> zzmm2 = new zzmm<T>(3);
        zzmm2.add(t2);
        zzmm2.add(t3);
        zzmm2.add(t4);
        return Collections.unmodifiableSet(zzmm2);
    }

    public static <T> Set<T> zza(T t2, T t3, T t4, T t5) {
        zzmm<T> zzmm2 = new zzmm<T>(4);
        zzmm2.add(t2);
        zzmm2.add(t3);
        zzmm2.add(t4);
        zzmm2.add(t5);
        return Collections.unmodifiableSet(zzmm2);
    }

    public static <T> List<T> zzc(T t2, T t3) {
        ArrayList<T> arrayList = new ArrayList<T>(2);
        arrayList.add(t2);
        arrayList.add(t3);
        return Collections.unmodifiableList(arrayList);
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static <T> Set<T> zzc(T ... object) {
        void var0_2;
        switch (((T[])object).length) {
            default: {
                if (((T[])object).length > 32) break;
                zzmm<T> zzmm2 = new zzmm<T>(Arrays.asList(object));
                return Collections.unmodifiableSet(var0_2);
            }
            case 0: {
                return zzmr.zzsb();
            }
            case 1: {
                return zzmr.zzA(object[0]);
            }
            case 2: {
                return zzmr.zzd(object[0], object[1]);
            }
            case 3: {
                return zzmr.zza(object[0], object[1], object[2]);
            }
            case 4: {
                return zzmr.zza(object[0], object[1], object[2], object[3]);
            }
        }
        HashSet<T> hashSet = new HashSet<T>(Arrays.asList(object));
        return Collections.unmodifiableSet(var0_2);
    }

    public static <T> Set<T> zzd(T t2, T t3) {
        zzmm<T> zzmm2 = new zzmm<T>(2);
        zzmm2.add(t2);
        zzmm2.add(t3);
        return Collections.unmodifiableSet(zzmm2);
    }

    public static <T> Set<T> zzsb() {
        return Collections.emptySet();
    }
}

