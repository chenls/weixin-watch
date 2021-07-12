/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.SimpleArrayMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;

public class zzmm<E>
extends AbstractSet<E> {
    private final ArrayMap<E, E> zzanZ;

    public zzmm() {
        this.zzanZ = new ArrayMap();
    }

    public zzmm(int n2) {
        this.zzanZ = new ArrayMap(n2);
    }

    public zzmm(Collection<E> collection) {
        this(collection.size());
        this.addAll(collection);
    }

    @Override
    public boolean add(E e2) {
        if (this.zzanZ.containsKey(e2)) {
            return false;
        }
        this.zzanZ.put(e2, e2);
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        if (collection instanceof zzmm) {
            return this.zza((zzmm)collection);
        }
        return super.addAll(collection);
    }

    @Override
    public void clear() {
        this.zzanZ.clear();
    }

    @Override
    public boolean contains(Object object) {
        return this.zzanZ.containsKey(object);
    }

    @Override
    public Iterator<E> iterator() {
        return this.zzanZ.keySet().iterator();
    }

    @Override
    public boolean remove(Object object) {
        if (!this.zzanZ.containsKey(object)) {
            return false;
        }
        this.zzanZ.remove(object);
        return true;
    }

    @Override
    public int size() {
        return this.zzanZ.size();
    }

    public boolean zza(zzmm<? extends E> zzmm2) {
        int n2 = this.size();
        ((SimpleArrayMap)this.zzanZ).putAll((SimpleArrayMap<E, E>)zzmm2.zzanZ);
        return this.size() > n2;
    }
}

