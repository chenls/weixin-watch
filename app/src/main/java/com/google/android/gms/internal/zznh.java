/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzx;
import java.util.Set;

public final class zznh {
    public static String[] zzb(Scope[] scopeArray) {
        zzx.zzb(scopeArray, (Object)"scopes can't be null.");
        String[] stringArray = new String[scopeArray.length];
        for (int i2 = 0; i2 < scopeArray.length; ++i2) {
            stringArray[i2] = scopeArray[i2].zzpb();
        }
        return stringArray;
    }

    public static String[] zzc(Set<Scope> set) {
        zzx.zzb(set, (Object)"scopes can't be null.");
        return zznh.zzb(set.toArray(new Scope[set.size()]));
    }
}

