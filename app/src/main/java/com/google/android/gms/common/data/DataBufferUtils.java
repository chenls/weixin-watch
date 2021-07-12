/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.common.data;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.Freezable;
import java.util.ArrayList;
import java.util.Iterator;

public final class DataBufferUtils {
    private DataBufferUtils() {
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freezeAndClose(DataBuffer<E> dataBuffer) {
        ArrayList arrayList = new ArrayList(dataBuffer.getCount());
        try {
            Iterator<E> iterator = dataBuffer.iterator();
            while (iterator.hasNext()) {
                arrayList.add(((Freezable)iterator.next()).freeze());
            }
        }
        finally {
            dataBuffer.close();
        }
        return arrayList;
    }

    public static boolean hasData(DataBuffer<?> dataBuffer) {
        return dataBuffer != null && dataBuffer.getCount() > 0;
    }

    public static boolean hasNextPage(DataBuffer<?> bundle) {
        return (bundle = bundle.zzpZ()) != null && bundle.getString("next_page_token") != null;
    }

    public static boolean hasPrevPage(DataBuffer<?> bundle) {
        return (bundle = bundle.zzpZ()) != null && bundle.getString("prev_page_token") != null;
    }
}

