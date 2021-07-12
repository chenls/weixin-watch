/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.common.data;

import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import java.util.ArrayList;

public abstract class zzf<T>
extends AbstractDataBuffer<T> {
    private boolean zzajw = false;
    private ArrayList<Integer> zzajx;

    protected zzf(DataHolder dataHolder) {
        super(dataHolder);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzqh() {
        synchronized (this) {
            if (!this.zzajw) {
                int n2 = this.zzahi.getCount();
                this.zzajx = new ArrayList();
                if (n2 > 0) {
                    this.zzajx.add(0);
                    String string2 = this.zzqg();
                    int n3 = this.zzahi.zzbH(0);
                    String string3 = this.zzahi.zzd(string2, 0, n3);
                    for (n3 = 1; n3 < n2; ++n3) {
                        int n4 = this.zzahi.zzbH(n3);
                        String string4 = this.zzahi.zzd(string2, n3, n4);
                        if (string4 == null) {
                            throw new NullPointerException("Missing value for markerColumn: " + string2 + ", at row: " + n3 + ", for window: " + n4);
                        }
                        if (string4.equals(string3)) continue;
                        this.zzajx.add(n3);
                        string3 = string4;
                    }
                }
                this.zzajw = true;
            }
            return;
        }
    }

    @Override
    public final T get(int n2) {
        this.zzqh();
        return this.zzk(this.zzbK(n2), this.zzbL(n2));
    }

    @Override
    public int getCount() {
        this.zzqh();
        return this.zzajx.size();
    }

    int zzbK(int n2) {
        if (n2 < 0 || n2 >= this.zzajx.size()) {
            throw new IllegalArgumentException("Position " + n2 + " is out of bounds for this buffer");
        }
        return this.zzajx.get(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected int zzbL(int n2) {
        if (n2 < 0) return 0;
        if (n2 == this.zzajx.size()) {
            return 0;
        }
        int n3 = n2 == this.zzajx.size() - 1 ? this.zzahi.getCount() - this.zzajx.get(n2) : this.zzajx.get(n2 + 1) - this.zzajx.get(n2);
        int n4 = n3;
        if (n3 != 1) return n4;
        n2 = this.zzbK(n2);
        int n5 = this.zzahi.zzbH(n2);
        String string2 = this.zzqi();
        n4 = n3;
        if (string2 == null) return n4;
        n4 = n3;
        if (this.zzahi.zzd(string2, n2, n5) != null) return n4;
        return 0;
    }

    protected abstract T zzk(int var1, int var2);

    protected abstract String zzqg();

    protected String zzqi() {
        return null;
    }
}

