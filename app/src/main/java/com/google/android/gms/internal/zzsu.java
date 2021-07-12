/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzsm;
import com.google.android.gms.internal.zzsn;
import com.google.android.gms.internal.zzst;
import com.google.android.gms.internal.zzsv;
import java.io.IOException;
import java.util.Arrays;

public abstract class zzsu {
    protected volatile int zzbuu = -1;

    public static final <T extends zzsu> T mergeFrom(T t2, byte[] byArray) throws zzst {
        return zzsu.mergeFrom(t2, byArray, 0, byArray.length);
    }

    public static final <T extends zzsu> T mergeFrom(T t2, byte[] object, int n2, int n3) throws zzst {
        try {
            object = zzsm.zza((byte[])object, n2, n3);
            t2.mergeFrom((zzsm)object);
            ((zzsm)object).zzmn(0);
        }
        catch (zzst zzst2) {
            throw zzst2;
        }
        catch (IOException iOException) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).");
        }
        return t2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static final boolean messageNanoEquals(zzsu zzsu2, zzsu zzsu3) {
        boolean bl2 = false;
        if (zzsu2 == zzsu3) {
            return true;
        }
        boolean bl3 = bl2;
        if (zzsu2 == null) return bl3;
        bl3 = bl2;
        if (zzsu3 == null) return bl3;
        bl3 = bl2;
        if (zzsu2.getClass() != zzsu3.getClass()) return bl3;
        int n2 = zzsu2.getSerializedSize();
        bl3 = bl2;
        if (zzsu3.getSerializedSize() != n2) return bl3;
        byte[] byArray = new byte[n2];
        byte[] byArray2 = new byte[n2];
        zzsu.toByteArray(zzsu2, byArray, 0, n2);
        zzsu.toByteArray(zzsu3, byArray2, 0, n2);
        return Arrays.equals(byArray, byArray2);
    }

    public static final void toByteArray(zzsu zzsu2, byte[] object, int n2, int n3) {
        try {
            object = zzsn.zzb((byte[])object, n2, n3);
            zzsu2.writeTo((zzsn)object);
            ((zzsn)object).zzJo();
            return;
        }
        catch (IOException iOException) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", iOException);
        }
    }

    public static final byte[] toByteArray(zzsu zzsu2) {
        byte[] byArray = new byte[zzsu2.getSerializedSize()];
        zzsu.toByteArray(zzsu2, byArray, 0, byArray.length);
        return byArray;
    }

    public zzsu clone() throws CloneNotSupportedException {
        return (zzsu)super.clone();
    }

    public int getCachedSize() {
        if (this.zzbuu < 0) {
            this.getSerializedSize();
        }
        return this.zzbuu;
    }

    public int getSerializedSize() {
        int n2;
        this.zzbuu = n2 = this.zzz();
        return n2;
    }

    public abstract zzsu mergeFrom(zzsm var1) throws IOException;

    public String toString() {
        return zzsv.zzf(this);
    }

    public void writeTo(zzsn zzsn2) throws IOException {
    }

    protected int zzz() {
        return 0;
    }
}

