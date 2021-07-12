/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.common.data;

import com.google.android.gms.common.data.Freezable;
import java.util.ArrayList;

public final class FreezableUtils {
    public static <T, E extends Freezable<T>> ArrayList<T> freeze(ArrayList<E> arrayList) {
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        int n2 = arrayList.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList2.add(((Freezable)arrayList.get(i2)).freeze());
        }
        return arrayList2;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freeze(E[] EArray) {
        ArrayList<T> arrayList = new ArrayList<T>(EArray.length);
        for (int i2 = 0; i2 < EArray.length; ++i2) {
            arrayList.add(EArray[i2].freeze());
        }
        return arrayList;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freezeIterable(Iterable<E> object) {
        ArrayList arrayList = new ArrayList();
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(((Freezable)object.next()).freeze());
        }
        return arrayList;
    }
}

