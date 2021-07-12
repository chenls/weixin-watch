/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 */
package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import com.google.android.gms.internal.zzpl;
import java.util.concurrent.Callable;

public abstract class zza<T> {

    public static class zza
    extends zza<Boolean> {
        public static Boolean zza(final SharedPreferences sharedPreferences, final String string2, final Boolean bl2) {
            return zzpl.zzb(new Callable<Boolean>(){

                @Override
                public /* synthetic */ Object call() throws Exception {
                    return this.zzvt();
                }

                public Boolean zzvt() {
                    return sharedPreferences.getBoolean(string2, bl2.booleanValue());
                }
            });
        }
    }

    public static class zzb
    extends zza<Integer> {
        public static Integer zza(final SharedPreferences sharedPreferences, final String string2, final Integer n2) {
            return zzpl.zzb(new Callable<Integer>(){

                @Override
                public /* synthetic */ Object call() throws Exception {
                    return this.zzvu();
                }

                public Integer zzvu() {
                    return sharedPreferences.getInt(string2, n2.intValue());
                }
            });
        }
    }

    public static class zzc
    extends zza<Long> {
        public static Long zza(final SharedPreferences sharedPreferences, final String string2, final Long l2) {
            return zzpl.zzb(new Callable<Long>(){

                @Override
                public /* synthetic */ Object call() throws Exception {
                    return this.zzvv();
                }

                public Long zzvv() {
                    return sharedPreferences.getLong(string2, l2.longValue());
                }
            });
        }
    }

    public static class zzd
    extends zza<String> {
        public static String zza(final SharedPreferences sharedPreferences, final String string2, final String string3) {
            return zzpl.zzb(new Callable<String>(){

                @Override
                public /* synthetic */ Object call() throws Exception {
                    return this.zzkp();
                }

                public String zzkp() {
                    return sharedPreferences.getString(string2, string3);
                }
            });
        }
    }
}

