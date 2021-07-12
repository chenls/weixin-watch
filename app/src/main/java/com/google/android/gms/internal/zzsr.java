/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzsn;
import com.google.android.gms.internal.zzsp;
import com.google.android.gms.internal.zzsu;
import com.google.android.gms.internal.zzsw;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class zzsr
implements Cloneable {
    private zzsp<?, ?> zzbuq;
    private Object zzbur;
    private List<zzsw> zzbus = new ArrayList<zzsw>();

    zzsr() {
    }

    private byte[] toByteArray() throws IOException {
        byte[] byArray = new byte[this.zzz()];
        this.writeTo(zzsn.zzE(byArray));
        return byArray;
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return this.zzJr();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object == this) {
            return true;
        }
        boolean bl3 = bl2;
        if (!(object instanceof zzsr)) return bl3;
        object = (zzsr)object;
        if (this.zzbur != null && ((zzsr)object).zzbur != null) {
            bl3 = bl2;
            if (this.zzbuq != ((zzsr)object).zzbuq) return bl3;
            if (!this.zzbuq.zzbuk.isArray()) {
                return this.zzbur.equals(((zzsr)object).zzbur);
            }
            if (this.zzbur instanceof byte[]) {
                return Arrays.equals((byte[])this.zzbur, (byte[])((zzsr)object).zzbur);
            }
            if (this.zzbur instanceof int[]) {
                return Arrays.equals((int[])this.zzbur, (int[])((zzsr)object).zzbur);
            }
            if (this.zzbur instanceof long[]) {
                return Arrays.equals((long[])this.zzbur, (long[])((zzsr)object).zzbur);
            }
            if (this.zzbur instanceof float[]) {
                return Arrays.equals((float[])this.zzbur, (float[])((zzsr)object).zzbur);
            }
            if (this.zzbur instanceof double[]) {
                return Arrays.equals((double[])this.zzbur, (double[])((zzsr)object).zzbur);
            }
            if (!(this.zzbur instanceof boolean[])) return Arrays.deepEquals((Object[])this.zzbur, (Object[])((zzsr)object).zzbur);
            return Arrays.equals((boolean[])this.zzbur, (boolean[])((zzsr)object).zzbur);
        }
        if (this.zzbus != null && ((zzsr)object).zzbus != null) {
            return this.zzbus.equals(((zzsr)object).zzbus);
        }
        try {
            return Arrays.equals(this.toByteArray(), super.toByteArray());
        }
        catch (IOException iOException) {
            throw new IllegalStateException(iOException);
        }
    }

    public int hashCode() {
        try {
            int n2 = Arrays.hashCode(this.toByteArray());
            return n2 + 527;
        }
        catch (IOException iOException) {
            throw new IllegalStateException(iOException);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void writeTo(zzsn zzsn2) throws IOException {
        if (this.zzbur != null) {
            this.zzbuq.zza(this.zzbur, zzsn2);
            return;
        } else {
            Iterator<zzsw> iterator = this.zzbus.iterator();
            while (iterator.hasNext()) {
                iterator.next().writeTo(zzsn2);
            }
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final zzsr zzJr() {
        int n2 = 0;
        zzsr zzsr2 = new zzsr();
        try {
            zzsr2.zzbuq = this.zzbuq;
            if (this.zzbus == null) {
                zzsr2.zzbus = null;
            } else {
                zzsr2.zzbus.addAll(this.zzbus);
            }
            if (this.zzbur == null) {
                return zzsr2;
            }
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError((Object)cloneNotSupportedException);
        }
        {
            zzsu[] zzsuArray;
            if (this.zzbur instanceof zzsu) {
                zzsr2.zzbur = ((zzsu)this.zzbur).clone();
                return zzsr2;
            }
            if (this.zzbur instanceof byte[]) {
                zzsr2.zzbur = ((byte[])this.zzbur).clone();
                return zzsr2;
            }
            if (this.zzbur instanceof byte[][]) {
                byte[][] byArray = (byte[][])this.zzbur;
                byte[][] byArrayArray = new byte[byArray.length][];
                zzsr2.zzbur = byArrayArray;
                for (n2 = 0; n2 < byArray.length; ++n2) {
                    byArrayArray[n2] = (byte[])byArray[n2].clone();
                }
                return zzsr2;
            }
            if (this.zzbur instanceof boolean[]) {
                zzsr2.zzbur = ((boolean[])this.zzbur).clone();
                return zzsr2;
            }
            if (this.zzbur instanceof int[]) {
                zzsr2.zzbur = ((int[])this.zzbur).clone();
                return zzsr2;
            }
            if (this.zzbur instanceof long[]) {
                zzsr2.zzbur = ((long[])this.zzbur).clone();
                return zzsr2;
            }
            if (this.zzbur instanceof float[]) {
                zzsr2.zzbur = ((float[])this.zzbur).clone();
                return zzsr2;
            }
            if (this.zzbur instanceof double[]) {
                zzsr2.zzbur = ((double[])this.zzbur).clone();
                return zzsr2;
            }
            if (!(this.zzbur instanceof zzsu[])) return zzsr2;
            zzsu[] zzsuArray2 = (zzsu[])this.zzbur;
            zzsr2.zzbur = zzsuArray = new zzsu[zzsuArray2.length];
            while (n2 < zzsuArray2.length) {
                zzsuArray[n2] = zzsuArray2[n2].clone();
                ++n2;
            }
            return zzsr2;
        }
    }

    void zza(zzsw zzsw2) {
        this.zzbus.add(zzsw2);
    }

    <T> T zzb(zzsp<?, T> zzsp2) {
        if (this.zzbur != null) {
            if (this.zzbuq != zzsp2) {
                throw new IllegalStateException("Tried to getExtension with a differernt Extension.");
            }
        } else {
            this.zzbuq = zzsp2;
            this.zzbur = zzsp2.zzJ(this.zzbus);
            this.zzbus = null;
        }
        return (T)this.zzbur;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    int zzz() {
        if (this.zzbur != null) {
            return this.zzbuq.zzY(this.zzbur);
        }
        Iterator<zzsw> iterator = this.zzbus.iterator();
        int n2 = 0;
        while (true) {
            int n3 = n2;
            if (!iterator.hasNext()) return n3;
            n2 = iterator.next().zzz() + n2;
        }
    }
}

