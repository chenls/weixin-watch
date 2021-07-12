/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.database.CharArrayBuffer
 *  android.net.Uri
 */
package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzx;

public abstract class zzc {
    protected final DataHolder zzahi;
    protected int zzaje;
    private int zzajf;

    public zzc(DataHolder dataHolder, int n2) {
        this.zzahi = zzx.zzz(dataHolder);
        this.zzbF(n2);
    }

    public boolean equals(Object object) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (object instanceof zzc) {
            object = (zzc)object;
            bl3 = bl2;
            if (zzw.equal(((zzc)object).zzaje, this.zzaje)) {
                bl3 = bl2;
                if (zzw.equal(((zzc)object).zzajf, this.zzajf)) {
                    bl3 = bl2;
                    if (((zzc)object).zzahi == this.zzahi) {
                        bl3 = true;
                    }
                }
            }
        }
        return bl3;
    }

    protected boolean getBoolean(String string2) {
        return this.zzahi.zze(string2, this.zzaje, this.zzajf);
    }

    protected byte[] getByteArray(String string2) {
        return this.zzahi.zzg(string2, this.zzaje, this.zzajf);
    }

    protected float getFloat(String string2) {
        return this.zzahi.zzf(string2, this.zzaje, this.zzajf);
    }

    protected int getInteger(String string2) {
        return this.zzahi.zzc(string2, this.zzaje, this.zzajf);
    }

    protected long getLong(String string2) {
        return this.zzahi.zzb(string2, this.zzaje, this.zzajf);
    }

    protected String getString(String string2) {
        return this.zzahi.zzd(string2, this.zzaje, this.zzajf);
    }

    public int hashCode() {
        return zzw.hashCode(this.zzaje, this.zzajf, this.zzahi);
    }

    public boolean isDataValid() {
        return !this.zzahi.isClosed();
    }

    protected void zza(String string2, CharArrayBuffer charArrayBuffer) {
        this.zzahi.zza(string2, this.zzaje, this.zzajf, charArrayBuffer);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void zzbF(int n2) {
        boolean bl2 = n2 >= 0 && n2 < this.zzahi.getCount();
        zzx.zzab(bl2);
        this.zzaje = n2;
        this.zzajf = this.zzahi.zzbH(this.zzaje);
    }

    protected Uri zzcA(String string2) {
        return this.zzahi.zzh(string2, this.zzaje, this.zzajf);
    }

    protected boolean zzcB(String string2) {
        return this.zzahi.zzi(string2, this.zzaje, this.zzajf);
    }

    public boolean zzcz(String string2) {
        return this.zzahi.zzcz(string2);
    }

    protected int zzqc() {
        return this.zzaje;
    }
}

