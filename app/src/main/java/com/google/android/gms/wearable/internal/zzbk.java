/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.WearableStatusCodes;

public final class zzbk {
    public static Status zzgc(int n2) {
        return new Status(n2, WearableStatusCodes.getStatusCodeString(n2));
    }
}

