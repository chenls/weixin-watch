/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.common.data;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.internal.zzx;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class zzb<T>
implements Iterator<T> {
    protected final DataBuffer<T> zzajb;
    protected int zzajc;

    public zzb(DataBuffer<T> dataBuffer) {
        this.zzajb = zzx.zzz(dataBuffer);
        this.zzajc = -1;
    }

    @Override
    public boolean hasNext() {
        return this.zzajc < this.zzajb.getCount() - 1;
    }

    @Override
    public T next() {
        int n2;
        if (!this.hasNext()) {
            throw new NoSuchElementException("Cannot advance the iterator beyond " + this.zzajc);
        }
        DataBuffer<T> dataBuffer = this.zzajb;
        this.zzajc = n2 = this.zzajc + 1;
        return dataBuffer.get(n2);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
    }
}

