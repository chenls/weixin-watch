/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzsm;
import com.google.android.gms.internal.zzsn;
import com.google.android.gms.internal.zzsp;
import com.google.android.gms.internal.zzsq;
import com.google.android.gms.internal.zzsr;
import com.google.android.gms.internal.zzss;
import com.google.android.gms.internal.zzsu;
import com.google.android.gms.internal.zzsw;
import com.google.android.gms.internal.zzsx;
import java.io.IOException;

public abstract class zzso<M extends zzso<M>>
extends zzsu {
    protected zzsq zzbuj;

    @Override
    public /* synthetic */ zzsu clone() throws CloneNotSupportedException {
        return this.zzJp();
    }

    @Override
    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return this.zzJp();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeTo(zzsn zzsn2) throws IOException {
        if (this.zzbuj != null) {
            for (int i2 = 0; i2 < this.zzbuj.size(); ++i2) {
                this.zzbuj.zzmG(i2).writeTo(zzsn2);
            }
        }
    }

    public M zzJp() throws CloneNotSupportedException {
        zzso zzso2 = (zzso)super.clone();
        zzss.zza(this, zzso2);
        return (M)zzso2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final <T> T zza(zzsp<M, T> zzsp2) {
        zzsr zzsr2;
        if (this.zzbuj == null || (zzsr2 = this.zzbuj.zzmF(zzsx.zzmJ(zzsp2.tag))) == null) {
            return null;
        }
        return zzsr2.zzb(zzsp2);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final boolean zza(zzsm object, int n2) throws IOException {
        int n3 = ((zzsm)object).getPosition();
        if (!((zzsm)object).zzmo(n2)) {
            return false;
        }
        int n4 = zzsx.zzmJ(n2);
        zzsw zzsw2 = new zzsw(n2, ((zzsm)object).zzz(n3, ((zzsm)object).getPosition() - n3));
        object = null;
        if (this.zzbuj == null) {
            this.zzbuj = new zzsq();
        } else {
            object = this.zzbuj.zzmF(n4);
        }
        Object object2 = object;
        if (object == null) {
            object2 = new zzsr();
            this.zzbuj.zza(n4, (zzsr)object2);
        }
        ((zzsr)object2).zza(zzsw2);
        return true;
    }

    @Override
    protected int zzz() {
        int n2;
        int n3 = 0;
        if (this.zzbuj != null) {
            int n4 = 0;
            while (true) {
                n2 = n4;
                if (n3 < this.zzbuj.size()) {
                    n4 += this.zzbuj.zzmG(n3).zzz();
                    ++n3;
                    continue;
                }
                break;
            }
        } else {
            n2 = 0;
        }
        return n2;
    }
}

