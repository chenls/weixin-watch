/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 */
package com.google.android.gms.internal;

import android.os.Binder;

public abstract class zzlz<T> {
    private static zza zzaiV;
    private static int zzaiW;
    private static String zzaiX;
    private static final Object zzqy;
    private T zzSC = null;
    protected final String zzvs;
    protected final T zzvt;

    static {
        zzqy = new Object();
        zzaiV = null;
        zzaiW = 0;
        zzaiX = "com.google.android.providers.gsf.permission.READ_GSERVICES";
    }

    protected zzlz(String string2, T t2) {
        this.zzvs = string2;
        this.zzvt = t2;
    }

    public static boolean isInitialized() {
        return zzaiV != null;
    }

    public static zzlz<Float> zza(String string2, Float f2) {
        return new zzlz<Float>(string2, f2){

            @Override
            protected /* synthetic */ Object zzct(String string2) {
                return this.zzcx(string2);
            }

            protected Float zzcx(String string2) {
                return zzaiV.zzb(this.zzvs, (Float)this.zzvt);
            }
        };
    }

    public static zzlz<Integer> zza(String string2, Integer n2) {
        return new zzlz<Integer>(string2, n2){

            @Override
            protected /* synthetic */ Object zzct(String string2) {
                return this.zzcw(string2);
            }

            protected Integer zzcw(String string2) {
                return zzaiV.zzb(this.zzvs, (Integer)this.zzvt);
            }
        };
    }

    public static zzlz<Long> zza(String string2, Long l2) {
        return new zzlz<Long>(string2, l2){

            @Override
            protected /* synthetic */ Object zzct(String string2) {
                return this.zzcv(string2);
            }

            protected Long zzcv(String string2) {
                return zzaiV.getLong(this.zzvs, (Long)this.zzvt);
            }
        };
    }

    public static zzlz<Boolean> zzk(String string2, boolean bl2) {
        return new zzlz<Boolean>(string2, Boolean.valueOf(bl2)){

            @Override
            protected /* synthetic */ Object zzct(String string2) {
                return this.zzcu(string2);
            }

            protected Boolean zzcu(String string2) {
                return zzaiV.zza(this.zzvs, (Boolean)this.zzvt);
            }
        };
    }

    public static int zzpW() {
        return zzaiW;
    }

    public static zzlz<String> zzv(String string2, String string3) {
        return new zzlz<String>(string2, string3){

            @Override
            protected /* synthetic */ Object zzct(String string2) {
                return this.zzcy(string2);
            }

            protected String zzcy(String string2) {
                return zzaiV.getString(this.zzvs, (String)this.zzvt);
            }
        };
    }

    public final T get() {
        if (this.zzSC != null) {
            return this.zzSC;
        }
        return this.zzct(this.zzvs);
    }

    protected abstract T zzct(String var1);

    public final T zzpX() {
        long l2 = Binder.clearCallingIdentity();
        try {
            T t2 = this.get();
            return t2;
        }
        finally {
            Binder.restoreCallingIdentity((long)l2);
        }
    }

    private static interface zza {
        public Long getLong(String var1, Long var2);

        public String getString(String var1, String var2);

        public Boolean zza(String var1, Boolean var2);

        public Float zzb(String var1, Float var2);

        public Integer zzb(String var1, Integer var2);
    }
}

