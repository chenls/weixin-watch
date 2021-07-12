/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.common.internal;

public class zzv {
    private final String separator;

    private zzv(String string2) {
        this.separator = string2;
    }

    public static zzv zzcL(String string2) {
        return new zzv(string2);
    }

    public final String zza(Iterable<?> iterable) {
        return this.zza(new StringBuilder(), iterable).toString();
    }

    public final StringBuilder zza(StringBuilder stringBuilder, Iterable<?> object) {
        if ((object = object.iterator()).hasNext()) {
            stringBuilder.append(this.zzx(object.next()));
            while (object.hasNext()) {
                stringBuilder.append(this.separator);
                stringBuilder.append(this.zzx(object.next()));
            }
        }
        return stringBuilder;
    }

    CharSequence zzx(Object object) {
        if (object instanceof CharSequence) {
            return (CharSequence)object;
        }
        return object.toString();
    }
}

