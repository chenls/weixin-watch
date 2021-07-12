/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzsr;

public final class zzsq
implements Cloneable {
    private static final zzsr zzbum = new zzsr();
    private int mSize;
    private boolean zzbun = false;
    private int[] zzbuo;
    private zzsr[] zzbup;

    zzsq() {
        this(10);
    }

    zzsq(int n2) {
        n2 = this.idealIntArraySize(n2);
        this.zzbuo = new int[n2];
        this.zzbup = new zzsr[n2];
        this.mSize = 0;
    }

    private void gc() {
        int n2 = this.mSize;
        int[] nArray = this.zzbuo;
        zzsr[] zzsrArray = this.zzbup;
        int n3 = 0;
        for (int i2 = 0; i2 < n2; ++i2) {
            zzsr zzsr2 = zzsrArray[i2];
            int n4 = n3;
            if (zzsr2 != zzbum) {
                if (i2 != n3) {
                    nArray[n3] = nArray[i2];
                    zzsrArray[n3] = zzsr2;
                    zzsrArray[i2] = null;
                }
                n4 = n3 + 1;
            }
            n3 = n4;
        }
        this.zzbun = false;
        this.mSize = n3;
    }

    private int idealByteArraySize(int n2) {
        int n3 = 4;
        while (true) {
            block4: {
                int n4;
                block3: {
                    n4 = n2;
                    if (n3 >= 32) break block3;
                    if (n2 > (1 << n3) - 12) break block4;
                    n4 = (1 << n3) - 12;
                }
                return n4;
            }
            ++n3;
        }
    }

    private int idealIntArraySize(int n2) {
        return this.idealByteArraySize(n2 * 4) / 4;
    }

    private boolean zza(int[] nArray, int[] nArray2, int n2) {
        for (int i2 = 0; i2 < n2; ++i2) {
            if (nArray[i2] == nArray2[i2]) continue;
            return false;
        }
        return true;
    }

    private boolean zza(zzsr[] zzsrArray, zzsr[] zzsrArray2, int n2) {
        for (int i2 = 0; i2 < n2; ++i2) {
            if (zzsrArray[i2].equals(zzsrArray2[i2])) continue;
            return false;
        }
        return true;
    }

    private int zzmH(int n2) {
        int n3 = 0;
        int n4 = this.mSize - 1;
        while (n3 <= n4) {
            int n5 = n3 + n4 >>> 1;
            int n6 = this.zzbuo[n5];
            if (n6 < n2) {
                n3 = n5 + 1;
                continue;
            }
            if (n6 > n2) {
                n4 = n5 - 1;
                continue;
            }
            return n5;
        }
        return ~n3;
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return this.zzJq();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block6: {
            block5: {
                if (object == this) break block5;
                if (!(object instanceof zzsq)) {
                    return false;
                }
                object = (zzsq)object;
                if (this.size() != ((zzsq)object).size()) {
                    return false;
                }
                if (!this.zza(this.zzbuo, ((zzsq)object).zzbuo, this.mSize) || !this.zza(this.zzbup, ((zzsq)object).zzbup, this.mSize)) break block6;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.zzbun) {
            this.gc();
        }
        int n2 = 17;
        for (int i2 = 0; i2 < this.mSize; ++i2) {
            n2 = (n2 * 31 + this.zzbuo[i2]) * 31 + this.zzbup[i2].hashCode();
        }
        return n2;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    int size() {
        if (this.zzbun) {
            this.gc();
        }
        return this.mSize;
    }

    public final zzsq zzJq() {
        int n2 = this.size();
        zzsq zzsq2 = new zzsq(n2);
        System.arraycopy(this.zzbuo, 0, zzsq2.zzbuo, 0, n2);
        for (int i2 = 0; i2 < n2; ++i2) {
            if (this.zzbup[i2] == null) continue;
            zzsq2.zzbup[i2] = this.zzbup[i2].zzJr();
        }
        zzsq2.mSize = n2;
        return zzsq2;
    }

    void zza(int n2, zzsr zzsr2) {
        int n3 = this.zzmH(n2);
        if (n3 >= 0) {
            this.zzbup[n3] = zzsr2;
            return;
        }
        int n4 = ~n3;
        if (n4 < this.mSize && this.zzbup[n4] == zzbum) {
            this.zzbuo[n4] = n2;
            this.zzbup[n4] = zzsr2;
            return;
        }
        n3 = n4;
        if (this.zzbun) {
            n3 = n4;
            if (this.mSize >= this.zzbuo.length) {
                this.gc();
                n3 = ~this.zzmH(n2);
            }
        }
        if (this.mSize >= this.zzbuo.length) {
            n4 = this.idealIntArraySize(this.mSize + 1);
            int[] nArray = new int[n4];
            zzsr[] zzsrArray = new zzsr[n4];
            System.arraycopy(this.zzbuo, 0, nArray, 0, this.zzbuo.length);
            System.arraycopy(this.zzbup, 0, zzsrArray, 0, this.zzbup.length);
            this.zzbuo = nArray;
            this.zzbup = zzsrArray;
        }
        if (this.mSize - n3 != 0) {
            System.arraycopy(this.zzbuo, n3, this.zzbuo, n3 + 1, this.mSize - n3);
            System.arraycopy(this.zzbup, n3, this.zzbup, n3 + 1, this.mSize - n3);
        }
        this.zzbuo[n3] = n2;
        this.zzbup[n3] = zzsr2;
        ++this.mSize;
    }

    zzsr zzmF(int n2) {
        if ((n2 = this.zzmH(n2)) < 0 || this.zzbup[n2] == zzbum) {
            return null;
        }
        return this.zzbup[n2];
    }

    zzsr zzmG(int n2) {
        if (this.zzbun) {
            this.gc();
        }
        return this.zzbup[n2];
    }
}

