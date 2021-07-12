/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.wearable.ChannelIOException;
import com.google.android.gms.wearable.internal.zzm;
import com.google.android.gms.wearable.internal.zzu;
import java.io.IOException;
import java.io.InputStream;

public final class zzp
extends InputStream {
    private final InputStream zzbsj;
    private volatile zzm zzbsk;

    public zzp(InputStream inputStream) {
        this.zzbsj = zzx.zzz(inputStream);
    }

    private int zzlK(int n2) throws ChannelIOException {
        zzm zzm2;
        if (n2 == -1 && (zzm2 = this.zzbsk) != null) {
            throw new ChannelIOException("Channel closed unexpectedly before stream was finished", zzm2.zzbsa, zzm2.zzbsb);
        }
        return n2;
    }

    @Override
    public int available() throws IOException {
        return this.zzbsj.available();
    }

    @Override
    public void close() throws IOException {
        this.zzbsj.close();
    }

    @Override
    public void mark(int n2) {
        this.zzbsj.mark(n2);
    }

    @Override
    public boolean markSupported() {
        return this.zzbsj.markSupported();
    }

    @Override
    public int read() throws IOException {
        return this.zzlK(this.zzbsj.read());
    }

    @Override
    public int read(byte[] byArray) throws IOException {
        return this.zzlK(this.zzbsj.read(byArray));
    }

    @Override
    public int read(byte[] byArray, int n2, int n3) throws IOException {
        return this.zzlK(this.zzbsj.read(byArray, n2, n3));
    }

    @Override
    public void reset() throws IOException {
        this.zzbsj.reset();
    }

    @Override
    public long skip(long l2) throws IOException {
        return this.zzbsj.skip(l2);
    }

    zzu zzIJ() {
        return new zzu(){

            @Override
            public void zzb(zzm zzm2) {
                zzp.this.zza(zzm2);
            }
        };
    }

    void zza(zzm zzm2) {
        this.zzbsk = zzx.zzz(zzm2);
    }
}

