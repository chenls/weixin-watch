/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.wearable.internal;

import android.util.Log;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.wearable.ChannelIOException;
import com.google.android.gms.wearable.internal.zzm;
import com.google.android.gms.wearable.internal.zzu;
import java.io.IOException;
import java.io.OutputStream;

public final class zzq
extends OutputStream {
    private volatile zzm zzbsk;
    private final OutputStream zzbsm;

    public zzq(OutputStream outputStream) {
        this.zzbsm = zzx.zzz(outputStream);
    }

    private IOException zza(IOException iOException) {
        zzm zzm2 = this.zzbsk;
        IOException iOException2 = iOException;
        if (zzm2 != null) {
            if (Log.isLoggable((String)"ChannelOutputStream", (int)2)) {
                Log.v((String)"ChannelOutputStream", (String)"Caught IOException, but channel has been closed. Translating to ChannelIOException.", (Throwable)iOException);
            }
            iOException2 = new ChannelIOException("Channel closed unexpectedly before stream was finished", zzm2.zzbsa, zzm2.zzbsb);
        }
        return iOException2;
    }

    @Override
    public void close() throws IOException {
        try {
            this.zzbsm.close();
            return;
        }
        catch (IOException iOException) {
            throw this.zza(iOException);
        }
    }

    @Override
    public void flush() throws IOException {
        try {
            this.zzbsm.flush();
            return;
        }
        catch (IOException iOException) {
            throw this.zza(iOException);
        }
    }

    @Override
    public void write(int n2) throws IOException {
        try {
            this.zzbsm.write(n2);
            return;
        }
        catch (IOException iOException) {
            throw this.zza(iOException);
        }
    }

    @Override
    public void write(byte[] byArray) throws IOException {
        try {
            this.zzbsm.write(byArray);
            return;
        }
        catch (IOException iOException) {
            throw this.zza(iOException);
        }
    }

    @Override
    public void write(byte[] byArray, int n2, int n3) throws IOException {
        try {
            this.zzbsm.write(byArray, n2, n3);
            return;
        }
        catch (IOException iOException) {
            throw this.zza(iOException);
        }
    }

    zzu zzIJ() {
        return new zzu(){

            @Override
            public void zzb(zzm zzm2) {
                zzq.this.zzc(zzm2);
            }
        };
    }

    void zzc(zzm zzm2) {
        this.zzbsk = zzm2;
    }
}

