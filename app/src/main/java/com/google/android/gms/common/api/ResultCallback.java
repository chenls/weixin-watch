/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.common.api;

import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Result;

public interface ResultCallback<R extends Result> {
    public void onResult(@NonNull R var1);
}

