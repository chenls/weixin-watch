/*
 * Decompiled with CFR 0.151.
 */
package com.mobvoi.android.common.api;

import com.mobvoi.android.common.api.Result;
import com.mobvoi.android.common.api.ResultCallback;
import java.util.concurrent.TimeUnit;

public interface PendingResult<R extends Result> {
    public R await();

    public R await(long var1, TimeUnit var3);

    public boolean isCanceled();

    public void setResultCallback(ResultCallback<R> var1);

    public void setResultCallback(ResultCallback<R> var1, long var2, TimeUnit var4);
}

