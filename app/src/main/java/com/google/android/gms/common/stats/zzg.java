/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.PowerManager$WakeLock
 *  android.os.Process
 *  android.text.TextUtils
 */
package com.google.android.gms.common.stats;

import android.os.PowerManager;
import android.os.Process;
import android.text.TextUtils;

public class zzg {
    public static String zza(PowerManager.WakeLock object, String string2) {
        StringBuilder stringBuilder = new StringBuilder().append(String.valueOf((long)Process.myPid() << 32 | (long)System.identityHashCode(object)));
        object = string2;
        if (TextUtils.isEmpty((CharSequence)string2)) {
            object = "";
        }
        return stringBuilder.append((String)object).toString();
    }
}

