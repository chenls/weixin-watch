/*
 * Decompiled with CFR 0.151.
 */
package com.mobvoi.android.common.data;

import com.mobvoi.android.common.data.DataBuffer;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DataBufferIterator<T>
implements Iterator<T> {
    protected final DataBuffer<T> a;
    protected int b;

    public DataBufferIterator(DataBuffer<T> dataBuffer) {
        this.a = dataBuffer;
        this.b = -1;
    }

    @Override
    public boolean hasNext() {
        return this.b < this.a.getCount() - 1;
    }

    @Override
    public T next() {
        int n2;
        if (!this.hasNext()) {
            throw new NoSuchElementException("no next element, index = " + this.b);
        }
        DataBuffer<T> dataBuffer = this.a;
        this.b = n2 = this.b + 1;
        return dataBuffer.get(n2);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("unsupported.");
    }
}

