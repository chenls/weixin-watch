/*
 * Decompiled with CFR 0.151.
 */
package com.android.volley.toolbox;

import com.android.volley.Cache;

public class NoCache
implements Cache {
    @Override
    public void clear() {
    }

    @Override
    public Cache.Entry get(String string2) {
        return null;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void invalidate(String string2, boolean bl2) {
    }

    @Override
    public void put(String string2, Cache.Entry entry) {
    }

    @Override
    public void remove(String string2) {
    }
}

