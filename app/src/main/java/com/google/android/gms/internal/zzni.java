/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zze;
import java.util.regex.Pattern;

public class zzni {
    private static final Pattern zzaok = Pattern.compile("\\$\\{(.*?)\\}");

    public static boolean zzcV(String string2) {
        return string2 == null || zze.zzakF.zzb(string2);
    }
}

