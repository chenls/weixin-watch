/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.common.data;

import com.google.android.gms.common.data.DataBufferObserver;
import java.util.HashSet;
import java.util.Iterator;

public final class DataBufferObserverSet
implements DataBufferObserver,
DataBufferObserver.Observable {
    private HashSet<DataBufferObserver> zzajd = new HashSet();

    @Override
    public void addObserver(DataBufferObserver dataBufferObserver) {
        this.zzajd.add(dataBufferObserver);
    }

    public void clear() {
        this.zzajd.clear();
    }

    public boolean hasObservers() {
        return !this.zzajd.isEmpty();
    }

    @Override
    public void onDataChanged() {
        Iterator<DataBufferObserver> iterator = this.zzajd.iterator();
        while (iterator.hasNext()) {
            iterator.next().onDataChanged();
        }
    }

    @Override
    public void onDataRangeChanged(int n2, int n3) {
        Iterator<DataBufferObserver> iterator = this.zzajd.iterator();
        while (iterator.hasNext()) {
            iterator.next().onDataRangeChanged(n2, n3);
        }
    }

    @Override
    public void onDataRangeInserted(int n2, int n3) {
        Iterator<DataBufferObserver> iterator = this.zzajd.iterator();
        while (iterator.hasNext()) {
            iterator.next().onDataRangeInserted(n2, n3);
        }
    }

    @Override
    public void onDataRangeMoved(int n2, int n3, int n4) {
        Iterator<DataBufferObserver> iterator = this.zzajd.iterator();
        while (iterator.hasNext()) {
            iterator.next().onDataRangeMoved(n2, n3, n4);
        }
    }

    @Override
    public void onDataRangeRemoved(int n2, int n3) {
        Iterator<DataBufferObserver> iterator = this.zzajd.iterator();
        while (iterator.hasNext()) {
            iterator.next().onDataRangeRemoved(n2, n3);
        }
    }

    @Override
    public void removeObserver(DataBufferObserver dataBufferObserver) {
        this.zzajd.remove(dataBufferObserver);
    }
}

