/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 */
package com.google.android.gms.common.api.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.internal.zzx;

public final class zzq<L> {
    private volatile L mListener;
    private final zza zzaiw;

    zzq(Looper looper, L l2) {
        this.zzaiw = new zza(looper);
        this.mListener = zzx.zzb(l2, (Object)"Listener must not be null");
    }

    public void clear() {
        this.mListener = null;
    }

    public void zza(zzb<? super L> message) {
        zzx.zzb(message, (Object)"Notifier must not be null");
        message = this.zzaiw.obtainMessage(1, message);
        this.zzaiw.sendMessage(message);
    }

    void zzb(zzb<? super L> zzb2) {
        L l2 = this.mListener;
        if (l2 == null) {
            zzb2.zzpr();
            return;
        }
        try {
            zzb2.zzt(l2);
            return;
        }
        catch (RuntimeException runtimeException) {
            zzb2.zzpr();
            throw runtimeException;
        }
    }

    private final class zza
    extends Handler {
        public zza(Looper looper) {
            super(looper);
        }

        /*
         * Enabled aggressive block sorting
         */
        public void handleMessage(Message message) {
            boolean bl2 = true;
            if (message.what != 1) {
                bl2 = false;
            }
            zzx.zzac(bl2);
            zzq.this.zzb((zzb)message.obj);
        }
    }

    public static interface zzb<L> {
        public void zzpr();

        public void zzt(L var1);
    }
}

