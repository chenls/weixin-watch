/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.internal.zzbp;

abstract class zzi<R extends Result>
extends zza.zza<R, zzbp> {
    public zzi(GoogleApiClient googleApiClient) {
        super(Wearable.zzUI, googleApiClient);
    }
}

