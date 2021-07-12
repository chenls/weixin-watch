/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.database.CharArrayBuffer
 *  android.database.CursorIndexOutOfBoundsException
 *  android.database.CursorWindow
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.util.Log
 */
package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.data.zze;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@KeepName
public final class DataHolder
implements SafeParcelable {
    public static final zze CREATOR = new zze();
    private static final zza zzajq = new zza(new String[0], null){};
    boolean mClosed = false;
    private final int mVersionCode;
    private final int zzade;
    private final String[] zzaji;
    Bundle zzajj;
    private final CursorWindow[] zzajk;
    private final Bundle zzajl;
    int[] zzajm;
    int zzajn;
    private Object zzajo;
    private boolean zzajp = true;

    DataHolder(int n2, String[] stringArray, CursorWindow[] cursorWindowArray, int n3, Bundle bundle) {
        this.mVersionCode = n2;
        this.zzaji = stringArray;
        this.zzajk = cursorWindowArray;
        this.zzade = n3;
        this.zzajl = bundle;
    }

    private DataHolder(zza zza2, int n2, Bundle bundle) {
        this(zza2.zzaji, DataHolder.zza(zza2, -1), n2, bundle);
    }

    public DataHolder(String[] stringArray, CursorWindow[] cursorWindowArray, int n2, Bundle bundle) {
        this.mVersionCode = 1;
        this.zzaji = zzx.zzz(stringArray);
        this.zzajk = zzx.zzz(cursorWindowArray);
        this.zzade = n2;
        this.zzajl = bundle;
        this.zzqd();
    }

    public static DataHolder zza(int n2, Bundle bundle) {
        return new DataHolder(zzajq, n2, bundle);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static CursorWindow[] zza(zza var0, int var1_2) {
        var4_3 = 0;
        if (zza.zza((zza)var0).length == 0) {
            return new CursorWindow[0];
        }
        var11_4 = var1_2 < 0 || var1_2 >= zza.zzb((zza)var0).size() ? zza.zzb((zza)var0) : zza.zzb((zza)var0).subList(0, var1_2);
        var5_5 = var11_4.size();
        var10_6 = new CursorWindow(false);
        var12_7 = new ArrayList<Object>();
        var12_7.add(var10_6);
        var10_6.setNumColumns(zza.zza((zza)var0).length);
        var1_2 = 0;
        var2_8 = 0;
        block4: while (true) {
            block22: {
                block21: {
                    if (var1_2 >= var5_5) {
                        return var12_7.toArray(new CursorWindow[var12_7.size()]);
                    }
                    var9_12 /* !! */  = var10_6;
                    try {
                        if (!var10_6.allocRow()) {
                            Log.d((String)"DataHolder", (String)("Allocating additional cursor window for large data set (row " + var1_2 + ")"));
                            var10_6 = new CursorWindow(false);
                            var10_6.setStartPosition(var1_2);
                            var10_6.setNumColumns(zza.zza((zza)var0).length);
                            var12_7.add(var10_6);
                            var9_12 /* !! */  = var10_6;
                            if (!var10_6.allocRow()) {
                                Log.e((String)"DataHolder", (String)"Unable to allocate row to hold data.");
                                var12_7.remove(var10_6);
                                return var12_7.toArray(new CursorWindow[var12_7.size()]);
                            }
                        }
                        var10_6 = (Map)var11_4.get(var1_2);
                        var3_9 = 0;
                        var6_10 = true;
lbl38:
                        // 2 sources

                        while (true) {
                            if (var3_9 < zza.zza((zza)var0).length && var6_10) {
                                var13_13 = zza.zza((zza)var0)[var3_9];
                                var14_14 = var10_6.get(var13_13);
                                if (var14_14 == null) {
                                    var6_10 = var9_12 /* !! */ .putNull(var1_2, var3_9);
                                    break block4;
                                }
                                if (var14_14 instanceof String) {
                                    var6_10 = var9_12 /* !! */ .putString((String)var14_14, var1_2, var3_9);
                                    break block4;
                                }
                                if (var14_14 instanceof Long) {
                                    var6_10 = var9_12 /* !! */ .putLong(((Long)var14_14).longValue(), var1_2, var3_9);
                                    break block4;
                                }
                                if (var14_14 instanceof Integer) {
                                    var6_10 = var9_12 /* !! */ .putLong((long)((Integer)var14_14).intValue(), var1_2, var3_9);
                                    break block4;
                                }
                                if (var14_14 instanceof Boolean) {
                                    var7_11 = (Boolean)var14_14 != false ? 1L : 0L;
                                    var6_10 = var9_12 /* !! */ .putLong(var7_11, var1_2, var3_9);
                                    break block4;
                                }
                                if (var14_14 instanceof byte[]) {
                                    var6_10 = var9_12 /* !! */ .putBlob((byte[])var14_14, var1_2, var3_9);
                                    break block4;
                                }
                                if (var14_14 instanceof Double) {
                                    var6_10 = var9_12 /* !! */ .putDouble(((Double)var14_14).doubleValue(), var1_2, var3_9);
                                    break block4;
                                }
                                if (!(var14_14 instanceof Float)) {
                                    throw new IllegalArgumentException("Unsupported object for column " + var13_13 + ": " + var14_14);
                                }
                                var6_10 = var9_12 /* !! */ .putDouble((double)((Float)var14_14).floatValue(), var1_2, var3_9);
                                break block4;
                            }
                            if (var6_10) break block21;
                            if (var2_8 != 0) {
                                throw new zzb("Could not add the value to a new CursorWindow. The size of value may be larger than what a CursorWindow can handle.");
                            }
                            break;
                        }
                    }
                    catch (RuntimeException var0_1) {
                        var2_8 = var12_7.size();
                        var1_2 = var4_3;
                        while (var1_2 < var2_8) {
                            ((CursorWindow)var12_7.get(var1_2)).close();
                            ++var1_2;
                        }
                        throw var0_1;
                    }
                    Log.d((String)"DataHolder", (String)("Couldn't populate window data for row " + var1_2 + " - allocating new window."));
                    var9_12 /* !! */ .freeLastRow();
                    var9_12 /* !! */  = new CursorWindow(false);
                    var9_12 /* !! */ .setStartPosition(var1_2);
                    var9_12 /* !! */ .setNumColumns(zza.zza((zza)var0).length);
                    var12_7.add(var9_12 /* !! */ );
                    var2_8 = var1_2 - 1;
                    var1_2 = 1;
                    break block22;
                }
                var3_9 = 0;
                var2_8 = var1_2;
                var1_2 = var3_9;
            }
            var3_9 = var1_2;
            var1_2 = var2_8 + 1;
            var10_6 = var9_12 /* !! */ ;
            var2_8 = var3_9;
        }
        ++var3_9;
        ** while (true)
    }

    public static DataHolder zzbI(int n2) {
        return DataHolder.zza(n2, null);
    }

    private void zzg(String string2, int n2) {
        if (this.zzajj == null || !this.zzajj.containsKey(string2)) {
            throw new IllegalArgumentException("No such column: " + string2);
        }
        if (this.isClosed()) {
            throw new IllegalArgumentException("Buffer is closed.");
        }
        if (n2 < 0 || n2 >= this.zzajn) {
            throw new CursorIndexOutOfBoundsException(n2, this.zzajn);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void close() {
        synchronized (this) {
            if (!this.mClosed) {
                this.mClosed = true;
                for (int i2 = 0; i2 < this.zzajk.length; ++i2) {
                    this.zzajk[i2].close();
                }
            }
            return;
        }
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void finalize() throws Throwable {
        try {
            if (!this.zzajp) return;
            if (this.zzajk.length <= 0) return;
            if (this.isClosed()) return;
            String string2 = this.zzajo == null ? "internal object: " + this.toString() : this.zzajo.toString();
            Log.e((String)"DataBuffer", (String)("Internal data leak within a DataBuffer object detected!  Be sure to explicitly call release() on all DataBuffer extending objects when you are done with them. (" + string2 + ")"));
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    public int getCount() {
        return this.zzajn;
    }

    public int getStatusCode() {
        return this.zzade;
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isClosed() {
        synchronized (this) {
            return this.mClosed;
        }
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zze.zza(this, parcel, n2);
    }

    public void zza(String string2, int n2, int n3, CharArrayBuffer charArrayBuffer) {
        this.zzg(string2, n2);
        this.zzajk[n3].copyStringToBuffer(n2, this.zzajj.getInt(string2), charArrayBuffer);
    }

    public long zzb(String string2, int n2, int n3) {
        this.zzg(string2, n2);
        return this.zzajk[n3].getLong(n2, this.zzajj.getInt(string2));
    }

    /*
     * Enabled aggressive block sorting
     */
    public int zzbH(int n2) {
        int n3;
        block1: {
            int n4 = 0;
            boolean bl2 = n2 >= 0 && n2 < this.zzajn;
            zzx.zzab(bl2);
            do {
                n3 = ++n4;
                if (n4 >= this.zzajm.length) break block1;
            } while (n2 >= this.zzajm[n4]);
            n3 = n4 - 1;
        }
        n2 = n3;
        if (n3 != this.zzajm.length) return n2;
        return n3 - 1;
    }

    public int zzc(String string2, int n2, int n3) {
        this.zzg(string2, n2);
        return this.zzajk[n3].getInt(n2, this.zzajj.getInt(string2));
    }

    public boolean zzcz(String string2) {
        return this.zzajj.containsKey(string2);
    }

    public String zzd(String string2, int n2, int n3) {
        this.zzg(string2, n2);
        return this.zzajk[n3].getString(n2, this.zzajj.getInt(string2));
    }

    public boolean zze(String string2, int n2, int n3) {
        this.zzg(string2, n2);
        return Long.valueOf(this.zzajk[n3].getLong(n2, this.zzajj.getInt(string2))) == 1L;
    }

    public float zzf(String string2, int n2, int n3) {
        this.zzg(string2, n2);
        return this.zzajk[n3].getFloat(n2, this.zzajj.getInt(string2));
    }

    public byte[] zzg(String string2, int n2, int n3) {
        this.zzg(string2, n2);
        return this.zzajk[n3].getBlob(n2, this.zzajj.getInt(string2));
    }

    public Uri zzh(String string2, int n2, int n3) {
        if ((string2 = this.zzd(string2, n2, n3)) == null) {
            return null;
        }
        return Uri.parse((String)string2);
    }

    public boolean zzi(String string2, int n2, int n3) {
        this.zzg(string2, n2);
        return this.zzajk[n3].isNull(n2, this.zzajj.getInt(string2));
    }

    public Bundle zzpZ() {
        return this.zzajl;
    }

    public void zzqd() {
        int n2;
        int n3 = 0;
        this.zzajj = new Bundle();
        for (n2 = 0; n2 < this.zzaji.length; ++n2) {
            this.zzajj.putInt(this.zzaji[n2], n2);
        }
        this.zzajm = new int[this.zzajk.length];
        int n4 = 0;
        n2 = n3;
        n3 = n4;
        while (n2 < this.zzajk.length) {
            this.zzajm[n2] = n3;
            n4 = this.zzajk[n2].getStartPosition();
            n3 += this.zzajk[n2].getNumRows() - (n3 - n4);
            ++n2;
        }
        this.zzajn = n3;
    }

    String[] zzqe() {
        return this.zzaji;
    }

    CursorWindow[] zzqf() {
        return this.zzajk;
    }

    public void zzu(Object object) {
        this.zzajo = object;
    }

    public static class zza {
        private final String[] zzaji;
        private final ArrayList<HashMap<String, Object>> zzajr;
        private final String zzajs;
        private final HashMap<Object, Integer> zzajt;
        private boolean zzaju;
        private String zzajv;

        private zza(String[] stringArray, String string2) {
            this.zzaji = zzx.zzz(stringArray);
            this.zzajr = new ArrayList();
            this.zzajs = string2;
            this.zzajt = new HashMap();
            this.zzaju = false;
            this.zzajv = null;
        }

        static /* synthetic */ ArrayList zzb(zza zza2) {
            return zza2.zzajr;
        }
    }

    public static class zzb
    extends RuntimeException {
        public zzb(String string2) {
            super(string2);
        }
    }
}

