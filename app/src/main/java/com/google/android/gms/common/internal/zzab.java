/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 *  android.view.View
 */
package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.SignInButtonConfig;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzg;

public final class zzab
extends zzg<zzu> {
    private static final zzab zzamw = new zzab();

    private zzab() {
        super("com.google.android.gms.common.ui.SignInButtonCreatorImpl");
    }

    public static View zzb(Context context, int n2, int n3, Scope[] scopeArray) throws zzg.zza {
        return zzamw.zzc(context, n2, n3, scopeArray);
    }

    private View zzc(Context context, int n2, int n3, Scope[] object) throws zzg.zza {
        try {
            object = new SignInButtonConfig(n2, n3, (Scope[])object);
            zzd zzd2 = zze.zzC(context);
            context = (View)zze.zzp(((zzu)this.zzaB(context)).zza(zzd2, (SignInButtonConfig)object));
            return context;
        }
        catch (Exception exception) {
            throw new zzg.zza("Could not get button with size " + n2 + " and color " + n3, exception);
        }
    }

    public zzu zzaV(IBinder iBinder) {
        return zzu.zza.zzaU(iBinder);
    }

    @Override
    public /* synthetic */ Object zzd(IBinder iBinder) {
        return this.zzaV(iBinder);
    }
}

