/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.data;

import android.os.Bundle;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzb;
import com.google.android.gms.common.data.zzg;
import java.util.Iterator;

public abstract class AbstractDataBuffer<T>
implements DataBuffer<T> {
    protected final DataHolder zzahi;

    protected AbstractDataBuffer(DataHolder dataHolder) {
        this.zzahi = dataHolder;
        if (this.zzahi != null) {
            this.zzahi.zzu(this);
        }
    }

    @Override
    @Deprecated
    public final void close() {
        this.release();
    }

    @Override
    public abstract T get(int var1);

    @Override
    public int getCount() {
        if (this.zzahi == null) {
            return 0;
        }
        return this.zzahi.getCount();
    }

    @Override
    @Deprecated
    public boolean isClosed() {
        return this.zzahi == null || this.zzahi.isClosed();
    }

    @Override
    public Iterator<T> iterator() {
        return new zzb(this);
    }

    @Override
    public void release() {
        if (this.zzahi != null) {
            this.zzahi.close();
        }
    }

    @Override
    public Iterator<T> singleRefIterator() {
        return new zzg(this);
    }

    @Override
    public Bundle zzpZ() {
        return this.zzahi.zzpZ();
    }
}

