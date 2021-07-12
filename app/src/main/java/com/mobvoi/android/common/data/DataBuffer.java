/*
 * Decompiled with CFR 0.151.
 */
package com.mobvoi.android.common.data;

import com.mobvoi.android.common.api.Releasable;
import com.mobvoi.android.common.data.DataBufferIterator;
import com.mobvoi.android.wearable.internal.DataHolder;
import java.util.Iterator;
import java.util.List;

public class DataBuffer<T>
implements Releasable,
Iterable<T> {
    public List<? extends T> a;
    protected DataHolder b;

    public DataBuffer() {
    }

    public DataBuffer(DataHolder dataHolder) {
        this.b = dataHolder;
    }

    @Deprecated
    public void close() {
        this.release();
    }

    public int describeContents() {
        return 0;
    }

    public T get(int n2) {
        return this.a.get(n2);
    }

    public int getCount() {
        return this.a.size();
    }

    @Deprecated
    public boolean isClosed() {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new DataBufferIterator(this);
    }

    @Override
    public void release() {
    }

    public Iterator<T> singleRefIterator() {
        return new DataBufferIterator(this);
    }
}

