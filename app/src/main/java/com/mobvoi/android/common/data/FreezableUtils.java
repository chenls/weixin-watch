/*
 * Decompiled with CFR 0.151.
 */
package com.mobvoi.android.common.data;

import com.mobvoi.android.common.data.Freezable;
import java.util.ArrayList;

public class FreezableUtils {
    public static <T, E extends Freezable<T>> ArrayList<T> freeze(ArrayList<E> object) {
        ArrayList arrayList = new ArrayList(((ArrayList)object).size());
        object = ((ArrayList)object).iterator();
        while (object.hasNext()) {
            arrayList.add(((Freezable)object.next()).freeze());
        }
        return arrayList;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freeze(E[] EArray) {
        ArrayList<T> arrayList = new ArrayList<T>(EArray.length);
        int n2 = EArray.length;
        for (int i2 = 0; i2 < n2; ++i2) {
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

