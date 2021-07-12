/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.common.server.response;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse;

public abstract class FastSafeParcelableJsonResponse
extends FastJsonResponse
implements SafeParcelable {
    @Override
    public Object zzcN(String string2) {
        return null;
    }

    @Override
    public boolean zzcO(String string2) {
        return false;
    }
}

