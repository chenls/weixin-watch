/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.IntentSender$SendIntentException
 *  android.util.Log
 */
package com.google.android.gms.common.api;

import android.app.Activity;
import android.content.IntentSender;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzx;

public abstract class ResolvingResultCallbacks<R extends Result>
extends ResultCallbacks<R> {
    private final Activity mActivity;
    private final int zzagz;

    protected ResolvingResultCallbacks(@NonNull Activity activity, int n2) {
        this.mActivity = zzx.zzb(activity, (Object)"Activity must not be null");
        this.zzagz = n2;
    }

    @Override
    public final void onFailure(@NonNull Status status) {
        if (status.hasResolution()) {
            try {
                status.startResolutionForResult(this.mActivity, this.zzagz);
                return;
            }
            catch (IntentSender.SendIntentException sendIntentException) {
                Log.e((String)"ResolvingResultCallback", (String)"Failed to start resolution", (Throwable)sendIntentException);
                this.onUnresolvableFailure(new Status(8));
                return;
            }
        }
        this.onUnresolvableFailure(status);
    }

    @Override
    public abstract void onSuccess(@NonNull R var1);

    public abstract void onUnresolvableFailure(@NonNull Status var1);
}

